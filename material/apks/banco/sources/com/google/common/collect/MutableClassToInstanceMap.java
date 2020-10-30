package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@GwtIncompatible
public final class MutableClassToInstanceMap<B> extends ConstrainedMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    private static final MapConstraint<Class<?>, Object> b = new MapConstraint<Class<?>, Object>() {
        /* renamed from: a */
        public void checkKeyValue(Class<?> cls, Object obj) {
            MutableClassToInstanceMap.b(cls, obj);
        }
    };

    static final class SerializedForm<B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Map<Class<? extends B>, B> a;

        SerializedForm(Map<Class<? extends B>, B> map) {
            this.a = map;
        }

        /* access modifiers changed from: 0000 */
        public Object readResolve() {
            return MutableClassToInstanceMap.create(this.a);
        }
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    public static <B> MutableClassToInstanceMap<B> create() {
        return new MutableClassToInstanceMap<>(new HashMap());
    }

    public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> map) {
        return new MutableClassToInstanceMap<>(map);
    }

    private MutableClassToInstanceMap(Map<Class<? extends B>, B> map) {
        super(map, b);
    }

    @CanIgnoreReturnValue
    public <T extends B> T putInstance(Class<T> cls, T t) {
        return b(cls, put(cls, t));
    }

    public <T extends B> T getInstance(Class<T> cls) {
        return b(cls, get(cls));
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public static <B, T extends B> T b(Class<T> cls, B b2) {
        return Primitives.wrap(cls).cast(b2);
    }

    private Object writeReplace() {
        return new SerializedForm(delegate());
    }
}
