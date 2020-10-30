package org.bouncycastle.crypto.tls;

class DTLSRecordLayer implements DatagramTransport {
    private final DatagramTransport a;
    private final TlsContext b;
    private final TlsPeer c;
    private final ByteQueue d = new ByteQueue();
    private volatile boolean e = false;
    private volatile boolean f = false;
    private volatile ProtocolVersion g = null;
    private volatile boolean h;
    private volatile int i;
    private DTLSEpoch j;
    private DTLSEpoch k;
    private DTLSEpoch l;
    private DTLSEpoch m;
    private DTLSHandshakeRetransmit n = null;
    private DTLSEpoch o = null;
    private long p = 0;

    DTLSRecordLayer(DatagramTransport datagramTransport, TlsContext tlsContext, TlsPeer tlsPeer, short s) {
        this.a = datagramTransport;
        this.b = tlsContext;
        this.c = tlsPeer;
        this.h = true;
        this.j = new DTLSEpoch(0, new TlsNullCipher(tlsContext));
        this.k = null;
        this.l = this.j;
        this.m = this.j;
        a(16384);
    }

    private int a(byte[] bArr, int i2, int i3, int i4) {
        int i5;
        if (this.d.size() > 0) {
            if (this.d.size() >= 13) {
                byte[] bArr2 = new byte[2];
                this.d.read(bArr2, 0, 2, 11);
                i5 = TlsUtils.readUint16(bArr2, 0);
            } else {
                i5 = 0;
            }
            int min = Math.min(this.d.size(), i5 + 13);
            this.d.removeData(bArr, i2, min, 0);
            return min;
        }
        int receive = this.a.receive(bArr, i2, i3, i4);
        if (receive >= 13) {
            int readUint16 = TlsUtils.readUint16(bArr, i2 + 11) + 13;
            if (receive > readUint16) {
                this.d.addData(bArr, i2 + readUint16, receive - readUint16);
                receive = readUint16;
            }
        }
        return receive;
    }

    private static long a(int i2, long j2) {
        return ((((long) i2) & 4294967295L) << 48) | j2;
    }

    private void a(short s, short s2, String str, Exception exc) {
        this.c.notifyAlertRaised(s, s2, str, exc);
        a(21, new byte[]{(byte) s, (byte) s2}, 0, 2);
    }

    private void a(short s, byte[] bArr, int i2, int i3) {
        short s2 = s;
        int i4 = i3;
        if (i4 > this.i) {
            throw new TlsFatalAlert(80);
        } else if (i4 >= 1 || s2 == 23) {
            int c2 = this.m.c();
            long a2 = this.m.a();
            byte[] encodePlaintext = this.m.b().encodePlaintext(a(c2, a2), s2, bArr, i2, i4);
            byte[] bArr2 = new byte[(encodePlaintext.length + 13)];
            TlsUtils.writeUint8(s2, bArr2, 0);
            TlsUtils.writeVersion(this.g != null ? this.g : this.b.getClientVersion(), bArr2, 1);
            TlsUtils.writeUint16(c2, bArr2, 3);
            TlsUtils.writeUint48(a2, bArr2, 5);
            TlsUtils.writeUint16(encodePlaintext.length, bArr2, 11);
            System.arraycopy(encodePlaintext, 0, bArr2, 13, encodePlaintext.length);
            this.a.send(bArr2, 0, bArr2.length);
        } else {
            throw new TlsFatalAlert(80);
        }
    }

