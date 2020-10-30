package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Entry<K, V>> {

    @GwtIncompatible
    static class EntrySetSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, V> a;

        EntrySetSerializedForm(ImmutableMap<K, V> immutableMap) {
            this.a = immutableMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.entrySet();
        }
    }

    static final class RegularEntrySet<K, V> extends ImmutableMapEntrySet<K, V> {
        @Weak
        private final transient ImmutableMap<K, V> a;
        private final transient Entry<K, V>[] b;

        RegularEntrySet(ImmutableMap<K, V> immutableMap, Entry<K, V>[] entryArr) {
            this.a = immutableMap;
            this.b = entryArr;
        }

        /* access modifiers changed from: 0000 */
        public ImmutableMap<K, V> b() {
            return this.a;
        }

        public UnmodifiableIterator<Entry<K, V>> iterator() {
            return Iterators.forArray(this.b);
        }

        /* access modifiers changed from: 0000 */
        public ImmutableList<Entry<K, V>> c() {
            return new RegularImmutableAsList((ImmutableCollection<E>) this, (Object[]) this.b);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract ImmutableMap<K, V> b();

    ImmutableMapEntrySet() {
    }

    public int size() {
        return b().size();
    }

    public boolean contains(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        Object obj2 = b().get(entry.getKey());
        if (obj2 != null && obj2.equals(entry.getValue())) {
            z = true;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return b().b();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public boolean e() {
        return b().g();
    }

    public int hashCode() {
        return b().hashCode();
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new EntrySetSerializedForm(b());
    }
}
