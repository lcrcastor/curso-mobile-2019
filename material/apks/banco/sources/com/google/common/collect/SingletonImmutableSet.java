package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(emulated = true, serializable = true)
final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    final transient E a;
    @LazyInit
    private transient int b;

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    public int size() {
        return 1;
    }

    SingletonImmutableSet(E e) {
        this.a = Preconditions.checkNotNull(e);
    }

    SingletonImmutableSet(E e, int i) {
        this.a = e;
        this.b = i;
    }

    public boolean contains(Object obj) {
        return this.a.equals(obj);
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.a);
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> c() {
        return ImmutableList.of(this.a);
    }

    /* access modifiers changed from: 0000 */
    public int a(Object[] objArr, int i) {
        objArr[i] = this.a;
        return i + 1;
    }

    public final int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int hashCode = this.a.hashCode();
        this.b = hashCode;
        return hashCode;
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return this.b != 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(this.a.toString());
        sb.append(']');
        return sb.toString();
    }
}
