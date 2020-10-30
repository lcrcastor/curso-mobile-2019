package okhttp3.internal.http2;

import java.util.Arrays;

public final class Settings {
    private int a;
    private final int[] b = new int[10];

    /* access modifiers changed from: 0000 */
    public void a() {
        this.a = 0;
        Arrays.fill(this.b, 0);
    }

    /* access modifiers changed from: 0000 */
    public Settings a(int i, int i2) {
        if (i < 0 || i >= this.b.length) {
            return this;
        }
        this.a = (1 << i) | this.a;
        this.b[i] = i2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        return ((1 << i) & this.a) != 0;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i) {
        return this.b[i];
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return Integer.bitCount(this.a);
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        if ((this.a & 2) != 0) {
            return this.b[1];
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public int c(int i) {
        return (this.a & 16) != 0 ? this.b[4] : i;
    }

    /* access modifiers changed from: 0000 */
    public int d(int i) {
        return (this.a & 32) != 0 ? this.b[5] : i;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        if ((this.a & 128) != 0) {
            return this.b[7];
        }
        return 65535;
    }

    /* access modifiers changed from: 0000 */
    public void a(Settings settings) {
        for (int i = 0; i < 10; i++) {
            if (settings.a(i)) {
                a(i, settings.b(i));
            }
        }
    }
}
