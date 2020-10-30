package org.bouncycastle.x509;

import org.bouncycastle.util.Selector;

public class X509CertPairStoreSelector implements Selector {
    private X509CertStoreSelector a;
    private X509CertStoreSelector b;
    private X509CertificatePair c;

    public Object clone() {
        X509CertPairStoreSelector x509CertPairStoreSelector = new X509CertPairStoreSelector();
        x509CertPairStoreSelector.c = this.c;
        if (this.a != null) {
            x509CertPairStoreSelector.setForwardSelector((X509CertStoreSelector) this.a.clone());
        }
        if (this.b != null) {
            x509CertPairStoreSelector.setReverseSelector((X509CertStoreSelector) this.b.clone());
        }
        return x509CertPairStoreSelector;
    }

    public X509CertificatePair getCertPair() {
        return this.c;
    }

    public X509CertStoreSelector getForwardSelector() {
        return this.a;
    }

    public X509CertStoreSelector getReverseSelector() {
        return this.b;
    }

    public boolean match(Object obj) {
        try {
            if (!(obj instanceof X509CertificatePair)) {
                return false;
            }
            X509CertificatePair x509CertificatePair = (X509CertificatePair) obj;
            if (this.a != null && !this.a.match((Object) x509CertificatePair.getForward())) {
                return false;
            }
            if (this.b != null && !this.b.match((Object) x509CertificatePair.getReverse())) {
                return false;
            }
            if (this.c != null) {
                return this.c.equals(obj);
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void setCertPair(X509CertificatePair x509CertificatePair) {
        this.c = x509CertificatePair;
    }

    public void setForwardSelector(X509CertStoreSelector x509CertStoreSelector) {
        this.a = x509CertStoreSelector;
    }

    public void setReverseSelector(X509CertStoreSelector x509CertStoreSelector) {
        this.b = x509CertStoreSelector;
    }
}
