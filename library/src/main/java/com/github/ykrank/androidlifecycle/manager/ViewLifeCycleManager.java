package com.github.ykrank.androidlifecycle.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.ref.WeakReference;

import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.R;
import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.event.InitSate;
import com.github.ykrank.androidlifecycle.event.ViewEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.util.Preconditions;
import com.github.ykrank.androidlifecycle.util.Util;

/**
 * Created by ykrank on 2017/7/12.
 */

public class ViewLifeCycleManager {
    private FragmentLifeCycleManager fragmentLifeCycleManager;
    private ActivityLifeCycleManager activityLifeCycleManager;

    public ViewLifeCycleManager(@NonNull FragmentLifeCycleManager fragmentLifeCycleManager) {
        this.fragmentLifeCycleManager = fragmentLifeCycleManager;
    }

    public ViewLifeCycleManager(@NonNull ActivityLifeCycleManager activityLifeCycleManager) {
        this.activityLifeCycleManager = activityLifeCycleManager;
    }

    @NonNull
    public static ViewLifeCycleManager get(@NonNull View view) {
        Util.assertMainThread();
        ViewLifeCycleManager manager = (ViewLifeCycleManager) view.getTag(R.id.tag_view_lifecycle_manager);
        if (manager != null) {
            return manager;
        }

        Object bindFragment = getBoundFragment(view);
        if (bindFragment != null) {
            if (bindFragment instanceof Fragment) {
                InitSate initSate = FragmentLifeCycleManager.getParentState((Fragment) bindFragment);
                if (initSate != InitSate.NONE) {
                    //Fragment added to parent
                    manager = new ViewLifeCycleManager(AndroidLifeCycle.with((Fragment) bindFragment));
                } else {
                    throw new IllegalStateException("Bound fragment not attached to parent");
                }
            } else if (bindFragment instanceof android.app.Fragment) {
                InitSate initSate = FragmentLifeCycleManager.getParentState((android.app.Fragment) bindFragment);
                if (initSate != InitSate.NONE) {
                    //Fragment added to parent
                    manager = new ViewLifeCycleManager(AndroidLifeCycle.with((android.app.Fragment) bindFragment));
                } else {
                    throw new IllegalStateException("Bound fragment not attached to parent");
                }
            }
        }

        if (manager == null) {
            manager = new ViewLifeCycleManager(AndroidLifeCycle.with(view.getContext()));
        }

        view.setTag(R.id.tag_view_lifecycle_manager, manager);

        return manager;
    }

    public static void bindFragment(View view, Fragment fragment) {
        view.setTag(R.id.tag_view_lifecycle_bind_fragment, new WeakReference<>(fragment));
    }

    public static void bindFragment(View view, android.app.Fragment fragment) {
        view.setTag(R.id.tag_view_lifecycle_bind_fragment, new WeakReference<>(fragment));
    }

    /**
     * Get bound {@link android.app.Fragment} or {@link Fragment}
     */
    @Nullable
    public static Object getBoundFragment(View view) {
        Util.assertMainThread();
        Object bindFragmentReference = view.getTag(R.id.tag_view_lifecycle_bind_fragment);
        if (bindFragmentReference != null) {
            return ((WeakReference<Object>) bindFragmentReference).get();
        }
        return null;
    }

    public ViewLifeCycleManager listen(ViewEvent viewEvent, LifeCycleListener listener) {
        if (fragmentLifeCycleManager != null) {
            fragmentLifeCycleManager.listen(toFragmentEventEvent(viewEvent), listener);
        } else {
            Preconditions.checkNotNull(activityLifeCycleManager);
            activityLifeCycleManager.listen(toActivityEvent(viewEvent), listener);
        }
        return this;
    }

    public ViewLifeCycleManager unListen(ViewEvent viewEvent, LifeCycleListener listener) {
        if (fragmentLifeCycleManager != null) {
            fragmentLifeCycleManager.unListen(toFragmentEventEvent(viewEvent), listener);
        } else {
            Preconditions.checkNotNull(activityLifeCycleManager);
            activityLifeCycleManager.unListen(toActivityEvent(viewEvent), listener);
        }
        return this;
    }

    private ActivityEvent toActivityEvent(ViewEvent event) {
        switch (event) {
            case START:
                return ActivityEvent.START;
            case RESUME:
                return ActivityEvent.RESUME;
            case PAUSE:
                return ActivityEvent.PAUSE;
            case STOP:
                return ActivityEvent.STOP;
            case DESTROY:
                return ActivityEvent.DESTROY;
            default:
                throw new IllegalArgumentException("Illegal ViewEvent:" + event);
        }
    }

    private FragmentEvent toFragmentEventEvent(ViewEvent event) {
        switch (event) {
            case START:
                return FragmentEvent.START;
            case RESUME:
                return FragmentEvent.RESUME;
            case PAUSE:
                return FragmentEvent.PAUSE;
            case STOP:
                return FragmentEvent.STOP;
            case DESTROY:
                return FragmentEvent.DESTROY;
            default:
                throw new IllegalArgumentException("Illegal ViewEvent:" + event);
        }
    }
}
