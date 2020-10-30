package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

class zzdc extends zzdb {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    private static zzdc n;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public zzaw c;
    private volatile zzau d;
    /* access modifiers changed from: private */
    public int e = 1800000;
    private boolean f = true;
    private boolean g = false;
    /* access modifiers changed from: private */
    public boolean h = true;
    private boolean i = true;
    private zzax j = new zzax() {
        public void a(boolean z) {
            zzdc.this.a(z, zzdc.this.h);
        }
    };
    private zza k;
    private zzbt l;
    private boolean m = false;

    public interface zza {
        void cancel();

        void zzcgx();

        void zzx(long j);
    }

    class zzb implements zza {
        private Handler b;

        private zzb() {
            this.b = new Handler(zzdc.this.b.getMainLooper(), new Callback() {
                public boolean handleMessage(Message message) {
                    if (1 == message.what && zzdc.a.equals(message.obj)) {
                        zzdc.this.dispatch();
                        if (!zzdc.this.f()) {
                            zzb.this.zzx((long) zzdc.this.e);
                        }
                    }
                    return true;
                }
            });
        }

        private Message a() {
            return this.b.obtainMessage(1, zzdc.a);
        }

        public void cancel() {
            this.b.removeMessages(1, zzdc.a);
        }

        public void zzcgx() {
            this.b.removeMessages(1, zzdc.a);
            this.b.sendMessage(a());
        }

        public void zzx(long j) {
            this.b.removeMessages(1, zzdc.a);
            this.b.sendMessageDelayed(a(), j);
        }
    }

    private zzdc() {
    }

    public static zzdc a() {
        if (n == null) {
            n = new zzdc();
        }
        return n;
    }

    private void d() {
        this.l = new zzbt(this);
        this.l.a(this.b);
    }

    private void e() {
        this.k = new zzb();
        if (this.e > 0) {
            this.k.zzx((long) this.e);
        }
    }

    /* access modifiers changed from: private */
    public boolean f() {
        return this.m || !this.h || this.e <= 0;
    }

    private void g() {
        String str;
        if (f()) {
            this.k.cancel();
            str = "PowerSaveMode initiated.";
        } else {
            this.k.zzx((long) this.e);
            str = "PowerSaveMode terminated.";
        }
        zzbo.v(str);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0014, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r2, com.google.android.gms.tagmanager.zzau r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.content.Context r0 = r1.b     // Catch:{ all -> 0x0015 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r1)
            return
        L_0x0007:
            android.content.Context r2 = r2.getApplicationContext()     // Catch:{ all -> 0x0015 }
            r1.b = r2     // Catch:{ all -> 0x0015 }
            com.google.android.gms.tagmanager.zzau r2 = r1.d     // Catch:{ all -> 0x0015 }
            if (r2 != 0) goto L_0x0013
            r1.d = r3     // Catch:{ all -> 0x0015 }
        L_0x0013:
            monitor-exit(r1)
            return
        L_0x0015:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzdc.a(android.content.Context, com.google.android.gms.tagmanager.zzau):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(boolean z, boolean z2) {
        boolean f2 = f();
        this.m = z;
        this.h = z2;
        if (f() != f2) {
            g();
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized zzaw b() {
        if (this.c == null) {
            if (this.b == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.c = new zzcg(this.j, this.b);
        }
        if (this.k == null) {
            e();
        }
        this.g = true;
        if (this.f) {
            dispatch();
            this.f = false;
        }
        if (this.l == null && this.i) {
            d();
        }
        return this.c;
    }

    public synchronized void dispatch() {
        if (!this.g) {
            zzbo.v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.f = true;
            return;
        }
        this.d.a((Runnable) new Runnable() {
            public void run() {
                zzdc.this.c.a();
            }
        });
    }

    public synchronized void zzaaj() {
        if (!f()) {
            this.k.zzcgx();
        }
    }

    public synchronized void zzcm(boolean z) {
        a(this.m, z);
    }
}
