package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.zzafk;
import com.google.android.gms.internal.zzah.zzj;

class zzct implements Runnable {
    private final Context a;
    private final zzafk b;
    private final String c;
    private final String d;
    private zzbn<zzj> e;
    private volatile zzt f;
    private volatile String g;
    private volatile String h;

    zzct(Context context, String str, zzafk zzafk, zzt zzt) {
        this.a = context;
        this.b = zzafk;
        this.c = str;
        this.f = zzt;
        String valueOf = String.valueOf("/r?id=");
        String valueOf2 = String.valueOf(str);
        this.d = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.g = this.d;
        this.h = null;
    }

    public zzct(Context context, String str, zzt zzt) {
        this(context, str, new zzafk(), zzt);
    }

    private boolean b() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzbo.v("...no network connectivity");
        return false;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x0133 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r7 = this;
            boolean r0 = r7.b()
            if (r0 != 0) goto L_0x000e
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r0 = r7.e
            com.google.android.gms.tagmanager.zzbn$zza r1 = com.google.android.gms.tagmanager.zzbn.zza.NOT_AVAILABLE
            r0.a(r1)
            return
        L_0x000e:
            java.lang.String r0 = "Start loading resource from network ..."
            com.google.android.gms.tagmanager.zzbo.v(r0)
            java.lang.String r0 = r7.a()
            com.google.android.gms.internal.zzafk r1 = r7.b
            com.google.android.gms.internal.zzafj r1 = r1.zzclg()
            r2 = 0
            java.io.InputStream r3 = r1.zzra(r0)     // Catch:{ FileNotFoundException -> 0x0133, zzafl -> 0x006a, IOException -> 0x0027 }
            r2 = r3
            goto L_0x008b
        L_0x0024:
            r0 = move-exception
            goto L_0x0174
        L_0x0027:
            r2 = move-exception
            java.lang.String r3 = r2.getMessage()     // Catch:{ all -> 0x0024 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0024 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0024 }
            java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0024 }
            int r5 = r5.length()     // Catch:{ all -> 0x0024 }
            int r5 = r5 + 40
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0024 }
            int r6 = r6.length()     // Catch:{ all -> 0x0024 }
            int r5 = r5 + r6
            r4.<init>(r5)     // Catch:{ all -> 0x0024 }
            java.lang.String r5 = "Error when loading resources from url: "
            r4.append(r5)     // Catch:{ all -> 0x0024 }
            r4.append(r0)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = " "
            r4.append(r0)     // Catch:{ all -> 0x0024 }
            r4.append(r3)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbo.zzd(r0, r2)     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r0 = r7.e     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn$zza r2 = com.google.android.gms.tagmanager.zzbn.zza.IO_ERROR     // Catch:{ all -> 0x0024 }
            r0.a(r2)     // Catch:{ all -> 0x0024 }
            r1.close()
            return
        L_0x006a:
            java.lang.String r3 = "Error when loading resource for url: "
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0024 }
            int r5 = r4.length()     // Catch:{ all -> 0x0024 }
            if (r5 == 0) goto L_0x007b
            java.lang.String r3 = r3.concat(r4)     // Catch:{ all -> 0x0024 }
            goto L_0x0081
        L_0x007b:
            java.lang.String r4 = new java.lang.String     // Catch:{ all -> 0x0024 }
            r4.<init>(r3)     // Catch:{ all -> 0x0024 }
            r3 = r4
        L_0x0081:
            com.google.android.gms.tagmanager.zzbo.zzdf(r3)     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r3 = r7.e     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn$zza r4 = com.google.android.gms.tagmanager.zzbn.zza.SERVER_UNAVAILABLE_ERROR     // Catch:{ all -> 0x0024 }
            r3.a(r4)     // Catch:{ all -> 0x0024 }
        L_0x008b:
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x00f0 }
            r3.<init>()     // Catch:{ IOException -> 0x00f0 }
            com.google.android.gms.internal.zzafg.zzc(r2, r3)     // Catch:{ IOException -> 0x00f0 }
            byte[] r2 = r3.toByteArray()     // Catch:{ IOException -> 0x00f0 }
            com.google.android.gms.internal.zzah$zzj r2 = com.google.android.gms.internal.zzah.zzj.zzf(r2)     // Catch:{ IOException -> 0x00f0 }
            java.lang.String r3 = java.lang.String.valueOf(r2)     // Catch:{ IOException -> 0x00f0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f0 }
            java.lang.String r5 = java.lang.String.valueOf(r3)     // Catch:{ IOException -> 0x00f0 }
            int r5 = r5.length()     // Catch:{ IOException -> 0x00f0 }
            int r5 = r5 + 43
            r4.<init>(r5)     // Catch:{ IOException -> 0x00f0 }
            java.lang.String r5 = "Successfully loaded supplemented resource: "
            r4.append(r5)     // Catch:{ IOException -> 0x00f0 }
            r4.append(r3)     // Catch:{ IOException -> 0x00f0 }
            java.lang.String r3 = r4.toString()     // Catch:{ IOException -> 0x00f0 }
            com.google.android.gms.tagmanager.zzbo.v(r3)     // Catch:{ IOException -> 0x00f0 }
            com.google.android.gms.internal.zzah$zzf r3 = r2.zzxr     // Catch:{ IOException -> 0x00f0 }
            if (r3 != 0) goto L_0x00e2
            com.google.android.gms.internal.zzah$zzi[] r3 = r2.zzxq     // Catch:{ IOException -> 0x00f0 }
            int r3 = r3.length     // Catch:{ IOException -> 0x00f0 }
            if (r3 != 0) goto L_0x00e2
            java.lang.String r3 = "No change for container: "
            java.lang.String r4 = r7.c     // Catch:{ IOException -> 0x00f0 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ IOException -> 0x00f0 }
            int r5 = r4.length()     // Catch:{ IOException -> 0x00f0 }
            if (r5 == 0) goto L_0x00d9
            java.lang.String r3 = r3.concat(r4)     // Catch:{ IOException -> 0x00f0 }
            goto L_0x00df
        L_0x00d9:
            java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x00f0 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x00f0 }
            r3 = r4
        L_0x00df:
            com.google.android.gms.tagmanager.zzbo.v(r3)     // Catch:{ IOException -> 0x00f0 }
        L_0x00e2:
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r3 = r7.e     // Catch:{ IOException -> 0x00f0 }
            r3.a(r2)     // Catch:{ IOException -> 0x00f0 }
            r1.close()
            java.lang.String r0 = "Load resource from network finished."
            com.google.android.gms.tagmanager.zzbo.v(r0)
            return
        L_0x00f0:
            r2 = move-exception
            java.lang.String r3 = r2.getMessage()     // Catch:{ all -> 0x0024 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0024 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0024 }
            java.lang.String r5 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0024 }
            int r5 = r5.length()     // Catch:{ all -> 0x0024 }
            int r5 = r5 + 51
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0024 }
            int r6 = r6.length()     // Catch:{ all -> 0x0024 }
            int r5 = r5 + r6
            r4.<init>(r5)     // Catch:{ all -> 0x0024 }
            java.lang.String r5 = "Error when parsing downloaded resources from url: "
            r4.append(r5)     // Catch:{ all -> 0x0024 }
            r4.append(r0)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = " "
            r4.append(r0)     // Catch:{ all -> 0x0024 }
            r4.append(r3)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbo.zzd(r0, r2)     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r0 = r7.e     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn$zza r2 = com.google.android.gms.tagmanager.zzbn.zza.SERVER_ERROR     // Catch:{ all -> 0x0024 }
            r0.a(r2)     // Catch:{ all -> 0x0024 }
            r1.close()
            return
        L_0x0133:
            java.lang.String r2 = r7.c     // Catch:{ all -> 0x0024 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0024 }
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0024 }
            int r4 = r4.length()     // Catch:{ all -> 0x0024 }
            int r4 = r4 + 79
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0024 }
            int r5 = r5.length()     // Catch:{ all -> 0x0024 }
            int r4 = r4 + r5
            r3.<init>(r4)     // Catch:{ all -> 0x0024 }
            java.lang.String r4 = "No data is retrieved from the given url: "
            r3.append(r4)     // Catch:{ all -> 0x0024 }
            r3.append(r0)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = ". Make sure container_id: "
            r3.append(r0)     // Catch:{ all -> 0x0024 }
            r3.append(r2)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = " is correct."
            r3.append(r0)     // Catch:{ all -> 0x0024 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbo.zzdf(r0)     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzah$zzj> r0 = r7.e     // Catch:{ all -> 0x0024 }
            com.google.android.gms.tagmanager.zzbn$zza r2 = com.google.android.gms.tagmanager.zzbn.zza.SERVER_ERROR     // Catch:{ all -> 0x0024 }
            r0.a(r2)     // Catch:{ all -> 0x0024 }
            r1.close()
            return
        L_0x0174:
            r1.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzct.c():void");
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        String valueOf = String.valueOf(this.f.a());
        String str = this.g;
        String valueOf2 = String.valueOf("&v=a65833898");
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 0 + String.valueOf(str).length() + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append(str);
        sb.append(valueOf2);
        String sb2 = sb.toString();
        if (this.h != null && !this.h.trim().equals("")) {
            String valueOf3 = String.valueOf(sb2);
            String valueOf4 = String.valueOf("&pv=");
            String str2 = this.h;
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 0 + String.valueOf(valueOf4).length() + String.valueOf(str2).length());
            sb3.append(valueOf3);
            sb3.append(valueOf4);
            sb3.append(str2);
            sb2 = sb3.toString();
        }
        if (zzcj.a().b().equals(zza.CONTAINER_DEBUG)) {
            String valueOf5 = String.valueOf(sb2);
            String valueOf6 = String.valueOf("&gtm_debug=x");
            if (valueOf6.length() != 0) {
                return valueOf5.concat(valueOf6);
            }
            sb2 = new String(valueOf5);
        }
        return sb2;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzbn<zzj> zzbn) {
        this.e = zzbn;
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (str == null) {
            str = this.d;
        } else {
            String str2 = "Setting CTFE URL path: ";
            String valueOf = String.valueOf(str);
            zzbo.zzdd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        this.g = str;
    }

    /* access modifiers changed from: 0000 */
    public void b(String str) {
        String str2 = "Setting previous container version: ";
        String valueOf = String.valueOf(str);
        zzbo.zzdd(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.h = str;
    }

    public void run() {
        if (this.e == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.e.a();
        c();
    }
}
