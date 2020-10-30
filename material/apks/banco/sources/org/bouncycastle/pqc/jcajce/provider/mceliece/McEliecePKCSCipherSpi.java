package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.mceliece.McElieceKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePKCSCipher;
import org.bouncycastle.pqc.jcajce.provider.util.AsymmetricBlockCipher;

public class McEliecePKCSCipherSpi extends AsymmetricBlockCipher implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest a;
    private McEliecePKCSCipher b;

    public static class McEliecePKCS extends McEliecePKCSCipherSpi {
        public McEliecePKCS() {
            super(new SHA1Digest(), new McEliecePKCSCipher());
        }
    }

    public static class McEliecePKCS224 extends McEliecePKCSCipherSpi {
        public McEliecePKCS224() {
            super(new SHA224Digest(), new McEliecePKCSCipher());
        }
    }

    public static class McEliecePKCS256 extends McEliecePKCSCipherSpi {
        public McEliecePKCS256() {
            super(new SHA256Digest(), new McEliecePKCSCipher());
        }
    }

    public static class McEliecePKCS384 extends McEliecePKCSCipherSpi {
        public McEliecePKCS384() {
            super(new SHA384Digest(), new McEliecePKCSCipher());
        }
    }

    public static class McEliecePKCS512 extends McEliecePKCSCipherSpi {
        public McEliecePKCS512() {
            super(new SHA512Digest(), new McEliecePKCSCipher());
        }
    }

    public McEliecePKCSCipherSpi(Digest digest, McEliecePKCSCipher mcEliecePKCSCipher) {
        this.a = digest;
        this.b = mcEliecePKCSCipher;
    }

    public int getKeySize(Key key) {
        return this.b.getKeySize((McElieceKeyParameters) (key instanceof PublicKey ? McElieceKeysToParams.generatePublicKeyParameter((PublicKey) key) : McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey) key)));
    }

    public String getName() {
        return "McEliecePKCS";
    }

    /* access modifiers changed from: protected */
    public void initCipherDecrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        AsymmetricKeyParameter generatePrivateKeyParameter = McElieceKeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        this.a.reset();
        this.b.init(false, generatePrivateKeyParameter);
        this.maxPlainTextSize = this.b.maxPlainTextSize;
        this.cipherTextSize = this.b.cipherTextSize;
    }

    /* access modifiers changed from: protected */
    public void initCipherEncrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        ParametersWithRandom parametersWithRandom = new ParametersWithRandom(McElieceKeysToParams.generatePublicKeyParameter((PublicKey) key), secureRandom);
        this.a.reset();
        this.b.init(true, parametersWithRandom);
        this.maxPlainTextSize = this.b.maxPlainTextSize;
        this.cipherTextSize = this.b.cipherTextSize;
    }

    /* access modifiers changed from: protected */
    public byte[] messageDecrypt(byte[] bArr) {
        try {
            return this.b.messageDecrypt(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] messageEncrypt(byte[] bArr) {
        try {
            return this.b.messageEncrypt(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
