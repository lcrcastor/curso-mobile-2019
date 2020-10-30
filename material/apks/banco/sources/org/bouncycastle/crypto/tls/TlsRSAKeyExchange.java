package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.io.Streams;

public class TlsRSAKeyExchange extends AbstractTlsKeyExchange {
    protected byte[] premasterSecret;
    protected RSAKeyParameters rsaServerPublicKey = null;
    protected TlsEncryptionCredentials serverCredentials = null;
    protected AsymmetricKeyParameter serverPublicKey = null;

    public TlsRSAKeyExchange(Vector vector) {
        super(1, vector);
    }

    public void generateClientKeyExchange(OutputStream outputStream) {
        this.premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(this.context, this.rsaServerPublicKey, outputStream);
    }

    public byte[] generatePremasterSecret() {
        if (this.premasterSecret == null) {
            throw new TlsFatalAlert(80);
        }
        byte[] bArr = this.premasterSecret;
        this.premasterSecret = null;
        return bArr;
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
    }

    public void processClientKeyExchange(InputStream inputStream) {
        this.premasterSecret = this.serverCredentials.decryptPreMasterSecret(TlsUtils.isSSL(this.context) ? Streams.readAll(inputStream) : TlsUtils.readOpaque16(inputStream));
    }

    public void processServerCertificate(Certificate certificate) {
        if (certificate.isEmpty()) {
            throw new TlsFatalAlert(42);
        }
        Certificate certificateAt = certificate.getCertificateAt(0);
        try {
            this.serverPublicKey = PublicKeyFactory.createKey(certificateAt.getSubjectPublicKeyInfo());
            if (this.serverPublicKey.isPrivate()) {
                throw new TlsFatalAlert(80);
            }
            this.rsaServerPublicKey = validateRSAPublicKey((RSAKeyParameters) this.serverPublicKey);
            TlsUtils.a(certificateAt, 32);
            super.processServerCertificate(certificate);
        } catch (RuntimeException unused) {
            throw new TlsFatalAlert(43);
        }
    }

    public void processServerCredentials(TlsCredentials tlsCredentials) {
        if (!(tlsCredentials instanceof TlsEncryptionCredentials)) {
            throw new TlsFatalAlert(80);
        }
        processServerCertificate(tlsCredentials.getCertificate());
        this.serverCredentials = (TlsEncryptionCredentials) tlsCredentials;
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
                        break;
                    default:
                        throw new TlsFatalAlert(47);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public RSAKeyParameters validateRSAPublicKey(RSAKeyParameters rSAKeyParameters) {
        if (rSAKeyParameters.getExponent().isProbablePrime(2)) {
            return rSAKeyParameters;
        }
        throw new TlsFatalAlert(47);
    }
}
