package okhttp3;

import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

public final class Challenge {
    private final String a;
    private final String b;
    private final Charset c;

    public Challenge(String str, String str2) {
        this(str, str2, Util.ISO_8859_1);
    }

    private Challenge(String str, String str2, Charset charset) {
        if (str == null) {
            throw new NullPointerException("scheme == null");
        } else if (str2 == null) {
            throw new NullPointerException("realm == null");
        } else if (charset == null) {
            throw new NullPointerException("charset == null");
        } else {
            this.a = str;
            this.b = str2;
            this.c = charset;
        }
    }

    public Challenge withCharset(Charset charset) {
        return new Challenge(this.a, this.b, charset);
    }

    public String scheme() {
        return this.a;
    }

    public String realm() {
        return this.b;
    }

    public Charset charset() {
        return this.c;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Challenge) {
            Challenge challenge = (Challenge) obj;
            if (challenge.a.equals(this.a) && challenge.b.equals(this.b) && challenge.c.equals(this.c)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((899 + this.b.hashCode()) * 31) + this.a.hashCode()) * 31) + this.c.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(" realm=\"");
        sb.append(this.b);
        sb.append("\" charset=\"");
        sb.append(this.c);
        sb.append("\"");
        return sb.toString();
    }
}
