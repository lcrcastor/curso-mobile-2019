package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import java.util.Random;

class RandomBackoff implements Backoff {
    final Backoff a;
    final Random b;
    final double c;

    public RandomBackoff(Backoff backoff, double d) {
        this(backoff, d, new Random());
    }

    public RandomBackoff(Backoff backoff, double d, Random random) {
        if (d < 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
        } else if (backoff == null) {
            throw new NullPointerException("backoff must not be null");
        } else if (random == null) {
            throw new NullPointerException("random must not be null");
        } else {
            this.a = backoff;
            this.c = d;
            this.b = random;
        }
    }

    public long getDelayMillis(int i) {
        return (long) (a() * ((double) this.a.getDelayMillis(i)));
    }

    /* access modifiers changed from: 0000 */
    public double a() {
        double d = 1.0d - this.c;
        return d + (((this.c + 1.0d) - d) * this.b.nextDouble());
    }
}
