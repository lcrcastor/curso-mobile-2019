package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible(emulated = true)
public final class MapMaker {
    boolean a;
    int b = -1;
    int c = -1;
    Strength d;
    Strength e;
    Equivalence<Object> f;

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    @CanIgnoreReturnValue
    public MapMaker a(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.f == null, "key equivalence was already set to %s", (Object) this.f);
        this.f = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.a = true;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Equivalence<Object> a() {
        return (Equivalence) MoreObjects.firstNonNull(this.f, d().a());
    }

    @CanIgnoreReturnValue
    public MapMaker initialCapacity(int i) {
        boolean z = false;
        Preconditions.checkState(this.b == -1, "initial capacity was already set to %s", this.b);
        if (i >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.b = i;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int b() {
        if (this.b == -1) {
            return 16;
        }
        return this.b;
    }

    @CanIgnoreReturnValue
    public MapMaker concurrencyLevel(int i) {
        boolean z = false;
        Preconditions.checkState(this.c == -1, "concurrency level was already set to %s", this.c);
        if (i > 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.c = i;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int c() {
        if (this.c == -1) {
            return 4;
        }
        return this.c;
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    public MapMaker weakKeys() {
        return a(Strength.WEAK);
    }

    /* access modifiers changed from: 0000 */
    public MapMaker a(Strength strength) {
        Preconditions.checkState(this.d == null, "Key strength was already set to %s", (Object) this.d);
        this.d = (Strength) Preconditions.checkNotNull(strength);
        if (strength != Strength.STRONG) {
            this.a = true;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Strength d() {
        return (Strength) MoreObjects.firstNonNull(this.d, Strength.STRONG);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    public MapMaker weakValues() {
        return b(Strength.WEAK);
    }

    /* access modifiers changed from: 0000 */
    public MapMaker b(Strength strength) {
        Preconditions.checkState(this.e == null, "Value strength was already set to %s", (Object) this.e);
        this.e = (Strength) Preconditions.checkNotNull(strength);
        if (strength != Strength.STRONG) {
            this.a = true;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Strength e() {
        return (Strength) MoreObjects.firstNonNull(this.e, Strength.STRONG);
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.a) {
            return new ConcurrentHashMap(b(), 0.75f, c());
        }
        return MapMakerInternalMap.a(this);
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public <K, V> MapMakerInternalMap<K, V, ?, ?> f() {
        return MapMakerInternalMap.a(this);
    }

    public String toString() {
        ToStringHelper stringHelper = MoreObjects.toStringHelper((Object) this);
        if (this.b != -1) {
            stringHelper.add("initialCapacity", this.b);
        }
        if (this.c != -1) {
            stringHelper.add("concurrencyLevel", this.c);
        }
        if (this.d != null) {
            stringHelper.add("keyStrength", (Object) Ascii.toLowerCase(this.d.toString()));
        }
        if (this.e != null) {
            stringHelper.add("valueStrength", (Object) Ascii.toLowerCase(this.e.toString()));
        }
        if (this.f != null) {
            stringHelper.addValue((Object) "keyEquivalence");
        }
        return stringHelper.toString();
    }
}
