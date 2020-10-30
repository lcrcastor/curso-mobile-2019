package org.bouncycastle.asn1.dvcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class DVCSRequestInformation extends ASN1Object {
    private int a = 1;
    private ServiceType b;
    private BigInteger c;
    private DVCSTime d;
    private GeneralNames e;
    private PolicyInformation f;
    private GeneralNames g;
    private GeneralNames h;
    private Extensions i;

    private DVCSRequestInformation(ASN1Sequence aSN1Sequence) {
        int i2 = 1;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
            this.a = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue().intValue();
        } else {
            this.a = 1;
            i2 = 0;
        }
        this.b = ServiceType.getInstance(aSN1Sequence.getObjectAt(i2));
        for (int i3 = i2 + 1; i3 < aSN1Sequence.size(); i3++) {
            ASN1Encodable objectAt = aSN1Sequence.getObjectAt(i3);
            if (objectAt instanceof ASN1Integer) {
                this.c = ASN1Integer.getInstance(objectAt).getValue();
            } else if (!(objectAt instanceof ASN1GeneralizedTime) && (objectAt instanceof ASN1TaggedObject)) {
                ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objectAt);
                switch (instance.getTagNo()) {
                    case 0:
                        this.e = GeneralNames.getInstance(instance, false);
                        break;
                    case 1:
                        this.f = PolicyInformation.getInstance(ASN1Sequence.getInstance(instance, false));
                        break;
                    case 2:
                        this.g = GeneralNames.getInstance(instance, false);
                        break;
                    case 3:
                        this.h = GeneralNames.getInstance(instance, false);
                        break;
                    case 4:
                        this.i = Extensions.getInstance(instance, false);
                        break;
                }
            } else {
                this.d = DVCSTime.getInstance(objectAt);
            }
        }
    }

    public static DVCSRequestInformation getInstance(Object obj) {
        if (obj instanceof DVCSRequestInformation) {
            return (DVCSRequestInformation) obj;
        }
        if (obj != null) {
            return new DVCSRequestInformation(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DVCSRequestInformation getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public GeneralNames getDVCS() {
        return this.g;
    }

    public GeneralNames getDataLocations() {
        return this.h;
    }

    public Extensions getExtensions() {
        return this.i;
    }

    public BigInteger getNonce() {
        return this.c;
    }

    public PolicyInformation getRequestPolicy() {
        return this.f;
    }

    public DVCSTime getRequestTime() {
        return this.d;
    }

    public GeneralNames getRequester() {
        return this.e;
    }

    public ServiceType getService() {
        return this.b;
    }

    public int getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != 1) {
            aSN1EncodableVector.add(new ASN1Integer((long) this.a));
        }
        aSN1EncodableVector.add(this.b);
        if (this.c != null) {
            aSN1EncodableVector.add(new ASN1Integer(this.c));
        }
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        int[] iArr = {0, 1, 2, 3, 4};
        ASN1Encodable[] aSN1EncodableArr = {this.e, this.f, this.g, this.h, this.i};
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = iArr[i2];
            ASN1Encodable aSN1Encodable = aSN1EncodableArr[i2];
            if (aSN1Encodable != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, i3, aSN1Encodable));
            }
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DVCSRequestInformation {\n");
        if (this.a != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("version: ");
            sb.append(this.a);
            sb.append("\n");
            stringBuffer.append(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("service: ");
        sb2.append(this.b);
        sb2.append("\n");
        stringBuffer.append(sb2.toString());
        if (this.c != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("nonce: ");
            sb3.append(this.c);
            sb3.append("\n");
            stringBuffer.append(sb3.toString());
        }
        if (this.d != null) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("requestTime: ");
            sb4.append(this.d);
            sb4.append("\n");
            stringBuffer.append(sb4.toString());
        }
        if (this.e != null) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("requester: ");
            sb5.append(this.e);
            sb5.append("\n");
            stringBuffer.append(sb5.toString());
        }
        if (this.f != null) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append("requestPolicy: ");
            sb6.append(this.f);
            sb6.append("\n");
            stringBuffer.append(sb6.toString());
        }
        if (this.g != null) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("dvcs: ");
            sb7.append(this.g);
            sb7.append("\n");
            stringBuffer.append(sb7.toString());
        }
        if (this.h != null) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("dataLocations: ");
            sb8.append(this.h);
            sb8.append("\n");
            stringBuffer.append(sb8.toString());
        }
        if (this.i != null) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("extensions: ");
            sb9.append(this.i);
            sb9.append("\n");
            stringBuffer.append(sb9.toString());
        }
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }
}
