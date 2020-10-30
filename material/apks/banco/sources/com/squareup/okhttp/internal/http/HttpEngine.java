package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import com.squareup.okhttp.internal.http.CacheStrategy.Factory;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.protocol.HTTP;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine {
    public static final int MAX_FOLLOW_UPS = 20;
    private static final ResponseBody c = new ResponseBody() {
        public long contentLength() {
            return 0;
        }

        public MediaType contentType() {
            return null;
        }

        public BufferedSource source() {
            return new Buffer();
        }
    };
    final OkHttpClient a;
    long b = -1;
    public final boolean bufferRequestBody;
    /* access modifiers changed from: private */
    public Connection d;
    private Address e;
    private RouteSelector f;
    private Route g;
    private final Response h;
    /* access modifiers changed from: private */
    public Transport i;
    private boolean j;
    private final Request k;
    /* access modifiers changed from: private */
    public Request l;
    private Response m;
    private Response n;
    private Sink o;
    private BufferedSink p;
    private final boolean q;
    private final boolean r;
    private CacheRequest s;
    private CacheStrategy t;

    class NetworkInterceptorChain implements Chain {
        private final int b;
        private final Request c;
        private int d;

        NetworkInterceptorChain(int i, Request request) {
            this.b = i;
            this.c = request;
        }

        public Connection connection() {
            return HttpEngine.this.d;
        }

        public Request request() {
            return this.c;
        }

        public Response proceed(Request request) {
            this.d++;
            if (this.b > 0) {
                Interceptor interceptor = (Interceptor) HttpEngine.this.a.networkInterceptors().get(this.b - 1);
                Address address = connection().getRoute().getAddress();
                if (!request.url().getHost().equals(address.getUriHost()) || Util.getEffectivePort(request.url()) != address.getUriPort()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("network interceptor ");
                    sb.append(interceptor);
                    sb.append(" must retain the same host and port");
                    throw new IllegalStateException(sb.toString());
                } else if (this.d > 1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("network interceptor ");
                    sb2.append(interceptor);
                    sb2.append(" must call proceed() exactly once");
                    throw new IllegalStateException(sb2.toString());
                }
            }
            if (this.b < HttpEngine.this.a.networkInterceptors().size()) {
                NetworkInterceptorChain networkInterceptorChain = new NetworkInterceptorChain(this.b + 1, request);
                Interceptor interceptor2 = (Interceptor) HttpEngine.this.a.networkInterceptors().get(this.b);
                Response intercept = interceptor2.intercept(networkInterceptorChain);
                if (networkInterceptorChain.d == 1) {
                    return intercept;
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append("network interceptor ");
                sb3.append(interceptor2);
                sb3.append(" must call proceed() exactly once");
                throw new IllegalStateException(sb3.toString());
            }
            HttpEngine.this.i.writeRequestHeaders(request);
            HttpEngine.this.l = request;
            if (HttpEngine.this.a() && request.body() != null) {
                BufferedSink buffer = Okio.buffer(HttpEngine.this.i.createRequestBody(request, request.body().contentLength()));
                request.body().writeTo(buffer);
                buffer.close();
            }
            Response c2 = HttpEngine.this.f();
            int code = c2.code();
            if ((code != 204 && code != 205) || c2.body().contentLength() <= 0) {
                return c2;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("HTTP ");
            sb4.append(code);
            sb4.append(" had non-zero Content-Length: ");
            sb4.append(c2.body().contentLength());
            throw new ProtocolException(sb4.toString());
        }
    }

    public HttpEngine(OkHttpClient okHttpClient, Request request, boolean z, boolean z2, boolean z3, Connection connection, RouteSelector routeSelector, RetryableSink retryableSink, Response response) {
        this.a = okHttpClient;
        this.k = request;
        this.bufferRequestBody = z;
        this.q = z2;
        this.r = z3;
        this.d = connection;
        this.f = routeSelector;
        this.o = retryableSink;
        this.h = response;
        if (connection != null) {
            Internal.instance.setOwner(connection, this);
            this.g = connection.getRoute();
            return;
        }
        this.g = null;
    }

    public void sendRequest() {
        if (this.t == null) {
            if (this.i != null) {
                throw new IllegalStateException();
            }
            Request a2 = a(this.k);
            InternalCache internalCache = Internal.instance.internalCache(this.a);
            Response response = internalCache != null ? internalCache.get(a2) : null;
            this.t = new Factory(System.currentTimeMillis(), a2, response).get();
            this.l = this.t.networkRequest;
            this.m = this.t.cacheResponse;
            if (internalCache != null) {
                internalCache.trackResponse(this.t);
            }
            if (response != null && this.m == null) {
                Util.closeQuietly((Closeable) response.body());
            }
            if (this.l != null) {
                if (this.d == null) {
                    b();
                }
                this.i = Internal.instance.newTransport(this.d, this);
                if (this.q && a() && this.o == null) {
                    long contentLength = OkHeaders.contentLength(a2);
                    if (!this.bufferRequestBody) {
                        this.i.writeRequestHeaders(this.l);
                        this.o = this.i.createRequestBody(this.l, contentLength);
                    } else if (contentLength > 2147483647L) {
                        throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                    } else if (contentLength != -1) {
                        this.i.writeRequestHeaders(this.l);
                        this.o = new RetryableSink((int) contentLength);
                    } else {
                        this.o = new RetryableSink();
                    }
                }
            } else {
                if (this.d != null) {
                    Internal.instance.recycle(this.a.getConnectionPool(), this.d);
                    this.d = null;
                }
                if (this.m != null) {
                    this.n = this.m.newBuilder().request(this.k).priorResponse(a(this.h)).cacheResponse(a(this.m)).build();
                } else {
                    this.n = new Builder().request(this.k).priorResponse(a(this.h)).protocol(Protocol.HTTP_1_1).code(HttpStatus.SC_GATEWAY_TIMEOUT).message("Unsatisfiable Request (only-if-cached)").body(c).build();
                }
                this.n = b(this.n);
            }
        }
    }

    private static Response a(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private void b() {
        if (this.d != null) {
            throw new IllegalStateException();
        }
        if (this.f == null) {
            this.e = a(this.a, this.l);
            try {
                this.f = RouteSelector.get(this.e, this.l, this.a);
            } catch (IOException e2) {
                throw new RequestException(e2);
            }
        }
        this.d = c();
        this.g = this.d.getRoute();
    }

    private Connection c() {
        Connection d2 = d();
        Internal.instance.connectAndSetOwner(this.a, d2, this, this.l);
        return d2;
    }

    private Connection d() {
        Connection connection;
        ConnectionPool connectionPool = this.a.getConnectionPool();
        while (true) {
            connection = connectionPool.get(this.e);
            if (connection == null) {
                try {
                    return new Connection(connectionPool, this.f.next());
                } catch (IOException e2) {
                    throw new RouteException(e2);
                }
            } else if (this.l.method().equals("GET") || Internal.instance.isReadable(connection)) {
                return connection;
            } else {
                Util.closeQuietly(connection.getSocket());
            }
        }
        return connection;
    }

    public void writingRequestHeaders() {
        if (this.b != -1) {
            throw new IllegalStateException();
        }
        this.b = System.currentTimeMillis();
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return HttpMethod.permitsRequestBody(this.k.method());
    }

    public Sink getRequestBody() {
        if (this.t != null) {
            return this.o;
        }
        throw new IllegalStateException();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink bufferedSink;
        BufferedSink bufferedSink2 = this.p;
        if (bufferedSink2 != null) {
            return bufferedSink2;
        }
        Sink requestBody = getRequestBody();
        if (requestBody != null) {
            bufferedSink = Okio.buffer(requestBody);
            this.p = bufferedSink;
        } else {
            bufferedSink = null;
        }
        return bufferedSink;
    }

    public boolean hasResponse() {
        return this.n != null;
    }

    public Request getRequest() {
        return this.k;
    }

    public Response getResponse() {
        if (this.n != null) {
            return this.n;
        }
        throw new IllegalStateException();
    }

    public Connection getConnection() {
        return this.d;
    }

    public HttpEngine recover(RouteException routeException) {
        if (!(this.f == null || this.d == null)) {
            a(this.f, routeException.getLastConnectException());
        }
        if ((this.f == null && this.d == null) || ((this.f != null && !this.f.hasNext()) || !a(routeException))) {
            return null;
        }
        HttpEngine httpEngine = new HttpEngine(this.a, this.k, this.bufferRequestBody, this.q, this.r, close(), this.f, (RetryableSink) this.o, this.h);
        return httpEngine;
    }

    private boolean a(RouteException routeException) {
        if (!this.a.getRetryOnConnectionFailure()) {
            return false;
        }
        IOException lastConnectException = routeException.getLastConnectException();
        if ((lastConnectException instanceof ProtocolException) || (lastConnectException instanceof InterruptedIOException)) {
            return false;
        }
        if ((!(lastConnectException instanceof SSLHandshakeException) || !(lastConnectException.getCause() instanceof CertificateException)) && !(lastConnectException instanceof SSLPeerUnverifiedException)) {
            return true;
        }
        return false;
    }

    public HttpEngine recover(IOException iOException, Sink sink) {
        if (!(this.f == null || this.d == null)) {
            a(this.f, iOException);
        }
        boolean z = sink == null || (sink instanceof RetryableSink);
        if ((this.f == null && this.d == null) || ((this.f != null && !this.f.hasNext()) || !a(iOException) || !z)) {
            return null;
        }
        HttpEngine httpEngine = new HttpEngine(this.a, this.k, this.bufferRequestBody, this.q, this.r, close(), this.f, (RetryableSink) sink, this.h);
        return httpEngine;
    }

    private void a(RouteSelector routeSelector, IOException iOException) {
        if (Internal.instance.recycleCount(this.d) <= 0) {
            routeSelector.connectFailed(this.d.getRoute(), iOException);
        }
    }

    public HttpEngine recover(IOException iOException) {
        return recover(iOException, this.o);
    }

    private boolean a(IOException iOException) {
        if (this.a.getRetryOnConnectionFailure() && !(iOException instanceof ProtocolException) && !(iOException instanceof InterruptedIOException)) {
            return true;
        }
        return false;
    }

    public Route getRoute() {
        return this.g;
    }

    private void e() {
        InternalCache internalCache = Internal.instance.internalCache(this.a);
        if (internalCache != null) {
            if (!CacheStrategy.isCacheable(this.n, this.l)) {
                if (HttpMethod.invalidatesCache(this.l.method())) {
                    try {
                        internalCache.remove(this.l);
                    } catch (IOException unused) {
                    }
                }
                return;
            }
            this.s = internalCache.put(a(this.n));
        }
    }

    public void releaseConnection() {
        if (!(this.i == null || this.d == null)) {
            this.i.releaseConnectionOnIdle();
        }
        this.d = null;
    }

    public void disconnect() {
        if (this.i != null) {
            try {
                this.i.disconnect(this);
            } catch (IOException unused) {
            }
        }
    }

    public Connection close() {
        if (this.p != null) {
            Util.closeQuietly((Closeable) this.p);
        } else if (this.o != null) {
            Util.closeQuietly((Closeable) this.o);
        }
        if (this.n == null) {
            if (this.d != null) {
                Util.closeQuietly(this.d.getSocket());
            }
            this.d = null;
            return null;
        }
        Util.closeQuietly((Closeable) this.n.body());
        if (this.i == null || this.d == null || this.i.canReuseConnection()) {
            if (this.d != null && !Internal.instance.clearOwner(this.d)) {
                this.d = null;
            }
            Connection connection = this.d;
            this.d = null;
            return connection;
        }
        Util.closeQuietly(this.d.getSocket());
        this.d = null;
        return null;
    }

    private Response b(Response response) {
        if (!this.j || !HttpRequest.ENCODING_GZIP.equalsIgnoreCase(this.n.header("Content-Encoding")) || response.body() == null) {
            return response;
        }
        GzipSource gzipSource = new GzipSource(response.body().source());
        Headers build = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
        return response.newBuilder().headers(build).body(new RealResponseBody(build, Okio.buffer((Source) gzipSource))).build();
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int code = response.code();
        if (((code >= 100 && code < 200) || code == 204 || code == 304) && OkHeaders.contentLength(response) == -1 && !HTTP.CHUNK_CODING.equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return false;
        }
        return true;
    }

    private Request a(Request request) {
        Request.Builder newBuilder = request.newBuilder();
        if (request.header("Host") == null) {
            newBuilder.header("Host", hostHeader(request.url()));
        }
        if ((this.d == null || this.d.getProtocol() != Protocol.HTTP_1_0) && request.header("Connection") == null) {
            newBuilder.header("Connection", HTTP.CONN_KEEP_ALIVE);
        }
        if (request.header("Accept-Encoding") == null) {
            this.j = true;
            newBuilder.header("Accept-Encoding", HttpRequest.ENCODING_GZIP);
        }
        CookieHandler cookieHandler = this.a.getCookieHandler();
        if (cookieHandler != null) {
            OkHeaders.addCookies(newBuilder, cookieHandler.get(request.uri(), OkHeaders.toMultimap(newBuilder.build().headers(), null)));
        }
        if (request.header("User-Agent") == null) {
            newBuilder.header("User-Agent", Version.userAgent());
        }
        return newBuilder.build();
    }

    public static String hostHeader(URL url) {
        if (Util.getEffectivePort(url) == Util.getDefaultPort(url.getProtocol())) {
            return url.getHost();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url.getHost());
        sb.append(":");
        sb.append(url.getPort());
        return sb.toString();
    }

    public void readResponse() {
        Response response;
        if (this.n == null) {
            if (this.l == null && this.m == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (this.l != null) {
                if (this.r) {
                    this.i.writeRequestHeaders(this.l);
                    response = f();
                } else if (!this.q) {
                    response = new NetworkInterceptorChain(0, this.l).proceed(this.l);
                } else {
                    if (this.p != null && this.p.buffer().size() > 0) {
                        this.p.emit();
                    }
                    if (this.b == -1) {
                        if (OkHeaders.contentLength(this.l) == -1 && (this.o instanceof RetryableSink)) {
                            this.l = this.l.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.o).contentLength())).build();
                        }
                        this.i.writeRequestHeaders(this.l);
                    }
                    if (this.o != null) {
                        if (this.p != null) {
                            this.p.close();
                        } else {
                            this.o.close();
                        }
                        if (this.o instanceof RetryableSink) {
                            this.i.writeRequestBody((RetryableSink) this.o);
                        }
                    }
                    response = f();
                }
                receiveHeaders(response.headers());
                if (this.m != null) {
                    if (a(this.m, response)) {
                        this.n = this.m.newBuilder().request(this.k).priorResponse(a(this.h)).headers(a(this.m.headers(), response.headers())).cacheResponse(a(this.m)).networkResponse(a(response)).build();
                        response.body().close();
                        releaseConnection();
                        InternalCache internalCache = Internal.instance.internalCache(this.a);
                        internalCache.trackConditionalCacheHit();
                        internalCache.update(this.m, a(this.n));
                        this.n = b(this.n);
                        return;
                    }
                    Util.closeQuietly((Closeable) this.m.body());
                }
                this.n = response.newBuilder().request(this.k).priorResponse(a(this.h)).cacheResponse(a(this.m)).networkResponse(a(response)).build();
                if (hasBody(this.n)) {
                    e();
                    this.n = b(a(this.s, this.n));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public Response f() {
        this.i.finishRequest();
        Response build = this.i.readResponseHeaders().request(this.l).handshake(this.d.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.b)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.r) {
            build = build.newBuilder().body(this.i.openResponseBody(build)).build();
        }
        Internal.instance.setProtocol(this.d, build.protocol());
        return build;
    }

    private Response a(final CacheRequest cacheRequest, Response response) {
        if (cacheRequest == null) {
            return response;
        }
        Sink body = cacheRequest.body();
        if (body == null) {
            return response;
        }
        final BufferedSource source = response.body().source();
        final BufferedSink buffer = Okio.buffer(body);
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer((Source) new Source() {
            boolean a;

            public long read(Buffer buffer, long j) {
                try {
                    long read = source.read(buffer, j);
                    if (read == -1) {
                        if (!this.a) {
                            this.a = true;
                            buffer.close();
                        }
                        return -1;
                    }
                    buffer.copyTo(buffer.buffer(), buffer.size() - read, read);
                    buffer.emitCompleteSegments();
                    return read;
                } catch (IOException e2) {
                    if (!this.a) {
                        this.a = true;
                        cacheRequest.abort();
                    }
                    throw e2;
                }
            }

            public Timeout timeout() {
                return source.timeout();
            }

            public void close() {
                if (!this.a && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.a = true;
                    cacheRequest.abort();
                }
                source.close();
            }
        }))).build();
    }

    private static boolean a(Response response, Response response2) {
        if (response2.code() == 304) {
            return true;
        }
        Date date = response.headers().getDate("Last-Modified");
        if (date != null) {
            Date date2 = response2.headers().getDate("Last-Modified");
            if (date2 != null && date2.getTime() < date.getTime()) {
                return true;
            }
        }
        return false;
    }

    private static Headers a(Headers headers, Headers headers2) {
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            String name = headers.name(i2);
            String value = headers.value(i2);
            if ((!"Warning".equalsIgnoreCase(name) || !value.startsWith("1")) && (!OkHeaders.a(name) || headers2.get(name) == null)) {
                builder.add(name, value);
            }
        }
        int size2 = headers2.size();
        for (int i3 = 0; i3 < size2; i3++) {
            String name2 = headers2.name(i3);
            if (!"Content-Length".equalsIgnoreCase(name2) && OkHeaders.a(name2)) {
                builder.add(name2, headers2.value(i3));
            }
        }
        return builder.build();
    }

    public void receiveHeaders(Headers headers) {
        CookieHandler cookieHandler = this.a.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.k.uri(), OkHeaders.toMultimap(headers, null));
        }
    }

    public Request followUpRequest() {
        Proxy proxy;
        if (this.n == null) {
            throw new IllegalStateException();
        }
        if (getRoute() != null) {
            proxy = getRoute().getProxy();
        } else {
            proxy = this.a.getProxy();
        }
        int code = this.n.code();
        if (code != 401) {
            if (code != 407) {
                switch (code) {
                    case HttpStatus.SC_MULTIPLE_CHOICES /*300*/:
                    case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
                    case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
                    case HttpStatus.SC_SEE_OTHER /*303*/:
                        break;
                    default:
                        switch (code) {
                            case 307:
                            case 308:
                                if (!this.k.method().equals("GET") && !this.k.method().equals("HEAD")) {
                                    return null;
                                }
                            default:
                                return null;
                        }
                }
                if (!this.a.getFollowRedirects()) {
                    return null;
                }
                String header = this.n.header("Location");
                if (header == null) {
                    return null;
                }
                URL url = new URL(this.k.url(), header);
                if (!url.getProtocol().equals("https") && !url.getProtocol().equals(HttpHost.DEFAULT_SCHEME_NAME)) {
                    return null;
                }
                if (!url.getProtocol().equals(this.k.url().getProtocol()) && !this.a.getFollowSslRedirects()) {
                    return null;
                }
                Request.Builder newBuilder = this.k.newBuilder();
                if (HttpMethod.permitsRequestBody(this.k.method())) {
                    newBuilder.method("GET", null);
                    newBuilder.removeHeader("Transfer-Encoding");
                    newBuilder.removeHeader("Content-Length");
                    newBuilder.removeHeader("Content-Type");
                }
                if (!sameConnection(url)) {
                    newBuilder.removeHeader("Authorization");
                }
                return newBuilder.url(url).build();
            } else if (proxy.type() != Type.HTTP) {
                throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
            }
        }
        return OkHeaders.processAuthHeader(this.a.getAuthenticator(), this.n, proxy);
    }

    public boolean sameConnection(URL url) {
        URL url2 = this.k.url();
        return url2.getHost().equals(url.getHost()) && Util.getEffectivePort(url2) == Util.getEffectivePort(url) && url2.getProtocol().equals(url.getProtocol());
    }

    private static Address a(OkHttpClient okHttpClient, Request request) {
        CertificatePinner certificatePinner;
        HostnameVerifier hostnameVerifier;
        SSLSocketFactory sSLSocketFactory;
        String host = request.url().getHost();
        if (host == null || host.length() == 0) {
            throw new RequestException(new UnknownHostException(request.url().toString()));
        }
        if (request.isHttps()) {
            SSLSocketFactory sslSocketFactory = okHttpClient.getSslSocketFactory();
            sSLSocketFactory = sslSocketFactory;
            hostnameVerifier = okHttpClient.getHostnameVerifier();
            certificatePinner = okHttpClient.getCertificatePinner();
        } else {
            sSLSocketFactory = null;
            hostnameVerifier = null;
            certificatePinner = null;
        }
        Address address = new Address(host, Util.getEffectivePort(request.url()), okHttpClient.getSocketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, okHttpClient.getAuthenticator(), okHttpClient.getProxy(), okHttpClient.getProtocols(), okHttpClient.getConnectionSpecs(), okHttpClient.getProxySelector());
        return address;
    }
}
