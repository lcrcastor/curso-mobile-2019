package com.crashlytics.android.answers;

public class ShareEvent extends PredefinedEvent<ShareEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "share";
    }

    public ShareEvent putMethod(String str) {
        this.d.a("method", str);
        return this;
    }

    public ShareEvent putContentId(String str) {
        this.d.a("contentId", str);
        return this;
    }

    public ShareEvent putContentName(String str) {
        this.d.a("contentName", str);
        return this;
    }

    public ShareEvent putContentType(String str) {
        this.d.a("contentType", str);
        return this;
    }
}
