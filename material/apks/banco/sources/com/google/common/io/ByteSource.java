package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

@GwtIncompatible
public abstract class ByteSource {

    final class AsCharSource extends CharSource {
        final Charset a;

        AsCharSource(Charset charset) {
            this.a = (Charset) Preconditions.checkNotNull(charset);
        }

        public ByteSource asByteSource(Charset charset) {
            if (charset.equals(this.a)) {
                return ByteSource.this;
            }
            return super.asByteSource(charset);
        }

        public Reader openStream() {
            return new InputStreamReader(ByteSource.this.openStream(), this.a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ByteSource.this.toString());
            sb.append(".asCharSource(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static class ByteArrayByteSource extends ByteSource {
        final byte[] a;
        final int b;
        final int c;

        ByteArrayByteSource(byte[] bArr) {
            this(bArr, 0, bArr.length);
        }

        ByteArrayByteSource(byte[] bArr, int i, int i2) {
            this.a = bArr;
            this.b = i;
            this.c = i2;
        }

        public InputStream openStream() {
            return new ByteArrayInputStream(this.a, this.b, this.c);
        }

        public InputStream openBufferedStream() {
            return openStream();
        }

        public boolean isEmpty() {
            return this.c == 0;
        }

        public long size() {
            return (long) this.c;
        }

        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf((long) this.c));
        }

        public byte[] read() {
            return Arrays.copyOfRange(this.a, this.b, this.b + this.c);
        }

        public long copyTo(OutputStream outputStream) {
            outputStream.write(this.a, this.b, this.c);
            return (long) this.c;
        }

        public <T> T read(ByteProcessor<T> byteProcessor) {
            byteProcessor.processBytes(this.a, this.b, this.c);
            return byteProcessor.getResult();
        }

        public HashCode hash(HashFunction hashFunction) {
            return hashFunction.hashBytes(this.a, this.b, this.c);
        }

        public ByteSource slice(long j, long j2) {
            boolean z = false;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            if (j2 >= 0) {
                z = true;
            }
            Preconditions.checkArgument(z, "length (%s) may not be negative", j2);
            long min = Math.min(j, (long) this.c);
            return new ByteArrayByteSource(this.a, this.b + ((int) min), (int) Math.min(j2, ((long) this.c) - min));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ByteSource.wrap(");
            sb.append(Ascii.truncate(BaseEncoding.base16().encode(this.a, this.b, this.c), 30, "..."));
            sb.append(")");
            return sb.toString();
        }
    }

    static final class ConcatenatedByteSource extends ByteSource {
        final Iterable<? extends ByteSource> a;

        ConcatenatedByteSource(Iterable<? extends ByteSource> iterable) {
            this.a = (Iterable) Preconditions.checkNotNull(iterable);
        }

        public InputStream openStream() {
            return new MultiInputStream(this.a.iterator());
        }

