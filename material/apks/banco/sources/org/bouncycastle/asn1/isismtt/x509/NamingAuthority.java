package org.bouncycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.bouncycastle.asn1.x500.DirectoryString;

public class NamingAuthority extends ASN1Object {
    public static final ASN1ObjectIdentifier id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern;
    private ASN1ObjectIdentifier a;
    private String b;
    private DirectoryString c;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(ISISMTTObjectIdentifiers.id_isismtt_at_namingAuthorities);
        sb.append(".1");
        id_isismtt_at_namingAuthorities_RechtWirtschaftSteuern = new ASN1ObjectIdentifier(sb.toString());
    }

    public NamingAuthority(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str, DirectoryString directoryString) {
        this.a = aSN1ObjectIdentifier;
        this.b = str;
        this.c = directoryString;
    }

    private NamingAuthority(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        if (objects.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable) objects.nextElement();
            if (aSN1Encodable instanceof ASN1ObjectIdentifier) {
                this.a = (ASN1ObjectIdentifier) aSN1Encodable;
            } else if (aSN1Encodable instanceof DERIA5String) {
                this.b = DERIA5String.getInstance(aSN1Encodable).getString();
            } else if (aSN1Encodable instanceof ASN1String) {
                this.c = DirectoryString.getInstance(aSN1Encodable);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Bad object encountered: ");
                sb2.append(aSN1Encodable.getClass());
                throw new IllegalArgumentException(sb2.toString());
            }
        }
        if (objects.hasMoreElements()) {
            ASN1Encodable aSN1Encodable2 = (ASN1Encodable) objects.nextElement();
            if (aSN1Encodable2 instanceof DERIA5String) {
                this.b = DERIA5String.getInstance(aSN1Encodable2).getString();
            } else if (aSN1Encodable2 instanceof ASN1String) {
                this.c = DirectoryString.getInstance(aSN1Encodable2);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Bad object encountered: ");
                sb3.append(aSN1Encodable2.getClass());
                throw new IllegalArgumentException(sb3.toString());
            }
        }
        if (objects.hasMoreElements()) {
            ASN1Encodable aSN1Encodable3 = (ASN1Encodable) objects.nextElement();
            if (aSN1Encodable3 instanceof ASN1String) {
                this.c = DirectoryString.getInstance(aSN1Encodable3);
                return;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Bad object encountered: ");
            sb4.append(aSN1Encodable3.getClass());
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    public static NamingAuthority getInstance(Object obj) {
        if (obj == null || (obj instanceof NamingAuthority)) {
            return (NamingAuthority) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new NamingAuthority((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static NamingAuthority getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public ASN1ObjectIdentifier getNamingAuthorityId() {
        return this.a;
    }

    public DirectoryString getNamingAuthorityText() {
        return this.c;
    }

    public String getNamingAuthorityUrl() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(this.a);
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERIA5String(this.b, true));
        }
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
