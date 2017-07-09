package me.ykrank.androidlifecycle.lifecycle;

/**
 * Inner {@link me.ykrank.androidlifecycle.manager.LifeCycleManagerSupportFragment}
 * and {@link me.ykrank.androidlifecycle.manager.LifeCycleManagerFragment} lifecycle event
 */

public interface LifeCycle {
    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroyView();

    void onDestroy();
}
