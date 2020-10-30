package org.bouncycastle.x509;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Collection;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;

public class X509Store implements Store {
    private Provider a;
    private X509StoreSpi b;

    private X509Store(Provider provider, X509StoreSpi x509StoreSpi) {
        this.a = provider;
        this.b = x509StoreSpi;
    }

    private static X509Store a(Implementation implementation, X509StoreParameters x509StoreParameters) {
        X509StoreSpi x509StoreSpi = (X509StoreSpi) implementation.a();
        x509StoreSpi.engineInit(x509StoreParameters);
        return new X509Store(implementation.b(), x509StoreSpi);
    }

    public static X509Store getInstance(String str, X509StoreParameters x509StoreParameters) {
        try {
            return a(X509Util.b("X509Store", str), x509StoreParameters);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchStoreException(e.getMessage());
        }
    }

    public static X509Store getInstance(String str, X509StoreParameters x509StoreParameters, String str2) {
        return getInstance(str, x509StoreParameters, X509Util.c(str2));
    }

    public static X509Store getInstance(String str, X509StoreParameters x509StoreParameters, Provider provider) {
        try {
            return a(X509Util.a("X509Store", str, provider), x509StoreParameters);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchStoreException(e.getMessage());
        }
    }

    public Collection getMatches(Selector selector) {
        return this.b.engineGetMatches(selector);
    }

    public Provider getProvider() {
        return this.a;
    }
}
