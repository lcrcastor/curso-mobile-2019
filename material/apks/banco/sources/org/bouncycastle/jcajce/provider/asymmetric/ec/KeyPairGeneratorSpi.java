package org.bouncycastle.jcajce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.Integers;

public abstract class KeyPairGeneratorSpi extends KeyPairGenerator {

    public static class EC extends KeyPairGeneratorSpi {
        private static Hashtable j = new Hashtable();
        ECKeyGenerationParameters a;
        ECKeyPairGenerator b;
        Object c;
        int d;
        int e;
        SecureRandom f;
        boolean g;
        String h;
        ProviderConfiguration i;

        static {
            j.put(Integers.valueOf(192), new ECGenParameterSpec("prime192v1"));
            j.put(Integers.valueOf(239), new ECGenParameterSpec("prime239v1"));
            j.put(Integers.valueOf(256), new ECGenParameterSpec("prime256v1"));
            j.put(Integers.valueOf(224), new ECGenParameterSpec("P-224"));
            j.put(Integers.valueOf(384), new ECGenParameterSpec("P-384"));
            j.put(Integers.valueOf(521), new ECGenParameterSpec("P-521"));
        }

        public EC() {
            super("EC");
            this.b = new ECKeyPairGenerator();
            this.c = null;
            this.d = 239;
            this.e = 50;
            this.f = new SecureRandom();
            this.g = false;
            this.h = "EC";
            this.i = BouncyCastleProvider.CONFIGURATION;
        }

        public EC(String str, ProviderConfiguration providerConfiguration) {
            super(str);
            this.b = new ECKeyPairGenerator();
            this.c = null;
            this.d = 239;
            this.e = 50;
            this.f = new SecureRandom();
            this.g = false;
            this.h = str;
            this.i = providerConfiguration;
        }

        /* access modifiers changed from: protected */
        public ECKeyGenerationParameters createKeyGenParamsBC(ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            return new ECKeyGenerationParameters(new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN()), secureRandom);
        }

        /* access modifiers changed from: protected */
        public ECKeyGenerationParameters createKeyGenParamsJCE(java.security.spec.ECParameterSpec eCParameterSpec, SecureRandom secureRandom) {
            ECCurve convertCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
            return new ECKeyGenerationParameters(new ECDomainParameters(convertCurve, EC5Util.convertPoint(convertCurve, eCParameterSpec.getGenerator(), false), eCParameterSpec.getOrder(), BigInteger.valueOf((long) eCParameterSpec.getCofactor())), secureRandom);
        }

