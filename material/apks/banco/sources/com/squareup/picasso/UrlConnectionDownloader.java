package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.Build.VERSION;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Downloader.ResponseException;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlConnectionDownloader implements Downloader {
    static volatile Object a;
    private static final Object b = new Object();
    private static final ThreadLocal<StringBuilder> c = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public StringBuilder initialValue() {
            return new StringBuilder();
        }
    };
    private final Context d;

    static class ResponseCacheIcs {
        private ResponseCacheIcs() {
        }

        static Object a(Context context) {
            File b = Utils.b(context);
            HttpResponseCache installed = HttpResponseCache.getInstalled();
            return installed == null ? HttpResponseCache.install(b, Utils.a(b)) : installed;
        }

        static void a(Object obj) {
            try {
                ((HttpResponseCache) obj).close();
            } catch (IOException unused) {
            }
        }
    }

    public UrlConnectionDownloader(Context context) {
        this.d = context.getApplicationContext();
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection openConnection(Uri uri) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(20000);
        return httpURLConnection;
    }

    public Response load(Uri uri, int i) {
        String str;
        if (VERSION.SDK_INT >= 14) {
            a(this.d);
        }
        HttpURLConnection openConnection = openConnection(uri);
        openConnection.setUseCaches(true);
        if (i != 0) {
            if (NetworkPolicy.isOfflineOnly(i)) {
                str = "only-if-cached,max-age=2147483647";
            } else {
                StringBuilder sb = (StringBuilder) c.get();
                sb.setLength(0);
                if (!NetworkPolicy.shouldReadFromDiskCache(i)) {
                    sb.append(HeaderConstants.CACHE_CONTROL_NO_CACHE);
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(i)) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(HeaderConstants.CACHE_CONTROL_NO_STORE);
                }
                str = sb.toString();
            }
            openConnection.setRequestProperty("Cache-Control", str);
        }
        int responseCode = openConnection.getResponseCode();
        if (responseCode >= 300) {
            openConnection.disconnect();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(responseCode);
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(openConnection.getResponseMessage());
            throw new ResponseException(sb2.toString(), i, responseCode);
        }
        long headerFieldInt = (long) openConnection.getHeaderFieldInt("Content-Length", -1);
        return new Response(openConnection.getInputStream(), Utils.a(openConnection.getHeaderField("X-Android-Response-Source")), headerFieldInt);
    }

    public void shutdown() {
        if (VERSION.SDK_INT >= 14 && a != null) {
            ResponseCacheIcs.a(a);
        }
    }

    private static void a(Context context) {
        if (a == null) {
            try {
                synchronized (b) {
                    if (a == null) {
                        a = ResponseCacheIcs.a(context);
                    }
                }
            } catch (IOException unused) {
            }
        }
    }
}
