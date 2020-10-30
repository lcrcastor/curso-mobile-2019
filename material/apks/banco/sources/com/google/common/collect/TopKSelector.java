package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
final class TopKSelector<T> {
    private final int a;
    private final Comparator<? super T> b;
    private final T[] c;
    private int d;
    private T e;

    public static <T> TopKSelector<T> a(int i, Comparator<? super T> comparator) {
        return new TopKSelector<>(comparator, i);
    }

    private TopKSelector(Comparator<? super T> comparator, int i) {
        this.b = (Comparator) Preconditions.checkNotNull(comparator, "comparator");
        this.a = i;
        Preconditions.checkArgument(i >= 0, "k must be nonnegative, was %s", i);
        this.c = (Object[]) new Object[(i * 2)];
        this.d = 0;
        this.e = null;
    }

    public void a(@Nullable T t) {
        if (this.a != 0) {
            if (this.d == 0) {
                this.c[0] = t;
                this.e = t;
                this.d = 1;
            } else if (this.d < this.a) {
                T[] tArr = this.c;
                int i = this.d;
                this.d = i + 1;
                tArr[i] = t;
                if (this.b.compare(t, this.e) > 0) {
                    this.e = t;
                }
            } else if (this.b.compare(t, this.e) < 0) {
                T[] tArr2 = this.c;
                int i2 = this.d;
                this.d = i2 + 1;
                tArr2[i2] = t;
                if (this.d == this.a * 2) {
                    b();
                }
            }
        }
    }

    private void b() {
        int i = (this.a * 2) - 1;
        int log2 = IntMath.log2(i + 0, RoundingMode.CEILING) * 3;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i2 >= i) {
                break;
            }
            int a2 = a(i2, i, ((i2 + i) + 1) >>> 1);
            if (a2 <= this.a) {
                if (a2 >= this.a) {
                    break;
                }
                i2 = Math.max(a2, i2 + 1);
                i4 = a2;
            } else {
                i = a2 - 1;
            }
            i3++;
            if (i3 >= log2) {
                Arrays.sort(this.c, i2, i, this.b);
                break;
            }
        }
        this.d = this.a;
        this.e = this.c[i4];
        while (true) {
            i4++;
            if (i4 >= this.a) {
                return;
            }
            if (this.b.compare(this.c[i4], this.e) > 0) {
                this.e = this.c[i4];
            }
        }
    }

    private int a(int i, int i2, int i3) {
        T t = this.c[i3];
        this.c[i3] = this.c[i2];
        int i4 = i;
        while (i < i2) {
            if (this.b.compare(this.c[i], t) < 0) {
                a(i4, i);
                i4++;
            }
            i++;
        }
        this.c[i2] = this.c[i4];
        this.c[i4] = t;
        return i4;
    }

    private void a(int i, int i2) {
        T t = this.c[i];
        this.c[i] = this.c[i2];
        this.c[i2] = t;
    }

    public void a(Iterator<? extends T> it) {
        while (it.hasNext()) {
            a((T) it.next());
        }
    }

    public List<T> a() {
        Arrays.sort(this.c, 0, this.d, this.b);
        if (this.d > this.a) {
            Arrays.fill(this.c, this.a, this.c.length, null);
            this.d = this.a;
            this.e = this.c[this.a - 1];
        }
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.c, this.d)));
    }
}
