package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableList<E> extends ImmutableList<E> {
    static final ImmutableList<Object> a = new RegularImmutableList(ObjectArrays.a);
    private final transient Object[] b;

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    RegularImmutableList(Object[] objArr) {
        this.b = objArr;
    }

    public int size() {
        return this.b.length;
    }

    /* access modifiers changed from: 0000 */
    public int a(Object[] objArr, int i) {
        System.arraycopy(this.b, 0, objArr, i, this.b.length);
        return i + this.b.length;
    }

    public E get(int i) {
        return this.b[i];
    }

    public UnmodifiableListIterator<E> listIterator(int i) {
        return Iterators.a(this.b, 0, this.b.length, i);
    }
}
