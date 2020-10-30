package io.fabric.sdk.android.services.concurrency.internal;

public class ExponentialBackoff implements Backoff {
    private final long a;
    private final int b;

    public ExponentialBackoff(long j) {
        this(j, 2);
    }

    public ExponentialBackoff(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public long getDelayMillis(int i) {
        return (long) (((double) this.a) * Math.pow((double) this.b, (double) i));
    }
}
