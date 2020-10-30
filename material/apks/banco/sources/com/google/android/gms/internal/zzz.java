package com.google.android.gms.internal;

import cz.msebera.android.httpclient.HttpVersion;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class zzz implements zzy {
    private final zza a;
    private final SSLSocketFactory b;

    public interface zza {
        String zzh(String str);
    }

    public zzz() {
        this(null);
    }

    public zzz(zza zza2) {
        this(zza2, null);
    }

    public zzz(zza zza2, SSLSocketFactory sSLSocketFactory) {
        this.a = zza2;
        this.b = sSLSocketFactory;
    }

    private HttpURLConnection a(URL url, zzk<?> zzk) {
        HttpURLConnection zza2 = zza(url);
        int zzs = zzk.zzs();
        zza2.setConnectTimeout(zzs);
        zza2.setReadTimeout(zzs);
        zza2.setUseCaches(false);
        zza2.setDoInput(true);
        if ("https".equals(url.getProtocol()) && this.b != null) {
            ((HttpsURLConnection) zza2).setSSLSocketFactory(this.b);
        }
        return zza2;
    }

    private static HttpEntity a(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException unused) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long) httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        r2.setRequestMethod(r0);
        b(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        r2.setRequestMethod(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(java.net.HttpURLConnection r2, com.google.android.gms.internal.zzk<?> r3) {
        /*
            int r0 = r3.getMethod()
            switch(r0) {
                case -1: goto L_0x0030;
                case 0: goto L_0x002a;
                case 1: goto L_0x0021;
                case 2: goto L_0x001e;
                case 3: goto L_0x001b;
                case 4: goto L_0x0018;
                case 5: goto L_0x0015;
                case 6: goto L_0x0012;
                case 7: goto L_0x000f;
                default: goto L_0x0007;
            }
        L_0x0007:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "Unknown method type."
            r2.<init>(r3)
            throw r2
        L_0x000f:
            java.lang.String r0 = "PATCH"
            goto L_0x0023
        L_0x0012:
            java.lang.String r3 = "TRACE"
            goto L_0x002c
        L_0x0015:
            java.lang.String r3 = "OPTIONS"
            goto L_0x002c
        L_0x0018:
            java.lang.String r3 = "HEAD"
            goto L_0x002c
        L_0x001b:
            java.lang.String r3 = "DELETE"
            goto L_0x002c
        L_0x001e:
            java.lang.String r0 = "PUT"
            goto L_0x0023
        L_0x0021:
            java.lang.String r0 = "POST"
        L_0x0023:
            r2.setRequestMethod(r0)
            b(r2, r3)
            return
        L_0x002a:
            java.lang.String r3 = "GET"
        L_0x002c:
            r2.setRequestMethod(r3)
            return
        L_0x0030:
            byte[] r0 = r3.zzl()
            if (r0 == 0) goto L_0x0057
            r1 = 1
            r2.setDoOutput(r1)
            java.lang.String r1 = "POST"
            r2.setRequestMethod(r1)
            java.lang.String r1 = "Content-Type"
            java.lang.String r3 = r3.zzk()
            r2.addRequestProperty(r1, r3)
            java.io.DataOutputStream r3 = new java.io.DataOutputStream
            java.io.OutputStream r2 = r2.getOutputStream()
            r3.<init>(r2)
            r3.write(r0)
            r3.close()
        L_0x0057:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzz.a(java.net.HttpURLConnection, com.google.android.gms.internal.zzk):void");
    }

    private static void b(HttpURLConnection httpURLConnection, zzk<?> zzk) {
        byte[] zzp = zzk.zzp();
        if (zzp != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", zzk.zzo());
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(zzp);
            dataOutputStream.close();
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection zza(URL url) {
        return (HttpURLConnection) url.openConnection();
    }

    public HttpResponse zza(zzk<?> zzk, Map<String, String> map) {
        String str;
        String url = zzk.getUrl();
        HashMap hashMap = new HashMap();
        hashMap.putAll(zzk.getHeaders());
        hashMap.putAll(map);
        if (this.a != null) {
            str = this.a.zzh(url);
            if (str == null) {
                String str2 = "URL blocked by rewriter: ";
                String valueOf = String.valueOf(url);
                throw new IOException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        } else {
            str = url;
        }
        HttpURLConnection a2 = a(new URL(str), zzk);
        for (String str3 : hashMap.keySet()) {
            a2.addRequestProperty(str3, (String) hashMap.get(str3));
        }
        a(a2, zzk);
        ProtocolVersion protocolVersion = new ProtocolVersion(HttpVersion.HTTP, 1, 1);
        if (a2.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(protocolVersion, a2.getResponseCode(), a2.getResponseMessage()));
        basicHttpResponse.setEntity(a(a2));
        for (Entry entry : a2.getHeaderFields().entrySet()) {
            if (entry.getKey() != null) {
                basicHttpResponse.addHeader(new BasicHeader((String) entry.getKey(), (String) ((List) entry.getValue()).get(0)));
            }
        }
        return basicHttpResponse;
    }
}
