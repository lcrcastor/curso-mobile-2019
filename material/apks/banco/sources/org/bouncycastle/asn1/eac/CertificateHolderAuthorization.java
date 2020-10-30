package org.bouncycastle.asn1.eac;

import com.google.common.primitives.UnsignedBytes;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.util.Integers;

public class CertificateHolderAuthorization extends ASN1Object {
    public static final int CVCA = 192;
    public static final int DV_DOMESTIC = 128;
    public static final int DV_FOREIGN = 64;
    public static final int IS = 0;
    public static final int RADG3 = 1;
    public static final int RADG4 = 2;
    static Hashtable c = new Hashtable();
    static BidirectionalMap d = new BidirectionalMap();
    static Hashtable e = new Hashtable();
    public static final ASN1ObjectIdentifier id_role_EAC = EACObjectIdentifiers.bsi_de.branch("3.1.2.1");
    ASN1ObjectIdentifier a;
    DERApplicationSpecific b;

    static {
        c.put(Integers.valueOf(2), "RADG4");
        c.put(Integers.valueOf(1), "RADG3");
        d.put(Integers.valueOf(192), "CVCA");
        d.put(Integers.valueOf(128), "DV_DOMESTIC");
        d.put(Integers.valueOf(64), "DV_FOREIGN");
        d.put(Integers.valueOf(0), "IS");
    }

    public CertificateHolderAuthorization(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i) {
        a(aSN1ObjectIdentifier);
        a((byte) i);
    }

    public CertificateHolderAuthorization(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 76) {
            a(new ASN1InputStream(dERApplicationSpecific.getContents()));
        }
    }

    public static int GetFlag(String str) {
        Integer num = (Integer) d.getReverse(str);
        if (num != null) {
            return num.intValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown value ");
        sb.append(str);
        throw new IllegalArgumentException(sb.toString());
    }

    public static String GetRoleDescription(int i) {
        return (String) d.get(Integers.valueOf(i));
    }

    private void a(byte b2) {
        this.b = new DERApplicationSpecific(EACTags.getTag(83), new byte[]{b2});
    }

    private void a(ASN1InputStream aSN1InputStream) {
        ASN1Primitive readObject = aSN1InputStream.readObject();
        if (readObject instanceof ASN1ObjectIdentifier) {
            this.a = (ASN1ObjectIdentifier) readObject;
            ASN1Primitive readObject2 = aSN1InputStream.readObject();
            if (readObject2 instanceof DERApplicationSpecific) {
                this.b = (DERApplicationSpecific) readObject2;
                return;
            }
            throw new IllegalArgumentException("No access rights in CerticateHolderAuthorization");
        }
        throw new IllegalArgumentException("no Oid in CerticateHolderAuthorization");
    }

    private void a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
    }

    public int getAccessRights() {
        return this.b.getContents()[0] & UnsignedBytes.MAX_VALUE;
    }

    public ASN1ObjectIdentifier getOid() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERApplicationSpecific(76, aSN1EncodableVector);
    }
}
