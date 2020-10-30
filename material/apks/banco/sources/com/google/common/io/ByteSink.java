package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

@GwtIncompatible
public abstract class ByteSink {

    final class AsCharSink extends CharSink {
        private final Charset b;

        private AsCharSink(Charset charset) {
            this.b = (Charset) Preconditions.checkNotNull(charset);
        }

        public Writer openStream() {
            return new OutputStreamWriter(ByteSink.this.openStream(), this.b);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ByteSink.this.toString());
            sb.append(".asCharSink(");
            sb.append(this.b);
            sb.append(")");
            return sb.toString();
        }
    }

    public abstract OutputStream openStream();

    protected ByteSink() {
    }

    public CharSink asCharSink(Charset charset) {
        return new AsCharSink(charset);
    }

    public OutputStream openBufferedStream() {
        OutputStream openStream = openStream();
        return openStream instanceof BufferedOutputStream ? (BufferedOutputStream) openStream : new BufferedOutputStream(openStream);
    }

    public void write(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        Closer create = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) create.register(openStream());
            outputStream.write(bArr);
            outputStream.flush();
            create.close();
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }

    @CanIgnoreReturnValue
    public long writeFrom(InputStream inputStream) {
        Preconditions.checkNotNull(inputStream);
        Closer create = Closer.create();
        try {
            OutputStream outputStream = (OutputStream) create.register(openStream());
            long copy = ByteStreams.copy(inputStream, outputStream);
            outputStream.flush();
            create.close();
            return copy;
        } catch (Throwable th) {
            create.close();
            throw th;
        }
    }
}
