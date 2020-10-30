package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMakerInternalMap.InternalEntry;
import com.google.common.collect.MapMakerInternalMap.Segment;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
class MapMakerInternalMap<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends AbstractMap<K, V> implements Serializable, ConcurrentMap<K, V> {
    static final WeakValueReference<Object, Object, DummyInternalEntry> g = new WeakValueReference<Object, Object, DummyInternalEntry>() {
        /* renamed from: a */
        public DummyInternalEntry b() {
            return null;
        }

        public WeakValueReference<Object, Object, DummyInternalEntry> a(ReferenceQueue<Object> referenceQueue, DummyInternalEntry dummyInternalEntry) {
            return this;
        }

        public void clear() {
        }

        public Object get() {
            return null;
        }
    };
    private static final long serialVersionUID = 5;
    final transient int a;
    final transient int b;
    final transient Segment<K, V, E, S>[] c;
    final int d;
    final Equivalence<Object> e;
    final transient InternalEntryHelper<K, V, E, S> f;
    transient Set<K> h;
    transient Collection<V> i;
    transient Set<Entry<K, V>> j;

    static abstract class AbstractStrongKeyEntry<K, V, E extends InternalEntry<K, V, E>> implements InternalEntry<K, V, E> {
        final K a;
        final int b;
        final E c;

        AbstractStrongKeyEntry(K k, int i, @Nullable E e) {
            this.a = k;
            this.b = i;
            this.c = e;
        }

        public K a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public E c() {
            return this.c;
        }
    }

    static abstract class AbstractWeakKeyEntry<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<K> implements InternalEntry<K, V, E> {
        final int a;
        final E b;

        AbstractWeakKeyEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable E e) {
            super(k, referenceQueue);
            this.a = i;
            this.b = e;
        }

        public K a() {
            return get();
        }

        public int b() {
            return this.a;
        }

