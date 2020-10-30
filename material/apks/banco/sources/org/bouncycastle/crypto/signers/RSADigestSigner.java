package org.bouncycastle.crypto.signers;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

public class RSADigestSigner implements Signer {
    private static final Hashtable e = new Hashtable();
    private final AsymmetricBlockCipher a;
    private final AlgorithmIdentifier b;
    private final Digest c;
    private boolean d;

    static {
        e.put("RIPEMD128", TeleTrusTObjectIdentifiers.ripemd128);
        e.put("RIPEMD160", TeleTrusTObjectIdentifiers.ripemd160);
        e.put("RIPEMD256", TeleTrusTObjectIdentifiers.ripemd256);
        e.put(CommonUtils.SHA1_INSTANCE, X509ObjectIdentifiers.id_SHA1);
        e.put("SHA-224", NISTObjectIdentifiers.id_sha224);
        e.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        e.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        e.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        e.put("SHA-512/224", NISTObjectIdentifiers.id_sha512_224);
        e.put("SHA-512/256", NISTObjectIdentifiers.id_sha512_256);
        e.put("MD2", PKCSObjectIdentifiers.md2);
        e.put("MD4", PKCSObjectIdentifiers.md4);
        e.put(CommonUtils.MD5_INSTANCE, PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(Digest digest) {
        this(digest, (ASN1ObjectIdentifier) e.get(digest.getAlgorithmName()));
    }

    public RSADigestSigner(Digest digest, ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = new PKCS1Encoding(new RSABlindedEngine());
        this.c = digest;
        this.b = new AlgorithmIdentifier(aSN1ObjectIdentifier, DERNull.INSTANCE);
    }

    private byte[] a(byte[] bArr) {
        return new DigestInfo(this.b, bArr).getEncoded(ASN1Encoding.DER);
    }

    public byte[] generateSignature() {
        if (!this.d) {
            throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.c.getDigestSize()];
        this.c.doFinal(bArr, 0);
        try {
            byte[] a2 = a(bArr);
            return this.a.processBlock(a2, 0, a2.length);
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to encode signature: ");
            sb.append(e2.getMessage());
            throw new CryptoException(sb.toString(), e2);
        }
    }

    public String getAlgorithmName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c.getAlgorithmName());
        sb.append("withRSA");
        return sb.toString();
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.d = z;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        } else if (z || !asymmetricKeyParameter.isPrivate()) {
            reset();
            this.a.init(z, cipherParameters);
        } else {
            throw new IllegalArgumentException("verification requires public key");
        }
    }

    public void reset() {
        this.c.reset();
    }

    public void update(byte b2) {
        this.c.update(b2);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.c.update(bArr, i, i2);
    }

    public boolean verifySignature(byte[] bArr) {
        if (this.d) {
            throw new IllegalStateException("RSADigestSigner not initialised for verification");
        }
        byte[] bArr2 = new byte[this.c.getDigestSize()];
        boolean z = false;
        this.c.doFinal(bArr2, 0);
        try {
            byte[] processBlock = this.a.processBlock(bArr, 0, bArr.length);
            byte[] a2 = a(bArr2);
            if (processBlock.length == a2.length) {
                return Arrays.constantTimeAreEqual(processBlock, a2);
            }
            if (processBlock.length == a2.length - 2) {
                int length = (processBlock.length - bArr2.length) - 2;
                int length2 = (a2.length - bArr2.length) - 2;
                a2[1] = (byte) (a2[1] - 2);
                a2[3] = (byte) (a2[3] - 2);
                byte b2 = 0;
                for (int i = 0; i < bArr2.length; i++) {
                    b2 |= processBlock[length + i] ^ a2[length2 + i];
                }
                for (int i2 = 0; i2 < length; i2++) {
                    b2 |= processBlock[i2] ^ a2[i2];
                }
                if (b2 == 0) {
                    z = true;
                }
            }
            return z;
        } catch (Exception unused) {
        }
    }
}
