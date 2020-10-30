package org.bouncycastle.math.field;

import org.bouncycastle.util.Arrays;

class GF2Polynomial implements Polynomial {
    protected final int[] a;

    GF2Polynomial(int[] iArr) {
        this.a = Arrays.clone(iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GF2Polynomial)) {
            return false;
        }
        return Arrays.areEqual(this.a, ((GF2Polynomial) obj).a);
    }

    public int getDegree() {
        return this.a[this.a.length - 1];
    }

    public int[] getExponentsPresent() {
        return Arrays.clone(this.a);
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }
}
