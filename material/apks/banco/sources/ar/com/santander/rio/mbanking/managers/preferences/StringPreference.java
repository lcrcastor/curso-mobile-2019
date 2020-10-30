package ar.com.santander.rio.mbanking.managers.preferences;

import android.content.SharedPreferences;

public class StringPreference implements Preference<String> {
    private SharedPreferences a;
    private String b;
    private String c;

    public StringPreference(SharedPreferences sharedPreferences, String str) {
        this.a = sharedPreferences;
        this.b = str;
    }

    public StringPreference(SharedPreferences sharedPreferences, String str, String str2) {
        this.a = sharedPreferences;
        this.b = str;
        this.c = str2;
    }

    public String get() {
        return this.a.getString(this.b, this.c);
    }

    public void set(String str) {
        this.a.edit().putString(this.b, str).apply();
    }

    public String getDefaultValue() {
        return this.c;
    }

    public boolean isSet() {
        return this.a.contains(this.b);
    }
}
