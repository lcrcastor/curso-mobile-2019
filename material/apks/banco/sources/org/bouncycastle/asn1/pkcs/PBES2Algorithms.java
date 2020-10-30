package org.bouncycastle.asn1.pkcs;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class PBES2Algorithms extends AlgorithmIdentifier implements PKCSObjectIdentifiers {
    private ASN1ObjectIdentifier a;
    private KeyDerivationFunc b;
    private EncryptionScheme c;

    public PBES2Algorithms(ASN1Sequence aSN1Sequence) {
        super(aSN1Sequence);
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = (ASN1ObjectIdentifier) objects.nextElement();
        Enumeration objects2 = ((ASN1Sequence) objects.nextElement()).getObjects();
        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) objects2.nextElement();
        if (aSN1Sequence2.getObjectAt(0).equals(id_PBKDF2)) {
            this.b = new KeyDerivationFunc(id_PBKDF2, PBKDF2Params.getInstance(aSN1Sequence2.getObjectAt(1)));
        } else {
            this.b = KeyDerivationFunc.getInstance(aSN1Sequence2);
        }
        this.c = EncryptionScheme.getInstance(objects2.nextElement());
    }

    public ASN1Primitive getASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector2.add(this.b);
        aSN1EncodableVector2.add(this.c);
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        return new DERSequence(aSN1EncodableVector);
    }

    public EncryptionScheme getEncryptionScheme() {
        return this.c;
    }

    public KeyDerivationFunc getKeyDerivationFunc() {
        return this.b;
    }

    public ASN1ObjectIdentifier getObjectId() {
        return this.a;
    }
}
