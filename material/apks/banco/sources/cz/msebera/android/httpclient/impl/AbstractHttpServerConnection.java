package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpConnectionMetrics;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestFactory;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpServerConnection;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.impl.entity.DisallowIdentityContentLengthStrategy;
import cz.msebera.android.httpclient.impl.entity.EntityDeserializer;
import cz.msebera.android.httpclient.impl.entity.EntitySerializer;
import cz.msebera.android.httpclient.impl.entity.LaxContentLengthStrategy;
import cz.msebera.android.httpclient.impl.entity.StrictContentLengthStrategy;
import cz.msebera.android.httpclient.impl.io.DefaultHttpRequestParser;
import cz.msebera.android.httpclient.impl.io.HttpResponseWriter;
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

@NotThreadSafe
@Deprecated
public abstract class AbstractHttpServerConnection implements HttpServerConnection {
    private final EntitySerializer a = createEntitySerializer();
    private final EntityDeserializer b = createEntityDeserializer();
    private SessionInputBuffer c = null;
    private SessionOutputBuffer d = null;
    private EofSensor e = null;
    private HttpMessageParser<HttpRequest> f = null;
    private HttpMessageWriter<HttpResponse> g = null;
    private HttpConnectionMetricsImpl h = null;

    /* access modifiers changed from: protected */
    public abstract void assertOpen();

    /* access modifiers changed from: protected */
    public EntityDeserializer createEntityDeserializer() {
        return new EntityDeserializer(new DisallowIdentityContentLengthStrategy(new LaxContentLengthStrategy(0)));
    }

    /* access modifiers changed from: protected */
    public EntitySerializer createEntitySerializer() {
        return new EntitySerializer(new StrictContentLengthStrategy());
    }

    /* access modifiers changed from: protected */
    public HttpRequestFactory createHttpRequestFactory() {
        return DefaultHttpRequestFactory.INSTANCE;
    }

    /* access modifiers changed from: protected */
    public HttpMessageParser<HttpRequest> createRequestParser(SessionInputBuffer sessionInputBuffer, HttpRequestFactory httpRequestFactory, HttpParams httpParams) {
        return new DefaultHttpRequestParser(sessionInputBuffer, (LineParser) null, httpRequestFactory, httpParams);
    }

    /* access modifiers changed from: protected */
    public HttpMessageWriter<HttpResponse> createResponseWriter(SessionOutputBuffer sessionOutputBuffer, HttpParams httpParams) {
        return new HttpResponseWriter(sessionOutputBuffer, null, httpParams);
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
        this.f = createRequestParser(sessionInputBuffer, createHttpRequestFactory(), httpParams);
        this.g = createResponseWriter(sessionOutputBuffer, httpParams);
        this.h = createConnectionMetrics(sessionInputBuffer.getMetrics(), sessionOutputBuffer.getMetrics());
    }

    public HttpRequest receiveRequestHeader() {
        assertOpen();
        HttpRequest httpRequest = (HttpRequest) this.f.parse();
        this.h.incrementRequestCount();
        return httpRequest;
    }

    public void receiveRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        assertOpen();
        httpEntityEnclosingRequest.setEntity(this.b.deserialize(this.c, httpEntityEnclosingRequest));
    }

    /* access modifiers changed from: protected */
    public void doFlush() {
        this.d.flush();
    }

    public void flush() {
        assertOpen();
        doFlush();
    }

    public void sendResponseHeader(HttpResponse httpResponse) {
        Args.notNull(httpResponse, "HTTP response");
        assertOpen();
        this.g.write(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            this.h.incrementResponseCount();
        }
    }

    public void sendResponseEntity(HttpResponse httpResponse) {
        if (httpResponse.getEntity() != null) {
            this.a.serialize(this.d, httpResponse, httpResponse.getEntity());
        }
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
        } catch (IOException unused) {
            return true;
        }
    }

    public HttpConnectionMetrics getMetrics() {
        return this.h;
    }
}
