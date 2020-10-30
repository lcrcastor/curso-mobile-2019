package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpConnection;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Deprecated
public class IdleConnectionHandler {
    private final Map<HttpConnection, TimeValues> a = new HashMap();
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    static class TimeValues {
        /* access modifiers changed from: private */
        public final long a;
        /* access modifiers changed from: private */
        public final long b;

        TimeValues(long j, long j2, TimeUnit timeUnit) {
            this.a = j;
            if (j2 > 0) {
                this.b = j + timeUnit.toMillis(j2);
            } else {
                this.b = Long.MAX_VALUE;
            }
        }
    }

    public void add(HttpConnection httpConnection, long j, TimeUnit timeUnit) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Adding connection at: ");
            sb.append(currentTimeMillis);
            httpClientAndroidLog.debug(sb.toString());
        }
        Map<HttpConnection, TimeValues> map = this.a;
        TimeValues timeValues = new TimeValues(currentTimeMillis, j, timeUnit);
        map.put(httpConnection, timeValues);
    }

    public boolean remove(HttpConnection httpConnection) {
        TimeValues timeValues = (TimeValues) this.a.remove(httpConnection);
        boolean z = true;
        if (timeValues == null) {
            this.log.warn("Removing a connection that never existed!");
            return true;
        }
        if (System.currentTimeMillis() > timeValues.b) {
            z = false;
        }
        return z;
    }

    public void removeAll() {
        this.a.clear();
    }

    public void closeIdleConnections(long j) {
        long currentTimeMillis = System.currentTimeMillis() - j;
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Checking for connections, idle timeout: ");
            sb.append(currentTimeMillis);
            httpClientAndroidLog.debug(sb.toString());
        }
        for (Entry entry : this.a.entrySet()) {
            HttpConnection httpConnection = (HttpConnection) entry.getKey();
            long b = ((TimeValues) entry.getValue()).a;
            if (b <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Closing idle connection, connection time: ");
                    sb2.append(b);
                    httpClientAndroidLog2.debug(sb2.toString());
                }
                try {
                    httpConnection.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }

    public void closeExpiredConnections() {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Checking for expired connections, now: ");
            sb.append(currentTimeMillis);
            httpClientAndroidLog.debug(sb.toString());
        }
        for (Entry entry : this.a.entrySet()) {
            HttpConnection httpConnection = (HttpConnection) entry.getKey();
            TimeValues timeValues = (TimeValues) entry.getValue();
            if (timeValues.b <= currentTimeMillis) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Closing connection, expired @: ");
                    sb2.append(timeValues.b);
                    httpClientAndroidLog2.debug(sb2.toString());
                }
                try {
                    httpConnection.close();
                } catch (IOException e) {
                    this.log.debug("I/O error closing connection", e);
                }
            }
        }
    }
}
