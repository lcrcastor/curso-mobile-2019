package ar.com.santander.rio.mbanking.managers.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

public class KeyConfiguration {
    private static KeyConfiguration a;
    private SharedPreferences b;

    private KeyConfiguration(Context context) {
        this.b = context.getSharedPreferences("ar.com.santander.rio.config.sec", 0);
    }

    public static KeyConfiguration getInstance(Context context) {
        if (a == null) {
            a = new KeyConfiguration(context);
        }
        return a;
    }

    public boolean hasKey() {
        return this.b.contains("KEY");
    }

    @SuppressLint({"ApplySharedPref"})
    public void generateKey() {
        this.b.edit().putString("KEY", UUID.randomUUID().toString()).commit();
    }

    public String getKey() {
        return this.b.getString("KEY", null);
    }
}
