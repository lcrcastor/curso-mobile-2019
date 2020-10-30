package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.lang.Enum;
import java.util.Collection;
import java.util.EnumSet;

@GwtCompatible(emulated = true, serializable = true)
final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E> {
    private final transient EnumSet<E> a;
    @LazyInit
    private transient int b;

    static class EnumSerializedForm<E extends Enum<E>> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumSet<E> a;

        EnumSerializedForm(EnumSet<E> enumSet) {
            this.a = enumSet;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return new ImmutableEnumSet(this.a.clone());
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean e() {
        return true;
    }

    static ImmutableSet a(EnumSet enumSet) {
        switch (enumSet.size()) {
            case 0:
                return ImmutableSet.of();
            case 1:
                return ImmutableSet.of(Iterables.getOnlyElement(enumSet));
            default:
                return new ImmutableEnumSet(enumSet);
        }
    }

    private ImmutableEnumSet(EnumSet<E> enumSet) {
        this.a = enumSet;
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.a.iterator());
    }

    public int size() {
        return this.a.size();
    }

    public boolean contains(Object obj) {
        return this.a.contains(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ImmutableEnumSet) {
            collection = ((ImmutableEnumSet) collection).a;
        }
        return this.a.containsAll(collection);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ImmutableEnumSet) {
            obj = ((ImmutableEnumSet) obj).a;
        }
        return this.a.equals(obj);
    }

    public int hashCode() {
        int i = this.b;
        if (i != 0) {
            return i;
        }
        int hashCode = this.a.hashCode();
        this.b = hashCode;
        return hashCode;
    }

    public String toString() {
        return this.a.toString();
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        return new EnumSerializedForm(this.a);
    }
}
