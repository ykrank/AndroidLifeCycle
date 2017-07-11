package me.ykrank.androidlifecycle_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.ykrank.androidlifecycle.AndroidLifeCycle;
import me.ykrank.androidlifecycle.event.FragmentEvent;
import me.ykrank.androidlifecycle.lifecycle.LifeCycleListener;

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
    }

    private void l(String msg) {
        Log.d("MainActivity2", msg);
    }
}
