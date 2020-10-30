package cz.msebera.android.httpclient.conn.scheme;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.util.Locale;

@Immutable
@Deprecated
public final class Scheme {
    private final String a;
    private final SchemeSocketFactory b;
    private final int c;
    private final boolean d;
    private String e;

    public Scheme(String str, int i, SchemeSocketFactory schemeSocketFactory) {
        Args.notNull(str, "Scheme name");
        Args.check(i > 0 && i <= 65535, "Port is invalid");
        Args.notNull(schemeSocketFactory, "Socket factory");
        this.a = str.toLowerCase(Locale.ENGLISH);
        this.c = i;
        if (schemeSocketFactory instanceof SchemeLayeredSocketFactory) {
            this.d = true;
            this.b = schemeSocketFactory;
        } else if (schemeSocketFactory instanceof LayeredSchemeSocketFactory) {
            this.d = true;
            this.b = new SchemeLayeredSocketFactoryAdaptor2((LayeredSchemeSocketFactory) schemeSocketFactory);
        } else {
            this.d = false;
            this.b = schemeSocketFactory;
        }
    }

    @Deprecated
    public Scheme(String str, SocketFactory socketFactory, int i) {
        Args.notNull(str, "Scheme name");
        Args.notNull(socketFactory, "Socket factory");
        Args.check(i > 0 && i <= 65535, "Port is invalid");
        this.a = str.toLowerCase(Locale.ENGLISH);
        if (socketFactory instanceof LayeredSocketFactory) {
            this.b = new SchemeLayeredSocketFactoryAdaptor((LayeredSocketFactory) socketFactory);
            this.d = true;
        } else {
            this.b = new SchemeSocketFactoryAdaptor(socketFactory);
            this.d = false;
        }
        this.c = i;
    }

    public final int getDefaultPort() {
        return this.c;
    }

    @Deprecated
    public final SocketFactory getSocketFactory() {
        if (this.b instanceof SchemeSocketFactoryAdaptor) {
            return ((SchemeSocketFactoryAdaptor) this.b).a();
        }
        if (this.d) {
            return new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory) this.b);
        }
        return new SocketFactoryAdaptor(this.b);
    }

    public final SchemeSocketFactory getSchemeSocketFactory() {
        return this.b;
    }

    public final String getName() {
        return this.a;
    }

    public final boolean isLayered() {
        return this.d;
    }

    public final int resolvePort(int i) {
        return i <= 0 ? this.c : i;
    }

    public final String toString() {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(':');
            sb.append(Integer.toString(this.c));
            this.e = sb.toString();
        }
        return this.e;
    }

    public final boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scheme)) {
            return false;
        }
        Scheme scheme = (Scheme) obj;
        if (!(this.a.equals(scheme.a) && this.c == scheme.c && this.d == scheme.d)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, this.c), (Object) this.a), this.d);
    }
}
