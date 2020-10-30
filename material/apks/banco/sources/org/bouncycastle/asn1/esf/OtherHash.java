package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OtherHash extends ASN1Object implements ASN1Choice {
    private ASN1OctetString a;
    private OtherHashAlgAndValue b;

    private OtherHash(ASN1OctetString aSN1OctetString) {
        this.a = aSN1OctetString;
    }

    public OtherHash(OtherHashAlgAndValue otherHashAlgAndValue) {
        this.b = otherHashAlgAndValue;
    }

    public OtherHash(byte[] bArr) {
        this.a = new DEROctetString(bArr);
    }

    public static OtherHash getInstance(Object obj) {
        return obj instanceof OtherHash ? (OtherHash) obj : obj instanceof ASN1OctetString ? new OtherHash((ASN1OctetString) obj) : new OtherHash(OtherHashAlgAndValue.getInstance(obj));
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.b == null ? new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1) : this.b.getHashAlgorithm();
    }

    public byte[] getHashValue() {
        return (this.b == null ? this.a : this.b.getHashValue()).getOctets();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b == null ? this.a : this.b.toASN1Primitive();
    }
}
