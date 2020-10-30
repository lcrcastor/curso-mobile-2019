package ar.com.santander.rio.mbanking.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import ar.com.santander.rio.mbanking.managers.security.SoftTokenManager;
import ar.com.santander.rio.mbanking.managers.session.SessionManager;
import ar.com.santander.rio.mbanking.services.soap.constants.ConstantsWS;

public class UtilDeviceInfo {
    public static String ANDROID_PREF = "A";
    public static String ANDROID_PREF_DEFAULT = "A000000000000000";
    public static String ANDROID_PREF_TEST = "A123456789012345";
    static final /* synthetic */ boolean a = true;

    public static String getMarca() {
        String str = Build.MANUFACTURER;
        if (str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(str.charAt(0)));
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static String getModelo() {
        return Build.MODEL;
    }

    public static String getVersion(Context context) {
        String str = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getLatitude() {
        return SessionManager.getLocation() != null ? String.valueOf(SessionManager.getLocation().latitude) : "0.00";
    }

    public static String getLongitude() {
        return SessionManager.getLocation() != null ? String.valueOf(SessionManager.getLocation().longitude) : "0.00";
    }

    public static String getIp(Context context) {
        try {
            String formatIpAddress = Formatter.formatIpAddress(((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getIpAddress());
            return !TextUtils.isEmpty(formatIpAddress) ? formatIpAddress : ConstantsWS.DEFAULT_IP;
        } catch (Exception e) {
            e.fillInStackTrace();
            return ConstantsWS.DEFAULT_IP;
        }
    }

    public static String getRSAData(Activity activity) {
        String rSAModuleData = SessionManager.getRSAModuleData(activity);
        return !TextUtils.isEmpty(rSAModuleData) ? rSAModuleData : "";
    }

    public static String getNewToken(Activity activity) {
        return new SoftTokenManager(activity.getBaseContext()).getNewToken();
    }

    public static boolean isPhoneDevice(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (a || telephonyManager != null) {
            return telephonyManager.getPhoneType() != 0;
        }
        throw new AssertionError();
    }

    public static boolean isPackageInstalled(String str, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(str, 0);
            return true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getVersionSO() {
        return VERSION.RELEASE;
    }
}
