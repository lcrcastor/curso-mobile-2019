package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.EnvelopedData;

public class EncryptedKey extends ASN1Object implements ASN1Choice {
    private EnvelopedData a;
    private EncryptedValue b;

    public EncryptedKey(EnvelopedData envelopedData) {
        this.a = envelopedData;
    }

    public EncryptedKey(EncryptedValue encryptedValue) {
        this.b = encryptedValue;
    }

    public static EncryptedKey getInstance(Object obj) {
        return obj instanceof EncryptedKey ? (EncryptedKey) obj : obj instanceof ASN1TaggedObject ? new EncryptedKey(EnvelopedData.getInstance((ASN1TaggedObject) obj, false)) : obj instanceof EncryptedValue ? new EncryptedKey((EncryptedValue) obj) : new EncryptedKey(EncryptedValue.getInstance(obj));
    }

    public ASN1Encodable getValue() {
        return this.b != null ? this.b : this.a;
    }

    public boolean isEncryptedValue() {
        return this.b != null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b != null ? this.b.toASN1Primitive() : new DERTaggedObject(false, 0, this.a);
    }
}
