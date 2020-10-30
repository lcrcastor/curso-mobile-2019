package ar.com.santander.rio.mbanking.managers.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.app.module.recargaSube.SharedPreferencesData;
import ar.com.santander.rio.mbanking.managers.crypto.CryptoManager;
import ar.com.santander.rio.mbanking.managers.preferences.PreferenceKeys.UserInfo;
import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;
import java.util.Map;

public class SecuredPreferences {
    private static SecuredPreferences d;
    private SharedPreferences a;
    private CryptoManager b;
    private Gson c = new Gson();

    private SecuredPreferences(Context context) {
        this.a = context.getSharedPreferences("ar.com.santander.rio.config.preferences", 0);
        this.b = new CryptoManager(context);
    }

    public static SecuredPreferences getInstance(Context context) {
        if (d == null) {
            d = new SecuredPreferences(context);
        }
        return d;
    }

    public boolean isEmpty() {
        return this.a.getAll() == null || this.a.getAll().isEmpty();
    }

    public Boolean contains(String str) {
        return Boolean.valueOf(this.a.contains(this.b.encrypt(str)));
    }

    public void remove(String str) {
        this.a.edit().remove(this.b.encrypt(str)).apply();
    }

    public void performMigration(Context context) {
        Map all = new SecurePreferences(context).getAll();
        for (String str : all.keySet()) {
            if (!(str == null || all.get(str) == null)) {
                a(str, all.get(str));
            }
        }
    }

    public void publicDateMigration(Context context) {
        SharedPreferencesData sharedPreferencesData = new SharedPreferencesData(context);
        String preference = sharedPreferencesData.getPreference(UserInfo.DOCUMENT);
        String preference2 = sharedPreferencesData.getPreference(UserInfo.DATE);
        String preference3 = sharedPreferencesData.getPreference(UserInfo.NUP);
        if (!TextUtils.isEmpty(preference) && !TextUtils.isEmpty(preference2) && !TextUtils.isEmpty(preference3)) {
            a(UserInfo.DOCUMENT, preference);
            a(UserInfo.DATE, preference2);
            a(UserInfo.NUP, preference3);
        }
    }

    private void a(String str, Object obj) {
        if (obj instanceof String) {
            putString(str, (String) obj);
        } else if (obj instanceof Boolean) {
            putBoolean(str, (Boolean) obj, Boolean.valueOf(true));
        } else if (obj instanceof Integer) {
            putInt(str, (Integer) obj, Boolean.valueOf(true));
        } else if (obj instanceof Long) {
            putLong(str, (Long) obj, Boolean.valueOf(true));
        } else {
            putObject(str, obj);
        }
    }

    public void putInt(String str, Integer num, Boolean bool) {
        a(str, num, bool);
    }

    private void a(String str, Integer num, Boolean bool) {
        String encrypt = this.b.encrypt(str);
        String encrypt2 = this.b.encrypt(num != null ? num.toString() : null);
        if (bool.booleanValue()) {
            this.a.edit().putString(encrypt, encrypt2).apply();
        } else {
            this.a.edit().putString(encrypt, encrypt2).apply();
        }
    }

    public Integer getInt(String str, Integer num) {
        String string = this.a.getString(this.b.encrypt(str), num.toString());
        if (string.equalsIgnoreCase(num.toString())) {
            return num;
        }
        String decrypt = this.b.decrypt(string);
        return decrypt != null ? Integer.valueOf(decrypt) : null;
    }

    public void putLong(String str, Long l, Boolean bool) {
        a(str, l, bool);
    }

    private void a(String str, Long l, Boolean bool) {
        String encrypt = this.b.encrypt(str);
        String encrypt2 = this.b.encrypt(l != null ? l.toString() : null);
        if (bool.booleanValue()) {
            this.a.edit().putString(encrypt, encrypt2).apply();
        } else {
            this.a.edit().putString(encrypt, encrypt2).apply();
        }
    }

    public Long getLong(String str, Long l) {
        String string = this.a.getString(this.b.encrypt(str), l.toString());
        if (string.equalsIgnoreCase(l.toString())) {
            return l;
        }
        String decrypt = this.b.decrypt(string);
        return decrypt != null ? Long.valueOf(decrypt) : null;
    }

    public void putBoolean(String str, Boolean bool, Boolean bool2) {
        a(str, bool, bool2);
    }

    private void a(String str, Boolean bool, Boolean bool2) {
        String encrypt = this.b.encrypt(str);
        String encrypt2 = this.b.encrypt(bool != null ? bool.toString() : null);
        if (bool2.booleanValue()) {
            this.a.edit().putString(encrypt, encrypt2).apply();
        } else {
            this.a.edit().putString(encrypt, encrypt2).apply();
        }
    }

    public Boolean getBoolean(String str, Boolean bool) {
        String string = this.a.getString(this.b.encrypt(str), bool.toString());
        if (string.equalsIgnoreCase(bool.toString())) {
            return bool;
        }
        String decrypt = this.b.decrypt(string);
        return decrypt != null ? Boolean.valueOf(decrypt) : null;
    }

    public void putString(String str, String str2) {
        putString(str, str2, Boolean.valueOf(true));
    }

    public void putString(String str, String str2, Boolean bool) {
        String encrypt = this.b.encrypt(str);
        String encrypt2 = this.b.encrypt(str2);
        if (bool.booleanValue()) {
            this.a.edit().putString(encrypt, encrypt2).commit();
        } else {
            this.a.edit().putString(encrypt, encrypt2).apply();
        }
    }

    public String getString(String str, String str2) {
        String string = this.a.getString(this.b.encrypt(str), str2);
        if (str2 == null || !str2.equalsIgnoreCase(string)) {
            return this.b.decrypt(string);
        }
        return str2;
    }

    public void putObject(String str, Object obj) {
        putString(str, this.c.toJson(obj), Boolean.valueOf(true));
    }

    public <T> T getObject(String str, Class<T> cls) {
        String string = getString(str, "");
        if (!TextUtils.isEmpty(string)) {
            return this.c.fromJson(string, cls);
        }
        return null;
    }
}
