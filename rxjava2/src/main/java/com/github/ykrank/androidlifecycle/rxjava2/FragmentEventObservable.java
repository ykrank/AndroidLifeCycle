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
final class FragmentEventObservable extends Observable<AndroidRxEvent> {
    private final Fragment supportFragment;
    private final android.app.Fragment fragment;
    private final FragmentEvent event;

    FragmentEventObservable(Fragment fragment, FragmentEvent event) {
        this.supportFragment = fragment;
        this.event = event;
        this.fragment = null;
    }

    FragmentEventObservable(android.app.Fragment fragment, FragmentEvent event) {
        this.event = event;
        this.supportFragment = null;
        this.fragment = fragment;
    }

    @Override
    protected void subscribeActual(final Observer<? super AndroidRxEvent> observer) {
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
                observer.onNext(AndroidRxEvent.DISPOSE);
            }
        };
        observer.onSubscribe(new ListenerDispose(lifeCycleManager, listener, event));

        lifeCycleManager.listen(event, listener);

        observer.onNext(AndroidRxEvent.START);
    }

    private static final class ListenerDispose extends MainThreadDisposable {
        private final FragmentLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;
        private final FragmentEvent event;

        ListenerDispose(@NonNull FragmentLifeCycleManager lifeCycleManager,
                        @NonNull LifeCycleListener listener, FragmentEvent event) {
            this.lifeCycleManager = lifeCycleManager;
            this.listener = listener;
            this.event = event;
        }

        @Override
        protected void onDispose() {
            lifeCycleManager.unListen(event, listener);
        }
    }
}
