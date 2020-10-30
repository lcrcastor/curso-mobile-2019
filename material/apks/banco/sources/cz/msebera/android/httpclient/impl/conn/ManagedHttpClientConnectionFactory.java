package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.conn.HttpConnectionFactory;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.entity.ContentLengthStrategy;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.entity.LaxContentLengthStrategy;
import cz.msebera.android.httpclient.impl.entity.StrictContentLengthStrategy;
import cz.msebera.android.httpclient.impl.io.DefaultHttpRequestWriterFactory;
import cz.msebera.android.httpclient.io.HttpMessageParserFactory;
import cz.msebera.android.httpclient.io.HttpMessageWriterFactory;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.atomic.AtomicLong;

@Immutable
public class ManagedHttpClientConnectionFactory implements HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> {
    public static final ManagedHttpClientConnectionFactory INSTANCE = new ManagedHttpClientConnectionFactory();
    private static final AtomicLong a = new AtomicLong();
    private final HttpMessageWriterFactory<HttpRequest> b;
    private final HttpMessageParserFactory<HttpResponse> c;
    private final ContentLengthStrategy d;
    private final ContentLengthStrategy e;
    public HttpClientAndroidLog headerlog;
    public HttpClientAndroidLog log;
    public HttpClientAndroidLog wirelog;

    public ManagedHttpClientConnectionFactory(HttpMessageWriterFactory<HttpRequest> httpMessageWriterFactory, HttpMessageParserFactory<HttpResponse> httpMessageParserFactory, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2) {
        this.log = new HttpClientAndroidLog(DefaultManagedHttpClientConnection.class);
        this.headerlog = new HttpClientAndroidLog("cz.msebera.android.httpclient.headers");
        this.wirelog = new HttpClientAndroidLog("cz.msebera.android.httpclient.wire");
        if (httpMessageWriterFactory == null) {
            httpMessageWriterFactory = DefaultHttpRequestWriterFactory.INSTANCE;
        }
        this.b = httpMessageWriterFactory;
        if (httpMessageParserFactory == null) {
            httpMessageParserFactory = DefaultHttpResponseParserFactory.INSTANCE;
        }
        this.c = httpMessageParserFactory;
        if (contentLengthStrategy == null) {
            contentLengthStrategy = LaxContentLengthStrategy.INSTANCE;
        }
        this.d = contentLengthStrategy;
        if (contentLengthStrategy2 == null) {
            contentLengthStrategy2 = StrictContentLengthStrategy.INSTANCE;
        }
        this.e = contentLengthStrategy2;
    }

    public ManagedHttpClientConnectionFactory(HttpMessageWriterFactory<HttpRequest> httpMessageWriterFactory, HttpMessageParserFactory<HttpResponse> httpMessageParserFactory) {
        this(httpMessageWriterFactory, httpMessageParserFactory, null, null);
    }

    public ManagedHttpClientConnectionFactory(HttpMessageParserFactory<HttpResponse> httpMessageParserFactory) {
        this(null, httpMessageParserFactory);
    }

    public ManagedHttpClientConnectionFactory() {
        this(null, null);
    }

    public ManagedHttpClientConnection create(HttpRoute httpRoute, ConnectionConfig connectionConfig) {
        CharsetEncoder charsetEncoder;
        CharsetDecoder charsetDecoder;
        ConnectionConfig connectionConfig2 = connectionConfig != null ? connectionConfig : ConnectionConfig.DEFAULT;
        Charset charset = connectionConfig2.getCharset();
        CodingErrorAction malformedInputAction = connectionConfig2.getMalformedInputAction() != null ? connectionConfig2.getMalformedInputAction() : CodingErrorAction.REPORT;
        CodingErrorAction unmappableInputAction = connectionConfig2.getUnmappableInputAction() != null ? connectionConfig2.getUnmappableInputAction() : CodingErrorAction.REPORT;
        if (charset != null) {
            CharsetDecoder newDecoder = charset.newDecoder();
            newDecoder.onMalformedInput(malformedInputAction);
            newDecoder.onUnmappableCharacter(unmappableInputAction);
            CharsetEncoder newEncoder = charset.newEncoder();
            newEncoder.onMalformedInput(malformedInputAction);
            newEncoder.onUnmappableCharacter(unmappableInputAction);
            charsetEncoder = newEncoder;
            charsetDecoder = newDecoder;
        } else {
            charsetDecoder = null;
            charsetEncoder = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("http-outgoing-");
        sb.append(Long.toString(a.getAndIncrement()));
        LoggingManagedHttpClientConnection loggingManagedHttpClientConnection = new LoggingManagedHttpClientConnection(sb.toString(), this.log, this.headerlog, this.wirelog, connectionConfig2.getBufferSize(), connectionConfig2.getFragmentSizeHint(), charsetDecoder, charsetEncoder, connectionConfig2.getMessageConstraints(), this.d, this.e, this.b, this.c);
        return loggingManagedHttpClientConnection;
    }
}
