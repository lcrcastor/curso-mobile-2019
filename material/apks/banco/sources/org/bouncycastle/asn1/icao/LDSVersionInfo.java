package org.bouncycastle.asn1.icao;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERSequence;

public class LDSVersionInfo extends ASN1Object {
    private DERPrintableString a;
    private DERPrintableString b;

    public LDSVersionInfo(String str, String str2) {
        this.a = new DERPrintableString(str);
        this.b = new DERPrintableString(str2);
    }

    private LDSVersionInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence wrong size for LDSVersionInfo");
        }
        this.a = DERPrintableString.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = DERPrintableString.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static LDSVersionInfo getInstance(Object obj) {
        if (obj instanceof LDSVersionInfo) {
            return (LDSVersionInfo) obj;
        }
        if (obj != null) {
            return new LDSVersionInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public String getLdsVersion() {
        return this.a.getString();
    }

    public String getUnicodeVersion() {
        return this.b.getString();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
