package com.github.ykrank.androidlifecycle.rxjava2;

import android.support.annotation.NonNull;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.ViewEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.manager.ViewLifeCycleManager;
import com.github.ykrank.androidlifecycle.util.Util;

/**
 * Provide view lifecycle event
 */
final class ViewEventObservable extends Observable<AndroidEvent> {
    private final View view;

    ViewEventObservable(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(final Observer<? super AndroidEvent> observer) {
        Util.assertMainThread();
        ViewLifeCycleManager lifeCycleManager = AndroidLifeCycle.with(view);

        LifeCycleListener listener = new LifeCycleListener() {
            @Override
            public void accept() {
                observer.onNext(AndroidEvent.DESTROY);
            }
        };
        observer.onSubscribe(new ListenerDispose(lifeCycleManager, listener));

        lifeCycleManager.listen(ViewEvent.DESTROY, listener);

        observer.onNext(AndroidEvent.START);
    }

    private static final class ListenerDispose extends MainThreadDisposable {
        private final ViewLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;

        ListenerDispose(@NonNull ViewLifeCycleManager lifeCycleManager, @NonNull LifeCycleListener listener) {
            this.lifeCycleManager = lifeCycleManager;
            this.listener = listener;
        }

        @Override
        protected void onDispose() {
            lifeCycleManager.unListen(ViewEvent.DESTROY, listener);
        }
    }
}
