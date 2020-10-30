package com.google.common.base;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.common.annotations.GwtIncompatible;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@GwtIncompatible
public final class Defaults {
    private static final Map<Class<?>, Object> a;

    private Defaults() {
    }

    static {
        HashMap hashMap = new HashMap();
        a(hashMap, Boolean.TYPE, Boolean.valueOf(false));
        a(hashMap, Character.TYPE, Character.valueOf(0));
        a(hashMap, Byte.TYPE, Byte.valueOf(0));
        a(hashMap, Short.TYPE, Short.valueOf(0));
        a(hashMap, Integer.TYPE, Integer.valueOf(0));
        a(hashMap, Long.TYPE, Long.valueOf(0));
        a(hashMap, Float.TYPE, Float.valueOf(BitmapDescriptorFactory.HUE_RED));
        a(hashMap, Double.TYPE, Double.valueOf(0.0d));
        a = Collections.unmodifiableMap(hashMap);
    }

    private static <T> void a(Map<Class<?>, Object> map, Class<T> cls, T t) {
        map.put(cls, t);
    }

    @Nullable
    public static <T> T defaultValue(Class<T> cls) {
        return a.get(Preconditions.checkNotNull(cls));
    }
}
