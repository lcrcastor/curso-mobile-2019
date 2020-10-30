package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> a = new RegularImmutableMultiset<>(ImmutableList.of());
    /* access modifiers changed from: private */
    public final transient ImmutableEntry<E>[] b;
    private final transient ImmutableEntry<E>[] c;
    private final transient int d;
    private final transient int e;
    @LazyInit
    private transient ImmutableSet<E> f;

    final class ElementSet extends Indexed<E> {
        /* access modifiers changed from: 0000 */
        public boolean a() {
            return true;
        }

        private ElementSet() {
        }

        /* access modifiers changed from: 0000 */
        public E a(int i) {
            return RegularImmutableMultiset.this.b[i].getElement();
        }

        public boolean contains(@Nullable Object obj) {
            return RegularImmutableMultiset.this.contains(obj);
        }

        public int size() {
            return RegularImmutableMultiset.this.b.length;
        }
    }

    static final class NonTerminalEntry<E> extends ImmutableEntry<E> {
        private final ImmutableEntry<E> a;

        NonTerminalEntry(E e, int i, ImmutableEntry<E> immutableEntry) {
            super(e, i);
            this.a = immutableEntry;
        }

        public ImmutableEntry<E> a() {
            return this.a;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    RegularImmutableMultiset(Collection<? extends Entry<? extends E>> collection) {
        ImmutableEntry<E> immutableEntry;
        int size = collection.size();
        ImmutableEntry<E>[] immutableEntryArr = new ImmutableEntry[size];
        if (size == 0) {
            this.b = immutableEntryArr;
            this.c = null;
            this.d = 0;
            this.e = 0;
            this.f = ImmutableSet.of();
            return;
        }
        int a2 = Hashing.a(size, 1.0d);
        int i = a2 - 1;
        ImmutableEntry<E>[] immutableEntryArr2 = new ImmutableEntry[a2];
        long j = 0;
        int i2 = 0;
        int i3 = 0;
        for (Entry entry : collection) {
            Object checkNotNull = Preconditions.checkNotNull(entry.getElement());
            int count = entry.getCount();
            int hashCode = checkNotNull.hashCode();
            int a3 = Hashing.a(hashCode) & i;
            ImmutableEntry<E> immutableEntry2 = immutableEntryArr2[a3];
            if (immutableEntry2 == null) {
                immutableEntry = (entry instanceof ImmutableEntry) && !(entry instanceof NonTerminalEntry) ? (ImmutableEntry) entry : new ImmutableEntry<>(checkNotNull, count);
            } else {
                immutableEntry = new NonTerminalEntry<>(checkNotNull, count, immutableEntry2);
            }
            i2 += hashCode ^ count;
            int i4 = i3 + 1;
            immutableEntryArr[i3] = immutableEntry;
            immutableEntryArr2[a3] = immutableEntry;
            i3 = i4;
            j += (long) count;
        }
        this.b = immutableEntryArr;
        this.c = immutableEntryArr2;
        this.d = Ints.saturatedCast(j);
        this.e = i2;
    }

    public int count(@Nullable Object obj) {
        ImmutableEntry<E>[] immutableEntryArr = this.c;
        if (obj == null || immutableEntryArr == null) {
            return 0;
        }
        for (ImmutableEntry<E> immutableEntry = immutableEntryArr[Hashing.a(obj) & (immutableEntryArr.length - 1)]; immutableEntry != null; immutableEntry = immutableEntry.a()) {
            if (Objects.equal(obj, immutableEntry.getElement())) {
                return immutableEntry.getCount();
            }
        }
        return 0;
    }

    public int size() {
        return this.d;
    }

    /* renamed from: c */
    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.f;
        if (immutableSet != null) {
            return immutableSet;
        }
        ElementSet elementSet = new ElementSet();
        this.f = elementSet;
        return elementSet;
    }

    /* access modifiers changed from: 0000 */
    public Entry<E> a(int i) {
        return this.b[i];
    }

    public int hashCode() {
        return this.e;
    }
}
