package org.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.Target;
import org.bouncycastle.asn1.x509.TargetInformation;
import org.bouncycastle.asn1.x509.Targets;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.util.Selector;

public class X509AttributeCertStoreSelector implements Selector {
    private AttributeCertificateHolder a;
    private AttributeCertificateIssuer b;
    private BigInteger c;
    private Date d;
    private X509AttributeCertificate e;
    private Collection f = new HashSet();
    private Collection g = new HashSet();

    private Set a(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        for (Object next : collection) {
            if (!(next instanceof GeneralName)) {
                next = GeneralName.getInstance(ASN1Primitive.fromByteArray((byte[]) next));
            }
            hashSet.add(next);
        }
        return hashSet;
    }

    public void addTargetGroup(GeneralName generalName) {
        this.g.add(generalName);
    }

    public void addTargetGroup(byte[] bArr) {
        addTargetGroup(GeneralName.getInstance(ASN1Primitive.fromByteArray(bArr)));
    }

    public void addTargetName(GeneralName generalName) {
        this.f.add(generalName);
    }

    public void addTargetName(byte[] bArr) {
        addTargetName(GeneralName.getInstance(ASN1Primitive.fromByteArray(bArr)));
    }

    public Object clone() {
        X509AttributeCertStoreSelector x509AttributeCertStoreSelector = new X509AttributeCertStoreSelector();
        x509AttributeCertStoreSelector.e = this.e;
        x509AttributeCertStoreSelector.d = getAttributeCertificateValid();
        x509AttributeCertStoreSelector.a = this.a;
        x509AttributeCertStoreSelector.b = this.b;
        x509AttributeCertStoreSelector.c = this.c;
        x509AttributeCertStoreSelector.g = getTargetGroups();
        x509AttributeCertStoreSelector.f = getTargetNames();
        return x509AttributeCertStoreSelector;
    }

    public X509AttributeCertificate getAttributeCert() {
        return this.e;
    }

    public Date getAttributeCertificateValid() {
        if (this.d != null) {
            return new Date(this.d.getTime());
        }
        return null;
    }

    public AttributeCertificateHolder getHolder() {
        return this.a;
    }

    public AttributeCertificateIssuer getIssuer() {
        return this.b;
    }

    public BigInteger getSerialNumber() {
        return this.c;
    }

    public Collection getTargetGroups() {
        return Collections.unmodifiableCollection(this.g);
    }

    public Collection getTargetNames() {
        return Collections.unmodifiableCollection(this.f);
    }

    public boolean match(Object obj) {
        if (!(obj instanceof X509AttributeCertificate)) {
            return false;
        }
        X509AttributeCertificate x509AttributeCertificate = (X509AttributeCertificate) obj;
        if (this.e != null && !this.e.equals(x509AttributeCertificate)) {
            return false;
        }
        if (this.c != null && !x509AttributeCertificate.getSerialNumber().equals(this.c)) {
            return false;
        }
        if (this.a != null && !x509AttributeCertificate.getHolder().equals(this.a)) {
            return false;
        }
        if (this.b != null && !x509AttributeCertificate.getIssuer().equals(this.b)) {
            return false;
        }
        if (this.d != null) {
            try {
                x509AttributeCertificate.checkValidity(this.d);
            } catch (CertificateExpiredException | CertificateNotYetValidException unused) {
                return false;
            }
        }
        if (!this.f.isEmpty() || !this.g.isEmpty()) {
            byte[] extensionValue = x509AttributeCertificate.getExtensionValue(X509Extensions.TargetInformation.getId());
            if (extensionValue != null) {
                try {
                    Targets[] targetsObjects = TargetInformation.getInstance(new ASN1InputStream(((DEROctetString) DEROctetString.fromByteArray(extensionValue)).getOctets()).readObject()).getTargetsObjects();
                    if (!this.f.isEmpty()) {
                        boolean z = false;
                        for (Targets targets : targetsObjects) {
                            Target[] targets2 = targets.getTargets();
                            int i = 0;
                            while (true) {
                                if (i >= targets2.length) {
                                    break;
                                } else if (this.f.contains(GeneralName.getInstance(targets2[i].getTargetName()))) {
                                    z = true;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                        if (!z) {
                            return false;
                        }
                    }
                    if (!this.g.isEmpty()) {
                        boolean z2 = false;
                        for (Targets targets3 : targetsObjects) {
                            Target[] targets4 = targets3.getTargets();
                            int i2 = 0;
                            while (true) {
                                if (i2 >= targets4.length) {
                                    break;
                                } else if (this.g.contains(GeneralName.getInstance(targets4[i2].getTargetGroup()))) {
                                    z2 = true;
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        if (!z2) {
                            return false;
                        }
                    }
                } catch (IOException | IllegalArgumentException unused2) {
                }
            }
        }
        return true;
    }

    public void setAttributeCert(X509AttributeCertificate x509AttributeCertificate) {
        this.e = x509AttributeCertificate;
    }

    public void setAttributeCertificateValid(Date date) {
        if (date != null) {
            this.d = new Date(date.getTime());
        } else {
            this.d = null;
        }
    }

    public void setHolder(AttributeCertificateHolder attributeCertificateHolder) {
        this.a = attributeCertificateHolder;
    }

    public void setIssuer(AttributeCertificateIssuer attributeCertificateIssuer) {
        this.b = attributeCertificateIssuer;
    }

    public void setSerialNumber(BigInteger bigInteger) {
        this.c = bigInteger;
    }

    public void setTargetGroups(Collection collection) {
        this.g = a(collection);
    }

    public void setTargetNames(Collection collection) {
        this.f = a(collection);
    }
}
