package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.RetryState;

class RetryManager {
    long a;
    private RetryState b;

    public RetryManager(RetryState retryState) {
        if (retryState == null) {
            throw new NullPointerException("retryState must not be null");
        }
        this.b = retryState;
    }

    public boolean a(long j) {
        return j - this.a >= this.b.getRetryDelay() * 1000000;
    }

    public void b(long j) {
        this.a = j;
        this.b = this.b.nextRetryState();
    }

    public void a() {
        this.a = 0;
        this.b = this.b.initialRetryState();
    }
}
