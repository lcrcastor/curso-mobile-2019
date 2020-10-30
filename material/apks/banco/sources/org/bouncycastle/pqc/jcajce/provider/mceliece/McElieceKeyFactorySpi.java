package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.pqc.asn1.McEliecePrivateKey;
import org.bouncycastle.pqc.asn1.McEliecePublicKey;
import org.bouncycastle.pqc.jcajce.spec.McEliecePrivateKeySpec;
import org.bouncycastle.pqc.jcajce.spec.McEliecePublicKeySpec;

public class McElieceKeyFactorySpi extends KeyFactorySpi {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";

    /* access modifiers changed from: protected */
    public PrivateKey engineGeneratePrivate(KeySpec keySpec) {
        return null;
    }

    /* access modifiers changed from: protected */
    public PublicKey engineGeneratePublic(KeySpec keySpec) {
        return null;
    }

    /* access modifiers changed from: protected */
    public KeySpec engineGetKeySpec(Key key, Class cls) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Key engineTranslateKey(Key key) {
        return null;
    }

    public PrivateKey generatePrivate(KeySpec keySpec) {
        if (keySpec instanceof McEliecePrivateKeySpec) {
            return new BCMcEliecePrivateKey((McEliecePrivateKeySpec) keySpec);
        }
        if (keySpec instanceof PKCS8EncodedKeySpec) {
            try {
                try {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(((PKCS8EncodedKeySpec) keySpec).getEncoded())).parsePrivateKey().toASN1Primitive();
                    ((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0)).toString();
                    int intValue = ((ASN1Integer) aSN1Sequence.getObjectAt(1)).getValue().intValue();
                    int intValue2 = ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue().intValue();
                    byte[] octets = ((ASN1OctetString) aSN1Sequence.getObjectAt(3)).getOctets();
                    byte[] octets2 = ((ASN1OctetString) aSN1Sequence.getObjectAt(4)).getOctets();
                    byte[] octets3 = ((ASN1OctetString) aSN1Sequence.getObjectAt(5)).getOctets();
                    byte[] octets4 = ((ASN1OctetString) aSN1Sequence.getObjectAt(6)).getOctets();
                    byte[] octets5 = ((ASN1OctetString) aSN1Sequence.getObjectAt(7)).getOctets();
                    byte[] octets6 = ((ASN1OctetString) aSN1Sequence.getObjectAt(8)).getOctets();
                    ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(9);
                    byte[][] bArr = new byte[aSN1Sequence2.size()][];
                    for (int i = 0; i < aSN1Sequence2.size(); i++) {
                        bArr[i] = ((ASN1OctetString) aSN1Sequence2.getObjectAt(i)).getOctets();
                    }
                    McEliecePrivateKeySpec mcEliecePrivateKeySpec = new McEliecePrivateKeySpec("1.3.6.1.4.1.8301.3.1.3.4.1", intValue, intValue2, octets, octets2, octets3, octets4, octets5, octets6, bArr);
                    return new BCMcEliecePrivateKey(mcEliecePrivateKeySpec);
                } catch (IOException unused) {
                    throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec.");
                }
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to decode PKCS8EncodedKeySpec: ");
                sb.append(e);
                throw new InvalidKeySpecException(sb.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unsupported key specification: ");
            sb2.append(keySpec.getClass());
            sb2.append(".");
            throw new InvalidKeySpecException(sb2.toString());
        }
    }

    public PrivateKey generatePrivate(PrivateKeyInfo privateKeyInfo) {
        try {
            McEliecePrivateKey instance = McEliecePrivateKey.getInstance(privateKeyInfo.parsePrivateKey().toASN1Primitive());
            BCMcEliecePrivateKey bCMcEliecePrivateKey = new BCMcEliecePrivateKey(instance.getOID().getId(), instance.getN(), instance.getK(), instance.getField(), instance.getGoppaPoly(), instance.getSInv(), instance.getP1(), instance.getP2(), instance.getH(), instance.getQInv());
            return bCMcEliecePrivateKey;
        } catch (IOException unused) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec");
        }
    }

