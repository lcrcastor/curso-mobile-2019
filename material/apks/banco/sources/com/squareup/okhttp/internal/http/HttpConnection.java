package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingTimeout;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpConnection {
    /* access modifiers changed from: private */
    public final ConnectionPool a;
    /* access modifiers changed from: private */
    public final Connection b;
    private final Socket c;
    /* access modifiers changed from: private */
    public final BufferedSource d;
    /* access modifiers changed from: private */
    public final BufferedSink e;
    /* access modifiers changed from: private */
    public int f = 0;
    /* access modifiers changed from: private */
    public int g = 0;

    abstract class AbstractSource implements Source {
        protected final ForwardingTimeout a;
        protected boolean b;

        private AbstractSource() {
            this.a = new ForwardingTimeout(HttpConnection.this.d.timeout());
        }

        public Timeout timeout() {
            return this.a;
        }

        /* access modifiers changed from: protected */
        public final void a(boolean z) {
            if (HttpConnection.this.f != 5) {
                StringBuilder sb = new StringBuilder();
                sb.append("state: ");
                sb.append(HttpConnection.this.f);
                throw new IllegalStateException(sb.toString());
            }
            HttpConnection.this.a(this.a);
            HttpConnection.this.f = 0;
            if (z && HttpConnection.this.g == 1) {
                HttpConnection.this.g = 0;
                Internal.instance.recycle(HttpConnection.this.a, HttpConnection.this.b);
            } else if (HttpConnection.this.g == 2) {
                HttpConnection.this.f = 6;
                HttpConnection.this.b.getSocket().close();
            }
        }

        /* access modifiers changed from: protected */
        public final void a() {
            Util.closeQuietly(HttpConnection.this.b.getSocket());
            HttpConnection.this.f = 6;
        }
    }

    final class ChunkedSink implements Sink {
        private final ForwardingTimeout b;
        private boolean c;

        private ChunkedSink() {
            this.b = new ForwardingTimeout(HttpConnection.this.e.timeout());
        }

        public Timeout timeout() {
            return this.b;
        }

        public void write(Buffer buffer, long j) {
            if (this.c) {
                throw new IllegalStateException("closed");
            } else if (j != 0) {
                HttpConnection.this.e.writeHexadecimalUnsignedLong(j);
                HttpConnection.this.e.writeUtf8("\r\n");
                HttpConnection.this.e.write(buffer, j);
                HttpConnection.this.e.writeUtf8("\r\n");
            }
        }

        public synchronized void flush() {
            if (!this.c) {
                HttpConnection.this.e.flush();
            }
        }

        public synchronized void close() {
            if (!this.c) {
                this.c = true;
                HttpConnection.this.e.writeUtf8("0\r\n\r\n");
                HttpConnection.this.a(this.b);
                HttpConnection.this.f = 3;
            }
        }
    }

    class ChunkedSource extends AbstractSource {
        private long e = -1;
        private boolean f = true;
        private final HttpEngine g;

        ChunkedSource(HttpEngine httpEngine) {
            super();
            this.g = httpEngine;
        }

        public long read(Buffer buffer, long j) {
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(j);
                throw new IllegalArgumentException(sb.toString());
            } else if (this.b) {
                throw new IllegalStateException("closed");
            } else if (!this.f) {
                return -1;
            } else {
                if (this.e == 0 || this.e == -1) {
                    b();
                    if (!this.f) {
                        return -1;
                    }
                }
                long read = HttpConnection.this.d.read(buffer, Math.min(j, this.e));
                if (read == -1) {
                    a();
                    throw new IOException("unexpected end of stream");
                }
                this.e -= read;
                return read;
            }
        }

        private void b() {
            if (this.e != -1) {
                HttpConnection.this.d.readUtf8LineStrict();
            }
            try {
                this.e = HttpConnection.this.d.readHexadecimalUnsignedLong();
                String trim = HttpConnection.this.d.readUtf8LineStrict().trim();
                if (this.e < 0 || (!trim.isEmpty() && !trim.startsWith(";"))) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("expected chunk size and optional extensions but was \"");
                    sb.append(this.e);
                    sb.append(trim);
                    sb.append("\"");
                    throw new ProtocolException(sb.toString());
                } else if (this.e == 0) {
                    this.f = false;
                    Builder builder = new Builder();
                    HttpConnection.this.readHeaders(builder);
                    this.g.receiveHeaders(builder.build());
                    a(true);
                }
            } catch (NumberFormatException e2) {
                throw new ProtocolException(e2.getMessage());
            }
        }

        public void close() {
            if (!this.b) {
                if (this.f && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    a();
                }
                this.b = true;
            }
        }
    }

    final class FixedLengthSink implements Sink {
        private final ForwardingTimeout b;
        private boolean c;
        private long d;

        private FixedLengthSink(long j) {
            this.b = new ForwardingTimeout(HttpConnection.this.e.timeout());
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
            HttpConnection.this.e.write(buffer, j);
            this.d -= j;
        }

        public void flush() {
            if (!this.c) {
                HttpConnection.this.e.flush();
            }
        }

        public void close() {
            if (!this.c) {
                this.c = true;
                if (this.d > 0) {
                    throw new ProtocolException("unexpected end of stream");
                }
                HttpConnection.this.a(this.b);
                HttpConnection.this.f = 3;
            }
        }
    }

    class FixedLengthSource extends AbstractSource {
        private long e;

        public FixedLengthSource(long j) {
            super();
            this.e = j;
            if (this.e == 0) {
                a(true);
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
            } else if (this.e == 0) {
                return -1;
            } else {
                long read = HttpConnection.this.d.read(buffer, Math.min(this.e, j));
                if (read == -1) {
                    a();
                    throw new ProtocolException("unexpected end of stream");
                }
                this.e -= read;
                if (this.e == 0) {
                    a(true);
                }
                return read;
            }
        }

        public void close() {
            if (!this.b) {
                if (this.e != 0 && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    a();
                }
                this.b = true;
            }
        }
    }

    class UnknownLengthSource extends AbstractSource {
        private boolean e;

        private UnknownLengthSource() {
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
            } else if (this.e) {
                return -1;
            } else {
                long read = HttpConnection.this.d.read(buffer, j);
                if (read != -1) {
                    return read;
                }
                this.e = true;
                a(false);
                return -1;
            }
        }

        public void close() {
            if (!this.b) {
                if (!this.e) {
                    a();
                }
                this.b = true;
            }
        }
    }

    public HttpConnection(ConnectionPool connectionPool, Connection connection, Socket socket) {
        this.a = connectionPool;
        this.b = connection;
        this.c = socket;
        this.d = Okio.buffer(Okio.source(socket));
        this.e = Okio.buffer(Okio.sink(socket));
    }

    public void setTimeouts(int i, int i2) {
        if (i != 0) {
            this.d.timeout().timeout((long) i, TimeUnit.MILLISECONDS);
        }
        if (i2 != 0) {
            this.e.timeout().timeout((long) i2, TimeUnit.MILLISECONDS);
        }
    }

    public void poolOnIdle() {
        this.g = 1;
        if (this.f == 0) {
            this.g = 0;
            Internal.instance.recycle(this.a, this.b);
        }
    }

    public void closeOnIdle() {
        this.g = 2;
        if (this.f == 0) {
            this.f = 6;
            this.b.getSocket().close();
        }
    }

    public boolean isClosed() {
        return this.f == 6;
    }

    public void closeIfOwnedBy(Object obj) {
        Internal.instance.closeIfOwnedBy(this.b, obj);
    }

    public void flush() {
        this.e.flush();
    }

    public long bufferSize() {
        return this.d.buffer().size();
    }

    public boolean isReadable() {
        int soTimeout;
        try {
            soTimeout = this.c.getSoTimeout();
            this.c.setSoTimeout(1);
            if (this.d.exhausted()) {
                this.c.setSoTimeout(soTimeout);
                return false;
            }
            this.c.setSoTimeout(soTimeout);
            return true;
        } catch (SocketTimeoutException unused) {
            return true;
        } catch (IOException unused2) {
            return false;
        } catch (Throwable th) {
            this.c.setSoTimeout(soTimeout);
            throw th;
        }
    }

    public void writeRequest(Headers headers, String str) {
        if (this.f != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.e.writeUtf8(str).writeUtf8("\r\n");
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            this.e.writeUtf8(headers.name(i)).writeUtf8(": ").writeUtf8(headers.value(i)).writeUtf8("\r\n");
        }
        this.e.writeUtf8("\r\n");
        this.f = 1;
    }

    public Response.Builder readResponse() {
        StatusLine parse;
        Response.Builder message;
        if (this.f == 1 || this.f == 3) {
            do {
                try {
                    parse = StatusLine.parse(this.d.readUtf8LineStrict());
                    message = new Response.Builder().protocol(parse.protocol).code(parse.code).message(parse.message);
                    Builder builder = new Builder();
                    readHeaders(builder);
                    builder.add(OkHeaders.SELECTED_PROTOCOL, parse.protocol.toString());
                    message.headers(builder.build());
                } catch (EOFException e2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("unexpected end of stream on ");
                    sb.append(this.b);
                    sb.append(" (recycle count=");
                    sb.append(Internal.instance.recycleCount(this.b));
                    sb.append(")");
                    IOException iOException = new IOException(sb.toString());
                    iOException.initCause(e2);
                    throw iOException;
                }
            } while (parse.code == 100);
            this.f = 4;
            return message;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("state: ");
        sb2.append(this.f);
        throw new IllegalStateException(sb2.toString());
    }

    public void readHeaders(Builder builder) {
        while (true) {
            String readUtf8LineStrict = this.d.readUtf8LineStrict();
            if (readUtf8LineStrict.length() != 0) {
                Internal.instance.addLenient(builder, readUtf8LineStrict);
            } else {
                return;
            }
        }
    }

    public Sink newChunkedSink() {
        if (this.f != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 2;
        return new ChunkedSink();
    }

    public Sink newFixedLengthSink(long j) {
        if (this.f != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 2;
        return new FixedLengthSink(j);
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        if (this.f != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 3;
        retryableSink.writeToSocket(this.e);
    }

    public Source newFixedLengthSource(long j) {
        if (this.f != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 5;
        return new FixedLengthSource(j);
    }

    public Source newChunkedSource(HttpEngine httpEngine) {
        if (this.f != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 5;
        return new ChunkedSource(httpEngine);
    }

    public Source newUnknownLengthSource() {
        if (this.f != 4) {
            StringBuilder sb = new StringBuilder();
            sb.append("state: ");
            sb.append(this.f);
            throw new IllegalStateException(sb.toString());
        }
        this.f = 5;
        return new UnknownLengthSource();
    }

    public BufferedSink rawSink() {
        return this.e;
    }

    public BufferedSource rawSource() {
        return this.d;
    }

    /* access modifiers changed from: private */
    public void a(ForwardingTimeout forwardingTimeout) {
        Timeout delegate = forwardingTimeout.delegate();
        forwardingTimeout.setDelegate(Timeout.NONE);
        delegate.clearDeadline();
        delegate.clearTimeout();
    }
}
