package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.content.Context;
import android.os.Message;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;

public abstract class SyncHttpClient extends AsyncHttpClient {
    /* access modifiers changed from: private */
    public int a;
    protected AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        /* access modifiers changed from: 0000 */
        public void a(HttpResponse httpResponse) {
            SyncHttpClient.this.a = httpResponse.getStatusLine().getStatusCode();
            super.a(httpResponse);
        }

        /* access modifiers changed from: protected */
        public void sendMessage(Message message) {
            handleMessage(message);
        }

        public void onSuccess(String str) {
            SyncHttpClient.this.result = str;
        }

        public void onFailure(Throwable th, String str) {
            SyncHttpClient.this.result = SyncHttpClient.this.onRequestFailed(th, str);
        }
    };
    protected String result;

    public abstract String onRequestFailed(Throwable th, String str);

    public int getResponseCode() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void sendRequest(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str, AsyncHttpResponseHandler asyncHttpResponseHandler, Context context) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        new AsyncHttpRequest(defaultHttpClient, httpContext, httpUriRequest, asyncHttpResponseHandler).run();
    }

    public void delete(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        delete(str, asyncHttpResponseHandler);
    }

    public String get(String str, RequestParams requestParams) {
        get(str, requestParams, this.responseHandler);
        return this.result;
    }

    public String get(String str) {
        get(str, (RequestParams) null, this.responseHandler);
        return this.result;
    }

    public String put(String str, RequestParams requestParams) {
        put(str, requestParams, this.responseHandler);
        return this.result;
    }

    public String put(String str) {
        put(str, null, this.responseHandler);
        return this.result;
    }

    public String post(String str, RequestParams requestParams) {
        post(str, requestParams, this.responseHandler);
        return this.result;
    }

    public String post(String str) {
        post(str, null, this.responseHandler);
        return this.result;
    }

    public String delete(String str, RequestParams requestParams) {
        delete(str, requestParams, this.responseHandler);
        return this.result;
    }

    public String delete(String str) {
        delete(str, null, this.responseHandler);
        return this.result;
    }
}