        /* access modifiers changed from: protected */
        public ECNamedCurveSpec createNamedCurveSpec(String str) {
            X9ECParameters byName = ECNamedCurveTable.getByName(str);
            if (byName == null) {
                try {
                    byName = ECNamedCurveTable.getByOID(new ASN1ObjectIdentifier(str));
                    if (byName == null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unknown curve OID: ");
                        sb.append(str);
                        throw new InvalidAlgorithmParameterException(sb.toString());
                    }
                } catch (IllegalArgumentException unused) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("unknown curve name: ");
                    sb2.append(str);
                    throw new InvalidAlgorithmParameterException(sb2.toString());
                }
            }
            ECNamedCurveSpec eCNamedCurveSpec = new ECNamedCurveSpec(str, byName.getCurve(), byName.getG(), byName.getN(), byName.getH(), null);
            return eCNamedCurveSpec;
        }

        public KeyPair generateKeyPair() {
            if (!this.g) {
                initialize(this.d, new SecureRandom());
            }
            AsymmetricCipherKeyPair generateKeyPair = this.b.generateKeyPair();
            ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) generateKeyPair.getPublic();
            ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) generateKeyPair.getPrivate();
            if (this.c instanceof ECParameterSpec) {
                ECParameterSpec eCParameterSpec = (ECParameterSpec) this.c;
                BCECPublicKey bCECPublicKey = new BCECPublicKey(this.h, eCPublicKeyParameters, eCParameterSpec, this.i);
                BCECPrivateKey bCECPrivateKey = new BCECPrivateKey(this.h, eCPrivateKeyParameters, bCECPublicKey, eCParameterSpec, this.i);
                return new KeyPair(bCECPublicKey, bCECPrivateKey);
            } else if (this.c == null) {
                return new KeyPair(new BCECPublicKey(this.h, eCPublicKeyParameters, this.i), new BCECPrivateKey(this.h, eCPrivateKeyParameters, this.i));
            } else {
                java.security.spec.ECParameterSpec eCParameterSpec2 = (java.security.spec.ECParameterSpec) this.c;
                BCECPublicKey bCECPublicKey2 = new BCECPublicKey(this.h, eCPublicKeyParameters, eCParameterSpec2, this.i);
                BCECPrivateKey bCECPrivateKey2 = new BCECPrivateKey(this.h, eCPrivateKeyParameters, bCECPublicKey2, eCParameterSpec2, this.i);
                return new KeyPair(bCECPublicKey2, bCECPrivateKey2);
            }
        }

        public void initialize(int i2, SecureRandom secureRandom) {
            this.d = i2;
            this.f = secureRandom;
            ECGenParameterSpec eCGenParameterSpec = (ECGenParameterSpec) j.get(Integers.valueOf(i2));
            if (eCGenParameterSpec == null) {
                throw new InvalidParameterException("unknown key size.");
            }
            try {
                initialize((AlgorithmParameterSpec) eCGenParameterSpec, secureRandom);
            } catch (InvalidAlgorithmParameterException unused) {
                throw new InvalidParameterException("key size not configurable.");
            }
        }

        public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            String name;
            ECKeyGenerationParameters createKeyGenParamsJCE;
            ECParameterSpec eCParameterSpec;
            if (algorithmParameterSpec == null) {
                eCParameterSpec = this.i.getEcImplicitlyCa();
                if (eCParameterSpec == null) {
                    throw new InvalidAlgorithmParameterException("null parameter passed but no implicitCA set");
                }
                this.c = null;
            } else if (algorithmParameterSpec instanceof ECParameterSpec) {
                this.c = algorithmParameterSpec;
                eCParameterSpec = (ECParameterSpec) algorithmParameterSpec;
            } else if (algorithmParameterSpec instanceof java.security.spec.ECParameterSpec) {
                this.c = algorithmParameterSpec;
                createKeyGenParamsJCE = createKeyGenParamsJCE((java.security.spec.ECParameterSpec) algorithmParameterSpec, secureRandom);
                this.a = createKeyGenParamsJCE;
                this.b.init(this.a);
                this.g = true;
            } else {
                if (algorithmParameterSpec instanceof ECGenParameterSpec) {
                    name = ((ECGenParameterSpec) algorithmParameterSpec).getName();
                } else if (algorithmParameterSpec instanceof ECNamedCurveGenParameterSpec) {
                    name = ((ECNamedCurveGenParameterSpec) algorithmParameterSpec).getName();
                } else {
                    throw new InvalidAlgorithmParameterException("parameter object not a ECParameterSpec");
                }
                initializeNamedCurve(name, secureRandom);
                this.b.init(this.a);
                this.g = true;
            }
            createKeyGenParamsJCE = createKeyGenParamsBC(eCParameterSpec, secureRandom);
            this.a = createKeyGenParamsJCE;
            this.b.init(this.a);
            this.g = true;
        }

        /* access modifiers changed from: protected */
        public void initializeNamedCurve(String str, SecureRandom secureRandom) {
            ECNamedCurveSpec createNamedCurveSpec = createNamedCurveSpec(str);
            this.c = createNamedCurveSpec;
            this.a = createKeyGenParamsJCE(createNamedCurveSpec, secureRandom);
        }
    }

    public static class ECDH extends EC {
        public ECDH() {
            super("ECDH", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDHC extends EC {
        public ECDHC() {
            super("ECDHC", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECDSA extends EC {
        public ECDSA() {
            super("ECDSA", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public static class ECMQV extends EC {
        public ECMQV() {
            super("ECMQV", BouncyCastleProvider.CONFIGURATION);
        }
    }

    public KeyPairGeneratorSpi(String str) {
        super(str);
    }
}
