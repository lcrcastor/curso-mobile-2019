package org.bouncycastle.crypto.tls;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;

public class TlsDHKeyExchange extends AbstractTlsKeyExchange {
    protected static final BigInteger ONE = BigInteger.valueOf(1);
    protected static final BigInteger TWO = BigInteger.valueOf(2);
    protected TlsAgreementCredentials agreementCredentials;
    protected DHPrivateKeyParameters dhAgreeClientPrivateKey;
    protected DHPublicKeyParameters dhAgreeClientPublicKey;
    protected DHPrivateKeyParameters dhAgreeServerPrivateKey;
    protected DHPublicKeyParameters dhAgreeServerPublicKey;
    protected DHParameters dhParameters;
    protected AsymmetricKeyParameter serverPublicKey;
    protected TlsSigner tlsSigner;

    public TlsDHKeyExchange(int i, Vector vector, DHParameters dHParameters) {
        TlsSigner tlsSigner2;
        super(i, vector);
        if (i == 3) {
            tlsSigner2 = new TlsDSSSigner();
        } else if (i == 5) {
            tlsSigner2 = new TlsRSASigner();
        } else if (i == 7 || i == 9) {
            tlsSigner2 = null;
        } else {
            throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.tlsSigner = tlsSigner2;
        this.dhParameters = dHParameters;
    }

    public void generateClientKeyExchange(OutputStream outputStream) {
        if (this.agreementCredentials == null) {
            this.dhAgreeClientPrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.dhAgreeServerPublicKey.getParameters(), outputStream);
        }
    }

    public byte[] generatePremasterSecret() {
        if (this.agreementCredentials != null) {
            return this.agreementCredentials.generateAgreement(this.dhAgreeServerPublicKey);
        }
        if (this.dhAgreeServerPrivateKey != null) {
            return TlsDHUtils.calculateDHBasicAgreement(this.dhAgreeClientPublicKey, this.dhAgreeServerPrivateKey);
        }
        if (this.dhAgreeClientPrivateKey != null) {
            return TlsDHUtils.calculateDHBasicAgreement(this.dhAgreeServerPublicKey, this.dhAgreeClientPrivateKey);
        }
        throw new TlsFatalAlert(80);
    }

    public void init(TlsContext tlsContext) {
        super.init(tlsContext);
        if (this.tlsSigner != null) {
            this.tlsSigner.init(tlsContext);
        }
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) {
        if (tlsCredentials instanceof TlsAgreementCredentials) {
            this.agreementCredentials = (TlsAgreementCredentials) tlsCredentials;
        } else if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
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
                    this.dhAgreeServerPublicKey = TlsDHUtils.validateDHPublicKey((DHPublicKeyParameters) this.serverPublicKey);
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
        int i = this.keyExchange;
        return i == 3 || i == 5 || i == 11;
    }

    public void skipServerCredentials() {
        throw new TlsFatalAlert(10);
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) {
        short[] certificateTypes = certificateRequest.getCertificateTypes();
        for (short s : certificateTypes) {
            if (s != 64) {
                switch (s) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        break;
                    default:
                        throw new TlsFatalAlert(47);
                }
            }
        }
    }
}
