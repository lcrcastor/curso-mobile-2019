package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@NotThreadSafe
final class ChainBuilder<E> {
    private final LinkedList<E> a = new LinkedList<>();
    private final Map<Class<?>, E> b = new HashMap();

    private void c(E e) {
        Object remove = this.b.remove(e.getClass());
        if (remove != null) {
            this.a.remove(remove);
        }
        this.b.put(e.getClass(), e);
    }

    public ChainBuilder<E> a(E e) {
        if (e == null) {
            return this;
        }
        c(e);
        this.a.addFirst(e);
        return this;
    }

    public ChainBuilder<E> b(E e) {
        if (e == null) {
            return this;
        }
        c(e);
        this.a.addLast(e);
        return this;
    }

    public ChainBuilder<E> a(E... eArr) {
        if (eArr == null) {
            return this;
        }
        for (E a2 : eArr) {
            a(a2);
        }
        return this;
    }

    public ChainBuilder<E> b(E... eArr) {
        if (eArr == null) {
            return this;
        }
        for (E b2 : eArr) {
            b(b2);
        }
        return this;
    }

    public LinkedList<E> a() {
        return new LinkedList<>(this.a);
    }
}
