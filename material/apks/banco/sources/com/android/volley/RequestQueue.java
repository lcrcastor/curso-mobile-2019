package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {
    private AtomicInteger a;
    private final Map<String, Queue<Request<?>>> b;
    private final Set<Request<?>> c;
    private final PriorityBlockingQueue<Request<?>> d;
    private final PriorityBlockingQueue<Request<?>> e;
    private final Cache f;
    private final Network g;
    private final ResponseDelivery h;
    private NetworkDispatcher[] i;
    private CacheDispatcher j;
    private List<RequestFinishedListener> k;

    public interface RequestFilter {
        boolean apply(Request<?> request);
    }

    public interface RequestFinishedListener<T> {
        void onRequestFinished(Request<T> request);
    }

    public RequestQueue(Cache cache, Network network, int i2, ResponseDelivery responseDelivery) {
        this.a = new AtomicInteger();
        this.b = new HashMap();
        this.c = new HashSet();
        this.d = new PriorityBlockingQueue<>();
        this.e = new PriorityBlockingQueue<>();
        this.k = new ArrayList();
        this.f = cache;
        this.g = network;
        this.i = new NetworkDispatcher[i2];
        this.h = responseDelivery;
    }

    public RequestQueue(Cache cache, Network network, int i2) {
        this(cache, network, i2, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public RequestQueue(Cache cache, Network network) {
        this(cache, network, 4);
    }

    public void start() {
        stop();
        this.j = new CacheDispatcher(this.d, this.e, this.f, this.h);
        this.j.start();
        for (int i2 = 0; i2 < this.i.length; i2++) {
            NetworkDispatcher networkDispatcher = new NetworkDispatcher(this.e, this.g, this.f, this.h);
            this.i[i2] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void stop() {
        if (this.j != null) {
            this.j.quit();
        }
        for (int i2 = 0; i2 < this.i.length; i2++) {
            if (this.i[i2] != null) {
                this.i[i2].quit();
            }
        }
    }

    public int getSequenceNumber() {
        return this.a.incrementAndGet();
    }

    public Cache getCache() {
        return this.f;
    }

    public void cancelAll(RequestFilter requestFilter) {
        synchronized (this.c) {
            for (Request request : this.c) {
                if (requestFilter.apply(request)) {
                    request.cancel();
                }
            }
        }
    }

    public void cancelAll(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot cancelAll with a null tag");
        }
        cancelAll((RequestFilter) new RequestFilter() {
            public boolean apply(Request<?> request) {
                return request.getTag() == obj;
            }
        });
    }

    public <T> Request<T> add(Request<T> request) {
        request.setRequestQueue(this);
        synchronized (this.c) {
            this.c.add(request);
        }
        request.setSequence(getSequenceNumber());
        request.addMarker("add-to-queue");
        if (!request.shouldCache()) {
            this.e.add(request);
            return request;
        }
        synchronized (this.b) {
            String cacheKey = request.getCacheKey();
            if (this.b.containsKey(cacheKey)) {
                Queue queue = (Queue) this.b.get(cacheKey);
                if (queue == null) {
                    queue = new LinkedList();
                }
                queue.add(request);
                this.b.put(cacheKey, queue);
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
                }
            } else {
                this.b.put(cacheKey, null);
                this.d.add(request);
            }
        }
        return request;
    }

    /* access modifiers changed from: 0000 */
    public <T> void a(Request<T> request) {
        synchronized (this.c) {
            this.c.remove(request);
        }
        synchronized (this.k) {
            for (RequestFinishedListener onRequestFinished : this.k) {
                onRequestFinished.onRequestFinished(request);
            }
        }
        if (request.shouldCache()) {
            synchronized (this.b) {
                String cacheKey = request.getCacheKey();
                Queue queue = (Queue) this.b.remove(cacheKey);
                if (queue != null) {
                    if (VolleyLog.DEBUG) {
                        VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(queue.size()), cacheKey);
                    }
                    this.d.addAll(queue);
                }
            }
        }
    }

    public <T> void addRequestFinishedListener(RequestFinishedListener<T> requestFinishedListener) {
        synchronized (this.k) {
            this.k.add(requestFinishedListener);
        }
    }

    public <T> void removeRequestFinishedListener(RequestFinishedListener<T> requestFinishedListener) {
        synchronized (this.k) {
            this.k.remove(requestFinishedListener);
        }
    }
}
