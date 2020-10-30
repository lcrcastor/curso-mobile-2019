package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Vector;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.Arrays;

public class TlsServerProtocol extends TlsProtocol {
    protected CertificateRequest certificateRequest = null;
    protected short clientCertificateType = -1;
    protected TlsKeyExchange keyExchange = null;
    protected TlsHandshakeHash prepareFinishHash = null;
    protected TlsCredentials serverCredentials = null;
    protected TlsServer tlsServer = null;
    protected TlsServerContextImpl tlsServerContext = null;

    public TlsServerProtocol(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        super(inputStream, outputStream, secureRandom);
    }

    public void accept(TlsServer tlsServer2) {
        if (tlsServer2 == null) {
            throw new IllegalArgumentException("'tlsServer' cannot be null");
        } else if (this.tlsServer != null) {
            throw new IllegalStateException("'accept' can only be called once");
        } else {
            this.tlsServer = tlsServer2;
            this.securityParameters = new SecurityParameters();
            this.securityParameters.a = 0;
            this.tlsServerContext = new TlsServerContextImpl(this.secureRandom, this.securityParameters);
            this.securityParameters.h = createRandomBlock(tlsServer2.shouldUseGMTUnixTime(), this.tlsServerContext.getNonceRandomGenerator());
            this.tlsServer.init(this.tlsServerContext);
            this.recordStream.a((TlsContext) this.tlsServerContext);
            this.recordStream.a(false);
            completeHandshake();
        }
    }

    /* access modifiers changed from: protected */
    public void cleanupHandshake() {
        super.cleanupHandshake();
        this.keyExchange = null;
        this.serverCredentials = null;
        this.certificateRequest = null;
        this.prepareFinishHash = null;
    }

    /* access modifiers changed from: protected */
    public boolean expectCertificateVerifyMessage() {
        return this.clientCertificateType >= 0 && TlsUtils.hasSigningCapability(this.clientCertificateType);
    }

    /* access modifiers changed from: protected */
    public AbstractTlsContext getContext() {
        return this.tlsServerContext;
    }

    /* access modifiers changed from: protected */
    public TlsPeer getPeer() {
        return this.tlsServer;
    }

