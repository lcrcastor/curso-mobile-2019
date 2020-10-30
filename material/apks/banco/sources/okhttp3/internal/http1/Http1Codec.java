package okhttp3.internal.http1;

import android.support.v4.media.session.PlaybackStateCompat;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http1Codec implements HttpCodec {
    final OkHttpClient a;
    final StreamAllocation b;
    final BufferedSource c;
    final BufferedSink d;
    int e = 0;
    private long f = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;

    abstract class AbstractSource implements Source {
        protected final ForwardingTimeout a;
        protected boolean b;
        protected long c;

        private AbstractSource() {
            this.a = new ForwardingTimeout(Http1Codec.this.c.timeout());
            this.c = 0;
        }

        public Timeout timeout() {
            return this.a;
        }

        public long read(Buffer buffer, long j) {
            try {
                long read = Http1Codec.this.c.read(buffer, j);
                if (read > 0) {
                    this.c += read;
                }
                return read;
            } catch (IOException e) {
                a(false, e);
                throw e;
            }
        }

        /* access modifiers changed from: protected */
        public final void a(boolean z, IOException iOException) {
            if (Http1Codec.this.e != 6) {
                if (Http1Codec.this.e != 5) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("state: ");
                    sb.append(Http1Codec.this.e);
                    throw new IllegalStateException(sb.toString());
                }
                Http1Codec.this.a(this.a);
                Http1Codec.this.e = 6;
                if (Http1Codec.this.b != null) {
                    Http1Codec.this.b.streamFinished(!z, Http1Codec.this, this.c, iOException);
                }
            }
        }
    }

    final class ChunkedSink implements Sink {
        private final ForwardingTimeout b = new ForwardingTimeout(Http1Codec.this.d.timeout());
        private boolean c;

        ChunkedSink() {
        }

        public Timeout timeout() {
            return this.b;
        }

        public void write(Buffer buffer, long j) {
            if (this.c) {
                throw new IllegalStateException("closed");
            } else if (j != 0) {
                Http1Codec.this.d.writeHexadecimalUnsignedLong(j);
                Http1Codec.this.d.writeUtf8("\r\n");
                Http1Codec.this.d.write(buffer, j);
                Http1Codec.this.d.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() {
            if (!this.c) {
                Http1Codec.this.d.flush();
            }
        }

        public synchronized void close() {
            if (!this.c) {
                this.c = true;
                Http1Codec.this.d.writeUtf8("0\r\n\r\n");
                Http1Codec.this.a(this.b);
                Http1Codec.this.e = 3;
            }
        }
    }

    class ChunkedSource extends AbstractSource {
        private final HttpUrl f;
        private long g = -1;
        private boolean h = true;

        ChunkedSource(HttpUrl httpUrl) {
            super();
            this.f = httpUrl;
        }

        public long read(Buffer buffer, long j) {
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(j);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (!this.h) {
                return -1;
            } else {
                if (this.g == 0 || this.g == -1) {
                    a();
                    if (!this.h) {
                        return -1;
                    }
                }
                long read = super.read(buffer, Math.min(j, this.g));
                if (read == -1) {
                    ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                    a(false, protocolException);
                    throw protocolException;
                }
                this.g -= read;
                return read;
            }
        }

        private void a() {
            if (this.g != -1) {
                Http1Codec.this.c.readUtf8LineStrict();
            }
            try {
                this.g = Http1Codec.this.c.readHexadecimalUnsignedLong();
                String trim = Http1Codec.this.c.readUtf8LineStrict().trim();
                if (this.g < 0 || (!trim.isEmpty() && !trim.startsWith(";"))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("expected chunk size and optional extensions but was \"");
                    sb.append(this.g);
                    sb.append(trim);
                    sb.append("\"");
                    throw new ProtocolException(sb.toString());
                } else if (this.g == 0) {
                    this.h = false;
                    HttpHeaders.receiveHeaders(Http1Codec.this.a.cookieJar(), this.f, Http1Codec.this.readHeaders());
                    a(true, null);
                }
            } catch (NumberFormatException e2) {
                throw new ProtocolException(e2.getMessage());
            }
        }

        public void close() {
            if (!this.b) {
                if (this.h && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    a(false, null);
                }
                this.b = true;
            }
        }
    }

    final class FixedLengthSink implements Sink {
        private final ForwardingTimeout b = new ForwardingTimeout(Http1Codec.this.d.timeout());
        private boolean c;
        private long d;

        FixedLengthSink(long j) {
            this.d = j;
        }

        public Timeout timeout() {
            return this.b;
        }

        public void write(Buffer buffer, long j) {
            if (this.c) {
                throw new IllegalStateException("closed");
            }
            Util.checkOffsetAndCount(buffer.size(), 0, j);
            if (j > this.d) {
                StringBuilder sb = new StringBuilder();
                sb.append("expected ");
                sb.append(this.d);
                sb.append(" bytes but received ");
                sb.append(j);
                throw new ProtocolException(sb.toString());
            }
            Http1Codec.this.d.write(buffer, j);
            this.d -= j;
        }

        public void flush() {
            if (!this.c) {
                Http1Codec.this.d.flush();
            }
        }

        public void close() {
            if (!this.c) {
                this.c = true;
                if (this.d > 0) {
                    throw new ProtocolException("unexpected end of stream");
                }
                Http1Codec.this.a(this.b);
                Http1Codec.this.e = 3;
            }
        }
    }

    class FixedLengthSource extends AbstractSource {
        private long f;

        FixedLengthSource(long j) {
            super();
            this.f = j;
            if (this.f == 0) {
                a(true, null);
            }
        }

        public long read(Buffer buffer, long j) {
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(j);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (this.f == 0) {
                return -1;
            } else {
                long read = super.read(buffer, Math.min(this.f, j));
                if (read == -1) {
                    ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                    a(false, protocolException);
                    throw protocolException;
                }
                this.f -= read;
                if (this.f == 0) {
                    a(true, null);
                }
                return read;
            }
        }

        public void close() {
            if (!this.b) {
                if (this.f != 0 && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    a(false, null);
                }
                this.b = true;
            }
        }
    }

    class UnknownLengthSource extends AbstractSource {
        private boolean f;

        UnknownLengthSource() {
            super();
        }

        public long read(Buffer buffer, long j) {
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(j);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (this.f) {
                return -1;
            } else {
                long read = super.read(buffer, j);
                if (read != -1) {
                    return read;
                }
                this.f = true;
                a(true, null);
                return -1;
            }
        }

        public void close() {
            if (!this.b) {
                if (!this.f) {
                    a(false, null);
                }
                this.b = true;
            }
        }
    }

    public Http1Codec(OkHttpClient okHttpClient, StreamAllocation streamAllocation, BufferedSource bufferedSource, BufferedSink bufferedSink) {
        this.a = okHttpClient;
        this.b = streamAllocation;
        this.c = bufferedSource;
        this.d = bufferedSink;
    }

    public Sink createRequestBody(Request request, long j) {
        if (HTTP.CHUNK_CODING.equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            return newChunkedSink();
        }
        if (j != -1) {
            return newFixedLengthSink(j);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    public void cancel() {
        RealConnection connection = this.b.connection();
        if (connection != null) {
            connection.cancel();
        }
    }

    public void writeRequestHeaders(Request request) {
        writeRequest(request.headers(), RequestLine.get(request, this.b.connection().route().proxy().type()));
    }

    public ResponseBody openResponseBody(Response response) {
        this.b.eventListener.responseBodyStart(this.b.call);
        String header = response.header("Content-Type");
        if (!HttpHeaders.hasBody(response)) {
            return new RealResponseBody(header, 0, Okio.buffer(newFixedLengthSource(0)));
        }
        if (HTTP.CHUNK_CODING.equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return new RealResponseBody(header, -1, Okio.buffer(newChunkedSource(response.request().url())));
        }
        long contentLength = HttpHeaders.contentLength(response);
        if (contentLength != -1) {
            return new RealResponseBody(header, contentLength, Okio.buffer(newFixedLengthSource(contentLength)));
        }
        return new RealResponseBody(header, -1, Okio.buffer(newUnknownLengthSource()));
    }

    public boolean isClosed() {
        return this.e == 6;
    }

    public void flushRequest() {
        this.d.flush();
    }

    public void finishRequest() {
        this.d.flush();
    }

    public void writeRequest(Headers headers, String str) {
        if (this.e != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        }
        this.d.writeUtf8(str).writeUtf8("\r\n");
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            this.d.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
        }
        this.d.writeUtf8("\r\n");
        this.e = 1;
    }

    public Builder readResponseHeaders(boolean z) {
        if (this.e == 1 || this.e == 3) {
            try {
                StatusLine parse = StatusLine.parse(a());
                Builder headers = new Builder().protocol(parse.protocol).code(parse.code).message(parse.message).headers(readHeaders());
                if (z && parse.code == 100) {
                    return null;
                }
                if (parse.code == 100) {
                    this.e = 3;
                    return headers;
                }
                this.e = 4;
                return headers;
            } catch (EOFException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected end of stream on ");
                sb.append(this.b);
                IOException iOException = new IOException(sb.toString());
                iOException.initCause(e2);
                throw iOException;
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("state: ");
            sb2.append(this.e);
            throw new IllegalStateException(sb2.toString());
        }
    }

    private String a() {
        String readUtf8LineStrict = this.c.readUtf8LineStrict(this.f);
        this.f -= (long) readUtf8LineStrict.length();
        return readUtf8LineStrict;
    }

    public Headers readHeaders() {
        Headers.Builder builder = new Headers.Builder();
        while (true) {
            String a2 = a();
            if (a2.length() == 0) {
                return builder.build();
            }
            Internal.instance.addLenient(builder, a2);
        }
    }

    public Sink newChunkedSink() {
        if (this.e != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        }
        this.e = 2;
        return new ChunkedSink();
    }

    public Sink newFixedLengthSink(long j) {
        if (this.e != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        }
        this.e = 2;
        return new FixedLengthSink(j);
    }

    public Source newFixedLengthSource(long j) {
        if (this.e != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        }
        this.e = 5;
        return new FixedLengthSource(j);
    }

    public Source newChunkedSource(HttpUrl httpUrl) {
        if (this.e != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        }
        this.e = 5;
        return new ChunkedSource(httpUrl);
    }

    public Source newUnknownLengthSource() {
        if (this.e != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.e);
            throw new IllegalStateException(sb.toString());
        } else if (this.b == null) {
            throw new IllegalStateException("streamAllocation == null");
        } else {
            this.e = 5;
            this.b.noNewStreams();
            return new UnknownLengthSource();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ForwardingTimeout forwardingTimeout) {
        Timeout delegate = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        delegate.clearDeadline();
        delegate.clearTimeout();
    }
}
