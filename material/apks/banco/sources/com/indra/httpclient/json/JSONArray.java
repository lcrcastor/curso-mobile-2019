package com.indra.httpclient.json;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class JSONArray {
    private final ArrayList<Object> a;

    public JSONArray() {
        this.a = new ArrayList<>();
    }

    public JSONArray(JSONTokener jSONTokener) {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        } else if (jSONTokener.nextClean() != ']') {
            jSONTokener.back();
            while (true) {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.a.add(JSONObject.NULL);
                } else {
                    jSONTokener.back();
                    this.a.add(jSONTokener.nextValue());
                }
                char nextClean = jSONTokener.nextClean();
                if (nextClean == ',' || nextClean == ';') {
                    if (jSONTokener.nextClean() != ']') {
                        jSONTokener.back();
                    } else {
                        return;
                    }
                } else if (nextClean != ']') {
                    throw jSONTokener.syntaxError("Expected a ',' or ']'");
                } else {
                    return;
                }
            }
        }
    }

    public JSONArray(String str) {
        this(new JSONTokener(str));
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r3v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public JSONArray(java.util.Collection<java.lang.Object> r3) {
        /*
            r2 = this;
            r2.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.a = r0
            if (r3 == 0) goto L_0x0024
            java.util.Iterator r3 = r3.iterator()
        L_0x0010:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0024
            java.lang.Object r0 = r3.next()
            java.util.ArrayList<java.lang.Object> r1 = r2.a
            java.lang.Object r0 = com.indra.httpclient.json.JSONObject.wrap(r0)
            r1.add(r0)
            goto L_0x0010
        L_0x0024:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.JSONArray.<init>(java.util.Collection):void");
    }

    public JSONArray(Object obj) {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                put(JSONObject.wrap(Array.get(obj, i)));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }

    public Object get(int i) {
        Object opt = opt(i);
        if (opt != null) {
            return opt;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray[");
        sb.append(i);
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public boolean getBoolean(int i) {
        Object obj = get(i);
        if (!obj.equals(Boolean.FALSE)) {
            boolean z = obj instanceof String;
            if (!z || !((String) obj).equalsIgnoreCase(Reintento.Reintento_Falso)) {
                if (obj.equals(Boolean.TRUE) || (z && ((String) obj).equalsIgnoreCase("true"))) {
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("JSONArray[");
                sb.append(i);
                sb.append("] is not a boolean.");
                throw new JSONException(sb.toString());
            }
        }
        return false;
    }

    public double getDouble(int i) {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            }
            return Double.parseDouble((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONArray[");
            sb.append(i);
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public int getInt(int i) {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }
            return Integer.parseInt((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONArray[");
            sb.append(i);
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public JSONArray getJSONArray(int i) {
        Object obj = get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray[");
        sb.append(i);
        sb.append("] is not a JSONArray.");
        throw new JSONException(sb.toString());
    }

    public JSONObject getJSONObject(int i) {
        Object obj = get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray[");
        sb.append(i);
        sb.append("] is not a JSONObject.");
        throw new JSONException(sb.toString());
    }

    public long getLong(int i) {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }
            return Long.parseLong((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONArray[");
            sb.append(i);
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public String getString(int i) {
        Object obj = get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONArray[");
        sb.append(i);
        sb.append("] not a string.");
        throw new JSONException(sb.toString());
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String str) {
        int length = length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append(JSONObject.valueToString(this.a.get(i)));
        }
        return sb.toString();
    }

    public int length() {
        return this.a.size();
    }

    public Object opt(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.a.get(i);
    }

    public boolean optBoolean(int i) {
        return optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean z) {
        try {
            return getBoolean(i);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(int i) {
        return optDouble(i, Double.NaN);
    }

    public double optDouble(int i, double d) {
        try {
            return getDouble(i);
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(int i) {
        return optInt(i, 0);
    }

    public int optInt(int i, int i2) {
        try {
            return getInt(i);
        } catch (Exception unused) {
            return i2;
        }
    }

    public JSONArray optJSONArray(int i) {
        Object opt = opt(i);
        if (opt instanceof JSONArray) {
            return (JSONArray) opt;
        }
        return null;
    }

    public JSONObject optJSONObject(int i) {
        Object opt = opt(i);
        if (opt instanceof JSONObject) {
            return (JSONObject) opt;
        }
        return null;
    }

    public long optLong(int i) {
        return optLong(i, 0);
    }

    public long optLong(int i, long j) {
        try {
            return getLong(i);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(int i) {
        return optString(i, "");
    }

    public String optString(int i, String str) {
        Object opt = opt(i);
        return JSONObject.NULL.equals(opt) ? str : opt.toString();
    }

    public JSONArray put(boolean z) {
        put((Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(Collection collection) {
        put((Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(double d) {
        Double valueOf = Double.valueOf(d);
        JSONObject.testValidity(valueOf);
        put((Object) valueOf);
        return this;
    }

    public JSONArray put(int i) {
        put((Object) Integer.valueOf(i));
        return this;
    }

    public JSONArray put(long j) {
        put((Object) new Long(j));
        return this;
    }

    public JSONArray put(Map map) {
        put((Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(Object obj) {
        this.a.add(obj);
        return this;
    }

    public JSONArray put(int i, boolean z) {
        put(i, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(int i, Collection collection) {
        put(i, (Object) new JSONArray(collection));
        return this;
    }

    public JSONArray put(int i, double d) {
        put(i, (Object) Double.valueOf(d));
        return this;
    }

    public JSONArray put(int i, int i2) {
        put(i, (Object) Integer.valueOf(i2));
        return this;
    }

    public JSONArray put(int i, long j) {
        put(i, (Object) Long.valueOf(j));
        return this;
    }

    public JSONArray put(int i, Map map) {
        put(i, (Object) new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, Object obj) {
        JSONObject.testValidity(obj);
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONArray[");
            sb.append(i);
            sb.append("] not found.");
            throw new JSONException(sb.toString());
        }
        if (i < length()) {
            this.a.set(i, obj);
        } else {
            while (i != length()) {
                put(JSONObject.NULL);
            }
            put(obj);
        }
        return this;
    }

    public Object remove(int i) {
        Object opt = opt(i);
        this.a.remove(i);
        return opt;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0 || length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONObject.put(jSONArray.getString(i), opt(i));
        }
        return jSONObject;
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            sb.append(join(","));
            sb.append(']');
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public String a(int i, int i2) {
        int length = length();
        if (length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        if (length == 1) {
            sb.append(JSONObject.a(this.a.get(0), i, i2));
        } else {
            int i3 = i2 + i;
            sb.append(10);
            for (int i4 = 0; i4 < length; i4++) {
                if (i4 > 0) {
                    sb.append(",\n");
                }
                for (int i5 = 0; i5 < i3; i5++) {
                    sb.append(TokenParser.SP);
                }
                sb.append(JSONObject.a(this.a.get(i4), i, i3));
            }
            sb.append(10);
            for (int i6 = 0; i6 < i2; i6++) {
                sb.append(TokenParser.SP);
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public Writer write(Writer writer) {
        try {
            int length = length();
            writer.write(91);
            int i = 0;
            boolean z = false;
            while (i < length) {
                if (z) {
                    writer.write(44);
                }
                Object obj = this.a.get(i);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(JSONObject.valueToString(obj));
                }
                i++;
                z = true;
            }
            writer.write(93);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
