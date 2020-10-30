package com.rsa.mobilesdk.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

public class ApplicationKey {
    public static final int DEVICE_BINDING_DATA_LENGTH = 16;

    public static synchronized String getApplicationKey(Context context) {
        String str;
        synchronized (ApplicationKey.class) {
            str = null;
            if (context != null) {
                str = a(context);
                if (TextUtils.isEmpty(str)) {
                    str = b(context);
                    a(context, str);
                }
            }
        }
        return str;
    }

    private static String a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("rsa_application_key_prefs", 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString("com.rsa.mobilesdk.app_key", null);
        }
        Log.e("ApplicationKey", "unexpected error in getStoredApplicationKey, can't get shared preferences");
        return "INVALID";
    }

    private static String b(Context context) {
        byte[] bArr = new byte[16];
        if (Utils.b(bArr)) {
            return Utils.a(bArr);
        }
        Log.e("ApplicationKey", "unexpected error in getStoredApplicationKey, can't generate key");
        return "INVALID";
    }

    private static void a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("rsa_application_key_prefs", 0);
        if (sharedPreferences == null) {
            Log.e("ApplicationKey", "unexpected error in storeApplicationKey, can't get shared preferences");
            return;
        }
        Editor edit = sharedPreferences.edit();
        edit.putString("com.rsa.mobilesdk.app_key", str);
        edit.commit();
    }
}
