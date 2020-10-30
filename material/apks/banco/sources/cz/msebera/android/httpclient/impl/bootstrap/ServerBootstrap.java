package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ConnectionReuseStrategy;
import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.config.ConnectionConfig;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.impl.DefaultBHttpServerConnection;
import cz.msebera.android.httpclient.protocol.HttpExpectationVerifier;
import cz.msebera.android.httpclient.protocol.HttpProcessor;
import cz.msebera.android.httpclient.protocol.HttpRequestHandler;
import cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;

public class ServerBootstrap {
    private int a;
    private InetAddress b;
    private SocketConfig c;
    private ConnectionConfig d;
    private LinkedList<HttpRequestInterceptor> e;
    private LinkedList<HttpRequestInterceptor> f;
    private LinkedList<HttpResponseInterceptor> g;
    private LinkedList<HttpResponseInterceptor> h;
    private String i;
    private HttpProcessor j;
    private ConnectionReuseStrategy k;
    private HttpResponseFactory l;
    private HttpRequestHandlerMapper m;
    private Map<String, HttpRequestHandler> n;
    private HttpExpectationVerifier o;
    private ServerSocketFactory p;
    private SSLContext q;
    private SSLServerSetupHandler r;
    private HttpConnectionFactory<? extends DefaultBHttpServerConnection> s;
    private ExceptionLogger t;

    private ServerBootstrap() {
    }

    public static ServerBootstrap bootstrap() {
        return new ServerBootstrap();
    }

    public final ServerBootstrap setListenerPort(int i2) {
        this.a = i2;
        return this;
    }

    public final ServerBootstrap setLocalAddress(InetAddress inetAddress) {
        this.b = inetAddress;
        return this;
    }

    public final ServerBootstrap setSocketConfig(SocketConfig socketConfig) {
        this.c = socketConfig;
        return this;
    }

    public final ServerBootstrap setConnectionConfig(ConnectionConfig connectionConfig) {
        this.d = connectionConfig;
        return this;
    }

    public final ServerBootstrap setHttpProcessor(HttpProcessor httpProcessor) {
        this.j = httpProcessor;
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.g == null) {
            this.g = new LinkedList<>();
        }
        this.g.addFirst(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.h == null) {
            this.h = new LinkedList<>();
        }
        this.h.addLast(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.e == null) {
            this.e = new LinkedList<>();
        }
        this.e.addFirst(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.f == null) {
            this.f = new LinkedList<>();
        }
        this.f.addLast(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap setServerInfo(String str) {
        this.i = str;
        return this;
    }

    public final ServerBootstrap setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.k = connectionReuseStrategy;
        return this;
    }

    public final ServerBootstrap setResponseFactory(HttpResponseFactory httpResponseFactory) {
        this.l = httpResponseFactory;
        return this;
    }

    public final ServerBootstrap setHandlerMapper(HttpRequestHandlerMapper httpRequestHandlerMapper) {
        this.m = httpRequestHandlerMapper;
        return this;
    }

    public final ServerBootstrap registerHandler(String str, HttpRequestHandler httpRequestHandler) {
        if (str == null || httpRequestHandler == null) {
            return this;
        }
        if (this.n == null) {
            this.n = new HashMap();
        }
        this.n.put(str, httpRequestHandler);
        return this;
    }

