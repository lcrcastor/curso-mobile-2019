package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

@GwtCompatible(emulated = true, serializable = true)
abstract class ImmutableAsList<E> extends ImmutableList<E> {

    @GwtIncompatible
    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableCollection<?> a;

        SerializedForm(ImmutableCollection<?> immutableCollection) {
            this.a = immutableCollection;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.asList();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract ImmutableCollection<E> b();

    ImmutableAsList() {
    }

    public boolean contains(Object obj) {
        return b().contains(obj);
    }

    public int size() {
        return b().size();
    }

    public boolean isEmpty() {
        return b().isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return b().a();
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Use SerializedForm");
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(b());
    }
}
