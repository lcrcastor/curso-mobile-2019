package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.impl.DefaultBHttpServerConnection;
import cz.msebera.android.httpclient.protocol.HttpService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;

public class HttpServer {
    private final int a;
    private final InetAddress b;
    private final SocketConfig c;
    private final ServerSocketFactory d;
    private final HttpService e;
    private final HttpConnectionFactory<? extends DefaultBHttpServerConnection> f;
    private final SSLServerSetupHandler g;
    private final ExceptionLogger h;
    private final ExecutorService i;
    private final ThreadGroup j = new ThreadGroup("HTTP-workers");
    private final ExecutorService k = Executors.newCachedThreadPool(new ThreadFactoryImpl("HTTP-worker", this.j));
    private final AtomicReference<Status> l = new AtomicReference<>(Status.READY);
    private volatile ServerSocket m;
    private volatile RequestListener n;

    enum Status {
        READY,
        ACTIVE,
        STOPPING
    }

    HttpServer(int i2, InetAddress inetAddress, SocketConfig socketConfig, ServerSocketFactory serverSocketFactory, HttpService httpService, HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory, SSLServerSetupHandler sSLServerSetupHandler, ExceptionLogger exceptionLogger) {
        this.a = i2;
        this.b = inetAddress;
        this.c = socketConfig;
        this.d = serverSocketFactory;
        this.e = httpService;
        this.f = httpConnectionFactory;
        this.g = sSLServerSetupHandler;
        this.h = exceptionLogger;
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP-listener-");
        sb.append(this.a);
        this.i = Executors.newSingleThreadExecutor(new ThreadFactoryImpl(sb.toString()));
    }

    public InetAddress getInetAddress() {
        ServerSocket serverSocket = this.m;
        if (serverSocket != null) {
            return serverSocket.getInetAddress();
        }
        return null;
    }

    public int getLocalPort() {
        ServerSocket serverSocket = this.m;
        if (serverSocket != null) {
            return serverSocket.getLocalPort();
        }
        return -1;
    }

    public void start() {
        if (this.l.compareAndSet(Status.READY, Status.ACTIVE)) {
            this.m = this.d.createServerSocket(this.a, this.c.getBacklogSize(), this.b);
            this.m.setReuseAddress(this.c.isSoReuseAddress());
            if (this.c.getRcvBufSize() > 0) {
                this.m.setReceiveBufferSize(this.c.getRcvBufSize());
            }
            if (this.g != null && (this.m instanceof SSLServerSocket)) {
                this.g.initialize((SSLServerSocket) this.m);
            }
            RequestListener requestListener = new RequestListener(this.c, this.m, this.e, this.f, this.h, this.k);
            this.n = requestListener;
            this.i.execute(this.n);
        }
    }

    public void stop() {
        if (this.l.compareAndSet(Status.ACTIVE, Status.STOPPING)) {
            RequestListener requestListener = this.n;
            if (requestListener != null) {
                try {
                    requestListener.b();
                } catch (IOException e2) {
                    this.h.log(e2);
                }
            }
            this.j.interrupt();
            this.i.shutdown();
            this.k.shutdown();
        }
    }

    public void awaitTermination(long j2, TimeUnit timeUnit) {
        this.k.awaitTermination(j2, timeUnit);
    }

    public void shutdown(long j2, TimeUnit timeUnit) {
        stop();
        if (j2 > 0) {
            try {
                awaitTermination(j2, timeUnit);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        for (Runnable runnable : this.k.shutdownNow()) {
            if (runnable instanceof Worker) {
                try {
                    ((Worker) runnable).a().shutdown();
                } catch (IOException e2) {
                    this.h.log(e2);
                }
            }
        }
    }
}