    /* access modifiers changed from: protected */
    public void handleHandshakeMessage(short s, byte[] bArr) {
        short s2;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Certificate certificate = null;
        if (s != 1) {
            if (s != 11) {
                if (s == 20) {
                    switch (this.connection_state) {
                        case 11:
                            if (expectCertificateVerifyMessage()) {
                                throw new TlsFatalAlert(10);
                            }
                            break;
                        case 12:
                            break;
                        default:
                            throw new TlsFatalAlert(10);
                    }
                    processFinishedMessage(byteArrayInputStream);
                    this.connection_state = 13;
                    if (this.expectSessionTicket) {
                        sendNewSessionTicketMessage(this.tlsServer.getNewSessionTicket());
                        sendChangeCipherSpecMessage();
                    }
                    this.connection_state = 14;
                    sendFinishedMessage();
                    this.connection_state = 15;
                    s2 = 16;
                } else if (s != 23) {
                    switch (s) {
                        case 15:
                            if (this.connection_state == 11) {
                                if (expectCertificateVerifyMessage()) {
                                    receiveCertificateVerifyMessage(byteArrayInputStream);
                                    s2 = 12;
                                    break;
                                } else {
                                    throw new TlsFatalAlert(10);
                                }
                            } else {
                                throw new TlsFatalAlert(10);
                            }
                        case 16:
                            switch (this.connection_state) {
                                case 8:
                                    this.tlsServer.processClientSupplementalData(null);
                                    break;
                                case 9:
                                    break;
                                case 10:
                                    break;
                                default:
                                    throw new TlsFatalAlert(10);
                            }
                            if (this.certificateRequest == null) {
                                this.keyExchange.skipClientCredentials();
                            } else if (TlsUtils.isTLSv12(getContext())) {
                                throw new TlsFatalAlert(10);
                            } else if (!TlsUtils.isSSL(getContext())) {
                                notifyClientCertificate(Certificate.EMPTY_CHAIN);
                            } else if (this.peerCertificate == null) {
                                throw new TlsFatalAlert(10);
                            }
                            receiveClientKeyExchangeMessage(byteArrayInputStream);
                            this.connection_state = 11;
                            return;
                        default:
                            throw new TlsFatalAlert(10);
                    }
                } else if (this.connection_state != 8) {
                    throw new TlsFatalAlert(10);
                } else {
                    this.tlsServer.processClientSupplementalData(readSupplementalDataMessage(byteArrayInputStream));
                    s2 = 9;
                }
                this.connection_state = s2;
                return;
            }
            switch (this.connection_state) {
                case 8:
                    this.tlsServer.processClientSupplementalData(null);
                    break;
                case 9:
                    break;
                default:
                    throw new TlsFatalAlert(10);
            }
            if (this.certificateRequest == null) {
                throw new TlsFatalAlert(10);
            }
            receiveCertificateMessage(byteArrayInputStream);
            this.connection_state = 10;
        } else if (this.connection_state != 0) {
            throw new TlsFatalAlert(10);
        } else {
            receiveClientHelloMessage(byteArrayInputStream);
            this.connection_state = 1;
            sendServerHelloMessage();
            this.connection_state = 2;
            Vector serverSupplementalData = this.tlsServer.getServerSupplementalData();
            if (serverSupplementalData != null) {
                sendSupplementalDataMessage(serverSupplementalData);
            }
            this.connection_state = 3;
            this.keyExchange = this.tlsServer.getKeyExchange();
            this.keyExchange.init(getContext());
            this.serverCredentials = this.tlsServer.getCredentials();
            if (this.serverCredentials == null) {
                this.keyExchange.skipServerCredentials();
            } else {
                this.keyExchange.processServerCredentials(this.serverCredentials);
                certificate = this.serverCredentials.getCertificate();
                sendCertificateMessage(certificate);
            }
            this.connection_state = 4;
            if (certificate == null || certificate.isEmpty()) {
                this.allowCertificateStatus = false;
            }
            if (this.allowCertificateStatus) {
                CertificateStatus certificateStatus = this.tlsServer.getCertificateStatus();
                if (certificateStatus != null) {
                    sendCertificateStatusMessage(certificateStatus);
                }
            }
            this.connection_state = 5;
            byte[] generateServerKeyExchange = this.keyExchange.generateServerKeyExchange();
            if (generateServerKeyExchange != null) {
                sendServerKeyExchangeMessage(generateServerKeyExchange);
            }
            this.connection_state = 6;
            if (this.serverCredentials != null) {
                this.certificateRequest = this.tlsServer.getCertificateRequest();
                if (this.certificateRequest != null) {
                    this.keyExchange.validateCertificateRequest(this.certificateRequest);
                    sendCertificateRequestMessage(this.certificateRequest);
                    TlsUtils.a(this.recordStream.h(), this.certificateRequest.getSupportedSignatureAlgorithms());
                }
            }
            this.connection_state = 7;
            sendServerHelloDoneMessage();
            this.connection_state = 8;
            this.recordStream.h().sealHashAlgorithms();
        }
    }

