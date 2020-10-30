package com.nineoldandroids.animation;

public class TimeAnimator extends ValueAnimator {
    private TimeListener h;
    private long i = -1;

    public interface TimeListener {
        void onTimeUpdate(TimeAnimator timeAnimator, long j, long j2);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
    }

    /* access modifiers changed from: 0000 */
    public void a(float f) {
    }

    /* access modifiers changed from: 0000 */
    public boolean a(long j) {
        if (this.d == 0) {
            this.d = 1;
            if (this.c < 0) {
                this.b = j;
            } else {
                this.b = j - this.c;
                this.c = -1;
            }
        }
        if (this.h != null) {
            long j2 = j - this.b;
            long j3 = this.i < 0 ? 0 : j - this.i;
            this.i = j;
            this.h.onTimeUpdate(this, j2, j3);
        }
        return false;
    }

    public void setTimeListener(TimeListener timeListener) {
        this.h = timeListener;
    }
}
