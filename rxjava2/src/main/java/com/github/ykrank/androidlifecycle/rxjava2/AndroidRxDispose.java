package com.github.ykrank.androidlifecycle.rxjava2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.event.ViewEvent;
import com.uber.autodispose.CompletableScoper;
import com.uber.autodispose.FlowableScoper;
import com.uber.autodispose.MaybeScoper;
import com.uber.autodispose.ObservableScoper;
import com.uber.autodispose.SingleScoper;

/**
 * Provide scope for rxjava
 * <p>
 * The basic flow stencil might look like this:
 * <pre><code>
 *   myThingObservable
 *        .to(AndroidRxDispose.withObservable(fragment))
 *        .subscribe(...)
 * </code></pre>
 * <p>
 */

public class AndroidRxDispose {

    public static <T> ObservableScoper<T> withObservable(Fragment fragment, FragmentEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> ObservableScoper<T> withObservable(android.app.Fragment fragment, FragmentEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> ObservableScoper<T> withObservable(Activity activity, ActivityEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> ObservableScoper<T> withObservable(FragmentActivity activity, ActivityEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> ObservableScoper<T> withObservable(Context context, ActivityEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(context, event));
    }

    public static <T> ObservableScoper<T> withObservable(View view, ViewEvent event) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(view, event));
    }

    public static <T> SingleScoper<T> withSingle(Fragment fragment, FragmentEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> SingleScoper<T> withSingle(android.app.Fragment fragment, FragmentEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> SingleScoper<T> withSingle(Activity activity, ActivityEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> SingleScoper<T> withSingle(FragmentActivity activity, ActivityEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> SingleScoper<T> withSingle(Context context, ActivityEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(context, event));
    }

    public static <T> SingleScoper<T> withSingle(View view, ViewEvent event) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(view, event));
    }

    public static CompletableScoper withCompletable(Fragment fragment, FragmentEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static CompletableScoper withCompletable(android.app.Fragment fragment, FragmentEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static CompletableScoper withCompletable(Activity activity, ActivityEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static CompletableScoper withCompletable(FragmentActivity activity, ActivityEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static CompletableScoper withCompletable(Context context, ActivityEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(context, event));
    }

    public static CompletableScoper withCompletable(View view, ViewEvent event) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(view, event));
    }

    public static <T> MaybeScoper<T> withMaybe(Fragment fragment, FragmentEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> MaybeScoper<T> withMaybe(android.app.Fragment fragment, FragmentEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> MaybeScoper<T> withMaybe(Activity activity, ActivityEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> MaybeScoper<T> withMaybe(FragmentActivity activity, ActivityEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> MaybeScoper<T> withMaybe(Context context, ActivityEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(context, event));
    }

    public static <T> MaybeScoper<T> withMaybe(View view, ViewEvent event) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(view, event));
    }

    public static <T> FlowableScoper<T> withFlowable(Fragment fragment, FragmentEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> FlowableScoper<T> withFlowable(android.app.Fragment fragment, FragmentEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(fragment, event));
    }

    public static <T> FlowableScoper<T> withFlowable(Activity activity, ActivityEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> FlowableScoper<T> withFlowable(FragmentActivity activity, ActivityEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(activity, event));
    }

    public static <T> FlowableScoper<T> withFlowable(Context context, ActivityEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(context, event));
    }

    public static <T> FlowableScoper<T> withFlowable(View view, ViewEvent event) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(view, event));
    }
}
