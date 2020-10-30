package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public class IgnoreSpec extends CookieSpecBase {
    public int getVersion() {
        return 0;
    }

    public Header getVersionHeader() {
        return null;
    }

    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) {
        return Collections.emptyList();
    }

    public List<Header> formatCookies(List<Cookie> list) {
        return Collections.emptyList();
    }
}
