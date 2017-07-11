package me.ykrank.androidlifecycle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;
import me.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;

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
     * @param context  activity, or wrapped activity, will not be retained
     * @param initSate activity state when call this method
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@Nullable Context context, InitSate initSate) {
        return get().lifeCycleImpl.with(context, initSate);
    }

    /**
     * Get a LifeCycleManager by passing in a {@link FragmentActivity}.
     *
     * @param activity listened activity, will not be retained
     * @param initSate activity state when call this method
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@NonNull FragmentActivity activity, InitSate initSate) {
        return get().lifeCycleImpl.with(activity, initSate);
    }

    /**
     * Get a LifeCycleManager by passing in a {@link Activity}.
     *
     * @param activity listened activity, will not be retained
     * @param initSate activity state when call this method
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.ActivityEvent}
     */
    @NonNull
    public static ActivityLifeCycleManager with(@NonNull Activity activity, InitSate initSate) {
        return get().lifeCycleImpl.with(activity, initSate);
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
        return with(fragment, getParentState(fragment));
    }

    /**
     * Get a LifeCycleManager by passing in a {@link Fragment}.
     *
     * @param fragment listened fragment, will not be retained
     * @param initSate fragment state when call this method
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.FragmentEvent}
     */
    @NonNull
    public static FragmentLifeCycleManager with(@NonNull Fragment fragment, InitSate initSate) {
        return get().lifeCycleImpl.with(fragment, initSate);
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
        return with(fragment, getParentState(fragment));
    }

    /**
     * Get a LifeCycleManager by passing in a {@link android.app.Fragment}.
     * <p>
     * <p>Only used after JELLY_BEAN_MR1(api 17), as fragment after this could add child fragment</p>
     *
     * @param fragment listened fragment, will not be retained
     * @param initSate fragment state when call this method
     * @return A LifeCycleManager to listen {@link me.ykrank.androidlifecycle.event.FragmentEvent}
     */
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static FragmentLifeCycleManager with(@NonNull android.app.Fragment fragment, InitSate initSate) {
        return get().lifeCycleImpl.with(fragment, initSate);
    }

    static InitSate getParentState(Fragment fragment) {
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

    static InitSate getParentState(android.app.Fragment fragment) {
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

    public static boolean loggable() {
        return AndroidLifeCycleImpl.loggable;
    }
}
