package cz.msebera.android.httpclient.impl.bootstrap;

import javax.net.ssl.SSLServerSocket;

public interface SSLServerSetupHandler {
    void initialize(SSLServerSocket sSLServerSocket);
}
