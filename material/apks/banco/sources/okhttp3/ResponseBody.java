package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

public abstract class ResponseBody implements Closeable {
    private Reader a;

    static final class BomAwareReader extends Reader {
        private final BufferedSource a;
        private final Charset b;
        private boolean c;
        private Reader d;

        BomAwareReader(BufferedSource bufferedSource, Charset charset) {
            this.a = bufferedSource;
            this.b = charset;
        }

        public int read(char[] cArr, int i, int i2) {
            if (this.c) {
                throw new IOException("Stream closed");
            }
            Reader reader = this.d;
            if (reader == null) {
                Reader inputStreamReader = new InputStreamReader(this.a.inputStream(), Util.bomAwareCharset(this.a, this.b));
                this.d = inputStreamReader;
                reader = inputStreamReader;
            }
            return reader.read(cArr, i, i2);
        }

        public void close() {
            this.c = true;
            if (this.d != null) {
                this.d.close();
            } else {
                this.a.close();
            }
        }
    }

    public abstract long contentLength();

    @Nullable
    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final InputStream byteStream() {
        return source().inputStream();
    }

    /* JADX INFO: finally extract failed */
    public final byte[] bytes() {
        long contentLength = contentLength();
        if (contentLength > 2147483647L) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot buffer entire body for content length: ");
            sb.append(contentLength);
            throw new IOException(sb.toString());
        }
        BufferedSource source = source();
        try {
            byte[] readByteArray = source.readByteArray();
            Util.closeQuietly((Closeable) source);
            if (contentLength == -1 || contentLength == ((long) readByteArray.length)) {
                return readByteArray;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Content-Length (");
            sb2.append(contentLength);
            sb2.append(") and stream length (");
            sb2.append(readByteArray.length);
            sb2.append(") disagree");
            throw new IOException(sb2.toString());
        } catch (Throwable th) {
            Util.closeQuietly((Closeable) source);
            throw th;
        }
    }

    public final Reader charStream() {
        Reader reader = this.a;
        if (reader != null) {
            return reader;
        }
        BomAwareReader bomAwareReader = new BomAwareReader(source(), a());
        this.a = bomAwareReader;
        return bomAwareReader;
    }

    public final String string() {
        BufferedSource source = source();
        try {
            return source.readString(Util.bomAwareCharset(source, a()));
        } finally {
            Util.closeQuietly((Closeable) source);
        }
    }

    private Charset a() {
        MediaType contentType = contentType();
        return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
    }

    public void close() {
        Util.closeQuietly((Closeable) source());
    }

    public static ResponseBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null) {
            charset = mediaType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                StringBuilder sb = new StringBuilder();
                sb.append(mediaType);
                sb.append("; charset=utf-8");
                mediaType = MediaType.parse(sb.toString());
            }
        }
        Buffer writeString = new Buffer().writeString(str, charset);
        return create(mediaType, writeString.size(), writeString);
    }

    public static ResponseBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, (long) bArr.length, new Buffer().write(bArr));
    }

    public static ResponseBody create(@Nullable MediaType mediaType, ByteString byteString) {
        return create(mediaType, (long) byteString.size(), new Buffer().write(byteString));
    }

    public static ResponseBody create(@Nullable final MediaType mediaType, final long j, final BufferedSource bufferedSource) {
        if (bufferedSource != null) {
            return new ResponseBody() {
                @Nullable
                public MediaType contentType() {
                    return MediaType.this;
                }

                public long contentLength() {
                    return j;
                }

                public BufferedSource source() {
                    return bufferedSource;
                }
            };
        }
        throw new NullPointerException("source == null");
    }
}