        public boolean isEmpty() {
            for (ByteSource isEmpty : this.a) {
                if (!isEmpty.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public Optional<Long> sizeIfKnown() {
            long j = 0;
            for (ByteSource sizeIfKnown : this.a) {
                Optional sizeIfKnown2 = sizeIfKnown.sizeIfKnown();
                if (!sizeIfKnown2.isPresent()) {
                    return Optional.absent();
                }
                j += ((Long) sizeIfKnown2.get()).longValue();
            }
            return Optional.of(Long.valueOf(j));
        }

        public long size() {
            long j = 0;
            for (ByteSource size : this.a) {
                j += size.size();
            }
            return j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ByteSource.concat(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class EmptyByteSource extends ByteArrayByteSource {
        static final EmptyByteSource d = new EmptyByteSource();

        public String toString() {
            return "ByteSource.empty()";
        }

        EmptyByteSource() {
            super(new byte[0]);
        }

        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        public byte[] read() {
            return this.a;
        }
    }

    final class SlicedByteSource extends ByteSource {
        final long a;
        final long b;

        SlicedByteSource(long j, long j2) {
            boolean z = false;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            if (j2 >= 0) {
                z = true;
            }
            Preconditions.checkArgument(z, "length (%s) may not be negative", j2);
            this.a = j;
            this.b = j2;
        }

        public InputStream openStream() {
            return a(ByteSource.this.openStream());
        }

        public InputStream openBufferedStream() {
            return a(ByteSource.this.openBufferedStream());
        }

        private InputStream a(InputStream inputStream) {
            Closer create;
            if (this.a > 0) {
                try {
                    if (ByteStreams.a(inputStream, this.a) < this.a) {
                        inputStream.close();
                        return new ByteArrayInputStream(new byte[0]);
                    }
                } catch (Throwable th) {
                    create.close();
                    throw th;
                }
            }
            return ByteStreams.limit(inputStream, this.b);
        }

        public ByteSource slice(long j, long j2) {
            boolean z = false;
            Preconditions.checkArgument(j >= 0, "offset (%s) may not be negative", j);
            if (j2 >= 0) {
                z = true;
            }
            Preconditions.checkArgument(z, "length (%s) may not be negative", j2);
            return ByteSource.this.slice(this.a + j, Math.min(j2, this.b - j));
        }

        public boolean isEmpty() {
            return this.b == 0 || ByteSource.super.isEmpty();
        }

        public Optional<Long> sizeIfKnown() {
            Optional sizeIfKnown = ByteSource.this.sizeIfKnown();
            if (!sizeIfKnown.isPresent()) {
                return Optional.absent();
            }
            long longValue = ((Long) sizeIfKnown.get()).longValue();
            return Optional.of(Long.valueOf(Math.min(this.b, longValue - Math.min(this.a, longValue))));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ByteSource.this.toString());
            sb.append(".slice(");
            sb.append(this.a);
            sb.append(", ");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    public abstract InputStream openStream();

    protected ByteSource() {
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    public InputStream openBufferedStream() {
        InputStream openStream = openStream();
        return openStream instanceof BufferedInputStream ? (BufferedInputStream) openStream : new BufferedInputStream(openStream);
    }

    public ByteSource slice(long j, long j2) {
        SlicedByteSource slicedByteSource = new SlicedByteSource(j, j2);
        return slicedByteSource;
    }

    public boolean isEmpty() {
        Optional sizeIfKnown = sizeIfKnown();
        boolean z = true;
        if (sizeIfKnown.isPresent() && ((Long) sizeIfKnown.get()).longValue() == 0) {
            return true;
        }
        Closer create = Closer.create();
        try {
            if (((InputStream) create.register(openStream())).read() != -1) {
                z = false;
            }
            create.close();
            return z;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @Beta
    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    public long size() {
        Closer create;
        Optional sizeIfKnown = sizeIfKnown();
        if (sizeIfKnown.isPresent()) {
            return ((Long) sizeIfKnown.get()).longValue();
        }
        Closer create2 = Closer.create();
        try {
            long a = a((InputStream) create2.register(openStream()));
            create2.close();
            return a;
        } catch (IOException unused) {
            create2.close();
            create = Closer.create();
            long exhaust = ByteStreams.exhaust((InputStream) create.register(openStream()));
            create.close();
            return exhaust;
        } catch (Throwable th) {
            try {
                throw create.rethrow(th);
            } catch (Throwable th2) {
                create.close();
                throw th2;
            }
        }
    }

    private long a(InputStream inputStream) {
        long j = 0;
        while (true) {
            long a = ByteStreams.a(inputStream, 2147483647L);
            if (a <= 0) {
                return j;
            }
            j += a;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(OutputStream outputStream) {
        Preconditions.checkNotNull(outputStream);
        Closer create = Closer.create();
        try {
            long copy = ByteStreams.copy((InputStream) create.register(openStream()), outputStream);
            create.close();
            return copy;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(ByteSink byteSink) {
        Preconditions.checkNotNull(byteSink);
        Closer create = Closer.create();
        try {
            long copy = ByteStreams.copy((InputStream) create.register(openStream()), (OutputStream) create.register(byteSink.openStream()));
            create.close();
            return copy;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public byte[] read() {
        Closer create = Closer.create();
        try {
            byte[] byteArray = ByteStreams.toByteArray((InputStream) create.register(openStream()));
            create.close();
            return byteArray;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    @Beta
    public <T> T read(ByteProcessor<T> byteProcessor) {
        Preconditions.checkNotNull(byteProcessor);
        Closer create = Closer.create();
        try {
            T readBytes = ByteStreams.readBytes((InputStream) create.register(openStream()), byteProcessor);
            create.close();
            return readBytes;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public HashCode hash(HashFunction hashFunction) {
        Hasher newHasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(newHasher));
        return newHasher.hash();
    }

    public boolean contentEquals(ByteSource byteSource) {
        int read;
        Preconditions.checkNotNull(byteSource);
        byte[] a = ByteStreams.a();
        byte[] a2 = ByteStreams.a();
        Closer create = Closer.create();
        try {
            InputStream inputStream = (InputStream) create.register(openStream());
            InputStream inputStream2 = (InputStream) create.register(byteSource.openStream());
            do {
                read = ByteStreams.read(inputStream, a, 0, a.length);
                if (read == ByteStreams.read(inputStream2, a2, 0, a2.length)) {
                    if (!Arrays.equals(a, a2)) {
                    }
                }
                create.close();
                return false;
            } while (read == a.length);
            create.close();
            return true;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new ConcatenatedByteSource(iterable);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> it) {
        return concat((Iterable<? extends ByteSource>) ImmutableList.copyOf(it));
    }

    public static ByteSource concat(ByteSource... byteSourceArr) {
        return concat((Iterable<? extends ByteSource>) ImmutableList.copyOf((E[]) byteSourceArr));
    }

    public static ByteSource wrap(byte[] bArr) {
        return new ByteArrayByteSource(bArr);
    }

    public static ByteSource empty() {
        return EmptyByteSource.d;
    }
}
