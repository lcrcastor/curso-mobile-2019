package cz.msebera.android.httpclient.pool;

import cz.msebera.android.httpclient.annotation.Immutable;

@Immutable
public class PoolStats {
    private final int a;
    private final int b;
    private final int c;
    private final int d;

    public PoolStats(int i, int i2, int i3, int i4) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    public int getLeased() {
        return this.a;
    }

    public int getPending() {
        return this.b;
    }

    public int getAvailable() {
        return this.c;
    }

    public int getMax() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[leased: ");
        sb.append(this.a);
        sb.append("; pending: ");
        sb.append(this.b);
        sb.append("; available: ");
        sb.append(this.c);
        sb.append("; max: ");
        sb.append(this.d);
        sb.append("]");
        return sb.toString();
    }
}
