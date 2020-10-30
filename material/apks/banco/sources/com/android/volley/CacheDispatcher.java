package com.android.volley;

import android.os.Process;
import com.android.volley.Cache.Entry;
import java.util.concurrent.BlockingQueue;

public class CacheDispatcher extends Thread {
    private static final boolean a = VolleyLog.DEBUG;
    private final BlockingQueue<Request<?>> b;
    /* access modifiers changed from: private */
    public final BlockingQueue<Request<?>> c;
    private final Cache d;
    private final ResponseDelivery e;
    private volatile boolean f = false;

    public CacheDispatcher(BlockingQueue<Request<?>> blockingQueue, BlockingQueue<Request<?>> blockingQueue2, Cache cache, ResponseDelivery responseDelivery) {
        this.b = blockingQueue;
        this.c = blockingQueue2;
        this.d = cache;
        this.e = responseDelivery;
    }

    public void quit() {
        this.f = true;
        interrupt();
    }

    public void run() {
        if (a) {
            VolleyLog.v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.d.initialize();
        while (true) {
            try {
                final Request request = (Request) this.b.take();
                try {
                    request.addMarker("cache-queue-take");
                    if (request.isCanceled()) {
                        request.a("cache-discard-canceled");
                    } else {
                        Entry entry = this.d.get(request.getCacheKey());
                        if (entry == null) {
                            request.addMarker("cache-miss");
                            this.c.put(request);
                        } else if (entry.isExpired()) {
                            request.addMarker("cache-hit-expired");
                            request.setCacheEntry(entry);
                            this.c.put(request);
                        } else {
                            request.addMarker("cache-hit");
                            Response parseNetworkResponse = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                            request.addMarker("cache-hit-parsed");
                            if (!entry.refreshNeeded()) {
                                this.e.postResponse(request, parseNetworkResponse);
                            } else {
                                request.addMarker("cache-hit-refresh-needed");
                                request.setCacheEntry(entry);
                                parseNetworkResponse.intermediate = true;
                                this.e.postResponse(request, parseNetworkResponse, new Runnable() {
                                    public void run() {
                                        try {
                                            CacheDispatcher.this.c.put(request);
                                        } catch (InterruptedException unused) {
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (Exception e2) {
                    VolleyLog.e(e2, "Unhandled exception %s", e2.toString());
                }
            } catch (InterruptedException unused) {
                if (this.f) {
                    return;
                }
            }
        }
    }
}
