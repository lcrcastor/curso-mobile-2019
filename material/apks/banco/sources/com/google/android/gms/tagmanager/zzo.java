package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;

class zzo implements ContainerHolder {
    private final Looper a;
    private Container b;
    private Container c;
    private Status d;
    private zzb e;
    private zza f;
    private boolean g;
    private TagManager h;

    public interface zza {
        String zzcdy();

        void zzcea();

        void zzoy(String str);
    }

    class zzb extends Handler {
        private final ContainerAvailableListener b;

        public zzb(ContainerAvailableListener containerAvailableListener, Looper looper) {
            super(looper);
            this.b = containerAvailableListener;
        }

        public void a(String str) {
            sendMessage(obtainMessage(1, str));
        }

        /* access modifiers changed from: protected */
        public void b(String str) {
            this.b.onContainerAvailable(zzo.this, str);
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                zzbo.e("Don't know how to handle this message.");
            } else {
                b((String) message.obj);
            }
        }
    }

    public zzo(Status status) {
        this.d = status;
        this.a = null;
    }

    public zzo(TagManager tagManager, Looper looper, Container container, zza zza2) {
        this.h = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.a = looper;
        this.b = container;
        this.f = zza2;
        this.d = Status.vY;
        tagManager.zza(this);
    }

    private void c() {
        if (this.e != null) {
            this.e.a(this.c.zzcdw());
        }
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        if (!this.g) {
            return this.b.getContainerId();
        }
        zzbo.e("getContainerId called on a released ContainerHolder.");
        return "";
    }

    public synchronized void a(Container container) {
        if (!this.g) {
            if (container == null) {
                zzbo.e("Unexpected null container.");
                return;
            }
            this.c = container;
            c();
        }
    }

    public synchronized void a(String str) {
        if (!this.g) {
            this.b.zzow(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        if (!this.g) {
            return this.f.zzcdy();
        }
        zzbo.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        if (this.g) {
            zzbo.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.f.zzoy(str);
        }
    }

    public synchronized Container getContainer() {
        if (this.g) {
            zzbo.e("ContainerHolder is released.");
            return null;
        }
        if (this.c != null) {
            this.b = this.c;
            this.c = null;
        }
        return this.b;
    }

    public Status getStatus() {
        return this.d;
    }

    public synchronized void refresh() {
        if (this.g) {
            zzbo.e("Refreshing a released ContainerHolder.");
        } else {
            this.f.zzcea();
        }
    }

    public synchronized void release() {
        if (this.g) {
            zzbo.e("Releasing a released ContainerHolder.");
            return;
        }
        this.g = true;
        this.h.zzb(this);
        this.b.a();
        this.b = null;
        this.c = null;
        this.f = null;
        this.e = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.g     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x000c
            java.lang.String r3 = "ContainerHolder is released."
            com.google.android.gms.tagmanager.zzbo.e(r3)     // Catch:{ all -> 0x0024 }
            monitor-exit(r2)
            return
        L_0x000c:
            if (r3 != 0) goto L_0x0012
            r3 = 0
            r2.e = r3     // Catch:{ all -> 0x0024 }
            goto L_0x0022
        L_0x0012:
            com.google.android.gms.tagmanager.zzo$zzb r0 = new com.google.android.gms.tagmanager.zzo$zzb     // Catch:{ all -> 0x0024 }
            android.os.Looper r1 = r2.a     // Catch:{ all -> 0x0024 }
            r0.<init>(r3, r1)     // Catch:{ all -> 0x0024 }
            r2.e = r0     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.Container r3 = r2.c     // Catch:{ all -> 0x0024 }
            if (r3 == 0) goto L_0x0022
            r2.c()     // Catch:{ all -> 0x0024 }
        L_0x0022:
            monitor-exit(r2)
            return
        L_0x0024:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzo.setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder$ContainerAvailableListener):void");
    }
}
