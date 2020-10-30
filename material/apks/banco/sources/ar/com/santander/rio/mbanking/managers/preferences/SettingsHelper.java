package ar.com.santander.rio.mbanking.managers.preferences;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;

public class SettingsHelper {
    private static final String a = "SettingsHelper";
    private SecuredPreferences b;
    private Gson c = new Gson();

    public SettingsHelper(Context context) {
        this.b = SecuredPreferences.getInstance(context);
    }

    public Boolean exists(String str) {
        return this.b.contains(str);
    }

    public void remove(String str) {
        this.b.remove(str);
    }

    public void putBoolean(String str, Boolean bool) {
        putBoolean(str, bool, Boolean.valueOf(true));
    }

    public void putBoolean(String str, Boolean bool, Boolean bool2) {
        this.b.putBoolean(str, bool, bool2);
    }

    public Boolean getBoolean(String str, Boolean bool) {
        return this.b.getBoolean(str, bool);
    }

    public void putString(String str, String str2) {
        putString(str, str2, Boolean.valueOf(true));
    }

    public void putString(String str, String str2, Boolean bool) {
        this.b.putString(str, str2, bool);
    }

    public String getString(String str, String str2) {
        return this.b.getString(str, str2);
    }

    public void putObject(String str, Object obj) {
        putObject(str, obj, Boolean.valueOf(true));
    }

    public void putObject(String str, Object obj, Boolean bool) {
        putString(str, this.c.toJson(obj), bool);
    }

    public <T> T getObject(String str, Class<T> cls) {
        String string = getString(str, "");
        if (!TextUtils.isEmpty(string)) {
            return this.c.fromJson(string, cls);
        }
        return null;
    }
}
