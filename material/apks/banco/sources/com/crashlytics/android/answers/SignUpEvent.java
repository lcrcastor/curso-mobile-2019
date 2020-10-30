package com.crashlytics.android.answers;

import com.facebook.Response;

public class SignUpEvent extends PredefinedEvent<SignUpEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "signUp";
    }

    public SignUpEvent putMethod(String str) {
        this.d.a("method", str);
        return this;
    }

    public SignUpEvent putSuccess(boolean z) {
        this.d.a(Response.SUCCESS_KEY, Boolean.toString(z));
        return this;
    }
}
