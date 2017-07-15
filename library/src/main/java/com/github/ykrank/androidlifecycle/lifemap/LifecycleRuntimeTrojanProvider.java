package com.github.ykrank.androidlifecycle.lifemap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

/**
 * Use ContentProvider to reg activity lifecycle callback
 */

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class LifecycleRuntimeTrojanProvider extends ContentProvider {
    public LifecycleRuntimeTrojanProvider() {
    }

    public boolean onCreate() {
        ActivityLifecycleMap.init(getContext());
        return true;
    }

    @Nullable
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}