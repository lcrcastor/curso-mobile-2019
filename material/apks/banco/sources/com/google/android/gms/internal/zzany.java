package com.google.android.gms.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class zzany {
    private final Field a;

    public zzany(Field field) {
        zzaoz.zzy(field);
        this.a = field;
    }

    public <T extends Annotation> T getAnnotation(Class<T> cls) {
        return this.a.getAnnotation(cls);
    }
}
