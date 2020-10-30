package com.twincoders.twinpush.sdk.forms;

import android.content.Context;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.Map;

public abstract class TwinFormsSDK {
    private static TwinFormsSDK a;

    public interface ReportListener {
        void onReportError(Exception exc);

        void onReportSuccess();
    }

    public abstract void report(PushNotification pushNotification, Map<String, Object> map);

    public abstract void report(PushNotification pushNotification, Map<String, Object> map, ReportListener reportListener);

    public abstract void setup(String str, String str2);

    public static TwinFormsSDK getInstance(Context context) {
        if (a == null) {
            a = new DefaultTwinFormsSDK(context);
        }
        return a;
    }
}
