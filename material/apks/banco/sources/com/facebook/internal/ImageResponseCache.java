package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

class ImageResponseCache {
    static final String a = "ImageResponseCache";
    private static volatile FileLruCache b;

    static class BufferedHttpInputStream extends BufferedInputStream {
        HttpURLConnection a;

        BufferedHttpInputStream(InputStream inputStream, HttpURLConnection httpURLConnection) {
            super(inputStream, 8192);
            this.a = httpURLConnection;
        }

        public void close() {
            super.close();
            Utility.disconnectQuietly(this.a);
        }
    }

    ImageResponseCache() {
    }

    static synchronized FileLruCache a(Context context) {
        FileLruCache fileLruCache;
        synchronized (ImageResponseCache.class) {
            if (b == null) {
                b = new FileLruCache(context.getApplicationContext(), a, new Limits());
            }
            fileLruCache = b;
        }
        return fileLruCache;
    }

    static InputStream a(URI uri, Context context) {
        if (uri != null && a(uri)) {
            try {
                return a(context).get(uri.toString());
            } catch (IOException e) {
                Logger.log(LoggingBehavior.CACHE, 5, a, e.toString());
            }
        }
        return null;
    }

    static InputStream a(Context context, HttpURLConnection httpURLConnection) {
        if (httpURLConnection.getResponseCode() != 200) {
            return null;
        }
        URL url = httpURLConnection.getURL();
        InputStream inputStream = httpURLConnection.getInputStream();
        try {
            return a(url.toURI()) ? a(context).interceptAndPut(url.toString(), new BufferedHttpInputStream(inputStream, httpURLConnection)) : inputStream;
        } catch (IOException | URISyntaxException unused) {
            return inputStream;
        }
    }

    private static boolean a(URI uri) {
        if (uri != null) {
            String host = uri.getHost();
            if (host.endsWith("fbcdn.net")) {
                return true;
            }
            if (host.startsWith("fbcdn") && host.endsWith("akamaihd.net")) {
                return true;
            }
        }
        return false;
    }

    static void b(Context context) {
        try {
            a(context).clearCache();
        } catch (IOException e) {
            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
            String str = a;
            StringBuilder sb = new StringBuilder();
            sb.append("clearCache failed ");
            sb.append(e.getMessage());
            Logger.log(loggingBehavior, 5, str, sb.toString());
        }
    }
}
