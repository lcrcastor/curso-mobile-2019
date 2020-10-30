package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.IssuerSerial;

public class ESSCertIDv2 extends ASN1Object {
    private static final AlgorithmIdentifier d = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
    private AlgorithmIdentifier a;
    private byte[] b;
    private IssuerSerial c;

    private ESSCertIDv2(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() > 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        int i = 0;
        if (aSN1Sequence.getObjectAt(0) instanceof ASN1OctetString) {
            this.a = d;
        } else {
            this.a = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(0).toASN1Primitive());
            i = 1;
        }
        int i2 = i + 1;
        this.b = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i).toASN1Primitive()).getOctets();
        if (aSN1Sequence.size() > i2) {
            this.c = IssuerSerial.getInstance(aSN1Sequence.getObjectAt(i2));
        }
    }

    public ESSCertIDv2(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this(algorithmIdentifier, bArr, null);
    }

    public ESSCertIDv2(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, IssuerSerial issuerSerial) {
        if (algorithmIdentifier == null) {
            algorithmIdentifier = d;
        }
        this.a = algorithmIdentifier;
        this.b = bArr;
        this.c = issuerSerial;
    }

    public ESSCertIDv2(byte[] bArr) {
        this(null, bArr, null);
    }

    public ESSCertIDv2(byte[] bArr, IssuerSerial issuerSerial) {
        this(null, bArr, issuerSerial);
    }

    public static ESSCertIDv2 getInstance(Object obj) {
        if (obj instanceof ESSCertIDv2) {
            return (ESSCertIDv2) obj;
        }
        if (obj != null) {
            return new ESSCertIDv2(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public byte[] getCertHash() {
        return this.b;
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.a;
    }

    public IssuerSerial getIssuerSerial() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (!this.a.equals(d)) {
            aSN1EncodableVector.add(this.a);
        }
        aSN1EncodableVector.add(new DEROctetString(this.b).toASN1Primitive());
        if (this.c != null) {
            aSN1EncodableVector.add(this.c);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
