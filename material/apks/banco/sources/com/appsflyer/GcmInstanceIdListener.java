package com.appsflyer;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

public class GcmInstanceIdListener extends InstanceIDListenerService {
    public void onTokenRefresh() {
        String str;
        super.onTokenRefresh();
        String string = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        long currentTimeMillis = System.currentTimeMillis();
        try {
            str = InstanceID.getInstance(getApplicationContext()).getToken(string, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (Throwable th) {
            AFLogger.afErrorLog("Error registering for uninstall tracking", th);
            str = null;
        }
        if (str != null) {
            AFLogger.afInfoLog("GCM Refreshed Token = ".concat(String.valueOf(str)));
            d a = d.a(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
            d dVar = new d(currentTimeMillis, str);
            if (a.a(dVar)) {
                u.a(getApplicationContext(), dVar);
            }
        }
    }
}
