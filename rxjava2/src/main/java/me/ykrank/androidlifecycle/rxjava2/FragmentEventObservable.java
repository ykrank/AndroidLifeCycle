package me.ykrank.androidlifecycle.rxjava2;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.event.FragmentEvent;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import me.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;
import me.ykrank.androidlifecycle.util.Util;

/**
 * Provide fragment lifecycle event
 */
final class FragmentEventObservable extends Observable<AndroidEvent> {
    private final FragmentLifeCycleManager lifeCycleManager;

    FragmentEventObservable(Fragment fragment) {
        lifeCycleManager = AndroidLifeCycle.with(fragment);
    }

    FragmentEventObservable(android.app.Fragment fragment) {
        lifeCycleManager = AndroidLifeCycle.with(fragment);
    }

    @Override
    protected void subscribeActual(final Observer<? super AndroidEvent> observer) {
        Util.assertMainThread();
        observer.onNext(AndroidEvent.START);

        LifeCycleListener listener = new LifeCycleListener() {
            @Override
            public void accept() {
                observer.onNext(AndroidEvent.DESTROY);
            }
        };

        observer.onSubscribe(new ListenerDispose(lifeCycleManager, listener));
        lifeCycleManager.listen(FragmentEvent.DESTROY, listener);
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
