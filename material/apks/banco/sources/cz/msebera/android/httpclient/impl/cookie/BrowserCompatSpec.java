package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.impl.cookie.BrowserCompatSpecFactory.SecurityLevel;
import cz.msebera.android.httpclient.message.BasicHeaderElement;
import cz.msebera.android.httpclient.message.BasicHeaderValueFormatter;
import cz.msebera.android.httpclient.message.BufferedHeader;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@ThreadSafe
public class BrowserCompatSpec extends CookieSpecBase {
    private static final String[] a = {"EEE, dd MMM yyyy HH:mm:ss zzz", "EEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z"};

    public int getVersion() {
        return 0;
    }

    public Header getVersionHeader() {
        return null;
    }

    public String toString() {
        return "compatibility";
    }

    public BrowserCompatSpec(String[] strArr, SecurityLevel securityLevel) {
        CommonCookieAttributeHandler[] commonCookieAttributeHandlerArr = new CommonCookieAttributeHandler[7];
        commonCookieAttributeHandlerArr[0] = new BrowserCompatVersionAttributeHandler();
        commonCookieAttributeHandlerArr[1] = new BasicDomainHandler();
        commonCookieAttributeHandlerArr[2] = securityLevel == SecurityLevel.SECURITYLEVEL_IE_MEDIUM ? new BasicPathHandler() {
            public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
            }
        } : new BasicPathHandler();
        commonCookieAttributeHandlerArr[3] = new BasicMaxAgeHandler();
        commonCookieAttributeHandlerArr[4] = new BasicSecureHandler();
        commonCookieAttributeHandlerArr[5] = new BasicCommentHandler();
        commonCookieAttributeHandlerArr[6] = new BasicExpiresHandler(strArr != null ? (String[]) strArr.clone() : a);
        super(commonCookieAttributeHandlerArr);
    }

    public BrowserCompatSpec(String[] strArr) {
        this(strArr, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public BrowserCompatSpec() {
        this(null, SecurityLevel.SECURITYLEVEL_DEFAULT);
    }

    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) {
        ParserCursor parserCursor;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        Args.notNull(cookieOrigin, "Cookie origin");
        if (!header.getName().equalsIgnoreCase("Set-Cookie")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized cookie header '");
            sb.append(header.toString());
            sb.append("'");
            throw new MalformedCookieException(sb.toString());
        }
        HeaderElement[] elements = header.getElements();
        boolean z = false;
        boolean z2 = false;
        for (HeaderElement headerElement : elements) {
            if (headerElement.getParameterByName("version") != null) {
                z2 = true;
            }
            if (headerElement.getParameterByName(ClientCookie.EXPIRES_ATTR) != null) {
                z = true;
            }
        }
        if (!z && z2) {
            return parse(elements, cookieOrigin);
        }
        NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
        if (header instanceof FormattedHeader) {
            FormattedHeader formattedHeader = (FormattedHeader) header;
            charArrayBuffer = formattedHeader.getBuffer();
            parserCursor = new ParserCursor(formattedHeader.getValuePos(), charArrayBuffer.length());
        } else {
            String value = header.getValue();
            if (value == null) {
                throw new MalformedCookieException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(value.length());
            charArrayBuffer.append(value);
            parserCursor = new ParserCursor(0, charArrayBuffer.length());
        }
        HeaderElement parseHeader = netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor);
        String name = parseHeader.getName();
        String value2 = parseHeader.getValue();
        if (name == null || name.isEmpty()) {
            throw new MalformedCookieException("Cookie name may not be empty");
        }
        BasicClientCookie basicClientCookie = new BasicClientCookie(name, value2);
        basicClientCookie.setPath(getDefaultPath(cookieOrigin));
        basicClientCookie.setDomain(getDefaultDomain(cookieOrigin));
        NameValuePair[] parameters = parseHeader.getParameters();
        for (int length = parameters.length - 1; length >= 0; length--) {
            NameValuePair nameValuePair = parameters[length];
            String lowerCase = nameValuePair.getName().toLowerCase(Locale.ROOT);
            basicClientCookie.setAttribute(lowerCase, nameValuePair.getValue());
            CookieAttributeHandler findAttribHandler = findAttribHandler(lowerCase);
            if (findAttribHandler != null) {
                findAttribHandler.parse(basicClientCookie, nameValuePair.getValue());
            }
        }
        if (z) {
            basicClientCookie.setVersion(0);
        }
        return Collections.singletonList(basicClientCookie);
    }

    private static boolean a(String str) {
        return str != null && str.startsWith("\"") && str.endsWith("\"");
    }

    public List<Header> formatCookies(List<Cookie> list) {
        Args.notEmpty(list, "List of cookies");
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(list.size() * 20);
        charArrayBuffer.append("Cookie");
        charArrayBuffer.append(": ");
        for (int i = 0; i < list.size(); i++) {
            Cookie cookie = (Cookie) list.get(i);
            if (i > 0) {
                charArrayBuffer.append("; ");
            }
            String name = cookie.getName();
            String value = cookie.getValue();
            if (cookie.getVersion() <= 0 || a(value)) {
                charArrayBuffer.append(name);
                charArrayBuffer.append("=");
                if (value != null) {
                    charArrayBuffer.append(value);
                }
            } else {
                BasicHeaderValueFormatter.INSTANCE.formatHeaderElement(charArrayBuffer, (HeaderElement) new BasicHeaderElement(name, value), false);
            }
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new BufferedHeader(charArrayBuffer));
        return arrayList;
    }
}
