package com.crashlytics.android.answers;

public class RatingEvent extends PredefinedEvent<RatingEvent> {
    /* access modifiers changed from: 0000 */
    public String a() {
        return "rating";
    }

    public RatingEvent putContentId(String str) {
        this.d.a("contentId", str);
        return this;
    }

    public RatingEvent putContentName(String str) {
        this.d.a("contentName", str);
        return this;
    }

    public RatingEvent putContentType(String str) {
        this.d.a("contentType", str);
        return this;
    }

    public RatingEvent putRating(int i) {
        this.d.a("rating", (Number) Integer.valueOf(i));
        return this;
    }
}
