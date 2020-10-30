package cz.msebera.android.httpclient.impl;

import cz.msebera.android.httpclient.HttpInetConnection;
import cz.msebera.android.httpclient.impl.io.SocketInputBuffer;
import cz.msebera.android.httpclient.impl.io.SocketOutputBuffer;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.io.SessionOutputBuffer;
import cz.msebera.android.httpclient.params.CoreConnectionPNames;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

@Deprecated
public class SocketHttpServerConnection extends AbstractHttpServerConnection implements HttpInetConnection {
    private volatile boolean a;
    private volatile Socket b = null;

    /* access modifiers changed from: protected */
    public void assertNotOpen() {
        Asserts.check(!this.a, "Connection is already open");
    }

    /* access modifiers changed from: protected */
    public void assertOpen() {
        Asserts.check(this.a, "Connection is not open");
    }

    /* access modifiers changed from: protected */
    public SessionInputBuffer createSessionInputBuffer(Socket socket, int i, HttpParams httpParams) {
        return new SocketInputBuffer(socket, i, httpParams);
    }

    /* access modifiers changed from: protected */
    public SessionOutputBuffer createSessionOutputBuffer(Socket socket, int i, HttpParams httpParams) {
        return new SocketOutputBuffer(socket, i, httpParams);
    }

    /* access modifiers changed from: protected */
    public void bind(Socket socket, HttpParams httpParams) {
        Args.notNull(socket, "Socket");
        Args.notNull(httpParams, "HTTP parameters");
        this.b = socket;
        int intParameter = httpParams.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
        init(createSessionInputBuffer(socket, intParameter, httpParams), createSessionOutputBuffer(socket, intParameter, httpParams), httpParams);
        this.a = true;
    }

    /* access modifiers changed from: protected */
    public Socket getSocket() {
        return this.b;
    }

    public boolean isOpen() {
        return this.a;
    }

    public InetAddress getLocalAddress() {
        if (this.b != null) {
            return this.b.getLocalAddress();
        }
        return null;
    }

    public int getLocalPort() {
        if (this.b != null) {
            return this.b.getLocalPort();
        }
        return -1;
    }

    public InetAddress getRemoteAddress() {
        if (this.b != null) {
            return this.b.getInetAddress();
        }
        return null;
    }

    public int getRemotePort() {
        if (this.b != null) {
            return this.b.getPort();
        }
        return -1;
    }

    public void setSocketTimeout(int i) {
        assertOpen();
        if (this.b != null) {
            try {
                this.b.setSoTimeout(i);
            } catch (SocketException unused) {
            }
        }
    }

    public int getSocketTimeout() {
        if (this.b == null) {
            return -1;
        }
        try {
            return this.b.getSoTimeout();
        } catch (SocketException unused) {
            return -1;
        }
    }

    public void shutdown() {
        this.a = false;
        Socket socket = this.b;
        if (socket != null) {
            socket.close();
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0012 */
    /* JADX WARNING: Removed duplicated region for block: B:11:? A[ExcHandler: UnsupportedOperationException (unused java.lang.UnsupportedOperationException), SYNTHETIC, Splitter:B:8:0x0012] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r2 = this;
            boolean r0 = r2.a
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r2.a = r0
            r2.a = r0
            java.net.Socket r0 = r2.b
            r2.doFlush()     // Catch:{ all -> 0x0019 }
            r0.shutdownOutput()     // Catch:{ IOException -> 0x0012 }
        L_0x0012:
            r0.shutdownInput()     // Catch:{ UnsupportedOperationException -> 0x0015, UnsupportedOperationException -> 0x0015 }
        L_0x0015:
            r0.close()
            return
        L_0x0019:
            r1 = move-exception
            r0.close()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: cz.msebera.android.httpclient.impl.SocketHttpServerConnection.close():void");
    }

    private static void a(StringBuilder sb, SocketAddress socketAddress) {
        Object obj;
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
            if (inetSocketAddress.getAddress() != null) {
                obj = inetSocketAddress.getAddress().getHostAddress();
            } else {
                obj = inetSocketAddress.getAddress();
            }
            sb.append(obj);
            sb.append(':');
            sb.append(inetSocketAddress.getPort());
            return;
        }
        sb.append(socketAddress);
    }

    public String toString() {
        if (this.b == null) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder();
        SocketAddress remoteSocketAddress = this.b.getRemoteSocketAddress();
        SocketAddress localSocketAddress = this.b.getLocalSocketAddress();
        if (!(remoteSocketAddress == null || localSocketAddress == null)) {
            a(sb, localSocketAddress);
            sb.append("<->");
            a(sb, remoteSocketAddress);
        }
        return sb.toString();
    }
}
