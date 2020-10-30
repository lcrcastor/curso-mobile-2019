package com.crashlytics.android.answers;

public class ContentViewEvent extends PredefinedEvent<ContentViewEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "contentView";
    }

    public ContentViewEvent putContentId(String str) {
        this.d.a("contentId", str);
        return this;
    }

    public ContentViewEvent putContentName(String str) {
        this.d.a("contentName", str);
        return this;
    }

    public ContentViewEvent putContentType(String str) {
        this.d.a("contentType", str);
        return this;
    }
}
