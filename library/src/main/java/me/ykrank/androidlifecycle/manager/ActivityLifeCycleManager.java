package me.ykrank.androidlifecycle.manager;

import android.support.annotation.NonNull;

import me.ykrank.androidlifecycle.event.ActivityEvent;
import me.ykrank.androidlifecycle.lifecycle.ActivityLifeCycle;
import me.ykrank.androidlifecycle.lifecycle.LifeCycle;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

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
}
