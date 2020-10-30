package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentMap;

@GwtIncompatible
@Beta
public final class Interners {

    static class InternerFunction<E> implements Function<E, E> {
        private final Interner<E> a;

        public InternerFunction(Interner<E> interner) {
            this.a = interner;
        }

        public E apply(E e) {
            return this.a.intern(e);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof InternerFunction)) {
                return false;
            }
            return this.a.equals(((InternerFunction) obj).a);
        }
    }

    static class WeakInterner<E> implements Interner<E> {
        private final MapMakerInternalMap<E, Dummy, ?, ?> a;

        enum Dummy {
            VALUE
        }

        private WeakInterner() {
            this.a = new MapMaker().weakKeys().a(Equivalence.equals()).f();
        }

        public E intern(E e) {
            do {
                InternalEntry b = this.a.b((Object) e);
                if (b != null) {
                    E a2 = b.a();
                    if (a2 != null) {
                        return a2;
                    }
                }
            } while (((Dummy) this.a.putIfAbsent(e, Dummy.VALUE)) != null);
            return e;
        }
    }

    private Interners() {
    }

    public static <E> Interner<E> newStrongInterner() {
        final ConcurrentMap makeMap = new MapMaker().makeMap();
        return new Interner<E>() {
            public E intern(E e) {
                Object putIfAbsent = makeMap.putIfAbsent(Preconditions.checkNotNull(e), e);
                return putIfAbsent == null ? e : putIfAbsent;
            }
        };
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return new WeakInterner();
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }
}
