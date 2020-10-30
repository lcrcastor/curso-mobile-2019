package okio;

import java.util.zip.Deflater;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class DeflaterSink implements Sink {
    private final BufferedSink a;
    private final Deflater b;
    private boolean c;

    public DeflaterSink(Sink sink, Deflater deflater) {
        this(Okio.buffer(sink), deflater);
    }

    DeflaterSink(BufferedSink bufferedSink, Deflater deflater) {
        if (bufferedSink == null) {
            throw new IllegalArgumentException("source == null");
        } else if (deflater == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.a = bufferedSink;
            this.b = deflater;
        }
    }

    public void write(Buffer buffer, long j) {
        Util.a(buffer.b, 0, j);
        while (j > 0) {
            Segment segment = buffer.a;
            int min = (int) Math.min(j, (long) (segment.c - segment.b));
            this.b.setInput(segment.a, segment.b, min);
            a(false);
            long j2 = (long) min;
            buffer.b -= j2;
            segment.b += min;
            if (segment.b == segment.c) {
                buffer.a = segment.c();
                SegmentPool.a(segment);
            }
            j -= j2;
        }
    }

    @IgnoreJRERequirement
    private void a(boolean z) {
        Segment a2;
        int i;
        Buffer buffer = this.a.buffer();
        while (true) {
            a2 = buffer.a(1);
            if (z) {
                i = this.b.deflate(a2.a, a2.c, 8192 - a2.c, 2);
            } else {
                i = this.b.deflate(a2.a, a2.c, 8192 - a2.c);
            }
            if (i > 0) {
                a2.c += i;
                buffer.b += (long) i;
                this.a.emitCompleteSegments();
            } else if (this.b.needsInput()) {
                break;
            }
        }
        if (a2.b == a2.c) {
            buffer.a = a2.c();
            SegmentPool.a(a2);
        }
    }

    public void flush() {
        a(true);
        this.a.flush();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.b.finish();
        a(false);
    }

    public void close() {
        if (!this.c) {
            Throwable th = null;
            try {
                a();
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                this.b.end();
            } catch (Throwable th3) {
                if (th == null) {
                    th = th3;
                }
            }
            try {
                this.a.close();
            } catch (Throwable th4) {
                if (th == null) {
                    th = th4;
                }
            }
            this.c = true;
            if (th != null) {
                Util.a(th);
            }
        }
    }

    public Timeout timeout() {
        return this.a.timeout();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeflaterSink(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
