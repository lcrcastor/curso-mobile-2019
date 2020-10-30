package com.google.common.html;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import cz.msebera.android.httpclient.message.TokenParser;

@GwtCompatible
@Beta
public final class HtmlEscapers {
    private static final Escaper a = Escapers.builder().addEscape(TokenParser.DQUOTE, "&quot;").addEscape('\'', "&#39;").addEscape('&', "&amp;").addEscape('<', "&lt;").addEscape('>', "&gt;").build();

    public static Escaper htmlEscaper() {
        return a;
    }

    private HtmlEscapers() {
    }
}
