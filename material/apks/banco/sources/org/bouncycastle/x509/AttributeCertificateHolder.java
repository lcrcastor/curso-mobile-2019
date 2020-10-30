package org.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.Holder;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.asn1.x509.ObjectDigestInfo;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Selector;

public class AttributeCertificateHolder implements CertSelector, Selector {
    final Holder a;

    public AttributeCertificateHolder(int i, String str, String str2, byte[] bArr) {
        this.a = new Holder(new ObjectDigestInfo(i, new ASN1ObjectIdentifier(str2), new AlgorithmIdentifier(str), Arrays.clone(bArr)));
    }

    public AttributeCertificateHolder(X509Certificate x509Certificate) {
        try {
            this.a = new Holder(new IssuerSerial(a(PrincipalUtil.getIssuerX509Principal(x509Certificate)), new ASN1Integer(x509Certificate.getSerialNumber())));
        } catch (Exception e) {
            throw new CertificateParsingException(e.getMessage());
        }
    }

    public AttributeCertificateHolder(X500Principal x500Principal) {
        this(X509Util.a(x500Principal));
    }

    public AttributeCertificateHolder(X500Principal x500Principal, BigInteger bigInteger) {
        this(X509Util.a(x500Principal), bigInteger);
    }

    AttributeCertificateHolder(ASN1Sequence aSN1Sequence) {
        this.a = Holder.getInstance(aSN1Sequence);
    }

    public AttributeCertificateHolder(X509Principal x509Principal) {
        this.a = new Holder(a(x509Principal));
    }

    public AttributeCertificateHolder(X509Principal x509Principal, BigInteger bigInteger) {
        this.a = new Holder(new IssuerSerial(GeneralNames.getInstance(new DERSequence((ASN1Encodable) new GeneralName((X509Name) x509Principal))), new ASN1Integer(bigInteger)));
    }

    private GeneralNames a(X509Principal x509Principal) {
        return GeneralNames.getInstance(new DERSequence((ASN1Encodable) new GeneralName((X509Name) x509Principal)));
    }

    private boolean a(X509Principal x509Principal, GeneralNames generalNames) {
        GeneralName[] names = generalNames.getNames();
        for (int i = 0; i != names.length; i++) {
            GeneralName generalName = names[i];
            if (generalName.getTagNo() == 4) {
                try {
                    if (new X509Principal(generalName.getName().toASN1Primitive().getEncoded()).equals(x509Principal)) {
                        return true;
                    }
                } catch (IOException unused) {
                    continue;
                }
            }
        }
        return false;
    }

    private Object[] a(GeneralName[] generalNameArr) {
        ArrayList arrayList = new ArrayList(generalNameArr.length);
        for (int i = 0; i != generalNameArr.length; i++) {
            if (generalNameArr[i].getTagNo() == 4) {
                try {
                    arrayList.add(new X500Principal(generalNameArr[i].getName().toASN1Primitive().getEncoded()));
                } catch (IOException unused) {
                    throw new RuntimeException("badly formed Name object");
                }
            }
        }
        return arrayList.toArray(new Object[arrayList.size()]);
    }

