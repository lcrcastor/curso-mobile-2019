package cz.msebera.android.httpclient.auth;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;

@Immutable
public class NTUserPrincipal implements Serializable, Principal {
    private static final long serialVersionUID = -6870169797924406894L;
    private final String a;
    private final String b;
    private final String c;

    public NTUserPrincipal(String str, String str2) {
        Args.notNull(str2, "User name");
        this.a = str2;
        if (str != null) {
            this.b = str.toUpperCase(Locale.ROOT);
        } else {
            this.b = null;
        }
        if (this.b == null || this.b.isEmpty()) {
            this.c = this.a;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(TokenParser.ESCAPE);
        sb.append(this.a);
        this.c = sb.toString();
    }

    public String getName() {
        return this.c;
    }

    public String getDomain() {
        return this.b;
    }

    public String getUsername() {
        return this.a;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, (Object) this.a), (Object) this.b);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NTUserPrincipal) {
            NTUserPrincipal nTUserPrincipal = (NTUserPrincipal) obj;
            if (LangUtils.equals((Object) this.a, (Object) nTUserPrincipal.a) && LangUtils.equals((Object) this.b, (Object) nTUserPrincipal.b)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.c;
    }
}
