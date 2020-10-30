package com.indra.httpclient.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class JsonBeanRequest<T> extends Request<T> {
    private final Gson a = new Gson();
    private final Class<T> b;
    private final Map<String, String> c;
    private final Listener<T> d;

    public JsonBeanRequest(String str, Class<T> cls, Map<String, String> map, Listener<T> listener, ErrorListener errorListener) {
        super(0, str, errorListener);
        this.b = cls;
        this.c = map;
        this.d = listener;
    }

    public Map<String, String> getHeaders() {
        return this.c != null ? this.c : super.getHeaders();
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(T t) {
        this.d.onResponse(t);
    }

    /* access modifiers changed from: protected */
    public Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            return Response.success(this.a.fromJson(new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers)), this.b), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError((Throwable) e));
        } catch (JsonSyntaxException e2) {
            return Response.error(new ParseError((Throwable) e2));
        }
    }
}
