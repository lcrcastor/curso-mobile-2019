package org.bouncycastle.jce;

import java.security.cert.CertStoreParameters;
import java.util.Collection;

public class MultiCertStoreParameters implements CertStoreParameters {
    private Collection a;
    private boolean b;

    public MultiCertStoreParameters(Collection collection) {
        this(collection, true);
    }

    public MultiCertStoreParameters(Collection collection, boolean z) {
        this.a = collection;
        this.b = z;
    }

    public Object clone() {
        return this;
    }

    public Collection getCertStores() {
        return this.a;
    }

    public boolean getSearchAllStores() {
        return this.b;
    }
}
