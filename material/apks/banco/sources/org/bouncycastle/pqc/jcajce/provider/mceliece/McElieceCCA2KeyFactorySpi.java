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
import org.bouncycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.bouncycastle.pqc.asn1.McElieceCCA2PublicKey;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2PrivateKeySpec;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2PublicKeySpec;

public class McElieceCCA2KeyFactorySpi extends KeyFactorySpi {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";

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
        if (keySpec instanceof McElieceCCA2PrivateKeySpec) {
            return new BCMcElieceCCA2PrivateKey((McElieceCCA2PrivateKeySpec) keySpec);
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
                    ASN1Sequence aSN1Sequence2 = (ASN1Sequence) aSN1Sequence.getObjectAt(7);
                    byte[][] bArr = new byte[aSN1Sequence2.size()][];
                    for (int i = 0; i < aSN1Sequence2.size(); i++) {
                        bArr[i] = ((ASN1OctetString) aSN1Sequence2.getObjectAt(i)).getOctets();
                    }
                    McElieceCCA2PrivateKeySpec mcElieceCCA2PrivateKeySpec = new McElieceCCA2PrivateKeySpec("1.3.6.1.4.1.8301.3.1.3.4.2", intValue, intValue2, octets, octets2, octets3, octets4, bArr);
                    return new BCMcElieceCCA2PrivateKey(mcElieceCCA2PrivateKeySpec);
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
            McElieceCCA2PrivateKey instance = McElieceCCA2PrivateKey.getInstance(privateKeyInfo.parsePrivateKey().toASN1Primitive());
            BCMcElieceCCA2PrivateKey bCMcElieceCCA2PrivateKey = new BCMcElieceCCA2PrivateKey(instance.getOID().getId(), instance.getN(), instance.getK(), instance.getField(), instance.getGoppaPoly(), instance.getP(), instance.getH(), instance.getQInv());
            return bCMcElieceCCA2PrivateKey;
        } catch (IOException unused) {
            throw new InvalidKeySpecException("Unable to decode PKCS8EncodedKeySpec");
        }
    }

    public PublicKey generatePublic(KeySpec keySpec) {
        if (keySpec instanceof McElieceCCA2PublicKeySpec) {
            return new BCMcElieceCCA2PublicKey((McElieceCCA2PublicKeySpec) keySpec);
        }
        if (keySpec instanceof X509EncodedKeySpec) {
            try {
                try {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(((X509EncodedKeySpec) keySpec).getEncoded())).parsePublicKey();
                    ((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0)).toString();
                    return new BCMcElieceCCA2PublicKey(new McElieceCCA2PublicKeySpec("1.3.6.1.4.1.8301.3.1.3.4.2", ((ASN1Integer) aSN1Sequence.getObjectAt(1)).getValue().intValue(), ((ASN1Integer) aSN1Sequence.getObjectAt(2)).getValue().intValue(), ((ASN1OctetString) aSN1Sequence.getObjectAt(3)).getOctets()));
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
            McElieceCCA2PublicKey instance = McElieceCCA2PublicKey.getInstance((ASN1Sequence) subjectPublicKeyInfo.parsePublicKey());
            return new BCMcElieceCCA2PublicKey(instance.getOID().getId(), instance.getN(), instance.getT(), instance.getG());
        } catch (IOException unused) {
            throw new InvalidKeySpecException("Unable to decode X509EncodedKeySpec");
        }
    }

    public KeySpec getKeySpec(Key key, Class cls) {
        if (key instanceof BCMcElieceCCA2PrivateKey) {
            if (PKCS8EncodedKeySpec.class.isAssignableFrom(cls)) {
                return new PKCS8EncodedKeySpec(key.getEncoded());
            }
            if (McElieceCCA2PrivateKeySpec.class.isAssignableFrom(cls)) {
                BCMcElieceCCA2PrivateKey bCMcElieceCCA2PrivateKey = (BCMcElieceCCA2PrivateKey) key;
                McElieceCCA2PrivateKeySpec mcElieceCCA2PrivateKeySpec = new McElieceCCA2PrivateKeySpec("1.3.6.1.4.1.8301.3.1.3.4.2", bCMcElieceCCA2PrivateKey.getN(), bCMcElieceCCA2PrivateKey.getK(), bCMcElieceCCA2PrivateKey.getField(), bCMcElieceCCA2PrivateKey.getGoppaPoly(), bCMcElieceCCA2PrivateKey.getP(), bCMcElieceCCA2PrivateKey.getH(), bCMcElieceCCA2PrivateKey.getQInv());
                return mcElieceCCA2PrivateKeySpec;
            }
        } else if (!(key instanceof BCMcElieceCCA2PublicKey)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported key type: ");
            sb.append(key.getClass());
            sb.append(".");
            throw new InvalidKeySpecException(sb.toString());
        } else if (X509EncodedKeySpec.class.isAssignableFrom(cls)) {
            return new X509EncodedKeySpec(key.getEncoded());
        } else {
            if (McElieceCCA2PublicKeySpec.class.isAssignableFrom(cls)) {
                BCMcElieceCCA2PublicKey bCMcElieceCCA2PublicKey = (BCMcElieceCCA2PublicKey) key;
                return new McElieceCCA2PublicKeySpec("1.3.6.1.4.1.8301.3.1.3.4.2", bCMcElieceCCA2PublicKey.getN(), bCMcElieceCCA2PublicKey.getT(), bCMcElieceCCA2PublicKey.getG());
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Unknown key specification: ");
        sb2.append(cls);
        sb2.append(".");
        throw new InvalidKeySpecException(sb2.toString());
    }

    public Key translateKey(Key key) {
        if ((key instanceof BCMcElieceCCA2PrivateKey) || (key instanceof BCMcElieceCCA2PublicKey)) {
            return key;
        }
        throw new InvalidKeyException("Unsupported key type.");
    }
}
