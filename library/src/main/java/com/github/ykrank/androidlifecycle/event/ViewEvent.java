package com.github.ykrank.androidlifecycle.event;

/**
 * View lifecycle events.
 * No onCreate event because we could only listen to context lifecycle event after create
 */
public enum ViewEvent {
    /** before onStart */
    START,
    /** after onResume */
    RESUME,
    /** before onPause */
    PAUSE,
    /** before onStop */
    STOP,
    /** before onDestroy */
    DESTROY
}
