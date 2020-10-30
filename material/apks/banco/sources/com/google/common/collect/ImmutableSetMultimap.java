package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public class ImmutableSetMultimap<K, V> extends ImmutableMultimap<K, V> implements SetMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private final transient ImmutableSet<V> a;
    @RetainedWith
    @LazyInit
    private transient ImmutableSetMultimap<V, K> d;
    private transient ImmutableSet<Entry<K, V>> e;

    static final class EntrySet<K, V> extends ImmutableSet<Entry<K, V>> {
        @Weak
        private final transient ImmutableSetMultimap<K, V> a;

        /* access modifiers changed from: 0000 */
        public boolean a() {
            return false;
        }

        EntrySet(ImmutableSetMultimap<K, V> immutableSetMultimap) {
            this.a = immutableSetMultimap;
        }

        public boolean contains(@Nullable Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.a.containsEntry(entry.getKey(), entry.getValue());
        }

        public int size() {
            return this.a.size();
        }

        public UnmodifiableIterator<Entry<K, V>> iterator() {
            return this.a.h();
        }
    }

    public static final class Builder<K, V> extends com.google.common.collect.ImmutableMultimap.Builder<K, V> {
        public Builder() {
            super(MultimapBuilder.linkedHashKeys().linkedHashSetValues().build());
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K k, V v) {
            this.a.put(Preconditions.checkNotNull(k), Preconditions.checkNotNull(v));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
            this.a.put(Preconditions.checkNotNull(entry.getKey()), Preconditions.checkNotNull(entry.getValue()));
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            Collection collection = this.a.get(Preconditions.checkNotNull(k));
            for (Object checkNotNull : iterable) {
                collection.add(Preconditions.checkNotNull(checkNotNull));
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(K k, V... vArr) {
            return putAll((Object) k, (Iterable) Arrays.asList(vArr));
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Entry entry : multimap.asMap().entrySet()) {
                putAll(entry.getKey(), (Iterable) entry.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            super.orderValuesBy(comparator);
            return this;
        }

        public ImmutableSetMultimap<K, V> build() {
            if (this.b != null) {
                SetMultimap build = MultimapBuilder.linkedHashKeys().linkedHashSetValues().build();
                for (Entry entry : Ordering.from(this.b).a().immutableSortedCopy(this.a.asMap().entrySet())) {
                    build.putAll(entry.getKey(), (Iterable) entry.getValue());
                }
                this.a = build;
            }
            return ImmutableSetMultimap.b(this.a, this.c);
        }
    }

    public static <K, V> ImmutableSetMultimap<K, V> of() {
        return EmptyImmutableSetMultimap.a;
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v) {
        Builder builder = builder();
        builder.put((Object) k, (Object) v);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2) {
        Builder builder = builder();
        builder.put((Object) k, (Object) v);
        builder.put((Object) k2, (Object) v2);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        Builder builder = builder();
        builder.put((Object) k, (Object) v);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder builder = builder();
        builder.put((Object) k, (Object) v);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        builder.put((Object) k4, (Object) v4);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder builder = builder();
        builder.put((Object) k, (Object) v);
        builder.put((Object) k2, (Object) v2);
        builder.put((Object) k3, (Object) v3);
        builder.put((Object) k4, (Object) v4);
        builder.put((Object) k5, (Object) v5);
        return builder.build();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        return b(multimap, null);
    }

    /* access modifiers changed from: private */
    public static <K, V> ImmutableSetMultimap<K, V> b(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> comparator) {
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && comparator == null) {
            return of();
        }
        if (multimap instanceof ImmutableSetMultimap) {
            ImmutableSetMultimap<K, V> immutableSetMultimap = (ImmutableSetMultimap) multimap;
            if (!immutableSetMultimap.a()) {
                return immutableSetMultimap;
            }
        }
        com.google.common.collect.ImmutableMap.Builder builder = new com.google.common.collect.ImmutableMap.Builder(multimap.asMap().size());
        int i = 0;
        for (Entry entry : multimap.asMap().entrySet()) {
            Object key = entry.getKey();
            ImmutableSet a2 = a(comparator, (Collection) entry.getValue());
            if (!a2.isEmpty()) {
                builder.put(key, a2);
                i += a2.size();
            }
        }
        return new ImmutableSetMultimap<>(builder.build(), i, comparator);
    }

    @Beta
    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> iterable) {
        return new Builder().putAll((Iterable) iterable).build();
    }

    ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> immutableMap, int i, @Nullable Comparator<? super V> comparator) {
        super(immutableMap, i);
        this.a = a(comparator);
    }

    public ImmutableSet<V> get(@Nullable K k) {
        return (ImmutableSet) MoreObjects.firstNonNull((ImmutableSet) this.b.get(k), this.a);
    }

    public ImmutableSetMultimap<V, K> inverse() {
        ImmutableSetMultimap<V, K> immutableSetMultimap = this.d;
        if (immutableSetMultimap != null) {
            return immutableSetMultimap;
        }
        ImmutableSetMultimap<V, K> o = o();
        this.d = o;
        return o;
    }

    private ImmutableSetMultimap<V, K> o() {
        Builder builder = builder();
        Iterator it = entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            builder.put(entry.getValue(), entry.getKey());
        }
        ImmutableSetMultimap<V, K> build = builder.build();
        build.d = this;
        return build;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableSet<V> removeAll(Object obj) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Entry<K, V>> entries() {
        ImmutableSet<Entry<K, V>> immutableSet = this.e;
        if (immutableSet != null) {
            return immutableSet;
        }
        EntrySet entrySet = new EntrySet(this);
        this.e = entrySet;
        return entrySet;
    }

    private static <V> ImmutableSet<V> a(@Nullable Comparator<? super V> comparator, Collection<? extends V> collection) {
        return comparator == null ? ImmutableSet.copyOf(collection) : ImmutableSortedSet.copyOf(comparator, collection);
    }

    private static <V> ImmutableSet<V> a(@Nullable Comparator<? super V> comparator) {
        return comparator == null ? ImmutableSet.of() : ImmutableSortedSet.a(comparator);
    }

    private static <V> com.google.common.collect.ImmutableSet.Builder<V> b(@Nullable Comparator<? super V> comparator) {
        return comparator == null ? new com.google.common.collect.ImmutableSet.Builder<>() : new com.google.common.collect.ImmutableSortedSet.Builder(comparator);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(n());
        Serialization.a((Multimap<K, V>) this, objectOutputStream);
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Comparator<? super V> n() {
        if (this.a instanceof ImmutableSortedSet) {
            return ((ImmutableSortedSet) this.a).comparator();
        }
        return null;
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        Comparator comparator = (Comparator) objectInputStream.readObject();
        int readInt = objectInputStream.readInt();
        if (readInt < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid key count ");
            sb.append(readInt);
            throw new InvalidObjectException(sb.toString());
        }
        com.google.common.collect.ImmutableMap.Builder builder = ImmutableMap.builder();
        int i = 0;
        for (int i2 = 0; i2 < readInt; i2++) {
            Object readObject = objectInputStream.readObject();
            int readInt2 = objectInputStream.readInt();
            if (readInt2 <= 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid value count ");
                sb2.append(readInt2);
                throw new InvalidObjectException(sb2.toString());
            }
            com.google.common.collect.ImmutableSet.Builder b = b(comparator);
            for (int i3 = 0; i3 < readInt2; i3++) {
                b.add(objectInputStream.readObject());
            }
            ImmutableSet build = b.build();
            if (build.size() != readInt2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Duplicate key-value pairs exist for key ");
                sb3.append(readObject);
                throw new InvalidObjectException(sb3.toString());
            }
            builder.put(readObject, build);
            i += readInt2;
        }
        try {
            FieldSettersHolder.a.a(this, (Object) builder.build());
            FieldSettersHolder.b.a(this, i);
            FieldSettersHolder.c.a(this, (Object) a(comparator));
        } catch (IllegalArgumentException e2) {
            throw ((InvalidObjectException) new InvalidObjectException(e2.getMessage()).initCause(e2));
        }
    }
}
