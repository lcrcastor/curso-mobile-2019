package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class GzipSource implements Source {
    private int a = 0;
    private final BufferedSource b;
    private final Inflater c;
    private final InflaterSource d;
    private final CRC32 e = new CRC32();

    public GzipSource(Source source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.c = new Inflater(true);
        this.b = Okio.buffer(source);
        this.d = new InflaterSource(this.b, this.c);
    }

    public long read(Buffer buffer, long j) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("byteCount < 0: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j == 0) {
            return 0;
        } else {
            if (this.a == 0) {
                a();
                this.a = 1;
            }
            if (this.a == 1) {
                long j2 = buffer.b;
                long read = this.d.read(buffer, j);
                if (read != -1) {
                    a(buffer, j2, read);
                    return read;
                }
                this.a = 2;
            }
            if (this.a == 2) {
                b();
                this.a = 3;
                if (!this.b.exhausted()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    private void a() {
        this.b.require(10);
        byte b2 = this.b.buffer().getByte(3);
        boolean z = ((b2 >> 1) & 1) == 1;
        if (z) {
            a(this.b.buffer(), 0, 10);
        }
        a("ID1ID2", 8075, (int) this.b.readShort());
        this.b.skip(8);
        if (((b2 >> 2) & 1) == 1) {
            this.b.require(2);
            if (z) {
                a(this.b.buffer(), 0, 2);
            }
            long readShortLe = (long) this.b.buffer().readShortLe();
            this.b.require(readShortLe);
            if (z) {
                a(this.b.buffer(), 0, readShortLe);
            }
            this.b.skip(readShortLe);
        }
        if (((b2 >> 3) & 1) == 1) {
            long indexOf = this.b.indexOf(0);
            if (indexOf == -1) {
                throw new EOFException();
            }
            if (z) {
                a(this.b.buffer(), 0, indexOf + 1);
            }
            this.b.skip(indexOf + 1);
        }
        if (((b2 >> 4) & 1) == 1) {
            long indexOf2 = this.b.indexOf(0);
            if (indexOf2 == -1) {
                throw new EOFException();
            }
            if (z) {
                a(this.b.buffer(), 0, indexOf2 + 1);
            }
            this.b.skip(indexOf2 + 1);
        }
        if (z) {
            a("FHCRC", (int) this.b.readShortLe(), (int) (short) ((int) this.e.getValue()));
            this.e.reset();
        }
    }

    private void b() {
        a("CRC", this.b.readIntLe(), (int) this.e.getValue());
        a("ISIZE", this.b.readIntLe(), (int) this.c.getBytesWritten());
    }

    public Timeout timeout() {
        return this.b.timeout();
    }

    public void close() {
        this.d.close();
    }

    private void a(Buffer buffer, long j, long j2) {
        Segment segment = buffer.a;
        while (j >= ((long) (segment.c - segment.b))) {
            long j3 = j - ((long) (segment.c - segment.b));
            segment = segment.f;
            j = j3;
        }
        while (j2 > 0) {
            int i = (int) (((long) segment.b) + j);
            int min = (int) Math.min((long) (segment.c - i), j2);
            this.e.update(segment.a, i, min);
            long j4 = j2 - ((long) min);
            segment = segment.f;
            j = 0;
            j2 = j4;
        }
    }

    private void a(String str, int i, int i2) {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i2), Integer.valueOf(i)}));
        }
    }
}
