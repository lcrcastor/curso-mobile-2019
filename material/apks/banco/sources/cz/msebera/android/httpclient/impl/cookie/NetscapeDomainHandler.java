package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.util.Locale;
import java.util.StringTokenizer;

@Immutable
public class NetscapeDomainHandler extends BasicDomainHandler {
    public String getAttributeName() {
        return ClientCookie.DOMAIN_ATTR;
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(str)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        }
        setCookie.setDomain(str);
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (!host.equals(domain) && !BasicDomainHandler.a(domain, host)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Illegal domain attribute \"");
            sb.append(domain);
            sb.append("\". Domain of origin: \"");
            sb.append(host);
            sb.append("\"");
            throw new CookieRestrictionViolationException(sb.toString());
        } else if (host.contains(".")) {
            int countTokens = new StringTokenizer(domain, ".").countTokens();
            if (a(domain)) {
                if (countTokens < 2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Domain attribute \"");
                    sb2.append(domain);
                    sb2.append("\" violates the Netscape cookie specification for ");
                    sb2.append("special domains");
                    throw new CookieRestrictionViolationException(sb2.toString());
                }
            } else if (countTokens < 3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Domain attribute \"");
                sb3.append(domain);
                sb3.append("\" violates the Netscape cookie specification");
                throw new CookieRestrictionViolationException(sb3.toString());
            }
        }
    }

    private static boolean a(String str) {
        String upperCase = str.toUpperCase(Locale.ROOT);
        return upperCase.endsWith(".COM") || upperCase.endsWith(".EDU") || upperCase.endsWith(".NET") || upperCase.endsWith(".GOV") || upperCase.endsWith(".MIL") || upperCase.endsWith(".ORG") || upperCase.endsWith(".INT");
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String host = cookieOrigin.getHost();
        String domain = cookie.getDomain();
        if (domain == null) {
            return false;
        }
        return host.endsWith(domain);
    }
}
