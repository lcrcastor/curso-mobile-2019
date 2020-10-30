package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import java.util.Date;

@Immutable
class CacheValidityPolicy {
    CacheValidityPolicy() {
    }

    public long a(HttpCacheEntry httpCacheEntry, Date date) {
        return l(httpCacheEntry) + d(httpCacheEntry, date);
    }

    public long a(HttpCacheEntry httpCacheEntry) {
        long m = m(httpCacheEntry);
        if (m > -1) {
            return m;
        }
        Date date = httpCacheEntry.getDate();
        if (date == null) {
            return 0;
        }
        Date n = n(httpCacheEntry);
        if (n == null) {
            return 0;
        }
        return (n.getTime() - date.getTime()) / 1000;
    }

    public boolean b(HttpCacheEntry httpCacheEntry, Date date) {
        return a(httpCacheEntry, date) < a(httpCacheEntry);
    }

    public boolean a(HttpCacheEntry httpCacheEntry, Date date, float f, long j) {
        return a(httpCacheEntry, date) < a(httpCacheEntry, f, j);
    }

    public long a(HttpCacheEntry httpCacheEntry, float f, long j) {
        Date date = httpCacheEntry.getDate();
        Date d = d(httpCacheEntry);
        if (date == null || d == null) {
            return j;
        }
        long time = date.getTime() - d.getTime();
        if (time < 0) {
            return 0;
        }
        return (long) (f * ((float) (time / 1000)));
    }

    public boolean b(HttpCacheEntry httpCacheEntry) {
        return a(httpCacheEntry, HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE);
    }

    public boolean c(HttpCacheEntry httpCacheEntry) {
        return a(httpCacheEntry, HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE);
    }

    public boolean c(HttpCacheEntry httpCacheEntry, Date date) {
        HeaderElement[] elements;
        for (Header elements2 : httpCacheEntry.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : elements2.getElements()) {
                if (HeaderConstants.STALE_WHILE_REVALIDATE.equalsIgnoreCase(headerElement.getName())) {
                    try {
                        if (e(httpCacheEntry, date) <= ((long) Integer.parseInt(headerElement.getValue()))) {
                            return true;
                        }
                    } catch (NumberFormatException unused) {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public boolean a(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        long e = e(httpCacheEntry, date);
        return a(httpRequest.getHeaders("Cache-Control"), e) || a(httpCacheEntry.getHeaders("Cache-Control"), e);
    }

    private boolean a(Header[] headerArr, long j) {
        boolean z = false;
        for (Header elements : headerArr) {
            HeaderElement[] elements2 = elements.getElements();
            int length = elements2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                HeaderElement headerElement = elements2[i];
                if (HeaderConstants.STALE_IF_ERROR.equals(headerElement.getName())) {
                    try {
                        if (j <= ((long) Integer.parseInt(headerElement.getValue()))) {
                            z = true;
                            break;
                        }
                    } catch (NumberFormatException unused) {
                        continue;
                    }
                }
                i++;
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public Date d(HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Last-Modified");
        if (firstHeader == null) {
            return null;
        }
        return DateUtils.parseDate(firstHeader.getValue());
    }

    /* access modifiers changed from: protected */
    public long e(HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Content-Length");
        if (firstHeader == null) {
            return -1;
        }
        try {
            return Long.parseLong(firstHeader.getValue());
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    /* access modifiers changed from: protected */
    public boolean f(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry.getFirstHeader("Content-Length") != null;
    }

    /* access modifiers changed from: protected */
    public boolean g(HttpCacheEntry httpCacheEntry) {
        return !f(httpCacheEntry) || (httpCacheEntry.getResource() != null && e(httpCacheEntry) == httpCacheEntry.getResource().length());
    }

    /* access modifiers changed from: protected */
    public long h(HttpCacheEntry httpCacheEntry) {
        Date date = httpCacheEntry.getDate();
        if (date == null) {
            return 2147483648L;
        }
        long time = httpCacheEntry.getResponseDate().getTime() - date.getTime();
        if (time < 0) {
            return 0;
        }
        return time / 1000;
    }

    /* access modifiers changed from: protected */
    public long i(HttpCacheEntry httpCacheEntry) {
        long j = 0;
        for (Header value : httpCacheEntry.getHeaders("Age")) {
            long j2 = 2147483648L;
            try {
                long parseLong = Long.parseLong(value.getValue());
                if (parseLong >= 0) {
                    j2 = parseLong;
                }
            } catch (NumberFormatException unused) {
            }
            if (j2 > j) {
                j = j2;
            }
        }
        return j;
    }

    /* access modifiers changed from: protected */
    public long j(HttpCacheEntry httpCacheEntry) {
        long h = h(httpCacheEntry);
        long i = i(httpCacheEntry);
        return h > i ? h : i;
    }

    /* access modifiers changed from: protected */
    public long k(HttpCacheEntry httpCacheEntry) {
        return (httpCacheEntry.getResponseDate().getTime() - httpCacheEntry.getRequestDate().getTime()) / 1000;
    }

    /* access modifiers changed from: protected */
    public long l(HttpCacheEntry httpCacheEntry) {
        return j(httpCacheEntry) + k(httpCacheEntry);
    }

    /* access modifiers changed from: protected */
    public long d(HttpCacheEntry httpCacheEntry, Date date) {
        return (date.getTime() - httpCacheEntry.getResponseDate().getTime()) / 1000;
    }

    /* access modifiers changed from: protected */
    public long m(HttpCacheEntry httpCacheEntry) {
        HeaderElement[] elements;
        Header[] headers = httpCacheEntry.getHeaders("Cache-Control");
        int length = headers.length;
        long j = -1;
        int i = 0;
        while (i < length) {
            long j2 = j;
            for (HeaderElement headerElement : headers[i].getElements()) {
                if ("max-age".equals(headerElement.getName()) || "s-maxage".equals(headerElement.getName())) {
                    try {
                        long parseLong = Long.parseLong(headerElement.getValue());
                        if (j2 == -1 || parseLong < j2) {
                            j2 = parseLong;
                        }
                    } catch (NumberFormatException unused) {
                        j2 = 0;
                    }
                }
            }
            i++;
            j = j2;
        }
        return j;
    }

    /* access modifiers changed from: protected */
    public Date n(HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Expires");
        if (firstHeader == null) {
            return null;
        }
        return DateUtils.parseDate(firstHeader.getValue());
    }

    public boolean a(HttpCacheEntry httpCacheEntry, String str) {
        for (Header elements : httpCacheEntry.getHeaders("Cache-Control")) {
            for (HeaderElement name : elements.getElements()) {
                if (str.equalsIgnoreCase(name.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public long e(HttpCacheEntry httpCacheEntry, Date date) {
        long a = a(httpCacheEntry, date);
        long a2 = a(httpCacheEntry);
        if (a <= a2) {
            return 0;
        }
        return a - a2;
    }
}
