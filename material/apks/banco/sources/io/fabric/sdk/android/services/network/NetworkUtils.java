package io.fabric.sdk.android.services.network;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class NetworkUtils {
    private NetworkUtils() {
    }

    public static final SSLSocketFactory getSSLSocketFactory(PinningInfoProvider pinningInfoProvider) {
        SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, new TrustManager[]{new PinningTrustManager(new SystemKeyStore(pinningInfoProvider.getKeyStoreStream(), pinningInfoProvider.getKeyStorePassword()), pinningInfoProvider)}, null);
        return instance.getSocketFactory();
    }
}
