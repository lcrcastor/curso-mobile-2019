package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ua.DSTU4145NamedCurves;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.DSTU4145KeyPairGenerator;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;

public class KeyPairGeneratorSpi extends KeyPairGenerator {
    Object a = null;
    ECKeyPairGenerator b = new DSTU4145KeyPairGenerator();
    String c = "DSTU4145";
    ECKeyGenerationParameters d;
    SecureRandom e = null;
    boolean f = false;

    public KeyPairGeneratorSpi() {
        super("DSTU4145");
    }

    public KeyPair generateKeyPair() {
        if (!this.f) {
            throw new IllegalStateException("DSTU Key Pair Generator not initialised");
        }
        AsymmetricCipherKeyPair generateKeyPair = this.b.generateKeyPair();
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) generateKeyPair.getPublic();
        ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) generateKeyPair.getPrivate();
        if (this.a instanceof ECParameterSpec) {
            ECParameterSpec eCParameterSpec = (ECParameterSpec) this.a;
            BCDSTU4145PublicKey bCDSTU4145PublicKey = new BCDSTU4145PublicKey(this.c, eCPublicKeyParameters, eCParameterSpec);
            return new KeyPair(bCDSTU4145PublicKey, new BCDSTU4145PrivateKey(this.c, eCPrivateKeyParameters, bCDSTU4145PublicKey, eCParameterSpec));
        } else if (this.a == null) {
            return new KeyPair(new BCDSTU4145PublicKey(this.c, eCPublicKeyParameters), new BCDSTU4145PrivateKey(this.c, eCPrivateKeyParameters));
        } else {
            java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) this.a;
            BCDSTU4145PublicKey bCDSTU4145PublicKey2 = new BCDSTU4145PublicKey(this.c, eCPublicKeyParameters, eCParameterSpec2);
            return new KeyPair(bCDSTU4145PublicKey2, new BCDSTU4145PrivateKey(this.c, eCPrivateKeyParameters, bCDSTU4145PublicKey2, eCParameterSpec2));
        }
    }

    public void initialize(int i, SecureRandom secureRandom) {
        this.e = secureRandom;
        if (this.a != null) {
            try {
                initialize((AlgorithmParameterSpec) (ECGenParameterSpec) this.a, secureRandom);
            } catch (InvalidAlgorithmParameterException unused) {
                throw new InvalidParameterException("key size not configurable.");
            }
        } else {
            throw new InvalidParameterException("unknown key size.");
        }
    }

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        ECKeyGenerationParameters eCKeyGenerationParameters;
        ECKeyGenerationParameters eCKeyGenerationParameters2;
        if (algorithmParameterSpec instanceof ECParameterSpec) {
            ECParameterSpec eCParameterSpec = (ECParameterSpec) algorithmParameterSpec;
            this.a = algorithmParameterSpec;
            eCKeyGenerationParameters2 = new ECKeyGenerationParameters(new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN()), secureRandom);
        } else {
            if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
                java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) algorithmParameterSpec;
                this.a = algorithmParameterSpec;
                ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec2.getCurve());
                eCKeyGenerationParameters = new ECKeyGenerationParameters(new ECDomainParameters(convertCurve, EC5Util.convertPoint(convertCurve, eCParameterSpec2.getGenerator(), false), eCParameterSpec2.getOrder(), BigInteger.valueOf((long) eCParameterSpec2.getCofactor())), secureRandom);
            } else {
                boolean z = algorithmParameterSpec instanceof ECGenParameterSpec;
                if (z || (algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec)) {
                    String name = z ? ((ECGenParameterSpec) algorithmParameterSpec).getName() : ((ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName();
                    ECDomainParameters byOID = DSTU4145NamedCurves.getByOID(new ASN1ObjectIdentifier(name));
                    if (byOID == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown curve name: ");
                        sb.append(name);
                        throw new InvalidAlgorithmParameterException(sb.toString());
                    }
                    ECNamedCurveSpec eCNamedCurveSpec = new ECNamedCurveSpec(name, byOID.getCurve(), byOID.getG(), byOID.getN(), byOID.getH(), byOID.getSeed());
                    this.a = eCNamedCurveSpec;
                    java.security.spec.ECParameterSpec eCParameterSpec3 = (java.security.spec.ECParameterSpec) this.a;
                    ECCurve convertCurve2 = EC5Util.convertCurve(eCParameterSpec3.getCurve());
                    eCKeyGenerationParameters = new ECKeyGenerationParameters(new ECDomainParameters(convertCurve2, EC5Util.convertPoint(convertCurve2, eCParameterSpec3.getGenerator(), false), eCParameterSpec3.getOrder(), BigInteger.valueOf((long) eCParameterSpec3.getCofactor())), secureRandom);
                } else if (algorithmParameterSpec == null && BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() != null) {
                    ECParameterSpec ecImplicitlyCa = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
                    this.a = algorithmParameterSpec;
                    eCKeyGenerationParameters2 = new ECKeyGenerationParameters(new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN()), secureRandom);
                } else if (algorithmParameterSpec == null && BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa() == null) {
                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("parameter object not a ECParameterSpec: ");
                    sb2.append(algorithmParameterSpec.getClass().getName());
                    throw new InvalidAlgorithmParameterException(sb2.toString());
                }
            }
            this.d = eCKeyGenerationParameters;
            this.b.init(this.d);
            this.f = true;
        }
        this.d = eCKeyGenerationParameters2;
        this.b.init(this.d);
        this.f = true;
    }
}
