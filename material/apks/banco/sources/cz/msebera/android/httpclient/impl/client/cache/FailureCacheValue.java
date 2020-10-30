package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;

@Immutable
public class FailureCacheValue {
    private final long a = System.nanoTime();
    private final String b;
    private final int c;

    public FailureCacheValue(String str, int i) {
        this.b = str;
        this.c = i;
    }

    public long getCreationTimeInNanos() {
        return this.a;
    }

    public String getKey() {
        return this.b;
    }

    public int getErrorCount() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[entry creationTimeInNanos=");
        sb.append(this.a);
        sb.append("; ");
        sb.append("key=");
        sb.append(this.b);
        sb.append("; errorCount=");
        sb.append(this.c);
        sb.append(']');
        return sb.toString();
    }
}
