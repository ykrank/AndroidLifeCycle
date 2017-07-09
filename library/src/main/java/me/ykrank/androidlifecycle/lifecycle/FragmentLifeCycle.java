package me.ykrank.androidlifecycle.lifecycle;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import me.ykrank.androidlifecycle.event.FragmentEvent;
import me.ykrank.androidlifecycle.util.Util;

/**
 * Add {@link LifeCycleListener} to attached fragment lifecycle
 */

public class FragmentLifeCycle implements LifeCycle {
    /**
     * weak LifeCycleListener hashSet
     */
    private Map<FragmentEvent, Set<LifeCycleListener>> lifeCycleListener = new HashMap<>(8);

    @Override
    public void onStart() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.START)) {
            listener.accept();
        }
    }

    @Override
    public void onResume() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.RESUME)) {
            listener.accept();
        }
    }

    @Override
    public void onPause() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.PAUSE)) {
            listener.accept();
        }
    }

    @Override
    public void onStop() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.STOP)) {
            listener.accept();
        }
    }

    @Override
    public void onDestroyView() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.DESTROY_VIEW)) {
            listener.accept();
        }
    }

    @Override
    public void onDestroy() {
        for (LifeCycleListener listener : getLifeCycleListeners(FragmentEvent.DESTROY)) {
            listener.accept();
        }
    }

    @NonNull
    private Set<LifeCycleListener> getDirtyLifeCycleListeners(FragmentEvent fragmentEvent) {
        Util.assertMainThread();
        Set<LifeCycleListener> lifeCycleListenerSet = lifeCycleListener.get(fragmentEvent);
        if (lifeCycleListenerSet == null) {
            lifeCycleListenerSet = Collections.newSetFromMap(new WeakHashMap<LifeCycleListener, Boolean>());
            lifeCycleListener.put(fragmentEvent, lifeCycleListenerSet);
        }
        return lifeCycleListenerSet;
    }

    /**
     * only for iterate, should not be hold by other object as LifeCycleListener in this list hold context
     */
    @NonNull
    List<LifeCycleListener> getLifeCycleListeners(FragmentEvent fragmentEvent) {
        return Util.getSnapshot(getDirtyLifeCycleListeners(fragmentEvent));
    }

    public void addLifeCycleListener(FragmentEvent fragmentEvent, LifeCycleListener listener) {
        getDirtyLifeCycleListeners(fragmentEvent).add(listener);
    }
}
