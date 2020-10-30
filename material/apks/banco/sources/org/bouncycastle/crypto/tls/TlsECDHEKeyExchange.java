package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.io.TeeInputStream;

public class TlsECDHEKeyExchange extends TlsECDHKeyExchange {
    protected TlsSignerCredentials serverCredentials = null;

    public TlsECDHEKeyExchange(int i, Vector vector, int[] iArr, short[] sArr, short[] sArr2) {
        super(i, vector, iArr, sArr, sArr2);
    }

    public byte[] generateServerKeyExchange() {
        int i;
        ECDomainParameters eCDomainParameters;
        Digest digest;
        int i2 = 23;
        if (this.namedCurves != null) {
            int i3 = 0;
            while (true) {
                if (i3 >= this.namedCurves.length) {
                    i = -1;
                    break;
                }
                int i4 = this.namedCurves[i3];
                if (NamedCurve.isValid(i4) && TlsECCUtils.isSupportedNamedCurve(i4)) {
                    i = i4;
                    break;
                }
                i3++;
            }
        } else {
            i = 23;
        }
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = null;
        if (i >= 0) {
            eCDomainParameters = TlsECCUtils.getParametersForNamedCurve(i);
        } else {
            if (!Arrays.contains(this.namedCurves, 65281)) {
                if (Arrays.contains(this.namedCurves, (int) NamedCurve.arbitrary_explicit_char2_curves)) {
                    i2 = 10;
                } else {
                    eCDomainParameters = null;
                }
            }
            eCDomainParameters = TlsECCUtils.getParametersForNamedCurve(i2);
        }
        if (eCDomainParameters == null) {
            throw new TlsFatalAlert(80);
        }
        AsymmetricCipherKeyPair generateECKeyPair = TlsECCUtils.generateECKeyPair(this.context.getSecureRandom(), eCDomainParameters);
        this.ecAgreePrivateKey = (ECPrivateKeyParameters) generateECKeyPair.getPrivate();
        DigestInputBuffer digestInputBuffer = new DigestInputBuffer();
        if (i < 0) {
            TlsECCUtils.writeExplicitECParameters(this.clientECPointFormats, eCDomainParameters, digestInputBuffer);
        } else {
            TlsECCUtils.writeNamedECParameters(i, digestInputBuffer);
        }
        TlsECCUtils.writeECPoint(this.clientECPointFormats, ((ECPublicKeyParameters) generateECKeyPair.getPublic()).getQ(), digestInputBuffer);
        if (TlsUtils.isTLSv12(this.context)) {
            signatureAndHashAlgorithm = this.serverCredentials.getSignatureAndHashAlgorithm();
            if (signatureAndHashAlgorithm == null) {
                throw new TlsFatalAlert(80);
            }
            digest = TlsUtils.createHash(signatureAndHashAlgorithm.getHash());
        } else {
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

    public void processClientCredentials(TlsCredentials tlsCredentials) {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert(80);
        }
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
        TeeInputStream teeInputStream = new TeeInputStream(inputStream, signerInputBuffer);
        ECDomainParameters readECParameters = TlsECCUtils.readECParameters(this.namedCurves, this.clientECPointFormats, teeInputStream);
        byte[] readOpaque8 = TlsUtils.readOpaque8(teeInputStream);
        DigitallySigned parse = DigitallySigned.parse(this.context, inputStream);
        Signer initVerifyer = initVerifyer(this.tlsSigner, parse.getAlgorithm(), securityParameters);
        signerInputBuffer.a(initVerifyer);
        if (!initVerifyer.verifySignature(parse.getSignature())) {
            throw new TlsFatalAlert(51);
        }
        this.ecAgreePublicKey = TlsECCUtils.validateECPublicKey(TlsECCUtils.deserializeECPublicKey(this.clientECPointFormats, readECParameters, readOpaque8));
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
}
