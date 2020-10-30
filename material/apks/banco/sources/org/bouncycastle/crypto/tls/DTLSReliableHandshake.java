package org.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.util.Integers;
import org.joda.time.DateTimeConstants;

class DTLSReliableHandshake {
    private final DTLSRecordLayer a;
    private TlsHandshakeHash b;
    /* access modifiers changed from: private */
    public Hashtable c = new Hashtable();
    private Hashtable d = null;
    private Vector e = new Vector();
    private boolean f = true;
    private int g = 0;
    /* access modifiers changed from: private */
    public int h = 0;

    static class Message {
        private final int a;
        private final short b;
        private final byte[] c;

        private Message(int i, short s, byte[] bArr) {
            this.a = i;
            this.b = s;
            this.c = bArr;
        }

        public int a() {
            return this.a;
        }

        public short b() {
            return this.b;
        }

        public byte[] c() {
            return this.c;
        }
    }

    static class RecordLayerBuffer extends ByteArrayOutputStream {
        RecordLayerBuffer(int i) {
            super(i);
        }

        /* access modifiers changed from: 0000 */
        public void a(DTLSRecordLayer dTLSRecordLayer) {
            dTLSRecordLayer.send(this.buf, 0, this.count);
            this.buf = null;
        }
    }

    DTLSReliableHandshake(TlsContext tlsContext, DTLSRecordLayer dTLSRecordLayer) {
        this.a = dTLSRecordLayer;
        this.b = new DeferredHash();
        this.b.init(tlsContext);
    }

    private Message a(Message message) {
        if (message.b() != 0) {
            byte[] c2 = message.c();
            byte[] bArr = new byte[12];
            TlsUtils.writeUint8(message.b(), bArr, 0);
            TlsUtils.writeUint24(c2.length, bArr, 1);
            TlsUtils.writeUint16(message.a(), bArr, 4);
            TlsUtils.writeUint24(0, bArr, 6);
            TlsUtils.writeUint24(c2.length, bArr, 9);
            this.b.update(bArr, 0, bArr.length);
            this.b.update(c2, 0, c2.length);
        }
        return message;
    }

    private void a(Message message, int i, int i2) {
        RecordLayerBuffer recordLayerBuffer = new RecordLayerBuffer(i2 + 12);
        TlsUtils.writeUint8(message.b(), (OutputStream) recordLayerBuffer);
        TlsUtils.writeUint24(message.c().length, recordLayerBuffer);
        TlsUtils.writeUint16(message.a(), recordLayerBuffer);
        TlsUtils.writeUint24(i, recordLayerBuffer);
        TlsUtils.writeUint24(i2, recordLayerBuffer);
        recordLayerBuffer.write(message.c(), i, i2);
        recordLayerBuffer.a(this.a);
    }

    private void b(Message message) {
        int sendLimit = this.a.getSendLimit() - 12;
        if (sendLimit < 1) {
            throw new TlsFatalAlert(80);
        }
        int length = message.c().length;
        int i = 0;
        do {
            int min = Math.min(length - i, sendLimit);
            a(message, i, min);
            i += min;
        } while (i < length);
    }

