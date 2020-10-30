package ar.com.santander.rio.mbanking.services.soap.beans;

import com.google.gson.JsonObject;
import com.indra.httpclient.json.JSONObject;

public abstract class BaseResponseBean {
    JsonObject jsonElement;

    public JsonObject getJsonElement() {
        return this.jsonElement;
    }

    public void setJsonElement(JSONObject jSONObject) {
        this.jsonElement = this.jsonElement;
    }
}
