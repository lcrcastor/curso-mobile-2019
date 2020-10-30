package cz.msebera.android.httpclient.conn;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.util.Args;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@Deprecated
public class HttpInetSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = -6650701828361907957L;
    private final HttpHost a;

    public HttpInetSocketAddress(HttpHost httpHost, InetAddress inetAddress, int i) {
        super(inetAddress, i);
        Args.notNull(httpHost, "HTTP host");
        this.a = httpHost;
    }

    public HttpHost getHttpHost() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getHostName());
        sb.append(":");
        sb.append(getPort());
        return sb.toString();
    }
}
