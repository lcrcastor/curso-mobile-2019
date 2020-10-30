package org.bouncycastle.asn1;

class BERFactory {
    static final BERSequence a = new BERSequence();
    static final BERSet b = new BERSet();

    BERFactory() {
    }

    static BERSequence a(ASN1EncodableVector aSN1EncodableVector) {
        return aSN1EncodableVector.size() < 1 ? a : new BERSequence(aSN1EncodableVector);
    }
}
