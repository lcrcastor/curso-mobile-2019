package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtIncompatible
public final class CacheBuilderSpec {
    private static final Splitter n = Splitter.on(',').trimResults();
    private static final Splitter o = Splitter.on('=').trimResults();
    private static final ImmutableMap<String, ValueParser> p = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", new MaximumSizeParser()).put("maximumWeight", new MaximumWeightParser()).put("concurrencyLevel", new ConcurrencyLevelParser()).put("weakKeys", new KeyStrengthParser(Strength.WEAK)).put("softValues", new ValueStrengthParser(Strength.SOFT)).put("weakValues", new ValueStrengthParser(Strength.WEAK)).put("recordStats", new RecordStatsParser()).put("expireAfterAccess", new AccessDurationParser()).put("expireAfterWrite", new WriteDurationParser()).put("refreshAfterWrite", new RefreshDurationParser()).put("refreshInterval", new RefreshDurationParser()).build();
    @VisibleForTesting
    Integer a;
    @VisibleForTesting
    Long b;
    @VisibleForTesting
    Long c;
    @VisibleForTesting
    Integer d;
    @VisibleForTesting
    Strength e;
    @VisibleForTesting
    Strength f;
    @VisibleForTesting
    Boolean g;
    @VisibleForTesting
    long h;
    @VisibleForTesting
    TimeUnit i;
    @VisibleForTesting
    long j;
    @VisibleForTesting
    TimeUnit k;
    @VisibleForTesting
    long l;
    @VisibleForTesting
    TimeUnit m;
    private final String q;

    static class KeyStrengthParser implements ValueParser {
        private final Strength a;

        public KeyStrengthParser(Strength strength) {
            this.a = strength;
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @Nullable String str2) {
            boolean z = false;
            Preconditions.checkArgument(str2 == null, "key %s does not take values", (Object) str);
            if (cacheBuilderSpec.e == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "%s was already set to %s", (Object) str, (Object) cacheBuilderSpec.e);
            cacheBuilderSpec.e = this.a;
        }
    }

    static class RecordStatsParser implements ValueParser {
        RecordStatsParser() {
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @Nullable String str2) {
            boolean z = false;
            Preconditions.checkArgument(str2 == null, "recordStats does not take values");
            if (cacheBuilderSpec.g == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "recordStats already set");
            cacheBuilderSpec.g = Boolean.valueOf(true);
        }
    }

    interface ValueParser {
        void a(CacheBuilderSpec cacheBuilderSpec, String str, @Nullable String str2);
    }

    static class ValueStrengthParser implements ValueParser {
        private final Strength a;

        public ValueStrengthParser(Strength strength) {
            this.a = strength;
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @Nullable String str2) {
            boolean z = false;
            Preconditions.checkArgument(str2 == null, "key %s does not take values", (Object) str);
            if (cacheBuilderSpec.f == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "%s was already set to %s", (Object) str, (Object) cacheBuilderSpec.f);
            cacheBuilderSpec.f = this.a;
        }
    }

