package com.indra.httpclient.client;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

public class CustomRetry implements RetryPolicy {
    private int a = 30000;
    private int b = 0;

    public CustomRetry() {
    }

    public CustomRetry(int i) {
        this.a = i;
    }

    public void setCurrentTimeOut(int i) {
        this.a = i;
    }

    public int getCurrentTimeout() {
        return this.a;
    }

    public int getCurrentRetryCount() {
        return this.b;
    }

    public void retry(VolleyError volleyError) {
        throw volleyError;
    }
}
