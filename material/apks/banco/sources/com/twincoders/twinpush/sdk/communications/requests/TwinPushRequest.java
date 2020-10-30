package com.twincoders.twinpush.sdk.communications.requests;

import com.twincoders.twinpush.sdk.TwinPushSDK;
import com.twincoders.twinpush.sdk.communications.RESTJSONRequest;
import com.twincoders.twinpush.sdk.entities.InboxNotification;
import com.twincoders.twinpush.sdk.logging.Ln;
import com.twincoders.twinpush.sdk.notifications.PushNotification;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class TwinPushRequest extends RESTJSONRequest {
    SimpleDateFormat b;
    protected boolean sequential;

    public boolean isHttpResponseStatusValid(int i) {
        return i == 422 || i == 403 || i == 404;
    }

    public abstract void onSuccess(JSONObject jSONObject);

    public TwinPushRequest(String str) {
        this(str, null, null);
    }

    public TwinPushRequest(String str, String str2) {
        this(str, str2, null);
    }

    public TwinPushRequest(String str, String str2, String str3) {
        this.sequential = false;
        this.b = null;
        if (str != null) {
            addSegmentParam("apps");
            addSegmentParam(str);
        }
        if (str2 != null) {
            addSegmentParam("devices");
            addSegmentParam(str2);
        }
        if (str3 != null) {
            addSegmentParam("notifications");
            addSegmentParam(str3);
        }
    }

    public String getBaseURL() {
        return String.format("%s/api/v2", new Object[]{TwinPushSDK.getInstance(getRequestLauncher().getContext()).getServerHost()});
    }

    public void onResponseProcess(JSONObject jSONObject) {
        String str;
        try {
            if (jSONObject.isNull("errors")) {
                onSuccess(jSONObject);
                return;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("errors");
            Object obj = jSONObject2.get("message");
            if (obj instanceof String) {
                str = (String) obj;
            } else {
                JSONArray jSONArray = jSONObject2.getJSONArray("message");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < jSONArray.length(); i++) {
                    if (i > 0) {
                        sb.append("\n");
                    }
                    sb.append(jSONArray.getString(i));
                }
                str = sb.toString();
            }
            getListener().onError(new Exception(str));
        } catch (JSONException e) {
            getListener().onError(e);
        }
    }

    /* access modifiers changed from: protected */
    public String getNullableString(JSONObject jSONObject, String str) {
        if (!jSONObject.has(str) || jSONObject.isNull(str)) {
            return null;
        }
        return jSONObject.getString(str);
    }

    public boolean isSequential() {
        return this.sequential;
    }

    /* access modifiers changed from: protected */
    public PushNotification parseNotification(JSONObject jSONObject) {
        PushNotification pushNotification = new PushNotification();
        pushNotification.setId(getNullableString(jSONObject, "id"));
        pushNotification.setTitle(getNullableString(jSONObject, "title"));
        pushNotification.setMessage(getNullableString(jSONObject, "alert"));
        pushNotification.setSound(getNullableString(jSONObject, "sound"));
        pushNotification.setRichURL(getNullableString(jSONObject, NotificationIntentService.EXTRA_NOTIFICATION_RICH_URL));
        pushNotification.setCustomProperties(getCustomPropertiesMap(jSONObject.getJSONObject("custom_properties")));
        if (!jSONObject.isNull("tags")) {
            pushNotification.setTags(getTags(jSONObject.getJSONArray("tags")));
        }
        pushNotification.setDate(parseDate(getNullableString(jSONObject, jSONObject.has("last_sent_at") ? "last_sent_at" : "send_since")));
        return pushNotification;
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getCustomPropertiesMap(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            try {
                if (!jSONObject.isNull(str)) {
                    hashMap.put(str, (String) jSONObject.get(str));
                }
            } catch (JSONException e) {
                Ln.e(e, "Could not find property %1$s on Custom properties JSON", new Object[0]);
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: protected */
    public List<String> getTags(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                arrayList.add(jSONArray.getString(i));
                i++;
            } catch (JSONException e) {
                Ln.e(e, "Error while trying to obtain tags for notification", new Object[0]);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public InboxNotification parseInboxNotification(JSONObject jSONObject) {
        InboxNotification inboxNotification = new InboxNotification();
        inboxNotification.setId(getNullableString(jSONObject, "id"));
        inboxNotification.setNotification(parseNotification(jSONObject.getJSONObject(NotificationIntentService.EXTRA_NOTIFICATION)));
        inboxNotification.setCreatedAt(parseDate(getNullableString(jSONObject, "created_at")));
        inboxNotification.setOpenAt(parseDate(getNullableString(jSONObject, "open_at")));
        return inboxNotification;
    }

    /* access modifiers changed from: protected */
    public Date parseDate(String str) {
        if (str != null) {
            if (this.b == null) {
                this.b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'", Locale.UK);
                this.b.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            try {
                return this.b.parse(str);
            } catch (ParseException e) {
                Ln.e(e, "Error while trying to parse notification date", new Object[0]);
            }
        }
        return null;
    }
}