    public final ServerBootstrap setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.o = httpExpectationVerifier;
        return this;
    }

    public final ServerBootstrap setConnectionFactory(HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory) {
        this.s = httpConnectionFactory;
        return this;
    }

    public final ServerBootstrap setSslSetupHandler(SSLServerSetupHandler sSLServerSetupHandler) {
        this.r = sSLServerSetupHandler;
        return this;
    }

    public final ServerBootstrap setServerSocketFactory(ServerSocketFactory serverSocketFactory) {
        this.p = serverSocketFactory;
        return this;
    }

    public final ServerBootstrap setSslContext(SSLContext sSLContext) {
        this.q = sSLContext;
        return this;
    }

    public final ServerBootstrap setExceptionLogger(ExceptionLogger exceptionLogger) {
        this.t = exceptionLogger;
        return this;
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r7v0, types: [cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper] */
    /* JADX WARNING: type inference failed for: r1v25, types: [cz.msebera.android.httpclient.protocol.UriHttpRequestHandlerMapper] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2, types: [cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper]
      assigns: [cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper, cz.msebera.android.httpclient.protocol.UriHttpRequestHandlerMapper]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], ?[OBJECT, ARRAY], cz.msebera.android.httpclient.protocol.UriHttpRequestHandlerMapper]
      mth insns count: 118
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.util.ArrayList.forEach(ArrayList.java:1249)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public cz.msebera.android.httpclient.impl.bootstrap.HttpServer create() {
        /*
            r17 = this;
            r0 = r17
            cz.msebera.android.httpclient.protocol.HttpProcessor r1 = r0.j
            r2 = 0
            if (r1 != 0) goto L_0x00a2
            cz.msebera.android.httpclient.protocol.HttpProcessorBuilder r1 = cz.msebera.android.httpclient.protocol.HttpProcessorBuilder.create()
            java.util.LinkedList<cz.msebera.android.httpclient.HttpRequestInterceptor> r3 = r0.e
            if (r3 == 0) goto L_0x0025
            java.util.LinkedList<cz.msebera.android.httpclient.HttpRequestInterceptor> r3 = r0.e
            java.util.Iterator r3 = r3.iterator()
        L_0x0015:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0025
            java.lang.Object r4 = r3.next()
            cz.msebera.android.httpclient.HttpRequestInterceptor r4 = (cz.msebera.android.httpclient.HttpRequestInterceptor) r4
            r1.addFirst(r4)
            goto L_0x0015
        L_0x0025:
            java.util.LinkedList<cz.msebera.android.httpclient.HttpResponseInterceptor> r3 = r0.g
            if (r3 == 0) goto L_0x003f
            java.util.LinkedList<cz.msebera.android.httpclient.HttpResponseInterceptor> r3 = r0.g
            java.util.Iterator r3 = r3.iterator()
        L_0x002f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003f
            java.lang.Object r4 = r3.next()
            cz.msebera.android.httpclient.HttpResponseInterceptor r4 = (cz.msebera.android.httpclient.HttpResponseInterceptor) r4
            r1.addFirst(r4)
            goto L_0x002f
        L_0x003f:
            java.lang.String r3 = r0.i
            if (r3 != 0) goto L_0x0045
            java.lang.String r3 = "Apache-HttpCore/1.1"
        L_0x0045:
            r4 = 4
            cz.msebera.android.httpclient.HttpResponseInterceptor[] r4 = new cz.msebera.android.httpclient.HttpResponseInterceptor[r4]
            cz.msebera.android.httpclient.protocol.ResponseDate r5 = new cz.msebera.android.httpclient.protocol.ResponseDate
            r5.<init>()
            r4[r2] = r5
            r5 = 1
            cz.msebera.android.httpclient.protocol.ResponseServer r6 = new cz.msebera.android.httpclient.protocol.ResponseServer
            r6.<init>(r3)
            r4[r5] = r6
            r3 = 2
            cz.msebera.android.httpclient.protocol.ResponseContent r5 = new cz.msebera.android.httpclient.protocol.ResponseContent
            r5.<init>()
            r4[r3] = r5
            r3 = 3
            cz.msebera.android.httpclient.protocol.ResponseConnControl r5 = new cz.msebera.android.httpclient.protocol.ResponseConnControl
            r5.<init>()
            r4[r3] = r5
            r1.addAll(r4)
            java.util.LinkedList<cz.msebera.android.httpclient.HttpRequestInterceptor> r3 = r0.f
            if (r3 == 0) goto L_0x0084
            java.util.LinkedList<cz.msebera.android.httpclient.HttpRequestInterceptor> r3 = r0.f
            java.util.Iterator r3 = r3.iterator()
        L_0x0074:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0084
            java.lang.Object r4 = r3.next()
            cz.msebera.android.httpclient.HttpRequestInterceptor r4 = (cz.msebera.android.httpclient.HttpRequestInterceptor) r4
            r1.addLast(r4)
            goto L_0x0074
        L_0x0084:
            java.util.LinkedList<cz.msebera.android.httpclient.HttpResponseInterceptor> r3 = r0.h
            if (r3 == 0) goto L_0x009e
            java.util.LinkedList<cz.msebera.android.httpclient.HttpResponseInterceptor> r3 = r0.h
            java.util.Iterator r3 = r3.iterator()
        L_0x008e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x009e
            java.lang.Object r4 = r3.next()
            cz.msebera.android.httpclient.HttpResponseInterceptor r4 = (cz.msebera.android.httpclient.HttpResponseInterceptor) r4
            r1.addLast(r4)
            goto L_0x008e
        L_0x009e:
            cz.msebera.android.httpclient.protocol.HttpProcessor r1 = r1.build()
        L_0x00a2:
            r4 = r1
            cz.msebera.android.httpclient.protocol.HttpRequestHandlerMapper r1 = r0.m
            if (r1 != 0) goto L_0x00d6
            cz.msebera.android.httpclient.protocol.UriHttpRequestHandlerMapper r1 = new cz.msebera.android.httpclient.protocol.UriHttpRequestHandlerMapper
            r1.<init>()
            java.util.Map<java.lang.String, cz.msebera.android.httpclient.protocol.HttpRequestHandler> r3 = r0.n
            if (r3 == 0) goto L_0x00d6
            java.util.Map<java.lang.String, cz.msebera.android.httpclient.protocol.HttpRequestHandler> r3 = r0.n
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x00ba:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00d6
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getKey()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r5 = r5.getValue()
            cz.msebera.android.httpclient.protocol.HttpRequestHandler r5 = (cz.msebera.android.httpclient.protocol.HttpRequestHandler) r5
            r1.register(r6, r5)
            goto L_0x00ba
        L_0x00d6:
            r7 = r1
            cz.msebera.android.httpclient.ConnectionReuseStrategy r1 = r0.k
            if (r1 != 0) goto L_0x00dd
            cz.msebera.android.httpclient.impl.DefaultConnectionReuseStrategy r1 = cz.msebera.android.httpclient.impl.DefaultConnectionReuseStrategy.INSTANCE
        L_0x00dd:
            r5 = r1
            cz.msebera.android.httpclient.HttpResponseFactory r1 = r0.l
            if (r1 != 0) goto L_0x00e4
            cz.msebera.android.httpclient.impl.DefaultHttpResponseFactory r1 = cz.msebera.android.httpclient.impl.DefaultHttpResponseFactory.INSTANCE
        L_0x00e4:
            r6 = r1
            cz.msebera.android.httpclient.protocol.HttpService r13 = new cz.msebera.android.httpclient.protocol.HttpService
            cz.msebera.android.httpclient.protocol.HttpExpectationVerifier r8 = r0.o
            r3 = r13
            r3.<init>(r4, r5, r6, r7, r8)
            javax.net.ServerSocketFactory r1 = r0.p
            if (r1 != 0) goto L_0x0100
            javax.net.ssl.SSLContext r1 = r0.q
            if (r1 == 0) goto L_0x00fc
            javax.net.ssl.SSLContext r1 = r0.q
            javax.net.ssl.SSLServerSocketFactory r1 = r1.getServerSocketFactory()
            goto L_0x0100
        L_0x00fc:
            javax.net.ServerSocketFactory r1 = javax.net.ServerSocketFactory.getDefault()
        L_0x0100:
            r12 = r1
            cz.msebera.android.httpclient.HttpConnectionFactory<? extends cz.msebera.android.httpclient.impl.DefaultBHttpServerConnection> r1 = r0.s
            if (r1 != 0) goto L_0x0113
            cz.msebera.android.httpclient.config.ConnectionConfig r1 = r0.d
            if (r1 == 0) goto L_0x0111
            cz.msebera.android.httpclient.impl.DefaultBHttpServerConnectionFactory r1 = new cz.msebera.android.httpclient.impl.DefaultBHttpServerConnectionFactory
            cz.msebera.android.httpclient.config.ConnectionConfig r3 = r0.d
            r1.<init>(r3)
            goto L_0x0113
        L_0x0111:
            cz.msebera.android.httpclient.impl.DefaultBHttpServerConnectionFactory r1 = cz.msebera.android.httpclient.impl.DefaultBHttpServerConnectionFactory.INSTANCE
        L_0x0113:
            r14 = r1
            cz.msebera.android.httpclient.ExceptionLogger r1 = r0.t
            if (r1 != 0) goto L_0x011a
            cz.msebera.android.httpclient.ExceptionLogger r1 = cz.msebera.android.httpclient.ExceptionLogger.NO_OP
        L_0x011a:
            r16 = r1
            cz.msebera.android.httpclient.impl.bootstrap.HttpServer r1 = new cz.msebera.android.httpclient.impl.bootstrap.HttpServer
            int r3 = r0.a
            if (r3 <= 0) goto L_0x0126
            int r2 = r0.a
            r9 = r2
            goto L_0x0127
        L_0x0126:
            r9 = 0
        L_0x0127:
            java.net.InetAddress r10 = r0.b
            cz.msebera.android.httpclient.config.SocketConfig r2 = r0.c
            if (r2 == 0) goto L_0x0131
            cz.msebera.android.httpclient.config.SocketConfig r2 = r0.c
        L_0x012f:
            r11 = r2
            goto L_0x0134
        L_0x0131:
            cz.msebera.android.httpclient.config.SocketConfig r2 = cz.msebera.android.httpclient.config.SocketConfig.DEFAULT
            goto L_0x012f
        L_0x0134:
            cz.msebera.android.httpclient.impl.bootstrap.SSLServerSetupHandler r15 = r0.r
            r8 = r1
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.bootstrap.ServerBootstrap.create():cz.msebera.android.httpclient.impl.bootstrap.HttpServer");
    }
}
