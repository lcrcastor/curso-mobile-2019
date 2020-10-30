package com.twincoders.twinpush.sdk.communications;

import android.net.Uri;
import android.net.Uri.Builder;
import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.logging.Ln;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class RESTRequest extends DefaultRequest {
    List<String> a;
    protected String baseURL;
    protected String methodName;
    protected String resourceName;

    public RESTRequest() {
        this.a = new ArrayList();
        this.baseURL = null;
        this.resourceName = null;
        this.methodName = null;
        this.httpMethod = HttpMethod.GET;
    }

    public void addSegmentParam(String str) {
        this.a.add(str);
    }

    /* access modifiers changed from: protected */
    public void serializeSegments(List<String> list, Builder builder) {
        for (String appendPath : list) {
            builder.appendPath(appendPath);
        }
    }

    /* access modifiers changed from: protected */
    public void serializeParams(List<TwinRequestParam> list, Builder builder) {
        if (this.httpMethod == HttpMethod.GET) {
            for (TwinRequestParam twinRequestParam : list) {
                builder.appendQueryParameter(twinRequestParam.getKey(), twinRequestParam.getValue().toString());
            }
        }
    }

    public String getURL() {
        String str = "";
        if (getBaseURL() == null) {
            return str;
        }
        Builder buildUpon = Uri.parse(getBaseURL()).buildUpon();
        if (getResourceName() != null) {
            buildUpon.appendPath(getResourceName());
        }
        if (getMethodName() != null) {
            buildUpon.appendPath(getMethodName());
        }
        serializeSegments(this.a, buildUpon);
        serializeParams(getParams(), buildUpon);
        return buildUpon.build().toString();
    }

    public String getBaseURL() {
        return this.baseURL;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    /* access modifiers changed from: protected */
    public JSONObject serializeBodyParams(List<TwinRequestParam> list) {
        JSONObject jSONObject = new JSONObject();
        for (TwinRequestParam twinRequestParam : list) {
            try {
                switch (twinRequestParam.getParamType()) {
                    case SIMPLE:
                        if (twinRequestParam.getValue() != null) {
                            if (!(twinRequestParam.getValue() instanceof Map)) {
                                jSONObject.put(twinRequestParam.getKey(), twinRequestParam.getValue());
                                break;
                            } else {
                                jSONObject.put(twinRequestParam.getKey(), new JSONObject((Map) twinRequestParam.getValue()));
                                break;
                            }
                        } else {
                            break;
                        }
                    case ARRAY:
                        if (twinRequestParam.getArrayValue() == null) {
                            break;
                        } else {
                            jSONObject.put(twinRequestParam.getKey(), new JSONArray(twinRequestParam.getArrayValue()));
                            break;
                        }
                }
            } catch (JSONException e) {
                Ln.e(e, "Error while trying to serialize params", new Object[0]);
            }
        }
        return jSONObject;
    }

    public String getBodyContent() {
        return serializeBodyParams(getParams()).toString();
    }
}
