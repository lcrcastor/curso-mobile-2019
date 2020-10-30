package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;
import org.bouncycastle.i18n.ErrorBundle;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform implements EventTransform<SessionEvent> {
    SessionEventTransform() {
    }

    /* renamed from: a */
    public byte[] toBytes(SessionEvent sessionEvent) {
        return b(sessionEvent).toString().getBytes("UTF-8");
    }

    @TargetApi(9)
    public JSONObject b(SessionEvent sessionEvent) {
        try {
            JSONObject jSONObject = new JSONObject();
            SessionEventMetadata sessionEventMetadata = sessionEvent.a;
            jSONObject.put("appBundleId", sessionEventMetadata.a);
            jSONObject.put("executionId", sessionEventMetadata.b);
            jSONObject.put("installationId", sessionEventMetadata.c);
            jSONObject.put("androidId", sessionEventMetadata.d);
            jSONObject.put("advertisingId", sessionEventMetadata.e);
            jSONObject.put("limitAdTrackingEnabled", sessionEventMetadata.f);
            jSONObject.put("betaDeviceToken", sessionEventMetadata.g);
            jSONObject.put("buildId", sessionEventMetadata.h);
            jSONObject.put("osVersion", sessionEventMetadata.i);
            jSONObject.put("deviceModel", sessionEventMetadata.j);
            jSONObject.put("appVersionCode", sessionEventMetadata.k);
            jSONObject.put("appVersionName", sessionEventMetadata.l);
            jSONObject.put("timestamp", sessionEvent.b);
            jSONObject.put("type", sessionEvent.c.toString());
            if (sessionEvent.d != null) {
                jSONObject.put(ErrorBundle.DETAIL_ENTRY, new JSONObject(sessionEvent.d));
            }
            jSONObject.put("customType", sessionEvent.e);
            if (sessionEvent.f != null) {
                jSONObject.put("customAttributes", new JSONObject(sessionEvent.f));
            }
            jSONObject.put("predefinedType", sessionEvent.g);
            if (sessionEvent.h != null) {
                jSONObject.put("predefinedAttributes", new JSONObject(sessionEvent.h));
            }
            return jSONObject;
        } catch (JSONException e) {
            if (VERSION.SDK_INT >= 9) {
                throw new IOException(e.getMessage(), e);
            }
            throw new IOException(e.getMessage());
        }
    }
}
