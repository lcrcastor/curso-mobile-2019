package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T> extends TypeAdapter<T> {
    final Gson a;
    private final JsonSerializer<T> b;
    private final JsonDeserializer<T> c;
    private final TypeToken<T> d;
    private final TypeAdapterFactory e;
    private final GsonContextImpl f = new GsonContextImpl<>();
    private TypeAdapter<T> g;

    final class GsonContextImpl implements JsonDeserializationContext, JsonSerializationContext {
        private GsonContextImpl() {
        }

        public JsonElement serialize(Object obj) {
            return TreeTypeAdapter.this.a.toJsonTree(obj);
        }

        public JsonElement serialize(Object obj, Type type) {
            return TreeTypeAdapter.this.a.toJsonTree(obj, type);
        }

        public <R> R deserialize(JsonElement jsonElement, Type type) {
            return TreeTypeAdapter.this.a.fromJson(jsonElement, type);
        }
    }

    static final class SingleTypeFactory implements TypeAdapterFactory {
        private final TypeToken<?> a;
        private final boolean b;
        private final Class<?> c;
        private final JsonSerializer<?> d;
        private final JsonDeserializer<?> e;

        SingleTypeFactory(Object obj, TypeToken<?> typeToken, boolean z, Class<?> cls) {
            JsonDeserializer<?> jsonDeserializer = null;
            this.d = obj instanceof JsonSerializer ? (JsonSerializer) obj : null;
            if (obj instanceof JsonDeserializer) {
                jsonDeserializer = (JsonDeserializer) obj;
            }
            this.e = jsonDeserializer;
            C$Gson$Preconditions.checkArgument((this.d == null && this.e == null) ? false : true);
            this.a = typeToken;
            this.b = z;
            this.c = cls;
        }

        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            boolean z = this.a != null ? this.a.equals(typeToken) || (this.b && this.a.getType() == typeToken.getRawType()) : this.c.isAssignableFrom(typeToken.getRawType());
            if (!z) {
                return null;
            }
            TreeTypeAdapter treeTypeAdapter = new TreeTypeAdapter(this.d, this.e, gson, typeToken, this);
            return treeTypeAdapter;
        }
    }

    public TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory) {
        this.b = jsonSerializer;
        this.c = jsonDeserializer;
        this.a = gson;
        this.d = typeToken;
        this.e = typeAdapterFactory;
    }

    public T read(JsonReader jsonReader) {
        if (this.c == null) {
            return a().read(jsonReader);
        }
        JsonElement parse = Streams.parse(jsonReader);
        if (parse.isJsonNull()) {
            return null;
        }
        return this.c.deserialize(parse, this.d.getType(), this.f);
    }

    public void write(JsonWriter jsonWriter, T t) {
        if (this.b == null) {
            a().write(jsonWriter, t);
        } else if (t == null) {
            jsonWriter.nullValue();
        } else {
            Streams.write(this.b.serialize(t, this.d.getType(), this.f), jsonWriter);
        }
    }

    private TypeAdapter<T> a() {
        TypeAdapter<T> typeAdapter = this.g;
        if (typeAdapter != null) {
            return typeAdapter;
        }
        TypeAdapter<T> delegateAdapter = this.a.getDelegateAdapter(this.e, this.d);
        this.g = delegateAdapter;
        return delegateAdapter;
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object obj) {
        return new SingleTypeFactory(obj, typeToken, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> typeToken, Object obj) {
        return new SingleTypeFactory(obj, typeToken, typeToken.getType() == typeToken.getRawType(), null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> cls, Object obj) {
        return new SingleTypeFactory(obj, null, false, cls);
    }
}
