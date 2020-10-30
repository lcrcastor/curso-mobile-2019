package com.rsa.mobilesdk.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RootedDeviceChecker {
    private static final String[] a = {"Superuser.apk", "myhappy.apk"};
    private static final String[] b = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/", "/system/bin/.ext/", "/system/usr/we-need-root/"};
    private static Set<String> c = new HashSet();

    static {
        c.add("com.noshufou.android.su");
        c.add("eu.chainfire.supersu");
        c.add("eu.chainfire.supersu.pro");
        c.add("com.koushikdutta.superuser");
        c.add("com.noshufou.android.su.elite");
        c.add("david.lahuta.superuser.free.pro");
        c.add("com.bitcubate.android.su.installer");
        c.add("com.bitcubate.superuser.pro");
        c.add("david.lahuta.superuser");
    }

    private RootedDeviceChecker() {
    }

    static int a(Context context) {
        int i = 0;
        for (Boolean booleanValue : new Boolean[]{Boolean.valueOf(b(context)), Boolean.valueOf(a()), Boolean.valueOf(b())}) {
            i = (i << 1) + (booleanValue.booleanValue() ? 1 : 0);
        }
        return i;
    }

    private static boolean a() {
        try {
            for (String str : Arrays.asList(a)) {
                StringBuilder sb = new StringBuilder();
                sb.append("/system/app/");
                sb.append(str);
                if (new File(sb.toString()).exists()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    private static boolean b(Context context) {
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(128);
        if (installedApplications != null) {
            for (ApplicationInfo applicationInfo : installedApplications) {
                if (c.contains(applicationInfo.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean b() {
        for (String str : Arrays.asList(b)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("su");
            if (new File(sb.toString()).exists()) {
                return true;
            }
        }
        return false;
    }
}
