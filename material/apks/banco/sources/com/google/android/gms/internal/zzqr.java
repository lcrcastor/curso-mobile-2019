package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.zzh;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class zzqr implements zzqg, zzqy {
    final Map<zzc<?>, zze> a;
    final Map<zzc<?>, ConnectionResult> b = new HashMap();
    final zzh c;
    final Map<Api<?>, Integer> d;
    final com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> e;
    int f;
    final zzqp g;
    final com.google.android.gms.internal.zzqy.zza h;
    /* access modifiers changed from: private */
    public final Lock i;
    private final Condition j;
    private final Context k;
    private final com.google.android.gms.common.zzc l;
    private final zzb m;
    /* access modifiers changed from: private */
    public volatile zzqq n;
    private ConnectionResult o = null;

    static abstract class zza {
        private final zzqq a;

        protected zza(zzqq zzqq) {
            this.a = zzqq;
        }

        /* access modifiers changed from: protected */
        public abstract void a();

        public final void a(zzqr zzqr) {
            zzqr.i.lock();
            try {
                if (zzqr.n == this.a) {
                    a();
                    zzqr.i.unlock();
                }
            } finally {
                zzqr.i.unlock();
            }
        }
    }

    final class zzb extends Handler {
        zzb(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    ((zza) message.obj).a(zzqr.this);
                    return;
                case 2:
                    throw ((RuntimeException) message.obj);
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(31);
                    sb.append("Unknown message id: ");
                    sb.append(i);
                    Log.w("GACStateManager", sb.toString());
                    return;
            }
        }
    }

    public zzqr(Context context, zzqp zzqp, Lock lock, Looper looper, com.google.android.gms.common.zzc zzc, Map<zzc<?>, zze> map, zzh zzh, Map<Api<?>, Integer> map2, com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> zza2, ArrayList<zzqf> arrayList, com.google.android.gms.internal.zzqy.zza zza3) {
        this.k = context;
        this.i = lock;
        this.l = zzc;
        this.a = map;
        this.c = zzh;
        this.d = map2;
        this.e = zza2;
        this.g = zzqp;
        this.h = zza3;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((zzqf) it.next()).zza(this);
        }
        this.m = new zzb(looper);
        this.j = lock.newCondition();
        this.n = new zzqo(this);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.i.lock();
        try {
            zzqn zzqn = new zzqn(this, this.c, this.d, this.l, this.e, this.i, this.k);
            this.n = zzqn;
            this.n.begin();
            this.j.signalAll();
        } finally {
            this.i.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ConnectionResult connectionResult) {
        this.i.lock();
        try {
            this.o = connectionResult;
            this.n = new zzqo(this);
            this.n.begin();
            this.j.signalAll();
        } finally {
            this.i.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(zza zza2) {
        this.m.sendMessage(this.m.obtainMessage(1, zza2));
    }

    /* access modifiers changed from: 0000 */
    public void a(RuntimeException runtimeException) {
        this.m.sendMessage(this.m.obtainMessage(2, runtimeException));
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.i.lock();
        try {
            this.g.d();
            this.n = new zzqm(this);
            this.n.begin();
            this.j.signalAll();
        } finally {
            this.i.unlock();
        }
    }

    public ConnectionResult blockingConnect() {
        connect();
        while (isConnecting()) {
            try {
                this.j.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        return isConnected() ? ConnectionResult.uJ : this.o != null ? this.o : new ConnectionResult(13, null);
    }

    public ConnectionResult blockingConnect(long j2, TimeUnit timeUnit) {
        connect();
        long nanos = timeUnit.toNanos(j2);
        while (isConnecting()) {
            if (nanos <= 0) {
                try {
                    disconnect();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            } else {
                nanos = this.j.awaitNanos(nanos);
            }
        }
        return isConnected() ? ConnectionResult.uJ : this.o != null ? this.o : new ConnectionResult(13, null);
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        for (zze disconnect : this.a.values()) {
            disconnect.disconnect();
        }
    }

    public void connect() {
        this.n.connect();
    }

    public void disconnect() {
        if (this.n.disconnect()) {
            this.b.clear();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.n);
        for (Api api : this.d.keySet()) {
            printWriter.append(str).append(api.getName()).println(":");
            ((zze) this.a.get(api.zzapp())).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        zzc zzapp = api.zzapp();
        if (this.a.containsKey(zzapp)) {
            if (((zze) this.a.get(zzapp)).isConnected()) {
                return ConnectionResult.uJ;
            }
            if (this.b.containsKey(zzapp)) {
                return (ConnectionResult) this.b.get(zzapp);
            }
        }
        return null;
    }

    public boolean isConnected() {
        return this.n instanceof zzqm;
    }

    public boolean isConnecting() {
        return this.n instanceof zzqn;
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.i.lock();
        try {
            this.n.onConnected(bundle);
        } finally {
            this.i.unlock();
        }
    }

    public void onConnectionSuspended(int i2) {
        this.i.lock();
        try {
            this.n.onConnectionSuspended(i2);
        } finally {
            this.i.unlock();
        }
    }

    public void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, int i2) {
        this.i.lock();
        try {
            this.n.zza(connectionResult, api, i2);
        } finally {
            this.i.unlock();
        }
    }

    public boolean zza(zzrl zzrl) {
        return false;
    }

    public void zzaqb() {
    }

    public void zzaqy() {
        if (isConnected()) {
            ((zzqm) this.n).a();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqc.zza<R, A>> T zzc(@NonNull T t) {
        t.zzaqt();
        return this.n.zzc(t);
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqc.zza<? extends Result, A>> T zzd(@NonNull T t) {
        t.zzaqt();
        return this.n.zzd(t);
    }
}
