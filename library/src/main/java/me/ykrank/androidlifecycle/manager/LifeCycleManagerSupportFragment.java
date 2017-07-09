package me.ykrank.androidlifecycle.manager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.ykrank.androidlifecycle.lifecycle.LifeCycle;

/**
 * Fragment attached to target support activity or fragment, to accept lifecycle event
 */
public class LifeCycleManagerSupportFragment extends Fragment {
    @Nullable
    private LifeCycleManager lifeCycleManager;
    @Nullable
    private LifeCycle lifeCycle;

    @Nullable
    LifeCycleManager getLifeCycleManager() {
        return lifeCycleManager;
    }

    public void setLifeCycleManager(@Nullable LifeCycleManager lifeCycleManager) {
        this.lifeCycleManager = lifeCycleManager;
        if (lifeCycleManager == null) {
            this.lifeCycle = null;
        } else {
            this.lifeCycle = lifeCycleManager.getLifeCycle();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifeCycle != null) {
            lifeCycle.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lifeCycle != null) {
            lifeCycle.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifeCycle != null) {
            lifeCycle.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (lifeCycle != null) {
            lifeCycle.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (lifeCycle != null) {
            lifeCycle.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lifeCycle != null) {
            lifeCycle.onDestroy();
        }
    }
}
