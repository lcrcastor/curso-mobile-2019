package com.twincoders.twinpush.sdk.communications.requests.notifications;

import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetNotificationDetailsRequest extends TwinPushRequest {
    Listener c;

    public interface Listener extends ErrorListener {
        void onSuccess(PushNotification pushNotification);
    }

    public GetNotificationDetailsRequest(String str, String str2, String str3, Listener listener) {
        super(str, str2, str3);
        this.c = listener;
        this.httpMethod = HttpMethod.GET;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("objects");
            if (jSONArray.length() > 0) {
                getListener().onSuccess(parseNotification(jSONArray.getJSONObject(0)));
                return;
            }
            getListener().onError(new Exception("Notification not found"));
        } catch (JSONException e) {
            Ln.e(e, "Error while trying to parse notifications from response", new Object[0]);
            getListener().onError(e);
        }
    }

    public Listener getListener() {
        return this.c;
    }
}
