package com.twincoders.twinpush.sdk.communications.asyhttp;

import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.AbstractHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class AsyncHttpRequest implements Runnable {
    private final AbstractHttpClient a;
    private final HttpContext b;
    private final HttpUriRequest c;
    private final AsyncHttpResponseHandler d;
    private boolean e;
    private int f;

    public AsyncHttpRequest(AbstractHttpClient abstractHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        this.a = abstractHttpClient;
        this.b = httpContext;
        this.c = httpUriRequest;
        this.d = asyncHttpResponseHandler;
        if (asyncHttpResponseHandler instanceof BinaryHttpResponseHandler) {
            this.e = true;
        }
    }

    public void run() {
        try {
            if (this.d != null) {
                this.d.sendStartMessage();
            }
            b();
            if (this.d != null) {
                this.d.sendFinishMessage();
            }
        } catch (IOException e2) {
            if (this.d != null) {
                this.d.sendFinishMessage();
                if (this.e) {
                    this.d.sendFailureMessage((Throwable) e2, (byte[]) null);
                } else {
                    this.d.sendFailureMessage((Throwable) e2, (String) null);
                }
            }
        }
    }

    private void a() {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                CloseableHttpResponse execute = this.a.execute(this.c, this.b);
                if (!Thread.currentThread().isInterrupted() && this.d != null) {
                    this.d.a(execute);
                }
            } catch (IOException e2) {
                if (!Thread.currentThread().isInterrupted()) {
                    throw e2;
                }
            }
        }
    }

    private void b() {
        HttpRequestRetryHandler httpRequestRetryHandler = this.a.getHttpRequestRetryHandler();
        e = null;
        boolean z = true;
        while (z) {
            try {
                a();
                return;
            } catch (UnknownHostException e2) {
                if (this.d != null) {
                    this.d.sendFailureMessage((Throwable) e2, "can't resolve host");
                }
                return;
            } catch (SocketException e3) {
                if (this.d != null) {
                    this.d.sendFailureMessage((Throwable) e3, "can't resolve host");
                }
                return;
            } catch (SocketTimeoutException e4) {
                if (this.d != null) {
                    this.d.sendFailureMessage((Throwable) e4, "socket time out");
                }
                return;
            } catch (IOException e5) {
                e = e5;
                int i = this.f + 1;
                this.f = i;
                z = httpRequestRetryHandler.retryRequest(e, i, this.b);
            } catch (NullPointerException e6) {
                StringBuilder sb = new StringBuilder();
                sb.append("NPE in HttpClient");
                sb.append(e6.getMessage());
                e = new IOException(sb.toString());
                int i2 = this.f + 1;
                this.f = i2;
                z = httpRequestRetryHandler.retryRequest(e, i2, this.b);
            }
        }
        ConnectException connectException = new ConnectException();
        connectException.initCause(e);
        throw connectException;
    }
}
