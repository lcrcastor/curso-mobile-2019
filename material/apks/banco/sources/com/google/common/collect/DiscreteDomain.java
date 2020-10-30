package com.google.common.collect;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@GwtCompatible
public abstract class DiscreteDomain<C extends Comparable> {

    static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
        /* access modifiers changed from: private */
        public static final BigIntegerDomain a = new BigIntegerDomain();
        private static final BigInteger b = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger c = BigInteger.valueOf(Long.MAX_VALUE);
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        private BigIntegerDomain() {
        }

        /* renamed from: a */
        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        /* renamed from: b */
        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }

        /* renamed from: a */
        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(b).min(c).longValue();
        }

        private Object readResolve() {
            return a;
        }
    }

    static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
        /* access modifiers changed from: private */
        public static final IntegerDomain a = new IntegerDomain();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        private IntegerDomain() {
        }

        /* renamed from: a */
        public Integer next(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MAX_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue + 1);
        }

        /* renamed from: b */
        public Integer previous(Integer num) {
            int intValue = num.intValue();
            if (intValue == Integer.MIN_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue - 1);
        }

        /* renamed from: a */
        public long distance(Integer num, Integer num2) {
            return ((long) num2.intValue()) - ((long) num.intValue());
        }

        /* renamed from: a */
        public Integer minValue() {
            return Integer.valueOf(Integer.MIN_VALUE);
        }

        /* renamed from: b */
        public Integer maxValue() {
            return Integer.valueOf(SubsamplingScaleImageView.TILE_SIZE_AUTO);
        }

        private Object readResolve() {
            return a;
        }
    }

    static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
        /* access modifiers changed from: private */
        public static final LongDomain a = new LongDomain();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        private LongDomain() {
        }

        /* renamed from: a */
        public Long next(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MAX_VALUE) {
                return null;
            }
            return Long.valueOf(longValue + 1);
        }

        /* renamed from: b */
        public Long previous(Long l) {
            long longValue = l.longValue();
            if (longValue == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(longValue - 1);
        }

        /* renamed from: a */
        public long distance(Long l, Long l2) {
            long longValue = l2.longValue() - l.longValue();
            if (l2.longValue() > l.longValue() && longValue < 0) {
                return Long.MAX_VALUE;
            }
            if (l2.longValue() >= l.longValue() || longValue <= 0) {
                return longValue;
            }
            return Long.MIN_VALUE;
        }

        /* renamed from: a */
        public Long minValue() {
            return Long.valueOf(Long.MIN_VALUE);
        }

        /* renamed from: b */
        public Long maxValue() {
            return Long.valueOf(Long.MAX_VALUE);
        }

        private Object readResolve() {
            return a;
        }
    }

    public abstract long distance(C c, C c2);

    public abstract C next(C c);

    public abstract C previous(C c);

    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.a;
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.a;
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.a;
    }

    protected DiscreteDomain() {
    }

    @CanIgnoreReturnValue
    public C minValue() {
        throw new NoSuchElementException();
    }

    @CanIgnoreReturnValue
    public C maxValue() {
        throw new NoSuchElementException();
    }
}
