package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtIncompatible
public final class ImmutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    private static final ImmutableClassToInstanceMap<Object> a = new ImmutableClassToInstanceMap<>(ImmutableMap.of());
    private final ImmutableMap<Class<? extends B>, B> b;

    public static final class Builder<B> {
        private final com.google.common.collect.ImmutableMap.Builder<Class<? extends B>, B> a = ImmutableMap.builder();

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> put(Class<T> cls, T t) {
            this.a.put(cls, t);
            return this;
        }

        @CanIgnoreReturnValue
        public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
            for (Entry entry : map.entrySet()) {
                Class cls = (Class) entry.getKey();
                this.a.put(cls, a(cls, entry.getValue()));
            }
            return this;
        }

        private static <B, T extends B> T a(Class<T> cls, B b) {
            return Primitives.wrap(cls).cast(b);
        }

        public ImmutableClassToInstanceMap<B> build() {
            ImmutableMap build = this.a.build();
            if (build.isEmpty()) {
                return ImmutableClassToInstanceMap.of();
            }
            return new ImmutableClassToInstanceMap<>(build);
        }
    }

    public static <B> ImmutableClassToInstanceMap<B> of() {
        return a;
    }

    public static <B, T extends B> ImmutableClassToInstanceMap<B> of(Class<T> cls, T t) {
        return new ImmutableClassToInstanceMap<>(ImmutableMap.of(cls, t));
    }

    public static <B> Builder<B> builder() {
        return new Builder<>();
    }

    public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
        if (map instanceof ImmutableClassToInstanceMap) {
            return (ImmutableClassToInstanceMap) map;
        }
        return new Builder().putAll(map).build();
    }

    private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> immutableMap) {
        this.b = immutableMap;
    }

    /* access modifiers changed from: protected */
    public Map<Class<? extends B>, B> delegate() {
        return this.b;
    }

    @Nullable
    public <T extends B> T getInstance(Class<T> cls) {
        return this.b.get(Preconditions.checkNotNull(cls));
    }

    @CanIgnoreReturnValue
    @Deprecated
    public <T extends B> T putInstance(Class<T> cls, T t) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public Object readResolve() {
        return isEmpty() ? of() : this;
    }
}
