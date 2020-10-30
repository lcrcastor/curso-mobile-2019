package org.bouncycastle.asn1.x509.qualified;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class BiometricData extends ASN1Object {
    private TypeOfBiometricData a;
    private AlgorithmIdentifier b;
    private ASN1OctetString c;
    private DERIA5String d;

    private BiometricData(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = TypeOfBiometricData.getInstance(objects.nextElement());
        this.b = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.c = ASN1OctetString.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.d = DERIA5String.getInstance(objects.nextElement());
        }
    }

    public BiometricData(TypeOfBiometricData typeOfBiometricData, AlgorithmIdentifier algorithmIdentifier, ASN1OctetString aSN1OctetString) {
        this.a = typeOfBiometricData;
        this.b = algorithmIdentifier;
        this.c = aSN1OctetString;
        this.d = null;
    }

    public BiometricData(TypeOfBiometricData typeOfBiometricData, AlgorithmIdentifier algorithmIdentifier, ASN1OctetString aSN1OctetString, DERIA5String dERIA5String) {
        this.a = typeOfBiometricData;
        this.b = algorithmIdentifier;
        this.c = aSN1OctetString;
        this.d = dERIA5String;
    }

    public static BiometricData getInstance(Object obj) {
        if (obj instanceof BiometricData) {
            return (BiometricData) obj;
        }
        if (obj != null) {
            return new BiometricData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1OctetString getBiometricDataHash() {
        return this.c;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.b;
    }

    public DERIA5String getSourceDataUri() {
        return this.d;
    }

    public TypeOfBiometricData getTypeOfBiometricData() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        if (this.d != null) {
            aSN1EncodableVector.add(this.d);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
