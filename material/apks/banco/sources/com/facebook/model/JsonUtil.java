package com.facebook.model;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class JsonUtil {

    static final class JSONObjectEntry implements Entry<String, Object> {
        private final String a;
        private final Object b;

        JSONObjectEntry(String str, Object obj) {
            this.a = str;
            this.b = obj;
        }

        @SuppressLint({"FieldGetter"})
        /* renamed from: a */
        public String getKey() {
            return this.a;
        }

        public Object getValue() {
            return this.b;
        }

        public Object setValue(Object obj) {
            throw new UnsupportedOperationException("JSONObjectEntry is immutable");
        }
    }

    JsonUtil() {
    }

    static void a(JSONObject jSONObject) {
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            keys.next();
            keys.remove();
        }
    }

    static boolean a(JSONObject jSONObject, Object obj) {
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            Object opt = jSONObject.opt((String) keys.next());
            if (opt != null && opt.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    static Set<Entry<String, Object>> b(JSONObject jSONObject) {
        HashSet hashSet = new HashSet();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            hashSet.add(new JSONObjectEntry(str, jSONObject.opt(str)));
        }
        return hashSet;
    }

    static Set<String> c(JSONObject jSONObject) {
        HashSet hashSet = new HashSet();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            hashSet.add(keys.next());
        }
        return hashSet;
    }

    static void a(JSONObject jSONObject, Map<String, Object> map) {
        for (Entry entry : map.entrySet()) {
            try {
                jSONObject.putOpt((String) entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    static Collection<Object> d(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            arrayList.add(jSONObject.opt((String) keys.next()));
        }
        return arrayList;
    }
}
