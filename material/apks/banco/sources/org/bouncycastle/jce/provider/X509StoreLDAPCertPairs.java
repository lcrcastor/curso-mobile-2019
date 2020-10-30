package org.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.X509CertPairStoreSelector;
import org.bouncycastle.x509.X509StoreParameters;
import org.bouncycastle.x509.X509StoreSpi;
import org.bouncycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCertPairs extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection engineGetMatches(Selector selector) {
        if (!(selector instanceof X509CertPairStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CertPairStoreSelector x509CertPairStoreSelector = (X509CertPairStoreSelector) selector;
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.a.getCrossCertificatePairs(x509CertPairStoreSelector));
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
