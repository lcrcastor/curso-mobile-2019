package cz.msebera.android.httpclient.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.util.Locale;

@Immutable
public final class CookieOrigin {
    private final String a;
    private final int b;
    private final String c;
    private final boolean d;

    public CookieOrigin(String str, int i, String str2, boolean z) {
        Args.notBlank(str, "Host");
        Args.notNegative(i, "Port");
        Args.notNull(str2, "Path");
        this.a = str.toLowerCase(Locale.ROOT);
        this.b = i;
        if (!TextUtils.isBlank(str2)) {
            this.c = str2;
        } else {
            this.c = "/";
        }
        this.d = z;
    }

    public String getHost() {
        return this.a;
    }

    public String getPath() {
        return this.c;
    }

    public int getPort() {
        return this.b;
    }

    public boolean isSecure() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        if (this.d) {
            sb.append("(secure)");
        }
        sb.append(this.a);
        sb.append(':');
        sb.append(Integer.toString(this.b));
        sb.append(this.c);
        sb.append(']');
        return sb.toString();
    }
}
