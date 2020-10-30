package okio;

import java.util.zip.CRC32;
import java.util.zip.Deflater;

public final class GzipSink implements Sink {
    private final BufferedSink a;
    private final Deflater b;
    private final DeflaterSink c;
    private boolean d;
    private final CRC32 e = new CRC32();

    public GzipSink(Sink sink) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        this.b = new Deflater(-1, true);
        this.a = Okio.buffer(sink);
        this.c = new DeflaterSink(this.a, this.b);
        a();
    }

    public void write(Buffer buffer, long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j != 0) {
            a(buffer, j);
            this.c.write(buffer, j);
        }
    }

    public void flush() {
        this.c.flush();
    }

    public Timeout timeout() {
        return this.a.timeout();
    }

    public void close() {
        if (!this.d) {
            Throwable th = null;
            try {
                this.c.a();
                b();
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
            this.d = true;
            if (th != null) {
                Util.a(th);
            }
        }
    }

    public Deflater deflater() {
        return this.b;
    }

    private void a() {
        Buffer buffer = this.a.buffer();
        buffer.writeShort(8075);
        buffer.writeByte(8);
        buffer.writeByte(0);
        buffer.writeInt(0);
        buffer.writeByte(0);
        buffer.writeByte(0);
    }

    private void b() {
        this.a.writeIntLe((int) this.e.getValue());
        this.a.writeIntLe((int) this.b.getBytesRead());
    }

    private void a(Buffer buffer, long j) {
        Segment segment = buffer.a;
        while (j > 0) {
            int min = (int) Math.min(j, (long) (segment.c - segment.b));
            this.e.update(segment.a, segment.b, min);
            long j2 = j - ((long) min);
            segment = segment.f;
            j = j2;
        }
    }
}
