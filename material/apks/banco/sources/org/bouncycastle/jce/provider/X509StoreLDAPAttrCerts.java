package org.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509StoreParameters;
import org.bouncycastle.x509.X509StoreSpi;
import org.bouncycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPAttrCerts extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection engineGetMatches(Selector selector) {
        if (!(selector instanceof X509AttributeCertStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509AttributeCertStoreSelector x509AttributeCertStoreSelector = (X509AttributeCertStoreSelector) selector;
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.a.getAACertificates(x509AttributeCertStoreSelector));
        hashSet.addAll(this.a.getAttributeCertificateAttributes(x509AttributeCertStoreSelector));
        hashSet.addAll(this.a.getAttributeDescriptorCertificates(x509AttributeCertStoreSelector));
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