    private Principal[] a(GeneralNames generalNames) {
        Object[] a2 = a(generalNames.getNames());
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i != a2.length; i++) {
            if (a2[i] instanceof Principal) {
                arrayList.add(a2[i]);
            }
        }
        return (Principal[]) arrayList.toArray(new Principal[arrayList.size()]);
    }

    public Object clone() {
        return new AttributeCertificateHolder((ASN1Sequence) this.a.toASN1Object());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeCertificateHolder)) {
            return false;
        }
        return this.a.equals(((AttributeCertificateHolder) obj).a);
    }

    public String getDigestAlgorithm() {
        if (this.a.getObjectDigestInfo() != null) {
            return this.a.getObjectDigestInfo().getDigestAlgorithm().getObjectId().getId();
        }
        return null;
    }

    public int getDigestedObjectType() {
        if (this.a.getObjectDigestInfo() != null) {
            return this.a.getObjectDigestInfo().getDigestedObjectType().getValue().intValue();
        }
        return -1;
    }

    public Principal[] getEntityNames() {
        if (this.a.getEntityName() != null) {
            return a(this.a.getEntityName());
        }
        return null;
    }

    public Principal[] getIssuer() {
        if (this.a.getBaseCertificateID() != null) {
            return a(this.a.getBaseCertificateID().getIssuer());
        }
        return null;
    }

    public byte[] getObjectDigest() {
        if (this.a.getObjectDigestInfo() != null) {
            return this.a.getObjectDigestInfo().getObjectDigest().getBytes();
        }
        return null;
    }

    public String getOtherObjectTypeID() {
        if (this.a.getObjectDigestInfo() != null) {
            this.a.getObjectDigestInfo().getOtherObjectTypeID().getId();
        }
        return null;
    }

    public BigInteger getSerialNumber() {
        if (this.a.getBaseCertificateID() != null) {
            return this.a.getBaseCertificateID().getSerial().getValue();
        }
        return null;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean match(Object obj) {
        if (!(obj instanceof X509Certificate)) {
            return false;
        }
        return match((Certificate) obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0077, code lost:
        r0.update(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0090, code lost:
        if (org.bouncycastle.util.Arrays.areEqual(r0.digest(), getObjectDigest()) != false) goto L_0x0092;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean match(java.security.cert.Certificate r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof java.security.cert.X509Certificate
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = r5
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            org.bouncycastle.asn1.x509.Holder r2 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.IssuerSerial r2 = r2.getBaseCertificateID()     // Catch:{  }
            r3 = 1
            if (r2 == 0) goto L_0x0040
            org.bouncycastle.asn1.x509.Holder r5 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.IssuerSerial r5 = r5.getBaseCertificateID()     // Catch:{  }
            org.bouncycastle.asn1.ASN1Integer r5 = r5.getSerial()     // Catch:{  }
            java.math.BigInteger r5 = r5.getValue()     // Catch:{  }
            java.math.BigInteger r2 = r0.getSerialNumber()     // Catch:{  }
            boolean r5 = r5.equals(r2)     // Catch:{  }
            if (r5 == 0) goto L_0x003f
            org.bouncycastle.jce.X509Principal r5 = org.bouncycastle.jce.PrincipalUtil.getIssuerX509Principal(r0)     // Catch:{  }
            org.bouncycastle.asn1.x509.Holder r0 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.IssuerSerial r0 = r0.getBaseCertificateID()     // Catch:{  }
            org.bouncycastle.asn1.x509.GeneralNames r0 = r0.getIssuer()     // Catch:{  }
            boolean r5 = r4.a(r5, r0)     // Catch:{  }
            if (r5 == 0) goto L_0x003f
            r1 = 1
        L_0x003f:
            return r1
        L_0x0040:
            org.bouncycastle.asn1.x509.Holder r2 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.GeneralNames r2 = r2.getEntityName()     // Catch:{  }
            if (r2 == 0) goto L_0x0059
            org.bouncycastle.jce.X509Principal r0 = org.bouncycastle.jce.PrincipalUtil.getSubjectX509Principal(r0)     // Catch:{  }
            org.bouncycastle.asn1.x509.Holder r2 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.GeneralNames r2 = r2.getEntityName()     // Catch:{  }
            boolean r0 = r4.a(r0, r2)     // Catch:{  }
            if (r0 == 0) goto L_0x0059
            return r3
        L_0x0059:
            org.bouncycastle.asn1.x509.Holder r0 = r4.a     // Catch:{  }
            org.bouncycastle.asn1.x509.ObjectDigestInfo r0 = r0.getObjectDigestInfo()     // Catch:{  }
            if (r0 == 0) goto L_0x0092
            java.lang.String r0 = r4.getDigestAlgorithm()     // Catch:{ CertificateEncodingException -> 0x0092 }
            java.lang.String r2 = "BC"
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r0, r2)     // Catch:{ CertificateEncodingException -> 0x0092 }
            int r2 = r4.getDigestedObjectType()     // Catch:{  }
            switch(r2) {
                case 0: goto L_0x007b;
                case 1: goto L_0x0073;
                default: goto L_0x0072;
            }     // Catch:{  }
        L_0x0072:
            goto L_0x0084
        L_0x0073:
            byte[] r5 = r5.getEncoded()     // Catch:{  }
        L_0x0077:
            r0.update(r5)     // Catch:{  }
            goto L_0x0084
        L_0x007b:
            java.security.PublicKey r5 = r5.getPublicKey()     // Catch:{  }
            byte[] r5 = r5.getEncoded()     // Catch:{  }
            goto L_0x0077
        L_0x0084:
            byte[] r5 = r0.digest()     // Catch:{  }
            byte[] r0 = r4.getObjectDigest()     // Catch:{  }
            boolean r5 = org.bouncycastle.util.Arrays.areEqual(r5, r0)     // Catch:{  }
            if (r5 != 0) goto L_0x0092
        L_0x0092:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.AttributeCertificateHolder.match(java.security.cert.Certificate):boolean");
    }
}
