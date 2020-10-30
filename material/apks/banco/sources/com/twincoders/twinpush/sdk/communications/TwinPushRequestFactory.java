package com.twincoders.twinpush.sdk.communications;

import android.content.Context;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.OnRequestFinishListener;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.communications.requests.forms.ReportFormRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.DeleteInboxNotificationRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetInboxRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationDetailsRequest;
import com.twincoders.twinpush.sdk.communications.requests.notifications.GetNotificationsRequest;
import com.twincoders.twinpush.sdk.communications.requests.properties.ClearCustomPropertiesRequest;
import com.twincoders.twinpush.sdk.communications.requests.properties.SetCustomPropertyRequest;
import com.twincoders.twinpush.sdk.communications.requests.register.RegisterRequest;
import com.twincoders.twinpush.sdk.communications.requests.register.RegisterRequest.Listener;
import com.twincoders.twinpush.sdk.communications.requests.statistics.CloseAppRequest;
import com.twincoders.twinpush.sdk.communications.requests.statistics.OpenAppRequest;
import com.twincoders.twinpush.sdk.communications.requests.statistics.OpenNotificationRequest;
import com.twincoders.twinpush.sdk.communications.requests.statistics.ReportStatisticsRequest;
import com.twincoders.twinpush.sdk.entities.InboxNotification;
import com.twincoders.twinpush.sdk.entities.PropertyType;
import com.twincoders.twinpush.sdk.entities.RegistrationInfo;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TwinPushRequestFactory {
    private static TwinPushRequestFactory d;
    TwinRequestLauncher a;
    String b = null;
    TwinPushSDK c;
    private List<TwinPushRequest> e = new ArrayList();
    /* access modifiers changed from: private */
    public boolean f = false;

    private String a() {
        return this.c.getDeviceId();
    }

    private String b() {
        return this.c.getAppId();
    }

    public static TwinPushRequestFactory getSharedinstance(Context context) {
        if (d == null) {
            d = new TwinPushRequestFactory(context);
        }
        return d;
    }

    private TwinPushRequestFactory(Context context) {
        this.a = new DefaultRequestLauncher(context);
        this.c = TwinPushSDK.getInstance(context);
    }

    public TwinPushRequest register(RegistrationInfo registrationInfo, Listener listener) {
        RegisterRequest registerRequest = new RegisterRequest(b(), registrationInfo, listener);
        a((TwinPushRequest) registerRequest);
        return registerRequest;
    }

    public TwinPushRequest getNotificationInbox(int i, int i2, List<String> list, List<String> list2, boolean z, GetNotificationsRequest.Listener listener) {
        GetNotificationsRequest getNotificationsRequest = new GetNotificationsRequest(b(), a(), i, i2, list, list2, z, listener);
        a((TwinPushRequest) getNotificationsRequest);
        return getNotificationsRequest;
    }

    public TwinPushRequest getUserInbox(int i, int i2, GetInboxRequest.Listener listener) {
        GetInboxRequest getInboxRequest = new GetInboxRequest(b(), a(), i, i2, listener);
        a((TwinPushRequest) getInboxRequest);
        return getInboxRequest;
    }

    public TwinPushRequest getNotification(String str, GetNotificationDetailsRequest.Listener listener) {
        GetNotificationDetailsRequest getNotificationDetailsRequest = new GetNotificationDetailsRequest(b(), a(), str, listener);
        a((TwinPushRequest) getNotificationDetailsRequest);
        return getNotificationDetailsRequest;
    }

    public TwinPushRequest deleteNotification(InboxNotification inboxNotification, DefaultListener defaultListener) {
        DeleteInboxNotificationRequest deleteInboxNotificationRequest = new DeleteInboxNotificationRequest(b(), a(), inboxNotification.getNotification().getId(), defaultListener);
        a((TwinPushRequest) deleteInboxNotificationRequest);
        return deleteInboxNotificationRequest;
    }

    public TwinPushRequest setCustomProperty(String str, PropertyType propertyType, Object obj, DefaultListener defaultListener) {
        SetCustomPropertyRequest setCustomPropertyRequest = new SetCustomPropertyRequest(b(), a(), str, propertyType, obj, defaultListener);
        a((TwinPushRequest) setCustomPropertyRequest);
        return setCustomPropertyRequest;
    }

    public TwinPushRequest clearCustomProperties(DefaultListener defaultListener) {
        ClearCustomPropertiesRequest clearCustomPropertiesRequest = new ClearCustomPropertiesRequest(b(), a(), defaultListener);
        a((TwinPushRequest) clearCustomPropertiesRequest);
        return clearCustomPropertiesRequest;
    }

    public TwinPushRequest openApp(DefaultListener defaultListener) {
        OpenAppRequest openAppRequest = new OpenAppRequest(b(), a(), defaultListener);
        a((TwinPushRequest) openAppRequest);
        return openAppRequest;
    }

    public TwinPushRequest closeApp(DefaultListener defaultListener) {
        CloseAppRequest closeAppRequest = new CloseAppRequest(b(), a(), defaultListener);
        a((TwinPushRequest) closeAppRequest);
        return closeAppRequest;
    }

    public TwinPushRequest reportStatistics(double d2, double d3, DefaultListener defaultListener) {
        ReportStatisticsRequest reportStatisticsRequest = new ReportStatisticsRequest(b(), a(), d2, d3, defaultListener);
        a((TwinPushRequest) reportStatisticsRequest);
        return reportStatisticsRequest;
    }

    public TwinPushRequest openNotification(PushNotification pushNotification, DefaultListener defaultListener) {
        OpenNotificationRequest openNotificationRequest = new OpenNotificationRequest(b(), a(), pushNotification, defaultListener);
        a((TwinPushRequest) openNotificationRequest);
        return openNotificationRequest;
    }

    public TwinPushRequest reportForm(String str, String str2, String str3, String str4, PushNotification pushNotification, Map<String, Object> map, ReportFormRequest.Listener listener) {
        ReportFormRequest reportFormRequest = new ReportFormRequest(str, str2, str3, str4, pushNotification, map, listener);
        a((TwinPushRequest) reportFormRequest);
        return reportFormRequest;
    }

    private void a(TwinPushRequest twinPushRequest) {
        if (this.f) {
            this.e.add(twinPushRequest);
            return;
        }
        if (twinPushRequest.isSequential()) {
            this.f = true;
            twinPushRequest.addOnRequestFinishListener(new OnRequestFinishListener() {
                public void onRequestFinish() {
                    TwinPushRequestFactory.this.f = false;
                    TwinPushRequestFactory.this.c();
                }
            });
        } else {
            c();
        }
        twinPushRequest.setRequestLauncher(this.a);
        twinPushRequest.launch();
    }

    /* access modifiers changed from: private */
    public void c() {
        if (!this.e.isEmpty()) {
            TwinPushRequest twinPushRequest = (TwinPushRequest) this.e.get(0);
            this.e.remove(twinPushRequest);
            a(twinPushRequest);
        }
    }
}
