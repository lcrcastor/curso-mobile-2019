package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

final class zzqh implements zzqy {
    private final Context a;
    private final zzqp b;
    private final Looper c;
    /* access modifiers changed from: private */
    public final zzqr d;
    /* access modifiers changed from: private */
    public final zzqr e;
    private final Map<zzc<?>, zzqr> f;
    private final Set<zzrl> g = Collections.newSetFromMap(new WeakHashMap());
    private final zze h;
    private Bundle i;
    /* access modifiers changed from: private */
    public ConnectionResult j = null;
    /* access modifiers changed from: private */
    public ConnectionResult k = null;
    /* access modifiers changed from: private */
    public boolean l = false;
    /* access modifiers changed from: private */
    public final Lock m;
    private int n = 0;

    class zza implements com.google.android.gms.internal.zzqy.zza {
        private zza() {
        }

        public void zzc(int i, boolean z) {
            zzqh.this.m.lock();
            try {
                if (!zzqh.this.l && zzqh.this.k != null) {
                    if (zzqh.this.k.isSuccess()) {
                        zzqh.this.l = true;
                        zzqh.this.e.onConnectionSuspended(i);
                        zzqh.this.m.unlock();
                        return;
                    }
                }
                zzqh.this.l = false;
                zzqh.this.a(i, z);
            } finally {
                zzqh.this.m.unlock();
            }
        }

        public void zzd(@NonNull ConnectionResult connectionResult) {
            zzqh.this.m.lock();
            try {
                zzqh.this.j = connectionResult;
                zzqh.this.c();
            } finally {
                zzqh.this.m.unlock();
            }
        }

        public void zzn(@Nullable Bundle bundle) {
            zzqh.this.m.lock();
            try {
                zzqh.this.a(bundle);
                zzqh.this.j = ConnectionResult.uJ;
                zzqh.this.c();
            } finally {
                zzqh.this.m.unlock();
            }
        }
    }

    class zzb implements com.google.android.gms.internal.zzqy.zza {
        private zzb() {
        }

        public void zzc(int i, boolean z) {
            zzqh.this.m.lock();
            try {
                if (zzqh.this.l) {
                    zzqh.this.l = false;
                    zzqh.this.a(i, z);
                    return;
                }
                zzqh.this.l = true;
                zzqh.this.d.onConnectionSuspended(i);
                zzqh.this.m.unlock();
            } finally {
                zzqh.this.m.unlock();
            }
        }

        public void zzd(@NonNull ConnectionResult connectionResult) {
            zzqh.this.m.lock();
            try {
                zzqh.this.k = connectionResult;
                zzqh.this.c();
            } finally {
                zzqh.this.m.unlock();
            }
        }

        public void zzn(@Nullable Bundle bundle) {
            zzqh.this.m.lock();
            try {
                zzqh.this.k = ConnectionResult.uJ;
                zzqh.this.c();
            } finally {
                zzqh.this.m.unlock();
            }
        }
    }

    private zzqh(Context context, zzqp zzqp, Lock lock, Looper looper, com.google.android.gms.common.zzc zzc, Map<zzc<?>, zze> map, Map<zzc<?>, zze> map2, zzh zzh, com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> zza2, zze zze, ArrayList<zzqf> arrayList, ArrayList<zzqf> arrayList2, Map<Api<?>, Integer> map3, Map<Api<?>, Integer> map4) {
        Context context2 = context;
        this.a = context2;
        this.b = zzqp;
        Lock lock2 = lock;
        this.m = lock2;
        Looper looper2 = looper;
        this.c = looper2;
        this.h = zze;
        Context context3 = context2;
        Lock lock3 = lock2;
        com.google.android.gms.common.zzc zzc2 = zzc;
        zzqr zzqr = r3;
        zzqr zzqr2 = new zzqr(context3, this.b, lock3, looper2, zzc2, map2, null, map4, null, arrayList2, new zza());
        this.d = zzqr;
        zzqr zzqr3 = new zzqr(context3, this.b, lock3, looper, zzc2, map, zzh, map3, zza2, arrayList, new zzb());
        this.e = zzqr3;
        ArrayMap arrayMap = new ArrayMap();
        for (zzc put : map2.keySet()) {
            arrayMap.put(put, this.d);
        }
        for (zzc put2 : map.keySet()) {
            arrayMap.put(put2, this.e);
        }
        this.f = Collections.unmodifiableMap(arrayMap);
    }

