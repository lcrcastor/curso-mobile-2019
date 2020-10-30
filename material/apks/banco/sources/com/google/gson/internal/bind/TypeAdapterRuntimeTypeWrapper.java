package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {
    private final Gson a;
    private final TypeAdapter<T> b;
    private final Type c;

    TypeAdapterRuntimeTypeWrapper(Gson gson, TypeAdapter<T> typeAdapter, Type type) {
        this.a = gson;
        this.b = typeAdapter;
        this.c = type;
    }

    public T read(JsonReader jsonReader) {
        return this.b.read(jsonReader);
    }

    public void write(JsonWriter jsonWriter, T t) {
        TypeAdapter<T> typeAdapter = this.b;
        Type a2 = a(this.c, t);
        if (a2 != this.c) {
            typeAdapter = this.a.getAdapter(TypeToken.get(a2));
            if ((typeAdapter instanceof Adapter) && !(this.b instanceof Adapter)) {
                typeAdapter = this.b;
            }
        }
        typeAdapter.write(jsonWriter, t);
    }

    private Type a(Type type, Object obj) {
        if (obj != null) {
            return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
        }
        return type;
    }
}
