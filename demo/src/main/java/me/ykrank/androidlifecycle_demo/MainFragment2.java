package me.ykrank.androidlifecycle_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.event.FragmentEvent;
import me.ykrank.androidlifecycle.event.ViewEvent;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

/**
 * Created by ykrank on 2017/7/9.
 */

public class MainFragment2 extends android.app.Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        View textView = view.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidLifeCycle.with(MainFragment2.this)
                        .listen(FragmentEvent.START, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.START");
                            }
                        })
                        .listen(FragmentEvent.RESUME, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.RESUME");
                            }
                        })
                        .listen(FragmentEvent.PAUSE, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.PAUSE");
                            }
                        })
                        .listen(FragmentEvent.STOP, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.STOP");
                            }
                        })
                        .listen(FragmentEvent.DESTROY_VIEW, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.DESTROY_VIEW");
                            }
                        })
                        .listen(FragmentEvent.DESTROY, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("FragmentEvent.DESTROY");
                            }
                        });
            }
        });

        AndroidLifeCycle.bindFragment(textView, this);
        AndroidLifeCycle.with(textView)
                .listen(ViewEvent.START, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ViewEvent.START");
                    }
                })
                .listen(ViewEvent.RESUME, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ViewEvent.RESUME");
                    }
                })
                .listen(ViewEvent.PAUSE, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ViewEvent.PAUSE");
                    }
                })
                .listen(ViewEvent.STOP, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ViewEvent.STOP");
                    }
                })
                .listen(ViewEvent.DESTROY, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ViewEvent.DESTROY");
                    }
                });
        return view;
    }

    private void l(String msg) {
        Log.d("MainFragment2", msg);
    }
}
