package com.google.android.gms.analytics.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.zzac;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

class zzah extends zzd {
    /* access modifiers changed from: private */
    public static final byte[] c = "\n".getBytes();
    private final String a = a("GoogleAnalytics", zze.VERSION, VERSION.RELEASE, zzao.zza(Locale.getDefault()), Build.MODEL, Build.ID);
    private final zzal b;

    class zza {
        private int b;
        private ByteArrayOutputStream c = new ByteArrayOutputStream();

        public zza() {
        }

        public int a() {
            return this.b;
        }

        public boolean a(zzab zzab) {
            zzac.zzy(zzab);
            if (this.b + 1 > zzah.this.zzaap().zzadf()) {
                return false;
            }
            String a2 = zzah.this.a(zzab, false);
            if (a2 == null) {
                zzah.this.zzaao().zza(zzab, "Error formatting hit");
                return true;
            }
            byte[] bytes = a2.getBytes();
            int length = bytes.length;
            if (length > zzah.this.zzaap().zzacx()) {
                zzah.this.zzaao().zza(zzab, "Hit size exceeds the maximum size limit");
                return true;
            }
            if (this.c.size() > 0) {
                length++;
            }
            if (this.c.size() + length > zzah.this.zzaap().zzacz()) {
                return false;
            }
            try {
                if (this.c.size() > 0) {
                    this.c.write(zzah.c);
                }
                this.c.write(bytes);
                this.b++;
                return true;
            } catch (IOException e) {
                zzah.this.zze("Failed to write payload when batching hits", e);
                return true;
            }
        }

        public byte[] b() {
            return this.c.toByteArray();
        }
    }

