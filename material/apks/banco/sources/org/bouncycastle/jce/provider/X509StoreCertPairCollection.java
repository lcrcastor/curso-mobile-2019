package org.bouncycastle.jce.provider;

import java.util.Collection;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.X509CollectionStoreParameters;
import org.bouncycastle.x509.X509StoreParameters;
import org.bouncycastle.x509.X509StoreSpi;

public class X509StoreCertPairCollection extends X509StoreSpi {
    private CollectionStore a;

    public Collection engineGetMatches(Selector selector) {
        return this.a.getMatches(selector);
    }

    public void engineInit(X509StoreParameters x509StoreParameters) {
        if (!(x509StoreParameters instanceof X509CollectionStoreParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Initialization parameters must be an instance of ");
            sb.append(X509CollectionStoreParameters.class.getName());
            sb.append(".");
            throw new IllegalArgumentException(sb.toString());
        }
        this.a = new CollectionStore(((X509CollectionStoreParameters) x509StoreParameters).getCollection());
    }
}
