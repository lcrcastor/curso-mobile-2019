package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.conn.EofSensorInputStream;
import cz.msebera.android.httpclient.conn.EofSensorWatcher;
import cz.msebera.android.httpclient.entity.HttpEntityWrapper;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

@NotThreadSafe
class ResponseEntityProxy extends HttpEntityWrapper implements EofSensorWatcher {
    private final ConnectionHolder a;

    public boolean isRepeatable() {
        return false;
    }

    public static void a(HttpResponse httpResponse, ConnectionHolder connectionHolder) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null && entity.isStreaming() && connectionHolder != null) {
            httpResponse.setEntity(new ResponseEntityProxy(entity, connectionHolder));
        }
    }

    ResponseEntityProxy(HttpEntity httpEntity, ConnectionHolder connectionHolder) {
        super(httpEntity);
        this.a = connectionHolder;
    }

    private void b() {
        if (this.a != null) {
            this.a.abortConnection();
        }
    }

    public void a() {
        if (this.a != null) {
            try {
                if (this.a.a()) {
                    this.a.releaseConnection();
                }
            } finally {
                b();
            }
        }
    }

    public InputStream getContent() {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }

    @Deprecated
    public void consumeContent() {
        a();
    }

    public void writeTo(OutputStream outputStream) {
        try {
            this.wrappedEntity.writeTo(outputStream);
            a();
        } finally {
            b();
        }
    }

    /* JADX INFO: finally extract failed */
    public boolean eofDetected(InputStream inputStream) {
        try {
            inputStream.close();
            a();
            b();
            return false;
        } catch (Throwable th) {
            b();
            throw th;
        }
    }

    public boolean streamClosed(InputStream inputStream) {
        boolean z;
        try {
            z = this.a != null && !this.a.d();
            inputStream.close();
            a();
        } catch (SocketException e) {
            if (z) {
                throw e;
            }
        } catch (Throwable th) {
            b();
            throw th;
        }
        b();
        return false;
    }

    public boolean streamAbort(InputStream inputStream) {
        b();
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ResponseEntityProxy{");
        sb.append(this.wrappedEntity);
        sb.append('}');
        return sb.toString();
    }
}
