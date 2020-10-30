package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzs;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class zzqe<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> a = new ThreadLocal<Boolean>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Boolean initialValue() {
            return Boolean.valueOf(false);
        }
    };
    private final Object b;
    private final CountDownLatch c;
    private final ArrayList<com.google.android.gms.common.api.PendingResult.zza> d;
    private ResultCallback<? super R> e;
    private final AtomicReference<zzb> f;
    /* access modifiers changed from: private */
    public R g;
    private zzb h;
    private volatile boolean i;
    private boolean j;
    private boolean k;
    private zzs l;
    private volatile zzrp<R> m;
    private boolean n;
    protected final zza<R> wH;
    protected final WeakReference<GoogleApiClient> wI;

    public static class zza<R extends Result> extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    zzb((ResultCallback) pair.first, (Result) pair.second);
                    return;
                case 2:
                    ((zzqe) message.obj).zzaa(Status.wb);
                    return;
                default:
                    int i = message.what;
                    StringBuilder sb = new StringBuilder(45);
                    sb.append("Don't know how to handle message: ");
                    sb.append(i);
                    Log.wtf("BasePendingResult", sb.toString(), new Exception());
                    return;
            }
        }

        public void zza(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public void zza(zzqe<R> zzqe, long j) {
            sendMessageDelayed(obtainMessage(2, zzqe), j);
        }

        public void zzaqw() {
            removeMessages(2);
        }

        /* access modifiers changed from: protected */
        public void zzb(ResultCallback<? super R> resultCallback, R r) {
            try {
                resultCallback.onResult(r);
            } catch (RuntimeException e) {
                zzqe.zze(r);
                throw e;
            }
        }
    }

    final class zzb {
        private zzb() {
        }

        /* access modifiers changed from: protected */
        public void finalize() {
            zzqe.zze(zzqe.this.g);
            super.finalize();
        }
    }

    @Deprecated
    zzqe() {
        this.b = new Object();
        this.c = new CountDownLatch(1);
        this.d = new ArrayList<>();
        this.f = new AtomicReference<>();
        this.n = false;
        this.wH = new zza<>(Looper.getMainLooper());
        this.wI = new WeakReference<>(null);
    }

    @Deprecated
    protected zzqe(Looper looper) {
        this.b = new Object();
        this.c = new CountDownLatch(1);
        this.d = new ArrayList<>();
        this.f = new AtomicReference<>();
        this.n = false;
        this.wH = new zza<>(looper);
        this.wI = new WeakReference<>(null);
    }

    protected zzqe(GoogleApiClient googleApiClient) {
        this.b = new Object();
        this.c = new CountDownLatch(1);
        this.d = new ArrayList<>();
        this.f = new AtomicReference<>();
        this.n = false;
        this.wH = new zza<>(googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
        this.wI = new WeakReference<>(googleApiClient);
    }

    private void a(R r) {
        this.g = r;
        this.l = null;
        this.c.countDown();
        Status status = this.g.getStatus();
        if (this.j) {
            this.e = null;
        } else if (this.e != null) {
            this.wH.zzaqw();
            this.wH.zza(this.e, c());
        } else if (this.g instanceof Releasable) {
            this.h = new zzb();
        }
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            ((com.google.android.gms.common.api.PendingResult.zza) it.next()).zzv(status);
        }
        this.d.clear();
    }

    private void b() {
        zzb zzb2 = (zzb) this.f.getAndSet(null);
        if (zzb2 != null) {
            zzb2.a(this);
        }
    }

    private R c() {
        R r;
        synchronized (this.b) {
            zzac.zza(!this.i, (Object) "Result has already been consumed.");
            zzac.zza(isReady(), (Object) "Result is not ready.");
            r = this.g;
            this.g = null;
            this.e = null;
            this.i = true;
        }
        b();
        return r;
    }

    public static void zze(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e2) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("BasePendingResult", sb.toString(), e2);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    public final R await() {
        boolean z = false;
        zzac.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        zzac.zza(!this.i, (Object) "Result has already been consumed");
        if (this.m == null) {
            z = true;
        }
        zzac.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.c.await();
        } catch (InterruptedException unused) {
            zzaa(Status.vZ);
        }
        zzac.zza(isReady(), (Object) "Result is not ready.");
        return c();
    }

    public final R await(long j2, TimeUnit timeUnit) {
        boolean z = false;
        zzac.zza(j2 <= 0 || Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread when time is greater than zero.");
        zzac.zza(!this.i, (Object) "Result has already been consumed.");
        if (this.m == null) {
            z = true;
        }
        zzac.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.c.await(j2, timeUnit)) {
                zzaa(Status.wb);
            }
        } catch (InterruptedException unused) {
            zzaa(Status.vZ);
        }
        zzac.zza(isReady(), (Object) "Result is not ready.");
        return c();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:8|(2:10|11)|12|13|14|15) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0015 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.b
            monitor-enter(r0)
            boolean r1 = r2.j     // Catch:{ all -> 0x002a }
            if (r1 != 0) goto L_0x0028
            boolean r1 = r2.i     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x000c
            goto L_0x0028
        L_0x000c:
            com.google.android.gms.common.internal.zzs r1 = r2.l     // Catch:{ all -> 0x002a }
            if (r1 == 0) goto L_0x0015
            com.google.android.gms.common.internal.zzs r1 = r2.l     // Catch:{ RemoteException -> 0x0015 }
            r1.cancel()     // Catch:{ RemoteException -> 0x0015 }
        L_0x0015:
            R r1 = r2.g     // Catch:{ all -> 0x002a }
            zze(r1)     // Catch:{ all -> 0x002a }
            r1 = 1
            r2.j = r1     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.Status r1 = com.google.android.gms.common.api.Status.wc     // Catch:{ all -> 0x002a }
            com.google.android.gms.common.api.Result r1 = r2.zzc(r1)     // Catch:{ all -> 0x002a }
            r2.a((R) r1)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0028:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqe.cancel():void");
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.b) {
            z = this.j;
        }
        return z;
    }

    public final boolean isReady() {
        return this.c.getCount() == 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.b
            monitor-enter(r0)
            if (r5 != 0) goto L_0x000c
            r5 = 0
            r4.e = r5     // Catch:{ all -> 0x000a }
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x000a:
            r5 = move-exception
            goto L_0x003c
        L_0x000c:
            boolean r1 = r4.i     // Catch:{ all -> 0x000a }
            r2 = 1
            r1 = r1 ^ r2
            java.lang.String r3 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzac.zza(r1, r3)     // Catch:{ all -> 0x000a }
            com.google.android.gms.internal.zzrp<R> r1 = r4.m     // Catch:{ all -> 0x000a }
            if (r1 != 0) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            r2 = 0
        L_0x001b:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzac.zza(r2, r1)     // Catch:{ all -> 0x000a }
            boolean r1 = r4.isCanceled()     // Catch:{ all -> 0x000a }
            if (r1 == 0) goto L_0x0028
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x0028:
            boolean r1 = r4.isReady()     // Catch:{ all -> 0x000a }
            if (r1 == 0) goto L_0x0038
            com.google.android.gms.internal.zzqe$zza<R> r1 = r4.wH     // Catch:{ all -> 0x000a }
            com.google.android.gms.common.api.Result r2 = r4.c()     // Catch:{ all -> 0x000a }
            r1.zza(r5, r2)     // Catch:{ all -> 0x000a }
            goto L_0x003a
        L_0x0038:
            r4.e = r5     // Catch:{ all -> 0x000a }
        L_0x003a:
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x003c:
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqe.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0044, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r5, long r6, java.util.concurrent.TimeUnit r8) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.b
            monitor-enter(r0)
            if (r5 != 0) goto L_0x000c
            r5 = 0
            r4.e = r5     // Catch:{ all -> 0x000a }
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x000a:
            r5 = move-exception
            goto L_0x0045
        L_0x000c:
            boolean r1 = r4.i     // Catch:{ all -> 0x000a }
            r2 = 1
            r1 = r1 ^ r2
            java.lang.String r3 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzac.zza(r1, r3)     // Catch:{ all -> 0x000a }
            com.google.android.gms.internal.zzrp<R> r1 = r4.m     // Catch:{ all -> 0x000a }
            if (r1 != 0) goto L_0x001a
            goto L_0x001b
        L_0x001a:
            r2 = 0
        L_0x001b:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzac.zza(r2, r1)     // Catch:{ all -> 0x000a }
            boolean r1 = r4.isCanceled()     // Catch:{ all -> 0x000a }
            if (r1 == 0) goto L_0x0028
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x0028:
            boolean r1 = r4.isReady()     // Catch:{ all -> 0x000a }
            if (r1 == 0) goto L_0x0038
            com.google.android.gms.internal.zzqe$zza<R> r6 = r4.wH     // Catch:{ all -> 0x000a }
            com.google.android.gms.common.api.Result r7 = r4.c()     // Catch:{ all -> 0x000a }
            r6.zza(r5, r7)     // Catch:{ all -> 0x000a }
            goto L_0x0043
        L_0x0038:
            r4.e = r5     // Catch:{ all -> 0x000a }
            com.google.android.gms.internal.zzqe$zza<R> r5 = r4.wH     // Catch:{ all -> 0x000a }
            long r6 = r8.toMillis(r6)     // Catch:{ all -> 0x000a }
            r5.zza(r4, r6)     // Catch:{ all -> 0x000a }
        L_0x0043:
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x0045:
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzqe.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        zzac.zza(!this.i, (Object) "Result has already been consumed.");
        synchronized (this.b) {
            boolean z = false;
            zzac.zza(this.m == null, (Object) "Cannot call then() twice.");
            if (this.e == null) {
                z = true;
            }
            zzac.zza(z, (Object) "Cannot call then() if callbacks are set.");
            this.n = true;
            this.m = new zzrp<>(this.wI);
            then = this.m.then(resultTransform);
            if (isReady()) {
                this.wH.zza((ResultCallback<? super R>) this.m, c());
            } else {
                this.e = this.m;
            }
        }
        return then;
    }

    public final void zza(com.google.android.gms.common.api.PendingResult.zza zza2) {
        boolean z = true;
        zzac.zza(!this.i, (Object) "Result has already been consumed.");
        if (zza2 == null) {
            z = false;
        }
        zzac.zzb(z, (Object) "Callback cannot be null.");
        synchronized (this.b) {
            if (isReady()) {
                zza2.zzv(this.g.getStatus());
            } else {
                this.d.add(zza2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(zzs zzs) {
        synchronized (this.b) {
            this.l = zzs;
        }
    }

    public void zza(zzb zzb2) {
        this.f.set(zzb2);
    }

    public final void zzaa(Status status) {
        synchronized (this.b) {
            if (!isReady()) {
                zzc((R) zzc(status));
                this.k = true;
            }
        }
    }

    public Integer zzaqf() {
        return null;
    }

    public boolean zzaqq() {
        boolean isCanceled;
        synchronized (this.b) {
            if (((GoogleApiClient) this.wI.get()) == null || !this.n) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public void zzaqs() {
        setResultCallback(null);
    }

    public void zzaqt() {
        this.n = this.n || ((Boolean) a.get()).booleanValue();
    }

    public abstract R zzc(Status status);

    public final void zzc(R r) {
        synchronized (this.b) {
            if (!this.k && !this.j) {
                if (!isReady() || !a()) {
                    zzac.zza(!isReady(), (Object) "Results have already been set");
                    zzac.zza(!this.i, (Object) "Result has already been consumed");
                    a(r);
                    return;
                }
            }
            zze(r);
        }
    }
}
