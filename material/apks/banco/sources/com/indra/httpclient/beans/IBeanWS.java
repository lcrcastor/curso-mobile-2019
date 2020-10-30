package com.indra.httpclient.beans;

import com.indra.httpclient.json.JSONObject;

public interface IBeanWS<T> {
    Class<T> getClassBean();

    void setJsonElement(JSONObject jSONObject);
}
