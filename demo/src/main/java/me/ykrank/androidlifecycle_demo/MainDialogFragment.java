package me.ykrank.androidlifecycle_demo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by ykrank on 2017/7/10.
 */

public class MainDialogFragment extends DialogFragment{
    static final String TAG = "MainDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
