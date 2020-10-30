package com.indra.httpclient.json;

import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import com.google.gson.annotations.SerializedName;
import cz.msebera.android.httpclient.message.TokenParser;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class XML {
    public static final Character AMP = Character.valueOf('&');
    public static final Character APOS = Character.valueOf('\'');
    public static final Character BANG = Character.valueOf('!');
    public static final Character EQ = Character.valueOf('=');
    public static final Character GT = Character.valueOf('>');
    public static final Character LT = Character.valueOf('<');
    public static final Character QUEST = Character.valueOf('?');
    public static final Character QUOT = Character.valueOf(TokenParser.DQUOTE);
    public static final Character SLASH = Character.valueOf('/');

    public static String escape(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '\"') {
                sb.append("&quot;");
            } else if (charAt == '<') {
                sb.append("&lt;");
            } else if (charAt != '>') {
                switch (charAt) {
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '\'':
                        sb.append("&apos;");
                        break;
                    default:
                        sb.append(charAt);
                        break;
                }
            } else {
                sb.append("&gt;");
            }
        }
        return sb.toString();
    }

    public static void noSpace(String str) {
        int length = str.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                StringBuilder sb = new StringBuilder();
                sb.append("'");
                sb.append(str);
                sb.append("' contains a space character.");
                throw new JSONException(sb.toString());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00f2, code lost:
        r5 = r7.nextToken();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00f8, code lost:
        if ((r5 instanceof java.lang.String) != false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0100, code lost:
        throw r7.syntaxError("Missing value");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(com.indra.httpclient.json.XMLTokener r7, com.indra.httpclient.json.JSONObject r8, java.lang.String r9, java.lang.Class r10) {
        /*
            java.lang.Object r0 = r7.nextToken()
            java.lang.Character r1 = BANG
            r2 = 1
            r3 = 0
            if (r0 != r1) goto L_0x006c
            char r9 = r7.next()
            r10 = 45
            if (r9 != r10) goto L_0x0022
            char r8 = r7.next()
            if (r8 != r10) goto L_0x001e
            java.lang.String r8 = "-->"
            r7.skipPast(r8)
            return r3
        L_0x001e:
            r7.back()
            goto L_0x004f
        L_0x0022:
            r10 = 91
            if (r9 != r10) goto L_0x004f
            java.lang.Object r9 = r7.nextToken()
            java.lang.String r0 = "CDATA"
            boolean r9 = r0.equals(r9)
            if (r9 == 0) goto L_0x0048
            char r9 = r7.next()
            if (r9 != r10) goto L_0x0048
            java.lang.String r7 = r7.nextCDATA()
            int r9 = r7.length()
            if (r9 <= 0) goto L_0x0047
            java.lang.String r9 = "content"
            r8.accumulate(r9, r7)
        L_0x0047:
            return r3
        L_0x0048:
            java.lang.String r8 = "Expected 'CDATA['"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x004f:
            java.lang.Object r8 = r7.nextMeta()
            if (r8 != 0) goto L_0x005c
            java.lang.String r8 = "Missing '>' after '<!'."
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x005c:
            java.lang.Character r9 = LT
            if (r8 != r9) goto L_0x0063
            int r2 = r2 + 1
            goto L_0x0069
        L_0x0063:
            java.lang.Character r9 = GT
            if (r8 != r9) goto L_0x0069
            int r2 = r2 + -1
        L_0x0069:
            if (r2 > 0) goto L_0x004f
            return r3
        L_0x006c:
            java.lang.Character r1 = QUEST
            if (r0 != r1) goto L_0x0076
            java.lang.String r8 = "?>"
            r7.skipPast(r8)
            return r3
        L_0x0076:
            java.lang.Character r1 = SLASH
            if (r0 != r1) goto L_0x00ca
            java.lang.Object r8 = r7.nextToken()
            if (r9 != 0) goto L_0x0096
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Mismatched close tag "
            r9.append(r10)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x0096:
            boolean r10 = r8.equals(r9)
            if (r10 != 0) goto L_0x00ba
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r0 = "Mismatched "
            r10.append(r0)
            r10.append(r9)
            java.lang.String r9 = " and "
            r10.append(r9)
            r10.append(r8)
            java.lang.String r8 = r10.toString()
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x00ba:
            java.lang.Object r8 = r7.nextToken()
            java.lang.Character r9 = GT
            if (r8 == r9) goto L_0x00c9
            java.lang.String r8 = "Misshaped close tag"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x00c9:
            return r2
        L_0x00ca:
            boolean r9 = r0 instanceof java.lang.Character
            if (r9 == 0) goto L_0x00d5
            java.lang.String r8 = "Misshaped tag"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x00d5:
            java.lang.String r0 = (java.lang.String) r0
            com.indra.httpclient.json.JSONObject r9 = new com.indra.httpclient.json.JSONObject
            r9.<init>()
            r1 = 0
        L_0x00dd:
            r4 = r1
        L_0x00de:
            if (r4 != 0) goto L_0x00e4
            java.lang.Object r4 = r7.nextToken()
        L_0x00e4:
            boolean r5 = r4 instanceof java.lang.String
            if (r5 == 0) goto L_0x0110
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r7.nextToken()
            java.lang.Character r6 = EQ
            if (r5 != r6) goto L_0x010b
            java.lang.Object r5 = r7.nextToken()
            boolean r6 = r5 instanceof java.lang.String
            if (r6 != 0) goto L_0x0101
            java.lang.String r8 = "Missing value"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x0101:
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r5 = stringToValue(r5)
            r9.accumulate(r4, r5)
            goto L_0x00dd
        L_0x010b:
            r9.accumulate(r4, r1)
            r4 = r5
            goto L_0x00de
        L_0x0110:
            java.lang.Character r5 = SLASH
            if (r4 != r5) goto L_0x0131
            java.lang.Object r10 = r7.nextToken()
            java.lang.Character r2 = GT
            if (r10 == r2) goto L_0x0123
            java.lang.String r8 = "Misshaped tag"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x0123:
            int r7 = r9.length()
            if (r7 <= 0) goto L_0x012d
            r8.accumulate(r0, r9)
            goto L_0x0130
        L_0x012d:
            r8.accumulate(r0, r1)
        L_0x0130:
            return r3
        L_0x0131:
            java.lang.Character r5 = GT
            if (r4 != r5) goto L_0x020c
        L_0x0135:
            java.lang.Object r4 = r7.nextContent()
            if (r4 != 0) goto L_0x0154
            if (r0 == 0) goto L_0x0153
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Unclosed tag "
            r8.append(r9)
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        L_0x0153:
            return r3
        L_0x0154:
            boolean r5 = r4 instanceof java.lang.String
            if (r5 == 0) goto L_0x016a
            java.lang.String r4 = (java.lang.String) r4
            int r5 = r4.length()
            if (r5 <= 0) goto L_0x0135
            java.lang.String r5 = "content"
            java.lang.Object r4 = stringToValue(r4)
            r9.accumulate(r5, r4)
            goto L_0x0135
        L_0x016a:
            java.lang.Character r5 = LT
            if (r4 != r5) goto L_0x0135
            if (r10 == 0) goto L_0x0175
            java.lang.reflect.Field r4 = a(r10, r0)
            goto L_0x0176
        L_0x0175:
            r4 = r1
        L_0x0176:
            if (r4 == 0) goto L_0x01b6
            java.lang.Class<java.util.List> r5 = java.util.List.class
            java.lang.Class r6 = r4.getType()
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x01b6
            java.lang.Class r5 = r4.getType()
            boolean r5 = r5.isPrimitive()
            if (r5 != 0) goto L_0x01b6
            java.lang.Class r5 = r4.getType()
            boolean r5 = r5.isArray()
            if (r5 != 0) goto L_0x01b6
            java.lang.Class r5 = r4.getType()
            boolean r5 = r5.isEnum()
            if (r5 != 0) goto L_0x01b6
            java.lang.Class r5 = r4.getType()
            java.lang.String r5 = r5.getCanonicalName()
            java.lang.String r6 = "java.lang"
            boolean r5 = r5.contains(r6)
            if (r5 != 0) goto L_0x01b6
            java.lang.Class r10 = r4.getType()
        L_0x01b6:
            boolean r5 = a(r7, r9, r0, r10)
            if (r5 == 0) goto L_0x0135
            int r7 = r9.length()
            if (r7 != 0) goto L_0x01c8
            java.lang.String r7 = ""
            r8.accumulate(r0, r7)
            goto L_0x020b
        L_0x01c8:
            int r7 = r9.length()
            if (r7 != r2) goto L_0x01e0
            java.lang.String r7 = "content"
            java.lang.Object r7 = r9.opt(r7)
            if (r7 == 0) goto L_0x01e0
            java.lang.String r7 = "content"
            java.lang.Object r7 = r9.opt(r7)
            r8.accumulate(r0, r7)
            goto L_0x020b
        L_0x01e0:
            if (r4 == 0) goto L_0x0208
            java.lang.Class<java.util.List> r7 = java.util.List.class
            java.lang.Class r10 = r4.getType()
            boolean r7 = r7.equals(r10)
            if (r7 != 0) goto L_0x01f8
            java.lang.Class r7 = r4.getType()
            boolean r7 = r7.isArray()
            if (r7 == 0) goto L_0x0208
        L_0x01f8:
            if (r8 == 0) goto L_0x0208
            boolean r7 = r8.has(r0)
            if (r7 != 0) goto L_0x0208
            com.indra.httpclient.json.JSONArray r7 = a(r9)
            r8.put(r0, r7)
            goto L_0x020b
        L_0x0208:
            r8.accumulate(r0, r9)
        L_0x020b:
            return r3
        L_0x020c:
            java.lang.String r8 = "Misshaped tag"
            com.indra.httpclient.json.JSONException r7 = r7.syntaxError(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.indra.httpclient.json.XML.a(com.indra.httpclient.json.XMLTokener, com.indra.httpclient.json.JSONObject, java.lang.String, java.lang.Class):boolean");
    }

    private static Field a(Class cls, String str) {
        if (cls != null) {
            try {
                Field[] fields = cls.getFields();
                for (int i = 0; i < fields.length; i++) {
                    Object value = (fields[i].getAnnotations().length <= 0 || fields[i].getAnnotation(SerializedName.class) == null) ? null : ((SerializedName) fields[i].getAnnotation(SerializedName.class)).value();
                    if ((value != null && str.equals(value)) || str.equals(fields[i].getName())) {
                        return fields[i];
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static JSONArray a(Object obj) {
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put((Object) (JSONObject) obj);
        return jSONArray;
    }

    public static Object stringToValue(String str) {
        if ("".equals(str)) {
            return str;
        }
        if ("true".equalsIgnoreCase(str)) {
            return Boolean.TRUE;
        }
        if (Reintento.Reintento_Falso.equalsIgnoreCase(str)) {
            return Boolean.FALSE;
        }
        return "null".equalsIgnoreCase(str) ? JSONObject.NULL : str;
    }

    public static JSONObject toJSONObject(String str, Class cls) {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(str);
        while (xMLTokener.more() && xMLTokener.skipPast("<")) {
            a(xMLTokener, jSONObject, null, cls);
        }
        return jSONObject;
    }

    public static JSONObject toJSONObject(String str) {
        return toJSONObject(str, null);
    }

    public static String toString(Object obj) {
        return toString(obj, null);
    }

    public static String toString(Object obj, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        if (obj instanceof JSONObject) {
            if (str != null) {
                sb.append('<');
                sb.append(str);
                sb.append('>');
            }
            JSONObject jSONObject = (JSONObject) obj;
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str3 = ((String) keys.next()).toString();
                Object opt = jSONObject.opt(str3);
                if (opt == null) {
                    opt = "";
                }
                if (opt instanceof String) {
                    String str4 = (String) opt;
                }
                if ("content".equals(str3)) {
                    if (opt instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) opt;
                        int length = jSONArray.length();
                        for (int i = 0; i < length; i++) {
                            if (i > 0) {
                                sb.append(10);
                            }
                            sb.append(escape(jSONArray.get(i).toString()));
                        }
                    } else {
                        sb.append(escape(opt.toString()));
                    }
                } else if (opt instanceof JSONArray) {
                    JSONArray jSONArray2 = (JSONArray) opt;
                    int length2 = jSONArray2.length();
                    for (int i2 = 0; i2 < length2; i2++) {
                        Object obj2 = jSONArray2.get(i2);
                        if (obj2 instanceof JSONArray) {
                            sb.append('<');
                            sb.append(str3);
                            sb.append('>');
                            sb.append(toString(obj2));
                            sb.append("</");
                            sb.append(str3);
                            sb.append('>');
                        } else {
                            sb.append(toString(obj2, str3));
                        }
                    }
                } else if ("".equals(opt)) {
                    sb.append('<');
                    sb.append(str3);
                    sb.append("/>");
                } else {
                    sb.append(toString(opt, str3));
                }
            }
            if (str != null) {
                sb.append("</");
                sb.append(str);
                sb.append('>');
            }
            return sb.toString();
        }
        if (obj.getClass().isArray()) {
            obj = new JSONArray((Collection) new ArrayList((Collection) obj));
        }
        if (obj instanceof JSONArray) {
            JSONArray jSONArray3 = (JSONArray) obj;
            int length3 = jSONArray3.length();
            for (int i3 = 0; i3 < length3; i3++) {
                sb.append(toString(jSONArray3.opt(i3), str == null ? "array" : str));
            }
            return sb.toString();
        }
        String escape = escape(obj.toString());
        if (str == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("\"");
            sb2.append(escape);
            sb2.append("\"");
            str2 = sb2.toString();
        } else if (escape.length() == 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("<");
            sb3.append(str);
            sb3.append("/>");
            str2 = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("<");
            sb4.append(str);
            sb4.append(">");
            sb4.append(escape);
            sb4.append("</");
            sb4.append(str);
            sb4.append(">");
            str2 = sb4.toString();
        }
        return str2;
    }
}
