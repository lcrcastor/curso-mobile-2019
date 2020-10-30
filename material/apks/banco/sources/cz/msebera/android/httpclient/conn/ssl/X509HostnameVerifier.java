package cz.msebera.android.httpclient.conn.ssl;

import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;

@Deprecated
public interface X509HostnameVerifier extends HostnameVerifier {
    void verify(String str, X509Certificate x509Certificate);

    void verify(String str, SSLSocket sSLSocket);

    void verify(String str, String[] strArr, String[] strArr2);
}
