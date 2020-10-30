package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;

public final class JsonTreeReader extends JsonReader {
    private static final Reader b = new Reader() {
        public int read(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }

        public void close() {
            throw new AssertionError();
        }
    };
    private static final Object c = new Object();
    private Object[] d = new Object[32];
    private int e = 0;
    private String[] f = new String[32];
    private int[] g = new int[32];

    public JsonTreeReader(JsonElement jsonElement) {
        super(b);
        a((Object) jsonElement);
    }

    public void beginArray() {
        a(JsonToken.BEGIN_ARRAY);
        a((Object) ((JsonArray) c()).iterator());
        this.g[this.e - 1] = 0;
    }

    public void endArray() {
        a(JsonToken.END_ARRAY);
        d();
        d();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public void beginObject() {
        a(JsonToken.BEGIN_OBJECT);
        a((Object) ((JsonObject) c()).entrySet().iterator());
    }

    public void endObject() {
        a(JsonToken.END_OBJECT);
        d();
        d();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public boolean hasNext() {
        JsonToken peek = peek();
        return (peek == JsonToken.END_OBJECT || peek == JsonToken.END_ARRAY) ? false : true;
    }

    public JsonToken peek() {
        if (this.e == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object c2 = c();
        if (c2 instanceof Iterator) {
            boolean z = this.d[this.e - 2] instanceof JsonObject;
            Iterator it = (Iterator) c2;
            if (!it.hasNext()) {
                return z ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            } else if (z) {
                return JsonToken.NAME;
            } else {
                a(it.next());
                return peek();
            }
        } else if (c2 instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        } else {
            if (c2 instanceof JsonArray) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (c2 instanceof JsonPrimitive) {
                JsonPrimitive jsonPrimitive = (JsonPrimitive) c2;
                if (jsonPrimitive.isString()) {
                    return JsonToken.STRING;
                }
                if (jsonPrimitive.isBoolean()) {
                    return JsonToken.BOOLEAN;
                }
                if (jsonPrimitive.isNumber()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            } else if (c2 instanceof JsonNull) {
                return JsonToken.NULL;
            } else {
                if (c2 == c) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    private Object c() {
        return this.d[this.e - 1];
    }

    private Object d() {
        Object[] objArr = this.d;
        int i = this.e - 1;
        this.e = i;
        Object obj = objArr[i];
        this.d[this.e] = null;
        return obj;
    }

    private void a(JsonToken jsonToken) {
        if (peek() != jsonToken) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected ");
            sb.append(jsonToken);
            sb.append(" but was ");
            sb.append(peek());
            sb.append(e());
            throw new IllegalStateException(sb.toString());
        }
    }

    public String nextName() {
        a(JsonToken.NAME);
        Entry entry = (Entry) ((Iterator) c()).next();
        String str = (String) entry.getKey();
        this.f[this.e - 1] = str;
        a(entry.getValue());
        return str;
    }

    public String nextString() {
        JsonToken peek = peek();
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            String asString = ((JsonPrimitive) d()).getAsString();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return asString;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected ");
        sb.append(JsonToken.STRING);
        sb.append(" but was ");
        sb.append(peek);
        sb.append(e());
        throw new IllegalStateException(sb.toString());
    }

    public boolean nextBoolean() {
        a(JsonToken.BOOLEAN);
        boolean asBoolean = ((JsonPrimitive) d()).getAsBoolean();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return asBoolean;
    }

    public void nextNull() {
        a(JsonToken.NULL);
        d();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public double nextDouble() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            double asDouble = ((JsonPrimitive) c()).getAsDouble();
            if (isLenient() || (!Double.isNaN(asDouble) && !Double.isInfinite(asDouble))) {
                d();
                if (this.e > 0) {
                    int[] iArr = this.g;
                    int i = this.e - 1;
                    iArr[i] = iArr[i] + 1;
                }
                return asDouble;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("JSON forbids NaN and infinities: ");
            sb.append(asDouble);
            throw new NumberFormatException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Expected ");
        sb2.append(JsonToken.NUMBER);
        sb2.append(" but was ");
        sb2.append(peek);
        sb2.append(e());
        throw new IllegalStateException(sb2.toString());
    }

    public long nextLong() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            long asLong = ((JsonPrimitive) c()).getAsLong();
            d();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return asLong;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected ");
        sb.append(JsonToken.NUMBER);
        sb.append(" but was ");
        sb.append(peek);
        sb.append(e());
        throw new IllegalStateException(sb.toString());
    }

    public int nextInt() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            int asInt = ((JsonPrimitive) c()).getAsInt();
            d();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return asInt;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected ");
        sb.append(JsonToken.NUMBER);
        sb.append(" but was ");
        sb.append(peek);
        sb.append(e());
        throw new IllegalStateException(sb.toString());
    }

    public void close() {
        this.d = new Object[]{c};
        this.e = 1;
    }

    public void skipValue() {
        if (peek() == JsonToken.NAME) {
            nextName();
            this.f[this.e - 2] = "null";
        } else {
            d();
            if (this.e > 0) {
                this.f[this.e - 1] = "null";
            }
        }
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public void promoteNameToValue() {
        a(JsonToken.NAME);
        Entry entry = (Entry) ((Iterator) c()).next();
        a(entry.getValue());
        a((Object) new JsonPrimitive((String) entry.getKey()));
    }

    private void a(Object obj) {
        if (this.e == this.d.length) {
            Object[] objArr = new Object[(this.e * 2)];
            int[] iArr = new int[(this.e * 2)];
            String[] strArr = new String[(this.e * 2)];
            System.arraycopy(this.d, 0, objArr, 0, this.e);
            System.arraycopy(this.g, 0, iArr, 0, this.e);
            System.arraycopy(this.f, 0, strArr, 0, this.e);
            this.d = objArr;
            this.g = iArr;
            this.f = strArr;
        }
        Object[] objArr2 = this.d;
        int i = this.e;
        this.e = i + 1;
        objArr2[i] = obj;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i = 0;
        while (i < this.e) {
            if (this.d[i] instanceof JsonArray) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    sb.append('[');
                    sb.append(this.g[i]);
                    sb.append(']');
                }
            } else if (this.d[i] instanceof JsonObject) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    sb.append('.');
                    if (this.f[i] != null) {
                        sb.append(this.f[i]);
                    }
                }
            }
            i++;
        }
        return sb.toString();
    }

    private String e() {
        StringBuilder sb = new StringBuilder();
        sb.append(" at path ");
        sb.append(getPath());
        return sb.toString();
    }
}
