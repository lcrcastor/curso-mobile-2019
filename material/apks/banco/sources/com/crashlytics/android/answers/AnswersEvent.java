package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersEvent;
import io.fabric.sdk.android.Fabric;
import java.util.Map;

public abstract class AnswersEvent<T extends AnswersEvent> {
    public static final int MAX_NUM_ATTRIBUTES = 20;
    public static final int MAX_STRING_LENGTH = 100;
    final AnswersEventValidator b = new AnswersEventValidator(20, 100, Fabric.isDebuggable());
    final AnswersAttributes c = new AnswersAttributes(this.b);

    /* access modifiers changed from: 0000 */
    public Map<String, Object> b() {
        return this.c.b;
    }

    public T putCustomAttribute(String str, String str2) {
        this.c.a(str, str2);
        return this;
    }

    public T putCustomAttribute(String str, Number number) {
        this.c.a(str, number);
        return this;
    }
}
