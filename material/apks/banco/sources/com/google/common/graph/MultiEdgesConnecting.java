package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

abstract class MultiEdgesConnecting<E> extends AbstractSet<E> {
    private final Map<E, ?> a;
    /* access modifiers changed from: private */
    public final Object b;

    MultiEdgesConnecting(Map<E, ?> map, Object obj) {
        this.a = (Map) Preconditions.checkNotNull(map);
        this.b = Preconditions.checkNotNull(obj);
    }

    /* renamed from: a */
    public UnmodifiableIterator<E> iterator() {
        final Iterator it = this.a.entrySet().iterator();
        return new AbstractIterator<E>() {
            /* access modifiers changed from: protected */
            public E computeNext() {
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    if (MultiEdgesConnecting.this.b.equals(entry.getValue())) {
                        return entry.getKey();
                    }
                }
                return endOfData();
            }
        };
    }

    public boolean contains(@Nullable Object obj) {
        return this.b.equals(this.a.get(obj));
    }
}
