package com.google.android.gms.internal;

import com.google.android.gms.internal.zzb.zza;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class zzt implements zzf {
    protected static final boolean DEBUG = zzs.DEBUG;
    private static int a = 3000;
    private static int b = 4096;
    protected final zzy zzbp;
    protected final zzu zzbq;

    public zzt(zzy zzy) {
        this(zzy, new zzu(b));
    }

    public zzt(zzy zzy, zzu zzu) {
        this.zzbp = zzy;
        this.zzbq = zzu;
    }

    private void a(long j, zzk<?> zzk, byte[] bArr, StatusLine statusLine) {
        if (DEBUG || j > ((long) a)) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = zzk;
            objArr[1] = Long.valueOf(j);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(zzk.zzt().zzd());
            zzs.zzb(str, objArr);
        }
    }

    private static void a(String str, zzk<?> zzk, zzr zzr) {
        zzo zzt = zzk.zzt();
        int zzs = zzk.zzs();
        try {
            zzt.zza(zzr);
            zzk.zzc(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(zzs)}));
        } catch (zzr e) {
            zzk.zzc(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(zzs)}));
            throw e;
        }
    }

    private void a(Map<String, String> map, zza zza) {
        if (zza != null) {
            if (zza.zza != null) {
                map.put("If-None-Match", zza.zza);
            }
            if (zza.zzc > 0) {
                map.put("If-Modified-Since", DateUtils.formatDate(new Date(zza.zzc)));
            }
        }
    }

    private byte[] a(HttpEntity httpEntity) {
        zzaa zzaa = new zzaa(this.zzbq, (int) httpEntity.getContentLength());
        byte[] bArr = null;
        try {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                throw new zzp();
            }
            byte[] zzb = this.zzbq.zzb(1024);
            while (true) {
                try {
                    int read = content.read(zzb);
                    if (read == -1) {
                        break;
                    }
                    zzaa.write(zzb, 0, read);
                } catch (Throwable th) {
                    th = th;
                    bArr = zzb;
                    try {
                        httpEntity.consumeContent();
                    } catch (IOException unused) {
                        zzs.zza("Error occured when calling consumingContent", new Object[0]);
                    }
                    this.zzbq.zza(bArr);
                    zzaa.close();
                    throw th;
                }
            }
            byte[] byteArray = zzaa.toByteArray();
            try {
                httpEntity.consumeContent();
            } catch (IOException unused2) {
                zzs.zza("Error occured when calling consumingContent", new Object[0]);
            }
            this.zzbq.zza(zzb);
            zzaa.close();
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            httpEntity.consumeContent();
            this.zzbq.zza(bArr);
            zzaa.close();
            throw th;
        }
    }

    protected static Map<String, String> zza(Header[] headerArr) {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }

    /* JADX INFO: used method not loaded: com.google.android.gms.internal.zzh.<init>(com.google.android.gms.internal.zzi):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0070, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0071, code lost:
        r1 = r0;
        r16 = null;
        r17 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bf, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c1, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c2, code lost:
        r2 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c3, code lost:
        r1 = r0;
        r17 = r2;
        r16 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d1, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d2, code lost:
        r17 = r1;
        r16 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d7, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d8, code lost:
        r17 = r1;
        r13 = null;
        r16 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00dd, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e0, code lost:
        r1 = r13.getStatusLine().getStatusCode();
        com.google.android.gms.internal.zzs.zzc("Unexpected response code %d for %s", java.lang.Integer.valueOf(r1), r24.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fd, code lost:
        if (r16 != null) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ff, code lost:
        r14 = new com.google.android.gms.internal.zzi(r1, r16, r17, false, android.os.SystemClock.elapsedRealtime() - r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0110, code lost:
        if (r1 == 401) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x011c, code lost:
        throw new com.google.android.gms.internal.zzp(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x011d, code lost:
        a("auth", r8, new com.google.android.gms.internal.zza(r14));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x012e, code lost:
        throw new com.google.android.gms.internal.zzh((com.google.android.gms.internal.zzi) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0134, code lost:
        throw new com.google.android.gms.internal.zzj(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0135, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0136, code lost:
        r1 = r0;
        r3 = "Bad URL ";
        r4 = java.lang.String.valueOf(r24.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0147, code lost:
        if (r4.length() != 0) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0149, code lost:
        r3 = r3.concat(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x014e, code lost:
        r3 = new java.lang.String(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0157, code lost:
        throw new java.lang.RuntimeException(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0158, code lost:
        r1 = "connection";
        r2 = new com.google.android.gms.internal.zzq();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0160, code lost:
        r1 = "socket";
        r2 = new com.google.android.gms.internal.zzq();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0167, code lost:
        a(r1, r8, r2);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0135 A[ExcHandler: MalformedURLException (r0v0 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:2:0x000e] */
    /* JADX WARNING: Removed duplicated region for block: B:71:? A[ExcHandler: ConnectTimeoutException (unused org.apache.http.conn.ConnectTimeoutException), SYNTHETIC, Splitter:B:2:0x000e] */
    /* JADX WARNING: Removed duplicated region for block: B:73:? A[ExcHandler: SocketTimeoutException (unused java.net.SocketTimeoutException), SYNTHETIC, Splitter:B:2:0x000e] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x012f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzi zza(com.google.android.gms.internal.zzk<?> r24) {
        /*
            r23 = this;
            r7 = r23
            r8 = r24
            long r9 = android.os.SystemClock.elapsedRealtime()
        L_0x0008:
            java.util.Map r1 = java.util.Collections.emptyMap()
            r11 = 0
            r12 = 0
            java.util.HashMap r2 = new java.util.HashMap     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            r2.<init>()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            com.google.android.gms.internal.zzb$zza r3 = r24.zzh()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            r7.a(r2, r3)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            com.google.android.gms.internal.zzy r3 = r7.zzbp     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            org.apache.http.HttpResponse r13 = r3.zza(r8, r2)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d7 }
            org.apache.http.StatusLine r6 = r13.getStatusLine()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d1 }
            int r15 = r6.getStatusCode()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d1 }
            org.apache.http.Header[] r2 = r13.getAllHeaders()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d1 }
            java.util.Map r14 = zza(r2)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00d1 }
            r1 = 304(0x130, float:4.26E-43)
            if (r15 != r1) goto L_0x0078
            com.google.android.gms.internal.zzb$zza r1 = r24.zzh()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            if (r1 != 0) goto L_0x0051
            com.google.android.gms.internal.zzi r1 = new com.google.android.gms.internal.zzi     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r17 = 304(0x130, float:4.26E-43)
            r18 = 0
            r20 = 1
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r4 = 0
            long r21 = r2 - r9
            r16 = r1
            r19 = r14
            r16.<init>(r17, r18, r19, r20, r21)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            return r1
        L_0x0051:
            java.util.Map<java.lang.String, java.lang.String> r2 = r1.zzf     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r2.putAll(r14)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            com.google.android.gms.internal.zzi r2 = new com.google.android.gms.internal.zzi     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r16 = 304(0x130, float:4.26E-43)
            byte[] r3 = r1.data     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r1.zzf     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r19 = 1
            long r4 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            r6 = 0
            long r20 = r4 - r9
            r15 = r2
            r17 = r3
            r18 = r1
            r15.<init>(r16, r17, r18, r19, r20)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            return r2
        L_0x0070:
            r0 = move-exception
            r1 = r0
            r16 = r12
            r17 = r14
            goto L_0x00de
        L_0x0078:
            org.apache.http.HttpEntity r1 = r13.getEntity()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c9 }
            if (r1 == 0) goto L_0x0087
            org.apache.http.HttpEntity r1 = r13.getEntity()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            byte[] r1 = r7.a(r1)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x0070 }
            goto L_0x0089
        L_0x0087:
            byte[] r1 = new byte[r11]     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c9 }
        L_0x0089:
            r21 = r1
            long r1 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c1 }
            r3 = 0
            long r3 = r1 - r9
            r1 = r7
            r2 = r3
            r4 = r8
            r5 = r21
            r1.a(r2, r4, r5, r6)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c1 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r15 < r1) goto L_0x00b8
            r1 = 299(0x12b, float:4.19E-43)
            if (r15 <= r1) goto L_0x00a3
            goto L_0x00b8
        L_0x00a3:
            com.google.android.gms.internal.zzi r1 = new com.google.android.gms.internal.zzi     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c1 }
            r18 = 0
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00c1 }
            r4 = 0
            long r19 = r2 - r9
            r2 = r14
            r14 = r1
            r16 = r21
            r17 = r2
            r14.<init>(r15, r16, r17, r18, r19)     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00bf }
            return r1
        L_0x00b8:
            r2 = r14
            java.io.IOException r1 = new java.io.IOException     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00bf }
            r1.<init>()     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00bf }
            throw r1     // Catch:{ SocketTimeoutException -> 0x0160, ConnectTimeoutException -> 0x0158, MalformedURLException -> 0x0135, IOException -> 0x00bf }
        L_0x00bf:
            r0 = move-exception
            goto L_0x00c3
        L_0x00c1:
            r0 = move-exception
            r2 = r14
        L_0x00c3:
            r1 = r0
            r17 = r2
            r16 = r21
            goto L_0x00de
        L_0x00c9:
            r0 = move-exception
            r2 = r14
            r1 = r0
            r17 = r2
            r16 = r12
            goto L_0x00de
        L_0x00d1:
            r0 = move-exception
            r17 = r1
            r16 = r12
            goto L_0x00dd
        L_0x00d7:
            r0 = move-exception
            r17 = r1
            r13 = r12
            r16 = r13
        L_0x00dd:
            r1 = r0
        L_0x00de:
            if (r13 == 0) goto L_0x012f
            org.apache.http.StatusLine r1 = r13.getStatusLine()
            int r1 = r1.getStatusCode()
            java.lang.String r2 = "Unexpected response code %d for %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
            r3[r11] = r4
            r4 = 1
            java.lang.String r5 = r24.getUrl()
            r3[r4] = r5
            com.google.android.gms.internal.zzs.zzc(r2, r3)
            if (r16 == 0) goto L_0x0129
            com.google.android.gms.internal.zzi r2 = new com.google.android.gms.internal.zzi
            r18 = 0
            long r3 = android.os.SystemClock.elapsedRealtime()
            long r19 = r3 - r9
            r14 = r2
            r15 = r1
            r14.<init>(r15, r16, r17, r18, r19)
            r3 = 401(0x191, float:5.62E-43)
            if (r1 == r3) goto L_0x011d
            r3 = 403(0x193, float:5.65E-43)
            if (r1 != r3) goto L_0x0117
            goto L_0x011d
        L_0x0117:
            com.google.android.gms.internal.zzp r1 = new com.google.android.gms.internal.zzp
            r1.<init>(r2)
            throw r1
        L_0x011d:
            java.lang.String r1 = "auth"
            com.google.android.gms.internal.zza r3 = new com.google.android.gms.internal.zza
            r3.<init>(r2)
            a(r1, r8, r3)
            goto L_0x0008
        L_0x0129:
            com.google.android.gms.internal.zzh r1 = new com.google.android.gms.internal.zzh
            r1.<init>(r12)
            throw r1
        L_0x012f:
            com.google.android.gms.internal.zzj r2 = new com.google.android.gms.internal.zzj
            r2.<init>(r1)
            throw r2
        L_0x0135:
            r0 = move-exception
            r1 = r0
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = "Bad URL "
            java.lang.String r4 = r24.getUrl()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            int r5 = r4.length()
            if (r5 == 0) goto L_0x014e
            java.lang.String r3 = r3.concat(r4)
            goto L_0x0154
        L_0x014e:
            java.lang.String r4 = new java.lang.String
            r4.<init>(r3)
            r3 = r4
        L_0x0154:
            r2.<init>(r3, r1)
            throw r2
        L_0x0158:
            java.lang.String r1 = "connection"
            com.google.android.gms.internal.zzq r2 = new com.google.android.gms.internal.zzq
            r2.<init>()
            goto L_0x0167
        L_0x0160:
            java.lang.String r1 = "socket"
            com.google.android.gms.internal.zzq r2 = new com.google.android.gms.internal.zzq
            r2.<init>()
        L_0x0167:
            a(r1, r8, r2)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzt.zza(com.google.android.gms.internal.zzk):com.google.android.gms.internal.zzi");
    }
}
