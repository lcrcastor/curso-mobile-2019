package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpServerConnection;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpCoreContext;
import cz.msebera.android.httpclient.protocol.HttpService;
import java.io.IOException;

class Worker implements Runnable {
    private final HttpService a;
    private final HttpServerConnection b;
    private final ExceptionLogger c;

    Worker(HttpService httpService, HttpServerConnection httpServerConnection, ExceptionLogger exceptionLogger) {
        this.a = httpService;
        this.b = httpServerConnection;
        this.c = exceptionLogger;
    }

    public HttpServerConnection a() {
        return this.b;
    }

    public void run() {
        try {
            BasicHttpContext basicHttpContext = new BasicHttpContext();
            HttpCoreContext adapt = HttpCoreContext.adapt(basicHttpContext);
            while (!Thread.interrupted() && this.b.isOpen()) {
                this.a.handleRequest(this.b, adapt);
                basicHttpContext.clear();
            }
            this.b.close();
            try {
            } catch (IOException e) {
                this.c.log(e);
            }
        } catch (Exception e2) {
            this.c.log(e2);
        } finally {
            try {
                this.b.shutdown();
            } catch (IOException e3) {
                this.c.log(e3);
            }
        }
    }
}
