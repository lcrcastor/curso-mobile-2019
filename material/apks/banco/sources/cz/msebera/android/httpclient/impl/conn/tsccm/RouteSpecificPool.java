package cz.msebera.android.httpclient.impl.conn.tsccm;

import cz.msebera.android.httpclient.conn.params.ConnPerRoute;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import cz.msebera.android.httpclient.util.LangUtils;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

@Deprecated
public class RouteSpecificPool {
    protected final ConnPerRoute connPerRoute;
    protected final LinkedList<BasicPoolEntry> freeEntries;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());
    protected final int maxEntries;
    protected int numEntries;
    protected final HttpRoute route;
    protected final Queue<WaitingThread> waitingThreads;

    @Deprecated
    public RouteSpecificPool(HttpRoute httpRoute, int i) {
        this.route = httpRoute;
        this.maxEntries = i;
        this.connPerRoute = new ConnPerRoute() {
            public int getMaxForRoute(HttpRoute httpRoute) {
                return RouteSpecificPool.this.maxEntries;
            }
        };
        this.freeEntries = new LinkedList<>();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }

    public RouteSpecificPool(HttpRoute httpRoute, ConnPerRoute connPerRoute2) {
        this.route = httpRoute;
        this.connPerRoute = connPerRoute2;
        this.maxEntries = connPerRoute2.getMaxForRoute(httpRoute);
        this.freeEntries = new LinkedList<>();
        this.waitingThreads = new LinkedList();
        this.numEntries = 0;
    }

    public final HttpRoute getRoute() {
        return this.route;
    }

    public final int getMaxEntries() {
        return this.maxEntries;
    }

    public boolean isUnused() {
        return this.numEntries < 1 && this.waitingThreads.isEmpty();
    }

    public int getCapacity() {
        return this.connPerRoute.getMaxForRoute(this.route) - this.numEntries;
    }

    public final int getEntryCount() {
        return this.numEntries;
    }

    public BasicPoolEntry allocEntry(Object obj) {
        if (!this.freeEntries.isEmpty()) {
            ListIterator listIterator = this.freeEntries.listIterator(this.freeEntries.size());
            while (listIterator.hasPrevious()) {
                BasicPoolEntry basicPoolEntry = (BasicPoolEntry) listIterator.previous();
                if (basicPoolEntry.getState() != null) {
                    if (LangUtils.equals(obj, basicPoolEntry.getState())) {
                    }
                }
                listIterator.remove();
                return basicPoolEntry;
            }
        }
        if (getCapacity() != 0 || this.freeEntries.isEmpty()) {
            return null;
        }
        BasicPoolEntry basicPoolEntry2 = (BasicPoolEntry) this.freeEntries.remove();
        basicPoolEntry2.shutdownEntry();
        try {
            basicPoolEntry2.getConnection().close();
        } catch (IOException e) {
            this.log.debug("I/O error closing connection", e);
        }
        return basicPoolEntry2;
    }

    public void freeEntry(BasicPoolEntry basicPoolEntry) {
        if (this.numEntries < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("No entry created for this pool. ");
            sb.append(this.route);
            throw new IllegalStateException(sb.toString());
        } else if (this.numEntries <= this.freeEntries.size()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("No entry allocated from this pool. ");
            sb2.append(this.route);
            throw new IllegalStateException(sb2.toString());
        } else {
            this.freeEntries.add(basicPoolEntry);
        }
    }

    public void createdEntry(BasicPoolEntry basicPoolEntry) {
        Args.check(this.route.equals(basicPoolEntry.getPlannedRoute()), "Entry not planned for this pool");
        this.numEntries++;
    }

    public boolean deleteEntry(BasicPoolEntry basicPoolEntry) {
        boolean remove = this.freeEntries.remove(basicPoolEntry);
        if (remove) {
            this.numEntries--;
        }
        return remove;
    }

    public void dropEntry() {
        Asserts.check(this.numEntries > 0, "There is no entry that could be dropped");
        this.numEntries--;
    }

    public void queueThread(WaitingThread waitingThread) {
        Args.notNull(waitingThread, "Waiting thread");
        this.waitingThreads.add(waitingThread);
    }

    public boolean hasThread() {
        return !this.waitingThreads.isEmpty();
    }

    public WaitingThread nextThread() {
        return (WaitingThread) this.waitingThreads.peek();
    }

    public void removeThread(WaitingThread waitingThread) {
        if (waitingThread != null) {
            this.waitingThreads.remove(waitingThread);
        }
    }
}
