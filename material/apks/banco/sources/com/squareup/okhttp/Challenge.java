package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;

public final class Challenge {
    private final String a;
    private final String b;

    public Challenge(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public String getScheme() {
        return this.a;
    }

    public String getRealm() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Challenge) {
            Challenge challenge = (Challenge) obj;
            if (Util.equal(this.a, challenge.a) && Util.equal(this.b, challenge.b)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (899 + (this.b != null ? this.b.hashCode() : 0)) * 31;
        if (this.a != null) {
            i = this.a.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(" realm=\"");
        sb.append(this.b);
        sb.append("\"");
        return sb.toString();
    }
}
