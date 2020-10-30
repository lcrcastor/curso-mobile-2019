package org.bouncycastle.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionStore implements Store {
    private Collection a;

    public CollectionStore(Collection collection) {
        this.a = new ArrayList(collection);
    }

    public Collection getMatches(Selector selector) {
        if (selector == null) {
            return new ArrayList(this.a);
        }
        ArrayList arrayList = new ArrayList();
        for (Object next : this.a) {
            if (selector.match(next)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
