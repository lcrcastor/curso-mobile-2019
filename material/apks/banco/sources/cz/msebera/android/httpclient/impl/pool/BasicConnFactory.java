package cz.msebera.android.httpclient.impl.pool;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.impl.DefaultBHttpClientConnection;
import cz.msebera.android.httpclient.impl.DefaultBHttpClientConnectionFactory;
import cz.msebera.android.httpclient.params.CoreConnectionPNames;
import cz.msebera.android.httpclient.params.HttpParamConfig;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.pool.ConnFactory;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

@Immutable
public class BasicConnFactory implements ConnFactory<HttpHost, HttpClientConnection> {
    private final SocketFactory a;
    private final SSLSocketFactory b;
    private final int c;
    private final SocketConfig d;
    private final HttpConnectionFactory<? extends HttpClientConnection> e;

    @Deprecated
    public BasicConnFactory(SSLSocketFactory sSLSocketFactory, HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP params");
        this.a = null;
        this.b = sSLSocketFactory;
        this.c = httpParams.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
        this.d = HttpParamConfig.getSocketConfig(httpParams);
        this.e = new DefaultBHttpClientConnectionFactory(HttpParamConfig.getConnectionConfig(httpParams));
    }

    @Deprecated
    public BasicConnFactory(HttpParams httpParams) {
        this((SSLSocketFactory) null, httpParams);
    }

    public BasicConnFactory(SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, int i, SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this.a = socketFactory;
        this.b = sSLSocketFactory;
        this.c = i;
        if (socketConfig == null) {
            socketConfig = SocketConfig.DEFAULT;
        }
        this.d = socketConfig;
        if (connectionConfig == null) {
            connectionConfig = ConnectionConfig.DEFAULT;
        }
        this.e = new DefaultBHttpClientConnectionFactory(connectionConfig);
    }

    public BasicConnFactory(int i, SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this(null, null, i, socketConfig, connectionConfig);
    }

    public BasicConnFactory(SocketConfig socketConfig, ConnectionConfig connectionConfig) {
        this(null, null, 0, socketConfig, connectionConfig);
    }

    public BasicConnFactory() {
        this(null, null, 0, SocketConfig.DEFAULT, ConnectionConfig.DEFAULT);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public HttpClientConnection create(Socket socket, HttpParams httpParams) {
        DefaultBHttpClientConnection defaultBHttpClientConnection = new DefaultBHttpClientConnection(httpParams.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8192));
        defaultBHttpClientConnection.bind(socket);
        return defaultBHttpClientConnection;
    }

    public HttpClientConnection create(HttpHost httpHost) {
        SocketFactory socketFactory;
        String schemeName = httpHost.getSchemeName();
        Socket socket = HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(schemeName) ? this.a != null ? this.a.createSocket() : new Socket() : null;
        if ("https".equalsIgnoreCase(schemeName)) {
            if (this.b != null) {
                socketFactory = this.b;
            } else {
                socketFactory = SSLSocketFactory.getDefault();
            }
            socket = socketFactory.createSocket();
        }
        if (socket == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(schemeName);
            sb.append(" scheme is not supported");
            throw new IOException(sb.toString());
        }
        String hostName = httpHost.getHostName();
        int port = httpHost.getPort();
        if (port == -1) {
            if (httpHost.getSchemeName().equalsIgnoreCase(HttpHost.DEFAULT_SCHEME_NAME)) {
                port = 80;
            } else if (httpHost.getSchemeName().equalsIgnoreCase("https")) {
                port = 443;
            }
        }
        socket.setSoTimeout(this.d.getSoTimeout());
        if (this.d.getSndBufSize() > 0) {
            socket.setSendBufferSize(this.d.getSndBufSize());
        }
        if (this.d.getRcvBufSize() > 0) {
            socket.setReceiveBufferSize(this.d.getRcvBufSize());
        }
        socket.setTcpNoDelay(this.d.isTcpNoDelay());
        int soLinger = this.d.getSoLinger();
        if (soLinger >= 0) {
            socket.setSoLinger(true, soLinger);
        }
        socket.setKeepAlive(this.d.isSoKeepAlive());
        socket.connect(new InetSocketAddress(hostName, port), this.c);
        return (HttpClientConnection) this.e.createConnection(socket);
    }
}
