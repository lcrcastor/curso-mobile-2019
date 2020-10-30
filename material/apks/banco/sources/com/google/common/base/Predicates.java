package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Predicates {
    /* access modifiers changed from: private */
    public static final Joiner a = Joiner.on(',');

    static class AndPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> a;

        private AndPredicate(List<? extends Predicate<? super T>> list) {
            this.a = list;
        }

        public boolean apply(@Nullable T t) {
            for (int i = 0; i < this.a.size(); i++) {
                if (!((Predicate) this.a.get(i)).apply(t)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.a.hashCode() + 306654252;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof AndPredicate)) {
                return false;
            }
            return this.a.equals(((AndPredicate) obj).a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.and(");
            sb.append(Predicates.a.join((Iterable<?>) this.a));
            sb.append(")");
            return sb.toString();
        }
    }

    static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {
        private static final long serialVersionUID = 0;
        final Predicate<B> a;
        final Function<A, ? extends B> b;

        private CompositionPredicate(Predicate<B> predicate, Function<A, ? extends B> function) {
            this.a = (Predicate) Preconditions.checkNotNull(predicate);
            this.b = (Function) Preconditions.checkNotNull(function);
        }

        public boolean apply(@Nullable A a2) {
            return this.a.apply(this.b.apply(a2));
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof CompositionPredicate)) {
                return false;
            }
            CompositionPredicate compositionPredicate = (CompositionPredicate) obj;
            if (this.b.equals(compositionPredicate.b) && this.a.equals(compositionPredicate.a)) {
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

    @GwtIncompatible
    static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
        private static final long serialVersionUID = 0;
        final CommonPattern a;

        ContainsPatternPredicate(CommonPattern commonPattern) {
            this.a = (CommonPattern) Preconditions.checkNotNull(commonPattern);
        }

        /* renamed from: a */
        public boolean apply(CharSequence charSequence) {
            return this.a.a(charSequence).b();
        }

        public int hashCode() {
            return Objects.hashCode(this.a.a(), Integer.valueOf(this.a.b()));
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof ContainsPatternPredicate)) {
                return false;
            }
            ContainsPatternPredicate containsPatternPredicate = (ContainsPatternPredicate) obj;
            if (Objects.equal(this.a.a(), containsPatternPredicate.a.a()) && this.a.b() == containsPatternPredicate.a.b()) {
                z = true;
            }
            return z;
        }

        public String toString() {
            String toStringHelper = MoreObjects.toStringHelper((Object) this.a).add("pattern", (Object) this.a.a()).add("pattern.flags", this.a.b()).toString();
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.contains(");
            sb.append(toStringHelper);
            sb.append(")");
            return sb.toString();
        }
    }

    static class InPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Collection<?> a;

        private InPredicate(Collection<?> collection) {
            this.a = (Collection) Preconditions.checkNotNull(collection);
        }

        public boolean apply(@Nullable T t) {
            try {
                return this.a.contains(t);
            } catch (NullPointerException unused) {
                return false;
            } catch (ClassCastException unused2) {
                return false;
            }
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof InPredicate)) {
                return false;
            }
            return this.a.equals(((InPredicate) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.in(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    @GwtIncompatible
    static class InstanceOfPredicate implements Predicate<Object>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> a;

        private InstanceOfPredicate(Class<?> cls) {
            this.a = (Class) Preconditions.checkNotNull(cls);
        }

        public boolean apply(@Nullable Object obj) {
            return this.a.isInstance(obj);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof InstanceOfPredicate)) {
                return false;
            }
            if (this.a == ((InstanceOfPredicate) obj).a) {
                z = true;
            }
            return z;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.instanceOf(");
            sb.append(this.a.getName());
            sb.append(")");
            return sb.toString();
        }
    }

    static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final T a;

        private IsEqualToPredicate(T t) {
            this.a = t;
        }

        public boolean apply(T t) {
            return this.a.equals(t);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof IsEqualToPredicate)) {
                return false;
            }
            return this.a.equals(((IsEqualToPredicate) obj).a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.equalTo(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static class NotPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Predicate<T> a;

        NotPredicate(Predicate<T> predicate) {
            this.a = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public boolean apply(@Nullable T t) {
            return !this.a.apply(t);
        }

        public int hashCode() {
            return this.a.hashCode() ^ -1;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof NotPredicate)) {
                return false;
            }
            return this.a.equals(((NotPredicate) obj).a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.not(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum ObjectPredicate implements Predicate<Object> {
        ALWAYS_TRUE {
            public boolean apply(@Nullable Object obj) {
                return true;
            }

            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        },
        ALWAYS_FALSE {
            public boolean apply(@Nullable Object obj) {
                return false;
            }

            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        },
        IS_NULL {
            public boolean apply(@Nullable Object obj) {
                return obj == null;
            }

            public String toString() {
                return "Predicates.isNull()";
            }
        },
        NOT_NULL {
            public boolean apply(@Nullable Object obj) {
                return obj != null;
            }

            public String toString() {
                return "Predicates.notNull()";
            }
        };

        /* access modifiers changed from: 0000 */
        public <T> Predicate<T> a() {
            return this;
        }
    }

    static class OrPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> a;

        private OrPredicate(List<? extends Predicate<? super T>> list) {
            this.a = list;
        }

        public boolean apply(@Nullable T t) {
            for (int i = 0; i < this.a.size(); i++) {
                if (((Predicate) this.a.get(i)).apply(t)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode() + 87855567;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof OrPredicate)) {
                return false;
            }
            return this.a.equals(((OrPredicate) obj).a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.or(");
            sb.append(Predicates.a.join((Iterable<?>) this.a));
            sb.append(")");
            return sb.toString();
        }
    }

    @GwtIncompatible
    static class SubtypeOfPredicate implements Predicate<Class<?>>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> a;

        private SubtypeOfPredicate(Class<?> cls) {
            this.a = (Class) Preconditions.checkNotNull(cls);
        }

        /* renamed from: a */
        public boolean apply(Class<?> cls) {
            return this.a.isAssignableFrom(cls);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof SubtypeOfPredicate)) {
                return false;
            }
            if (this.a == ((SubtypeOfPredicate) obj).a) {
                z = true;
            }
            return z;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.subtypeOf(");
            sb.append(this.a.getName());
            sb.append(")");
            return sb.toString();
        }
    }

    @GwtIncompatible
    static class ContainsPatternFromStringPredicate extends ContainsPatternPredicate {
        private static final long serialVersionUID = 0;

        ContainsPatternFromStringPredicate(String str) {
            super(Platform.b(str));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Predicates.containsPattern(");
            sb.append(this.a.a());
            sb.append(")");
            return sb.toString();
        }
    }

    private Predicates() {
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.a();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> iterable) {
        return new AndPredicate(a(iterable));
    }

    public static <T> Predicate<T> and(Predicate<? super T>... predicateArr) {
        return new AndPredicate(a((T[]) predicateArr));
    }

    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new AndPredicate(a((Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> iterable) {
        return new OrPredicate(a(iterable));
    }

    public static <T> Predicate<T> or(Predicate<? super T>... predicateArr) {
        return new OrPredicate(a((T[]) predicateArr));
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new OrPredicate(a((Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> equalTo(@Nullable T t) {
        return t == null ? isNull() : new IsEqualToPredicate(t);
    }

    @GwtIncompatible
    public static Predicate<Object> instanceOf(Class<?> cls) {
        return new InstanceOfPredicate(cls);
    }

    @GwtIncompatible
    @Deprecated
    @Beta
    public static Predicate<Class<?>> assignableFrom(Class<?> cls) {
        return subtypeOf(cls);
    }

    @GwtIncompatible
    @Beta
    public static Predicate<Class<?>> subtypeOf(Class<?> cls) {
        return new SubtypeOfPredicate(cls);
    }

    public static <T> Predicate<T> in(Collection<? extends T> collection) {
        return new InPredicate(collection);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate(predicate, function);
    }

    @GwtIncompatible
    public static Predicate<CharSequence> containsPattern(String str) {
        return new ContainsPatternFromStringPredicate(str);
    }

    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(new JdkPattern(pattern));
    }

    private static <T> List<Predicate<? super T>> a(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return Arrays.asList(new Predicate[]{predicate, predicate2});
    }

    private static <T> List<T> a(T... tArr) {
        return a((Iterable<T>) Arrays.asList(tArr));
    }

    static <T> List<T> a(Iterable<T> iterable) {
        ArrayList arrayList = new ArrayList();
        for (T checkNotNull : iterable) {
            arrayList.add(Preconditions.checkNotNull(checkNotNull));
        }
        return arrayList;
    }
}
