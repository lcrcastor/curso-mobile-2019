package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import java.util.BitSet;

@GwtIncompatible
final class SmallCharMatcher extends NamedFastMatcher {
    private final char[] a;
    private final boolean b;
    private final long c;

    private SmallCharMatcher(char[] cArr, long j, boolean z, String str) {
        super(str);
        this.a = cArr;
        this.c = j;
        this.b = z;
    }

    static int a(int i) {
        return Integer.rotateLeft(i * -862048943, 15) * 461845907;
    }

    private boolean c(int i) {
        return 1 == ((this.c >> i) & 1);
    }

    @VisibleForTesting
    static int b(int i) {
        if (i == 1) {
            return 2;
        }
        int highestOneBit = Integer.highestOneBit(i - 1) << 1;
        while (((double) highestOneBit) * 0.5d < ((double) i)) {
            highestOneBit <<= 1;
        }
        return highestOneBit;
    }

    static CharMatcher a(BitSet bitSet, String str) {
        int i;
        int cardinality = bitSet.cardinality();
        boolean z = bitSet.get(0);
        char[] cArr = new char[b(cardinality)];
        int length = cArr.length - 1;
        int nextSetBit = bitSet.nextSetBit(0);
        long j = 0;
        while (nextSetBit != -1) {
            long j2 = j | (1 << nextSetBit);
            int a2 = a(nextSetBit);
            while (true) {
                i = a2 & length;
                if (cArr[i] == 0) {
                    break;
                }
                a2 = i + 1;
            }
            cArr[i] = (char) nextSetBit;
            nextSetBit = bitSet.nextSetBit(nextSetBit + 1);
            j = j2;
        }
        SmallCharMatcher smallCharMatcher = new SmallCharMatcher(cArr, j, z, str);
        return smallCharMatcher;
    }

    public boolean matches(char c2) {
        if (c2 == 0) {
            return this.b;
        }
        if (!c(c2)) {
            return false;
        }
        int length = this.a.length - 1;
        int a2 = a((int) c2) & length;
        int i = a2;
        while (this.a[i] != 0) {
            if (this.a[i] == c2) {
                return true;
            }
            i = (i + 1) & length;
            if (i == a2) {
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void a(BitSet bitSet) {
        char[] cArr;
        if (this.b) {
            bitSet.set(0);
        }
        for (char c2 : this.a) {
            if (c2 != 0) {
                bitSet.set(c2);
            }
        }
    }
}
