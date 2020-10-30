package com.google.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private final List<JsonElement> a;

    public JsonArray() {
        this.a = new ArrayList();
    }

    public JsonArray(int i) {
        this.a = new ArrayList(i);
    }

    public JsonArray deepCopy() {
        if (this.a.isEmpty()) {
            return new JsonArray();
        }
        JsonArray jsonArray = new JsonArray(this.a.size());
        for (JsonElement deepCopy : this.a) {
            jsonArray.add(deepCopy.deepCopy());
        }
        return jsonArray;
    }

    public void add(Boolean bool) {
        this.a.add(bool == null ? JsonNull.INSTANCE : new JsonPrimitive(bool));
    }

    public void add(Character ch) {
        this.a.add(ch == null ? JsonNull.INSTANCE : new JsonPrimitive(ch));
    }

    public void add(Number number) {
        this.a.add(number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void add(String str) {
        this.a.add(str == null ? JsonNull.INSTANCE : new JsonPrimitive(str));
    }

    public void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.a.add(jsonElement);
    }

    public void addAll(JsonArray jsonArray) {
        this.a.addAll(jsonArray.a);
    }

    public JsonElement set(int i, JsonElement jsonElement) {
        return (JsonElement) this.a.set(i, jsonElement);
    }

    public boolean remove(JsonElement jsonElement) {
        return this.a.remove(jsonElement);
    }

    public JsonElement remove(int i) {
        return (JsonElement) this.a.remove(i);
    }

    public boolean contains(JsonElement jsonElement) {
        return this.a.contains(jsonElement);
    }

    public int size() {
        return this.a.size();
    }

    public Iterator<JsonElement> iterator() {
        return this.a.iterator();
    }

    public JsonElement get(int i) {
        return (JsonElement) this.a.get(i);
    }

    public Number getAsNumber() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsNumber();
        }
        throw new IllegalStateException();
    }

    public String getAsString() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsString();
        }
        throw new IllegalStateException();
    }

    public double getAsDouble() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsDouble();
        }
        throw new IllegalStateException();
    }

    public BigDecimal getAsBigDecimal() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }

    public BigInteger getAsBigInteger() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsBigInteger();
        }
        throw new IllegalStateException();
    }

    public float getAsFloat() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsFloat();
        }
        throw new IllegalStateException();
    }

    public long getAsLong() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsLong();
        }
        throw new IllegalStateException();
    }

    public int getAsInt() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsInt();
        }
        throw new IllegalStateException();
    }

    public byte getAsByte() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsByte();
        }
        throw new IllegalStateException();
    }

    public char getAsCharacter() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsCharacter();
        }
        throw new IllegalStateException();
    }

    public short getAsShort() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsShort();
        }
        throw new IllegalStateException();
    }

    public boolean getAsBoolean() {
        if (this.a.size() == 1) {
            return ((JsonElement) this.a.get(0)).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonArray) && ((JsonArray) obj).a.equals(this.a));
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
