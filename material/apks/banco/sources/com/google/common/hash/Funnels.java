package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

@Beta
public final class Funnels {

    static class SequentialFunnel<E> implements Funnel<Iterable<? extends E>>, Serializable {
        private final Funnel<E> a;

        SequentialFunnel(Funnel<E> funnel) {
            this.a = (Funnel) Preconditions.checkNotNull(funnel);
        }

        /* renamed from: a */
        public void funnel(Iterable<? extends E> iterable, PrimitiveSink primitiveSink) {
            for (Object funnel : iterable) {
                this.a.funnel(funnel, primitiveSink);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Funnels.sequentialFunnel(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof SequentialFunnel)) {
                return false;
            }
            return this.a.equals(((SequentialFunnel) obj).a);
        }

        public int hashCode() {
            return SequentialFunnel.class.hashCode() ^ this.a.hashCode();
        }
    }

    static class StringCharsetFunnel implements Funnel<CharSequence>, Serializable {
        private final Charset a;

        static class SerializedForm implements Serializable {
            private static final long serialVersionUID = 0;
            private final String a;

            SerializedForm(Charset charset) {
                this.a = charset.name();
            }

            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.a));
            }
        }

        StringCharsetFunnel(Charset charset) {
            this.a = (Charset) Preconditions.checkNotNull(charset);
        }

        /* renamed from: a */
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Funnels.stringFunnel(");
            sb.append(this.a.name());
            sb.append(")");
            return sb.toString();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof StringCharsetFunnel)) {
                return false;
            }
            return this.a.equals(((StringCharsetFunnel) obj).a);
        }

        public int hashCode() {
            return StringCharsetFunnel.class.hashCode() ^ this.a.hashCode();
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new SerializedForm(this.a);
        }
    }

    enum ByteArrayFunnel implements Funnel<byte[]> {
        INSTANCE;

        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }

        /* renamed from: a */
        public void funnel(byte[] bArr, PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(bArr);
        }
    }

    enum IntegerFunnel implements Funnel<Integer> {
        INSTANCE;

        public String toString() {
            return "Funnels.integerFunnel()";
        }

        /* renamed from: a */
        public void funnel(Integer num, PrimitiveSink primitiveSink) {
            primitiveSink.putInt(num.intValue());
        }
    }

    enum LongFunnel implements Funnel<Long> {
        INSTANCE;

        public String toString() {
            return "Funnels.longFunnel()";
        }

        /* renamed from: a */
        public void funnel(Long l, PrimitiveSink primitiveSink) {
            primitiveSink.putLong(l.longValue());
        }
    }

    static class SinkAsStream extends OutputStream {
        final PrimitiveSink a;

        SinkAsStream(PrimitiveSink primitiveSink) {
            this.a = (PrimitiveSink) Preconditions.checkNotNull(primitiveSink);
        }

        public void write(int i) {
            this.a.putByte((byte) i);
        }

        public void write(byte[] bArr) {
            this.a.putBytes(bArr);
        }

        public void write(byte[] bArr, int i, int i2) {
            this.a.putBytes(bArr, i, i2);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Funnels.asOutputStream(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    enum UnencodedCharsFunnel implements Funnel<CharSequence> {
        INSTANCE;

        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }

        /* renamed from: a */
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }
    }

    private Funnels() {
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return ByteArrayFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return UnencodedCharsFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new StringCharsetFunnel(charset);
    }

    public static Funnel<Integer> integerFunnel() {
        return IntegerFunnel.INSTANCE;
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> funnel) {
        return new SequentialFunnel(funnel);
    }

    public static Funnel<Long> longFunnel() {
        return LongFunnel.INSTANCE;
    }

    public static OutputStream asOutputStream(PrimitiveSink primitiveSink) {
        return new SinkAsStream(primitiveSink);
    }
}
