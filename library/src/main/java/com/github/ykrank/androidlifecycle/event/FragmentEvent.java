package com.github.ykrank.androidlifecycle.event;

/**
 * Fragment lifecycle events
 * No onAttach and onCreate event because we could only listen to fragment lifecycle event after fragment attach to host
 */
public enum FragmentEvent {
    /** after onStart */
    START,
    /** after onResume */
    RESUME,
    /** before onPause */
    PAUSE,
    /** before onStop */
    STOP,
    /** before onDestroyView */
    DESTROY_VIEW,
    /** before onDestroy */
    DESTROY
}
