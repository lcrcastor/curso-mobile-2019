package org.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.X509CertPairStoreSelector;
import org.bouncycastle.x509.X509CertStoreSelector;
import org.bouncycastle.x509.X509CertificatePair;
import org.bouncycastle.x509.X509StoreParameters;
import org.bouncycastle.x509.X509StoreSpi;
import org.bouncycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCerts extends X509StoreSpi {
    private LDAPStoreHelper a;

    private Collection a(X509CertStoreSelector x509CertStoreSelector) {
        HashSet hashSet = new HashSet();
        X509CertPairStoreSelector x509CertPairStoreSelector = new X509CertPairStoreSelector();
        x509CertPairStoreSelector.setForwardSelector(x509CertStoreSelector);
        x509CertPairStoreSelector.setReverseSelector(new X509CertStoreSelector());
        HashSet<X509CertificatePair> hashSet2 = new HashSet<>(this.a.getCrossCertificatePairs(x509CertPairStoreSelector));
        HashSet hashSet3 = new HashSet();
        HashSet hashSet4 = new HashSet();
        for (X509CertificatePair x509CertificatePair : hashSet2) {
            if (x509CertificatePair.getForward() != null) {
                hashSet3.add(x509CertificatePair.getForward());
            }
            if (x509CertificatePair.getReverse() != null) {
                hashSet4.add(x509CertificatePair.getReverse());
            }
        }
        hashSet.addAll(hashSet3);
        hashSet.addAll(hashSet4);
        return hashSet;
    }

    public Collection engineGetMatches(Selector selector) {
        Collection userCertificates;
        if (!(selector instanceof X509CertStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CertStoreSelector x509CertStoreSelector = (X509CertStoreSelector) selector;
        HashSet hashSet = new HashSet();
        if (x509CertStoreSelector.getBasicConstraints() <= 0) {
            if (x509CertStoreSelector.getBasicConstraints() == -2) {
                userCertificates = this.a.getUserCertificates(x509CertStoreSelector);
                hashSet.addAll(userCertificates);
                return hashSet;
            }
            hashSet.addAll(this.a.getUserCertificates(x509CertStoreSelector));
        }
        hashSet.addAll(this.a.getCACertificates(x509CertStoreSelector));
        userCertificates = a(x509CertStoreSelector);
        hashSet.addAll(userCertificates);
        return hashSet;
    }

    public void engineInit(X509StoreParameters x509StoreParameters) {
        if (!(x509StoreParameters instanceof X509LDAPCertStoreParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Initialization parameters must be an instance of ");
            sb.append(X509LDAPCertStoreParameters.class.getName());
            sb.append(".");
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = new LDAPStoreHelper((X509LDAPCertStoreParameters) x509StoreParameters);
    }
}
