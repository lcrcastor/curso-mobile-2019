package com.google.common.xml;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.google.common.escape.Escapers.Builder;
import cz.msebera.android.httpclient.message.TokenParser;

@GwtCompatible
@Beta
public class XmlEscapers {
    private static final Escaper a;
    private static final Escaper b;
    private static final Escaper c;

    private XmlEscapers() {
    }

    public static Escaper xmlContentEscaper() {
        return b;
    }

    public static Escaper xmlAttributeEscaper() {
        return c;
    }

    static {
        Builder builder = Escapers.builder();
        builder.setSafeRange(0, 65533);
        builder.setUnsafeReplacement("�");
        for (char c2 = 0; c2 <= 31; c2 = (char) (c2 + 1)) {
            if (!(c2 == 9 || c2 == 10 || c2 == 13)) {
                builder.addEscape(c2, "�");
            }
        }
        builder.addEscape('&', "&amp;");
        builder.addEscape('<', "&lt;");
        builder.addEscape('>', "&gt;");
        b = builder.build();
        builder.addEscape('\'', "&apos;");
        builder.addEscape(TokenParser.DQUOTE, "&quot;");
        a = builder.build();
        builder.addEscape(9, "&#x9;");
        builder.addEscape(10, "&#xA;");
        builder.addEscape(TokenParser.CR, "&#xD;");
        c = builder.build();
    }
}
