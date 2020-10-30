package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.concurrent.Cancellable;
import cz.msebera.android.httpclient.conn.ConnectionReleaseTrigger;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@ThreadSafe
class ConnectionHolder implements Cancellable, ConnectionReleaseTrigger, Closeable {
    public HttpClientAndroidLog a;
    private final HttpClientConnectionManager b;
    private final HttpClientConnection c;
    private volatile boolean d;
    private volatile Object e;
    private volatile long f;
    private volatile TimeUnit g;
    private volatile boolean h;

    public ConnectionHolder(HttpClientAndroidLog httpClientAndroidLog, HttpClientConnectionManager httpClientConnectionManager, HttpClientConnection httpClientConnection) {
        this.a = httpClientAndroidLog;
        this.b = httpClientConnectionManager;
        this.c = httpClientConnection;
    }

    public boolean a() {
        return this.d;
    }

    public void b() {
        this.d = true;
    }

    public void c() {
        this.d = false;
    }

    public void a(Object obj) {
        this.e = obj;
    }

    public void a(long j, TimeUnit timeUnit) {
        synchronized (this.c) {
            this.f = j;
            this.g = timeUnit;
        }
    }

    public void releaseConnection() {
        synchronized (this.c) {
            if (!this.h) {
                this.h = true;
                if (this.d) {
                    this.b.releaseConnection(this.c, this.e, this.f, this.g);
                } else {
                    try {
                        this.c.close();
                        this.a.debug("Connection discarded");
                        this.b.releaseConnection(this.c, null, 0, TimeUnit.MILLISECONDS);
                    } catch (IOException e2) {
                        try {
                            if (this.a.isDebugEnabled()) {
                                this.a.debug(e2.getMessage(), e2);
                            }
                        } finally {
                            this.b.releaseConnection(this.c, null, 0, TimeUnit.MILLISECONDS);
                        }
                    }
                }
            }
        }
    }

    public void abortConnection() {
        synchronized (this.c) {
            if (!this.h) {
                this.h = true;
                try {
                    this.c.shutdown();
                    this.a.debug("Connection discarded");
                    this.b.releaseConnection(this.c, null, 0, TimeUnit.MILLISECONDS);
                } catch (IOException e2) {
                    try {
                        if (this.a.isDebugEnabled()) {
                            this.a.debug(e2.getMessage(), e2);
                        }
                    } finally {
                        this.b.releaseConnection(this.c, null, 0, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }

    public boolean cancel() {
        boolean z = this.h;
        this.a.debug("Cancelling request execution");
        abortConnection();
        return !z;
    }

    public boolean d() {
        return this.h;
    }

    public void close() {
        abortConnection();
    }
}
