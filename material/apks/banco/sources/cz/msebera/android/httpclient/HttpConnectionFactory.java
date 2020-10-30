package cz.msebera.android.httpclient;

import cz.msebera.android.httpclient.HttpConnection;
import java.net.Socket;

public interface HttpConnectionFactory<T extends HttpConnection> {
    T createConnection(Socket socket);
}