    public static zzqh a(Context context, zzqp zzqp, Lock lock, Looper looper, com.google.android.gms.common.zzc zzc, Map<zzc<?>, zze> map, zzh zzh, Map<Api<?>, Integer> map2, com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> zza2, ArrayList<zzqf> arrayList) {
        Map<Api<?>, Integer> map3 = map2;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        zze zze = null;
        for (Entry entry : map.entrySet()) {
            zze zze2 = (zze) entry.getValue();
            if (zze2.zzahs()) {
                zze = zze2;
            }
            if (zze2.zzahd()) {
                arrayMap.put((zzc) entry.getKey(), zze2);
            } else {
                arrayMap2.put((zzc) entry.getKey(), zze2);
            }
        }
        zzac.zza(!arrayMap.isEmpty(), (Object) "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api api : map2.keySet()) {
            zzc zzapp = api.zzapp();
            if (arrayMap.containsKey(zzapp)) {
                arrayMap3.put(api, (Integer) map3.get(api));
            } else if (arrayMap2.containsKey(zzapp)) {
                arrayMap4.put(api, (Integer) map3.get(api));
            } else {
                throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            zzqf zzqf = (zzqf) it.next();
            if (arrayMap3.containsKey(zzqf.tv)) {
                arrayList2.add(zzqf);
            } else if (arrayMap4.containsKey(zzqf.tv)) {
                arrayList3.add(zzqf);
            } else {
                throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
            }
        }
        zzqh zzqh = new zzqh(context, zzqp, lock, looper, zzc, arrayMap, arrayMap2, zzh, zza2, zze, arrayList2, arrayList3, arrayMap3, arrayMap4);
        return zzqh;
    }

    /* access modifiers changed from: private */
    public void a(int i2, boolean z) {
        this.b.zzc(i2, z);
        this.k = null;
        this.j = null;
    }

    /* access modifiers changed from: private */
    public void a(Bundle bundle) {
        if (this.i == null) {
            this.i = bundle;
            return;
        }
        if (bundle != null) {
            this.i.putAll(bundle);
        }
    }

    private void a(ConnectionResult connectionResult) {
        switch (this.n) {
            case 1:
                break;
            case 2:
                this.b.zzd(connectionResult);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
                break;
        }
        e();
        this.n = 0;
    }

    private boolean a(com.google.android.gms.internal.zzqc.zza<? extends Result, ? extends com.google.android.gms.common.api.Api.zzb> zza2) {
        zzc zzapp = zza2.zzapp();
        zzac.zzb(this.f.containsKey(zzapp), (Object) "GoogleApiClient is not configured to use the API required for this call.");
        return ((zzqr) this.f.get(zzapp)).equals(this.e);
    }

    private void b() {
        this.k = null;
        this.j = null;
        this.d.connect();
        this.e.connect();
    }

    private static boolean b(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    /* access modifiers changed from: private */
    public void c() {
        ConnectionResult connectionResult;
        if (!b(this.j)) {
            if (this.j != null && b(this.k)) {
                this.e.disconnect();
                connectionResult = this.j;
            } else if (!(this.j == null || this.k == null)) {
                connectionResult = this.j;
                if (this.e.f < this.d.f) {
                    connectionResult = this.k;
                }
            }
            a(connectionResult);
        } else if (b(this.k) || f()) {
            d();
        } else if (this.k != null) {
            if (this.n == 1) {
                e();
                return;
            }
            a(this.k);
            this.d.disconnect();
        }
    }

    private void d() {
        switch (this.n) {
            case 1:
                break;
            case 2:
                this.b.zzn(this.i);
                break;
            default:
                Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
                break;
        }
        e();
        this.n = 0;
    }

    private void e() {
        for (zzrl zzahr : this.g) {
            zzahr.zzahr();
        }
        this.g.clear();
    }

    private boolean f() {
        return this.k != null && this.k.getErrorCode() == 4;
    }

    @Nullable
    private PendingIntent g() {
        if (this.h == null) {
            return null;
        }
        return PendingIntent.getActivity(this.a, this.b.getSessionId(), this.h.zzaht(), 134217728);
    }

    public boolean a() {
        return this.e.isConnected();
    }

    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    public ConnectionResult blockingConnect(long j2, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public void connect() {
        this.n = 2;
        this.l = false;
        b();
    }

    public void disconnect() {
        this.k = null;
        this.j = null;
        this.n = 0;
        this.d.disconnect();
        this.e.disconnect();
        e();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("authClient").println(":");
        this.e.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append(str).append("anonClient").println(":");
        this.d.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return ((zzqr) this.f.get(api.zzapp())).equals(this.e) ? f() ? new ConnectionResult(4, g()) : this.e.getConnectionResult(api) : this.d.getConnectionResult(api);
    }

    public boolean isConnected() {
        this.m.lock();
        try {
            boolean z = true;
            if (!this.d.isConnected() || (!a() && !f() && this.n != 1)) {
                z = false;
            }
            return z;
        } finally {
            this.m.unlock();
        }
    }

    public boolean isConnecting() {
        this.m.lock();
        try {
            return this.n == 2;
        } finally {
            this.m.unlock();
        }
    }

    public boolean zza(zzrl zzrl) {
        this.m.lock();
        try {
            if ((isConnecting() || isConnected()) && !a()) {
                this.g.add(zzrl);
                if (this.n == 0) {
                    this.n = 1;
                }
                this.k = null;
                this.e.connect();
                return true;
            }
            this.m.unlock();
            return false;
        } finally {
            this.m.unlock();
        }
    }

    public void zzaqb() {
        this.m.lock();
        try {
            boolean isConnecting = isConnecting();
            this.e.disconnect();
            this.k = new ConnectionResult(4);
            if (isConnecting) {
                new Handler(this.c).post(new Runnable() {
                    public void run() {
                        zzqh.this.m.lock();
                        try {
                            zzqh.this.c();
                        } finally {
                            zzqh.this.m.unlock();
                        }
                    }
                });
            } else {
                e();
            }
        } finally {
            this.m.unlock();
        }
    }

    public void zzaqy() {
        this.d.zzaqy();
        this.e.zzaqy();
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqc.zza<R, A>> T zzc(@NonNull T t) {
        if (!a((com.google.android.gms.internal.zzqc.zza<? extends Result, ? extends com.google.android.gms.common.api.Api.zzb>) t)) {
            return this.d.zzc(t);
        }
        if (!f()) {
            return this.e.zzc(t);
        }
        t.zzz(new Status(4, null, g()));
        return t;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqc.zza<? extends Result, A>> T zzd(@NonNull T t) {
        if (!a((com.google.android.gms.internal.zzqc.zza<? extends Result, ? extends com.google.android.gms.common.api.Api.zzb>) t)) {
            return this.d.zzd(t);
        }
        if (!f()) {
            return this.e.zzd(t);
        }
        t.zzz(new Status(4, null, g()));
        return t;
    }
}
