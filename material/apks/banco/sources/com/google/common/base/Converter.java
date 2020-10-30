package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class Converter<A, B> implements Function<A, B> {
    private final boolean a;
    @LazyInit
    private transient Converter<B, A> b;

    static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> a;
        final Converter<B, C> b;

        ConverterComposition(Converter<A, B> converter, Converter<B, C> converter2) {
            this.a = converter;
            this.b = converter2;
        }

        /* access modifiers changed from: protected */
        public C doForward(A a2) {
            throw new AssertionError();
        }

        /* access modifiers changed from: protected */
        public A doBackward(C c) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public C a(@Nullable A a2) {
            return this.b.a(this.a.a(a2));
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public A b(@Nullable C c) {
            return this.a.b(this.b.b(c));
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof ConverterComposition)) {
                return false;
            }
            ConverterComposition converterComposition = (ConverterComposition) obj;
            if (this.a.equals(converterComposition.a) && this.b.equals(converterComposition.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".andThen(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
        private final Function<? super A, ? extends B> a;
        private final Function<? super B, ? extends A> b;

        private FunctionBasedConverter(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
            this.a = (Function) Preconditions.checkNotNull(function);
            this.b = (Function) Preconditions.checkNotNull(function2);
        }

        /* access modifiers changed from: protected */
        public B doForward(A a2) {
            return this.a.apply(a2);
        }

        /* access modifiers changed from: protected */
        public A doBackward(B b2) {
            return this.b.apply(b2);
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof FunctionBasedConverter)) {
                return false;
            }
            FunctionBasedConverter functionBasedConverter = (FunctionBasedConverter) obj;
            if (this.a.equals(functionBasedConverter.a) && this.b.equals(functionBasedConverter.b)) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Converter.from(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {
        private static final long serialVersionUID = 0;
        final Converter<A, B> a;

        ReverseConverter(Converter<A, B> converter) {
            this.a = converter;
        }

        /* access modifiers changed from: protected */
        public A doForward(B b) {
            throw new AssertionError();
        }

        /* access modifiers changed from: protected */
        public B doBackward(A a2) {
            throw new AssertionError();
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public A a(@Nullable B b) {
            return this.a.b(b);
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public B b(@Nullable A a2) {
            return this.a.a(a2);
        }

        public Converter<A, B> reverse() {
            return this.a;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof ReverseConverter)) {
                return false;
            }
            return this.a.equals(((ReverseConverter) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode() ^ -1;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(".reverse()");
            return sb.toString();
        }
    }

    static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {
        static final IdentityConverter a = new IdentityConverter();
        private static final long serialVersionUID = 0;

        /* renamed from: a */
        public IdentityConverter<T> reverse() {
            return this;
        }

        /* access modifiers changed from: protected */
        public T doBackward(T t) {
            return t;
        }

        /* access modifiers changed from: protected */
        public T doForward(T t) {
            return t;
        }

        public String toString() {
            return "Converter.identity()";
        }

        private IdentityConverter() {
        }

        /* access modifiers changed from: 0000 */
        public <S> Converter<T, S> a(Converter<T, S> converter) {
            return (Converter) Preconditions.checkNotNull(converter, "otherConverter");
        }

        private Object readResolve() {
            return a;
        }
    }

    public abstract A doBackward(B b2);

    public abstract B doForward(A a2);

    protected Converter() {
        this(true);
    }

    Converter(boolean z) {
        this.a = z;
    }

    @CanIgnoreReturnValue
    @Nullable
    public final B convert(@Nullable A a2) {
        return a(a2);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public B a(@Nullable A a2) {
        B b2;
        if (!this.a) {
            return doForward(a2);
        }
        if (a2 == null) {
            b2 = null;
        } else {
            b2 = Preconditions.checkNotNull(doForward(a2));
        }
        return b2;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public A b(@Nullable B b2) {
        A a2;
        if (!this.a) {
            return doBackward(b2);
        }
        if (b2 == null) {
            a2 = null;
        } else {
            a2 = Preconditions.checkNotNull(doBackward(b2));
        }
        return a2;
    }

    @CanIgnoreReturnValue
    public Iterable<B> convertAll(final Iterable<? extends A> iterable) {
        Preconditions.checkNotNull(iterable, "fromIterable");
        return new Iterable<B>() {
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    private final Iterator<? extends A> b = iterable.iterator();

                    public boolean hasNext() {
                        return this.b.hasNext();
                    }

                    public B next() {
                        return Converter.this.convert(this.b.next());
                    }

                    public void remove() {
                        this.b.remove();
                    }
                };
            }
        };
    }

    @CanIgnoreReturnValue
    public Converter<B, A> reverse() {
        Converter<B, A> converter = this.b;
        if (converter != null) {
            return converter;
        }
        ReverseConverter reverseConverter = new ReverseConverter(this);
        this.b = reverseConverter;
        return reverseConverter;
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> converter) {
        return a(converter);
    }

    /* access modifiers changed from: 0000 */
    public <C> Converter<A, C> a(Converter<B, C> converter) {
        return new ConverterComposition(this, (Converter) Preconditions.checkNotNull(converter));
    }

    @CanIgnoreReturnValue
    @Deprecated
    @Nullable
    public final B apply(@Nullable A a2) {
        return convert(a2);
    }

    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> function, Function<? super B, ? extends A> function2) {
        return new FunctionBasedConverter(function, function2);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.a;
    }
}
