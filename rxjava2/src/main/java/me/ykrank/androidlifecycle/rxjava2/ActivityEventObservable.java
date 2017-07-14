package me.ykrank.androidlifecycle.rxjava2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.event.ActivityEvent;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import me.ykrank.androidlifecycle.manager.ActivityLifeCycleManager;
import me.ykrank.androidlifecycle.util.Util;

/**
 * Created by ykrank on 2017/7/15.
 */

final class ActivityEventObservable extends Observable<AndroidEvent> {
    private final ActivityLifeCycleManager lifeCycleManager;

    ActivityEventObservable(Activity activity) {
        lifeCycleManager = AndroidLifeCycle.with(activity);
    }

    ActivityEventObservable(FragmentActivity activity) {
        lifeCycleManager = AndroidLifeCycle.with(activity);
    }

    ActivityEventObservable(Context context) {
        lifeCycleManager = AndroidLifeCycle.with(context);
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
        lifeCycleManager.listen(ActivityEvent.DESTROY, listener);
    }

    private static final class ListenerDispose extends MainThreadDisposable {
        private final ActivityLifeCycleManager lifeCycleManager;
        private final LifeCycleListener listener;

        ListenerDispose(@NonNull ActivityLifeCycleManager lifeCycleManager, @NonNull LifeCycleListener listener) {
            this.lifeCycleManager = lifeCycleManager;
            this.listener = listener;
        }

        @Override
        protected void onDispose() {
            lifeCycleManager.unListen(ActivityEvent.DESTROY, listener);
        }
    }
}
