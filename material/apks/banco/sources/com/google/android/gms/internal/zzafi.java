package com.google.android.gms.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class zzafi implements zzafj {
    private HttpURLConnection a;

    zzafi() {
    }

    private InputStream a(HttpURLConnection httpURLConnection) {
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            return httpURLConnection.getInputStream();
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Bad response: ");
        sb.append(responseCode);
        String sb2 = sb.toString();
        if (responseCode == 404) {
            throw new FileNotFoundException(sb2);
        } else if (responseCode == 503) {
            throw new zzafl(sb2);
        } else {
            throw new IOException(sb2);
        }
    }

    private void b(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

    /* access modifiers changed from: 0000 */
    public HttpURLConnection a(String str) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        return httpURLConnection;
    }

    public void close() {
        b(this.a);
    }

    public InputStream zzra(String str) {
        this.a = a(str);
        return a(this.a);
    }
}
