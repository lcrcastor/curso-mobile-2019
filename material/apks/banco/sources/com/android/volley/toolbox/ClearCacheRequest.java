package com.android.volley.toolbox;

import android.os.Handler;
import android.os.Looper;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;

public class ClearCacheRequest extends Request<Object> {
    private final Cache a;
    private final Runnable b;

    /* access modifiers changed from: protected */
    public void deliverResponse(Object obj) {
    }

    /* access modifiers changed from: protected */
    public Response<Object> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    public ClearCacheRequest(Cache cache, Runnable runnable) {
        super(0, null, null);
        this.a = cache;
        this.b = runnable;
    }

    public boolean isCanceled() {
        this.a.clear();
        if (this.b != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(this.b);
        }
        return true;
    }

    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }
}
