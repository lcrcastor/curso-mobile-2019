package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

final class zzo extends zzn implements Callback {
    /* access modifiers changed from: private */
    public final HashMap<zza, zzb> a = new HashMap<>();
    /* access modifiers changed from: private */
    public final Context b;
    private final Handler c;
    /* access modifiers changed from: private */
    public final com.google.android.gms.common.stats.zzb d;
    private final long e;

    static final class zza {
        private final String a;
        private final String b;
        private final ComponentName c;

        public zza(ComponentName componentName) {
            this.a = null;
            this.b = null;
            this.c = (ComponentName) zzac.zzy(componentName);
        }

        public zza(String str, String str2) {
            this.a = zzac.zzhz(str);
            this.b = zzac.zzhz(str2);
            this.c = null;
        }

        public Intent a() {
            return this.a != null ? new Intent(this.a).setPackage(this.b) : new Intent().setComponent(this.c);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            return zzab.equal(this.a, zza.a) && zzab.equal(this.c, zza.c);
        }

        public int hashCode() {
            return zzab.hashCode(this.a, this.c);
        }

        public String toString() {
            return this.a == null ? this.c.flattenToString() : this.a;
        }
    }

    final class zzb {
        private final zza b = new zza();
        /* access modifiers changed from: private */
        public final Set<ServiceConnection> c = new HashSet();
        /* access modifiers changed from: private */
        public int d = 2;
        private boolean e;
        /* access modifiers changed from: private */
        public IBinder f;
        /* access modifiers changed from: private */
        public final zza g;
        /* access modifiers changed from: private */
        public ComponentName h;

        public class zza implements ServiceConnection {
            public zza() {
            }

            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                synchronized (zzo.this.a) {
                    zzb.this.f = iBinder;
                    zzb.this.h = componentName;
                    for (ServiceConnection onServiceConnected : zzb.this.c) {
                        onServiceConnected.onServiceConnected(componentName, iBinder);
                    }
                    zzb.this.d = 1;
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                synchronized (zzo.this.a) {
                    zzb.this.f = null;
                    zzb.this.h = componentName;
                    for (ServiceConnection onServiceDisconnected : zzb.this.c) {
                        onServiceDisconnected.onServiceDisconnected(componentName);
                    }
                    zzb.this.d = 2;
                }
            }
        }

        public zzb(zza zza2) {
            this.g = zza2;
        }

        public void a(ServiceConnection serviceConnection, String str) {
            zzo.this.d.zza(zzo.this.b, serviceConnection, str, this.g.a());
            this.c.add(serviceConnection);
        }

        @TargetApi(14)
        public void a(String str) {
            this.d = 3;
            this.e = zzo.this.d.zza(zzo.this.b, str, this.g.a(), this.b, 129);
            if (!this.e) {
                this.d = 2;
                try {
                    zzo.this.d.zza(zzo.this.b, this.b);
                } catch (IllegalArgumentException unused) {
                }
            }
        }

        public boolean a() {
            return this.e;
        }

        public boolean a(ServiceConnection serviceConnection) {
            return this.c.contains(serviceConnection);
        }

        public int b() {
            return this.d;
        }

        public void b(ServiceConnection serviceConnection, String str) {
            zzo.this.d.zzb(zzo.this.b, serviceConnection);
            this.c.remove(serviceConnection);
        }

        public void b(String str) {
            zzo.this.d.zza(zzo.this.b, this.b);
            this.e = false;
            this.d = 2;
        }

        public boolean c() {
            return this.c.isEmpty();
        }

        public IBinder d() {
            return this.f;
        }

        public ComponentName e() {
            return this.h;
        }
    }

    zzo(Context context) {
        this.b = context.getApplicationContext();
        this.c = new Handler(context.getMainLooper(), this);
        this.d = com.google.android.gms.common.stats.zzb.zzawu();
        this.e = LocalizacionManager.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
    }

    private boolean a(zza zza2, ServiceConnection serviceConnection, String str) {
        boolean a2;
        zzac.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.a) {
            zzb zzb2 = (zzb) this.a.get(zza2);
            if (zzb2 != null) {
                this.c.removeMessages(0, zzb2);
                if (!zzb2.a(serviceConnection)) {
                    zzb2.a(serviceConnection, str);
                    switch (zzb2.b()) {
                        case 1:
                            serviceConnection.onServiceConnected(zzb2.e(), zzb2.d());
                            break;
                        case 2:
                            zzb2.a(str);
                            break;
                    }
                } else {
                    String valueOf = String.valueOf(zza2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 81);
                    sb.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                zzb2 = new zzb(zza2);
                zzb2.a(serviceConnection, str);
                zzb2.a(str);
                this.a.put(zza2, zzb2);
            }
            a2 = zzb2.a();
        }
        return a2;
    }

    private void b(zza zza2, ServiceConnection serviceConnection, String str) {
        zzac.zzb(serviceConnection, (Object) "ServiceConnection must not be null");
        synchronized (this.a) {
            zzb zzb2 = (zzb) this.a.get(zza2);
            if (zzb2 == null) {
                String valueOf = String.valueOf(zza2);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
                sb.append("Nonexistent connection status for service config: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            } else if (!zzb2.a(serviceConnection)) {
                String valueOf2 = String.valueOf(zza2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 76);
                sb2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                sb2.append(valueOf2);
                throw new IllegalStateException(sb2.toString());
            } else {
                zzb2.b(serviceConnection, str);
                if (zzb2.c()) {
                    this.c.sendMessageDelayed(this.c.obtainMessage(0, zzb2), this.e);
                }
            }
        }
    }

    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            return false;
        }
        zzb zzb2 = (zzb) message.obj;
        synchronized (this.a) {
            if (zzb2.c()) {
                if (zzb2.a()) {
                    zzb2.b("GmsClientSupervisor");
                }
                this.a.remove(zzb2.g);
            }
        }
        return true;
    }

    public boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        return a(new zza(componentName), serviceConnection, str);
    }

    public boolean zza(String str, String str2, ServiceConnection serviceConnection, String str3) {
        return a(new zza(str, str2), serviceConnection, str3);
    }

    public void zzb(ComponentName componentName, ServiceConnection serviceConnection, String str) {
        b(new zza(componentName), serviceConnection, str);
    }

    public void zzb(String str, String str2, ServiceConnection serviceConnection, String str3) {
        b(new zza(str, str2), serviceConnection, str3);
    }
}
