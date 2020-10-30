package android.arch.core.internal;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.Iterator;
import java.util.WeakHashMap;

@RestrictTo({Scope.LIBRARY_GROUP})
public class SafeIterableMap<K, V> implements Iterable<java.util.Map.Entry<K, V>> {
    /* access modifiers changed from: private */
    public Entry<K, V> a;
    private Entry<K, V> b;
    private WeakHashMap<SupportRemove<K, V>, Boolean> c = new WeakHashMap<>();
    private int d = 0;

    static class AscendingIterator<K, V> extends ListIterator<K, V> {
        AscendingIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            super(entry, entry2);
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, V> a(Entry<K, V> entry) {
            return entry.c;
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, V> b(Entry<K, V> entry) {
            return entry.d;
        }
    }

    static class DescendingIterator<K, V> extends ListIterator<K, V> {
        DescendingIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            super(entry, entry2);
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, V> a(Entry<K, V> entry) {
            return entry.d;
        }

        /* access modifiers changed from: 0000 */
        public Entry<K, V> b(Entry<K, V> entry) {
            return entry.c;
        }
    }

    static class Entry<K, V> implements java.util.Map.Entry<K, V> {
        @NonNull
        final K a;
        @NonNull
        final V b;
        Entry<K, V> c;
        Entry<K, V> d;

        Entry(@NonNull K k, @NonNull V v) {
            this.a = k;
            this.b = v;
        }

        @NonNull
        public K getKey() {
            return this.a;
        }

        @NonNull
        public V getValue() {
            return this.b;
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("=");
            sb.append(this.b);
            return sb.toString();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (!this.a.equals(entry.a) || !this.b.equals(entry.b)) {
                z = false;
            }
            return z;
        }
    }

    public class IteratorWithAdditions implements SupportRemove<K, V>, Iterator<java.util.Map.Entry<K, V>> {
        private Entry<K, V> b;
        private boolean c;

        private IteratorWithAdditions() {
            this.c = true;
        }

        public void a_(@NonNull Entry<K, V> entry) {
            if (entry == this.b) {
                this.b = this.b.d;
                this.c = this.b == null;
            }
        }

        public boolean hasNext() {
            boolean z = false;
            if (this.c) {
                if (SafeIterableMap.this.a != null) {
                    z = true;
                }
                return z;
            }
            if (!(this.b == null || this.b.c == null)) {
                z = true;
            }
            return z;
        }

        /* renamed from: a */
        public java.util.Map.Entry<K, V> next() {
            if (this.c) {
                this.c = false;
                this.b = SafeIterableMap.this.a;
            } else {
                this.b = this.b != null ? this.b.c : null;
            }
            return this.b;
        }
    }

    static abstract class ListIterator<K, V> implements SupportRemove<K, V>, Iterator<java.util.Map.Entry<K, V>> {
        Entry<K, V> a;
        Entry<K, V> b;

        /* access modifiers changed from: 0000 */
        public abstract Entry<K, V> a(Entry<K, V> entry);

        /* access modifiers changed from: 0000 */
        public abstract Entry<K, V> b(Entry<K, V> entry);

        ListIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            this.a = entry2;
            this.b = entry;
        }

        public boolean hasNext() {
            return this.b != null;
        }

        public void a_(@NonNull Entry<K, V> entry) {
            if (this.a == entry && entry == this.b) {
                this.b = null;
                this.a = null;
            }
            if (this.a == entry) {
                this.a = b(this.a);
            }
            if (this.b == entry) {
                this.b = b();
            }
        }

        private Entry<K, V> b() {
            if (this.b == this.a || this.a == null) {
                return null;
            }
            return a(this.b);
        }

        /* renamed from: a */
        public java.util.Map.Entry<K, V> next() {
            Entry<K, V> entry = this.b;
            this.b = b();
            return entry;
        }
    }

    interface SupportRemove<K, V> {
        void a_(@NonNull Entry<K, V> entry);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> get(K k) {
        Entry<K, V> entry = this.a;
        while (entry != null && !entry.a.equals(k)) {
            entry = entry.c;
        }
        return entry;
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        Entry entry = get(k);
        if (entry != null) {
            return entry.b;
        }
        put(k, v);
        return null;
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> put(@NonNull K k, @NonNull V v) {
        Entry<K, V> entry = new Entry<>(k, v);
        this.d++;
        if (this.b == null) {
            this.a = entry;
            this.b = this.a;
            return entry;
        }
        this.b.c = entry;
        entry.d = this.b;
        this.b = entry;
        return entry;
    }

    public V remove(@NonNull K k) {
        Entry entry = get(k);
        if (entry == null) {
            return null;
        }
        this.d--;
        if (!this.c.isEmpty()) {
            for (SupportRemove a_ : this.c.keySet()) {
                a_.a_(entry);
            }
        }
        if (entry.d != null) {
            entry.d.c = entry.c;
        } else {
            this.a = entry.c;
        }
        if (entry.c != null) {
            entry.c.d = entry.d;
        } else {
            this.b = entry.d;
        }
        entry.c = null;
        entry.d = null;
        return entry.b;
    }

    public int size() {
        return this.d;
    }

    @NonNull
    public Iterator<java.util.Map.Entry<K, V>> iterator() {
        AscendingIterator ascendingIterator = new AscendingIterator(this.a, this.b);
        this.c.put(ascendingIterator, Boolean.valueOf(false));
        return ascendingIterator;
    }

    public Iterator<java.util.Map.Entry<K, V>> descendingIterator() {
        DescendingIterator descendingIterator = new DescendingIterator(this.b, this.a);
        this.c.put(descendingIterator, Boolean.valueOf(false));
        return descendingIterator;
    }

    public IteratorWithAdditions iteratorWithAdditions() {
        IteratorWithAdditions iteratorWithAdditions = new IteratorWithAdditions<>();
        this.c.put(iteratorWithAdditions, Boolean.valueOf(false));
        return iteratorWithAdditions;
    }

    public java.util.Map.Entry<K, V> eldest() {
        return this.a;
    }

    public java.util.Map.Entry<K, V> newest() {
        return this.b;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (size() != safeIterableMap.size()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = safeIterableMap.iterator();
        while (it.hasNext() && it2.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            Object next = it2.next();
            if ((entry == null && next != null) || (entry != null && !entry.equals(next))) {
                return false;
            }
        }
        if (it.hasNext() || it2.hasNext()) {
            z = false;
        }
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator it = iterator();
        while (it.hasNext()) {
            sb.append(((java.util.Map.Entry) it.next()).toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
