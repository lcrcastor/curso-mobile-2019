package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzai;
import com.google.android.gms.common.internal.zze.zzf;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class zzqt implements Callback {
    /* access modifiers changed from: private */
    public static final Object d = new Object();
    private static zzqt e;
    /* access modifiers changed from: private */
    public long a;
    /* access modifiers changed from: private */
    public long b;
    /* access modifiers changed from: private */
    public long c;
    /* access modifiers changed from: private */
    public final Context f;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability g;
    /* access modifiers changed from: private */
    public int h;
    private final AtomicInteger i;
    private final SparseArray<zzc<?>> j;
    /* access modifiers changed from: private */
    public final Map<zzpz<?>, zzc<?>> k;
    private zzqi l;
    /* access modifiers changed from: private */
    public final Set<zzpz<?>> m;
    /* access modifiers changed from: private */
    public final Handler n;
    private final ReferenceQueue<com.google.android.gms.common.api.zzd<?>> o;
    /* access modifiers changed from: private */
    public final SparseArray<zza> p;
    private zzb q;

    final class zza extends PhantomReference<com.google.android.gms.common.api.zzd<?>> {
        /* access modifiers changed from: private */
        public final int b;

        public zza(com.google.android.gms.common.api.zzd zzd, int i, ReferenceQueue<com.google.android.gms.common.api.zzd<?>> referenceQueue) {
            super(zzd, referenceQueue);
            this.b = i;
        }

        public void a() {
            zzqt.this.n.sendMessage(zzqt.this.n.obtainMessage(2, this.b, 2));
        }
    }

    static final class zzb extends Thread {
        private final ReferenceQueue<com.google.android.gms.common.api.zzd<?>> a;
        private final SparseArray<zza> b;
        /* access modifiers changed from: private */
        public final AtomicBoolean c = new AtomicBoolean();

        public zzb(ReferenceQueue<com.google.android.gms.common.api.zzd<?>> referenceQueue, SparseArray<zza> sparseArray) {
            super("GoogleApiCleanup");
            this.a = referenceQueue;
            this.b = sparseArray;
        }

        public void run() {
            this.c.set(true);
            Process.setThreadPriority(10);
            while (this.c.get()) {
                try {
                    zza zza = (zza) this.a.remove();
                    this.b.remove(zza.b);
                    zza.a();
                } catch (InterruptedException unused) {
                } catch (Throwable th) {
                    this.c.set(false);
                    throw th;
                }
            }
            this.c.set(false);
        }
    }

    class zzc<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zzqg {
        /* access modifiers changed from: private */
        public final Queue<zzpy> b = new LinkedList();
        private final zze c;
        private final com.google.android.gms.common.api.Api.zzb d;
        private final zzpz<O> e;
        private final SparseArray<zzrq> f = new SparseArray<>();
        private final Set<zzqb> g = new HashSet();
        private final SparseArray<Map<com.google.android.gms.internal.zzrd.zzb<?>, zzri>> h = new SparseArray<>();
        private boolean i;
        private ConnectionResult j = null;

        @WorkerThread
        public zzc(com.google.android.gms.common.api.zzd<O> zzd) {
            this.c = zzd.zza(zzqt.this.n.getLooper(), this, this);
            this.d = this.c instanceof zzai ? ((zzai) this.c).zzavk() : this.c;
            this.e = zzd.zzapx();
        }

        @WorkerThread
        private void a(ConnectionResult connectionResult) {
            for (zzqb zza : this.g) {
                zza.zza(this.e, connectionResult);
            }
            this.g.clear();
        }

        /* access modifiers changed from: private */
        @WorkerThread
        public void a(Status status) {
            for (zzpy zzx : this.b) {
                zzx.zzx(status);
            }
            this.b.clear();
        }

        @WorkerThread
        private void b(zzpy zzpy) {
            zzpy.zza(this.f);
            try {
                zzpy.zzb(this.d);
            } catch (DeadObjectException unused) {
                this.c.disconnect();
                onConnectionSuspended(1);
            }
        }

        /* access modifiers changed from: private */
        @WorkerThread
        public void e() {
            if (this.i) {
                j();
            }
        }

        @WorkerThread
        private void f() {
            if (this.i) {
                zzqt.this.n.removeMessages(10, this.e);
                zzqt.this.n.removeMessages(9, this.e);
                this.i = false;
            }
        }

        /* access modifiers changed from: private */
        @WorkerThread
        public void g() {
            if (this.i) {
                f();
                a(zzqt.this.g.isGooglePlayServicesAvailable(zzqt.this.f) == 18 ? new Status(8, "Connection timed out while waiting for Google Play services update to complete.") : new Status(8, "API failed to connect while resuming due to an unknown error."));
                this.c.disconnect();
            }
        }

        private void h() {
            zzqt.this.n.removeMessages(11, this.e);
            zzqt.this.n.sendMessageDelayed(zzqt.this.n.obtainMessage(11, this.e), zzqt.this.c);
        }

        /* access modifiers changed from: private */
        public void i() {
            if (this.c.isConnected() && this.h.size() == 0) {
                for (int i2 = 0; i2 < this.f.size(); i2++) {
                    if (((zzrq) this.f.get(this.f.keyAt(i2))).zzasx()) {
                        h();
                        return;
                    }
                }
                this.c.disconnect();
            }
        }

        /* access modifiers changed from: private */
        @WorkerThread
        public void j() {
            if (!this.c.isConnected() && !this.c.isConnecting()) {
                if (this.c.zzapr() && zzqt.this.h != 0) {
                    zzqt.this.h = zzqt.this.g.isGooglePlayServicesAvailable(zzqt.this.f);
                    if (zzqt.this.h != 0) {
                        onConnectionFailed(new ConnectionResult(zzqt.this.h, null));
                        return;
                    }
                }
                this.c.zza(new zzd(this.c, this.e));
            }
        }

        @WorkerThread
        public void a() {
            while (this.c.isConnected() && !this.b.isEmpty()) {
                b((zzpy) this.b.remove());
            }
        }

        @WorkerThread
        public void a(int i2) {
            this.f.put(i2, new zzrq(this.c));
        }

        @WorkerThread
        public void a(int i2, @NonNull com.google.android.gms.internal.zzrd.zzb<?> zzb, @NonNull TaskCompletionSource<Void> taskCompletionSource) {
            Map map = (Map) this.h.get(i2);
            if (map == null || map.get(zzb) == null) {
                taskCompletionSource.setException(new com.google.android.gms.common.api.zza(Status.wa));
                Log.wtf("GoogleApiManager", "Received call to unregister a listener without a matching registration call.", new Exception());
                return;
            }
            a((zzpy) new zzpy.zze(i2, ((zzri) map.get(zzb)).wk, taskCompletionSource, this.h));
        }

        @WorkerThread
        public void a(int i2, @NonNull zzri zzri, @NonNull TaskCompletionSource<Void> taskCompletionSource) {
            a((zzpy) new com.google.android.gms.internal.zzpy.zzc(i2, zzri, taskCompletionSource, this.h));
        }

        @WorkerThread
        public void a(int i2, boolean z) {
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                zzpy zzpy = (zzpy) it.next();
                if (zzpy.wf == i2 && zzpy.lN != 1 && zzpy.cancel()) {
                    it.remove();
                }
            }
            ((zzrq) this.f.get(i2)).release();
            this.h.delete(i2);
            if (!z) {
                this.f.remove(i2);
                zzqt.this.p.remove(i2);
                if (this.f.size() == 0 && this.b.isEmpty()) {
                    f();
                    this.c.disconnect();
                    zzqt.this.k.remove(this.e);
                    synchronized (zzqt.d) {
                        zzqt.this.m.remove(this.e);
                    }
                }
            }
        }

        @WorkerThread
        public void a(zzpy zzpy) {
            if (this.c.isConnected()) {
                b(zzpy);
                h();
                return;
            }
            this.b.add(zzpy);
            if (this.j == null || !this.j.hasResolution()) {
                j();
            } else {
                onConnectionFailed(this.j);
            }
        }

        @WorkerThread
        public void a(zzqb zzqb) {
            this.g.add(zzqb);
        }

        @WorkerThread
        public void b() {
            this.j = null;
        }

        @WorkerThread
        public void b(final int i2) {
            ((zzrq) this.f.get(i2)).zza(new zzc() {
                public void a() {
                    if (zzc.this.b.isEmpty()) {
                        zzc.this.a(i2, false);
                    }
                }
            });
        }

        /* access modifiers changed from: 0000 */
        public ConnectionResult c() {
            return this.j;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            return this.c.isConnected();
        }

        @WorkerThread
        public void onConnected(@Nullable Bundle bundle) {
            b();
            a(ConnectionResult.uJ);
            f();
            for (int i2 = 0; i2 < this.h.size(); i2++) {
                for (zzri zzri : ((Map) this.h.get(this.h.keyAt(i2))).values()) {
                    try {
                        zzri.wj.zza(this.d, new TaskCompletionSource());
                    } catch (DeadObjectException unused) {
                        this.c.disconnect();
                        onConnectionSuspended(1);
                    }
                }
            }
            a();
            h();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
            if (r4.a.a(r5, r0) != false) goto L_0x00ae;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0053, code lost:
            if (r5.getErrorCode() != 18) goto L_0x0058;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0055, code lost:
            r4.i = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x005a, code lost:
            if (r4.i == false) goto L_0x007a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
            com.google.android.gms.internal.zzqt.a(r4.a).sendMessageDelayed(android.os.Message.obtain(com.google.android.gms.internal.zzqt.a(r4.a), 9, r4.e), com.google.android.gms.internal.zzqt.b(r4.a));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0079, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x007a, code lost:
            r1 = java.lang.String.valueOf(r4.e.zzaqj());
            r2 = new java.lang.StringBuilder(java.lang.String.valueOf(r1).length() + 38);
            r2.append("API: ");
            r2.append(r1);
            r2.append(" is not available on this device.");
            a(new com.google.android.gms.common.api.Status(17, r2.toString()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ae, code lost:
            return;
         */
        @android.support.annotation.WorkerThread
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r5) {
            /*
                r4 = this;
                r4.b()
                com.google.android.gms.internal.zzqt r0 = com.google.android.gms.internal.zzqt.this
                r1 = -1
                r0.h = r1
                r4.a(r5)
                android.util.SparseArray<com.google.android.gms.internal.zzrq> r0 = r4.f
                r1 = 0
                int r0 = r0.keyAt(r1)
                java.util.Queue<com.google.android.gms.internal.zzpy> r1 = r4.b
                boolean r1 = r1.isEmpty()
                if (r1 == 0) goto L_0x001e
                r4.j = r5
                return
            L_0x001e:
                java.lang.Object r1 = com.google.android.gms.internal.zzqt.d
                monitor-enter(r1)
                com.google.android.gms.internal.zzqt r2 = com.google.android.gms.internal.zzqt.this     // Catch:{ all -> 0x00af }
                com.google.android.gms.internal.zzqi r2 = com.google.android.gms.internal.zzqt.d(r2)     // Catch:{ all -> 0x00af }
                if (r2 == 0) goto L_0x0044
                com.google.android.gms.internal.zzqt r2 = com.google.android.gms.internal.zzqt.this     // Catch:{ all -> 0x00af }
                java.util.Set r2 = r2.m     // Catch:{ all -> 0x00af }
                com.google.android.gms.internal.zzpz<O> r3 = r4.e     // Catch:{ all -> 0x00af }
                boolean r2 = r2.contains(r3)     // Catch:{ all -> 0x00af }
                if (r2 == 0) goto L_0x0044
                com.google.android.gms.internal.zzqt r2 = com.google.android.gms.internal.zzqt.this     // Catch:{ all -> 0x00af }
                com.google.android.gms.internal.zzqi r2 = com.google.android.gms.internal.zzqt.d(r2)     // Catch:{ all -> 0x00af }
                r2.zzb(r5, r0)     // Catch:{ all -> 0x00af }
                monitor-exit(r1)     // Catch:{ all -> 0x00af }
                return
            L_0x0044:
                monitor-exit(r1)     // Catch:{ all -> 0x00af }
                com.google.android.gms.internal.zzqt r1 = com.google.android.gms.internal.zzqt.this
                boolean r0 = r1.a(r5, r0)
                if (r0 != 0) goto L_0x00ae
                int r5 = r5.getErrorCode()
                r0 = 18
                if (r5 != r0) goto L_0x0058
                r5 = 1
                r4.i = r5
            L_0x0058:
                boolean r5 = r4.i
                if (r5 == 0) goto L_0x007a
                com.google.android.gms.internal.zzqt r5 = com.google.android.gms.internal.zzqt.this
                android.os.Handler r5 = r5.n
                com.google.android.gms.internal.zzqt r0 = com.google.android.gms.internal.zzqt.this
                android.os.Handler r0 = r0.n
                r1 = 9
                com.google.android.gms.internal.zzpz<O> r2 = r4.e
                android.os.Message r0 = android.os.Message.obtain(r0, r1, r2)
                com.google.android.gms.internal.zzqt r1 = com.google.android.gms.internal.zzqt.this
                long r1 = r1.a
                r5.sendMessageDelayed(r0, r1)
                return
            L_0x007a:
                com.google.android.gms.common.api.Status r5 = new com.google.android.gms.common.api.Status
                r0 = 17
                com.google.android.gms.internal.zzpz<O> r1 = r4.e
                java.lang.String r1 = r1.zzaqj()
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r3 = r3 + 38
                r2.<init>(r3)
                java.lang.String r3 = "API: "
                r2.append(r3)
                r2.append(r1)
                java.lang.String r1 = " is not available on this device."
                r2.append(r1)
                java.lang.String r1 = r2.toString()
                r5.<init>(r0, r1)
                r4.a(r5)
            L_0x00ae:
                return
            L_0x00af:
                r5 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x00af }
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqt.zzc.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
        }

        @WorkerThread
        public void onConnectionSuspended(int i2) {
            b();
            this.i = true;
            zzqt.this.n.sendMessageDelayed(Message.obtain(zzqt.this.n, 9, this.e), zzqt.this.a);
            zzqt.this.n.sendMessageDelayed(Message.obtain(zzqt.this.n, 10, this.e), zzqt.this.b);
            zzqt.this.h = -1;
        }

        public void zza(ConnectionResult connectionResult, Api<?> api, int i2) {
            onConnectionFailed(connectionResult);
        }
    }

    class zzd implements zzf {
        private final zze b;
        private final zzpz<?> c;

        public zzd(zze zze, zzpz<?> zzpz) {
            this.b = zze;
            this.c = zzpz;
        }

        @WorkerThread
        public void zzh(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                this.b.zza(null, Collections.emptySet());
            } else {
                ((zzc) zzqt.this.k.get(this.c)).onConnectionFailed(connectionResult);
            }
        }
    }

    private zzqt(Context context) {
        this(context, GoogleApiAvailability.getInstance());
    }

    private zzqt(Context context, GoogleApiAvailability googleApiAvailability) {
        this.a = LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
        this.b = 120000;
        this.c = LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS;
        this.h = -1;
        this.i = new AtomicInteger(1);
        this.j = new SparseArray<>();
        this.k = new ConcurrentHashMap(5, 0.75f, 1);
        this.l = null;
        this.m = new com.google.android.gms.common.util.zza();
        this.o = new ReferenceQueue<>();
        this.p = new SparseArray<>();
        this.f = context;
        HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
        handlerThread.start();
        this.n = new Handler(handlerThread.getLooper(), this);
        this.g = googleApiAvailability;
    }

    private int a(com.google.android.gms.common.api.zzd<?> zzd2) {
        int andIncrement = this.i.getAndIncrement();
        this.n.sendMessage(this.n.obtainMessage(6, andIncrement, 0, zzd2));
        return andIncrement;
    }

    @WorkerThread
    private void a(int i2) {
        zzc zzc2 = (zzc) this.j.get(i2);
        if (zzc2 != null) {
            this.j.delete(i2);
            zzc2.b(i2);
            return;
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append("onCleanupLeakInternal received for unknown instance: ");
        sb.append(i2);
        Log.wtf("GoogleApiManager", sb.toString(), new Exception());
    }

    @WorkerThread
    private void a(int i2, com.google.android.gms.internal.zzrd.zzb<?> zzb2, TaskCompletionSource<Void> taskCompletionSource) {
        ((zzc) this.j.get(i2)).a(i2, zzb2, taskCompletionSource);
    }

    @WorkerThread
    private void a(int i2, zzri zzri, TaskCompletionSource<Void> taskCompletionSource) {
        ((zzc) this.j.get(i2)).a(i2, zzri, taskCompletionSource);
    }

    @WorkerThread
    private void a(int i2, boolean z) {
        zzc zzc2 = (zzc) this.j.get(i2);
        if (zzc2 != null) {
            if (!z) {
                this.j.delete(i2);
            }
            zzc2.a(i2, z);
            return;
        }
        StringBuilder sb = new StringBuilder(52);
        sb.append("onRelease received for unknown instance: ");
        sb.append(i2);
        Log.wtf("GoogleApiManager", sb.toString(), new Exception());
    }

    @WorkerThread
    private void a(com.google.android.gms.common.api.zzd<?> zzd2, int i2) {
        zzpz zzapx = zzd2.zzapx();
        if (!this.k.containsKey(zzapx)) {
            this.k.put(zzapx, new zzc(zzd2));
        }
        zzc zzc2 = (zzc) this.k.get(zzapx);
        zzc2.a(i2);
        this.j.put(i2, zzc2);
        zzc2.j();
        this.p.put(i2, new zza(zzd2, i2, this.o));
        if (this.q == null || !this.q.c.get()) {
            this.q = new zzb(this.o, this.p);
            this.q.start();
        }
    }

    @WorkerThread
    private void a(zzpy zzpy) {
        ((zzc) this.j.get(zzpy.wf)).a(zzpy);
    }

    @WorkerThread
    private void b() {
        for (zzc zzc2 : this.k.values()) {
            zzc2.b();
            zzc2.j();
        }
    }

    static /* synthetic */ zzqi d(zzqt zzqt) {
        return null;
    }

    public static Pair<zzqt, Integer> zza(Context context, com.google.android.gms.common.api.zzd<?> zzd2) {
        Pair<zzqt, Integer> create;
        synchronized (d) {
            if (e == null) {
                e = new zzqt(context.getApplicationContext());
            }
            create = Pair.create(e, Integer.valueOf(e.a(zzd2)));
        }
        return create;
    }

    public static zzqt zzasa() {
        zzqt zzqt;
        synchronized (d) {
            zzqt = e;
        }
        return zzqt;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(ConnectionResult connectionResult, int i2) {
        if (!connectionResult.hasResolution() && !this.g.isUserResolvableError(connectionResult.getErrorCode())) {
            return false;
        }
        this.g.zza(this.f, connectionResult, i2);
        return true;
    }

    @WorkerThread
    public boolean handleMessage(Message message) {
        boolean z = false;
        switch (message.what) {
            case 1:
                zza((zzqb) message.obj);
                break;
            case 2:
                a(message.arg1);
                return true;
            case 3:
                b();
                return true;
            case 4:
                a((zzpy) message.obj);
                return true;
            case 5:
                if (this.j.get(message.arg1) != null) {
                    ((zzc) this.j.get(message.arg1)).a(new Status(17, "Error resolution was canceled by the user."));
                    return true;
                }
                break;
            case 6:
                a((com.google.android.gms.common.api.zzd) message.obj, message.arg1);
                return true;
            case 7:
                Pair pair = (Pair) message.obj;
                a(message.arg1, (zzri) pair.first, (TaskCompletionSource) pair.second);
                return true;
            case 8:
                int i2 = message.arg1;
                if (message.arg2 == 1) {
                    z = true;
                }
                a(i2, z);
                return true;
            case 9:
                if (this.k.containsKey(message.obj)) {
                    ((zzc) this.k.get(message.obj)).e();
                    return true;
                }
                break;
            case 10:
                if (this.k.containsKey(message.obj)) {
                    ((zzc) this.k.get(message.obj)).g();
                    return true;
                }
                break;
            case 11:
                if (this.k.containsKey(message.obj)) {
                    ((zzc) this.k.get(message.obj)).i();
                    return true;
                }
                break;
            case 12:
                Pair pair2 = (Pair) message.obj;
                a(message.arg1, (com.google.android.gms.internal.zzrd.zzb) pair2.first, (TaskCompletionSource) pair2.second);
                return true;
            default:
                int i3 = message.what;
                StringBuilder sb = new StringBuilder(31);
                sb.append("Unknown message id: ");
                sb.append(i3);
                Log.w("GoogleApiManager", sb.toString());
                return false;
        }
        return true;
    }

    public void zza(ConnectionResult connectionResult, int i2) {
        if (!a(connectionResult, i2)) {
            this.n.sendMessage(this.n.obtainMessage(5, i2, 0));
        }
    }

    public <O extends ApiOptions> void zza(com.google.android.gms.common.api.zzd<O> zzd2, int i2, com.google.android.gms.internal.zzqc.zza<? extends Result, com.google.android.gms.common.api.Api.zzb> zza2) {
        this.n.sendMessage(this.n.obtainMessage(4, new com.google.android.gms.internal.zzpy.zzb(zzd2.getInstanceId(), i2, zza2)));
    }

    public <O extends ApiOptions, TResult> void zza(com.google.android.gms.common.api.zzd<O> zzd2, int i2, zzro<com.google.android.gms.common.api.Api.zzb, TResult> zzro, TaskCompletionSource<TResult> taskCompletionSource) {
        this.n.sendMessage(this.n.obtainMessage(4, new com.google.android.gms.internal.zzpy.zzd(zzd2.getInstanceId(), i2, zzro, taskCompletionSource)));
    }

    @WorkerThread
    public void zza(zzqb zzqb) {
        ConnectionResult c2;
        for (zzpz zzpz : zzqb.zzaqm()) {
            zzc zzc2 = (zzc) this.k.get(zzpz);
            if (zzc2 == null) {
                zzqb.cancel();
                return;
            }
            if (zzc2.d()) {
                c2 = ConnectionResult.uJ;
            } else if (zzc2.c() != null) {
                c2 = zzc2.c();
            } else {
                zzc2.a(zzqb);
            }
            zzqb.zza(zzpz, c2);
        }
    }

    public void zza(zzqi zzqi) {
        synchronized (d) {
            if (zzqi == null) {
                try {
                    this.l = null;
                    this.m.clear();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void zzaqk() {
        this.n.sendMessage(this.n.obtainMessage(3));
    }

    public void zzd(int i2, boolean z) {
        this.n.sendMessage(this.n.obtainMessage(8, i2, z ? 1 : 2));
    }
}
