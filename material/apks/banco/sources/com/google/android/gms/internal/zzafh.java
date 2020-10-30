package com.google.android.gms.internal;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class zzafh {
    public static final Integer aJs = Integer.valueOf(0);
    public static final Integer aJt = Integer.valueOf(1);
    private final Context a;
    private final ExecutorService b;

    public zzafh(Context context) {
        this(context, Executors.newSingleThreadExecutor());
    }

    zzafh(Context context, ExecutorService executorService) {
        this.a = context;
        this.b = executorService;
    }
}
