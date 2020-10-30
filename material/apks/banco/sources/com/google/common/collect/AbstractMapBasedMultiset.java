package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {
    @GwtIncompatible
    private static final long serialVersionUID = -2250766705698539974L;
    /* access modifiers changed from: private */
    public transient Map<E, Count> a;
    /* access modifiers changed from: private */
    public transient long b = ((long) super.size());

    class MapBasedMultisetIterator implements Iterator<E> {
        final Iterator<Entry<E, Count>> a;
        Entry<E, Count> b;
        int c;
        boolean d;

        MapBasedMultisetIterator() {
            this.a = AbstractMapBasedMultiset.this.a.entrySet().iterator();
        }

        public boolean hasNext() {
            return this.c > 0 || this.a.hasNext();
        }

        public E next() {
            if (this.c == 0) {
                this.b = (Entry) this.a.next();
                this.c = ((Count) this.b.getValue()).a();
            }
            this.c--;
            this.d = true;
            return this.b.getKey();
        }

        public void remove() {
            CollectPreconditions.a(this.d);
            if (((Count) this.b.getValue()).a() <= 0) {
                throw new ConcurrentModificationException();
            }
            if (((Count) this.b.getValue()).b(-1) == 0) {
                this.a.remove();
            }
            AbstractMapBasedMultiset.this.b = AbstractMapBasedMultiset.this.b - 1;
            this.d = false;
        }
    }

    static /* synthetic */ long a(AbstractMapBasedMultiset abstractMapBasedMultiset, long j) {
        long j2 = abstractMapBasedMultiset.b - j;
        abstractMapBasedMultiset.b = j2;
        return j2;
    }

    protected AbstractMapBasedMultiset(Map<E, Count> map) {
        this.a = (Map) Preconditions.checkNotNull(map);
    }

    /* access modifiers changed from: 0000 */
    public void a(Map<E, Count> map) {
        this.a = map;
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return super.entrySet();
    }

    /* access modifiers changed from: 0000 */
    public Iterator<Multiset.Entry<E>> a() {
        final Iterator it = this.a.entrySet().iterator();
        return new Iterator<Multiset.Entry<E>>() {
            Entry<E, Count> a;

            public boolean hasNext() {
                return it.hasNext();
            }

            /* renamed from: a */
            public Multiset.Entry<E> next() {
                final Entry<E, Count> entry = (Entry) it.next();
                this.a = entry;
                return new AbstractEntry<E>() {
                    public E getElement() {
                        return entry.getKey();
                    }

                    public int getCount() {
                        int i;
                        Count count = (Count) entry.getValue();
                        if (count == null || count.a() == 0) {
                            Count count2 = (Count) AbstractMapBasedMultiset.this.a.get(getElement());
                            if (count2 != null) {
                                return count2.a();
                            }
                        }
                        if (count == null) {
                            i = 0;
                        } else {
                            i = count.a();
                        }
                        return i;
                    }
                };
            }

            public void remove() {
                CollectPreconditions.a(this.a != null);
                AbstractMapBasedMultiset.a(AbstractMapBasedMultiset.this, (long) ((Count) this.a.getValue()).d(0));
                it.remove();
                this.a = null;
            }
        };
    }

    public void clear() {
        for (Count c : this.a.values()) {
            c.c(0);
        }
        this.a.clear();
        this.b = 0;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        return this.a.size();
    }

    public int size() {
        return Ints.saturatedCast(this.b);
    }

    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator();
    }

    public int count(@Nullable Object obj) {
        Count count = (Count) Maps.a(this.a, obj);
        if (count == null) {
            return 0;
        }
        return count.a();
    }

    @CanIgnoreReturnValue
    public int add(@Nullable E e, int i) {
        int i2;
        if (i == 0) {
            return count(e);
        }
        boolean z = true;
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        Count count = (Count) this.a.get(e);
        if (count == null) {
            this.a.put(e, new Count(i));
            i2 = 0;
        } else {
            i2 = count.a();
            long j = ((long) i2) + ((long) i);
            if (j > 2147483647L) {
                z = false;
            }
            Preconditions.checkArgument(z, "too many occurrences: %s", j);
            count.a(i);
        }
        this.b += (long) i;
        return i2;
    }

    @CanIgnoreReturnValue
    public int remove(@Nullable Object obj, int i) {
        if (i == 0) {
            return count(obj);
        }
        Preconditions.checkArgument(i > 0, "occurrences cannot be negative: %s", i);
        Count count = (Count) this.a.get(obj);
        if (count == null) {
            return 0;
        }
        int a2 = count.a();
        if (a2 <= i) {
            this.a.remove(obj);
            i = a2;
        }
        count.a(-i);
        this.b -= (long) i;
        return a2;
    }

    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int i) {
        int i2;
        CollectPreconditions.a(i, NewHtcHomeBadger.COUNT);
        if (i == 0) {
            i2 = a((Count) this.a.remove(e), i);
        } else {
            Count count = (Count) this.a.get(e);
            int a2 = a(count, i);
            if (count == null) {
                this.a.put(e, new Count(i));
            }
            i2 = a2;
        }
        this.b += (long) (i - i2);
        return i2;
    }

    private static int a(@Nullable Count count, int i) {
        if (count == null) {
            return 0;
        }
        return count.d(i);
    }
}
