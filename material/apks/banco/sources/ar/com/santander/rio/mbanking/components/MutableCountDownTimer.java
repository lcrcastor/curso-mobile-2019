package ar.com.santander.rio.mbanking.components;

import android.os.Handler;
import android.os.SystemClock;

public abstract class MutableCountDownTimer {
    private long a;
    /* access modifiers changed from: private */
    public long b;
    /* access modifiers changed from: private */
    public long c;
    /* access modifiers changed from: private */
    public boolean d = false;
    private Handler e = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0069, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r11) {
            /*
                r10 = this;
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r11 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this
                monitor-enter(r11)
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r0 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                boolean r0 = r0.d     // Catch:{ all -> 0x006a }
                if (r0 == 0) goto L_0x000d
                monitor-exit(r11)     // Catch:{ all -> 0x006a }
                return
            L_0x000d:
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r0 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                long r0 = r0.c     // Catch:{ all -> 0x006a }
                long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x006a }
                r4 = 0
                long r4 = r0 - r2
                r0 = 0
                int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r2 > 0) goto L_0x0026
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r0 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                r0.onFinish()     // Catch:{ all -> 0x006a }
                goto L_0x0068
            L_0x0026:
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r2 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                long r2 = r2.b     // Catch:{ all -> 0x006a }
                int r6 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                r2 = 1
                if (r6 >= 0) goto L_0x0039
                android.os.Message r0 = r10.obtainMessage(r2)     // Catch:{ all -> 0x006a }
                r10.sendMessageDelayed(r0, r4)     // Catch:{ all -> 0x006a }
                goto L_0x0068
            L_0x0039:
                long r6 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x006a }
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r3 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                r3.onTick(r4)     // Catch:{ all -> 0x006a }
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r3 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                long r3 = r3.b     // Catch:{ all -> 0x006a }
                r5 = 0
                long r8 = r6 + r3
                long r3 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x006a }
                r5 = 0
                long r5 = r8 - r3
            L_0x0052:
                int r3 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                if (r3 >= 0) goto L_0x0061
                ar.com.santander.rio.mbanking.components.MutableCountDownTimer r3 = ar.com.santander.rio.mbanking.components.MutableCountDownTimer.this     // Catch:{ all -> 0x006a }
                long r3 = r3.b     // Catch:{ all -> 0x006a }
                r7 = 0
                long r7 = r5 + r3
                r5 = r7
                goto L_0x0052
            L_0x0061:
                android.os.Message r0 = r10.obtainMessage(r2)     // Catch:{ all -> 0x006a }
                r10.sendMessageDelayed(r0, r5)     // Catch:{ all -> 0x006a }
            L_0x0068:
                monitor-exit(r11)     // Catch:{ all -> 0x006a }
                return
            L_0x006a:
                r0 = move-exception
                monitor-exit(r11)     // Catch:{ all -> 0x006a }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: ar.com.santander.rio.mbanking.components.MutableCountDownTimer.AnonymousClass1.handleMessage(android.os.Message):void");
        }
    };

    public abstract void onFinish();

    public abstract void onTick(long j);

    public MutableCountDownTimer(long j, long j2) {
        this.a = j;
        this.b = j2;
    }

    public long getMillisInFuture() {
        return this.a;
    }

    public void setMillisInFuture(long j) {
        this.a = j;
    }

    public long getCountdownInterval() {
        return this.b;
    }

    public void setCountdownInterval(long j) {
        this.b = j;
    }

    public final synchronized void cancel() {
        this.d = true;
        this.e.removeMessages(1);
    }

    public final synchronized MutableCountDownTimer start() {
        this.d = false;
        if (this.a <= 0) {
            onFinish();
            return this;
        }
        this.c = SystemClock.elapsedRealtime() + this.a;
        this.e.sendMessage(this.e.obtainMessage(1));
        return this;
    }
}
