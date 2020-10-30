package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtCompatible
public final class Suppliers {

    static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Function<? super F, T> a;
        final Supplier<F> b;

        SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
            this.a = function;
            this.b = supplier;
        }

        public T get() {
            return this.a.apply(this.b.get());
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof SupplierComposition)) {
                return false;
            }
            SupplierComposition supplierComposition = (SupplierComposition) obj;
            if (this.a.equals(supplierComposition.a) && this.b.equals(supplierComposition.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.compose(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final T a;

        SupplierOfInstance(@Nullable T t) {
            this.a = t;
        }

        public T get() {
            return this.a;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof SupplierOfInstance)) {
                return false;
            }
            return Objects.equal(this.a, ((SupplierOfInstance) obj).a);
        }

        public int hashCode() {
            return Objects.hashCode(this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.ofInstance(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    @VisibleForTesting
    static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier<T> a;
        final long b;
        volatile transient T c;
        volatile transient long d;

        ExpiringMemoizingSupplier(Supplier<T> supplier, long j, TimeUnit timeUnit) {
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
            this.b = timeUnit.toNanos(j);
            Preconditions.checkArgument(j > 0);
        }

        public T get() {
            long j = this.d;
            long a2 = Platform.a();
            if (j == 0 || a2 - j >= 0) {
                synchronized (this) {
                    if (j == this.d) {
                        T t = this.a.get();
                        this.c = t;
                        long j2 = a2 + this.b;
                        if (j2 == 0) {
                            j2 = 1;
                        }
                        this.d = j2;
                        return t;
                    }
                }
            }
            return this.c;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoizeWithExpiration(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(", NANOS)");
            return sb.toString();
        }
    }

    @VisibleForTesting
    static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier<T> a;
        volatile transient boolean b;
        transient T c;

        MemoizingSupplier(Supplier<T> supplier) {
            this.a = supplier;
        }

        public T get() {
            if (!this.b) {
                synchronized (this) {
                    if (!this.b) {
                        T t = this.a.get();
                        this.c = t;
                        this.b = true;
                        return t;
                    }
                }
            }
            return this.c;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.memoize(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    interface SupplierFunction extends Function {
    }

    enum SupplierFunctionImpl implements SupplierFunction<Object> {
        INSTANCE;

        public String toString() {
            return "Suppliers.supplierFunction()";
        }

        /* renamed from: a */
        public Object apply(Supplier<Object> supplier) {
            return supplier.get();
        }
    }

    static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Supplier<T> a;

        ThreadSafeSupplier(Supplier<T> supplier) {
            this.a = supplier;
        }

        public T get() {
            T t;
            synchronized (this.a) {
                t = this.a.get();
            }
            return t;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Suppliers.synchronizedSupplier(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    private Suppliers() {
    }

    public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(supplier);
        return new SupplierComposition(function, supplier);
    }

    public static <T> Supplier<T> memoize(Supplier<T> supplier) {
        return supplier instanceof MemoizingSupplier ? supplier : new MemoizingSupplier((Supplier) Preconditions.checkNotNull(supplier));
    }

    public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> supplier, long j, TimeUnit timeUnit) {
        return new ExpiringMemoizingSupplier(supplier, j, timeUnit);
    }

    public static <T> Supplier<T> ofInstance(@Nullable T t) {
        return new SupplierOfInstance(t);
    }

    public static <T> Supplier<T> synchronizedSupplier(Supplier<T> supplier) {
        return new ThreadSafeSupplier((Supplier) Preconditions.checkNotNull(supplier));
    }

    public static <T> Function<Supplier<T>, T> supplierFunction() {
        return SupplierFunctionImpl.INSTANCE;
    }
}
