package com.indra.httpclient.client;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.indra.httpclient.request.VolleyRequestSingleton;

public class ClientWS {
    private String a = "tag_cancel_all_request";
    private Context b;
    private RequestQueue c = null;

    public ClientWS(Context context) {
        this.b = context;
        this.c = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (this.c == null) {
            this.c = VolleyRequestSingleton.getInstance(this.b).getRequestQueue();
        }
        return this.c;
    }

    public Context getAppContext() {
        return this.b;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        if (request.getTag() == null) {
            request.setTag(this.a);
        }
        VolleyRequestSingleton.getInstance(this.b).addToRequestQueue(request);
    }

    public void cancelAllRequest() {
        if (this.c != null) {
            this.c.cancelAll((Object) this.a);
        }
    }

    public void cancelRequest(String str) {
        if (this.c != null) {
            this.c.cancelAll((Object) str);
        }
    }
}
