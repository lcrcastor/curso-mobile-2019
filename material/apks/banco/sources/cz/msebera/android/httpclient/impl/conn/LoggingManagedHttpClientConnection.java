package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.config.MessageConstraints;
import cz.msebera.android.httpclient.entity.ContentLengthStrategy;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.io.HttpMessageParserFactory;
import cz.msebera.android.httpclient.io.HttpMessageWriterFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

@NotThreadSafe
class LoggingManagedHttpClientConnection extends DefaultManagedHttpClientConnection {
    public HttpClientAndroidLog a;
    private final HttpClientAndroidLog b;
    private final Wire c;

    public LoggingManagedHttpClientConnection(String str, HttpClientAndroidLog httpClientAndroidLog, HttpClientAndroidLog httpClientAndroidLog2, HttpClientAndroidLog httpClientAndroidLog3, int i, int i2, CharsetDecoder charsetDecoder, CharsetEncoder charsetEncoder, MessageConstraints messageConstraints, ContentLengthStrategy contentLengthStrategy, ContentLengthStrategy contentLengthStrategy2, HttpMessageWriterFactory<HttpRequest> httpMessageWriterFactory, HttpMessageParserFactory<HttpResponse> httpMessageParserFactory) {
        String str2 = str;
        super(str2, i, i2, charsetDecoder, charsetEncoder, messageConstraints, contentLengthStrategy, contentLengthStrategy2, httpMessageWriterFactory, httpMessageParserFactory);
        this.a = httpClientAndroidLog;
        this.b = httpClientAndroidLog2;
        this.c = new Wire(httpClientAndroidLog3, str2);
    }

    public void close() {
        if (this.a.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append(getId());
            sb.append(": Close connection");
            httpClientAndroidLog.debug(sb.toString());
        }
        super.close();
    }

    public void shutdown() {
        if (this.a.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append(getId());
            sb.append(": Shutdown connection");
            httpClientAndroidLog.debug(sb.toString());
        }
        super.shutdown();
    }

    /* access modifiers changed from: protected */
    public InputStream getSocketInputStream(Socket socket) {
        InputStream socketInputStream = super.getSocketInputStream(socket);
        return this.c.enabled() ? new LoggingInputStream(socketInputStream, this.c) : socketInputStream;
    }

    /* access modifiers changed from: protected */
    public OutputStream getSocketOutputStream(Socket socket) {
        OutputStream socketOutputStream = super.getSocketOutputStream(socket);
        return this.c.enabled() ? new LoggingOutputStream(socketOutputStream, this.c) : socketOutputStream;
    }

    /* access modifiers changed from: protected */
    public void onResponseReceived(HttpResponse httpResponse) {
        Header[] allHeaders;
        if (httpResponse != null && this.b.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append(getId());
            sb.append(" << ");
            sb.append(httpResponse.getStatusLine().toString());
            httpClientAndroidLog.debug(sb.toString());
            for (Header header : httpResponse.getAllHeaders()) {
                HttpClientAndroidLog httpClientAndroidLog2 = this.b;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getId());
                sb2.append(" << ");
                sb2.append(header.toString());
                httpClientAndroidLog2.debug(sb2.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRequestSubmitted(HttpRequest httpRequest) {
        Header[] allHeaders;
        if (httpRequest != null && this.b.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.b;
            StringBuilder sb = new StringBuilder();
            sb.append(getId());
            sb.append(" >> ");
            sb.append(httpRequest.getRequestLine().toString());
            httpClientAndroidLog.debug(sb.toString());
            for (Header header : httpRequest.getAllHeaders()) {
                HttpClientAndroidLog httpClientAndroidLog2 = this.b;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getId());
                sb2.append(" >> ");
                sb2.append(header.toString());
                httpClientAndroidLog2.debug(sb2.toString());
            }
        }
    }
}
