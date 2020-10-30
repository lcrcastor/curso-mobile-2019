package com.twincoders.twinpush.sdk.communications.security;

import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Map;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class TwinPushSSLSocketFactory extends SSLSocketFactory {
    String a = null;
    Map<String, String> b = null;
    Map<String, String> c = null;
    SSLContext d = SSLContext.getInstance("TLS");

    public TwinPushSSLSocketFactory(KeyStore keyStore, TwinPushTrustManager twinPushTrustManager) {
        super(keyStore);
        this.d.init(null, new TrustManager[]{twinPushTrustManager}, null);
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) {
        return this.d.getSocketFactory().createSocket(socket, str, i, z);
    }

    public Socket createSocket() {
        return this.d.getSocketFactory().createSocket();
    }
}
