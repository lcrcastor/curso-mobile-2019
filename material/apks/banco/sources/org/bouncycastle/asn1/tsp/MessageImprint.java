package org.bouncycastle.asn1.tsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class MessageImprint extends ASN1Object {
    AlgorithmIdentifier a;
    byte[] b;

    private MessageImprint(ASN1Sequence aSN1Sequence) {
        this.a = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets();
    }

    public MessageImprint(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.a = algorithmIdentifier;
        this.b = bArr;
    }

    public static MessageImprint getInstance(Object obj) {
        if (obj instanceof MessageImprint) {
            return (MessageImprint) obj;
        }
        if (obj != null) {
            return new MessageImprint(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.a;
    }

    public byte[] getHashedMessage() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(new DEROctetString(this.b));
        return new DERSequence(aSN1EncodableVector);
    }
}
