package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor a;
    private final FieldNamingStrategy b;
    private final Excluder c;
    private final JsonAdapterAnnotationTypeAdapterFactory d;

    public static final class Adapter<T> extends TypeAdapter<T> {
        private final ObjectConstructor<T> a;
        private final Map<String, BoundField> b;

        Adapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            this.a = objectConstructor;
            this.b = map;
        }

        public T read(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T construct = this.a.construct();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    BoundField boundField = (BoundField) this.b.get(jsonReader.nextName());
                    if (boundField != null) {
                        if (boundField.j) {
                            boundField.a(jsonReader, (Object) construct);
                        }
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return construct;
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void write(JsonWriter jsonWriter, T t) {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField boundField : this.b.values()) {
                    if (boundField.a(t)) {
                        jsonWriter.name(boundField.h);
                        boundField.a(jsonWriter, (Object) t);
                    }
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    static abstract class BoundField {
        final String h;
        final boolean i;
        final boolean j;

        /* access modifiers changed from: 0000 */
        public abstract void a(JsonReader jsonReader, Object obj);

        /* access modifiers changed from: 0000 */
        public abstract void a(JsonWriter jsonWriter, Object obj);

        /* access modifiers changed from: 0000 */
        public abstract boolean a(Object obj);

        protected BoundField(String str, boolean z, boolean z2) {
            this.h = str;
            this.i = z;
            this.j = z2;
        }
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.a = constructorConstructor;
        this.b = fieldNamingStrategy;
        this.c = excluder;
        this.d = jsonAdapterAnnotationTypeAdapterFactory;
    }

    public boolean excludeField(Field field, boolean z) {
        return a(field, z, this.c);
    }

    static boolean a(Field field, boolean z, Excluder excluder) {
        return !excluder.excludeClass(field.getType(), z) && !excluder.excludeField(field, z);
    }

    private List<String> a(Field field) {
        SerializedName serializedName = (SerializedName) field.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return Collections.singletonList(this.b.translateName(field));
        }
        String value = serializedName.value();
        String[] alternate = serializedName.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        ArrayList arrayList = new ArrayList(alternate.length + 1);
        arrayList.add(value);
        for (String add : alternate) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class rawType = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(rawType)) {
            return null;
        }
        return new Adapter(this.a.get(typeToken), a(gson, typeToken, rawType));
    }

    private BoundField a(Gson gson, Field field, String str, TypeToken<?> typeToken, boolean z, boolean z2) {
        final Gson gson2 = gson;
        final TypeToken<?> typeToken2 = typeToken;
        final boolean isPrimitive = Primitives.isPrimitive(typeToken.getRawType());
        final Field field2 = field;
        JsonAdapter jsonAdapter = (JsonAdapter) field2.getAnnotation(JsonAdapter.class);
        TypeAdapter a2 = jsonAdapter != null ? this.d.a(this.a, gson2, typeToken2, jsonAdapter) : null;
        final boolean z3 = a2 != null;
        if (a2 == null) {
            a2 = gson2.getAdapter(typeToken2);
        }
        final TypeAdapter typeAdapter = a2;
        AnonymousClass1 r0 = new BoundField(str, z, z2) {
            /* access modifiers changed from: 0000 */
            public void a(JsonWriter jsonWriter, Object obj) {
                TypeAdapter typeAdapter;
                Object obj2 = field2.get(obj);
                if (z3) {
                    typeAdapter = typeAdapter;
                } else {
                    typeAdapter = new TypeAdapterRuntimeTypeWrapper(gson2, typeAdapter, typeToken2.getType());
                }
                typeAdapter.write(jsonWriter, obj2);
            }

            /* access modifiers changed from: 0000 */
            public void a(JsonReader jsonReader, Object obj) {
                Object read = typeAdapter.read(jsonReader);
                if (read != null || !isPrimitive) {
                    field2.set(obj, read);
                }
            }

            public boolean a(Object obj) {
                boolean z = false;
                if (!this.i) {
                    return false;
                }
                if (field2.get(obj) != obj) {
                    z = true;
                }
                return z;
            }
        };
        return r0;
    }

    private Map<String, BoundField> a(Gson gson, TypeToken<?> typeToken, Class<?> cls) {
        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory = this;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type type = typeToken.getType();
        TypeToken<?> typeToken2 = typeToken;
        Class<?> cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            int length = declaredFields.length;
            boolean z = false;
            int i = 0;
            while (i < length) {
                Field field = declaredFields[i];
                boolean excludeField = reflectiveTypeAdapterFactory.excludeField(field, true);
                boolean excludeField2 = reflectiveTypeAdapterFactory.excludeField(field, z);
                if (excludeField || excludeField2) {
                    field.setAccessible(true);
                    Type resolve = C$Gson$Types.resolve(typeToken2.getType(), cls2, field.getGenericType());
                    List a2 = reflectiveTypeAdapterFactory.a(field);
                    int size = a2.size();
                    int i2 = 0;
                    boolean z2 = excludeField;
                    BoundField boundField = null;
                    boolean z3 = z2;
                    while (i2 < size) {
                        String str = (String) a2.get(i2);
                        boolean z4 = i2 != 0 ? false : z3;
                        ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory2 = reflectiveTypeAdapterFactory;
                        BoundField boundField2 = boundField;
                        int i3 = i2;
                        int i4 = size;
                        List list = a2;
                        Type type2 = resolve;
                        Field field2 = field;
                        boundField = boundField2 == null ? (BoundField) linkedHashMap.put(str, reflectiveTypeAdapterFactory2.a(gson, field, str, TypeToken.get(resolve), z4, excludeField2)) : boundField2;
                        i2 = i3 + 1;
                        z3 = z4;
                        resolve = type2;
                        size = i4;
                        a2 = list;
                        field = field2;
                        reflectiveTypeAdapterFactory = this;
                    }
                    BoundField boundField3 = boundField;
                    if (boundField3 != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(type);
                        sb.append(" declares multiple JSON fields named ");
                        sb.append(boundField3.h);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                i++;
                reflectiveTypeAdapterFactory = this;
                z = false;
            }
            typeToken2 = TypeToken.get(C$Gson$Types.resolve(typeToken2.getType(), cls2, cls2.getGenericSuperclass()));
            cls2 = typeToken2.getRawType();
            reflectiveTypeAdapterFactory = this;
        }
        return linkedHashMap;
    }
}
