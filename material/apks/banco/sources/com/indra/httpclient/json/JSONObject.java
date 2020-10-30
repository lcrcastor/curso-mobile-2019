package com.indra.httpclient.json;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import butterknife.internal.ButterKnifeProcessor;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import org.bouncycastle.asn1.eac.EACTags;

public class JSONObject {
    public static final Object NULL = new Null();
    private final Map a;

    static final class Null {
        /* access modifiers changed from: protected */
        public final Object clone() {
            return this;
        }

        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public String toString() {
            return "null";
        }

        private Null() {
        }
    }

    public JSONObject() {
        this.a = new HashMap();
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) {
        this();
        for (String str : strArr) {
            try {
                putOnce(str, jSONObject.opt(str));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(JSONTokener jSONTokener) {
        this();
        if (jSONTokener.nextClean() != '{') {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char nextClean = jSONTokener.nextClean();
            if (nextClean == 0) {
                throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
            } else if (nextClean != '}') {
                jSONTokener.back();
                String obj = jSONTokener.nextValue().toString();
                char nextClean2 = jSONTokener.nextClean();
                if (nextClean2 == '=') {
                    if (jSONTokener.next() != '>') {
                        jSONTokener.back();
                    }
                } else if (nextClean2 != ':') {
                    throw jSONTokener.syntaxError("Expected a ':' after a key");
                }
                putOnce(obj, jSONTokener.nextValue());
                char nextClean3 = jSONTokener.nextClean();
                if (nextClean3 == ',' || nextClean3 == ';') {
                    if (jSONTokener.nextClean() != '}') {
                        jSONTokener.back();
                    } else {
                        return;
                    }
                } else if (nextClean3 != '}') {
                    throw jSONTokener.syntaxError("Expected a ',' or '}'");
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public JSONObject(Map map) {
        this.a = new HashMap();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value != null) {
                    this.a.put(entry.getKey(), wrap(value));
                }
            }
        }
    }

    public JSONObject(Object obj) {
        this();
        a(obj);
    }

    public JSONObject(Object obj, String[] strArr) {
        this();
        Class cls = obj.getClass();
        for (String str : strArr) {
            try {
                putOpt(str, cls.getField(str).get(obj));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(String str) {
        this(new JSONTokener(str));
    }

    public JSONObject(String str, Locale locale) {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(str, locale, Thread.currentThread().getContextClassLoader());
        Enumeration keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            if (nextElement instanceof String) {
                String str2 = (String) nextElement;
                String[] split = str2.split("\\.");
                int length = split.length - 1;
                JSONObject jSONObject = this;
                for (int i = 0; i < length; i++) {
                    String str3 = split[i];
                    JSONObject optJSONObject = jSONObject.optJSONObject(str3);
                    if (optJSONObject == null) {
                        optJSONObject = new JSONObject();
                        jSONObject.put(str3, (Object) optJSONObject);
                    }
                    jSONObject = optJSONObject;
                }
                jSONObject.put(split[length], (Object) bundle.getString(str2));
            }
        }
    }

    public JSONObject accumulate(String str, Object obj) {
        testValidity(obj);
        Object opt = opt(str);
        if (opt == null) {
            if (obj instanceof JSONArray) {
                obj = new JSONArray().put(obj);
            }
            put(str, obj);
        } else if (opt instanceof JSONArray) {
            ((JSONArray) opt).put(obj);
        } else {
            put(str, (Object) new JSONArray().put(opt).put(obj));
        }
        return this;
    }

    public JSONObject append(String str, Object obj) {
        testValidity(obj);
        Object opt = opt(str);
        if (opt == null) {
            put(str, (Object) new JSONArray().put(obj));
        } else if (opt instanceof JSONArray) {
            put(str, (Object) ((JSONArray) opt).put(obj));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONObject[");
            sb.append(str);
            sb.append("] is not a JSONArray.");
            throw new JSONException(sb.toString());
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return "null";
        }
        String d2 = Double.toString(d);
        if (d2.indexOf(46) > 0 && d2.indexOf(101) < 0 && d2.indexOf(69) < 0) {
            while (d2.endsWith("0")) {
                d2 = d2.substring(0, d2.length() - 1);
            }
            if (d2.endsWith(".")) {
                d2 = d2.substring(0, d2.length() - 1);
            }
        }
        return d2;
    }

    public Object get(String str) {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        Object opt = opt(str);
        if (opt != null) {
            return opt;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject[");
        sb.append(quote(str));
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public boolean getBoolean(String str) {
        Object obj = get(str);
        if (!obj.equals(Boolean.FALSE)) {
            boolean z = obj instanceof String;
            if (!z || !((String) obj).equalsIgnoreCase(Reintento.Reintento_Falso)) {
                if (obj.equals(Boolean.TRUE) || (z && ((String) obj).equalsIgnoreCase("true"))) {
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("JSONObject[");
                sb.append(quote(str));
                sb.append("] is not a Boolean.");
                throw new JSONException(sb.toString());
            }
        }
        return false;
    }

    public double getDouble(String str) {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            }
            return Double.parseDouble((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONObject[");
            sb.append(quote(str));
            sb.append("] is not a number.");
            throw new JSONException(sb.toString());
        }
    }

    public int getInt(String str) {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }
            return Integer.parseInt((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONObject[");
            sb.append(quote(str));
            sb.append("] is not an int.");
            throw new JSONException(sb.toString());
        }
    }

    public JSONArray getJSONArray(String str) {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject[");
        sb.append(quote(str));
        sb.append("] is not a JSONArray.");
        throw new JSONException(sb.toString());
    }

    public JSONObject getJSONObject(String str) {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject[");
        sb.append(quote(str));
        sb.append("] is not a JSONObject.");
        throw new JSONException(sb.toString());
    }

    public long getLong(String str) {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }
            return Long.parseLong((String) obj);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("JSONObject[");
            sb.append(quote(str));
            sb.append("] is not a long.");
            throw new JSONException(sb.toString());
        }
    }

    public static String[] getNames(JSONObject jSONObject) {
        int length = jSONObject.length();
        if (length == 0) {
            return null;
        }
        Iterator keys = jSONObject.keys();
        String[] strArr = new String[length];
        int i = 0;
        while (keys.hasNext()) {
            strArr[i] = (String) keys.next();
            i++;
        }
        return strArr;
    }

    public static String[] getNames(Object obj) {
        if (obj == null) {
            return null;
        }
        Field[] fields = obj.getClass().getFields();
        int length = fields.length;
        if (length == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public String getString(String str) {
        Object obj = get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("JSONObject[");
        sb.append(quote(str));
        sb.append("] not a string.");
        throw new JSONException(sb.toString());
    }

    public boolean has(String str) {
        return this.a.containsKey(str);
    }

    public JSONObject increment(String str) {
        Object opt = opt(str);
        if (opt == null) {
            put(str, 1);
        } else if (opt instanceof Integer) {
            put(str, ((Integer) opt).intValue() + 1);
        } else if (opt instanceof Long) {
            put(str, ((Long) opt).longValue() + 1);
        } else if (opt instanceof Double) {
            put(str, ((Double) opt).doubleValue() + 1.0d);
        } else if (opt instanceof Float) {
            put(str, (double) (((Float) opt).floatValue() + 1.0f));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to increment [");
            sb.append(quote(str));
            sb.append("].");
            throw new JSONException(sb.toString());
        }
        return this;
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return this.a.keySet().iterator();
    }

    public int length() {
        return this.a.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Iterator keys = keys();
        while (keys.hasNext()) {
            jSONArray.put(keys.next());
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }

    public static String numberToString(Number number) {
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(number);
        String obj = number.toString();
        if (obj.indexOf(46) <= 0 || obj.indexOf(101) >= 0 || obj.indexOf(69) >= 0) {
            return obj;
        }
        while (obj.endsWith("0")) {
            obj = obj.substring(0, obj.length() - 1);
        }
        return obj.endsWith(".") ? obj.substring(0, obj.length() - 1) : obj;
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.a.get(str);
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        try {
            return getDouble(str);
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        try {
            return getInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public JSONArray optJSONArray(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONArray) {
            return (JSONArray) opt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object opt = opt(str);
        if (opt instanceof JSONObject) {
            return (JSONObject) opt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0);
    }

    public long optLong(String str, long j) {
        try {
            return getLong(str);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object opt = opt(str);
        return NULL.equals(opt) ? str2 : opt.toString();
    }

    private void a(Object obj) {
        Method[] methodArr;
        Class cls = obj.getClass();
        if (cls.getClassLoader() != null) {
            methodArr = cls.getMethods();
        } else {
            methodArr = cls.getDeclaredMethods();
        }
        for (int i = 0; i < methodArr.length; i++) {
            try {
                Method method = methodArr[i];
                if (Modifier.isPublic(method.getModifiers())) {
                    String name = method.getName();
                    String str = "";
                    if (name.startsWith("get")) {
                        if (!"getClass".equals(name)) {
                            if (!"getDeclaringClass".equals(name)) {
                                str = name.substring(3);
                            }
                        }
                        str = "";
                    } else if (name.startsWith("is")) {
                        str = name.substring(2);
                    }
                    if (str.length() > 0 && Character.isUpperCase(str.charAt(0)) && method.getParameterTypes().length == 0) {
                        if (str.length() == 1) {
                            str = str.toLowerCase();
                        } else if (!Character.isUpperCase(str.charAt(1))) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str.substring(0, 1).toLowerCase());
                            sb.append(str.substring(1));
                            str = sb.toString();
                        }
                        Object invoke = method.invoke(obj, null);
                        if (invoke != null) {
                            this.a.put(str, wrap(invoke));
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject put(String str, boolean z) {
        put(str, (Object) z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String str, Collection collection) {
        put(str, (Object) new JSONArray(collection));
        return this;
    }

    public JSONObject put(String str, double d) {
        put(str, (Object) new Double(d));
        return this;
    }

    public JSONObject put(String str, int i) {
        put(str, (Object) new Integer(i));
        return this;
    }

    public JSONObject put(String str, long j) {
        put(str, (Object) new Long(j));
        return this;
    }

    public JSONObject put(String str, Map map) {
        put(str, (Object) new JSONObject(map));
        return this;
    }

    public JSONObject put(String str, Object obj) {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        if (obj != null) {
            testValidity(obj);
            this.a.put(str, obj);
        } else {
            remove(str);
        }
        return this;
    }

    public JSONObject putOnce(String str, Object obj) {
        if (!(str == null || obj == null)) {
            if (opt(str) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Duplicate key \"");
                sb.append(str);
                sb.append("\"");
                throw new JSONException(sb.toString());
            }
            put(str, obj);
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) {
        if (!(str == null || obj == null)) {
            put(str, obj);
        }
        return this;
    }

    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append(TokenParser.DQUOTE);
        int i = 0;
        char c = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt != '\"') {
                if (charAt != '/') {
                    if (charAt != '\\') {
                        switch (charAt) {
                            case 8:
                                stringBuffer.append("\\b");
                                break;
                            case 9:
                                stringBuffer.append("\\t");
                                break;
                            case 10:
                                stringBuffer.append("\\n");
                                break;
                            default:
                                switch (charAt) {
                                    case 12:
                                        stringBuffer.append("\\f");
                                        break;
                                    case 13:
                                        stringBuffer.append("\\r");
                                        break;
                                    default:
                                        if (charAt >= ' ' && ((charAt < 128 || charAt >= 160) && (charAt < 8192 || charAt >= 8448))) {
                                            stringBuffer.append(charAt);
                                            break;
                                        } else {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("000");
                                            sb.append(Integer.toHexString(charAt));
                                            String sb2 = sb.toString();
                                            StringBuilder sb3 = new StringBuilder();
                                            sb3.append("\\u");
                                            sb3.append(sb2.substring(sb2.length() - 4));
                                            stringBuffer.append(sb3.toString());
                                            break;
                                        }
                                        break;
                                }
                        }
                    }
                } else {
                    if (c == '<') {
                        stringBuffer.append(TokenParser.ESCAPE);
                    }
                    stringBuffer.append(charAt);
                }
                i++;
                c = charAt;
            }
            stringBuffer.append(TokenParser.ESCAPE);
            stringBuffer.append(charAt);
            i++;
            c = charAt;
        }
        stringBuffer.append(TokenParser.DQUOTE);
        return stringBuffer.toString();
    }

    public Object remove(String str) {
        return this.a.remove(str);
    }

    public static Object stringToValue(String str) {
        if (str.equals("")) {
            return str;
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Reintento.Reintento_Falso)) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("null")) {
            return NULL;
        }
        char charAt = str.charAt(0);
        if ((charAt >= '0' && charAt <= '9') || charAt == '.' || charAt == '-' || charAt == '+') {
            try {
                if (str.indexOf(46) <= -1 && str.indexOf(101) <= -1) {
                    if (str.indexOf(69) <= -1) {
                        Long l = new Long(str);
                        return l.longValue() == ((long) l.intValue()) ? new Integer(l.intValue()) : l;
                    }
                }
                Double valueOf = Double.valueOf(str);
                return (valueOf.isInfinite() || valueOf.isNaN()) ? str : valueOf;
            } catch (Exception unused) {
            }
        }
    }

    public static void testValidity(Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.isInfinite() || d.isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        } else if (obj instanceof Float) {
            Float f = (Float) obj;
            if (f.isInfinite() || f.isNaN()) {
                throw new JSONException("JSON does not allow non-finite numbers.");
            }
        }
    }

    public JSONArray toJSONArray(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    public String toString() {
        try {
            Iterator keys = keys();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (keys.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = keys.next();
                stringBuffer.append(quote(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(valueToString(this.a.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public String a(int i, int i2) {
        int i3;
        int length = length();
        if (length == 0) {
            return "{}";
        }
        Iterator keys = keys();
        int i4 = i2 + i;
        StringBuffer stringBuffer = new StringBuffer("{");
        if (length == 1) {
            Object next = keys.next();
            stringBuffer.append(quote(next.toString()));
            stringBuffer.append(": ");
            stringBuffer.append(a(this.a.get(next), i, i2));
        } else {
            while (true) {
                i3 = 0;
                if (!keys.hasNext()) {
                    break;
                }
                Object next2 = keys.next();
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(",\n");
                } else {
                    stringBuffer.append(10);
                }
                while (i3 < i4) {
                    stringBuffer.append(TokenParser.SP);
                    i3++;
                }
                stringBuffer.append(quote(next2.toString()));
                stringBuffer.append(": ");
                stringBuffer.append(a(this.a.get(next2), i, i4));
            }
            if (stringBuffer.length() > 1) {
                stringBuffer.append(10);
                while (i3 < i2) {
                    stringBuffer.append(TokenParser.SP);
                    i3++;
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public static String valueToString(Object obj) {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (obj instanceof JSONString) {
            try {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Bad value from toJSONString: ");
                sb.append(jSONString);
                throw new JSONException(sb.toString());
            } catch (Exception e) {
                throw new JSONException((Throwable) e);
            }
        } else if (obj instanceof Number) {
            return numberToString((Number) obj);
        } else {
            if ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) {
                return obj.toString();
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj).toString();
            }
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj).toString();
            }
            if (obj.getClass().isArray()) {
                return new JSONArray(obj).toString();
            }
            return quote(obj.toString());
        }
    }

    static String a(Object obj, int i, int i2) {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        try {
            if (obj instanceof JSONString) {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
            }
        } catch (Exception unused) {
        }
        if (obj instanceof Number) {
            return numberToString((Number) obj);
        }
        if (obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).a(i, i2);
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).a(i, i2);
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj).a(i, i2);
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj).a(i, i2);
        }
        if (obj.getClass().isArray()) {
            return new JSONArray(obj).a(i, i2);
        }
        return quote(obj.toString());
    }

    public static Object wrap(Object obj) {
        if (obj == null) {
            try {
                return NULL;
            } catch (Exception unused) {
                return null;
            }
        } else {
            if (!(obj instanceof JSONObject) && !(obj instanceof JSONArray) && !NULL.equals(obj) && !(obj instanceof JSONString) && !(obj instanceof Byte) && !(obj instanceof Character) && !(obj instanceof Short) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Boolean) && !(obj instanceof Float) && !(obj instanceof Double)) {
                if (!(obj instanceof String)) {
                    if (obj instanceof Collection) {
                        return new JSONArray((Collection) obj);
                    }
                    if (obj.getClass().isArray()) {
                        return new JSONArray(obj);
                    }
                    if (obj instanceof Map) {
                        return new JSONObject((Map) obj);
                    }
                    Package packageR = obj.getClass().getPackage();
                    String name = packageR != null ? packageR.getName() : "";
                    if (!name.startsWith(ButterKnifeProcessor.JAVA_PREFIX) && !name.startsWith("javax.")) {
                        if (obj.getClass().getClassLoader() != null) {
                            return new JSONObject(obj);
                        }
                    }
                    return obj.toString();
                }
            }
            return obj;
        }
    }

    public Writer write(Writer writer) {
        boolean z = false;
        try {
            Iterator keys = keys();
            writer.write(EACTags.SECURITY_ENVIRONMENT_TEMPLATE);
            while (keys.hasNext()) {
                if (z) {
                    writer.write(44);
                }
                Object next = keys.next();
                writer.write(quote(next.toString()));
                writer.write(58);
                Object obj = this.a.get(next);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(valueToString(obj));
                }
                z = true;
            }
            writer.write(EACTags.SECURE_MESSAGING_TEMPLATE);
            return writer;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }
}
