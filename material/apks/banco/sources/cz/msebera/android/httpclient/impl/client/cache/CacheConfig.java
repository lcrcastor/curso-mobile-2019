package cz.msebera.android.httpclient.impl.client.cache;

import android.support.v4.media.session.PlaybackStateCompat;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import cz.msebera.android.httpclient.util.Args;

public class CacheConfig implements Cloneable {
    public static final CacheConfig DEFAULT = new Builder().build();
    public static final boolean DEFAULT_303_CACHING_ENABLED = false;
    public static final int DEFAULT_ASYNCHRONOUS_WORKERS_CORE = 1;
    public static final int DEFAULT_ASYNCHRONOUS_WORKERS_MAX = 1;
    public static final int DEFAULT_ASYNCHRONOUS_WORKER_IDLE_LIFETIME_SECS = 60;
    public static final boolean DEFAULT_HEURISTIC_CACHING_ENABLED = false;
    public static final float DEFAULT_HEURISTIC_COEFFICIENT = 0.1f;
    public static final long DEFAULT_HEURISTIC_LIFETIME = 0;
    public static final int DEFAULT_MAX_CACHE_ENTRIES = 1000;
    public static final int DEFAULT_MAX_OBJECT_SIZE_BYTES = 8192;
    public static final int DEFAULT_MAX_UPDATE_RETRIES = 1;
    public static final int DEFAULT_REVALIDATION_QUEUE_SIZE = 100;
    public static final boolean DEFAULT_WEAK_ETAG_ON_PUTDELETE_ALLOWED = false;
    private long a;
    private int b;
    private int c;
    private boolean d;
    private boolean e;
    private boolean f;
    private float g;
    private long h;
    private boolean i;
    private int j;
    private int k;
    private int l;
    private int m;
    private boolean n;

    public static class Builder {
        private long a = PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        private int b = 1000;
        private int c = 1;
        private boolean d = false;
        private boolean e = false;
        private boolean f = false;
        private float g = 0.1f;
        private long h = 0;
        private boolean i = true;
        private int j = 1;
        private int k = 1;
        private int l = 60;
        private int m = 100;
        private boolean n;

        Builder() {
        }

        public Builder setMaxObjectSize(long j2) {
            this.a = j2;
            return this;
        }

        public Builder setMaxCacheEntries(int i2) {
            this.b = i2;
            return this;
        }

        public Builder setMaxUpdateRetries(int i2) {
            this.c = i2;
            return this;
        }

