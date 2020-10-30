package com.indra.httpclient.json;

import cz.msebera.android.httpclient.message.TokenParser;

public class CDL {
    private static String a(JSONTokener jSONTokener) {
        char next;
        while (true) {
            next = jSONTokener.next();
            if (next != ' ' && next != 9) {
                break;
            }
        }
        if (next == 0) {
            return null;
        }
        if (next == '\"' || next == '\'') {
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                char next2 = jSONTokener.next();
                if (next2 == next) {
                    return stringBuffer.toString();
                }
                if (next2 == 0 || next2 == 10 || next2 == 13) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Missing close quote '");
                    sb.append(next);
                    sb.append("'.");
                } else {
                    stringBuffer.append(next2);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Missing close quote '");
            sb2.append(next);
            sb2.append("'.");
            throw jSONTokener.syntaxError(sb2.toString());
        } else if (next != ',') {
            jSONTokener.back();
            return jSONTokener.nextTo(',');
        } else {
            jSONTokener.back();
            return "";
        }
    }

    public static JSONArray rowToJSONArray(JSONTokener jSONTokener) {
        JSONArray jSONArray = new JSONArray();
        while (true) {
            String a = a(jSONTokener);
            char next = jSONTokener.next();
            if (a != null && (jSONArray.length() != 0 || a.length() != 0 || next == ',')) {
                jSONArray.put((Object) a);
                while (true) {
                    if (next != ',') {
                        if (next == ' ') {
                            next = jSONTokener.next();
                        } else if (next == 10 || next == 13 || next == 0) {
                            return jSONArray;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Bad character '");
                            sb.append(next);
                            sb.append("' (");
                            sb.append(next);
                            sb.append(").");
                            throw jSONTokener.syntaxError(sb.toString());
                        }
                    }
                }
            }
        }
        return null;
    }

    public static JSONObject rowToJSONObject(JSONArray jSONArray, JSONTokener jSONTokener) {
        JSONArray rowToJSONArray = rowToJSONArray(jSONTokener);
        if (rowToJSONArray != null) {
            return rowToJSONArray.toJSONObject(jSONArray);
        }
        return null;
    }

    public static String rowToString(JSONArray jSONArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jSONArray.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            Object opt = jSONArray.opt(i);
            if (opt != null) {
                String obj = opt.toString();
                if (obj.length() <= 0 || (obj.indexOf(44) < 0 && obj.indexOf(10) < 0 && obj.indexOf(13) < 0 && obj.indexOf(0) < 0 && obj.charAt(0) != '\"')) {
                    sb.append(obj);
                } else {
                    sb.append(TokenParser.DQUOTE);
                    int length = obj.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        char charAt = obj.charAt(i2);
                        if (charAt >= ' ' && charAt != '\"') {
                            sb.append(charAt);
                        }
                    }
                    sb.append(TokenParser.DQUOTE);
                }
            }
        }
        sb.append(10);
        return sb.toString();
    }

    public static JSONArray toJSONArray(String str) {
        return toJSONArray(new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONTokener jSONTokener) {
        return toJSONArray(rowToJSONArray(jSONTokener), jSONTokener);
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, String str) {
        return toJSONArray(jSONArray, new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, JSONTokener jSONTokener) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        while (true) {
            JSONObject rowToJSONObject = rowToJSONObject(jSONArray, jSONTokener);
            if (rowToJSONObject == null) {
                break;
            }
            jSONArray2.put((Object) rowToJSONObject);
        }
        if (jSONArray2.length() == 0) {
            return null;
        }
        return jSONArray2;
    }

    public static String toString(JSONArray jSONArray) {
        JSONObject optJSONObject = jSONArray.optJSONObject(0);
        if (optJSONObject != null) {
            JSONArray names = optJSONObject.names();
            if (names != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(rowToString(names));
                sb.append(toString(names, jSONArray));
                return sb.toString();
            }
        }
        return null;
    }

    public static String toString(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jSONArray2.length(); i++) {
            JSONObject optJSONObject = jSONArray2.optJSONObject(i);
            if (optJSONObject != null) {
                sb.append(rowToString(optJSONObject.toJSONArray(jSONArray)));
            }
        }
        return sb.toString();
    }
}
