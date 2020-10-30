package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.EofSensor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import java.net.Socket;

@NotThreadSafe
@Deprecated
public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor {
    private final Socket a;
    private boolean b = false;

    public SocketInputBuffer(Socket socket, int i, HttpParams httpParams) {
        Args.notNull(socket, "Socket");
        this.a = socket;
        if (i < 0) {
            i = socket.getReceiveBufferSize();
        }
        if (i < 1024) {
            i = 1024;
        }
        init(socket.getInputStream(), i, httpParams);
    }

    /* access modifiers changed from: protected */
    public int fillBuffer() {
        int fillBuffer = super.fillBuffer();
        this.b = fillBuffer == -1;
        return fillBuffer;
    }

    public boolean isDataAvailable(int i) {
        boolean hasBufferedData = hasBufferedData();
        if (hasBufferedData) {
            return hasBufferedData;
        }
        int soTimeout = this.a.getSoTimeout();
        try {
            this.a.setSoTimeout(i);
            fillBuffer();
            return hasBufferedData();
        } finally {
            this.a.setSoTimeout(soTimeout);
        }
    }

    public boolean isEof() {
        return this.b;
    }
}
