package me.ykrank.androidlifecycle.manager;

import android.support.annotation.NonNull;
import android.view.View;

import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.R;
import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.util.Util;

/**
 * Created by ykrank on 2017/7/12.
 */

public class ViewLifeCycleManager {
    @NonNull
    private View view;
    private FragmentLifeCycleManager fragmentLifeCycleManager;
    private ActivityLifeCycleManager activityLifeCycleManager;

    public ViewLifeCycleManager(@NonNull View view, @NonNull FragmentLifeCycleManager fragmentLifeCycleManager) {
        this.view = view;
        this.fragmentLifeCycleManager = fragmentLifeCycleManager;
    }

    public ViewLifeCycleManager(@NonNull View view, @NonNull ActivityLifeCycleManager activityLifeCycleManager) {
        this.view = view;
        this.activityLifeCycleManager = activityLifeCycleManager;
    }

    @NonNull
    public static ViewLifeCycleManager get(@NonNull View view) {
        Util.assertMainThread();
        ViewLifeCycleManager manager = (ViewLifeCycleManager) view.getTag(R.id.tag_view_lifecycle_manager);
        if (manager != null) {
            return manager;
        }

        FragmentLifeCycleManager fragmentLifeCycleManager = AndroidLifeCycle.getBoundFragmentLifeCycle(view);
        if (fragmentLifeCycleManager != null) {
            manager = new ViewLifeCycleManager(view, fragmentLifeCycleManager);
        } else {
            manager = new ViewLifeCycleManager(view, AndroidLifeCycle.with(view.getContext()));
        }
        view.setTag(R.id.tag_view_lifecycle_manager, manager);

        return manager;
    }
}
