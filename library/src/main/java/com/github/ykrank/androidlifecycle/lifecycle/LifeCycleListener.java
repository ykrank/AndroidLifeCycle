package com.github.ykrank.androidlifecycle.lifecycle;

import android.support.annotation.MainThread;

/**
 * When accept lifecycle event
 */

public interface LifeCycleListener {
    @MainThread
    void accept();
}
