package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.util.Locale;

@Immutable
public class BasicDomainHandler implements CommonCookieAttributeHandler {
    public String getAttributeName() {
        return ClientCookie.DOMAIN_ATTR;
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(str)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        } else if (!str.endsWith(".")) {
            if (str.startsWith(".")) {
                str = str.substring(1);
            }
            setCookie.setDomain(str.toLowerCase(Locale.ROOT));
        }
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie 'domain' may not be null");
        } else if (!host.equals(domain) && !a(domain, host)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal 'domain' attribute \"");
            sb.append(domain);
            sb.append("\". Domain of origin: \"");
            sb.append(host);
            sb.append("\"");
            throw new CookieRestrictionViolationException(sb.toString());
        }
    }

    static boolean a(String str, String str2) {
        if (InetAddressUtils.isIPv4Address(str2) || InetAddressUtils.isIPv6Address(str2)) {
            return false;
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        if (str2.endsWith(str)) {
            int length = str2.length() - str.length();
            if (length == 0) {
                return true;
            }
            if (length <= 1 || str2.charAt(length - 1) != '.') {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        if (domain.startsWith(".")) {
            domain = domain.substring(1);
        }
        String lowerCase = domain.toLowerCase(Locale.ROOT);
        if (host.equals(lowerCase)) {
            return true;
        }
        if (!(cookie instanceof ClientCookie) || !((ClientCookie) cookie).containsAttribute(ClientCookie.DOMAIN_ATTR)) {
            return false;
        }
        return a(lowerCase, host);
    }
}
