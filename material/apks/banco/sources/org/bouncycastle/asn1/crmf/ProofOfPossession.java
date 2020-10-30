package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERTaggedObject;

public class ProofOfPossession extends ASN1Object implements ASN1Choice {
    public static final int TYPE_KEY_AGREEMENT = 3;
    public static final int TYPE_KEY_ENCIPHERMENT = 2;
    public static final int TYPE_RA_VERIFIED = 0;
    public static final int TYPE_SIGNING_KEY = 1;
    private int a;
    private ASN1Encodable b;

    public ProofOfPossession() {
        this.a = 0;
        this.b = DERNull.INSTANCE;
    }

    public ProofOfPossession(int i, POPOPrivKey pOPOPrivKey) {
        this.a = i;
        this.b = pOPOPrivKey;
    }

    private ProofOfPossession(ASN1TaggedObject aSN1TaggedObject) {
        ASN1Encodable aSN1Encodable;
        this.a = aSN1TaggedObject.getTagNo();
        switch (this.a) {
            case 0:
                aSN1Encodable = DERNull.INSTANCE;
                break;
            case 1:
                aSN1Encodable = POPOSigningKey.getInstance(aSN1TaggedObject, false);
                break;
            case 2:
            case 3:
                aSN1Encodable = POPOPrivKey.getInstance(aSN1TaggedObject, true);
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("unknown tag: ");
                sb.append(this.a);
                throw new IllegalArgumentException(sb.toString());
        }
        this.b = aSN1Encodable;
    }

    public ProofOfPossession(POPOSigningKey pOPOSigningKey) {
        this.a = 1;
        this.b = pOPOSigningKey;
    }

    public static ProofOfPossession getInstance(Object obj) {
        if (obj == null || (obj instanceof ProofOfPossession)) {
            return (ProofOfPossession) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new ProofOfPossession((ASN1TaggedObject) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid object: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public ASN1Encodable getObject() {
        return this.b;
    }

    public int getType() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.a, this.b);
    }
}
