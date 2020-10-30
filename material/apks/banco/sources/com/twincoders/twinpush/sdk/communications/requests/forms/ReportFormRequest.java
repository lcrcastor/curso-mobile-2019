package com.twincoders.twinpush.sdk.communications.requests.forms;

import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.Map;
import org.json.JSONObject;

public class ReportFormRequest extends TwinPushRequest {
    Listener c;

    public interface Listener extends ErrorListener {
        void onSuccess();
    }

    public String getBaseURL() {
        return "https://forms.twinpush.com";
    }

    /* access modifiers changed from: protected */
    public void onResponseProcess(JSONObject jSONObject) {
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
    }

    public ReportFormRequest(String str, String str2, String str3, String str4, PushNotification pushNotification, Map<String, Object> map, Listener listener) {
        super(str3);
        this.c = listener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("report");
        addParam("reporter_token", str4);
        if (pushNotification != null) {
            addParam("notification_id", pushNotification.getId());
            addParam("title", pushNotification.getTitle());
            addParam("message", pushNotification.getMessage());
        }
        addParam("device_id", str);
        addParam("alias", str2);
        addParam("form", map);
    }

    public void onResponseProcess(String str) {
        getListener().onSuccess();
    }

    public Listener getListener() {
        return this.c;
    }
}
