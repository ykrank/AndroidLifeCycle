package me.ykrank.androidlifecycle_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.ykrank.androidlifecycle.AndroidLifeCycle;
import com.github.ykrank.androidlifecycle.event.ActivityEvent;
import com.github.ykrank.androidlifecycle.lifecycle.LifeCycleListener;


/**
 * test AndroidLifeCycle with fragment after created, and with view
 */
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main2Activity");
        setContentView(R.layout.activity_main2);

        final MainFragment2 mainFragment2 = new MainFragment2();

        View view = findViewById(R.id.button1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.layout_fragment, mainFragment2).commit();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.layout_fragment, new RxjavaFragment()).commit();
            }
        });

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
    }

    private void l(String msg) {
        Log.d("MainActivity2", msg);
    }
}
