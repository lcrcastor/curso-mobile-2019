package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import java.util.Date;

@Immutable
class CachedHttpResponseGenerator {
    private final CacheValidityPolicy a;

    CachedHttpResponseGenerator(CacheValidityPolicy cacheValidityPolicy) {
        this.a = cacheValidityPolicy;
    }

    CachedHttpResponseGenerator() {
        this(new CacheValidityPolicy());
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry) {
        Date date = new Date();
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion) HttpVersion.HTTP_1_1, httpCacheEntry.getStatusCode(), httpCacheEntry.getReasonPhrase());
        basicHttpResponse.setHeaders(httpCacheEntry.getAllHeaders());
        if (b(httpRequestWrapper, httpCacheEntry)) {
            CacheEntity cacheEntity = new CacheEntity(httpCacheEntry);
            a((HttpResponse) basicHttpResponse, (HttpEntity) cacheEntity);
            basicHttpResponse.setEntity(cacheEntity);
        }
        long a2 = this.a.a(httpCacheEntry, date);
        if (a2 > 0) {
            if (a2 >= 2147483647L) {
                basicHttpResponse.setHeader("Age", "2147483648");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append((int) a2);
                basicHttpResponse.setHeader("Age", sb.toString());
            }
        }
        return Proxies.a(basicHttpResponse);
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpCacheEntry httpCacheEntry) {
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion) HttpVersion.HTTP_1_1, (int) HttpStatus.SC_NOT_MODIFIED, "Not Modified");
        Header firstHeader = httpCacheEntry.getFirstHeader("Date");
        if (firstHeader == null) {
            firstHeader = new BasicHeader("Date", DateUtils.formatDate(new Date()));
        }
        basicHttpResponse.addHeader(firstHeader);
        Header firstHeader2 = httpCacheEntry.getFirstHeader("ETag");
        if (firstHeader2 != null) {
            basicHttpResponse.addHeader(firstHeader2);
        }
        Header firstHeader3 = httpCacheEntry.getFirstHeader("Content-Location");
        if (firstHeader3 != null) {
            basicHttpResponse.addHeader(firstHeader3);
        }
        Header firstHeader4 = httpCacheEntry.getFirstHeader("Expires");
        if (firstHeader4 != null) {
            basicHttpResponse.addHeader(firstHeader4);
        }
        Header firstHeader5 = httpCacheEntry.getFirstHeader("Cache-Control");
        if (firstHeader5 != null) {
            basicHttpResponse.addHeader(firstHeader5);
        }
        Header firstHeader6 = httpCacheEntry.getFirstHeader("Vary");
        if (firstHeader6 != null) {
            basicHttpResponse.addHeader(firstHeader6);
        }
        return Proxies.a(basicHttpResponse);
    }

    private void a(HttpResponse httpResponse, HttpEntity httpEntity) {
        if (!a(httpResponse) && httpResponse.getFirstHeader("Content-Length") == null) {
            httpResponse.setHeader(new BasicHeader("Content-Length", Long.toString(httpEntity.getContentLength())));
        }
    }

    private boolean a(HttpResponse httpResponse) {
        return httpResponse.getFirstHeader("Transfer-Encoding") != null;
    }

    private boolean b(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry) {
        return httpRequestWrapper.getRequestLine().getMethod().equals("GET") && httpCacheEntry.getResource() != null;
    }
}
