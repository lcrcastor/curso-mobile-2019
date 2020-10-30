package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.LangUtils;

@NotThreadSafe
public class BasicHeaderElement implements HeaderElement, Cloneable {
    private final String a;
    private final String b;
    private final NameValuePair[] c;

    public BasicHeaderElement(String str, String str2, NameValuePair[] nameValuePairArr) {
        this.a = (String) Args.notNull(str, "Name");
        this.b = str2;
        if (nameValuePairArr != null) {
            this.c = nameValuePairArr;
        } else {
            this.c = new NameValuePair[0];
        }
    }

    public BasicHeaderElement(String str, String str2) {
        this(str, str2, null);
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public NameValuePair[] getParameters() {
        return (NameValuePair[]) this.c.clone();
    }

    public int getParameterCount() {
        return this.c.length;
    }

    public NameValuePair getParameter(int i) {
        return this.c[i];
    }

    public NameValuePair getParameterByName(String str) {
        NameValuePair[] nameValuePairArr;
        Args.notNull(str, "Name");
        for (NameValuePair nameValuePair : this.c) {
            if (nameValuePair.getName().equalsIgnoreCase(str)) {
                return nameValuePair;
            }
        }
        return null;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HeaderElement)) {
            return false;
        }
        BasicHeaderElement basicHeaderElement = (BasicHeaderElement) obj;
        if (!this.a.equals(basicHeaderElement.a) || !LangUtils.equals((Object) this.b, (Object) basicHeaderElement.b) || !LangUtils.equals((Object[]) this.c, (Object[]) basicHeaderElement.c)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int hashCode = LangUtils.hashCode(LangUtils.hashCode(17, (Object) this.a), (Object) this.b);
        for (NameValuePair hashCode2 : this.c) {
            hashCode = LangUtils.hashCode(hashCode, (Object) hashCode2);
        }
        return hashCode;
    }

    public String toString() {
        NameValuePair[] nameValuePairArr;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        if (this.b != null) {
            sb.append("=");
            sb.append(this.b);
        }
        for (NameValuePair nameValuePair : this.c) {
            sb.append("; ");
            sb.append(nameValuePair);
        }
        return sb.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
