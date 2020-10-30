package org.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509StoreParameters;
import org.bouncycastle.x509.X509StoreSpi;
import org.bouncycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCRLs extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection engineGetMatches(Selector selector) {
        Collection certificateRevocationLists;
        if (!(selector instanceof X509CRLStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CRLStoreSelector x509CRLStoreSelector = (X509CRLStoreSelector) selector;
        HashSet hashSet = new HashSet();
        if (x509CRLStoreSelector.isDeltaCRLIndicatorEnabled()) {
            certificateRevocationLists = this.a.getDeltaCertificateRevocationLists(x509CRLStoreSelector);
        } else {
            hashSet.addAll(this.a.getDeltaCertificateRevocationLists(x509CRLStoreSelector));
            hashSet.addAll(this.a.getAttributeAuthorityRevocationLists(x509CRLStoreSelector));
            hashSet.addAll(this.a.getAttributeCertificateRevocationLists(x509CRLStoreSelector));
            hashSet.addAll(this.a.getAuthorityRevocationLists(x509CRLStoreSelector));
            certificateRevocationLists = this.a.getCertificateRevocationLists(x509CRLStoreSelector);
        }
        hashSet.addAll(certificateRevocationLists);
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
