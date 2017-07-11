package me.ykrank.androidlifecycle.manager;

import android.support.annotation.NonNull;

import me.ykrank.androidlifecycle.event.FragmentEvent;
import me.ykrank.androidlifecycle.lifecycle.FragmentLifeCycle;
import me.ykrank.androidlifecycle.lifecycle.LifeCycle;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

/**
 * manage lifecycle listener for {@link FragmentEvent}
 */

public class FragmentLifeCycleManager implements LifeCycleManager {
    private final FragmentLifeCycle fragmentLifeCycle = new FragmentLifeCycle();

    @NonNull
    @Override
    public LifeCycle getLifeCycle() {
        return fragmentLifeCycle;
    }

    public FragmentLifeCycleManager listen(FragmentEvent fragmentEvent, LifeCycleListener listener) {
        fragmentLifeCycle.addLifeCycleListener(fragmentEvent, listener);
        return this;
    }
}
