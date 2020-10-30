package com.indra.httpclient.request;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.indra.httpclient.http.HurlStack;

public class VolleyRequestSingleton {
    private static Context b;
    private static VolleyRequestSingleton c;
    private RequestQueue a = getRequestQueue();

    private VolleyRequestSingleton(Context context) {
        b = context;
    }

    public static synchronized VolleyRequestSingleton getInstance(Context context) {
        VolleyRequestSingleton volleyRequestSingleton;
        synchronized (VolleyRequestSingleton.class) {
            if (c == null) {
                c = new VolleyRequestSingleton(context);
            }
            volleyRequestSingleton = c;
        }
        return volleyRequestSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (this.a == null) {
            this.a = Volley.newRequestQueue(b, (HttpStack) new HurlStack().setmContext(b));
        }
        return this.a;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
