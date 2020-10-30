package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object a = new Object();
    /* access modifiers changed from: private */
    public final Map<N, Object> b;
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public int d;

    static final class PredAndSucc {
        /* access modifiers changed from: private */
        public final Object a;

        PredAndSucc(Object obj) {
            this.a = obj;
        }
    }

    private DirectedGraphConnections(Map<N, Object> map, int i, int i2) {
        this.b = (Map) Preconditions.checkNotNull(map);
        this.c = Graphs.a(i);
        this.d = Graphs.a(i2);
        Preconditions.checkState(i <= map.size() && i2 <= map.size());
    }

    static <N, V> DirectedGraphConnections<N, V> a() {
        return new DirectedGraphConnections<>(new HashMap(4, 1.0f), 0, 0);
    }

    static <N, V> DirectedGraphConnections<N, V> a(Set<N> set, Map<N, V> map) {
        HashMap hashMap = new HashMap();
        hashMap.putAll(map);
        for (Object next : set) {
            Object put = hashMap.put(next, a);
            if (put != null) {
                hashMap.put(next, new PredAndSucc(put));
            }
        }
        return new DirectedGraphConnections<>(ImmutableMap.copyOf((Map<? extends K, ? extends V>) hashMap), set.size(), map.size());
    }

    public Set<N> b() {
        return Collections.unmodifiableSet(this.b.keySet());
    }

    public Set<N> c() {
        return new AbstractSet<N>() {
            /* renamed from: a */
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.b.entrySet().iterator();
                return new AbstractIterator<N>() {
                    /* access modifiers changed from: protected */
                    public N computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            if (DirectedGraphConnections.f(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.c;
            }

            public boolean contains(@Nullable Object obj) {
                return DirectedGraphConnections.f(DirectedGraphConnections.this.b.get(obj));
            }
        };
    }

    public Set<N> d() {
        return new AbstractSet<N>() {
            /* renamed from: a */
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.b.entrySet().iterator();
                return new AbstractIterator<N>() {
                    /* access modifiers changed from: protected */
                    public N computeNext() {
                        while (it.hasNext()) {
                            Entry entry = (Entry) it.next();
                            if (DirectedGraphConnections.g(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.d;
            }

            public boolean contains(@Nullable Object obj) {
                return DirectedGraphConnections.g(DirectedGraphConnections.this.b.get(obj));
            }
        };
    }

    public V a(Object obj) {
        V v = this.b.get(obj);
        if (v == a) {
            return null;
        }
        return v instanceof PredAndSucc ? ((PredAndSucc) v).a : v;
    }

    public void b(Object obj) {
        Object obj2 = this.b.get(obj);
        if (obj2 == a) {
            this.b.remove(obj);
            int i = this.c - 1;
            this.c = i;
            Graphs.a(i);
        } else if (obj2 instanceof PredAndSucc) {
            this.b.put(obj, ((PredAndSucc) obj2).a);
            int i2 = this.c - 1;
            this.c = i2;
            Graphs.a(i2);
        }
    }

    public V c(Object obj) {
        V v = this.b.get(obj);
        if (v == null || v == a) {
            return null;
        }
        if (v instanceof PredAndSucc) {
            this.b.put(obj, a);
            int i = this.d - 1;
            this.d = i;
            Graphs.a(i);
            return ((PredAndSucc) v).a;
        }
        this.b.remove(obj);
        int i2 = this.d - 1;
        this.d = i2;
        Graphs.a(i2);
        return v;
    }

    public void a(N n, V v) {
        Object put = this.b.put(n, a);
        if (put == null) {
            int i = this.c + 1;
            this.c = i;
            Graphs.b(i);
        } else if (put instanceof PredAndSucc) {
            this.b.put(n, put);
        } else if (put != a) {
            this.b.put(n, new PredAndSucc(put));
            int i2 = this.c + 1;
            this.c = i2;
            Graphs.b(i2);
        }
    }

    public V b(N n, V v) {
        V put = this.b.put(n, v);
        if (put == null) {
            int i = this.d + 1;
            this.d = i;
            Graphs.b(i);
            return null;
        } else if (put instanceof PredAndSucc) {
            this.b.put(n, new PredAndSucc(v));
            return ((PredAndSucc) put).a;
        } else if (put != a) {
            return put;
        } else {
            this.b.put(n, new PredAndSucc(v));
            int i2 = this.d + 1;
            this.d = i2;
            Graphs.b(i2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static boolean f(@Nullable Object obj) {
        return obj == a || (obj instanceof PredAndSucc);
    }

    /* access modifiers changed from: private */
    public static boolean g(@Nullable Object obj) {
        return (obj == a || obj == null) ? false : true;
    }
}
