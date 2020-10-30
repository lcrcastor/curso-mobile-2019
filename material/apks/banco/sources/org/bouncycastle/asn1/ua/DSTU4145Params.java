package org.bouncycastle.asn1.ua;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.util.Arrays;

public class DSTU4145Params extends ASN1Object {
    private static final byte[] a = {-87, -42, -21, 69, -15, 60, 112, -126, UnsignedBytes.MAX_POWER_OF_TWO, -60, -106, 123, 35, Ascii.US, 94, -83, -10, 88, -21, -92, -64, 55, 41, Ascii.GS, 56, -39, 107, -16, 37, -54, 78, Ascii.ETB, -8, -23, 114, Ascii.CR, -58, Ascii.NAK, -76, 58, 40, -105, 95, Ascii.VT, -63, -34, -93, 100, 56, -75, 100, -22, 44, Ascii.ETB, -97, -48, Ascii.DC2, 62, 109, -72, -6, -59, 121, 4};
    private ASN1ObjectIdentifier b;
    private DSTU4145ECBinary c;
    private byte[] d = a;

    public DSTU4145Params(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.b = aSN1ObjectIdentifier;
    }

    public DSTU4145Params(DSTU4145ECBinary dSTU4145ECBinary) {
        this.c = dSTU4145ECBinary;
    }

    public static byte[] getDefaultDKE() {
        return a;
    }

    public static DSTU4145Params getInstance(Object obj) {
        if (obj instanceof DSTU4145Params) {
            return (DSTU4145Params) obj;
        }
        if (obj != null) {
            ASN1Sequence instance = ASN1Sequence.getInstance(obj);
            DSTU4145Params dSTU4145Params = instance.getObjectAt(0) instanceof ASN1ObjectIdentifier ? new DSTU4145Params(ASN1ObjectIdentifier.getInstance(instance.getObjectAt(0))) : new DSTU4145Params(DSTU4145ECBinary.getInstance(instance.getObjectAt(0)));
            if (instance.size() == 2) {
                dSTU4145Params.d = ASN1OctetString.getInstance(instance.getObjectAt(1)).getOctets();
                if (dSTU4145Params.d.length != a.length) {
                    throw new IllegalArgumentException("object parse error");
                }
            }
            return dSTU4145Params;
        }
        throw new IllegalArgumentException("object parse error");
    }

    public byte[] getDKE() {
        return this.d;
    }

    public DSTU4145ECBinary getECBinary() {
        return this.c;
    }

    public ASN1ObjectIdentifier getNamedCurve() {
        return this.b;
    }

    public boolean isNamedCurve() {
        return this.b != null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b != null ? this.b : this.c);
        if (!Arrays.areEqual(this.d, a)) {
            aSN1EncodableVector.add(new DEROctetString(this.d));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
