package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzh<TResult> a = new zzh<>();

    @NonNull
    public Task<TResult> getTask() {
        return this.a;
    }

    public void setException(@NonNull Exception exc) {
        this.a.a(exc);
    }

    public void setResult(TResult tresult) {
        this.a.a(tresult);
    }
}
