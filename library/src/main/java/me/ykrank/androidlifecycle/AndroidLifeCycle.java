package me.ykrank.androidlifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.lang.ref.WeakReference;

import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.lifemap.ActivityLifecycleMap;
import me.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;
import me.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;
import me.ykrank.androidlifecycle.manager.ViewLifeCycleManager;
import me.ykrank.androidlifecycle.util.Util;

/**
 * A singleton to present a simple static interface for building lifecycle manager with context, activity, fragment
 */

public class AndroidLifeCycle {
    private static final String TAG = "AndroidLifeCycle";
    private static volatile AndroidLifeCycle instance;

    private AndroidLifeCycleImpl lifeCycleImpl = new AndroidLifeCycleImpl();

    /**
     * Get the singleton.
     *
     * @return the singleton
     */
    public static AndroidLifeCycle get() {
        if (instance == null) {
            synchronized (AndroidLifeCycle.class) {
                if (instance == null) {
                    instance = new AndroidLifeCycle();
                }
            }
        }
        return instance;
    }

    /**
     * Get a LifeCycleManager by passing in a context.
     *
     * @param context activity, or wrapped activity, will not be retained
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@Nullable Context context) {
        Util.assertMainThread();
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        if (context instanceof FragmentActivity) {
            return with((FragmentActivity) context);
        } else if (context instanceof Activity) {
            return with((Activity) context);
        } else if (context instanceof ContextWrapper) {
            return with(((ContextWrapper) context).getBaseContext());
        }

        throw new IllegalArgumentException("Illegal type of context:" + context.toString());
    }

    /**
     * Get a LifeCycleManager by passing in a {@link FragmentActivity}.
     *
     * @param activity listened activity, will not be retained
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@NonNull FragmentActivity activity) {
        return get().lifeCycleImpl.with(activity, ActivityLifecycleMap.getState(activity));
    }

    /**
     * Get a LifeCycleManager by passing in a {@link Activity}.
     *
     * @param activity listened activity, will not be retained
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@NonNull Activity activity) {
        return get().lifeCycleImpl.with(activity, ActivityLifecycleMap.getState(activity));
    }

    /**
     * Get a LifeCycleManager by passing in a {@link Fragment}.
     * <p>
     * <p>Auto init fragment state</p>
     *
     * @param fragment listened fragment, will not be retained
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.FragmentEvent}
     */
    @NonNull
    public static FragmentLifeCycleManager with(@NonNull Fragment fragment) {
        return get().lifeCycleImpl.with(fragment, getParentState(fragment));
    }

    /**
     * Get a LifeCycleManager by passing in a {@link android.app.Fragment}.
     * <p>
     * <p>Only used after JELLY_BEAN_MR1(api 17), as fragment after this could add child fragment</p>
     * <p>Auto init fragment state</p>
     *
     * @param fragment listened fragment, will not be retained
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.FragmentEvent}
     */
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static FragmentLifeCycleManager with(@NonNull android.app.Fragment fragment) {
        return get().lifeCycleImpl.with(fragment, getParentState(fragment));
    }

    public static ViewLifeCycleManager with(@NonNull View view) {
        return ViewLifeCycleManager.get(view);
    }

    static InitSate getParentState(Fragment fragment) {
        InitSate initState;
        if (fragment.isResumed()) {
            initState = InitSate.RESUMED;
        } else if (fragment.isVisible()) {
            initState = InitSate.STARTED;
        } else {
            initState = InitSate.CREATED;
        }
        return initState;
    }

    static InitSate getParentState(android.app.Fragment fragment) {
        InitSate initState;
        if (fragment.isResumed()) {
            initState = InitSate.RESUMED;
        } else if (fragment.isVisible()) {
            initState = InitSate.STARTED;
        } else {
            initState = InitSate.CREATED;
        }
        return initState;
    }

    public static void bindFragment(View view, Fragment fragment) {
        view.setTag(R.id.tag_view_lifecycle_bind_fragment, new WeakReference<>(fragment));
    }

    public static void bindFragment(View view, android.app.Fragment fragment) {
        view.setTag(R.id.tag_view_lifecycle_bind_fragment, new WeakReference<>(fragment));
    }

    @Nullable
    public static FragmentLifeCycleManager getBoundFragmentLifeCycle(View view) {
        Util.assertMainThread();
        Object bindFragmentReference = view.getTag(R.id.tag_view_lifecycle_bind_fragment);
        if (bindFragmentReference != null) {
            Object bindFragment = ((WeakReference<Object>) bindFragmentReference).get();
            if (bindFragment != null) {
                if (bindFragment instanceof Fragment) {
                    return with((Fragment) bindFragment);
                }
                if (bindFragment instanceof android.app.Fragment) {
                    return with((android.app.Fragment) bindFragment);
                }
            }
        }
        return null;
    }

    public static boolean loggable() {
        return AndroidLifeCycleImpl.loggable;
    }
}
