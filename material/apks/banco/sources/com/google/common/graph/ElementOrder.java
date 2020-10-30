package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.Map;
import javax.annotation.Nullable;

@Beta
public final class ElementOrder<T> {
    private final Type a;
    @Nullable
    private final Comparator<T> b;

    public enum Type {
        UNORDERED,
        INSERTION,
        SORTED
    }

    /* access modifiers changed from: 0000 */
    public <T1 extends T> ElementOrder<T1> a() {
        return this;
    }

    private ElementOrder(Type type, @Nullable Comparator<T> comparator) {
        this.a = (Type) Preconditions.checkNotNull(type);
        this.b = comparator;
        boolean z = false;
        if ((type == Type.SORTED) == (comparator != null)) {
            z = true;
        }
        Preconditions.checkState(z);
    }

    public static <S> ElementOrder<S> unordered() {
        return new ElementOrder<>(Type.UNORDERED, null);
    }

    public static <S> ElementOrder<S> insertion() {
        return new ElementOrder<>(Type.INSERTION, null);
    }

    public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
        return new ElementOrder<>(Type.SORTED, Ordering.natural());
    }

    public static <S> ElementOrder<S> sorted(Comparator<S> comparator) {
        return new ElementOrder<>(Type.SORTED, comparator);
    }

    public Type type() {
        return this.a;
    }

    public Comparator<T> comparator() {
        if (this.b != null) {
            return this.b;
        }
        throw new UnsupportedOperationException("This ordering does not define a comparator.");
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ElementOrder)) {
            return false;
        }
        ElementOrder elementOrder = (ElementOrder) obj;
        if (this.a != elementOrder.a || !Objects.equal(this.b, elementOrder.b)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b);
    }

    public String toString() {
        ToStringHelper add = MoreObjects.toStringHelper((Object) this).add("type", (Object) this.a);
        if (this.b != null) {
            add.add("comparator", (Object) this.b);
        }
        return add.toString();
    }

    /* access modifiers changed from: 0000 */
    public <K extends T, V> Map<K, V> a(int i) {
        switch (this.a) {
            case UNORDERED:
                return Maps.newHashMapWithExpectedSize(i);
            case INSERTION:
                return Maps.newLinkedHashMapWithExpectedSize(i);
            case SORTED:
                return Maps.newTreeMap(comparator());
            default:
                throw new AssertionError();
        }
    }
}
