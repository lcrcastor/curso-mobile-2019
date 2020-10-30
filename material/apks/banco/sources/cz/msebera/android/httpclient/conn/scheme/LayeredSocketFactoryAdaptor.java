package cz.msebera.android.httpclient.conn.scheme;

import java.net.Socket;

@Deprecated
class LayeredSocketFactoryAdaptor extends SocketFactoryAdaptor implements LayeredSocketFactory {
    private final LayeredSchemeSocketFactory a;

    LayeredSocketFactoryAdaptor(LayeredSchemeSocketFactory layeredSchemeSocketFactory) {
        super(layeredSchemeSocketFactory);
        this.a = layeredSchemeSocketFactory;
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return this.a.createLayeredSocket(socket, str, i, z);
    }
}
