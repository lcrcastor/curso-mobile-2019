package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;
import java.io.Serializable;

@Immutable
public class BasicNameValuePair implements NameValuePair, Serializable, Cloneable {
    private static final long serialVersionUID = -6437800749411518984L;
    private final String a;
    private final String b;

    public BasicNameValuePair(String str, String str2) {
        this.a = (String) Args.notNull(str, "Name");
        this.b = str2;
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public String toString() {
        if (this.b == null) {
            return this.a;
        }
        StringBuilder sb = new StringBuilder(this.a.length() + 1 + this.b.length());
        sb.append(this.a);
        sb.append("=");
        sb.append(this.b);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NameValuePair)) {
            return false;
        }
        BasicNameValuePair basicNameValuePair = (BasicNameValuePair) obj;
        if (!this.a.equals(basicNameValuePair.a) || !LangUtils.equals((Object) this.b, (Object) basicNameValuePair.b)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return LangUtils.hashCode(LangUtils.hashCode(17, (Object) this.a), (Object) this.b);
    }

    public Object clone() {
        return super.clone();
    }
}
