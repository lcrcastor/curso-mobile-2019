package com.squareup.picasso;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

class Stats {
    final HandlerThread a = new HandlerThread("Picasso-Stats", 10);
    final Cache b;
    final Handler c;
    long d;
    long e;
    long f;
    long g;
    long h;
    long i;
    long j;
    long k;
    int l;
    int m;
    int n;

    static class StatsHandler extends Handler {
        private final Stats a;

        public StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.a = stats;
        }

        public void handleMessage(final Message message) {
            switch (message.what) {
                case 0:
                    this.a.d();
                    return;
                case 1:
                    this.a.e();
                    return;
                case 2:
                    this.a.b((long) message.arg1);
                    return;
                case 3:
                    this.a.c((long) message.arg1);
                    return;
                case 4:
                    this.a.a((Long) message.obj);
                    return;
                default:
                    Picasso.a.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unhandled stats message.");
                            sb.append(message.what);
                            throw new AssertionError(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    Stats(Cache cache) {
        this.b = cache;
        this.a.start();
        Utils.a(this.a.getLooper());
        this.c = new StatsHandler(this.a.getLooper(), this);
    }

    /* access modifiers changed from: 0000 */
    public void a(Bitmap bitmap) {
        a(bitmap, 2);
    }

    /* access modifiers changed from: 0000 */
    public void b(Bitmap bitmap) {
        a(bitmap, 3);
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2) {
        this.c.sendMessage(this.c.obtainMessage(4, Long.valueOf(j2)));
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.c.sendEmptyMessage(0);
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.c.sendEmptyMessage(1);
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.a.quit();
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        this.d++;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        this.e++;
    }

    /* access modifiers changed from: 0000 */
    public void a(Long l2) {
        this.l++;
        this.f += l2.longValue();
        this.i = a(this.l, this.f);
    }

    /* access modifiers changed from: 0000 */
    public void b(long j2) {
        this.m++;
        this.g += j2;
        this.j = a(this.m, this.g);
    }

    /* access modifiers changed from: 0000 */
    public void c(long j2) {
        this.n++;
        this.h += j2;
        this.k = a(this.m, this.h);
    }

    /* access modifiers changed from: 0000 */
    public StatsSnapshot f() {
        int maxSize = this.b.maxSize();
        int size = this.b.size();
        long j2 = this.d;
        long j3 = this.e;
        long j4 = this.f;
        long j5 = this.g;
        long j6 = this.h;
        long j7 = this.i;
        long j8 = this.j;
        long j9 = this.k;
        int i2 = this.l;
        long j10 = j9;
        int i3 = this.m;
        long j11 = j8;
        long j12 = j10;
        long j13 = j7;
        StatsSnapshot statsSnapshot = new StatsSnapshot(maxSize, size, j2, j3, j4, j5, j6, j13, j11, j12, i2, i3, this.n, System.currentTimeMillis());
        return statsSnapshot;
    }

    private void a(Bitmap bitmap, int i2) {
        this.c.sendMessage(this.c.obtainMessage(i2, Utils.a(bitmap), 0));
    }

    private static long a(int i2, long j2) {
        return j2 / ((long) i2);
    }
}
