package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzm implements Callback {
    final ArrayList<ConnectionCallbacks> a = new ArrayList<>();
    private final zza b;
    private final ArrayList<ConnectionCallbacks> c = new ArrayList<>();
    private final ArrayList<OnConnectionFailedListener> d = new ArrayList<>();
    private volatile boolean e = false;
    private final AtomicInteger f = new AtomicInteger(0);
    private boolean g = false;
    private final Handler h;
    private final Object i = new Object();

    public interface zza {
        boolean isConnected();

        Bundle zzaoe();
    }

    public zzm(Looper looper, zza zza2) {
        this.b = zza2;
        this.h = new Handler(looper, this);
    }

    public boolean handleMessage(Message message) {
        if (message.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) message.obj;
            synchronized (this.i) {
                if (this.e && this.b.isConnected() && this.c.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.b.zzaoe());
                }
            }
            return true;
        }
        int i2 = message.what;
        StringBuilder sb = new StringBuilder(45);
        sb.append("Don't know how to handle message: ");
        sb.append(i2);
        Log.wtf("GmsClientEvents", sb.toString(), new Exception());
        return false;
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzac.zzy(connectionCallbacks);
        synchronized (this.i) {
            contains = this.c.contains(connectionCallbacks);
        }
        return contains;
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzac.zzy(onConnectionFailedListener);
        synchronized (this.i) {
            contains = this.d.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzac.zzy(connectionCallbacks);
        synchronized (this.i) {
            if (this.c.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 62);
                sb.append("registerConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.c.add(connectionCallbacks);
            }
        }
        if (this.b.isConnected()) {
            this.h.sendMessage(this.h.obtainMessage(1, connectionCallbacks));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzac.zzy(onConnectionFailedListener);
        synchronized (this.i) {
            if (this.d.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 67);
                sb.append("registerConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.d.add(onConnectionFailedListener);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        zzac.zzy(connectionCallbacks);
        synchronized (this.i) {
            if (!this.c.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 52);
                sb.append("unregisterConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            } else if (this.g) {
                this.a.add(connectionCallbacks);
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
        zzac.zzy(onConnectionFailedListener);
        synchronized (this.i) {
            if (!this.d.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57);
                sb.append("unregisterConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            }
        }
    }

    public void zzaut() {
        this.e = false;
        this.f.incrementAndGet();
    }

    public void zzauu() {
        this.e = true;
    }

    public void zzgo(int i2) {
        zzac.zza(Looper.myLooper() == this.h.getLooper(), (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        this.h.removeMessages(1);
        synchronized (this.i) {
            this.g = true;
            ArrayList arrayList = new ArrayList(this.c);
            int i3 = this.f.get();
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) it.next();
                if (!this.e) {
                    break;
                } else if (this.f.get() != i3) {
                    break;
                } else if (this.c.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i2);
                }
            }
            this.a.clear();
            this.g = false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0054, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzn(com.google.android.gms.common.ConnectionResult r6) {
        /*
            r5 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Handler r1 = r5.h
            android.os.Looper r1 = r1.getLooper()
            r2 = 1
            if (r0 != r1) goto L_0x000f
            r0 = 1
            goto L_0x0010
        L_0x000f:
            r0 = 0
        L_0x0010:
            java.lang.String r1 = "onConnectionFailure must only be called on the Handler thread"
            com.google.android.gms.common.internal.zzac.zza(r0, r1)
            android.os.Handler r0 = r5.h
            r0.removeMessages(r2)
            java.lang.Object r0 = r5.i
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0057 }
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r2 = r5.d     // Catch:{ all -> 0x0057 }
            r1.<init>(r2)     // Catch:{ all -> 0x0057 }
            java.util.concurrent.atomic.AtomicInteger r2 = r5.f     // Catch:{ all -> 0x0057 }
            int r2 = r2.get()     // Catch:{ all -> 0x0057 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0057 }
        L_0x002e:
            boolean r3 = r1.hasNext()     // Catch:{ all -> 0x0057 }
            if (r3 == 0) goto L_0x0055
            java.lang.Object r3 = r1.next()     // Catch:{ all -> 0x0057 }
            com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener r3 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r3     // Catch:{ all -> 0x0057 }
            boolean r4 = r5.e     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0053
            java.util.concurrent.atomic.AtomicInteger r4 = r5.f     // Catch:{ all -> 0x0057 }
            int r4 = r4.get()     // Catch:{ all -> 0x0057 }
            if (r4 == r2) goto L_0x0047
            goto L_0x0053
        L_0x0047:
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r4 = r5.d     // Catch:{ all -> 0x0057 }
            boolean r4 = r4.contains(r3)     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x002e
            r3.onConnectionFailed(r6)     // Catch:{ all -> 0x0057 }
            goto L_0x002e
        L_0x0053:
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            return
        L_0x0055:
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            return
        L_0x0057:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0057 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzm.zzn(com.google.android.gms.common.ConnectionResult):void");
    }

    public void zzp(Bundle bundle) {
        boolean z = true;
        zzac.zza(Looper.myLooper() == this.h.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.i) {
            zzac.zzbr(!this.g);
            this.h.removeMessages(1);
            this.g = true;
            if (this.a.size() != 0) {
                z = false;
            }
            zzac.zzbr(z);
            ArrayList arrayList = new ArrayList(this.c);
            int i2 = this.f.get();
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) it.next();
                if (!this.e || !this.b.isConnected()) {
                    break;
                } else if (this.f.get() != i2) {
                    break;
                } else if (!this.a.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.a.clear();
            this.g = false;
        }
    }
}
