package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSet<E> extends Indexed<E> {
    static final RegularImmutableSet<Object> a = new RegularImmutableSet<>(ObjectArrays.a, 0, null, 0);
    @VisibleForTesting
    final transient Object[] b;
    private final transient Object[] c;
    private final transient int d;
    private final transient int e;

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return true;
    }

    RegularImmutableSet(Object[] objArr, int i, Object[] objArr2, int i2) {
        this.c = objArr;
        this.b = objArr2;
        this.d = i2;
        this.e = i;
    }

    public boolean contains(@Nullable Object obj) {
        Object[] objArr = this.b;
        if (obj == null || objArr == null) {
            return false;
        }
        int a2 = Hashing.a(obj);
        while (true) {
            int i = a2 & this.d;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            a2 = i + 1;
        }
    }

    public int size() {
        return this.c.length;
    }

    /* access modifiers changed from: 0000 */
    public E a(int i) {
        return this.c[i];
    }

    /* access modifiers changed from: 0000 */
    public int a(Object[] objArr, int i) {
        System.arraycopy(this.c, 0, objArr, i, this.c.length);
        return i + this.c.length;
    }

    /* access modifiers changed from: 0000 */
    public ImmutableList<E> c() {
        return this.b == null ? ImmutableList.of() : new RegularImmutableAsList((ImmutableCollection<E>) this, this.c);
    }

    public int hashCode() {
        return this.e;
    }
}
