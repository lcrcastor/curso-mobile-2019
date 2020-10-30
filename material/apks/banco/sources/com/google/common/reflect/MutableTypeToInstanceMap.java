package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class MutableTypeToInstanceMap<B> extends ForwardingMap<TypeToken<? extends B>, B> implements TypeToInstanceMap<B> {
    private final Map<TypeToken<? extends B>, B> a = Maps.newHashMap();

    static final class UnmodifiableEntry<K, V> extends ForwardingMapEntry<K, V> {
        private final Entry<K, V> a;

        static <K, V> Set<Entry<K, V>> a(final Set<Entry<K, V>> set) {
            return new ForwardingSet<Entry<K, V>>() {
                /* access modifiers changed from: protected */
                public Set<Entry<K, V>> delegate() {
                    return set;
                }

                public Iterator<Entry<K, V>> iterator() {
                    return UnmodifiableEntry.b(super.iterator());
                }

                public Object[] toArray() {
                    return standardToArray();
                }

                public <T> T[] toArray(T[] tArr) {
                    return standardToArray(tArr);
                }
            };
        }

        /* access modifiers changed from: private */
        public static <K, V> Iterator<Entry<K, V>> b(Iterator<Entry<K, V>> it) {
            return Iterators.transform(it, new Function<Entry<K, V>, Entry<K, V>>() {
                /* renamed from: a */
                public Entry<K, V> apply(Entry<K, V> entry) {
                    return new UnmodifiableEntry(entry);
                }
            });
        }

        private UnmodifiableEntry(Entry<K, V> entry) {
            this.a = (Entry) Preconditions.checkNotNull(entry);
        }

        /* access modifiers changed from: protected */
        public Entry<K, V> delegate() {
            return this.a;
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }
    }

    @Nullable
    public <T extends B> T getInstance(Class<T> cls) {
        return a(TypeToken.of(cls));
    }

    @CanIgnoreReturnValue
    @Nullable
    public <T extends B> T putInstance(Class<T> cls, @Nullable T t) {
        return a(TypeToken.of(cls), t);
    }

    @Nullable
    public <T extends B> T getInstance(TypeToken<T> typeToken) {
        return a(typeToken.d());
    }

    @CanIgnoreReturnValue
    @Nullable
    public <T extends B> T putInstance(TypeToken<T> typeToken, @Nullable T t) {
        return a(typeToken.d(), t);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public B put(TypeToken<? extends B> typeToken, B b) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    @Deprecated
    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    public Set<Entry<TypeToken<? extends B>, B>> entrySet() {
        return UnmodifiableEntry.a(super.entrySet());
    }

    /* access modifiers changed from: protected */
    public Map<TypeToken<? extends B>, B> delegate() {
        return this.a;
    }

    @Nullable
    private <T extends B> T a(TypeToken<T> typeToken, @Nullable T t) {
        return this.a.put(typeToken, t);
    }

    @Nullable
    private <T extends B> T a(TypeToken<T> typeToken) {
        return this.a.get(typeToken);
    }
}
