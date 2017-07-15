package com.github.ykrank.androidlifecycle.lifecycle;

/**
 * Inner {@link com.github.ykrank.androidlifecycle.manager.LifeCycleManagerSupportFragment}
 * and {@link com.github.ykrank.androidlifecycle.manager.LifeCycleManagerFragment} lifecycle event
 */

public interface LifeCycle {
    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroyView();

    void onDestroy();
}
