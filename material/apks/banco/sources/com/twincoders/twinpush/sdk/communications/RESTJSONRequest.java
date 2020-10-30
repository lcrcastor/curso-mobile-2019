package com.twincoders.twinpush.sdk.communications;

import com.twincoders.twinpush.sdk.logging.Ln;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class RESTJSONRequest extends RESTRequest {
    public String getContentType() {
        return "application/json";
    }

    public abstract void onResponseProcess(JSONObject jSONObject);

    public void onResponseProcess(String str) {
        if (!isCanceled().booleanValue()) {
            try {
                JSONObject jSONObject = new JSONObject();
                if (str != null && str.trim().length() > 0) {
                    jSONObject = new JSONObject(str);
                }
                onResponseProcess(jSONObject);
            } catch (JSONException e) {
                Ln.e(e);
                onRequestError(e);
            }
            notifyFinishListeners();
        }
    }
}
