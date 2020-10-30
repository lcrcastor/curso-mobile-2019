package com.google.android.gms.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class zzapf<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean f = true;
    private static final Comparator<Comparable> g = new Comparator<Comparable>() {
        /* renamed from: a */
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    Comparator<? super K> a;
    zzd<K, V> b;
    int c;
    int d;
    final zzd<K, V> e;
    private zza h;
    private zzb i;

    class zza extends AbstractSet<Entry<K, V>> {
        zza() {
        }

        public void clear() {
            zzapf.this.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Entry) && zzapf.this.a((Entry) obj) != null;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new zzc<Entry<K, V>>() {
                {
                    zzapf zzapf = zzapf.this;
                }

                /* renamed from: a */
                public Entry<K, V> next() {
                    return b();
                }
            };
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            zzd a2 = zzapf.this.a((Entry) obj);
            if (a2 == null) {
                return false;
            }
            zzapf.this.a(a2, true);
            return true;
        }

        public int size() {
            return zzapf.this.c;
        }
    }

    final class zzb extends AbstractSet<K> {
        zzb() {
        }

        public void clear() {
            zzapf.this.clear();
        }

        public boolean contains(Object obj) {
            return zzapf.this.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return new zzc<K>() {
                {
                    zzapf zzapf = zzapf.this;
                }

                public K next() {
                    return b().f;
                }
            };
        }

        public boolean remove(Object obj) {
            return zzapf.this.b(obj) != null;
        }

        public int size() {
            return zzapf.this.c;
        }
    }

    abstract class zzc<T> implements Iterator<T> {
        zzd<K, V> b;
        zzd<K, V> c;
        int d;

        private zzc() {
            this.b = zzapf.this.e.d;
            this.c = null;
            this.d = zzapf.this.d;
        }

        /* access modifiers changed from: 0000 */
        public final zzd<K, V> b() {
            zzd<K, V> zzd = this.b;
            if (zzd == zzapf.this.e) {
                throw new NoSuchElementException();
            } else if (zzapf.this.d != this.d) {
                throw new ConcurrentModificationException();
            } else {
                this.b = zzd.d;
                this.c = zzd;
                return zzd;
            }
        }

        public final boolean hasNext() {
            return this.b != zzapf.this.e;
        }

        public final void remove() {
            if (this.c == null) {
                throw new IllegalStateException();
            }
            zzapf.this.a(this.c, true);
            this.c = null;
            this.d = zzapf.this.d;
        }
    }

    static final class zzd<K, V> implements Entry<K, V> {
        zzd<K, V> a;
        zzd<K, V> b;
        zzd<K, V> c;
        zzd<K, V> d;
        zzd<K, V> e;
        final K f;
        V g;
        int h;

        zzd() {
            this.f = null;
            this.e = this;
            this.d = this;
        }

        zzd(zzd<K, V> zzd, K k, zzd<K, V> zzd2, zzd<K, V> zzd3) {
            this.a = zzd;
            this.f = k;
            this.h = 1;
            this.d = zzd2;
            this.e = zzd3;
            zzd3.d = this;
            zzd2.e = this;
        }

        public zzd<K, V> a() {
            zzd zzd = this;
            for (zzd zzd2 = this.b; zzd2 != null; zzd2 = zzd2.b) {
                zzd = zzd2;
            }
            return zzd;
        }

        public zzd<K, V> b() {
            zzd zzd = this;
            for (zzd zzd2 = this.c; zzd2 != null; zzd2 = zzd2.c) {
                zzd = zzd2;
            }
            return zzd;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.f == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!this.f.equals(entry.getKey())) {
                return false;
            }
            if (this.g == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!this.g.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() {
            return this.f;
        }

        public V getValue() {
            return this.g;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            if (this.g != null) {
                i = this.g.hashCode();
            }
            return hashCode ^ i;
        }

        public V setValue(V v) {
            V v2 = this.g;
            this.g = v;
            return v2;
        }

        public String toString() {
            String valueOf = String.valueOf(this.f);
            String valueOf2 = String.valueOf(this.g);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length());
            sb.append(valueOf);
            sb.append("=");
            sb.append(valueOf2);
            return sb.toString();
        }
    }

    public zzapf() {
        this(g);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Comparator<? super K>, code=java.util.Comparator, for r2v0, types: [java.util.Comparator<? super K>, java.util.Comparator] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzapf(java.util.Comparator r2) {
        /*
            r1 = this;
            r1.<init>()
            r0 = 0
            r1.c = r0
            r1.d = r0
            com.google.android.gms.internal.zzapf$zzd r0 = new com.google.android.gms.internal.zzapf$zzd
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzapf.<init>(java.util.Comparator):void");
    }

    private void a(zzd<K, V> zzd2) {
        zzd<K, V> zzd3 = zzd2.b;
        zzd<K, V> zzd4 = zzd2.c;
        zzd<K, V> zzd5 = zzd4.b;
        zzd<K, V> zzd6 = zzd4.c;
        zzd2.c = zzd5;
        if (zzd5 != null) {
            zzd5.a = zzd2;
        }
        a(zzd2, zzd4);
        zzd4.b = zzd2;
        zzd2.a = zzd4;
        int i2 = 0;
        zzd2.h = Math.max(zzd3 != null ? zzd3.h : 0, zzd5 != null ? zzd5.h : 0) + 1;
        int i3 = zzd2.h;
        if (zzd6 != null) {
            i2 = zzd6.h;
        }
        zzd4.h = Math.max(i3, i2) + 1;
    }

    private void a(zzd<K, V> zzd2, zzd<K, V> zzd3) {
        zzd<K, V> zzd4 = zzd2.a;
        zzd2.a = null;
        if (zzd3 != null) {
            zzd3.a = zzd4;
        }
        if (zzd4 == null) {
            this.b = zzd3;
        } else if (zzd4.b == zzd2) {
            zzd4.b = zzd3;
        } else if (f || zzd4.c == zzd2) {
            zzd4.c = zzd3;
        } else {
            throw new AssertionError();
        }
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void b(zzd<K, V> zzd2) {
        zzd<K, V> zzd3 = zzd2.b;
        zzd<K, V> zzd4 = zzd2.c;
        zzd<K, V> zzd5 = zzd3.b;
        zzd<K, V> zzd6 = zzd3.c;
        zzd2.b = zzd6;
        if (zzd6 != null) {
            zzd6.a = zzd2;
        }
        a(zzd2, zzd3);
        zzd3.c = zzd2;
        zzd2.a = zzd3;
        int i2 = 0;
        zzd2.h = Math.max(zzd4 != null ? zzd4.h : 0, zzd6 != null ? zzd6.h : 0) + 1;
        int i3 = zzd2.h;
        if (zzd5 != null) {
            i2 = zzd5.h;
        }
        zzd3.h = Math.max(i3, i2) + 1;
    }

    private void b(zzd<K, V> zzd2, boolean z) {
        while (zzd2 != null) {
            zzd<K, V> zzd3 = zzd2.b;
            zzd<K, V> zzd4 = zzd2.c;
            int i2 = 0;
            int i3 = zzd3 != null ? zzd3.h : 0;
            int i4 = zzd4 != null ? zzd4.h : 0;
            int i5 = i3 - i4;
            if (i5 == -2) {
                zzd<K, V> zzd5 = zzd4.b;
                zzd<K, V> zzd6 = zzd4.c;
                int i6 = zzd6 != null ? zzd6.h : 0;
                if (zzd5 != null) {
                    i2 = zzd5.h;
                }
                int i7 = i2 - i6;
                if (i7 != -1 && (i7 != 0 || z)) {
                    if (f || i7 == 1) {
                        b(zzd4);
                    } else {
                        throw new AssertionError();
                    }
                }
                a(zzd2);
                if (z) {
                    return;
                }
            } else if (i5 == 2) {
                zzd<K, V> zzd7 = zzd3.b;
                zzd<K, V> zzd8 = zzd3.c;
                int i8 = zzd8 != null ? zzd8.h : 0;
                if (zzd7 != null) {
                    i2 = zzd7.h;
                }
                int i9 = i2 - i8;
                if (i9 != 1 && (i9 != 0 || z)) {
                    if (f || i9 == -1) {
                        a(zzd3);
                    } else {
                        throw new AssertionError();
                    }
                }
                b(zzd2);
                if (z) {
                    return;
                }
            } else if (i5 == 0) {
                zzd2.h = i3 + 1;
                if (z) {
                    return;
                }
            } else if (f || i5 == -1 || i5 == 1) {
                zzd2.h = Math.max(i3, i4) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            zzd2 = zzd2.a;
        }
    }

    /* access modifiers changed from: 0000 */
    public zzd<K, V> a(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return a((K) obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public zzd<K, V> a(K k, boolean z) {
        int i2;
        zzd<K, V> zzd2;
        Comparator<? super K> comparator = this.a;
        zzd<K, V> zzd3 = this.b;
        if (zzd3 != null) {
            Comparable comparable = comparator == g ? (Comparable) k : null;
            while (true) {
                i2 = comparable != null ? comparable.compareTo(zzd3.f) : comparator.compare(k, zzd3.f);
                if (i2 == 0) {
                    return zzd3;
                }
                zzd<K, V> zzd4 = i2 < 0 ? zzd3.b : zzd3.c;
                if (zzd4 == null) {
                    break;
                }
                zzd3 = zzd4;
            }
        } else {
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        zzd<K, V> zzd5 = this.e;
        if (zzd3 != null) {
            zzd2 = new zzd<>(zzd3, k, zzd5, zzd5.e);
            if (i2 < 0) {
                zzd3.b = zzd2;
            } else {
                zzd3.c = zzd2;
            }
            b(zzd3, true);
        } else if (comparator != g || (k instanceof Comparable)) {
            zzd2 = new zzd<>(zzd3, k, zzd5, zzd5.e);
            this.b = zzd2;
        } else {
            throw new ClassCastException(String.valueOf(k.getClass().getName()).concat(" is not Comparable"));
        }
        this.c++;
        this.d++;
        return zzd2;
    }

    /* access modifiers changed from: 0000 */
    public zzd<K, V> a(Entry<?, ?> entry) {
        zzd<K, V> a2 = a(entry.getKey());
        if (a2 != null && a((Object) a2.g, entry.getValue())) {
            return a2;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void a(zzd<K, V> zzd2, boolean z) {
        int i2;
        if (z) {
            zzd2.e.d = zzd2.d;
            zzd2.d.e = zzd2.e;
        }
        zzd<K, V> zzd3 = zzd2.b;
        zzd<K, V> zzd4 = zzd2.c;
        zzd<K, V> zzd5 = zzd2.a;
        int i3 = 0;
        if (zzd3 == null || zzd4 == null) {
            if (zzd3 != null) {
                a(zzd2, zzd3);
                zzd2.b = null;
            } else if (zzd4 != null) {
                a(zzd2, zzd4);
                zzd2.c = null;
            } else {
                a(zzd2, null);
            }
            b(zzd5, false);
            this.c--;
            this.d++;
            return;
        }
        zzd<K, V> b2 = zzd3.h > zzd4.h ? zzd3.b() : zzd4.a();
        a(b2, false);
        zzd<K, V> zzd6 = zzd2.b;
        if (zzd6 != null) {
            i2 = zzd6.h;
            b2.b = zzd6;
            zzd6.a = b2;
            zzd2.b = null;
        } else {
            i2 = 0;
        }
        zzd<K, V> zzd7 = zzd2.c;
        if (zzd7 != null) {
            i3 = zzd7.h;
            b2.c = zzd7;
            zzd7.a = b2;
            zzd2.c = null;
        }
        b2.h = Math.max(i2, i3) + 1;
        a(zzd2, b2);
    }

    /* access modifiers changed from: 0000 */
    public zzd<K, V> b(Object obj) {
        zzd<K, V> a2 = a(obj);
        if (a2 != null) {
            a(a2, true);
        }
        return a2;
    }

    public void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        zzd<K, V> zzd2 = this.e;
        zzd2.e = zzd2;
        zzd2.d = zzd2;
    }

    public boolean containsKey(Object obj) {
        return a(obj) != null;
    }

    public Set<Entry<K, V>> entrySet() {
        zza zza2 = this.h;
        if (zza2 != null) {
            return zza2;
        }
        zza zza3 = new zza();
        this.h = zza3;
        return zza3;
    }

    public V get(Object obj) {
        zzd a2 = a(obj);
        if (a2 != null) {
            return a2.g;
        }
        return null;
    }

    public Set<K> keySet() {
        zzb zzb2 = this.i;
        if (zzb2 != null) {
            return zzb2;
        }
        zzb zzb3 = new zzb();
        this.i = zzb3;
        return zzb3;
    }

    public V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        zzd a2 = a(k, true);
        V v2 = a2.g;
        a2.g = v;
        return v2;
    }

    public V remove(Object obj) {
        zzd b2 = b(obj);
        if (b2 != null) {
            return b2.g;
        }
        return null;
    }

    public int size() {
        return this.c;
    }
}
