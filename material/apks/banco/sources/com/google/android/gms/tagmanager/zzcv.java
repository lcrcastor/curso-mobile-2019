package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.internal.zzafe.zza;
import com.google.android.gms.internal.zzafg;
import com.google.android.gms.internal.zzafg.zzc;
import com.google.android.gms.internal.zzafg.zzg;
import com.google.android.gms.internal.zzah.zzf;
import com.google.android.gms.internal.zzarj;
import com.google.android.gms.internal.zzark;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class zzcv implements zzf {
    private final Context a;
    private final String b;
    private final ExecutorService c = Executors.newSingleThreadExecutor();
    private zzbn<zza> d;

    zzcv(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    private zzc a(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzbh.a(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            zzbo.zzdd("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException unused2) {
            zzbo.zzdf("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private zzc a(byte[] bArr) {
        try {
            zzc zzb = zzafg.zzb(zzf.zze(bArr));
            if (zzb != null) {
                zzbo.v("The container was successfully loaded from the resource (using binary file)");
            }
            return zzb;
        } catch (zzarj unused) {
            zzbo.e("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        } catch (zzg unused2) {
            zzbo.zzdf("The resource file is invalid. The container from the binary file is invalid");
            return null;
        }
    }

    private void c(zza zza) {
        if (zza.zzxr == null && zza.aJk == null) {
            throw new IllegalArgumentException("Resource and SupplementedResource are NULL.");
        }
    }

    public zzc a(int i) {
        String sb;
        try {
            InputStream openRawResource = this.a.getResources().openRawResource(i);
            String valueOf = String.valueOf(this.a.getResources().getResourceName(i));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 66);
            sb2.append("Attempting to load a container from the resource ID ");
            sb2.append(i);
            sb2.append(" (");
            sb2.append(valueOf);
            sb2.append(")");
            zzbo.v(sb2.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzafg.zzc(openRawResource, byteArrayOutputStream);
                zzc a2 = a(byteArrayOutputStream);
                if (a2 == null) {
                    return a(byteArrayOutputStream.toByteArray());
                }
                zzbo.v("The container was successfully loaded from the resource (using JSON file format)");
                return a2;
            } catch (IOException unused) {
                String valueOf2 = String.valueOf(this.a.getResources().getResourceName(i));
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 67);
                sb3.append("Error reading the default container with resource ID ");
                sb3.append(i);
                sb3.append(" (");
                sb3.append(valueOf2);
                sb3.append(")");
                sb = sb3.toString();
                zzbo.zzdf(sb);
                return null;
            }
        } catch (NotFoundException unused2) {
            StringBuilder sb4 = new StringBuilder(98);
            sb4.append("Failed to load the container. No default container resource found with the resource ID ");
            sb4.append(i);
            sb = sb4.toString();
            zzbo.zzdf(sb);
            return null;
        }
    }

    public void a() {
        this.c.execute(new Runnable() {
            public void run() {
                zzcv.this.b();
            }
        });
    }

    public void a(final zza zza) {
        this.c.execute(new Runnable() {
            public void run() {
                zzcv.this.b(zza);
            }
        });
    }

    public void a(zzbn<zza> zzbn) {
        this.d = zzbn;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0068, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r3.d.a(com.google.android.gms.tagmanager.zzbn.zza.IO_ERROR);
        com.google.android.gms.tagmanager.zzbo.zzdf("Failed to read the resource from disk. The resource is inconsistent");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0096, code lost:
        com.google.android.gms.tagmanager.zzbo.zzdf("Error closing stream for reading resource from disk");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009b, code lost:
        throw r1;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:17:0x006a, B:21:0x007a] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x006a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:17:0x006a=Splitter:B:17:0x006a, B:21:0x007a=Splitter:B:21:0x007a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r3 = this;
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r0 = r3.d
            if (r0 != 0) goto L_0x000c
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Callback must be set before execute"
            r0.<init>(r1)
            throw r0
        L_0x000c:
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r0 = r3.d
            r0.a()
            java.lang.String r0 = "Attempting to load resource from disk"
            com.google.android.gms.tagmanager.zzbo.v(r0)
            com.google.android.gms.tagmanager.zzcj r0 = com.google.android.gms.tagmanager.zzcj.a()
            com.google.android.gms.tagmanager.zzcj$zza r0 = r0.b()
            com.google.android.gms.tagmanager.zzcj$zza r1 = com.google.android.gms.tagmanager.zzcj.zza.CONTAINER
            if (r0 == r1) goto L_0x002e
            com.google.android.gms.tagmanager.zzcj r0 = com.google.android.gms.tagmanager.zzcj.a()
            com.google.android.gms.tagmanager.zzcj$zza r0 = r0.b()
            com.google.android.gms.tagmanager.zzcj$zza r1 = com.google.android.gms.tagmanager.zzcj.zza.CONTAINER_DEBUG
            if (r0 != r1) goto L_0x0046
        L_0x002e:
            java.lang.String r0 = r3.b
            com.google.android.gms.tagmanager.zzcj r1 = com.google.android.gms.tagmanager.zzcj.a()
            java.lang.String r1 = r1.d()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0046
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r0 = r3.d
            com.google.android.gms.tagmanager.zzbn$zza r1 = com.google.android.gms.tagmanager.zzbn.zza.NOT_AVAILABLE
            r0.a(r1)
            return
        L_0x0046:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x009c }
            java.io.File r1 = r3.c()     // Catch:{ FileNotFoundException -> 0x009c }
            r0.<init>(r1)     // Catch:{ FileNotFoundException -> 0x009c }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            r1.<init>()     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            com.google.android.gms.internal.zzafg.zzc(r0, r1)     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            byte[] r1 = r1.toByteArray()     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            com.google.android.gms.internal.zzafe$zza r1 = com.google.android.gms.internal.zzafe.zza.zzao(r1)     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            r3.c(r1)     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r2 = r3.d     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            r2.a(r1)     // Catch:{ IOException -> 0x007a, IllegalArgumentException -> 0x006a }
            goto L_0x0076
        L_0x0068:
            r1 = move-exception
            goto L_0x0092
        L_0x006a:
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r1 = r3.d     // Catch:{ all -> 0x0068 }
            com.google.android.gms.tagmanager.zzbn$zza r2 = com.google.android.gms.tagmanager.zzbn.zza.IO_ERROR     // Catch:{ all -> 0x0068 }
            r1.a(r2)     // Catch:{ all -> 0x0068 }
            java.lang.String r1 = "Failed to read the resource from disk. The resource is inconsistent"
            com.google.android.gms.tagmanager.zzbo.zzdf(r1)     // Catch:{ all -> 0x0068 }
        L_0x0076:
            r0.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x008c
        L_0x007a:
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r1 = r3.d     // Catch:{ all -> 0x0068 }
            com.google.android.gms.tagmanager.zzbn$zza r2 = com.google.android.gms.tagmanager.zzbn.zza.IO_ERROR     // Catch:{ all -> 0x0068 }
            r1.a(r2)     // Catch:{ all -> 0x0068 }
            java.lang.String r1 = "Failed to read the resource from disk"
            com.google.android.gms.tagmanager.zzbo.zzdf(r1)     // Catch:{ all -> 0x0068 }
            goto L_0x0076
        L_0x0087:
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbo.zzdf(r0)
        L_0x008c:
            java.lang.String r0 = "The Disk resource was successfully read."
            com.google.android.gms.tagmanager.zzbo.v(r0)
            return
        L_0x0092:
            r0.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x009b
        L_0x0096:
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzbo.zzdf(r0)
        L_0x009b:
            throw r1
        L_0x009c:
            java.lang.String r0 = "Failed to find the resource in the disk"
            com.google.android.gms.tagmanager.zzbo.zzdd(r0)
            com.google.android.gms.tagmanager.zzbn<com.google.android.gms.internal.zzafe$zza> r0 = r3.d
            com.google.android.gms.tagmanager.zzbn$zza r1 = com.google.android.gms.tagmanager.zzbn.zza.NOT_AVAILABLE
            r0.a(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzcv.b():void");
    }

    /* access modifiers changed from: 0000 */
    public boolean b(zza zza) {
        File c2 = c();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(c2);
            try {
                fileOutputStream.write(zzark.zzf(zza));
                try {
                } catch (IOException unused) {
                    zzbo.zzdf("error closing stream for writing resource to disk");
                }
                return true;
            } catch (IOException unused2) {
                zzbo.zzdf("Error writing resource to disk. Removing resource from disk.");
                c2.delete();
                try {
                    return false;
                } catch (IOException unused3) {
                    zzbo.zzdf("error closing stream for writing resource to disk");
                    return false;
                }
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException unused4) {
                    zzbo.zzdf("error closing stream for writing resource to disk");
                }
            }
        } catch (FileNotFoundException unused5) {
            zzbo.e("Error opening resource file for writing");
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public File c() {
        String valueOf = String.valueOf("resource_");
        String valueOf2 = String.valueOf(this.b);
        return new File(this.a.getDir("google_tagmanager", 0), valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public synchronized void release() {
        this.c.shutdown();
    }
}
