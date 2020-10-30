package org.bouncycastle.crypto.tls;

class DTLSReplayWindow {
    private long a = -1;
    private long b = 0;

    DTLSReplayWindow() {
    }

    /* access modifiers changed from: 0000 */
    public boolean a(long j) {
        if ((j & 281474976710655L) != j) {
            return true;
        }
        if (j <= this.a) {
            long j2 = this.a - j;
            if (j2 >= 64 || (this.b & (1 << ((int) j2))) != 0) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void b(long j) {
        if ((j & 281474976710655L) != j) {
            throw new IllegalArgumentException("'seq' out of range");
        }
        if (j <= this.a) {
            long j2 = this.a - j;
            if (j2 < 64) {
                this.b |= 1 << ((int) j2);
            }
        } else {
            long j3 = j - this.a;
            if (j3 >= 64) {
                this.b = 1;
            } else {
                this.b <<= (int) j3;
                this.b |= 1;
            }
            this.a = j;
        }
    }
}
