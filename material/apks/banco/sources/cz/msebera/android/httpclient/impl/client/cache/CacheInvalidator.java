package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Immutable
class CacheInvalidator implements HttpCacheInvalidator {
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());
    private final HttpCacheStorage b;
    private final CacheKeyGenerator c;

    public CacheInvalidator(CacheKeyGenerator cacheKeyGenerator, HttpCacheStorage httpCacheStorage) {
        this.c = cacheKeyGenerator;
        this.b = httpCacheStorage;
    }

    public void flushInvalidatedCacheEntries(HttpHost httpHost, HttpRequest httpRequest) {
        String a2 = this.c.a(httpHost, httpRequest);
        HttpCacheEntry b2 = b(a2);
        if (a(httpRequest) || a(httpRequest, b2)) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Invalidating parent cache entry: ");
            sb.append(b2);
            httpClientAndroidLog.debug(sb.toString());
            if (b2 != null) {
                for (String a3 : b2.getVariantMap().values()) {
                    a(a3);
                }
                a(a2);
            }
            URL c2 = c(a2);
            if (c2 == null) {
                this.a.error("Couldn't transform request into valid URL");
                return;
            }
            Header firstHeader = httpRequest.getFirstHeader("Content-Location");
            if (firstHeader != null) {
                String value = firstHeader.getValue();
                if (!b(c2, value)) {
                    a(c2, value);
                }
            }
            Header firstHeader2 = httpRequest.getFirstHeader("Location");
            if (firstHeader2 != null) {
                b(c2, firstHeader2.getValue());
            }
        }
    }

    private boolean a(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        return b(httpRequest) && a(httpCacheEntry);
    }

    private boolean b(HttpRequest httpRequest) {
        return httpRequest.getRequestLine().getMethod().equals("GET");
    }

    private boolean a(HttpCacheEntry httpCacheEntry) {
        return httpCacheEntry != null && httpCacheEntry.getRequestMethod().equals("HEAD");
    }

    private void a(String str) {
        try {
            this.b.removeEntry(str);
        } catch (IOException e) {
            this.a.warn("unable to flush cache entry", e);
        }
    }

    private HttpCacheEntry b(String str) {
        try {
            return this.b.getEntry(str);
        } catch (IOException e) {
            this.a.warn("could not retrieve entry from storage", e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void a(URL url, URL url2) {
        URL c2 = c(this.c.a(url2.toString()));
        if (c2 != null && c2.getAuthority().equalsIgnoreCase(url.getAuthority())) {
            a(c2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void a(URL url, String str) {
        URL c2 = c(url, str);
        if (c2 != null) {
            a(url, c2);
        }
    }

    /* access modifiers changed from: protected */
    public boolean b(URL url, String str) {
        URL c2 = c(str);
        if (c2 == null) {
            return false;
        }
        a(url, c2);
        return true;
    }

    private URL c(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    private URL c(URL url, String str) {
        try {
            return new URL(url, str);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean a(HttpRequest httpRequest) {
        return d(httpRequest.getRequestLine().getMethod());
    }

    private boolean d(String str) {
        return !"GET".equals(str) && !"HEAD".equals(str);
    }

    public void flushInvalidatedCacheEntries(HttpHost httpHost, HttpRequest httpRequest, HttpResponse httpResponse) {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode >= 200 && statusCode <= 299) {
            URL c2 = c(this.c.a(httpHost, httpRequest));
            if (c2 != null) {
                URL a2 = a(c2, httpResponse);
                if (a2 != null) {
                    a(c2, httpResponse, a2);
                }
                URL b2 = b(c2, httpResponse);
                if (b2 != null) {
                    a(c2, httpResponse, b2);
                }
            }
        }
    }

    private void a(URL url, HttpResponse httpResponse, URL url2) {
        HttpCacheEntry b2 = b(this.c.a(url2.toString()));
        if (b2 != null && !b(httpResponse, b2) && a(httpResponse, b2)) {
            a(url, url2);
        }
    }

    private URL a(URL url, HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Content-Location");
        if (firstHeader == null) {
            return null;
        }
        String value = firstHeader.getValue();
        URL c2 = c(value);
        if (c2 != null) {
            return c2;
        }
        return c(url, value);
    }

    private URL b(URL url, HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Location");
        if (firstHeader == null) {
            return null;
        }
        String value = firstHeader.getValue();
        URL c2 = c(value);
        if (c2 != null) {
            return c2;
        }
        return c(url, value);
    }

    private boolean a(HttpResponse httpResponse, HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("ETag");
        Header firstHeader2 = httpResponse.getFirstHeader("ETag");
        if (firstHeader == null || firstHeader2 == null) {
            return false;
        }
        return !firstHeader.getValue().equals(firstHeader2.getValue());
    }

    private boolean b(HttpResponse httpResponse, HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Date");
        Header firstHeader2 = httpResponse.getFirstHeader("Date");
        if (firstHeader == null || firstHeader2 == null) {
            return false;
        }
        Date parseDate = DateUtils.parseDate(firstHeader.getValue());
        Date parseDate2 = DateUtils.parseDate(firstHeader2.getValue());
        if (parseDate == null || parseDate2 == null) {
            return false;
        }
        return parseDate2.before(parseDate);
    }
}
