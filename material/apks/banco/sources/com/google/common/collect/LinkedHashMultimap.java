package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public final class LinkedHashMultimap<K, V> extends AbstractSetMultimap<K, V> {
    @GwtIncompatible
    private static final long serialVersionUID = 1;
    @VisibleForTesting
    transient int a = 2;
    /* access modifiers changed from: private */
    public transient ValueEntry<K, V> b;

    @VisibleForTesting
    static final class ValueEntry<K, V> extends ImmutableEntry<K, V> implements ValueSetLink<K, V> {
        final int a;
        @Nullable
        ValueEntry<K, V> b;
        ValueSetLink<K, V> c;
        ValueSetLink<K, V> d;
        ValueEntry<K, V> e;
        ValueEntry<K, V> f;

        ValueEntry(@Nullable K k, @Nullable V v, int i, @Nullable ValueEntry<K, V> valueEntry) {
            super(k, v);
            this.a = i;
            this.b = valueEntry;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(@Nullable Object obj, int i) {
            return this.a == i && Objects.equal(getValue(), obj);
        }

        public ValueSetLink<K, V> a() {
            return this.c;
        }

        public ValueSetLink<K, V> b() {
            return this.d;
        }

        public void a(ValueSetLink<K, V> valueSetLink) {
            this.c = valueSetLink;
        }

        public void b(ValueSetLink<K, V> valueSetLink) {
            this.d = valueSetLink;
        }

        public ValueEntry<K, V> c() {
            return this.e;
        }

        public ValueEntry<K, V> d() {
            return this.f;
        }

        public void a(ValueEntry<K, V> valueEntry) {
            this.f = valueEntry;
        }

        public void b(ValueEntry<K, V> valueEntry) {
            this.e = valueEntry;
        }
    }

    @VisibleForTesting
    final class ValueSet extends ImprovedAbstractSet<V> implements ValueSetLink<K, V> {
        @VisibleForTesting
        ValueEntry<K, V>[] a;
        private final K c;
        private int d = 0;
        /* access modifiers changed from: private */
        public int e = 0;
        /* access modifiers changed from: private */
        public ValueSetLink<K, V> f;
        private ValueSetLink<K, V> g;

        ValueSet(K k, int i) {
            this.c = k;
            this.f = this;
            this.g = this;
            this.a = new ValueEntry[Hashing.a(i, 1.0d)];
        }

        private int c() {
            return this.a.length - 1;
        }

        public ValueSetLink<K, V> a() {
            return this.g;
        }

        public ValueSetLink<K, V> b() {
            return this.f;
        }

        public void a(ValueSetLink<K, V> valueSetLink) {
            this.g = valueSetLink;
        }

        public void b(ValueSetLink<K, V> valueSetLink) {
            this.f = valueSetLink;
        }

        public Iterator<V> iterator() {
            return new Iterator<V>() {
                ValueSetLink<K, V> a = ValueSet.this.f;
                ValueEntry<K, V> b;
                int c = ValueSet.this.e;

                private void a() {
                    if (ValueSet.this.e != this.c) {
                        throw new ConcurrentModificationException();
                    }
                }

                public boolean hasNext() {
                    a();
                    return this.a != ValueSet.this;
                }

                public V next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    ValueEntry<K, V> valueEntry = (ValueEntry) this.a;
                    V value = valueEntry.getValue();
                    this.b = valueEntry;
                    this.a = valueEntry.b();
                    return value;
                }

                public void remove() {
                    a();
                    CollectPreconditions.a(this.b != null);
                    ValueSet.this.remove(this.b.getValue());
                    this.c = ValueSet.this.e;
                    this.b = null;
                }
            };
        }

        public int size() {
            return this.d;
        }

        public boolean contains(@Nullable Object obj) {
            int a2 = Hashing.a(obj);
            for (ValueEntry<K, V> valueEntry = this.a[c() & a2]; valueEntry != null; valueEntry = valueEntry.b) {
                if (valueEntry.a(obj, a2)) {
                    return true;
                }
            }
            return false;
        }

        public boolean add(@Nullable V v) {
            int a2 = Hashing.a((Object) v);
            int c2 = c() & a2;
            ValueEntry<K, V> valueEntry = this.a[c2];
            for (ValueEntry<K, V> valueEntry2 = valueEntry; valueEntry2 != null; valueEntry2 = valueEntry2.b) {
                if (valueEntry2.a(v, a2)) {
                    return false;
                }
            }
            ValueEntry<K, V> valueEntry3 = new ValueEntry<>(this.c, v, a2, valueEntry);
            LinkedHashMultimap.b(this.g, (ValueSetLink<K, V>) valueEntry3);
            LinkedHashMultimap.b((ValueSetLink<K, V>) valueEntry3, (ValueSetLink<K, V>) this);
            LinkedHashMultimap.b(LinkedHashMultimap.this.b.c(), valueEntry3);
            LinkedHashMultimap.b(valueEntry3, LinkedHashMultimap.this.b);
            this.a[c2] = valueEntry3;
            this.d++;
            this.e++;
            d();
            return true;
        }

        private void d() {
            if (Hashing.a(this.d, this.a.length, 1.0d)) {
                ValueEntry<K, V>[] valueEntryArr = new ValueEntry[(this.a.length * 2)];
                this.a = valueEntryArr;
                int length = valueEntryArr.length - 1;
                for (ValueSetLink<K, V> valueSetLink = this.f; valueSetLink != this; valueSetLink = valueSetLink.b()) {
                    ValueEntry<K, V> valueEntry = (ValueEntry) valueSetLink;
                    int i = valueEntry.a & length;
                    valueEntry.b = valueEntryArr[i];
                    valueEntryArr[i] = valueEntry;
                }
            }
        }

        @CanIgnoreReturnValue
        public boolean remove(@Nullable Object obj) {
            int a2 = Hashing.a(obj);
            int c2 = c() & a2;
            ValueEntry<K, V> valueEntry = this.a[c2];
            ValueEntry<K, V> valueEntry2 = null;
            while (true) {
                ValueEntry<K, V> valueEntry3 = valueEntry2;
                valueEntry2 = valueEntry;
                ValueEntry<K, V> valueEntry4 = valueEntry3;
                if (valueEntry2 == null) {
                    return false;
                }
                if (valueEntry2.a(obj, a2)) {
                    if (valueEntry4 == null) {
                        this.a[c2] = valueEntry2.b;
                    } else {
                        valueEntry4.b = valueEntry2.b;
                    }
                    LinkedHashMultimap.b((ValueSetLink<K, V>) valueEntry2);
                    LinkedHashMultimap.b(valueEntry2);
                    this.d--;
                    this.e++;
                    return true;
                }
                valueEntry = valueEntry2.b;
            }
        }

        public void clear() {
            Arrays.fill(this.a, null);
            this.d = 0;
            for (ValueSetLink<K, V> valueSetLink = this.f; valueSetLink != this; valueSetLink = valueSetLink.b()) {
                LinkedHashMultimap.b((ValueEntry) valueSetLink);
            }
            LinkedHashMultimap.b((ValueSetLink<K, V>) this, (ValueSetLink<K, V>) this);
            this.e++;
        }
    }

    interface ValueSetLink<K, V> {
        ValueSetLink<K, V> a();

        void a(ValueSetLink<K, V> valueSetLink);

        ValueSetLink<K, V> b();

        void b(ValueSetLink<K, V> valueSetLink);
    }

    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ Set get(Object obj) {
        return super.get(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public /* bridge */ /* synthetic */ boolean put(Object obj, Object obj2) {
        return super.put(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    public /* bridge */ /* synthetic */ Set removeAll(Object obj) {
        return super.removeAll(obj);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<>(16, 2);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int i, int i2) {
        return new LinkedHashMultimap<>(Maps.a(i), Maps.a(i2));
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> create = create(multimap.keySet().size(), 2);
        create.putAll(multimap);
        return create;
    }

    /* access modifiers changed from: private */
    public static <K, V> void b(ValueSetLink<K, V> valueSetLink, ValueSetLink<K, V> valueSetLink2) {
        valueSetLink.b(valueSetLink2);
        valueSetLink2.a(valueSetLink);
    }

    /* access modifiers changed from: private */
    public static <K, V> void b(ValueEntry<K, V> valueEntry, ValueEntry<K, V> valueEntry2) {
        valueEntry.a(valueEntry2);
        valueEntry2.b(valueEntry);
    }

    /* access modifiers changed from: private */
    public static <K, V> void b(ValueSetLink<K, V> valueSetLink) {
        b(valueSetLink.a(), valueSetLink.b());
    }

    /* access modifiers changed from: private */
    public static <K, V> void b(ValueEntry<K, V> valueEntry) {
        b(valueEntry.c(), valueEntry.d());
    }

    private LinkedHashMultimap(int i, int i2) {
        super(new LinkedHashMap(i));
        CollectPreconditions.a(i2, "expectedValuesPerKey");
        this.a = i2;
        this.b = new ValueEntry<>(null, null, 0, null);
        b(this.b, this.b);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Set<V> c() {
        return new LinkedHashSet(this.a);
    }

    /* access modifiers changed from: 0000 */
    public Collection<V> a(K k) {
        return new ValueSet(k, this.a);
    }

    @CanIgnoreReturnValue
    public Set<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        return super.replaceValues((Object) k, (Iterable) iterable);
    }

    public Set<Entry<K, V>> entries() {
        return super.entries();
    }

    public Set<K> keySet() {
        return super.keySet();
    }

    public Collection<V> values() {
        return super.values();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> h() {
        return new Iterator<Entry<K, V>>() {
            ValueEntry<K, V> a = LinkedHashMultimap.this.b.f;
            ValueEntry<K, V> b;

            public boolean hasNext() {
                return this.a != LinkedHashMultimap.this.b;
            }

            /* renamed from: a */
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                ValueEntry<K, V> valueEntry = this.a;
                this.b = valueEntry;
                this.a = this.a.f;
                return valueEntry;
            }

            public void remove() {
                CollectPreconditions.a(this.b != null);
                LinkedHashMultimap.this.remove(this.b.getKey(), this.b.getValue());
                this.b = null;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Iterator<V> g() {
        return Maps.b(h());
    }

    public void clear() {
        super.clear();
        b(this.b, this.b);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(keySet().size());
        for (Object writeObject : keySet()) {
            objectOutputStream.writeObject(writeObject);
        }
        objectOutputStream.writeInt(size());
        for (Entry entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = new ValueEntry<>(null, null, 0, null);
        b(this.b, this.b);
        this.a = 2;
        int readInt = objectInputStream.readInt();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < readInt; i++) {
            Object readObject = objectInputStream.readObject();
            linkedHashMap.put(readObject, a((K) readObject));
        }
        int readInt2 = objectInputStream.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            Object readObject2 = objectInputStream.readObject();
            ((Collection) linkedHashMap.get(readObject2)).add(objectInputStream.readObject());
        }
        a((Map<K, Collection<V>>) linkedHashMap);
    }
}
