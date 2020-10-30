package com.twincoders.twinpush.sdk.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import com.google.android.gms.gcm.GcmListenerService;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationIntentService extends GcmListenerService {
    public static final String EXTRA_NOTIFICATION = "notification";
    public static final String EXTRA_NOTIFICATION_CUSTOM = "custom";
    public static final String EXTRA_NOTIFICATION_ID = "tp_id";
    public static final String EXTRA_NOTIFICATION_MESSAGE = "message";
    public static final String EXTRA_NOTIFICATION_RICH_URL = "tp_rich_url";
    public static final String EXTRA_NOTIFICATION_TITLE = "title";
    public static final String ON_NOTIFICATION_OPENED_ACTION = "con.twincoders.twinpush.sdk.PUSH_NOTIFICATION_OPENED";

    public void onMessageReceived(String str, Bundle bundle) {
        Ln.i("Received message", new Object[0]);
        displayNotification(getBaseContext(), getNotification(bundle));
    }

    public void displayNotification(Context context, PushNotification pushNotification) {
        PendingIntent contentIntent = getContentIntent(context, pushNotification);
        String title = pushNotification.getTitle();
        if (title == null || title.trim().length() == 0) {
            title = context.getString(context.getApplicationInfo().labelRes);
        }
        ((NotificationManager) context.getSystemService(EXTRA_NOTIFICATION)).notify(pushNotification.getId().hashCode(), new Builder(context).setContentTitle(title).setContentText(pushNotification.getMessage()).setTicker(pushNotification.getMessage()).setSmallIcon(TwinPushSDK.getInstance(context).getNotificationSmallIcon()).setDefaults(7).setContentIntent(contentIntent).setAutoCancel(true).setStyle(new BigTextStyle().bigText(pushNotification.getMessage())).build());
    }

    /* access modifiers changed from: protected */
    public PendingIntent getContentIntent(Context context, PushNotification pushNotification) {
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntentForPackage.setAction(ON_NOTIFICATION_OPENED_ACTION);
        launchIntentForPackage.setFlags(603979776);
        launchIntentForPackage.putExtra(EXTRA_NOTIFICATION, pushNotification);
        return PendingIntent.getActivity(context, pushNotification.getId().hashCode(), launchIntentForPackage, 268435456);
    }

    /* access modifiers changed from: protected */
    public PushNotification getNotification(Bundle bundle) {
        String string = bundle.getString(EXTRA_NOTIFICATION_ID);
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("message");
        String string4 = bundle.getString(EXTRA_NOTIFICATION_RICH_URL);
        Date date = new Date();
        Map b = b(bundle);
        PushNotification pushNotification = new PushNotification();
        pushNotification.setId(string);
        pushNotification.setTitle(string2);
        pushNotification.setMessage(string3);
        pushNotification.setDate(date);
        pushNotification.setRichURL(string4);
        pushNotification.setCustomProperties(b);
        return pushNotification;
    }

    private Map<String, String> b(Bundle bundle) {
        HashMap hashMap = new HashMap();
        try {
            String string = bundle.getString("custom");
            if (string != null) {
                JSONObject jSONObject = new JSONObject(string);
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    try {
                        hashMap.put(str, (String) jSONObject.get(str));
                    } catch (JSONException e) {
                        Ln.e(e, "Could not find property %1$s on Custom properties JSON", new Object[0]);
                    }
                }
            }
        } catch (Exception e2) {
            Ln.e(e2, "Error while trying to parse JSON object", new Object[0]);
        }
        return hashMap;
    }
}
