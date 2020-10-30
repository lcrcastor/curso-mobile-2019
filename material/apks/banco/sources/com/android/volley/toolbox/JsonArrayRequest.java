package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonArrayRequest extends JsonRequest<JSONArray> {
    public JsonArrayRequest(int i, String str, String str2, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(i, str, str2, listener, errorListener);
    }

    public JsonArrayRequest(String str, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(0, str, null, listener, errorListener);
    }

    public JsonArrayRequest(int i, String str, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(i, str, null, listener, errorListener);
    }

    public JsonArrayRequest(int i, String str, JSONArray jSONArray, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(i, str, jSONArray == null ? null : jSONArray.toString(), listener, errorListener);
    }

    public JsonArrayRequest(int i, String str, JSONObject jSONObject, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(i, str, jSONObject == null ? null : jSONObject.toString(), listener, errorListener);
    }

    public JsonArrayRequest(String str, JSONArray jSONArray, Listener<JSONArray> listener, ErrorListener errorListener) {
        this(jSONArray == null ? 0 : 1, str, jSONArray, listener, errorListener);
    }

    public JsonArrayRequest(String str, JSONObject jSONObject, Listener<JSONArray> listener, ErrorListener errorListener) {
        this(jSONObject == null ? 0 : 1, str, jSONObject, listener, errorListener);
    }

    /* access modifiers changed from: protected */
    public Response<JSONArray> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(new JSONArray(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError((Throwable) e));
        } catch (JSONException e2) {
            return Response.error(new ParseError((Throwable) e2));
        }
    }
}
