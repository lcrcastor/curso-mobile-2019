package com.android.volley;

import android.content.Intent;

public class AuthFailureError extends VolleyError {
    private Intent a;

    public AuthFailureError() {
    }

    public AuthFailureError(Intent intent) {
        this.a = intent;
    }

    public AuthFailureError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public AuthFailureError(String str) {
        super(str);
    }

    public AuthFailureError(String str, Exception exc) {
        super(str, exc);
    }

    public Intent getResolutionIntent() {
        return this.a;
    }

    public String getMessage() {
        if (this.a != null) {
            return "User needs to (re)enter credentials.";
        }
        return super.getMessage();
    }
}