    /* access modifiers changed from: protected */
    public void handleWarningMessage(short s) {
        if (s != 41) {
            super.handleWarningMessage(s);
            return;
        }
        if (TlsUtils.isSSL(getContext()) && this.certificateRequest != null) {
            notifyClientCertificate(Certificate.EMPTY_CHAIN);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyClientCertificate(Certificate certificate) {
        if (this.certificateRequest == null) {
            throw new IllegalStateException();
        } else if (this.peerCertificate != null) {
            throw new TlsFatalAlert(10);
        } else {
            this.peerCertificate = certificate;
            if (certificate.isEmpty()) {
                this.keyExchange.skipClientCredentials();
            } else {
                this.clientCertificateType = TlsUtils.a(certificate, this.serverCredentials.getCertificate());
                this.keyExchange.processClientCertificate(certificate);
            }
            this.tlsServer.notifyClientCertificate(certificate);
        }
    }

    /* access modifiers changed from: protected */
    public void receiveCertificateMessage(ByteArrayInputStream byteArrayInputStream) {
        Certificate parse = Certificate.parse(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        notifyClientCertificate(parse);
    }

    /* access modifiers changed from: protected */
    public void receiveCertificateVerifyMessage(ByteArrayInputStream byteArrayInputStream) {
        DigitallySigned parse = DigitallySigned.parse(getContext(), byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        boolean z = false;
        try {
            byte[] finalHash = TlsUtils.isTLSv12(getContext()) ? this.prepareFinishHash.getFinalHash(parse.getAlgorithm().getHash()) : TlsProtocol.getCurrentPRFHash(getContext(), this.prepareFinishHash, null);
            AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(this.peerCertificate.getCertificateAt(0).getSubjectPublicKeyInfo());
            TlsSigner createTlsSigner = TlsUtils.createTlsSigner(this.clientCertificateType);
            createTlsSigner.init(getContext());
            z = createTlsSigner.verifyRawSignature(parse.getAlgorithm(), parse.getSignature(), createKey, finalHash);
        } catch (Exception unused) {
        }
        if (!z) {
            throw new TlsFatalAlert(51);
        }
    }

    /* access modifiers changed from: protected */
    public void receiveClientHelloMessage(ByteArrayInputStream byteArrayInputStream) {
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        if (readVersion.isDTLS()) {
            throw new TlsFatalAlert(47);
        }
        byte[] readFully = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
        if (TlsUtils.readOpaque8(byteArrayInputStream).length > 32) {
            throw new TlsFatalAlert(47);
        }
        int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
        if (readUint16 < 2 || (readUint16 & 1) != 0) {
            throw new TlsFatalAlert(50);
        }
        this.offeredCipherSuites = TlsUtils.readUint16Array(readUint16 / 2, byteArrayInputStream);
        short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
        if (readUint8 < 1) {
            throw new TlsFatalAlert(47);
        }
        this.offeredCompressionMethods = TlsUtils.readUint8Array(readUint8, byteArrayInputStream);
        this.clientExtensions = readExtensions(byteArrayInputStream);
        getContext().a(readVersion);
        this.tlsServer.notifyClientVersion(readVersion);
        this.securityParameters.g = readFully;
        this.tlsServer.notifyOfferedCipherSuites(this.offeredCipherSuites);
        this.tlsServer.notifyOfferedCompressionMethods(this.offeredCompressionMethods);
        if (Arrays.contains(this.offeredCipherSuites, 255)) {
            this.secure_renegotiation = true;
        }
        byte[] extensionData = TlsUtils.getExtensionData(this.clientExtensions, EXT_RenegotiationInfo);
        if (extensionData != null) {
            this.secure_renegotiation = true;
            if (!Arrays.constantTimeAreEqual(extensionData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                throw new TlsFatalAlert(40);
            }
        }
        this.tlsServer.notifySecureRenegotiation(this.secure_renegotiation);
        if (this.clientExtensions != null) {
            this.tlsServer.processClientExtensions(this.clientExtensions);
        }
    }

    /* access modifiers changed from: protected */
    public void receiveClientKeyExchangeMessage(ByteArrayInputStream byteArrayInputStream) {
        this.keyExchange.processClientKeyExchange(byteArrayInputStream);
        assertEmpty(byteArrayInputStream);
        establishMasterSecret(getContext(), this.keyExchange);
        this.recordStream.a(getPeer().getCompression(), getPeer().getCipher());
        this.prepareFinishHash = this.recordStream.i();
        if (!this.expectSessionTicket) {
            sendChangeCipherSpecMessage();
        }
    }

    /* access modifiers changed from: protected */
    public void sendCertificateRequestMessage(CertificateRequest certificateRequest2) {
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 13);
        certificateRequest2.encode(handshakeMessage);
        handshakeMessage.a();
    }

    /* access modifiers changed from: protected */
    public void sendCertificateStatusMessage(CertificateStatus certificateStatus) {
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 22);
        certificateStatus.encode(handshakeMessage);
        handshakeMessage.a();
    }

    /* access modifiers changed from: protected */
    public void sendNewSessionTicketMessage(NewSessionTicket newSessionTicket) {
        if (newSessionTicket == null) {
            throw new TlsFatalAlert(80);
        }
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 4);
        newSessionTicket.encode(handshakeMessage);
        handshakeMessage.a();
    }

    /* access modifiers changed from: protected */
    public void sendServerHelloDoneMessage() {
        byte[] bArr = new byte[4];
        TlsUtils.writeUint8(14, bArr, 0);
        TlsUtils.writeUint24(0, bArr, 1);
        writeHandshakeMessage(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: protected */
    public void sendServerHelloMessage() {
        HandshakeMessage handshakeMessage = new HandshakeMessage(this, 2);
        ProtocolVersion serverVersion = this.tlsServer.getServerVersion();
        if (!serverVersion.isEqualOrEarlierVersionOf(getContext().getClientVersion())) {
            throw new TlsFatalAlert(80);
        }
        this.recordStream.a(serverVersion);
        this.recordStream.b(serverVersion);
        this.recordStream.a(true);
        getContext().b(serverVersion);
        TlsUtils.writeVersion(serverVersion, handshakeMessage);
        handshakeMessage.write(this.securityParameters.h);
        TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, handshakeMessage);
        int selectedCipherSuite = this.tlsServer.getSelectedCipherSuite();
        if (!Arrays.contains(this.offeredCipherSuites, selectedCipherSuite) || selectedCipherSuite == 0 || selectedCipherSuite == 255 || !TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, serverVersion)) {
            throw new TlsFatalAlert(80);
        }
        this.securityParameters.b = selectedCipherSuite;
        short selectedCompressionMethod = this.tlsServer.getSelectedCompressionMethod();
        if (!Arrays.contains(this.offeredCompressionMethods, selectedCompressionMethod)) {
            throw new TlsFatalAlert(80);
        }
        this.securityParameters.c = selectedCompressionMethod;
        TlsUtils.writeUint16(selectedCipherSuite, handshakeMessage);
        TlsUtils.writeUint8(selectedCompressionMethod, (OutputStream) handshakeMessage);
        this.serverExtensions = this.tlsServer.getServerExtensions();
        boolean z = false;
        if (this.secure_renegotiation) {
            if (TlsUtils.getExtensionData(this.serverExtensions, EXT_RenegotiationInfo) == null) {
                this.serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(this.serverExtensions);
                this.serverExtensions.put(EXT_RenegotiationInfo, createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
            }
        }
        if (this.serverExtensions != null) {
            this.securityParameters.k = TlsExtensionsUtils.hasEncryptThenMACExtension(this.serverExtensions);
            this.securityParameters.i = processMaxFragmentLengthExtension(this.clientExtensions, this.serverExtensions, 80);
            this.securityParameters.j = TlsExtensionsUtils.hasTruncatedHMacExtension(this.serverExtensions);
            this.allowCertificateStatus = !this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsExtensionsUtils.EXT_status_request, 80);
            if (!this.resumedSession && TlsUtils.hasExpectedEmptyExtensionData(this.serverExtensions, TlsProtocol.EXT_SessionTicket, 80)) {
                z = true;
            }
            this.expectSessionTicket = z;
            writeExtensions(handshakeMessage, this.serverExtensions);
        }
        if (this.securityParameters.i >= 0) {
            this.recordStream.a(1 << (this.securityParameters.i + 8));
        }
        this.securityParameters.d = getPRFAlgorithm(getContext(), this.securityParameters.getCipherSuite());
        this.securityParameters.e = 12;
        handshakeMessage.a();
        this.recordStream.g();
    }

    /* access modifiers changed from: protected */
    public void sendServerKeyExchangeMessage(byte[] bArr) {
        HandshakeMessage handshakeMessage = new HandshakeMessage(12, bArr.length);
        handshakeMessage.write(bArr);
        handshakeMessage.a();
    }
}
