package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.internal.Util;
import java.net.ProtocolException;
import okio.Buffer;
import okio.Sink;
import okio.Timeout;

public final class RetryableSink implements Sink {
    private boolean a;
    private final int b;
    private final Buffer c;

    public void flush() {
    }

    public RetryableSink(int i) {
        this.c = new Buffer();
        this.b = i;
    }

    public RetryableSink() {
        this(-1);
    }

    public void close() {
        if (!this.a) {
            this.a = true;
            if (this.c.size() < ((long) this.b)) {
                StringBuilder sb = new StringBuilder();
                sb.append("content-length promised ");
                sb.append(this.b);
                sb.append(" bytes, but received ");
                sb.append(this.c.size());
                throw new ProtocolException(sb.toString());
            }
        }
    }

    public void write(Buffer buffer, long j) {
        if (this.a) {
            throw new IllegalStateException("closed");
        }
        Util.checkOffsetAndCount(buffer.size(), 0, j);
        if (this.b == -1 || this.c.size() <= ((long) this.b) - j) {
            this.c.write(buffer, j);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("exceeded content-length limit of ");
        sb.append(this.b);
        sb.append(" bytes");
        throw new ProtocolException(sb.toString());
    }

    public Timeout timeout() {
        return Timeout.NONE;
    }

    public long contentLength() {
        return this.c.size();
    }

    public void writeToSocket(Sink sink) {
        Buffer buffer = new Buffer();
        this.c.copyTo(buffer, 0, this.c.size());
        sink.write(buffer, buffer.size());
    }
}
