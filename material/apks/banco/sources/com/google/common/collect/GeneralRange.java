package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class GeneralRange<T> implements Serializable {
    private final Comparator<? super T> a;
    private final boolean b;
    @Nullable
    private final T c;
    private final BoundType d;
    private final boolean e;
    @Nullable
    private final T f;
    private final BoundType g;

    static <T> GeneralRange<T> a(Comparator<? super T> comparator) {
        GeneralRange generalRange = new GeneralRange(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
        return generalRange;
    }

    static <T> GeneralRange<T> a(Comparator<? super T> comparator, @Nullable T t, BoundType boundType) {
        GeneralRange generalRange = new GeneralRange(comparator, true, t, boundType, false, null, BoundType.OPEN);
        return generalRange;
    }

    static <T> GeneralRange<T> b(Comparator<? super T> comparator, @Nullable T t, BoundType boundType) {
        GeneralRange generalRange = new GeneralRange(comparator, false, null, BoundType.OPEN, true, t, boundType);
        return generalRange;
    }

    private GeneralRange(Comparator<? super T> comparator, boolean z, @Nullable T t, BoundType boundType, boolean z2, @Nullable T t2, BoundType boundType2) {
        this.a = (Comparator) Preconditions.checkNotNull(comparator);
        this.b = z;
        this.e = z2;
        this.c = t;
        this.d = (BoundType) Preconditions.checkNotNull(boundType);
        this.f = t2;
        this.g = (BoundType) Preconditions.checkNotNull(boundType2);
        if (z) {
            comparator.compare(t, t);
        }
        if (z2) {
            comparator.compare(t2, t2);
        }
        if (z && z2) {
            int compare = comparator.compare(t, t2);
            boolean z3 = false;
            Preconditions.checkArgument(compare <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", (Object) t, (Object) t2);
            if (compare == 0) {
                boolean z4 = boundType != BoundType.OPEN;
                if (boundType2 != BoundType.OPEN) {
                    z3 = true;
                }
                Preconditions.checkArgument(z4 | z3);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Comparator<? super T> a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(@Nullable T t) {
        if (!b()) {
            return false;
        }
        int compare = this.a.compare(t, d());
        boolean z = true;
        boolean z2 = compare < 0;
        boolean z3 = compare == 0;
        if (e() != BoundType.OPEN) {
            z = false;
        }
        return (z3 & z) | z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean b(@Nullable T t) {
        if (!c()) {
            return false;
        }
        int compare = this.a.compare(t, f());
        boolean z = true;
        boolean z2 = compare > 0;
        boolean z3 = compare == 0;
        if (g() != BoundType.OPEN) {
            z = false;
        }
        return (z3 & z) | z2;
    }

    /* access modifiers changed from: 0000 */
    public boolean c(@Nullable T t) {
        return !a(t) && !b(t);
    }

    /* access modifiers changed from: 0000 */
    public GeneralRange<T> a(GeneralRange<T> generalRange) {
        BoundType boundType;
        BoundType boundType2;
        Object obj;
        Preconditions.checkNotNull(generalRange);
        Preconditions.checkArgument(this.a.equals(generalRange.a));
        boolean z = this.b;
        Object d2 = d();
        BoundType e2 = e();
        if (!b()) {
            z = generalRange.b;
            d2 = generalRange.d();
            e2 = generalRange.e();
        } else if (generalRange.b()) {
            int compare = this.a.compare(d(), generalRange.d());
            if (compare < 0 || (compare == 0 && generalRange.e() == BoundType.OPEN)) {
                d2 = generalRange.d();
                e2 = generalRange.e();
            }
        }
        boolean z2 = z;
        boolean z3 = this.e;
        Object f2 = f();
        BoundType g2 = g();
        if (!c()) {
            z3 = generalRange.e;
            f2 = generalRange.f();
            g2 = generalRange.g();
        } else if (generalRange.c()) {
            int compare2 = this.a.compare(f(), generalRange.f());
            if (compare2 > 0 || (compare2 == 0 && generalRange.g() == BoundType.OPEN)) {
                f2 = generalRange.f();
                g2 = generalRange.g();
            }
        }
        boolean z4 = z3;
        Object obj2 = f2;
        if (z2 && z4) {
            int compare3 = this.a.compare(d2, obj2);
            if (compare3 > 0 || (compare3 == 0 && e2 == BoundType.OPEN && g2 == BoundType.OPEN)) {
                boundType2 = BoundType.OPEN;
                boundType = BoundType.CLOSED;
                obj = obj2;
                GeneralRange generalRange2 = new GeneralRange(this.a, z2, obj, boundType2, z4, obj2, boundType);
                return generalRange2;
            }
        }
        boundType2 = e2;
        boundType = g2;
        obj = d2;
        GeneralRange generalRange22 = new GeneralRange(this.a, z2, obj, boundType2, z4, obj2, boundType);
        return generalRange22;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof GeneralRange)) {
            return false;
        }
        GeneralRange generalRange = (GeneralRange) obj;
        if (this.a.equals(generalRange.a) && this.b == generalRange.b && this.e == generalRange.e && e().equals(generalRange.e()) && g().equals(generalRange.g()) && Objects.equal(d(), generalRange.d()) && Objects.equal(f(), generalRange.f())) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.a, d(), e(), f(), g());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(":");
        sb.append(this.d == BoundType.CLOSED ? '[' : '(');
        sb.append(this.b ? this.c : "-∞");
        sb.append(',');
        sb.append(this.e ? this.f : "∞");
        sb.append(this.g == BoundType.CLOSED ? ']' : ')');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public T d() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public BoundType e() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public T f() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public BoundType g() {
        return this.g;
    }
}
