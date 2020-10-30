package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpConnectionMetrics;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import java.util.HashMap;
import java.util.Map;

@NotThreadSafe
public class HttpConnectionMetricsImpl implements HttpConnectionMetrics {
    public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
    public static final String REQUEST_COUNT = "http.request-count";
    public static final String RESPONSE_COUNT = "http.response-count";
    public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
    private final HttpTransportMetrics a;
    private final HttpTransportMetrics b;
    private long c = 0;
    private long d = 0;
    private Map<String, Object> e;

    public HttpConnectionMetricsImpl(HttpTransportMetrics httpTransportMetrics, HttpTransportMetrics httpTransportMetrics2) {
        this.a = httpTransportMetrics;
        this.b = httpTransportMetrics2;
    }

    public long getReceivedBytesCount() {
        if (this.a != null) {
            return this.a.getBytesTransferred();
        }
        return -1;
    }

    public long getSentBytesCount() {
        if (this.b != null) {
            return this.b.getBytesTransferred();
        }
        return -1;
    }

    public long getRequestCount() {
        return this.c;
    }

    public void incrementRequestCount() {
        this.c++;
    }

    public long getResponseCount() {
        return this.d;
    }

    public void incrementResponseCount() {
        this.d++;
    }

    public Object getMetric(String str) {
        Object obj = this.e != null ? this.e.get(str) : null;
        if (obj == null) {
            if (REQUEST_COUNT.equals(str)) {
                obj = Long.valueOf(this.c);
            } else if (RESPONSE_COUNT.equals(str)) {
                obj = Long.valueOf(this.d);
            } else if (RECEIVED_BYTES_COUNT.equals(str)) {
                if (this.a != null) {
                    return Long.valueOf(this.a.getBytesTransferred());
                }
                return null;
            } else if (SENT_BYTES_COUNT.equals(str)) {
                if (this.b != null) {
                    return Long.valueOf(this.b.getBytesTransferred());
                }
                return null;
            }
        }
        return obj;
    }

    public void setMetric(String str, Object obj) {
        if (this.e == null) {
            this.e = new HashMap();
        }
        this.e.put(str, obj);
    }

    public void reset() {
        if (this.b != null) {
            this.b.reset();
        }
        if (this.a != null) {
            this.a.reset();
        }
        this.c = 0;
        this.d = 0;
        this.e = null;
    }
}
