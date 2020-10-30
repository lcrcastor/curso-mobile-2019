package com.twincoders.twinpush.sdk.forms;

import android.content.Context;
import android.content.SharedPreferences;
import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.communications.TwinPushRequestFactory;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.communications.requests.forms.ReportFormRequest.Listener;
import com.twincoders.twinpush.sdk.forms.TwinFormsSDK.ReportListener;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.Map;

public class DefaultTwinFormsSDK extends TwinFormsSDK {
    TwinPushSDK a = null;
    private Context b = null;
    /* access modifiers changed from: private */
    public TwinPushRequest c = null;
    private String d = null;
    private String e = null;

    protected DefaultTwinFormsSDK(Context context) {
        this.b = context.getApplicationContext();
        this.a = TwinPushSDK.getInstance(this.b);
    }

    public void report(PushNotification pushNotification, Map<String, Object> map) {
        report(pushNotification, map, null);
    }

    public void report(PushNotification pushNotification, Map<String, Object> map, final ReportListener reportListener) {
        if (this.c != null) {
            this.c.cancel();
        }
        this.c = b().reportForm(this.a.getDeviceId(), this.a.getDeviceAlias(), getAppToken(), getReporterToken(), pushNotification, map, new Listener() {
            public void onError(Exception exc) {
                if (reportListener != null) {
                    reportListener.onReportError(exc);
                }
                DefaultTwinFormsSDK.this.c = null;
            }

            public void onSuccess() {
                if (reportListener != null) {
                    reportListener.onReportSuccess();
                }
                DefaultTwinFormsSDK.this.c = null;
            }
        });
    }

    public void setup(String str, String str2) {
        setAppToken(str);
        setReporterToken(str2);
    }

    private SharedPreferences a() {
        return a("TwinFormsPrefs");
    }

    private SharedPreferences a(String str) {
        return getContext().getSharedPreferences(str, 0);
    }

    public Context getContext() {
        return this.b;
    }

    public void setAppToken(String str) {
        a().edit().putString("APP_TOKEN", str).commit();
        this.d = str;
    }

    public String getAppToken() {
        if (this.d == null) {
            this.d = a().getString("APP_TOKEN", null);
        }
        return this.d;
    }

    public void setReporterToken(String str) {
        a().edit().putString("REPORTER_TOKEN", str).commit();
        this.e = str;
    }

    public String getReporterToken() {
        if (this.e == null) {
            this.e = a().getString("REPORTER_TOKEN", null);
        }
        return this.e;
    }

    private TwinPushRequestFactory b() {
        return TwinPushRequestFactory.getSharedinstance(getContext());
    }
}
