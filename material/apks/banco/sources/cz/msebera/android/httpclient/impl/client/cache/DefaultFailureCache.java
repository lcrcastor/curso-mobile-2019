package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
public class DefaultFailureCache implements FailureCache {
    private final int a;
    private final ConcurrentMap<String, FailureCacheValue> b;

    public DefaultFailureCache() {
        this(1000);
    }

    public DefaultFailureCache(int i) {
        this.a = i;
        this.b = new ConcurrentHashMap();
    }

    public int getErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        FailureCacheValue failureCacheValue = (FailureCacheValue) this.b.get(str);
        if (failureCacheValue != null) {
            return failureCacheValue.getErrorCount();
        }
        return 0;
    }

    public void resetErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        this.b.remove(str);
    }

    public void increaseErrorCount(String str) {
        if (str == null) {
            throw new IllegalArgumentException("identifier may not be null");
        }
        a(str);
        a();
    }

    private void a(String str) {
        for (int i = 0; i < 10; i++) {
            FailureCacheValue failureCacheValue = (FailureCacheValue) this.b.get(str);
            if (failureCacheValue == null) {
                if (this.b.putIfAbsent(str, new FailureCacheValue(str, 1)) == null) {
                    return;
                }
            } else {
                int errorCount = failureCacheValue.getErrorCount();
                if (errorCount != Integer.MAX_VALUE) {
                    if (this.b.replace(str, failureCacheValue, new FailureCacheValue(str, errorCount + 1))) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    private void a() {
        if (this.b.size() > this.a) {
            FailureCacheValue b2 = b();
            if (b2 != null) {
                this.b.remove(b2.getKey(), b2);
            }
        }
    }

    private FailureCacheValue b() {
        long j = Long.MAX_VALUE;
        FailureCacheValue failureCacheValue = null;
        for (Entry entry : this.b.entrySet()) {
            long creationTimeInNanos = ((FailureCacheValue) entry.getValue()).getCreationTimeInNanos();
            if (creationTimeInNanos < j) {
                failureCacheValue = (FailureCacheValue) entry.getValue();
                j = creationTimeInNanos;
            }
        }
        return failureCacheValue;
    }
}
