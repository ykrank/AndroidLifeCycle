package com.github.ykrank.androidlifecycle.manager;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.event.InitSate;
import com.github.ykrank.androidlifecycle.lifecycle.FragmentLifeCycle;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycle;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

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

    @AnyThread
    public FragmentLifeCycleManager listen(FragmentEvent fragmentEvent, LifeCycleListener listener) {
        fragmentLifeCycle.addLifeCycleListener(fragmentEvent, listener);
        return this;
    }

    @AnyThread
    public FragmentLifeCycleManager unListen(FragmentEvent fragmentEvent, LifeCycleListener listener) {
        fragmentLifeCycle.removeLifeCycleListener(fragmentEvent, listener);
        return this;
    }

    public static InitSate getParentState(Fragment fragment) {
        InitSate initState;
        if (fragment.isResumed()) {
            initState = InitSate.RESUMED;
        } else if (fragment.isVisible()) {
            initState = InitSate.STARTED;
        } else if (fragment.isAdded()) {
            initState = InitSate.CREATED;
        } else {
            initState = InitSate.NONE;
        }
        return initState;
    }

    public static InitSate getParentState(android.app.Fragment fragment) {
        InitSate initState;
        if (fragment.isResumed()) {
            initState = InitSate.RESUMED;
        } else if (fragment.isVisible()) {
            initState = InitSate.STARTED;
        } else if (fragment.isAdded()) {
            initState = InitSate.CREATED;
        } else {
            initState = InitSate.NONE;
        }
        return initState;
    }
}
