package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map.Entry;
import java.util.Set;

public final class JsonObject extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> a = new LinkedTreeMap<>();

    public JsonObject deepCopy() {
        JsonObject jsonObject = new JsonObject();
        for (Entry entry : this.a.entrySet()) {
            jsonObject.add((String) entry.getKey(), ((JsonElement) entry.getValue()).deepCopy());
        }
        return jsonObject;
    }

    public void add(String str, JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.a.put(str, jsonElement);
    }

    public JsonElement remove(String str) {
        return (JsonElement) this.a.remove(str);
    }

    public void addProperty(String str, String str2) {
        add(str, a(str2));
    }

    public void addProperty(String str, Number number) {
        add(str, a(number));
    }

    public void addProperty(String str, Boolean bool) {
        add(str, a(bool));
    }

    public void addProperty(String str, Character ch) {
        add(str, a(ch));
    }

    private JsonElement a(Object obj) {
        return obj == null ? JsonNull.INSTANCE : new JsonPrimitive(obj);
    }

    public Set<Entry<String, JsonElement>> entrySet() {
        return this.a.entrySet();
    }

    public Set<String> keySet() {
        return this.a.keySet();
    }

    public int size() {
        return this.a.size();
    }

    public boolean has(String str) {
        return this.a.containsKey(str);
    }

    public JsonElement get(String str) {
        return (JsonElement) this.a.get(str);
    }

    public JsonPrimitive getAsJsonPrimitive(String str) {
        return (JsonPrimitive) this.a.get(str);
    }

    public JsonArray getAsJsonArray(String str) {
        return (JsonArray) this.a.get(str);
    }

    public JsonObject getAsJsonObject(String str) {
        return (JsonObject) this.a.get(str);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonObject) && ((JsonObject) obj).a.equals(this.a));
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
