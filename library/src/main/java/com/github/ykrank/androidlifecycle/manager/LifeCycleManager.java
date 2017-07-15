package com.github.ykrank.androidlifecycle.manager;

import android.support.annotation.Nullable;

import com.github.ykrank.androidlifecycle.lifecycle.LifeCycle;

/**
 * manage lifecycle listener
 */

public interface LifeCycleManager {

    @Nullable
    LifeCycle getLifeCycle();
}
