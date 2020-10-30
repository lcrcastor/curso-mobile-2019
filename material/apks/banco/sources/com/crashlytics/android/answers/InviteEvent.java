package com.crashlytics.android.answers;

public class InviteEvent extends PredefinedEvent<InviteEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "invite";
    }

    public InviteEvent putMethod(String str) {
        this.d.a("method", str);
        return this;
    }
}
