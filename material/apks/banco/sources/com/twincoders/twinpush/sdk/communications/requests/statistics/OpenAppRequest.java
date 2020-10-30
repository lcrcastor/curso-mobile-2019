package com.twincoders.twinpush.sdk.communications.requests.statistics;

import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import org.json.JSONObject;

public class OpenAppRequest extends TwinPushRequest {
    DefaultListener c;

    public OpenAppRequest(String str, String str2, DefaultListener defaultListener) {
        super(str, str2);
        this.c = defaultListener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("open_app");
    }

    public ErrorListener getListener() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        this.c.onSuccess();
    }
}
