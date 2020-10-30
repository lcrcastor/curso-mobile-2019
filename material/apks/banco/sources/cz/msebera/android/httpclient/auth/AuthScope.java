package cz.msebera.android.httpclient.auth;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.util.Locale;

@Immutable
public class AuthScope {
    public static final AuthScope ANY = new AuthScope(ANY_HOST, -1, ANY_REALM, ANY_SCHEME);
    public static final String ANY_HOST = null;
    public static final int ANY_PORT = -1;
    public static final String ANY_REALM = null;
    public static final String ANY_SCHEME = null;
    private final String a;
    private final String b;
    private final String c;
    private final int d;
    private final HttpHost e;

    public AuthScope(String str, int i, String str2, String str3) {
        this.c = str == null ? ANY_HOST : str.toLowerCase(Locale.ROOT);
        if (i < 0) {
            i = -1;
        }
        this.d = i;
        if (str2 == null) {
            str2 = ANY_REALM;
        }
        this.b = str2;
        this.a = str3 == null ? ANY_SCHEME : str3.toUpperCase(Locale.ROOT);
        this.e = null;
    }

    public AuthScope(HttpHost httpHost, String str, String str2) {
        Args.notNull(httpHost, "Host");
        this.c = httpHost.getHostName().toLowerCase(Locale.ROOT);
        this.d = httpHost.getPort() < 0 ? -1 : httpHost.getPort();
        if (str == null) {
            str = ANY_REALM;
        }
        this.b = str;
        this.a = str2 == null ? ANY_SCHEME : str2.toUpperCase(Locale.ROOT);
        this.e = httpHost;
    }

    public AuthScope(HttpHost httpHost) {
        this(httpHost, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(String str, int i, String str2) {
        this(str, i, str2, ANY_SCHEME);
    }

    public AuthScope(String str, int i) {
        this(str, i, ANY_REALM, ANY_SCHEME);
    }

    public AuthScope(AuthScope authScope) {
        Args.notNull(authScope, "Scope");
        this.c = authScope.getHost();
        this.d = authScope.getPort();
        this.b = authScope.getRealm();
        this.a = authScope.getScheme();
        this.e = authScope.getOrigin();
    }

    public HttpHost getOrigin() {
        return this.e;
    }

    public String getHost() {
        return this.c;
    }

    public int getPort() {
        return this.d;
    }

    public String getRealm() {
        return this.b;
    }

    public String getScheme() {
        return this.a;
    }

    public int match(AuthScope authScope) {
        int i;
        if (LangUtils.equals((Object) this.a, (Object) authScope.a)) {
            i = 1;
        } else if (this.a != ANY_SCHEME && authScope.a != ANY_SCHEME) {
            return -1;
        } else {
            i = 0;
        }
        if (LangUtils.equals((Object) this.b, (Object) authScope.b)) {
            i += 2;
        } else if (!(this.b == ANY_REALM || authScope.b == ANY_REALM)) {
            return -1;
        }
        if (this.d == authScope.d) {
            i += 4;
        } else if (!(this.d == -1 || authScope.d == -1)) {
            return -1;
        }
        if (LangUtils.equals((Object) this.c, (Object) authScope.c)) {
            i += 8;
        } else if (this.c == ANY_HOST || authScope.c == ANY_HOST) {
            return i;
        } else {
            return -1;
        }
        return i;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AuthScope)) {
            return super.equals(obj);
        }
        AuthScope authScope = (AuthScope) obj;
        if (LangUtils.equals((Object) this.c, (Object) authScope.c) && this.d == authScope.d && LangUtils.equals((Object) this.b, (Object) authScope.b) && LangUtils.equals((Object) this.a, (Object) authScope.a)) {
            z = true;
        }
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.a != null) {
            sb.append(this.a.toUpperCase(Locale.ROOT));
            sb.append(TokenParser.SP);
        }
        if (this.b != null) {
            sb.append('\'');
            sb.append(this.b);
            sb.append('\'');
        } else {
            sb.append("<any realm>");
        }
        if (this.c != null) {
            sb.append('@');
            sb.append(this.c);
            if (this.d >= 0) {
                sb.append(':');
                sb.append(this.d);
            }
        }
        return sb.toString();
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(17, (Object) this.c), this.d), (Object) this.b), (Object) this.a);
    }
}
