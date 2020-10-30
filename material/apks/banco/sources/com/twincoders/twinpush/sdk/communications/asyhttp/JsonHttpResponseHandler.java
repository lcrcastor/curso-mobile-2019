package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.os.Message;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHttpResponseHandler extends AsyncHttpResponseHandler {
    protected static final int SUCCESS_JSON_MESSAGE = 100;

    public void onFailure(Throwable th, JSONArray jSONArray) {
    }

    public void onFailure(Throwable th, JSONObject jSONObject) {
    }

    public void onSuccess(JSONArray jSONArray) {
    }

    public void onSuccess(JSONObject jSONObject) {
    }

    public void onSuccess(int i, Header[] headerArr, JSONObject jSONObject) {
        onSuccess(i, jSONObject);
    }

    public void onSuccess(int i, JSONObject jSONObject) {
        onSuccess(jSONObject);
    }

    public void onSuccess(int i, Header[] headerArr, JSONArray jSONArray) {
        onSuccess(i, jSONArray);
    }

    public void onSuccess(int i, JSONArray jSONArray) {
        onSuccess(jSONArray);
    }

    /* access modifiers changed from: protected */
    public void sendSuccessMessage(int i, Header[] headerArr, String str) {
        if (i != 204) {
            try {
                sendMessage(obtainMessage(100, new Object[]{Integer.valueOf(i), headerArr, parseResponse(str)}));
            } catch (JSONException e) {
                sendFailureMessage((Throwable) e, str);
            }
        } else {
            sendMessage(obtainMessage(100, new Object[]{Integer.valueOf(i), new JSONObject()}));
        }
    }

    /* access modifiers changed from: protected */
    public void handleMessage(Message message) {
        if (message.what != 100) {
            super.handleMessage(message);
            return;
        }
        Object[] objArr = (Object[]) message.obj;
        handleSuccessJsonMessage(((Integer) objArr[0]).intValue(), (Header[]) objArr[1], objArr[2]);
    }

    /* access modifiers changed from: protected */
    public void handleSuccessJsonMessage(int i, Header[] headerArr, Object obj) {
        if (obj instanceof JSONObject) {
            onSuccess(i, headerArr, (JSONObject) obj);
        } else if (obj instanceof JSONArray) {
            onSuccess(i, headerArr, (JSONArray) obj);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected type ");
            sb.append(obj.getClass().getName());
            onFailure((Throwable) new JSONException(sb.toString()), (JSONObject) null);
        }
    }

    /* access modifiers changed from: protected */
    public Object parseResponse(String str) {
        Object obj;
        String trim = str.trim();
        if (trim.startsWith("{") || trim.startsWith("[")) {
            obj = new JSONTokener(trim).nextValue();
        } else {
            obj = null;
        }
        return obj == null ? trim : obj;
    }

    /* access modifiers changed from: protected */
    public void handleFailureMessage(Throwable th, String str) {
        if (str != null) {
            try {
                Object parseResponse = parseResponse(str);
                if (parseResponse instanceof JSONObject) {
                    onFailure(th, (JSONObject) parseResponse);
                } else if (parseResponse instanceof JSONArray) {
                    onFailure(th, (JSONArray) parseResponse);
                } else {
                    onFailure(th, str);
                }
            } catch (JSONException unused) {
                onFailure(th, str);
            }
        } else {
            onFailure(th, "");
        }
    }
}
