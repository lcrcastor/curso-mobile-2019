package com.appsflyer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppsFlyerProperties {
    public static final String ADDITIONAL_CUSTOM_DATA = "additionalCustomData";
    public static final String AF_KEY = "AppsFlyerKey";
    public static final String AF_WAITFOR_CUSTOMERID = "waitForCustomerId";
    public static final String APP_ID = "appid";
    public static final String APP_USER_ID = "AppUserId";
    public static final String CHANNEL = "channel";
    public static final String COLLECT_ANDROID_ID = "collectAndroidId";
    public static final String COLLECT_FACEBOOK_ATTR_ID = "collectFacebookAttrId";
    public static final String COLLECT_FINGER_PRINT = "collectFingerPrint";
    public static final String COLLECT_IMEI = "collectIMEI";
    public static final String COLLECT_MAC = "collectMAC";
    public static final String CURRENCY_CODE = "currencyCode";
    public static final String DEVICE_TRACKING_DISABLED = "deviceTrackingDisabled";
    public static final String DISABLE_LOGS_COMPLETELY = "disableLogs";
    public static final String DISABLE_OTHER_SDK = "disableOtherSdk";
    public static final String EMAIL_CRYPT_TYPE = "userEmailsCryptType";
    public static final String ENABLE_GPS_FALLBACK = "enableGpsFallback";
    public static final String EXTENSION = "sdkExtension";
    public static final String IS_MONITOR = "shouldMonitor";
    public static final String IS_UPDATE = "IS_UPDATE";
    public static final String LAUNCH_PROTECT_ENABLED = "launchProtectEnabled";
    public static final String ONELINK_DOMAIN = "onelinkDomain";
    public static final String ONELINK_ID = "oneLinkSlug";
    public static final String ONELINK_SCHEME = "onelinkScheme";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_EMAILS = "userEmails";
    public static final String USE_HTTP_FALLBACK = "useHttpFallback";
    private static AppsFlyerProperties a = new AppsFlyerProperties();
    private Map<String, Object> b = new HashMap();
    private boolean c;
    private boolean d;
    private String e;
    private boolean f = false;

    public enum EmailsCryptType {
        NONE(0),
        SHA1(1),
        MD5(2),
        SHA256(3);
        
        private final int a;

        private EmailsCryptType(int i) {
            this.a = i;
        }

        public final int getValue() {
            return this.a;
        }
    }

    public void remove(String str) {
        this.b.remove(str);
    }

    private AppsFlyerProperties() {
    }

    public static AppsFlyerProperties getInstance() {
        return a;
    }

    public void set(String str, String str2) {
        this.b.put(str, str2);
    }

    public void set(String str, String[] strArr) {
        this.b.put(str, strArr);
    }

    public void set(String str, int i) {
        this.b.put(str, Integer.toString(i));
    }

    public void set(String str, long j) {
        this.b.put(str, Long.toString(j));
    }

    public void set(String str, boolean z) {
        this.b.put(str, Boolean.toString(z));
    }

    public void setCustomData(String str) {
        this.b.put(ADDITIONAL_CUSTOM_DATA, str);
    }

    public void setUserEmails(String str) {
        this.b.put(USER_EMAILS, str);
    }

    public String getString(String str) {
        return (String) this.b.get(str);
    }

    public boolean getBoolean(String str, boolean z) {
        String string = getString(str);
        if (string == null) {
            return z;
        }
        return Boolean.valueOf(string).booleanValue();
    }

    public int getInt(String str, int i) {
        String string = getString(str);
        if (string == null) {
            return i;
        }
        return Integer.valueOf(string).intValue();
    }

    public long getLong(String str, long j) {
        String string = getString(str);
        if (string == null) {
            return j;
        }
        return Long.valueOf(string).longValue();
    }

    public Object getObject(String str) {
        return this.b.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean isOnReceiveCalled() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void setOnReceiveCalled() {
        this.c = true;
    }

    /* access modifiers changed from: protected */
    public boolean isFirstLaunchCalled() {
        return this.d;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled(boolean z) {
        this.d = z;
    }

    /* access modifiers changed from: protected */
    public void setFirstLaunchCalled() {
        this.d = true;
    }

    /* access modifiers changed from: protected */
    public void setReferrer(String str) {
        set("AF_REFERRER", str);
        this.e = str;
    }

    public String getReferrer(Context context) {
        if (this.e != null) {
            return this.e;
        }
        if (getString("AF_REFERRER") != null) {
            return getString("AF_REFERRER");
        }
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences("appsflyer-data", 0).getString("referrer", null);
    }

    public boolean isEnableLog() {
        return getBoolean("shouldLog", true);
    }

    public boolean isLogsDisabledCompletely() {
        return getBoolean(DISABLE_LOGS_COMPLETELY, false);
    }

    public boolean isOtherSdkStringDisabled() {
        return getBoolean(DISABLE_OTHER_SDK, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void saveProperties(SharedPreferences sharedPreferences) {
        String jSONObject = new JSONObject(this.b).toString();
        Editor edit = sharedPreferences.edit();
        edit.putString("savedProperties", jSONObject);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    public void loadProperties(Context context) {
        if (!this.f) {
            String string = context.getSharedPreferences("appsflyer-data", 0).getString("savedProperties", null);
            if (string != null) {
                AFLogger.afDebugLog("Loading properties..");
                try {
                    JSONObject jSONObject = new JSONObject(string);
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String str = (String) keys.next();
                        if (this.b.get(str) == null) {
                            this.b.put(str, jSONObject.getString(str));
                        }
                    }
                    this.f = true;
                } catch (JSONException e2) {
                    AFLogger.afErrorLog("Failed loading properties", e2);
                }
                StringBuilder sb = new StringBuilder("Done loading properties: ");
                sb.append(this.f);
                AFLogger.afDebugLog(sb.toString());
            }
        }
    }
}
