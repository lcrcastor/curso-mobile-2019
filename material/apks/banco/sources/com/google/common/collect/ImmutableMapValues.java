package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
    /* access modifiers changed from: private */
    @Weak
    public final ImmutableMap<K, V> a;

    @GwtIncompatible
    static class SerializedForm<V> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<?, V> a;

        SerializedForm(ImmutableMap<?, V> immutableMap) {
            this.a = immutableMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.values();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return true;
    }

    ImmutableMapValues(ImmutableMap<K, V> immutableMap) {
        this.a = immutableMap;
    }

    public int size() {
        return this.a.size();
    }

    public UnmodifiableIterator<V> iterator() {
        return new UnmodifiableIterator<V>() {
            final UnmodifiableIterator<Entry<K, V>> a = ImmutableMapValues.this.a.entrySet().iterator();

            public boolean hasNext() {
                return this.a.hasNext();
            }

            public V next() {
                return ((Entry) this.a.next()).getValue();
            }
        };
    }

    public boolean contains(@Nullable Object obj) {
        return obj != null && Iterators.contains(iterator(), obj);
    }

    public ImmutableList<V> asList() {
        final ImmutableList asList = this.a.entrySet().asList();
        return new ImmutableAsList<V>() {
            public V get(int i) {
                return ((Entry) asList.get(i)).getValue();
            }

            /* access modifiers changed from: 0000 */
            public ImmutableCollection<V> b() {
                return ImmutableMapValues.this;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.a);
    }
}
