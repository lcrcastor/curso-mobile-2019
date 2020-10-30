package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;

public class TlsECDHKeyExchange extends AbstractTlsKeyExchange {
    protected TlsAgreementCredentials agreementCredentials;
    protected short[] clientECPointFormats;
    protected ECPrivateKeyParameters ecAgreePrivateKey;
    protected ECPublicKeyParameters ecAgreePublicKey;
    protected int[] namedCurves;
    protected short[] serverECPointFormats;
    protected AsymmetricKeyParameter serverPublicKey;
    protected TlsSigner tlsSigner;

    public TlsECDHKeyExchange(int i, Vector vector, int[] iArr, short[] sArr, short[] sArr2) {
        TlsSigner tlsSigner2;
        super(i, vector);
        switch (i) {
            case 16:
            case 18:
                tlsSigner2 = null;
                break;
            case 17:
                tlsSigner2 = new TlsECDSASigner();
                break;
            case 19:
                tlsSigner2 = new TlsRSASigner();
                break;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.tlsSigner = tlsSigner2;
        this.keyExchange = i;
        this.namedCurves = iArr;
        this.clientECPointFormats = sArr;
        this.serverECPointFormats = sArr2;
    }

    public void generateClientKeyExchange(OutputStream outputStream) {
        if (this.agreementCredentials == null) {
            this.ecAgreePrivateKey = TlsECCUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.serverECPointFormats, this.ecAgreePublicKey.getParameters(), outputStream);
        }
    }

    public byte[] generatePremasterSecret() {
        if (this.agreementCredentials != null) {
            return this.agreementCredentials.generateAgreement(this.ecAgreePublicKey);
        }
        if (this.ecAgreePrivateKey != null) {
            return TlsECCUtils.calculateECDHBasicAgreement(this.ecAgreePublicKey, this.ecAgreePrivateKey);
        }
        throw new TlsFatalAlert(80);
    }

    public void init(TlsContext tlsContext) {
        super.init(tlsContext);
        if (this.tlsSigner != null) {
            this.tlsSigner.init(tlsContext);
        }
    }

    public void processClientCertificate(Certificate certificate) {
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) {
        if (tlsCredentials instanceof TlsAgreementCredentials) {
            this.agreementCredentials = (TlsAgreementCredentials) tlsCredentials;
        } else if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void processClientKeyExchange(InputStream inputStream) {
        if (this.ecAgreePublicKey == null) {
            byte[] readOpaque8 = TlsUtils.readOpaque8(inputStream);
            this.ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(this.serverECPointFormats, this.ecAgreePrivateKey.getParameters(), readOpaque8));
        }
    }

    public void processServerCertificate(Certificate certificate) {
        int i;
        if (certificate.isEmpty()) {
            throw new TlsFatalAlert(42);
        }
        Certificate certificateAt = certificate.getCertificateAt(0);
        try {
            this.serverPublicKey = PublicKeyFactory.createKey(certificateAt.getSubjectPublicKeyInfo());
            if (this.tlsSigner == null) {
                try {
                    this.ecAgreePublicKey = TlsECCUtils.validateECPublicKey((ECPublicKeyParameters) this.serverPublicKey);
                    i = 8;
                } catch (ClassCastException unused) {
                    throw new TlsFatalAlert(46);
                }
            } else if (!this.tlsSigner.isValidPublicKey(this.serverPublicKey)) {
                throw new TlsFatalAlert(46);
            } else {
                i = 128;
            }
            TlsUtils.a(certificateAt, i);
            super.processServerCertificate(certificate);
        } catch (RuntimeException unused2) {
            throw new TlsFatalAlert(43);
        }
    }

    public boolean requiresServerKeyExchange() {
        switch (this.keyExchange) {
            case 17:
            case 19:
            case 20:
                return true;
            default:
                return false;
        }
    }

    public void skipServerCredentials() {
        throw new TlsFatalAlert(10);
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) {
        short[] certificateTypes = certificateRequest.getCertificateTypes();
        for (short s : certificateTypes) {
            switch (s) {
                case 1:
                case 2:
                    break;
                default:
                    switch (s) {
                        case 64:
                        case 65:
                        case 66:
                            break;
                        default:
                            throw new TlsFatalAlert(47);
                    }
            }
        }
    }
}
