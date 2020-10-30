package com.google.gson.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean f = true;
    private static final Comparator<Comparable> g = new Comparator<Comparable>() {
        /* renamed from: a */
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    Comparator<? super K> a;
    Node<K, V> b;
    int c;
    int d;
    final Node<K, V> e;
    private EntrySet h;
    private KeySet i;

    class EntrySet extends AbstractSet<Entry<K, V>> {
        EntrySet() {
        }

        public int size() {
            return LinkedTreeMap.this.c;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new LinkedTreeMapIterator<Entry<K, V>>() {
                {
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                /* renamed from: a */
                public Entry<K, V> next() {
                    return b();
                }
            };
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && LinkedTreeMap.this.a((Entry) obj) != null;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Node a2 = LinkedTreeMap.this.a((Entry) obj);
            if (a2 == null) {
                return false;
            }
            LinkedTreeMap.this.a(a2, true);
            return true;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {
        KeySet() {
        }

        public int size() {
            return LinkedTreeMap.this.c;
        }

        public Iterator<K> iterator() {
            return new LinkedTreeMapIterator<K>() {
                {
                    LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
                }

                public K next() {
                    return b().f;
                }
            };
        }

        public boolean contains(Object obj) {
            return LinkedTreeMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return LinkedTreeMap.this.b(obj) != null;
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }
    }

    abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
        Node<K, V> b = LinkedTreeMap.this.e.d;
        Node<K, V> c = null;
        int d = LinkedTreeMap.this.d;

        LinkedTreeMapIterator() {
        }

        public final boolean hasNext() {
            return this.b != LinkedTreeMap.this.e;
        }

        /* access modifiers changed from: 0000 */
        public final Node<K, V> b() {
            Node<K, V> node = this.b;
            if (node == LinkedTreeMap.this.e) {
                throw new NoSuchElementException();
            } else if (LinkedTreeMap.this.d != this.d) {
                throw new ConcurrentModificationException();
            } else {
                this.b = node.d;
                this.c = node;
                return node;
            }
        }

        public final void remove() {
            if (this.c == null) {
                throw new IllegalStateException();
            }
            LinkedTreeMap.this.a(this.c, true);
            this.c = null;
            this.d = LinkedTreeMap.this.d;
        }
    }

    static final class Node<K, V> implements Entry<K, V> {
        Node<K, V> a;
        Node<K, V> b;
        Node<K, V> c;
        Node<K, V> d;
        Node<K, V> e;
        final K f;
        V g;
        int h;

        Node() {
            this.f = null;
            this.e = this;
            this.d = this;
        }

        Node(Node<K, V> node, K k, Node<K, V> node2, Node<K, V> node3) {
            this.a = node;
            this.f = k;
            this.h = 1;
            this.d = node2;
            this.e = node3;
            node3.d = this;
            node2.e = this;
        }

        public K getKey() {
            return this.f;
        }

        public V getValue() {
            return this.g;
        }

        public V setValue(V v) {
            V v2 = this.g;
            this.g = v;
            return v2;
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.f != null ? this.f.equals(entry.getKey()) : entry.getKey() == null) {
                if (this.g != null ? this.g.equals(entry.getValue()) : entry.getValue() == null) {
                    z = true;
                }
            }
            return z;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            if (this.g != null) {
                i = this.g.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f);
            sb.append("=");
            sb.append(this.g);
            return sb.toString();
        }

        public Node<K, V> a() {
            Node node = this;
            for (Node node2 = this.b; node2 != null; node2 = node2.b) {
                node = node2;
            }
            return node;
        }

        public Node<K, V> b() {
            Node node = this;
            for (Node node2 = this.c; node2 != null; node2 = node2.c) {
                node = node2;
            }
            return node;
        }
    }

    public LinkedTreeMap() {
        this(g);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Comparator<? super K>, code=java.util.Comparator, for r2v0, types: [java.util.Comparator<? super K>, java.util.Comparator] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LinkedTreeMap(java.util.Comparator r2) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.c = r0
            r1.d = r0
            com.google.gson.internal.LinkedTreeMap$Node r0 = new com.google.gson.internal.LinkedTreeMap$Node
            r0.<init>()
            r1.e = r0
            if (r2 == 0) goto L_0x0012
            goto L_0x0014
        L_0x0012:
            java.util.Comparator<java.lang.Comparable> r2 = g
        L_0x0014:
            r1.a = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.LinkedTreeMap.<init>(java.util.Comparator):void");
    }

    public int size() {
        return this.c;
    }

    public V get(Object obj) {
        Node a2 = a(obj);
        if (a2 != null) {
            return a2.g;
        }
        return null;
    }

    public boolean containsKey(Object obj) {
        return a(obj) != null;
    }

    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        Node a2 = a(k, true);
        V v2 = a2.g;
        a2.g = v;
        return v2;
    }

    public void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        Node<K, V> node = this.e;
        node.e = node;
        node.d = node;
    }

    public V remove(Object obj) {
        Node b2 = b(obj);
        if (b2 != null) {
            return b2.g;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Node<K, V> a(K k, boolean z) {
        int i2;
        Node<K, V> node;
        Node<K, V> node2;
        Comparator<? super K> comparator = this.a;
        Node<K, V> node3 = this.b;
        if (node3 != null) {
            Comparable comparable = comparator == g ? (Comparable) k : null;
            while (true) {
                if (comparable != null) {
                    i2 = comparable.compareTo(node3.f);
                } else {
                    i2 = comparator.compare(k, node3.f);
                }
                if (i2 == 0) {
                    return node3;
                }
                if (i2 < 0) {
                    node2 = node3.b;
                } else {
                    node2 = node3.c;
                }
                if (node2 == null) {
                    break;
                }
                node3 = node2;
            }
        } else {
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        Node<K, V> node4 = this.e;
        if (node3 != null) {
            node = new Node<>(node3, k, node4, node4.e);
            if (i2 < 0) {
                node3.b = node;
            } else {
                node3.c = node;
            }
            b(node3, true);
        } else if (comparator != g || (k instanceof Comparable)) {
            node = new Node<>(node3, k, node4, node4.e);
            this.b = node;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(k.getClass().getName());
            sb.append(" is not Comparable");
            throw new ClassCastException(sb.toString());
        }
        this.c++;
        this.d++;
        return node;
    }

    /* access modifiers changed from: 0000 */
    public Node<K, V> a(Object obj) {
        Node<K, V> node;
        if (obj != null) {
            try {
                node = a((K) obj, false);
            } catch (ClassCastException unused) {
                return null;
            }
        } else {
            node = null;
        }
        return node;
    }

    /* access modifiers changed from: 0000 */
    public Node<K, V> a(Entry<?, ?> entry) {
        Node<K, V> a2 = a(entry.getKey());
        if (a2 != null && a((Object) a2.g, entry.getValue())) {
            return a2;
        }
        return null;
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    /* access modifiers changed from: 0000 */
    public void a(Node<K, V> node, boolean z) {
        int i2;
        if (z) {
            node.e.d = node.d;
            node.d.e = node.e;
        }
        Node<K, V> node2 = node.b;
        Node<K, V> node3 = node.c;
        Node<K, V> node4 = node.a;
        int i3 = 0;
        if (node2 == null || node3 == null) {
            if (node2 != null) {
                a(node, node2);
                node.b = null;
            } else if (node3 != null) {
                a(node, node3);
                node.c = null;
            } else {
                a(node, null);
            }
            b(node4, false);
            this.c--;
            this.d++;
            return;
        }
        Node<K, V> b2 = node2.h > node3.h ? node2.b() : node3.a();
        a(b2, false);
        Node<K, V> node5 = node.b;
        if (node5 != null) {
            i2 = node5.h;
            b2.b = node5;
            node5.a = b2;
            node.b = null;
        } else {
            i2 = 0;
        }
        Node<K, V> node6 = node.c;
        if (node6 != null) {
            i3 = node6.h;
            b2.c = node6;
            node6.a = b2;
            node.c = null;
        }
        b2.h = Math.max(i2, i3) + 1;
        a(node, b2);
    }

    /* access modifiers changed from: 0000 */
    public Node<K, V> b(Object obj) {
        Node<K, V> a2 = a(obj);
        if (a2 != null) {
            a(a2, true);
        }
        return a2;
    }

    private void a(Node<K, V> node, Node<K, V> node2) {
        Node<K, V> node3 = node.a;
        node.a = null;
        if (node2 != null) {
            node2.a = node3;
        }
        if (node3 == null) {
            this.b = node2;
        } else if (node3.b == node) {
            node3.b = node2;
        } else if (f || node3.c == node) {
            node3.c = node2;
        } else {
            throw new AssertionError();
        }
    }

    private void b(Node<K, V> node, boolean z) {
        while (node != null) {
            Node<K, V> node2 = node.b;
            Node<K, V> node3 = node.c;
            int i2 = 0;
            int i3 = node2 != null ? node2.h : 0;
            int i4 = node3 != null ? node3.h : 0;
            int i5 = i3 - i4;
            if (i5 == -2) {
                Node<K, V> node4 = node3.b;
                Node<K, V> node5 = node3.c;
                int i6 = node5 != null ? node5.h : 0;
                if (node4 != null) {
                    i2 = node4.h;
                }
                int i7 = i2 - i6;
                if (i7 == -1 || (i7 == 0 && !z)) {
                    a(node);
                } else if (f || i7 == 1) {
                    b(node3);
                    a(node);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i5 == 2) {
                Node<K, V> node6 = node2.b;
                Node<K, V> node7 = node2.c;
                int i8 = node7 != null ? node7.h : 0;
                if (node6 != null) {
                    i2 = node6.h;
                }
                int i9 = i2 - i8;
                if (i9 == 1 || (i9 == 0 && !z)) {
                    b(node);
                } else if (f || i9 == -1) {
                    a(node2);
                    b(node);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i5 == 0) {
                node.h = i3 + 1;
                if (z) {
                    return;
                }
            } else if (f || i5 == -1 || i5 == 1) {
                node.h = Math.max(i3, i4) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            node = node.a;
        }
    }

    private void a(Node<K, V> node) {
        Node<K, V> node2 = node.b;
        Node<K, V> node3 = node.c;
        Node<K, V> node4 = node3.b;
        Node<K, V> node5 = node3.c;
        node.c = node4;
        if (node4 != null) {
            node4.a = node;
        }
        a(node, node3);
        node3.b = node;
        node.a = node3;
        int i2 = 0;
        node.h = Math.max(node2 != null ? node2.h : 0, node4 != null ? node4.h : 0) + 1;
        int i3 = node.h;
        if (node5 != null) {
            i2 = node5.h;
        }
        node3.h = Math.max(i3, i2) + 1;
    }

    private void b(Node<K, V> node) {
        Node<K, V> node2 = node.b;
        Node<K, V> node3 = node.c;
        Node<K, V> node4 = node2.b;
        Node<K, V> node5 = node2.c;
        node.b = node5;
        if (node5 != null) {
            node5.a = node;
        }
        a(node, node2);
        node2.c = node;
        node.a = node2;
        int i2 = 0;
        node.h = Math.max(node3 != null ? node3.h : 0, node5 != null ? node5.h : 0) + 1;
        int i3 = node.h;
        if (node4 != null) {
            i2 = node4.h;
        }
        node2.h = Math.max(i3, i2) + 1;
    }

    public Set<Entry<K, V>> entrySet() {
        EntrySet entrySet = this.h;
        if (entrySet != null) {
            return entrySet;
        }
        EntrySet entrySet2 = new EntrySet<>();
        this.h = entrySet2;
        return entrySet2;
    }

    public Set<K> keySet() {
        KeySet keySet = this.i;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet<>();
        this.i = keySet2;
        return keySet2;
    }

    private Object writeReplace() {
        return new LinkedHashMap(this);
    }
}
