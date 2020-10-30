package cz.msebera.android.httpclient.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import java.io.Serializable;
import java.util.Comparator;

@Immutable
public class CookiePathComparator implements Serializable, Comparator<Cookie> {
    public static final CookiePathComparator INSTANCE = new CookiePathComparator();
    private static final long serialVersionUID = 7523645369616405818L;

    private String a(Cookie cookie) {
        String path = cookie.getPath();
        if (path == null) {
            path = "/";
        }
        if (path.endsWith("/")) {
            return path;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append('/');
        return sb.toString();
    }

    public int compare(Cookie cookie, Cookie cookie2) {
        String a = a(cookie);
        String a2 = a(cookie2);
        if (a.equals(a2)) {
            return 0;
        }
        if (a.startsWith(a2)) {
            return -1;
        }
        if (a2.startsWith(a)) {
            return 1;
        }
        return 0;
    }
}
