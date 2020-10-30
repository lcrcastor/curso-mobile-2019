package org.thoughtcrime.ssl.pinning.util;

import android.content.Context;
import cz.msebera.android.httpclient.HttpHost;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.thoughtcrime.ssl.pinning.PinningSSLSocketFactory;
import org.thoughtcrime.ssl.pinning.PinningTrustManager;
import org.thoughtcrime.ssl.pinning.SystemKeyStore;

public class PinningHelper {
    public static HttpClient getPinnedHttpClient(Context context, String[] strArr) {
        try {
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", new PinningSSLSocketFactory(context, strArr, 0), 443));
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        } catch (UnrecoverableKeyException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e2) {
            throw new AssertionError(e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new AssertionError(e3);
        } catch (KeyStoreException e4) {
            throw new AssertionError(e4);
        }
    }

    public static HttpsURLConnection getPinnedHttpsURLConnection(Context context, String[] strArr, URL url) {
        try {
            if (!url.getProtocol().equals("https")) {
                throw new IllegalArgumentException("Attempt to construct pinned non-https connection!");
            }
            TrustManager[] trustManagerArr = {new PinningTrustManager(SystemKeyStore.getInstance(context), strArr, 0)};
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, trustManagerArr, null);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
            return httpsURLConnection;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e2) {
            throw new AssertionError(e2);
        }
    }
}
