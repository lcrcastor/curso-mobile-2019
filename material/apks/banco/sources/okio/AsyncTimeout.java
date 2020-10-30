package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class AsyncTimeout extends Timeout {
    private static final long a = TimeUnit.SECONDS.toMillis(60);
    @Nullable
    static AsyncTimeout b;
    private static final long c = TimeUnit.MILLISECONDS.toNanos(a);
    private boolean d;
    @Nullable
    private AsyncTimeout e;
    private long f;

    static final class Watchdog extends Thread {
        Watchdog() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r1.timedOut();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
            L_0x0000:
                java.lang.Class<okio.AsyncTimeout> r0 = okio.AsyncTimeout.class
                monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0000 }
                okio.AsyncTimeout r1 = okio.AsyncTimeout.b()     // Catch:{ all -> 0x0019 }
                if (r1 != 0) goto L_0x000b
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                goto L_0x0000
            L_0x000b:
                okio.AsyncTimeout r2 = okio.AsyncTimeout.b     // Catch:{ all -> 0x0019 }
                if (r1 != r2) goto L_0x0014
                r1 = 0
                okio.AsyncTimeout.b = r1     // Catch:{ all -> 0x0019 }
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                return
            L_0x0014:
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                r1.timedOut()     // Catch:{ InterruptedException -> 0x0000 }
                goto L_0x0000
            L_0x0019:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                throw r1     // Catch:{ InterruptedException -> 0x0000 }
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.AsyncTimeout.Watchdog.run():void");
        }
    }

    public void timedOut() {
    }

    public final void enter() {
        if (this.d) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long timeoutNanos = timeoutNanos();
        boolean hasDeadline = hasDeadline();
        if (timeoutNanos != 0 || hasDeadline) {
            this.d = true;
            a(this, timeoutNanos, hasDeadline);
        }
    }

    private static synchronized void a(AsyncTimeout asyncTimeout, long j, boolean z) {
        synchronized (AsyncTimeout.class) {
            if (b == null) {
                b = new AsyncTimeout();
                new Watchdog().start();
            }
            long nanoTime = System.nanoTime();
            if (j != 0 && z) {
                asyncTimeout.f = nanoTime + Math.min(j, asyncTimeout.deadlineNanoTime() - nanoTime);
            } else if (j != 0) {
                asyncTimeout.f = nanoTime + j;
            } else if (z) {
                asyncTimeout.f = asyncTimeout.deadlineNanoTime();
            } else {
                throw new AssertionError();
            }
            long a2 = asyncTimeout.a(nanoTime);
            AsyncTimeout asyncTimeout2 = b;
            while (true) {
                if (asyncTimeout2.e == null) {
                    break;
                } else if (a2 < asyncTimeout2.e.a(nanoTime)) {
                    break;
                } else {
                    asyncTimeout2 = asyncTimeout2.e;
                }
            }
            asyncTimeout.e = asyncTimeout2.e;
            asyncTimeout2.e = asyncTimeout;
            if (asyncTimeout2 == b) {
                AsyncTimeout.class.notify();
            }
        }
    }

    public final boolean exit() {
        if (!this.d) {
            return false;
        }
        this.d = false;
        return a(this);
    }

    private static synchronized boolean a(AsyncTimeout asyncTimeout) {
        synchronized (AsyncTimeout.class) {
            for (AsyncTimeout asyncTimeout2 = b; asyncTimeout2 != null; asyncTimeout2 = asyncTimeout2.e) {
                if (asyncTimeout2.e == asyncTimeout) {
                    asyncTimeout2.e = asyncTimeout.e;
                    asyncTimeout.e = null;
                    return false;
                }
            }
            return true;
        }
    }

    private long a(long j) {
        return this.f - j;
    }

    public final Sink sink(final Sink sink) {
        return new Sink() {
            public void write(Buffer buffer, long j) {
                Util.a(buffer.b, 0, j);
                while (true) {
                    long j2 = 0;
                    if (j > 0) {
                        Segment segment = buffer.a;
                        while (true) {
                            if (j2 >= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                                break;
                            }
                            long j3 = j2 + ((long) (segment.c - segment.b));
                            if (j3 >= j) {
                                j2 = j;
                                break;
                            } else {
                                segment = segment.f;
                                j2 = j3;
                            }
                        }
                        AsyncTimeout.this.enter();
                        try {
                            sink.write(buffer, j2);
                            long j4 = j - j2;
                            AsyncTimeout.this.a(true);
                            j = j4;
                        } catch (IOException e) {
                            throw AsyncTimeout.this.a(e);
                        } catch (Throwable th) {
                            AsyncTimeout.this.a(false);
                            throw th;
                        }
                    } else {
                        return;
                    }
                }
            }

            public void flush() {
                AsyncTimeout.this.enter();
                try {
                    sink.flush();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    throw AsyncTimeout.this.a(e);
                } catch (Throwable th) {
                    AsyncTimeout.this.a(false);
                    throw th;
                }
            }

            public void close() {
                AsyncTimeout.this.enter();
                try {
                    sink.close();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    throw AsyncTimeout.this.a(e);
                } catch (Throwable th) {
                    AsyncTimeout.this.a(false);
                    throw th;
                }
            }

            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("AsyncTimeout.sink(");
                sb.append(sink);
                sb.append(")");
                return sb.toString();
            }
        };
    }

    public final Source source(final Source source) {
        return new Source() {
            public long read(Buffer buffer, long j) {
                AsyncTimeout.this.enter();
                try {
                    long read = source.read(buffer, j);
                    AsyncTimeout.this.a(true);
                    return read;
                } catch (IOException e) {
                    throw AsyncTimeout.this.a(e);
                } catch (Throwable th) {
                    AsyncTimeout.this.a(false);
                    throw th;
                }
            }

            public void close() {
                try {
                    source.close();
                    AsyncTimeout.this.a(true);
                } catch (IOException e) {
                    throw AsyncTimeout.this.a(e);
                } catch (Throwable th) {
                    AsyncTimeout.this.a(false);
                    throw th;
                }
            }

            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("AsyncTimeout.source(");
                sb.append(source);
                sb.append(")");
                return sb.toString();
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        if (exit() && z) {
            throw newTimeoutException(null);
        }
    }

    /* access modifiers changed from: 0000 */
    public final IOException a(IOException iOException) {
        if (!exit()) {
            return iOException;
        }
        return newTimeoutException(iOException);
    }

    /* access modifiers changed from: protected */
    public IOException newTimeoutException(@Nullable IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    @Nullable
    static AsyncTimeout b() {
        AsyncTimeout asyncTimeout = b.e;
        AsyncTimeout asyncTimeout2 = null;
        if (asyncTimeout == null) {
            long nanoTime = System.nanoTime();
            AsyncTimeout.class.wait(a);
            if (b.e == null && System.nanoTime() - nanoTime >= c) {
                asyncTimeout2 = b;
            }
            return asyncTimeout2;
        }
        long a2 = asyncTimeout.a(System.nanoTime());
        if (a2 > 0) {
            long j = a2 / 1000000;
            AsyncTimeout.class.wait(j, (int) (a2 - (1000000 * j)));
            return null;
        }
        b.e = asyncTimeout.e;
        asyncTimeout.e = null;
        return asyncTimeout;
    }
}
