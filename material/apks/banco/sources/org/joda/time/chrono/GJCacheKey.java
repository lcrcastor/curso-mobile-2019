package org.joda.time.chrono;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

class GJCacheKey {
    private final DateTimeZone a;
    private final Instant b;
    private final int c;

    GJCacheKey(DateTimeZone dateTimeZone, Instant instant, int i) {
        this.a = dateTimeZone;
        this.b = instant;
        this.c = i;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((this.b == null ? 0 : this.b.hashCode()) + 31) * 31) + this.c) * 31;
        if (this.a != null) {
            i = this.a.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GJCacheKey)) {
            return false;
        }
        GJCacheKey gJCacheKey = (GJCacheKey) obj;
        if (this.b == null) {
            if (gJCacheKey.b != null) {
                return false;
            }
        } else if (!this.b.equals(gJCacheKey.b)) {
            return false;
        }
        if (this.c != gJCacheKey.c) {
            return false;
        }
        if (this.a == null) {
            if (gJCacheKey.a != null) {
                return false;
            }
        } else if (!this.a.equals(gJCacheKey.a)) {
            return false;
        }
        return true;
    }
}
