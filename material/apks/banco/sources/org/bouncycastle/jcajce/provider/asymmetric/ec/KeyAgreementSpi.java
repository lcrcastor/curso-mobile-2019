package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9IntegerConverter;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.agreement.ECDHCBasicAgreement;
import org.bouncycastle.crypto.agreement.ECMQVBasicAgreement;
import org.bouncycastle.crypto.agreement.kdf.DHKDFParameters;
import org.bouncycastle.crypto.agreement.kdf.ECDHKEKGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.MQVPrivateParameters;
import org.bouncycastle.crypto.params.MQVPublicParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.interfaces.MQVPrivateKey;
import org.bouncycastle.jce.interfaces.MQVPublicKey;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;

public class KeyAgreementSpi extends javax.crypto.KeyAgreementSpi {
    private static final X9IntegerConverter a = new X9IntegerConverter();
    private static final Hashtable b = new Hashtable();
    private static final Hashtable c = new Hashtable();
    private static final Hashtable d = new Hashtable();
    private String e;
    private BigInteger f;
    private ECDomainParameters g;
    private BasicAgreement h;
    private DerivationFunction i;

    public static class DH extends KeyAgreementSpi {
        public DH() {
            super("ECDH", new ECDHBasicAgreement(), null);
        }
    }

    public static class DHC extends KeyAgreementSpi {
        public DHC() {
            super("ECDHC", new ECDHCBasicAgreement(), null);
        }
    }

    public static class DHwithSHA1KDF extends KeyAgreementSpi {
        public DHwithSHA1KDF() {
            super("ECDHwithSHA1KDF", new ECDHBasicAgreement(), new ECDHKEKGenerator(new SHA1Digest()));
        }
    }

    public static class MQV extends KeyAgreementSpi {
        public MQV() {
            super("ECMQV", new ECMQVBasicAgreement(), null);
        }
    }

    public static class MQVwithSHA1KDF extends KeyAgreementSpi {
        public MQVwithSHA1KDF() {
            super("ECMQVwithSHA1KDF", new ECMQVBasicAgreement(), new ECDHKEKGenerator(new SHA1Digest()));
        }
    }

