package me.ykrank.androidlifecycle.manager;

import android.support.annotation.Nullable;

import me.ykrank.androidlifecycle.lifecycle.LifeCycle;

/**
 * manage lifecycle listener
 */

public interface LifeCycleManager {

    @Nullable
    LifeCycle getLifeCycle();
}
