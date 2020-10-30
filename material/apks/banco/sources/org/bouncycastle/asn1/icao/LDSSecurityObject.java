package org.bouncycastle.asn1.icao;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class LDSSecurityObject extends ASN1Object implements ICAOObjectIdentifiers {
    public static final int ub_DataGroups = 16;
    private ASN1Integer a;
    private AlgorithmIdentifier b;
    private DataGroupHash[] c;
    private LDSVersionInfo d;

    private LDSSecurityObject(ASN1Sequence aSN1Sequence) {
        this.a = new ASN1Integer(0);
        if (aSN1Sequence == null || aSN1Sequence.size() == 0) {
            throw new IllegalArgumentException("null or empty sequence passed.");
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Integer.getInstance(objects.nextElement());
        this.b = AlgorithmIdentifier.getInstance(objects.nextElement());
        ASN1Sequence instance = ASN1Sequence.getInstance(objects.nextElement());
        if (this.a.getValue().intValue() == 1) {
            this.d = LDSVersionInfo.getInstance(objects.nextElement());
        }
        a(instance.size());
        this.c = new DataGroupHash[instance.size()];
        for (int i = 0; i < instance.size(); i++) {
            this.c[i] = DataGroupHash.getInstance(instance.getObjectAt(i));
        }
    }

    public LDSSecurityObject(AlgorithmIdentifier algorithmIdentifier, DataGroupHash[] dataGroupHashArr) {
        this.a = new ASN1Integer(0);
        this.a = new ASN1Integer(0);
        this.b = algorithmIdentifier;
        this.c = dataGroupHashArr;
        a(dataGroupHashArr.length);
    }

    public LDSSecurityObject(AlgorithmIdentifier algorithmIdentifier, DataGroupHash[] dataGroupHashArr, LDSVersionInfo lDSVersionInfo) {
        this.a = new ASN1Integer(0);
        this.a = new ASN1Integer(1);
        this.b = algorithmIdentifier;
        this.c = dataGroupHashArr;
        this.d = lDSVersionInfo;
        a(dataGroupHashArr.length);
    }

    private void a(int i) {
        if (i < 2 || i > 16) {
            throw new IllegalArgumentException("wrong size in DataGroupHashValues : not in (2..16)");
        }
    }

    public static LDSSecurityObject getInstance(Object obj) {
        if (obj instanceof LDSSecurityObject) {
            return (LDSSecurityObject) obj;
        }
        if (obj != null) {
            return new LDSSecurityObject(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DataGroupHash[] getDatagroupHash() {
        return this.c;
    }

    public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
        return this.b;
    }

    public int getVersion() {
        return this.a.getValue().intValue();
    }

    public LDSVersionInfo getVersionInfo() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (DataGroupHash add : this.c) {
            aSN1EncodableVector2.add(add);
        }
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
