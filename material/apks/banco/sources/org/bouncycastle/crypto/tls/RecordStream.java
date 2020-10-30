package org.bouncycastle.crypto.tls;

import android.support.v4.view.InputDeviceCompat;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

class RecordStream {
    private static int a = 16384;
    private TlsProtocol b;
    private InputStream c;
    private OutputStream d;
    private TlsCompression e = null;
    private TlsCompression f = null;
    private TlsCompression g = null;
    private TlsCipher h = null;
    private TlsCipher i = null;
    private TlsCipher j = null;
    private long k = 0;
    private long l = 0;
    private ByteArrayOutputStream m = new ByteArrayOutputStream();
    private TlsContext n = null;
    private TlsHandshakeHash o = null;
    private ProtocolVersion p = null;
    private ProtocolVersion q = null;
    private boolean r = true;
    private int s;
    private int t;
    private int u;

    RecordStream(TlsProtocol tlsProtocol, InputStream inputStream, OutputStream outputStream) {
        this.b = tlsProtocol;
        this.c = inputStream;
        this.d = outputStream;
        this.f = new TlsNullCompression();
        this.g = this.f;
        this.i = new TlsNullCipher(this.n);
        this.j = this.i;
        a(a);
    }

    private static void a(int i2, int i3, short s2) {
        if (i2 > i3) {
            throw new TlsFatalAlert(s2);
        }
    }

    private static void a(short s2, short s3) {
        switch (s2) {
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return;
            default:
                throw new TlsFatalAlert(s3);
        }
    }

    private byte[] l() {
        byte[] byteArray = this.m.toByteArray();
        this.m.reset();
        return byteArray;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.s = i2;
        this.t = this.s + 1024;
        this.u = this.t + 1024;
    }

    /* access modifiers changed from: 0000 */
    public void a(ProtocolVersion protocolVersion) {
        this.p = protocolVersion;
    }

    /* access modifiers changed from: 0000 */
    public void a(TlsCompression tlsCompression, TlsCipher tlsCipher) {
        this.e = tlsCompression;
        this.h = tlsCipher;
    }

    /* access modifiers changed from: 0000 */
    public void a(TlsContext tlsContext) {
        this.n = tlsContext;
        this.o = new DeferredHash();
        this.o.init(tlsContext);
    }

