package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.j2objc.annotations.Weak;

@GwtCompatible(emulated = true)
class RegularImmutableAsList<E> extends ImmutableAsList<E> {
    @Weak
    private final ImmutableCollection<E> a;
    private final ImmutableList<? extends E> b;

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, ImmutableList<? extends E> immutableList) {
        this.a = immutableCollection;
        this.b = immutableList;
    }

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] objArr) {
        this(immutableCollection, ImmutableList.a(objArr));
    }

    /* access modifiers changed from: 0000 */
    public ImmutableCollection<E> b() {
        return this.a;
    }

    public UnmodifiableListIterator<E> listIterator(int i) {
        return this.b.listIterator(i);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public int a(Object[] objArr, int i) {
        return this.b.a(objArr, i);
    }

    public E get(int i) {
        return this.b.get(i);
    }
}
