package com.github.ykrank.androidlifecycle.manager;

import android.support.annotation.NonNull;

import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.lifecycle.ActivityLifeCycle;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycle;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

/**
 * manage lifecycle listener for {@link ActivityEvent}
 */

public class ActivityLifeCycleManager implements LifeCycleManager {
    private final ActivityLifeCycle activityLifeCycle = new ActivityLifeCycle();

    @NonNull
    @Override
    public LifeCycle getLifeCycle() {
        return activityLifeCycle;
    }

    public ActivityLifeCycleManager listen(ActivityEvent activityEvent, LifeCycleListener listener) {
        activityLifeCycle.addLifeCycleListener(activityEvent, listener);
        return this;
    }

    public ActivityLifeCycleManager unListen(ActivityEvent activityEvent, LifeCycleListener listener) {
        activityLifeCycle.removeLifeCycleListener(activityEvent, listener);
        return this;
    }
}
