package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class Equivalence<T> {

    static final class EquivalentToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<T> a;
        @Nullable
        private final T b;

        EquivalentToPredicate(Equivalence<T> equivalence, @Nullable T t) {
            this.a = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.b = t;
        }

        public boolean apply(@Nullable T t) {
            return this.a.equivalent(t, this.b);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EquivalentToPredicate)) {
                return false;
            }
            EquivalentToPredicate equivalentToPredicate = (EquivalentToPredicate) obj;
            if (!this.a.equals(equivalentToPredicate.a) || !Objects.equal(this.b, equivalentToPredicate.b)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".equivalentTo(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    public static final class Wrapper<T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Equivalence<? super T> a;
        @Nullable
        private final T b;

        private Wrapper(Equivalence<? super T> equivalence, @Nullable T t) {
            this.a = (Equivalence) Preconditions.checkNotNull(equivalence);
            this.b = t;
        }

        @Nullable
        public T get() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Wrapper) {
                Wrapper wrapper = (Wrapper) obj;
                if (this.a.equals(wrapper.a)) {
                    return this.a.equivalent(this.b, wrapper.b);
                }
            }
            return false;
        }

        public int hashCode() {
            return this.a.hash(this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".wrap(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class Equals extends Equivalence<Object> implements Serializable {
        static final Equals a = new Equals();
        private static final long serialVersionUID = 1;

        Equals() {
        }

        /* access modifiers changed from: protected */
        public boolean doEquivalent(Object obj, Object obj2) {
            return obj.equals(obj2);
        }

        /* access modifiers changed from: protected */
        public int doHash(Object obj) {
            return obj.hashCode();
        }

        private Object readResolve() {
            return a;
        }
    }

    static final class Identity extends Equivalence<Object> implements Serializable {
        static final Identity a = new Identity();
        private static final long serialVersionUID = 1;

        /* access modifiers changed from: protected */
        public boolean doEquivalent(Object obj, Object obj2) {
            return false;
        }

        Identity() {
        }

        /* access modifiers changed from: protected */
        public int doHash(Object obj) {
            return System.identityHashCode(obj);
        }

        private Object readResolve() {
            return a;
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean doEquivalent(T t, T t2);

    /* access modifiers changed from: protected */
    public abstract int doHash(T t);

    protected Equivalence() {
    }

    public final boolean equivalent(@Nullable T t, @Nullable T t2) {
        if (t == t2) {
            return true;
        }
        if (t == null || t2 == null) {
            return false;
        }
        return doEquivalent(t, t2);
    }

    public final int hash(@Nullable T t) {
        if (t == null) {
            return 0;
        }
        return doHash(t);
    }

    public final <F> Equivalence<F> onResultOf(Function<F, ? extends T> function) {
        return new FunctionalEquivalence(function, this);
    }

    public final <S extends T> Wrapper<S> wrap(@Nullable S s) {
        return new Wrapper<>(s);
    }

    @GwtCompatible(serializable = true)
    public final <S extends T> Equivalence<Iterable<S>> pairwise() {
        return new PairwiseEquivalence(this);
    }

    public final Predicate<T> equivalentTo(@Nullable T t) {
        return new EquivalentToPredicate(this, t);
    }

    public static Equivalence<Object> equals() {
        return Equals.a;
    }

    public static Equivalence<Object> identity() {
        return Identity.a;
    }
}
