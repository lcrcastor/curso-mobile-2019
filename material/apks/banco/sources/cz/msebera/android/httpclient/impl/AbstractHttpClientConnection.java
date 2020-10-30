package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpConnectionMetrics;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.impl.entity.EntityDeserializer;
import cz.msebera.android.httpclient.impl.entity.EntitySerializer;
import cz.msebera.android.httpclient.impl.entity.LaxContentLengthStrategy;
import cz.msebera.android.httpclient.impl.entity.StrictContentLengthStrategy;
import cz.msebera.android.httpclient.impl.io.DefaultHttpResponseParser;
import cz.msebera.android.httpclient.impl.io.HttpRequestWriter;
import cz.msebera.android.httpclient.io.EofSensor;
import cz.msebera.android.httpclient.io.HttpMessageParser;
import cz.msebera.android.httpclient.io.HttpMessageWriter;
import cz.msebera.android.httpclient.io.HttpTransportMetrics;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.SocketTimeoutException;

@NotThreadSafe
@Deprecated
public abstract class AbstractHttpClientConnection implements HttpClientConnection {
    private final EntitySerializer a = createEntitySerializer();
    private final EntityDeserializer b = createEntityDeserializer();
    private SessionInputBuffer c = null;
    private SessionOutputBuffer d = null;
    private EofSensor e = null;
    private HttpMessageParser<HttpResponse> f = null;
    private HttpMessageWriter<HttpRequest> g = null;
    private HttpConnectionMetricsImpl h = null;

    /* access modifiers changed from: protected */
    public abstract void assertOpen();

    /* access modifiers changed from: protected */
    public EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new LaxContentLengthStrategy());
    }

    /* access modifiers changed from: protected */
    public EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }

    /* access modifiers changed from: protected */
    public HttpResponseFactory createHttpResponseFactory() {
        return DefaultHttpResponseFactory.INSTANCE;
    }

    public HttpMessageParser<HttpResponse> createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultHttpResponseParser(sessionInputBuffer, (LineParser) null, httpResponseFactory, httpParams);
    }

    /* access modifiers changed from: protected */
    public HttpMessageWriter<HttpRequest> createRequestWriter(SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        return new HttpRequestWriter(sessionOutputBuffer, null, httpParams);
    }

    /* access modifiers changed from: protected */
    public HttpConnectionMetricsImpl createConnectionMetrics(HttpTransportMetrics httpTransportMetrics, HttpTransportMetrics httpTransportMetrics2) {
        return new HttpConnectionMetricsImpl(httpTransportMetrics, httpTransportMetrics2);
    }

    /* access modifiers changed from: protected */
    public void init(SessionInputBuffer sessionInputBuffer, SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        this.c = (SessionInputBuffer) Args.notNull(sessionInputBuffer, "Input session buffer");
        this.d = (SessionOutputBuffer) Args.notNull(sessionOutputBuffer, "Output session buffer");
        if (sessionInputBuffer instanceof EofSensor) {
            this.e = (EofSensor) sessionInputBuffer;
        }
        this.f = createResponseParser(sessionInputBuffer, createHttpResponseFactory(), httpParams);
        this.g = createRequestWriter(sessionOutputBuffer, httpParams);
        this.h = createConnectionMetrics(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
    }

    public boolean isResponseAvailable(int i) {
        assertOpen();
        try {
            return this.c.isDataAvailable(i);
        } catch (SocketTimeoutException unused) {
            return false;
        }
    }

    public void sendRequestHeader(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        assertOpen();
        this.g.write(httpRequest);
        this.h.incrementRequestCount();
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        assertOpen();
        if (httpEntityEnclosingRequest.getEntity() != null) {
            this.a.serialize(this.d, httpEntityEnclosingRequest, httpEntityEnclosingRequest.getEntity());
        }
    }

    /* access modifiers changed from: protected */
    public void doFlush() {
        this.d.flush();
    }

    public void flush() {
        assertOpen();
        doFlush();
    }

    public HttpResponse receiveResponseHeader() {
        assertOpen();
        HttpResponse httpResponse = (HttpResponse) this.f.parse();
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.h.incrementResponseCount();
        }
        return httpResponse;
    }

    public void receiveResponseEntity(HttpResponse httpResponse) {
        Args.notNull(httpResponse, "HTTP response");
        assertOpen();
        httpResponse.setEntity(this.b.deserialize(this.c, httpResponse));
    }

    /* access modifiers changed from: protected */
    public boolean isEof() {
        return this.e != null && this.e.isEof();
    }

    public boolean isStale() {
        if (!isOpen() || isEof()) {
            return true;
        }
        try {
            this.c.isDataAvailable(1);
            return isEof();
        } catch (SocketTimeoutException unused) {
            return false;
        } catch (IOException unused2) {
            return true;
        }
    }

    public HttpConnectionMetrics getMetrics() {
        return this.h;
    }
}
