package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Enums {
    @GwtIncompatible
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> a = new WeakHashMap();

    static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> a;

        StringConverter(Class<T> cls) {
            this.a = (Class) Preconditions.checkNotNull(cls);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public T doForward(String str) {
            return Enum.valueOf(this.a, str);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doBackward(T t) {
            return t.name();
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof StringConverter)) {
                return false;
            }
            return this.a.equals(((StringConverter) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Enums.stringConverter(");
            sb.append(this.a.getName());
            sb.append(".class)");
            return sb.toString();
        }
    }

    private Enums() {
    }

    @GwtIncompatible
    public static Field getField(Enum<?> enumR) {
        try {
            return enumR.getDeclaringClass().getDeclaredField(enumR.name());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> cls, String str) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(str);
        return Platform.a(cls, str);
    }

    @GwtIncompatible
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> b(Class<T> cls) {
        HashMap hashMap = new HashMap();
        Iterator it = EnumSet.allOf(cls).iterator();
        while (it.hasNext()) {
            Enum enumR = (Enum) it.next();
            hashMap.put(enumR.name(), new WeakReference(enumR));
        }
        a.put(cls, hashMap);
        return hashMap;
    }

    @GwtIncompatible
    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> a(Class<T> cls) {
        Map<String, WeakReference<? extends Enum<?>>> map;
        synchronized (a) {
            map = (Map) a.get(cls);
            if (map == null) {
                map = b(cls);
            }
        }
        return map;
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> cls) {
        return new StringConverter(cls);
    }
}
