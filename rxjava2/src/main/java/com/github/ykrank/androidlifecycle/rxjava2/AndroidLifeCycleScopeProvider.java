package com.github.ykrank.androidlifecycle.rxjava2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.event.ViewEvent;
import com.uber.autodispose.LifecycleEndedException;
import com.uber.autodispose.LifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

import static com.github.ykrank.androidlifecycle.rxjava2.AndroidRxEvent.DISPOSE;

/**
 * A {@link LifecycleScopeProvider} that can provide scoping for Android {@link Fragment}, {@link Activity}, {@link View} classes.
 */
public class AndroidLifeCycleScopeProvider implements LifecycleScopeProvider<AndroidRxEvent> {

    private static final Function<AndroidRxEvent, AndroidRxEvent> CORRESPONDING_EVENTS =
            new Function<AndroidRxEvent, AndroidRxEvent>() {
                @Override
                public AndroidRxEvent apply(AndroidRxEvent lastEvent) throws Exception {
                    switch (lastEvent) {
                        case START:
                            return DISPOSE;
                        default:
                            throw new LifecycleEndedException("Fragment or activity is detached!");
                    }
                }
            };

    private final Observable<AndroidRxEvent> lifecycle;

    AndroidLifeCycleScopeProvider(Fragment fragment, FragmentEvent event) {
        lifecycle = new FragmentEventObservable(fragment, event);
    }

    AndroidLifeCycleScopeProvider(android.app.Fragment fragment, FragmentEvent event) {
        lifecycle = new FragmentEventObservable(fragment, event);
    }

    AndroidLifeCycleScopeProvider(Activity activity, ActivityEvent event) {
        lifecycle = new ActivityEventObservable(activity, event);
    }

    AndroidLifeCycleScopeProvider(FragmentActivity activity, ActivityEvent event) {
        lifecycle = new ActivityEventObservable(activity, event);
    }

    AndroidLifeCycleScopeProvider(Context context, ActivityEvent event) {
        lifecycle = new ActivityEventObservable(context, event);
    }

    AndroidLifeCycleScopeProvider(View view, ViewEvent event) {
        lifecycle = new ViewEventObservable(view, event);
    }

    @Override
    public Observable<AndroidRxEvent> lifecycle() {
        return lifecycle.subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Function<AndroidRxEvent, AndroidRxEvent> correspondingEvents() {
        return CORRESPONDING_EVENTS;
    }

    @Override
    public AndroidRxEvent peekLifecycle() {
        return AndroidRxEvent.START;
    }
}
