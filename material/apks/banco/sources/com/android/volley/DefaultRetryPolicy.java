package com.android.volley;

public class DefaultRetryPolicy implements RetryPolicy {
    public static final float DEFAULT_BACKOFF_MULT = 1.0f;
    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final int DEFAULT_TIMEOUT_MS = 2500;
    private int a;
    private int b;
    private final int c;
    private final float d;

    public DefaultRetryPolicy() {
        this(DEFAULT_TIMEOUT_MS, 0, 1.0f);
    }

    public DefaultRetryPolicy(int i, int i2, float f) {
        this.a = i;
        this.c = i2;
        this.d = f;
    }

    public int getCurrentTimeout() {
        return this.a;
    }

    public int getCurrentRetryCount() {
        return this.b;
    }

    public float getBackoffMultiplier() {
        return this.d;
    }

    public void retry(VolleyError volleyError) {
        this.b++;
        this.a = (int) (((float) this.a) + (((float) this.a) * this.d));
        if (!hasAttemptRemaining()) {
            throw volleyError;
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasAttemptRemaining() {
        return this.b <= this.c;
    }
}