    public PublicKey generatePublic(KeySpec keySpec) {
        if (keySpec instanceof McEliecePublicKeySpec) {
            return new BCMcEliecePublicKey((McEliecePublicKeySpec) keySpec);
        }
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                try {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(((X509EncodedKeySpec) keySpec).getEncoded())).parsePublicKey();
                    ((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0)).toString();
                    return new BCMcEliecePublicKey(new McEliecePublicKeySpec("1.3.6.1.4.1.8301.3.1.3.4.1", ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue().intValue(), ((ASN1Integer) aSN1Sequence.getObjectAt(1)).getValue().intValue(), ((ASN1OctetString) aSN1Sequence.getObjectAt(3)).getOctets()));
                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to decode X509EncodedKeySpec: ");
                    sb.append(e.getMessage());
                    throw new InvalidKeySpecException(sb.toString());
                }
            } catch (IOException e2) {
                throw new InvalidKeySpecException(e2.toString());
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unsupported key specification: ");
            sb2.append(keySpec.getClass());
            sb2.append(".");
            throw new InvalidKeySpecException(sb2.toString());
        }
    }

    public PublicKey generatePublic(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            McEliecePublicKey instance = McEliecePublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey());
            return new BCMcEliecePublicKey(instance.getOID().getId(), instance.getN(), instance.getT(), instance.getG());
        } catch (IOException unused) {
            throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec");
        }
    }

    public KeySpec getKeySpec(Key key, Class cls) {
        if (key instanceof BCMcEliecePrivateKey) {
            if (PKCS8EncodedKeySpec.class.isAssignableFrom(cls)) {
                return new PKCS8EncodedKeySpec(key.getEncoded());
            }
            if (McEliecePrivateKeySpec.class.isAssignableFrom(cls)) {
                BCMcEliecePrivateKey bCMcEliecePrivateKey = (BCMcEliecePrivateKey) key;
                McEliecePrivateKeySpec mcEliecePrivateKeySpec = new McEliecePrivateKeySpec("1.3.6.1.4.1.8301.3.1.3.4.1", bCMcEliecePrivateKey.getN(), bCMcEliecePrivateKey.getK(), bCMcEliecePrivateKey.getField(), bCMcEliecePrivateKey.getGoppaPoly(), bCMcEliecePrivateKey.getSInv(), bCMcEliecePrivateKey.getP1(), bCMcEliecePrivateKey.getP2(), bCMcEliecePrivateKey.getH(), bCMcEliecePrivateKey.getQInv());
                return mcEliecePrivateKeySpec;
            }
        } else if (!(key instanceof BCMcEliecePublicKey)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported key type: ");
            sb.append(key.getClass());
            sb.append(".");
            throw new InvalidKeySpecException(sb.toString());
        } else if (X509EncodedKeySpec.class.isAssignableFrom(cls)) {
            return new X509EncodedKeySpec(key.getEncoded());
        } else {
            if (McEliecePublicKeySpec.class.isAssignableFrom(cls)) {
                BCMcEliecePublicKey bCMcEliecePublicKey = (BCMcEliecePublicKey) key;
                return new McEliecePublicKeySpec("1.3.6.1.4.1.8301.3.1.3.4.1", bCMcEliecePublicKey.getN(), bCMcEliecePublicKey.getT(), bCMcEliecePublicKey.getG());
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Unknown key specification: ");
        sb2.append(cls);
        sb2.append(".");
        throw new InvalidKeySpecException(sb2.toString());
    }

    public Key translateKey(Key key) {
        if ((key instanceof BCMcEliecePrivateKey) || (key instanceof BCMcEliecePublicKey)) {
            return key;
        }
        throw new InvalidKeyException("Unsupported key type.");
    }
}
