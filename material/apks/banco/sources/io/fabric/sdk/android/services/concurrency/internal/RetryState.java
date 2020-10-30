package io.fabric.sdk.android.services.concurrency.internal;

public class RetryState {
    private final int a;
    private final Backoff b;
    private final RetryPolicy c;

    public RetryState(Backoff backoff, RetryPolicy retryPolicy) {
        this(0, backoff, retryPolicy);
    }

    public RetryState(int i, Backoff backoff, RetryPolicy retryPolicy) {
        this.a = i;
        this.b = backoff;
        this.c = retryPolicy;
    }

    public int getRetryCount() {
        return this.a;
    }

    public long getRetryDelay() {
        return this.b.getDelayMillis(this.a);
    }

    public Backoff getBackoff() {
        return this.b;
    }

    public RetryPolicy getRetryPolicy() {
        return this.c;
    }

    public RetryState nextRetryState() {
        return new RetryState(this.a + 1, this.b, this.c);
    }

    public RetryState initialRetryState() {
        return new RetryState(this.b, this.c);
    }
}
