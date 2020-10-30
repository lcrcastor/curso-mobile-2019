package com.twincoders.twinpush.sdk.communications.requests.properties;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.twincoders.twinpush.sdk.communications.TwinRequest.DefaultListener;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.requests.TwinPushRequest;
import com.twincoders.twinpush.sdk.entities.PropertyType;
import org.json.JSONObject;

public class SetCustomPropertyRequest extends TwinPushRequest {
    DefaultListener c;

    public SetCustomPropertyRequest(String str, String str2, String str3, PropertyType propertyType, Object obj, DefaultListener defaultListener) {
        super(str, str2);
        this.c = defaultListener;
        this.httpMethod = HttpMethod.POST;
        addSegmentParam("set_custom_property");
        addParam("name", str3);
        addParam("type", a(propertyType));
        addParam(TarjetasConstants.VALUE, obj);
    }

    /* access modifiers changed from: protected */
    public void onSuccess(JSONObject jSONObject) {
        getListener().onSuccess();
    }

    public DefaultListener getListener() {
        return this.c;
    }

    private String a(PropertyType propertyType) {
        switch (propertyType) {
            case STRING:
                return "string";
            case BOOLEAN:
                return "boolean";
            case INTEGER:
                return "integer";
            case FLOAT:
                return "float";
            default:
                return null;
        }
    }
}
