package okhttp3;

import cz.msebera.android.httpclient.message.TokenParser;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public final class MediaType {
    private static final Pattern a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    private static final Pattern b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    private final String c;
    private final String d;
    private final String e;
    @Nullable
    private final String f;

    private MediaType(String str, String str2, String str3, @Nullable String str4) {
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
    }

    public static MediaType get(String str) {
        Matcher matcher = a.matcher(str);
        if (!matcher.lookingAt()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No subtype found for: \"");
            sb.append(str);
            sb.append(TokenParser.DQUOTE);
            throw new IllegalArgumentException(sb.toString());
        }
        String lowerCase = matcher.group(1).toLowerCase(Locale.US);
        String lowerCase2 = matcher.group(2).toLowerCase(Locale.US);
        String str2 = null;
        Matcher matcher2 = b.matcher(str);
        for (int end = matcher.end(); end < str.length(); end = matcher2.end()) {
            matcher2.region(end, str.length());
            if (!matcher2.lookingAt()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Parameter is not formatted correctly: \"");
                sb2.append(str.substring(end));
                sb2.append("\" for: \"");
                sb2.append(str);
                sb2.append(TokenParser.DQUOTE);
                throw new IllegalArgumentException(sb2.toString());
            }
            String group = matcher2.group(1);
            if (group != null && group.equalsIgnoreCase(HttpRequest.PARAM_CHARSET)) {
                String group2 = matcher2.group(2);
                if (group2 == null) {
                    group2 = matcher2.group(3);
                } else if (group2.startsWith("'") && group2.endsWith("'") && group2.length() > 2) {
                    group2 = group2.substring(1, group2.length() - 1);
                }
                if (str2 == null || group2.equalsIgnoreCase(str2)) {
                    str2 = group2;
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Multiple charsets defined: \"");
                    sb3.append(str2);
                    sb3.append("\" and: \"");
                    sb3.append(group2);
                    sb3.append("\" for: \"");
                    sb3.append(str);
                    sb3.append(TokenParser.DQUOTE);
                    throw new IllegalArgumentException(sb3.toString());
                }
            }
        }
        return new MediaType(str, lowerCase, lowerCase2, str2);
    }

    @Nullable
    public static MediaType parse(String str) {
        try {
            return get(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public String type() {
        return this.d;
    }

    public String subtype() {
        return this.e;
    }

    @Nullable
    public Charset charset() {
        return charset(null);
    }

    @Nullable
    public Charset charset(@Nullable Charset charset) {
        try {
            if (this.f != null) {
                charset = Charset.forName(this.f);
            }
            return charset;
        } catch (IllegalArgumentException unused) {
            return charset;
        }
    }

    public String toString() {
        return this.c;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof MediaType) && ((MediaType) obj).c.equals(this.c);
    }

    public int hashCode() {
        return this.c.hashCode();
    }
}
