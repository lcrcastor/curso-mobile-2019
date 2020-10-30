package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.crypto.prng.ThreadedSeedGenerator;
import org.bouncycastle.util.Arrays;

public class TlsClientProtocol extends TlsProtocol {
    protected TlsAuthentication authentication;
    protected CertificateRequest certificateRequest;
    protected CertificateStatus certificateStatus;
    protected TlsKeyExchange keyExchange;
    protected byte[] selectedSessionID;
    protected TlsClient tlsClient;
    protected TlsClientContextImpl tlsClientContext;

    public TlsClientProtocol(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, outputStream, a());
    }

    public TlsClientProtocol(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        super(inputStream, outputStream, secureRandom);
        this.tlsClient = null;
        this.tlsClientContext = null;
        this.selectedSessionID = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }

    private static SecureRandom a() {
        ThreadedSeedGenerator threadedSeedGenerator = new ThreadedSeedGenerator();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(threadedSeedGenerator.generateSeed(20, true));
        return secureRandom;
    }

    /* access modifiers changed from: protected */
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.selectedSessionID = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateStatus = null;
        this.certificateRequest = null;
    }

    public void connect(TlsClient tlsClient2) {
        if (tlsClient2 == null) {
            throw new IllegalArgumentException("'tlsClient' cannot be null");
        } else if (this.tlsClient != null) {
            throw new IllegalStateException("'connect' can only be called once");
        } else {
            this.tlsClient = tlsClient2;
            this.securityParameters = new SecurityParameters();
            this.securityParameters.a = 1;
            this.tlsClientContext = new TlsClientContextImpl(this.secureRandom, this.securityParameters);
            this.securityParameters.g = createRandomBlock(tlsClient2.shouldUseGMTUnixTime(), this.tlsClientContext.getNonceRandomGenerator());
            this.tlsClient.init(this.tlsClientContext);
            this.recordStream.a((TlsContext) this.tlsClientContext);
            TlsSession sessionToResume = tlsClient2.getSessionToResume();
            if (sessionToResume != null) {
                SessionParameters exportSessionParameters = sessionToResume.exportSessionParameters();
                if (exportSessionParameters != null) {
                    this.tlsSession = sessionToResume;
                    this.sessionParameters = exportSessionParameters;
                }
            }
            sendClientHelloMessage();
            this.connection_state = 1;
            completeHandshake();
        }
    }

    /* access modifiers changed from: protected */
    public AbstractTlsContext getContext() {
        return this.tlsClientContext;
    }

    /* access modifiers changed from: protected */
    public TlsPeer getPeer() {
        return this.tlsClient;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008a, code lost:
        r11.keyExchange.skipServerCredentials();
        r11.authentication = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0091, code lost:
        r11.keyExchange.skipServerKeyExchange();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0096, code lost:
        assertEmpty(r0);
        r11.connection_state = 8;
        r11.recordStream.h().sealHashAlgorithms();
        r12 = r11.tlsClient.getClientSupplementalData();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00aa, code lost:
        if (r12 == null) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ac, code lost:
        sendSupplementalDataMessage(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00af, code lost:
        r11.connection_state = 9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00b5, code lost:
        if (r11.certificateRequest != null) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b7, code lost:
        r11.keyExchange.skipClientCredentials();
        r12 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00be, code lost:
        r12 = r11.authentication.getClientCredentials(r11.certificateRequest);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c6, code lost:
        if (r12 != null) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c8, code lost:
        r11.keyExchange.skipClientCredentials();
        r13 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00cf, code lost:
        sendCertificateMessage(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d3, code lost:
        r11.keyExchange.processClientCredentials(r12);
        r13 = r12.getCertificate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00dd, code lost:
        r11.connection_state = 10;
        sendClientKeyExchangeMessage();
        r11.connection_state = 11;
        establishMasterSecret(getContext(), r11.keyExchange);
        r11.recordStream.a(getPeer().getCompression(), getPeer().getCipher());
        r13 = r11.recordStream.i();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010a, code lost:
        if (r12 == null) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x010e, code lost:
        if ((r12 instanceof org.bouncycastle.crypto.tls.TlsSignerCredentials) == false) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0110, code lost:
        r12 = (org.bouncycastle.crypto.tls.TlsSignerCredentials) r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x011a, code lost:
        if (org.bouncycastle.crypto.tls.TlsUtils.isTLSv12(getContext()) == false) goto L_0x0133;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x011c, code lost:
        r9 = r12.getSignatureAndHashAlgorithm();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0120, code lost:
        if (r9 != null) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0129, code lost:
        throw new org.bouncycastle.crypto.tls.TlsFatalAlert(80);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x012a, code lost:
        r13 = r13.getFinalHash(r9.getHash());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0133, code lost:
        r13 = getCurrentPRFHash(getContext(), r13, null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x013b, code lost:
        sendCertificateVerifyMessage(new org.bouncycastle.crypto.tls.DigitallySigned(r9, r12.generateCertificateSignature(r13)));
        r11.connection_state = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0149, code lost:
        sendChangeCipherSpecMessage();
        sendFinishedMessage();
        r11.connection_state = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0151, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x019f, code lost:
        r11.keyExchange.skipServerCredentials();
        r11.authentication = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01a6, code lost:
        r11.keyExchange.processServerKeyExchange(r0);
        assertEmpty(r0);
        r12 = 6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleHandshakeMessage(short r12, byte[] r13) {
        /*
            r11 = this;
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r13)
            boolean r13 = r11.resumedSession
            r1 = 15
            r2 = 20
            r3 = 16
            r4 = 13
            r5 = 2
            r6 = 10
            if (r13 == 0) goto L_0x002e
            if (r12 != r2) goto L_0x0028
            short r12 = r11.connection_state
            if (r12 == r5) goto L_0x001b
            goto L_0x0028
        L_0x001b:
            r11.processFinishedMessage(r0)
            r11.connection_state = r1
            r11.sendFinishedMessage()
            r11.connection_state = r4
            r11.connection_state = r3
            return
        L_0x0028:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x002e:
            r13 = 40
            if (r12 == 0) goto L_0x02a8
            r7 = 12
            r8 = 8
            r9 = 0
            if (r12 == r5) goto L_0x022e
            r10 = 4
            if (r12 == r10) goto L_0x020e
            if (r12 == r2) goto L_0x01f1
            switch(r12) {
                case 11: goto L_0x01b2;
                case 12: goto L_0x0191;
                case 13: goto L_0x0152;
                case 14: goto L_0x007c;
                default: goto L_0x0041;
            }
        L_0x0041:
            switch(r12) {
                case 22: goto L_0x005c;
                case 23: goto L_0x004a;
                default: goto L_0x0044;
            }
        L_0x0044:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x004a:
            short r12 = r11.connection_state
            if (r12 == r5) goto L_0x0054
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0054:
            java.util.Vector r12 = readSupplementalDataMessage(r0)
            r11.handleSupplementalData(r12)
            return
        L_0x005c:
            short r12 = r11.connection_state
            if (r12 == r10) goto L_0x0066
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0066:
            boolean r12 = r11.allowCertificateStatus
            if (r12 != 0) goto L_0x0070
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0070:
            org.bouncycastle.crypto.tls.CertificateStatus r12 = org.bouncycastle.crypto.tls.CertificateStatus.parse(r0)
            r11.certificateStatus = r12
            assertEmpty(r0)
            r12 = 5
            goto L_0x01af
        L_0x007c:
            short r12 = r11.connection_state
            switch(r12) {
                case 2: goto L_0x0087;
                case 3: goto L_0x008a;
                case 4: goto L_0x0091;
                case 5: goto L_0x0091;
                case 6: goto L_0x0096;
                case 7: goto L_0x0096;
                default: goto L_0x0081;
            }
        L_0x0081:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r13)
            throw r12
        L_0x0087:
            r11.handleSupplementalData(r9)
        L_0x008a:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.skipServerCredentials()
            r11.authentication = r9
        L_0x0091:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.skipServerKeyExchange()
        L_0x0096:
            assertEmpty(r0)
            r11.connection_state = r8
            org.bouncycastle.crypto.tls.RecordStream r12 = r11.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r12 = r12.h()
            r12.sealHashAlgorithms()
            org.bouncycastle.crypto.tls.TlsClient r12 = r11.tlsClient
            java.util.Vector r12 = r12.getClientSupplementalData()
            if (r12 == 0) goto L_0x00af
            r11.sendSupplementalDataMessage(r12)
        L_0x00af:
            r12 = 9
            r11.connection_state = r12
            org.bouncycastle.crypto.tls.CertificateRequest r12 = r11.certificateRequest
            if (r12 != 0) goto L_0x00be
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.skipClientCredentials()
            r12 = r9
            goto L_0x00dd
        L_0x00be:
            org.bouncycastle.crypto.tls.TlsAuthentication r12 = r11.authentication
            org.bouncycastle.crypto.tls.CertificateRequest r13 = r11.certificateRequest
            org.bouncycastle.crypto.tls.TlsCredentials r12 = r12.getClientCredentials(r13)
            if (r12 != 0) goto L_0x00d3
            org.bouncycastle.crypto.tls.TlsKeyExchange r13 = r11.keyExchange
            r13.skipClientCredentials()
            org.bouncycastle.crypto.tls.Certificate r13 = org.bouncycastle.crypto.tls.Certificate.EMPTY_CHAIN
        L_0x00cf:
            r11.sendCertificateMessage(r13)
            goto L_0x00dd
        L_0x00d3:
            org.bouncycastle.crypto.tls.TlsKeyExchange r13 = r11.keyExchange
            r13.processClientCredentials(r12)
            org.bouncycastle.crypto.tls.Certificate r13 = r12.getCertificate()
            goto L_0x00cf
        L_0x00dd:
            r11.connection_state = r6
            r11.sendClientKeyExchangeMessage()
            r13 = 11
            r11.connection_state = r13
            org.bouncycastle.crypto.tls.AbstractTlsContext r13 = r11.getContext()
            org.bouncycastle.crypto.tls.TlsKeyExchange r0 = r11.keyExchange
            establishMasterSecret(r13, r0)
            org.bouncycastle.crypto.tls.RecordStream r13 = r11.recordStream
            org.bouncycastle.crypto.tls.TlsPeer r0 = r11.getPeer()
            org.bouncycastle.crypto.tls.TlsCompression r0 = r0.getCompression()
            org.bouncycastle.crypto.tls.TlsPeer r1 = r11.getPeer()
            org.bouncycastle.crypto.tls.TlsCipher r1 = r1.getCipher()
            r13.a(r0, r1)
            org.bouncycastle.crypto.tls.RecordStream r13 = r11.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r13 = r13.i()
            if (r12 == 0) goto L_0x0149
            boolean r0 = r12 instanceof org.bouncycastle.crypto.tls.TlsSignerCredentials
            if (r0 == 0) goto L_0x0149
            org.bouncycastle.crypto.tls.TlsSignerCredentials r12 = (org.bouncycastle.crypto.tls.TlsSignerCredentials) r12
            org.bouncycastle.crypto.tls.AbstractTlsContext r0 = r11.getContext()
            boolean r0 = org.bouncycastle.crypto.tls.TlsUtils.isTLSv12(r0)
            if (r0 == 0) goto L_0x0133
            org.bouncycastle.crypto.tls.SignatureAndHashAlgorithm r9 = r12.getSignatureAndHashAlgorithm()
            if (r9 != 0) goto L_0x012a
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r13 = 80
            r12.<init>(r13)
            throw r12
        L_0x012a:
            short r0 = r9.getHash()
            byte[] r13 = r13.getFinalHash(r0)
            goto L_0x013b
        L_0x0133:
            org.bouncycastle.crypto.tls.AbstractTlsContext r0 = r11.getContext()
            byte[] r13 = getCurrentPRFHash(r0, r13, r9)
        L_0x013b:
            byte[] r12 = r12.generateCertificateSignature(r13)
            org.bouncycastle.crypto.tls.DigitallySigned r13 = new org.bouncycastle.crypto.tls.DigitallySigned
            r13.<init>(r9, r12)
            r11.sendCertificateVerifyMessage(r13)
            r11.connection_state = r7
        L_0x0149:
            r11.sendChangeCipherSpecMessage()
            r11.sendFinishedMessage()
            r11.connection_state = r4
            return
        L_0x0152:
            short r12 = r11.connection_state
            switch(r12) {
                case 4: goto L_0x015d;
                case 5: goto L_0x015d;
                case 6: goto L_0x0162;
                default: goto L_0x0157;
            }
        L_0x0157:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x015d:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.skipServerKeyExchange()
        L_0x0162:
            org.bouncycastle.crypto.tls.TlsAuthentication r12 = r11.authentication
            if (r12 != 0) goto L_0x016c
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r13)
            throw r12
        L_0x016c:
            org.bouncycastle.crypto.tls.AbstractTlsContext r12 = r11.getContext()
            org.bouncycastle.crypto.tls.CertificateRequest r12 = org.bouncycastle.crypto.tls.CertificateRequest.parse(r12, r0)
            r11.certificateRequest = r12
            assertEmpty(r0)
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            org.bouncycastle.crypto.tls.CertificateRequest r13 = r11.certificateRequest
            r12.validateCertificateRequest(r13)
            org.bouncycastle.crypto.tls.RecordStream r12 = r11.recordStream
            org.bouncycastle.crypto.tls.TlsHandshakeHash r12 = r12.h()
            org.bouncycastle.crypto.tls.CertificateRequest r13 = r11.certificateRequest
            java.util.Vector r13 = r13.getSupportedSignatureAlgorithms()
            org.bouncycastle.crypto.tls.TlsUtils.a(r12, r13)
            r12 = 7
            goto L_0x01af
        L_0x0191:
            short r12 = r11.connection_state
            switch(r12) {
                case 2: goto L_0x019c;
                case 3: goto L_0x019f;
                case 4: goto L_0x01a6;
                case 5: goto L_0x01a6;
                default: goto L_0x0196;
            }
        L_0x0196:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x019c:
            r11.handleSupplementalData(r9)
        L_0x019f:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.skipServerCredentials()
            r11.authentication = r9
        L_0x01a6:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            r12.processServerKeyExchange(r0)
            assertEmpty(r0)
            r12 = 6
        L_0x01af:
            r11.connection_state = r12
            return
        L_0x01b2:
            short r12 = r11.connection_state
            switch(r12) {
                case 2: goto L_0x01bd;
                case 3: goto L_0x01c0;
                default: goto L_0x01b7;
            }
        L_0x01b7:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x01bd:
            r11.handleSupplementalData(r9)
        L_0x01c0:
            org.bouncycastle.crypto.tls.Certificate r12 = org.bouncycastle.crypto.tls.Certificate.parse(r0)
            r11.peerCertificate = r12
            assertEmpty(r0)
            org.bouncycastle.crypto.tls.Certificate r12 = r11.peerCertificate
            if (r12 == 0) goto L_0x01d5
            org.bouncycastle.crypto.tls.Certificate r12 = r11.peerCertificate
            boolean r12 = r12.isEmpty()
            if (r12 == 0) goto L_0x01d8
        L_0x01d5:
            r12 = 0
            r11.allowCertificateStatus = r12
        L_0x01d8:
            org.bouncycastle.crypto.tls.TlsKeyExchange r12 = r11.keyExchange
            org.bouncycastle.crypto.tls.Certificate r13 = r11.peerCertificate
            r12.processServerCertificate(r13)
            org.bouncycastle.crypto.tls.TlsClient r12 = r11.tlsClient
            org.bouncycastle.crypto.tls.TlsAuthentication r12 = r12.getAuthentication()
            r11.authentication = r12
            org.bouncycastle.crypto.tls.TlsAuthentication r12 = r11.authentication
            org.bouncycastle.crypto.tls.Certificate r13 = r11.peerCertificate
            r12.notifyServerCertificate(r13)
            r11.connection_state = r10
            return
        L_0x01f1:
            short r12 = r11.connection_state
            switch(r12) {
                case 13: goto L_0x01fc;
                case 14: goto L_0x0206;
                default: goto L_0x01f6;
            }
        L_0x01f6:
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x01fc:
            boolean r12 = r11.expectSessionTicket
            if (r12 == 0) goto L_0x0206
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0206:
            r11.processFinishedMessage(r0)
            r11.connection_state = r1
            r11.connection_state = r3
            return
        L_0x020e:
            short r12 = r11.connection_state
            if (r12 == r4) goto L_0x0218
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0218:
            boolean r12 = r11.expectSessionTicket
            if (r12 != 0) goto L_0x0222
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0222:
            r11.invalidateSession()
            r11.receiveNewSessionTicketMessage(r0)
            r12 = 14
            r11.connection_state = r12
            goto L_0x02a8
        L_0x022e:
            short r12 = r11.connection_state
            r13 = 1
            if (r12 == r13) goto L_0x0239
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r6)
            throw r12
        L_0x0239:
            r11.receiveServerHelloMessage(r0)
            r11.connection_state = r5
            org.bouncycastle.crypto.tls.SecurityParameters r12 = r11.securityParameters
            short r12 = r12.i
            if (r12 < 0) goto L_0x0250
            org.bouncycastle.crypto.tls.SecurityParameters r12 = r11.securityParameters
            short r12 = r12.i
            int r12 = r12 + r8
            int r12 = r13 << r12
            org.bouncycastle.crypto.tls.RecordStream r13 = r11.recordStream
            r13.a(r12)
        L_0x0250:
            org.bouncycastle.crypto.tls.SecurityParameters r12 = r11.securityParameters
            org.bouncycastle.crypto.tls.AbstractTlsContext r13 = r11.getContext()
            org.bouncycastle.crypto.tls.SecurityParameters r0 = r11.securityParameters
            int r0 = r0.getCipherSuite()
            int r13 = getPRFAlgorithm(r13, r0)
            r12.d = r13
            org.bouncycastle.crypto.tls.SecurityParameters r12 = r11.securityParameters
            r12.e = r7
            org.bouncycastle.crypto.tls.RecordStream r12 = r11.recordStream
            r12.g()
            boolean r12 = r11.resumedSession
            if (r12 == 0) goto L_0x0296
            org.bouncycastle.crypto.tls.SecurityParameters r12 = r11.securityParameters
            org.bouncycastle.crypto.tls.SessionParameters r13 = r11.sessionParameters
            byte[] r13 = r13.getMasterSecret()
            byte[] r13 = org.bouncycastle.util.Arrays.clone(r13)
            r12.f = r13
            org.bouncycastle.crypto.tls.RecordStream r12 = r11.recordStream
            org.bouncycastle.crypto.tls.TlsPeer r13 = r11.getPeer()
            org.bouncycastle.crypto.tls.TlsCompression r13 = r13.getCompression()
            org.bouncycastle.crypto.tls.TlsPeer r0 = r11.getPeer()
            org.bouncycastle.crypto.tls.TlsCipher r0 = r0.getCipher()
            r12.a(r13, r0)
            r11.sendChangeCipherSpecMessage()
            return
        L_0x0296:
            r11.invalidateSession()
            byte[] r12 = r11.selectedSessionID
            int r12 = r12.length
            if (r12 <= 0) goto L_0x02c6
            org.bouncycastle.crypto.tls.TlsSessionImpl r12 = new org.bouncycastle.crypto.tls.TlsSessionImpl
            byte[] r13 = r11.selectedSessionID
            r12.<init>(r13, r9)
            r11.tlsSession = r12
            return
        L_0x02a8:
            assertEmpty(r0)
            short r12 = r11.connection_state
            if (r12 != r3) goto L_0x02c6
            org.bouncycastle.crypto.tls.AbstractTlsContext r12 = r11.getContext()
            boolean r12 = org.bouncycastle.crypto.tls.TlsUtils.isSSL(r12)
            if (r12 == 0) goto L_0x02bf
            org.bouncycastle.crypto.tls.TlsFatalAlert r12 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r12.<init>(r13)
            throw r12
        L_0x02bf:
            java.lang.String r12 = "Renegotiation not supported"
            r13 = 100
            r11.raiseWarning(r13, r12)
        L_0x02c6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsClientProtocol.handleHandshakeMessage(short, byte[]):void");
    }

    /* access modifiers changed from: protected */
    public void handleSupplementalData(Vector vector) {
        this.tlsClient.processServerSupplementalData(vector);
        this.connection_state = 3;
        this.keyExchange = this.tlsClient.getKeyExchange();
        this.keyExchange.init(getContext());
    }

    /* access modifiers changed from: protected */
    public void receiveNewSessionTicketMessage(ByteArrayInputStream byteArrayInputStream) {
        NewSessionTicket parse = NewSessionTicket.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        this.tlsClient.notifyNewSessionTicket(parse);
    }

    /* access modifiers changed from: protected */
    public void receiveServerHelloMessage(ByteArrayInputStream byteArrayInputStream) {
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        if (readVersion.isDTLS()) {
            throw new TlsFatalAlert(47);
        } else if (!readVersion.equals(this.recordStream.b())) {
            throw new TlsFatalAlert(47);
        } else if (!readVersion.isEqualOrEarlierVersionOf(getContext().getClientVersion())) {
            throw new TlsFatalAlert(47);
        } else {
            this.recordStream.b(readVersion);
            getContext().b(readVersion);
            this.tlsClient.notifyServerVersion(readVersion);
            this.securityParameters.h = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
            this.selectedSessionID = TlsUtils.readOpaque8(byteArrayInputStream);
            if (this.selectedSessionID.length > 32) {
                throw new TlsFatalAlert(47);
            }
            this.tlsClient.notifySessionID(this.selectedSessionID);
            boolean z = false;
            this.resumedSession = this.selectedSessionID.length > 0 && this.tlsSession != null && Arrays.areEqual(this.selectedSessionID, this.tlsSession.getSessionID());
            int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
            if (!Arrays.contains(this.offeredCipherSuites, readUint16) || readUint16 == 0 || readUint16 == 255 || !TlsUtils.isValidCipherSuiteForVersion(readUint16, readVersion)) {
                throw new TlsFatalAlert(47);
            }
            this.tlsClient.notifySelectedCipherSuite(readUint16);
            short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
            if (!Arrays.contains(this.offeredCompressionMethods, readUint8)) {
                throw new TlsFatalAlert(47);
            }
            this.tlsClient.notifySelectedCompressionMethod(readUint8);
            this.serverExtensions = readExtensions(byteArrayInputStream);
            if (this.serverExtensions != null) {
                Enumeration keys = this.serverExtensions.keys();
                while (keys.hasMoreElements()) {
                    Integer num = (Integer) keys.nextElement();
                    if (!num.equals(EXT_RenegotiationInfo)) {
                        boolean z2 = this.resumedSession;
                        if (TlsUtils.getExtensionData(this.clientExtensions, num) == null) {
                            throw new TlsFatalAlert(AlertDescription.unsupported_extension);
                        }
                    }
                }
            }
            byte[] extensionData = TlsUtils.getExtensionData(this.serverExtensions, EXT_RenegotiationInfo);
            if (extensionData != null) {
                this.secure_renegotiation = true;
                if (!Arrays.constantTimeAreEqual(extensionData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                    throw new TlsFatalAlert(40);
                }
            }
            this.tlsClient.notifySecureRenegotiation(this.secure_renegotiation);
            Hashtable hashtable = this.clientExtensions;
            Hashtable hashtable2 = this.serverExtensions;
            if (this.resumedSession) {
                if (readUint16 == this.sessionParameters.getCipherSuite() && readUint8 == this.sessionParameters.getCompressionAlgorithm()) {
                    hashtable = null;
                    hashtable2 = this.sessionParameters.readServerExtensions();
                } else {
                    throw new TlsFatalAlert(47);
                }
            }
            this.securityParameters.b = readUint16;
            this.securityParameters.c = readUint8;
            if (hashtable2 != null) {
                boolean hasEncryptThenMACExtension = TlsExtensionsUtils.hasEncryptThenMACExtension(hashtable2);
                if (!hasEncryptThenMACExtension || TlsUtils.isBlockCipherSuite(readUint16)) {
                    this.securityParameters.k = hasEncryptThenMACExtension;
                    this.securityParameters.i = processMaxFragmentLengthExtension(hashtable, hashtable2, 47);
                    this.securityParameters.j = TlsExtensionsUtils.hasTruncatedHMacExtension(hashtable2);
                    this.allowCertificateStatus = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsExtensionsUtils.EXT_status_request, 47);
                    if (!this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(hashtable2, TlsProtocol.EXT_SessionTicket, 47)) {
                        z = true;
                    }
                    this.expectSessionTicket = z;
                } else {
                    throw new TlsFatalAlert(47);
                }
            }
            if (hashtable != null) {
                this.tlsClient.processServerExtensions(hashtable2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sendCertificateVerifyMessage(DigitallySigned digitallySigned) {
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 15);
        digitallySigned.encode(handshakeMessage);
        handshakeMessage.a();
    }

    /* access modifiers changed from: protected */
    public void sendClientHelloMessage() {
        this.recordStream.b(this.tlsClient.getClientHelloRecordLayerVersion());
        ProtocolVersion clientVersion = this.tlsClient.getClientVersion();
        if (clientVersion.isDTLS()) {
            throw new TlsFatalAlert(80);
        }
        getContext().a(clientVersion);
        byte[] bArr = TlsUtils.EMPTY_BYTES;
        if (this.tlsSession != null) {
            bArr = this.tlsSession.getSessionID();
            if (bArr == null || bArr.length > 32) {
                bArr = TlsUtils.EMPTY_BYTES;
            }
        }
        this.offeredCipherSuites = this.tlsClient.getCipherSuites();
        this.offeredCompressionMethods = this.tlsClient.getCompressionMethods();
        if (bArr.length > 0 && this.sessionParameters != null && (!Arrays.contains(this.offeredCipherSuites, this.sessionParameters.getCipherSuite()) || !Arrays.contains(this.offeredCompressionMethods, this.sessionParameters.getCompressionAlgorithm()))) {
            bArr = TlsUtils.EMPTY_BYTES;
        }
        this.clientExtensions = this.tlsClient.getClientExtensions();
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 1);
        TlsUtils.writeVersion(clientVersion, handshakeMessage);
        handshakeMessage.write(this.securityParameters.getClientRandom());
        TlsUtils.writeOpaque8(bArr, handshakeMessage);
        boolean z = !Arrays.contains(this.offeredCipherSuites, 255);
        if ((TlsUtils.getExtensionData(this.clientExtensions, EXT_RenegotiationInfo) == null) && z) {
            this.offeredCipherSuites = Arrays.append(this.offeredCipherSuites, 255);
        }
        TlsUtils.writeUint16ArrayWithUint16Length(this.offeredCipherSuites, handshakeMessage);
        TlsUtils.writeUint8ArrayWithUint8Length(this.offeredCompressionMethods, handshakeMessage);
        if (this.clientExtensions != null) {
            writeExtensions(handshakeMessage, this.clientExtensions);
        }
        handshakeMessage.a();
    }

    /* access modifiers changed from: protected */
    public void sendClientKeyExchangeMessage() {
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 16);
        this.keyExchange.generateClientKeyExchange(handshakeMessage);
        handshakeMessage.a();
    }
}
