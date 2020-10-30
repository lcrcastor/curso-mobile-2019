package cz.msebera.android.httpclient.conn.scheme;

import java.net.InetAddress;

@Deprecated
public interface HostNameResolver {
    InetAddress resolve(String str);
}
