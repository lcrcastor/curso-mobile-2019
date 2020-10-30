package cz.msebera.android.httpclient.auth;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.io.Serializable;
import java.security.Principal;
import java.util.Locale;

@Immutable
public class NTCredentials implements Credentials, Serializable {
    private static final long serialVersionUID = -7385699315228907265L;
    private final NTUserPrincipal a;
    private final String b;
    private final String c;

    public NTCredentials(String str) {
        Args.notNull(str, "Username:password string");
        int indexOf = str.indexOf(58);
        if (indexOf >= 0) {
            String substring = str.substring(0, indexOf);
            this.b = str.substring(indexOf + 1);
            str = substring;
        } else {
            this.b = null;
        }
        int indexOf2 = str.indexOf(47);
        if (indexOf2 >= 0) {
            this.a = new NTUserPrincipal(str.substring(0, indexOf2).toUpperCase(Locale.ROOT), str.substring(indexOf2 + 1));
        } else {
            this.a = new NTUserPrincipal(null, str.substring(indexOf2 + 1));
        }
        this.c = null;
    }

    public NTCredentials(String str, String str2, String str3, String str4) {
        Args.notNull(str, "User name");
        this.a = new NTUserPrincipal(str4, str);
        this.b = str2;
        if (str3 != null) {
            this.c = str3.toUpperCase(Locale.ROOT);
        } else {
            this.c = null;
        }
    }

    public Principal getUserPrincipal() {
        return this.a;
    }

    public String getUserName() {
        return this.a.getUsername();
    }

    public String getPassword() {
        return this.b;
    }

    public String getDomain() {
        return this.a.getDomain();
    }

    public String getWorkstation() {
        return this.c;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, (Object) this.a), (Object) this.c);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof NTCredentials) {
            NTCredentials nTCredentials = (NTCredentials) obj;
            if (LangUtils.equals((Object) this.a, (Object) nTCredentials.a) && LangUtils.equals((Object) this.c, (Object) nTCredentials.c)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[principal: ");
        sb.append(this.a);
        sb.append("][workstation: ");
        sb.append(this.c);
        sb.append("]");
        return sb.toString();
    }
}
