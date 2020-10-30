package okhttp3;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public final class Dispatcher {
    private int a = 64;
    private int b = 5;
    @Nullable
    private Runnable c;
    @Nullable
    private ExecutorService d;
    private final Deque<AsyncCall> e = new ArrayDeque();
    private final Deque<AsyncCall> f = new ArrayDeque();
    private final Deque<RealCall> g = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.d = executorService;
    }

    public Dispatcher() {
    }

    public synchronized ExecutorService executorService() {
        if (this.d == null) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, SubsamplingScaleImageView.TILE_SIZE_AUTO, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
            this.d = threadPoolExecutor;
        }
        return this.d;
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

    public synchronized void setIdleCallback(@Nullable Runnable runnable) {
        this.c = runnable;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(AsyncCall asyncCall) {
        if (this.f.size() >= this.a || c(asyncCall) >= this.b) {
            this.e.add(asyncCall);
        } else {
            this.f.add(asyncCall);
            executorService().execute(asyncCall);
        }
    }

    public synchronized void cancelAll() {
        for (AsyncCall b2 : this.e) {
            b2.b().cancel();
        }
        for (AsyncCall b3 : this.f) {
            b3.b().cancel();
        }
        for (RealCall cancel : this.g) {
            cancel.cancel();
        }
    }

    private void a() {
        if (this.f.size() < this.a && !this.e.isEmpty()) {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                AsyncCall asyncCall = (AsyncCall) it.next();
                if (c(asyncCall) < this.b) {
                    it.remove();
                    this.f.add(asyncCall);
                    executorService().execute(asyncCall);
                }
                if (this.f.size() >= this.a) {
                    return;
                }
            }
        }
    }

    private int c(AsyncCall asyncCall) {
        int i = 0;
        for (AsyncCall asyncCall2 : this.f) {
            if (!asyncCall2.b().d && asyncCall2.a().equals(asyncCall.a())) {
                i++;
            }
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(RealCall realCall) {
        this.g.add(realCall);
    }

    /* access modifiers changed from: 0000 */
    public void b(AsyncCall asyncCall) {
        a(this.f, asyncCall, true);
    }

    /* access modifiers changed from: 0000 */
    public void b(RealCall realCall) {
        a(this.g, realCall, false);
    }

    private <T> void a(Deque<T> deque, T t, boolean z) {
        int runningCallsCount;
        Runnable runnable;
        synchronized (this) {
            if (!deque.remove(t)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            if (z) {
                a();
            }
            runningCallsCount = runningCallsCount();
            runnable = this.c;
        }
        if (runningCallsCount == 0 && runnable != null) {
            runnable.run();
        }
    }

    public synchronized List<Call> queuedCalls() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (AsyncCall b2 : this.e) {
            arrayList.add(b2.b());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public synchronized List<Call> runningCalls() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        arrayList.addAll(this.g);
        for (AsyncCall b2 : this.f) {
            arrayList.add(b2.b());
        }
        return Collections.unmodifiableList(arrayList);
    }

    public synchronized int queuedCallsCount() {
        return this.e.size();
    }

    public synchronized int runningCallsCount() {
        return this.f.size() + this.g.size();
    }
}