    static class AccessDurationParser extends DurationParser {
        AccessDurationParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.k == null, "expireAfterAccess already set");
            cacheBuilderSpec.j = j;
            cacheBuilderSpec.k = timeUnit;
        }
    }

    static class ConcurrencyLevelParser extends IntegerParser {
        ConcurrencyLevelParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, int i) {
            Preconditions.checkArgument(cacheBuilderSpec.d == null, "concurrency level was already set to ", (Object) cacheBuilderSpec.d);
            cacheBuilderSpec.d = Integer.valueOf(i);
        }
    }

    static abstract class DurationParser implements ValueParser {
        /* access modifiers changed from: protected */
        public abstract void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit);

        DurationParser() {
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            TimeUnit timeUnit;
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", (Object) str);
            try {
                char charAt = str2.charAt(str2.length() - 1);
                if (charAt == 'd') {
                    timeUnit = TimeUnit.DAYS;
                } else if (charAt == 'h') {
                    timeUnit = TimeUnit.HOURS;
                } else if (charAt == 'm') {
                    timeUnit = TimeUnit.MINUTES;
                } else if (charAt != 's') {
                    throw new IllegalArgumentException(CacheBuilderSpec.b("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", str, str2));
                } else {
                    timeUnit = TimeUnit.SECONDS;
                }
                a(cacheBuilderSpec, Long.parseLong(str2.substring(0, str2.length() - 1)), timeUnit);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2));
            }
        }
    }

    static class InitialCapacityParser extends IntegerParser {
        InitialCapacityParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, int i) {
            Preconditions.checkArgument(cacheBuilderSpec.a == null, "initial capacity was already set to ", (Object) cacheBuilderSpec.a);
            cacheBuilderSpec.a = Integer.valueOf(i);
        }
    }

    static abstract class IntegerParser implements ValueParser {
        /* access modifiers changed from: protected */
        public abstract void a(CacheBuilderSpec cacheBuilderSpec, int i);

        IntegerParser() {
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", (Object) str);
            try {
                a(cacheBuilderSpec, Integer.parseInt(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2), e);
            }
        }
    }

    static abstract class LongParser implements ValueParser {
        /* access modifiers changed from: protected */
        public abstract void a(CacheBuilderSpec cacheBuilderSpec, long j);

        LongParser() {
        }

        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", (Object) str);
            try {
                a(cacheBuilderSpec, Long.parseLong(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2), e);
            }
        }
    }

    static class MaximumSizeParser extends LongParser {
        MaximumSizeParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, long j) {
            boolean z = false;
            Preconditions.checkArgument(cacheBuilderSpec.b == null, "maximum size was already set to ", (Object) cacheBuilderSpec.b);
            if (cacheBuilderSpec.c == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "maximum weight was already set to ", (Object) cacheBuilderSpec.c);
            cacheBuilderSpec.b = Long.valueOf(j);
        }
    }

    static class MaximumWeightParser extends LongParser {
        MaximumWeightParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, long j) {
            boolean z = false;
            Preconditions.checkArgument(cacheBuilderSpec.c == null, "maximum weight was already set to ", (Object) cacheBuilderSpec.c);
            if (cacheBuilderSpec.b == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "maximum size was already set to ", (Object) cacheBuilderSpec.b);
            cacheBuilderSpec.c = Long.valueOf(j);
        }
    }

    static class RefreshDurationParser extends DurationParser {
        RefreshDurationParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.m == null, "refreshAfterWrite already set");
            cacheBuilderSpec.l = j;
            cacheBuilderSpec.m = timeUnit;
        }
    }

    static class WriteDurationParser extends DurationParser {
        WriteDurationParser() {
        }

        /* access modifiers changed from: protected */
        public void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.i == null, "expireAfterWrite already set");
            cacheBuilderSpec.h = j;
            cacheBuilderSpec.i = timeUnit;
        }
    }

    private CacheBuilderSpec(String str) {
        this.q = str;
    }

    public static CacheBuilderSpec parse(String str) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(str);
        if (!str.isEmpty()) {
            for (String str2 : n.split(str)) {
                ImmutableList copyOf = ImmutableList.copyOf(o.split(str2));
                Preconditions.checkArgument(!copyOf.isEmpty(), "blank key-value pair");
                boolean z = false;
                Preconditions.checkArgument(copyOf.size() <= 2, "key-value pair %s with more than one equals sign", (Object) str2);
                String str3 = (String) copyOf.get(0);
                ValueParser valueParser = (ValueParser) p.get(str3);
                if (valueParser != null) {
                    z = true;
                }
                Preconditions.checkArgument(z, "unknown key %s", (Object) str3);
                valueParser.a(cacheBuilderSpec, str3, copyOf.size() == 1 ? null : (String) copyOf.get(1));
            }
        }
        return cacheBuilderSpec;
    }

    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }

    /* access modifiers changed from: 0000 */
    public CacheBuilder<Object, Object> a() {
        CacheBuilder<Object, Object> newBuilder = CacheBuilder.newBuilder();
        if (this.a != null) {
            newBuilder.initialCapacity(this.a.intValue());
        }
        if (this.b != null) {
            newBuilder.maximumSize(this.b.longValue());
        }
        if (this.c != null) {
            newBuilder.maximumWeight(this.c.longValue());
        }
        if (this.d != null) {
            newBuilder.concurrencyLevel(this.d.intValue());
        }
        if (this.e != null) {
            if (AnonymousClass1.a[this.e.ordinal()] != 1) {
                throw new AssertionError();
            }
            newBuilder.weakKeys();
        }
        if (this.f != null) {
            switch (this.f) {
                case WEAK:
                    newBuilder.weakValues();
                    break;
                case SOFT:
                    newBuilder.softValues();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        if (this.g != null && this.g.booleanValue()) {
            newBuilder.recordStats();
        }
        if (this.i != null) {
            newBuilder.expireAfterWrite(this.h, this.i);
        }
        if (this.k != null) {
            newBuilder.expireAfterAccess(this.j, this.k);
        }
        if (this.m != null) {
            newBuilder.refreshAfterWrite(this.l, this.m);
        }
        return newBuilder;
    }

    public String toParsableString() {
        return this.q;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).addValue((Object) toParsableString()).toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b, this.c, this.d, this.e, this.f, this.g, a(this.h, this.i), a(this.j, this.k), a(this.l, this.m));
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheBuilderSpec)) {
            return false;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec) obj;
        if (!Objects.equal(this.a, cacheBuilderSpec.a) || !Objects.equal(this.b, cacheBuilderSpec.b) || !Objects.equal(this.c, cacheBuilderSpec.c) || !Objects.equal(this.d, cacheBuilderSpec.d) || !Objects.equal(this.e, cacheBuilderSpec.e) || !Objects.equal(this.f, cacheBuilderSpec.f) || !Objects.equal(this.g, cacheBuilderSpec.g) || !Objects.equal(a(this.h, this.i), a(cacheBuilderSpec.h, cacheBuilderSpec.i)) || !Objects.equal(a(this.j, this.k), a(cacheBuilderSpec.j, cacheBuilderSpec.k)) || !Objects.equal(a(this.l, this.m), a(cacheBuilderSpec.l, cacheBuilderSpec.m))) {
            z = false;
        }
        return z;
    }

    @Nullable
    private static Long a(long j2, @Nullable TimeUnit timeUnit) {
        if (timeUnit == null) {
            return null;
        }
        return Long.valueOf(timeUnit.toNanos(j2));
    }

    /* access modifiers changed from: private */
    public static String b(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }
}
