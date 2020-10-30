package cz.msebera.android.httpclient.impl.bootstrap;

import cz.msebera.android.httpclient.ExceptionLogger;
import cz.msebera.android.httpclient.HttpConnectionFactory;
import cz.msebera.android.httpclient.HttpServerConnection;
import cz.msebera.android.httpclient.config.SocketConfig;
import cz.msebera.android.httpclient.protocol.HttpService;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

class RequestListener implements Runnable {
    private final SocketConfig a;
    private final ServerSocket b;
    private final HttpService c;
    private final HttpConnectionFactory<? extends HttpServerConnection> d;
    private final ExceptionLogger e;
    private final ExecutorService f;
    private final AtomicBoolean g = new AtomicBoolean(false);

    public RequestListener(SocketConfig socketConfig, ServerSocket serverSocket, HttpService httpService, HttpConnectionFactory<? extends HttpServerConnection> httpConnectionFactory, ExceptionLogger exceptionLogger, ExecutorService executorService) {
        this.a = socketConfig;
        this.b = serverSocket;
        this.d = httpConnectionFactory;
        this.c = httpService;
        this.e = exceptionLogger;
        this.f = executorService;
    }

    public void run() {
        while (!a() && !Thread.interrupted()) {
            try {
                Socket accept = this.b.accept();
                accept.setSoTimeout(this.a.getSoTimeout());
                accept.setKeepAlive(this.a.isSoKeepAlive());
                accept.setTcpNoDelay(this.a.isTcpNoDelay());
                if (this.a.getRcvBufSize() > 0) {
                    accept.setReceiveBufferSize(this.a.getRcvBufSize());
                }
                if (this.a.getSndBufSize() > 0) {
                    accept.setSendBufferSize(this.a.getSndBufSize());
                }
                if (this.a.getSoLinger() >= 0) {
                    accept.setSoLinger(true, this.a.getSoLinger());
                }
                this.f.execute(new Worker(this.c, (HttpServerConnection) this.d.createConnection(accept), this.e));
            } catch (Exception e2) {
                this.e.log(e2);
                return;
            }
        }
    }

    public boolean a() {
        return this.g.get();
    }

    public void b() {
        if (this.g.compareAndSet(false, true)) {
            this.b.close();
        }
    }
}
