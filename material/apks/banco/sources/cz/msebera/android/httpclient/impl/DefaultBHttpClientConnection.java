package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.config.MessageConstraints;
import cz.msebera.android.httpclient.entity.ContentLengthStrategy;
import cz.msebera.android.httpclient.impl.io.DefaultHttpRequestWriterFactory;
import cz.msebera.android.httpclient.impl.io.DefaultHttpResponseParserFactory;
import cz.msebera.android.httpclient.io.HttpMessageParser;
import cz.msebera.android.httpclient.io.HttpMessageParserFactory;
import cz.msebera.android.httpclient.io.HttpMessageWriter;
import cz.msebera.android.httpclient.io.HttpMessageWriterFactory;
import cz.msebera.android.httpclient.util.Args;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

@NotThreadSafe
public class DefaultBHttpClientConnection extends BHttpConnectionBase implements HttpClientConnection {
    private final HttpMessageParser<HttpResponse> a;
    private final HttpMessageWriter<HttpRequest> b;

    public void onRequestSubmitted(HttpRequest httpRequest) {
    }

    public void onResponseReceived(HttpResponse httpResponse) {
    }

    public DefaultBHttpClientConnection(int i, int i2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2, HttpMessageWriterFactory<HttpRequest> httpMessageWriterFactory, HttpMessageParserFactory<HttpResponse> httpMessageParserFactory) {
        super(i, i2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2);
        if (httpMessageWriterFactory == null) {
            httpMessageWriterFactory = DefaultHttpRequestWriterFactory.INSTANCE;
        }
        this.b = httpMessageWriterFactory.create(getSessionOutputBuffer());
        if (httpMessageParserFactory == null) {
            httpMessageParserFactory = DefaultHttpResponseParserFactory.INSTANCE;
        }
        this.a = httpMessageParserFactory.create(getSessionInputBuffer(), messageConstraints);
    }

    public DefaultBHttpClientConnection(int i, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints) {
        this(i, i, charsetDecoder, charsetEncoder, messageConstraints, null, null, null, null);
    }

    public DefaultBHttpClientConnection(int i) {
        this(i, i, null, null, null, null, null, null, null);
    }

    public void bind(Socket socket) {
        super.bind(socket);
    }

    public boolean isResponseAvailable(int i) {
        ensureOpen();
        try {
            return awaitInput(i);
        } catch (SocketTimeoutException unused) {
            return false;
        }
    }

    public void sendRequestHeader(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        ensureOpen();
        this.b.write(httpRequest);
        onRequestSubmitted(httpRequest);
        incrementRequestCount();
    }

    public void sendRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        Args.notNull(httpEntityEnclosingRequest, "HTTP request");
        ensureOpen();
        HttpEntity entity = httpEntityEnclosingRequest.getEntity();
        if (entity != null) {
            OutputStream prepareOutput = prepareOutput(httpEntityEnclosingRequest);
            entity.writeTo(prepareOutput);
            prepareOutput.close();
        }
    }

    public HttpResponse receiveResponseHeader() {
        ensureOpen();
        HttpResponse httpResponse = (HttpResponse) this.a.parse();
        onResponseReceived(httpResponse);
        if (httpResponse.getStatusLine().getStatusCode() >= 200) {
            incrementResponseCount();
        }
        return httpResponse;
    }

    public void receiveResponseEntity(HttpResponse httpResponse) {
        Args.notNull(httpResponse, "HTTP response");
        ensureOpen();
        httpResponse.setEntity(prepareInput(httpResponse));
    }

    public void flush() {
        ensureOpen();
        doFlush();
    }
}
