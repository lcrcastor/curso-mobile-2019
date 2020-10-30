package org.bouncycastle.util.io.pem;

public class PemHeader {
    private String a;
    private String b;

    public PemHeader(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    private int a(String str) {
        if (str == null) {
            return 1;
        }
        return str.hashCode();
    }

    private boolean a(String str, String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof PemHeader)) {
            return false;
        }
        PemHeader pemHeader = (PemHeader) obj;
        if (pemHeader == this || (a(this.a, pemHeader.a) && a(this.b, pemHeader.b))) {
            z = true;
        }
        return z;
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public int hashCode() {
        return a(this.a) + (a(this.b) * 31);
    }
}