    private void d() {
        if (!this.e) {
            try {
                if (!this.f) {
                    a(0, (String) null);
                }
                this.a.close();
            } catch (Exception unused) {
            }
            this.e = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public ProtocolVersion a() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.i = i2;
    }

    /* access modifiers changed from: 0000 */
    public void a(DTLSHandshakeRetransmit dTLSHandshakeRetransmit) {
        if (this.l == this.j || this.m == this.j) {
            throw new IllegalStateException();
        }
        if (dTLSHandshakeRetransmit != null) {
            this.n = dTLSHandshakeRetransmit;
            this.o = this.j;
            this.p = System.currentTimeMillis() + 240000;
        }
        this.h = false;
        this.j = this.k;
        this.k = null;
    }

    /* access modifiers changed from: 0000 */
    public void a(TlsCipher tlsCipher) {
        if (this.k != null) {
            throw new IllegalStateException();
        }
        this.k = new DTLSEpoch(this.m.c() + 1, tlsCipher);
    }

    /* access modifiers changed from: 0000 */
    public void a(short s) {
        if (!this.e) {
            try {
                a(2, s, (String) null, (Exception) null);
            } catch (Exception unused) {
            }
            this.f = true;
            d();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(short s, String str) {
        a(1, s, str, (Exception) null);
    }

    /* access modifiers changed from: 0000 */
    public ProtocolVersion b() {
        ProtocolVersion protocolVersion = this.g;
        this.g = null;
        return protocolVersion;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.m = this.o != null ? this.o : this.j;
    }

    public void close() {
        if (!this.e) {
            if (this.h) {
                a(90, "User canceled handshake");
            }
            d();
        }
    }

    public int getReceiveLimit() {
        return Math.min(this.i, this.l.b().getPlaintextLimit(this.a.getReceiveLimit() - 13));
    }

    public int getSendLimit() {
        return Math.min(this.i, this.m.b().getPlaintextLimit(this.a.getSendLimit() - 13));
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [org.bouncycastle.crypto.tls.DTLSEpoch, org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r12v0, types: [org.bouncycastle.crypto.tls.DTLSEpoch] */
    /* JADX WARNING: type inference failed for: r3v4, types: [org.bouncycastle.crypto.tls.DTLSEpoch] */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: type inference failed for: r12v2 */
    /* JADX WARNING: type inference failed for: r7v19, types: [org.bouncycastle.crypto.tls.DTLSEpoch] */
    /* JADX WARNING: type inference failed for: r7v20 */
    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r7v21, types: [org.bouncycastle.crypto.tls.DTLSEpoch] */
    /* JADX WARNING: type inference failed for: r2v23 */
    /* JADX WARNING: type inference failed for: r2v24 */
    /* JADX WARNING: type inference failed for: r2v25 */
    /* JADX WARNING: type inference failed for: r2v26 */
    /* JADX WARNING: type inference failed for: r7v24 */
    /* JADX WARNING: type inference failed for: r7v25 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v1, types: [org.bouncycastle.crypto.tls.DTLSEpoch, org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit]
      assigns: []
      uses: [?[OBJECT, ARRAY], org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit, org.bouncycastle.crypto.tls.DTLSEpoch]
      mth insns count: 153
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0077 A[Catch:{ IOException -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0078 A[Catch:{ IOException -> 0x0142 }] */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int receive(byte[] r22, int r23, int r24, int r25) {
        /*
            r21 = this;
            r1 = r21
            r2 = 0
            r3 = r2
        L_0x0004:
            int r4 = r21.getReceiveLimit()
            r5 = r24
            int r4 = java.lang.Math.min(r5, r4)
            r6 = 13
            int r4 = r4 + r6
            if (r3 == 0) goto L_0x0016
            int r7 = r3.length
            if (r7 >= r4) goto L_0x0018
        L_0x0016:
            byte[] r3 = new byte[r4]
        L_0x0018:
            org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit r7 = r1.n     // Catch:{ IOException -> 0x0142 }
            if (r7 == 0) goto L_0x002a
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0142 }
            long r9 = r1.p     // Catch:{ IOException -> 0x0142 }
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 <= 0) goto L_0x002a
            r1.n = r2     // Catch:{ IOException -> 0x0142 }
            r1.o = r2     // Catch:{ IOException -> 0x0142 }
        L_0x002a:
            r14 = 0
            r15 = r25
            int r4 = r1.a(r3, r14, r4, r15)     // Catch:{ IOException -> 0x0142 }
            if (r4 >= 0) goto L_0x0034
            return r4
        L_0x0034:
            if (r4 >= r6) goto L_0x003e
        L_0x0036:
            r5 = r23
            r19 = r3
            r3 = r22
            goto L_0x013e
        L_0x003e:
            r7 = 11
            int r7 = org.bouncycastle.crypto.tls.TlsUtils.readUint16(r3, r7)     // Catch:{ IOException -> 0x0142 }
            int r7 = r7 + r6
            if (r4 == r7) goto L_0x0048
            goto L_0x0036
        L_0x0048:
            short r6 = org.bouncycastle.crypto.tls.TlsUtils.readUint8(r3, r14)     // Catch:{ IOException -> 0x0142 }
            switch(r6) {
                case 20: goto L_0x0050;
                case 21: goto L_0x0050;
                case 22: goto L_0x0050;
                case 23: goto L_0x0050;
                case 24: goto L_0x0050;
                default: goto L_0x004f;
            }     // Catch:{ IOException -> 0x0142 }
        L_0x004f:
            goto L_0x0036
        L_0x0050:
            r7 = 3
            int r13 = org.bouncycastle.crypto.tls.TlsUtils.readUint16(r3, r7)     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.DTLSEpoch r7 = r1.l     // Catch:{ IOException -> 0x0142 }
            int r7 = r7.c()     // Catch:{ IOException -> 0x0142 }
            if (r13 != r7) goto L_0x0061
            org.bouncycastle.crypto.tls.DTLSEpoch r7 = r1.l     // Catch:{ IOException -> 0x0142 }
        L_0x005f:
            r12 = r7
            goto L_0x0075
        L_0x0061:
            r7 = 22
            if (r6 != r7) goto L_0x0074
            org.bouncycastle.crypto.tls.DTLSEpoch r7 = r1.o     // Catch:{ IOException -> 0x0142 }
            if (r7 == 0) goto L_0x0074
            org.bouncycastle.crypto.tls.DTLSEpoch r7 = r1.o     // Catch:{ IOException -> 0x0142 }
            int r7 = r7.c()     // Catch:{ IOException -> 0x0142 }
            if (r13 != r7) goto L_0x0074
            org.bouncycastle.crypto.tls.DTLSEpoch r7 = r1.o     // Catch:{ IOException -> 0x0142 }
            goto L_0x005f
        L_0x0074:
            r12 = r2
        L_0x0075:
            if (r12 != 0) goto L_0x0078
            goto L_0x0036
        L_0x0078:
            r7 = 5
            long r10 = org.bouncycastle.crypto.tls.TlsUtils.readUint48(r3, r7)     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.DTLSReplayWindow r7 = r12.d()     // Catch:{ IOException -> 0x0142 }
            boolean r7 = r7.a(r10)     // Catch:{ IOException -> 0x0142 }
            if (r7 == 0) goto L_0x0088
            goto L_0x0036
        L_0x0088:
            r8 = 1
            org.bouncycastle.crypto.tls.ProtocolVersion r9 = org.bouncycastle.crypto.tls.TlsUtils.readVersion(r3, r8)     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.ProtocolVersion r7 = r1.g     // Catch:{ IOException -> 0x0142 }
            if (r7 == 0) goto L_0x009a
            org.bouncycastle.crypto.tls.ProtocolVersion r7 = r1.g     // Catch:{ IOException -> 0x0142 }
            boolean r7 = r7.equals(r9)     // Catch:{ IOException -> 0x0142 }
            if (r7 != 0) goto L_0x009a
            goto L_0x0036
        L_0x009a:
            org.bouncycastle.crypto.tls.TlsCipher r7 = r12.b()     // Catch:{ IOException -> 0x0142 }
            int r8 = r12.c()     // Catch:{ IOException -> 0x0142 }
            long r16 = a(r8, r10)     // Catch:{ IOException -> 0x0142 }
            r18 = 13
            int r4 = r4 + -13
            r2 = r9
            r8 = r16
            r14 = r10
            r10 = r6
            r11 = r3
            r19 = r3
            r3 = r12
            r12 = r18
            r5 = r13
            r13 = r4
            byte[] r4 = r7.decodeCiphertext(r8, r10, r11, r12, r13)     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.DTLSReplayWindow r3 = r3.d()     // Catch:{ IOException -> 0x0142 }
            r3.b(r14)     // Catch:{ IOException -> 0x0142 }
            int r3 = r4.length     // Catch:{ IOException -> 0x0142 }
            int r7 = r1.i     // Catch:{ IOException -> 0x0142 }
            if (r3 <= r7) goto L_0x00ce
        L_0x00c7:
            r3 = r22
            r5 = r23
            r2 = 0
            goto L_0x013e
        L_0x00ce:
            org.bouncycastle.crypto.tls.ProtocolVersion r3 = r1.g     // Catch:{ IOException -> 0x0142 }
            if (r3 != 0) goto L_0x00d4
            r1.g = r2     // Catch:{ IOException -> 0x0142 }
        L_0x00d4:
            switch(r6) {
                case 20: goto L_0x010f;
                case 21: goto L_0x00ed;
                case 22: goto L_0x00dd;
                case 23: goto L_0x00d8;
                case 24: goto L_0x00c7;
                default: goto L_0x00d7;
            }     // Catch:{ IOException -> 0x0142 }
        L_0x00d7:
            goto L_0x0126
        L_0x00d8:
            boolean r2 = r1.h     // Catch:{ IOException -> 0x0142 }
            if (r2 == 0) goto L_0x0126
            goto L_0x00c7
        L_0x00dd:
            boolean r2 = r1.h     // Catch:{ IOException -> 0x0142 }
            if (r2 != 0) goto L_0x0126
            org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit r2 = r1.n     // Catch:{ IOException -> 0x0142 }
            if (r2 == 0) goto L_0x00c7
            org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit r2 = r1.n     // Catch:{ IOException -> 0x0142 }
            int r3 = r4.length     // Catch:{ IOException -> 0x0142 }
            r6 = 0
            r2.a(r5, r4, r6, r3)     // Catch:{ IOException -> 0x0142 }
            goto L_0x00c7
        L_0x00ed:
            int r2 = r4.length     // Catch:{ IOException -> 0x0142 }
            r3 = 2
            if (r2 != r3) goto L_0x00c7
            r2 = 0
            byte r2 = r4[r2]     // Catch:{ IOException -> 0x0142 }
            short r2 = (short) r2     // Catch:{ IOException -> 0x0142 }
            r5 = 1
            byte r4 = r4[r5]     // Catch:{ IOException -> 0x0142 }
            short r4 = (short) r4     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.TlsPeer r5 = r1.c     // Catch:{ IOException -> 0x0142 }
            r5.notifyAlertReceived(r2, r4)     // Catch:{ IOException -> 0x0142 }
            if (r2 != r3) goto L_0x0109
            r1.a(r4)     // Catch:{ IOException -> 0x0142 }
            org.bouncycastle.crypto.tls.TlsFatalAlert r2 = new org.bouncycastle.crypto.tls.TlsFatalAlert     // Catch:{ IOException -> 0x0142 }
            r2.<init>(r4)     // Catch:{ IOException -> 0x0142 }
            throw r2     // Catch:{ IOException -> 0x0142 }
        L_0x0109:
            if (r4 != 0) goto L_0x00c7
            r21.d()     // Catch:{ IOException -> 0x0142 }
            goto L_0x00c7
        L_0x010f:
            r5 = 1
            r2 = 0
        L_0x0111:
            int r3 = r4.length     // Catch:{ IOException -> 0x0142 }
            if (r2 >= r3) goto L_0x00c7
            short r3 = org.bouncycastle.crypto.tls.TlsUtils.readUint8(r4, r2)     // Catch:{ IOException -> 0x0142 }
            if (r3 == r5) goto L_0x011b
            goto L_0x0123
        L_0x011b:
            org.bouncycastle.crypto.tls.DTLSEpoch r3 = r1.k     // Catch:{ IOException -> 0x0142 }
            if (r3 == 0) goto L_0x0123
            org.bouncycastle.crypto.tls.DTLSEpoch r3 = r1.k     // Catch:{ IOException -> 0x0142 }
            r1.l = r3     // Catch:{ IOException -> 0x0142 }
        L_0x0123:
            int r2 = r2 + 1
            goto L_0x0111
        L_0x0126:
            boolean r2 = r1.h     // Catch:{ IOException -> 0x0142 }
            if (r2 != 0) goto L_0x0133
            org.bouncycastle.crypto.tls.DTLSHandshakeRetransmit r2 = r1.n     // Catch:{ IOException -> 0x0142 }
            if (r2 == 0) goto L_0x0133
            r2 = 0
            r1.n = r2     // Catch:{ IOException -> 0x0142 }
            r1.o = r2     // Catch:{ IOException -> 0x0142 }
        L_0x0133:
            int r2 = r4.length     // Catch:{ IOException -> 0x0142 }
            r3 = r22
            r5 = r23
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r3, r5, r2)     // Catch:{ IOException -> 0x0142 }
            int r2 = r4.length     // Catch:{ IOException -> 0x0142 }
            return r2
        L_0x013e:
            r3 = r19
            goto L_0x0004
        L_0x0142:
            r0 = move-exception
            r2 = r0
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.DTLSRecordLayer.receive(byte[], int, int, int):int");
    }

    public void send(byte[] bArr, int i2, int i3) {
        short s;
        if (this.h || this.m == this.o) {
            s = 22;
            if (TlsUtils.readUint8(bArr, i2) == 20) {
                DTLSEpoch dTLSEpoch = null;
                if (this.h) {
                    dTLSEpoch = this.k;
                } else if (this.m == this.o) {
                    dTLSEpoch = this.j;
                }
                if (dTLSEpoch == null) {
                    throw new IllegalStateException();
                }
                byte[] bArr2 = {1};
                a(20, bArr2, 0, bArr2.length);
                this.m = dTLSEpoch;
            }
        } else {
            s = 23;
        }
        a(s, bArr, i2, i3);
    }
}
