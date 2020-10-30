package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.util.io.TeeInputStream;

public class TlsDHEKeyExchange extends TlsDHKeyExchange {
    protected TlsSignerCredentials serverCredentials = null;

    public TlsDHEKeyExchange(int i, Vector vector, DHParameters dHParameters) {
        super(i, vector, dHParameters);
    }

    public byte[] generateServerKeyExchange() {
        SignatureAndHashAlgorithm signatureAndHashAlgorithm;
        Digest digest;
        if (this.dhParameters == null) {
            throw new TlsFatalAlert(80);
        }
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        this.dhAgreeServerPrivateKey = TlsDHUtils.generateEphemeralServerKeyExchange(this.context.getSecureRandom(), this.dhParameters, digestInputBuffer);
        if (TlsUtils.isTLSv12(this.context)) {
            signatureAndHashAlgorithm = this.serverCredentials.getSignatureAndHashAlgorithm();
            if (signatureAndHashAlgorithm == null) {
                throw new TlsFatalAlert(80);
            }
            digest = TlsUtils.createHash(signatureAndHashAlgorithm.getHash());
        } else {
            signatureAndHashAlgorithm = null;
            digest = new CombinedHash();
        }
        SecurityParameters securityParameters = this.context.getSecurityParameters();
        digest.update(securityParameters.g, 0, securityParameters.g.length);
        digest.update(securityParameters.h, 0, securityParameters.h.length);
        digestInputBuffer.a(digest);
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        new DigitallySigned(signatureAndHashAlgorithm, this.serverCredentials.generateCertificateSignature(bArr)).encode(digestInputBuffer);
        return digestInputBuffer.toByteArray();
    }

    /* access modifiers changed from: protected */
    public Signer initVerifyer(TlsSigner tlsSigner, SignatureAndHashAlgorithm signatureAndHashAlgorithm, SecurityParameters securityParameters) {
        Signer createVerifyer = tlsSigner.createVerifyer(signatureAndHashAlgorithm, this.serverPublicKey);
        createVerifyer.update(securityParameters.g, 0, securityParameters.g.length);
        createVerifyer.update(securityParameters.h, 0, securityParameters.h.length);
        return createVerifyer;
    }

    public void processServerCredentials(TlsCredentials tlsCredentials) {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
        processServerCertificate(tlsCredentials.getCertificate());
        this.serverCredentials = (TlsSignerCredentials) tlsCredentials;
    }

    public void processServerKeyExchange(InputStream inputStream) {
        SecurityParameters securityParameters = this.context.getSecurityParameters();
        SignerInputBuffer signerInputBuffer = new SignerInputBuffer();
        ServerDHParams parse = ServerDHParams.parse(new TeeInputStream(inputStream, signerInputBuffer));
        DigitallySigned parse2 = DigitallySigned.parse(this.context, inputStream);
        Signer initVerifyer = initVerifyer(this.tlsSigner, parse2.getAlgorithm(), securityParameters);
        signerInputBuffer.a(initVerifyer);
        if (!initVerifyer.verifySignature(parse2.getSignature())) {
            throw new TlsFatalAlert(51);
        }
        this.dhAgreeServerPublicKey = TlsDHUtils.validateDHPublicKey(parse.getPublicKey());
    }
}
