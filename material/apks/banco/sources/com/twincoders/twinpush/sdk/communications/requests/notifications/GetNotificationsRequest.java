package com.twincoders.twinpush.sdk.communications.requests.notifications;

import com.twincoders.twinpush.sdk.communications.DefaultRequestParam;
import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetNotificationsRequest extends TwinPushRequest {
    Listener c;

    public interface Listener extends ErrorListener {
        void onSuccess(List<PushNotification> list, int i);
    }

    public GetNotificationsRequest(String str, String str2, int i, int i2, List<String> list, List<String> list2, boolean z, Listener listener) {
        super(str, str2);
        this.c = listener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("search_notifications");
        addParam("page", String.valueOf(i + 1));
        addParam("per_page", String.valueOf(i2));
        if (z) {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add("tp_rich");
        }
        addParam(DefaultRequestParam.arrayParam("tags", list));
        addParam(DefaultRequestParam.arrayParam("no_tags", list2));
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        try {
            int i = jSONObject.has("total_pages") ? jSONObject.getInt("total_pages") : 0;
            JSONArray jSONArray = jSONObject.getJSONArray("objects");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                arrayList.add(parseNotification(jSONArray.getJSONObject(i2)));
            }
            getListener().onSuccess(arrayList, i);
        } catch (JSONException e) {
            Ln.e(e, "Error while trying to parse notifications from response", new Object[0]);
            getListener().onError(e);
        }
    }

    public Listener getListener() {
        return this.c;
    }
}
