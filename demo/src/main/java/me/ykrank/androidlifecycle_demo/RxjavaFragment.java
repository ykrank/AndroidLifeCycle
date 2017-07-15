package me.ykrank.androidlifecycle_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.rxjava2.AndroidRxDispose;

/**
 * Test rxjava. you can see log after switch to other fragment or activity
 */

public class RxjavaFragment extends android.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxjava, container, false);

        Button button = (Button) view.findViewById(R.id.button1);
        AndroidLifeCycle.bindFragment(button, this);

        Observable.intervalRange(1, 100, 0, 1, TimeUnit.SECONDS, Schedulers.single())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        l("Rxjava dispose");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .to(AndroidRxDispose.withObservable(button))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object object) throws Exception {
                        l(object.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        return view;
    }

    private static void l(String msg) {
        Log.d("RxjavaFragment", msg);
    }
}