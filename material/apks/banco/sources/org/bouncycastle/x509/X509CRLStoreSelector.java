package org.bouncycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRL;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.extension.X509ExtensionUtil;

public class X509CRLStoreSelector extends X509CRLSelector implements Selector {
    private boolean a = false;
    private boolean b = false;
    private BigInteger c = null;
    private byte[] d = null;
    private boolean e = false;
    private X509AttributeCertificate f;

    public static X509CRLStoreSelector getInstance(X509CRLSelector x509CRLSelector) {
        if (x509CRLSelector == null) {
            throw new IllegalArgumentException("cannot create from null selector");
        }
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        x509CRLStoreSelector.setCertificateChecking(x509CRLSelector.getCertificateChecking());
        x509CRLStoreSelector.setDateAndTime(x509CRLSelector.getDateAndTime());
        try {
            x509CRLStoreSelector.setIssuerNames(x509CRLSelector.getIssuerNames());
            x509CRLStoreSelector.setIssuers(x509CRLSelector.getIssuers());
            x509CRLStoreSelector.setMaxCRLNumber(x509CRLSelector.getMaxCRL());
            x509CRLStoreSelector.setMinCRLNumber(x509CRLSelector.getMinCRL());
            return x509CRLStoreSelector;
        } catch (IOException e2) {
            throw new IllegalArgumentException(e2.getMessage());
        }
    }

    public Object clone() {
        X509CRLStoreSelector instance = getInstance(this);
        instance.a = this.a;
        instance.b = this.b;
        instance.c = this.c;
        instance.f = this.f;
        instance.e = this.e;
        instance.d = Arrays.clone(this.d);
        return instance;
    }

    public X509AttributeCertificate getAttrCertificateChecking() {
        return this.f;
    }

    public byte[] getIssuingDistributionPoint() {
        return Arrays.clone(this.d);
    }

    public BigInteger getMaxBaseCRLNumber() {
        return this.c;
    }

    public boolean isCompleteCRLEnabled() {
        return this.b;
    }

    public boolean isDeltaCRLIndicatorEnabled() {
        return this.a;
    }

    public boolean isIssuingDistributionPointEnabled() {
        return this.e;
    }

    public boolean match(Object obj) {
        if (!(obj instanceof X509CRL)) {
            return false;
        }
        X509CRL x509crl = (X509CRL) obj;
        ASN1Integer aSN1Integer = null;
        try {
            byte[] extensionValue = x509crl.getExtensionValue(X509Extensions.DeltaCRLIndicator.getId());
            if (extensionValue != null) {
                aSN1Integer = ASN1Integer.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
            }
            if (isDeltaCRLIndicatorEnabled() && aSN1Integer == null) {
                return false;
            }
            if (isCompleteCRLEnabled() && aSN1Integer != null) {
                return false;
            }
            if (aSN1Integer != null && this.c != null && aSN1Integer.getPositiveValue().compareTo(this.c) == 1) {
                return false;
            }
            if (this.e) {
                byte[] extensionValue2 = x509crl.getExtensionValue(X509Extensions.IssuingDistributionPoint.getId());
                if (this.d == null) {
                    if (extensionValue2 != null) {
                        return false;
                    }
                } else if (!Arrays.areEqual(extensionValue2, this.d)) {
                    return false;
                }
            }
            return super.match(x509crl);
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean match(CRL crl) {
        return match((Object) crl);
    }

    public void setAttrCertificateChecking(X509AttributeCertificate x509AttributeCertificate) {
        this.f = x509AttributeCertificate;
    }

    public void setCompleteCRLEnabled(boolean z) {
        this.b = z;
    }

    public void setDeltaCRLIndicatorEnabled(boolean z) {
        this.a = z;
    }

    public void setIssuingDistributionPoint(byte[] bArr) {
        this.d = Arrays.clone(bArr);
    }

    public void setIssuingDistributionPointEnabled(boolean z) {
        this.e = z;
    }

    public void setMaxBaseCRLNumber(BigInteger bigInteger) {
        this.c = bigInteger;
    }
}
