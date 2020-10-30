package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.jcajce.provider.util.DigestFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PSSSignatureSpi extends SignatureSpi {
    private AlgorithmParameters a;
    private PSSParameterSpec b;
    private PSSParameterSpec c;
    private AsymmetricBlockCipher d;
    private Digest e;
    private Digest f;
    private int g;
    private byte h;
    private boolean i;
    private PSSSigner j;

    class NullPssDigest implements Digest {
        private ByteArrayOutputStream b = new ByteArrayOutputStream();
        private Digest c;
        private boolean d = true;

        public NullPssDigest(Digest digest) {
            this.c = digest;
        }

        public int doFinal(byte[] bArr, int i) {
            byte[] byteArray = this.b.toByteArray();
            if (this.d) {
                System.arraycopy(byteArray, 0, bArr, i, byteArray.length);
            } else {
                this.c.update(byteArray, 0, byteArray.length);
                this.c.doFinal(bArr, i);
            }
            reset();
            this.d = !this.d;
            return byteArray.length;
        }

        public String getAlgorithmName() {
            return "NULL";
        }

        public int getDigestSize() {
            return this.c.getDigestSize();
        }

        public void reset() {
            this.b.reset();
            this.c.reset();
        }

        public void update(byte b2) {
            this.b.write(b2);
        }

        public void update(byte[] bArr, int i, int i2) {
            this.b.write(bArr, i, i2);
        }
    }

    public static class PSSwithRSA extends PSSSignatureSpi {
        public PSSwithRSA() {
            super(new RSABlindedEngine(), null);
        }
    }

    public static class SHA1withRSA extends PSSSignatureSpi {
        public SHA1withRSA() {
            super(new RSABlindedEngine(), PSSParameterSpec.DEFAULT);
        }
    }

    public static class SHA224withRSA extends PSSSignatureSpi {
        public SHA224withRSA() {
            RSABlindedEngine rSABlindedEngine = new RSABlindedEngine();
            PSSParameterSpec pSSParameterSpec = new PSSParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), 28, 1);
            super(rSABlindedEngine, pSSParameterSpec);
        }
    }

    public static class SHA256withRSA extends PSSSignatureSpi {
        public SHA256withRSA() {
            RSABlindedEngine rSABlindedEngine = new RSABlindedEngine();
            PSSParameterSpec pSSParameterSpec = new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1);
            super(rSABlindedEngine, pSSParameterSpec);
        }
    }

    public static class SHA384withRSA extends PSSSignatureSpi {
        public SHA384withRSA() {
            RSABlindedEngine rSABlindedEngine = new RSABlindedEngine();
            PSSParameterSpec pSSParameterSpec = new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1);
            super(rSABlindedEngine, pSSParameterSpec);
        }
    }

    public static class SHA512withRSA extends PSSSignatureSpi {
        public SHA512withRSA() {
            RSABlindedEngine rSABlindedEngine = new RSABlindedEngine();
            PSSParameterSpec pSSParameterSpec = new PSSParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-512"), 64, 1);
            super(rSABlindedEngine, pSSParameterSpec);
        }
    }

    public static class nonePSS extends PSSSignatureSpi {
        public nonePSS() {
            super(new RSABlindedEngine(), null, true);
        }
    }

    protected PSSSignatureSpi(AsymmetricBlockCipher asymmetricBlockCipher, PSSParameterSpec pSSParameterSpec) {
        this(asymmetricBlockCipher, pSSParameterSpec, false);
    }

    protected PSSSignatureSpi(AsymmetricBlockCipher asymmetricBlockCipher, PSSParameterSpec pSSParameterSpec, boolean z) {
        this.d = asymmetricBlockCipher;
        this.c = pSSParameterSpec;
        if (pSSParameterSpec == null) {
            this.b = PSSParameterSpec.DEFAULT;
        } else {
            this.b = pSSParameterSpec;
        }
        this.f = DigestFactory.getDigest(this.b.getDigestAlgorithm());
        this.g = this.b.getSaltLength();
        this.h = a(this.b.getTrailerField());
        this.i = z;
        a();
    }

    private byte a(int i2) {
        if (i2 == 1) {
            return PSSSigner.TRAILER_IMPLICIT;
        }
        throw new IllegalArgumentException("unknown trailer field");
    }

    private void a() {
        this.e = this.i ? new NullPssDigest(this.f) : this.f;
    }

    /* access modifiers changed from: protected */
    public Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.a == null && this.b != null) {
            try {
                this.a = AlgorithmParameters.getInstance("PSS", BouncyCastleProvider.PROVIDER_NAME);
                this.a.init(this.b);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey) {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.d, this.e, this.f, this.g, this.h);
        this.j = pSSSigner;
        this.j.init(true, RSAUtil.a((RSAPrivateKey) privateKey));
    }

    /* access modifiers changed from: protected */
    public void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.d, this.e, this.f, this.g, this.h);
        this.j = pSSSigner;
        this.j.init(true, new ParametersWithRandom(RSAUtil.a((RSAPrivateKey) privateKey), secureRandom));
    }

    /* access modifiers changed from: protected */
    public void engineInitVerify(PublicKey publicKey) {
        if (!(publicKey instanceof RSAPublicKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPublicKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.d, this.e, this.f, this.g, this.h);
        this.j = pSSSigner;
        this.j.init(false, RSAUtil.a((RSAPublicKey) publicKey));
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    /* access modifiers changed from: protected */
    public void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof PSSParameterSpec) {
            PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
            if (this.c != null && !DigestFactory.isSameDigest(this.c.getDigestAlgorithm(), pSSParameterSpec.getDigestAlgorithm())) {
                StringBuilder sb = new StringBuilder();
                sb.append("parameter must be using ");
                sb.append(this.c.getDigestAlgorithm());
                throw new InvalidParameterException(sb.toString());
            } else if (!pSSParameterSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !pSSParameterSpec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
                throw new InvalidParameterException("unknown mask generation function specified");
            } else if (!(pSSParameterSpec.getMGFParameters() instanceof MGF1ParameterSpec)) {
                throw new InvalidParameterException("unkown MGF parameters");
            } else {
                MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) pSSParameterSpec.getMGFParameters();
                if (!DigestFactory.isSameDigest(mGF1ParameterSpec.getDigestAlgorithm(), pSSParameterSpec.getDigestAlgorithm())) {
                    throw new InvalidParameterException("digest algorithm for MGF should be the same as for PSS parameters.");
                }
                Digest digest = DigestFactory.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
                if (digest == null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("no match on MGF digest algorithm: ");
                    sb2.append(mGF1ParameterSpec.getDigestAlgorithm());
                    throw new InvalidParameterException(sb2.toString());
                }
                this.a = null;
                this.b = pSSParameterSpec;
                this.f = digest;
                this.g = this.b.getSaltLength();
                this.h = a(this.b.getTrailerField());
                a();
            }
        } else {
            throw new InvalidParameterException("Only PSSParameterSpec supported");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineSign() {
        try {
            return this.j.generateSignature();
        } catch (CryptoException e2) {
            throw new SignatureException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte b2) {
        this.j.update(b2);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        this.j.update(bArr, i2, i3);
    }

    /* access modifiers changed from: protected */
    public boolean engineVerify(byte[] bArr) {
        return this.j.verifySignature(bArr);
    }
}
