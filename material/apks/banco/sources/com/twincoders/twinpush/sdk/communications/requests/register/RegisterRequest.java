package com.twincoders.twinpush.sdk.communications.requests.register;

import com.twincoders.twinpush.sdk.communications.TwinRequest.ErrorListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushTokenRequest;
import com.twincoders.twinpush.sdk.entities.RegistrationInfo;
import com.twincoders.twinpush.sdk.logging.Ln;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterRequest extends TwinPushTokenRequest {
    Listener c;

    public interface Listener extends ErrorListener {
        void onRegistrationSuccess(String str, String str2);
    }

    public RegisterRequest(String str, RegistrationInfo registrationInfo, Listener listener) {
        super(str);
        this.sequential = true;
        this.c = listener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("devices");
        addSegmentParam("register");
        addParam("platform", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        addParam("udid", registrationInfo.udid);
        addParam("alias_device", registrationInfo.deviceAlias);
        addParam("push_token", registrationInfo.pushToken);
        addParam("app_version", registrationInfo.appVersion);
        addParam("sdk_version", registrationInfo.sdkVersion);
        addParam("os_version", registrationInfo.osVersion);
        addParam("os_version_code", registrationInfo.osVersionInt);
        addParam("device_manufacturer", registrationInfo.deviceManufacturer);
        addParam("device_model", registrationInfo.deviceModel);
        addParam("device_code", registrationInfo.deviceCode);
        addParam("language", registrationInfo.language);
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        String str;
        String str2 = null;
        try {
            JSONObject jSONObject2 = jSONObject.getJSONArray("objects").getJSONObject(0);
            try {
                if (!jSONObject2.isNull("alias_device")) {
                    str = jSONObject2.getString("alias_device");
                    str2 = jSONObject2.getString("id");
                    getListener().onRegistrationSuccess(str2, str);
                }
            } catch (JSONException e) {
                Ln.w(e, "Could not find field %1$s on response", "alias_device");
            }
            str = null;
            try {
                str2 = jSONObject2.getString("id");
            } catch (JSONException e2) {
                try {
                    Ln.w(e2, "Could not find field %1$s on response", "id");
                } catch (JSONException e3) {
                    e = e3;
                }
            }
        } catch (JSONException e4) {
            e = e4;
            str = null;
            Ln.w(e, "Could obtain device object on response", new Object[0]);
            getListener().onRegistrationSuccess(str2, str);
        }
        getListener().onRegistrationSuccess(str2, str);
    }

    public Listener getListener() {
        return this.c;
    }
}
