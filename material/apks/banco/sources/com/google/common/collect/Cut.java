package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@GwtCompatible
abstract class Cut<C extends Comparable> implements Serializable, Comparable<Cut<C>> {
    private static final long serialVersionUID = 0;
    final C a;

    static final class AboveAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final AboveAll b = new AboveAll();
        private static final long serialVersionUID = 0;

        /* renamed from: a */
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Comparable<?> comparable) {
            return false;
        }

        public String toString() {
            return "+∞";
        }

        private AboveAll() {
            super(null);
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> c() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: 0000 */
        public BoundType a() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public BoundType b() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> a(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> b(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public void a(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public void b(StringBuilder sb) {
            sb.append("+∞)");
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> a(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> b(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        private Object readResolve() {
            return b;
        }
    }

    static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public /* synthetic */ int compareTo(Object obj) {
            return Cut.super.compareTo((Cut) obj);
        }

        AboveValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        /* access modifiers changed from: 0000 */
        public boolean a(C c) {
            return Range.a(this.a, (Comparable) c) < 0;
        }

        /* access modifiers changed from: 0000 */
        public BoundType a() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: 0000 */
        public BoundType b() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    Comparable next = discreteDomain.next(this.a);
                    return next == null ? Cut.d() : b(next);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    Comparable next = discreteDomain.next(this.a);
                    return next == null ? Cut.e() : b(next);
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(StringBuilder sb) {
            sb.append('(');
            sb.append(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void b(StringBuilder sb) {
            sb.append(this.a);
            sb.append(']');
        }

        /* access modifiers changed from: 0000 */
        public C a(DiscreteDomain<C> discreteDomain) {
            return discreteDomain.next(this.a);
        }

        /* access modifiers changed from: 0000 */
        public C b(DiscreteDomain<C> discreteDomain) {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> c(DiscreteDomain<C> discreteDomain) {
            Comparable a = a(discreteDomain);
            return a != null ? b(a) : Cut.e();
        }

        public int hashCode() {
            return this.a.hashCode() ^ -1;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(this.a);
            sb.append("\\");
            return sb.toString();
        }
    }

    static final class BelowAll extends Cut<Comparable<?>> {
        /* access modifiers changed from: private */
        public static final BelowAll b = new BelowAll();
        private static final long serialVersionUID = 0;

        /* renamed from: a */
        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Comparable<?> comparable) {
            return true;
        }

        public String toString() {
            return "-∞";
        }

        private BelowAll() {
            super(null);
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> c() {
            throw new IllegalStateException("range unbounded on this side");
        }

        /* access modifiers changed from: 0000 */
        public BoundType a() {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public BoundType b() {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> a(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> b(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        /* access modifiers changed from: 0000 */
        public void a(StringBuilder sb) {
            sb.append("(-∞");
        }

        /* access modifiers changed from: 0000 */
        public void b(StringBuilder sb) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> a(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        /* access modifiers changed from: 0000 */
        public Comparable<?> b(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        public Cut<Comparable<?>> c(DiscreteDomain<Comparable<?>> discreteDomain) {
            try {
                return Cut.b(discreteDomain.minValue());
            } catch (NoSuchElementException unused) {
                return this;
            }
        }

        private Object readResolve() {
            return b;
        }
    }

    static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public /* synthetic */ int compareTo(Object obj) {
            return Cut.super.compareTo((Cut) obj);
        }

        BelowValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        /* access modifiers changed from: 0000 */
        public boolean a(C c) {
            return Range.a(this.a, (Comparable) c) <= 0;
        }

        /* access modifiers changed from: 0000 */
        public BoundType a() {
            return BoundType.CLOSED;
        }

        /* access modifiers changed from: 0000 */
        public BoundType b() {
            return BoundType.OPEN;
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    return this;
                case OPEN:
                    Comparable previous = discreteDomain.previous(this.a);
                    return previous == null ? Cut.d() : new AboveValue<>(previous);
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public Cut<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            switch (boundType) {
                case CLOSED:
                    Comparable previous = discreteDomain.previous(this.a);
                    return previous == null ? Cut.e() : new AboveValue<>(previous);
                case OPEN:
                    return this;
                default:
                    throw new AssertionError();
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(StringBuilder sb) {
            sb.append('[');
            sb.append(this.a);
        }

        /* access modifiers changed from: 0000 */
        public void b(StringBuilder sb) {
            sb.append(this.a);
            sb.append(')');
        }

        /* access modifiers changed from: 0000 */
        public C a(DiscreteDomain<C> discreteDomain) {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public C b(DiscreteDomain<C> discreteDomain) {
            return discreteDomain.previous(this.a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\\");
            sb.append(this.a);
            sb.append("/");
            return sb.toString();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract BoundType a();

    /* access modifiers changed from: 0000 */
    public abstract Cut<C> a(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract C a(DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract void a(StringBuilder sb);

    /* access modifiers changed from: 0000 */
    public abstract boolean a(C c);

    /* access modifiers changed from: 0000 */
    public abstract BoundType b();

    /* access modifiers changed from: 0000 */
    public abstract Cut<C> b(BoundType boundType, DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract C b(DiscreteDomain<C> discreteDomain);

    /* access modifiers changed from: 0000 */
    public abstract void b(StringBuilder sb);

    /* access modifiers changed from: 0000 */
    public Cut<C> c(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    Cut(@Nullable C c) {
        this.a = c;
    }

    /* renamed from: a */
    public int compareTo(Cut<C> cut) {
        if (cut == d()) {
            return 1;
        }
        if (cut == e()) {
            return -1;
        }
        int a2 = Range.a((Comparable) this.a, (Comparable) cut.a);
        if (a2 != 0) {
            return a2;
        }
        return Booleans.compare(this instanceof AboveValue, cut instanceof AboveValue);
    }

    /* access modifiers changed from: 0000 */
    public C c() {
        return this.a;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj instanceof Cut) {
            try {
                if (compareTo((Cut) obj) == 0) {
                    z = true;
                }
                return z;
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    static <C extends Comparable> Cut<C> d() {
        return BelowAll.b;
    }

    static <C extends Comparable> Cut<C> e() {
        return AboveAll.b;
    }

    static <C extends Comparable> Cut<C> b(C c) {
        return new BelowValue(c);
    }

    static <C extends Comparable> Cut<C> c(C c) {
        return new AboveValue(c);
    }
}
