package com.appsflyer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class MultipleInstallBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("referrer");
            if (stringExtra != null) {
                if ("AppsFlyer_Test".equals(stringExtra) && intent.getStringExtra("TestIntegrationMode") != null) {
                    AppsFlyerLib.getInstance().a(context, intent);
                    return;
                } else if (context.getSharedPreferences("appsflyer-data", 0).getString("referrer", null) != null) {
                    AppsFlyerLib.getInstance();
                    AppsFlyerLib.a(context, stringExtra);
                    return;
                }
            }
            AFLogger.afInfoLog("MultipleInstallBroadcastReceiver called");
            AppsFlyerLib.getInstance().a(context, intent);
            for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.vending.INSTALL_REFERRER"), 0)) {
                String action = intent.getAction();
                if (resolveInfo.activityInfo.packageName.equals(context.getPackageName()) && "com.android.vending.INSTALL_REFERRER".equals(action) && !getClass().getName().equals(resolveInfo.activityInfo.name)) {
                    StringBuilder sb = new StringBuilder("trigger onReceive: class: ");
                    sb.append(resolveInfo.activityInfo.name);
                    AFLogger.afInfoLog(sb.toString());
                    try {
                        ((BroadcastReceiver) Class.forName(resolveInfo.activityInfo.name).newInstance()).onReceive(context, intent);
                    } catch (Throwable th) {
                        StringBuilder sb2 = new StringBuilder("error in BroadcastReceiver ");
                        sb2.append(resolveInfo.activityInfo.name);
                        AFLogger.afErrorLog(sb2.toString(), th);
                    }
                }
            }
        }
    }
}
