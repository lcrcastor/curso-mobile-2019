package com.twincoders.twinpush.sdk.communications.requests.statistics;

import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import org.json.JSONObject;

public class OpenNotificationRequest extends TwinPushRequest {
    DefaultListener c;

    public OpenNotificationRequest(String str, String str2, PushNotification pushNotification, DefaultListener defaultListener) {
        super(str, str2, pushNotification.getId());
        this.c = defaultListener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("open_notification");
    }

    public ErrorListener getListener() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        this.c.onSuccess();
    }
}
