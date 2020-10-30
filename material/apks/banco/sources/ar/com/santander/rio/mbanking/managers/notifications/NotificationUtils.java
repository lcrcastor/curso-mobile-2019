package ar.com.santander.rio.mbanking.managers.notifications;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;
import java.util.List;

public class NotificationUtils {
    public static void createNotificacionChannelIfNecessary(NotificationManager notificationManager) {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel("Santander Río", "Santander Río", 3);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    public static Builder createNotificationBuilder(Context context) {
        if (VERSION.SDK_INT >= 26) {
            return new Builder(context, "Santander Río");
        }
        return new Builder(context);
    }

    public static boolean appIsNotRunning(Context context) {
        List runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks == null || runningTasks.isEmpty()) {
            return true;
        }
        return checkIfPackageIsNotFromApp(context, ((RunningTaskInfo) runningTasks.get(0)).topActivity.getPackageName());
    }

    public static boolean checkIfPackageIsNotFromApp(Context context, String str) {
        return !context.getPackageName().equalsIgnoreCase(str);
    }
}
