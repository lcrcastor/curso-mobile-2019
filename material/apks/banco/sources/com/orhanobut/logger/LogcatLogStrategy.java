package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class LogcatLogStrategy implements LogStrategy {
    public void log(int i, @Nullable String str, @NonNull String str2) {
        Utils.b(str2);
        if (str == null) {
            str = "NO_TAG";
        }
        Log.println(i, str, str2);
    }
}
