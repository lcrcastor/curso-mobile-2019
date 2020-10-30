package okhttp3.internal.connection;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import cz.msebera.android.httpclient.protocol.HTTP;
import io.reactivex.annotations.SchedulerSupport;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http1.Http1Codec;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Codec;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection.Builder;
import okhttp3.internal.http2.Http2Connection.Listener;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket.Streams;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public final class RealConnection extends Listener implements Connection {
    private final ConnectionPool a;
    public int allocationLimit = 1;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList();
    private final Route b;
    private Socket c;
    private Socket d;
    private Handshake e;
    private Protocol f;
    private Http2Connection g;
    private BufferedSource h;
    private BufferedSink i;
    public long idleAtNanos = Long.MAX_VALUE;
    public boolean noNewStreams;
    public int successCount;

    public RealConnection(ConnectionPool connectionPool, Route route) {
        this.a = connectionPool;
        this.b = route;
    }

    public static RealConnection testConnection(ConnectionPool connectionPool, Route route, Socket socket, long j) {
        RealConnection realConnection = new RealConnection(connectionPool, route);
        realConnection.d = socket;
        realConnection.idleAtNanos = j;
        return realConnection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0149  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0156 A[EDGE_INSN: B:63:0x0156->B:61:0x0156 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(int r18, int r19, int r20, int r21, boolean r22, okhttp3.Call r23, okhttp3.EventListener r24) {
        /*
            r17 = this;
            r7 = r17
            r8 = r23
            r9 = r24
            okhttp3.Protocol r1 = r7.f
            if (r1 == 0) goto L_0x0012
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "already connected"
            r1.<init>(r2)
            throw r1
        L_0x0012:
            okhttp3.Route r1 = r7.b
            okhttp3.Address r1 = r1.address()
            java.util.List r1 = r1.connectionSpecs()
            okhttp3.internal.connection.ConnectionSpecSelector r10 = new okhttp3.internal.connection.ConnectionSpecSelector
            r10.<init>(r1)
            okhttp3.Route r2 = r7.b
            okhttp3.Address r2 = r2.address()
            javax.net.ssl.SSLSocketFactory r2 = r2.sslSocketFactory()
            if (r2 != 0) goto L_0x007b
            okhttp3.ConnectionSpec r2 = okhttp3.ConnectionSpec.CLEARTEXT
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0042
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r2 = new java.net.UnknownServiceException
            java.lang.String r3 = "CLEARTEXT communication not enabled for client"
            r2.<init>(r3)
            r1.<init>(r2)
            throw r1
        L_0x0042:
            okhttp3.Route r1 = r7.b
            okhttp3.Address r1 = r1.address()
            okhttp3.HttpUrl r1 = r1.url()
            java.lang.String r1 = r1.host()
            okhttp3.internal.platform.Platform r2 = okhttp3.internal.platform.Platform.get()
            boolean r2 = r2.isCleartextTrafficPermitted(r1)
            if (r2 != 0) goto L_0x009a
            okhttp3.internal.connection.RouteException r2 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r3 = new java.net.UnknownServiceException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "CLEARTEXT communication to "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r1 = " not permitted by network security policy"
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r3.<init>(r1)
            r2.<init>(r3)
            throw r2
        L_0x007b:
            okhttp3.Route r1 = r7.b
            okhttp3.Address r1 = r1.address()
            java.util.List r1 = r1.protocols()
            okhttp3.Protocol r2 = okhttp3.Protocol.H2_PRIOR_KNOWLEDGE
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L_0x009a
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r2 = new java.net.UnknownServiceException
            java.lang.String r3 = "H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"
            r2.<init>(r3)
            r1.<init>(r2)
            throw r1
        L_0x009a:
            r11 = 0
            r12 = r11
        L_0x009c:
            okhttp3.Route r1 = r7.b     // Catch:{ IOException -> 0x010a }
            boolean r1 = r1.requiresTunnel()     // Catch:{ IOException -> 0x010a }
            if (r1 == 0) goto L_0x00ba
            r1 = r7
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r8
            r6 = r9
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x010a }
            java.net.Socket r1 = r7.c     // Catch:{ IOException -> 0x010a }
            if (r1 != 0) goto L_0x00b5
            goto L_0x00d7
        L_0x00b5:
            r13 = r18
            r14 = r19
            goto L_0x00c1
        L_0x00ba:
            r13 = r18
            r14 = r19
            r7.a(r13, r14, r8, r9)     // Catch:{ IOException -> 0x0108 }
        L_0x00c1:
            r15 = r21
            r7.a(r10, r15, r8, r9)     // Catch:{ IOException -> 0x0106 }
            okhttp3.Route r1 = r7.b     // Catch:{ IOException -> 0x0106 }
            java.net.InetSocketAddress r1 = r1.socketAddress()     // Catch:{ IOException -> 0x0106 }
            okhttp3.Route r2 = r7.b     // Catch:{ IOException -> 0x0106 }
            java.net.Proxy r2 = r2.proxy()     // Catch:{ IOException -> 0x0106 }
            okhttp3.Protocol r3 = r7.f     // Catch:{ IOException -> 0x0106 }
            r9.connectEnd(r8, r1, r2, r3)     // Catch:{ IOException -> 0x0106 }
        L_0x00d7:
            okhttp3.Route r1 = r7.b
            boolean r1 = r1.requiresTunnel()
            if (r1 == 0) goto L_0x00f0
            java.net.Socket r1 = r7.c
            if (r1 != 0) goto L_0x00f0
            java.net.ProtocolException r1 = new java.net.ProtocolException
            java.lang.String r2 = "Too many tunnel connections attempted: 21"
            r1.<init>(r2)
            okhttp3.internal.connection.RouteException r2 = new okhttp3.internal.connection.RouteException
            r2.<init>(r1)
            throw r2
        L_0x00f0:
            okhttp3.internal.http2.Http2Connection r1 = r7.g
            if (r1 == 0) goto L_0x0105
            okhttp3.ConnectionPool r1 = r7.a
            monitor-enter(r1)
            okhttp3.internal.http2.Http2Connection r2 = r7.g     // Catch:{ all -> 0x0101 }
            int r2 = r2.maxConcurrentStreams()     // Catch:{ all -> 0x0101 }
            r7.allocationLimit = r2     // Catch:{ all -> 0x0101 }
            monitor-exit(r1)     // Catch:{ all -> 0x0101 }
            goto L_0x0105
        L_0x0101:
            r0 = move-exception
            r2 = r0
            monitor-exit(r1)     // Catch:{ all -> 0x0101 }
            throw r2
        L_0x0105:
            return
        L_0x0106:
            r0 = move-exception
            goto L_0x0111
        L_0x0108:
            r0 = move-exception
            goto L_0x010f
        L_0x010a:
            r0 = move-exception
            r13 = r18
            r14 = r19
        L_0x010f:
            r15 = r21
        L_0x0111:
            r6 = r0
            java.net.Socket r1 = r7.d
            okhttp3.internal.Util.closeQuietly(r1)
            java.net.Socket r1 = r7.c
            okhttp3.internal.Util.closeQuietly(r1)
            r7.d = r11
            r7.c = r11
            r7.h = r11
            r7.i = r11
            r7.e = r11
            r7.f = r11
            r7.g = r11
            okhttp3.Route r1 = r7.b
            java.net.InetSocketAddress r3 = r1.socketAddress()
            okhttp3.Route r1 = r7.b
            java.net.Proxy r4 = r1.proxy()
            r5 = 0
            r1 = r9
            r2 = r8
            r16 = r6
            r1.connectFailed(r2, r3, r4, r5, r6)
            if (r12 != 0) goto L_0x0149
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            r2 = r16
            r1.<init>(r2)
            r12 = r1
            goto L_0x014e
        L_0x0149:
            r2 = r16
            r12.addConnectException(r2)
        L_0x014e:
            if (r22 == 0) goto L_0x0156
            boolean r2 = r10.connectionFailed(r2)
            if (r2 != 0) goto L_0x009c
        L_0x0156:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealConnection.connect(int, int, int, int, boolean, okhttp3.Call, okhttp3.EventListener):void");
    }

    private void a(int i2, int i3, int i4, Call call, EventListener eventListener) {
        Request a2 = a();
        HttpUrl url = a2.url();
        int i5 = 0;
        while (i5 < 21) {
            a(i2, i3, call, eventListener);
            a2 = a(i3, i4, a2, url);
            if (a2 != null) {
                Util.closeQuietly(this.c);
                this.c = null;
                this.i = null;
                this.h = null;
                eventListener.connectEnd(call, this.b.socketAddress(), this.b.proxy(), null);
                i5++;
            } else {
                return;
            }
        }
    }

    private void a(int i2, int i3, Call call, EventListener eventListener) {
        Socket socket;
        Proxy proxy = this.b.proxy();
        Address address = this.b.address();
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.HTTP) {
            socket = address.socketFactory().createSocket();
        } else {
            socket = new Socket(proxy);
        }
        this.c = socket;
        eventListener.connectStart(call, this.b.socketAddress(), proxy);
        this.c.setSoTimeout(i3);
        try {
            Platform.get().connectSocket(this.c, this.b.socketAddress(), i2);
            try {
                this.h = Okio.buffer(Okio.source(this.c));
                this.i = Okio.buffer(Okio.sink(this.c));
            } catch (NullPointerException e2) {
                if ("throw with null exception".equals(e2.getMessage())) {
                    throw new IOException(e2);
                }
            }
        } catch (ConnectException e3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to connect to ");
            sb.append(this.b.socketAddress());
            ConnectException connectException = new ConnectException(sb.toString());
            connectException.initCause(e3);
            throw connectException;
        }
    }

    private void a(ConnectionSpecSelector connectionSpecSelector, int i2, Call call, EventListener eventListener) {
        if (this.b.address().sslSocketFactory() != null) {
            eventListener.secureConnectStart(call);
            a(connectionSpecSelector);
            eventListener.secureConnectEnd(call, this.e);
            if (this.f == Protocol.HTTP_2) {
                a(i2);
            }
        } else if (this.b.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.d = this.c;
            this.f = Protocol.H2_PRIOR_KNOWLEDGE;
            a(i2);
        } else {
            this.d = this.c;
            this.f = Protocol.HTTP_1_1;
        }
    }

    private void a(int i2) {
        this.d.setSoTimeout(0);
        this.g = new Builder(true).socket(this.d, this.b.address().url().host(), this.h, this.i).listener(this).pingIntervalMillis(i2).build();
        this.g.start();
    }

    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0111 A[Catch:{ all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0117 A[Catch:{ all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x011a  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(okhttp3.internal.connection.ConnectionSpecSelector r8) {
        /*
            r7 = this;
            okhttp3.Route r0 = r7.b
            okhttp3.Address r0 = r0.address()
            javax.net.ssl.SSLSocketFactory r1 = r0.sslSocketFactory()
            r2 = 0
            java.net.Socket r3 = r7.c     // Catch:{ AssertionError -> 0x010a }
            okhttp3.HttpUrl r4 = r0.url()     // Catch:{ AssertionError -> 0x010a }
            java.lang.String r4 = r4.host()     // Catch:{ AssertionError -> 0x010a }
            okhttp3.HttpUrl r5 = r0.url()     // Catch:{ AssertionError -> 0x010a }
            int r5 = r5.port()     // Catch:{ AssertionError -> 0x010a }
            r6 = 1
            java.net.Socket r1 = r1.createSocket(r3, r4, r5, r6)     // Catch:{ AssertionError -> 0x010a }
            javax.net.ssl.SSLSocket r1 = (javax.net.ssl.SSLSocket) r1     // Catch:{ AssertionError -> 0x010a }
            okhttp3.ConnectionSpec r8 = r8.configureSecureSocket(r1)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            boolean r3 = r8.supportsTlsExtensions()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            if (r3 == 0) goto L_0x0041
            okhttp3.internal.platform.Platform r3 = okhttp3.internal.platform.Platform.get()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okhttp3.HttpUrl r4 = r0.url()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r4 = r4.host()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.util.List r5 = r0.protocols()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.configureTlsExtensions(r1, r4, r5)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
        L_0x0041:
            r1.startHandshake()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            javax.net.ssl.SSLSession r3 = r1.getSession()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okhttp3.Handshake r4 = okhttp3.Handshake.get(r3)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            javax.net.ssl.HostnameVerifier r5 = r0.hostnameVerifier()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okhttp3.HttpUrl r6 = r0.url()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r6 = r6.host()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            boolean r3 = r5.verify(r6, r3)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            if (r3 != 0) goto L_0x00b0
            java.util.List r8 = r4.peerCertificates()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r2 = 0
            java.lang.Object r8 = r8.get(r2)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.security.cert.X509Certificate r8 = (java.security.cert.X509Certificate) r8     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            javax.net.ssl.SSLPeerUnverifiedException r2 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.<init>()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r4 = "Hostname "
            r3.append(r4)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okhttp3.HttpUrl r0 = r0.url()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = r0.host()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = " not verified:\n    certificate: "
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = okhttp3.CertificatePinner.pin(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = "\n    DN: "
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.security.Principal r0 = r8.getSubjectDN()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = r0.getName()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = "\n    subjectAltNames: "
            r3.append(r0)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.util.List r8 = okhttp3.internal.tls.OkHostnameVerifier.allSubjectAltNames(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.append(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r8 = r3.toString()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r2.<init>(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            throw r2     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
        L_0x00b0:
            okhttp3.CertificatePinner r3 = r0.certificatePinner()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okhttp3.HttpUrl r0 = r0.url()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r0 = r0.host()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.util.List r5 = r4.peerCertificates()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r3.check(r0, r5)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            boolean r8 = r8.supportsTlsExtensions()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            if (r8 == 0) goto L_0x00d1
            okhttp3.internal.platform.Platform r8 = okhttp3.internal.platform.Platform.get()     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.lang.String r2 = r8.getSelectedProtocol(r1)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
        L_0x00d1:
            r7.d = r1     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.net.Socket r8 = r7.d     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okio.Source r8 = okio.Okio.source(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okio.BufferedSource r8 = okio.Okio.buffer(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r7.h = r8     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            java.net.Socket r8 = r7.d     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okio.Sink r8 = okio.Okio.sink(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            okio.BufferedSink r8 = okio.Okio.buffer(r8)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r7.i = r8     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            r7.e = r4     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            if (r2 == 0) goto L_0x00f4
            okhttp3.Protocol r8 = okhttp3.Protocol.get(r2)     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            goto L_0x00f6
        L_0x00f4:
            okhttp3.Protocol r8 = okhttp3.Protocol.HTTP_1_1     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
        L_0x00f6:
            r7.f = r8     // Catch:{ AssertionError -> 0x0104, all -> 0x0102 }
            if (r1 == 0) goto L_0x0101
            okhttp3.internal.platform.Platform r8 = okhttp3.internal.platform.Platform.get()
            r8.afterHandshake(r1)
        L_0x0101:
            return
        L_0x0102:
            r8 = move-exception
            goto L_0x0118
        L_0x0104:
            r8 = move-exception
            r2 = r1
            goto L_0x010b
        L_0x0107:
            r8 = move-exception
            r1 = r2
            goto L_0x0118
        L_0x010a:
            r8 = move-exception
        L_0x010b:
            boolean r0 = okhttp3.internal.Util.isAndroidGetsocknameError(r8)     // Catch:{ all -> 0x0107 }
            if (r0 == 0) goto L_0x0117
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0107 }
            r0.<init>(r8)     // Catch:{ all -> 0x0107 }
            throw r0     // Catch:{ all -> 0x0107 }
        L_0x0117:
            throw r8     // Catch:{ all -> 0x0107 }
        L_0x0118:
            if (r1 == 0) goto L_0x0121
            okhttp3.internal.platform.Platform r0 = okhttp3.internal.platform.Platform.get()
            r0.afterHandshake(r1)
        L_0x0121:
            okhttp3.internal.Util.closeQuietly(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealConnection.a(okhttp3.internal.connection.ConnectionSpecSelector):void");
    }

    private Request a(int i2, int i3, Request request, HttpUrl httpUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append("CONNECT ");
        sb.append(Util.hostHeader(httpUrl, true));
        sb.append(" HTTP/1.1");
        String sb2 = sb.toString();
        while (true) {
            Http1Codec http1Codec = new Http1Codec(null, null, this.h, this.i);
            this.h.timeout().timeout((long) i2, TimeUnit.MILLISECONDS);
            this.i.timeout().timeout((long) i3, TimeUnit.MILLISECONDS);
            http1Codec.writeRequest(request.headers(), sb2);
            http1Codec.finishRequest();
            Response build = http1Codec.readResponseHeaders(false).request(request).build();
            long contentLength = HttpHeaders.contentLength(build);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source newFixedLengthSource = http1Codec.newFixedLengthSource(contentLength);
            Util.skipAll(newFixedLengthSource, SubsamplingScaleImageView.TILE_SIZE_AUTO, TimeUnit.MILLISECONDS);
            newFixedLengthSource.close();
            int code = build.code();
            if (code != 200) {
                if (code != 407) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Unexpected response code for CONNECT: ");
                    sb3.append(build.code());
                    throw new IOException(sb3.toString());
                }
                Request authenticate = this.b.address().proxyAuthenticator().authenticate(this.b, build);
                if (authenticate == null) {
                    throw new IOException("Failed to authenticate with proxy");
                } else if ("close".equalsIgnoreCase(build.header("Connection"))) {
                    return authenticate;
                } else {
                    request = authenticate;
                }
            } else if (this.h.buffer().exhausted() && this.i.buffer().exhausted()) {
                return null;
            } else {
                throw new IOException("TLS tunnel buffered too many bytes!");
            }
        }
    }

    private Request a() {
        return new Request.Builder().url(this.b.address().url()).header("Host", Util.hostHeader(this.b.address().url(), true)).header("Proxy-Connection", HTTP.CONN_KEEP_ALIVE).header("User-Agent", Version.userAgent()).build();
    }

    public boolean isEligible(Address address, @Nullable Route route) {
        if (this.allocations.size() >= this.allocationLimit || this.noNewStreams || !Internal.instance.equalsNonHost(this.b.address(), address)) {
            return false;
        }
        if (address.url().host().equals(route().address().url().host())) {
            return true;
        }
        if (this.g == null || route == null || route.proxy().type() != Type.DIRECT || this.b.proxy().type() != Type.DIRECT || !this.b.socketAddress().equals(route.socketAddress()) || route.address().hostnameVerifier() != OkHostnameVerifier.INSTANCE || !supportsUrl(address.url())) {
            return false;
        }
        try {
            address.certificatePinner().check(address.url().host(), handshake().peerCertificates());
            return true;
        } catch (SSLPeerUnverifiedException unused) {
            return false;
        }
    }

    public boolean supportsUrl(HttpUrl httpUrl) {
        if (httpUrl.port() != this.b.address().url().port()) {
            return false;
        }
        boolean z = true;
        if (httpUrl.host().equals(this.b.address().url().host())) {
            return true;
        }
        if (this.e == null || !OkHostnameVerifier.INSTANCE.verify(httpUrl.host(), (X509Certificate) this.e.peerCertificates().get(0))) {
            z = false;
        }
        return z;
    }

    public HttpCodec newCodec(OkHttpClient okHttpClient, Chain chain, StreamAllocation streamAllocation) {
        if (this.g != null) {
            return new Http2Codec(okHttpClient, chain, streamAllocation, this.g);
        }
        this.d.setSoTimeout(chain.readTimeoutMillis());
        this.h.timeout().timeout((long) chain.readTimeoutMillis(), TimeUnit.MILLISECONDS);
        this.i.timeout().timeout((long) chain.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        return new Http1Codec(okHttpClient, streamAllocation, this.h, this.i);
    }

    public Streams newWebSocketStreams(StreamAllocation streamAllocation) {
        final StreamAllocation streamAllocation2 = streamAllocation;
        AnonymousClass1 r0 = new Streams(true, this.h, this.i) {
            public void close() {
                streamAllocation2.streamFinished(true, streamAllocation2.codec(), -1, null);
            }
        };
        return r0;
    }

    public Route route() {
        return this.b;
    }

    public void cancel() {
        Util.closeQuietly(this.c);
    }

    public Socket socket() {
        return this.d;
    }

    public boolean isHealthy(boolean z) {
        int soTimeout;
        if (this.d.isClosed() || this.d.isInputShutdown() || this.d.isOutputShutdown()) {
            return false;
        }
        if (this.g != null) {
            return !this.g.isShutdown();
        }
        if (z) {
            try {
                soTimeout = this.d.getSoTimeout();
                this.d.setSoTimeout(1);
                if (this.h.exhausted()) {
                    this.d.setSoTimeout(soTimeout);
                    return false;
                }
                this.d.setSoTimeout(soTimeout);
                return true;
            } catch (SocketTimeoutException unused) {
            } catch (IOException unused2) {
                return false;
            } catch (Throwable th) {
                this.d.setSoTimeout(soTimeout);
                throw th;
            }
        }
        return true;
    }

    public void onStream(Http2Stream http2Stream) {
        http2Stream.close(ErrorCode.REFUSED_STREAM);
    }

    public void onSettings(Http2Connection http2Connection) {
        synchronized (this.a) {
            this.allocationLimit = http2Connection.maxConcurrentStreams();
        }
    }

    public Handshake handshake() {
        return this.e;
    }

    public boolean isMultiplexed() {
        return this.g != null;
    }

    public Protocol protocol() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.b.address().url().host());
        sb.append(":");
        sb.append(this.b.address().url().port());
        sb.append(", proxy=");
        sb.append(this.b.proxy());
        sb.append(" hostAddress=");
        sb.append(this.b.socketAddress());
        sb.append(" cipherSuite=");
        sb.append(this.e != null ? this.e.cipherSuite() : SchedulerSupport.NONE);
        sb.append(" protocol=");
        sb.append(this.f);
        sb.append('}');
        return sb.toString();
    }
}
