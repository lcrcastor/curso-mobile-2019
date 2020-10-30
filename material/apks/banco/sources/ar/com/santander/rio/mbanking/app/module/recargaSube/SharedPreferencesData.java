package ar.com.santander.rio.mbanking.app.module.recargaSube;

import android.content.Context;
import android.content.SharedPreferences;
import com.securepreferences.SecurePreferences;

public class SharedPreferencesData implements ISharedPreferencesData {
    private Context a;
    private SharedPreferences b = new SecurePreferences(this.a);

    public SharedPreferencesData(Context context) {
        this.a = context;
    }

    public void setPreference(String str, String str2) {
        try {
            this.b.edit().putString(str, str2).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPreference(String str) {
        return this.b.getString(str, "");
    }
}
