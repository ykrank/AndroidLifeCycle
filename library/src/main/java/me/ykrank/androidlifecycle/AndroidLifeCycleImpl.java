package me.ykrank.androidlifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;
import me.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;
import me.ykrank.androidlifecycle.manager.LifeCycleManagerFragment;
import me.ykrank.androidlifecycle.manager.LifeCycleManagerSupportFragment;
import me.ykrank.androidlifecycle.util.Preconditions;
import me.ykrank.androidlifecycle.util.Util;

/**
 * delegate of {@link AndroidLifeCycle}
 */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class AndroidLifeCycleImpl implements Handler.Callback {
    private static final String TAG = "AndroidLifeCycle";
    private static final String FRAGMENT_TAG = "me.ykrank.androidlifecycle.manager";
    static boolean loggable = false;

    private static final int ID_REMOVE_FRAGMENT_MANAGER = 1;

    /**
     * Pending adds for RequestManagerFragments.
     */
    final Map<android.app.FragmentManager, LifeCycleManagerFragment> pendingLifeCycleManagerFragments =
            new HashMap<>();

    /**
     * Main thread handler to handle cleaning up pending fragment maps.
     */
    private final Handler handler = new Handler(Looper.getMainLooper(), this /* Callback */);

    ActivityLifeCycleManager with(@Nullable Context context, InitSate initSate) {
        Util.assertMainThread();
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        if (context instanceof FragmentActivity) {
            return with((FragmentActivity) context, initSate);
        } else if (context instanceof Activity) {
            return with((Activity) context, initSate);
        } else if (context instanceof ContextWrapper) {
            return with(((ContextWrapper) context).getBaseContext(), initSate);
        }

        throw new IllegalArgumentException("Illegal type of context:" + context.toString());
    }

    @NonNull
    ActivityLifeCycleManager with(@NonNull FragmentActivity activity, InitSate initSate) {
        Util.assertMainThread();
        assertNotDestroyed(activity);
        FragmentManager fm = activity.getSupportFragmentManager();
        LifeCycleManagerSupportFragment current = getLifeCycleManagerSupportFragment(fm, null);
        ActivityLifeCycleManager lifeCycleManager = (ActivityLifeCycleManager) current.getLifeCycleManager();
        if (lifeCycleManager == null) {
            current.setInitState(initSate);
            lifeCycleManager = new ActivityLifeCycleManager();
            current.setLifeCycleManager(lifeCycleManager);
        }
        return (ActivityLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    @NonNull
    ActivityLifeCycleManager with(@NonNull Activity activity, InitSate initSate) {
        Util.assertMainThread();
        assertNotDestroyed(activity);
        android.app.FragmentManager fm = activity.getFragmentManager();

        LifeCycleManagerFragment current = getLifeCycleManagerFragment(fm, null);
        ActivityLifeCycleManager lifeCycleManager = (ActivityLifeCycleManager) current.getLifeCycleManager();
        if (lifeCycleManager == null) {
            current.setInitState(initSate);
            lifeCycleManager = new ActivityLifeCycleManager();
            current.setLifeCycleManager(lifeCycleManager);
        }
        return (ActivityLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    @NonNull
    FragmentLifeCycleManager with(@NonNull Fragment fragment, InitSate initSate) {
        if (loggable) {
            Log.d(TAG, "isAdded:" + fragment.isAdded() + ",isResumed:" + fragment.isResumed() + ",isVisible:" + fragment.isVisible());
        }
        Preconditions.checkNotNull(fragment.getActivity(),
                "You cannot start a load on a fragment before it is attached or after it is destroyed");
        Util.assertMainThread();
        FragmentManager fm = fragment.getChildFragmentManager();
        LifeCycleManagerSupportFragment current = getLifeCycleManagerSupportFragment(fm, fragment);
        FragmentLifeCycleManager lifeCycleManager = (FragmentLifeCycleManager) current.getLifeCycleManager();
        if (lifeCycleManager == null) {
            current.setInitState(initSate);
            lifeCycleManager = new FragmentLifeCycleManager();
            current.setLifeCycleManager(lifeCycleManager);
        }
        return (FragmentLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    FragmentLifeCycleManager with(@NonNull android.app.Fragment fragment, InitSate initSate) {
        if (loggable) {
            Log.d(TAG, "isAdded:" + fragment.isAdded() + ",isResumed:" + fragment.isResumed() + ",isVisible:" + fragment.isVisible());
        }
        Preconditions.checkNotNull(fragment.getActivity(),
                "You cannot start a load on a fragment before it is attached or after it is destroyed");
        Util.assertMainThread();
        android.app.FragmentManager fm = fragment.getChildFragmentManager();
        LifeCycleManagerFragment current = getLifeCycleManagerFragment(fm, fragment);
        FragmentLifeCycleManager lifeCycleManager = (FragmentLifeCycleManager) current.getLifeCycleManager();
        if (lifeCycleManager == null) {
            current.setInitState(initSate);
            lifeCycleManager = new FragmentLifeCycleManager();
            current.setLifeCycleManager(lifeCycleManager);
        }
        return (FragmentLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    @NonNull
    LifeCycleManagerFragment getLifeCycleManagerFragment(
            @NonNull final android.app.FragmentManager fm, @Nullable android.app.Fragment parentHint) {
        LifeCycleManagerFragment current = (LifeCycleManagerFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = pendingLifeCycleManagerFragments.get(fm);
            if (current == null) {
                current = new LifeCycleManagerFragment();
                current.setParentFragmentHint(parentHint);
                pendingLifeCycleManagerFragments.put(fm, current);
                fm.beginTransaction().add(current, FRAGMENT_TAG).commitAllowingStateLoss();
                handler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fm).sendToTarget();
            }
        }
        return current;
    }

    @NonNull
    LifeCycleManagerSupportFragment getLifeCycleManagerSupportFragment(
            @NonNull final FragmentManager fm, @Nullable Fragment parentHint) {
        LifeCycleManagerSupportFragment current = (LifeCycleManagerSupportFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = new LifeCycleManagerSupportFragment();
            current.setParentFragmentHint(parentHint);
            fm.beginTransaction().add(current, FRAGMENT_TAG).commitNowAllowingStateLoss();
        }
        return current;
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (msg.what) {
            case ID_REMOVE_FRAGMENT_MANAGER:
                android.app.FragmentManager fm = (android.app.FragmentManager) msg.obj;
                key = fm;
                removed = pendingLifeCycleManagerFragments.remove(fm);
                break;
            default:
                handled = false;
                break;
        }
        if (handled && removed == null && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }
}
