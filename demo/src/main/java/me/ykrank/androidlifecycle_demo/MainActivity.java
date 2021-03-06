package me.ykrank.androidlifecycle_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.event.FragmentEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;
import com.github.ykrank.androidlifecycle.manager.FragmentLifeCycleManager;

/**
 * test AndroidLifeCycle with fragmentActivity and support fragment in onCreate
 */
public class MainActivity extends AppCompatActivity {
    static final String FRAGMENT_TAG = "me.ykrank.androidlifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MainActivity");

        l("onCreate");
        setContentView(R.layout.activity_main);

        final MainFragment mainFragment = new MainFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.layout_fragment, mainFragment).commitNow();

        AndroidLifeCycle.with(this)
                .listen(ActivityEvent.START, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.START");
                    }
                })
                .listen(ActivityEvent.RESUME, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.RESUME");
                    }
                })
                .listen(ActivityEvent.PAUSE, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.PAUSE");
                    }
                })
                .listen(ActivityEvent.STOP, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.STOP");
                    }
                })
                .listen(ActivityEvent.DESTROY, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("ActivityEvent.DESTROY");
                    }
                });

        final FragmentLifeCycleManager manager = AndroidLifeCycle.with(mainFragment);

        new Thread(new Runnable() {
            @Override
            public void run() {
                manager.listen(FragmentEvent.START, new LifeCycleListener() {
                    @Override
                    public void accept() {
                        l("MainFragment FragmentEvent.START");
                    }
                })
                        .listen(FragmentEvent.RESUME, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("MainFragment FragmentEvent.RESUME");
                            }
                        })
                        .listen(FragmentEvent.PAUSE, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("MainFragment FragmentEvent.PAUSE");
                            }
                        })
                        .listen(FragmentEvent.STOP, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("MainFragment FragmentEvent.STOP");
                            }
                        })
                        .listen(FragmentEvent.DESTROY_VIEW, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("MainFragment FragmentEvent.DESTROY_VIEW");
                            }
                        })
                        .listen(FragmentEvent.DESTROY, new LifeCycleListener() {
                            @Override
                            public void accept() {
                                l("MainFragment FragmentEvent.DESTROY");
                            }
                        });
            }
        }).start();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        l("onPostCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        l("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        l("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        l("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        l("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        l("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        l("onDestroy");
    }

    private void l(String msg) {
        Log.d("MainActivity", msg);
    }
}
