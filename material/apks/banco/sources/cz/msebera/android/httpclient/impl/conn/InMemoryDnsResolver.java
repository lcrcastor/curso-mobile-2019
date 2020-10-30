package cz.msebera.android.httpclient.impl.conn;

import cz.msebera.android.httpclient.conn.DnsResolver;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDnsResolver implements DnsResolver {
    private final Map<String, InetAddress[]> a = new ConcurrentHashMap();
    public HttpClientAndroidLog log = new HttpClientAndroidLog(InMemoryDnsResolver.class);

    public void add(String str, InetAddress... inetAddressArr) {
        Args.notNull(str, "Host name");
        Args.notNull(inetAddressArr, "Array of IP addresses");
        this.a.put(str, inetAddressArr);
    }

    public InetAddress[] resolve(String str) {
        InetAddress[] inetAddressArr = (InetAddress[]) this.a.get(str);
        if (this.log.isInfoEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Resolving ");
            sb.append(str);
            sb.append(" to ");
            sb.append(Arrays.deepToString(inetAddressArr));
            httpClientAndroidLog.info(sb.toString());
        }
        if (inetAddressArr != null) {
            return inetAddressArr;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(" cannot be resolved");
        throw new UnknownHostException(sb2.toString());
    }
}