        public Builder setAllow303Caching(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setWeakETagOnPutDeleteAllowed(boolean z) {
            this.e = z;
            return this;
        }

        public Builder setHeuristicCachingEnabled(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setHeuristicCoefficient(float f2) {
            this.g = f2;
            return this;
        }

        public Builder setHeuristicDefaultLifetime(long j2) {
            this.h = j2;
            return this;
        }

        public Builder setSharedCache(boolean z) {
            this.i = z;
            return this;
        }

        public Builder setAsynchronousWorkersMax(int i2) {
            this.j = i2;
            return this;
        }

        public Builder setAsynchronousWorkersCore(int i2) {
            this.k = i2;
            return this;
        }

        public Builder setAsynchronousWorkerIdleLifetimeSecs(int i2) {
            this.l = i2;
            return this;
        }

        public Builder setRevalidationQueueSize(int i2) {
            this.m = i2;
            return this;
        }

        public Builder setNeverCacheHTTP10ResponsesWithQueryString(boolean z) {
            this.n = z;
            return this;
        }

        public CacheConfig build() {
            long j2 = this.a;
            int i2 = this.b;
            int i3 = this.c;
            boolean z = this.d;
            boolean z2 = this.e;
            boolean z3 = this.f;
            float f2 = this.g;
            long j3 = this.h;
            boolean z4 = this.i;
            int i4 = this.j;
            int i5 = this.k;
            int i6 = this.l;
            int i7 = i6;
            int i8 = i7;
            CacheConfig cacheConfig = new CacheConfig(j2, i2, i3, z, z2, z3, f2, j3, z4, i4, i5, i8, this.m, this.n);
            return cacheConfig;
        }
    }

    @Deprecated
    public CacheConfig() {
        this.a = PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        this.b = 1000;
        this.c = 1;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = 0.1f;
        this.h = 0;
        this.i = true;
        this.j = 1;
        this.k = 1;
        this.l = 60;
        this.m = 100;
    }

    CacheConfig(long j2, int i2, int i3, boolean z, boolean z2, boolean z3, float f2, long j3, boolean z4, int i4, int i5, int i6, int i7, boolean z5) {
        this.a = j2;
        this.b = i2;
        this.c = i3;
        this.d = z;
        this.e = z2;
        this.f = z3;
        this.g = f2;
        this.h = j3;
        this.i = z4;
        this.j = i4;
        this.k = i5;
        this.l = i6;
        this.m = i7;
    }

    @Deprecated
    public int getMaxObjectSizeBytes() {
        return this.a > 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : (int) this.a;
    }

    @Deprecated
    public void setMaxObjectSizeBytes(int i2) {
        if (i2 > Integer.MAX_VALUE) {
            this.a = 2147483647L;
        } else {
            this.a = (long) i2;
        }
    }

    public long getMaxObjectSize() {
        return this.a;
    }

    @Deprecated
    public void setMaxObjectSize(long j2) {
        this.a = j2;
    }

    public boolean isNeverCacheHTTP10ResponsesWithQuery() {
        return this.n;
    }

    public int getMaxCacheEntries() {
        return this.b;
    }

    @Deprecated
    public void setMaxCacheEntries(int i2) {
        this.b = i2;
    }

    public int getMaxUpdateRetries() {
        return this.c;
    }

    @Deprecated
    public void setMaxUpdateRetries(int i2) {
        this.c = i2;
    }

    public boolean is303CachingEnabled() {
        return this.d;
    }

    public boolean isWeakETagOnPutDeleteAllowed() {
        return this.e;
    }

    public boolean isHeuristicCachingEnabled() {
        return this.f;
    }

    @Deprecated
    public void setHeuristicCachingEnabled(boolean z) {
        this.f = z;
    }

    public float getHeuristicCoefficient() {
        return this.g;
    }

    @Deprecated
    public void setHeuristicCoefficient(float f2) {
        this.g = f2;
    }

    public long getHeuristicDefaultLifetime() {
        return this.h;
    }

    @Deprecated
    public void setHeuristicDefaultLifetime(long j2) {
        this.h = j2;
    }

    public boolean isSharedCache() {
        return this.i;
    }

    @Deprecated
    public void setSharedCache(boolean z) {
        this.i = z;
    }

    public int getAsynchronousWorkersMax() {
        return this.j;
    }

    @Deprecated
    public void setAsynchronousWorkersMax(int i2) {
        this.j = i2;
    }

    public int getAsynchronousWorkersCore() {
        return this.k;
    }

    @Deprecated
    public void setAsynchronousWorkersCore(int i2) {
        this.k = i2;
    }

    public int getAsynchronousWorkerIdleLifetimeSecs() {
        return this.l;
    }

    @Deprecated
    public void setAsynchronousWorkerIdleLifetimeSecs(int i2) {
        this.l = i2;
    }

    public int getRevalidationQueueSize() {
        return this.m;
    }

    @Deprecated
    public void setRevalidationQueueSize(int i2) {
        this.m = i2;
    }

    /* access modifiers changed from: protected */
    public CacheConfig clone() {
        return (CacheConfig) super.clone();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(CacheConfig cacheConfig) {
        Args.notNull(cacheConfig, "Cache config");
        return new Builder().setMaxObjectSize(cacheConfig.getMaxObjectSize()).setMaxCacheEntries(cacheConfig.getMaxCacheEntries()).setMaxUpdateRetries(cacheConfig.getMaxUpdateRetries()).setHeuristicCachingEnabled(cacheConfig.isHeuristicCachingEnabled()).setHeuristicCoefficient(cacheConfig.getHeuristicCoefficient()).setHeuristicDefaultLifetime(cacheConfig.getHeuristicDefaultLifetime()).setSharedCache(cacheConfig.isSharedCache()).setAsynchronousWorkersMax(cacheConfig.getAsynchronousWorkersMax()).setAsynchronousWorkersCore(cacheConfig.getAsynchronousWorkersCore()).setAsynchronousWorkerIdleLifetimeSecs(cacheConfig.getAsynchronousWorkerIdleLifetimeSecs()).setRevalidationQueueSize(cacheConfig.getRevalidationQueueSize()).setNeverCacheHTTP10ResponsesWithQueryString(cacheConfig.isNeverCacheHTTP10ResponsesWithQuery());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[maxObjectSize=");
        sb.append(this.a);
        sb.append(", maxCacheEntries=");
        sb.append(this.b);
        sb.append(", maxUpdateRetries=");
        sb.append(this.c);
        sb.append(", 303CachingEnabled=");
        sb.append(this.d);
        sb.append(", weakETagOnPutDeleteAllowed=");
        sb.append(this.e);
        sb.append(", heuristicCachingEnabled=");
        sb.append(this.f);
        sb.append(", heuristicCoefficient=");
        sb.append(this.g);
        sb.append(", heuristicDefaultLifetime=");
        sb.append(this.h);
        sb.append(", isSharedCache=");
        sb.append(this.i);
        sb.append(", asynchronousWorkersMax=");
        sb.append(this.j);
        sb.append(", asynchronousWorkersCore=");
        sb.append(this.k);
        sb.append(", asynchronousWorkerIdleLifetimeSecs=");
        sb.append(this.l);
        sb.append(", revalidationQueueSize=");
        sb.append(this.m);
        sb.append(", neverCacheHTTP10ResponsesWithQuery=");
        sb.append(this.n);
        sb.append("]");
        return sb.toString();
    }
}
