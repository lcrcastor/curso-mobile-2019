package org.bouncycastle.asn1.pkcs;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class RSAESOAEPparams extends ASN1Object {
    public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
    public static final AlgorithmIdentifier DEFAULT_P_SOURCE_ALGORITHM = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(new byte[0]));
    private AlgorithmIdentifier a;
    private AlgorithmIdentifier b;
    private AlgorithmIdentifier c;

    public RSAESOAEPparams() {
        this.a = DEFAULT_HASH_ALGORITHM;
        this.b = DEFAULT_MASK_GEN_FUNCTION;
        this.c = DEFAULT_P_SOURCE_ALGORITHM;
    }

    public RSAESOAEPparams(ASN1Sequence aSN1Sequence) {
        this.a = DEFAULT_HASH_ALGORITHM;
        this.b = DEFAULT_MASK_GEN_FUNCTION;
        this.c = DEFAULT_P_SOURCE_ALGORITHM;
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
                    this.c = AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag");
            }
        }
    }

    public RSAESOAEPparams(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, AlgorithmIdentifier algorithmIdentifier3) {
        this.a = algorithmIdentifier;
        this.b = algorithmIdentifier2;
        this.c = algorithmIdentifier3;
    }

    public static RSAESOAEPparams getInstance(Object obj) {
        if (obj instanceof RSAESOAEPparams) {
            return (RSAESOAEPparams) obj;
        }
        if (obj != null) {
            return new RSAESOAEPparams(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.a;
    }

    public AlgorithmIdentifier getMaskGenAlgorithm() {
        return this.b;
    }

    public AlgorithmIdentifier getPSourceAlgorithm() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (!this.a.equals(DEFAULT_HASH_ALGORITHM)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.a));
        }
        if (!this.b.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, this.b));
        }
        if (!this.c.equals(DEFAULT_P_SOURCE_ALGORITHM)) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, this.c));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
