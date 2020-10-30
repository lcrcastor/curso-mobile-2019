package com.squareup.okhttp.internal.spdy;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.FrameReader.Handler;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class SpdyConnection implements Closeable {
    static final /* synthetic */ boolean k = true;
    /* access modifiers changed from: private */
    public static final ExecutorService l;
    final Protocol a;
    final boolean b;
    long c;
    long d;
    final Settings e;
    final Settings f;
    final Variant g;
    final Socket h;
    final FrameWriter i;
    final Reader j;
    /* access modifiers changed from: private */
    public final IncomingStreamHandler m;
    /* access modifiers changed from: private */
    public final Map<Integer, SpdyStream> n;
    /* access modifiers changed from: private */
    public final String o;
    /* access modifiers changed from: private */
    public int p;
    /* access modifiers changed from: private */
    public int q;
    /* access modifiers changed from: private */
    public boolean r;
    private long s;
    private final ExecutorService t;
    private Map<Integer, Ping> u;
    /* access modifiers changed from: private */
    public final PushObserver v;
    private int w;
    /* access modifiers changed from: private */
    public boolean x;
    /* access modifiers changed from: private */
    public final Set<Integer> y;

    public static class Builder {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public Socket b;
        /* access modifiers changed from: private */
        public IncomingStreamHandler c;
        /* access modifiers changed from: private */
        public Protocol d;
        /* access modifiers changed from: private */
        public PushObserver e;
        /* access modifiers changed from: private */
        public boolean f;

        public Builder(boolean z, Socket socket) {
            this(((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), z, socket);
        }

        public Builder(String str, boolean z, Socket socket) {
            this.c = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
            this.d = Protocol.SPDY_3;
            this.e = PushObserver.CANCEL;
            this.a = str;
            this.f = z;
            this.b = socket;
        }

        public Builder handler(IncomingStreamHandler incomingStreamHandler) {
            this.c = incomingStreamHandler;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.d = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.e = pushObserver;
            return this;
        }

        public SpdyConnection build() {
            return new SpdyConnection(this);
        }
    }

    class Reader extends NamedRunnable implements Handler {
        FrameReader a;

        public void ackSettings() {
        }

        public void alternateService(int i, String str, ByteString byteString, String str2, int i2, long j) {
        }

        public void priority(int i, int i2, int i3, boolean z) {
        }

        private Reader() {
            super("OkHttp %s", SpdyConnection.this.o);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Can't wrap try/catch for region: R(11:14|13|16|17|18|19|20|21|22|23|25) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
            r2 = th;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x003d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r6 = this;
                com.squareup.okhttp.internal.spdy.ErrorCode r0 = com.squareup.okhttp.internal.spdy.ErrorCode.INTERNAL_ERROR
                com.squareup.okhttp.internal.spdy.ErrorCode r1 = com.squareup.okhttp.internal.spdy.ErrorCode.INTERNAL_ERROR
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.Variant r2 = r2.g     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.SpdyConnection r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x003d }
                java.net.Socket r3 = r3.h     // Catch:{ IOException -> 0x003d }
                okio.Source r3 = okio.Okio.source(r3)     // Catch:{ IOException -> 0x003d }
                okio.BufferedSource r3 = okio.Okio.buffer(r3)     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.SpdyConnection r4 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x003d }
                boolean r4 = r4.b     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.FrameReader r2 = r2.newReader(r3, r4)     // Catch:{ IOException -> 0x003d }
                r6.a = r2     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x003d }
                boolean r2 = r2.b     // Catch:{ IOException -> 0x003d }
                if (r2 != 0) goto L_0x0029
                com.squareup.okhttp.internal.spdy.FrameReader r2 = r6.a     // Catch:{ IOException -> 0x003d }
                r2.readConnectionPreface()     // Catch:{ IOException -> 0x003d }
            L_0x0029:
                com.squareup.okhttp.internal.spdy.FrameReader r2 = r6.a     // Catch:{ IOException -> 0x003d }
                boolean r2 = r2.nextFrame(r6)     // Catch:{ IOException -> 0x003d }
                if (r2 == 0) goto L_0x0032
                goto L_0x0029
            L_0x0032:
                com.squareup.okhttp.internal.spdy.ErrorCode r2 = com.squareup.okhttp.internal.spdy.ErrorCode.NO_ERROR     // Catch:{ IOException -> 0x003d }
                com.squareup.okhttp.internal.spdy.ErrorCode r0 = com.squareup.okhttp.internal.spdy.ErrorCode.CANCEL     // Catch:{ IOException -> 0x0039 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x0046 }
                goto L_0x0043
            L_0x0039:
                r0 = r2
                goto L_0x003d
            L_0x003b:
                r2 = move-exception
                goto L_0x0050
            L_0x003d:
                com.squareup.okhttp.internal.spdy.ErrorCode r2 = com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x003b }
                com.squareup.okhttp.internal.spdy.ErrorCode r0 = com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x004c }
                com.squareup.okhttp.internal.spdy.SpdyConnection r1 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x0046 }
            L_0x0043:
                r1.a(r2, r0)     // Catch:{ IOException -> 0x0046 }
            L_0x0046:
                com.squareup.okhttp.internal.spdy.FrameReader r0 = r6.a
                com.squareup.okhttp.internal.Util.closeQuietly(r0)
                return
            L_0x004c:
                r0 = move-exception
                r5 = r2
                r2 = r0
                r0 = r5
            L_0x0050:
                com.squareup.okhttp.internal.spdy.SpdyConnection r3 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ IOException -> 0x0055 }
                r3.a(r0, r1)     // Catch:{ IOException -> 0x0055 }
            L_0x0055:
                com.squareup.okhttp.internal.spdy.FrameReader r0 = r6.a
                com.squareup.okhttp.internal.Util.closeQuietly(r0)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.Reader.execute():void");
        }

        public void data(boolean z, int i, BufferedSource bufferedSource, int i2) {
            if (SpdyConnection.this.d(i)) {
                SpdyConnection.this.a(i, bufferedSource, i2, z);
                return;
            }
            SpdyStream a2 = SpdyConnection.this.a(i);
            if (a2 == null) {
                SpdyConnection.this.a(i, ErrorCode.INVALID_STREAM);
                bufferedSource.skip((long) i2);
                return;
            }
            a2.a(bufferedSource, i2);
            if (z) {
                a2.a();
            }
        }

        /* JADX INFO: used method not loaded: com.squareup.okhttp.internal.spdy.SpdyStream.a(java.util.List, com.squareup.okhttp.internal.spdy.HeadersMode):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x008f, code lost:
            if (r14.failIfStreamPresent() == false) goto L_0x009c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0091, code lost:
            r0.closeLater(com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR);
            r8.b.b(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x009b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x009c, code lost:
            r0.a((java.util.List) r13, r14);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x009f, code lost:
            if (r10 == false) goto L_0x00a4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a1, code lost:
            r0.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a4, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void headers(boolean r9, boolean r10, int r11, int r12, java.util.List<com.squareup.okhttp.internal.spdy.Header> r13, com.squareup.okhttp.internal.spdy.HeadersMode r14) {
            /*
                r8 = this;
                com.squareup.okhttp.internal.spdy.SpdyConnection r12 = com.squareup.okhttp.internal.spdy.SpdyConnection.this
                boolean r12 = r12.d(r11)
                if (r12 == 0) goto L_0x000e
                com.squareup.okhttp.internal.spdy.SpdyConnection r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.this
                r9.a(r11, r13, r10)
                return
            L_0x000e:
                com.squareup.okhttp.internal.spdy.SpdyConnection r12 = com.squareup.okhttp.internal.spdy.SpdyConnection.this
                monitor-enter(r12)
                com.squareup.okhttp.internal.spdy.SpdyConnection r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                boolean r0 = r0.r     // Catch:{ all -> 0x00a5 }
                if (r0 == 0) goto L_0x001b
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                return
            L_0x001b:
                com.squareup.okhttp.internal.spdy.SpdyConnection r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = r0.a(r11)     // Catch:{ all -> 0x00a5 }
                if (r0 != 0) goto L_0x008a
                boolean r14 = r14.failIfStreamAbsent()     // Catch:{ all -> 0x00a5 }
                if (r14 == 0) goto L_0x0032
                com.squareup.okhttp.internal.spdy.SpdyConnection r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.ErrorCode r10 = com.squareup.okhttp.internal.spdy.ErrorCode.INVALID_STREAM     // Catch:{ all -> 0x00a5 }
                r9.a(r11, r10)     // Catch:{ all -> 0x00a5 }
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                return
            L_0x0032:
                com.squareup.okhttp.internal.spdy.SpdyConnection r14 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                int r14 = r14.p     // Catch:{ all -> 0x00a5 }
                if (r11 > r14) goto L_0x003c
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                return
            L_0x003c:
                int r14 = r11 % 2
                com.squareup.okhttp.internal.spdy.SpdyConnection r0 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                int r0 = r0.q     // Catch:{ all -> 0x00a5 }
                r1 = 2
                int r0 = r0 % r1
                if (r14 != r0) goto L_0x004a
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                return
            L_0x004a:
                com.squareup.okhttp.internal.spdy.SpdyStream r14 = new com.squareup.okhttp.internal.spdy.SpdyStream     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r4 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                r2 = r14
                r3 = r11
                r5 = r9
                r6 = r10
                r7 = r13
                r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                r9.p = r11     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                java.util.Map r9 = r9.n     // Catch:{ all -> 0x00a5 }
                java.lang.Integer r10 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x00a5 }
                r9.put(r10, r14)     // Catch:{ all -> 0x00a5 }
                java.util.concurrent.ExecutorService r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.l     // Catch:{ all -> 0x00a5 }
                com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$1 r10 = new com.squareup.okhttp.internal.spdy.SpdyConnection$Reader$1     // Catch:{ all -> 0x00a5 }
                java.lang.String r13 = "OkHttp %s stream %d"
                java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x00a5 }
                r1 = 0
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = com.squareup.okhttp.internal.spdy.SpdyConnection.this     // Catch:{ all -> 0x00a5 }
                java.lang.String r2 = r2.o     // Catch:{ all -> 0x00a5 }
                r0[r1] = r2     // Catch:{ all -> 0x00a5 }
                r1 = 1
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x00a5 }
                r0[r1] = r11     // Catch:{ all -> 0x00a5 }
                r10.<init>(r13, r0, r14)     // Catch:{ all -> 0x00a5 }
                r9.execute(r10)     // Catch:{ all -> 0x00a5 }
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                return
            L_0x008a:
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                boolean r9 = r14.failIfStreamPresent()
                if (r9 == 0) goto L_0x009c
                com.squareup.okhttp.internal.spdy.ErrorCode r9 = com.squareup.okhttp.internal.spdy.ErrorCode.PROTOCOL_ERROR
                r0.closeLater(r9)
                com.squareup.okhttp.internal.spdy.SpdyConnection r9 = com.squareup.okhttp.internal.spdy.SpdyConnection.this
                r9.b(r11)
                return
            L_0x009c:
                r0.a(r13, r14)
                if (r10 == 0) goto L_0x00a4
                r0.a()
            L_0x00a4:
                return
            L_0x00a5:
                r9 = move-exception
                monitor-exit(r12)     // Catch:{ all -> 0x00a5 }
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.Reader.headers(boolean, boolean, int, int, java.util.List, com.squareup.okhttp.internal.spdy.HeadersMode):void");
        }

        public void rstStream(int i, ErrorCode errorCode) {
            if (SpdyConnection.this.d(i)) {
                SpdyConnection.this.c(i, errorCode);
                return;
            }
            SpdyStream b2 = SpdyConnection.this.b(i);
            if (b2 != null) {
                b2.a(errorCode);
            }
        }

        public void settings(boolean z, Settings settings) {
            SpdyStream[] spdyStreamArr;
            long j;
            synchronized (SpdyConnection.this) {
                int e = SpdyConnection.this.f.e(65536);
                if (z) {
                    SpdyConnection.this.f.a();
                }
                SpdyConnection.this.f.a(settings);
                if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2) {
                    a(settings);
                }
                int e2 = SpdyConnection.this.f.e(65536);
                spdyStreamArr = null;
                if (e2 == -1 || e2 == e) {
                    j = 0;
                } else {
                    j = (long) (e2 - e);
                    if (!SpdyConnection.this.x) {
                        SpdyConnection.this.a(j);
                        SpdyConnection.this.x = true;
                    }
                    if (!SpdyConnection.this.n.isEmpty()) {
                        spdyStreamArr = (SpdyStream[]) SpdyConnection.this.n.values().toArray(new SpdyStream[SpdyConnection.this.n.size()]);
                    }
                }
            }
            if (spdyStreamArr != null && j != 0) {
                for (SpdyStream spdyStream : spdyStreamArr) {
                    synchronized (spdyStream) {
                        spdyStream.a(j);
                    }
                }
            }
        }

        private void a(final Settings settings) {
            SpdyConnection.l.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{SpdyConnection.this.o}) {
                public void execute() {
                    try {
                        SpdyConnection.this.i.ackSettings(settings);
                    } catch (IOException unused) {
                    }
                }
            });
        }

        public void ping(boolean z, int i, int i2) {
            if (z) {
                Ping c = SpdyConnection.this.c(i);
                if (c != null) {
                    c.b();
                    return;
                }
                return;
            }
            SpdyConnection.this.a(true, i, i2, (Ping) null);
        }

        public void goAway(int i, ErrorCode errorCode, ByteString byteString) {
            SpdyStream[] spdyStreamArr;
            byteString.size();
            synchronized (SpdyConnection.this) {
                spdyStreamArr = (SpdyStream[]) SpdyConnection.this.n.values().toArray(new SpdyStream[SpdyConnection.this.n.size()]);
                SpdyConnection.this.r = true;
            }
            for (SpdyStream spdyStream : spdyStreamArr) {
                if (spdyStream.getId() > i && spdyStream.isLocallyInitiated()) {
                    spdyStream.a(ErrorCode.REFUSED_STREAM);
                    SpdyConnection.this.b(spdyStream.getId());
                }
            }
        }

        public void windowUpdate(int i, long j) {
            if (i == 0) {
                synchronized (SpdyConnection.this) {
                    SpdyConnection.this.d += j;
                    SpdyConnection.this.notifyAll();
                }
                return;
            }
            SpdyStream a2 = SpdyConnection.this.a(i);
            if (a2 != null) {
                synchronized (a2) {
                    a2.a(j);
                }
            }
        }

        public void pushPromise(int i, int i2, List<Header> list) {
            SpdyConnection.this.a(i2, list);
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, SubsamplingScaleImageView.TILE_SIZE_AUTO, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp SpdyConnection", true));
        l = threadPoolExecutor;
    }

    private SpdyConnection(Builder builder) {
        this.n = new HashMap();
        this.s = System.nanoTime();
        this.c = 0;
        this.e = new Settings();
        this.f = new Settings();
        this.x = false;
        this.y = new LinkedHashSet();
        this.a = builder.d;
        this.v = builder.e;
        this.b = builder.f;
        this.m = builder.c;
        int i2 = 2;
        this.q = builder.f ? 1 : 2;
        if (builder.f && this.a == Protocol.HTTP_2) {
            this.q += 2;
        }
        if (builder.f) {
            i2 = 1;
        }
        this.w = i2;
        if (builder.f) {
            this.e.a(7, 0, 16777216);
        }
        this.o = builder.a;
        if (this.a == Protocol.HTTP_2) {
            this.g = new Http2();
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(String.format("OkHttp %s Push Observer", new Object[]{this.o}), true));
            this.t = threadPoolExecutor;
            this.f.a(7, 0, 65535);
            this.f.a(5, 0, 16384);
        } else if (this.a == Protocol.SPDY_3) {
            this.g = new Spdy3();
            this.t = null;
        } else {
            throw new AssertionError(this.a);
        }
        this.d = (long) this.f.e(65536);
        this.h = builder.b;
        this.i = this.g.newWriter(Okio.buffer(Okio.sink(builder.b)), this.b);
        this.j = new Reader();
        new Thread(this.j).start();
    }

    public Protocol getProtocol() {
        return this.a;
    }

    public synchronized int openStreamCount() {
        return this.n.size();
    }

    /* access modifiers changed from: 0000 */
    public synchronized SpdyStream a(int i2) {
        return (SpdyStream) this.n.get(Integer.valueOf(i2));
    }

    /* access modifiers changed from: 0000 */
    public synchronized SpdyStream b(int i2) {
        SpdyStream spdyStream;
        spdyStream = (SpdyStream) this.n.remove(Integer.valueOf(i2));
        if (spdyStream != null && this.n.isEmpty()) {
            a(true);
        }
        notifyAll();
        return spdyStream;
    }

    private synchronized void a(boolean z) {
        long j2;
        if (z) {
            try {
                j2 = System.nanoTime();
            } catch (Throwable th) {
                throw th;
            }
        } else {
            j2 = Long.MAX_VALUE;
        }
        this.s = j2;
    }

    public synchronized boolean isIdle() {
        return this.s != Long.MAX_VALUE;
    }

    public synchronized long getIdleStartTimeNs() {
        return this.s;
    }

    public SpdyStream pushStream(int i2, List<Header> list, boolean z) {
        if (this.b) {
            throw new IllegalStateException("Client cannot push requests.");
        } else if (this.a == Protocol.HTTP_2) {
            return a(i2, list, z, false);
        } else {
            throw new IllegalStateException("protocol != HTTP_2");
        }
    }

    public SpdyStream newStream(List<Header> list, boolean z, boolean z2) {
        return a(0, list, z, z2);
    }

    private SpdyStream a(int i2, List<Header> list, boolean z, boolean z2) {
        int i3;
        SpdyStream spdyStream;
        boolean z3 = !z;
        boolean z4 = !z2;
        synchronized (this.i) {
            synchronized (this) {
                if (this.r) {
                    throw new IOException("shutdown");
                }
                i3 = this.q;
                this.q += 2;
                spdyStream = new SpdyStream(i3, this, z3, z4, list);
                if (spdyStream.isOpen()) {
                    this.n.put(Integer.valueOf(i3), spdyStream);
                    a(false);
                }
            }
            if (i2 == 0) {
                this.i.synStream(z3, z4, i3, i2, list);
            } else if (this.b) {
                throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
            } else {
                this.i.pushPromise(i2, i3, list);
            }
        }
        if (!z) {
            this.i.flush();
        }
        return spdyStream;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, boolean z, List<Header> list) {
        this.i.synReply(z, i2, list);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:26|27|28) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r2 = java.lang.Math.min((int) java.lang.Math.min(r14, r10.d), r10.i.maxDataLength());
        r6 = (long) r2;
        r10.d -= r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x005e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeData(int r11, boolean r12, okio.Buffer r13, long r14) {
        /*
            r10 = this;
            r0 = 0
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            r3 = 0
            if (r2 != 0) goto L_0x000d
            com.squareup.okhttp.internal.spdy.FrameWriter r14 = r10.i
            r14.data(r12, r11, r13, r3)
            return
        L_0x000d:
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x0066
            monitor-enter(r10)
        L_0x0012:
            long r4 = r10.d     // Catch:{ InterruptedException -> 0x005e }
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x0030
            java.util.Map<java.lang.Integer, com.squareup.okhttp.internal.spdy.SpdyStream> r2 = r10.n     // Catch:{ InterruptedException -> 0x005e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r11)     // Catch:{ InterruptedException -> 0x005e }
            boolean r2 = r2.containsKey(r4)     // Catch:{ InterruptedException -> 0x005e }
            if (r2 != 0) goto L_0x002c
            java.io.IOException r11 = new java.io.IOException     // Catch:{ InterruptedException -> 0x005e }
            java.lang.String r12 = "stream closed"
            r11.<init>(r12)     // Catch:{ InterruptedException -> 0x005e }
            throw r11     // Catch:{ InterruptedException -> 0x005e }
        L_0x002c:
            r10.wait()     // Catch:{ InterruptedException -> 0x005e }
            goto L_0x0012
        L_0x0030:
            long r4 = r10.d     // Catch:{ all -> 0x005c }
            long r4 = java.lang.Math.min(r14, r4)     // Catch:{ all -> 0x005c }
            int r2 = (int) r4     // Catch:{ all -> 0x005c }
            com.squareup.okhttp.internal.spdy.FrameWriter r4 = r10.i     // Catch:{ all -> 0x005c }
            int r4 = r4.maxDataLength()     // Catch:{ all -> 0x005c }
            int r2 = java.lang.Math.min(r2, r4)     // Catch:{ all -> 0x005c }
            long r4 = r10.d     // Catch:{ all -> 0x005c }
            long r6 = (long) r2     // Catch:{ all -> 0x005c }
            long r8 = r4 - r6
            r10.d = r8     // Catch:{ all -> 0x005c }
            monitor-exit(r10)     // Catch:{ all -> 0x005c }
            r4 = 0
            long r4 = r14 - r6
            com.squareup.okhttp.internal.spdy.FrameWriter r14 = r10.i
            if (r12 == 0) goto L_0x0056
            int r15 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r15 != 0) goto L_0x0056
            r15 = 1
            goto L_0x0057
        L_0x0056:
            r15 = 0
        L_0x0057:
            r14.data(r15, r11, r13, r2)
            r14 = r4
            goto L_0x000d
        L_0x005c:
            r11 = move-exception
            goto L_0x0064
        L_0x005e:
            java.io.InterruptedIOException r11 = new java.io.InterruptedIOException     // Catch:{ all -> 0x005c }
            r11.<init>()     // Catch:{ all -> 0x005c }
            throw r11     // Catch:{ all -> 0x005c }
        L_0x0064:
            monitor-exit(r10)     // Catch:{ all -> 0x005c }
            throw r11
        L_0x0066:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyConnection.writeData(int, boolean, okio.Buffer, long):void");
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2) {
        this.d += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, ErrorCode errorCode) {
        ExecutorService executorService = l;
        final int i3 = i2;
        final ErrorCode errorCode2 = errorCode;
        AnonymousClass1 r1 = new NamedRunnable("OkHttp %s stream %d", new Object[]{this.o, Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    SpdyConnection.this.b(i3, errorCode2);
                } catch (IOException unused) {
                }
            }
        };
        executorService.submit(r1);
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2, ErrorCode errorCode) {
        this.i.rstStream(i2, errorCode);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, long j2) {
        ExecutorService executorService = l;
        final int i3 = i2;
        final long j3 = j2;
        AnonymousClass2 r1 = new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.o, Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    SpdyConnection.this.i.windowUpdate(i3, j3);
                } catch (IOException unused) {
                }
            }
        };
        executorService.execute(r1);
    }

    public Ping ping() {
        int i2;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.r) {
                throw new IOException("shutdown");
            }
            i2 = this.w;
            this.w += 2;
            if (this.u == null) {
                this.u = new HashMap();
            }
            this.u.put(Integer.valueOf(i2), ping);
        }
        b(false, i2, 1330343787, ping);
        return ping;
    }

    /* access modifiers changed from: private */
    public void a(boolean z, int i2, int i3, Ping ping) {
        ExecutorService executorService = l;
        final boolean z2 = z;
        final int i4 = i2;
        final int i5 = i3;
        final Ping ping2 = ping;
        AnonymousClass3 r1 = new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.o, Integer.valueOf(i2), Integer.valueOf(i3)}) {
            public void execute() {
                try {
                    SpdyConnection.this.b(z2, i4, i5, ping2);
                } catch (IOException unused) {
                }
            }
        };
        executorService.execute(r1);
    }

    /* access modifiers changed from: private */
    public void b(boolean z, int i2, int i3, Ping ping) {
        synchronized (this.i) {
            if (ping != null) {
                try {
                    ping.a();
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.i.ping(z, i2, i3);
        }
    }

    /* access modifiers changed from: private */
    public synchronized Ping c(int i2) {
        return this.u != null ? (Ping) this.u.remove(Integer.valueOf(i2)) : null;
    }

    public void flush() {
        this.i.flush();
    }

    public void shutdown(ErrorCode errorCode) {
        synchronized (this.i) {
            synchronized (this) {
                if (!this.r) {
                    this.r = true;
                    int i2 = this.p;
                    this.i.goAway(i2, errorCode, Util.EMPTY_BYTE_ARRAY);
                }
            }
        }
    }

    public void close() {
        a(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    /* access modifiers changed from: private */
    public void a(ErrorCode errorCode, ErrorCode errorCode2) {
        int i2;
        SpdyStream[] spdyStreamArr;
        if (k || !Thread.holdsLock(this)) {
            Ping[] pingArr = null;
            try {
                shutdown(errorCode);
                e = null;
            } catch (IOException e2) {
                e = e2;
            }
            synchronized (this) {
                if (!this.n.isEmpty()) {
                    spdyStreamArr = (SpdyStream[]) this.n.values().toArray(new SpdyStream[this.n.size()]);
                    this.n.clear();
                    a(false);
                } else {
                    spdyStreamArr = null;
                }
                if (this.u != null) {
                    Ping[] pingArr2 = (Ping[]) this.u.values().toArray(new Ping[this.u.size()]);
                    this.u = null;
                    pingArr = pingArr2;
                }
            }
            if (spdyStreamArr != null) {
                IOException iOException = e;
                for (SpdyStream close : spdyStreamArr) {
                    try {
                        close.close(errorCode2);
                    } catch (IOException e3) {
                        if (iOException != null) {
                            iOException = e3;
                        }
                    }
                }
                e = iOException;
            }
            if (pingArr != null) {
                for (Ping c2 : pingArr) {
                    c2.c();
                }
            }
            try {
                this.i.close();
            } catch (IOException e4) {
                if (e == null) {
                    e = e4;
                }
            }
            try {
                this.h.close();
            } catch (IOException e5) {
                e = e5;
            }
            if (e != null) {
                throw e;
            }
            return;
        }
        throw new AssertionError();
    }

    public void sendConnectionPreface() {
        this.i.connectionPreface();
        this.i.settings(this.e);
        int e2 = this.e.e(65536);
        if (e2 != 65536) {
            this.i.windowUpdate(0, (long) (e2 - 65536));
        }
    }

    /* access modifiers changed from: private */
    public boolean d(int i2) {
        return this.a == Protocol.HTTP_2 && i2 != 0 && (i2 & 1) == 0;
    }

    /* access modifiers changed from: private */
    public void a(int i2, List<Header> list) {
        synchronized (this) {
            if (this.y.contains(Integer.valueOf(i2))) {
                a(i2, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.y.add(Integer.valueOf(i2));
            ExecutorService executorService = this.t;
            final int i3 = i2;
            final List<Header> list2 = list;
            AnonymousClass4 r1 = new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.o, Integer.valueOf(i2)}) {
                public void execute() {
                    if (SpdyConnection.this.v.onRequest(i3, list2)) {
                        try {
                            SpdyConnection.this.i.rstStream(i3, ErrorCode.CANCEL);
                            synchronized (SpdyConnection.this) {
                                SpdyConnection.this.y.remove(Integer.valueOf(i3));
                            }
                        } catch (IOException unused) {
                        }
                    }
                }
            };
            executorService.execute(r1);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, List<Header> list, boolean z) {
        ExecutorService executorService = this.t;
        final int i3 = i2;
        final List<Header> list2 = list;
        final boolean z2 = z;
        AnonymousClass5 r1 = new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.o, Integer.valueOf(i2)}) {
            public void execute() {
                boolean onHeaders = SpdyConnection.this.v.onHeaders(i3, list2, z2);
                if (onHeaders) {
                    try {
                        SpdyConnection.this.i.rstStream(i3, ErrorCode.CANCEL);
                    } catch (IOException unused) {
                        return;
                    }
                }
                if (onHeaders || z2) {
                    synchronized (SpdyConnection.this) {
                        SpdyConnection.this.y.remove(Integer.valueOf(i3));
                    }
                }
            }
        };
        executorService.execute(r1);
    }

    /* access modifiers changed from: private */
    public void a(int i2, BufferedSource bufferedSource, int i3, boolean z) {
        final Buffer buffer = new Buffer();
        long j2 = (long) i3;
        bufferedSource.require(j2);
        bufferedSource.read(buffer, j2);
        if (buffer.size() != j2) {
            StringBuilder sb = new StringBuilder();
            sb.append(buffer.size());
            sb.append(" != ");
            sb.append(i3);
            throw new IOException(sb.toString());
        }
        ExecutorService executorService = this.t;
        final int i4 = i2;
        final int i5 = i3;
        final boolean z2 = z;
        AnonymousClass6 r0 = new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.o, Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    boolean onData = SpdyConnection.this.v.onData(i4, buffer, i5, z2);
                    if (onData) {
                        SpdyConnection.this.i.rstStream(i4, ErrorCode.CANCEL);
                    }
                    if (onData || z2) {
                        synchronized (SpdyConnection.this) {
                            SpdyConnection.this.y.remove(Integer.valueOf(i4));
                        }
                    }
                } catch (IOException unused) {
                }
            }
        };
        executorService.execute(r0);
    }

    /* access modifiers changed from: private */
    public void c(int i2, ErrorCode errorCode) {
        ExecutorService executorService = this.t;
        final int i3 = i2;
        final ErrorCode errorCode2 = errorCode;
        AnonymousClass7 r1 = new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.o, Integer.valueOf(i2)}) {
            public void execute() {
                SpdyConnection.this.v.onReset(i3, errorCode2);
                synchronized (SpdyConnection.this) {
                    SpdyConnection.this.y.remove(Integer.valueOf(i3));
                }
            }
        };
        executorService.execute(r1);
    }
}