    /* access modifiers changed from: private */
    public static boolean c(Hashtable hashtable) {
        Enumeration elements = hashtable.elements();
        while (elements.hasMoreElements()) {
            if (((DTLSReassembler) elements.nextElement()).b() == null) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void d(Hashtable hashtable) {
        Enumeration elements = hashtable.elements();
        while (elements.hasMoreElements()) {
            ((DTLSReassembler) elements.nextElement()).c();
        }
    }

    private void g() {
        Enumeration keys = this.c.keys();
        while (keys.hasMoreElements()) {
            ((Integer) keys.nextElement()).intValue();
            int i = this.h;
        }
    }

    private void h() {
        d(this.c);
        this.d = this.c;
        this.c = new Hashtable();
    }

    /* access modifiers changed from: private */
    public void i() {
        this.a.c();
        for (int i = 0; i < this.e.size(); i++) {
            b((Message) this.e.elementAt(i));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.b = this.b.notifyPRFDetermined();
    }

    /* access modifiers changed from: 0000 */
    public void a(short s, byte[] bArr) {
        TlsUtils.checkUint24(bArr.length);
        if (!this.f) {
            g();
            this.f = true;
            this.e.removeAllElements();
        }
        int i = this.g;
        this.g = i + 1;
        Message message = new Message(i, s, bArr);
        this.e.addElement(message);
        b(message);
        a(message);
    }

    /* access modifiers changed from: 0000 */
    public byte[] a(short s) {
        Message d2 = d();
        if (d2.b() == s) {
            return d2.c();
        }
        throw new TlsFatalAlert(10);
    }

    /* access modifiers changed from: 0000 */
    public TlsHandshakeHash b() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public TlsHandshakeHash c() {
        TlsHandshakeHash tlsHandshakeHash = this.b;
        this.b = this.b.stopTracking();
        return tlsHandshakeHash;
    }

    /* access modifiers changed from: 0000 */
    public Message d() {
        if (this.f) {
            this.f = false;
            h();
        }
        DTLSReassembler dTLSReassembler = (DTLSReassembler) this.c.get(Integers.valueOf(this.h));
        if (dTLSReassembler != null) {
            byte[] b2 = dTLSReassembler.b();
            if (b2 != null) {
                this.d = null;
                int i = this.h;
                this.h = i + 1;
                return a(new Message(i, dTLSReassembler.a(), b2));
            }
        }
        byte[] bArr = null;
        int i2 = 1000;
        while (true) {
            int receiveLimit = this.a.getReceiveLimit();
            if (bArr == null || bArr.length < receiveLimit) {
                bArr = new byte[receiveLimit];
            }
            while (true) {
                try {
                    int receive = this.a.receive(bArr, 0, receiveLimit, i2);
                    if (receive < 0) {
                        break;
                    } else if (receive >= 12) {
                        int readUint24 = TlsUtils.readUint24(bArr, 9);
                        if (receive == readUint24 + 12) {
                            int readUint16 = TlsUtils.readUint16(bArr, 4);
                            if (readUint16 <= this.h + 10) {
                                short readUint8 = TlsUtils.readUint8(bArr, 0);
                                int readUint242 = TlsUtils.readUint24(bArr, 1);
                                int readUint243 = TlsUtils.readUint24(bArr, 6);
                                if (readUint243 + readUint24 <= readUint242) {
                                    if (readUint16 >= this.h) {
                                        DTLSReassembler dTLSReassembler2 = (DTLSReassembler) this.c.get(Integers.valueOf(readUint16));
                                        if (dTLSReassembler2 == null) {
                                            dTLSReassembler2 = new DTLSReassembler(readUint8, readUint242);
                                            this.c.put(Integers.valueOf(readUint16), dTLSReassembler2);
                                        }
                                        DTLSReassembler dTLSReassembler3 = dTLSReassembler2;
                                        dTLSReassembler3.a(readUint8, readUint242, bArr, 12, readUint243, readUint24);
                                        if (readUint16 == this.h) {
                                            byte[] b3 = dTLSReassembler3.b();
                                            if (b3 != null) {
                                                this.d = null;
                                                int i3 = this.h;
                                                this.h = i3 + 1;
                                                return a(new Message(i3, dTLSReassembler3.a(), b3));
                                            }
                                        } else {
                                            continue;
                                        }
                                    } else if (this.d != null) {
                                        DTLSReassembler dTLSReassembler4 = (DTLSReassembler) this.d.get(Integers.valueOf(readUint16));
                                        if (dTLSReassembler4 != null) {
                                            dTLSReassembler4.a(readUint8, readUint242, bArr, 12, readUint243, readUint24);
                                            if (c(this.d)) {
                                                i();
                                                int min = Math.min(i2 * 2, DateTimeConstants.MILLIS_PER_MINUTE);
                                                try {
                                                    d(this.d);
                                                    i2 = min;
                                                } catch (IOException unused) {
                                                    i2 = min;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException unused2) {
                }
            }
            i();
            i2 = Math.min(i2 * 2, DateTimeConstants.MILLIS_PER_MINUTE);
        }
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        AnonymousClass1 r0;
        if (!this.f) {
            g();
        } else if (this.c != null) {
            r0 = new DTLSHandshakeRetransmit() {
                public void a(int i, byte[] bArr, int i2, int i3) {
                    if (i3 >= 12) {
                        int readUint24 = TlsUtils.readUint24(bArr, i2 + 9);
                        if (i3 == readUint24 + 12) {
                            int readUint16 = TlsUtils.readUint16(bArr, i2 + 4);
                            if (readUint16 < DTLSReliableHandshake.this.h) {
                                short readUint8 = TlsUtils.readUint8(bArr, i2);
                                if (i == (readUint8 == 20 ? 1 : 0)) {
                                    int readUint242 = TlsUtils.readUint24(bArr, i2 + 1);
                                    int readUint243 = TlsUtils.readUint24(bArr, i2 + 6);
                                    if (readUint243 + readUint24 <= readUint242) {
                                        DTLSReassembler dTLSReassembler = (DTLSReassembler) DTLSReliableHandshake.this.c.get(Integers.valueOf(readUint16));
                                        if (dTLSReassembler != null) {
                                            dTLSReassembler.a(readUint8, readUint242, bArr, i2 + 12, readUint243, readUint24);
                                            if (DTLSReliableHandshake.c(DTLSReliableHandshake.this.c)) {
                                                DTLSReliableHandshake.this.i();
                                                DTLSReliableHandshake.d(DTLSReliableHandshake.this.c);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            };
            this.a.a((DTLSHandshakeRetransmit) r0);
        }
        r0 = null;
        this.a.a((DTLSHandshakeRetransmit) r0);
    }

    /* access modifiers changed from: 0000 */
    public void f() {
        this.b.reset();
    }
}
