package com.github.ykrank.androidlifecycle.rxjava2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.uber.autodispose.LifecycleEndedException;
import com.uber.autodispose.LifecycleScopeProvider;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static com.github.ykrank.androidlifecycle.rxjava2.AndroidEvent.DESTROY;

/**
 * A {@link LifecycleScopeProvider} that can provide scoping for Android {@link Fragment}, {@link Activity}, {@link View} classes.
 */
public class AndroidLifeCycleScopeProvider implements LifecycleScopeProvider<AndroidEvent> {

    private static final Function<AndroidEvent, AndroidEvent> CORRESPONDING_EVENTS =
            new Function<AndroidEvent, AndroidEvent>() {
                @Override
                public AndroidEvent apply(AndroidEvent lastEvent) throws Exception {
                    switch (lastEvent) {
                        case START:
                            return DESTROY;
                        default:
                            throw new LifecycleEndedException("Fragment or activity is detached!");
                    }
                }
            };

    private final Observable<AndroidEvent> lifecycle;

    AndroidLifeCycleScopeProvider(Fragment fragment) {
        lifecycle = new FragmentEventObservable(fragment);
    }

    AndroidLifeCycleScopeProvider(android.app.Fragment fragment) {
        lifecycle = new FragmentEventObservable(fragment);
    }

    AndroidLifeCycleScopeProvider(Activity activity) {
        lifecycle = new ActivityEventObservable(activity);
    }

    AndroidLifeCycleScopeProvider(FragmentActivity activity) {
        lifecycle = new ActivityEventObservable(activity);
    }

    AndroidLifeCycleScopeProvider(Context context) {
        lifecycle = new ActivityEventObservable(context);
    }

    AndroidLifeCycleScopeProvider(View view) {
        lifecycle = new ViewEventObservable(view);
    }

    @Override
    public Observable<AndroidEvent> lifecycle() {
        return lifecycle;
    }

    @Override
    public Function<AndroidEvent, AndroidEvent> correspondingEvents() {
        return CORRESPONDING_EVENTS;
    }

    @Override
    public AndroidEvent peekLifecycle() {
        return AndroidEvent.START;
    }
}
