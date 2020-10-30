package com.google.gson.internal;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Primitives {
    private static final Map<Class<?>, Class<?>> a;
    private static final Map<Class<?>, Class<?>> b;

    private Primitives() {
        throw new UnsupportedOperationException();
    }

    static {
        HashMap hashMap = new HashMap(16);
        HashMap hashMap2 = new HashMap(16);
        a(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        a(hashMap, hashMap2, Byte.TYPE, Byte.class);
        a(hashMap, hashMap2, Character.TYPE, Character.class);
        a(hashMap, hashMap2, Double.TYPE, Double.class);
        a(hashMap, hashMap2, Float.TYPE, Float.class);
        a(hashMap, hashMap2, Integer.TYPE, Integer.class);
        a(hashMap, hashMap2, Long.TYPE, Long.class);
        a(hashMap, hashMap2, Short.TYPE, Short.class);
        a(hashMap, hashMap2, Void.TYPE, Void.class);
        a = Collections.unmodifiableMap(hashMap);
        b = Collections.unmodifiableMap(hashMap2);
    }

    private static void a(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> cls, Class<?> cls2) {
        map.put(cls, cls2);
        map2.put(cls2, cls);
    }

    public static boolean isPrimitive(Type type) {
        return a.containsKey(type);
    }

    public static boolean isWrapperType(Type type) {
        return b.containsKey(C$Gson$Preconditions.checkNotNull(type));
    }

    public static <T> Class<T> wrap(Class<T> cls) {
        Class cls2 = (Class) a.get(C$Gson$Preconditions.checkNotNull(cls));
        return cls2 == null ? cls : cls2;
    }

    public static <T> Class<T> unwrap(Class<T> cls) {
        Class cls2 = (Class) b.get(C$Gson$Preconditions.checkNotNull(cls));
        return cls2 == null ? cls : cls2;
    }
}
