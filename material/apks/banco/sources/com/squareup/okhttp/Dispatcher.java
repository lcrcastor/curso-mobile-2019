package com.squareup.okhttp;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpEngine;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class Dispatcher {
    private int a = 64;
    private int b = 5;
    private ExecutorService c;
    private final Deque<AsyncCall> d = new ArrayDeque();
    private final Deque<AsyncCall> e = new ArrayDeque();
    private final Deque<Call> f = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.c = executorService;
    }

    public Dispatcher() {
    }

    public synchronized ExecutorService getExecutorService() {
        if (this.c == null) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, SubsamplingScaleImageView.TILE_SIZE_AUTO, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
            this.c = threadPoolExecutor;
        }
        return this.c;
    }

    public synchronized void setMaxRequests(int i) {
        if (i < 1) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("max < 1: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } catch (Throwable th) {
                throw th;
            }
        } else {
            this.a = i;
            a();
        }
    }

    public synchronized int getMaxRequests() {
        return this.a;
    }

    public synchronized void setMaxRequestsPerHost(int i) {
        if (i < 1) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("max < 1: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            } catch (Throwable th) {
                throw th;
            }
        } else {
            this.b = i;
            a();
        }
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(AsyncCall asyncCall) {
        if (this.e.size() >= this.a || c(asyncCall) >= this.b) {
            this.d.add(asyncCall);
        } else {
            this.e.add(asyncCall);
            getExecutorService().execute(asyncCall);
        }
    }

    public synchronized void cancel(Object obj) {
        for (AsyncCall asyncCall : this.d) {
            if (Util.equal(obj, asyncCall.b())) {
                asyncCall.c();
            }
        }
        for (AsyncCall asyncCall2 : this.e) {
            if (Util.equal(obj, asyncCall2.b())) {
                asyncCall2.d().a = true;
                HttpEngine httpEngine = asyncCall2.d().c;
                if (httpEngine != null) {
                    httpEngine.disconnect();
                }
            }
        }
        for (Call call : this.f) {
            if (Util.equal(obj, call.a())) {
                call.cancel();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void b(AsyncCall asyncCall) {
        if (!this.e.remove(asyncCall)) {
            throw new AssertionError("AsyncCall wasn't running!");
        }
        a();
    }

    private void a() {
        if (this.e.size() < this.a && !this.d.isEmpty()) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                AsyncCall asyncCall = (AsyncCall) it.next();
                if (c(asyncCall) < this.b) {
                    it.remove();
                    this.e.add(asyncCall);
                    getExecutorService().execute(asyncCall);
                }
                if (this.e.size() >= this.a) {
                    return;
                }
            }
        }
    }

    private int c(AsyncCall asyncCall) {
        int i = 0;
        for (AsyncCall a2 : this.e) {
            if (a2.a().equals(asyncCall.a())) {
                i++;
            }
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(Call call) {
        this.f.add(call);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void b(Call call) {
        if (!this.f.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }

    public synchronized int getRunningCallCount() {
        return this.e.size();
    }

    public synchronized int getQueuedCallCount() {
        return this.d.size();
    }
}