    static {
        Integer valueOf = Integers.valueOf(64);
        Integer valueOf2 = Integers.valueOf(128);
        Integer valueOf3 = Integers.valueOf(192);
        Integer valueOf4 = Integers.valueOf(256);
        b.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), valueOf2);
        b.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), valueOf3);
        b.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), valueOf4);
        b.put(NISTObjectIdentifiers.id_aes128_wrap.getId(), valueOf2);
        b.put(NISTObjectIdentifiers.id_aes192_wrap.getId(), valueOf3);
        b.put(NISTObjectIdentifiers.id_aes256_wrap.getId(), valueOf4);
        b.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), valueOf3);
        b.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), valueOf3);
        b.put(OIWObjectIdentifiers.desCBC.getId(), valueOf);
        c.put("DESEDE", PKCSObjectIdentifiers.des_EDE3_CBC);
        c.put("AES", NISTObjectIdentifiers.id_aes256_CBC);
        c.put("DES", OIWObjectIdentifiers.desCBC);
        d.put("DES", "DES");
        d.put("DESEDE", "DES");
        d.put(OIWObjectIdentifiers.desCBC.getId(), "DES");
        d.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), "DES");
        d.put(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId(), "DES");
    }

    protected KeyAgreementSpi(String str, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        this.e = str;
        this.h = basicAgreement;
        this.i = derivationFunction;
    }

    private static String a(Class cls) {
        String name = cls.getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    private void a(Key key) {
        CipherParameters cipherParameters;
        ECDomainParameters eCDomainParameters;
        if (this.h instanceof ECMQVBasicAgreement) {
            if (!(key instanceof MQVPrivateKey)) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.e);
                sb.append(" key agreement requires ");
                sb.append(a(MQVPrivateKey.class));
                sb.append(" for initialisation");
                throw new InvalidKeyException(sb.toString());
            }
            MQVPrivateKey mQVPrivateKey = (MQVPrivateKey) key;
            ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(mQVPrivateKey.getStaticPrivateKey());
            ECPrivateKeyParameters eCPrivateKeyParameters2 = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter(mQVPrivateKey.getEphemeralPrivateKey());
            ECPublicKeyParameters eCPublicKeyParameters = null;
            if (mQVPrivateKey.getEphemeralPublicKey() != null) {
                eCPublicKeyParameters = (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(mQVPrivateKey.getEphemeralPublicKey());
            }
            CipherParameters mQVPrivateParameters = new MQVPrivateParameters(eCPrivateKeyParameters, eCPrivateKeyParameters2, eCPublicKeyParameters);
            eCDomainParameters = eCPrivateKeyParameters.getParameters();
            cipherParameters = mQVPrivateParameters;
        } else if (!(key instanceof PrivateKey)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.e);
            sb2.append(" key agreement requires ");
            sb2.append(a(ECPrivateKey.class));
            sb2.append(" for initialisation");
            throw new InvalidKeyException(sb2.toString());
        } else {
            ECPrivateKeyParameters eCPrivateKeyParameters3 = (ECPrivateKeyParameters) ECUtil.generatePrivateKeyParameter((PrivateKey) key);
            eCDomainParameters = eCPrivateKeyParameters3.getParameters();
            cipherParameters = eCPrivateKeyParameters3;
        }
        this.g = eCDomainParameters;
        this.h.init(cipherParameters);
    }

    private byte[] a(BigInteger bigInteger) {
        return a.integerToBytes(bigInteger, a.getByteLength(this.g.getCurve()));
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean z) {
        CipherParameters cipherParameters;
        if (this.g == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.e);
            sb.append(" not initialised.");
            throw new IllegalStateException(sb.toString());
        } else if (!z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.e);
            sb2.append(" can only be between two parties.");
            throw new IllegalStateException(sb2.toString());
        } else {
            if (this.h instanceof ECMQVBasicAgreement) {
                if (!(key instanceof MQVPublicKey)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.e);
                    sb3.append(" key agreement requires ");
                    sb3.append(a(MQVPublicKey.class));
                    sb3.append(" for doPhase");
                    throw new InvalidKeyException(sb3.toString());
                }
                MQVPublicKey mQVPublicKey = (MQVPublicKey) key;
                cipherParameters = new MQVPublicParameters((ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(mQVPublicKey.getStaticKey()), (ECPublicKeyParameters) ECUtil.generatePublicKeyParameter(mQVPublicKey.getEphemeralKey()));
            } else if (!(key instanceof PublicKey)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(this.e);
                sb4.append(" key agreement requires ");
                sb4.append(a(ECPublicKey.class));
                sb4.append(" for doPhase");
                throw new InvalidKeyException(sb4.toString());
            } else {
                cipherParameters = ECUtil.generatePublicKeyParameter((PublicKey) key);
            }
            this.f = this.h.calculateAgreement(cipherParameters);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] bArr, int i2) {
        byte[] engineGenerateSecret = engineGenerateSecret();
        if (bArr.length - i2 < engineGenerateSecret.length) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.e);
            sb.append(" key agreement: need ");
            sb.append(engineGenerateSecret.length);
            sb.append(" bytes");
            throw new ShortBufferException(sb.toString());
        }
        System.arraycopy(engineGenerateSecret, 0, bArr, i2, engineGenerateSecret.length);
        return engineGenerateSecret.length;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String str) {
        byte[] a2 = a(this.f);
        String upperCase = Strings.toUpperCase(str);
        String id2 = c.containsKey(upperCase) ? ((ASN1ObjectIdentifier) c.get(upperCase)).getId() : str;
        if (this.i != null) {
            if (!b.containsKey(id2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("unknown algorithm encountered: ");
                sb.append(str);
                throw new NoSuchAlgorithmException(sb.toString());
            }
            int intValue = ((Integer) b.get(id2)).intValue();
            DHKDFParameters dHKDFParameters = new DHKDFParameters(new ASN1ObjectIdentifier(id2), intValue, a2);
            a2 = new byte[(intValue / 8)];
            this.i.init(dHKDFParameters);
            this.i.generateBytes(a2, 0, a2.length);
        } else if (b.containsKey(id2)) {
            byte[] bArr = new byte[(((Integer) b.get(id2)).intValue() / 8)];
            System.arraycopy(a2, 0, bArr, 0, bArr.length);
            a2 = bArr;
        }
        if (d.containsKey(id2)) {
            DESParameters.setOddParity(a2);
        }
        return new SecretKeySpec(a2, str);
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() {
        if (this.i == null) {
            return a(this.f);
        }
        throw new UnsupportedOperationException("KDF can only be used when algorithm is known");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom secureRandom) {
        a(key);
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        a(key);
    }
}
