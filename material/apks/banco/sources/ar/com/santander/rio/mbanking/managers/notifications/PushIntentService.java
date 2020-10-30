package ar.com.santander.rio.mbanking.managers.notifications;

import android.app.Notification.BigPictureStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.activities.SplashScreenActivity;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import me.leolin.shortcutbadger.ShortcutBadger;

public class PushIntentService extends NotificationIntentService {
    private int a = 0;

    /* access modifiers changed from: protected */
    public void displayNotification(Context context, PushNotification pushNotification) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(Constants.PUSH_PREFERENCES, 0);
        Editor edit = sharedPreferences.edit();
        this.a = sharedPreferences.getInt(Constants.ID_NOTIFICACION_INDEX, 0);
        if (this.a < 0) {
            this.a = 0;
        }
        this.a++;
        edit.putInt(Constants.ID_NOTIFICACION_INDEX, this.a);
        edit.apply();
        ShortcutBadger.applyCount(context, this.a);
        try {
            if (NotificationUtils.appIsNotRunning(this)) {
                a(context, pushNotification);
            }
        } catch (Exception unused) {
            a(context, pushNotification);
        }
    }

    private void a(Context context, PushNotification pushNotification) {
        if (pushNotification.getCustomProperties().containsKey(PushNotificationsManager.IMAGE_TYPE)) {
            a(context, pushNotification, (String) pushNotification.getCustomProperties().get(PushNotificationsManager.IMAGE_TYPE));
        } else {
            b(context, pushNotification);
        }
    }

    private void b(Context context, PushNotification pushNotification) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationIntentService.EXTRA_NOTIFICATION);
        NotificationUtils.createNotificacionChannelIfNecessary(notificationManager);
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.setAction(NotificationIntentService.ON_NOTIFICATION_OPENED_ACTION);
        intent.putExtra(NotificationIntentService.EXTRA_NOTIFICATION, pushNotification);
        TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addParentStack(SplashScreenActivity.class);
        create.addNextIntent(intent);
        PendingIntent pendingIntent = create.getPendingIntent((int) (Math.random() * 100.0d), 134217728);
        Builder createNotificationBuilder = NotificationUtils.createNotificationBuilder(context);
        createNotificationBuilder.setContentTitle(pushNotification.getTitle()).setAutoCancel(true).setDefaults(2).setContentText(pushNotification.getMessage());
        if (VERSION.SDK_INT >= 21) {
            createNotificationBuilder.setSmallIcon(R.drawable.notif_circle);
        } else {
            createNotificationBuilder.setSmallIcon(R.drawable.notif_square);
        }
        createNotificationBuilder.setContentIntent(pendingIntent);
        if (notificationManager != null) {
            notificationManager.notify(this.a, createNotificationBuilder.build());
        }
    }

    private void a(Context context, PushNotification pushNotification, String str) {
        Bitmap bitmap;
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.setAction(NotificationIntentService.ON_NOTIFICATION_OPENED_ACTION);
        intent.putExtra(NotificationIntentService.EXTRA_NOTIFICATION, pushNotification);
        TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addParentStack(SplashScreenActivity.class);
        create.addNextIntent(intent);
        PendingIntent pendingIntent = create.getPendingIntent((int) (Math.random() * 100.0d), 134217728);
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(str).getContent());
        } catch (IOException unused) {
            bitmap = null;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationIntentService.EXTRA_NOTIFICATION);
        NotificationUtils.createNotificacionChannelIfNecessary(notificationManager);
        Builder createNotificationBuilder = NotificationUtils.createNotificationBuilder(context);
        createNotificationBuilder.setAutoCancel(true);
        createNotificationBuilder.setPriority(2);
        if (VERSION.SDK_INT >= 21) {
            createNotificationBuilder.setSmallIcon(R.drawable.notif_circle);
        } else {
            createNotificationBuilder.setSmallIcon(R.drawable.notif_square);
        }
        createNotificationBuilder.setContentTitle(pushNotification.getTitle());
        createNotificationBuilder.setContentText(pushNotification.getMessage());
        createNotificationBuilder.setContentIntent(pendingIntent);
        createNotificationBuilder.setStyle(new BigPictureStyle().bigPicture(bitmap));
        notificationManager.notify(this.a, createNotificationBuilder.build());
    }
}
