package com.indra.httpclient.certificates;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NukeSSLCerts {
    protected static final String TAG = "NukeSSLCerts";

    public static void nuke() {
        try {
            TrustManager[] trustManagerArr = {new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext instance = SSLContext.getInstance("SSL");
            instance.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(instance.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            });
        } catch (Exception unused) {
        }
    }
}
