package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.crypto.tls.CipherSuite;

public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private double a = -1.0d;
    private int b = CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA;
    private boolean c = true;
    private boolean d;
    private List<ExclusionStrategy> e = Collections.emptyList();
    private List<ExclusionStrategy> f = Collections.emptyList();

    /* access modifiers changed from: protected */
    public Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public Excluder withVersion(double d2) {
        Excluder clone = clone();
        clone.a = d2;
        return clone;
    }

    public Excluder withModifiers(int... iArr) {
        Excluder clone = clone();
        clone.b = 0;
        for (int i : iArr) {
            clone.b = i | clone.b;
        }
        return clone;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder clone = clone();
        clone.c = false;
        return clone;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder clone = clone();
        clone.d = true;
        return clone;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean z, boolean z2) {
        Excluder clone = clone();
        if (z) {
            clone.e = new ArrayList(this.e);
            clone.e.add(exclusionStrategy);
        }
        if (z2) {
            clone.f = new ArrayList(this.f);
            clone.f.add(exclusionStrategy);
        }
        return clone;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class rawType = typeToken.getRawType();
        final boolean excludeClass = excludeClass(rawType, true);
        final boolean excludeClass2 = excludeClass(rawType, false);
        if (!excludeClass && !excludeClass2) {
            return null;
        }
        final Gson gson2 = gson;
        final TypeToken<T> typeToken2 = typeToken;
        AnonymousClass1 r2 = new TypeAdapter<T>() {
            private TypeAdapter<T> f;

            public T read(JsonReader jsonReader) {
                if (!excludeClass2) {
                    return a().read(jsonReader);
                }
                jsonReader.skipValue();
                return null;
            }

            public void write(JsonWriter jsonWriter, T t) {
                if (excludeClass) {
                    jsonWriter.nullValue();
                } else {
                    a().write(jsonWriter, t);
                }
            }

            private TypeAdapter<T> a() {
                TypeAdapter<T> typeAdapter = this.f;
                if (typeAdapter != null) {
                    return typeAdapter;
                }
                TypeAdapter<T> delegateAdapter = gson2.getDelegateAdapter(Excluder.this, typeToken2);
                this.f = delegateAdapter;
                return delegateAdapter;
            }
        };
        return r2;
    }

    public boolean excludeField(Field field, boolean z) {
        if ((this.b & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.a != -1.0d && !a((Since) field.getAnnotation(Since.class), (Until) field.getAnnotation(Until.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.d) {
            Expose expose = (Expose) field.getAnnotation(Expose.class);
            if (expose == null || (!z ? !expose.deserialize() : !expose.serialize())) {
                return true;
            }
        }
        if ((!this.c && b(field.getType())) || a(field.getType())) {
            return true;
        }
        List<ExclusionStrategy> list = z ? this.e : this.f;
        if (!list.isEmpty()) {
            FieldAttributes fieldAttributes = new FieldAttributes(field);
            for (ExclusionStrategy shouldSkipField : list) {
                if (shouldSkipField.shouldSkipField(fieldAttributes)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean excludeClass(Class<?> cls, boolean z) {
        if (this.a != -1.0d && !a((Since) cls.getAnnotation(Since.class), (Until) cls.getAnnotation(Until.class))) {
            return true;
        }
        if ((!this.c && b(cls)) || a(cls)) {
            return true;
        }
        for (ExclusionStrategy shouldSkipClass : z ? this.e : this.f) {
            if (shouldSkipClass.shouldSkipClass(cls)) {
                return true;
            }
        }
        return false;
    }

    private boolean a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean b(Class<?> cls) {
        return cls.isMemberClass() && !c(cls);
    }

    private boolean c(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean a(Since since, Until until) {
        return a(since) && a(until);
    }

    private boolean a(Since since) {
        return since == null || since.value() <= this.a;
    }

    private boolean a(Until until) {
        return until == null || until.value() > this.a;
    }
}
