package com.indra.httpclient.request;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;

public class CustomRetryPolicy extends DefaultRetryPolicy {
    private int a = 0;

    public CustomRetryPolicy(int i, int i2, float f) {
        super(i, i2, f);
        this.a = i2;
    }

    public void retry(VolleyError volleyError) {
        if (this.a == 0) {
            throw volleyError;
        }
        super.retry(volleyError);
    }
}
