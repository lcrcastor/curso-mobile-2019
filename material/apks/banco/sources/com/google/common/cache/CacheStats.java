package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@GwtCompatible
public final class CacheStats {
    private final long a;
    private final long b;
    private final long c;
    private final long d;
    private final long e;
    private final long f;

    public CacheStats(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j;
        long j8 = j2;
        long j9 = j3;
        long j10 = j4;
        long j11 = j5;
        long j12 = j6;
        Preconditions.checkArgument(j7 >= 0);
        Preconditions.checkArgument(j8 >= 0);
        Preconditions.checkArgument(j9 >= 0);
        Preconditions.checkArgument(j10 >= 0);
        Preconditions.checkArgument(j11 >= 0);
        Preconditions.checkArgument(j12 >= 0);
        this.a = j7;
        this.b = j8;
        this.c = j9;
        this.d = j10;
        this.e = j11;
        this.f = j12;
    }

    public long requestCount() {
        return this.a + this.b;
    }

    public long hitCount() {
        return this.a;
    }

    public double hitRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 1.0d;
        }
        return ((double) this.a) / ((double) requestCount);
    }

    public long missCount() {
        return this.b;
    }

    public double missRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 0.0d;
        }
        return ((double) this.b) / ((double) requestCount);
    }

    public long loadCount() {
        return this.c + this.d;
    }

    public long loadSuccessCount() {
        return this.c;
    }

    public long loadExceptionCount() {
        return this.d;
    }

    public double loadExceptionRate() {
        long j = this.c + this.d;
        if (j == 0) {
            return 0.0d;
        }
        return ((double) this.d) / ((double) j);
    }

    public long totalLoadTime() {
        return this.e;
    }

    public double averageLoadPenalty() {
        long j = this.c + this.d;
        if (j == 0) {
            return 0.0d;
        }
        return ((double) this.e) / ((double) j);
    }

    public long evictionCount() {
        return this.f;
    }

    public CacheStats minus(CacheStats cacheStats) {
        CacheStats cacheStats2 = cacheStats;
        long max = Math.max(0, this.a - cacheStats2.a);
        long max2 = Math.max(0, this.b - cacheStats2.b);
        long max3 = Math.max(0, this.c - cacheStats2.c);
        CacheStats cacheStats3 = new CacheStats(max, max2, max3, Math.max(0, this.d - cacheStats2.d), Math.max(0, this.e - cacheStats2.e), Math.max(0, this.f - cacheStats2.f));
        return cacheStats3;
    }

    public CacheStats plus(CacheStats cacheStats) {
        CacheStats cacheStats2 = cacheStats;
        CacheStats cacheStats3 = new CacheStats(this.a + cacheStats2.a, this.b + cacheStats2.b, this.c + cacheStats2.c, this.d + cacheStats2.d, this.e + cacheStats2.e, this.f + cacheStats2.f);
        return cacheStats3;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.a), Long.valueOf(this.b), Long.valueOf(this.c), Long.valueOf(this.d), Long.valueOf(this.e), Long.valueOf(this.f));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof CacheStats)) {
            return false;
        }
        CacheStats cacheStats = (CacheStats) obj;
        if (this.a == cacheStats.a && this.b == cacheStats.b && this.c == cacheStats.c && this.d == cacheStats.d && this.e == cacheStats.e && this.f == cacheStats.f) {
            z = true;
        }
        return z;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("hitCount", this.a).add("missCount", this.b).add("loadSuccessCount", this.c).add("loadExceptionCount", this.d).add("totalLoadTime", this.e).add("evictionCount", this.f).toString();
    }
}
