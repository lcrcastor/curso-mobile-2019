package cz.msebera.android.httpclient.conn;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.util.Arrays;

@Immutable
public class HttpHostConnectException extends ConnectException {
    private static final long serialVersionUID = -3194482710275220224L;
    private final HttpHost a;

    @Deprecated
    public HttpHostConnectException(HttpHost httpHost, ConnectException connectException) {
        this(connectException, httpHost, null);
    }

    public HttpHostConnectException(IOException iOException, HttpHost httpHost, InetAddress... inetAddressArr) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append("Connect to ");
        sb.append(httpHost != null ? httpHost.toHostString() : "remote host");
        if (inetAddressArr == null || inetAddressArr.length <= 0) {
            str = "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(UtilsCuentas.SEPARAOR2);
            sb2.append(Arrays.asList(inetAddressArr));
            str = sb2.toString();
        }
        sb.append(str);
        if (iOException == null || iOException.getMessage() == null) {
            str2 = " refused";
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" failed: ");
            sb3.append(iOException.getMessage());
            str2 = sb3.toString();
        }
        sb.append(str2);
        super(sb.toString());
        this.a = httpHost;
        initCause(iOException);
    }

    public HttpHost getHost() {
        return this.a;
    }
}
