package com.securepreferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Log;
import com.securepreferences.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SecurePreferences implements SharedPreferences {
    /* access modifiers changed from: private */
    public static SharedPreferences a = null;
    private static byte[] b = null;
    private static boolean c = false;
    private static HashMap<OnSharedPreferenceChangeListener, OnSharedPreferenceChangeListener> d = null;
    /* access modifiers changed from: private */
    public static final String e = "com.securepreferences.SecurePreferences";

    public static class Editor implements android.content.SharedPreferences.Editor {
        private android.content.SharedPreferences.Editor a;

        private Editor() {
            this.a = SecurePreferences.a.edit();
        }

        public android.content.SharedPreferences.Editor putString(String str, String str2) {
            this.a.putString(SecurePreferences.d(str), SecurePreferences.d(str2));
            return this;
        }

        public android.content.SharedPreferences.Editor putStringNoEncrypted(String str, String str2) {
            this.a.putString(SecurePreferences.d(str), str2);
            return this;
        }

        @TargetApi(11)
        public android.content.SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            HashSet hashSet = new HashSet(set.size());
            for (String a2 : set) {
                hashSet.add(SecurePreferences.d(a2));
            }
            this.a.putStringSet(SecurePreferences.d(str), hashSet);
            return this;
        }

        public android.content.SharedPreferences.Editor putInt(String str, int i) {
            this.a.putString(SecurePreferences.d(str), SecurePreferences.d(Integer.toString(i)));
            return this;
        }

        public android.content.SharedPreferences.Editor putLong(String str, long j) {
            this.a.putString(SecurePreferences.d(str), SecurePreferences.d(Long.toString(j)));
            return this;
        }

        public android.content.SharedPreferences.Editor putFloat(String str, float f) {
            this.a.putString(SecurePreferences.d(str), SecurePreferences.d(Float.toString(f)));
            return this;
        }

        public android.content.SharedPreferences.Editor putBoolean(String str, boolean z) {
            this.a.putString(SecurePreferences.d(str), SecurePreferences.d(Boolean.toString(z)));
            return this;
        }

        public android.content.SharedPreferences.Editor remove(String str) {
            this.a.remove(SecurePreferences.d(str));
            return this;
        }

        public android.content.SharedPreferences.Editor clear() {
            this.a.clear();
            return this;
        }

        public boolean commit() {
            return this.a.commit();
        }

        @TargetApi(9)
        public void apply() {
            if (VERSION.SDK_INT >= 9) {
                this.a.apply();
            } else {
                commit();
            }
        }
    }

    public SecurePreferences(Context context) {
        if (a == null) {
            a = PreferenceManager.getDefaultSharedPreferences(context);
        }
        try {
            String a2 = a(context);
            String string = a.getString(a2, null);
            if (string == null) {
                string = c();
                a.edit().putString(a2, string).commit();
            }
            b = c(string);
            d = new HashMap<>(10);
        } catch (Exception e2) {
            if (c) {
                String str = e;
                StringBuilder sb = new StringBuilder();
                sb.append("Error init:");
                sb.append(e2.getMessage());
                Log.e(str, sb.toString());
            }
            throw new IllegalStateException(e2);
        }
    }

    private static String a(byte[] bArr) {
        return Base64.encodeToString(bArr, 3);
    }

    private static byte[] c(String str) {
        return Base64.decode(str, 3);
    }

    private static String a(Context context) {
        SecretKey secretKey;
        char[] charArray = context.getPackageName().toCharArray();
        byte[] bytes = b(context).getBytes();
        try {
            secretKey = a(charArray, bytes, "PBKDF2WithHmacSHA1", 2000, 256);
        } catch (NoSuchAlgorithmException unused) {
            secretKey = a(charArray, bytes, "PBEWithMD5AndDES", 2000, 256);
        }
        return a(secretKey.getEncoded());
    }

    private static SecretKey a(char[] cArr, byte[] bArr, String str, int i, int i2) {
        if (i == 0) {
            i = 1000;
        }
        return SecretKeyFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME).generateSecret(new PBEKeySpec(cArr, bArr, i, i2));
    }

    private static String b(Context context) {
        try {
            String str = (String) Build.class.getField("SERIAL").get(null);
            if (TextUtils.isEmpty(str)) {
                str = Secure.getString(context.getContentResolver(), "android_id");
            }
            return str;
        } catch (Exception unused) {
            return Secure.getString(context.getContentResolver(), "android_id");
        }
    }

    private static String c() {
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        try {
            instance.init(256, secureRandom);
        } catch (Exception unused) {
            try {
                instance.init(192, secureRandom);
            } catch (Exception unused2) {
                instance.init(128, secureRandom);
            }
        }
        return a(instance.generateKey().getEncoded());
    }

    /* access modifiers changed from: private */
    public static String d(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            Cipher instance = Cipher.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            instance.init(1, new SecretKeySpec(b, "AES"));
            return a(instance.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e2) {
            if (c) {
                Log.w(e, "encrypt", e2);
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static String e(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        try {
            Cipher instance = Cipher.getInstance("AES", BouncyCastleProvider.PROVIDER_NAME);
            instance.init(2, new SecretKeySpec(b, "AES"));
            return new String(instance.doFinal(c(str)), "UTF-8");
        } catch (Exception e2) {
            if (c) {
                Log.w(e, "decrypt", e2);
            }
            return null;
        }
    }

    public Map<String, String> getAll() {
        Map all = a.getAll();
        HashMap hashMap = new HashMap(all.size());
        for (Entry entry : all.entrySet()) {
            try {
                hashMap.put(e((String) entry.getKey()), e(entry.getValue().toString()));
            } catch (Exception unused) {
            }
        }
        return hashMap;
    }

    public String getString(String str, String str2) {
        String string = a.getString(d(str), null);
        return string != null ? e(string) : str2;
    }

    public String getStringUnencrypted(String str, String str2) {
        String string = a.getString(d(str), null);
        return string != null ? string : str2;
    }

    @TargetApi(11)
    public Set<String> getStringSet(String str, Set<String> set) {
        Set<String> stringSet = a.getStringSet(d(str), null);
        if (stringSet == null) {
            return set;
        }
        HashSet hashSet = new HashSet(stringSet.size());
        for (String e2 : stringSet) {
            hashSet.add(e(e2));
        }
        return hashSet;
    }

    public int getInt(String str, int i) {
        String string = a.getString(d(str), null);
        if (string == null) {
            return i;
        }
        try {
            return Integer.parseInt(e(string));
        } catch (NumberFormatException e2) {
            throw new ClassCastException(e2.getMessage());
        }
    }

    public long getLong(String str, long j) {
        String string = a.getString(d(str), null);
        if (string == null) {
            return j;
        }
        try {
            return Long.parseLong(e(string));
        } catch (NumberFormatException e2) {
            throw new ClassCastException(e2.getMessage());
        }
    }

    public float getFloat(String str, float f) {
        String string = a.getString(d(str), null);
        if (string == null) {
            return f;
        }
        try {
            return Float.parseFloat(e(string));
        } catch (NumberFormatException e2) {
            throw new ClassCastException(e2.getMessage());
        }
    }

    public boolean getBoolean(String str, boolean z) {
        String string = a.getString(d(str), null);
        if (string == null) {
            return z;
        }
        try {
            return Boolean.parseBoolean(e(string));
        } catch (NumberFormatException e2) {
            throw new ClassCastException(e2.getMessage());
        }
    }

    public boolean contains(String str) {
        return a.contains(d(str));
    }

    public Editor edit() {
        return new Editor();
    }

    public static boolean isLoggingEnabled() {
        return c;
    }

    public static void setLoggingEnabled(boolean z) {
        c = z;
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        a.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void registerOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener onSharedPreferenceChangeListener, boolean z) {
        if (!z) {
            registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            return;
        }
        AnonymousClass1 r3 = new OnSharedPreferenceChangeListener() {
            private OnSharedPreferenceChangeListener c = onSharedPreferenceChangeListener;

            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                try {
                    String b2 = SecurePreferences.e(str);
                    if (b2 != null) {
                        this.c.onSharedPreferenceChanged(sharedPreferences, b2);
                    }
                } catch (Exception unused) {
                    String b3 = SecurePreferences.e;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to decrypt key: ");
                    sb.append(str);
                    Log.w(b3, sb.toString());
                }
            }
        };
        d.put(onSharedPreferenceChangeListener, r3);
        a.registerOnSharedPreferenceChangeListener(r3);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        if (d.containsKey(onSharedPreferenceChangeListener)) {
            a.unregisterOnSharedPreferenceChangeListener((OnSharedPreferenceChangeListener) d.remove(onSharedPreferenceChangeListener));
            return;
        }
        a.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
