package cz.msebera.android.httpclient.client.utils;

import cz.msebera.android.httpclient.annotation.Immutable;

@Immutable
@Deprecated
public class Punycode {
    private static final Idn a;

    static {
        Idn idn;
        try {
            idn = new JdkIdn();
        } catch (Exception unused) {
            idn = new Rfc3492Idn();
        }
        a = idn;
    }

    public static String toUnicode(String str) {
        return a.toUnicode(str);
    }
}
