package com.android.volley;

public class RedirectError extends VolleyError {
    public RedirectError() {
    }

    public RedirectError(Throwable th) {
        super(th);
    }

    public RedirectError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
