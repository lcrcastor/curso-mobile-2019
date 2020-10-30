package com.crashlytics.android.answers;

import com.crashlytics.android.answers.PredefinedEvent;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.Map;

public abstract class PredefinedEvent<T extends PredefinedEvent> extends AnswersEvent<T> {
    final AnswersAttributes d = new AnswersAttributes(this.b);

    /* access modifiers changed from: 0000 */
    public abstract String a();

    /* access modifiers changed from: 0000 */
    public Map<String, Object> c() {
        return this.d.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{type:\"");
        sb.append(a());
        sb.append(TokenParser.DQUOTE);
        sb.append(", predefinedAttributes:");
        sb.append(this.d);
        sb.append(", customAttributes:");
        sb.append(this.c);
        sb.append("}");
        return sb.toString();
    }
}
