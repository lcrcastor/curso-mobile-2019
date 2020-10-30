package okhttp3.internal.http2;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class Http2Connection implements Closeable {
    static final /* synthetic */ boolean r = true;
    /* access modifiers changed from: private */
    public static final ExecutorService s;
    final boolean a;
    final Listener b;
    final Map<Integer, Http2Stream> c = new LinkedHashMap();
    final String d;
    int e;
    int f;
    boolean g;
    final PushObserver h;
    long i = 0;
    long j;
    Settings k = new Settings();
    final Settings l = new Settings();
    boolean m = false;
    final Socket n;
    final Http2Writer o;
    final ReaderRunnable p;
    final Set<Integer> q = new LinkedHashSet();
    /* access modifiers changed from: private */
    public final ScheduledExecutorService t;
    private final ExecutorService u;
    /* access modifiers changed from: private */
    public boolean v;

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() {
            public void onStream(Http2Stream http2Stream) {
                http2Stream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public void onSettings(Http2Connection http2Connection) {
        }

        public abstract void onStream(Http2Stream http2Stream);
    }

    public static class Builder {
        Socket a;
        String b;
        BufferedSource c;
        BufferedSink d;
        Listener e = Listener.REFUSE_INCOMING_STREAMS;
        PushObserver f = PushObserver.CANCEL;
        boolean g;
        int h;

        public Builder(boolean z) {
            this.g = z;
        }

        public Builder socket(Socket socket) {
            return socket(socket, ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String str, BufferedSource bufferedSource, BufferedSink bufferedSink) {
            this.a = socket;
            this.b = str;
            this.c = bufferedSource;
            this.d = bufferedSink;
            return this;
        }

        public Builder listener(Listener listener) {
            this.e = listener;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.f = pushObserver;
            return this;
        }

        public Builder pingIntervalMillis(int i) {
            this.h = i;
            return this;
        }

        public Http2Connection build() {
            return new Http2Connection(this);
        }
    }

    final class PingRunnable extends NamedRunnable {
        final boolean a;
        final int b;
        final int c;

        PingRunnable(boolean z, int i, int i2) {
            super("OkHttp %s ping %08x%08x", Http2Connection.this.d, Integer.valueOf(i), Integer.valueOf(i2));
            this.a = z;
            this.b = i;
            this.c = i2;
        }

        public void execute() {
            Http2Connection.this.a(this.a, this.b, this.c);
        }
    }

    class ReaderRunnable extends NamedRunnable implements Handler {
        final Http2Reader a;

        public void a() {
        }

        public void a(int i, int i2, int i3, boolean z) {
        }

        ReaderRunnable(Http2Reader http2Reader) {
            super("OkHttp %s", Http2Connection.this.d);
            this.a = http2Reader;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Can't wrap try/catch for region: R(5:12|11|14|15|(7:16|17|18|19|20|21|23)) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x001c, code lost:
            r2 = th;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x001e */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void execute() {
            /*
                r5 = this;
                okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.INTERNAL_ERROR
                okhttp3.internal.http2.ErrorCode r1 = okhttp3.internal.http2.ErrorCode.INTERNAL_ERROR
                okhttp3.internal.http2.Http2Reader r2 = r5.a     // Catch:{ IOException -> 0x001e }
                r2.a(r5)     // Catch:{ IOException -> 0x001e }
            L_0x0009:
                okhttp3.internal.http2.Http2Reader r2 = r5.a     // Catch:{ IOException -> 0x001e }
                r3 = 0
                boolean r2 = r2.a(r3, r5)     // Catch:{ IOException -> 0x001e }
                if (r2 == 0) goto L_0x0013
                goto L_0x0009
            L_0x0013:
                okhttp3.internal.http2.ErrorCode r2 = okhttp3.internal.http2.ErrorCode.NO_ERROR     // Catch:{ IOException -> 0x001e }
                okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.CANCEL     // Catch:{ IOException -> 0x001a }
                okhttp3.internal.http2.Http2Connection r1 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ IOException -> 0x0027 }
                goto L_0x0024
            L_0x001a:
                r0 = r2
                goto L_0x001e
            L_0x001c:
                r2 = move-exception
                goto L_0x0031
            L_0x001e:
                okhttp3.internal.http2.ErrorCode r2 = okhttp3.internal.http2.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x001c }
                okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x002d }
                okhttp3.internal.http2.Http2Connection r1 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ IOException -> 0x0027 }
            L_0x0024:
                r1.a(r2, r0)     // Catch:{ IOException -> 0x0027 }
            L_0x0027:
                okhttp3.internal.http2.Http2Reader r0 = r5.a
                okhttp3.internal.Util.closeQuietly(r0)
                return
            L_0x002d:
                r0 = move-exception
                r4 = r2
                r2 = r0
                r0 = r4
            L_0x0031:
                okhttp3.internal.http2.Http2Connection r3 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ IOException -> 0x0036 }
                r3.a(r0, r1)     // Catch:{ IOException -> 0x0036 }
            L_0x0036:
                okhttp3.internal.http2.Http2Reader r0 = r5.a
                okhttp3.internal.Util.closeQuietly(r0)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.ReaderRunnable.execute():void");
        }

        public void a(boolean z, int i, BufferedSource bufferedSource, int i2) {
            if (Http2Connection.this.c(i)) {
                Http2Connection.this.a(i, bufferedSource, i2, z);
                return;
            }
            Http2Stream a2 = Http2Connection.this.a(i);
            if (a2 == null) {
                Http2Connection.this.a(i, ErrorCode.PROTOCOL_ERROR);
                long j = (long) i2;
                Http2Connection.this.a(j);
                bufferedSource.skip(j);
                return;
            }
            a2.a(bufferedSource, i2);
            if (z) {
                a2.a();
            }
        }

        /* JADX INFO: used method not loaded: okhttp3.internal.http2.Http2Stream.a(java.util.List):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0071, code lost:
            r0.a((java.util.List) r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0074, code lost:
            if (r10 == false) goto L_0x0079;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0076, code lost:
            r0.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0079, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(boolean r10, int r11, int r12, java.util.List<okhttp3.internal.http2.Header> r13) {
            /*
                r9 = this;
                okhttp3.internal.http2.Http2Connection r12 = okhttp3.internal.http2.Http2Connection.this
                boolean r12 = r12.c(r11)
                if (r12 == 0) goto L_0x000e
                okhttp3.internal.http2.Http2Connection r12 = okhttp3.internal.http2.Http2Connection.this
                r12.a(r11, r13, r10)
                return
            L_0x000e:
                okhttp3.internal.http2.Http2Connection r12 = okhttp3.internal.http2.Http2Connection.this
                monitor-enter(r12)
                okhttp3.internal.http2.Http2Connection r0 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.Http2Stream r0 = r0.a(r11)     // Catch:{ all -> 0x007a }
                if (r0 != 0) goto L_0x0070
                okhttp3.internal.http2.Http2Connection r0 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                boolean r0 = r0.g     // Catch:{ all -> 0x007a }
                if (r0 == 0) goto L_0x0021
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                return
            L_0x0021:
                okhttp3.internal.http2.Http2Connection r0 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                int r0 = r0.e     // Catch:{ all -> 0x007a }
                if (r11 > r0) goto L_0x0029
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                return
            L_0x0029:
                int r0 = r11 % 2
                okhttp3.internal.http2.Http2Connection r1 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                int r1 = r1.f     // Catch:{ all -> 0x007a }
                r2 = 2
                int r1 = r1 % r2
                if (r0 != r1) goto L_0x0035
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                return
            L_0x0035:
                okhttp3.internal.http2.Http2Stream r0 = new okhttp3.internal.http2.Http2Stream     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.Http2Connection r5 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                r6 = 0
                r3 = r0
                r4 = r11
                r7 = r10
                r8 = r13
                r3.<init>(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.Http2Connection r10 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                r10.e = r11     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.Http2Connection r10 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r10 = r10.c     // Catch:{ all -> 0x007a }
                java.lang.Integer r13 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x007a }
                r10.put(r13, r0)     // Catch:{ all -> 0x007a }
                java.util.concurrent.ExecutorService r10 = okhttp3.internal.http2.Http2Connection.s     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.Http2Connection$ReaderRunnable$1 r13 = new okhttp3.internal.http2.Http2Connection$ReaderRunnable$1     // Catch:{ all -> 0x007a }
                java.lang.String r1 = "OkHttp %s stream %d"
                java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x007a }
                r3 = 0
                okhttp3.internal.http2.Http2Connection r4 = okhttp3.internal.http2.Http2Connection.this     // Catch:{ all -> 0x007a }
                java.lang.String r4 = r4.d     // Catch:{ all -> 0x007a }
                r2[r3] = r4     // Catch:{ all -> 0x007a }
                r3 = 1
                java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x007a }
                r2[r3] = r11     // Catch:{ all -> 0x007a }
                r13.<init>(r1, r2, r0)     // Catch:{ all -> 0x007a }
                r10.execute(r13)     // Catch:{ all -> 0x007a }
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                return
            L_0x0070:
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                r0.a(r13)
                if (r10 == 0) goto L_0x0079
                r0.a()
            L_0x0079:
                return
            L_0x007a:
                r10 = move-exception
                monitor-exit(r12)     // Catch:{ all -> 0x007a }
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.ReaderRunnable.a(boolean, int, int, java.util.List):void");
        }

        public void a(int i, ErrorCode errorCode) {
            if (Http2Connection.this.c(i)) {
                Http2Connection.this.c(i, errorCode);
                return;
            }
            Http2Stream b2 = Http2Connection.this.b(i);
            if (b2 != null) {
                b2.a(errorCode);
            }
        }

        public void a(boolean z, Settings settings) {
            Http2Stream[] http2StreamArr;
            long j;
            int i;
            synchronized (Http2Connection.this) {
                int d = Http2Connection.this.l.d();
                if (z) {
                    Http2Connection.this.l.a();
                }
                Http2Connection.this.l.a(settings);
                a(settings);
                int d2 = Http2Connection.this.l.d();
                http2StreamArr = null;
                if (d2 == -1 || d2 == d) {
                    j = 0;
                } else {
                    j = (long) (d2 - d);
                    if (!Http2Connection.this.m) {
                        Http2Connection.this.m = true;
                    }
                    if (!Http2Connection.this.c.isEmpty()) {
                        http2StreamArr = (Http2Stream[]) Http2Connection.this.c.values().toArray(new Http2Stream[Http2Connection.this.c.size()]);
                    }
                }
                Http2Connection.s.execute(new NamedRunnable("OkHttp %s settings", Http2Connection.this.d) {
                    public void execute() {
                        Http2Connection.this.b.onSettings(Http2Connection.this);
                    }
                });
            }
            if (http2StreamArr != null && j != 0) {
                for (Http2Stream http2Stream : http2StreamArr) {
                    synchronized (http2Stream) {
                        http2Stream.a(j);
                    }
                }
            }
        }

        private void a(final Settings settings) {
            try {
                Http2Connection.this.t.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{Http2Connection.this.d}) {
                    public void execute() {
                        try {
                            Http2Connection.this.o.a(settings);
                        } catch (IOException unused) {
                            Http2Connection.this.b();
                        }
                    }
                });
            } catch (RejectedExecutionException unused) {
            }
        }

        public void a(boolean z, int i, int i2) {
            if (z) {
                synchronized (Http2Connection.this) {
                    Http2Connection.this.v = false;
                    Http2Connection.this.notifyAll();
                }
                return;
            }
            try {
                Http2Connection.this.t.execute(new PingRunnable(true, i, i2));
            } catch (RejectedExecutionException unused) {
            }
        }

        public void a(int i, ErrorCode errorCode, ByteString byteString) {
            Http2Stream[] http2StreamArr;
            byteString.size();
            synchronized (Http2Connection.this) {
                http2StreamArr = (Http2Stream[]) Http2Connection.this.c.values().toArray(new Http2Stream[Http2Connection.this.c.size()]);
                Http2Connection.this.g = true;
            }
            for (Http2Stream http2Stream : http2StreamArr) {
                if (http2Stream.getId() > i && http2Stream.isLocallyInitiated()) {
                    http2Stream.a(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.b(http2Stream.getId());
                }
            }
        }

        public void a(int i, long j) {
            if (i == 0) {
                synchronized (Http2Connection.this) {
                    Http2Connection.this.j += j;
                    Http2Connection.this.notifyAll();
                }
                return;
            }
            Http2Stream a2 = Http2Connection.this.a(i);
            if (a2 != null) {
                synchronized (a2) {
                    a2.a(j);
                }
            }
        }

        public void a(int i, int i2, List<Header> list) {
            Http2Connection.this.a(i2, list);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean c(int i2) {
        return i2 != 0 && (i2 & 1) == 0;
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, SubsamplingScaleImageView.TILE_SIZE_AUTO, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));
        s = threadPoolExecutor;
    }

    Http2Connection(Builder builder) {
        Builder builder2 = builder;
        this.h = builder2.f;
        this.a = builder2.g;
        this.b = builder2.e;
        this.f = builder2.g ? 1 : 2;
        if (builder2.g) {
            this.f += 2;
        }
        if (builder2.g) {
            this.k.a(7, 16777216);
        }
        this.d = builder2.b;
        this.t = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", this.d), false));
        if (builder2.h != 0) {
            this.t.scheduleAtFixedRate(new PingRunnable(false, 0, 0), (long) builder2.h, (long) builder2.h, TimeUnit.MILLISECONDS);
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", this.d), true));
        this.u = threadPoolExecutor;
        this.l.a(7, 65535);
        this.l.a(5, 16384);
        this.j = (long) this.l.d();
        this.n = builder2.a;
        this.o = new Http2Writer(builder2.d, this.a);
        this.p = new ReaderRunnable(new Http2Reader(builder2.c, this.a));
    }

    public Protocol getProtocol() {
        return Protocol.HTTP_2;
    }

    public synchronized int openStreamCount() {
        return this.c.size();
    }

    /* access modifiers changed from: 0000 */
    public synchronized Http2Stream a(int i2) {
        return (Http2Stream) this.c.get(Integer.valueOf(i2));
    }

    /* access modifiers changed from: 0000 */
    public synchronized Http2Stream b(int i2) {
        Http2Stream http2Stream;
        http2Stream = (Http2Stream) this.c.remove(Integer.valueOf(i2));
        notifyAll();
        return http2Stream;
    }

    public synchronized int maxConcurrentStreams() {
        return this.l.c(SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(long j2) {
        this.i += j2;
        if (this.i >= ((long) (this.k.d() / 2))) {
            a(0, this.i);
            this.i = 0;
        }
    }

    public Http2Stream pushStream(int i2, List<Header> list, boolean z) {
        if (!this.a) {
            return b(i2, list, z);
        }
        throw new IllegalStateException("Client cannot push requests.");
    }

    public Http2Stream newStream(List<Header> list, boolean z) {
        return b(0, list, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.http2.Http2Stream b(int r11, java.util.List<okhttp3.internal.http2.Header> r12, boolean r13) {
        /*
            r10 = this;
            r6 = r13 ^ 1
            r4 = 0
            okhttp3.internal.http2.Http2Writer r7 = r10.o
            monitor-enter(r7)
            monitor-enter(r10)     // Catch:{ all -> 0x0078 }
            int r0 = r10.f     // Catch:{ all -> 0x0075 }
            r1 = 1073741823(0x3fffffff, float:1.9999999)
            if (r0 <= r1) goto L_0x0013
            okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.REFUSED_STREAM     // Catch:{ all -> 0x0075 }
            r10.shutdown(r0)     // Catch:{ all -> 0x0075 }
        L_0x0013:
            boolean r0 = r10.g     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x001d
            okhttp3.internal.http2.ConnectionShutdownException r11 = new okhttp3.internal.http2.ConnectionShutdownException     // Catch:{ all -> 0x0075 }
            r11.<init>()     // Catch:{ all -> 0x0075 }
            throw r11     // Catch:{ all -> 0x0075 }
        L_0x001d:
            int r8 = r10.f     // Catch:{ all -> 0x0075 }
            int r0 = r10.f     // Catch:{ all -> 0x0075 }
            int r0 = r0 + 2
            r10.f = r0     // Catch:{ all -> 0x0075 }
            okhttp3.internal.http2.Http2Stream r9 = new okhttp3.internal.http2.Http2Stream     // Catch:{ all -> 0x0075 }
            r0 = r9
            r1 = r8
            r2 = r10
            r3 = r6
            r5 = r12
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0075 }
            if (r13 == 0) goto L_0x0042
            long r0 = r10.j     // Catch:{ all -> 0x0075 }
            r2 = 0
            int r13 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r13 == 0) goto L_0x0042
            long r0 = r9.b     // Catch:{ all -> 0x0075 }
            int r13 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r13 != 0) goto L_0x0040
            goto L_0x0042
        L_0x0040:
            r13 = 0
            goto L_0x0043
        L_0x0042:
            r13 = 1
        L_0x0043:
            boolean r0 = r9.isOpen()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x0052
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r0 = r10.c     // Catch:{ all -> 0x0075 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0075 }
            r0.put(r1, r9)     // Catch:{ all -> 0x0075 }
        L_0x0052:
            monitor-exit(r10)     // Catch:{ all -> 0x0075 }
            if (r11 != 0) goto L_0x005b
            okhttp3.internal.http2.Http2Writer r0 = r10.o     // Catch:{ all -> 0x0078 }
            r0.a(r6, r8, r11, r12)     // Catch:{ all -> 0x0078 }
            goto L_0x006c
        L_0x005b:
            boolean r0 = r10.a     // Catch:{ all -> 0x0078 }
            if (r0 == 0) goto L_0x0067
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0078 }
            java.lang.String r12 = "client streams shouldn't have associated stream IDs"
            r11.<init>(r12)     // Catch:{ all -> 0x0078 }
            throw r11     // Catch:{ all -> 0x0078 }
        L_0x0067:
            okhttp3.internal.http2.Http2Writer r0 = r10.o     // Catch:{ all -> 0x0078 }
            r0.a(r11, r8, r12)     // Catch:{ all -> 0x0078 }
        L_0x006c:
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            if (r13 == 0) goto L_0x0074
            okhttp3.internal.http2.Http2Writer r11 = r10.o
            r11.b()
        L_0x0074:
            return r9
        L_0x0075:
            r11 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0075 }
            throw r11     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r11 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.b(int, java.util.List, boolean):okhttp3.internal.http2.Http2Stream");
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, boolean z, List<Header> list) {
        this.o.a(z, i2, list);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:26|27|28) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r2 = java.lang.Math.min((int) java.lang.Math.min(r14, r10.j), r10.o.c());
        r6 = (long) r2;
        r10.j -= r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006a, code lost:
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
            okhttp3.internal.http2.Http2Writer r14 = r10.o
            r14.a(r12, r11, r13, r3)
            return
        L_0x000d:
            int r2 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x006d
            monitor-enter(r10)
        L_0x0012:
            long r4 = r10.j     // Catch:{ InterruptedException -> 0x005e }
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x0030
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r2 = r10.c     // Catch:{ InterruptedException -> 0x005e }
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
            long r4 = r10.j     // Catch:{ all -> 0x005c }
            long r4 = java.lang.Math.min(r14, r4)     // Catch:{ all -> 0x005c }
            int r2 = (int) r4     // Catch:{ all -> 0x005c }
            okhttp3.internal.http2.Http2Writer r4 = r10.o     // Catch:{ all -> 0x005c }
            int r4 = r4.c()     // Catch:{ all -> 0x005c }
            int r2 = java.lang.Math.min(r2, r4)     // Catch:{ all -> 0x005c }
            long r4 = r10.j     // Catch:{ all -> 0x005c }
            long r6 = (long) r2     // Catch:{ all -> 0x005c }
            long r8 = r4 - r6
            r10.j = r8     // Catch:{ all -> 0x005c }
            monitor-exit(r10)     // Catch:{ all -> 0x005c }
            r4 = 0
            long r4 = r14 - r6
            okhttp3.internal.http2.Http2Writer r14 = r10.o
            if (r12 == 0) goto L_0x0056
            int r15 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r15 != 0) goto L_0x0056
            r15 = 1
            goto L_0x0057
        L_0x0056:
            r15 = 0
        L_0x0057:
            r14.a(r15, r11, r13, r2)
            r14 = r4
            goto L_0x000d
        L_0x005c:
            r11 = move-exception
            goto L_0x006b
        L_0x005e:
            java.lang.Thread r11 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x005c }
            r11.interrupt()     // Catch:{ all -> 0x005c }
            java.io.InterruptedIOException r11 = new java.io.InterruptedIOException     // Catch:{ all -> 0x005c }
            r11.<init>()     // Catch:{ all -> 0x005c }
            throw r11     // Catch:{ all -> 0x005c }
        L_0x006b:
            monitor-exit(r10)     // Catch:{ all -> 0x005c }
            throw r11
        L_0x006d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.writeData(int, boolean, okio.Buffer, long):void");
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, ErrorCode errorCode) {
        try {
            ScheduledExecutorService scheduledExecutorService = this.t;
            final int i3 = i2;
            final ErrorCode errorCode2 = errorCode;
            AnonymousClass1 r1 = new NamedRunnable("OkHttp %s stream %d", new Object[]{this.d, Integer.valueOf(i2)}) {
                public void execute() {
                    try {
                        Http2Connection.this.b(i3, errorCode2);
                    } catch (IOException unused) {
                        Http2Connection.this.b();
                    }
                }
            };
            scheduledExecutorService.execute(r1);
        } catch (RejectedExecutionException unused) {
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2, ErrorCode errorCode) {
        this.o.a(i2, errorCode);
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, long j2) {
        try {
            ScheduledExecutorService scheduledExecutorService = this.t;
            final int i3 = i2;
            final long j3 = j2;
            AnonymousClass2 r1 = new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.d, Integer.valueOf(i2)}) {
                public void execute() {
                    try {
                        Http2Connection.this.o.a(i3, j3);
                    } catch (IOException unused) {
                        Http2Connection.this.b();
                    }
                }
            };
            scheduledExecutorService.execute(r1);
        } catch (RejectedExecutionException unused) {
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z, int i2, int i3) {
        boolean z2;
        if (!z) {
            synchronized (this) {
                z2 = this.v;
                this.v = true;
            }
            if (z2) {
                b();
                return;
            }
        }
        try {
            this.o.a(z, i2, i3);
        } catch (IOException unused) {
            b();
        }
    }

    public void flush() {
        this.o.b();
    }

    public void shutdown(ErrorCode errorCode) {
        synchronized (this.o) {
            synchronized (this) {
                if (!this.g) {
                    this.g = true;
                    int i2 = this.e;
                    this.o.a(i2, errorCode, Util.EMPTY_BYTE_ARRAY);
                }
            }
        }
    }

    public void close() {
        a(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    /* access modifiers changed from: 0000 */
    public void a(ErrorCode errorCode, ErrorCode errorCode2) {
        if (r || !Thread.holdsLock(this)) {
            Http2Stream[] http2StreamArr = null;
            try {
                shutdown(errorCode);
                e = null;
            } catch (IOException e2) {
                e = e2;
            }
            synchronized (this) {
                if (!this.c.isEmpty()) {
                    http2StreamArr = (Http2Stream[]) this.c.values().toArray(new Http2Stream[this.c.size()]);
                    this.c.clear();
                }
            }
            if (http2StreamArr != null) {
                for (Http2Stream close : http2StreamArr) {
                    try {
                        close.close(errorCode2);
                    } catch (IOException e3) {
                        if (e != null) {
                            e = e3;
                        }
                    }
                }
            }
            try {
                this.o.close();
            } catch (IOException e4) {
                if (e == null) {
                    e = e4;
                }
            }
            try {
                this.n.close();
            } catch (IOException e5) {
                e = e5;
            }
            this.t.shutdown();
            this.u.shutdown();
            if (e != null) {
                throw e;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: private */
    public void b() {
        try {
            a(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR);
        } catch (IOException unused) {
        }
    }

    public void start() {
        a(true);
    }

    /* access modifiers changed from: 0000 */
    public void a(boolean z) {
        if (z) {
            this.o.a();
            this.o.b(this.k);
            int d2 = this.k.d();
            if (d2 != 65535) {
                this.o.a(0, (long) (d2 - 65535));
            }
        }
        new Thread(this.p).start();
    }

    public void setSettings(Settings settings) {
        synchronized (this.o) {
            synchronized (this) {
                if (this.g) {
                    throw new ConnectionShutdownException();
                }
                this.k.a(settings);
            }
            this.o.b(settings);
        }
    }

    public synchronized boolean isShutdown() {
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r3 = r8;
        r6 = r9;
        r7 = r10;
        r2 = new okhttp3.internal.http2.Http2Connection.AnonymousClass3(r3, "OkHttp %s Push Request[%s]", new java.lang.Object[]{r8.d, java.lang.Integer.valueOf(r9)});
        a((okhttp3.internal.NamedRunnable) r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r9, java.util.List<okhttp3.internal.http2.Header> r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.util.Set<java.lang.Integer> r0 = r8.q     // Catch:{ all -> 0x003c }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x003c }
            boolean r0 = r0.contains(r1)     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x0014
            okhttp3.internal.http2.ErrorCode r10 = okhttp3.internal.http2.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x003c }
            r8.a(r9, r10)     // Catch:{ all -> 0x003c }
            monitor-exit(r8)     // Catch:{ all -> 0x003c }
            return
        L_0x0014:
            java.util.Set<java.lang.Integer> r0 = r8.q     // Catch:{ all -> 0x003c }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x003c }
            r0.add(r1)     // Catch:{ all -> 0x003c }
            monitor-exit(r8)     // Catch:{ all -> 0x003c }
            okhttp3.internal.http2.Http2Connection$3 r0 = new okhttp3.internal.http2.Http2Connection$3     // Catch:{ RejectedExecutionException -> 0x003b }
            java.lang.String r4 = "OkHttp %s Push Request[%s]"
            r1 = 2
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ RejectedExecutionException -> 0x003b }
            r1 = 0
            java.lang.String r2 = r8.d     // Catch:{ RejectedExecutionException -> 0x003b }
            r5[r1] = r2     // Catch:{ RejectedExecutionException -> 0x003b }
            r1 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)     // Catch:{ RejectedExecutionException -> 0x003b }
            r5[r1] = r2     // Catch:{ RejectedExecutionException -> 0x003b }
            r2 = r0
            r3 = r8
            r6 = r9
            r7 = r10
            r2.<init>(r4, r5, r6, r7)     // Catch:{ RejectedExecutionException -> 0x003b }
            r8.a(r0)     // Catch:{ RejectedExecutionException -> 0x003b }
        L_0x003b:
            return
        L_0x003c:
            r9 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x003c }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.a(int, java.util.List):void");
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2, List<Header> list, boolean z) {
        try {
            final int i3 = i2;
            final List<Header> list2 = list;
            final boolean z2 = z;
            AnonymousClass4 r0 = new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.d, Integer.valueOf(i2)}) {
                public void execute() {
                    boolean onHeaders = Http2Connection.this.h.onHeaders(i3, list2, z2);
                    if (onHeaders) {
                        try {
                            Http2Connection.this.o.a(i3, ErrorCode.CANCEL);
                        } catch (IOException unused) {
                            return;
                        }
                    }
                    if (onHeaders || z2) {
                        synchronized (Http2Connection.this) {
                            Http2Connection.this.q.remove(Integer.valueOf(i3));
                        }
                    }
                }
            };
            a((NamedRunnable) r0);
        } catch (RejectedExecutionException unused) {
        }
    }

    /* access modifiers changed from: 0000 */
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
        final int i4 = i2;
        final int i5 = i3;
        final boolean z2 = z;
        AnonymousClass5 r0 = new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.d, Integer.valueOf(i2)}) {
            public void execute() {
                try {
                    boolean onData = Http2Connection.this.h.onData(i4, buffer, i5, z2);
                    if (onData) {
                        Http2Connection.this.o.a(i4, ErrorCode.CANCEL);
                    }
                    if (onData || z2) {
                        synchronized (Http2Connection.this) {
                            Http2Connection.this.q.remove(Integer.valueOf(i4));
                        }
                    }
                } catch (IOException unused) {
                }
            }
        };
        a((NamedRunnable) r0);
    }

    /* access modifiers changed from: 0000 */
    public void c(int i2, ErrorCode errorCode) {
        final int i3 = i2;
        final ErrorCode errorCode2 = errorCode;
        AnonymousClass6 r0 = new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.d, Integer.valueOf(i2)}) {
            public void execute() {
                Http2Connection.this.h.onReset(i3, errorCode2);
                synchronized (Http2Connection.this) {
                    Http2Connection.this.q.remove(Integer.valueOf(i3));
                }
            }
        };
        a((NamedRunnable) r0);
    }

    private synchronized void a(NamedRunnable namedRunnable) {
        if (!isShutdown()) {
            this.u.execute(namedRunnable);
        }
    }
}
