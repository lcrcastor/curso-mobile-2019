package org.bouncycastle.x509;

import java.util.ArrayList;
import java.util.Collection;

public class X509CollectionStoreParameters implements X509StoreParameters {
    private Collection a;

    public X509CollectionStoreParameters(Collection collection) {
        if (collection == null) {
            throw new NullPointerException("collection cannot be null");
        }
        this.a = collection;
    }

    public Object clone() {
        return new X509CollectionStoreParameters(this.a);
    }

    public Collection getCollection() {
        return new ArrayList(this.a);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("X509CollectionStoreParameters: [\n");
        StringBuilder sb = new StringBuilder();
        sb.append("  collection: ");
        sb.append(this.a);
        sb.append("\n");
        stringBuffer.append(sb.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
