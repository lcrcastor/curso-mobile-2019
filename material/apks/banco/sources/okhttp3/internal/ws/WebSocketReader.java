package okhttp3.internal.ws;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSource;
import okio.ByteString;

final class WebSocketReader {
    final boolean a;
    final BufferedSource b;
    final FrameCallback c;
    boolean d;
    int e;
    long f;
    boolean g;
    boolean h;
    private final Buffer i = new Buffer();
    private final Buffer j = new Buffer();
    private final byte[] k;
    private final UnsafeCursor l;

    public interface FrameCallback {
        void onReadClose(int i, String str);

        void onReadMessage(String str);

        void onReadMessage(ByteString byteString);

        void onReadPing(ByteString byteString);

        void onReadPong(ByteString byteString);
    }

    WebSocketReader(boolean z, BufferedSource bufferedSource, FrameCallback frameCallback) {
        byte[] bArr;
        if (bufferedSource == null) {
            throw new NullPointerException("source == null");
        } else if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        } else {
            this.a = z;
            this.b = bufferedSource;
            this.c = frameCallback;
            UnsafeCursor unsafeCursor = null;
            if (z) {
                bArr = null;
            } else {
                bArr = new byte[4];
            }
            this.k = bArr;
            if (!z) {
                unsafeCursor = new UnsafeCursor();
            }
            this.l = unsafeCursor;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        b();
        if (this.h) {
            c();
        } else {
            d();
        }
    }

    /* JADX INFO: finally extract failed */
    private void b() {
        if (this.d) {
            throw new IOException("closed");
        }
        long timeoutNanos = this.b.timeout().timeoutNanos();
        this.b.timeout().clearTimeout();
        try {
            byte readByte = this.b.readByte() & UnsignedBytes.MAX_VALUE;
            this.b.timeout().timeout(timeoutNanos, TimeUnit.NANOSECONDS);
            this.e = readByte & Ascii.SI;
            boolean z = false;
            this.g = (readByte & UnsignedBytes.MAX_POWER_OF_TWO) != 0;
            this.h = (readByte & 8) != 0;
            if (!this.h || this.g) {
                boolean z2 = (readByte & SignedBytes.MAX_POWER_OF_TWO) != 0;
                boolean z3 = (readByte & 32) != 0;
                boolean z4 = (readByte & Ascii.DLE) != 0;
                if (z2 || z3 || z4) {
                    throw new ProtocolException("Reserved flags are unsupported.");
                }
                byte readByte2 = this.b.readByte() & UnsignedBytes.MAX_VALUE;
                if ((readByte2 & UnsignedBytes.MAX_POWER_OF_TWO) != 0) {
                    z = true;
                }
                if (z == this.a) {
                    throw new ProtocolException(this.a ? "Server-sent frames must not be masked." : "Client-sent frames must be masked.");
                }
                this.f = (long) (readByte2 & Ascii.DEL);
                if (this.f == 126) {
                    this.f = ((long) this.b.readShort()) & 65535;
                } else if (this.f == 127) {
                    this.f = this.b.readLong();
                    if (this.f < 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Frame length 0x");
                        sb.append(Long.toHexString(this.f));
                        sb.append(" > 0x7FFFFFFFFFFFFFFF");
                        throw new ProtocolException(sb.toString());
                    }
                }
                if (this.h && this.f > 125) {
                    throw new ProtocolException("Control frame must be less than 125B.");
                } else if (z) {
                    this.b.readFully(this.k);
                }
            } else {
                throw new ProtocolException("Control frames must be final.");
            }
        } catch (Throwable th) {
            this.b.timeout().timeout(timeoutNanos, TimeUnit.NANOSECONDS);
            throw th;
        }
    }

    private void c() {
        if (this.f > 0) {
            this.b.readFully(this.i, this.f);
            if (!this.a) {
                this.i.readAndWriteUnsafe(this.l);
                this.l.seek(0);
                WebSocketProtocol.a(this.l, this.k);
                this.l.close();
            }
        }
        switch (this.e) {
            case 8:
                short s = 1005;
                String str = "";
                long size = this.i.size();
                if (size == 1) {
                    throw new ProtocolException("Malformed close payload length of 1.");
                }
                if (size != 0) {
                    s = this.i.readShort();
                    str = this.i.readUtf8();
                    String a2 = WebSocketProtocol.a(s);
                    if (a2 != null) {
                        throw new ProtocolException(a2);
                    }
                }
                this.c.onReadClose(s, str);
                this.d = true;
                return;
            case 9:
                this.c.onReadPing(this.i.readByteString());
                return;
            case 10:
                this.c.onReadPong(this.i.readByteString());
                return;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown control opcode: ");
                sb.append(Integer.toHexString(this.e));
                throw new ProtocolException(sb.toString());
        }
    }

    private void d() {
        int i2 = this.e;
        if (i2 == 1 || i2 == 2) {
            f();
            if (i2 == 1) {
                this.c.onReadMessage(this.j.readUtf8());
            } else {
                this.c.onReadMessage(this.j.readByteString());
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown opcode: ");
            sb.append(Integer.toHexString(i2));
            throw new ProtocolException(sb.toString());
        }
    }

    private void e() {
        while (!this.d) {
            b();
            if (this.h) {
                c();
            } else {
                return;
            }
        }
    }

    private void f() {
        while (!this.d) {
            if (this.f > 0) {
                this.b.readFully(this.j, this.f);
                if (!this.a) {
                    this.j.readAndWriteUnsafe(this.l);
                    this.l.seek(this.j.size() - this.f);
                    WebSocketProtocol.a(this.l, this.k);
                    this.l.close();
                }
            }
            if (!this.g) {
                e();
                if (this.e != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Expected continuation opcode. Got: ");
                    sb.append(Integer.toHexString(this.e));
                    throw new ProtocolException(sb.toString());
                }
            } else {
                return;
            }
        }
        throw new IOException("closed");
    }
}
