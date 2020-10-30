package com.squareup.okhttp.internal.spdy;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream {
    static final /* synthetic */ boolean d = true;
    long a = 0;
    long b;
    final SpdyDataSink c;
    /* access modifiers changed from: private */
    public final int e;
    /* access modifiers changed from: private */
    public final SpdyConnection f;
    private final List<Header> g;
    private List<Header> h;
    private final SpdyDataSource i;
    /* access modifiers changed from: private */
    public final SpdyTimeout j = new SpdyTimeout();
    /* access modifiers changed from: private */
    public final SpdyTimeout k = new SpdyTimeout();
    /* access modifiers changed from: private */
    public ErrorCode l = null;

    final class SpdyDataSink implements Sink {
        static final /* synthetic */ boolean a = true;
        private final Buffer c = new Buffer();
        /* access modifiers changed from: private */
        public boolean d;
        /* access modifiers changed from: private */
        public boolean e;

        static {
            Class<SpdyStream> cls = SpdyStream.class;
        }

        SpdyDataSink() {
        }

        public void write(Buffer buffer, long j) {
            if (a || !Thread.holdsLock(SpdyStream.this)) {
                this.c.write(buffer, j);
                while (this.c.size() >= PlaybackStateCompat.ACTION_PREPARE) {
                    a(false);
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX INFO: finally extract failed */
        private void a(boolean z) {
            long min;
            synchronized (SpdyStream.this) {
                SpdyStream.this.k.enter();
                while (SpdyStream.this.b <= 0 && !this.e && !this.d && SpdyStream.this.l == null) {
                    try {
                        SpdyStream.this.d();
                    } catch (Throwable th) {
                        SpdyStream.this.k.a();
                        throw th;
                    }
                }
                SpdyStream.this.k.a();
                SpdyStream.this.c();
                min = Math.min(SpdyStream.this.b, this.c.size());
                SpdyStream.this.b -= min;
            }
            SpdyStream.this.k.enter();
            try {
                SpdyStream.this.f.writeData(SpdyStream.this.e, z && min == this.c.size(), this.c, min);
            } finally {
                SpdyStream.this.k.a();
            }
        }

        public void flush() {
            if (a || !Thread.holdsLock(SpdyStream.this)) {
                synchronized (SpdyStream.this) {
                    SpdyStream.this.c();
                }
                while (this.c.size() > 0) {
                    a(false);
                    SpdyStream.this.f.flush();
                }
                return;
            }
            throw new AssertionError();
        }

        public Timeout timeout() {
            return SpdyStream.this.k;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0023, code lost:
            if (r8.b.c.e != false) goto L_0x0052;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002f, code lost:
            if (r8.c.size() <= 0) goto L_0x003f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
            if (r8.c.size() <= 0) goto L_0x0052;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
            a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
            com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).writeData(com.squareup.okhttp.internal.spdy.SpdyStream.b(r8.b), true, null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
            r2 = r8.b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0054, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r8.d = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0057, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0058, code lost:
            com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).flush();
            com.squareup.okhttp.internal.spdy.SpdyStream.f(r8.b);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r8 = this;
                boolean r0 = a
                if (r0 != 0) goto L_0x0012
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 == 0) goto L_0x0012
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            L_0x0012:
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                monitor-enter(r0)
                boolean r1 = r8.d     // Catch:{ all -> 0x006a }
                if (r1 == 0) goto L_0x001b
                monitor-exit(r0)     // Catch:{ all -> 0x006a }
                return
            L_0x001b:
                monitor-exit(r0)     // Catch:{ all -> 0x006a }
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                com.squareup.okhttp.internal.spdy.SpdyStream$SpdyDataSink r0 = r0.c
                boolean r0 = r0.e
                r1 = 1
                if (r0 != 0) goto L_0x0052
                okio.Buffer r0 = r8.c
                long r2 = r0.size()
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x003f
            L_0x0031:
                okio.Buffer r0 = r8.c
                long r2 = r0.size()
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0052
                r8.a(r1)
                goto L_0x0031
            L_0x003f:
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = r0.f
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                int r3 = r0.e
                r4 = 1
                r5 = 0
                r6 = 0
                r2.writeData(r3, r4, r5, r6)
            L_0x0052:
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                monitor-enter(r2)
                r8.d = r1     // Catch:{ all -> 0x0067 }
                monitor-exit(r2)     // Catch:{ all -> 0x0067 }
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                com.squareup.okhttp.internal.spdy.SpdyConnection r0 = r0.f
                r0.flush()
                com.squareup.okhttp.internal.spdy.SpdyStream r0 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                r0.b()
                return
            L_0x0067:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0067 }
                throw r0
            L_0x006a:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x006a }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSink.close():void");
        }
    }

    final class SpdyDataSource implements Source {
        static final /* synthetic */ boolean a = true;
        private final Buffer c;
        private final Buffer d;
        private final long e;
        /* access modifiers changed from: private */
        public boolean f;
        /* access modifiers changed from: private */
        public boolean g;

        static {
            Class<SpdyStream> cls = SpdyStream.class;
        }

        private SpdyDataSource(long j) {
            this.c = new Buffer();
            this.d = new Buffer();
            this.e = j;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x007e, code lost:
            r11 = com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0084, code lost:
            monitor-enter(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r2 = com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b);
            r2.c += r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ab, code lost:
            if (com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).c < ((long) (com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).e.e(65536) / 2))) goto L_0x00c7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ad, code lost:
            com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).a(0, com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).c);
            com.squareup.okhttp.internal.spdy.SpdyStream.a(r8.b).c = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00c7, code lost:
            monitor-exit(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c8, code lost:
            return r9;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(okio.Buffer r9, long r10) {
            /*
                r8 = this;
                r0 = 0
                int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                if (r2 >= 0) goto L_0x001d
                java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "byteCount < 0: "
                r0.append(r1)
                r0.append(r10)
                java.lang.String r10 = r0.toString()
                r9.<init>(r10)
                throw r9
            L_0x001d:
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                monitor-enter(r2)
                r8.a()     // Catch:{ all -> 0x00cc }
                r8.b()     // Catch:{ all -> 0x00cc }
                okio.Buffer r3 = r8.d     // Catch:{ all -> 0x00cc }
                long r3 = r3.size()     // Catch:{ all -> 0x00cc }
                int r5 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                if (r5 != 0) goto L_0x0034
                r9 = -1
                monitor-exit(r2)     // Catch:{ all -> 0x00cc }
                return r9
            L_0x0034:
                okio.Buffer r3 = r8.d     // Catch:{ all -> 0x00cc }
                okio.Buffer r4 = r8.d     // Catch:{ all -> 0x00cc }
                long r4 = r4.size()     // Catch:{ all -> 0x00cc }
                long r10 = java.lang.Math.min(r10, r4)     // Catch:{ all -> 0x00cc }
                long r9 = r3.read(r9, r10)     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                long r3 = r11.a     // Catch:{ all -> 0x00cc }
                r5 = 0
                long r5 = r3 + r9
                r11.a = r5     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                long r3 = r11.a     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyConnection r11 = r11.f     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.Settings r11 = r11.e     // Catch:{ all -> 0x00cc }
                r5 = 65536(0x10000, float:9.18355E-41)
                int r11 = r11.e(r5)     // Catch:{ all -> 0x00cc }
                int r11 = r11 / 2
                long r6 = (long) r11     // Catch:{ all -> 0x00cc }
                int r11 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
                if (r11 < 0) goto L_0x007d
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyConnection r11 = r11.f     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r3 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                int r3 = r3.e     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r4 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                long r6 = r4.a     // Catch:{ all -> 0x00cc }
                r11.a(r3, r6)     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00cc }
                r11.a = r0     // Catch:{ all -> 0x00cc }
            L_0x007d:
                monitor-exit(r2)     // Catch:{ all -> 0x00cc }
                com.squareup.okhttp.internal.spdy.SpdyStream r11 = com.squareup.okhttp.internal.spdy.SpdyStream.this
                com.squareup.okhttp.internal.spdy.SpdyConnection r11 = r11.f
                monitor-enter(r11)
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = r2.f     // Catch:{ all -> 0x00c9 }
                long r3 = r2.c     // Catch:{ all -> 0x00c9 }
                r6 = 0
                long r6 = r3 + r9
                r2.c = r6     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = r2.f     // Catch:{ all -> 0x00c9 }
                long r2 = r2.c     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyStream r4 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r4 = r4.f     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.Settings r4 = r4.e     // Catch:{ all -> 0x00c9 }
                int r4 = r4.e(r5)     // Catch:{ all -> 0x00c9 }
                int r4 = r4 / 2
                long r4 = (long) r4     // Catch:{ all -> 0x00c9 }
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 < 0) goto L_0x00c7
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = r2.f     // Catch:{ all -> 0x00c9 }
                r3 = 0
                com.squareup.okhttp.internal.spdy.SpdyStream r4 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r4 = r4.f     // Catch:{ all -> 0x00c9 }
                long r4 = r4.c     // Catch:{ all -> 0x00c9 }
                r2.a(r3, r4)     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyStream r2 = com.squareup.okhttp.internal.spdy.SpdyStream.this     // Catch:{ all -> 0x00c9 }
                com.squareup.okhttp.internal.spdy.SpdyConnection r2 = r2.f     // Catch:{ all -> 0x00c9 }
                r2.c = r0     // Catch:{ all -> 0x00c9 }
            L_0x00c7:
                monitor-exit(r11)     // Catch:{ all -> 0x00c9 }
                return r9
            L_0x00c9:
                r9 = move-exception
                monitor-exit(r11)     // Catch:{ all -> 0x00c9 }
                throw r9
            L_0x00cc:
                r9 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x00cc }
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.spdy.SpdyStream.SpdyDataSource.read(okio.Buffer, long):long");
        }

        private void a() {
            SpdyStream.this.j.enter();
            while (this.d.size() == 0 && !this.g && !this.f && SpdyStream.this.l == null) {
                try {
                    SpdyStream.this.d();
                } finally {
                    SpdyStream.this.j.a();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(BufferedSource bufferedSource, long j) {
            boolean z;
            boolean z2;
            boolean z3;
            if (a || !Thread.holdsLock(SpdyStream.this)) {
                while (j > 0) {
                    synchronized (SpdyStream.this) {
                        z = this.g;
                        z2 = false;
                        z3 = j + this.d.size() > this.e;
                    }
                    if (z3) {
                        bufferedSource.skip(j);
                        SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    } else if (z) {
                        bufferedSource.skip(j);
                        return;
                    } else {
                        long read = bufferedSource.read(this.c, j);
                        if (read == -1) {
                            throw new EOFException();
                        }
                        long j2 = j - read;
                        synchronized (SpdyStream.this) {
                            if (this.d.size() == 0) {
                                z2 = true;
                            }
                            this.d.writeAll(this.c);
                            if (z2) {
                                SpdyStream.this.notifyAll();
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
            return SpdyStream.this.j;
        }

        public void close() {
            synchronized (SpdyStream.this) {
                this.f = true;
                this.d.clear();
                SpdyStream.this.notifyAll();
            }
            SpdyStream.this.b();
        }

        private void b() {
            if (this.f) {
                throw new IOException("stream closed");
            } else if (SpdyStream.this.l != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("stream was reset: ");
                sb.append(SpdyStream.this.l);
                throw new IOException(sb.toString());
            }
        }
    }

    class SpdyTimeout extends AsyncTimeout {
        SpdyTimeout() {
        }

        /* access modifiers changed from: protected */
        public void timedOut() {
            SpdyStream.this.closeLater(ErrorCode.CANCEL);
        }

        public void a() {
            if (exit()) {
                throw new InterruptedIOException("timeout");
            }
        }
    }

    SpdyStream(int i2, SpdyConnection spdyConnection, boolean z, boolean z2, List<Header> list) {
        if (spdyConnection == null) {
            throw new NullPointerException("connection == null");
        } else if (list == null) {
            throw new NullPointerException("requestHeaders == null");
        } else {
            this.e = i2;
            this.f = spdyConnection;
            this.b = (long) spdyConnection.f.e(65536);
            this.i = new SpdyDataSource((long) spdyConnection.e.e(65536));
            this.c = new SpdyDataSink();
            this.i.g = z2;
            this.c.e = z;
            this.g = list;
        }
    }

    public int getId() {
        return this.e;
    }

    public synchronized boolean isOpen() {
        if (this.l != null) {
            return false;
        }
        if ((this.i.g || this.i.f) && ((this.c.e || this.c.d) && this.h != null)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.f.b == ((this.e & 1) == 1)) {
            return true;
        }
        return false;
    }

    public SpdyConnection getConnection() {
        return this.f;
    }

    public List<Header> getRequestHeaders() {
        return this.g;
    }

    /* JADX INFO: finally extract failed */
    public synchronized List<Header> getResponseHeaders() {
        this.j.enter();
        while (this.h == null && this.l == null) {
            try {
                d();
            } catch (Throwable th) {
                this.j.a();
                throw th;
            }
        }
        this.j.a();
        if (this.h != null) {
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("stream was reset: ");
            sb.append(this.l);
            throw new IOException(sb.toString());
        }
        return this.h;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.l;
    }

    public void reply(List<Header> list, boolean z) {
        if (d || !Thread.holdsLock(this)) {
            boolean z2 = false;
            synchronized (this) {
                if (list == null) {
                    try {
                        throw new NullPointerException("responseHeaders == null");
                    } catch (Throwable th) {
                        while (true) {
                            throw th;
                        }
                    }
                } else if (this.h != null) {
                    throw new IllegalStateException("reply already sent");
                } else {
                    this.h = list;
                    if (!z) {
                        this.c.e = true;
                        z2 = true;
                    }
                }
            }
            this.f.a(this.e, z2, list);
            if (z2) {
                this.f.flush();
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    public Timeout readTimeout() {
        return this.j;
    }

    public Timeout writeTimeout() {
        return this.k;
    }

    public Source getSource() {
        return this.i;
    }

    public Sink getSink() {
        synchronized (this) {
            if (this.h == null && !isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.c;
    }

    public void close(ErrorCode errorCode) {
        if (b(errorCode)) {
            this.f.b(this.e, errorCode);
        }
    }

    public void closeLater(ErrorCode errorCode) {
        if (b(errorCode)) {
            this.f.a(this.e, errorCode);
        }
    }

    private boolean b(ErrorCode errorCode) {
        if (d || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.l != null) {
                    return false;
                }
                if (this.i.g && this.c.e) {
                    return false;
                }
                this.l = errorCode;
                notifyAll();
                this.f.b(this.e);
                return true;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a(List<Header> list, HeadersMode headersMode) {
        if (d || !Thread.holdsLock(this)) {
            ErrorCode errorCode = null;
            boolean z = true;
            synchronized (this) {
                if (this.h == null) {
                    if (headersMode.failIfHeadersAbsent()) {
                        errorCode = ErrorCode.PROTOCOL_ERROR;
                    } else {
                        this.h = list;
                        z = isOpen();
                        notifyAll();
                    }
                } else if (headersMode.failIfHeadersPresent()) {
                    errorCode = ErrorCode.STREAM_IN_USE;
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.h);
                    arrayList.addAll(list);
                    this.h = arrayList;
                }
            }
            if (errorCode != null) {
                closeLater(errorCode);
            } else if (!z) {
                this.f.b(this.e);
            }
        } else {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(BufferedSource bufferedSource, int i2) {
        if (d || !Thread.holdsLock(this)) {
            this.i.a(bufferedSource, (long) i2);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        boolean isOpen;
        if (d || !Thread.holdsLock(this)) {
            synchronized (this) {
                this.i.g = true;
                isOpen = isOpen();
                notifyAll();
            }
            if (!isOpen) {
                this.f.b(this.e);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(ErrorCode errorCode) {
        if (this.l == null) {
            this.l = errorCode;
            notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        boolean z;
        boolean isOpen;
        if (d || !Thread.holdsLock(this)) {
            synchronized (this) {
                z = !this.i.g && this.i.f && (this.c.e || this.c.d);
                isOpen = isOpen();
            }
            if (z) {
                close(ErrorCode.CANCEL);
            } else if (!isOpen) {
                this.f.b(this.e);
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

    /* access modifiers changed from: private */
    public void c() {
        if (this.c.d) {
            throw new IOException("stream closed");
        } else if (this.c.e) {
            throw new IOException("stream finished");
        } else if (this.l != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("stream was reset: ");
            sb.append(this.l);
            throw new IOException(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            wait();
        } catch (InterruptedException unused) {
            throw new InterruptedIOException();
        }
    }
}
