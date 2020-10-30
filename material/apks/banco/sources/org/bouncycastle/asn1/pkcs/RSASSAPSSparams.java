package org.bouncycastle.asn1.pkcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class RSASSAPSSparams extends ASN1Object {
    public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
    public static final ASN1Integer DEFAULT_SALT_LENGTH = new ASN1Integer(20);
    public static final ASN1Integer DEFAULT_TRAILER_FIELD = new ASN1Integer(1);
    private AlgorithmIdentifier a;
    private AlgorithmIdentifier b;
    private ASN1Integer c;
    private ASN1Integer d;

    public RSASSAPSSparams() {
        this.a = DEFAULT_HASH_ALGORITHM;
        this.b = DEFAULT_MASK_GEN_FUNCTION;
        this.c = DEFAULT_SALT_LENGTH;
        this.d = DEFAULT_TRAILER_FIELD;
    }

    private RSASSAPSSparams(ASN1Sequence aSN1Sequence) {
        this.a = DEFAULT_HASH_ALGORITHM;
        this.b = DEFAULT_MASK_GEN_FUNCTION;
        this.c = DEFAULT_SALT_LENGTH;
        this.d = DEFAULT_TRAILER_FIELD;
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Sequence.getObjectAt(i);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.a = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.b = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                case 2:
                    this.c = ASN1Integer.getInstance(aSN1TaggedObject, true);
                    break;
                case 3:
                    this.d = ASN1Integer.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag");
            }
        }
    }

    public RSASSAPSSparams(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2) {
        this.a = algorithmIdentifier;
        this.b = algorithmIdentifier2;
        this.c = aSN1Integer;
        this.d = aSN1Integer2;
    }

    public static RSASSAPSSparams getInstance(Object obj) {
        if (obj instanceof RSASSAPSSparams) {
            return (RSASSAPSSparams) obj;
        }
        if (obj != null) {
            return new RSASSAPSSparams(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.a;
    }

    public AlgorithmIdentifier getMaskGenAlgorithm() {
        return this.b;
    }

    public BigInteger getSaltLength() {
        return this.c.getValue();
    }

    public BigInteger getTrailerField() {
        return this.d.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (!this.a.equals(DEFAULT_HASH_ALGORITHM)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a));
        }
        if (!this.b.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.b));
        }
        if (!this.c.equals(DEFAULT_SALT_LENGTH)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c));
        }
        if (!this.d.equals(DEFAULT_TRAILER_FIELD)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 3, this.d));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
