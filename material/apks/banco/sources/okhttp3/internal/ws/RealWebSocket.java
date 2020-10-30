package okhttp3.internal.ws;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.ws.WebSocketReader.FrameCallback;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

public final class RealWebSocket implements WebSocket, FrameCallback {
    static final /* synthetic */ boolean b = true;
    private static final List<Protocol> c = Collections.singletonList(Protocol.HTTP_1_1);
    final WebSocketListener a;
    private final Request d;
    private final Random e;
    private final long f;
    private final String g;
    private Call h;
    private final Runnable i;
    private WebSocketReader j;
    private WebSocketWriter k;
    private ScheduledExecutorService l;
    private Streams m;
    private final ArrayDeque<ByteString> n = new ArrayDeque<>();
    private final ArrayDeque<Object> o = new ArrayDeque<>();
    private long p;
    private boolean q;
    private ScheduledFuture<?> r;
    private int s = -1;
    private String t;
    private boolean u;
    private int v;
    private int w;
    private int x;
    private boolean y;

    final class CancelRunnable implements Runnable {
        CancelRunnable() {
        }

        public void run() {
            RealWebSocket.this.cancel();
        }
    }

    static final class Close {
        final int a;
        final ByteString b;
        final long c;

        Close(int i, ByteString byteString, long j) {
            this.a = i;
            this.b = byteString;
            this.c = j;
        }
    }

    static final class Message {
        final int a;
        final ByteString b;

        Message(int i, ByteString byteString) {
            this.a = i;
            this.b = byteString;
        }
    }

    final class PingRunnable implements Runnable {
        PingRunnable() {
        }

        public void run() {
            RealWebSocket.this.b();
        }
    }

    public static abstract class Streams implements Closeable {
        public final boolean client;
        public final BufferedSink sink;
        public final BufferedSource source;

        public Streams(boolean z, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.client = z;
            this.source = bufferedSource;
            this.sink = bufferedSink;
        }
    }

    public RealWebSocket(Request request, WebSocketListener webSocketListener, Random random, long j2) {
        if (!"GET".equals(request.method())) {
            StringBuilder sb = new StringBuilder();
            sb.append("Request must be GET: ");
            sb.append(request.method());
            throw new IllegalArgumentException(sb.toString());
        }
        this.d = request;
        this.a = webSocketListener;
        this.e = random;
        this.f = j2;
        byte[] bArr = new byte[16];
        random.nextBytes(bArr);
        this.g = ByteString.of(bArr).base64();
        this.i = new Runnable() {
            public void run() {
                do {
                    try {
                    } catch (IOException e) {
                        RealWebSocket.this.failWebSocket(e, null);
                        return;
                    }
                } while (RealWebSocket.this.a());
            }
        };
    }

    public Request request() {
        return this.d;
    }

    public synchronized long queueSize() {
        return this.p;
    }

    public void cancel() {
        this.h.cancel();
    }

