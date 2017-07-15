package com.github.ykrank.androidlifecycle.rxjava2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

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

    public static <T> ObservableScoper<T> withObservable(Fragment fragment) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> ObservableScoper<T> withObservable(android.app.Fragment fragment) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> ObservableScoper<T> withObservable(Activity activity) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> ObservableScoper<T> withObservable(FragmentActivity activity) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> ObservableScoper<T> withObservable(Context context) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(context));
    }

    public static <T> ObservableScoper<T> withObservable(View view) {
        return new ObservableScoper<>(new AndroidLifeCycleScopeProvider(view));
    }

    public static <T> SingleScoper<T> withSingle(Fragment fragment) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> SingleScoper<T> withSingle(android.app.Fragment fragment) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> SingleScoper<T> withSingle(Activity activity) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> SingleScoper<T> withSingle(FragmentActivity activity) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> SingleScoper<T> withSingle(Context context) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(context));
    }

    public static <T> SingleScoper<T> withSingle(View view) {
        return new SingleScoper<>(new AndroidLifeCycleScopeProvider(view));
    }

    public static CompletableScoper withCompletable(Fragment fragment) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static CompletableScoper withCompletable(android.app.Fragment fragment) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static CompletableScoper withCompletable(Activity activity) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(activity));
    }

    public static CompletableScoper withCompletable(FragmentActivity activity) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(activity));
    }

    public static CompletableScoper withCompletable(Context context) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(context));
    }

    public static CompletableScoper withCompletable(View view) {
        return new CompletableScoper(new AndroidLifeCycleScopeProvider(view));
    }

    public static <T> MaybeScoper<T> withMaybe(Fragment fragment) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> MaybeScoper<T> withMaybe(android.app.Fragment fragment) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> MaybeScoper<T> withMaybe(Activity activity) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> MaybeScoper<T> withMaybe(FragmentActivity activity) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> MaybeScoper<T> withMaybe(Context context) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(context));
    }

    public static <T> MaybeScoper<T> withMaybe(View view) {
        return new MaybeScoper<>(new AndroidLifeCycleScopeProvider(view));
    }

    public static <T> FlowableScoper<T> withFlowable(Fragment fragment) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> FlowableScoper<T> withFlowable(android.app.Fragment fragment) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(fragment));
    }

    public static <T> FlowableScoper<T> withFlowable(Activity activity) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> FlowableScoper<T> withFlowable(FragmentActivity activity) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(activity));
    }

    public static <T> FlowableScoper<T> withFlowable(Context context) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(context));
    }

    public static <T> FlowableScoper<T> withFlowable(View view) {
        return new FlowableScoper<>(new AndroidLifeCycleScopeProvider(view));
    }
}
