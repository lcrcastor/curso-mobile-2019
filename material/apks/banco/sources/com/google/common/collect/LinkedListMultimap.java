package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true, serializable = true)
public class LinkedListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V>, Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public transient Node<K, V> a;
    /* access modifiers changed from: private */
    public transient Node<K, V> b;
    /* access modifiers changed from: private */
    public transient Map<K, KeyList<K, V>> c;
    /* access modifiers changed from: private */
    public transient int d;
    /* access modifiers changed from: private */
    public transient int e;

    static final class Node<K, V> extends AbstractMapEntry<K, V> {
        final K a;
        V b;
        Node<K, V> c;
        Node<K, V> d;
        Node<K, V> e = null;
        Node<K, V> f = null;

        Node(@Nullable K k, @Nullable V v) {
            this.a = k;
            this.b = v;
        }

        public K getKey() {
            return this.a;
        }

        public V getValue() {
            return this.b;
        }

        public V setValue(@Nullable V v) {
            V v2 = this.b;
            this.b = v;
            return v2;
        }
    }

    class ValueForKeyIterator implements ListIterator<V> {
        final Object a;
        int b;
        Node<K, V> c;
        Node<K, V> d;
        Node<K, V> e;

        ValueForKeyIterator(Object obj) {
            Node<K, V> node;
            this.a = obj;
            KeyList keyList = (KeyList) LinkedListMultimap.this.c.get(obj);
            if (keyList == null) {
                node = null;
            } else {
                node = keyList.a;
            }
            this.c = node;
        }

        public ValueForKeyIterator(Object obj, @Nullable int i) {
            int i2;
            Node<K, V> node;
            Node<K, V> node2;
            KeyList keyList = (KeyList) LinkedListMultimap.this.c.get(obj);
            if (keyList == null) {
                i2 = 0;
            } else {
                i2 = keyList.c;
            }
            Preconditions.checkPositionIndex(i, i2);
            if (i < i2 / 2) {
                if (keyList == null) {
                    node = null;
                } else {
                    node = keyList.a;
                }
                this.c = node;
                while (true) {
                    int i3 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    next();
                    i = i3;
                }
            } else {
                if (keyList == null) {
                    node2 = null;
                } else {
                    node2 = keyList.b;
                }
                this.e = node2;
                this.b = i2;
                while (true) {
                    int i4 = i + 1;
                    if (i >= i2) {
                        break;
                    }
                    previous();
                    i = i4;
                }
            }
            this.a = obj;
            this.d = null;
        }

        public boolean hasNext() {
            return this.c != null;
        }

        @CanIgnoreReturnValue
        public V next() {
            LinkedListMultimap.c((Object) this.c);
            Node<K, V> node = this.c;
            this.d = node;
            this.e = node;
            this.c = this.c.e;
            this.b++;
            return this.d.b;
        }

        public boolean hasPrevious() {
            return this.e != null;
        }

        @CanIgnoreReturnValue
        public V previous() {
            LinkedListMultimap.c((Object) this.e);
            Node<K, V> node = this.e;
            this.d = node;
            this.c = node;
            this.e = this.e.f;
            this.b--;
            return this.d.b;
        }

        public int nextIndex() {
            return this.b;
        }

        public int previousIndex() {
            return this.b - 1;
        }

        public void remove() {
            CollectPreconditions.a(this.d != null);
            if (this.d != this.c) {
                this.e = this.d.f;
                this.b--;
            } else {
                this.c = this.d.e;
            }
            LinkedListMultimap.this.a(this.d);
            this.d = null;
        }

        public void set(V v) {
            Preconditions.checkState(this.d != null);
            this.d.b = v;
        }

        public void add(V v) {
            this.e = LinkedListMultimap.this.a(this.a, v, this.c);
            this.b++;
            this.d = null;
        }
    }

    class DistinctKeyIterator implements Iterator<K> {
        final Set<K> a;
        Node<K, V> b;
        Node<K, V> c;
        int d;

        private DistinctKeyIterator() {
            this.a = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.b = LinkedListMultimap.this.a;
            this.d = LinkedListMultimap.this.e;
        }

        private void a() {
            if (LinkedListMultimap.this.e != this.d) {
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {
            a();
            return this.b != null;
        }

        public K next() {
            a();
            LinkedListMultimap.c((Object) this.b);
            this.c = this.b;
            this.a.add(this.c.a);
            do {
                this.b = this.b.c;
                if (this.b == null) {
                    break;
                }
            } while (!this.a.add(this.b.a));
            return this.c.a;
        }

        public void remove() {
            a();
            CollectPreconditions.a(this.c != null);
            LinkedListMultimap.this.b((Object) this.c.a);
            this.c = null;
            this.d = LinkedListMultimap.this.e;
        }
    }

    static class KeyList<K, V> {
        Node<K, V> a;
        Node<K, V> b;
        int c = 1;

        KeyList(Node<K, V> node) {
            this.a = node;
            this.b = node;
        }
    }

    class NodeIterator implements ListIterator<Entry<K, V>> {
        int a;
        Node<K, V> b;
        Node<K, V> c;
        Node<K, V> d;
        int e = LinkedListMultimap.this.e;

        NodeIterator(int i) {
            int size = LinkedListMultimap.this.size();
            Preconditions.checkPositionIndex(i, size);
            if (i < size / 2) {
                this.b = LinkedListMultimap.this.a;
                while (true) {
                    int i2 = i - 1;
                    if (i <= 0) {
                        break;
                    }
                    next();
                    i = i2;
                }
            } else {
                this.d = LinkedListMultimap.this.b;
                this.a = size;
                while (true) {
                    int i3 = i + 1;
                    if (i >= size) {
                        break;
                    }
                    previous();
                    i = i3;
                }
            }
            this.c = null;
        }

        private void c() {
            if (LinkedListMultimap.this.e != this.e) {
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {
            c();
            return this.b != null;
        }

        @CanIgnoreReturnValue
        /* renamed from: a */
        public Node<K, V> next() {
            c();
            LinkedListMultimap.c((Object) this.b);
            Node<K, V> node = this.b;
            this.c = node;
            this.d = node;
            this.b = this.b.c;
            this.a++;
            return this.c;
        }

        public void remove() {
            c();
            CollectPreconditions.a(this.c != null);
            if (this.c != this.b) {
                this.d = this.c.d;
                this.a--;
            } else {
                this.b = this.c.c;
            }
            LinkedListMultimap.this.a(this.c);
            this.c = null;
            this.e = LinkedListMultimap.this.e;
        }

        public boolean hasPrevious() {
            c();
            return this.d != null;
        }

        @CanIgnoreReturnValue
        /* renamed from: b */
        public Node<K, V> previous() {
            c();
            LinkedListMultimap.c((Object) this.d);
            Node<K, V> node = this.d;
            this.c = node;
            this.b = node;
            this.d = this.d.d;
            this.a--;
            return this.c;
        }

        public int nextIndex() {
            return this.a;
        }

        public int previousIndex() {
            return this.a - 1;
        }

        /* renamed from: a */
        public void set(Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        /* renamed from: b */
        public void add(Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        /* access modifiers changed from: 0000 */
        public void a(V v) {
            Preconditions.checkState(this.c != null);
            this.c.b = v;
        }
    }

    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
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

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<>();
    }

    public static <K, V> LinkedListMultimap<K, V> create(int i) {
        return new LinkedListMultimap<>(i);
    }

    public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new LinkedListMultimap<>(multimap);
    }

    LinkedListMultimap() {
        this.c = Maps.newHashMap();
    }

    private LinkedListMultimap(int i) {
        this.c = new HashMap(i);
    }

    private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size());
        putAll(multimap);
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public Node<K, V> a(@Nullable K k, @Nullable V v, @Nullable Node<K, V> node) {
        Node<K, V> node2 = new Node<>(k, v);
        if (this.a == null) {
            this.b = node2;
            this.a = node2;
            this.c.put(k, new KeyList(node2));
            this.e++;
        } else if (node == null) {
            this.b.c = node2;
            node2.d = this.b;
            this.b = node2;
            KeyList keyList = (KeyList) this.c.get(k);
            if (keyList == null) {
                this.c.put(k, new KeyList(node2));
                this.e++;
            } else {
                keyList.c++;
                Node<K, V> node3 = keyList.b;
                node3.e = node2;
                node2.f = node3;
                keyList.b = node2;
            }
        } else {
            KeyList keyList2 = (KeyList) this.c.get(k);
            keyList2.c++;
            node2.d = node.d;
            node2.f = node.f;
            node2.c = node;
            node2.e = node;
            if (node.f == null) {
                ((KeyList) this.c.get(k)).a = node2;
            } else {
                node.f.e = node2;
            }
            if (node.d == null) {
                this.a = node2;
            } else {
                node.d.c = node2;
            }
            node.d = node2;
            node.f = node2;
        }
        this.d++;
        return node2;
    }

    /* access modifiers changed from: private */
    public void a(Node<K, V> node) {
        if (node.d != null) {
            node.d.c = node.c;
        } else {
            this.a = node.c;
        }
        if (node.c != null) {
            node.c.d = node.d;
        } else {
            this.b = node.d;
        }
        if (node.f == null && node.e == null) {
            ((KeyList) this.c.remove(node.a)).c = 0;
            this.e++;
        } else {
            KeyList keyList = (KeyList) this.c.get(node.a);
            keyList.c--;
            if (node.f == null) {
                keyList.a = node.e;
            } else {
                node.f.e = node.e;
            }
            if (node.e == null) {
                keyList.b = node.f;
            } else {
                node.e.f = node.f;
            }
        }
        this.d--;
    }

    /* access modifiers changed from: private */
    public void b(@Nullable Object obj) {
        Iterators.b(new ValueForKeyIterator(obj));
    }

    /* access modifiers changed from: private */
    public static void c(@Nullable Object obj) {
        if (obj == null) {
            throw new NoSuchElementException();
        }
    }

    public int size() {
        return this.d;
    }

    public boolean isEmpty() {
        return this.a == null;
    }

    public boolean containsKey(@Nullable Object obj) {
        return this.c.containsKey(obj);
    }

    public boolean containsValue(@Nullable Object obj) {
        return values().contains(obj);
    }

    @CanIgnoreReturnValue
    public boolean put(@Nullable K k, @Nullable V v) {
        a(k, v, null);
        return true;
    }

    @CanIgnoreReturnValue
    public List<V> replaceValues(@Nullable K k, Iterable<? extends V> iterable) {
        List<V> d2 = d((Object) k);
        ValueForKeyIterator valueForKeyIterator = new ValueForKeyIterator(k);
        Iterator it = iterable.iterator();
        while (valueForKeyIterator.hasNext() && it.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.set(it.next());
        }
        while (valueForKeyIterator.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.remove();
        }
        while (it.hasNext()) {
            valueForKeyIterator.add(it.next());
        }
        return d2;
    }

    private List<V> d(@Nullable Object obj) {
        return Collections.unmodifiableList(Lists.newArrayList((Iterator<? extends E>) new ValueForKeyIterator<Object>(obj)));
    }

    @CanIgnoreReturnValue
    public List<V> removeAll(@Nullable Object obj) {
        List<V> d2 = d(obj);
        b(obj);
        return d2;
    }

    public void clear() {
        this.a = null;
        this.b = null;
        this.c.clear();
        this.d = 0;
        this.e++;
    }

    public List<V> get(@Nullable final K k) {
        return new AbstractSequentialList<V>() {
            public int size() {
                KeyList keyList = (KeyList) LinkedListMultimap.this.c.get(k);
                if (keyList == null) {
                    return 0;
                }
                return keyList.c;
            }

            public ListIterator<V> listIterator(int i) {
                return new ValueForKeyIterator(k, i);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Set<K> f() {
        return new ImprovedAbstractSet<K>() {
            public int size() {
                return LinkedListMultimap.this.c.size();
            }

            public Iterator<K> iterator() {
                return new DistinctKeyIterator();
            }

            public boolean contains(Object obj) {
                return LinkedListMultimap.this.containsKey(obj);
            }

            public boolean remove(Object obj) {
                return !LinkedListMultimap.this.removeAll(obj).isEmpty();
            }
        };
    }

    public List<V> values() {
        return (List) super.values();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public List<V> l() {
        return new AbstractSequentialList<V>() {
            public int size() {
                return LinkedListMultimap.this.d;
            }

            public ListIterator<V> listIterator(int i) {
                final NodeIterator nodeIterator = new NodeIterator(i);
                return new TransformedListIterator<Entry<K, V>, V>(nodeIterator) {
                    /* access modifiers changed from: 0000 */
                    public V a(Entry<K, V> entry) {
                        return entry.getValue();
                    }

                    public void set(V v) {
                        nodeIterator.a(v);
                    }
                };
            }
        };
    }

    public List<Entry<K, V>> entries() {
        return (List) super.entries();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public List<Entry<K, V>> j() {
        return new AbstractSequentialList<Entry<K, V>>() {
            public int size() {
                return LinkedListMultimap.this.d;
            }

            public ListIterator<Entry<K, V>> listIterator(int i) {
                return new NodeIterator(i);
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Entry<K, V>> h() {
        throw new AssertionError("should never be called");
    }

    /* access modifiers changed from: 0000 */
    public Map<K, Collection<V>> i() {
        return new AsMap(this);
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        for (Entry entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.c = Maps.newLinkedHashMap();
        int readInt = objectInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }
}