    public void connect(OkHttpClient okHttpClient) {
        OkHttpClient build = okHttpClient.newBuilder().eventListener(EventListener.NONE).protocols(c).build();
        final Request build2 = this.d.newBuilder().header("Upgrade", "websocket").header("Connection", "Upgrade").header("Sec-WebSocket-Key", this.g).header("Sec-WebSocket-Version", "13").build();
        this.h = Internal.instance.newWebSocketCall(build, build2);
        this.h.enqueue(new Callback() {
            public void onResponse(Call call, Response response) {
                try {
                    RealWebSocket.this.a(response);
                    StreamAllocation streamAllocation = Internal.instance.streamAllocation(call);
                    streamAllocation.noNewStreams();
                    Streams newWebSocketStreams = streamAllocation.connection().newWebSocketStreams(streamAllocation);
                    try {
                        RealWebSocket.this.a.onOpen(RealWebSocket.this, response);
                        StringBuilder sb = new StringBuilder();
                        sb.append("OkHttp WebSocket ");
                        sb.append(build2.url().redact());
                        RealWebSocket.this.initReaderAndWriter(sb.toString(), newWebSocketStreams);
                        streamAllocation.connection().socket().setSoTimeout(0);
                        RealWebSocket.this.loopReader();
                    } catch (Exception e) {
                        RealWebSocket.this.failWebSocket(e, null);
                    }
                } catch (ProtocolException e2) {
                    RealWebSocket.this.failWebSocket(e2, response);
                    Util.closeQuietly((Closeable) response);
                }
            }

            public void onFailure(Call call, IOException iOException) {
                RealWebSocket.this.failWebSocket(iOException, null);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void a(Response response) {
        if (response.code() != 101) {
            StringBuilder sb = new StringBuilder();
            sb.append("Expected HTTP 101 response but was '");
            sb.append(response.code());
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(response.message());
            sb.append("'");
            throw new ProtocolException(sb.toString());
        }
        String header = response.header("Connection");
        if (!"Upgrade".equalsIgnoreCase(header)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Expected 'Connection' header value 'Upgrade' but was '");
            sb2.append(header);
            sb2.append("'");
            throw new ProtocolException(sb2.toString());
        }
        String header2 = response.header("Upgrade");
        if (!"websocket".equalsIgnoreCase(header2)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Expected 'Upgrade' header value 'websocket' but was '");
            sb3.append(header2);
            sb3.append("'");
            throw new ProtocolException(sb3.toString());
        }
        String header3 = response.header("Sec-WebSocket-Accept");
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.g);
        sb4.append("258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        String base64 = ByteString.encodeUtf8(sb4.toString()).sha1().base64();
        if (!base64.equals(header3)) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Expected 'Sec-WebSocket-Accept' header value '");
            sb5.append(base64);
            sb5.append("' but was '");
            sb5.append(header3);
            sb5.append("'");
            throw new ProtocolException(sb5.toString());
        }
    }

    public void initReaderAndWriter(String str, Streams streams) {
        synchronized (this) {
            this.m = streams;
            this.k = new WebSocketWriter(streams.client, streams.sink, this.e);
            this.l = new ScheduledThreadPoolExecutor(1, Util.threadFactory(str, false));
            if (this.f != 0) {
                this.l.scheduleAtFixedRate(new PingRunnable(), this.f, this.f, TimeUnit.MILLISECONDS);
            }
            if (!this.o.isEmpty()) {
                c();
            }
        }
        this.j = new WebSocketReader(streams.client, streams.source, this);
    }

    public void loopReader() {
        while (this.s == -1) {
            this.j.a();
        }
    }

    public void onReadMessage(String str) {
        this.a.onMessage((WebSocket) this, str);
    }

    public void onReadMessage(ByteString byteString) {
        this.a.onMessage((WebSocket) this, byteString);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onReadPing(okio.ByteString r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.u     // Catch:{ all -> 0x0024 }
            if (r0 != 0) goto L_0x0022
            boolean r0 = r1.q     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0012
            java.util.ArrayDeque<java.lang.Object> r0 = r1.o     // Catch:{ all -> 0x0024 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0022
        L_0x0012:
            java.util.ArrayDeque<okio.ByteString> r0 = r1.n     // Catch:{ all -> 0x0024 }
            r0.add(r2)     // Catch:{ all -> 0x0024 }
            r1.c()     // Catch:{ all -> 0x0024 }
            int r2 = r1.w     // Catch:{ all -> 0x0024 }
            int r2 = r2 + 1
            r1.w = r2     // Catch:{ all -> 0x0024 }
            monitor-exit(r1)
            return
        L_0x0022:
            monitor-exit(r1)
            return
        L_0x0024:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.onReadPing(okio.ByteString):void");
    }

    public synchronized void onReadPong(ByteString byteString) {
        this.x++;
        this.y = false;
    }

    public void onReadClose(int i2, String str) {
        Closeable closeable;
        if (i2 == -1) {
            throw new IllegalArgumentException();
        }
        synchronized (this) {
            if (this.s != -1) {
                throw new IllegalStateException("already closed");
            }
            this.s = i2;
            this.t = str;
            if (!this.q || !this.o.isEmpty()) {
                closeable = null;
            } else {
                closeable = this.m;
                this.m = null;
                if (this.r != null) {
                    this.r.cancel(false);
                }
                this.l.shutdown();
            }
        }
        try {
            this.a.onClosing(this, i2, str);
            if (closeable != null) {
                this.a.onClosed(this, i2, str);
            }
        } finally {
            Util.closeQuietly(closeable);
        }
    }

    public boolean send(String str) {
        if (str != null) {
            return a(ByteString.encodeUtf8(str), 1);
        }
        throw new NullPointerException("text == null");
    }

    public boolean send(ByteString byteString) {
        if (byteString != null) {
            return a(byteString, 2);
        }
        throw new NullPointerException("bytes == null");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean a(okio.ByteString r9, int r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            boolean r0 = r8.u     // Catch:{ all -> 0x0040 }
            r1 = 0
            if (r0 != 0) goto L_0x003e
            boolean r0 = r8.q     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x000b
            goto L_0x003e
        L_0x000b:
            long r2 = r8.p     // Catch:{ all -> 0x0040 }
            int r0 = r9.size()     // Catch:{ all -> 0x0040 }
            long r4 = (long) r0     // Catch:{ all -> 0x0040 }
            long r6 = r2 + r4
            r2 = 16777216(0x1000000, double:8.289046E-317)
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0023
            r9 = 1001(0x3e9, float:1.403E-42)
            r10 = 0
            r8.close(r9, r10)     // Catch:{ all -> 0x0040 }
            monitor-exit(r8)
            return r1
        L_0x0023:
            long r0 = r8.p     // Catch:{ all -> 0x0040 }
            int r2 = r9.size()     // Catch:{ all -> 0x0040 }
            long r2 = (long) r2     // Catch:{ all -> 0x0040 }
            long r4 = r0 + r2
            r8.p = r4     // Catch:{ all -> 0x0040 }
            java.util.ArrayDeque<java.lang.Object> r0 = r8.o     // Catch:{ all -> 0x0040 }
            okhttp3.internal.ws.RealWebSocket$Message r1 = new okhttp3.internal.ws.RealWebSocket$Message     // Catch:{ all -> 0x0040 }
            r1.<init>(r10, r9)     // Catch:{ all -> 0x0040 }
            r0.add(r1)     // Catch:{ all -> 0x0040 }
            r8.c()     // Catch:{ all -> 0x0040 }
            r9 = 1
            monitor-exit(r8)
            return r9
        L_0x003e:
            monitor-exit(r8)
            return r1
        L_0x0040:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.a(okio.ByteString, int):boolean");
    }

    public boolean close(int i2, String str) {
        return a(i2, str, 60000);
    }

    /* access modifiers changed from: 0000 */
    public synchronized boolean a(int i2, String str, long j2) {
        WebSocketProtocol.b(i2);
        ByteString byteString = null;
        if (str != null) {
            byteString = ByteString.encodeUtf8(str);
            if (((long) byteString.size()) > 123) {
                StringBuilder sb = new StringBuilder();
                sb.append("reason.size() > 123: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (!this.u) {
            if (!this.q) {
                this.q = true;
                this.o.add(new Close(i2, byteString, j2));
                c();
                return true;
            }
        }
        return false;
    }

    private void c() {
        if (!b && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (this.l != null) {
            this.l.execute(this.i);
        }
    }

    /* JADX INFO: used method not loaded: okhttp3.internal.ws.WebSocketWriter.a(int, long):null, types can be incorrect */
    /* JADX INFO: used method not loaded: okhttp3.internal.ws.WebSocketWriter.a(int, okio.ByteString):null, types can be incorrect */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0050, code lost:
        if (r2 == null) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r0.b(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0056, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005a, code lost:
        if ((r5 instanceof okhttp3.internal.ws.RealWebSocket.Message) == false) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005c, code lost:
        r1 = ((okhttp3.internal.ws.RealWebSocket.Message) r5).b;
        r0 = okio.Okio.buffer(r0.a(((okhttp3.internal.ws.RealWebSocket.Message) r5).a, (long) r1.size()));
        r0.write(r1);
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0078, code lost:
        monitor-enter(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r11.p -= (long) r1.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0084, code lost:
        monitor-exit(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008b, code lost:
        if ((r5 instanceof okhttp3.internal.ws.RealWebSocket.Close) == false) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008d, code lost:
        r5 = (okhttp3.internal.ws.RealWebSocket.Close) r5;
        r0.a(r5.a, r5.b);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0096, code lost:
        if (r4 == null) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0098, code lost:
        r11.a.onClosed(r11, r1, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009d, code lost:
        okhttp3.internal.Util.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a1, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a7, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a8, code lost:
        okhttp3.internal.Util.closeQuietly(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ab, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a() {
        /*
            r11 = this;
            monitor-enter(r11)
            boolean r0 = r11.u     // Catch:{ all -> 0x00ac }
            r1 = 0
            if (r0 == 0) goto L_0x0008
            monitor-exit(r11)     // Catch:{ all -> 0x00ac }
            return r1
        L_0x0008:
            okhttp3.internal.ws.WebSocketWriter r0 = r11.k     // Catch:{ all -> 0x00ac }
            java.util.ArrayDeque<okio.ByteString> r2 = r11.n     // Catch:{ all -> 0x00ac }
            java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x00ac }
            okio.ByteString r2 = (okio.ByteString) r2     // Catch:{ all -> 0x00ac }
            r3 = -1
            r4 = 0
            if (r2 != 0) goto L_0x004c
            java.util.ArrayDeque<java.lang.Object> r5 = r11.o     // Catch:{ all -> 0x00ac }
            java.lang.Object r5 = r5.poll()     // Catch:{ all -> 0x00ac }
            boolean r6 = r5 instanceof okhttp3.internal.ws.RealWebSocket.Close     // Catch:{ all -> 0x00ac }
            if (r6 == 0) goto L_0x0046
            int r1 = r11.s     // Catch:{ all -> 0x00ac }
            java.lang.String r6 = r11.t     // Catch:{ all -> 0x00ac }
            if (r1 == r3) goto L_0x0031
            okhttp3.internal.ws.RealWebSocket$Streams r3 = r11.m     // Catch:{ all -> 0x00ac }
            r11.m = r4     // Catch:{ all -> 0x00ac }
            java.util.concurrent.ScheduledExecutorService r4 = r11.l     // Catch:{ all -> 0x00ac }
            r4.shutdown()     // Catch:{ all -> 0x00ac }
            r4 = r3
            goto L_0x004f
        L_0x0031:
            java.util.concurrent.ScheduledExecutorService r3 = r11.l     // Catch:{ all -> 0x00ac }
            okhttp3.internal.ws.RealWebSocket$CancelRunnable r7 = new okhttp3.internal.ws.RealWebSocket$CancelRunnable     // Catch:{ all -> 0x00ac }
            r7.<init>()     // Catch:{ all -> 0x00ac }
            r8 = r5
            okhttp3.internal.ws.RealWebSocket$Close r8 = (okhttp3.internal.ws.RealWebSocket.Close) r8     // Catch:{ all -> 0x00ac }
            long r8 = r8.c     // Catch:{ all -> 0x00ac }
            java.util.concurrent.TimeUnit r10 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00ac }
            java.util.concurrent.ScheduledFuture r3 = r3.schedule(r7, r8, r10)     // Catch:{ all -> 0x00ac }
            r11.r = r3     // Catch:{ all -> 0x00ac }
            goto L_0x004f
        L_0x0046:
            if (r5 != 0) goto L_0x004a
            monitor-exit(r11)     // Catch:{ all -> 0x00ac }
            return r1
        L_0x004a:
            r6 = r4
            goto L_0x004e
        L_0x004c:
            r5 = r4
            r6 = r5
        L_0x004e:
            r1 = -1
        L_0x004f:
            monitor-exit(r11)     // Catch:{ all -> 0x00ac }
            if (r2 == 0) goto L_0x0058
            r0.b(r2)     // Catch:{ all -> 0x0056 }
            goto L_0x009d
        L_0x0056:
            r0 = move-exception
            goto L_0x00a8
        L_0x0058:
            boolean r2 = r5 instanceof okhttp3.internal.ws.RealWebSocket.Message     // Catch:{ all -> 0x0056 }
            if (r2 == 0) goto L_0x0089
            r1 = r5
            okhttp3.internal.ws.RealWebSocket$Message r1 = (okhttp3.internal.ws.RealWebSocket.Message) r1     // Catch:{ all -> 0x0056 }
            okio.ByteString r1 = r1.b     // Catch:{ all -> 0x0056 }
            okhttp3.internal.ws.RealWebSocket$Message r5 = (okhttp3.internal.ws.RealWebSocket.Message) r5     // Catch:{ all -> 0x0056 }
            int r2 = r5.a     // Catch:{ all -> 0x0056 }
            int r3 = r1.size()     // Catch:{ all -> 0x0056 }
            long r5 = (long) r3     // Catch:{ all -> 0x0056 }
            okio.Sink r0 = r0.a(r2, r5)     // Catch:{ all -> 0x0056 }
            okio.BufferedSink r0 = okio.Okio.buffer(r0)     // Catch:{ all -> 0x0056 }
            r0.write(r1)     // Catch:{ all -> 0x0056 }
            r0.close()     // Catch:{ all -> 0x0056 }
            monitor-enter(r11)     // Catch:{ all -> 0x0056 }
            long r2 = r11.p     // Catch:{ all -> 0x0086 }
            int r0 = r1.size()     // Catch:{ all -> 0x0086 }
            long r0 = (long) r0     // Catch:{ all -> 0x0086 }
            long r5 = r2 - r0
            r11.p = r5     // Catch:{ all -> 0x0086 }
            monitor-exit(r11)     // Catch:{ all -> 0x0086 }
            goto L_0x009d
        L_0x0086:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0086 }
            throw r0     // Catch:{ all -> 0x0056 }
        L_0x0089:
            boolean r2 = r5 instanceof okhttp3.internal.ws.RealWebSocket.Close     // Catch:{ all -> 0x0056 }
            if (r2 == 0) goto L_0x00a2
            okhttp3.internal.ws.RealWebSocket$Close r5 = (okhttp3.internal.ws.RealWebSocket.Close) r5     // Catch:{ all -> 0x0056 }
            int r2 = r5.a     // Catch:{ all -> 0x0056 }
            okio.ByteString r3 = r5.b     // Catch:{ all -> 0x0056 }
            r0.a(r2, r3)     // Catch:{ all -> 0x0056 }
            if (r4 == 0) goto L_0x009d
            okhttp3.WebSocketListener r0 = r11.a     // Catch:{ all -> 0x0056 }
            r0.onClosed(r11, r1, r6)     // Catch:{ all -> 0x0056 }
        L_0x009d:
            r0 = 1
            okhttp3.internal.Util.closeQuietly(r4)
            return r0
        L_0x00a2:
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch:{ all -> 0x0056 }
            r0.<init>()     // Catch:{ all -> 0x0056 }
            throw r0     // Catch:{ all -> 0x0056 }
        L_0x00a8:
            okhttp3.internal.Util.closeQuietly(r4)
            throw r0
        L_0x00ac:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x00ac }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.a():boolean");
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
        if (r1 == -1) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001e, code lost:
        r2 = new java.lang.StringBuilder();
        r2.append("sent ping but didn't receive pong within ");
        r2.append(r7.f);
        r2.append("ms (after ");
        r2.append(r1 - 1);
        r2.append(" successful ping/pongs)");
        failWebSocket(new java.net.SocketTimeoutException(r2.toString()), null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0047, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r0.a(okio.ByteString.EMPTY);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        failWebSocket(r0, null);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.u     // Catch:{ all -> 0x0053 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r7)     // Catch:{ all -> 0x0053 }
            return
        L_0x0007:
            okhttp3.internal.ws.WebSocketWriter r0 = r7.k     // Catch:{ all -> 0x0053 }
            boolean r1 = r7.y     // Catch:{ all -> 0x0053 }
            r2 = -1
            if (r1 == 0) goto L_0x0011
            int r1 = r7.v     // Catch:{ all -> 0x0053 }
            goto L_0x0012
        L_0x0011:
            r1 = -1
        L_0x0012:
            int r3 = r7.v     // Catch:{ all -> 0x0053 }
            r4 = 1
            int r3 = r3 + r4
            r7.v = r3     // Catch:{ all -> 0x0053 }
            r7.y = r4     // Catch:{ all -> 0x0053 }
            monitor-exit(r7)     // Catch:{ all -> 0x0053 }
            r3 = 0
            if (r1 == r2) goto L_0x0048
            java.net.SocketTimeoutException r0 = new java.net.SocketTimeoutException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "sent ping but didn't receive pong within "
            r2.append(r5)
            long r5 = r7.f
            r2.append(r5)
            java.lang.String r5 = "ms (after "
            r2.append(r5)
            int r1 = r1 - r4
            r2.append(r1)
            java.lang.String r1 = " successful ping/pongs)"
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            r7.failWebSocket(r0, r3)
            return
        L_0x0048:
            okio.ByteString r1 = okio.ByteString.EMPTY     // Catch:{ IOException -> 0x004e }
            r0.a(r1)     // Catch:{ IOException -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r0 = move-exception
            r7.failWebSocket(r0, r3)
        L_0x0052:
            return
        L_0x0053:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0053 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.b():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r3.a.onFailure(r3, r4, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002d, code lost:
        okhttp3.internal.Util.closeQuietly((java.io.Closeable) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0030, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void failWebSocket(java.lang.Exception r4, @javax.annotation.Nullable okhttp3.Response r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.u     // Catch:{ all -> 0x0031 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r3)     // Catch:{ all -> 0x0031 }
            return
        L_0x0007:
            r0 = 1
            r3.u = r0     // Catch:{ all -> 0x0031 }
            okhttp3.internal.ws.RealWebSocket$Streams r0 = r3.m     // Catch:{ all -> 0x0031 }
            r1 = 0
            r3.m = r1     // Catch:{ all -> 0x0031 }
            java.util.concurrent.ScheduledFuture<?> r1 = r3.r     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x0019
            java.util.concurrent.ScheduledFuture<?> r1 = r3.r     // Catch:{ all -> 0x0031 }
            r2 = 0
            r1.cancel(r2)     // Catch:{ all -> 0x0031 }
        L_0x0019:
            java.util.concurrent.ScheduledExecutorService r1 = r3.l     // Catch:{ all -> 0x0031 }
            if (r1 == 0) goto L_0x0022
            java.util.concurrent.ScheduledExecutorService r1 = r3.l     // Catch:{ all -> 0x0031 }
            r1.shutdown()     // Catch:{ all -> 0x0031 }
        L_0x0022:
            monitor-exit(r3)     // Catch:{ all -> 0x0031 }
            okhttp3.WebSocketListener r1 = r3.a     // Catch:{ all -> 0x002c }
            r1.onFailure(r3, r4, r5)     // Catch:{ all -> 0x002c }
            okhttp3.internal.Util.closeQuietly(r0)
            return
        L_0x002c:
            r4 = move-exception
            okhttp3.internal.Util.closeQuietly(r0)
            throw r4
        L_0x0031:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0031 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.RealWebSocket.failWebSocket(java.lang.Exception, okhttp3.Response):void");
    }
}
