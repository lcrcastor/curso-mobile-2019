package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.signin.internal.SignInResponse;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public class zzqn implements zzqq {
    /* access modifiers changed from: private */
    public final zzqr a;
    /* access modifiers changed from: private */
    public final Lock b;
    /* access modifiers changed from: private */
    public final Context c;
    /* access modifiers changed from: private */
    public final com.google.android.gms.common.zzc d;
    private ConnectionResult e;
    private int f;
    private int g = 0;
    private int h;
    private final Bundle i = new Bundle();
    private final Set<com.google.android.gms.common.api.Api.zzc> j = new HashSet();
    /* access modifiers changed from: private */
    public zzwz k;
    private int l;
    /* access modifiers changed from: private */
    public boolean m;
    private boolean n;
    /* access modifiers changed from: private */
    public zzr o;
    private boolean p;
    private boolean q;
    private final zzh r;
    private final Map<Api<?>, Integer> s;
    private final com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> t;
    private ArrayList<Future<?>> u = new ArrayList<>();

    static class zza implements com.google.android.gms.common.internal.zze.zzf {
        private final WeakReference<zzqn> a;
        private final Api<?> b;
        /* access modifiers changed from: private */
        public final int c;

        public zza(zzqn zzqn, Api<?> api, int i) {
            this.a = new WeakReference<>(zzqn);
            this.b = api;
            this.c = i;
        }

        public void zzh(@NonNull ConnectionResult connectionResult) {
            zzqn zzqn = (zzqn) this.a.get();
            if (zzqn != null) {
                zzac.zza(Looper.myLooper() == zzqn.a.g.getLooper(), (Object) "onReportServiceBinding must be called on the GoogleApiClient handler thread");
                zzqn.b.lock();
                try {
                    if (zzqn.a(0)) {
                        if (!connectionResult.isSuccess()) {
                            zzqn.a(connectionResult, this.b, this.c);
                        }
                        if (zzqn.a()) {
                            zzqn.b();
                        }
                        zzqn.b.unlock();
                    }
                } finally {
                    zzqn.b.unlock();
                }
            }
        }
    }

    class zzb extends zzf {
        private final Map<com.google.android.gms.common.api.Api.zze, zza> c;

        public zzb(Map<com.google.android.gms.common.api.Api.zze, zza> map) {
            super();
            this.c = map;
        }

        @WorkerThread
        public void a() {
            boolean z;
            Iterator it = this.c.keySet().iterator();
            boolean z2 = true;
            int i = 0;
            boolean z3 = false;
            boolean z4 = true;
            while (true) {
                if (!it.hasNext()) {
                    z2 = z3;
                    z = false;
                    break;
                }
                com.google.android.gms.common.api.Api.zze zze = (com.google.android.gms.common.api.Api.zze) it.next();
                if (!zze.zzapr()) {
                    z4 = false;
                } else if (((zza) this.c.get(zze)).c == 0) {
                    z = true;
                    break;
                } else {
                    z3 = true;
                }
            }
            if (z2) {
                i = zzqn.this.d.isGooglePlayServicesAvailable(zzqn.this.c);
            }
            if (i == 0 || (!z && !z4)) {
                if (zzqn.this.m) {
                    zzqn.this.k.connect();
                }
                for (com.google.android.gms.common.api.Api.zze zze2 : this.c.keySet()) {
                    final com.google.android.gms.common.internal.zze.zzf zzf = (com.google.android.gms.common.internal.zze.zzf) this.c.get(zze2);
                    if (!zze2.zzapr() || i == 0) {
                        zze2.zza(zzf);
                    } else {
                        zzqn.this.a.a((zza) new zza(zzqn.this) {
                            public void a() {
                                zzf.zzh(new ConnectionResult(16, null));
                            }
                        });
                    }
                }
                return;
            }
            final ConnectionResult connectionResult = new ConnectionResult(i, null);
            zzqn.this.a.a((zza) new zza(zzqn.this) {
                public void a() {
                    zzqn.this.c(connectionResult);
                }
            });
        }
    }

    class zzc extends zzf {
        private final ArrayList<com.google.android.gms.common.api.Api.zze> c;

        public zzc(ArrayList<com.google.android.gms.common.api.Api.zze> arrayList) {
            super();
            this.c = arrayList;
        }

        @WorkerThread
        public void a() {
            zzqn.this.a.g.d = zzqn.this.g();
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                ((com.google.android.gms.common.api.Api.zze) it.next()).zza(zzqn.this.o, zzqn.this.a.g.d);
            }
        }
    }

    static class zzd extends com.google.android.gms.signin.internal.zzb {
        private final WeakReference<zzqn> a;

        zzd(zzqn zzqn) {
            this.a = new WeakReference<>(zzqn);
        }

        @BinderThread
        public void zzb(final SignInResponse signInResponse) {
            final zzqn zzqn = (zzqn) this.a.get();
            if (zzqn != null) {
                zzqn.a.a((zza) new zza(zzqn) {
                    public void a() {
                        zzqn.a(signInResponse);
                    }
                });
            }
        }
    }

    class zze implements ConnectionCallbacks, OnConnectionFailedListener {
        private zze() {
        }

        public void onConnected(Bundle bundle) {
            zzqn.this.k.zza(new zzd(zzqn.this));
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            zzqn.this.b.lock();
            try {
                if (zzqn.this.b(connectionResult)) {
                    zzqn.this.e();
                    zzqn.this.b();
                } else {
                    zzqn.this.c(connectionResult);
                }
            } finally {
                zzqn.this.b.unlock();
            }
        }

        public void onConnectionSuspended(int i) {
        }
    }

    abstract class zzf implements Runnable {
        private zzf() {
        }

        /* access modifiers changed from: protected */
        @WorkerThread
        public abstract void a();

        @WorkerThread
        public void run() {
            zzqn.this.b.lock();
            try {
                if (Thread.interrupted()) {
                    zzqn.this.b.unlock();
                    return;
                }
                a();
                zzqn.this.b.unlock();
            } catch (RuntimeException e) {
                zzqn.this.a.a(e);
            } catch (Throwable th) {
                zzqn.this.b.unlock();
                throw th;
            }
        }
    }

    public zzqn(zzqr zzqr, zzh zzh, Map<Api<?>, Integer> map, com.google.android.gms.common.zzc zzc2, com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> zza2, Lock lock, Context context) {
        this.a = zzqr;
        this.r = zzh;
        this.s = map;
        this.d = zzc2;
        this.t = zza2;
        this.b = lock;
        this.c = context;
    }

    /* access modifiers changed from: private */
    public void a(ConnectionResult connectionResult, Api<?> api, int i2) {
        if (i2 != 2) {
            int priority = api.zzapm().getPriority();
            if (a(priority, i2, connectionResult)) {
                this.e = connectionResult;
                this.f = priority;
            }
        }
        this.a.b.put(api.zzapp(), connectionResult);
    }

    /* access modifiers changed from: private */
    public void a(SignInResponse signInResponse) {
        if (a(0)) {
            ConnectionResult zzave = signInResponse.zzave();
            if (zzave.isSuccess()) {
                ResolveAccountResponse zzcdl = signInResponse.zzcdl();
                ConnectionResult zzave2 = zzcdl.zzave();
                if (!zzave2.isSuccess()) {
                    String valueOf = String.valueOf(zzave2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
                    sb.append("Sign-in succeeded with resolve account failure: ");
                    sb.append(valueOf);
                    Log.wtf("GoogleApiClientConnecting", sb.toString(), new Exception());
                    c(zzave2);
                    return;
                }
                this.n = true;
                this.o = zzcdl.zzavd();
                this.p = zzcdl.zzavf();
                this.q = zzcdl.zzavg();
            } else if (b(zzave)) {
                e();
            } else {
                c(zzave);
                return;
            }
            b();
        }
    }

    private void a(boolean z) {
        if (this.k != null) {
            if (this.k.isConnected() && z) {
                this.k.zzcda();
            }
            this.k.disconnect();
            this.o = null;
        }
    }

    /* access modifiers changed from: private */
    public boolean a() {
        ConnectionResult connectionResult;
        this.h--;
        if (this.h > 0) {
            return false;
        }
        if (this.h < 0) {
            Log.w("GoogleApiClientConnecting", this.a.g.f());
            Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            connectionResult = new ConnectionResult(8, null);
        } else if (this.e == null) {
            return true;
        } else {
            this.a.f = this.f;
            connectionResult = this.e;
        }
        c(connectionResult);
        return false;
    }

    /* access modifiers changed from: private */
    public boolean a(int i2) {
        if (this.g == i2) {
            return true;
        }
        Log.w("GoogleApiClientConnecting", this.a.g.f());
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(valueOf);
        Log.w("GoogleApiClientConnecting", sb.toString());
        int i3 = this.h;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i3);
        Log.w("GoogleApiClientConnecting", sb2.toString());
        String valueOf2 = String.valueOf(b(this.g));
        String valueOf3 = String.valueOf(b(i2));
        StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 70 + String.valueOf(valueOf3).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(valueOf2);
        sb3.append(" but received callback for step ");
        sb3.append(valueOf3);
        Log.wtf("GoogleApiClientConnecting", sb3.toString(), new Exception());
        c(new ConnectionResult(8, null));
        return false;
    }

    private boolean a(int i2, int i3, ConnectionResult connectionResult) {
        boolean z = false;
        if (i3 == 1 && !a(connectionResult)) {
            return false;
        }
        if (this.e == null || i2 < this.f) {
            z = true;
        }
        return z;
    }

    private boolean a(ConnectionResult connectionResult) {
        return connectionResult.hasResolution() || this.d.zzfl(connectionResult.getErrorCode()) != null;
    }

    private String b(int i2) {
        switch (i2) {
            case 0:
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            case 1:
                return "STEP_GETTING_REMOTE_SERVICE";
            default:
                return "UNKNOWN";
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.h == 0) {
            if (!this.m || this.n) {
                c();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean b(ConnectionResult connectionResult) {
        boolean z = true;
        if (this.l != 2) {
            if (this.l == 1 && !connectionResult.hasResolution()) {
                return true;
            }
            z = false;
        }
        return z;
    }

    private void c() {
        ArrayList arrayList = new ArrayList();
        this.g = 1;
        this.h = this.a.a.size();
        for (com.google.android.gms.common.api.Api.zzc zzc2 : this.a.a.keySet()) {
            if (!this.a.b.containsKey(zzc2)) {
                arrayList.add((com.google.android.gms.common.api.Api.zze) this.a.a.get(zzc2));
            } else if (a()) {
                d();
            }
        }
        if (!arrayList.isEmpty()) {
            this.u.add(zzqs.zzarz().submit(new zzc(arrayList)));
        }
    }

    /* access modifiers changed from: private */
    public void c(ConnectionResult connectionResult) {
        f();
        a(!connectionResult.hasResolution());
        this.a.a(connectionResult);
        this.a.h.zzd(connectionResult);
    }

    private void d() {
        this.a.b();
        zzqs.zzarz().execute(new Runnable() {
            public void run() {
                zzqn.this.d.zzbq(zzqn.this.c);
            }
        });
        if (this.k != null) {
            if (this.p) {
                this.k.zza(this.o, this.q);
            }
            a(false);
        }
        for (com.google.android.gms.common.api.Api.zzc zzc2 : this.a.b.keySet()) {
            ((com.google.android.gms.common.api.Api.zze) this.a.a.get(zzc2)).disconnect();
        }
        this.a.h.zzn(this.i.isEmpty() ? null : this.i);
    }

    /* access modifiers changed from: private */
    public void e() {
        this.m = false;
        this.a.g.d = Collections.emptySet();
        for (com.google.android.gms.common.api.Api.zzc zzc2 : this.j) {
            if (!this.a.b.containsKey(zzc2)) {
                this.a.b.put(zzc2, new ConnectionResult(17, null));
            }
        }
    }

    private void f() {
        Iterator it = this.u.iterator();
        while (it.hasNext()) {
            ((Future) it.next()).cancel(true);
        }
        this.u.clear();
    }

    /* access modifiers changed from: private */
    public Set<Scope> g() {
        if (this.r == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(this.r.zzaug());
        Map zzaui = this.r.zzaui();
        for (Api api : zzaui.keySet()) {
            if (!this.a.b.containsKey(api.zzapp())) {
                hashSet.addAll(((com.google.android.gms.common.internal.zzh.zza) zzaui.get(api)).hm);
            }
        }
        return hashSet;
    }

    public void begin() {
        this.a.b.clear();
        this.m = false;
        this.e = null;
        this.g = 0;
        this.l = 2;
        this.n = false;
        this.p = false;
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (Api api : this.s.keySet()) {
            com.google.android.gms.common.api.Api.zze zze2 = (com.google.android.gms.common.api.Api.zze) this.a.a.get(api.zzapp());
            int intValue = ((Integer) this.s.get(api)).intValue();
            z |= api.zzapm().getPriority() == 1;
            if (zze2.zzahd()) {
                this.m = true;
                if (intValue < this.l) {
                    this.l = intValue;
                }
                if (intValue != 0) {
                    this.j.add(api.zzapp());
                }
            }
            hashMap.put(zze2, new zza(this, api, intValue));
        }
        if (z) {
            this.m = false;
        }
        if (this.m) {
            this.r.zzc(Integer.valueOf(this.a.g.getSessionId()));
            zze zze3 = new zze();
            this.k = (zzwz) this.t.zza(this.c, this.a.g.getLooper(), this.r, this.r.zzaum(), zze3, zze3);
        }
        this.h = this.a.a.size();
        this.u.add(zzqs.zzarz().submit(new zzb(hashMap)));
    }

    public void connect() {
    }

    public boolean disconnect() {
        f();
        a(true);
        this.a.a((ConnectionResult) null);
        return true;
    }

    public void onConnected(Bundle bundle) {
        if (a(1)) {
            if (bundle != null) {
                this.i.putAll(bundle);
            }
            if (a()) {
                d();
            }
        }
    }

    public void onConnectionSuspended(int i2) {
        c(new ConnectionResult(8, null));
    }

    public void zza(ConnectionResult connectionResult, Api<?> api, int i2) {
        if (a(1)) {
            a(connectionResult, api, i2);
            if (a()) {
                d();
            }
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqc.zza<R, A>> T zzc(T t2) {
        this.a.g.a.add(t2);
        return t2;
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqc.zza<? extends Result, A>> T zzd(T t2) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
