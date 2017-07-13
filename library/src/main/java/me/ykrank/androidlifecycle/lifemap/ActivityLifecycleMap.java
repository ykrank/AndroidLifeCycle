package me.ykrank.androidlifecycle.lifemap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import me.ykrank.androidlifecycle.event.InitSate;

/**
 * Record activity state
 */
public class ActivityLifecycleMap {
    private static AtomicBoolean sInitialized = new AtomicBoolean(false);
    private static DispatcherActivityCallback callback;

    static void init(Context context) {
        if (!sInitialized.getAndSet(true)) {
            callback = new DispatcherActivityCallback();
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(callback);
        }
    }

    /**
     * Get activity state
     */
    @NonNull
    public static InitSate getState(Activity activity) {
        InitSate state = null;
        if (callback != null) {
            state = callback.lifecycleMap.get(activity);
        }
        if (state == null) {
            state = InitSate.NONE;
        }
        return state;
    }

    @VisibleForTesting
    private static class DispatcherActivityCallback extends EmptyActivityLifecycleCallbacks {
        private final WeakHashMap<Activity, InitSate> lifecycleMap = new WeakHashMap<>();

        DispatcherActivityCallback() {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            lifecycleMap.put(activity, InitSate.CREATED);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            lifecycleMap.put(activity, InitSate.STARTED);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            lifecycleMap.put(activity, InitSate.RESUMED);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            lifecycleMap.remove(activity);
        }
    }


}
