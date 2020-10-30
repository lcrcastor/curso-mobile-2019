package com.twincoders.twinpush.sdk;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetInboxRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationDetailsRequest.Listener;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationsRequest;
import com.twincoders.twinpush.sdk.entities.InboxNotification;
import com.twincoders.twinpush.sdk.entities.LocationPrecision;
import com.twincoders.twinpush.sdk.entities.TwinPushOptions;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.List;
import java.util.Map;

public abstract class TwinPushSDK {
    private static TwinPushSDK a;

    public interface OnRegistrationListener {
        void onRegistrationError(Exception exc);

        void onRegistrationSuccess(String str);
    }

    public abstract void activityStart(Activity activity);

    public abstract void activityStop(Activity activity);

    public abstract void addSSLIssuerCheck(String str, String str2);

    public abstract void addSSLSubjectCheck(String str, String str2);

    public abstract void clearProperties();

    public abstract void deleteNotification(InboxNotification inboxNotification, DefaultListener defaultListener);

    public abstract String getApiKey();

    public abstract String getAppId();

    public abstract String getDeviceAlias();

    public abstract String getDeviceId();

    public abstract String getGcmProjectNumber();

    public abstract Location getLastKnownLocation();

    public abstract int getLocationMinUpdateDistance();

    public abstract long getLocationMinUpdateTime();

    public abstract void getNotification(String str, Listener listener);

    public abstract int getNotificationSmallIcon();

    public abstract void getNotifications(int i, int i2, GetNotificationsRequest.Listener listener);

    public abstract void getNotifications(int i, int i2, List<String> list, List<String> list2, boolean z, GetNotificationsRequest.Listener listener);

    public abstract Map<String, String> getSSLIssuerChecks();

    public abstract String getSSLPublicKeyCheck();

    public abstract Map<String, String> getSSLSubjectChecks();

    public abstract String getServerHost();

    public abstract String getSubdomain();

    public abstract void getUserInbox(int i, int i2, GetInboxRequest.Listener listener);

    public abstract boolean isMonitoringLocationChanges();

    public abstract void onNotificationOpen(PushNotification pushNotification);

    public abstract void register();

    public abstract void register(String str);

    public abstract void register(String str, OnRegistrationListener onRegistrationListener);

    public abstract void setLocation(double d, double d2);

    public abstract void setLocation(Location location);

    public abstract void setProperty(String str, Boolean bool);

    public abstract void setProperty(String str, Double d);

    public abstract void setProperty(String str, Float f);

    public abstract void setProperty(String str, Integer num);

    public abstract void setProperty(String str, String str2);

    public abstract void setSSLPublicKeyCheck(String str);

    public abstract boolean setup(TwinPushOptions twinPushOptions);

    public abstract void startMonitoringLocationChanges();

    public abstract void startMonitoringLocationChanges(LocationPrecision locationPrecision);

    public abstract void stopMonitoringLocationChanges();

    public abstract void updateLocation();

    public abstract void updateLocation(LocationPrecision locationPrecision);

    public static TwinPushSDK getInstance(Context context) {
        if (a == null) {
            a = new DefaultTwinPushSDK(context);
        }
        return a;
    }
}
