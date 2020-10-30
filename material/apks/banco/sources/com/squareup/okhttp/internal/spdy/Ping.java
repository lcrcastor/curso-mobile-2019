package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping {
    private final CountDownLatch a = new CountDownLatch(1);
    private long b = -1;
    private long c = -1;

    Ping() {
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.b != -1) {
            throw new IllegalStateException();
        }
        this.b = System.nanoTime();
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        if (this.c != -1 || this.b == -1) {
            throw new IllegalStateException();
        }
        this.c = System.nanoTime();
        this.a.countDown();
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.c != -1 || this.b == -1) {
            throw new IllegalStateException();
        }
        this.c = this.b - 1;
        this.a.countDown();
    }

    public long roundTripTime() {
        this.a.await();
        return this.c - this.b;
    }

    public long roundTripTime(long j, TimeUnit timeUnit) {
        if (this.a.await(j, timeUnit)) {
            return this.c - this.b;
        }
        return -2;
    }
}
