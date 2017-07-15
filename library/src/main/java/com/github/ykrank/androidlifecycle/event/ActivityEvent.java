package com.github.ykrank.androidlifecycle.event;

/**
 * Activity lifecycle events.
 * No onCreate event because we could only listen to activity lifecycle event after create
 */
public enum ActivityEvent {
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
