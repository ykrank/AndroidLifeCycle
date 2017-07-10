package me.ykrank.androidlifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;
import me.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;
import me.ykrank.androidlifecycle.manager.LifeCycleManagerSupportFragment;
import me.ykrank.androidlifecycle.util.Preconditions;
import me.ykrank.androidlifecycle.util.Util;

/**
 * A singleton to present a simple static interface for building lifecycle manager with context, activity, fragment
 */

public class AndroidLifeCycle {
    private static final String TAG = "AndroidLifeCycle";
    private static final String FRAGMENT_TAG = "me.ykrank.androidlifecycle.manager";
    private static boolean loggable = true;

    @NonNull
    public static ActivityLifeCycleManager get(@NonNull FragmentActivity activity) {
        return get(activity, InitSate.BEFORE_STARTED);
    }

    @NonNull
    public static ActivityLifeCycleManager get(@NonNull FragmentActivity activity, InitSate initSate) {
        Util.assertMainThread();
        assertNotDestroyed(activity);
        FragmentManager fm = activity.getSupportFragmentManager();
        LifeCycleManagerSupportFragment current =
                (LifeCycleManagerSupportFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            current = new LifeCycleManagerSupportFragment();
            current.setParentFragmentHint(null);
            current.setInitState(initSate);
            current.setLifeCycleManager(new ActivityLifeCycleManager());

            fm.beginTransaction().add(current, FRAGMENT_TAG).commitNowAllowingStateLoss();
        }
        return (ActivityLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    @NonNull
    public static FragmentLifeCycleManager get(@NonNull Fragment fragment) {
        return get(fragment, getParentState(fragment));
    }

    @NonNull
    public static FragmentLifeCycleManager get(@NonNull Fragment fragment, InitSate initSate) {
        Preconditions.checkNotNull(fragment.getActivity(),
                "You cannot start a load on a fragment before it is attached or after it is destroyed");
        Util.assertMainThread();
        FragmentManager fm = fragment.getChildFragmentManager();
        LifeCycleManagerSupportFragment current =
                (LifeCycleManagerSupportFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        if (current == null) {
            if (loggable) {
                Log.d(TAG, "isAdded:" + fragment.isAdded() + ",isResumed:" + fragment.isResumed() + ",isVisible:" + fragment.isVisible());
            }

            current = new LifeCycleManagerSupportFragment();
            current.setParentFragmentHint(fragment);
            current.setInitState(initSate);
            current.setLifeCycleManager(new FragmentLifeCycleManager());

            fm.beginTransaction().add(current, FRAGMENT_TAG).commitNowAllowingStateLoss();
        }
        return (FragmentLifeCycleManager) Preconditions.checkNotNull(current.getLifeCycleManager());
    }

    private static InitSate getParentState(Fragment fragment) {
        InitSate initState;
        if (fragment.isResumed()) {
            initState = InitSate.RESUMED;
        } else if (fragment.isVisible()) {
            initState = InitSate.BEFORE_RESUMED;
        } else {
            initState = InitSate.BEFORE_STARTED;
        }
        return initState;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }
}