    /* access modifiers changed from: protected */
    public void a(short s2, byte[] bArr, int i2, int i3) {
        TlsCipher tlsCipher;
        long j2;
        short s3;
        short s4 = s2;
        byte[] bArr2 = bArr;
        int i4 = i2;
        int i5 = i3;
        if (this.q != null) {
            a(s4, 80);
            a(i5, this.s, 80);
            if (i5 >= 1 || s4 == 23) {
                if (s4 == 22) {
                    a(bArr2, i4, i5);
                }
                OutputStream compress = this.g.compress(this.m);
                if (compress == this.m) {
                    tlsCipher = this.j;
                    long j3 = this.l;
                    this.l = j3 + 1;
                    j2 = j3;
                    s3 = s4;
                } else {
                    compress.write(bArr2, i4, i5);
                    compress.flush();
                    bArr2 = l();
                    a(bArr2.length, i5 + 1024, 80);
                    tlsCipher = this.j;
                    long j4 = this.l;
                    this.l = j4 + 1;
                    j2 = j4;
                    s3 = s4;
                    i4 = 0;
                    i5 = bArr2.length;
                }
                byte[] encodePlaintext = tlsCipher.encodePlaintext(j2, s3, bArr2, i4, i5);
                a(encodePlaintext.length, this.u, 80);
                byte[] bArr3 = new byte[(encodePlaintext.length + 5)];
                TlsUtils.writeUint8(s4, bArr3, 0);
                TlsUtils.writeVersion(this.q, bArr3, 1);
                TlsUtils.writeUint16(encodePlaintext.length, bArr3, 3);
                System.arraycopy(encodePlaintext, 0, bArr3, 5, encodePlaintext.length);
                this.d.write(bArr3);
                this.d.flush();
                return;
            }
            throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        this.r = z;
    }

    /* access modifiers changed from: 0000 */
    public void a(byte[] bArr, int i2, int i3) {
        this.o.update(bArr, i2, i3);
    }

    /* access modifiers changed from: protected */
    public byte[] a(short s2, InputStream inputStream, int i2) {
        a(i2, this.u, 22);
        byte[] readFully = TlsUtils.readFully(i2, inputStream);
        TlsCipher tlsCipher = this.i;
        long j2 = this.k;
        this.k = j2 + 1;
        byte[] decodeCiphertext = tlsCipher.decodeCiphertext(j2, s2, readFully, 0, readFully.length);
        a(decodeCiphertext.length, this.t, 22);
        OutputStream decompress = this.f.decompress(this.m);
        if (decompress != this.m) {
            decompress.write(decodeCiphertext, 0, decodeCiphertext.length);
            decompress.flush();
            decodeCiphertext = l();
        }
        a(decodeCiphertext.length, this.s, 30);
        if (decodeCiphertext.length >= 1 || s2 == 23) {
            return decodeCiphertext;
        }
        throw new TlsFatalAlert(47);
    }

    /* access modifiers changed from: 0000 */
    public ProtocolVersion b() {
        return this.p;
    }

    /* access modifiers changed from: 0000 */
    public void b(ProtocolVersion protocolVersion) {
        this.q = protocolVersion;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.e == null || this.h == null) {
            throw new TlsFatalAlert(40);
        }
        this.g = this.e;
        this.j = this.h;
        this.l = 0;
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        if (this.e == null || this.h == null) {
            throw new TlsFatalAlert(40);
        }
        this.f = this.e;
        this.i = this.h;
        this.k = 0;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        if (this.f == this.e && this.g == this.e && this.i == this.h && this.j == this.h) {
            this.e = null;
            this.h = null;
            return;
        }
        throw new TlsFatalAlert(40);
    }

    public boolean f() {
        byte[] readAllOrNothing = TlsUtils.readAllOrNothing(5, this.c);
        if (readAllOrNothing == null) {
            return false;
        }
        short readUint8 = TlsUtils.readUint8(readAllOrNothing, 0);
        a(readUint8, 10);
        if (this.r) {
            ProtocolVersion readVersion = TlsUtils.readVersion(readAllOrNothing, 1);
            if (this.p == null) {
                this.p = readVersion;
            } else if (!readVersion.equals(this.p)) {
                throw new TlsFatalAlert(47);
            }
        } else if ((TlsUtils.readVersionRaw(readAllOrNothing, 1) & InputDeviceCompat.SOURCE_ANY) != 768) {
            throw new TlsFatalAlert(47);
        }
        byte[] a2 = a(readUint8, this.c, TlsUtils.readUint16(readAllOrNothing, 3));
        this.b.processRecord(readUint8, a2, 0, a2.length);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void g() {
        this.o = this.o.notifyPRFDetermined();
    }

    /* access modifiers changed from: 0000 */
    public TlsHandshakeHash h() {
        return this.o;
    }

    /* access modifiers changed from: 0000 */
    public TlsHandshakeHash i() {
        TlsHandshakeHash tlsHandshakeHash = this.o;
        this.o = this.o.stopTracking();
        return tlsHandshakeHash;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:0|1|2|3|5) */
    /* JADX WARNING: Code restructure failed: missing block: B:6:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0005 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void j() {
        /*
            r1 = this;
            java.io.InputStream r0 = r1.c     // Catch:{ IOException -> 0x0005 }
            r0.close()     // Catch:{ IOException -> 0x0005 }
        L_0x0005:
            java.io.OutputStream r0 = r1.d     // Catch:{ IOException -> 0x000a }
            r0.close()     // Catch:{ IOException -> 0x000a }
        L_0x000a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.RecordStream.j():void");
    }

    /* access modifiers changed from: protected */
    public void k() {
        this.d.flush();
    }
}
