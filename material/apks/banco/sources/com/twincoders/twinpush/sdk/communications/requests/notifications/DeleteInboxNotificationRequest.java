package com.twincoders.twinpush.sdk.communications.requests.notifications;

import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import org.json.JSONObject;

public class DeleteInboxNotificationRequest extends TwinPushRequest {
    DefaultListener c;

    public DeleteInboxNotificationRequest(String str, String str2, String str3, DefaultListener defaultListener) {
        super(str, str2, str3);
        this.c = defaultListener;
        this.httpMethod = HttpMethod.DELETE;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        if (getListener() != null) {
            getListener().onSuccess();
        }
    }

    public DefaultListener getListener() {
        return this.c;
    }
}
