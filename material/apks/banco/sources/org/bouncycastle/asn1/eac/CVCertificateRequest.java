package org.bouncycastle.asn1.eac;

import java.io.IOException;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1ParsingException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERApplicationSpecific;
import org.bouncycastle.asn1.DEROctetString;

public class CVCertificateRequest extends ASN1Object {
    public static byte[] ZeroArray = {0};
    private static int i = 1;
    private static int j = 2;
    ASN1ObjectIdentifier a = null;
    ASN1ObjectIdentifier b = null;
    byte[] c = null;
    PublicKeyDataObject d = null;
    private CertificateBody e;
    private byte[] f = null;
    private byte[] g = null;
    private int h;
    protected String overSignerReference = null;

    private CVCertificateRequest(DERApplicationSpecific dERApplicationSpecific) {
        if (dERApplicationSpecific.getApplicationTag() == 103) {
            ASN1Sequence instance = ASN1Sequence.getInstance(dERApplicationSpecific.getObject(16));
            a(DERApplicationSpecific.getInstance(instance.getObjectAt(0)));
            this.g = DERApplicationSpecific.getInstance(instance.getObjectAt(instance.size() - 1)).getContents();
            return;
        }
        a(dERApplicationSpecific);
    }

    private void a(DERApplicationSpecific dERApplicationSpecific) {
        int i2;
        int i3;
        if (dERApplicationSpecific.getApplicationTag() == 33) {
            Enumeration objects = ASN1Sequence.getInstance(dERApplicationSpecific.getObject(16)).getObjects();
            while (objects.hasMoreElements()) {
                DERApplicationSpecific instance = DERApplicationSpecific.getInstance(objects.nextElement());
                int applicationTag = instance.getApplicationTag();
                if (applicationTag == 55) {
                    this.f = instance.getContents();
                    i3 = this.h;
                    i2 = j;
                } else if (applicationTag != 78) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid tag, not an CV Certificate Request element:");
                    sb.append(instance.getApplicationTag());
                    throw new IOException(sb.toString());
                } else {
                    this.e = CertificateBody.getInstance(instance);
                    i3 = this.h;
                    i2 = i;
                }
                this.h = i3 | i2;
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("not a CARDHOLDER_CERTIFICATE in request:");
        sb2.append(dERApplicationSpecific.getApplicationTag());
        throw new IOException(sb2.toString());
    }

    public static CVCertificateRequest getInstance(Object obj) {
        if (obj instanceof CVCertificateRequest) {
            return (CVCertificateRequest) obj;
        }
        if (obj == null) {
            return null;
        }
        try {
            return new CVCertificateRequest(DERApplicationSpecific.getInstance(obj));
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable to parse data: ");
            sb.append(e2.getMessage());
            throw new ASN1ParsingException(sb.toString(), e2);
        }
    }

    public CertificateBody getCertificateBody() {
        return this.e;
    }

    public byte[] getInnerSignature() {
        return this.f;
    }

    public byte[] getOuterSignature() {
        return this.g;
    }

    public PublicKeyDataObject getPublicKey() {
        return this.e.getPublicKey();
    }

    public boolean hasOuterSignature() {
        return this.g != null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.e);
        try {
            aSN1EncodableVector.add(new DERApplicationSpecific(false, 55, (ASN1Encodable) new DEROctetString(this.f)));
            return new DERApplicationSpecific(33, aSN1EncodableVector);
        } catch (IOException unused) {
            throw new IllegalStateException("unable to convert signature!");
        }
    }
}
