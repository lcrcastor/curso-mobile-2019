package org.bouncycastle.math.ec;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import java.math.BigInteger;

class SimpleBigDecimal {
    private final BigInteger a;
    private final int b;

    public SimpleBigDecimal(BigInteger bigInteger, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("scale may not be negative");
        }
        this.a = bigInteger;
        this.b = i;
    }

    private void c(SimpleBigDecimal simpleBigDecimal) {
        if (this.b != simpleBigDecimal.b) {
            throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
        }
    }

    public SimpleBigDecimal a() {
        return new SimpleBigDecimal(this.a.negate(), this.b);
    }

    public SimpleBigDecimal a(int i) {
        if (i >= 0) {
            return i == this.b ? this : new SimpleBigDecimal(this.a.shiftLeft(i - this.b), i);
        }
        throw new IllegalArgumentException("scale may not be negative");
    }

    public SimpleBigDecimal a(BigInteger bigInteger) {
        return new SimpleBigDecimal(this.a.subtract(bigInteger.shiftLeft(this.b)), this.b);
    }

    public SimpleBigDecimal a(SimpleBigDecimal simpleBigDecimal) {
        c(simpleBigDecimal);
        return new SimpleBigDecimal(this.a.add(simpleBigDecimal.a), this.b);
    }

    public int b(BigInteger bigInteger) {
        return this.a.compareTo(bigInteger.shiftLeft(this.b));
    }

    public BigInteger b() {
        return this.a.shiftRight(this.b);
    }

    public SimpleBigDecimal b(SimpleBigDecimal simpleBigDecimal) {
        return a(simpleBigDecimal.a());
    }

    public BigInteger c() {
        return a(new SimpleBigDecimal(ECConstants.ONE, 1).a(this.b)).b();
    }

    public int d() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimpleBigDecimal)) {
            return false;
        }
        SimpleBigDecimal simpleBigDecimal = (SimpleBigDecimal) obj;
        return this.a.equals(simpleBigDecimal.a) && this.b == simpleBigDecimal.b;
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b;
    }

    public String toString() {
        if (this.b == 0) {
            return this.a.toString();
        }
        BigInteger b2 = b();
        BigInteger subtract = this.a.subtract(b2.shiftLeft(this.b));
        if (this.a.signum() == -1) {
            subtract = ECConstants.ONE.shiftLeft(this.b).subtract(subtract);
        }
        if (b2.signum() == -1 && !subtract.equals(ECConstants.ZERO)) {
            b2 = b2.add(ECConstants.ONE);
        }
        String bigInteger = b2.toString();
        char[] cArr = new char[this.b];
        String bigInteger2 = subtract.toString(2);
        int length = bigInteger2.length();
        int i = this.b - length;
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = TarjetasConstants.ULT_NUM_AMEX;
        }
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i + i3] = bigInteger2.charAt(i3);
        }
        String str = new String(cArr);
        StringBuffer stringBuffer = new StringBuffer(bigInteger);
        stringBuffer.append(".");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
