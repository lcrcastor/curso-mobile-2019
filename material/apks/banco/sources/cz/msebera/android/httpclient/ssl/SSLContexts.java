package cz.msebera.android.httpclient.ssl;

import cz.msebera.android.httpclient.annotation.Immutable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

@Immutable
public class SSLContexts {
    public static SSLContext createDefault() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, null, null);
            return instance;
        } catch (NoSuchAlgorithmException e) {
            throw new SSLInitializationException(e.getMessage(), e);
        } catch (KeyManagementException e2) {
            throw new SSLInitializationException(e2.getMessage(), e2);
        }
    }

    public static SSLContext createSystemDefault() {
        try {
            return SSLContext.getDefault();
        } catch (NoSuchAlgorithmException unused) {
            return createDefault();
        }
    }

    public static SSLContextBuilder custom() {
        return SSLContextBuilder.create();
    }
}
