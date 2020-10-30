package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;

public abstract class JsonRequest<T> extends Request<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static final String a = String.format("application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});
    private Listener<T> b;
    private final String c;

    /* access modifiers changed from: protected */
    public abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    public JsonRequest(String str, String str2, Listener<T> listener, ErrorListener errorListener) {
        this(-1, str, str2, listener, errorListener);
    }

    public JsonRequest(int i, String str, String str2, Listener<T> listener, ErrorListener errorListener) {
        super(i, str, errorListener);
        this.b = listener;
        this.c = str2;
    }

    /* access modifiers changed from: protected */
    public void onFinish() {
        super.onFinish();
        this.b = null;
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(T t) {
        if (this.b != null) {
            this.b.onResponse(t);
        }
    }

    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    public byte[] getPostBody() {
        return getBody();
    }

    public String getBodyContentType() {
        return a;
    }

    public byte[] getBody() {
        byte[] bArr = null;
        try {
            if (this.c != null) {
                bArr = this.c.getBytes(PROTOCOL_CHARSET);
            }
            return bArr;
        } catch (UnsupportedEncodingException unused) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", this.c, PROTOCOL_CHARSET);
            return null;
        }
    }
}
