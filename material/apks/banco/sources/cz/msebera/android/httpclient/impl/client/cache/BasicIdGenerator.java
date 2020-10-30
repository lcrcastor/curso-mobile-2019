package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.Locale;

@ThreadSafe
class BasicIdGenerator {
    private final String a;
    private final SecureRandom b;
    @GuardedBy("this")
    private long c;

    public BasicIdGenerator() {
        String str;
        try {
            str = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException unused) {
            str = "localhost";
        }
        this.a = str;
        try {
            this.b = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new Error(e);
        }
    }

    public synchronized void a(StringBuilder sb) {
        this.c++;
        int nextInt = this.b.nextInt();
        sb.append(System.currentTimeMillis());
        sb.append('.');
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%1$016x-%2$08x", new Object[]{Long.valueOf(this.c), Integer.valueOf(nextInt)});
        formatter.close();
        sb.append('.');
        sb.append(this.a);
    }
}