    zzah(zzf zzf) {
        super(zzf);
        this.b = new zzal(zzf.zzaan());
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0086 A[SYNTHETIC, Splitter:B:36:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009d A[SYNTHETIC, Splitter:B:46:0x009d] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00a9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(java.net.URL r4, byte[] r5) {
        /*
            r3 = this;
            com.google.android.gms.common.internal.zzac.zzy(r4)
            com.google.android.gms.common.internal.zzac.zzy(r5)
            java.lang.String r0 = "POST bytes, url"
            int r1 = r5.length
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r3.zzb(r0, r1, r4)
            boolean r0 = r3.zzue()
            if (r0 == 0) goto L_0x0020
            java.lang.String r0 = "Post payload\n"
            java.lang.String r1 = new java.lang.String
            r1.<init>(r5)
            r3.zza(r0, r1)
        L_0x0020:
            r0 = 0
            android.content.Context r1 = r3.getContext()     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            r3.a(r1)     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            java.net.HttpURLConnection r4 = r3.a(r4)     // Catch:{ IOException -> 0x007d, all -> 0x007a }
            r1 = 1
            r4.setDoOutput(r1)     // Catch:{ IOException -> 0x0078 }
            int r1 = r5.length     // Catch:{ IOException -> 0x0078 }
            r4.setFixedLengthStreamingMode(r1)     // Catch:{ IOException -> 0x0078 }
            r4.connect()     // Catch:{ IOException -> 0x0078 }
            java.io.OutputStream r1 = r4.getOutputStream()     // Catch:{ IOException -> 0x0078 }
            r1.write(r5)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r3.a(r4)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            int r5 = r4.getResponseCode()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r0 = 200(0xc8, float:2.8E-43)
            if (r5 != r0) goto L_0x0054
            com.google.android.gms.analytics.internal.zzb r0 = r3.zzxu()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r0.b()     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
        L_0x0054:
            java.lang.String r0 = "POST status"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            r3.zzb(r0, r2)     // Catch:{ IOException -> 0x0075, all -> 0x0072 }
            if (r1 == 0) goto L_0x0069
            r1.close()     // Catch:{ IOException -> 0x0063 }
            goto L_0x0069
        L_0x0063:
            r0 = move-exception
            java.lang.String r1 = "Error closing http post connection output stream"
            r3.zze(r1, r0)
        L_0x0069:
            if (r4 == 0) goto L_0x006e
            r4.disconnect()
        L_0x006e:
            r3.b()
            return r5
        L_0x0072:
            r5 = move-exception
            r0 = r1
            goto L_0x009b
        L_0x0075:
            r5 = move-exception
            r0 = r1
            goto L_0x007f
        L_0x0078:
            r5 = move-exception
            goto L_0x007f
        L_0x007a:
            r5 = move-exception
            r4 = r0
            goto L_0x009b
        L_0x007d:
            r5 = move-exception
            r4 = r0
        L_0x007f:
            java.lang.String r1 = "Network POST connection error"
            r3.zzd(r1, r5)     // Catch:{ all -> 0x009a }
            if (r0 == 0) goto L_0x0090
            r0.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x0090
        L_0x008a:
            r5 = move-exception
            java.lang.String r0 = "Error closing http post connection output stream"
            r3.zze(r0, r5)
        L_0x0090:
            if (r4 == 0) goto L_0x0095
            r4.disconnect()
        L_0x0095:
            r3.b()
            r4 = 0
            return r4
        L_0x009a:
            r5 = move-exception
        L_0x009b:
            if (r0 == 0) goto L_0x00a7
            r0.close()     // Catch:{ IOException -> 0x00a1 }
            goto L_0x00a7
        L_0x00a1:
            r0 = move-exception
            java.lang.String r1 = "Error closing http post connection output stream"
            r3.zze(r1, r0)
        L_0x00a7:
            if (r4 == 0) goto L_0x00ac
            r4.disconnect()
        L_0x00ac:
            r3.b()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.a(java.net.URL, byte[]):int");
    }

    private static String a(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    private URL a(zzab zzab, String str) {
        String valueOf;
        String valueOf2;
        StringBuilder sb;
        if (zzab.zzaes()) {
            valueOf = String.valueOf(zzaap().zzadh());
            valueOf2 = String.valueOf(zzaap().zzadj());
            sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length() + String.valueOf(str).length());
        } else {
            valueOf = String.valueOf(zzaap().zzadi());
            valueOf2 = String.valueOf(zzaap().zzadj());
            sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length() + String.valueOf(str).length());
        }
        sb.append(valueOf);
        sb.append(valueOf2);
        sb.append("?");
        sb.append(str);
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private void a(StringBuilder sb, String str, String str2) {
        if (sb.length() != 0) {
            sb.append('&');
        }
        sb.append(URLEncoder.encode(str, "UTF-8"));
        sb.append('=');
        sb.append(URLEncoder.encode(str2, "UTF-8"));
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0022 A[SYNTHETIC, Splitter:B:18:0x0022] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.net.HttpURLConnection r3) {
        /*
            r2 = this;
            java.io.InputStream r3 = r3.getInputStream()     // Catch:{ all -> 0x001e }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x001c }
        L_0x0008:
            int r1 = r3.read(r0)     // Catch:{ all -> 0x001c }
            if (r1 <= 0) goto L_0x000f
            goto L_0x0008
        L_0x000f:
            if (r3 == 0) goto L_0x001b
            r3.close()     // Catch:{ IOException -> 0x0015 }
            return
        L_0x0015:
            r3 = move-exception
            java.lang.String r0 = "Error closing http connection input stream"
            r2.zze(r0, r3)
        L_0x001b:
            return
        L_0x001c:
            r0 = move-exception
            goto L_0x0020
        L_0x001e:
            r0 = move-exception
            r3 = 0
        L_0x0020:
            if (r3 == 0) goto L_0x002c
            r3.close()     // Catch:{ IOException -> 0x0026 }
            goto L_0x002c
        L_0x0026:
            r3 = move-exception
            java.lang.String r1 = "Error closing http connection input stream"
            r2.zze(r1, r3)
        L_0x002c:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.a(java.net.HttpURLConnection):void");
    }

    private boolean a(zzab zzab) {
        String str;
        zzaf zzaao;
        String str2;
        zzac.zzy(zzab);
        String a2 = a(zzab, !zzab.zzaes());
        if (a2 == null) {
            zzaao = zzaao();
            str2 = "Error formatting hit for upload";
        } else {
            if (a2.length() <= zzaap().zzacw()) {
                URL a3 = a(zzab, a2);
                if (a3 != null) {
                    return b(a3) == 200;
                }
                str = "Failed to build collect GET endpoint url";
            } else {
                String a4 = a(zzab, false);
                if (a4 == null) {
                    zzaao = zzaao();
                    str2 = "Error formatting hit for POST upload";
                } else {
                    byte[] bytes = a4.getBytes();
                    if (bytes.length > zzaap().zzacy()) {
                        zzaao = zzaao();
                        str2 = "Hit payload exceeds size limit";
                    } else {
                        URL b2 = b(zzab);
                        if (b2 != null) {
                            return a(b2, bytes) == 200;
                        }
                        str = "Failed to build collect POST endpoint url";
                    }
                }
            }
            zzet(str);
            return false;
        }
        zzaao.zza(zzab, str2);
        return true;
    }

    private static byte[] a(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b(java.net.URL r5) {
        /*
            r4 = this;
            com.google.android.gms.common.internal.zzac.zzy(r5)
            java.lang.String r0 = "GET request"
            r4.zzb(r0, r5)
            r0 = 0
            java.net.HttpURLConnection r5 = r4.a(r5)     // Catch:{ IOException -> 0x003a, all -> 0x0035 }
            r5.connect()     // Catch:{ IOException -> 0x0033 }
            r4.a(r5)     // Catch:{ IOException -> 0x0033 }
            int r0 = r5.getResponseCode()     // Catch:{ IOException -> 0x0033 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 != r1) goto L_0x0022
            com.google.android.gms.analytics.internal.zzb r1 = r4.zzxu()     // Catch:{ IOException -> 0x0033 }
            r1.b()     // Catch:{ IOException -> 0x0033 }
        L_0x0022:
            java.lang.String r1 = "GET status"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)     // Catch:{ IOException -> 0x0033 }
            r4.zzb(r1, r2)     // Catch:{ IOException -> 0x0033 }
            if (r5 == 0) goto L_0x0030
            r5.disconnect()
        L_0x0030:
            return r0
        L_0x0031:
            r0 = move-exception
            goto L_0x004a
        L_0x0033:
            r0 = move-exception
            goto L_0x003e
        L_0x0035:
            r5 = move-exception
            r3 = r0
            r0 = r5
            r5 = r3
            goto L_0x004a
        L_0x003a:
            r5 = move-exception
            r3 = r0
            r0 = r5
            r5 = r3
        L_0x003e:
            java.lang.String r1 = "Network GET connection error"
            r4.zzd(r1, r0)     // Catch:{ all -> 0x0031 }
            if (r5 == 0) goto L_0x0048
            r5.disconnect()
        L_0x0048:
            r5 = 0
            return r5
        L_0x004a:
            if (r5 == 0) goto L_0x004f
            r5.disconnect()
        L_0x004f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.b(java.net.URL):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00cc A[SYNTHETIC, Splitter:B:43:0x00cc] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00e3 A[SYNTHETIC, Splitter:B:53:0x00e3] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00ef  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b(java.net.URL r10, byte[] r11) {
        /*
            r9 = this;
            com.google.android.gms.common.internal.zzac.zzy(r10)
            com.google.android.gms.common.internal.zzac.zzy(r11)
            r0 = 0
            android.content.Context r1 = r9.getContext()     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r9.a(r1)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            byte[] r1 = a(r11)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.String r2 = "POST compressed size, ratio %, url"
            int r3 = r1.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r4 = 100
            int r6 = r1.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            long r6 = (long) r6     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            long r6 = r6 * r4
            int r4 = r11.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            long r4 = (long) r4     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            long r6 = r6 / r4
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r9.zza(r2, r3, r4, r10)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            int r2 = r1.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            int r3 = r11.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            if (r2 <= r3) goto L_0x0040
            java.lang.String r2 = "Compressed payload is larger then uncompressed. compressed, uncompressed"
            int r3 = r1.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            int r4 = r11.length     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r9.zzc(r2, r3, r4)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
        L_0x0040:
            boolean r2 = r9.zzue()     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            if (r2 == 0) goto L_0x0066
            java.lang.String r2 = "Post payload"
            java.lang.String r3 = "\n"
            java.lang.String r4 = new java.lang.String     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r4.<init>(r11)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            java.lang.String r11 = java.lang.String.valueOf(r4)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            int r4 = r11.length()     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            if (r4 == 0) goto L_0x005e
            java.lang.String r11 = r3.concat(r11)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            goto L_0x0063
        L_0x005e:
            java.lang.String r11 = new java.lang.String     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r11.<init>(r3)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
        L_0x0063:
            r9.zza(r2, r11)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
        L_0x0066:
            java.net.HttpURLConnection r10 = r9.a(r10)     // Catch:{ IOException -> 0x00c3, all -> 0x00c0 }
            r11 = 1
            r10.setDoOutput(r11)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            java.lang.String r11 = "Content-Encoding"
            java.lang.String r2 = "gzip"
            r10.addRequestProperty(r11, r2)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            int r11 = r1.length     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r10.setFixedLengthStreamingMode(r11)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r10.connect()     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            java.io.OutputStream r11 = r10.getOutputStream()     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r11.write(r1)     // Catch:{ IOException -> 0x00b0, all -> 0x00aa }
            r11.close()     // Catch:{ IOException -> 0x00b0, all -> 0x00aa }
            r9.a(r10)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            int r11 = r10.getResponseCode()     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r11 != r1) goto L_0x0098
            com.google.android.gms.analytics.internal.zzb r1 = r9.zzxu()     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r1.b()     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
        L_0x0098:
            java.lang.String r1 = "POST status"
            java.lang.Integer r2 = java.lang.Integer.valueOf(r11)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            r9.zzb(r1, r2)     // Catch:{ IOException -> 0x00bb, all -> 0x00b6 }
            if (r10 == 0) goto L_0x00a6
            r10.disconnect()
        L_0x00a6:
            r9.b()
            return r11
        L_0x00aa:
            r0 = move-exception
            r8 = r11
            r11 = r10
            r10 = r0
            r0 = r8
            goto L_0x00e1
        L_0x00b0:
            r0 = move-exception
            r8 = r11
            r11 = r10
            r10 = r0
            r0 = r8
            goto L_0x00c5
        L_0x00b6:
            r11 = move-exception
            r8 = r11
            r11 = r10
            r10 = r8
            goto L_0x00e1
        L_0x00bb:
            r11 = move-exception
            r8 = r11
            r11 = r10
            r10 = r8
            goto L_0x00c5
        L_0x00c0:
            r10 = move-exception
            r11 = r0
            goto L_0x00e1
        L_0x00c3:
            r10 = move-exception
            r11 = r0
        L_0x00c5:
            java.lang.String r1 = "Network compressed POST connection error"
            r9.zzd(r1, r10)     // Catch:{ all -> 0x00e0 }
            if (r0 == 0) goto L_0x00d6
            r0.close()     // Catch:{ IOException -> 0x00d0 }
            goto L_0x00d6
        L_0x00d0:
            r10 = move-exception
            java.lang.String r0 = "Error closing http compressed post connection output stream"
            r9.zze(r0, r10)
        L_0x00d6:
            if (r11 == 0) goto L_0x00db
            r11.disconnect()
        L_0x00db:
            r9.b()
            r10 = 0
            return r10
        L_0x00e0:
            r10 = move-exception
        L_0x00e1:
            if (r0 == 0) goto L_0x00ed
            r0.close()     // Catch:{ IOException -> 0x00e7 }
            goto L_0x00ed
        L_0x00e7:
            r0 = move-exception
            java.lang.String r1 = "Error closing http compressed post connection output stream"
            r9.zze(r1, r0)
        L_0x00ed:
            if (r11 == 0) goto L_0x00f2
            r11.disconnect()
        L_0x00f2:
            r9.b()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzah.b(java.net.URL, byte[]):int");
    }

    private URL b(zzab zzab) {
        String str;
        String valueOf;
        String valueOf2;
        String str2;
        if (zzab.zzaes()) {
            valueOf = String.valueOf(zzaap().zzadh());
            valueOf2 = String.valueOf(zzaap().zzadj());
            if (valueOf2.length() == 0) {
                str2 = new String(valueOf);
                str = str2;
                return new URL(str);
            }
        } else {
            valueOf = String.valueOf(zzaap().zzadi());
            valueOf2 = String.valueOf(zzaap().zzadj());
            if (valueOf2.length() == 0) {
                str2 = new String(valueOf);
                str = str2;
                return new URL(str);
            }
        }
        str = valueOf.concat(valueOf2);
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private String c(zzab zzab) {
        return String.valueOf(zzab.zzaep());
    }

    private URL d() {
        String valueOf = String.valueOf(zzaap().zzadh());
        String valueOf2 = String.valueOf(zzaap().zzadk());
        try {
            return new URL(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public String a(zzab zzab, boolean z) {
        zzac.zzy(zzab);
        StringBuilder sb = new StringBuilder();
        try {
            for (Entry entry : zzab.zzm().entrySet()) {
                String str = (String) entry.getKey();
                if (!"ht".equals(str)) {
                    if (!"qt".equals(str)) {
                        if (!"AppUID".equals(str)) {
                            if (!"z".equals(str)) {
                                if (!"_gmsv".equals(str)) {
                                    a(sb, str, (String) entry.getValue());
                                }
                            }
                        }
                    }
                }
            }
            a(sb, "ht", String.valueOf(zzab.zzaeq()));
            a(sb, "qt", String.valueOf(zzaan().currentTimeMillis() - zzab.zzaeq()));
            if (zzaap().zzact()) {
                a(sb, "_gmsv", zze.VERSION);
            }
            if (z) {
                long zzaet = zzab.zzaet();
                a(sb, "z", zzaet != 0 ? String.valueOf(zzaet) : c(zzab));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public HttpURLConnection a(URL url) {
        URLConnection openConnection = url.openConnection();
        if (!(openConnection instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
        httpURLConnection.setDefaultUseCaches(false);
        httpURLConnection.setConnectTimeout(zzaap().zzadw());
        httpURLConnection.setReadTimeout(zzaap().zzadx());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", this.a);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    public List<Long> a(List<zzab> list) {
        boolean z;
        zzyl();
        zzaax();
        zzac.zzy(list);
        boolean z2 = false;
        if (zzaap().zzadn().isEmpty() || !this.b.a(zzaap().zzadg() * 1000)) {
            z = false;
        } else {
            z = zzaap().zzadl() != zzm.bG;
            if (zzaap().zzadm() == zzo.GZIP) {
                z2 = true;
            }
        }
        return z ? a(list, z2) : b(list);
    }

    /* access modifiers changed from: 0000 */
    public List<Long> a(List<zzab> list, boolean z) {
        zzac.zzbs(!list.isEmpty());
        zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
        zza zza2 = new zza();
        ArrayList arrayList = new ArrayList();
        for (zzab zzab : list) {
            if (!zza2.a(zzab)) {
                break;
            }
            arrayList.add(Long.valueOf(zzab.zzaep()));
        }
        if (zza2.a() == 0) {
            return arrayList;
        }
        URL d = d();
        if (d == null) {
            zzet("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        int b2 = z ? b(d, zza2.b()) : a(d, zza2.b());
        if (200 == b2) {
            zza("Batched upload completed. Hits batched", Integer.valueOf(zza2.a()));
            return arrayList;
        }
        zza("Network error uploading hits. status code", Integer.valueOf(b2));
        if (zzaap().zzadn().contains(Integer.valueOf(b2))) {
            zzes("Server instructed the client to stop batching");
            this.b.a();
        }
        return Collections.emptyList();
    }

    /* access modifiers changed from: protected */
    public void a(String str) {
    }

    public boolean a() {
        NetworkInfo networkInfo;
        zzyl();
        zzaax();
        try {
            networkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException unused) {
            networkInfo = null;
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        zzep("No network connectivity");
        return false;
    }

    /* access modifiers changed from: 0000 */
    public List<Long> b(List<zzab> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (zzab zzab : list) {
            if (a(zzab)) {
                arrayList.add(Long.valueOf(zzab.zzaep()));
                if (arrayList.size() >= zzaap().zzade()) {
                    break;
                }
            } else {
                return arrayList;
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void b() {
    }

    /* access modifiers changed from: protected */
    public void zzym() {
        zza("Network initialized. User agent", this.a);
    }
}
