package org.bouncycastle.crypto.params;

public class GOST3410ValidationParameters {
    private int a;
    private int b;
    private long c;
    private long d;

    public GOST3410ValidationParameters(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public GOST3410ValidationParameters(long j, long j2) {
        this.c = j;
        this.d = j2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410ValidationParameters)) {
            return false;
        }
        GOST3410ValidationParameters gOST3410ValidationParameters = (GOST3410ValidationParameters) obj;
        return gOST3410ValidationParameters.b == this.b && gOST3410ValidationParameters.a == this.a && gOST3410ValidationParameters.d == this.d && gOST3410ValidationParameters.c == this.c;
    }

    public int getC() {
        return this.b;
    }

    public long getCL() {
        return this.d;
    }

    public int getX0() {
        return this.a;
    }

    public long getX0L() {
        return this.c;
    }

    public int hashCode() {
        return ((((this.a ^ this.b) ^ ((int) this.c)) ^ ((int) (this.c >> 32))) ^ ((int) this.d)) ^ ((int) (this.d >> 32));
    }
}
