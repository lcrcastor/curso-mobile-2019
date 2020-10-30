package com.twincoders.twinpush.sdk.entities;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.twincoders.twinpush.sdk.BuildConfig;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.logging.Ln;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class RegistrationInfo {
    public String appID = null;
    public String appVersion = null;
    public String deviceAlias = null;
    public String deviceCode = null;
    public String deviceManufacturer = null;
    public String deviceModel = null;
    public String language = null;
    public String osVersion = null;
    public Integer osVersionInt = null;
    public String pushToken = null;
    public String sdkVersion = null;
    public String udid = null;

    public static RegistrationInfo fromContext(Context context, String str, String str2, String str3) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        try {
            registrationInfo.appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            Ln.e(e, "Could not obtain application version", new Object[0]);
        }
        registrationInfo.sdkVersion = BuildConfig.VERSION_NAME;
        registrationInfo.udid = str;
        registrationInfo.deviceAlias = str2;
        registrationInfo.pushToken = str3;
        registrationInfo.osVersion = VERSION.RELEASE;
        registrationInfo.osVersionInt = Integer.valueOf(VERSION.SDK_INT);
        registrationInfo.deviceManufacturer = a(Build.MANUFACTURER);
        registrationInfo.deviceModel = a(Build.MODEL);
        registrationInfo.deviceCode = a(Build.DEVICE);
        registrationInfo.language = Locale.getDefault().toString();
        registrationInfo.appID = TwinPushSDK.getInstance(context).getAppId();
        registrationInfo.printLog();
        return registrationInfo;
    }

    private static String a(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char charAt = str.charAt(0);
        if (Character.isUpperCase(charAt) || str.contains(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(charAt));
        sb.append(str.substring(1));
        return sb.toString();
    }

    public void printLog() {
        Ln.d("============================================", new Object[0]);
        Ln.d("===      TwinPush Registration info      ===", new Object[0]);
        Ln.d("============================================", new Object[0]);
        Ln.d("TwinPush App ID: %s", this.appID);
        Ln.d("App Version:     %s", this.appVersion);
        Ln.d("SDK Version:     %s", this.sdkVersion);
        Ln.d("Android Version: %s (API %d)", this.osVersion, this.osVersionInt);
        Ln.d("Device:          %s %s (%s)", this.deviceManufacturer, this.deviceModel, this.deviceCode);
        Ln.d("UDID:            %s", this.udid);
        Ln.d("Language:        %s", this.language);
        Ln.d("Alias:           %s", this.deviceAlias);
        Ln.d("Push Token:      %s", this.pushToken);
        Ln.d("============================================", new Object[0]);
    }

    public Map<String, Object> toMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("app_id", this.appID);
        linkedHashMap.put("app_version", this.appVersion);
        linkedHashMap.put("sdk_version", this.sdkVersion);
        linkedHashMap.put("os_version", this.osVersion);
        linkedHashMap.put("os_version_api", this.osVersionInt);
        linkedHashMap.put("device_manufacturer", this.deviceManufacturer);
        linkedHashMap.put("device_model", this.deviceModel);
        linkedHashMap.put("device_code", this.deviceCode);
        linkedHashMap.put("udid", this.deviceCode);
        linkedHashMap.put("language", this.language);
        linkedHashMap.put("device_alias", this.deviceAlias);
        linkedHashMap.put("push_token", this.pushToken);
        return linkedHashMap;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : toMap().entrySet()) {
            sb.append((String) entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(";");
        }
        return sb.toString();
    }
}
