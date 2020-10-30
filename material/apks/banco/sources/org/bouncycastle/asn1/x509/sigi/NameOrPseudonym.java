package org.bouncycastle.asn1.x509.sigi;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.DirectoryString;

public class NameOrPseudonym extends ASN1Object implements ASN1Choice {
    private DirectoryString a;
    private DirectoryString b;
    private ASN1Sequence c;

    public NameOrPseudonym(String str) {
        this(new DirectoryString(str));
    }

    private NameOrPseudonym(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        } else if (!(aSN1Sequence.getObjectAt(0) instanceof ASN1String)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Bad object encountered: ");
            sb2.append(aSN1Sequence.getObjectAt(0).getClass());
            throw new IllegalArgumentException(sb2.toString());
        } else {
            this.b = DirectoryString.getInstance(aSN1Sequence.getObjectAt(0));
            this.c = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public NameOrPseudonym(DirectoryString directoryString) {
        this.a = directoryString;
    }

    public NameOrPseudonym(DirectoryString directoryString, ASN1Sequence aSN1Sequence) {
        this.b = directoryString;
        this.c = aSN1Sequence;
    }

    public static NameOrPseudonym getInstance(Object obj) {
        if (obj == null || (obj instanceof NameOrPseudonym)) {
            return (NameOrPseudonym) obj;
        }
        if (obj instanceof ASN1String) {
            return new NameOrPseudonym(DirectoryString.getInstance(obj));
        }
        if (obj instanceof ASN1Sequence) {
            return new NameOrPseudonym((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public DirectoryString[] getGivenName() {
        DirectoryString[] directoryStringArr = new DirectoryString[this.c.size()];
        Enumeration objects = this.c.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            directoryStringArr[i] = DirectoryString.getInstance(objects.nextElement());
            i = i2;
        }
        return directoryStringArr;
    }

    public DirectoryString getPseudonym() {
        return this.a;
    }

    public DirectoryString getSurname() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.a != null) {
            return this.a.toASN1Primitive();
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        return new DERSequence(aSN1EncodableVector);
    }
}
