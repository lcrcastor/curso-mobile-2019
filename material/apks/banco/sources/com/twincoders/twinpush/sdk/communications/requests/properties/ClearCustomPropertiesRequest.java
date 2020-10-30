package com.twincoders.twinpush.sdk.communications.requests.properties;

import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import org.json.JSONObject;

public class ClearCustomPropertiesRequest extends TwinPushRequest {
    DefaultListener c;

    public ClearCustomPropertiesRequest(String str, String str2, DefaultListener defaultListener) {
        super(str, str2);
        this.sequential = true;
        this.c = defaultListener;
        this.httpMethod = HttpMethod.DELETE;
        addSegmentParam("clear_custom_properties");
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        getListener().onSuccess();
    }

    public DefaultListener getListener() {
        return this.c;
    }
}
