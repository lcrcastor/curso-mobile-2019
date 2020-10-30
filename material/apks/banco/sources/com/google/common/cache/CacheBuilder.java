package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckReturnValue;

@GwtCompatible(emulated = true)
public final class CacheBuilder<K, V> {
    static final Supplier<? extends StatsCounter> a = Suppliers.ofInstance(new StatsCounter() {
        public void recordEviction() {
        }

        public void recordHits(int i) {
        }

        public void recordLoadException(long j) {
        }

        public void recordLoadSuccess(long j) {
        }

        public void recordMisses(int i) {
        }

        public CacheStats snapshot() {
            return CacheBuilder.b;
        }
    });
    static final CacheStats b;
    static final Supplier<StatsCounter> c = new Supplier<StatsCounter>() {
        /* renamed from: a */
        public StatsCounter get() {
            return new SimpleStatsCounter();
        }
    };
    static final Ticker d = new Ticker() {
        public long read() {
            return 0;
        }
    };
    private static final Logger u = Logger.getLogger(CacheBuilder.class.getName());
    boolean e = true;
    int f = -1;
    int g = -1;
    long h = -1;
    long i = -1;
    Weigher<? super K, ? super V> j;
    Strength k;
    Strength l;
    long m = -1;
    long n = -1;
    long o = -1;
    Equivalence<Object> p;
    Equivalence<Object> q;
    RemovalListener<? super K, ? super V> r;
    Ticker s;
    Supplier<? extends StatsCounter> t = a;

    enum NullListener implements RemovalListener<Object, Object> {
        INSTANCE;

        public void onRemoval(RemovalNotification<Object, Object> removalNotification) {
        }
    }

    enum OneWeigher implements Weigher<Object, Object> {
        INSTANCE;

        public int weigh(Object obj, Object obj2) {
            return 1;
        }
    }

    static {
        CacheStats cacheStats = new CacheStats(0, 0, 0, 0, 0, 0);
        b = cacheStats;
    }

    CacheBuilder() {
    }

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<>();
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(CacheBuilderSpec cacheBuilderSpec) {
        return cacheBuilderSpec.a().a();
    }

