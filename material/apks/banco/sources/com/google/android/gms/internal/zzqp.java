package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.android.gms.auth.api.signin.internal.zzk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzh;
import com.google.android.gms.common.internal.zzm;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zzqp extends GoogleApiClient implements com.google.android.gms.internal.zzqy.zza {
    final Queue<com.google.android.gms.internal.zzqc.zza<?, ?>> a = new LinkedList();
    zzqv b;
    final Map<zzc<?>, zze> c;
    Set<Scope> d = new HashSet();
    final zzh e;
    final Map<Api<?>, Integer> f;
    final com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> g;
    Set<zzrp> h = null;
    final zzrq i;
    private final Lock j;
    private final zzm k;
    private zzqy l = null;
    private final int m;
    /* access modifiers changed from: private */
    public final Context n;
    private final Looper o;
    private volatile boolean p;
    private long q = 120000;
    private long r = LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
    private final zza s;
    private final GoogleApiAvailability t;
    private final zzre u = new zzre();
    private final ArrayList<zzqf> v;
    private Integer w = null;
    private final com.google.android.gms.common.internal.zzm.zza x = new com.google.android.gms.common.internal.zzm.zza() {
        public boolean isConnected() {
            return zzqp.this.isConnected();
        }

        public Bundle zzaoe() {
            return null;
        }
    };

    final class zza extends Handler {
        zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    zzqp.this.i();
                    return;
                case 2:
                    zzqp.this.h();
                    return;
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(31);
                    sb.append("Unknown message id: ");
                    sb.append(i);
                    Log.w("GoogleApiClientImpl", sb.toString());
                    return;
            }
        }
    }

    static class zzb extends com.google.android.gms.internal.zzqv.zza {
        private WeakReference<zzqp> a;

        zzb(zzqp zzqp) {
            this.a = new WeakReference<>(zzqp);
        }

        public void zzaqp() {
            zzqp zzqp = (zzqp) this.a.get();
            if (zzqp != null) {
                zzqp.h();
            }
        }
    }

    public zzqp(Context context, Lock lock, Looper looper, zzh zzh, GoogleApiAvailability googleApiAvailability, com.google.android.gms.common.api.Api.zza<? extends zzwz, zzxa> zza2, Map<Api<?>, Integer> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<zzc<?>, zze> map2, int i2, int i3, ArrayList<zzqf> arrayList) {
        Looper looper2 = looper;
        this.n = context;
        this.j = lock;
        this.k = new zzm(looper2, this.x);
        this.o = looper2;
        this.s = new zza(looper2);
        this.t = googleApiAvailability;
        this.m = i2;
        if (this.m >= 0) {
            this.w = Integer.valueOf(i3);
        }
        this.f = map;
        this.c = map2;
        this.v = arrayList;
        this.i = new zzrq(this.c);
        for (ConnectionCallbacks registerConnectionCallbacks : list) {
            this.k.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : list2) {
            this.k.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        this.e = zzh;
        this.g = zza2;
    }

    static String a(int i2) {
        switch (i2) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    /* access modifiers changed from: private */
    public void a(final GoogleApiClient googleApiClient, final zzrm zzrm, final boolean z) {
        zzrx.Dh.zzg(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            /* renamed from: a */
            public void onResult(@NonNull Status status) {
                zzk.zzbd(zzqp.this.n).zzaie();
                if (status.isSuccess() && zzqp.this.isConnected()) {
                    zzqp.this.reconnect();
                }
                zzrm.zzc(status);
                if (z) {
                    googleApiClient.disconnect();
                }
            }
        });
    }

    private void a(@NonNull zzqz zzqz) {
        if (this.m >= 0) {
            zzqa.zza(zzqz).zzfq(this.m);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    /* JADX WARNING: type inference failed for: r14v11, types: [com.google.android.gms.internal.zzqh] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r14) {
        /*
            r13 = this;
            java.lang.Integer r0 = r13.w
            if (r0 != 0) goto L_0x000b
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            r13.w = r14
            goto L_0x005b
        L_0x000b:
            java.lang.Integer r0 = r13.w
            int r0 = r0.intValue()
            if (r0 == r14) goto L_0x005b
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r14 = a(r14)
            java.lang.String r14 = java.lang.String.valueOf(r14)
            java.lang.Integer r1 = r13.w
            int r1 = r1.intValue()
            java.lang.String r1 = a(r1)
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = java.lang.String.valueOf(r14)
            int r3 = r3.length()
            int r3 = r3 + 51
            java.lang.String r4 = java.lang.String.valueOf(r1)
            int r4 = r4.length()
            int r3 = r3 + r4
            r2.<init>(r3)
            java.lang.String r3 = "Cannot use sign-in mode: "
            r2.append(r3)
            r2.append(r14)
            java.lang.String r14 = ". Mode was already set to "
            r2.append(r14)
            r2.append(r1)
            java.lang.String r14 = r2.toString()
            r0.<init>(r14)
            throw r0
        L_0x005b:
            com.google.android.gms.internal.zzqy r14 = r13.l
            if (r14 == 0) goto L_0x0060
            return
        L_0x0060:
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.Api$zze> r14 = r13.c
            java.util.Collection r14 = r14.values()
            java.util.Iterator r14 = r14.iterator()
            r0 = 0
            r1 = 0
        L_0x006c:
            boolean r2 = r14.hasNext()
            if (r2 == 0) goto L_0x0088
            java.lang.Object r2 = r14.next()
            com.google.android.gms.common.api.Api$zze r2 = (com.google.android.gms.common.api.Api.zze) r2
            boolean r3 = r2.zzahd()
            r4 = 1
            if (r3 == 0) goto L_0x0080
            r0 = 1
        L_0x0080:
            boolean r2 = r2.zzahs()
            if (r2 == 0) goto L_0x006c
            r1 = 1
            goto L_0x006c
        L_0x0088:
            java.lang.Integer r14 = r13.w
            int r14 = r14.intValue()
            switch(r14) {
                case 1: goto L_0x00ae;
                case 2: goto L_0x0092;
                case 3: goto L_0x00c2;
                default: goto L_0x0091;
            }
        L_0x0091:
            goto L_0x00c2
        L_0x0092:
            if (r0 == 0) goto L_0x00c2
            android.content.Context r2 = r13.n
            java.util.concurrent.locks.Lock r4 = r13.j
            android.os.Looper r5 = r13.o
            com.google.android.gms.common.GoogleApiAvailability r6 = r13.t
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.Api$zze> r7 = r13.c
            com.google.android.gms.common.internal.zzh r8 = r13.e
            java.util.Map<com.google.android.gms.common.api.Api<?>, java.lang.Integer> r9 = r13.f
            com.google.android.gms.common.api.Api$zza<? extends com.google.android.gms.internal.zzwz, com.google.android.gms.internal.zzxa> r10 = r13.g
            java.util.ArrayList<com.google.android.gms.internal.zzqf> r11 = r13.v
            r3 = r13
            com.google.android.gms.internal.zzqh r14 = com.google.android.gms.internal.zzqh.a(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
        L_0x00ab:
            r13.l = r14
            return
        L_0x00ae:
            if (r0 != 0) goto L_0x00b8
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead."
            r14.<init>(r0)
            throw r14
        L_0x00b8:
            if (r1 == 0) goto L_0x00c2
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead."
            r14.<init>(r0)
            throw r14
        L_0x00c2:
            com.google.android.gms.internal.zzqr r14 = new com.google.android.gms.internal.zzqr
            android.content.Context r2 = r13.n
            java.util.concurrent.locks.Lock r4 = r13.j
            android.os.Looper r5 = r13.o
            com.google.android.gms.common.GoogleApiAvailability r6 = r13.t
            java.util.Map<com.google.android.gms.common.api.Api$zzc<?>, com.google.android.gms.common.api.Api$zze> r7 = r13.c
            com.google.android.gms.common.internal.zzh r8 = r13.e
            java.util.Map<com.google.android.gms.common.api.Api<?>, java.lang.Integer> r9 = r13.f
            com.google.android.gms.common.api.Api$zza<? extends com.google.android.gms.internal.zzwz, com.google.android.gms.internal.zzxa> r10 = r13.g
            java.util.ArrayList<com.google.android.gms.internal.zzqf> r11 = r13.v
            r1 = r14
            r3 = r13
            r12 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqp.b(int):void");
    }

    private void g() {
        this.k.zzauu();
        this.l.connect();
    }

    /* access modifiers changed from: private */
    public void h() {
        this.j.lock();
        try {
            if (b()) {
                g();
            }
        } finally {
            this.j.unlock();
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        this.j.lock();
        try {
            if (d()) {
                g();
            }
        } finally {
            this.j.unlock();
        }
    }

    public static int zza(Iterable<zze> iterable, boolean z) {
        boolean z2 = false;
        boolean z3 = false;
        for (zze zze : iterable) {
            if (zze.zzahd()) {
                z2 = true;
            }
            if (zze.zzahs()) {
                z3 = true;
            }
        }
        if (z2) {
            return (!z3 || !z) ? 1 : 2;
        }
        return 3;
    }

    /* access modifiers changed from: 0000 */
    public <C extends zze> C a(zzc<?> zzc) {
        C c2 = (zze) this.c.get(zzc);
        zzac.zzb(c2, (Object) "Appropriate Api was not requested.");
        return c2;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.p;
    }

    public ConnectionResult blockingConnect() {
        boolean z = true;
        zzac.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        this.j.lock();
        try {
            if (this.m >= 0) {
                if (this.w == null) {
                    z = false;
                }
                zzac.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.w == null) {
                this.w = Integer.valueOf(zza(this.c.values(), false));
            } else if (this.w.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            b(this.w.intValue());
            this.k.zzauu();
            return this.l.blockingConnect();
        } finally {
            this.j.unlock();
        }
    }

    public ConnectionResult blockingConnect(long j2, @NonNull TimeUnit timeUnit) {
        zzac.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "blockingConnect must not be called on the UI thread");
        zzac.zzb(timeUnit, (Object) "TimeUnit must not be null");
        this.j.lock();
        try {
            if (this.w == null) {
                this.w = Integer.valueOf(zza(this.c.values(), false));
            } else if (this.w.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            b(this.w.intValue());
            this.k.zzauu();
            return this.l.blockingConnect(j2, timeUnit);
        } finally {
            this.j.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (!b()) {
            this.p = true;
            if (this.b == null) {
                this.b = this.t.zza(this.n.getApplicationContext(), (com.google.android.gms.internal.zzqv.zza) new zzb(this));
            }
            this.s.sendMessageDelayed(this.s.obtainMessage(1), this.q);
            this.s.sendMessageDelayed(this.s.obtainMessage(2), this.r);
        }
    }

    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        zzac.zza(isConnected(), (Object) "GoogleApiClient is not connected yet.");
        zzac.zza(this.w.intValue() != 2, (Object) "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        final zzrm zzrm = new zzrm((GoogleApiClient) this);
        if (this.c.containsKey(zzrx.fa)) {
            a(this, zzrm, false);
            return zzrm;
        }
        final AtomicReference atomicReference = new AtomicReference();
        GoogleApiClient build = new Builder(this.n).addApi(zzrx.API).addConnectionCallbacks(new ConnectionCallbacks() {
            public void onConnected(Bundle bundle) {
                zzqp.this.a((GoogleApiClient) atomicReference.get(), zzrm, true);
            }

            public void onConnectionSuspended(int i) {
            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener() {
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                zzrm.zzc(new Status(8));
            }
        }).setHandler(this.s).build();
        atomicReference.set(build);
        build.connect();
        return zzrm;
    }

    public void connect() {
        this.j.lock();
        try {
            boolean z = false;
            if (this.m >= 0) {
                if (this.w != null) {
                    z = true;
                }
                zzac.zza(z, (Object) "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.w == null) {
                this.w = Integer.valueOf(zza(this.c.values(), false));
            } else if (this.w.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.w.intValue());
        } finally {
            this.j.unlock();
        }
    }

    public void connect(int i2) {
        this.j.lock();
        boolean z = true;
        if (!(i2 == 3 || i2 == 1 || i2 == 2)) {
            z = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i2);
            zzac.zzb(z, (Object) sb.toString());
            b(i2);
            g();
        } finally {
            this.j.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        if (!b()) {
            return false;
        }
        this.p = false;
        this.s.removeMessages(2);
        this.s.removeMessages(1);
        if (this.b != null) {
            this.b.unregister();
            this.b = null;
        }
        return true;
    }

    public void disconnect() {
        this.j.lock();
        try {
            this.i.release();
            if (this.l != null) {
                this.l.disconnect();
            }
            this.u.release();
            for (com.google.android.gms.internal.zzqc.zza zza2 : this.a) {
                zza2.zza((zzb) null);
                zza2.cancel();
            }
            this.a.clear();
            if (this.l != null) {
                d();
                this.k.zzaut();
                this.j.unlock();
            }
        } finally {
            this.j.unlock();
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.n);
        printWriter.append(str).append("mResuming=").print(this.p);
        printWriter.append(" mWorkQueue.size()=").print(this.a.size());
        this.i.dump(printWriter);
        if (this.l != null) {
            this.l.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public boolean e() {
        this.j.lock();
        try {
            if (this.h == null) {
                this.j.unlock();
                return false;
            }
            boolean z = !this.h.isEmpty();
            this.j.unlock();
            return z;
        } catch (Throwable th) {
            this.j.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public String f() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        ConnectionResult connectionResult;
        this.j.lock();
        try {
            if (!isConnected() && !b()) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            } else if (this.c.containsKey(api.zzapp())) {
                ConnectionResult connectionResult2 = this.l.getConnectionResult(api);
                if (connectionResult2 == null) {
                    if (b()) {
                        connectionResult = ConnectionResult.uJ;
                    } else {
                        Log.w("GoogleApiClientImpl", f());
                        Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                        connectionResult = new ConnectionResult(8, null);
                    }
                    return connectionResult;
                }
                this.j.unlock();
                return connectionResult2;
            } else {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
        } finally {
            this.j.unlock();
        }
    }

    public Context getContext() {
        return this.n;
    }

    public Looper getLooper() {
        return this.o;
    }

    public int getSessionId() {
        return System.identityHashCode(this);
    }

    public boolean hasConnectedApi(@NonNull Api<?> api) {
        zze zze = (zze) this.c.get(api.zzapp());
        return zze != null && zze.isConnected();
    }

    public boolean isConnected() {
        return this.l != null && this.l.isConnected();
    }

    public boolean isConnecting() {
        return this.l != null && this.l.isConnecting();
    }

    public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.k.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.k.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public void reconnect() {
        disconnect();
        connect();
    }

    public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.k.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.k.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        a(new zzqz(fragmentActivity));
    }

    public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.k.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.k.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> zzc) {
        C c2 = (zze) this.c.get(zzc);
        zzac.zzb(c2, (Object) "Appropriate Api was not requested.");
        return c2;
    }

    public void zza(zzrp zzrp) {
        this.j.lock();
        try {
            if (this.h == null) {
                this.h = new HashSet();
            }
            this.h.add(zzrp);
        } finally {
            this.j.unlock();
        }
    }

    public boolean zza(@NonNull Api<?> api) {
        return this.c.containsKey(api.zzapp());
    }

    public boolean zza(zzrl zzrl) {
        return this.l != null && this.l.zza(zzrl);
    }

    public void zzaqb() {
        if (this.l != null) {
            this.l.zzaqb();
        }
    }

    public void zzb(zzrp zzrp) {
        String str;
        String str2;
        Exception exc;
        this.j.lock();
        try {
            if (this.h == null) {
                str = "GoogleApiClientImpl";
                str2 = "Attempted to remove pending transform when no transforms are registered.";
                exc = new Exception();
            } else if (!this.h.remove(zzrp)) {
                str = "GoogleApiClientImpl";
                str2 = "Failed to remove pending transform - this may lead to memory leaks!";
                exc = new Exception();
            } else {
                if (!e()) {
                    this.l.zzaqy();
                }
            }
            Log.wtf(str, str2, exc);
        } finally {
            this.j.unlock();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, R extends Result, T extends com.google.android.gms.internal.zzqc.zza<R, A>> T zzc(@NonNull T t2) {
        zzac.zzb(t2.zzapp() != null, (Object) "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.c.containsKey(t2.zzapp());
        String name = t2.zzaqn() != null ? t2.zzaqn().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        zzac.zzb(containsKey, (Object) sb.toString());
        this.j.lock();
        try {
            if (this.l == null) {
                this.a.add(t2);
            } else {
                t2 = this.l.zzc(t2);
            }
            return t2;
        } finally {
            this.j.unlock();
        }
    }

    public void zzc(int i2, boolean z) {
        if (i2 == 1 && !z) {
            c();
        }
        this.i.zzasw();
        this.k.zzgo(i2);
        this.k.zzaut();
        if (i2 == 2) {
            g();
        }
    }

    public <A extends com.google.android.gms.common.api.Api.zzb, T extends com.google.android.gms.internal.zzqc.zza<? extends Result, A>> T zzd(@NonNull T t2) {
        zzac.zzb(t2.zzapp() != null, (Object) "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.c.containsKey(t2.zzapp());
        String name = t2.zzaqn() != null ? t2.zzaqn().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        zzac.zzb(containsKey, (Object) sb.toString());
        this.j.lock();
        try {
            if (this.l == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (b()) {
                this.a.add(t2);
                while (!this.a.isEmpty()) {
                    com.google.android.gms.internal.zzqc.zza zza2 = (com.google.android.gms.internal.zzqc.zza) this.a.remove();
                    this.i.a((zzqe<? extends Result>) zza2);
                    zza2.zzz(Status.wa);
                }
            } else {
                t2 = this.l.zzd(t2);
            }
            return t2;
        } finally {
            this.j.unlock();
        }
    }

    public void zzd(ConnectionResult connectionResult) {
        if (!this.t.zzd(this.n, connectionResult.getErrorCode())) {
            d();
        }
        if (!b()) {
            this.k.zzn(connectionResult);
            this.k.zzaut();
        }
    }

    public void zzn(Bundle bundle) {
        while (!this.a.isEmpty()) {
            zzd((T) (com.google.android.gms.internal.zzqc.zza) this.a.remove());
        }
        this.k.zzp(bundle);
    }

    public <L> zzrd<L> zzs(@NonNull L l2) {
        this.j.lock();
        try {
            return this.u.zzb(l2, this.o);
        } finally {
            this.j.unlock();
        }
    }
}
