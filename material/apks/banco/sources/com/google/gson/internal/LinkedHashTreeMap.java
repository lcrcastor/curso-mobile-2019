package com.google.gson.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean g = true;
    private static final Comparator<Comparable> h = new Comparator<Comparable>() {
        /* renamed from: a */
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    Comparator<? super K> a;
    Node<K, V>[] b;
    final Node<K, V> c;
    int d;
    int e;
    int f;
    private EntrySet i;
    private KeySet j;

    static final class AvlBuilder<K, V> {
        private Node<K, V> a;
        private int b;
        private int c;
        private int d;

        AvlBuilder() {
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            this.b = ((Integer.highestOneBit(i) * 2) - 1) - i;
            this.d = 0;
            this.c = 0;
            this.a = null;
        }

        /* access modifiers changed from: 0000 */
        public void a(Node<K, V> node) {
            node.c = null;
            node.a = null;
            node.b = null;
            node.i = 1;
            if (this.b > 0 && (this.d & 1) == 0) {
                this.d++;
                this.b--;
                this.c++;
            }
            node.a = this.a;
            this.a = node;
            this.d++;
            if (this.b > 0 && (this.d & 1) == 0) {
                this.d++;
                this.b--;
                this.c++;
            }
            int i = 4;
            while (true) {
                int i2 = i - 1;
                if ((this.d & i2) == i2) {
                    if (this.c == 0) {
                        Node<K, V> node2 = this.a;
                        Node<K, V> node3 = node2.a;
                        Node<K, V> node4 = node3.a;
                        node3.a = node4.a;
                        this.a = node3;
                        node3.b = node4;
                        node3.c = node2;
                        node3.i = node2.i + 1;
                        node4.a = node3;
                        node2.a = node3;
                    } else if (this.c == 1) {
                        Node<K, V> node5 = this.a;
                        Node<K, V> node6 = node5.a;
                        this.a = node6;
                        node6.c = node5;
                        node6.i = node5.i + 1;
                        node5.a = node6;
                        this.c = 0;
                    } else if (this.c == 2) {
                        this.c = 0;
                    }
                    i *= 2;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public Node<K, V> a() {
            Node<K, V> node = this.a;
            if (node.a == null) {
                return node;
            }
            throw new IllegalStateException();
        }
    }

    static class AvlIterator<K, V> {
        private Node<K, V> a;

        AvlIterator() {
        }

        /* access modifiers changed from: 0000 */
        public void a(Node<K, V> node) {
            Node<K, V> node2 = null;
            while (true) {
                Node<K, V> node3 = node2;
                node2 = node;
                Node<K, V> node4 = node3;
                if (node2 != null) {
                    node2.a = node4;
                    node = node2.b;
                } else {
                    this.a = node4;
                    return;
                }
            }
        }

        public Node<K, V> a() {
            Node<K, V> node = this.a;
            if (node == null) {
                return null;
            }
            Node<K, V> node2 = node.a;
            node.a = null;
            Node<K, V> node3 = node.c;
            while (true) {
                Node<K, V> node4 = node2;
                node2 = node3;
                Node<K, V> node5 = node4;
                if (node2 != null) {
                    node2.a = node5;
                    node3 = node2.b;
                } else {
                    this.a = node5;
                    return node;
                }
            }
        }
    }

    final class EntrySet extends AbstractSet<Entry<K, V>> {
        EntrySet() {
        }

        public int size() {
            return LinkedHashTreeMap.this.d;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new LinkedTreeMapIterator<Entry<K, V>>() {
                {
                    LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
                }

                /* renamed from: a */
                public Entry<K, V> next() {
                    return b();
                }
            };
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && LinkedHashTreeMap.this.a((Entry) obj) != null;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Node a2 = LinkedHashTreeMap.this.a((Entry) obj);
            if (a2 == null) {
                return false;
            }
            LinkedHashTreeMap.this.a(a2, true);
            return true;
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {
        KeySet() {
        }

        public int size() {
            return LinkedHashTreeMap.this.d;
        }

        public Iterator<K> iterator() {
            return new LinkedTreeMapIterator<K>() {
                {
                    LinkedHashTreeMap linkedHashTreeMap = LinkedHashTreeMap.this;
                }

                public K next() {
                    return b().f;
                }
            };
        }

        public boolean contains(Object obj) {
            return LinkedHashTreeMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return LinkedHashTreeMap.this.b(obj) != null;
        }

        public void clear() {
            LinkedHashTreeMap.this.clear();
        }
    }

    abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
        Node<K, V> b = LinkedHashTreeMap.this.c.d;
        Node<K, V> c = null;
        int d = LinkedHashTreeMap.this.e;

        LinkedTreeMapIterator() {
        }

        public final boolean hasNext() {
            return this.b != LinkedHashTreeMap.this.c;
        }

        /* access modifiers changed from: 0000 */
        public final Node<K, V> b() {
            Node<K, V> node = this.b;
            if (node == LinkedHashTreeMap.this.c) {
                throw new NoSuchElementException();
            } else if (LinkedHashTreeMap.this.e != this.d) {
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
            LinkedHashTreeMap.this.a(this.c, true);
            this.c = null;
            this.d = LinkedHashTreeMap.this.e;
        }
    }

    static final class Node<K, V> implements Entry<K, V> {
        Node<K, V> a;
        Node<K, V> b;
        Node<K, V> c;
        Node<K, V> d;
        Node<K, V> e;
        final K f;
        final int g;
        V h;
        int i;

        Node() {
            this.f = null;
            this.g = -1;
            this.e = this;
            this.d = this;
        }

        Node(Node<K, V> node, K k, int i2, Node<K, V> node2, Node<K, V> node3) {
            this.a = node;
            this.f = k;
            this.g = i2;
            this.i = 1;
            this.d = node2;
            this.e = node3;
            node3.d = this;
            node2.e = this;
        }

        public K getKey() {
            return this.f;
        }

        public V getValue() {
            return this.h;
        }

        public V setValue(V v) {
            V v2 = this.h;
            this.h = v;
            return v2;
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.f != null ? this.f.equals(entry.getKey()) : entry.getKey() == null) {
                if (this.h != null ? this.h.equals(entry.getValue()) : entry.getValue() == null) {
                    z = true;
                }
            }
            return z;
        }

        public int hashCode() {
            int i2 = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            if (this.h != null) {
                i2 = this.h.hashCode();
            }
            return hashCode ^ i2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f);
            sb.append("=");
            sb.append(this.h);
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

    private static int a(int i2) {
        int i3 = i2 ^ ((i2 >>> 20) ^ (i2 >>> 12));
        return (i3 >>> 4) ^ ((i3 >>> 7) ^ i3);
    }

    public LinkedHashTreeMap() {
        this(h);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Comparator<? super K>, code=java.util.Comparator, for r2v0, types: [java.util.Comparator<? super K>, java.util.Comparator] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LinkedHashTreeMap(java.util.Comparator r2) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.d = r0
            r1.e = r0
            if (r2 == 0) goto L_0x000b
            goto L_0x000d
        L_0x000b:
            java.util.Comparator<java.lang.Comparable> r2 = h
        L_0x000d:
            r1.a = r2
            com.google.gson.internal.LinkedHashTreeMap$Node r2 = new com.google.gson.internal.LinkedHashTreeMap$Node
            r2.<init>()
            r1.c = r2
            r2 = 16
            com.google.gson.internal.LinkedHashTreeMap$Node[] r2 = new com.google.gson.internal.LinkedHashTreeMap.Node[r2]
            r1.b = r2
            com.google.gson.internal.LinkedHashTreeMap$Node<K, V>[] r2 = r1.b
            int r2 = r2.length
            int r2 = r2 / 2
            com.google.gson.internal.LinkedHashTreeMap$Node<K, V>[] r0 = r1.b
            int r0 = r0.length
            int r0 = r0 / 4
            int r2 = r2 + r0
            r1.f = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.LinkedHashTreeMap.<init>(java.util.Comparator):void");
    }

    public int size() {
        return this.d;
    }

    public V get(Object obj) {
        Node a2 = a(obj);
        if (a2 != null) {
            return a2.h;
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
        V v2 = a2.h;
        a2.h = v;
        return v2;
    }

    public void clear() {
        Arrays.fill(this.b, null);
        this.d = 0;
        this.e++;
        Node<K, V> node = this.c;
        Node<K, V> node2 = node.d;
        while (node2 != node) {
            Node<K, V> node3 = node2.d;
            node2.e = null;
            node2.d = null;
            node2 = node3;
        }
        node.e = node;
        node.d = node;
    }

    public V remove(Object obj) {
        Node b2 = b(obj);
        if (b2 != null) {
            return b2.h;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Node<K, V> a(K k, boolean z) {
        int i2;
        Node<K, V> node;
        Node<K, V> node2;
        int i3;
        Node<K, V> node3;
        Comparator<? super K> comparator = this.a;
        Node<K, V>[] nodeArr = this.b;
        int a2 = a(k.hashCode());
        int length = (nodeArr.length - 1) & a2;
        Node<K, V> node4 = nodeArr[length];
        if (node4 != null) {
            Comparable comparable = comparator == h ? (Comparable) k : null;
            while (true) {
                if (comparable != null) {
                    i3 = comparable.compareTo(node4.f);
                } else {
                    i3 = comparator.compare(k, node4.f);
                }
                if (i3 == 0) {
                    return node4;
                }
                if (i3 < 0) {
                    node3 = node4.b;
                } else {
                    node3 = node4.c;
                }
                if (node3 == null) {
                    node = node4;
                    i2 = i3;
                    break;
                }
                node4 = node3;
            }
        } else {
            node = node4;
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        Node<K, V> node5 = this.c;
        if (node != null) {
            node2 = new Node<>(node, k, a2, node5, node5.e);
            if (i2 < 0) {
                node.b = node2;
            } else {
                node.c = node2;
            }
            b(node, true);
        } else if (comparator != h || (k instanceof Comparable)) {
            node2 = new Node<>(node, k, a2, node5, node5.e);
            nodeArr[length] = node2;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(k.getClass().getName());
            sb.append(" is not Comparable");
            throw new ClassCastException(sb.toString());
        }
        int i4 = this.d;
        this.d = i4 + 1;
        if (i4 > this.f) {
            a();
        }
        this.e++;
        return node2;
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
        if (a2 != null && a((Object) a2.h, entry.getValue())) {
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
            node.e = null;
            node.d = null;
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
            this.d--;
            this.e++;
            return;
        }
        Node<K, V> b2 = node2.i > node3.i ? node2.b() : node3.a();
        a(b2, false);
        Node<K, V> node5 = node.b;
        if (node5 != null) {
            i2 = node5.i;
            b2.b = node5;
            node5.a = b2;
            node.b = null;
        } else {
            i2 = 0;
        }
        Node<K, V> node6 = node.c;
        if (node6 != null) {
            i3 = node6.i;
            b2.c = node6;
            node6.a = b2;
            node.c = null;
        }
        b2.i = Math.max(i2, i3) + 1;
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
            this.b[node.g & (this.b.length - 1)] = node2;
        } else if (node3.b == node) {
            node3.b = node2;
        } else if (g || node3.c == node) {
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
            int i3 = node2 != null ? node2.i : 0;
            int i4 = node3 != null ? node3.i : 0;
            int i5 = i3 - i4;
            if (i5 == -2) {
                Node<K, V> node4 = node3.b;
                Node<K, V> node5 = node3.c;
                int i6 = node5 != null ? node5.i : 0;
                if (node4 != null) {
                    i2 = node4.i;
                }
                int i7 = i2 - i6;
                if (i7 == -1 || (i7 == 0 && !z)) {
                    a(node);
                } else if (g || i7 == 1) {
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
                int i8 = node7 != null ? node7.i : 0;
                if (node6 != null) {
                    i2 = node6.i;
                }
                int i9 = i2 - i8;
                if (i9 == 1 || (i9 == 0 && !z)) {
                    b(node);
                } else if (g || i9 == -1) {
                    a(node2);
                    b(node);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i5 == 0) {
                node.i = i3 + 1;
                if (z) {
                    return;
                }
            } else if (g || i5 == -1 || i5 == 1) {
                node.i = Math.max(i3, i4) + 1;
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
        node.i = Math.max(node2 != null ? node2.i : 0, node4 != null ? node4.i : 0) + 1;
        int i3 = node.i;
        if (node5 != null) {
            i2 = node5.i;
        }
        node3.i = Math.max(i3, i2) + 1;
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
        node.i = Math.max(node3 != null ? node3.i : 0, node5 != null ? node5.i : 0) + 1;
        int i3 = node.i;
        if (node4 != null) {
            i2 = node4.i;
        }
        node2.i = Math.max(i3, i2) + 1;
    }

    public Set<Entry<K, V>> entrySet() {
        EntrySet entrySet = this.i;
        if (entrySet != null) {
            return entrySet;
        }
        EntrySet entrySet2 = new EntrySet<>();
        this.i = entrySet2;
        return entrySet2;
    }

    public Set<K> keySet() {
        KeySet keySet = this.j;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet<>();
        this.j = keySet2;
        return keySet2;
    }

    private void a() {
        this.b = a(this.b);
        this.f = (this.b.length / 2) + (this.b.length / 4);
    }

    static <K, V> Node<K, V>[] a(Node<K, V>[] nodeArr) {
        int length = nodeArr.length;
        Node<K, V>[] nodeArr2 = new Node[(length * 2)];
        AvlIterator avlIterator = new AvlIterator();
        AvlBuilder avlBuilder = new AvlBuilder();
        AvlBuilder avlBuilder2 = new AvlBuilder();
        for (int i2 = 0; i2 < length; i2++) {
            Node<K, V> node = nodeArr[i2];
            if (node != null) {
                avlIterator.a(node);
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    Node a2 = avlIterator.a();
                    if (a2 == null) {
                        break;
                    } else if ((a2.g & length) == 0) {
                        i3++;
                    } else {
                        i4++;
                    }
                }
                avlBuilder.a(i3);
                avlBuilder2.a(i4);
                avlIterator.a(node);
                while (true) {
                    Node a3 = avlIterator.a();
                    if (a3 == null) {
                        break;
                    } else if ((a3.g & length) == 0) {
                        avlBuilder.a(a3);
                    } else {
                        avlBuilder2.a(a3);
                    }
                }
                Node<K, V> node2 = null;
                nodeArr2[i2] = i3 > 0 ? avlBuilder.a() : null;
                int i5 = i2 + length;
                if (i4 > 0) {
                    node2 = avlBuilder2.a();
                }
                nodeArr2[i5] = node2;
            }
        }
        return nodeArr2;
    }

    private Object writeReplace() {
        return new LinkedHashMap(this);
    }
}
