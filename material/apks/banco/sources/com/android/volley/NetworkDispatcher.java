package com.android.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher extends Thread {
    private final BlockingQueue<Request<?>> a;
    private final Network b;
    private final Cache c;
    private final ResponseDelivery d;
    private volatile boolean e = false;

    public NetworkDispatcher(BlockingQueue<Request<?>> blockingQueue, Network network, Cache cache, ResponseDelivery responseDelivery) {
        this.a = blockingQueue;
        this.b = network;
        this.c = cache;
        this.d = responseDelivery;
    }

    public void quit() {
        this.e = true;
        interrupt();
    }

    @TargetApi(14)
    private void a(Request<?> request) {
        if (VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            try {
                Request request = (Request) this.a.take();
                try {
                    request.addMarker("network-queue-take");
                    if (request.isCanceled()) {
                        request.a("network-discard-cancelled");
                    } else {
                        a(request);
                        NetworkResponse performRequest = this.b.performRequest(request);
                        request.addMarker("network-http-complete");
                        if (!performRequest.notModified || !request.hasHadResponseDelivered()) {
                            Response parseNetworkResponse = request.parseNetworkResponse(performRequest);
                            request.addMarker("network-parse-complete");
                            if (request.shouldCache() && parseNetworkResponse.cacheEntry != null) {
                                this.c.put(request.getCacheKey(), parseNetworkResponse.cacheEntry);
                                request.addMarker("network-cache-written");
                            }
                            request.markDelivered();
                            this.d.postResponse(request, parseNetworkResponse);
                        } else {
                            request.a("not-modified");
                        }
                    }
                } catch (VolleyError e2) {
                    e2.a(SystemClock.elapsedRealtime() - elapsedRealtime);
                    a(request, e2);
                } catch (Exception e3) {
                    VolleyLog.e(e3, "Unhandled exception %s", e3.toString());
                    VolleyError volleyError = new VolleyError((Throwable) e3);
                    volleyError.a(SystemClock.elapsedRealtime() - elapsedRealtime);
                    this.d.postError(request, volleyError);
                }
            } catch (InterruptedException unused) {
                if (this.e) {
                    return;
                }
            }
        }
    }

    private void a(Request<?> request, VolleyError volleyError) {
        this.d.postError(request, request.parseNetworkError(volleyError));
    }
}
