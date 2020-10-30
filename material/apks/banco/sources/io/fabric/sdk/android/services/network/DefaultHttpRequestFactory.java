package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DefaultHttpRequestFactory implements HttpRequestFactory {
    private final Logger a;
    private PinningInfoProvider b;
    private SSLSocketFactory c;
    private boolean d;

    public DefaultHttpRequestFactory() {
        this(new DefaultLogger());
    }

    public DefaultHttpRequestFactory(Logger logger) {
        this.a = logger;
    }

    public PinningInfoProvider getPinningInfoProvider() {
        return this.b;
    }

    public void setPinningInfoProvider(PinningInfoProvider pinningInfoProvider) {
        if (this.b != pinningInfoProvider) {
            this.b = pinningInfoProvider;
            a();
        }
    }

    private synchronized void a() {
        this.d = false;
        this.c = null;
    }

    public HttpRequest buildHttpRequest(HttpMethod httpMethod, String str) {
        return buildHttpRequest(httpMethod, str, Collections.emptyMap());
    }

    public HttpRequest buildHttpRequest(HttpMethod httpMethod, String str, Map<String, String> map) {
        HttpRequest httpRequest;
        switch (httpMethod) {
            case GET:
                httpRequest = HttpRequest.get((CharSequence) str, map, true);
                break;
            case POST:
                httpRequest = HttpRequest.post((CharSequence) str, map, true);
                break;
            case PUT:
                httpRequest = HttpRequest.put((CharSequence) str);
                break;
            case DELETE:
                httpRequest = HttpRequest.delete((CharSequence) str);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method!");
        }
        if (a(str) && this.b != null) {
            SSLSocketFactory b2 = b();
            if (b2 != null) {
                ((HttpsURLConnection) httpRequest.getConnection()).setSSLSocketFactory(b2);
            }
        }
        return httpRequest;
    }

    private boolean a(String str) {
        return str != null && str.toLowerCase(Locale.US).startsWith("https");
    }

    private synchronized SSLSocketFactory b() {
        if (this.c == null && !this.d) {
            this.c = c();
        }
        return this.c;
    }

    private synchronized SSLSocketFactory c() {
        SSLSocketFactory sSLSocketFactory;
        this.d = true;
        try {
            sSLSocketFactory = NetworkUtils.getSSLSocketFactory(this.b);
            this.a.d(Fabric.TAG, "Custom SSL pinning enabled");
        } catch (Exception e) {
            this.a.e(Fabric.TAG, "Exception while validating pinned certs", e);
            return null;
        }
        return sSLSocketFactory;
    }
}
