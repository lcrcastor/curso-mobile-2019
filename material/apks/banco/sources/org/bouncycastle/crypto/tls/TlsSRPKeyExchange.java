package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;
import org.bouncycastle.crypto.agreement.srp.SRP6Util;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.io.TeeInputStream;

public class TlsSRPKeyExchange extends AbstractTlsKeyExchange {
    protected BigInteger B = null;
    protected byte[] identity;
    protected byte[] password;
    protected byte[] s = null;
    protected AsymmetricKeyParameter serverPublicKey = null;
    protected SRP6Client srpClient = new SRP6Client();
    protected TlsSigner tlsSigner;

    public TlsSRPKeyExchange(int i, Vector vector, byte[] bArr, byte[] bArr2) {
        super(i, vector);
        TlsSigner tlsSigner2 = null;
        switch (i) {
            case 21:
                break;
            case 22:
                tlsSigner2 = new TlsDSSSigner();
                break;
            case 23:
                tlsSigner2 = new TlsRSASigner();
                break;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
        this.tlsSigner = tlsSigner2;
        this.keyExchange = i;
        this.identity = bArr;
        this.password = bArr2;
    }

    public void generateClientKeyExchange(OutputStream outputStream) {
        TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(this.srpClient.generateClientCredentials(this.s, this.identity, this.password)), outputStream);
    }

    public byte[] generatePremasterSecret() {
        try {
            return BigIntegers.asUnsignedByteArray(this.srpClient.calculateSecret(this.B));
        } catch (CryptoException unused) {
            throw new TlsFatalAlert(47);
        }
    }

    public void init(TlsContext tlsContext) {
        super.init(tlsContext);
        if (this.tlsSigner != null) {
            this.tlsSigner.init(tlsContext);
        }
    }

    /* access modifiers changed from: protected */
    public Signer initVerifyer(TlsSigner tlsSigner2, SignatureAndHashAlgorithm signatureAndHashAlgorithm, SecurityParameters securityParameters) {
        Signer createVerifyer = tlsSigner2.createVerifyer(signatureAndHashAlgorithm, this.serverPublicKey);
        createVerifyer.update(securityParameters.g, 0, securityParameters.g.length);
        createVerifyer.update(securityParameters.h, 0, securityParameters.h.length);
        return createVerifyer;
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) {
        throw new TlsFatalAlert(80);
    }

    public void processServerCertificate(Certificate certificate) {
        if (this.tlsSigner == null) {
            throw new TlsFatalAlert(10);
        } else if (certificate.isEmpty()) {
            throw new TlsFatalAlert(42);
        } else {
            Certificate certificateAt = certificate.getCertificateAt(0);
            try {
                this.serverPublicKey = PublicKeyFactory.createKey(certificateAt.getSubjectPublicKeyInfo());
                if (!this.tlsSigner.isValidPublicKey(this.serverPublicKey)) {
                    throw new TlsFatalAlert(46);
                }
                TlsUtils.a(certificateAt, 128);
                super.processServerCertificate(certificate);
            } catch (RuntimeException unused) {
                throw new TlsFatalAlert(43);
            }
        }
    }

    public void processServerKeyExchange(InputStream inputStream) {
        InputStream inputStream2;
        SignerInputBuffer signerInputBuffer;
        SecurityParameters securityParameters = this.context.getSecurityParameters();
        if (this.tlsSigner != null) {
            signerInputBuffer = new SignerInputBuffer();
            inputStream2 = new TeeInputStream(inputStream, signerInputBuffer);
        } else {
            signerInputBuffer = null;
            inputStream2 = inputStream;
        }
        byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream2);
        byte[] readOpaque162 = TlsUtils.readOpaque16(inputStream2);
        byte[] readOpaque8 = TlsUtils.readOpaque8(inputStream2);
        byte[] readOpaque163 = TlsUtils.readOpaque16(inputStream2);
        if (signerInputBuffer != null) {
            DigitallySigned parse = DigitallySigned.parse(this.context, inputStream);
            Signer initVerifyer = initVerifyer(this.tlsSigner, parse.getAlgorithm(), securityParameters);
            signerInputBuffer.a(initVerifyer);
            if (!initVerifyer.verifySignature(parse.getSignature())) {
                throw new TlsFatalAlert(51);
            }
        }
        BigInteger bigInteger = new BigInteger(1, readOpaque16);
        BigInteger bigInteger2 = new BigInteger(1, readOpaque162);
        this.s = readOpaque8;
        try {
            this.B = SRP6Util.validatePublicValue(bigInteger, new BigInteger(1, readOpaque163));
            this.srpClient.init(bigInteger, bigInteger2, TlsUtils.createHash(2), this.context.getSecureRandom());
        } catch (CryptoException unused) {
            throw new TlsFatalAlert(47);
        }
    }

    public boolean requiresServerKeyExchange() {
        return true;
    }

    public void skipServerCredentials() {
        if (this.tlsSigner != null) {
            throw new TlsFatalAlert(10);
        }
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) {
        throw new TlsFatalAlert(10);
    }
}
