package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.crypto.tls.SessionParameters.Builder;
import org.bouncycastle.util.Arrays;

public class DTLSClientProtocol extends DTLSProtocol {

    public static class ClientHandshakeState {
        TlsClient a = null;
        TlsClientContextImpl b = null;
        TlsSession c = null;
        SessionParameters d = null;
        Builder e = null;
        int[] f = null;
        short[] g = null;
        Hashtable h = null;
        byte[] i = null;
        int j = -1;
        short k = -1;
        boolean l = false;
        short m = -1;
        boolean n = false;
        boolean o = false;
        TlsKeyExchange p = null;
        TlsAuthentication q = null;
        CertificateStatus r = null;
        CertificateRequest s = null;
        TlsCredentials t = null;

        protected ClientHandshakeState() {
        }
    }

    public DTLSClientProtocol(SecureRandom secureRandom) {
        super(secureRandom);
    }

    protected static byte[] patchClientHelloWithCookie(byte[] bArr, byte[] bArr2) {
        int readUint8 = 35 + TlsUtils.readUint8(bArr, 34);
        int i = readUint8 + 1;
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, bArr3, 0, readUint8);
        TlsUtils.checkUint8(bArr2.length);
        TlsUtils.writeUint8(bArr2.length, bArr3, readUint8);
        System.arraycopy(bArr2, 0, bArr3, i, bArr2.length);
        System.arraycopy(bArr, i, bArr3, bArr2.length + i, bArr.length - i);
        return bArr3;
    }

    /* access modifiers changed from: protected */
    public DTLSTransport clientHandshake(ClientHandshakeState clientHandshakeState, DTLSRecordLayer dTLSRecordLayer) {
        Message message;
        Certificate certificate;
        SignatureAndHashAlgorithm signatureAndHashAlgorithm;
        byte[] bArr;
        SecurityParameters securityParameters = clientHandshakeState.b.getSecurityParameters();
        DTLSReliableHandshake dTLSReliableHandshake = new DTLSReliableHandshake(clientHandshakeState.b, dTLSRecordLayer);
        byte[] generateClientHello = generateClientHello(clientHandshakeState, clientHandshakeState.a);
        boolean z = true;
        dTLSReliableHandshake.a(1, generateClientHello);
        while (true) {
            Message d = dTLSReliableHandshake.d();
            if (d.b() == 3) {
                if (!dTLSRecordLayer.b().isEqualOrEarlierVersionOf(clientHandshakeState.b.getClientVersion())) {
                    throw new TlsFatalAlert(47);
                }
                byte[] patchClientHelloWithCookie = patchClientHelloWithCookie(generateClientHello, processHelloVerifyRequest(clientHandshakeState, d.c()));
                dTLSReliableHandshake.f();
                dTLSReliableHandshake.a(1, patchClientHelloWithCookie);
            } else if (d.b() == 2) {
                reportServerVersion(clientHandshakeState, dTLSRecordLayer.a());
                processServerHello(clientHandshakeState, d.c());
                if (clientHandshakeState.m >= 0) {
                    dTLSRecordLayer.a(1 << (clientHandshakeState.m + 8));
                }
                securityParameters.b = clientHandshakeState.j;
                securityParameters.c = clientHandshakeState.k;
                securityParameters.d = TlsProtocol.getPRFAlgorithm(clientHandshakeState.b, clientHandshakeState.j);
                securityParameters.e = 12;
                dTLSReliableHandshake.a();
                if (clientHandshakeState.i.length <= 0 || clientHandshakeState.c == null || !Arrays.areEqual(clientHandshakeState.i, clientHandshakeState.c.getSessionID())) {
                    z = false;
                }
                if (!z) {
                    invalidateSession(clientHandshakeState);
                    if (clientHandshakeState.i.length > 0) {
                        clientHandshakeState.c = new TlsSessionImpl(clientHandshakeState.i, null);
                    }
                    Message d2 = dTLSReliableHandshake.d();
                    if (d2.b() == 23) {
                        processServerSupplementalData(clientHandshakeState, d2.c());
                        d2 = dTLSReliableHandshake.d();
                    } else {
                        clientHandshakeState.a.processServerSupplementalData(null);
                    }
                    clientHandshakeState.p = clientHandshakeState.a.getKeyExchange();
                    clientHandshakeState.p.init(clientHandshakeState.b);
                    if (d2.b() == 11) {
                        certificate = processServerCertificate(clientHandshakeState, d2.c());
                        message = dTLSReliableHandshake.d();
                    } else {
                        clientHandshakeState.p.skipServerCredentials();
                        message = d2;
                        certificate = null;
                    }
                    if (certificate == null || certificate.isEmpty()) {
                        clientHandshakeState.n = false;
                    }
                    if (message.b() == 22) {
                        processCertificateStatus(clientHandshakeState, message.c());
                        message = dTLSReliableHandshake.d();
                    }
                    if (message.b() == 12) {
                        processServerKeyExchange(clientHandshakeState, message.c());
                        message = dTLSReliableHandshake.d();
                    } else {
                        clientHandshakeState.p.skipServerKeyExchange();
                    }
                    if (message.b() == 13) {
                        processCertificateRequest(clientHandshakeState, message.c());
                        TlsUtils.a(dTLSReliableHandshake.b(), clientHandshakeState.s.getSupportedSignatureAlgorithms());
                        message = dTLSReliableHandshake.d();
                    }
                    if (message.b() != 14) {
                        throw new TlsFatalAlert(10);
                    } else if (message.c().length != 0) {
                        throw new TlsFatalAlert(50);
                    } else {
                        dTLSReliableHandshake.b().sealHashAlgorithms();
                        Vector clientSupplementalData = clientHandshakeState.a.getClientSupplementalData();
                        if (clientSupplementalData != null) {
                            dTLSReliableHandshake.a(23, generateSupplementalData(clientSupplementalData));
                        }
                        if (clientHandshakeState.s != null) {
                            clientHandshakeState.t = clientHandshakeState.q.getClientCredentials(clientHandshakeState.s);
                            Certificate certificate2 = clientHandshakeState.t != null ? clientHandshakeState.t.getCertificate() : null;
                            if (certificate2 == null) {
                                certificate2 = Certificate.EMPTY_CHAIN;
                            }
                            dTLSReliableHandshake.a(11, generateCertificate(certificate2));
                        }
                        if (clientHandshakeState.t != null) {
                            clientHandshakeState.p.processClientCredentials(clientHandshakeState.t);
                        } else {
                            clientHandshakeState.p.skipClientCredentials();
                        }
                        dTLSReliableHandshake.a(16, generateClientKeyExchange(clientHandshakeState));
                        TlsProtocol.establishMasterSecret(clientHandshakeState.b, clientHandshakeState.p);
                        dTLSRecordLayer.a(clientHandshakeState.a.getCipher());
                        TlsHandshakeHash c = dTLSReliableHandshake.c();
                        if (clientHandshakeState.t != null && (clientHandshakeState.t instanceof TlsSignerCredentials)) {
                            TlsSignerCredentials tlsSignerCredentials = (TlsSignerCredentials) clientHandshakeState.t;
                            if (TlsUtils.isTLSv12(clientHandshakeState.b)) {
                                signatureAndHashAlgorithm = tlsSignerCredentials.getSignatureAndHashAlgorithm();
                                if (signatureAndHashAlgorithm == null) {
                                    throw new TlsFatalAlert(80);
                                }
                                bArr = c.getFinalHash(signatureAndHashAlgorithm.getHash());
                            } else {
                                bArr = TlsProtocol.getCurrentPRFHash(clientHandshakeState.b, c, null);
                                signatureAndHashAlgorithm = null;
                            }
                            dTLSReliableHandshake.a(15, generateCertificateVerify(clientHandshakeState, new DigitallySigned(signatureAndHashAlgorithm, tlsSignerCredentials.generateCertificateSignature(bArr))));
                        }
                        dTLSReliableHandshake.a(20, TlsUtils.a((TlsContext) clientHandshakeState.b, ExporterLabel.client_finished, TlsProtocol.getCurrentPRFHash(clientHandshakeState.b, dTLSReliableHandshake.b(), null)));
                        if (clientHandshakeState.o) {
                            Message d3 = dTLSReliableHandshake.d();
                            if (d3.b() == 4) {
                                processNewSessionTicket(clientHandshakeState, d3.c());
                            } else {
                                throw new TlsFatalAlert(10);
                            }
                        }
                        processFinished(dTLSReliableHandshake.a(20), TlsUtils.a((TlsContext) clientHandshakeState.b, ExporterLabel.server_finished, TlsProtocol.getCurrentPRFHash(clientHandshakeState.b, dTLSReliableHandshake.b(), null)));
                        dTLSReliableHandshake.e();
                        if (clientHandshakeState.c != null) {
                            clientHandshakeState.d = new Builder().setCipherSuite(securityParameters.b).setCompressionAlgorithm(securityParameters.c).setMasterSecret(securityParameters.f).setPeerCertificate(certificate).build();
                            clientHandshakeState.c = TlsUtils.importSession(clientHandshakeState.c.getSessionID(), clientHandshakeState.d);
                            clientHandshakeState.b.a(clientHandshakeState.c);
                        }
                        clientHandshakeState.a.notifyHandshakeComplete();
                        return new DTLSTransport(dTLSRecordLayer);
                    }
                } else if (securityParameters.getCipherSuite() == clientHandshakeState.d.getCipherSuite() && securityParameters.getCompressionAlgorithm() == clientHandshakeState.d.getCompressionAlgorithm()) {
                    securityParameters.f = Arrays.clone(clientHandshakeState.d.getMasterSecret());
                    dTLSRecordLayer.a(clientHandshakeState.a.getCipher());
                    processFinished(dTLSReliableHandshake.a(20), TlsUtils.a((TlsContext) clientHandshakeState.b, ExporterLabel.server_finished, TlsProtocol.getCurrentPRFHash(clientHandshakeState.b, dTLSReliableHandshake.b(), null)));
                    dTLSReliableHandshake.a(20, TlsUtils.a((TlsContext) clientHandshakeState.b, ExporterLabel.client_finished, TlsProtocol.getCurrentPRFHash(clientHandshakeState.b, dTLSReliableHandshake.b(), null)));
                    dTLSReliableHandshake.e();
                    clientHandshakeState.b.a(clientHandshakeState.c);
                    clientHandshakeState.a.notifyHandshakeComplete();
                    return new DTLSTransport(dTLSRecordLayer);
                } else {
                    throw new TlsFatalAlert(47);
                }
            } else {
                throw new TlsFatalAlert(10);
            }
        }
    }

    public DTLSTransport connect(TlsClient tlsClient, DatagramTransport datagramTransport) {
        if (tlsClient == null) {
            throw new IllegalArgumentException("'client' cannot be null");
        } else if (datagramTransport == null) {
            throw new IllegalArgumentException("'transport' cannot be null");
        } else {
            SecurityParameters securityParameters = new SecurityParameters();
            securityParameters.a = 1;
            ClientHandshakeState clientHandshakeState = new ClientHandshakeState();
            clientHandshakeState.a = tlsClient;
            clientHandshakeState.b = new TlsClientContextImpl(this.secureRandom, securityParameters);
            securityParameters.g = TlsProtocol.createRandomBlock(tlsClient.shouldUseGMTUnixTime(), clientHandshakeState.b.getNonceRandomGenerator());
            tlsClient.init(clientHandshakeState.b);
            DTLSRecordLayer dTLSRecordLayer = new DTLSRecordLayer(datagramTransport, clientHandshakeState.b, tlsClient, 22);
            TlsSession sessionToResume = clientHandshakeState.a.getSessionToResume();
            if (sessionToResume != null) {
                SessionParameters exportSessionParameters = sessionToResume.exportSessionParameters();
                if (exportSessionParameters != null) {
                    clientHandshakeState.c = sessionToResume;
                    clientHandshakeState.d = exportSessionParameters;
                }
            }
            try {
                return clientHandshake(clientHandshakeState, dTLSRecordLayer);
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
    public byte[] generateCertificateVerify(ClientHandshakeState clientHandshakeState, DigitallySigned digitallySigned) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        digitallySigned.encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] generateClientHello(ClientHandshakeState clientHandshakeState, TlsClient tlsClient) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ProtocolVersion clientVersion = tlsClient.getClientVersion();
        if (!clientVersion.isDTLS()) {
            throw new TlsFatalAlert(80);
        }
        clientHandshakeState.b.a(clientVersion);
        TlsUtils.writeVersion(clientVersion, byteArrayOutputStream);
        byteArrayOutputStream.write(clientHandshakeState.b.getSecurityParameters().getClientRandom());
        byte[] bArr = TlsUtils.EMPTY_BYTES;
        if (clientHandshakeState.c != null) {
            bArr = clientHandshakeState.c.getSessionID();
            if (bArr == null || bArr.length > 32) {
                bArr = TlsUtils.EMPTY_BYTES;
            }
        }
        TlsUtils.writeOpaque8(bArr, byteArrayOutputStream);
        TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, byteArrayOutputStream);
        clientHandshakeState.f = tlsClient.getCipherSuites();
        clientHandshakeState.h = tlsClient.getClientExtensions();
        boolean z = !Arrays.contains(clientHandshakeState.f, 255);
        if ((TlsUtils.getExtensionData(clientHandshakeState.h, TlsProtocol.EXT_RenegotiationInfo) == null) && z) {
            clientHandshakeState.f = Arrays.append(clientHandshakeState.f, 255);
        }
        TlsUtils.writeUint16ArrayWithUint16Length(clientHandshakeState.f, byteArrayOutputStream);
        clientHandshakeState.g = new short[]{0};
        TlsUtils.writeUint8ArrayWithUint8Length(clientHandshakeState.g, byteArrayOutputStream);
        if (clientHandshakeState.h != null) {
            TlsProtocol.writeExtensions(byteArrayOutputStream, clientHandshakeState.h);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public byte[] generateClientKeyExchange(ClientHandshakeState clientHandshakeState) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        clientHandshakeState.p.generateClientKeyExchange(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public void invalidateSession(ClientHandshakeState clientHandshakeState) {
        if (clientHandshakeState.d != null) {
            clientHandshakeState.d.clear();
            clientHandshakeState.d = null;
        }
        if (clientHandshakeState.c != null) {
            clientHandshakeState.c.invalidate();
            clientHandshakeState.c = null;
        }
    }

    /* access modifiers changed from: protected */
    public void processCertificateRequest(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        if (clientHandshakeState.q == null) {
            throw new TlsFatalAlert(40);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        clientHandshakeState.s = CertificateRequest.parse(clientHandshakeState.b, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        clientHandshakeState.p.validateCertificateRequest(clientHandshakeState.s);
    }

    /* access modifiers changed from: protected */
    public void processCertificateStatus(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        if (!clientHandshakeState.n) {
            throw new TlsFatalAlert(10);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        clientHandshakeState.r = CertificateStatus.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    /* access modifiers changed from: protected */
    public byte[] processHelloVerifyRequest(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        byte[] readOpaque8 = TlsUtils.readOpaque8(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        if (!readVersion.isEqualOrEarlierVersionOf(clientHandshakeState.b.getClientVersion())) {
            throw new TlsFatalAlert(47);
        } else if (ProtocolVersion.DTLSv12.isEqualOrEarlierVersionOf(readVersion) || readOpaque8.length <= 32) {
            return readOpaque8;
        } else {
            throw new TlsFatalAlert(47);
        }
    }

    /* access modifiers changed from: protected */
    public void processNewSessionTicket(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        NewSessionTicket parse = NewSessionTicket.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        clientHandshakeState.a.notifyNewSessionTicket(parse);
    }

    /* access modifiers changed from: protected */
    public Certificate processServerCertificate(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        Certificate parse = Certificate.parse(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        clientHandshakeState.p.processServerCertificate(parse);
        clientHandshakeState.q = clientHandshakeState.a.getAuthentication();
        clientHandshakeState.q.notifyServerCertificate(parse);
        return parse;
    }

    /* access modifiers changed from: protected */
    public void processServerHello(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        SecurityParameters securityParameters = clientHandshakeState.b.getSecurityParameters();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
        reportServerVersion(clientHandshakeState, readVersion);
        securityParameters.h = TlsUtils.readFully(32, (InputStream) byteArrayInputStream);
        clientHandshakeState.i = TlsUtils.readOpaque8(byteArrayInputStream);
        if (clientHandshakeState.i.length > 32) {
            throw new TlsFatalAlert(47);
        }
        clientHandshakeState.a.notifySessionID(clientHandshakeState.i);
        clientHandshakeState.j = TlsUtils.readUint16(byteArrayInputStream);
        if (!Arrays.contains(clientHandshakeState.f, clientHandshakeState.j) || clientHandshakeState.j == 0 || clientHandshakeState.j == 255 || !TlsUtils.isValidCipherSuiteForVersion(clientHandshakeState.j, readVersion)) {
            throw new TlsFatalAlert(47);
        }
        validateSelectedCipherSuite(clientHandshakeState.j, 47);
        clientHandshakeState.a.notifySelectedCipherSuite(clientHandshakeState.j);
        clientHandshakeState.k = TlsUtils.readUint8(byteArrayInputStream);
        if (!Arrays.contains(clientHandshakeState.g, clientHandshakeState.k)) {
            throw new TlsFatalAlert(47);
        }
        clientHandshakeState.a.notifySelectedCompressionMethod(clientHandshakeState.k);
        Hashtable readExtensions = TlsProtocol.readExtensions(byteArrayInputStream);
        if (readExtensions != null) {
            Enumeration keys = readExtensions.keys();
            while (keys.hasMoreElements()) {
                Integer num = (Integer) keys.nextElement();
                if (!num.equals(TlsProtocol.EXT_RenegotiationInfo) && TlsUtils.getExtensionData(clientHandshakeState.h, num) == null) {
                    throw new TlsFatalAlert(AlertDescription.unsupported_extension);
                }
            }
            byte[] bArr2 = (byte[]) readExtensions.get(TlsProtocol.EXT_RenegotiationInfo);
            if (bArr2 != null) {
                clientHandshakeState.l = true;
                if (!Arrays.constantTimeAreEqual(bArr2, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES))) {
                    throw new TlsFatalAlert(40);
                }
            }
            boolean hasEncryptThenMACExtension = TlsExtensionsUtils.hasEncryptThenMACExtension(readExtensions);
            if (!hasEncryptThenMACExtension || TlsUtils.isBlockCipherSuite(clientHandshakeState.j)) {
                securityParameters.k = hasEncryptThenMACExtension;
                clientHandshakeState.m = evaluateMaxFragmentLengthExtension(clientHandshakeState.h, readExtensions, 47);
                securityParameters.j = TlsExtensionsUtils.hasTruncatedHMacExtension(readExtensions);
                clientHandshakeState.n = TlsUtils.hasExpectedEmptyExtensionData(readExtensions, TlsExtensionsUtils.EXT_status_request, 47);
                clientHandshakeState.o = TlsUtils.hasExpectedEmptyExtensionData(readExtensions, TlsProtocol.EXT_SessionTicket, 47);
            } else {
                throw new TlsFatalAlert(47);
            }
        }
        clientHandshakeState.a.notifySecureRenegotiation(clientHandshakeState.l);
        if (clientHandshakeState.h != null) {
            clientHandshakeState.a.processServerExtensions(readExtensions);
        }
    }

    /* access modifiers changed from: protected */
    public void processServerKeyExchange(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        clientHandshakeState.p.processServerKeyExchange(byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
    }

    /* access modifiers changed from: protected */
    public void processServerSupplementalData(ClientHandshakeState clientHandshakeState, byte[] bArr) {
        clientHandshakeState.a.processServerSupplementalData(TlsProtocol.readSupplementalDataMessage(new ByteArrayInputStream(bArr)));
    }

    /* access modifiers changed from: protected */
    public void reportServerVersion(ClientHandshakeState clientHandshakeState, ProtocolVersion protocolVersion) {
        TlsClientContextImpl tlsClientContextImpl = clientHandshakeState.b;
        ProtocolVersion serverVersion = tlsClientContextImpl.getServerVersion();
        if (serverVersion == null) {
            tlsClientContextImpl.b(protocolVersion);
            clientHandshakeState.a.notifyServerVersion(protocolVersion);
        } else if (!serverVersion.equals(protocolVersion)) {
            throw new TlsFatalAlert(47);
        }
    }
}
