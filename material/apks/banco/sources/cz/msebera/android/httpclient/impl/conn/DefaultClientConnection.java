package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.conn.ManagedHttpClientConnection;
import cz.msebera.android.httpclient.conn.OperatedClientConnection;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.SocketHttpClientConnection;
import cz.msebera.android.httpclient.io.HttpMessageParser;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

@NotThreadSafe
@Deprecated
public class DefaultClientConnection extends SocketHttpClientConnection implements ManagedHttpClientConnection, OperatedClientConnection, HttpContext {
    private volatile Socket a;
    private HttpHost b;
    private boolean c;
    private volatile boolean d;
    private final Map<String, Object> e = new HashMap();
    public HttpClientAndroidLog headerLog = new HttpClientAndroidLog("cz.msebera.android.httpclient.headers");
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    public HttpClientAndroidLog wireLog = new HttpClientAndroidLog("cz.msebera.android.httpclient.wire");

    public String getId() {
        return null;
    }

    public final HttpHost getTargetHost() {
        return this.b;
    }

    public final boolean isSecure() {
        return this.c;
    }

    public final Socket getSocket() {
        return this.a;
    }

    public SSLSession getSSLSession() {
        if (this.a instanceof SSLSocket) {
            return ((SSLSocket) this.a).getSession();
        }
        return null;
    }

    public void opening(Socket socket, HttpHost httpHost) {
        assertNotOpen();
        this.a = socket;
        this.b = httpHost;
        if (this.d) {
            socket.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
    }

    public void openCompleted(boolean z, HttpParams httpParams) {
        Args.notNull(httpParams, "Parameters");
        assertNotOpen();
        this.c = z;
        bind(this.a, httpParams);
    }

    public void shutdown() {
        this.d = true;
        try {
            super.shutdown();
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Connection ");
                sb.append(this);
                sb.append(" shut down");
                httpClientAndroidLog.debug(sb.toString());
            }
            Socket socket = this.a;
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e2) {
            this.log.debug("I/O error shutting down connection", e2);
        }
    }

    public void close() {
        try {
            super.close();
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Connection ");
                sb.append(this);
                sb.append(" closed");
                httpClientAndroidLog.debug(sb.toString());
            }
        } catch (IOException e2) {
            this.log.debug("I/O error closing connection", e2);
        }
    }

    /* access modifiers changed from: protected */
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int i, HttpParams httpParams) {
        if (i <= 0) {
            i = 8192;
        }
        SessionInputBuffer createSessionInputBuffer = super.createSessionInputBuffer(socket, i, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionInputBuffer(createSessionInputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams)) : createSessionInputBuffer;
    }

    /* access modifiers changed from: protected */
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int i, HttpParams httpParams) {
        if (i <= 0) {
            i = 8192;
        }
        SessionOutputBuffer createSessionOutputBuffer = super.createSessionOutputBuffer(socket, i, httpParams);
        return this.wireLog.isDebugEnabled() ? new LoggingSessionOutputBuffer(createSessionOutputBuffer, new Wire(this.wireLog), HttpProtocolParams.getHttpElementCharset(httpParams)) : createSessionOutputBuffer;
    }

    /* access modifiers changed from: protected */
    public HttpMessageParser<HttpResponse> createResponseParser(SessionInputBuffer sessionInputBuffer, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        return new DefaultHttpResponseParser(sessionInputBuffer, (LineParser) null, httpResponseFactory, httpParams);
    }

    public void bind(Socket socket) {
        bind(socket, new BasicHttpParams());
    }

    public void update(Socket socket, HttpHost httpHost, boolean z, HttpParams httpParams) {
        assertOpen();
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        if (socket != null) {
            this.a = socket;
            bind(socket, httpParams);
        }
        this.b = httpHost;
        this.c = z;
    }

    public HttpResponse receiveResponseHeader() {
        Header[] allHeaders;
        HttpResponse receiveResponseHeader = super.receiveResponseHeader();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Receiving response: ");
            sb.append(receiveResponseHeader.getStatusLine());
            httpClientAndroidLog.debug(sb.toString());
        }
        if (this.headerLog.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog2 = this.headerLog;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("<< ");
            sb2.append(receiveResponseHeader.getStatusLine().toString());
            httpClientAndroidLog2.debug(sb2.toString());
            for (Header header : receiveResponseHeader.getAllHeaders()) {
                HttpClientAndroidLog httpClientAndroidLog3 = this.headerLog;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("<< ");
                sb3.append(header.toString());
                httpClientAndroidLog3.debug(sb3.toString());
            }
        }
        return receiveResponseHeader;
    }

    public void sendRequestHeader(HttpRequest httpRequest) {
        Header[] allHeaders;
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Sending request: ");
            sb.append(httpRequest.getRequestLine());
            httpClientAndroidLog.debug(sb.toString());
        }
        super.sendRequestHeader(httpRequest);
        if (this.headerLog.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog2 = this.headerLog;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(">> ");
            sb2.append(httpRequest.getRequestLine().toString());
            httpClientAndroidLog2.debug(sb2.toString());
            for (Header header : httpRequest.getAllHeaders()) {
                HttpClientAndroidLog httpClientAndroidLog3 = this.headerLog;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(">> ");
                sb3.append(header.toString());
                httpClientAndroidLog3.debug(sb3.toString());
            }
        }
    }

    public Object getAttribute(String str) {
        return this.e.get(str);
    }

    public Object removeAttribute(String str) {
        return this.e.remove(str);
    }

    public void setAttribute(String str, Object obj) {
        this.e.put(str, obj);
    }
}
