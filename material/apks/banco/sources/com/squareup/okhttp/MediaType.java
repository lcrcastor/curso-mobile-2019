package com.squareup.okhttp;

import io.fabric.sdk.android.services.network.HttpRequest;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType {
    private static final Pattern a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    private static final Pattern b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    private final String c;
    private final String d;
    private final String e;
    private final String f;

    private MediaType(String str, String str2, String str3, String str4) {
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
    }

    public static MediaType parse(String str) {
        String str2;
        Matcher matcher = a.matcher(str);
        if (!matcher.lookingAt()) {
            return null;
        }
        String lowerCase = matcher.group(1).toLowerCase(Locale.US);
        String lowerCase2 = matcher.group(2).toLowerCase(Locale.US);
        Matcher matcher2 = b.matcher(str);
        String str3 = null;
        for (int end = matcher.end(); end < str.length(); end = matcher2.end()) {
            matcher2.region(end, str.length());
            if (!matcher2.lookingAt()) {
                return null;
            }
            String group = matcher2.group(1);
            if (group != null && group.equalsIgnoreCase(HttpRequest.PARAM_CHARSET)) {
                if (matcher2.group(2) != null) {
                    str2 = matcher2.group(2);
                } else {
                    str2 = matcher2.group(3);
                }
                if (str3 == null || str2.equalsIgnoreCase(str3)) {
                    str3 = str2;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Multiple different charsets: ");
                    sb.append(str);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
        }
        return new MediaType(str, lowerCase, lowerCase2, str3);
    }

    public String type() {
        return this.d;
    }

    public String subtype() {
        return this.e;
    }

    public Charset charset() {
        if (this.f != null) {
            return Charset.forName(this.f);
        }
        return null;
    }

    public Charset charset(Charset charset) {
        return this.f != null ? Charset.forName(this.f) : charset;
    }

    public String toString() {
        return this.c;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MediaType) && ((MediaType) obj).c.equals(this.c);
    }

    public int hashCode() {
        return this.c.hashCode();
    }
}
