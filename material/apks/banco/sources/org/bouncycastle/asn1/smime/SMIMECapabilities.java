package org.bouncycastle.asn1.smime;

import java.util.Enumeration;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapabilities extends ASN1Object {
    public static final ASN1ObjectIdentifier canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
    public static final ASN1ObjectIdentifier dES_CBC = new ASN1ObjectIdentifier("1.3.14.3.2.7");
    public static final ASN1ObjectIdentifier dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
    public static final ASN1ObjectIdentifier preferSignedData = PKCSObjectIdentifiers.preferSignedData;
    public static final ASN1ObjectIdentifier rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
    public static final ASN1ObjectIdentifier sMIMECapabilitesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
    private ASN1Sequence a;

    public SMIMECapabilities(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public static SMIMECapabilities getInstance(Object obj) {
        if (obj == null || (obj instanceof SMIMECapabilities)) {
            return (SMIMECapabilities) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new SMIMECapabilities((ASN1Sequence) obj);
        }
        if (obj instanceof Attribute) {
            return new SMIMECapabilities((ASN1Sequence) ((Attribute) obj).getAttrValues().getObjectAt(0));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknown object in factory: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public Vector getCapabilities(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Enumeration objects = this.a.getObjects();
        Vector vector = new Vector();
        if (aSN1ObjectIdentifier == null) {
            while (objects.hasMoreElements()) {
                vector.addElement(SMIMECapability.getInstance(objects.nextElement()));
            }
        } else {
            while (objects.hasMoreElements()) {
                SMIMECapability instance = SMIMECapability.getInstance(objects.nextElement());
                if (aSN1ObjectIdentifier.equals(instance.getCapabilityID())) {
                    vector.addElement(instance);
                }
            }
        }
        return vector;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }
}
