package me.ykrank.androidlifecycle.lifecycle;

import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.ykrank.androidlifecycle.event.ActivityEvent;
import me.ykrank.androidlifecycle.util.Util;

/**
 * Add {@link LifeCycleListener} to attached activity lifecycle
 */
public class ActivityLifeCycle implements LifeCycle {
    /**
     * weak LifeCycleListener hashSet
     */
    private Map<ActivityEvent, Set<LifeCycleListener>> lifeCycleListener = new HashMap<>(8);

    @Override
    public void onStart() {
        for (LifeCycleListener listener : getLifeCycleListeners(ActivityEvent.START)) {
            listener.accept();
        }
    }

    @Override
    public void onResume() {
        for (LifeCycleListener listener : getLifeCycleListeners(ActivityEvent.RESUME)) {
            listener.accept();
        }
    }

    @Override
    public void onPause() {
        for (LifeCycleListener listener : getLifeCycleListeners(ActivityEvent.PAUSE)) {
            listener.accept();
        }
    }

    @Override
    public void onStop() {
        for (LifeCycleListener listener : getLifeCycleListeners(ActivityEvent.STOP)) {
            listener.accept();
        }
    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDestroy() {
        for (LifeCycleListener listener : getLifeCycleListeners(ActivityEvent.DESTROY)) {
            listener.accept();
        }
        lifeCycleListener.clear();
    }

    @NonNull
    private Set<LifeCycleListener> getDirtyLifeCycleListeners(ActivityEvent activityEvent) {
        Set<LifeCycleListener> lifeCycleListenerSet = lifeCycleListener.get(activityEvent);
        if (lifeCycleListenerSet == null) {
            lifeCycleListenerSet = new ArraySet<>();
            lifeCycleListener.put(activityEvent, lifeCycleListenerSet);
        }
        return lifeCycleListenerSet;
    }

    /**
     * only for iterate, should not be hold by other object as LifeCycleListener in this list hold context
     */
    @NonNull
    List<LifeCycleListener> getLifeCycleListeners(ActivityEvent activityEvent) {
        return Util.getSnapshot(getDirtyLifeCycleListeners(activityEvent));
    }

    public boolean addLifeCycleListener(ActivityEvent activityEvent, LifeCycleListener listener) {
        return getDirtyLifeCycleListeners(activityEvent).add(listener);
    }

    public boolean removeLifeCycleListener(ActivityEvent activityEvent, LifeCycleListener listener) {
        return getDirtyLifeCycleListeners(activityEvent).remove(listener);
    }
}
