package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class HashBiMap<K, V> extends IteratorBasedAbstractMap<K, V> implements BiMap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    private transient BiEntry<K, V>[] a;
    private transient BiEntry<K, V>[] b;
    /* access modifiers changed from: private */
    public transient BiEntry<K, V> c;
    private transient BiEntry<K, V> d;
    /* access modifiers changed from: private */
    public transient int e;
    private transient int f;
    /* access modifiers changed from: private */
    public transient int g;
    @RetainedWith
    private transient BiMap<V, K> h;

    static final class BiEntry<K, V> extends ImmutableEntry<K, V> {
        final int a;
        final int b;
        @Nullable
        BiEntry<K, V> c;
        @Nullable
        BiEntry<K, V> d;
        @Nullable
        BiEntry<K, V> e;
        @Nullable
        BiEntry<K, V> f;

        BiEntry(K k, int i, V v, int i2) {
            super(k, v);
            this.a = i;
            this.b = i2;
        }
    }

    final class Inverse extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {

        final class InverseKeySet extends KeySet<V, K> {
            InverseKeySet() {
                super(Inverse.this);
            }

            public boolean remove(@Nullable Object obj) {
                BiEntry b = HashBiMap.this.b(obj, Hashing.a(obj));
                if (b == null) {
                    return false;
                }
                HashBiMap.this.a(b);
                return true;
            }

            public Iterator<V> iterator() {
                return new Itr<V>() {
                    {
                        HashBiMap hashBiMap = HashBiMap.this;
                    }

                    /* access modifiers changed from: 0000 */
                    public V b(BiEntry<K, V> biEntry) {
                        return biEntry.h;
                    }
                };
            }
        }

        private Inverse() {
        }

        /* access modifiers changed from: 0000 */
        public BiMap<K, V> a() {
            return HashBiMap.this;
        }

        public int size() {
            return HashBiMap.this.e;
        }

        public void clear() {
            a().clear();
        }

        public boolean containsKey(@Nullable Object obj) {
            return a().containsValue(obj);
        }

        public K get(@Nullable Object obj) {
            return Maps.b((Entry<K, ?>) HashBiMap.this.b(obj, Hashing.a(obj)));
        }

        public K put(@Nullable V v, @Nullable K k) {
            return HashBiMap.this.b(v, k, false);
        }

        public K forcePut(@Nullable V v, @Nullable K k) {
            return HashBiMap.this.b(v, k, true);
        }

        public K remove(@Nullable Object obj) {
            BiEntry b = HashBiMap.this.b(obj, Hashing.a(obj));
            if (b == null) {
                return null;
            }
            HashBiMap.this.a(b);
            b.f = null;
            b.e = null;
            return b.g;
        }

        public BiMap<K, V> inverse() {
            return a();
        }

        public Set<V> keySet() {
            return new InverseKeySet();
        }

        public Set<K> values() {
            return a().keySet();
        }

        public Set<Entry<V, K>> entrySet() {
            return new EntrySet<V, K>() {
                /* access modifiers changed from: 0000 */
                public Map<V, K> a() {
                    return Inverse.this;
                }

                public Iterator<Entry<V, K>> iterator() {
                    return new Itr<Entry<V, K>>() {

                        /* renamed from: com.google.common.collect.HashBiMap$Inverse$1$1$InverseEntry */
                        class InverseEntry extends AbstractMapEntry<V, K> {
                            BiEntry<K, V> a;

                            InverseEntry(BiEntry<K, V> biEntry) {
                                this.a = biEntry;
                            }

                            public V getKey() {
                                return this.a.h;
                            }

                            public K getValue() {
                                return this.a.g;
                            }

                            public K setValue(K k) {
                                K k2 = this.a.g;
                                int a2 = Hashing.a((Object) k);
                                if (a2 == this.a.a && Objects.equal(k, k2)) {
                                    return k;
                                }
                                Preconditions.checkArgument(HashBiMap.this.a((Object) k, a2) == null, "value already present: %s", (Object) k);
                                HashBiMap.this.a(this.a);
                                BiEntry<K, V> biEntry = new BiEntry<>(k, a2, this.a.h, this.a.b);
                                this.a = biEntry;
                                HashBiMap.this.a(biEntry, null);
                                AnonymousClass1.this.d = HashBiMap.this.g;
                                return k2;
                            }
                        }

                        {
                            HashBiMap hashBiMap = HashBiMap.this;
                        }

                        /* access modifiers changed from: 0000 */
                        /* renamed from: a */
                        public Entry<V, K> b(BiEntry<K, V> biEntry) {
                            return new InverseEntry(biEntry);
                        }
                    };
                }
            };
        }

        /* access modifiers changed from: 0000 */
        public Object writeReplace() {
            return new InverseSerializedForm(HashBiMap.this);
        }
    }

    final class KeySet extends KeySet<K, V> {
        KeySet() {
            super(HashBiMap.this);
        }

        public Iterator<K> iterator() {
            return new Itr<K>() {
                {
                    HashBiMap hashBiMap = HashBiMap.this;
                }

                /* access modifiers changed from: 0000 */
                public K b(BiEntry<K, V> biEntry) {
                    return biEntry.g;
                }
            };
        }

        public boolean remove(@Nullable Object obj) {
            BiEntry a2 = HashBiMap.this.a(obj, Hashing.a(obj));
            if (a2 == null) {
                return false;
            }
            HashBiMap.this.a(a2);
            a2.f = null;
            a2.e = null;
            return true;
        }
    }

    static final class InverseSerializedForm<K, V> implements Serializable {
        private final HashBiMap<K, V> a;

        InverseSerializedForm(HashBiMap<K, V> hashBiMap) {
            this.a = hashBiMap;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return this.a.inverse();
        }
    }

    abstract class Itr<T> implements Iterator<T> {
        BiEntry<K, V> b = HashBiMap.this.c;
        BiEntry<K, V> c = null;
        int d = HashBiMap.this.g;

        /* access modifiers changed from: 0000 */
        public abstract T b(BiEntry<K, V> biEntry);

        Itr() {
        }

        public boolean hasNext() {
            if (HashBiMap.this.g == this.d) {
                return this.b != null;
            }
            throw new ConcurrentModificationException();
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BiEntry<K, V> biEntry = this.b;
            this.b = biEntry.e;
            this.c = biEntry;
            return b(biEntry);
        }

        public void remove() {
            if (HashBiMap.this.g != this.d) {
                throw new ConcurrentModificationException();
            }
            CollectPreconditions.a(this.c != null);
            HashBiMap.this.a(this.c);
            this.d = HashBiMap.this.g;
            this.c = null;
        }
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int i) {
        return new HashBiMap<>(i);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }

    private HashBiMap(int i) {
        a(i);
    }

    private void a(int i) {
        CollectPreconditions.a(i, "expectedSize");
        int a2 = Hashing.a(i, 1.0d);
        this.a = b(a2);
        this.b = b(a2);
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = a2 - 1;
        this.g = 0;
    }

    /* access modifiers changed from: private */
    public void a(BiEntry<K, V> biEntry) {
        BiEntry<K, V> biEntry2;
        int i = biEntry.a & this.f;
        BiEntry<K, V> biEntry3 = null;
        BiEntry<K, V> biEntry4 = null;
        for (BiEntry<K, V> biEntry5 = this.a[i]; biEntry5 != biEntry; biEntry5 = biEntry5.c) {
            biEntry4 = biEntry5;
        }
        if (biEntry4 == null) {
            this.a[i] = biEntry.c;
        } else {
            biEntry4.c = biEntry.c;
        }
        int i2 = biEntry.b & this.f;
        BiEntry<K, V> biEntry6 = this.b[i2];
        while (true) {
            BiEntry<K, V> biEntry7 = biEntry3;
            biEntry3 = biEntry6;
            biEntry2 = biEntry7;
            if (biEntry3 == biEntry) {
                break;
            }
            biEntry6 = biEntry3.d;
        }
        if (biEntry2 == null) {
            this.b[i2] = biEntry.d;
        } else {
            biEntry2.d = biEntry.d;
        }
        if (biEntry.f == null) {
            this.c = biEntry.e;
        } else {
            biEntry.f.e = biEntry.e;
        }
        if (biEntry.e == null) {
            this.d = biEntry.f;
        } else {
            biEntry.e.f = biEntry.f;
        }
        this.e--;
        this.g++;
    }

    /* access modifiers changed from: private */
    public void a(BiEntry<K, V> biEntry, @Nullable BiEntry<K, V> biEntry2) {
        int i = biEntry.a & this.f;
        biEntry.c = this.a[i];
        this.a[i] = biEntry;
        int i2 = biEntry.b & this.f;
        biEntry.d = this.b[i2];
        this.b[i2] = biEntry;
        if (biEntry2 == null) {
            biEntry.f = this.d;
            biEntry.e = null;
            if (this.d == null) {
                this.c = biEntry;
            } else {
                this.d.e = biEntry;
            }
            this.d = biEntry;
        } else {
            biEntry.f = biEntry2.f;
            if (biEntry.f == null) {
                this.c = biEntry;
            } else {
                biEntry.f.e = biEntry;
            }
            biEntry.e = biEntry2.e;
            if (biEntry.e == null) {
                this.d = biEntry;
            } else {
                biEntry.e.f = biEntry;
            }
        }
        this.e++;
        this.g++;
    }

    /* access modifiers changed from: private */
    public BiEntry<K, V> a(@Nullable Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.a[this.f & i]; biEntry != null; biEntry = biEntry.c) {
            if (i == biEntry.a && Objects.equal(obj, biEntry.g)) {
                return biEntry;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public BiEntry<K, V> b(@Nullable Object obj, int i) {
        for (BiEntry<K, V> biEntry = this.b[this.f & i]; biEntry != null; biEntry = biEntry.d) {
            if (i == biEntry.b && Objects.equal(obj, biEntry.h)) {
                return biEntry;
            }
        }
        return null;
    }

    public boolean containsKey(@Nullable Object obj) {
        return a(obj, Hashing.a(obj)) != null;
    }

    public boolean containsValue(@Nullable Object obj) {
        return b(obj, Hashing.a(obj)) != null;
    }

    @Nullable
    public V get(@Nullable Object obj) {
        return Maps.c((Entry<?, V>) a(obj, Hashing.a(obj)));
    }

    @CanIgnoreReturnValue
    public V put(@Nullable K k, @Nullable V v) {
        return a(k, v, false);
    }

    @CanIgnoreReturnValue
    public V forcePut(@Nullable K k, @Nullable V v) {
        return a(k, v, true);
    }

    private V a(@Nullable K k, @Nullable V v, boolean z) {
        int a2 = Hashing.a((Object) k);
        int a3 = Hashing.a((Object) v);
        BiEntry a4 = a((Object) k, a2);
        if (a4 != null && a3 == a4.b && Objects.equal(v, a4.h)) {
            return v;
        }
        BiEntry b2 = b(v, a3);
        if (b2 != null) {
            if (z) {
                a(b2);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("value already present: ");
                sb.append(v);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        BiEntry biEntry = new BiEntry(k, a2, v, a3);
        if (a4 != null) {
            a(a4);
            a(biEntry, a4);
            a4.f = null;
            a4.e = null;
            a();
            return a4.h;
        }
        a(biEntry, null);
        a();
        return null;
    }

    /* access modifiers changed from: private */
    @Nullable
    public K b(@Nullable V v, @Nullable K k, boolean z) {
        int a2 = Hashing.a((Object) v);
        int a3 = Hashing.a((Object) k);
        BiEntry b2 = b(v, a2);
        if (b2 != null && a3 == b2.a && Objects.equal(k, b2.g)) {
            return k;
        }
        BiEntry a4 = a((Object) k, a3);
        if (a4 != null) {
            if (z) {
                a(a4);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("value already present: ");
                sb.append(k);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (b2 != null) {
            a(b2);
        }
        a(new BiEntry<>(k, a3, v, a2), a4);
        if (a4 != null) {
            a4.f = null;
            a4.e = null;
        }
        a();
        return Maps.b((Entry<K, ?>) b2);
    }

    private void a() {
        BiEntry<K, V>[] biEntryArr = this.a;
        if (Hashing.a(this.e, biEntryArr.length, 1.0d)) {
            int length = biEntryArr.length * 2;
            this.a = b(length);
            this.b = b(length);
            this.f = length - 1;
            this.e = 0;
            for (BiEntry<K, V> biEntry = this.c; biEntry != null; biEntry = biEntry.e) {
                a(biEntry, biEntry);
            }
            this.g++;
        }
    }

    private BiEntry<K, V>[] b(int i) {
        return new BiEntry[i];
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj) {
        BiEntry a2 = a(obj, Hashing.a(obj));
        if (a2 == null) {
            return null;
        }
        a(a2);
        a2.f = null;
        a2.e = null;
        return a2.h;
    }

    public void clear() {
        this.e = 0;
        Arrays.fill(this.a, null);
        Arrays.fill(this.b, null);
        this.c = null;
        this.d = null;
        this.g++;
    }

    public int size() {
        return this.e;
    }

    public Set<K> keySet() {
        return new KeySet();
    }

    public Set<V> values() {
        return inverse().keySet();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> b() {
        return new Itr<Entry<K, V>>() {

            /* renamed from: com.google.common.collect.HashBiMap$1$MapEntry */
            class MapEntry extends AbstractMapEntry<K, V> {
                BiEntry<K, V> a;

                MapEntry(BiEntry<K, V> biEntry) {
                    this.a = biEntry;
                }

                public K getKey() {
                    return this.a.g;
                }

                public V getValue() {
                    return this.a.h;
                }

                public V setValue(V v) {
                    V v2 = this.a.h;
                    int a2 = Hashing.a((Object) v);
                    if (a2 == this.a.b && Objects.equal(v, v2)) {
                        return v;
                    }
                    Preconditions.checkArgument(HashBiMap.this.b(v, a2) == null, "value already present: %s", (Object) v);
                    HashBiMap.this.a(this.a);
                    BiEntry<K, V> biEntry = new BiEntry<>(this.a.g, this.a.a, v, a2);
                    HashBiMap.this.a(biEntry, this.a);
                    this.a.f = null;
                    this.a.e = null;
                    AnonymousClass1.this.d = HashBiMap.this.g;
                    if (AnonymousClass1.this.c == this.a) {
                        AnonymousClass1.this.c = biEntry;
                    }
                    this.a = biEntry;
                    return v2;
                }
            }

            /* access modifiers changed from: 0000 */
            /* renamed from: a */
            public Entry<K, V> b(BiEntry<K, V> biEntry) {
                return new MapEntry(biEntry);
            }
        };
    }

    public BiMap<V, K> inverse() {
        if (this.h != null) {
            return this.h;
        }
        Inverse inverse = new Inverse();
        this.h = inverse;
        return inverse;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        Serialization.a((Map<K, V>) this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        a(16);
        Serialization.a((Map<K, V>) this, objectInputStream, Serialization.a(objectInputStream));
    }
}
