package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonTreeWriter extends JsonWriter {
    private static final Writer a = new Writer() {
        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }

        public void flush() {
            throw new AssertionError();
        }

        public void close() {
            throw new AssertionError();
        }
    };
    private static final JsonPrimitive b = new JsonPrimitive("closed");
    private final List<JsonElement> c = new ArrayList();
    private String d;
    private JsonElement e = JsonNull.INSTANCE;

    public void flush() {
    }

    public JsonTreeWriter() {
        super(a);
    }

    public JsonElement get() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected one JSON element but was ");
        sb.append(this.c);
        throw new IllegalStateException(sb.toString());
    }

    private JsonElement a() {
        return (JsonElement) this.c.get(this.c.size() - 1);
    }

    private void a(JsonElement jsonElement) {
        if (this.d != null) {
            if (!jsonElement.isJsonNull() || getSerializeNulls()) {
                ((JsonObject) a()).add(this.d, jsonElement);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = jsonElement;
        } else {
            JsonElement a2 = a();
            if (a2 instanceof JsonArray) {
                ((JsonArray) a2).add(jsonElement);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public JsonWriter beginArray() {
        JsonArray jsonArray = new JsonArray();
        a(jsonArray);
        this.c.add(jsonArray);
        return this;
    }

    public JsonWriter endArray() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof JsonArray) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter beginObject() {
        JsonObject jsonObject = new JsonObject();
        a(jsonObject);
        this.c.add(jsonObject);
        return this;
    }

    public JsonWriter endObject() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof JsonObject) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter name(String str) {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (a() instanceof JsonObject) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter value(String str) {
        if (str == null) {
            return nullValue();
        }
        a(new JsonPrimitive(str));
        return this;
    }

    public JsonWriter nullValue() {
        a(JsonNull.INSTANCE);
        return this;
    }

    public JsonWriter value(boolean z) {
        a(new JsonPrimitive(Boolean.valueOf(z)));
        return this;
    }

    public JsonWriter value(Boolean bool) {
        if (bool == null) {
            return nullValue();
        }
        a(new JsonPrimitive(bool));
        return this;
    }

    public JsonWriter value(double d2) {
        if (isLenient() || (!Double.isNaN(d2) && !Double.isInfinite(d2))) {
            a(new JsonPrimitive((Number) Double.valueOf(d2)));
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSON forbids NaN and infinities: ");
        sb.append(d2);
        throw new IllegalArgumentException(sb.toString());
    }

    public JsonWriter value(long j) {
        a(new JsonPrimitive((Number) Long.valueOf(j)));
        return this;
    }

    public JsonWriter value(Number number) {
        if (number == null) {
            return nullValue();
        }
        if (!isLenient()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                StringBuilder sb = new StringBuilder();
                sb.append("JSON forbids NaN and infinities: ");
                sb.append(number);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        a(new JsonPrimitive(number));
        return this;
    }

    public void close() {
        if (!this.c.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.c.add(b);
    }
}
