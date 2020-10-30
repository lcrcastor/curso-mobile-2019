package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Pair;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Api.zzh;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzai;
import com.google.android.gms.internal.zzpz;
import com.google.android.gms.internal.zzqc.zza;
import com.google.android.gms.internal.zzqt;
import com.google.android.gms.internal.zzqu;
import com.google.android.gms.internal.zzre;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzd<O extends ApiOptions> {
    private final Context a;
    private final zzre b;
    private final Api<O> c;
    private final O d;
    private final zzpz<O> e;
    private final Looper f;
    private final int g;
    private final zzqt h;
    private final GoogleApiClient i;
    private final AtomicBoolean j;
    private final AtomicInteger k;
    private zze l;

    public zzd(@NonNull Context context, Api<O> api, O o) {
        this(context, api, o, Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
    }

    public zzd(@NonNull Context context, Api<O> api, O o, Looper looper) {
        this.j = new AtomicBoolean(false);
        this.k = new AtomicInteger(0);
        zzac.zzb(context, (Object) "Null context is not permitted.");
        zzac.zzb(api, (Object) "Api must not be null.");
        zzac.zzb(looper, (Object) "Looper must not be null.");
        this.a = context.getApplicationContext();
        this.c = api;
        this.d = o;
        this.f = looper;
        this.b = new zzre();
        this.e = zzpz.zza(this.c, this.d);
        this.i = new zzqu(this);
        Pair zza = zzqt.zza(this.a, this);
        this.h = (zzqt) zza.first;
        this.g = ((Integer) zza.second).intValue();
    }

    private <A extends zzb, T extends zza<? extends Result, A>> T a(int i2, @NonNull T t) {
        t.zzaqt();
        this.h.zza(this, i2, t);
        return t;
    }

    private <TResult, A extends zzb> Task<TResult> a(int i2, @NonNull zzro<A, TResult> zzro) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.h.zza(this, i2, zzro, taskCompletionSource);
        return taskCompletionSource.getTask();
    }

    public int getInstanceId() {
        return this.g;
    }

    public Looper getLooper() {
        return this.f;
    }

    public void release() {
        boolean z = true;
        if (!this.j.getAndSet(true)) {
            this.b.release();
            zzqt zzqt = this.h;
            int i2 = this.g;
            if (this.k.get() <= 0) {
                z = false;
            }
            zzqt.zzd(i2, z);
        }
    }

    @WorkerThread
    public zze zza(Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        if (!zzapw()) {
            if (this.c.zzapq()) {
                zzh zzapo = this.c.zzapo();
                zzai zzai = new zzai(this.a, looper, zzapo.zzapt(), connectionCallbacks, onConnectionFailedListener, com.google.android.gms.common.internal.zzh.zzcd(this.a), zzapo.zzr(this.d));
                this.l = zzai;
            } else {
                this.l = this.c.zzapn().zza(this.a, looper, com.google.android.gms.common.internal.zzh.zzcd(this.a), this.d, connectionCallbacks, onConnectionFailedListener);
            }
        }
        return this.l;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zza(@NonNull T t) {
        return a(0, t);
    }

    public <TResult, A extends zzb> Task<TResult> zza(zzro<A, TResult> zzro) {
        return a(0, zzro);
    }

    public void zzapu() {
        this.k.incrementAndGet();
    }

    public void zzapv() {
        if (this.k.decrementAndGet() == 0 && this.j.get()) {
            this.h.zzd(this.g, false);
        }
    }

    public boolean zzapw() {
        return this.l != null;
    }

    public zzpz<O> zzapx() {
        return this.e;
    }

    public GoogleApiClient zzapy() {
        return this.i;
    }

    public <A extends zzb, T extends zza<? extends Result, A>> T zzb(@NonNull T t) {
        return a(1, t);
    }

    public <TResult, A extends zzb> Task<TResult> zzb(zzro<A, TResult> zzro) {
        return a(1, zzro);
    }
}
