package ar.com.santander.rio.mbanking.managers.notifications;

import android.app.Activity;
import android.content.Intent;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.base.BaseApplication;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.TwinPushSDK.OnRegistrationListener;
import com.twincoders.twinpush.sdk.entities.TwinPushOptions;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;

public class PushNotificationsManager implements IPushNotificationsManager {
    public static String IMAGE_TYPE = "URL_IMG";
    public static String PROMO_TYPE = "ID_PROMO";
    public static String SEGMENT_TYPE = "ID_SEGMENT";
    BaseApplication a;

    public PushNotificationsManager(BaseApplication baseApplication) {
        this.a = baseApplication;
    }

    public void setUp(Activity activity) {
        TwinPushSDK instance = TwinPushSDK.getInstance(activity);
        TwinPushOptions twinPushOptions = new TwinPushOptions();
        twinPushOptions.twinPushAppId = this.a.getString(R.string.TWINPUSH_APP_ID);
        twinPushOptions.twinPushApiKey = this.a.getString(R.string.TWINPUSH_TOKEN);
        twinPushOptions.gcmProjectNumber = this.a.getString(R.string.TWINPUSH_GCM_PROJECT_NUMBER);
        twinPushOptions.subdomain = this.a.getString(R.string.TWINPUSH_SUBDOMAIN);
        twinPushOptions.notificationIcon = R.drawable.notif_circle;
        instance.setup(twinPushOptions);
        instance.startMonitoringLocationChanges();
    }

    public void register(Activity activity, String str) {
        TwinPushSDK.getInstance(activity).register(str, new OnRegistrationListener() {
            public void onRegistrationError(Exception exc) {
            }

            public void onRegistrationSuccess(String str) {
            }
        });
    }

    public void register(Activity activity) {
        TwinPushSDK.getInstance(activity).register();
    }

    public PushNotification checkPushNotification(Intent intent) {
        if (intent == null || intent.getAction() == null || !intent.getAction().equals(NotificationIntentService.ON_NOTIFICATION_OPENED_ACTION)) {
            return null;
        }
        PushNotification pushNotification = (PushNotification) intent.getSerializableExtra(NotificationIntentService.EXTRA_NOTIFICATION);
        TwinPushSDK.getInstance(this.a.getApplicationContext()).onNotificationOpen(pushNotification);
        return pushNotification;
    }
}
