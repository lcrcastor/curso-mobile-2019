package com.google.android.gms.analytics.internal;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class zzn extends zzd {
    private volatile String a;
    private Future<String> b;

    protected zzn(zzf zzf) {
        super(zzf);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x003d A[SYNTHETIC, Splitter:B:24:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0050 A[SYNTHETIC, Splitter:B:35:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x005d A[SYNTHETIC, Splitter:B:42:0x005d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.content.Context r4, java.lang.String r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.zzac.zzhz(r5)
            java.lang.String r0 = "ClientId should be saved from worker thread"
            com.google.android.gms.common.internal.zzac.zzhr(r0)
            r0 = 0
            r1 = 0
            java.lang.String r2 = "Storing clientId"
            r3.zza(r2, r5)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0035 }
            java.lang.String r2 = "gaClientId"
            java.io.FileOutputStream r4 = r4.openFileOutput(r2, r0)     // Catch:{ FileNotFoundException -> 0x0048, IOException -> 0x0035 }
            byte[] r5 = r5.getBytes()     // Catch:{ FileNotFoundException -> 0x0030, IOException -> 0x002d, all -> 0x002a }
            r4.write(r5)     // Catch:{ FileNotFoundException -> 0x0030, IOException -> 0x002d, all -> 0x002a }
            if (r4 == 0) goto L_0x0028
            r4.close()     // Catch:{ IOException -> 0x0022 }
            goto L_0x0028
        L_0x0022:
            r4 = move-exception
            java.lang.String r5 = "Failed to close clientId writing stream"
            r3.zze(r5, r4)
        L_0x0028:
            r4 = 1
            return r4
        L_0x002a:
            r5 = move-exception
            r1 = r4
            goto L_0x005b
        L_0x002d:
            r5 = move-exception
            r1 = r4
            goto L_0x0036
        L_0x0030:
            r5 = move-exception
            r1 = r4
            goto L_0x0049
        L_0x0033:
            r5 = move-exception
            goto L_0x005b
        L_0x0035:
            r5 = move-exception
        L_0x0036:
            java.lang.String r4 = "Error writing to clientId file"
            r3.zze(r4, r5)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch:{ IOException -> 0x0041 }
            return r0
        L_0x0041:
            r4 = move-exception
            java.lang.String r5 = "Failed to close clientId writing stream"
            r3.zze(r5, r4)
        L_0x0047:
            return r0
        L_0x0048:
            r5 = move-exception
        L_0x0049:
            java.lang.String r4 = "Error creating clientId file"
            r3.zze(r4, r5)     // Catch:{ all -> 0x0033 }
            if (r1 == 0) goto L_0x005a
            r1.close()     // Catch:{ IOException -> 0x0054 }
            return r0
        L_0x0054:
            r4 = move-exception
            java.lang.String r5 = "Failed to close clientId writing stream"
            r3.zze(r5, r4)
        L_0x005a:
            return r0
        L_0x005b:
            if (r1 == 0) goto L_0x0067
            r1.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0067
        L_0x0061:
            r4 = move-exception
            java.lang.String r0 = "Failed to close clientId writing stream"
            r3.zze(r0, r4)
        L_0x0067:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzn.a(android.content.Context, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public String c() {
        String zzacq = zzacq();
        try {
            if (!a(zzaaq().getContext(), zzacq)) {
                zzacq = "0";
            }
            return zzacq;
        } catch (Exception e) {
            zze("Error saving clientId file", e);
            return "0";
        }
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        synchronized (this) {
            this.a = null;
            this.b = zzaaq().zzc(new Callable<String>() {
                /* renamed from: a */
                public String call() {
                    return zzn.this.c();
                }
            });
        }
        return zzacm();
    }

    /* access modifiers changed from: 0000 */
    public String b() {
        String zzbb = zzbb(zzaaq().getContext());
        return zzbb == null ? c() : zzbb;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003e A[Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0026 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String zzacm() {
        /*
            r2 = this;
            r2.zzaax()
            monitor-enter(r2)
            java.lang.String r0 = r2.a     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0017
            com.google.android.gms.analytics.zzi r0 = r2.zzaaq()     // Catch:{ all -> 0x0050 }
            com.google.android.gms.analytics.internal.zzn$1 r1 = new com.google.android.gms.analytics.internal.zzn$1     // Catch:{ all -> 0x0050 }
            r1.<init>()     // Catch:{ all -> 0x0050 }
            java.util.concurrent.Future r0 = r0.zzc(r1)     // Catch:{ all -> 0x0050 }
            r2.b = r0     // Catch:{ all -> 0x0050 }
        L_0x0017:
            java.util.concurrent.Future<java.lang.String> r0 = r2.b     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x004c
            java.util.concurrent.Future<java.lang.String> r0 = r2.b     // Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0026 }
            java.lang.Object r0 = r0.get()     // Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0026 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0026 }
            r2.a = r0     // Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0026 }
            goto L_0x003a
        L_0x0026:
            r0 = move-exception
            java.lang.String r1 = "Failed to load or generate client id"
            r2.zze(r1, r0)     // Catch:{ all -> 0x0050 }
            java.lang.String r0 = "0"
        L_0x002e:
            r2.a = r0     // Catch:{ all -> 0x0050 }
            goto L_0x003a
        L_0x0031:
            r0 = move-exception
            java.lang.String r1 = "ClientId loading or generation was interrupted"
            r2.zzd(r1, r0)     // Catch:{ all -> 0x0050 }
            java.lang.String r0 = "0"
            goto L_0x002e
        L_0x003a:
            java.lang.String r0 = r2.a     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0042
            java.lang.String r0 = "0"
            r2.a = r0     // Catch:{ all -> 0x0050 }
        L_0x0042:
            java.lang.String r0 = "Loaded clientId"
            java.lang.String r1 = r2.a     // Catch:{ all -> 0x0050 }
            r2.zza(r0, r1)     // Catch:{ all -> 0x0050 }
            r0 = 0
            r2.b = r0     // Catch:{ all -> 0x0050 }
        L_0x004c:
            java.lang.String r0 = r2.a     // Catch:{ all -> 0x0050 }
            monitor-exit(r2)     // Catch:{ all -> 0x0050 }
            return r0
        L_0x0050:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0050 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzn.zzacm():java.lang.String");
    }

    /* access modifiers changed from: protected */
    public String zzacq() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0080 A[SYNTHETIC, Splitter:B:45:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x008e A[SYNTHETIC, Splitter:B:53:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x009c A[SYNTHETIC, Splitter:B:62:0x009c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String zzbb(android.content.Context r7) {
        /*
            r6 = this;
            java.lang.String r0 = "ClientId should be loaded from worker thread"
            com.google.android.gms.common.internal.zzac.zzhr(r0)
            r0 = 0
            java.lang.String r1 = "gaClientId"
            java.io.FileInputStream r1 = r7.openFileInput(r1)     // Catch:{ FileNotFoundException -> 0x0099, IOException -> 0x0072, all -> 0x006f }
            r2 = 36
            byte[] r3 = new byte[r2]     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            r4 = 0
            int r2 = r1.read(r3, r4, r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            int r5 = r1.available()     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            if (r5 <= 0) goto L_0x0035
            java.lang.String r2 = "clientId file seems corrupted, deleting it."
            r6.zzes(r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            r1.close()     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            java.lang.String r2 = "gaClientId"
            r7.deleteFile(r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ IOException -> 0x002e }
            return r0
        L_0x002e:
            r7 = move-exception
            java.lang.String r1 = "Failed to close client id reading stream"
            r6.zze(r1, r7)
        L_0x0034:
            return r0
        L_0x0035:
            r5 = 14
            if (r2 >= r5) goto L_0x0053
            java.lang.String r2 = "clientId file is empty, deleting it."
            r6.zzes(r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            r1.close()     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            java.lang.String r2 = "gaClientId"
            r7.deleteFile(r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ IOException -> 0x004c }
            return r0
        L_0x004c:
            r7 = move-exception
            java.lang.String r1 = "Failed to close client id reading stream"
            r6.zze(r1, r7)
        L_0x0052:
            return r0
        L_0x0053:
            r1.close()     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            java.lang.String r5 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            r5.<init>(r3, r4, r2)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            java.lang.String r2 = "Read client id from disk"
            r6.zza(r2, r5)     // Catch:{ FileNotFoundException -> 0x009a, IOException -> 0x006d }
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ IOException -> 0x0066 }
            return r5
        L_0x0066:
            r7 = move-exception
            java.lang.String r0 = "Failed to close client id reading stream"
            r6.zze(r0, r7)
        L_0x006c:
            return r5
        L_0x006d:
            r2 = move-exception
            goto L_0x0074
        L_0x006f:
            r7 = move-exception
            r1 = r0
            goto L_0x008c
        L_0x0072:
            r2 = move-exception
            r1 = r0
        L_0x0074:
            java.lang.String r3 = "Error reading client id file, deleting it"
            r6.zze(r3, r2)     // Catch:{ all -> 0x008b }
            java.lang.String r2 = "gaClientId"
            r7.deleteFile(r2)     // Catch:{ all -> 0x008b }
            if (r1 == 0) goto L_0x008a
            r1.close()     // Catch:{ IOException -> 0x0084 }
            return r0
        L_0x0084:
            r7 = move-exception
            java.lang.String r1 = "Failed to close client id reading stream"
            r6.zze(r1, r7)
        L_0x008a:
            return r0
        L_0x008b:
            r7 = move-exception
        L_0x008c:
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ IOException -> 0x0092 }
            goto L_0x0098
        L_0x0092:
            r0 = move-exception
            java.lang.String r1 = "Failed to close client id reading stream"
            r6.zze(r1, r0)
        L_0x0098:
            throw r7
        L_0x0099:
            r1 = r0
        L_0x009a:
            if (r1 == 0) goto L_0x00a6
            r1.close()     // Catch:{ IOException -> 0x00a0 }
            return r0
        L_0x00a0:
            r7 = move-exception
            java.lang.String r1 = "Failed to close client id reading stream"
            r6.zze(r1, r7)
        L_0x00a6:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzn.zzbb(android.content.Context):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void zzym() {
    }
}
