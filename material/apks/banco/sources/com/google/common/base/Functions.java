package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
public final class Functions {

    static class ConstantFunction<E> implements Function<Object, E>, Serializable {
        private static final long serialVersionUID = 0;
        private final E a;

        public ConstantFunction(@Nullable E e) {
            this.a = e;
        }

        public E apply(@Nullable Object obj) {
            return this.a;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ConstantFunction)) {
                return false;
            }
            return Objects.equal(this.a, ((ConstantFunction) obj).a);
        }

        public int hashCode() {
            if (this.a == null) {
                return 0;
            }
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.constant(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static class ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, ? extends V> a;
        final V b;

        ForMapWithDefault(Map<K, ? extends V> map, @Nullable V v) {
            this.a = (Map) Preconditions.checkNotNull(map);
            this.b = v;
        }

        public V apply(@Nullable K k) {
            V v = this.a.get(k);
            return (v != null || this.a.containsKey(k)) ? v : this.b;
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof ForMapWithDefault)) {
                return false;
            }
            ForMapWithDefault forMapWithDefault = (ForMapWithDefault) obj;
            if (this.a.equals(forMapWithDefault.a) && Objects.equal(this.b, forMapWithDefault.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(this.a, this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forMap(");
            sb.append(this.a);
            sb.append(", defaultValue=");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static class FunctionComposition<A, B, C> implements Function<A, C>, Serializable {
        private static final long serialVersionUID = 0;
        private final Function<B, C> a;
        private final Function<A, ? extends B> b;

        public FunctionComposition(Function<B, C> function, Function<A, ? extends B> function2) {
            this.a = (Function) Preconditions.checkNotNull(function);
            this.b = (Function) Preconditions.checkNotNull(function2);
        }

        public C apply(@Nullable A a2) {
            return this.a.apply(this.b.apply(a2));
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof FunctionComposition)) {
                return false;
            }
            FunctionComposition functionComposition = (FunctionComposition) obj;
            if (this.b.equals(functionComposition.b) && this.a.equals(functionComposition.a)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.b.hashCode() ^ this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static class FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final Map<K, V> a;

        FunctionForMapNoDefault(Map<K, V> map) {
            this.a = (Map) Preconditions.checkNotNull(map);
        }

        public V apply(@Nullable K k) {
            V v = this.a.get(k);
            Preconditions.checkArgument(v != null || this.a.containsKey(k), "Key '%s' not present in map", (Object) k);
            return v;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof FunctionForMapNoDefault)) {
                return false;
            }
            return this.a.equals(((FunctionForMapNoDefault) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forMap(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum IdentityFunction implements Function<Object, Object> {
        INSTANCE;

        @Nullable
        public Object apply(@Nullable Object obj) {
            return obj;
        }

        public String toString() {
            return "Functions.identity()";
        }
    }

    static class PredicateFunction<T> implements Function<T, Boolean>, Serializable {
        private static final long serialVersionUID = 0;
        private final Predicate<T> a;

        private PredicateFunction(Predicate<T> predicate) {
            this.a = (Predicate) Preconditions.checkNotNull(predicate);
        }

        /* renamed from: a */
        public Boolean apply(@Nullable T t) {
            return Boolean.valueOf(this.a.apply(t));
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof PredicateFunction)) {
                return false;
            }
            return this.a.equals(((PredicateFunction) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forPredicate(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static class SupplierFunction<T> implements Function<Object, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<T> a;

        private SupplierFunction(Supplier<T> supplier) {
            this.a = (Supplier) Preconditions.checkNotNull(supplier);
        }

        public T apply(@Nullable Object obj) {
            return this.a.get();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof SupplierFunction)) {
                return false;
            }
            return this.a.equals(((SupplierFunction) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Functions.forSupplier(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum ToStringFunction implements Function<Object, String> {
        INSTANCE;

        public String toString() {
            return "Functions.toStringFunction()";
        }

        /* renamed from: a */
        public String apply(Object obj) {
            Preconditions.checkNotNull(obj);
            return obj.toString();
        }
    }

    private Functions() {
    }

    public static Function<Object, String> toStringFunction() {
        return ToStringFunction.INSTANCE;
    }

    public static <E> Function<E, E> identity() {
        return IdentityFunction.INSTANCE;
    }

    public static <K, V> Function<K, V> forMap(Map<K, V> map) {
        return new FunctionForMapNoDefault(map);
    }

    public static <K, V> Function<K, V> forMap(Map<K, ? extends V> map, @Nullable V v) {
        return new ForMapWithDefault(map, v);
    }

    public static <A, B, C> Function<A, C> compose(Function<B, C> function, Function<A, ? extends B> function2) {
        return new FunctionComposition(function, function2);
    }

    public static <T> Function<T, Boolean> forPredicate(Predicate<T> predicate) {
        return new PredicateFunction(predicate);
    }

    public static <E> Function<Object, E> constant(@Nullable E e) {
        return new ConstantFunction(e);
    }

    public static <T> Function<Object, T> forSupplier(Supplier<T> supplier) {
        return new SupplierFunction(supplier);
    }
}
