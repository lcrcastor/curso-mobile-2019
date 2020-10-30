package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import java.util.Locale;

@Immutable
public class RFC2109DomainHandler implements CommonCookieAttributeHandler {
    public String getAttributeName() {
        return ClientCookie.DOMAIN_ATTR;
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (str == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        } else if (str.trim().isEmpty()) {
            throw new MalformedCookieException("Blank value for domain attribute");
        } else {
            setCookie.setDomain(str);
        }
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        } else if (domain.equals(host)) {
        } else {
            if (domain.indexOf(46) == -1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Domain attribute \"");
                sb.append(domain);
                sb.append("\" does not match the host \"");
                sb.append(host);
                sb.append("\"");
                throw new CookieRestrictionViolationException(sb.toString());
            } else if (!domain.startsWith(".")) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Domain attribute \"");
                sb2.append(domain);
                sb2.append("\" violates RFC 2109: domain must start with a dot");
                throw new CookieRestrictionViolationException(sb2.toString());
            } else {
                int indexOf = domain.indexOf(46, 1);
                if (indexOf < 0 || indexOf == domain.length() - 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Domain attribute \"");
                    sb3.append(domain);
                    sb3.append("\" violates RFC 2109: domain must contain an embedded dot");
                    throw new CookieRestrictionViolationException(sb3.toString());
                }
                String lowerCase = host.toLowerCase(Locale.ROOT);
                if (!lowerCase.endsWith(domain)) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Illegal domain attribute \"");
                    sb4.append(domain);
                    sb4.append("\". Domain of origin: \"");
                    sb4.append(lowerCase);
                    sb4.append("\"");
                    throw new CookieRestrictionViolationException(sb4.toString());
                } else if (lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) != -1) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Domain attribute \"");
                    sb5.append(domain);
                    sb5.append("\" violates RFC 2109: host minus domain may not contain any dots");
                    throw new CookieRestrictionViolationException(sb5.toString());
                }
            }
        }
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        boolean z = false;
        if (domain == null) {
            return false;
        }
        if (host.equals(domain) || (domain.startsWith(".") && host.endsWith(domain))) {
            z = true;
        }
        return z;
    }
}
