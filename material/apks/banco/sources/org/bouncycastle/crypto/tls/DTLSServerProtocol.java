package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.Arrays;

public class DTLSServerProtocol extends DTLSProtocol {
    protected boolean verifyRequests = true;

    public static class ServerHandshakeState {
        TlsServer a = null;
        TlsServerContextImpl b = null;
        int[] c;
        short[] d;
        Hashtable e;
        int f = -1;
        short g = -1;
        boolean h = false;
        short i = -1;
        boolean j = false;
        boolean k = false;
        Hashtable l = null;
        TlsKeyExchange m = null;
        TlsCredentials n = null;
        CertificateRequest o = null;
        short p = -1;
        Certificate q = null;

        protected ServerHandshakeState() {
        }
    }

    public DTLSServerProtocol(SecureRandom secureRandom) {
        super(secureRandom);
    }

    public DTLSTransport accept(TlsServer tlsServer, DatagramTransport datagramTransport) {
        if (tlsServer == null) {
            throw new IllegalArgumentException("'server' cannot be null");
        } else if (datagramTransport == null) {
            throw new IllegalArgumentException("'transport' cannot be null");
        } else {
            SecurityParameters securityParameters = new SecurityParameters();
            securityParameters.a = 0;
            ServerHandshakeState serverHandshakeState = new ServerHandshakeState();
            serverHandshakeState.a = tlsServer;
            serverHandshakeState.b = new TlsServerContextImpl(this.secureRandom, securityParameters);
            securityParameters.h = TlsProtocol.createRandomBlock(tlsServer.shouldUseGMTUnixTime(), serverHandshakeState.b.getNonceRandomGenerator());
            tlsServer.init(serverHandshakeState.b);
            DTLSRecordLayer dTLSRecordLayer = new DTLSRecordLayer(datagramTransport, serverHandshakeState.b, tlsServer, 22);
            try {
                return serverHandshake(serverHandshakeState, dTLSRecordLayer);
            } catch (TlsFatalAlert e) {
                dTLSRecordLayer.a(e.getAlertDescription());
                throw e;
            } catch (IOException e2) {
                dTLSRecordLayer.a(80);
                throw e2;
            } catch (RuntimeException unused) {
                dTLSRecordLayer.a(80);
                throw new TlsFatalAlert(80);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean expectCertificateVerifyMessage(ServerHandshakeState serverHandshakeState) {
        return serverHandshakeState.p >= 0 && TlsUtils.hasSigningCapability(serverHandshakeState.p);
    }

    /* access modifiers changed from: protected */
    public byte[] generateCertificateRequest(ServerHandshakeState serverHandshakeState, CertificateRequest certificateRequest) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificateRequest.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] generateCertificateStatus(ServerHandshakeState serverHandshakeState, CertificateStatus certificateStatus) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificateStatus.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] generateNewSessionTicket(ServerHandshakeState serverHandshakeState, NewSessionTicket newSessionTicket) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        newSessionTicket.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] generateServerHello(ServerHandshakeState serverHandshakeState) {
        SecurityParameters securityParameters = serverHandshakeState.b.getSecurityParameters();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ProtocolVersion serverVersion = serverHandshakeState.a.getServerVersion();
        if (!serverVersion.isEqualOrEarlierVersionOf(serverHandshakeState.b.getClientVersion())) {
            throw new TlsFatalAlert(80);
        }
        serverHandshakeState.b.b(serverVersion);
        TlsUtils.writeVersion(serverHandshakeState.b.getServerVersion(), byteArrayOutputStream);
        byteArrayOutputStream.write(securityParameters.getServerRandom());
        TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, byteArrayOutputStream);
        serverHandshakeState.f = serverHandshakeState.a.getSelectedCipherSuite();
        if (!Arrays.contains(serverHandshakeState.c, serverHandshakeState.f) || serverHandshakeState.f == 0 || serverHandshakeState.f == 255 || !TlsUtils.isValidCipherSuiteForVersion(serverHandshakeState.f, serverVersion)) {
            throw new TlsFatalAlert(80);
        }
        validateSelectedCipherSuite(serverHandshakeState.f, 80);
        serverHandshakeState.g = serverHandshakeState.a.getSelectedCompressionMethod();
        if (!Arrays.contains(serverHandshakeState.d, serverHandshakeState.g)) {
            throw new TlsFatalAlert(80);
        }
        TlsUtils.writeUint16(serverHandshakeState.f, byteArrayOutputStream);
        TlsUtils.writeUint8(serverHandshakeState.g, (OutputStream) byteArrayOutputStream);
        serverHandshakeState.l = serverHandshakeState.a.getServerExtensions();
        if (serverHandshakeState.h) {
            if (TlsUtils.getExtensionData(serverHandshakeState.l, TlsProtocol.EXT_RenegotiationInfo) == null) {
                serverHandshakeState.l = TlsExtensionsUtils.ensureExtensionsInitialised(serverHandshakeState.l);
                serverHandshakeState.l.put(TlsProtocol.EXT_RenegotiationInfo, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
            }
        }
        if (serverHandshakeState.l != null) {
            securityParameters.k = TlsExtensionsUtils.hasEncryptThenMACExtension(serverHandshakeState.l);
            serverHandshakeState.i = evaluateMaxFragmentLengthExtension(serverHandshakeState.e, serverHandshakeState.l, 80);
            securityParameters.j = TlsExtensionsUtils.hasTruncatedHMacExtension(serverHandshakeState.l);
            serverHandshakeState.j = TlsUtils.hasExpectedEmptyExtensionData(serverHandshakeState.l, TlsExtensionsUtils.EXT_status_request, 80);
            serverHandshakeState.k = TlsUtils.hasExpectedEmptyExtensionData(serverHandshakeState.l, TlsProtocol.EXT_SessionTicket, 80);
            TlsProtocol.writeExtensions(byteArrayOutputStream, serverHandshakeState.l);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public boolean getVerifyRequests() {
        return this.verifyRequests;
    }

    /* access modifiers changed from: protected */
    public void notifyClientCertificate(ServerHandshakeState serverHandshakeState, Certificate certificate) {
        if (serverHandshakeState.o == null) {
            throw new IllegalStateException();
        } else if (serverHandshakeState.q != null) {
            throw new TlsFatalAlert(10);
        } else {
            serverHandshakeState.q = certificate;
            if (certificate.isEmpty()) {
                serverHandshakeState.m.skipClientCredentials();
            } else {
                serverHandshakeState.p = TlsUtils.a(certificate, serverHandshakeState.n.getCertificate());
                serverHandshakeState.m.processClientCertificate(certificate);
            }
            serverHandshakeState.a.notifyClientCertificate(certificate);
        }
    }

    /* access modifiers changed from: protected */
    public void processCertificateVerify(ServerHandshakeState serverHandshakeState, byte[] bArr, TlsHandshakeHash tlsHandshakeHash) {
        boolean z;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        DigitallySigned parse = DigitallySigned.parse(serverHandshakeState.b, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        try {
            byte[] finalHash = TlsUtils.isTLSv12(serverHandshakeState.b) ? tlsHandshakeHash.getFinalHash(parse.getAlgorithm().getHash()) : TlsProtocol.getCurrentPRFHash(serverHandshakeState.b, tlsHandshakeHash, null);
            AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(serverHandshakeState.q.getCertificateAt(0).getSubjectPublicKeyInfo());
            TlsSigner createTlsSigner = TlsUtils.createTlsSigner(serverHandshakeState.p);
            createTlsSigner.init(serverHandshakeState.b);
            z = createTlsSigner.verifyRawSignature(parse.getAlgorithm(), parse.getSignature(), createKey, finalHash);
        } catch (Exception unused) {
            z = false;
        }
        if (!z) {
            throw new TlsFatalAlert(51);
        }
    }

    /* access modifiers changed from: protected */
    public void processClientCertificate(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Certificate parse = Certificate.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        notifyClientCertificate(serverHandshakeState, parse);
    }

    /* access modifiers changed from: protected */
    public void processClientHello(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        if (!readVersion.isDTLS()) {
            throw new TlsFatalAlert(47);
        }
        byte[] readFully = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
        if (TlsUtils.readOpaque8(byteArrayInputStream).length > 32) {
            throw new TlsFatalAlert(47);
        }
        TlsUtils.readOpaque8(byteArrayInputStream);
        int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
        if (readUint16 < 2 || (readUint16 & 1) != 0) {
            throw new TlsFatalAlert(50);
        }
        serverHandshakeState.c = TlsUtils.readUint16Array(readUint16 / 2, byteArrayInputStream);
        short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
        if (readUint8 < 1) {
            throw new TlsFatalAlert(47);
        }
        serverHandshakeState.d = TlsUtils.readUint8Array(readUint8, byteArrayInputStream);
        serverHandshakeState.e = TlsProtocol.readExtensions(byteArrayInputStream);
        serverHandshakeState.b.a(readVersion);
        serverHandshakeState.a.notifyClientVersion(readVersion);
        serverHandshakeState.b.getSecurityParameters().g = readFully;
        serverHandshakeState.a.notifyOfferedCipherSuites(serverHandshakeState.c);
        serverHandshakeState.a.notifyOfferedCompressionMethods(serverHandshakeState.d);
        if (Arrays.contains(serverHandshakeState.c, 255)) {
            serverHandshakeState.h = true;
        }
        byte[] extensionData = TlsUtils.getExtensionData(serverHandshakeState.e, TlsProtocol.EXT_RenegotiationInfo);
        if (extensionData != null) {
            serverHandshakeState.h = true;
            if (!Arrays.constantTimeAreEqual(extensionData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                throw new TlsFatalAlert(40);
            }
        }
        serverHandshakeState.a.notifySecureRenegotiation(serverHandshakeState.h);
        if (serverHandshakeState.e != null) {
            serverHandshakeState.a.processClientExtensions(serverHandshakeState.e);
        }
    }

    /* access modifiers changed from: protected */
    public void processClientKeyExchange(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        serverHandshakeState.m.processClientKeyExchange(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    /* access modifiers changed from: protected */
    public void processClientSupplementalData(ServerHandshakeState serverHandshakeState, byte[] bArr) {
        serverHandshakeState.a.processClientSupplementalData(TlsProtocol.readSupplementalDataMessage(new ByteArrayInputStream(bArr)));
    }

    /* access modifiers changed from: protected */
    public DTLSTransport serverHandshake(ServerHandshakeState serverHandshakeState, DTLSRecordLayer dTLSRecordLayer) {
        Certificate certificate;
        SecurityParameters securityParameters = serverHandshakeState.b.getSecurityParameters();
        DTLSReliableHandshake dTLSReliableHandshake = new DTLSReliableHandshake(serverHandshakeState.b, dTLSRecordLayer);
        Message d = dTLSReliableHandshake.d();
        serverHandshakeState.b.a(dTLSRecordLayer.a());
        if (d.b() == 1) {
            processClientHello(serverHandshakeState, d.c());
            byte[] generateServerHello = generateServerHello(serverHandshakeState);
            if (serverHandshakeState.i >= 0) {
                dTLSRecordLayer.a(1 << (serverHandshakeState.i + 8));
            }
            securityParameters.b = serverHandshakeState.f;
            securityParameters.c = serverHandshakeState.g;
            securityParameters.d = TlsProtocol.getPRFAlgorithm(serverHandshakeState.b, serverHandshakeState.f);
            securityParameters.e = 12;
            dTLSReliableHandshake.a(2, generateServerHello);
            dTLSReliableHandshake.a();
            Vector serverSupplementalData = serverHandshakeState.a.getServerSupplementalData();
            if (serverSupplementalData != null) {
                dTLSReliableHandshake.a(23, generateSupplementalData(serverSupplementalData));
            }
            serverHandshakeState.m = serverHandshakeState.a.getKeyExchange();
            serverHandshakeState.m.init(serverHandshakeState.b);
            serverHandshakeState.n = serverHandshakeState.a.getCredentials();
            if (serverHandshakeState.n == null) {
                serverHandshakeState.m.skipServerCredentials();
                certificate = null;
            } else {
                serverHandshakeState.m.processServerCredentials(serverHandshakeState.n);
                certificate = serverHandshakeState.n.getCertificate();
                dTLSReliableHandshake.a(11, generateCertificate(certificate));
            }
            if (certificate == null || certificate.isEmpty()) {
                serverHandshakeState.j = false;
            }
            if (serverHandshakeState.j) {
                CertificateStatus certificateStatus = serverHandshakeState.a.getCertificateStatus();
                if (certificateStatus != null) {
                    dTLSReliableHandshake.a(22, generateCertificateStatus(serverHandshakeState, certificateStatus));
                }
            }
            byte[] generateServerKeyExchange = serverHandshakeState.m.generateServerKeyExchange();
            if (generateServerKeyExchange != null) {
                dTLSReliableHandshake.a(12, generateServerKeyExchange);
            }
            if (serverHandshakeState.n != null) {
                serverHandshakeState.o = serverHandshakeState.a.getCertificateRequest();
                if (serverHandshakeState.o != null) {
                    serverHandshakeState.m.validateCertificateRequest(serverHandshakeState.o);
                    dTLSReliableHandshake.a(13, generateCertificateRequest(serverHandshakeState, serverHandshakeState.o));
                    TlsUtils.a(dTLSReliableHandshake.b(), serverHandshakeState.o.getSupportedSignatureAlgorithms());
                }
            }
            dTLSReliableHandshake.a(14, TlsUtils.EMPTY_BYTES);
            dTLSReliableHandshake.b().sealHashAlgorithms();
            Message d2 = dTLSReliableHandshake.d();
            if (d2.b() == 23) {
                processClientSupplementalData(serverHandshakeState, d2.c());
                d2 = dTLSReliableHandshake.d();
            } else {
                serverHandshakeState.a.processClientSupplementalData(null);
            }
            if (serverHandshakeState.o == null) {
                serverHandshakeState.m.skipClientCredentials();
            } else if (d2.b() == 11) {
                processClientCertificate(serverHandshakeState, d2.c());
                d2 = dTLSReliableHandshake.d();
            } else if (TlsUtils.isTLSv12(serverHandshakeState.b)) {
                throw new TlsFatalAlert(10);
            } else {
                notifyClientCertificate(serverHandshakeState, Certificate.EMPTY_CHAIN);
            }
            if (d2.b() == 16) {
                processClientKeyExchange(serverHandshakeState, d2.c());
                TlsProtocol.establishMasterSecret(serverHandshakeState.b, serverHandshakeState.m);
                dTLSRecordLayer.a(serverHandshakeState.a.getCipher());
                TlsHandshakeHash c = dTLSReliableHandshake.c();
                if (expectCertificateVerifyMessage(serverHandshakeState)) {
                    processCertificateVerify(serverHandshakeState, dTLSReliableHandshake.a(15), c);
                }
                processFinished(dTLSReliableHandshake.a(20), TlsUtils.a((TlsContext) serverHandshakeState.b, ExporterLabel.client_finished, TlsProtocol.getCurrentPRFHash(serverHandshakeState.b, dTLSReliableHandshake.b(), null)));
                if (serverHandshakeState.k) {
                    dTLSReliableHandshake.a(4, generateNewSessionTicket(serverHandshakeState, serverHandshakeState.a.getNewSessionTicket()));
                }
                dTLSReliableHandshake.a(20, TlsUtils.a((TlsContext) serverHandshakeState.b, ExporterLabel.server_finished, TlsProtocol.getCurrentPRFHash(serverHandshakeState.b, dTLSReliableHandshake.b(), null)));
                dTLSReliableHandshake.e();
                serverHandshakeState.a.notifyHandshakeComplete();
                return new DTLSTransport(dTLSRecordLayer);
            }
            throw new TlsFatalAlert(10);
        }
        throw new TlsFatalAlert(10);
    }

    public void setVerifyRequests(boolean z) {
        this.verifyRequests = z;
    }
}
