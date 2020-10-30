package com.crashlytics.android.answers;

import com.facebook.Response;

public class LoginEvent extends PredefinedEvent<LoginEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "login";
    }

    public LoginEvent putMethod(String str) {
        this.d.a("method", str);
        return this;
    }

    public LoginEvent putSuccess(boolean z) {
        this.d.a(Response.SUCCESS_KEY, Boolean.toString(z));
        return this;
    }
}
