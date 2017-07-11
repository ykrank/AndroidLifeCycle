package me.ykrank.androidlifecycle.manager;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;

import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.event.InitSate;
import me.ykrank.androidlifecycle.lifecycle.LifeCycle;

/**

 */
public class LifeCycleManagerFragment extends Fragment {
    static final String TAG = "LifeCycleFragment";
    
    @Nullable
    private LifeCycleManager lifeCycleManager;
    @Nullable
    private LifeCycle lifeCycle;
    /**
     * associate fragment if attach to fragment childFragmentManager
     */
    @Nullable
    private Fragment parentFragmentHint;
    /**
     * Attached fragment or activity state when attached
     * 0: isAdded:true,isResumed:false,isVisible:false
     * 1: isAdded:true,isResumed:false,isVisible:true
     * 2: isAdded:true,isResumed:true,isVisible:true
     */
    private InitSate initState = InitSate.BEFORE_STARTED;
    private boolean init = false;

    @Nullable
    public LifeCycleManager getLifeCycleManager() {
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

    public void setInitState(InitSate initState) {
        this.initState = initState;
    }

    @Nullable
    public Fragment getParentFragmentHint() {
        return parentFragmentHint;
    }

    public void setParentFragmentHint(@Nullable Fragment parentFragmentHint) {
        this.parentFragmentHint = parentFragmentHint;
    }

    @Override
    public void onStart() {
        super.onStart();
        l("onStart");
        if (!init) {
            if (initState == InitSate.RESUMED) {
                return;
            } else if (initState == InitSate.BEFORE_RESUMED) {
                init = true;
                return;
            }
            init = true;
        }

        if (lifeCycle != null) {
            lifeCycle.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        l("onResume");
        if (!init) {
            if (initState == InitSate.RESUMED) {
                init = true;
                return;
            }
            init = true;
        }
        if (lifeCycle != null) {
            lifeCycle.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        l("onPause");
        if (lifeCycle != null) {
            lifeCycle.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        l("onStop");
        if (lifeCycle != null) {
            lifeCycle.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        l("onDestroyView");
        if (lifeCycle != null) {
            lifeCycle.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        l("onDestroy");
        if (lifeCycle != null) {
            lifeCycle.onDestroy();
        }
    }

    private void l(String msg) {
        if (AndroidLifeCycle.loggable() && Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, msg);
        }
    }

}
