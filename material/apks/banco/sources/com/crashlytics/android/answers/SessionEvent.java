package com.crashlytics.android.answers;

import android.app.Activity;
import java.util.Collections;
import java.util.Map;

final class SessionEvent {
    public final SessionEventMetadata a;
    public final long b;
    public final Type c;
    public final Map<String, String> d;
    public final String e;
    public final Map<String, Object> f;
    public final String g;
    public final Map<String, Object> h;
    private String i;

    static class Builder {
        final Type a;
        final long b = System.currentTimeMillis();
        Map<String, String> c = null;
        String d = null;
        Map<String, Object> e = null;
        String f = null;
        Map<String, Object> g = null;

        public Builder(Type type) {
            this.a = type;
        }

        public Builder a(Map<String, String> map) {
            this.c = map;
            return this;
        }

        public Builder a(String str) {
            this.d = str;
            return this;
        }

        public Builder b(Map<String, Object> map) {
            this.e = map;
            return this;
        }

        public Builder b(String str) {
            this.f = str;
            return this;
        }

        public Builder c(Map<String, Object> map) {
            this.g = map;
            return this;
        }

        public SessionEvent a(SessionEventMetadata sessionEventMetadata) {
            SessionEvent sessionEvent = new SessionEvent(sessionEventMetadata, this.b, this.a, this.c, this.d, this.e, this.f, this.g);
            return sessionEvent;
        }
    }

    enum Type {
        START,
        RESUME,
        PAUSE,
        STOP,
        CRASH,
        INSTALL,
        CUSTOM,
        PREDEFINED
    }

    public static Builder a(Type type, Activity activity) {
        return new Builder(type).a(Collections.singletonMap("activity", activity.getClass().getName()));
    }

    public static Builder a(long j) {
        return new Builder(Type.INSTALL).a(Collections.singletonMap("installedAt", String.valueOf(j)));
    }

    public static Builder a(String str) {
        return new Builder(Type.CRASH).a(Collections.singletonMap("sessionId", str));
    }

    public static Builder a(String str, String str2) {
        return a(str).b(Collections.singletonMap("exceptionName", str2));
    }

    public static Builder a(CustomEvent customEvent) {
        return new Builder(Type.CUSTOM).a(customEvent.a()).b(customEvent.b());
    }

    public static Builder a(PredefinedEvent<?> predefinedEvent) {
        return new Builder(Type.PREDEFINED).b(predefinedEvent.a()).c(predefinedEvent.c()).b(predefinedEvent.b());
    }

    private SessionEvent(SessionEventMetadata sessionEventMetadata, long j, Type type, Map<String, String> map, String str, Map<String, Object> map2, String str2, Map<String, Object> map3) {
        this.a = sessionEventMetadata;
        this.b = j;
        this.c = type;
        this.d = map;
        this.e = str;
        this.f = map2;
        this.g = str2;
        this.h = map3;
    }

    public String toString() {
        if (this.i == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(getClass().getSimpleName());
            sb.append(": ");
            sb.append("timestamp=");
            sb.append(this.b);
            sb.append(", type=");
            sb.append(this.c);
            sb.append(", details=");
            sb.append(this.d);
            sb.append(", customType=");
            sb.append(this.e);
            sb.append(", customAttributes=");
            sb.append(this.f);
            sb.append(", predefinedType=");
            sb.append(this.g);
            sb.append(", predefinedAttributes=");
            sb.append(this.h);
            sb.append(", metadata=[");
            sb.append(this.a);
            sb.append("]]");
            this.i = sb.toString();
        }
        return this.i;
    }
}
