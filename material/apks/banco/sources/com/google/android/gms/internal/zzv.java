package com.google.android.gms.internal;

import android.os.SystemClock;
import java.io.EOFException;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class zzv implements zzb {
    private final Map<String, zza> a;
    private long b;
    private final File c;
    private final int d;

    static class zza {
        public long a;
        public String b;
        public String c;
        public long d;
        public long e;
        public long f;
        public long g;
        public Map<String, String> h;

        private zza() {
        }

        public zza(String str, com.google.android.gms.internal.zzb.zza zza) {
            this.b = str;
            this.a = (long) zza.data.length;
            this.c = zza.zza;
            this.d = zza.zzb;
            this.e = zza.zzc;
            this.f = zza.zzd;
            this.g = zza.zze;
            this.h = zza.zzf;
        }

        public static zza a(InputStream inputStream) {
            zza zza = new zza();
            if (zzv.a(inputStream) != 538247942) {
                throw new IOException();
            }
            zza.b = zzv.c(inputStream);
            zza.c = zzv.c(inputStream);
            if (zza.c.equals("")) {
                zza.c = null;
            }
            zza.d = zzv.b(inputStream);
            zza.e = zzv.b(inputStream);
            zza.f = zzv.b(inputStream);
            zza.g = zzv.b(inputStream);
            zza.h = zzv.d(inputStream);
            return zza;
        }

        public com.google.android.gms.internal.zzb.zza a(byte[] bArr) {
            com.google.android.gms.internal.zzb.zza zza = new com.google.android.gms.internal.zzb.zza();
            zza.data = bArr;
            zza.zza = this.c;
            zza.zzb = this.d;
            zza.zzc = this.e;
            zza.zzd = this.f;
            zza.zze = this.g;
            zza.zzf = this.h;
            return zza;
        }

        public boolean a(OutputStream outputStream) {
            try {
                zzv.a(outputStream, 538247942);
                zzv.a(outputStream, this.b);
                zzv.a(outputStream, this.c == null ? "" : this.c);
                zzv.a(outputStream, this.d);
                zzv.a(outputStream, this.e);
                zzv.a(outputStream, this.f);
                zzv.a(outputStream, this.g);
                zzv.a(this.h, outputStream);
                outputStream.flush();
                return true;
            } catch (IOException e2) {
                zzs.zzb("%s", e2.toString());
                return false;
            }
        }
    }

    static class zzb extends FilterInputStream {
        /* access modifiers changed from: private */
        public int a;

        private zzb(InputStream inputStream) {
            super(inputStream);
            this.a = 0;
        }

        public int read() {
            int read = super.read();
            if (read != -1) {
                this.a++;
            }
            return read;
        }

        public int read(byte[] bArr, int i, int i2) {
            int read = super.read(bArr, i, i2);
            if (read != -1) {
                this.a += read;
            }
            return read;
        }
    }

    public zzv(File file) {
        this(file, 5242880);
    }

    public zzv(File file, int i) {
        this.a = new LinkedHashMap(16, 0.75f, true);
        this.b = 0;
        this.c = file;
        this.d = i;
    }

    static int a(InputStream inputStream) {
        return (e(inputStream) << 24) | (e(inputStream) << 0) | 0 | (e(inputStream) << 8) | (e(inputStream) << 16);
    }

    private String a(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private void a(int i) {
        long j;
        long j2;
        long j3 = (long) i;
        if (this.b + j3 >= ((long) this.d)) {
            if (zzs.DEBUG) {
                zzs.zza("Pruning old cache entries.", new Object[0]);
            }
            long j4 = this.b;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Iterator it = this.a.entrySet().iterator();
            int i2 = 0;
            while (true) {
                if (!it.hasNext()) {
                    j = elapsedRealtime;
                    break;
                }
                zza zza2 = (zza) ((Entry) it.next()).getValue();
                if (zzf(zza2.b).delete()) {
                    j2 = j3;
                    j = elapsedRealtime;
                    this.b -= zza2.a;
                } else {
                    j2 = j3;
                    j = elapsedRealtime;
                    zzs.zzb("Could not delete cache entry for key=%s, filename=%s", zza2.b, a(zza2.b));
                }
                it.remove();
                i2++;
                if (((float) (this.b + j2)) < ((float) this.d) * 0.9f) {
                    break;
                }
                j3 = j2;
                elapsedRealtime = j;
            }
            if (zzs.DEBUG) {
                zzs.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i2), Long.valueOf(this.b - j4), Long.valueOf(SystemClock.elapsedRealtime() - j));
            }
        }
    }

    static void a(OutputStream outputStream, int i) {
        outputStream.write((i >> 0) & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write((i >> 24) & 255);
    }

    static void a(OutputStream outputStream, long j) {
        outputStream.write((byte) ((int) (j >>> 0)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void a(OutputStream outputStream, String str) {
        byte[] bytes = str.getBytes("UTF-8");
        a(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private void a(String str, zza zza2) {
        if (!this.a.containsKey(str)) {
            this.b += zza2.a;
        } else {
            this.b += zza2.a - ((zza) this.a.get(str)).a;
        }
        this.a.put(str, zza2);
    }

    static void a(Map<String, String> map, OutputStream outputStream) {
        if (map != null) {
            a(outputStream, map.size());
            for (Entry entry : map.entrySet()) {
                a(outputStream, (String) entry.getKey());
                a(outputStream, (String) entry.getValue());
            }
            return;
        }
        a(outputStream, 0);
    }

    private static byte[] a(InputStream inputStream, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == i) {
            return bArr;
        }
        StringBuilder sb = new StringBuilder(50);
        sb.append("Expected ");
        sb.append(i);
        sb.append(" bytes, read ");
        sb.append(i2);
        sb.append(" bytes");
        throw new IOException(sb.toString());
    }

    static long b(InputStream inputStream) {
        return ((((long) e(inputStream)) & 255) << 0) | 0 | ((((long) e(inputStream)) & 255) << 8) | ((((long) e(inputStream)) & 255) << 16) | ((((long) e(inputStream)) & 255) << 24) | ((((long) e(inputStream)) & 255) << 32) | ((((long) e(inputStream)) & 255) << 40) | ((((long) e(inputStream)) & 255) << 48) | ((((long) e(inputStream)) & 255) << 56);
    }

    private void b(String str) {
        zza zza2 = (zza) this.a.get(str);
        if (zza2 != null) {
            this.b -= zza2.a;
            this.a.remove(str);
        }
    }

    static String c(InputStream inputStream) {
        return new String(a(inputStream, (int) b(inputStream)), "UTF-8");
    }

    static Map<String, String> d(InputStream inputStream) {
        int a2 = a(inputStream);
        Map<String, String> emptyMap = a2 == 0 ? Collections.emptyMap() : new HashMap<>(a2);
        for (int i = 0; i < a2; i++) {
            emptyMap.put(c(inputStream).intern(), c(inputStream).intern());
        }
        return emptyMap;
    }

    private static int e(InputStream inputStream) {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:26|27|(2:36|37)|38|39) */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x0065 */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x005c A[SYNTHETIC, Splitter:B:33:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0068 A[SYNTHETIC, Splitter:B:41:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x006b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initialize() {
        /*
            r8 = this;
            monitor-enter(r8)
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x0070 }
            r1 = 0
            if (r0 != 0) goto L_0x0024
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            boolean r0 = r0.mkdirs()     // Catch:{ all -> 0x0070 }
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Unable to create cache dir %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0070 }
            java.io.File r3 = r8.c     // Catch:{ all -> 0x0070 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x0070 }
            r2[r1] = r3     // Catch:{ all -> 0x0070 }
            com.google.android.gms.internal.zzs.zzc(r0, r2)     // Catch:{ all -> 0x0070 }
        L_0x0022:
            monitor-exit(r8)
            return
        L_0x0024:
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            java.io.File[] r0 = r0.listFiles()     // Catch:{ all -> 0x0070 }
            if (r0 != 0) goto L_0x002e
            monitor-exit(r8)
            return
        L_0x002e:
            int r2 = r0.length     // Catch:{ all -> 0x0070 }
        L_0x002f:
            if (r1 >= r2) goto L_0x006e
            r3 = r0[r1]     // Catch:{ all -> 0x0070 }
            r4 = 0
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x005a }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x005a }
            r6.<init>(r3)     // Catch:{ IOException -> 0x005a }
            r5.<init>(r6)     // Catch:{ IOException -> 0x005a }
            com.google.android.gms.internal.zzv$zza r4 = com.google.android.gms.internal.zzv.zza.a(r5)     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            long r6 = r3.length()     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            r4.a = r6     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            java.lang.String r6 = r4.b     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            r8.a(r6, r4)     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            if (r5 == 0) goto L_0x006b
            r5.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006b
        L_0x0053:
            r0 = move-exception
            r4 = r5
            goto L_0x0060
        L_0x0056:
            r4 = r5
            goto L_0x005a
        L_0x0058:
            r0 = move-exception
            goto L_0x0060
        L_0x005a:
            if (r3 == 0) goto L_0x0066
            r3.delete()     // Catch:{ all -> 0x0058 }
            goto L_0x0066
        L_0x0060:
            if (r4 == 0) goto L_0x0065
            r4.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0065:
            throw r0     // Catch:{ all -> 0x0070 }
        L_0x0066:
            if (r4 == 0) goto L_0x006b
            r4.close()     // Catch:{ IOException -> 0x006b }
        L_0x006b:
            int r1 = r1 + 1
            goto L_0x002f
        L_0x006e:
            monitor-exit(r8)
            return
        L_0x0070:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzv.initialize():void");
    }

    public synchronized void remove(String str) {
        boolean delete = zzf(str).delete();
        b(str);
        if (!delete) {
            zzs.zzb("Could not delete cache entry for key=%s, filename=%s", str, a(str));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005f A[SYNTHETIC, Splitter:B:30:0x005f] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x006a A[SYNTHETIC, Splitter:B:39:0x006a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.google.android.gms.internal.zzb.zza zza(java.lang.String r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzv$zza> r0 = r10.a     // Catch:{ all -> 0x0071 }
            java.lang.Object r0 = r0.get(r11)     // Catch:{ all -> 0x0071 }
            com.google.android.gms.internal.zzv$zza r0 = (com.google.android.gms.internal.zzv.zza) r0     // Catch:{ all -> 0x0071 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r10)
            return r1
        L_0x000e:
            java.io.File r2 = r10.zzf(r11)     // Catch:{ all -> 0x0071 }
            com.google.android.gms.internal.zzv$zzb r3 = new com.google.android.gms.internal.zzv$zzb     // Catch:{ IOException -> 0x0042, all -> 0x003f }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0042, all -> 0x003f }
            r4.<init>(r2)     // Catch:{ IOException -> 0x0042, all -> 0x003f }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0042, all -> 0x003f }
            com.google.android.gms.internal.zzv.zza.a(r3)     // Catch:{ IOException -> 0x003d }
            long r4 = r2.length()     // Catch:{ IOException -> 0x003d }
            int r6 = r3.a     // Catch:{ IOException -> 0x003d }
            long r6 = (long) r6     // Catch:{ IOException -> 0x003d }
            long r8 = r4 - r6
            int r4 = (int) r8     // Catch:{ IOException -> 0x003d }
            byte[] r4 = a(r3, r4)     // Catch:{ IOException -> 0x003d }
            com.google.android.gms.internal.zzb$zza r0 = r0.a(r4)     // Catch:{ IOException -> 0x003d }
            if (r3 == 0) goto L_0x003b
            r3.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003b
        L_0x0039:
            monitor-exit(r10)
            return r1
        L_0x003b:
            monitor-exit(r10)
            return r0
        L_0x003d:
            r0 = move-exception
            goto L_0x0044
        L_0x003f:
            r11 = move-exception
            r3 = r1
            goto L_0x0068
        L_0x0042:
            r0 = move-exception
            r3 = r1
        L_0x0044:
            java.lang.String r4 = "%s: %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0067 }
            r6 = 0
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x0067 }
            r5[r6] = r2     // Catch:{ all -> 0x0067 }
            r2 = 1
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0067 }
            r5[r2] = r0     // Catch:{ all -> 0x0067 }
            com.google.android.gms.internal.zzs.zzb(r4, r5)     // Catch:{ all -> 0x0067 }
            r10.remove(r11)     // Catch:{ all -> 0x0067 }
            if (r3 == 0) goto L_0x0065
            r3.close()     // Catch:{ IOException -> 0x0063 }
            goto L_0x0065
        L_0x0063:
            monitor-exit(r10)
            return r1
        L_0x0065:
            monitor-exit(r10)
            return r1
        L_0x0067:
            r11 = move-exception
        L_0x0068:
            if (r3 == 0) goto L_0x0070
            r3.close()     // Catch:{ IOException -> 0x006e }
            goto L_0x0070
        L_0x006e:
            monitor-exit(r10)
            return r1
        L_0x0070:
            throw r11     // Catch:{ all -> 0x0071 }
        L_0x0071:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzv.zza(java.lang.String):com.google.android.gms.internal.zzb$zza");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:12|13|(1:15)|16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r0.delete() == false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        com.google.android.gms.internal.zzs.zzb("Could not clean up file %s", r0.getAbsolutePath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0040 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void zza(java.lang.String r7, com.google.android.gms.internal.zzb.zza r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            byte[] r0 = r8.data     // Catch:{ all -> 0x0055 }
            int r0 = r0.length     // Catch:{ all -> 0x0055 }
            r6.a(r0)     // Catch:{ all -> 0x0055 }
            java.io.File r0 = r6.zzf(r7)     // Catch:{ all -> 0x0055 }
            r1 = 0
            r2 = 1
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0040 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0040 }
            com.google.android.gms.internal.zzv$zza r4 = new com.google.android.gms.internal.zzv$zza     // Catch:{ IOException -> 0x0040 }
            r4.<init>(r7, r8)     // Catch:{ IOException -> 0x0040 }
            boolean r5 = r4.a(r3)     // Catch:{ IOException -> 0x0040 }
            if (r5 != 0) goto L_0x0033
            r3.close()     // Catch:{ IOException -> 0x0040 }
            java.lang.String r7 = "Failed to write header for %s"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0040 }
            java.lang.String r3 = r0.getAbsolutePath()     // Catch:{ IOException -> 0x0040 }
            r8[r1] = r3     // Catch:{ IOException -> 0x0040 }
            com.google.android.gms.internal.zzs.zzb(r7, r8)     // Catch:{ IOException -> 0x0040 }
            java.io.IOException r7 = new java.io.IOException     // Catch:{ IOException -> 0x0040 }
            r7.<init>()     // Catch:{ IOException -> 0x0040 }
            throw r7     // Catch:{ IOException -> 0x0040 }
        L_0x0033:
            byte[] r8 = r8.data     // Catch:{ IOException -> 0x0040 }
            r3.write(r8)     // Catch:{ IOException -> 0x0040 }
            r3.close()     // Catch:{ IOException -> 0x0040 }
            r6.a(r7, r4)     // Catch:{ IOException -> 0x0040 }
            monitor-exit(r6)
            return
        L_0x0040:
            boolean r7 = r0.delete()     // Catch:{ all -> 0x0055 }
            if (r7 != 0) goto L_0x0053
            java.lang.String r7 = "Could not clean up file %s"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ all -> 0x0055 }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ all -> 0x0055 }
            r8[r1] = r0     // Catch:{ all -> 0x0055 }
            com.google.android.gms.internal.zzs.zzb(r7, r8)     // Catch:{ all -> 0x0055 }
        L_0x0053:
            monitor-exit(r6)
            return
        L_0x0055:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzv.zza(java.lang.String, com.google.android.gms.internal.zzb$zza):void");
    }

    public File zzf(String str) {
        return new File(this.c, a(str));
    }
}
