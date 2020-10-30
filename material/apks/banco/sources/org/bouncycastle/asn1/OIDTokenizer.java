package org.bouncycastle.asn1;

public class OIDTokenizer {
    private String a;
    private int b = 0;

    public OIDTokenizer(String str) {
        this.a = str;
    }

    public boolean hasMoreTokens() {
        return this.b != -1;
    }

    public String nextToken() {
        if (this.b == -1) {
            return null;
        }
        int indexOf = this.a.indexOf(46, this.b);
        if (indexOf == -1) {
            String substring = this.a.substring(this.b);
            this.b = -1;
            return substring;
        }
        String substring2 = this.a.substring(this.b, indexOf);
        this.b = indexOf + 1;
        return substring2;
    }
}
