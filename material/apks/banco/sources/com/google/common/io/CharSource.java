package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
public abstract class CharSource {

    final class AsByteSource extends ByteSource {
        final Charset a;

        AsByteSource(Charset charset) {
            this.a = (Charset) Preconditions.checkNotNull(charset);
        }

        public CharSource asCharSource(Charset charset) {
            if (charset.equals(this.a)) {
                return CharSource.this;
            }
            return super.asCharSource(charset);
        }

        public InputStream openStream() {
            return new ReaderInputStream(CharSource.this.openStream(), this.a, 8192);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(CharSource.this.toString());
            sb.append(".asByteSource(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static class CharSequenceCharSource extends CharSource {
        /* access modifiers changed from: private */
        public static final Splitter a = Splitter.onPattern("\r\n|\n|\r");
        /* access modifiers changed from: private */
        public final CharSequence b;

        protected CharSequenceCharSource(CharSequence charSequence) {
            this.b = (CharSequence) Preconditions.checkNotNull(charSequence);
        }

        public Reader openStream() {
            return new CharSequenceReader(this.b);
        }

        public String read() {
            return this.b.toString();
        }

        public boolean isEmpty() {
            return this.b.length() == 0;
        }

        public long length() {
            return (long) this.b.length();
        }

        public Optional<Long> lengthIfKnown() {
            return Optional.of(Long.valueOf((long) this.b.length()));
        }

        private Iterable<String> b() {
            return new Iterable<String>() {
                public Iterator<String> iterator() {
                    return new AbstractIterator<String>() {
                        Iterator<String> a = CharSequenceCharSource.a.split(CharSequenceCharSource.this.b).iterator();

                        /* access modifiers changed from: protected */
                        /* renamed from: a */
                        public String computeNext() {
                            if (this.a.hasNext()) {
                                String str = (String) this.a.next();
                                if (this.a.hasNext() || !str.isEmpty()) {
                                    return str;
                                }
                            }
                            return (String) endOfData();
                        }
                    };
                }
            };
        }

        public String readFirstLine() {
            Iterator it = b().iterator();
            if (it.hasNext()) {
                return (String) it.next();
            }
            return null;
        }

        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(b());
        }

        public <T> T readLines(LineProcessor<T> lineProcessor) {
            for (String processLine : b()) {
                if (!lineProcessor.processLine(processLine)) {
                    break;
                }
            }
            return lineProcessor.getResult();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharSource.wrap(");
            sb.append(Ascii.truncate(this.b, 30, "..."));
            sb.append(")");
            return sb.toString();
        }
    }

    static final class ConcatenatedCharSource extends CharSource {
        private final Iterable<? extends CharSource> a;

        ConcatenatedCharSource(Iterable<? extends CharSource> iterable) {
            this.a = (Iterable) Preconditions.checkNotNull(iterable);
        }

        public Reader openStream() {
            return new MultiReader(this.a.iterator());
        }

        public boolean isEmpty() {
            for (CharSource isEmpty : this.a) {
                if (!isEmpty.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public Optional<Long> lengthIfKnown() {
            long j = 0;
            for (CharSource lengthIfKnown : this.a) {
                Optional lengthIfKnown2 = lengthIfKnown.lengthIfKnown();
                if (!lengthIfKnown2.isPresent()) {
                    return Optional.absent();
                }
                j += ((Long) lengthIfKnown2.get()).longValue();
            }
            return Optional.of(Long.valueOf(j));
        }

        public long length() {
            long j = 0;
            for (CharSource length : this.a) {
                j += length.length();
            }
            return j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("CharSource.concat(");
            sb.append(this.a);
            sb.append(")");
            return sb.toString();
        }
    }

    static final class EmptyCharSource extends CharSequenceCharSource {
        /* access modifiers changed from: private */
        public static final EmptyCharSource a = new EmptyCharSource();

        public String toString() {
            return "CharSource.empty()";
        }

        private EmptyCharSource() {
            super("");
        }
    }

    public abstract Reader openStream();

    protected CharSource() {
    }

    @Beta
    public ByteSource asByteSource(Charset charset) {
        return new AsByteSource(charset);
    }

    public BufferedReader openBufferedStream() {
        Reader openStream = openStream();
        return openStream instanceof BufferedReader ? (BufferedReader) openStream : new BufferedReader(openStream);
    }

    @Beta
    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    @Beta
    public long length() {
        Optional lengthIfKnown = lengthIfKnown();
        if (lengthIfKnown.isPresent()) {
            return ((Long) lengthIfKnown.get()).longValue();
        }
        Closer create = Closer.create();
        try {
            long a = a((Reader) create.register(openStream()));
            create.close();
            return a;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    private long a(Reader reader) {
        long j = 0;
        while (true) {
            long skip = reader.skip(Long.MAX_VALUE);
            if (skip == 0) {
                return j;
            }
            j += skip;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(Appendable appendable) {
        Preconditions.checkNotNull(appendable);
        Closer create = Closer.create();
        try {
            long copy = CharStreams.copy((Reader) create.register(openStream()), appendable);
            create.close();
            return copy;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        Closer create = Closer.create();
        try {
            long copy = CharStreams.copy((Reader) create.register(openStream()), (Writer) create.register(charSink.openStream()));
            create.close();
            return copy;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public String read() {
        Closer create = Closer.create();
        try {
            String charStreams = CharStreams.toString((Reader) create.register(openStream()));
            create.close();
            return charStreams;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @Nullable
    public String readFirstLine() {
        Closer create = Closer.create();
        try {
            String readLine = ((BufferedReader) create.register(openBufferedStream())).readLine();
            create.close();
            return readLine;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public ImmutableList<String> readLines() {
        Closer create = Closer.create();
        try {
            BufferedReader bufferedReader = (BufferedReader) create.register(openBufferedStream());
            ArrayList newArrayList = Lists.newArrayList();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    newArrayList.add(readLine);
                } else {
                    ImmutableList<String> copyOf = ImmutableList.copyOf((Collection<? extends E>) newArrayList);
                    create.close();
                    return copyOf;
                }
            }
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    @Beta
    public <T> T readLines(LineProcessor<T> lineProcessor) {
        Preconditions.checkNotNull(lineProcessor);
        Closer create = Closer.create();
        try {
            T readLines = CharStreams.readLines((Reader) create.register(openStream()), lineProcessor);
            create.close();
            return readLines;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public boolean isEmpty() {
        Optional lengthIfKnown = lengthIfKnown();
        boolean z = true;
        if (lengthIfKnown.isPresent() && ((Long) lengthIfKnown.get()).longValue() == 0) {
            return true;
        }
        Closer create = Closer.create();
        try {
            if (((Reader) create.register(openStream())).read() != -1) {
                z = false;
            }
            create.close();
            return z;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    public static CharSource concat(Iterable<? extends CharSource> iterable) {
        return new ConcatenatedCharSource(iterable);
    }

    public static CharSource concat(Iterator<? extends CharSource> it) {
        return concat((Iterable<? extends CharSource>) ImmutableList.copyOf(it));
    }

    public static CharSource concat(CharSource... charSourceArr) {
        return concat((Iterable<? extends CharSource>) ImmutableList.copyOf((E[]) charSourceArr));
    }

    public static CharSource wrap(CharSequence charSequence) {
        return new CharSequenceCharSource(charSequence);
    }

    public static CharSource empty() {
        return EmptyCharSource.a;
    }
}
