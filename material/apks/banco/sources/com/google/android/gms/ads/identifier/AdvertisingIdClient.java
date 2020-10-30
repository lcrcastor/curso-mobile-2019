package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.RemoteException;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.localizacion.LocalizacionManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzci;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zzaku;
    zzci zzakv;
    boolean zzakw;
    Object zzakx;
    zza zzaky;
    final long zzakz;

    public static final class Info {
        private final String zzale;
        private final boolean zzalf;

        public Info(String str, boolean z) {
            this.zzale = str;
            this.zzalf = z;
        }

        public String getId() {
            return this.zzale;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzalf;
        }

        public String toString() {
            String str = this.zzale;
            boolean z = this.zzalf;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 7);
            sb.append("{");
            sb.append(str);
            sb.append("}");
            sb.append(z);
            return sb.toString();
        }
    }

    static class zza extends Thread {
        CountDownLatch a = new CountDownLatch(1);
        boolean b = false;
        private WeakReference<AdvertisingIdClient> c;
        private long d;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.c = new WeakReference<>(advertisingIdClient);
            this.d = j;
            start();
        }

        private void c() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.c.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.b = true;
            }
        }

        public void a() {
            this.a.countDown();
        }

        public boolean b() {
            return this.b;
        }

        public void run() {
            try {
                if (!this.a.await(this.d, TimeUnit.MILLISECONDS)) {
                    c();
                }
            } catch (InterruptedException unused) {
                c();
            }
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000);
    }

    public AdvertisingIdClient(Context context, long j) {
        this.zzakx = new Object();
        zzac.zzy(context);
        this.mContext = context;
        this.zzakw = false;
        this.zzakz = j;
    }

    public static Info getAdvertisingIdInfo(Context context) {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1);
        try {
            advertisingIdClient.zze(false);
            return advertisingIdClient.getInfo();
        } finally {
            advertisingIdClient.finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean z) {
    }

    static zzci zza(Context context, com.google.android.gms.common.zza zza2) {
        try {
            return com.google.android.gms.internal.zzci.zza.zzf(zza2.zza(LocalizacionManager.UPDATE_INTERVAL_IN_MILLISECONDS, TimeUnit.MILLISECONDS));
        } catch (InterruptedException unused) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:2|3|(3:5|6|7)|8|9|(1:11)|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0011 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzdn() {
        /*
            r6 = this;
            java.lang.Object r0 = r6.zzakx
            monitor-enter(r0)
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzaky     // Catch:{ all -> 0x0024 }
            if (r1 == 0) goto L_0x0011
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzaky     // Catch:{ all -> 0x0024 }
            r1.a()     // Catch:{ all -> 0x0024 }
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = r6.zzaky     // Catch:{ InterruptedException -> 0x0011 }
            r1.join()     // Catch:{ InterruptedException -> 0x0011 }
        L_0x0011:
            long r1 = r6.zzakz     // Catch:{ all -> 0x0024 }
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 <= 0) goto L_0x0022
            com.google.android.gms.ads.identifier.AdvertisingIdClient$zza r1 = new com.google.android.gms.ads.identifier.AdvertisingIdClient$zza     // Catch:{ all -> 0x0024 }
            long r2 = r6.zzakz     // Catch:{ all -> 0x0024 }
            r1.<init>(r6, r2)     // Catch:{ all -> 0x0024 }
            r6.zzaky = r1     // Catch:{ all -> 0x0024 }
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.zzdn():void");
    }

    static com.google.android.gms.common.zza zzg(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            int isGooglePlayServicesAvailable = zzc.zzapd().isGooglePlayServicesAvailable(context);
            if (isGooglePlayServicesAvailable == 0 || isGooglePlayServicesAvailable == 2) {
                com.google.android.gms.common.zza zza2 = new com.google.android.gms.common.zza();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage("com.google.android.gms");
                try {
                    if (zzb.zzawu().zza(context, intent, (ServiceConnection) zza2, 1)) {
                        return zza2;
                    }
                    throw new IOException("Connection failure");
                } catch (Throwable th) {
                    throw new IOException(th);
                }
            } else {
                throw new IOException("Google Play services not available");
            }
        } catch (NameNotFoundException unused) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        finish();
        super.finalize();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0032, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finish() {
        /*
            r3 = this;
            java.lang.String r0 = "Calling this from your main thread can lead to deadlock"
            com.google.android.gms.common.internal.zzac.zzhr(r0)
            monitor-enter(r3)
            android.content.Context r0 = r3.mContext     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x0031
            com.google.android.gms.common.zza r0 = r3.zzaku     // Catch:{ all -> 0x0033 }
            if (r0 != 0) goto L_0x000f
            goto L_0x0031
        L_0x000f:
            boolean r0 = r3.zzakw     // Catch:{ IllegalArgumentException -> 0x001f }
            if (r0 == 0) goto L_0x0027
            com.google.android.gms.common.stats.zzb r0 = com.google.android.gms.common.stats.zzb.zzawu()     // Catch:{ IllegalArgumentException -> 0x001f }
            android.content.Context r1 = r3.mContext     // Catch:{ IllegalArgumentException -> 0x001f }
            com.google.android.gms.common.zza r2 = r3.zzaku     // Catch:{ IllegalArgumentException -> 0x001f }
            r0.zza(r1, r2)     // Catch:{ IllegalArgumentException -> 0x001f }
            goto L_0x0027
        L_0x001f:
            r0 = move-exception
            java.lang.String r1 = "AdvertisingIdClient"
            java.lang.String r2 = "AdvertisingIdClient unbindService failed."
            android.util.Log.i(r1, r2, r0)     // Catch:{ all -> 0x0033 }
        L_0x0027:
            r0 = 0
            r3.zzakw = r0     // Catch:{ all -> 0x0033 }
            r0 = 0
            r3.zzakv = r0     // Catch:{ all -> 0x0033 }
            r3.zzaku = r0     // Catch:{ all -> 0x0033 }
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            return
        L_0x0031:
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            return
        L_0x0033:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.identifier.AdvertisingIdClient.finish():void");
    }

    public Info getInfo() {
        Info info;
        zzac.zzhr("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zzakw) {
                synchronized (this.zzakx) {
                    if (this.zzaky != null) {
                        if (!this.zzaky.b()) {
                        }
                    }
                    throw new IOException("AdvertisingIdClient is not connected.");
                }
                try {
                    zze(false);
                    if (!this.zzakw) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (RemoteException e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzac.zzy(this.zzaku);
            zzac.zzy(this.zzakv);
            info = new Info(this.zzakv.getId(), this.zzakv.zzf(true));
        }
        zzdn();
        return info;
    }

    public void start() {
        zze(true);
    }

    /* access modifiers changed from: protected */
    public void zze(boolean z) {
        zzac.zzhr("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzakw) {
                finish();
            }
            this.zzaku = zzg(this.mContext);
            this.zzakv = zza(this.mContext, this.zzaku);
            this.zzakw = true;
            if (z) {
                zzdn();
            }
        }
    }
}
