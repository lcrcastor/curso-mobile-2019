package com.twincoders.twinpush.sdk.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.logging.Ln;

public class RegistrationIntentService extends IntentService {
    public static final String EXTRA_PUSH_TOKEN = "PUSH_TOKEN";
    public static final String EXTRA_REGISTRATION_ERROR = "REGISTRATION_ERROR";
    public static final String REGISTRATION_COMPLETE = "REGISTRATION_COMPLETE";

    public RegistrationIntentService() {
        super(RegistrationIntentService.class.getName());
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        String str;
        TwinPushSDK instance = TwinPushSDK.getInstance(getApplicationContext());
        String str2 = null;
        try {
            Ln.d("Obtaining GCM Push Token", new Object[0]);
            str = InstanceID.getInstance(this).getToken(instance.getGcmProjectNumber(), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Ln.d("GCM Registration Push Token: %s", str);
        } catch (Exception e) {
            Ln.e(e, "Failed to complete token refresh", new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to complete token refresh: ");
            sb.append(e.getLocalizedMessage());
            str2 = sb.toString();
            str = null;
        }
        Intent intent2 = new Intent(REGISTRATION_COMPLETE);
        if (str != null) {
            intent2.putExtra(EXTRA_PUSH_TOKEN, str);
        }
        if (str2 != null) {
            intent2.putExtra(EXTRA_REGISTRATION_ERROR, str2);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
    }
}
