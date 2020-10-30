package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesTokenCachingStrategy extends TokenCachingStrategy {
    private static final String a = "SharedPreferencesTokenCachingStrategy";
    private String b;
    private SharedPreferences c;

    public SharedPreferencesTokenCachingStrategy(Context context) {
        this(context, null);
    }

    public SharedPreferencesTokenCachingStrategy(Context context, String str) {
        Validate.notNull(context, "context");
        if (Utility.isNullOrEmpty(str)) {
            str = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
        }
        this.b = str;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this.c = context.getSharedPreferences(this.b, 0);
    }

    public Bundle load() {
        Bundle bundle = new Bundle();
        for (String str : this.c.getAll().keySet()) {
            try {
                a(str, bundle);
            } catch (JSONException e) {
                LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                String str2 = a;
                StringBuilder sb = new StringBuilder();
                sb.append("Error reading cached value for key: '");
                sb.append(str);
                sb.append("' -- ");
                sb.append(e);
                Logger.log(loggingBehavior, 5, str2, sb.toString());
                return null;
            }
        }
        return bundle;
    }

    public void save(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        Editor edit = this.c.edit();
        for (String str : bundle.keySet()) {
            try {
                a(str, bundle, edit);
            } catch (JSONException e) {
                LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                String str2 = a;
                StringBuilder sb = new StringBuilder();
                sb.append("Error processing value for key: '");
                sb.append(str);
                sb.append("' -- ");
                sb.append(e);
                Logger.log(loggingBehavior, 5, str2, sb.toString());
                return;
            }
        }
        edit.apply();
    }

    public void clear() {
        this.c.edit().clear().apply();
    }

    private void a(String str, Bundle bundle, Editor editor) {
        Object obj;
        String str2;
        Object obj2 = bundle.get(str);
        if (obj2 != null) {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = null;
            if (obj2 instanceof Byte) {
                obj = "byte";
                jSONObject.put(TarjetasConstants.VALUE, ((Byte) obj2).intValue());
            } else if (obj2 instanceof Short) {
                obj = "short";
                jSONObject.put(TarjetasConstants.VALUE, ((Short) obj2).intValue());
            } else if (obj2 instanceof Integer) {
                obj = "int";
                jSONObject.put(TarjetasConstants.VALUE, ((Integer) obj2).intValue());
            } else if (obj2 instanceof Long) {
                obj = "long";
                jSONObject.put(TarjetasConstants.VALUE, ((Long) obj2).longValue());
            } else if (obj2 instanceof Float) {
                obj = "float";
                jSONObject.put(TarjetasConstants.VALUE, ((Float) obj2).doubleValue());
            } else if (obj2 instanceof Double) {
                obj = "double";
                jSONObject.put(TarjetasConstants.VALUE, ((Double) obj2).doubleValue());
            } else if (obj2 instanceof Boolean) {
                obj = "bool";
                jSONObject.put(TarjetasConstants.VALUE, ((Boolean) obj2).booleanValue());
            } else if (obj2 instanceof Character) {
                obj = "char";
                jSONObject.put(TarjetasConstants.VALUE, obj2.toString());
            } else if (obj2 instanceof String) {
                obj = "string";
                jSONObject.put(TarjetasConstants.VALUE, (String) obj2);
            } else if (obj2 instanceof Enum) {
                obj = "enum";
                jSONObject.put(TarjetasConstants.VALUE, obj2.toString());
                jSONObject.put("enumType", obj2.getClass().getName());
            } else {
                JSONArray jSONArray2 = new JSONArray();
                int i = 0;
                if (obj2 instanceof byte[]) {
                    str2 = "byte[]";
                    byte[] bArr = (byte[]) obj2;
                    int length = bArr.length;
                    while (i < length) {
                        jSONArray2.put(bArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof short[]) {
                    str2 = "short[]";
                    short[] sArr = (short[]) obj2;
                    int length2 = sArr.length;
                    while (i < length2) {
                        jSONArray2.put(sArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof int[]) {
                    str2 = "int[]";
                    int[] iArr = (int[]) obj2;
                    int length3 = iArr.length;
                    while (i < length3) {
                        jSONArray2.put(iArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof long[]) {
                    str2 = "long[]";
                    long[] jArr = (long[]) obj2;
                    int length4 = jArr.length;
                    while (i < length4) {
                        jSONArray2.put(jArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof float[]) {
                    str2 = "float[]";
                    float[] fArr = (float[]) obj2;
                    int length5 = fArr.length;
                    while (i < length5) {
                        jSONArray2.put((double) fArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof double[]) {
                    str2 = "double[]";
                    double[] dArr = (double[]) obj2;
                    int length6 = dArr.length;
                    while (i < length6) {
                        jSONArray2.put(dArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof boolean[]) {
                    str2 = "bool[]";
                    boolean[] zArr = (boolean[]) obj2;
                    int length7 = zArr.length;
                    while (i < length7) {
                        jSONArray2.put(zArr[i]);
                        i++;
                    }
                } else if (obj2 instanceof char[]) {
                    str2 = "char[]";
                    char[] cArr = (char[]) obj2;
                    int length8 = cArr.length;
                    while (i < length8) {
                        jSONArray2.put(String.valueOf(cArr[i]));
                        i++;
                    }
                } else if (obj2 instanceof List) {
                    str2 = "stringList";
                    for (Object obj3 : (List) obj2) {
                        if (obj3 == null) {
                            obj3 = JSONObject.NULL;
                        }
                        jSONArray2.put(obj3);
                    }
                } else {
                    obj = null;
                }
                Object obj4 = str2;
                jSONArray = jSONArray2;
                obj = obj4;
            }
            if (obj != null) {
                jSONObject.put("valueType", obj);
                if (jSONArray != null) {
                    jSONObject.putOpt(TarjetasConstants.VALUE, jSONArray);
                }
                editor.putString(str, jSONObject.toString());
            }
        }
    }

    private void a(String str, Bundle bundle) {
        JSONObject jSONObject = new JSONObject(this.c.getString(str, "{}"));
        String string = jSONObject.getString("valueType");
        if (string.equals("bool")) {
            bundle.putBoolean(str, jSONObject.getBoolean(TarjetasConstants.VALUE));
            return;
        }
        int i = 0;
        if (string.equals("bool[]")) {
            JSONArray jSONArray = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            boolean[] zArr = new boolean[jSONArray.length()];
            while (i < zArr.length) {
                zArr[i] = jSONArray.getBoolean(i);
                i++;
            }
            bundle.putBooleanArray(str, zArr);
        } else if (string.equals("byte")) {
            bundle.putByte(str, (byte) jSONObject.getInt(TarjetasConstants.VALUE));
        } else if (string.equals("byte[]")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            byte[] bArr = new byte[jSONArray2.length()];
            while (i < bArr.length) {
                bArr[i] = (byte) jSONArray2.getInt(i);
                i++;
            }
            bundle.putByteArray(str, bArr);
        } else if (string.equals("short")) {
            bundle.putShort(str, (short) jSONObject.getInt(TarjetasConstants.VALUE));
        } else if (string.equals("short[]")) {
            JSONArray jSONArray3 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            short[] sArr = new short[jSONArray3.length()];
            while (i < sArr.length) {
                sArr[i] = (short) jSONArray3.getInt(i);
                i++;
            }
            bundle.putShortArray(str, sArr);
        } else if (string.equals("int")) {
            bundle.putInt(str, jSONObject.getInt(TarjetasConstants.VALUE));
        } else if (string.equals("int[]")) {
            JSONArray jSONArray4 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            int[] iArr = new int[jSONArray4.length()];
            while (i < iArr.length) {
                iArr[i] = jSONArray4.getInt(i);
                i++;
            }
            bundle.putIntArray(str, iArr);
        } else if (string.equals("long")) {
            bundle.putLong(str, jSONObject.getLong(TarjetasConstants.VALUE));
        } else if (string.equals("long[]")) {
            JSONArray jSONArray5 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            long[] jArr = new long[jSONArray5.length()];
            while (i < jArr.length) {
                jArr[i] = jSONArray5.getLong(i);
                i++;
            }
            bundle.putLongArray(str, jArr);
        } else if (string.equals("float")) {
            bundle.putFloat(str, (float) jSONObject.getDouble(TarjetasConstants.VALUE));
        } else if (string.equals("float[]")) {
            JSONArray jSONArray6 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            float[] fArr = new float[jSONArray6.length()];
            while (i < fArr.length) {
                fArr[i] = (float) jSONArray6.getDouble(i);
                i++;
            }
            bundle.putFloatArray(str, fArr);
        } else if (string.equals("double")) {
            bundle.putDouble(str, jSONObject.getDouble(TarjetasConstants.VALUE));
        } else if (string.equals("double[]")) {
            JSONArray jSONArray7 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            double[] dArr = new double[jSONArray7.length()];
            while (i < dArr.length) {
                dArr[i] = jSONArray7.getDouble(i);
                i++;
            }
            bundle.putDoubleArray(str, dArr);
        } else if (string.equals("char")) {
            String string2 = jSONObject.getString(TarjetasConstants.VALUE);
            if (string2 != null && string2.length() == 1) {
                bundle.putChar(str, string2.charAt(0));
            }
        } else if (string.equals("char[]")) {
            JSONArray jSONArray8 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            char[] cArr = new char[jSONArray8.length()];
            for (int i2 = 0; i2 < cArr.length; i2++) {
                String string3 = jSONArray8.getString(i2);
                if (string3 != null && string3.length() == 1) {
                    cArr[i2] = string3.charAt(0);
                }
            }
            bundle.putCharArray(str, cArr);
        } else if (string.equals("string")) {
            bundle.putString(str, jSONObject.getString(TarjetasConstants.VALUE));
        } else if (string.equals("stringList")) {
            JSONArray jSONArray9 = jSONObject.getJSONArray(TarjetasConstants.VALUE);
            int length = jSONArray9.length();
            ArrayList arrayList = new ArrayList(length);
            while (i < length) {
                Object obj = jSONArray9.get(i);
                arrayList.add(i, obj == JSONObject.NULL ? null : (String) obj);
                i++;
            }
            bundle.putStringArrayList(str, arrayList);
        } else if (string.equals("enum")) {
            try {
                bundle.putSerializable(str, Enum.valueOf(Class.forName(jSONObject.getString("enumType")), jSONObject.getString(TarjetasConstants.VALUE)));
            } catch (ClassNotFoundException | IllegalArgumentException unused) {
            }
        }
    }
}
