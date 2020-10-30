package cz.msebera.android.httpclient.conn;

import java.net.InetAddress;

public interface DnsResolver {
    InetAddress[] resolve(String str);
}