    @GwtIncompatible
    public static CacheBuilder<Object, Object> from(String str) {
        return from(CacheBuilderSpec.parse(str));
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public CacheBuilder<K, V> a() {
        this.e = false;
        return this;
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public CacheBuilder<K, V> a(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.p == null, "key equivalence was already set to %s", (Object) this.p);
        this.p = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Equivalence<Object> b() {
        return (Equivalence) MoreObjects.firstNonNull(this.p, h().a());
    }

    /* access modifiers changed from: 0000 */
    @GwtIncompatible
    public CacheBuilder<K, V> b(Equivalence<Object> equivalence) {
        Preconditions.checkState(this.q == null, "value equivalence was already set to %s", (Object) this.q);
        this.q = (Equivalence) Preconditions.checkNotNull(equivalence);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Equivalence<Object> c() {
        return (Equivalence) MoreObjects.firstNonNull(this.q, i().a());
    }

    public CacheBuilder<K, V> initialCapacity(int i2) {
        boolean z = false;
        Preconditions.checkState(this.f == -1, "initial capacity was already set to %s", this.f);
        if (i2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.f = i2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int d() {
        if (this.f == -1) {
            return 16;
        }
        return this.f;
    }

    public CacheBuilder<K, V> concurrencyLevel(int i2) {
        boolean z = false;
        Preconditions.checkState(this.g == -1, "concurrency level was already set to %s", this.g);
        if (i2 > 0) {
            z = true;
        }
        Preconditions.checkArgument(z);
        this.g = i2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public int e() {
        if (this.g == -1) {
            return 4;
        }
        return this.g;
    }

    public CacheBuilder<K, V> maximumSize(long j2) {
        boolean z = false;
        Preconditions.checkState(this.h == -1, "maximum size was already set to %s", this.h);
        Preconditions.checkState(this.i == -1, "maximum weight was already set to %s", this.i);
        Preconditions.checkState(this.j == null, "maximum size can not be combined with weigher");
        if (j2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.h = j2;
        return this;
    }

    @GwtIncompatible
    public CacheBuilder<K, V> maximumWeight(long j2) {
        boolean z = false;
        Preconditions.checkState(this.i == -1, "maximum weight was already set to %s", this.i);
        Preconditions.checkState(this.h == -1, "maximum size was already set to %s", this.h);
        this.i = j2;
        if (j2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum weight must not be negative");
        return this;
    }

    @GwtIncompatible
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> weigher(Weigher<? super K1, ? super V1> weigher) {
        boolean z = false;
        Preconditions.checkState(this.j == null);
        if (this.e) {
            if (this.h == -1) {
                z = true;
            }
            Preconditions.checkState(z, "weigher can not be combined with maximum size", this.h);
        }
        this.j = (Weigher) Preconditions.checkNotNull(weigher);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long f() {
        if (this.m == 0 || this.n == 0) {
            return 0;
        }
        return this.j == null ? this.h : this.i;
    }

    /* access modifiers changed from: 0000 */
    public <K1 extends K, V1 extends V> Weigher<K1, V1> g() {
        return (Weigher) MoreObjects.firstNonNull(this.j, OneWeigher.INSTANCE);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> weakKeys() {
        return a(Strength.WEAK);
    }

    /* access modifiers changed from: 0000 */
    public CacheBuilder<K, V> a(Strength strength) {
        Preconditions.checkState(this.k == null, "Key strength was already set to %s", (Object) this.k);
        this.k = (Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Strength h() {
        return (Strength) MoreObjects.firstNonNull(this.k, Strength.STRONG);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> weakValues() {
        return b(Strength.WEAK);
    }

    @GwtIncompatible
    public CacheBuilder<K, V> softValues() {
        return b(Strength.SOFT);
    }

    /* access modifiers changed from: 0000 */
    public CacheBuilder<K, V> b(Strength strength) {
        Preconditions.checkState(this.l == null, "Value strength was already set to %s", (Object) this.l);
        this.l = (Strength) Preconditions.checkNotNull(strength);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Strength i() {
        return (Strength) MoreObjects.firstNonNull(this.l, Strength.STRONG);
    }

    public CacheBuilder<K, V> expireAfterWrite(long j2, TimeUnit timeUnit) {
        boolean z = false;
        Preconditions.checkState(this.m == -1, "expireAfterWrite was already set to %s ns", this.m);
        if (j2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "duration cannot be negative: %s %s", j2, (Object) timeUnit);
        this.m = timeUnit.toNanos(j2);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long j() {
        if (this.m == -1) {
            return 0;
        }
        return this.m;
    }

    public CacheBuilder<K, V> expireAfterAccess(long j2, TimeUnit timeUnit) {
        boolean z = false;
        Preconditions.checkState(this.n == -1, "expireAfterAccess was already set to %s ns", this.n);
        if (j2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "duration cannot be negative: %s %s", j2, (Object) timeUnit);
        this.n = timeUnit.toNanos(j2);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long k() {
        if (this.n == -1) {
            return 0;
        }
        return this.n;
    }

    @GwtIncompatible
    public CacheBuilder<K, V> refreshAfterWrite(long j2, TimeUnit timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        boolean z = false;
        Preconditions.checkState(this.o == -1, "refresh was already set to %s ns", this.o);
        if (j2 > 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "duration must be positive: %s %s", j2, (Object) timeUnit);
        this.o = timeUnit.toNanos(j2);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public long l() {
        if (this.o == -1) {
            return 0;
        }
        return this.o;
    }

    public CacheBuilder<K, V> ticker(Ticker ticker) {
        Preconditions.checkState(this.s == null);
        this.s = (Ticker) Preconditions.checkNotNull(ticker);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Ticker a(boolean z) {
        if (this.s != null) {
            return this.s;
        }
        return z ? Ticker.systemTicker() : d;
    }

    @CheckReturnValue
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(RemovalListener<? super K1, ? super V1> removalListener) {
        Preconditions.checkState(this.r == null);
        this.r = (RemovalListener) Preconditions.checkNotNull(removalListener);
        return this;
    }

    /* access modifiers changed from: 0000 */
    public <K1 extends K, V1 extends V> RemovalListener<K1, V1> m() {
        return (RemovalListener) MoreObjects.firstNonNull(this.r, NullListener.INSTANCE);
    }

    public CacheBuilder<K, V> recordStats() {
        this.t = c;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Supplier<? extends StatsCounter> n() {
        return this.t;
    }

    public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> cacheLoader) {
        p();
        return new LocalLoadingCache(this, cacheLoader);
    }

    public <K1 extends K, V1 extends V> Cache<K1, V1> build() {
        p();
        o();
        return new LocalManualCache(this);
    }

    private void o() {
        Preconditions.checkState(this.o == -1, "refreshAfterWrite requires a LoadingCache");
    }

    private void p() {
        boolean z = false;
        if (this.j == null) {
            if (this.i == -1) {
                z = true;
            }
            Preconditions.checkState(z, "maximumWeight requires weigher");
        } else if (this.e) {
            if (this.i != -1) {
                z = true;
            }
            Preconditions.checkState(z, "weigher requires maximumWeight");
        } else if (this.i == -1) {
            u.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
        }
    }

    public String toString() {
        ToStringHelper stringHelper = MoreObjects.toStringHelper((Object) this);
        if (this.f != -1) {
            stringHelper.add("initialCapacity", this.f);
        }
        if (this.g != -1) {
            stringHelper.add("concurrencyLevel", this.g);
        }
        if (this.h != -1) {
            stringHelper.add("maximumSize", this.h);
        }
        if (this.i != -1) {
            stringHelper.add("maximumWeight", this.i);
        }
        if (this.m != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.m);
            sb.append("ns");
            stringHelper.add("expireAfterWrite", (Object) sb.toString());
        }
        if (this.n != -1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.n);
            sb2.append("ns");
            stringHelper.add("expireAfterAccess", (Object) sb2.toString());
        }
        if (this.k != null) {
            stringHelper.add("keyStrength", (Object) Ascii.toLowerCase(this.k.toString()));
        }
        if (this.l != null) {
            stringHelper.add("valueStrength", (Object) Ascii.toLowerCase(this.l.toString()));
        }
        if (this.p != null) {
            stringHelper.addValue((Object) "keyEquivalence");
        }
        if (this.q != null) {
            stringHelper.addValue((Object) "valueEquivalence");
        }
        if (this.r != null) {
            stringHelper.addValue((Object) "removalListener");
        }
        return stringHelper.toString();
    }
}
