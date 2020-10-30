package com.crashlytics.android.answers;

import cz.msebera.android.httpclient.message.TokenParser;

public class CustomEvent extends AnswersEvent<CustomEvent> {
    private final String a;

    public CustomEvent(String str) {
        if (str == null) {
            throw new NullPointerException("eventName must not be null");
        }
        this.a = this.b.a(str);
    }

    /* access modifiers changed from: 0000 */
    public String a() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{eventName:\"");
        sb.append(this.a);
        sb.append(TokenParser.DQUOTE);
        sb.append(", customAttributes:");
        sb.append(this.c);
        sb.append("}");
        return sb.toString();
    }
}
