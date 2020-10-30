package com.android.volley;

import android.os.Handler;
import java.util.concurrent.Executor;

public class ExecutorDelivery implements ResponseDelivery {
    private final Executor a;

    class ResponseDeliveryRunnable implements Runnable {
        private final Request b;
        private final Response c;
        private final Runnable d;

        public ResponseDeliveryRunnable(Request request, Response response, Runnable runnable) {
            this.b = request;
            this.c = response;
            this.d = runnable;
        }

        public void run() {
            if (this.b.isCanceled()) {
                this.b.a("canceled-at-delivery");
                return;
            }
            if (this.c.isSuccess()) {
                this.b.deliverResponse(this.c.result);
            } else {
                this.b.deliverError(this.c.error);
            }
            if (this.c.intermediate) {
                this.b.addMarker("intermediate-response");
            } else {
                this.b.a("done");
            }
            if (this.d != null) {
                this.d.run();
            }
        }
    }

    public ExecutorDelivery(final Handler handler) {
        this.a = new Executor() {
            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        };
    }

    public ExecutorDelivery(Executor executor) {
        this.a = executor;
    }

    public void postResponse(Request<?> request, Response<?> response) {
        postResponse(request, response, null);
    }

    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        request.markDelivered();
        request.addMarker("post-response");
        this.a.execute(new ResponseDeliveryRunnable(request, response, runnable));
    }

    public void postError(Request<?> request, VolleyError volleyError) {
        request.addMarker("post-error");
        this.a.execute(new ResponseDeliveryRunnable(request, Response.error(volleyError), null));
    }
}
