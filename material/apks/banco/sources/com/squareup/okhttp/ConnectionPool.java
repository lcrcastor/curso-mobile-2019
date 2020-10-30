package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool {
    private static final ConnectionPool a;
    private final int b;
    private final long c;
    private final LinkedList<Connection> d = new LinkedList<>();
    private Executor e;
    private final Runnable f;

    static {
        String property = System.getProperty("http.keepAlive");
        String property2 = System.getProperty("http.keepAliveDuration");
        String property3 = System.getProperty("http.maxConnections");
        long parseLong = property2 != null ? Long.parseLong(property2) : 300000;
        if (property != null && !Boolean.parseBoolean(property)) {
            a = new ConnectionPool(0, parseLong);
        } else if (property3 != null) {
            a = new ConnectionPool(Integer.parseInt(property3), parseLong);
        } else {
            a = new ConnectionPool(5, parseLong);
        }
    }

    public ConnectionPool(int i, long j) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
        this.e = threadPoolExecutor;
        this.f = new Runnable() {
            public void run() {
                ConnectionPool.this.b();
            }
        };
        this.b = i;
        this.c = j * 1000 * 1000;
    }

    public static ConnectionPool getDefault() {
        return a;
    }

    public synchronized int getConnectionCount() {
        return this.d.size();
    }

    @Deprecated
    public synchronized int getSpdyConnectionCount() {
        return getMultiplexedConnectionCount();
    }

    public synchronized int getMultiplexedConnectionCount() {
        int i;
        i = 0;
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            if (((Connection) it.next()).j()) {
                i++;
            }
        }
        return i;
    }

    public synchronized int getHttpConnectionCount() {
        return this.d.size() - getMultiplexedConnectionCount();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0076, code lost:
        r0 = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.Connection get(com.squareup.okhttp.Address r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = 0
            java.util.LinkedList<com.squareup.okhttp.Connection> r1 = r9.d     // Catch:{ all -> 0x0086 }
            java.util.LinkedList<com.squareup.okhttp.Connection> r2 = r9.d     // Catch:{ all -> 0x0086 }
            int r2 = r2.size()     // Catch:{ all -> 0x0086 }
            java.util.ListIterator r1 = r1.listIterator(r2)     // Catch:{ all -> 0x0086 }
        L_0x000e:
            boolean r2 = r1.hasPrevious()     // Catch:{ all -> 0x0086 }
            if (r2 == 0) goto L_0x0077
            java.lang.Object r2 = r1.previous()     // Catch:{ all -> 0x0086 }
            com.squareup.okhttp.Connection r2 = (com.squareup.okhttp.Connection) r2     // Catch:{ all -> 0x0086 }
            com.squareup.okhttp.Route r3 = r2.getRoute()     // Catch:{ all -> 0x0086 }
            com.squareup.okhttp.Address r3 = r3.getAddress()     // Catch:{ all -> 0x0086 }
            boolean r3 = r3.equals(r10)     // Catch:{ all -> 0x0086 }
            if (r3 == 0) goto L_0x000e
            boolean r3 = r2.e()     // Catch:{ all -> 0x0086 }
            if (r3 == 0) goto L_0x000e
            long r3 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0086 }
            long r5 = r2.i()     // Catch:{ all -> 0x0086 }
            r7 = 0
            long r7 = r3 - r5
            long r3 = r9.c     // Catch:{ all -> 0x0086 }
            int r5 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0040
            goto L_0x000e
        L_0x0040:
            r1.remove()     // Catch:{ all -> 0x0086 }
            boolean r3 = r2.j()     // Catch:{ all -> 0x0086 }
            if (r3 != 0) goto L_0x0076
            com.squareup.okhttp.internal.Platform r3 = com.squareup.okhttp.internal.Platform.get()     // Catch:{ SocketException -> 0x0055 }
            java.net.Socket r4 = r2.getSocket()     // Catch:{ SocketException -> 0x0055 }
            r3.tagSocket(r4)     // Catch:{ SocketException -> 0x0055 }
            goto L_0x0076
        L_0x0055:
            r3 = move-exception
            java.net.Socket r2 = r2.getSocket()     // Catch:{ all -> 0x0086 }
            com.squareup.okhttp.internal.Util.closeQuietly(r2)     // Catch:{ all -> 0x0086 }
            com.squareup.okhttp.internal.Platform r2 = com.squareup.okhttp.internal.Platform.get()     // Catch:{ all -> 0x0086 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
            r4.<init>()     // Catch:{ all -> 0x0086 }
            java.lang.String r5 = "Unable to tagSocket(): "
            r4.append(r5)     // Catch:{ all -> 0x0086 }
            r4.append(r3)     // Catch:{ all -> 0x0086 }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x0086 }
            r2.logW(r3)     // Catch:{ all -> 0x0086 }
            goto L_0x000e
        L_0x0076:
            r0 = r2
        L_0x0077:
            if (r0 == 0) goto L_0x0084
            boolean r10 = r0.j()     // Catch:{ all -> 0x0086 }
            if (r10 == 0) goto L_0x0084
            java.util.LinkedList<com.squareup.okhttp.Connection> r10 = r9.d     // Catch:{ all -> 0x0086 }
            r10.addFirst(r0)     // Catch:{ all -> 0x0086 }
        L_0x0084:
            monitor-exit(r9)
            return r0
        L_0x0086:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.ConnectionPool.get(com.squareup.okhttp.Address):com.squareup.okhttp.Connection");
    }

    /* access modifiers changed from: 0000 */
    public void a(Connection connection) {
        if (connection.j() || !connection.a()) {
            return;
        }
        if (!connection.e()) {
            Util.closeQuietly(connection.getSocket());
            return;
        }
        try {
            Platform.get().untagSocket(connection.getSocket());
            synchronized (this) {
                c(connection);
                connection.k();
                connection.g();
            }
        } catch (SocketException e2) {
            Platform platform = Platform.get();
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to untagSocket(): ");
            sb.append(e2);
            platform.logW(sb.toString());
            Util.closeQuietly(connection.getSocket());
        }
    }

    private void c(Connection connection) {
        boolean isEmpty = this.d.isEmpty();
        this.d.addFirst(connection);
        if (isEmpty) {
            this.e.execute(this.f);
        } else {
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Connection connection) {
        if (!connection.j()) {
            throw new IllegalArgumentException();
        } else if (connection.e()) {
            synchronized (this) {
                c(connection);
            }
        }
    }

    public void evictAll() {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.d);
            this.d.clear();
            notifyAll();
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Util.closeQuietly(((Connection) arrayList.get(i)).getSocket());
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        do {
        } while (a());
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        synchronized (this) {
            if (this.d.isEmpty()) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            long nanoTime = System.nanoTime();
            long j = this.c;
            ListIterator listIterator = this.d.listIterator(this.d.size());
            long j2 = j;
            int i = 0;
            while (listIterator.hasPrevious()) {
                Connection connection = (Connection) listIterator.previous();
                long i2 = (connection.i() + this.c) - nanoTime;
                if (i2 > 0) {
                    if (connection.e()) {
                        if (connection.h()) {
                            i++;
                            j2 = Math.min(j2, i2);
                        }
                    }
                }
                listIterator.remove();
                arrayList.add(connection);
            }
            ListIterator listIterator2 = this.d.listIterator(this.d.size());
            while (listIterator2.hasPrevious() && i > this.b) {
                Connection connection2 = (Connection) listIterator2.previous();
                if (connection2.h()) {
                    arrayList.add(connection2);
                    listIterator2.remove();
                    i--;
                }
            }
            if (arrayList.isEmpty()) {
                try {
                    long j3 = j2 / 1000000;
                    wait(j3, (int) (j2 - (1000000 * j3)));
                    return true;
                } catch (InterruptedException unused) {
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        Util.closeQuietly(((Connection) arrayList.get(i3)).getSocket());
                    }
                    return true;
                }
            }
        }
    }
}
