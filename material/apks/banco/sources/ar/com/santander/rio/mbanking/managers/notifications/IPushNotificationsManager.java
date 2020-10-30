package ar.com.santander.rio.mbanking.managers.notifications;

import android.app.Activity;
import android.content.Intent;
import com.twincoders.twinpush.sdk.notifications.PushNotification;

public interface IPushNotificationsManager {
    PushNotification checkPushNotification(Intent intent);

    void register(Activity activity);

    void register(Activity activity, String str);

    void setUp(Activity activity);
}
