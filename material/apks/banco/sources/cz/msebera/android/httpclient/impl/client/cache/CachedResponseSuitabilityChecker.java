package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.util.Date;

@Immutable
class CachedResponseSuitabilityChecker {
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());
    private final boolean b;
    private final boolean c;
    private final float d;
    private final long e;
    private final CacheValidityPolicy f;

    CachedResponseSuitabilityChecker(CacheValidityPolicy cacheValidityPolicy, CacheConfig cacheConfig) {
        this.f = cacheValidityPolicy;
        this.b = cacheConfig.isSharedCache();
        this.c = cacheConfig.isHeuristicCachingEnabled();
        this.d = cacheConfig.getHeuristicCoefficient();
        this.e = cacheConfig.getHeuristicDefaultLifetime();
    }

    private boolean a(HttpCacheEntry httpCacheEntry, HttpRequest httpRequest, Date date) {
        boolean z = true;
        if (this.f.b(httpCacheEntry, date)) {
            return true;
        }
        if (this.c) {
            if (this.f.a(httpCacheEntry, date, this.d, this.e)) {
                return true;
            }
        }
        if (a(httpCacheEntry)) {
            return false;
        }
        long b2 = b(httpRequest);
        if (b2 == -1) {
            return false;
        }
        if (b2 <= this.f.e(httpCacheEntry, date)) {
            z = false;
        }
        return z;
    }

    private boolean a(HttpCacheEntry httpCacheEntry) {
        boolean z = true;
        if (this.f.b(httpCacheEntry)) {
            return true;
        }
        if (!this.b) {
            return false;
        }
        if (!this.f.c(httpCacheEntry) && !this.f.a(httpCacheEntry, "s-maxage")) {
            z = false;
        }
        return z;
    }

    private long b(HttpRequest httpRequest) {
        HeaderElement[] elements;
        Header[] headers = httpRequest.getHeaders("Cache-Control");
        int length = headers.length;
        long j = -1;
        int i = 0;
        while (i < length) {
            long j2 = j;
            for (HeaderElement headerElement : headers[i].getElements()) {
                long j3 = 0;
                if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(headerElement.getName())) {
                    if ((headerElement.getValue() == null || "".equals(headerElement.getValue().trim())) && j2 == -1) {
                        j2 = Long.MAX_VALUE;
                    } else {
                        try {
                            long parseLong = Long.parseLong(headerElement.getValue());
                            if (parseLong >= 0) {
                                j3 = parseLong;
                            }
                            if (j2 != -1 && j3 >= j2) {
                            }
                        } catch (NumberFormatException unused) {
                        }
                        j2 = j3;
                    }
                }
            }
            i++;
            j = j2;
        }
        return j;
    }

    public boolean a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        int i;
        HttpRequest httpRequest2 = httpRequest;
        HttpCacheEntry httpCacheEntry2 = httpCacheEntry;
        Date date2 = date;
        boolean z = false;
        if (!a(httpCacheEntry2, httpRequest2, date2)) {
            this.a.trace("Cache entry was not fresh enough");
            return false;
        } else if (c(httpRequest2) && !this.f.g(httpCacheEntry2)) {
            this.a.debug("Cache entry Content-Length and header information do not match");
            return false;
        } else if (d(httpRequest2)) {
            this.a.debug("Request contained conditional headers we don't handle");
            return false;
        } else if (!a(httpRequest2) && httpCacheEntry.getStatusCode() == 304) {
            return false;
        } else {
            if (a(httpRequest2) && !a(httpRequest2, httpCacheEntry2, date2)) {
                return false;
            }
            if (a(httpRequest2, httpCacheEntry2)) {
                this.a.debug("HEAD response caching enabled but the cache entry does not contain a request method, entity or a 204 response");
                return false;
            }
            Header[] headers = httpRequest2.getHeaders("Cache-Control");
            int length = headers.length;
            int i2 = 0;
            while (i2 < length) {
                HeaderElement[] elements = headers[i2].getElements();
                int length2 = elements.length;
                int i3 = 0;
                while (i3 < length2) {
                    HeaderElement headerElement = elements[i3];
                    if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName())) {
                        this.a.trace("Response contained NO CACHE directive, cache was not suitable");
                        return z;
                    } else if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(headerElement.getName())) {
                        this.a.trace("Response contained NO STORE directive, cache was not suitable");
                        return z;
                    } else {
                        if ("max-age".equals(headerElement.getName())) {
                            try {
                                i = i2;
                                if (this.f.a(httpCacheEntry2, date2) > ((long) Integer.parseInt(headerElement.getValue()))) {
                                    this.a.trace("Response from cache was NOT suitable due to max age");
                                    return false;
                                }
                            } catch (NumberFormatException e2) {
                                NumberFormatException numberFormatException = e2;
                                HttpClientAndroidLog httpClientAndroidLog = this.a;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Response from cache was malformed");
                                sb.append(numberFormatException.getMessage());
                                httpClientAndroidLog.debug(sb.toString());
                                return false;
                            }
                        } else {
                            i = i2;
                        }
                        if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(headerElement.getName())) {
                            try {
                                if (this.f.a(httpCacheEntry2) > ((long) Integer.parseInt(headerElement.getValue()))) {
                                    this.a.trace("Response from cache was not suitable due to Max stale freshness");
                                    return false;
                                }
                            } catch (NumberFormatException e3) {
                                NumberFormatException numberFormatException2 = e3;
                                HttpClientAndroidLog httpClientAndroidLog2 = this.a;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("Response from cache was malformed: ");
                                sb2.append(numberFormatException2.getMessage());
                                httpClientAndroidLog2.debug(sb2.toString());
                                return false;
                            }
                        }
                        if (HeaderConstants.CACHE_CONTROL_MIN_FRESH.equals(headerElement.getName())) {
                            try {
                                long parseLong = Long.parseLong(headerElement.getValue());
                                if (parseLong < 0) {
                                    return false;
                                }
                                if (this.f.a(httpCacheEntry2) - this.f.a(httpCacheEntry2, date2) < parseLong) {
                                    this.a.trace("Response from cache was not suitable due to min fresh freshness requirement");
                                    return false;
                                }
                            } catch (NumberFormatException e4) {
                                NumberFormatException numberFormatException3 = e4;
                                HttpClientAndroidLog httpClientAndroidLog3 = this.a;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("Response from cache was malformed: ");
                                sb3.append(numberFormatException3.getMessage());
                                httpClientAndroidLog3.debug(sb3.toString());
                                return false;
                            }
                        }
                        z = false;
                        i3++;
                        i2 = i;
                    }
                }
                i2++;
            }
            this.a.trace("Response from cache was suitable");
            return true;
        }
    }

    private boolean c(HttpRequest httpRequest) {
        return httpRequest.getRequestLine().getMethod().equals("GET");
    }

    private boolean b(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry.getStatusCode() != 204;
    }

    private boolean c(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry.getRequestMethod() == null && httpCacheEntry.getResource() == null;
    }

    private boolean a(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        return c(httpRequest) && c(httpCacheEntry) && b(httpCacheEntry);
    }

    public boolean a(HttpRequest httpRequest) {
        return e(httpRequest) || f(httpRequest);
    }

    public boolean a(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        boolean e2 = e(httpRequest);
        boolean f2 = f(httpRequest);
        boolean z = e2 && b(httpRequest, httpCacheEntry);
        boolean z2 = f2 && b(httpRequest, httpCacheEntry, date);
        if (e2 && f2 && (!z || !z2)) {
            return false;
        }
        if (!e2 || z) {
            return !f2 || z2;
        }
        return false;
    }

    private boolean d(HttpRequest httpRequest) {
        return (httpRequest.getFirstHeader("If-Range") == null && httpRequest.getFirstHeader("If-Match") == null && !a(httpRequest, "If-Unmodified-Since")) ? false : true;
    }

    private boolean e(HttpRequest httpRequest) {
        return httpRequest.containsHeader("If-None-Match");
    }

    private boolean f(HttpRequest httpRequest) {
        return a(httpRequest, "If-Modified-Since");
    }

    private boolean b(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("ETag");
        String value = firstHeader != null ? firstHeader.getValue() : null;
        Header[] headers = httpRequest.getHeaders("If-None-Match");
        if (headers != null) {
            for (Header elements : headers) {
                for (HeaderElement obj : elements.getElements()) {
                    String obj2 = obj.toString();
                    if (("*".equals(obj2) && value != null) || obj2.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean b(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, Date date) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Last-Modified");
        Date parseDate = firstHeader != null ? DateUtils.parseDate(firstHeader.getValue()) : null;
        if (parseDate == null) {
            return false;
        }
        for (Header value : httpRequest.getHeaders("If-Modified-Since")) {
            Date parseDate2 = DateUtils.parseDate(value.getValue());
            if (parseDate2 != null && (parseDate2.after(date) || parseDate.after(parseDate2))) {
                return false;
            }
        }
        return true;
    }

    private boolean a(HttpRequest httpRequest, String str) {
        Header[] headers = httpRequest.getHeaders(str);
        boolean z = false;
        if (headers.length <= 0) {
            return false;
        }
        if (DateUtils.parseDate(headers[0].getValue()) != null) {
            z = true;
        }
        return z;
    }
}
