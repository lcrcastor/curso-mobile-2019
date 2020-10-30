package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class InflaterSource implements Source {
    private final BufferedSource a;
    private final Inflater b;
    private int c;
    private boolean d;

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.a = bufferedSource;
            this.b = inflater;
        }
    }

    public long read(Buffer buffer, long j) {
        boolean refill;
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.d) {
            throw new IllegalStateException("closed");
        } else if (j == 0) {
            return 0;
        } else {
            do {
                refill = refill();
                try {
                    Segment a2 = buffer.a(1);
                    int inflate = this.b.inflate(a2.a, a2.c, (int) Math.min(j, (long) (8192 - a2.c)));
                    if (inflate > 0) {
                        a2.c += inflate;
                        long j2 = (long) inflate;
                        buffer.b += j2;
                        return j2;
                    }
                    if (!this.b.finished()) {
                        if (this.b.needsDictionary()) {
                        }
                    }
                    a();
                    if (a2.b == a2.c) {
                        buffer.a = a2.c();
                        SegmentPool.a(a2);
                    }
                    return -1;
                } catch (DataFormatException e) {
                    throw new IOException(e);
                }
            } while (!refill);
            throw new EOFException("source exhausted prematurely");
        }
    }

    public boolean refill() {
        if (!this.b.needsInput()) {
            return false;
        }
        a();
        if (this.b.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.a.exhausted()) {
            return true;
        } else {
            Segment segment = this.a.buffer().a;
            this.c = segment.c - segment.b;
            this.b.setInput(segment.a, segment.b, this.c);
            return false;
        }
    }

    private void a() {
        if (this.c != 0) {
            int remaining = this.c - this.b.getRemaining();
            this.c -= remaining;
            this.a.skip((long) remaining);
        }
    }

    public Timeout timeout() {
        return this.a.timeout();
    }

    public void close() {
        if (!this.d) {
            this.b.end();
            this.d = true;
            this.a.close();
        }
    }
}
