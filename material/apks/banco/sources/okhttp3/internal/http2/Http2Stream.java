package okhttp3.internal.http2;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
    static final /* synthetic */ boolean i = true;
    long a = 0;
    long b;
    final int c;
    final Http2Connection d;
    final FramingSink e;
    final StreamTimeout f = new StreamTimeout();
    final StreamTimeout g = new StreamTimeout();
    ErrorCode h = null;
    private final List<Header> j;
    private List<Header> k;
    private boolean l;
    private final FramingSource m;

    final class FramingSink implements Sink {
        static final /* synthetic */ boolean c = true;
        boolean a;
        boolean b;
        private final Buffer e = new Buffer();

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSink() {
        }

        public void write(Buffer buffer, long j) {
            if (c || !Thread.holdsLock(Http2Stream.this)) {
                this.e.write(buffer, j);
                while (this.e.size() >= PlaybackStateCompat.ACTION_PREPARE) {
                    a(false);
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX INFO: finally extract failed */
        private void a(boolean z) {
            long min;
            synchronized (Http2Stream.this) {
                Http2Stream.this.g.enter();
                while (Http2Stream.this.b <= 0 && !this.b && !this.a && Http2Stream.this.h == null) {
                    try {
                        Http2Stream.this.d();
                    } catch (Throwable th) {
                        Http2Stream.this.g.a();
                        throw th;
                    }
                }
                Http2Stream.this.g.a();
                Http2Stream.this.c();
                min = Math.min(Http2Stream.this.b, this.e.size());
                Http2Stream.this.b -= min;
            }
            Http2Stream.this.g.enter();
            try {
                Http2Stream.this.d.writeData(Http2Stream.this.c, z && min == this.e.size(), this.e, min);
            } finally {
                Http2Stream.this.g.a();
            }
        }

        public void flush() {
            if (c || !Thread.holdsLock(Http2Stream.this)) {
                synchronized (Http2Stream.this) {
                    Http2Stream.this.c();
                }
                while (this.e.size() > 0) {
                    a(false);
                    Http2Stream.this.d.flush();
                }
                return;
            }
            throw new AssertionError();
        }

        public Timeout timeout() {
            return Http2Stream.this.g;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
            if (r8.d.e.b != false) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002f, code lost:
            if (r8.e.size() <= 0) goto L_0x003f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
            if (r8.e.size() <= 0) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
            a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
            r8.d.d.writeData(r8.d.c, true, null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x004e, code lost:
            r2 = r8.d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0050, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r8.a = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0053, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0054, code lost:
            r8.d.d.flush();
            r8.d.b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0060, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r8 = this;
                boolean r0 = c
                if (r0 != 0) goto L_0x0012
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 == 0) goto L_0x0012
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            L_0x0012:
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r0)
                boolean r1 = r8.a     // Catch:{ all -> 0x0064 }
                if (r1 == 0) goto L_0x001b
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                return
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$FramingSink r0 = r0.e
                boolean r0 = r0.b
                r1 = 1
                if (r0 != 0) goto L_0x004e
                okio.Buffer r0 = r8.e
                long r2 = r0.size()
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x003f
            L_0x0031:
                okio.Buffer r0 = r8.e
                long r2 = r0.size()
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x004e
                r8.a(r1)
                goto L_0x0031
            L_0x003f:
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r2 = r0.d
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                int r3 = r0.c
                r4 = 1
                r5 = 0
                r6 = 0
                r2.writeData(r3, r4, r5, r6)
            L_0x004e:
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r2)
                r8.a = r1     // Catch:{ all -> 0x0061 }
                monitor-exit(r2)     // Catch:{ all -> 0x0061 }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r0 = r0.d
                r0.flush()
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                r0.b()
                return
            L_0x0061:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0061 }
                throw r0
            L_0x0064:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0064 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.FramingSink.close():void");
        }
    }

    final class FramingSource implements Source {
        static final /* synthetic */ boolean c = true;
        boolean a;
        boolean b;
        private final Buffer e = new Buffer();
        private final Buffer f = new Buffer();
        private final long g;

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSource(long j) {
            this.g = j;
        }

        public long read(Buffer buffer, long j) {
            ErrorCode errorCode;
            long j2;
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(j);
                throw new IllegalArgumentException(sb.toString());
            }
            synchronized (Http2Stream.this) {
                a();
                if (this.a) {
                    throw new IOException("stream closed");
                }
                errorCode = Http2Stream.this.h;
                if (this.f.size() > 0) {
                    j2 = this.f.read(buffer, Math.min(j, this.f.size()));
                    Http2Stream.this.a += j2;
                } else {
                    j2 = -1;
                }
                if (errorCode == null && Http2Stream.this.a >= ((long) (Http2Stream.this.d.k.d() / 2))) {
                    Http2Stream.this.d.a(Http2Stream.this.c, Http2Stream.this.a);
                    Http2Stream.this.a = 0;
                }
            }
            if (j2 != -1) {
                a(j2);
                return j2;
            } else if (errorCode == null) {
                return -1;
            } else {
                throw new StreamResetException(errorCode);
            }
        }

        private void a(long j) {
            if (c || !Thread.holdsLock(Http2Stream.this)) {
                Http2Stream.this.d.a(j);
                return;
            }
            throw new AssertionError();
        }

        private void a() {
            Http2Stream.this.f.enter();
            while (this.f.size() == 0 && !this.b && !this.a && Http2Stream.this.h == null) {
                try {
                    Http2Stream.this.d();
                } finally {
                    Http2Stream.this.f.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(BufferedSource bufferedSource, long j) {
            boolean z;
            boolean z2;
            boolean z3;
            if (c || !Thread.holdsLock(Http2Stream.this)) {
                while (j > 0) {
                    synchronized (Http2Stream.this) {
                        z = this.b;
                        z2 = false;
                        z3 = j + this.f.size() > this.g;
                    }
                    if (z3) {
                        bufferedSource.skip(j);
                        Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    } else if (z) {
                        bufferedSource.skip(j);
                        return;
                    } else {
                        long read = bufferedSource.read(this.e, j);
                        if (read == -1) {
                            throw new EOFException();
                        }
                        long j2 = j - read;
                        synchronized (Http2Stream.this) {
                            if (this.f.size() == 0) {
                                z2 = true;
                            }
                            this.f.writeAll(this.e);
                            if (z2) {
                                Http2Stream.this.notifyAll();
                            }
                        }
                        j = j2;
                    }
                }
                return;
            }
            throw new AssertionError();
        }

        public Timeout timeout() {
            return Http2Stream.this.f;
        }

        public void close() {
            long size;
            synchronized (Http2Stream.this) {
                this.a = true;
                size = this.f.size();
                this.f.clear();
                Http2Stream.this.notifyAll();
            }
            if (size > 0) {
                a(size);
            }
            Http2Stream.this.b();
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        /* access modifiers changed from: protected */
        public void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
        }

        /* access modifiers changed from: protected */
        public IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        public void a() {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }
    }

    Http2Stream(int i2, Http2Connection http2Connection, boolean z, boolean z2, List<Header> list) {
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        } else if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.c = i2;
            this.d = http2Connection;
            this.b = (long) http2Connection.l.d();
            this.m = new FramingSource((long) http2Connection.k.d());
            this.e = new FramingSink();
            this.m.b = z2;
            this.e.b = z;
            this.j = list;
        }
    }

    public int getId() {
        return this.c;
    }

    public synchronized boolean isOpen() {
        if (this.h != null) {
            return false;
        }
        if ((this.m.b || this.m.a) && ((this.e.b || this.e.a) && this.l)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.d.a == ((this.c & 1) == 1)) {
            return true;
        }
        return false;
    }

    public Http2Connection getConnection() {
        return this.d;
    }

    public List<Header> getRequestHeaders() {
        return this.j;
    }

    /* JADX INFO: finally extract failed */
    public synchronized List<Header> takeResponseHeaders() {
        List<Header> list;
        if (!isLocallyInitiated()) {
            throw new IllegalStateException("servers cannot read response headers");
        }
        this.f.enter();
        while (this.k == null && this.h == null) {
            try {
                d();
            } catch (Throwable th) {
                this.f.a();
                throw th;
            }
        }
        this.f.a();
        list = this.k;
        if (list != null) {
            this.k = null;
        } else {
            throw new StreamResetException(this.h);
        }
        return list;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.h;
    }

    public void sendResponseHeaders(List<Header> list, boolean z) {
        boolean z2;
        boolean z3;
        if (!i && Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (list == null) {
            throw new NullPointerException("responseHeaders == null");
        } else {
            synchronized (this) {
                this.l = true;
                if (!z) {
                    this.e.b = true;
                    z2 = true;
                    z3 = true;
                } else {
                    z2 = false;
                    z3 = false;
                }
            }
            if (!z2) {
                synchronized (this.d) {
                    z2 = this.d.j == 0;
                }
            }
            this.d.a(this.c, z3, list);
            if (z2) {
                this.d.flush();
            }
        }
    }

    public Timeout readTimeout() {
        return this.f;
    }

    public Timeout writeTimeout() {
        return this.g;
    }

    public Source getSource() {
        return this.m;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.l && !isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.e;
    }

    public void close(ErrorCode errorCode) {
        if (b(errorCode)) {
            this.d.b(this.c, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (b(errorCode)) {
            this.d.a(this.c, errorCode);
        }
    }

    private boolean b(ErrorCode errorCode) {
        if (i || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.h != null) {
                    return false;
                }
                if (this.m.b && this.e.b) {
                    return false;
                }
                this.h = errorCode;
                notifyAll();
                this.d.b(this.c);
                return true;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a(List<Header> list) {
        boolean z;
        if (i || !Thread.holdsLock(this)) {
            synchronized (this) {
                z = true;
                this.l = true;
                if (this.k == null) {
                    this.k = list;
                    z = isOpen();
                    notifyAll();
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.k);
                    arrayList.add(null);
                    arrayList.addAll(list);
                    this.k = arrayList;
                }
            }
            if (!z) {
                this.d.b(this.c);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a(BufferedSource bufferedSource, int i2) {
        if (i || !Thread.holdsLock(this)) {
            this.m.a(bufferedSource, (long) i2);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        boolean isOpen;
        if (i || !Thread.holdsLock(this)) {
            synchronized (this) {
                this.m.b = true;
                isOpen = isOpen();
                notifyAll();
            }
            if (!isOpen) {
                this.d.b(this.c);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(ErrorCode errorCode) {
        if (this.h == null) {
            this.h = errorCode;
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        boolean z;
        boolean isOpen;
        if (i || !Thread.holdsLock(this)) {
            synchronized (this) {
                z = !this.m.b && this.m.a && (this.e.b || this.e.a);
                isOpen = isOpen();
            }
            if (z) {
                close(ErrorCode.CANCEL);
            } else if (!isOpen) {
                this.d.b(this.c);
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2) {
        this.b += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.e.a) {
            throw new IOException("stream closed");
        } else if (this.e.b) {
            throw new IOException("stream finished");
        } else if (this.h != null) {
            throw new StreamResetException(this.h);
        }
    }

    /* access modifiers changed from: 0000 */
    public void d() {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }
}
