package com.squareup.okhttp.internal.spdy;

import java.util.Arrays;

public final class Settings {
    private int a;
    private int b;
    private int c;
    private final int[] d = new int[10];

    /* access modifiers changed from: 0000 */
    public void a() {
        this.c = 0;
        this.b = 0;
        this.a = 0;
        Arrays.fill(this.d, 0);
    }

    /* access modifiers changed from: 0000 */
    public Settings a(int i, int i2, int i3) {
        if (i >= this.d.length) {
            return this;
        }
        int i4 = 1 << i;
        this.a |= i4;
        if ((i2 & 1) != 0) {
            this.b |= i4;
        } else {
            this.b &= i4 ^ -1;
        }
        if ((i2 & 2) != 0) {
            this.c |= i4;
        } else {
            this.c &= i4 ^ -1;
        }
        this.d[i] = i3;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i) {
        return ((1 << i) & this.a) != 0;
    }

    /* access modifiers changed from: 0000 */
    public int b(int i) {
        return this.d[i];
    }

    /* access modifiers changed from: 0000 */
    public int c(int i) {
        int i2 = g(i) ? 2 : 0;
        return f(i) ? i2 | 1 : i2;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return Integer.bitCount(this.a);
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        if ((this.a & 2) != 0) {
            return this.d[1];
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public int d(int i) {
        return (this.a & 32) != 0 ? this.d[5] : i;
    }

    /* access modifiers changed from: 0000 */
    public int e(int i) {
        return (this.a & 128) != 0 ? this.d[7] : i;
    }

    /* access modifiers changed from: 0000 */
    public boolean f(int i) {
        return ((1 << i) & this.b) != 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean g(int i) {
        return ((1 << i) & this.c) != 0;
    }

    /* access modifiers changed from: 0000 */
    public void a(Settings settings) {
        for (int i = 0; i < 10; i++) {
            if (settings.a(i)) {
                a(i, settings.c(i), settings.b(i));
            }
        }
    }
}
