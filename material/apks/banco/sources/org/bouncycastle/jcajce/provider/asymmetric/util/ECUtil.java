package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X962NamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

public class ECUtil {
    static int[] a(int[] iArr) {
        int[] iArr2 = new int[3];
        if (iArr.length == 1) {
            iArr2[0] = iArr[0];
            return iArr2;
        } else if (iArr.length != 3) {
            throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
        } else if (iArr[0] < iArr[1] && iArr[0] < iArr[2]) {
            iArr2[0] = iArr[0];
            if (iArr[1] < iArr[2]) {
                iArr2[1] = iArr[1];
                iArr2[2] = iArr[2];
                return iArr2;
            }
            iArr2[1] = iArr[2];
            iArr2[2] = iArr[1];
            return iArr2;
        } else if (iArr[1] < iArr[2]) {
            iArr2[0] = iArr[1];
            if (iArr[0] < iArr[2]) {
                iArr2[1] = iArr[0];
                iArr2[2] = iArr[2];
                return iArr2;
            }
            iArr2[1] = iArr[2];
            iArr2[2] = iArr[0];
            return iArr2;
        } else {
            iArr2[0] = iArr[2];
            if (iArr[0] < iArr[1]) {
                iArr2[1] = iArr[0];
                iArr2[2] = iArr[1];
                return iArr2;
            }
            iArr2[1] = iArr[1];
            iArr2[2] = iArr[0];
            return iArr2;
        }
    }

    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (privateKey instanceof ECPrivateKey) {
            ECPrivateKey eCPrivateKey = (ECPrivateKey) privateKey;
            ECParameterSpec parameters = eCPrivateKey.getParameters();
            if (parameters == null) {
                parameters = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
            }
            BigInteger d = eCPrivateKey.getD();
            ECDomainParameters eCDomainParameters = new ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed());
            return new ECPrivateKeyParameters(d, eCDomainParameters);
        } else if (privateKey instanceof java.security.interfaces.ECPrivateKey) {
            java.security.interfaces.ECPrivateKey eCPrivateKey2 = (java.security.interfaces.ECPrivateKey) privateKey;
            ECParameterSpec convertSpec = EC5Util.convertSpec(eCPrivateKey2.getParams(), false);
            BigInteger s = eCPrivateKey2.getS();
            ECDomainParameters eCDomainParameters2 = new ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed());
            return new ECPrivateKeyParameters(s, eCDomainParameters2);
        } else {
            try {
                byte[] encoded = privateKey.getEncoded();
                if (encoded == null) {
                    throw new InvalidKeyException("no encoding for EC private key");
                }
                PrivateKey privateKey2 = BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.getInstance(encoded));
                if (privateKey2 instanceof java.security.interfaces.ECPrivateKey) {
                    return generatePrivateKeyParameter(privateKey2);
                }
                throw new InvalidKeyException("can't identify EC private key.");
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("cannot identify EC private key: ");
                sb.append(e.toString());
                throw new InvalidKeyException(sb.toString());
            }
        }
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof ECPublicKey) {
            ECPublicKey eCPublicKey = (ECPublicKey) publicKey;
            ECParameterSpec parameters = eCPublicKey.getParameters();
            if (parameters == null) {
                ECParameterSpec ecImplicitlyCa = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
                ECPoint engineGetQ = ((BCECPublicKey) eCPublicKey).engineGetQ();
                ECDomainParameters eCDomainParameters = new ECDomainParameters(ecImplicitlyCa.getCurve(), ecImplicitlyCa.getG(), ecImplicitlyCa.getN(), ecImplicitlyCa.getH(), ecImplicitlyCa.getSeed());
                return new ECPublicKeyParameters(engineGetQ, eCDomainParameters);
            }
            ECPoint q = eCPublicKey.getQ();
            ECDomainParameters eCDomainParameters2 = new ECDomainParameters(parameters.getCurve(), parameters.getG(), parameters.getN(), parameters.getH(), parameters.getSeed());
            return new ECPublicKeyParameters(q, eCDomainParameters2);
        } else if (publicKey instanceof java.security.interfaces.ECPublicKey) {
            java.security.interfaces.ECPublicKey eCPublicKey2 = (java.security.interfaces.ECPublicKey) publicKey;
            ECParameterSpec convertSpec = EC5Util.convertSpec(eCPublicKey2.getParams(), false);
            ECPoint convertPoint = EC5Util.convertPoint(eCPublicKey2.getParams(), eCPublicKey2.getW(), false);
            ECDomainParameters eCDomainParameters3 = new ECDomainParameters(convertSpec.getCurve(), convertSpec.getG(), convertSpec.getN(), convertSpec.getH(), convertSpec.getSeed());
            return new ECPublicKeyParameters(convertPoint, eCDomainParameters3);
        } else {
            try {
                byte[] encoded = publicKey.getEncoded();
                if (encoded == null) {
                    throw new InvalidKeyException("no encoding for EC public key");
                }
                PublicKey publicKey2 = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(encoded));
                if (publicKey2 instanceof java.security.interfaces.ECPublicKey) {
                    return generatePublicKeyParameter(publicKey2);
                }
                throw new InvalidKeyException("cannot identify EC public key.");
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("cannot identify EC public key: ");
                sb.append(e.toString());
                throw new InvalidKeyException(sb.toString());
            }
        }
    }

    public static String getCurveName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String name = X962NamedCurves.getName(aSN1ObjectIdentifier);
        if (name != null) {
            return name;
        }
        String name2 = SECNamedCurves.getName(aSN1ObjectIdentifier);
        if (name2 == null) {
            name2 = NISTNamedCurves.getName(aSN1ObjectIdentifier);
        }
        if (name2 == null) {
            name2 = TeleTrusTNamedCurves.getName(aSN1ObjectIdentifier);
        }
        return name2 == null ? ECGOST3410NamedCurves.getName(aSN1ObjectIdentifier) : name2;
    }

    public static X9ECParameters getNamedCurveByOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParameters byOID = CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
        if (byOID != null) {
            return byOID;
        }
        X9ECParameters byOID2 = X962NamedCurves.getByOID(aSN1ObjectIdentifier);
        if (byOID2 == null) {
            byOID2 = SECNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        if (byOID2 == null) {
            byOID2 = NISTNamedCurves.getByOID(aSN1ObjectIdentifier);
        }
        return byOID2 == null ? TeleTrusTNamedCurves.getByOID(aSN1ObjectIdentifier) : byOID2;
    }

    public static ASN1ObjectIdentifier getNamedCurveOid(String str) {
        ASN1ObjectIdentifier oid = X962NamedCurves.getOID(str);
        if (oid != null) {
            return oid;
        }
        ASN1ObjectIdentifier oid2 = SECNamedCurves.getOID(str);
        if (oid2 == null) {
            oid2 = NISTNamedCurves.getOID(str);
        }
        if (oid2 == null) {
            oid2 = TeleTrusTNamedCurves.getOID(str);
        }
        return oid2 == null ? ECGOST3410NamedCurves.getOID(str) : oid2;
    }
}
