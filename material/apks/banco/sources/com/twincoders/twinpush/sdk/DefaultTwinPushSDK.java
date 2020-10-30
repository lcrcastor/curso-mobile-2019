package com.twincoders.twinpush.sdk;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.twincoders.twinpush.sdk.TwinPushSDK.OnRegistrationListener;
import com.twincoders.twinpush.sdk.communications.TwinPushRequestFactory;
import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetInboxRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationDetailsRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationsRequest;
import com.twincoders.twinpush.sdk.communications.requests.register.RegisterRequest.Listener;
import com.twincoders.twinpush.sdk.entities.InboxNotification;
import com.twincoders.twinpush.sdk.entities.LocationPrecision;
import com.twincoders.twinpush.sdk.entities.PropertyType;
import com.twincoders.twinpush.sdk.entities.RegistrationInfo;
import com.twincoders.twinpush.sdk.entities.TwinPushOptions;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.logging.Strings;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import com.twincoders.twinpush.sdk.services.LocationChangeReceiver;
import com.twincoders.twinpush.sdk.services.RegistrationIntentService;
import com.twincoders.twinpush.sdk.util.LastLocationFinder;
import com.twincoders.twinpush.sdk.util.StringEncrypter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultTwinPushSDK extends TwinPushSDK implements LocationListener {
    private Context a = null;
    /* access modifiers changed from: private */
    public TwinPushRequest b = null;
    private BroadcastReceiver c;
    private List<Activity> d = new ArrayList();
    private LastLocationFinder e = null;
    private String f = null;
    private String g = null;
    private int h = 0;
    private String i = null;
    private String j = null;
    private String k = null;
    private String l = null;

    class RegisterBroadcastReceiver extends BroadcastReceiver {
        private String b;
        private OnRegistrationListener c;

        private RegisterBroadcastReceiver(String str, OnRegistrationListener onRegistrationListener) {
            this.b = str;
            this.c = onRegistrationListener;
        }

        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra(RegistrationIntentService.EXTRA_PUSH_TOKEN);
            String stringExtra2 = intent.getStringExtra(RegistrationIntentService.EXTRA_REGISTRATION_ERROR);
            if (Strings.isEmpty(stringExtra2)) {
                DefaultTwinPushSDK.this.a(this.b, stringExtra, this.c);
            } else {
                DefaultTwinPushSDK.this.a(new Exception(stringExtra2), this.c);
            }
            DefaultTwinPushSDK.this.c();
        }
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i2, Bundle bundle) {
    }

    protected DefaultTwinPushSDK(Context context) {
        this.a = context.getApplicationContext();
        this.e = new LastLocationFinder(context.getApplicationContext());
        this.e.setChangedLocationListener(this);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str, final String str2, final OnRegistrationListener onRegistrationListener) {
        Ln.d("GCM registration completed", new Object[0]);
        RegistrationInfo fromContext = RegistrationInfo.fromContext(getContext(), getDeviceUDID(), str, str2);
        String g2 = g(fromContext.toString());
        if (!Strings.equals(g2, j())) {
            i(g2);
            Ln.d("Registration changed! Launching new registration request", new Object[0]);
            if (this.b != null) {
                this.b.cancel();
            }
            this.b = h().register(fromContext, new Listener() {
                public void onError(Exception exc) {
                    DefaultTwinPushSDK.this.a(exc, onRegistrationListener);
                }

                public void onRegistrationSuccess(String str, String str2) {
                    Ln.i("Device successfully registered on TwinPush (deviceId:%s, alias:%s)", str, str2);
                    DefaultTwinPushSDK.this.b = null;
                    DefaultTwinPushSDK.this.d(str);
                    DefaultTwinPushSDK.this.c(str2);
                    DefaultTwinPushSDK.this.f(str2);
                    if (onRegistrationListener != null) {
                        onRegistrationListener.onRegistrationSuccess(str2);
                    }
                }
            });
            return;
        }
        Ln.d("Registration info did not change since last registration", new Object[0]);
        if (onRegistrationListener != null) {
            onRegistrationListener.onRegistrationSuccess(str);
        }
    }

    public void register() {
        register(null);
    }

    public void register(String str) {
        register(str, null);
    }

    public void register(String str, OnRegistrationListener onRegistrationListener) {
        String gcmProjectNumber = getGcmProjectNumber();
        String appId = getAppId();
        String apiKey = getApiKey();
        if (gcmProjectNumber == null) {
            a(new Exception("GCM Sender ID is not setup in TwinPush SDK"), onRegistrationListener);
        } else if (appId == null) {
            a(new Exception("Application ID is not setup in TwinPush SDK"), onRegistrationListener);
        } else if (apiKey == null) {
            a(new Exception("Token is not setup in TwinPush SDK"), onRegistrationListener);
        } else if (d()) {
            Ln.i("Starting RegistrationIntentService", new Object[0]);
            this.c = new RegisterBroadcastReceiver(str, onRegistrationListener);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.c, new IntentFilter(RegistrationIntentService.REGISTRATION_COMPLETE));
            getContext().startService(new Intent(getContext(), RegistrationIntentService.class));
        } else {
            a(new Exception("No valid Google Play Services APK found"), onRegistrationListener);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Exception exc, OnRegistrationListener onRegistrationListener) {
        Ln.e(exc);
        if (onRegistrationListener != null) {
            onRegistrationListener.onRegistrationError(exc);
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.c != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.c);
            this.c = null;
        }
    }

    private boolean d() {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (!GooglePlayServicesUtil.isUserRecoverableError(isGooglePlayServicesAvailable) || !(getContext() instanceof Activity)) {
            Ln.e("checkPlayServices failed: This device is not supported.", new Object[0]);
        } else {
            GooglePlayServicesUtil.getErrorDialog(isGooglePlayServicesAvailable, (Activity) getContext(), 9000).show();
        }
        return false;
    }

    public void getNotifications(int i2, int i3, List<String> list, List<String> list2, boolean z, GetNotificationsRequest.Listener listener) {
        h().getNotificationInbox(i2, i3, list, list2, z, listener);
    }

    public void getNotifications(int i2, int i3, GetNotificationsRequest.Listener listener) {
        getNotifications(i2, i3, null, null, true, listener);
    }

    public void getUserInbox(int i2, int i3, GetInboxRequest.Listener listener) {
        h().getUserInbox(i2, i3, listener);
    }

    public void getNotification(String str, GetNotificationDetailsRequest.Listener listener) {
        h().getNotification(str, listener);
    }

    public void deleteNotification(InboxNotification inboxNotification, DefaultListener defaultListener) {
        h().deleteNotification(inboxNotification, defaultListener);
    }

    public void setProperty(String str, String str2) {
        a(str, (Object) str2, PropertyType.STRING);
    }

    public void setProperty(String str, Boolean bool) {
        a(str, (Object) bool, PropertyType.BOOLEAN);
    }

    public void setProperty(String str, Integer num) {
        a(str, (Object) num, PropertyType.INTEGER);
    }

    public void setProperty(String str, Float f2) {
        a(str, (Object) f2, PropertyType.FLOAT);
    }

    public void setProperty(String str, Double d2) {
        a(str, (Object) d2, PropertyType.FLOAT);
    }

    private void a(String str, Object obj, PropertyType propertyType) {
        if (b()) {
            String str2 = "Set property '%s' = '%s'";
            Object[] objArr = new Object[2];
            objArr[0] = str;
            objArr[1] = obj == null ? "null" : obj.toString();
            h().setCustomProperty(str, propertyType, obj, a(String.format(str2, objArr)));
            return;
        }
        Ln.w("Not launching 'Set custom property': Device unregistered", new Object[0]);
    }

    public void clearProperties() {
        if (b()) {
            h().clearCustomProperties(a("Clear properties"));
            return;
        }
        Ln.w("Not launching 'Clear custom properties': Device unregistered", new Object[0]);
    }

    public void setLocation(double d2, double d3) {
        Location location = new Location("USER_ENTRY");
        location.setLatitude(d2);
        location.setLongitude(d3);
        location.setTime(new Date().getTime());
        setLocation(location);
    }

    public void setLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        a(location);
        DefaultListener a2 = a("Update location");
        if (b()) {
            h().reportStatistics(latitude, longitude, a2);
        } else {
            Ln.w("Not launching 'Location update': Device unregistered", new Object[0]);
        }
    }

    public void updateLocation() {
        updateLocation(LocationPrecision.MEDIUM);
    }

    public void updateLocation(LocationPrecision locationPrecision) {
        Location lastBestLocation = this.e.getLastBestLocation(locationPrecision.getMinUpdateDistance(), locationPrecision.getMinUpdateTime());
        if (lastBestLocation != null) {
            setLocation(lastBestLocation.getLatitude(), lastBestLocation.getLongitude());
        }
    }

    /* access modifiers changed from: 0000 */
    public PendingIntent a() {
        return PendingIntent.getBroadcast(getContext(), 0, new Intent(getContext(), LocationChangeReceiver.class), 134217728);
    }

    public void startMonitoringLocationChanges() {
        startMonitoringLocationChanges(LocationPrecision.MEDIUM);
    }

    public void startMonitoringLocationChanges(LocationPrecision locationPrecision) {
        Ln.i("Registering for location updates", new Object[0]);
        g().edit().putLong("LOCATION_MIN_UPDATE_TIME", locationPrecision.getMinUpdateTime()).putInt("LOCATION_MIN_UPDATE_DISTANCE", locationPrecision.getMinUpdateDistance()).commit();
        a(true);
        registerForLocationUpdates();
    }

    public void stopMonitoringLocationChanges() {
        a(false);
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            ((LocationManager) getContext().getSystemService("location")).removeUpdates(a());
        }
    }

    public void registerForLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(getContext(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            ((LocationManager) getContext().getSystemService("location")).requestLocationUpdates("passive", getLocationMinUpdateTime(), (float) getLocationMinUpdateDistance(), a());
        } else {
            Ln.e("Could not start location updates, required permissions not found", new Object[0]);
        }
    }

    private void a(boolean z) {
        g().edit().putBoolean("MONITOR_LOCATION_CHANGES", z).commit();
    }

    public boolean isMonitoringLocationChanges() {
        return g().getBoolean("MONITOR_LOCATION_CHANGES", false);
    }

    public void activityStart(Activity activity) {
        if (this.d.isEmpty()) {
            e();
        }
        if (!this.d.contains(activity)) {
            this.d.add(activity);
        }
    }

    public void activityStop(Activity activity) {
        if (this.d.contains(activity)) {
            this.d.remove(activity);
        }
        if (this.d.isEmpty()) {
            f();
        }
    }

    private void e() {
        if (b()) {
            h().openApp(a("On Application Open"));
            return;
        }
        Ln.w("Not launching 'On application open event': Device unregistered", new Object[0]);
    }

    private void f() {
        if (b()) {
            h().closeApp(a("On Application Close"));
            return;
        }
        Ln.w("Not launching 'On application close event': Device unregistered", new Object[0]);
    }

    public void onNotificationOpen(PushNotification pushNotification) {
        if (pushNotification != null) {
            h().openNotification(pushNotification, a(String.format("On Notification Open: %s", new Object[]{pushNotification.getId()})));
        }
    }

    private SharedPreferences g() {
        return b("TwinPushPrefs");
    }

    private SharedPreferences b(String str) {
        return getContext().getSharedPreferences(str, 0);
    }

    public Context getContext() {
        return this.a;
    }

    public int getNotificationSmallIcon() {
        if (this.h == 0) {
            this.h = g().getInt("NOTIFICATION_SMALL_ICON", 0);
        }
        return this.h;
    }

    private void a(int i2) {
        g().edit().putInt("NOTIFICATION_SMALL_ICON", i2).commit();
        this.h = i2;
    }

    public String getDeviceAlias() {
        if (this.f == null) {
            this.f = h(g().getString("DEVICE_ALIAS", null));
        }
        return this.f;
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        g().edit().putString("DEVICE_ALIAS", g(str)).commit();
        this.f = str;
    }

    public String getDeviceId() {
        if (this.g == null) {
            this.g = g().getString("DEVICE_ID", null);
        }
        return this.g;
    }

    /* access modifiers changed from: private */
    public void d(String str) {
        g().edit().putString("DEVICE_ID", str).commit();
        this.g = str;
    }

    public String getGcmProjectNumber() {
        if (this.i == null) {
            this.i = g().getString("GCM_SENDER_ID", null);
        }
        return this.i;
    }

    private void e(String str) {
        g().edit().putString("GCM_SENDER_ID", str).commit();
        this.i = str;
    }

    public String getApiKey() {
        if (this.j == null) {
            this.j = g().getString("TWINPUSH_TOKEN", null);
        }
        return this.j;
    }

    /* access modifiers changed from: protected */
    public void setApiKey(String str) {
        g().edit().putString("TWINPUSH_TOKEN", str).commit();
        this.j = str;
    }

    public String getAppId() {
        if (this.k == null) {
            this.k = g().getString("TWINPUSH_APP_ID", null);
        }
        return this.k;
    }

    /* access modifiers changed from: protected */
    public void setAppId(String str) {
        g().edit().putString("TWINPUSH_APP_ID", str).commit();
        this.k = str;
    }

    public String getPushToken() {
        if (this.l == null) {
            this.l = g().getString("DEVICE_PUSH_TOKEN", null);
        }
        return this.l;
    }

    /* access modifiers changed from: private */
    public void f(String str) {
        g().edit().putString("DEVICE_PUSH_TOKEN", str).commit();
        this.l = str;
    }

    public long getLocationMinUpdateTime() {
        return g().getLong("LOCATION_MIN_UPDATE_TIME", 0);
    }

    public int getLocationMinUpdateDistance() {
        return g().getInt("LOCATION_MIN_UPDATE_DISTANCE", 0);
    }

    /* access modifiers changed from: protected */
    public String getDeviceUDID() {
        return Secure.getString(getContext().getContentResolver(), "android_id");
    }

    public boolean setup(TwinPushOptions twinPushOptions) {
        if (twinPushOptions != null) {
            String str = twinPushOptions.twinPushAppId;
            String str2 = twinPushOptions.twinPushApiKey;
            String str3 = twinPushOptions.gcmProjectNumber;
            String str4 = twinPushOptions.subdomain;
            String str5 = twinPushOptions.serverHost;
            boolean z = Strings.notEmpty(str) && Strings.notEmpty(str2) && Strings.notEmpty(str3) && twinPushOptions.notificationIcon > 0;
            boolean z2 = Strings.notEmpty(str4) || Strings.notEmpty(str5);
            if (!z) {
                Ln.e("TwinPush Setup Error: some of the required fields are missing", new Object[0]);
            } else if (z2) {
                setAppId(twinPushOptions.twinPushAppId);
                setApiKey(twinPushOptions.twinPushApiKey);
                e(twinPushOptions.gcmProjectNumber);
                a(twinPushOptions.notificationIcon);
                if (twinPushOptions.serverHost != null) {
                    setServerHost(twinPushOptions.serverHost);
                } else {
                    setSubdomain(twinPushOptions.subdomain);
                }
                i();
                return true;
            } else {
                Ln.e("TwinPush Setup Error: subdomain or serverHost are required", new Object[0]);
            }
        } else {
            Ln.e("TwinPush Setup Error: options object is null", new Object[0]);
        }
        return false;
    }

    private TwinPushRequestFactory h() {
        return TwinPushRequestFactory.getSharedinstance(getContext());
    }

    /* access modifiers changed from: 0000 */
    public DefaultListener a(final String str) {
        return new DefaultListener() {
            public void onError(Exception exc) {
                Ln.e(exc, String.format("Error while trying to send %s request", new Object[]{str}), new Object[0]);
            }

            public void onSuccess() {
                Ln.i("Successfuly sent %s request", str);
            }
        };
    }

    private void a(Location location) {
        if (location != null) {
            g().edit().putFloat("LOCATION_LATITUDE", (float) location.getLatitude()).putFloat("LOCATION_LONGITUDE", (float) location.getLongitude()).putFloat("LOCATION_ALTITUDE", (float) location.getAltitude()).putFloat("LOCATION_ACCURACY", location.getAccuracy()).putLong("LOCATION_TIME", location.getTime()).putString("LOCATION_PROVIDER", location.getProvider()).commit();
        }
    }

    public Location getLastKnownLocation() {
        SharedPreferences g2 = g();
        long j2 = g2.getLong("LOCATION_TIME", 0);
        if (j2 <= 0) {
            return null;
        }
        float f2 = g2.getFloat("LOCATION_LATITUDE", BitmapDescriptorFactory.HUE_RED);
        float f3 = g2.getFloat("LOCATION_LONGITUDE", BitmapDescriptorFactory.HUE_RED);
        float f4 = g2.getFloat("LOCATION_ALTITUDE", BitmapDescriptorFactory.HUE_RED);
        float f5 = g2.getFloat("LOCATION_ACCURACY", BitmapDescriptorFactory.HUE_RED);
        Location location = new Location(g2.getString("LOCATION_PROVIDER", ""));
        location.setAccuracy(f5);
        location.setLatitude((double) f2);
        location.setLongitude((double) f3);
        location.setAltitude((double) f4);
        location.setTime(j2);
        return location;
    }

    public void onLocationChanged(Location location) {
        setLocation(location.getLatitude(), location.getLongitude());
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return getDeviceId() != null;
    }

    private void i() {
        setSSLPublicKeyCheck(null);
        b("PREF_SSL_ISSUER").edit().clear().commit();
        b("PREF_SSL_SUBJECT").edit().clear().commit();
    }

    public void setSSLPublicKeyCheck(String str) {
        g().edit().putString("PREF_SSL_PUBLIC_KEY", str).commit();
    }

    public String getSSLPublicKeyCheck() {
        return g().getString("PREF_SSL_PUBLIC_KEY", null);
    }

    public void addSSLIssuerCheck(String str, String str2) {
        b("PREF_SSL_ISSUER").edit().putString(str, str2).commit();
    }

    public void addSSLSubjectCheck(String str, String str2) {
        b("PREF_SSL_SUBJECT").edit().putString(str, str2).commit();
    }

    public Map<String, String> getSSLIssuerChecks() {
        HashMap hashMap = new HashMap();
        for (Entry entry : b("PREF_SSL_ISSUER").getAll().entrySet()) {
            hashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return hashMap;
    }

    public Map<String, String> getSSLSubjectChecks() {
        HashMap hashMap = new HashMap();
        for (Entry entry : b("PREF_SSL_SUBJECT").getAll().entrySet()) {
            hashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return hashMap;
    }

    private String g(String str) {
        if (str != null) {
            try {
                return new StringEncrypter(getDeviceUDID()).encryptString(str);
            } catch (Exception e2) {
                Ln.e(e2, "Error trying to encrypt string", new Object[0]);
            }
        }
        return null;
    }

    private String h(String str) {
        if (str != null) {
            try {
                return new StringEncrypter(getDeviceUDID()).decryptString(str);
            } catch (Exception e2) {
                Ln.e(e2, "Error trying to decrypt string", new Object[0]);
            }
        }
        return null;
    }

    public void setSubdomain(String str) {
        g().edit().putString("TWINPUSH_SUBDOMAIN", str).commit();
    }

    public String getSubdomain() {
        return g().getString("TWINPUSH_SUBDOMAIN", "app");
    }

    public void setServerHost(String str) {
        g().edit().putString("TWINPUSH_CUSTOM_HOST", str).commit();
    }

    public String getServerHost() {
        return g().getString("TWINPUSH_CUSTOM_HOST", String.format("https://%s.twinpush.com", new Object[]{getSubdomain()}));
    }

    private void i(String str) {
        g().edit().putString("REGISTRATION_HASH", str).commit();
    }

    private String j() {
        return g().getString("REGISTRATION_HASH", null);
    }
}
