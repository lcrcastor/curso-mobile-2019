package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.os.SystemClock;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.SSLException;

class RetryHandler implements HttpRequestRetryHandler {
    private static HashSet<Class<?>> a = new HashSet<>();
    private static HashSet<Class<?>> b = new HashSet<>();
    private final int c;

    static {
        a.add(NoHttpResponseException.class);
        a.add(UnknownHostException.class);
        a.add(SocketException.class);
        b.add(InterruptedIOException.class);
        b.add(SSLException.class);
    }

    public RetryHandler(int i) {
        this.c = i;
    }

    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        Boolean bool = (Boolean) httpContext.getAttribute("http.request_sent");
        boolean z = false;
        if (bool == null || !bool.booleanValue()) {
        }
        if (i <= this.c && !a(b, iOException)) {
            boolean a2 = a(a, iOException);
            z = true;
        }
        if (z) {
            z = !((HttpUriRequest) httpContext.getAttribute("http.request")).getMethod().equals("POST");
        }
        if (z) {
            SystemClock.sleep(1500);
        } else {
            iOException.printStackTrace();
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public boolean a(HashSet<Class<?>> hashSet, Throwable th) {
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((Class) it.next()).isInstance(th)) {
                return true;
            }
        }
        return false;
    }
}
