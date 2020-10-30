package com.facebook.internal;

import com.facebook.Request;
import com.facebook.RequestBatch;

public class CacheableRequestBatch extends RequestBatch {
    private String a;
    private boolean b;

    public CacheableRequestBatch() {
    }

    public CacheableRequestBatch(Request... requestArr) {
        super(requestArr);
    }

    public final String getCacheKeyOverride() {
        return this.a;
    }

    public final void setCacheKeyOverride(String str) {
        this.a = str;
    }

    public final boolean getForceRoundTrip() {
        return this.b;
    }

    public final void setForceRoundTrip(boolean z) {
        this.b = z;
    }
}
