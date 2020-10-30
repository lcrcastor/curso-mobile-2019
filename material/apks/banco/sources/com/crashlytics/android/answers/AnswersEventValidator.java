package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import java.util.Locale;
import java.util.Map;

class AnswersEventValidator {
    final int a;
    final int b;
    boolean c;

    public AnswersEventValidator(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }

    public String a(String str) {
        if (str.length() <= this.b) {
            return str;
        }
        a((RuntimeException) new IllegalArgumentException(String.format(Locale.US, "String is too long, truncating to %d characters", new Object[]{Integer.valueOf(this.b)})));
        return str.substring(0, this.b);
    }

    public boolean a(Object obj, String str) {
        if (obj != null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" must not be null");
        a((RuntimeException) new NullPointerException(sb.toString()));
        return true;
    }

    public boolean a(Map<String, Object> map, String str) {
        if (map.size() < this.a || map.containsKey(str)) {
            return false;
        }
        a((RuntimeException) new IllegalArgumentException(String.format(Locale.US, "Limit of %d attributes reached, skipping attribute", new Object[]{Integer.valueOf(this.a)})));
        return true;
    }

    private void a(RuntimeException runtimeException) {
        if (this.c) {
            throw runtimeException;
        }
        Fabric.getLogger().e(Answers.TAG, "Invalid user input detected", runtimeException);
    }
}
