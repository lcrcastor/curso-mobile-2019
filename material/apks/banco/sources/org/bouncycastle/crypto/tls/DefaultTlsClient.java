package org.bouncycastle.crypto.tls;

public abstract class DefaultTlsClient extends AbstractTlsClient {
    public DefaultTlsClient() {
    }

    public DefaultTlsClient(TlsCipherFactory tlsCipherFactory) {
        super(tlsCipherFactory);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createDHEKeyExchange(int i) {
        return new TlsDHEKeyExchange(i, this.supportedSignatureAlgorithms, null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createDHKeyExchange(int i) {
        return new TlsDHKeyExchange(i, this.supportedSignatureAlgorithms, null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createECDHEKeyExchange(int i) {
        TlsECDHEKeyExchange tlsECDHEKeyExchange = new TlsECDHEKeyExchange(i, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
        return tlsECDHEKeyExchange;
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createECDHKeyExchange(int i) {
        TlsECDHKeyExchange tlsECDHKeyExchange = new TlsECDHKeyExchange(i, this.supportedSignatureAlgorithms, this.namedCurves, this.clientECPointFormats, this.serverECPointFormats);
        return tlsECDHKeyExchange;
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange createRSAKeyExchange() {
        return new TlsRSAKeyExchange(this.supportedSignatureAlgorithms);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005d, code lost:
        return r9.cipherFactory.createCipher(r9.context, 101, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0068, code lost:
        return r9.cipherFactory.createCipher(r9.context, 100, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00aa, code lost:
        return r9.cipherFactory.createCipher(r9.context, 20, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b5, code lost:
        return r9.cipherFactory.createCipher(r9.context, 19, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d1, code lost:
        return r9.cipherFactory.createCipher(r9.context, 7, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e3, code lost:
        return r9.cipherFactory.createCipher(r9.context, 12, 3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ee, code lost:
        return r9.cipherFactory.createCipher(r9.context, 11, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f9, code lost:
        return r9.cipherFactory.createCipher(r9.context, 10, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x011f, code lost:
        return r9.cipherFactory.createCipher(r9.context, 9, 3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0128, code lost:
        return r9.cipherFactory.createCipher(r9.context, 8, 3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013a, code lost:
        return r9.cipherFactory.createCipher(r9.context, 9, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0143, code lost:
        return r9.cipherFactory.createCipher(r9.context, 8, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x014c, code lost:
        return r9.cipherFactory.createCipher(r9.context, 2, 2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.crypto.tls.TlsCipher getCipher() {
        /*
            r9 = this;
            int r0 = r9.selectedCipherSuite
            r1 = 1
            r2 = 0
            r3 = 2
            switch(r0) {
                case 1: goto L_0x015f;
                case 2: goto L_0x0156;
                default: goto L_0x0008;
            }
        L_0x0008:
            switch(r0) {
                case 4: goto L_0x014d;
                case 5: goto L_0x0144;
                default: goto L_0x000b;
            }
        L_0x000b:
            r1 = 8
            switch(r0) {
                case 47: goto L_0x013b;
                case 48: goto L_0x013b;
                case 49: goto L_0x013b;
                case 50: goto L_0x013b;
                case 51: goto L_0x013b;
                default: goto L_0x0010;
            }
        L_0x0010:
            r4 = 9
            switch(r0) {
                case 53: goto L_0x0132;
                case 54: goto L_0x0132;
                case 55: goto L_0x0132;
                case 56: goto L_0x0132;
                case 57: goto L_0x0132;
                default: goto L_0x0015;
            }
        L_0x0015:
            r5 = 12
            r6 = 3
            switch(r0) {
                case 59: goto L_0x0129;
                case 60: goto L_0x0120;
                case 61: goto L_0x0117;
                case 62: goto L_0x0120;
                case 63: goto L_0x0120;
                case 64: goto L_0x0120;
                case 65: goto L_0x010e;
                case 66: goto L_0x010e;
                case 67: goto L_0x010e;
                case 68: goto L_0x010e;
                case 69: goto L_0x010e;
                default: goto L_0x001b;
            }
        L_0x001b:
            switch(r0) {
                case 103: goto L_0x0120;
                case 104: goto L_0x0117;
                case 105: goto L_0x0117;
                case 106: goto L_0x0117;
                case 107: goto L_0x0117;
                default: goto L_0x001e;
            }
        L_0x001e:
            r7 = 13
            switch(r0) {
                case 132: goto L_0x0105;
                case 133: goto L_0x0105;
                case 134: goto L_0x0105;
                case 135: goto L_0x0105;
                case 136: goto L_0x0105;
                default: goto L_0x0023;
            }
        L_0x0023:
            switch(r0) {
                case 150: goto L_0x00fa;
                case 151: goto L_0x00fa;
                case 152: goto L_0x00fa;
                case 153: goto L_0x00fa;
                case 154: goto L_0x00fa;
                default: goto L_0x0026;
            }
        L_0x0026:
            switch(r0) {
                case 156: goto L_0x00ef;
                case 157: goto L_0x00e4;
                case 158: goto L_0x00ef;
                case 159: goto L_0x00e4;
                case 160: goto L_0x00ef;
                case 161: goto L_0x00e4;
                case 162: goto L_0x00ef;
                case 163: goto L_0x00e4;
                case 164: goto L_0x00ef;
                case 165: goto L_0x00e4;
                default: goto L_0x0029;
            }
        L_0x0029:
            switch(r0) {
                case 186: goto L_0x00db;
                case 187: goto L_0x00db;
                case 188: goto L_0x00db;
                case 189: goto L_0x00db;
                case 190: goto L_0x00db;
                default: goto L_0x002c;
            }
        L_0x002c:
            switch(r0) {
                case 192: goto L_0x00d2;
                case 193: goto L_0x00d2;
                case 194: goto L_0x00d2;
                case 195: goto L_0x00d2;
                case 196: goto L_0x00d2;
                default: goto L_0x002f;
            }
        L_0x002f:
            switch(r0) {
                case 49153: goto L_0x0156;
                case 49154: goto L_0x0144;
                case 49155: goto L_0x00c8;
                case 49156: goto L_0x013b;
                case 49157: goto L_0x0132;
                case 49158: goto L_0x0156;
                case 49159: goto L_0x0144;
                case 49160: goto L_0x00c8;
                case 49161: goto L_0x013b;
                case 49162: goto L_0x0132;
                case 49163: goto L_0x0156;
                case 49164: goto L_0x0144;
                case 49165: goto L_0x00c8;
                case 49166: goto L_0x013b;
                case 49167: goto L_0x0132;
                case 49168: goto L_0x0156;
                case 49169: goto L_0x0144;
                case 49170: goto L_0x00c8;
                case 49171: goto L_0x013b;
                case 49172: goto L_0x0132;
                default: goto L_0x0032;
            }
        L_0x0032:
            r8 = 4
            switch(r0) {
                case 49187: goto L_0x0120;
                case 49188: goto L_0x00bf;
                case 49189: goto L_0x0120;
                case 49190: goto L_0x00bf;
                case 49191: goto L_0x0120;
                case 49192: goto L_0x00bf;
                case 49193: goto L_0x0120;
                case 49194: goto L_0x00bf;
                case 49195: goto L_0x00ef;
                case 49196: goto L_0x00e4;
                case 49197: goto L_0x00ef;
                case 49198: goto L_0x00e4;
                case 49199: goto L_0x00ef;
                case 49200: goto L_0x00e4;
                case 49201: goto L_0x00ef;
                case 49202: goto L_0x00e4;
                default: goto L_0x0036;
            }
        L_0x0036:
            switch(r0) {
                case 49266: goto L_0x00db;
                case 49267: goto L_0x00b6;
                case 49268: goto L_0x00db;
                case 49269: goto L_0x00b6;
                case 49270: goto L_0x00db;
                case 49271: goto L_0x00b6;
                case 49272: goto L_0x00db;
                case 49273: goto L_0x00b6;
                case 49274: goto L_0x00ab;
                case 49275: goto L_0x00a0;
                case 49276: goto L_0x00ab;
                case 49277: goto L_0x00a0;
                case 49278: goto L_0x00ab;
                case 49279: goto L_0x00a0;
                case 49280: goto L_0x00ab;
                case 49281: goto L_0x00a0;
                case 49282: goto L_0x00ab;
                case 49283: goto L_0x00a0;
                default: goto L_0x0039;
            }
        L_0x0039:
            switch(r0) {
                case 49286: goto L_0x00ab;
                case 49287: goto L_0x00a0;
                case 49288: goto L_0x00ab;
                case 49289: goto L_0x00a0;
                case 49290: goto L_0x00ab;
                case 49291: goto L_0x00a0;
                case 49292: goto L_0x00ab;
                case 49293: goto L_0x00a0;
                default: goto L_0x003c;
            }
        L_0x003c:
            switch(r0) {
                case 49308: goto L_0x0095;
                case 49309: goto L_0x008a;
                case 49310: goto L_0x0095;
                case 49311: goto L_0x008a;
                case 49312: goto L_0x007f;
                case 49313: goto L_0x0074;
                case 49314: goto L_0x007f;
                case 49315: goto L_0x0074;
                default: goto L_0x003f;
            }
        L_0x003f:
            switch(r0) {
                case 52243: goto L_0x0069;
                case 52244: goto L_0x0069;
                case 52245: goto L_0x0069;
                default: goto L_0x0042;
            }
        L_0x0042:
            switch(r0) {
                case 58384: goto L_0x005e;
                case 58385: goto L_0x0053;
                case 58386: goto L_0x005e;
                case 58387: goto L_0x0053;
                case 58388: goto L_0x005e;
                case 58389: goto L_0x0053;
                default: goto L_0x0045;
            }
        L_0x0045:
            switch(r0) {
                case 58398: goto L_0x005e;
                case 58399: goto L_0x0053;
                default: goto L_0x0048;
            }
        L_0x0048:
            switch(r0) {
                case 10: goto L_0x00c8;
                case 13: goto L_0x00c8;
                case 16: goto L_0x00c8;
                case 19: goto L_0x00c8;
                case 22: goto L_0x00c8;
                default: goto L_0x004b;
            }
        L_0x004b:
            org.bouncycastle.crypto.tls.TlsFatalAlert r0 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r1 = 80
            r0.<init>(r1)
            throw r0
        L_0x0053:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r2 = 101(0x65, float:1.42E-43)
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r3)
            return r0
        L_0x005e:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r2 = 100
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r3)
            return r0
        L_0x0069:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 102(0x66, float:1.43E-43)
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x0074:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 18
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x007f:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 16
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x008a:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 17
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x0095:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 15
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x00a0:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 20
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x00ab:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 19
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x00b6:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r7, r8)
            return r0
        L_0x00bf:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r4, r8)
            return r0
        L_0x00c8:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r2 = 7
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r3)
            return r0
        L_0x00d2:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r7, r6)
            return r0
        L_0x00db:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r5, r6)
            return r0
        L_0x00e4:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 11
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x00ef:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r3 = 10
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r2)
            return r0
        L_0x00fa:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            r2 = 14
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r3)
            return r0
        L_0x0105:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r7, r3)
            return r0
        L_0x010e:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r5, r3)
            return r0
        L_0x0117:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r4, r6)
            return r0
        L_0x0120:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r2 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r2, r1, r6)
            return r0
        L_0x0129:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r6)
            return r0
        L_0x0132:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r4, r3)
            return r0
        L_0x013b:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r2 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r2, r1, r3)
            return r0
        L_0x0144:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r3, r3)
            return r0
        L_0x014d:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r2 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r2, r3, r1)
            return r0
        L_0x0156:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r1 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r1, r2, r3)
            return r0
        L_0x015f:
            org.bouncycastle.crypto.tls.TlsCipherFactory r0 = r9.cipherFactory
            org.bouncycastle.crypto.tls.TlsClientContext r3 = r9.context
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.createCipher(r3, r2, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.DefaultTlsClient.getCipher():org.bouncycastle.crypto.tls.TlsCipher");
    }

    public int[] getCipherSuites() {
        return new int[]{CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, 60, 47};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        return createECDHEKeyExchange(19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0053, code lost:
        return createECDHKeyExchange(18);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
        return createECDHEKeyExchange(17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
        return createECDHKeyExchange(16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0067, code lost:
        return createDHEKeyExchange(5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x006d, code lost:
        return createDHEKeyExchange(3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0074, code lost:
        return createDHKeyExchange(9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007a, code lost:
        return createDHKeyExchange(7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.bouncycastle.crypto.tls.TlsKeyExchange getKeyExchange() {
        /*
            r2 = this;
            int r0 = r2.selectedCipherSuite
            switch(r0) {
                case 1: goto L_0x007b;
                case 2: goto L_0x007b;
                default: goto L_0x0005;
            }
        L_0x0005:
            switch(r0) {
                case 4: goto L_0x007b;
                case 5: goto L_0x007b;
                default: goto L_0x0008;
            }
        L_0x0008:
            switch(r0) {
                case 47: goto L_0x007b;
                case 48: goto L_0x0075;
                case 49: goto L_0x006e;
                case 50: goto L_0x0068;
                case 51: goto L_0x0062;
                default: goto L_0x000b;
            }
        L_0x000b:
            switch(r0) {
                case 53: goto L_0x007b;
                case 54: goto L_0x0075;
                case 55: goto L_0x006e;
                case 56: goto L_0x0068;
                case 57: goto L_0x0062;
                default: goto L_0x000e;
            }
        L_0x000e:
            switch(r0) {
                case 59: goto L_0x007b;
                case 60: goto L_0x007b;
                case 61: goto L_0x007b;
                case 62: goto L_0x0075;
                case 63: goto L_0x006e;
                case 64: goto L_0x0068;
                case 65: goto L_0x007b;
                case 66: goto L_0x0075;
                case 67: goto L_0x006e;
                case 68: goto L_0x0068;
                case 69: goto L_0x0062;
                default: goto L_0x0011;
            }
        L_0x0011:
            switch(r0) {
                case 103: goto L_0x0062;
                case 104: goto L_0x0075;
                case 105: goto L_0x006e;
                case 106: goto L_0x0068;
                case 107: goto L_0x0062;
                default: goto L_0x0014;
            }
        L_0x0014:
            switch(r0) {
                case 132: goto L_0x007b;
                case 133: goto L_0x0075;
                case 134: goto L_0x006e;
                case 135: goto L_0x0068;
                case 136: goto L_0x0062;
                default: goto L_0x0017;
            }
        L_0x0017:
            switch(r0) {
                case 150: goto L_0x007b;
                case 151: goto L_0x0075;
                case 152: goto L_0x006e;
                case 153: goto L_0x0068;
                case 154: goto L_0x0062;
                default: goto L_0x001a;
            }
        L_0x001a:
            switch(r0) {
                case 156: goto L_0x007b;
                case 157: goto L_0x007b;
                case 158: goto L_0x0062;
                case 159: goto L_0x0062;
                case 160: goto L_0x006e;
                case 161: goto L_0x006e;
                case 162: goto L_0x0068;
                case 163: goto L_0x0068;
                case 164: goto L_0x0075;
                case 165: goto L_0x0075;
                default: goto L_0x001d;
            }
        L_0x001d:
            switch(r0) {
                case 186: goto L_0x007b;
                case 187: goto L_0x0075;
                case 188: goto L_0x006e;
                case 189: goto L_0x0068;
                case 190: goto L_0x0062;
                default: goto L_0x0020;
            }
        L_0x0020:
            switch(r0) {
                case 192: goto L_0x007b;
                case 193: goto L_0x0075;
                case 194: goto L_0x006e;
                case 195: goto L_0x0068;
                case 196: goto L_0x0062;
                default: goto L_0x0023;
            }
        L_0x0023:
            switch(r0) {
                case 49153: goto L_0x005b;
                case 49154: goto L_0x005b;
                case 49155: goto L_0x005b;
                case 49156: goto L_0x005b;
                case 49157: goto L_0x005b;
                case 49158: goto L_0x0054;
                case 49159: goto L_0x0054;
                case 49160: goto L_0x0054;
                case 49161: goto L_0x0054;
                case 49162: goto L_0x0054;
                case 49163: goto L_0x004d;
                case 49164: goto L_0x004d;
                case 49165: goto L_0x004d;
                case 49166: goto L_0x004d;
                case 49167: goto L_0x004d;
                case 49168: goto L_0x0046;
                case 49169: goto L_0x0046;
                case 49170: goto L_0x0046;
                case 49171: goto L_0x0046;
                case 49172: goto L_0x0046;
                default: goto L_0x0026;
            }
        L_0x0026:
            switch(r0) {
                case 49187: goto L_0x0054;
                case 49188: goto L_0x0054;
                case 49189: goto L_0x005b;
                case 49190: goto L_0x005b;
                case 49191: goto L_0x0046;
                case 49192: goto L_0x0046;
                case 49193: goto L_0x004d;
                case 49194: goto L_0x004d;
                case 49195: goto L_0x0054;
                case 49196: goto L_0x0054;
                case 49197: goto L_0x005b;
                case 49198: goto L_0x005b;
                case 49199: goto L_0x0046;
                case 49200: goto L_0x0046;
                case 49201: goto L_0x004d;
                case 49202: goto L_0x004d;
                default: goto L_0x0029;
            }
        L_0x0029:
            switch(r0) {
                case 49266: goto L_0x0054;
                case 49267: goto L_0x0054;
                case 49268: goto L_0x005b;
                case 49269: goto L_0x005b;
                case 49270: goto L_0x0046;
                case 49271: goto L_0x0046;
                case 49272: goto L_0x004d;
                case 49273: goto L_0x004d;
                case 49274: goto L_0x007b;
                case 49275: goto L_0x007b;
                case 49276: goto L_0x0062;
                case 49277: goto L_0x0062;
                case 49278: goto L_0x006e;
                case 49279: goto L_0x006e;
                case 49280: goto L_0x0068;
                case 49281: goto L_0x0068;
                case 49282: goto L_0x0075;
                case 49283: goto L_0x0075;
                default: goto L_0x002c;
            }
        L_0x002c:
            switch(r0) {
                case 49286: goto L_0x0054;
                case 49287: goto L_0x0054;
                case 49288: goto L_0x005b;
                case 49289: goto L_0x005b;
                case 49290: goto L_0x0046;
                case 49291: goto L_0x0046;
                case 49292: goto L_0x004d;
                case 49293: goto L_0x004d;
                default: goto L_0x002f;
            }
        L_0x002f:
            switch(r0) {
                case 49308: goto L_0x007b;
                case 49309: goto L_0x007b;
                case 49310: goto L_0x0062;
                case 49311: goto L_0x0062;
                case 49312: goto L_0x007b;
                case 49313: goto L_0x007b;
                case 49314: goto L_0x0062;
                case 49315: goto L_0x0062;
                default: goto L_0x0032;
            }
        L_0x0032:
            switch(r0) {
                case 52243: goto L_0x0046;
                case 52244: goto L_0x0054;
                case 52245: goto L_0x0062;
                default: goto L_0x0035;
            }
        L_0x0035:
            switch(r0) {
                case 58384: goto L_0x007b;
                case 58385: goto L_0x007b;
                case 58386: goto L_0x0046;
                case 58387: goto L_0x0046;
                case 58388: goto L_0x0054;
                case 58389: goto L_0x0054;
                default: goto L_0x0038;
            }
        L_0x0038:
            switch(r0) {
                case 58398: goto L_0x0062;
                case 58399: goto L_0x0062;
                default: goto L_0x003b;
            }
        L_0x003b:
            switch(r0) {
                case 10: goto L_0x007b;
                case 13: goto L_0x0075;
                case 16: goto L_0x006e;
                case 19: goto L_0x0068;
                case 22: goto L_0x0062;
                default: goto L_0x003e;
            }
        L_0x003e:
            org.bouncycastle.crypto.tls.TlsFatalAlert r0 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r1 = 80
            r0.<init>(r1)
            throw r0
        L_0x0046:
            r0 = 19
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createECDHEKeyExchange(r0)
            return r0
        L_0x004d:
            r0 = 18
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createECDHKeyExchange(r0)
            return r0
        L_0x0054:
            r0 = 17
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createECDHEKeyExchange(r0)
            return r0
        L_0x005b:
            r0 = 16
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createECDHKeyExchange(r0)
            return r0
        L_0x0062:
            r0 = 5
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createDHEKeyExchange(r0)
            return r0
        L_0x0068:
            r0 = 3
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createDHEKeyExchange(r0)
            return r0
        L_0x006e:
            r0 = 9
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createDHKeyExchange(r0)
            return r0
        L_0x0075:
            r0 = 7
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createDHKeyExchange(r0)
            return r0
        L_0x007b:
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r2.createRSAKeyExchange()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.DefaultTlsClient.getKeyExchange():org.bouncycastle.crypto.tls.TlsKeyExchange");
    }
}
