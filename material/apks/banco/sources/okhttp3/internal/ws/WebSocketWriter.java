package okhttp3.internal.ws;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.asn1.eac.EACTags;

final class WebSocketWriter {
    final boolean a;
    final Random b;
    final BufferedSink c;
    final Buffer d;
    boolean e;
    final Buffer f = new Buffer();
    final FrameSink g = new FrameSink();
    boolean h;
    private final byte[] i;
    private final UnsafeCursor j;

    final class FrameSink implements Sink {
        int a;
        long b;
        boolean c;
        boolean d;

        FrameSink() {
        }

        public void write(Buffer buffer, long j) {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.f.write(buffer, j);
            boolean z = this.c && this.b != -1 && WebSocketWriter.this.f.size() > this.b - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            long completeSegmentByteCount = WebSocketWriter.this.f.completeSegmentByteCount();
            if (completeSegmentByteCount > 0 && !z) {
                WebSocketWriter.this.a(this.a, completeSegmentByteCount, this.c, false);
                this.c = false;
            }
        }

        public void flush() {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.a(this.a, WebSocketWriter.this.f.size(), this.c, false);
            this.c = false;
        }

        public Timeout timeout() {
            return WebSocketWriter.this.c.timeout();
        }

        public void close() {
            if (this.d) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.a(this.a, WebSocketWriter.this.f.size(), this.c, true);
            this.d = true;
            WebSocketWriter.this.h = false;
        }
    }

    WebSocketWriter(boolean z, BufferedSink bufferedSink, Random random) {
        if (bufferedSink == null) {
            throw new NullPointerException("sink == null");
        } else if (random == null) {
            throw new NullPointerException("random == null");
        } else {
            this.a = z;
            this.c = bufferedSink;
            this.d = bufferedSink.buffer();
            this.b = random;
            UnsafeCursor unsafeCursor = null;
            this.i = z ? new byte[4] : null;
            if (z) {
                unsafeCursor = new UnsafeCursor();
            }
            this.j = unsafeCursor;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(ByteString byteString) {
        b(9, byteString);
    }

    /* access modifiers changed from: 0000 */
    public void b(ByteString byteString) {
        b(10, byteString);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, ByteString byteString) {
        ByteString byteString2 = ByteString.EMPTY;
        if (!(i2 == 0 && byteString == null)) {
            if (i2 != 0) {
                WebSocketProtocol.b(i2);
            }
            Buffer buffer = new Buffer();
            buffer.writeShort(i2);
            if (byteString != null) {
                buffer.write(byteString);
            }
            byteString2 = buffer.readByteString();
        }
        try {
            b(8, byteString2);
        } finally {
            this.e = true;
        }
    }

    private void b(int i2, ByteString byteString) {
        if (this.e) {
            throw new IOException("closed");
        }
        int size = byteString.size();
        if (((long) size) > 125) {
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        this.d.writeByte(i2 | 128);
        if (this.a) {
            this.d.writeByte(size | 128);
            this.b.nextBytes(this.i);
            this.d.write(this.i);
            if (size > 0) {
                long size2 = this.d.size();
                this.d.write(byteString);
                this.d.readAndWriteUnsafe(this.j);
                this.j.seek(size2);
                WebSocketProtocol.a(this.j, this.i);
                this.j.close();
            }
        } else {
            this.d.writeByte(size);
            this.d.write(byteString);
        }
        this.c.flush();
    }

    /* access modifiers changed from: 0000 */
    public Sink a(int i2, long j2) {
        if (this.h) {
            throw new IllegalStateException("Another message writer is active. Did you call close()?");
        }
        this.h = true;
        this.g.a = i2;
        this.g.b = j2;
        this.g.c = true;
        this.g.d = false;
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, long j2, boolean z, boolean z2) {
        if (this.e) {
            throw new IOException("closed");
        }
        int i3 = 0;
        if (!z) {
            i2 = 0;
        }
        if (z2) {
            i2 |= 128;
        }
        this.d.writeByte(i2);
        if (this.a) {
            i3 = 128;
        }
        if (j2 <= 125) {
            this.d.writeByte(((int) j2) | i3);
        } else if (j2 <= 65535) {
            this.d.writeByte(i3 | EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE);
            this.d.writeShort((int) j2);
        } else {
            this.d.writeByte(i3 | CertificateBody.profileType);
            this.d.writeLong(j2);
        }
        if (this.a) {
            this.b.nextBytes(this.i);
            this.d.write(this.i);
            if (j2 > 0) {
                long size = this.d.size();
                this.d.write(this.f, j2);
                this.d.readAndWriteUnsafe(this.j);
                this.j.seek(size);
                WebSocketProtocol.a(this.j, this.i);
                this.j.close();
            }
        } else {
            this.d.write(this.f, j2);
        }
        this.c.emit();
    }
}
