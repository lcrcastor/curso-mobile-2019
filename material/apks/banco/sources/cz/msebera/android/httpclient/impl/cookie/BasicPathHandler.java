package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;

@Immutable
public class BasicPathHandler implements CommonCookieAttributeHandler {
    public String getAttributeName() {
        return ClientCookie.PATH_ATTR;
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(str)) {
            str = "/";
        }
        setCookie.setPath(str);
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
        if (!match(cookie, cookieOrigin)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal 'path' attribute \"");
            sb.append(cookie.getPath());
            sb.append("\". Path of origin: \"");
            sb.append(cookieOrigin.getPath());
            sb.append("\"");
            throw new CookieRestrictionViolationException(sb.toString());
        }
    }

    static boolean a(String str, String str2) {
        if (str2 == null) {
            str2 = "/";
        }
        if (str2.length() > 1 && str2.endsWith("/")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        return str.startsWith(str2) && (str2.equals("/") || str.length() == str2.length() || str.charAt(str2.length()) == '/');
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return a(cookieOrigin.getPath(), cookie.getPath());
    }
}
