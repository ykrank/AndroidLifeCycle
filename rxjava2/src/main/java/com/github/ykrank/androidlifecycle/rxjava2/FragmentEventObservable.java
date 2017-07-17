package com.github.ykrank.androidlifecycle.rxjava2;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;
import com.github.ykrank.androidlifecycle.util.Preconditions;
import com.github.ykrank.androidlifecycle.util.Util;

/**
 * Provide fragment lifecycle event
 */
final class FragmentEventObservable extends Observable<AndroidEvent> {
    private final Fragment supportFragment;
    private final android.app.Fragment fragment;

    FragmentEventObservable(Fragment fragment) {
        this.supportFragment = fragment;
        this.fragment = null;
    }

    FragmentEventObservable(android.app.Fragment fragment) {
        this.supportFragment = null;
        this.fragment = fragment;
    }

    @Override
    protected void subscribeActual(final Observer<? super AndroidEvent> observer) {
        Util.assertMainThread();
        FragmentLifeCycleManager lifeCycleManager;
        if (supportFragment != null) {
            lifeCycleManager = AndroidLifeCycle.with(supportFragment);
        } else {
            Preconditions.checkNotNull(fragment);
            lifeCycleManager = AndroidLifeCycle.with(fragment);
        }

        LifeCycleListener listener = new LifeCycleListener() {
            @Override
            public void accept() {
                observer.onNext(AndroidEvent.DESTROY);
            }
        };
        observer.onSubscribe(new ListenerDispose(lifeCycleManager, listener));

        lifeCycleManager.listen(FragmentEvent.DESTROY, listener);

        observer.onNext(AndroidEvent.START);
    }

    private static final class ListenerDispose extends MainThreadDisposable {
        private final FragmentLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;

        ListenerDispose(@NonNull FragmentLifeCycleManager lifeCycleManager, @NonNull LifeCycleListener listener) {
            this.lifeCycleManager = lifeCycleManager;
            this.listener = listener;
        }

        @Override
        protected void onDispose() {
            lifeCycleManager.unListen(FragmentEvent.DESTROY, listener);
        }
    }
}
