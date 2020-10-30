package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache.Entry;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.impl.cookie.DateUtils;

public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static int a = 3000;
    private static int b = 4096;
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(b));
    }

    public BasicNetwork(HttpStack httpStack, ByteArrayPool byteArrayPool) {
        this.mHttpStack = httpStack;
        this.mPool = byteArrayPool;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0074, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0075, code lost:
        r1 = r0;
        r17 = null;
        r18 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d5, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d7, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00d8, code lost:
        r23 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00da, code lost:
        r1 = r0;
        r17 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e7, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e8, code lost:
        r18 = r1;
        r17 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00ed, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00ee, code lost:
        r18 = r1;
        r14 = null;
        r17 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f3, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f6, code lost:
        r2 = r14.getStatusLine().getStatusCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0100, code lost:
        if (r2 == 301) goto L_0x0119;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0105, code lost:
        com.android.volley.VolleyLog.e("Unexpected response code %d for %s", java.lang.Integer.valueOf(r2), r25.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0119, code lost:
        com.android.volley.VolleyLog.e("Request at %s has been redirected to %s", r25.getOriginUrl(), r25.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x012c, code lost:
        if (r17 != null) goto L_0x012e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x012e, code lost:
        r15 = new com.android.volley.NetworkResponse(r2, r17, r18, false, android.os.SystemClock.elapsedRealtime() - r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0140, code lost:
        if (r2 == 401) goto L_0x015e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0147, code lost:
        if (r2 == 301) goto L_0x0152;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0151, code lost:
        throw new com.android.volley.ServerError(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0152, code lost:
        a("redirect", r8, new com.android.volley.RedirectError(r15));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x015e, code lost:
        a("auth", r8, new com.android.volley.AuthFailureError(r15));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x016f, code lost:
        throw new com.android.volley.NetworkError(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x0175, code lost:
        throw new com.android.volley.NoConnectionError(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0176, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0177, code lost:
        r1 = r0;
        r3 = new java.lang.StringBuilder();
        r3.append("Bad URL ");
        r3.append(r25.getUrl());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0192, code lost:
        throw new java.lang.RuntimeException(r3.toString(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0193, code lost:
        a("connection", r8, new com.android.volley.TimeoutError());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x019f, code lost:
        a("socket", r8, new com.android.volley.TimeoutError());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0176 A[ExcHandler: MalformedURLException (r0v0 'e' java.net.MalformedURLException A[CUSTOM_DECLARE]), Splitter:B:2:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:79:? A[ExcHandler: ConnectTimeoutException (unused org.apache.http.conn.ConnectTimeoutException), SYNTHETIC, Splitter:B:2:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:81:? A[ExcHandler: SocketTimeoutException (unused java.net.SocketTimeoutException), SYNTHETIC, Splitter:B:2:0x0012] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0170 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.android.volley.NetworkResponse performRequest(com.android.volley.Request<?> r25) {
        /*
            r24 = this;
            r7 = r24
            r8 = r25
            long r9 = android.os.SystemClock.elapsedRealtime()
        L_0x0008:
            java.util.Map r1 = java.util.Collections.emptyMap()
            r2 = 0
            r11 = 0
            r12 = 302(0x12e, float:4.23E-43)
            r13 = 301(0x12d, float:4.22E-43)
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            r3.<init>()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            com.android.volley.Cache$Entry r4 = r25.getCacheEntry()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            r7.a(r3, r4)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            com.android.volley.toolbox.HttpStack r4 = r7.mHttpStack     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            org.apache.http.HttpResponse r14 = r4.performRequest(r8, r3)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00ed }
            org.apache.http.StatusLine r6 = r14.getStatusLine()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00e7 }
            int r15 = r6.getStatusCode()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00e7 }
            org.apache.http.Header[] r3 = r14.getAllHeaders()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00e7 }
            java.util.Map r5 = convertHeaders(r3)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00e7 }
            r1 = 304(0x130, float:4.26E-43)
            if (r15 != r1) goto L_0x007c
            com.android.volley.Cache$Entry r1 = r25.getCacheEntry()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            if (r1 != 0) goto L_0x0055
            com.android.volley.NetworkResponse r1 = new com.android.volley.NetworkResponse     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r17 = 304(0x130, float:4.26E-43)
            r18 = 0
            r20 = 1
            long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r6 = 0
            long r21 = r3 - r9
            r16 = r1
            r19 = r5
            r16.<init>(r17, r18, r19, r20, r21)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            return r1
        L_0x0055:
            java.util.Map<java.lang.String, java.lang.String> r3 = r1.responseHeaders     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r3.putAll(r5)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            com.android.volley.NetworkResponse r3 = new com.android.volley.NetworkResponse     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r16 = 304(0x130, float:4.26E-43)
            byte[] r4 = r1.data     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r1.responseHeaders     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r19 = 1
            long r17 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            r6 = 0
            long r20 = r17 - r9
            r15 = r3
            r17 = r4
            r18 = r1
            r15.<init>(r16, r17, r18, r19, r20)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            return r3
        L_0x0074:
            r0 = move-exception
            r1 = r0
            r17 = r2
            r18 = r5
            goto L_0x00f4
        L_0x007c:
            if (r15 == r13) goto L_0x0080
            if (r15 != r12) goto L_0x008b
        L_0x0080:
            java.lang.String r1 = "Location"
            java.lang.Object r1 = r5.get(r1)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00de }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00de }
            r8.setRedirectUrl(r1)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00de }
        L_0x008b:
            org.apache.http.HttpEntity r1 = r14.getEntity()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00de }
            if (r1 == 0) goto L_0x009a
            org.apache.http.HttpEntity r1 = r14.getEntity()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            byte[] r1 = r7.a(r1)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x0074 }
            goto L_0x009c
        L_0x009a:
            byte[] r1 = new byte[r11]     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00de }
        L_0x009c:
            r22 = r1
            long r1 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d7 }
            r3 = 0
            long r3 = r1 - r9
            r1 = r7
            r2 = r3
            r4 = r8
            r23 = r5
            r5 = r22
            r1.a(r2, r4, r5, r6)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            r1 = 200(0xc8, float:2.8E-43)
            if (r15 < r1) goto L_0x00cf
            r1 = 299(0x12b, float:4.19E-43)
            if (r15 <= r1) goto L_0x00b8
            goto L_0x00cf
        L_0x00b8:
            com.android.volley.NetworkResponse r1 = new com.android.volley.NetworkResponse     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            r19 = 0
            long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            r4 = 0
            long r20 = r2 - r9
            r2 = r15
            r15 = r1
            r16 = r2
            r17 = r22
            r18 = r23
            r15.<init>(r16, r17, r18, r19, r20)     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            return r1
        L_0x00cf:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            r1.<init>()     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
            throw r1     // Catch:{ SocketTimeoutException -> 0x019f, ConnectTimeoutException -> 0x0193, MalformedURLException -> 0x0176, IOException -> 0x00d5 }
        L_0x00d5:
            r0 = move-exception
            goto L_0x00da
        L_0x00d7:
            r0 = move-exception
            r23 = r5
        L_0x00da:
            r1 = r0
            r17 = r22
            goto L_0x00e4
        L_0x00de:
            r0 = move-exception
            r23 = r5
            r1 = r0
            r17 = r2
        L_0x00e4:
            r18 = r23
            goto L_0x00f4
        L_0x00e7:
            r0 = move-exception
            r18 = r1
            r17 = r2
            goto L_0x00f3
        L_0x00ed:
            r0 = move-exception
            r18 = r1
            r14 = r2
            r17 = r14
        L_0x00f3:
            r1 = r0
        L_0x00f4:
            if (r14 == 0) goto L_0x0170
            org.apache.http.StatusLine r2 = r14.getStatusLine()
            int r2 = r2.getStatusCode()
            r3 = 1
            r4 = 2
            if (r2 == r13) goto L_0x0119
            if (r2 != r12) goto L_0x0105
            goto L_0x0119
        L_0x0105:
            java.lang.String r5 = "Unexpected response code %d for %s"
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            r4[r11] = r6
            java.lang.String r6 = r25.getUrl()
            r4[r3] = r6
            com.android.volley.VolleyLog.e(r5, r4)
            goto L_0x012c
        L_0x0119:
            java.lang.String r5 = "Request at %s has been redirected to %s"
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r6 = r25.getOriginUrl()
            r4[r11] = r6
            java.lang.String r6 = r25.getUrl()
            r4[r3] = r6
            com.android.volley.VolleyLog.e(r5, r4)
        L_0x012c:
            if (r17 == 0) goto L_0x016a
            com.android.volley.NetworkResponse r1 = new com.android.volley.NetworkResponse
            r19 = 0
            long r3 = android.os.SystemClock.elapsedRealtime()
            long r20 = r3 - r9
            r15 = r1
            r16 = r2
            r15.<init>(r16, r17, r18, r19, r20)
            r3 = 401(0x191, float:5.62E-43)
            if (r2 == r3) goto L_0x015e
            r3 = 403(0x193, float:5.65E-43)
            if (r2 != r3) goto L_0x0147
            goto L_0x015e
        L_0x0147:
            if (r2 == r13) goto L_0x0152
            if (r2 != r12) goto L_0x014c
            goto L_0x0152
        L_0x014c:
            com.android.volley.ServerError r2 = new com.android.volley.ServerError
            r2.<init>(r1)
            throw r2
        L_0x0152:
            java.lang.String r2 = "redirect"
            com.android.volley.RedirectError r3 = new com.android.volley.RedirectError
            r3.<init>(r1)
            a(r2, r8, r3)
            goto L_0x0008
        L_0x015e:
            java.lang.String r2 = "auth"
            com.android.volley.AuthFailureError r3 = new com.android.volley.AuthFailureError
            r3.<init>(r1)
            a(r2, r8, r3)
            goto L_0x0008
        L_0x016a:
            com.android.volley.NetworkError r2 = new com.android.volley.NetworkError
            r2.<init>(r1)
            throw r2
        L_0x0170:
            com.android.volley.NoConnectionError r2 = new com.android.volley.NoConnectionError
            r2.<init>(r1)
            throw r2
        L_0x0176:
            r0 = move-exception
            r1 = r0
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Bad URL "
            r3.append(r4)
            java.lang.String r4 = r25.getUrl()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3, r1)
            throw r2
        L_0x0193:
            java.lang.String r1 = "connection"
            com.android.volley.TimeoutError r2 = new com.android.volley.TimeoutError
            r2.<init>()
            a(r1, r8, r2)
            goto L_0x0008
        L_0x019f:
            java.lang.String r1 = "socket"
            com.android.volley.TimeoutError r2 = new com.android.volley.TimeoutError
            r2.<init>()
            a(r1, r8, r2)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.BasicNetwork.performRequest(com.android.volley.Request):com.android.volley.NetworkResponse");
    }

    private void a(long j, Request<?> request, byte[] bArr, StatusLine statusLine) {
        if (DEBUG || j > ((long) a)) {
            String str = "HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]";
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(j);
            objArr[2] = bArr != null ? Integer.valueOf(bArr.length) : "null";
            objArr[3] = Integer.valueOf(statusLine.getStatusCode());
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d(str, objArr);
        }
    }

    private static void a(String str, Request<?> request, VolleyError volleyError) {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int timeoutMs = request.getTimeoutMs();
        try {
            retryPolicy.retry(volleyError);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{str, Integer.valueOf(timeoutMs)}));
            throw e;
        }
    }

    private void a(Map<String, String> map, Entry entry) {
        if (entry != null) {
            if (entry.etag != null) {
                map.put("If-None-Match", entry.etag);
            }
            if (entry.lastModified > 0) {
                map.put("If-Modified-Since", DateUtils.formatDate(new Date(entry.lastModified)));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void logError(String str, String str2, long j) {
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", str, Long.valueOf(SystemClock.elapsedRealtime() - j), str2);
    }

    private byte[] a(HttpEntity httpEntity) {
        PoolingByteArrayOutputStream poolingByteArrayOutputStream = new PoolingByteArrayOutputStream(this.mPool, (int) httpEntity.getContentLength());
        byte[] bArr = null;
        try {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                throw new ServerError();
            }
            byte[] buf = this.mPool.getBuf(1024);
            while (true) {
                try {
                    int read = content.read(buf);
                    if (read == -1) {
                        break;
                    }
                    poolingByteArrayOutputStream.write(buf, 0, read);
                } catch (Throwable th) {
                    th = th;
                    bArr = buf;
                    try {
                        httpEntity.consumeContent();
                    } catch (IOException unused) {
                        VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
                    }
                    this.mPool.returnBuf(bArr);
                    poolingByteArrayOutputStream.close();
                    throw th;
                }
            }
            byte[] byteArray = poolingByteArrayOutputStream.toByteArray();
            try {
                httpEntity.consumeContent();
            } catch (IOException unused2) {
                VolleyLog.v("Error occured when calling consumingContent", new Object[0]);
            }
            this.mPool.returnBuf(buf);
            poolingByteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            httpEntity.consumeContent();
            this.mPool.returnBuf(bArr);
            poolingByteArrayOutputStream.close();
            throw th;
        }
    }

    protected static Map<String, String> convertHeaders(Header[] headerArr) {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headerArr.length; i++) {
            treeMap.put(headerArr[i].getName(), headerArr[i].getValue());
        }
        return treeMap;
    }
}