        public E c() {
            return this.b;
        }
    }

    interface InternalEntry<K, V, E extends InternalEntry<K, V, E>> {
        K a();

        int b();

        E c();

        V e();
    }

    interface InternalEntryHelper<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> {
        E a(S s, E e, @Nullable E e2);

        E a(S s, K k, int i, @Nullable E e);

        S a(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i, int i2);

        Strength a();

        void a(S s, E e, V v);

        Strength b();
    }

    static abstract class Segment<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends ReentrantLock {
        @Weak
        final MapMakerInternalMap<K, V, E, S> a;
        volatile int b;
        int c;
        int d;
        volatile AtomicReferenceArray<E> e;
        final int f;
        final AtomicInteger g = new AtomicInteger();

        /* access modifiers changed from: 0000 */
        public abstract S a();

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b() {
        }

        /* access modifiers changed from: 0000 */
        public void c() {
        }

        Segment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i, int i2) {
            this.a = mapMakerInternalMap;
            this.f = i2;
            a(a(i));
        }

        /* access modifiers changed from: 0000 */
        public void a(E e2, V v) {
            this.a.f.a(a(), e2, v);
        }

        /* access modifiers changed from: 0000 */
        public E a(E e2, E e3) {
            return this.a.f.a(a(), e2, e3);
        }

        /* access modifiers changed from: 0000 */
        public AtomicReferenceArray<E> a(int i) {
            return new AtomicReferenceArray<>(i);
        }

        /* access modifiers changed from: 0000 */
        public void a(AtomicReferenceArray<E> atomicReferenceArray) {
            this.d = (atomicReferenceArray.length() * 3) / 4;
            if (this.d == this.f) {
                this.d++;
            }
            this.e = atomicReferenceArray;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (tryLock()) {
                try {
                    b();
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void a(ReferenceQueue<K> referenceQueue) {
            int i = 0;
            do {
                Reference poll = referenceQueue.poll();
                if (poll != null) {
                    this.a.a((InternalEntry) poll);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void b(ReferenceQueue<V> referenceQueue) {
            int i = 0;
            do {
                Reference poll = referenceQueue.poll();
                if (poll != null) {
                    this.a.a((WeakValueReference) poll);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: 0000 */
        public <T> void c(ReferenceQueue<T> referenceQueue) {
            do {
            } while (referenceQueue.poll() != null);
        }

        /* access modifiers changed from: 0000 */
        public E b(int i) {
            AtomicReferenceArray<E> atomicReferenceArray = this.e;
            return (InternalEntry) atomicReferenceArray.get(i & (atomicReferenceArray.length() - 1));
        }

        /* access modifiers changed from: 0000 */
        public E a(Object obj, int i) {
            if (this.b != 0) {
                for (E b2 = b(i); b2 != null; b2 = b2.c()) {
                    if (b2.b() == i) {
                        Object a2 = b2.a();
                        if (a2 == null) {
                            d();
                        } else if (this.a.e.equivalent(obj, a2)) {
                            return b2;
                        }
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public E b(Object obj, int i) {
            return a(obj, i);
        }

        /* access modifiers changed from: 0000 */
        public V c(Object obj, int i) {
            try {
                InternalEntry b2 = b(obj, i);
                if (b2 == null) {
                    return null;
                }
                V e2 = b2.e();
                if (e2 == null) {
                    d();
                }
                g();
                return e2;
            } finally {
                g();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean d(Object obj, int i) {
            try {
                boolean z = false;
                if (this.b != 0) {
                    InternalEntry b2 = b(obj, i);
                    if (!(b2 == null || b2.e() == null)) {
                        z = true;
                    }
                    return z;
                }
                g();
                return false;
            } finally {
                g();
            }
        }

        /* access modifiers changed from: 0000 */
        public V a(K k, int i, V v, boolean z) {
            lock();
            try {
                h();
                int i2 = this.b + 1;
                if (i2 > this.d) {
                    e();
                    i2 = this.b + 1;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object a2 = internalEntry2.a();
                    if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(k, a2)) {
                        internalEntry2 = internalEntry2.c();
                    } else {
                        V e2 = internalEntry2.e();
                        if (e2 == null) {
                            this.c++;
                            a((E) internalEntry2, v);
                            this.b = this.b;
                            return null;
                        } else if (z) {
                            unlock();
                            return e2;
                        } else {
                            this.c++;
                            a((E) internalEntry2, v);
                            unlock();
                            return e2;
                        }
                    }
                }
                this.c++;
                InternalEntry a3 = this.a.f.a(a(), k, i, internalEntry);
                a((E) a3, v);
                atomicReferenceArray.set(length, a3);
                this.b = i2;
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void e() {
            AtomicReferenceArray<E> atomicReferenceArray = this.e;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i = this.b;
                AtomicReferenceArray<E> a2 = a(length << 1);
                this.d = (a2.length() * 3) / 4;
                int length2 = a2.length() - 1;
                for (int i2 = 0; i2 < length; i2++) {
                    InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(i2);
                    if (internalEntry != null) {
                        InternalEntry c2 = internalEntry.c();
                        int b2 = internalEntry.b() & length2;
                        if (c2 == null) {
                            a2.set(b2, internalEntry);
                        } else {
                            InternalEntry internalEntry2 = internalEntry;
                            while (c2 != null) {
                                int b3 = c2.b() & length2;
                                if (b3 != b2) {
                                    internalEntry2 = c2;
                                    b2 = b3;
                                }
                                c2 = c2.c();
                            }
                            a2.set(b2, internalEntry2);
                            while (internalEntry != internalEntry2) {
                                int b4 = internalEntry.b() & length2;
                                InternalEntry a3 = a((E) internalEntry, (E) (InternalEntry) a2.get(b4));
                                if (a3 != null) {
                                    a2.set(b4, a3);
                                } else {
                                    i--;
                                }
                                internalEntry = internalEntry.c();
                            }
                        }
                    }
                }
                this.e = a2;
                this.b = i;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(K k, int i, V v, V v2) {
            lock();
            try {
                h();
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object a2 = internalEntry2.a();
                    if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(k, a2)) {
                        internalEntry2 = internalEntry2.c();
                    } else {
                        Object e2 = internalEntry2.e();
                        if (e2 == null) {
                            if (a((E) internalEntry2)) {
                                int i2 = this.b;
                                this.c++;
                                int i3 = this.b - 1;
                                atomicReferenceArray.set(length, b((E) internalEntry, (E) internalEntry2));
                                this.b = i3;
                            }
                            return false;
                        } else if (this.a.b().equivalent(v, e2)) {
                            this.c++;
                            a((E) internalEntry2, v2);
                            unlock();
                            return true;
                        } else {
                            unlock();
                            return false;
                        }
                    }
                }
                unlock();
                return false;
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public V a(K k, int i, V v) {
            lock();
            try {
                h();
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object a2 = internalEntry2.a();
                    if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(k, a2)) {
                        internalEntry2 = internalEntry2.c();
                    } else {
                        V e2 = internalEntry2.e();
                        if (e2 == null) {
                            if (a((E) internalEntry2)) {
                                int i2 = this.b;
                                this.c++;
                                int i3 = this.b - 1;
                                atomicReferenceArray.set(length, b((E) internalEntry, (E) internalEntry2));
                                this.b = i3;
                            }
                            return null;
                        }
                        this.c++;
                        a((E) internalEntry2, v);
                        unlock();
                        return e2;
                    }
                }
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        @CanIgnoreReturnValue
        public V e(Object obj, int i) {
            lock();
            try {
                h();
                int i2 = this.b;
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object a2 = internalEntry2.a();
                    if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(obj, a2)) {
                        internalEntry2 = internalEntry2.c();
                    } else {
                        V e2 = internalEntry2.e();
                        if (e2 == null) {
                            if (!a((E) internalEntry2)) {
                                unlock();
                                return null;
                            }
                        }
                        this.c++;
                        InternalEntry b2 = b((E) internalEntry, (E) internalEntry2);
                        int i3 = this.b - 1;
                        atomicReferenceArray.set(length, b2);
                        this.b = i3;
                        return e2;
                    }
                }
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b(Object obj, int i, Object obj2) {
            lock();
            try {
                h();
                int i2 = this.b;
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (true) {
                    boolean z = false;
                    if (internalEntry2 != null) {
                        Object a2 = internalEntry2.a();
                        if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(obj, a2)) {
                            internalEntry2 = internalEntry2.c();
                        } else {
                            if (this.a.b().equivalent(obj2, internalEntry2.e())) {
                                z = true;
                            } else if (!a((E) internalEntry2)) {
                                unlock();
                                return false;
                            }
                            this.c++;
                            int i3 = this.b - 1;
                            atomicReferenceArray.set(length, b((E) internalEntry, (E) internalEntry2));
                            this.b = i3;
                            return z;
                        }
                    } else {
                        unlock();
                        return false;
                    }
                }
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        public void f() {
            if (this.b != 0) {
                lock();
                try {
                    AtomicReferenceArray<E> atomicReferenceArray = this.e;
                    for (int i = 0; i < atomicReferenceArray.length(); i++) {
                        atomicReferenceArray.set(i, null);
                    }
                    c();
                    this.g.set(0);
                    this.c++;
                    this.b = 0;
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public E b(E e2, E e3) {
            int i = this.b;
            E c2 = e3.c();
            while (e2 != e3) {
                E a2 = a(e2, c2);
                if (a2 != null) {
                    c2 = a2;
                } else {
                    i--;
                }
                e2 = e2.c();
            }
            this.b = i;
            return c2;
        }

        /* access modifiers changed from: 0000 */
        @CanIgnoreReturnValue
        public boolean a(E e2, int i) {
            lock();
            try {
                int i2 = this.b;
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = i & (atomicReferenceArray.length() - 1);
                E e3 = (InternalEntry) atomicReferenceArray.get(length);
                for (E e4 = e3; e4 != null; e4 = e4.c()) {
                    if (e4 == e2) {
                        this.c++;
                        InternalEntry b2 = b(e3, e4);
                        int i3 = this.b - 1;
                        atomicReferenceArray.set(length, b2);
                        this.b = i3;
                        return true;
                    }
                }
                unlock();
                return false;
            } finally {
                unlock();
            }
        }

        /* access modifiers changed from: 0000 */
        @CanIgnoreReturnValue
        public boolean a(K k, int i, WeakValueReference<K, V, E> weakValueReference) {
            lock();
            try {
                int i2 = this.b;
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int length = (atomicReferenceArray.length() - 1) & i;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object a2 = internalEntry2.a();
                    if (internalEntry2.b() != i || a2 == null || !this.a.e.equivalent(k, a2)) {
                        internalEntry2 = internalEntry2.c();
                    } else if (((WeakValueEntry) internalEntry2).d() == weakValueReference) {
                        this.c++;
                        int i3 = this.b - 1;
                        atomicReferenceArray.set(length, b((E) internalEntry, (E) internalEntry2));
                        this.b = i3;
                        return true;
                    } else {
                        unlock();
                        return false;
                    }
                }
                unlock();
                return false;
            } finally {
                unlock();
            }
        }

        static <K, V, E extends InternalEntry<K, V, E>> boolean a(E e2) {
            return e2.e() == null;
        }

        /* access modifiers changed from: 0000 */
        @Nullable
        public V b(E e2) {
            if (e2.a() == null) {
                d();
                return null;
            }
            V e3 = e2.e();
            if (e3 != null) {
                return e3;
            }
            d();
            return null;
        }

        /* access modifiers changed from: 0000 */
        public void g() {
            if ((this.g.incrementAndGet() & 63) == 0) {
                i();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("this")
        public void h() {
            j();
        }

        /* access modifiers changed from: 0000 */
        public void i() {
            j();
        }

        /* access modifiers changed from: 0000 */
        public void j() {
            if (tryLock()) {
                try {
                    b();
                    this.g.set(0);
                } finally {
                    unlock();
                }
            }
        }
    }

    static final class StrongKeyStrongValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, StrongKeyStrongValueEntry<K, V>> {
        @Nullable
        private volatile V d = null;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> a = new Helper<>();

            Helper() {
            }

            static <K, V> Helper<K, V> c() {
                return a;
            }

            public Strength a() {
                return Strength.STRONG;
            }

            public Strength b() {
                return Strength.STRONG;
            }

            /* renamed from: b */
            public StrongKeyStrongValueSegment<K, V> a(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new StrongKeyStrongValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public StrongKeyStrongValueEntry<K, V> a(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2) {
                return strongKeyStrongValueEntry.a(strongKeyStrongValueEntry2);
            }

            public void a(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, V v) {
                strongKeyStrongValueEntry.a(v);
            }

            public StrongKeyStrongValueEntry<K, V> a(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, K k, int i, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
                return new StrongKeyStrongValueEntry<>(k, i, strongKeyStrongValueEntry);
            }
        }

        StrongKeyStrongValueEntry(K k, int i, @Nullable StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            super(k, i, strongKeyStrongValueEntry);
        }

        @Nullable
        public V e() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public void a(V v) {
            this.d = v;
        }

        /* access modifiers changed from: 0000 */
        public StrongKeyStrongValueEntry<K, V> a(StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2 = new StrongKeyStrongValueEntry<>(this.a, this.b, strongKeyStrongValueEntry);
            strongKeyStrongValueEntry2.d = this.d;
            return strongKeyStrongValueEntry2;
        }
    }

    static final class StrongKeyWeakValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, StrongKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> d = MapMakerInternalMap.a();

        static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> a = new Helper<>();

            Helper() {
            }

            static <K, V> Helper<K, V> c() {
                return a;
            }

            public Strength a() {
                return Strength.STRONG;
            }

            public Strength b() {
                return Strength.WEAK;
            }

            /* renamed from: b */
            public StrongKeyWeakValueSegment<K, V> a(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new StrongKeyWeakValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public StrongKeyWeakValueEntry<K, V> a(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2) {
                if (Segment.a(strongKeyWeakValueEntry)) {
                    return null;
                }
                return strongKeyWeakValueEntry.a(strongKeyWeakValueSegment.h, strongKeyWeakValueEntry2);
            }

            public void a(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, V v) {
                strongKeyWeakValueEntry.a(v, strongKeyWeakValueSegment.h);
            }

            public StrongKeyWeakValueEntry<K, V> a(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, K k, int i, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
                return new StrongKeyWeakValueEntry<>(k, i, strongKeyWeakValueEntry);
            }
        }

        StrongKeyWeakValueEntry(K k, int i, @Nullable StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            super(k, i, strongKeyWeakValueEntry);
        }

        public V e() {
            return this.d.get();
        }

        /* access modifiers changed from: 0000 */
        public void a(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> weakValueReference = this.d;
            this.d = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        /* access modifiers changed from: 0000 */
        public StrongKeyWeakValueEntry<K, V> a(ReferenceQueue<V> referenceQueue, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2 = new StrongKeyWeakValueEntry<>(this.a, this.b, strongKeyWeakValueEntry);
            strongKeyWeakValueEntry2.d = this.d.a(referenceQueue, strongKeyWeakValueEntry2);
            return strongKeyWeakValueEntry2;
        }

        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> d() {
            return this.d;
        }
    }

    interface StrongValueEntry extends InternalEntry {
    }

    static final class WeakKeyStrongValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, WeakKeyStrongValueEntry<K, V>> {
        @Nullable
        private volatile V c = null;

        static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
            private static final Helper<?, ?> a = new Helper<>();

            Helper() {
            }

            static <K, V> Helper<K, V> c() {
                return a;
            }

            public Strength a() {
                return Strength.WEAK;
            }

            public Strength b() {
                return Strength.STRONG;
            }

            /* renamed from: b */
            public WeakKeyStrongValueSegment<K, V> a(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new WeakKeyStrongValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public WeakKeyStrongValueEntry<K, V> a(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2) {
                if (weakKeyStrongValueEntry.a() == null) {
                    return null;
                }
                return weakKeyStrongValueEntry.a(weakKeyStrongValueSegment.h, weakKeyStrongValueEntry2);
            }

            public void a(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, V v) {
                weakKeyStrongValueEntry.a(v);
            }

            public WeakKeyStrongValueEntry<K, V> a(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, K k, int i, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                return new WeakKeyStrongValueEntry<>(weakKeyStrongValueSegment.h, k, i, weakKeyStrongValueEntry);
            }
        }

        WeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            super(referenceQueue, k, i, weakKeyStrongValueEntry);
        }

        @Nullable
        public V e() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public void a(V v) {
            this.c = v;
        }

        /* access modifiers changed from: 0000 */
        public WeakKeyStrongValueEntry<K, V> a(ReferenceQueue<K> referenceQueue, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2 = new WeakKeyStrongValueEntry<>(referenceQueue, a(), this.a, weakKeyStrongValueEntry);
            weakKeyStrongValueEntry2.a(this.c);
            return weakKeyStrongValueEntry2;
        }
    }

    static final class WeakKeyWeakValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, WeakKeyWeakValueEntry<K, V>> {
        private volatile WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> c = MapMakerInternalMap.a();

        static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
            private static final Helper<?, ?> a = new Helper<>();

            Helper() {
            }

            static <K, V> Helper<K, V> c() {
                return a;
            }

            public Strength a() {
                return Strength.WEAK;
            }

            public Strength b() {
                return Strength.WEAK;
            }

            /* renamed from: b */
            public WeakKeyWeakValueSegment<K, V> a(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new WeakKeyWeakValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public WeakKeyWeakValueEntry<K, V> a(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2) {
                if (weakKeyWeakValueEntry.a() != null && !Segment.a(weakKeyWeakValueEntry)) {
                    return weakKeyWeakValueEntry.a(weakKeyWeakValueSegment.h, weakKeyWeakValueSegment.i, weakKeyWeakValueEntry2);
                }
                return null;
            }

            public void a(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, V v) {
                weakKeyWeakValueEntry.a(v, weakKeyWeakValueSegment.i);
            }

            public WeakKeyWeakValueEntry<K, V> a(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, K k, int i, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
                return new WeakKeyWeakValueEntry<>(weakKeyWeakValueSegment.h, k, i, weakKeyWeakValueEntry);
            }
        }

        WeakKeyWeakValueEntry(ReferenceQueue<K> referenceQueue, K k, int i, @Nullable WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            super(referenceQueue, k, i, weakKeyWeakValueEntry);
        }

        public V e() {
            return this.c.get();
        }

        /* access modifiers changed from: 0000 */
        public WeakKeyWeakValueEntry<K, V> a(ReferenceQueue<K> referenceQueue, ReferenceQueue<V> referenceQueue2, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2 = new WeakKeyWeakValueEntry<>(referenceQueue, a(), this.a, weakKeyWeakValueEntry);
            weakKeyWeakValueEntry2.c = this.c.a(referenceQueue2, weakKeyWeakValueEntry2);
            return weakKeyWeakValueEntry2;
        }

        /* access modifiers changed from: 0000 */
        public void a(V v, ReferenceQueue<V> referenceQueue) {
            WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> weakValueReference = this.c;
            this.c = new WeakValueReferenceImpl(referenceQueue, v, this);
            weakValueReference.clear();
        }

        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> d() {
            return this.c;
        }
    }

    interface WeakValueEntry<K, V, E extends InternalEntry<K, V, E>> extends InternalEntry<K, V, E> {
        WeakValueReference<K, V, E> d();
    }

    interface WeakValueReference<K, V, E extends InternalEntry<K, V, E>> {
        WeakValueReference<K, V, E> a(ReferenceQueue<V> referenceQueue, E e);

        E b();

        void clear();

        @Nullable
        V get();
    }

    final class WriteThroughEntry extends AbstractMapEntry<K, V> {
        final K a;
        V b;

        WriteThroughEntry(K k, V v) {
            this.a = k;
            this.b = v;
        }

        public K getKey() {
            return this.a;
        }

        public V getValue() {
            return this.b;
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (this.a.equals(entry.getKey()) && this.b.equals(entry.getValue())) {
                z = true;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public V setValue(V v) {
            V put = MapMakerInternalMap.this.put(this.a, v);
            this.b = v;
            return put;
        }
    }

    static abstract class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
        private static final long serialVersionUID = 3;
        final Strength a;
        final Strength b;
        final Equivalence<Object> c;
        final Equivalence<Object> d;
        final int e;
        transient ConcurrentMap<K, V> f;

        AbstractSerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i, ConcurrentMap<K, V> concurrentMap) {
            this.a = strength;
            this.b = strength2;
            this.c = equivalence;
            this.d = equivalence2;
            this.e = i;
            this.f = concurrentMap;
        }

        /* access modifiers changed from: protected */
        public ConcurrentMap<K, V> delegate() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public void a(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeInt(this.f.size());
            for (Entry entry : this.f.entrySet()) {
                objectOutputStream.writeObject(entry.getKey());
                objectOutputStream.writeObject(entry.getValue());
            }
            objectOutputStream.writeObject(null);
        }

        /* access modifiers changed from: 0000 */
        public MapMaker a(ObjectInputStream objectInputStream) {
            return new MapMaker().initialCapacity(objectInputStream.readInt()).a(this.a).b(this.b).a(this.c).concurrencyLevel(this.e);
        }

        /* access modifiers changed from: 0000 */
        public void b(ObjectInputStream objectInputStream) {
            while (true) {
                Object readObject = objectInputStream.readObject();
                if (readObject != null) {
                    this.f.put(readObject, objectInputStream.readObject());
                } else {
                    return;
                }
            }
        }
    }

    static final class DummyInternalEntry implements InternalEntry<Object, Object, DummyInternalEntry> {
        private DummyInternalEntry() {
            throw new AssertionError();
        }

        /* renamed from: d */
        public DummyInternalEntry c() {
            throw new AssertionError();
        }

        public int b() {
            throw new AssertionError();
        }

        public Object a() {
            throw new AssertionError();
        }

        public Object e() {
            throw new AssertionError();
        }
    }

    final class EntryIterator extends HashIterator<Entry<K, V>> {
        EntryIterator() {
            super();
        }

        /* renamed from: a */
        public Entry<K, V> next() {
            return e();
        }
    }

    final class EntrySet extends SafeToArraySet<Entry<K, V>> {
        EntrySet() {
            super();
        }

        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public boolean contains(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            if (key == null) {
                return false;
            }
            Object obj2 = MapMakerInternalMap.this.get(key);
            if (obj2 != null && MapMakerInternalMap.this.b().equivalent(entry.getValue(), obj2)) {
                z = true;
            }
            return z;
        }

        public boolean remove(Object obj) {
            boolean z = false;
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            Object key = entry.getKey();
            if (key != null && MapMakerInternalMap.this.remove(key, entry.getValue())) {
                z = true;
            }
            return z;
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }
    }

    abstract class HashIterator<T> implements Iterator<T> {
        int b;
        int c = -1;
        Segment<K, V, E, S> d;
        AtomicReferenceArray<E> e;
        E f;
        WriteThroughEntry g;
        WriteThroughEntry h;

        HashIterator() {
            this.b = MapMakerInternalMap.this.c.length - 1;
            b();
        }

        /* access modifiers changed from: 0000 */
        public final void b() {
            this.g = null;
            if (!c() && !d()) {
                while (this.b >= 0) {
                    Segment<K, V, E, S>[] segmentArr = MapMakerInternalMap.this.c;
                    int i2 = this.b;
                    this.b = i2 - 1;
                    this.d = segmentArr[i2];
                    if (this.d.b != 0) {
                        this.e = this.d.e;
                        this.c = this.e.length() - 1;
                        if (d()) {
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            if (this.f != null) {
                do {
                    this.f = this.f.c();
                    if (this.f != null) {
                    }
                } while (!a(this.f));
                return true;
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean d() {
            while (this.c >= 0) {
                AtomicReferenceArray<E> atomicReferenceArray = this.e;
                int i2 = this.c;
                this.c = i2 - 1;
                E e2 = (InternalEntry) atomicReferenceArray.get(i2);
                this.f = e2;
                if (e2 != null && (a(this.f) || c())) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(E e2) {
            boolean z;
            try {
                Object a = e2.a();
                Object b2 = MapMakerInternalMap.this.b(e2);
                if (b2 != null) {
                    this.g = new WriteThroughEntry<>(a, b2);
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } finally {
                this.d.g();
            }
        }

        public boolean hasNext() {
            return this.g != null;
        }

        /* access modifiers changed from: 0000 */
        public WriteThroughEntry e() {
            if (this.g == null) {
                throw new NoSuchElementException();
            }
            this.h = this.g;
            b();
            return this.h;
        }

        public void remove() {
            CollectPreconditions.a(this.h != null);
            MapMakerInternalMap.this.remove(this.h.getKey());
            this.h = null;
        }
    }

    final class KeyIterator extends HashIterator<K> {
        KeyIterator() {
            super();
        }

        public K next() {
            return e().getKey();
        }
    }

    final class KeySet extends SafeToArraySet<K> {
        KeySet() {
            super();
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            return MapMakerInternalMap.this.remove(obj) != null;
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }
    }

    static abstract class SafeToArraySet<E> extends AbstractSet<E> {
        private SafeToArraySet() {
        }

        public Object[] toArray() {
            return MapMakerInternalMap.b((Collection<E>) this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return MapMakerInternalMap.b((Collection<E>) this).toArray(eArr);
        }
    }

    static final class SerializationProxy<K, V> extends AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 3;

        SerializationProxy(Strength strength, Strength strength2, Equivalence<Object> equivalence, Equivalence<Object> equivalence2, int i, ConcurrentMap<K, V> concurrentMap) {
            super(strength, strength2, equivalence, equivalence2, i, concurrentMap);
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.defaultWriteObject();
            a(objectOutputStream);
        }

        private void readObject(ObjectInputStream objectInputStream) {
            objectInputStream.defaultReadObject();
            this.f = a(objectInputStream).makeMap();
            b(objectInputStream);
        }

        private Object readResolve() {
            return this.f;
        }
    }

    enum Strength {
        STRONG {
            /* access modifiers changed from: 0000 */
            public Equivalence<Object> a() {
                return Equivalence.equals();
            }
        },
        WEAK {
            /* access modifiers changed from: 0000 */
            public Equivalence<Object> a() {
                return Equivalence.identity();
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract Equivalence<Object> a();
    }

    static final class StrongKeyStrongValueSegment<K, V> extends Segment<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
        /* access modifiers changed from: 0000 */
        /* renamed from: k */
        public StrongKeyStrongValueSegment<K, V> a() {
            return this;
        }

        StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }
    }

    static final class StrongKeyWeakValueSegment<K, V> extends Segment<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
        /* access modifiers changed from: private */
        public final ReferenceQueue<V> h = new ReferenceQueue<>();

        /* access modifiers changed from: 0000 */
        /* renamed from: k */
        public StrongKeyWeakValueSegment<K, V> a() {
            return this;
        }

        StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            b(this.h);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            c(this.h);
        }
    }

    final class ValueIterator extends HashIterator<V> {
        ValueIterator() {
            super();
        }

        public V next() {
            return e().getValue();
        }
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsValue(obj);
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        public Object[] toArray() {
            return MapMakerInternalMap.b((Collection<E>) this).toArray();
        }

        public <E> E[] toArray(E[] eArr) {
            return MapMakerInternalMap.b((Collection<E>) this).toArray(eArr);
        }
    }

    static final class WeakKeyStrongValueSegment<K, V> extends Segment<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
        /* access modifiers changed from: private */
        public final ReferenceQueue<K> h = new ReferenceQueue<>();

        /* access modifiers changed from: 0000 */
        /* renamed from: k */
        public WeakKeyStrongValueSegment<K, V> a() {
            return this;
        }

        WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            a(this.h);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            c(this.h);
        }
    }

    static final class WeakKeyWeakValueSegment<K, V> extends Segment<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
        /* access modifiers changed from: private */
        public final ReferenceQueue<K> h = new ReferenceQueue<>();
        /* access modifiers changed from: private */
        public final ReferenceQueue<V> i = new ReferenceQueue<>();

        /* access modifiers changed from: 0000 */
        /* renamed from: k */
        public WeakKeyWeakValueSegment<K, V> a() {
            return this;
        }

        WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i2, int i3) {
            super(mapMakerInternalMap, i2, i3);
        }

        /* access modifiers changed from: 0000 */
        public void b() {
            a(this.h);
            b(this.i);
        }

        /* access modifiers changed from: 0000 */
        public void c() {
            c(this.h);
        }
    }

    static final class WeakValueReferenceImpl<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<V> implements WeakValueReference<K, V, E> {
        final E a;

        WeakValueReferenceImpl(ReferenceQueue<V> referenceQueue, V v, E e) {
            super(v, referenceQueue);
            this.a = e;
        }

        public E b() {
            return this.a;
        }

        public WeakValueReference<K, V, E> a(ReferenceQueue<V> referenceQueue, E e) {
            return new WeakValueReferenceImpl(referenceQueue, get(), e);
        }
    }

    static int a(int i2) {
        int i3 = i2 + ((i2 << 15) ^ -12931);
        int i4 = i3 ^ (i3 >>> 10);
        int i5 = i4 + (i4 << 3);
        int i6 = i5 ^ (i5 >>> 6);
        int i7 = i6 + (i6 << 2) + (i6 << 14);
        return i7 ^ (i7 >>> 16);
    }

    private MapMakerInternalMap(MapMaker mapMaker, InternalEntryHelper<K, V, E, S> internalEntryHelper) {
        this.d = Math.min(mapMaker.c(), 65536);
        this.e = mapMaker.a();
        this.f = internalEntryHelper;
        int min = Math.min(mapMaker.b(), 1073741824);
        int i2 = 1;
        int i3 = 1;
        int i4 = 0;
        while (i3 < this.d) {
            i4++;
            i3 <<= 1;
        }
        this.b = 32 - i4;
        this.a = i3 - 1;
        this.c = c(i3);
        int i5 = min / i3;
        if (i3 * i5 < min) {
            i5++;
        }
        while (i2 < i5) {
            i2 <<= 1;
        }
        for (int i6 = 0; i6 < this.c.length; i6++) {
            this.c[i6] = a(i2, -1);
        }
    }

    static <K, V> MapMakerInternalMap<K, V, ? extends InternalEntry<K, V, ?>, ?> a(MapMaker mapMaker) {
        if (mapMaker.d() == Strength.STRONG && mapMaker.e() == Strength.STRONG) {
            return new MapMakerInternalMap<>(mapMaker, Helper.c());
        }
        if (mapMaker.d() == Strength.STRONG && mapMaker.e() == Strength.WEAK) {
            return new MapMakerInternalMap<>(mapMaker, Helper.c());
        }
        if (mapMaker.d() == Strength.WEAK && mapMaker.e() == Strength.STRONG) {
            return new MapMakerInternalMap<>(mapMaker, Helper.c());
        }
        if (mapMaker.d() == Strength.WEAK && mapMaker.e() == Strength.WEAK) {
            return new MapMakerInternalMap<>(mapMaker, Helper.c());
        }
        throw new AssertionError();
    }

    static <K, V, E extends InternalEntry<K, V, E>> WeakValueReference<K, V, E> a() {
        return g;
    }

    /* access modifiers changed from: 0000 */
    public int a(Object obj) {
        return a(this.e.hash(obj));
    }

    /* access modifiers changed from: 0000 */
    public void a(WeakValueReference<K, V, E> weakValueReference) {
        InternalEntry b2 = weakValueReference.b();
        int b3 = b2.b();
        b(b3).a(b2.a(), b3, weakValueReference);
    }

    /* access modifiers changed from: 0000 */
    public void a(E e2) {
        int b2 = e2.b();
        b(b2).a(e2, b2);
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V, E, S> b(int i2) {
        return this.c[(i2 >>> this.b) & this.a];
    }

    /* access modifiers changed from: 0000 */
    public Segment<K, V, E, S> a(int i2, int i3) {
        return this.f.a(this, i2, i3);
    }

    /* access modifiers changed from: 0000 */
    public V b(E e2) {
        if (e2.a() == null) {
            return null;
        }
        V e3 = e2.e();
        if (e3 == null) {
            return null;
        }
        return e3;
    }

    /* access modifiers changed from: 0000 */
    public final Segment<K, V, E, S>[] c(int i2) {
        return new Segment[i2];
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public Equivalence<Object> b() {
        return this.f.b().a();
    }

    public boolean isEmpty() {
        Segment<K, V, E, S>[] segmentArr = this.c;
        long j2 = 0;
        int i2 = 0;
        while (i2 < segmentArr.length) {
            if (segmentArr[i2].b != 0) {
                return false;
            }
            i2++;
            j2 += (long) segmentArr[i2].c;
        }
        if (j2 != 0) {
            int i3 = 0;
            while (i3 < segmentArr.length) {
                if (segmentArr[i3].b != 0) {
                    return false;
                }
                i3++;
                j2 -= (long) segmentArr[i3].c;
            }
            if (j2 != 0) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        Segment<K, V, E, S>[] segmentArr = this.c;
        long j2 = 0;
        int i2 = 0;
        while (i2 < segmentArr.length) {
            i2++;
            j2 += (long) segmentArr[i2].b;
        }
        return Ints.saturatedCast(j2);
    }

    public V get(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).c(obj, a2);
    }

    /* access modifiers changed from: 0000 */
    public E b(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).a(obj, a2);
    }

    public boolean containsKey(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).d(obj, a2);
    }

    public boolean containsValue(@Nullable Object obj) {
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        Segment<K, V, E, S>[] segmentArr = this.c;
        long j2 = -1;
        int i2 = 0;
        while (i2 < 3) {
            int length = segmentArr.length;
            long j3 = 0;
            int i3 = 0;
            while (i3 < length) {
                Segment<K, V, E, S> segment = segmentArr[i3];
                int i4 = segment.b;
                AtomicReferenceArray<E> atomicReferenceArray = segment.e;
                for (int i5 = 0; i5 < atomicReferenceArray.length(); i5++) {
                    for (InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(i5); internalEntry != null; internalEntry = internalEntry.c()) {
                        Object b2 = segment.b(internalEntry);
                        if (b2 != null && b().equivalent(obj2, b2)) {
                            return true;
                        }
                    }
                }
                i3++;
                j3 += (long) segment.c;
            }
            if (j3 == j2) {
                break;
            }
            i2++;
            j2 = j3;
        }
        return false;
    }

    @CanIgnoreReturnValue
    public V put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int a2 = a((Object) k);
        return b(a2).a(k, a2, v, false);
    }

    @CanIgnoreReturnValue
    public V putIfAbsent(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int a2 = a((Object) k);
        return b(a2).a(k, a2, v, true);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @CanIgnoreReturnValue
    public V remove(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        int a2 = a(obj);
        return b(a2).e(obj, a2);
    }

    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null || obj2 == null) {
            return false;
        }
        int a2 = a(obj);
        return b(a2).b(obj, a2, obj2);
    }

    @CanIgnoreReturnValue
    public boolean replace(K k, @Nullable V v, V v2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return false;
        }
        int a2 = a((Object) k);
        return b(a2).a(k, a2, v, v2);
    }

    @CanIgnoreReturnValue
    public V replace(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        int a2 = a((Object) k);
        return b(a2).a(k, a2, v);
    }

    public void clear() {
        for (Segment<K, V, E, S> f2 : this.c) {
            f2.f();
        }
    }

    public Set<K> keySet() {
        Set<K> set = this.h;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.h = keySet;
        return keySet;
    }

    public Collection<V> values() {
        Collection<V> collection = this.i;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.i = values;
        return values;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = this.j;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.j = entrySet;
        return entrySet;
    }

    /* access modifiers changed from: private */
    public static <E> ArrayList<E> b(Collection<E> collection) {
        ArrayList<E> arrayList = new ArrayList<>(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public Object writeReplace() {
        SerializationProxy serializationProxy = new SerializationProxy(this.f.a(), this.f.b(), this.e, this.f.b().a(), this.d, this);
        return serializationProxy;
    }
}
