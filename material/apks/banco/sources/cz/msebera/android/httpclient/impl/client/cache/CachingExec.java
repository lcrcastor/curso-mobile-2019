package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.cache.CacheResponseStatus;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.cache.HttpCacheContext;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.execchain.ClientExecChain;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.VersionInfo;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class CachingExec implements ClientExecChain {
    private final AtomicLong a;
    private final AtomicLong b;
    private final AtomicLong c;
    private final Map<ProtocolVersion, String> d;
    private final CacheConfig e;
    private final ClientExecChain f;
    private final HttpCache g;
    private final CacheValidityPolicy h;
    private final CachedHttpResponseGenerator i;
    private final CacheableRequestPolicy j;
    private final CachedResponseSuitabilityChecker k;
    private final ConditionalRequestBuilder l;
    public HttpClientAndroidLog log;
    private final ResponseProtocolCompliance m;
    private final RequestProtocolCompliance n;
    private final ResponseCachingPolicy o;
    private final AsynchronousValidator p;

    private boolean a(int i2) {
        return i2 == 500 || i2 == 502 || i2 == 503 || i2 == 504;
    }

    public boolean supportsRangeAndContentRangeHeaders() {
        return false;
    }

    public CachingExec(ClientExecChain clientExecChain, HttpCache httpCache, CacheConfig cacheConfig) {
        this(clientExecChain, httpCache, cacheConfig, (AsynchronousValidator) null);
    }

    public CachingExec(ClientExecChain clientExecChain, HttpCache httpCache, CacheConfig cacheConfig, AsynchronousValidator asynchronousValidator) {
        this.a = new AtomicLong();
        this.b = new AtomicLong();
        this.c = new AtomicLong();
        this.d = new HashMap(4);
        this.log = new HttpClientAndroidLog(getClass());
        Args.notNull(clientExecChain, "HTTP backend");
        Args.notNull(httpCache, "HttpCache");
        if (cacheConfig == null) {
            cacheConfig = CacheConfig.DEFAULT;
        }
        this.e = cacheConfig;
        this.f = clientExecChain;
        this.g = httpCache;
        this.h = new CacheValidityPolicy();
        this.i = new CachedHttpResponseGenerator(this.h);
        this.j = new CacheableRequestPolicy();
        this.k = new CachedResponseSuitabilityChecker(this.h, this.e);
        this.l = new ConditionalRequestBuilder();
        this.m = new ResponseProtocolCompliance();
        this.n = new RequestProtocolCompliance(this.e.isWeakETagOnPutDeleteAllowed());
        ResponseCachingPolicy responseCachingPolicy = new ResponseCachingPolicy(this.e.getMaxObjectSize(), this.e.isSharedCache(), this.e.isNeverCacheHTTP10ResponsesWithQuery(), this.e.is303CachingEnabled());
        this.o = responseCachingPolicy;
        this.p = asynchronousValidator;
    }

    public CachingExec(ClientExecChain clientExecChain, ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig) {
        this(clientExecChain, new BasicHttpCache(resourceFactory, httpCacheStorage, cacheConfig), cacheConfig);
    }

    public CachingExec(ClientExecChain clientExecChain) {
        this(clientExecChain, new BasicHttpCache(), CacheConfig.DEFAULT);
    }

    public long getCacheHits() {
        return this.a.get();
    }

    public long getCacheMisses() {
        return this.b.get();
    }

    public long getCacheUpdates() {
        return this.c.get();
    }

    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper) {
        return execute(httpRoute, httpRequestWrapper, HttpClientContext.create(), null);
    }

    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext) {
        return execute(httpRoute, httpRequestWrapper, httpClientContext, null);
    }

    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) {
        HttpHost targetHost = httpClientContext.getTargetHost();
        String a2 = a((HttpMessage) httpRequestWrapper.getOriginal());
        a((HttpContext) httpClientContext, CacheResponseStatus.CACHE_MISS);
        if (a((HttpRequest) httpRequestWrapper)) {
            a((HttpContext) httpClientContext, CacheResponseStatus.CACHE_MODULE_RESPONSE);
            return Proxies.a(new OptionsHttp11Response());
        }
        HttpResponse a3 = a(httpRequestWrapper, (HttpContext) httpClientContext);
        if (a3 != null) {
            return Proxies.a(a3);
        }
        this.n.a(httpRequestWrapper);
        httpRequestWrapper.addHeader("Via", a2);
        e(httpClientContext.getTargetHost(), httpRequestWrapper);
        if (!this.j.a(httpRequestWrapper)) {
            this.log.debug("Request is not servable from cache");
            return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        }
        HttpCacheEntry a4 = a(targetHost, httpRequestWrapper);
        if (a4 != null) {
            return b(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, a4);
        }
        this.log.debug("Cache miss");
        return b(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
    }

    private CloseableHttpResponse b(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry) {
        CloseableHttpResponse closeableHttpResponse;
        HttpHost targetHost = httpClientContext.getTargetHost();
        d(targetHost, httpRequestWrapper);
        Date a2 = a();
        if (this.k.a(targetHost, httpRequestWrapper, httpCacheEntry, a2)) {
            this.log.debug("Cache hit");
            closeableHttpResponse = a(httpRequestWrapper, (HttpContext) httpClientContext, httpCacheEntry, a2);
        } else if (!a(httpRequestWrapper)) {
            this.log.debug("Cache entry not suitable but only-if-cached requested");
            closeableHttpResponse = b(httpClientContext);
        } else if (httpCacheEntry.getStatusCode() != 304 || this.k.a((HttpRequest) httpRequestWrapper)) {
            this.log.debug("Revalidating cache entry");
            return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, httpCacheEntry, a2);
        } else {
            this.log.debug("Cache entry not usable; calling backend");
            return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        }
        httpClientContext.setAttribute("http.route", httpRoute);
        httpClientContext.setAttribute("http.target_host", targetHost);
        httpClientContext.setAttribute("http.request", httpRequestWrapper);
        httpClientContext.setAttribute("http.response", closeableHttpResponse);
        httpClientContext.setAttribute("http.request_sent", Boolean.TRUE);
        return closeableHttpResponse;
    }

    private CloseableHttpResponse a(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry, Date date) {
        try {
            if (this.p == null || a(httpRequestWrapper, httpCacheEntry, date) || !this.h.c(httpCacheEntry, date)) {
                return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, httpCacheEntry);
            }
            this.log.trace("Serving stale with asynchronous revalidation");
            CloseableHttpResponse a2 = a(httpRequestWrapper, (HttpContext) httpClientContext, httpCacheEntry, date);
            this.p.a(this, httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, httpCacheEntry);
            return a2;
        } catch (IOException unused) {
            return b(httpRequestWrapper, (HttpContext) httpClientContext, httpCacheEntry, date);
        }
    }

    private CloseableHttpResponse b(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) {
        HttpHost targetHost = httpClientContext.getTargetHost();
        c(targetHost, httpRequestWrapper);
        if (!a(httpRequestWrapper)) {
            return Proxies.a(new BasicHttpResponse((ProtocolVersion) HttpVersion.HTTP_1_1, (int) HttpStatus.SC_GATEWAY_TIMEOUT, "Gateway Timeout"));
        }
        Map b2 = b(targetHost, httpRequestWrapper);
        if (b2 == null || b2.isEmpty()) {
            return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        }
        return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, b2);
    }

    private HttpCacheEntry a(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper) {
        try {
            return this.g.b(httpHost, httpRequestWrapper);
        } catch (IOException e2) {
            this.log.warn("Unable to retrieve entries from cache", e2);
            return null;
        }
    }

    private HttpResponse a(HttpRequestWrapper httpRequestWrapper, HttpContext httpContext) {
        HttpResponse httpResponse = null;
        for (RequestProtocolError requestProtocolError : this.n.a((HttpRequest) httpRequestWrapper)) {
            a(httpContext, CacheResponseStatus.CACHE_MODULE_RESPONSE);
            httpResponse = this.n.a(requestProtocolError);
        }
        return httpResponse;
    }

    private Map<String, Variant> b(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper) {
        try {
            return this.g.d(httpHost, httpRequestWrapper);
        } catch (IOException e2) {
            this.log.warn("Unable to retrieve variant entries from cache", e2);
            return null;
        }
    }

    private void c(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper) {
        this.b.getAndIncrement();
        if (this.log.isTraceEnabled()) {
            RequestLine requestLine = httpRequestWrapper.getRequestLine();
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Cache miss [host: ");
            sb.append(httpHost);
            sb.append("; uri: ");
            sb.append(requestLine.getUri());
            sb.append("]");
            httpClientAndroidLog.trace(sb.toString());
        }
    }

    private void d(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper) {
        this.a.getAndIncrement();
        if (this.log.isTraceEnabled()) {
            RequestLine requestLine = httpRequestWrapper.getRequestLine();
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb = new StringBuilder();
            sb.append("Cache hit [host: ");
            sb.append(httpHost);
            sb.append("; uri: ");
            sb.append(requestLine.getUri());
            sb.append("]");
            httpClientAndroidLog.trace(sb.toString());
        }
    }

    private void a(HttpContext httpContext) {
        this.c.getAndIncrement();
        a(httpContext, CacheResponseStatus.VALIDATED);
    }

    private void e(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper) {
        try {
            this.g.c(httpHost, httpRequestWrapper);
        } catch (IOException e2) {
            this.log.warn("Unable to flush invalidated entries from cache", e2);
        }
    }

    private CloseableHttpResponse a(HttpRequestWrapper httpRequestWrapper, HttpContext httpContext, HttpCacheEntry httpCacheEntry, Date date) {
        CloseableHttpResponse closeableHttpResponse;
        if (httpRequestWrapper.containsHeader("If-None-Match") || httpRequestWrapper.containsHeader("If-Modified-Since")) {
            closeableHttpResponse = this.i.a(httpCacheEntry);
        } else {
            closeableHttpResponse = this.i.a(httpRequestWrapper, httpCacheEntry);
        }
        a(httpContext, CacheResponseStatus.CACHE_HIT);
        if (this.h.e(httpCacheEntry, date) > 0) {
            closeableHttpResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
        }
        return closeableHttpResponse;
    }

    private CloseableHttpResponse b(HttpRequestWrapper httpRequestWrapper, HttpContext httpContext, HttpCacheEntry httpCacheEntry, Date date) {
        if (a(httpRequestWrapper, httpCacheEntry, date)) {
            return b(httpContext);
        }
        return a(httpRequestWrapper, httpContext, httpCacheEntry);
    }

    private CloseableHttpResponse b(HttpContext httpContext) {
        a(httpContext, CacheResponseStatus.CACHE_MODULE_RESPONSE);
        return Proxies.a(new BasicHttpResponse((ProtocolVersion) HttpVersion.HTTP_1_1, (int) HttpStatus.SC_GATEWAY_TIMEOUT, "Gateway Timeout"));
    }

    private CloseableHttpResponse a(HttpRequestWrapper httpRequestWrapper, HttpContext httpContext, HttpCacheEntry httpCacheEntry) {
        CloseableHttpResponse a2 = this.i.a(httpRequestWrapper, httpCacheEntry);
        a(httpContext, CacheResponseStatus.CACHE_HIT);
        a2.addHeader("Warning", "111 localhost \"Revalidation failed\"");
        return a2;
    }

    private boolean a(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry, Date date) {
        return this.h.b(httpCacheEntry) || (this.e.isSharedCache() && this.h.c(httpCacheEntry)) || b(httpRequestWrapper, httpCacheEntry, date);
    }

    private boolean a(HttpRequestWrapper httpRequestWrapper) {
        for (Header elements : httpRequestWrapper.getHeaders("Cache-Control")) {
            for (HeaderElement name : elements.getElements()) {
                if ("only-if-cached".equals(name.getName())) {
                    this.log.trace("Request marked only-if-cached");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean b(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry, Date date) {
        HeaderElement[] elements;
        HttpCacheEntry httpCacheEntry2 = httpCacheEntry;
        for (Header elements2 : httpRequestWrapper.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : elements2.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(headerElement.getName())) {
                    try {
                        if (this.h.a(httpCacheEntry2, date) - this.h.a(httpCacheEntry2) > ((long) Integer.parseInt(headerElement.getValue()))) {
                            return true;
                        }
                    } catch (NumberFormatException unused) {
                        return true;
                    }
                } else {
                    Date date2 = date;
                    if (HeaderConstants.CACHE_CONTROL_MIN_FRESH.equals(headerElement.getName()) || "max-age".equals(headerElement.getName())) {
                        return true;
                    }
                }
            }
            Date date3 = date;
        }
        return false;
    }

    private String a(HttpMessage httpMessage) {
        String str;
        ProtocolVersion protocolVersion = httpMessage.getProtocolVersion();
        String str2 = (String) this.d.get(protocolVersion);
        if (str2 != null) {
            return str2;
        }
        VersionInfo loadVersionInfo = VersionInfo.loadVersionInfo("cz.msebera.android.httpclient.client", getClass().getClassLoader());
        String release = loadVersionInfo != null ? loadVersionInfo.getRelease() : VersionInfo.UNAVAILABLE;
        int major = protocolVersion.getMajor();
        int minor = protocolVersion.getMinor();
        if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(protocolVersion.getProtocol())) {
            str = String.format("%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[]{Integer.valueOf(major), Integer.valueOf(minor), release});
        } else {
            str = String.format("%s/%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[]{protocolVersion.getProtocol(), Integer.valueOf(major), Integer.valueOf(minor), release});
        }
        this.d.put(protocolVersion, str);
        return str;
    }

    private void a(HttpContext httpContext, CacheResponseStatus cacheResponseStatus) {
        if (httpContext != null) {
            httpContext.setAttribute(HttpCacheContext.CACHE_RESPONSE_STATUS, cacheResponseStatus);
        }
    }

    /* access modifiers changed from: 0000 */
    public Date a() {
        return new Date();
    }

    /* access modifiers changed from: 0000 */
    public boolean a(HttpRequest httpRequest) {
        RequestLine requestLine = httpRequest.getRequestLine();
        if ("OPTIONS".equals(requestLine.getMethod()) && "*".equals(requestLine.getUri()) && "0".equals(httpRequest.getFirstHeader("Max-Forwards").getValue())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) {
        Date a2 = a();
        this.log.trace("Calling the backend");
        CloseableHttpResponse execute = this.f.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        try {
            execute.addHeader("Via", a((HttpMessage) execute));
            return a(httpRequestWrapper, httpClientContext, a2, a(), execute);
        } catch (IOException e2) {
            execute.close();
            throw e2;
        } catch (RuntimeException e3) {
            execute.close();
            throw e3;
        }
    }

    private boolean a(HttpResponse httpResponse, HttpCacheEntry httpCacheEntry) {
        Header firstHeader = httpCacheEntry.getFirstHeader("Date");
        Header firstHeader2 = httpResponse.getFirstHeader("Date");
        if (!(firstHeader == null || firstHeader2 == null)) {
            Date parseDate = DateUtils.parseDate(firstHeader.getValue());
            Date parseDate2 = DateUtils.parseDate(firstHeader2.getValue());
            if (!(parseDate == null || parseDate2 == null || !parseDate2.before(parseDate))) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, Map<String, Variant> map) {
        HttpRequestWrapper a2 = this.l.a(httpRequestWrapper, map);
        Date a3 = a();
        CloseableHttpResponse execute = this.f.execute(httpRoute, a2, httpClientContext, httpExecutionAware);
        try {
            Date a4 = a();
            execute.addHeader("Via", a((HttpMessage) execute));
            if (execute.getStatusLine().getStatusCode() != 304) {
                return a(httpRequestWrapper, httpClientContext, a3, a4, execute);
            }
            Header firstHeader = execute.getFirstHeader("ETag");
            if (firstHeader == null) {
                this.log.warn("304 response did not contain ETag");
                IOUtils.a(execute.getEntity());
                execute.close();
                return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
            }
            Variant variant = (Variant) map.get(firstHeader.getValue());
            if (variant == null) {
                this.log.debug("304 response did not contain ETag matching one sent in If-None-Match");
                IOUtils.a(execute.getEntity());
                execute.close();
                return a(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
            }
            HttpCacheEntry b2 = variant.b();
            if (a((HttpResponse) execute, b2)) {
                IOUtils.a(execute.getEntity());
                execute.close();
                return c(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware, b2);
            }
            a((HttpContext) httpClientContext);
            HttpCacheEntry a5 = a(httpClientContext.getTargetHost(), a2, a3, a4, execute, variant, b2);
            execute.close();
            CloseableHttpResponse a6 = this.i.a(httpRequestWrapper, a5);
            a(httpClientContext.getTargetHost(), httpRequestWrapper, variant);
            return a(httpRequestWrapper, a5) ? this.i.a(a5) : a6;
        } catch (IOException e2) {
            execute.close();
            throw e2;
        } catch (RuntimeException e3) {
            execute.close();
            throw e3;
        }
    }

    private CloseableHttpResponse c(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry) {
        return a(httpRoute, this.l.b(httpRequestWrapper, httpCacheEntry), httpClientContext, httpExecutionAware);
    }

    /* JADX INFO: finally extract failed */
    private HttpCacheEntry a(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper, Date date, Date date2, CloseableHttpResponse closeableHttpResponse, Variant variant, HttpCacheEntry httpCacheEntry) {
        try {
            HttpCacheEntry a2 = this.g.a(httpHost, httpRequestWrapper, httpCacheEntry, closeableHttpResponse, date, date2, variant.a());
            closeableHttpResponse.close();
            return a2;
        } catch (IOException e2) {
            this.log.warn("Could not update cache entry", e2);
            closeableHttpResponse.close();
            return httpCacheEntry;
        } catch (Throwable th) {
            closeableHttpResponse.close();
            throw th;
        }
    }

    private void a(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper, Variant variant) {
        try {
            this.g.a(httpHost, (HttpRequest) httpRequestWrapper, variant);
        } catch (IOException e2) {
            this.log.warn("Could not update cache entry to reuse variant", e2);
        }
    }

    private boolean a(HttpRequestWrapper httpRequestWrapper, HttpCacheEntry httpCacheEntry) {
        return this.k.a((HttpRequest) httpRequestWrapper) && this.k.a((HttpRequest) httpRequestWrapper, httpCacheEntry, new Date());
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware, HttpCacheEntry httpCacheEntry) {
        Date date;
        Date date2;
        CloseableHttpResponse closeableHttpResponse;
        HttpRequestWrapper a2 = this.l.a(httpRequestWrapper, httpCacheEntry);
        URI uri = a2.getURI();
        if (uri != null) {
            try {
                a2.setURI(URIUtils.rewriteURIForRoute(uri, httpRoute));
            } catch (URISyntaxException e2) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid URI: ");
                sb.append(uri);
                throw new ProtocolException(sb.toString(), e2);
            }
        }
        Date a3 = a();
        CloseableHttpResponse execute = this.f.execute(httpRoute, a2, httpClientContext, httpExecutionAware);
        Date a4 = a();
        if (a((HttpResponse) execute, httpCacheEntry)) {
            execute.close();
            HttpRequestWrapper b2 = this.l.b(httpRequestWrapper, httpCacheEntry);
            Date a5 = a();
            closeableHttpResponse = this.f.execute(httpRoute, b2, httpClientContext, httpExecutionAware);
            date = a();
            date2 = a5;
        } else {
            date2 = a3;
            closeableHttpResponse = execute;
            date = a4;
        }
        closeableHttpResponse.addHeader("Via", a((HttpMessage) closeableHttpResponse));
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        if (statusCode == 304 || statusCode == 200) {
            a((HttpContext) httpClientContext);
        }
        if (statusCode == 304) {
            HttpCacheEntry a6 = this.g.a(httpClientContext.getTargetHost(), httpRequestWrapper, httpCacheEntry, closeableHttpResponse, date2, date);
            if (!this.k.a((HttpRequest) httpRequestWrapper) || !this.k.a((HttpRequest) httpRequestWrapper, a6, new Date())) {
                return this.i.a(httpRequestWrapper, a6);
            }
            return this.i.a(a6);
        } else if (!a(statusCode) || a(httpRequestWrapper, httpCacheEntry, a()) || !this.h.a((HttpRequest) httpRequestWrapper, httpCacheEntry, date)) {
            return a(a2, httpClientContext, date2, date, closeableHttpResponse);
        } else {
            try {
                CloseableHttpResponse a7 = this.i.a(httpRequestWrapper, httpCacheEntry);
                a7.addHeader("Warning", "110 localhost \"Response is stale\"");
                return a7;
            } finally {
                closeableHttpResponse.close();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse a(HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, Date date, Date date2, CloseableHttpResponse closeableHttpResponse) {
        this.log.trace("Handling Backend response");
        this.m.a(httpRequestWrapper, (HttpResponse) closeableHttpResponse);
        HttpHost targetHost = httpClientContext.getTargetHost();
        boolean a2 = this.o.a((HttpRequest) httpRequestWrapper, (HttpResponse) closeableHttpResponse);
        this.g.a(targetHost, (HttpRequest) httpRequestWrapper, (HttpResponse) closeableHttpResponse);
        if (!a2 || a(targetHost, httpRequestWrapper, (HttpResponse) closeableHttpResponse)) {
            if (!a2) {
                try {
                    this.g.a(targetHost, httpRequestWrapper);
                } catch (IOException e2) {
                    this.log.warn("Unable to flush invalid cache entries", e2);
                }
            }
            return closeableHttpResponse;
        }
        a((HttpRequest) httpRequestWrapper, (HttpResponse) closeableHttpResponse);
        return this.g.a(targetHost, httpRequestWrapper, closeableHttpResponse, date, date2);
    }

    private void a(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpResponse.getStatusLine().getStatusCode() == 304) {
            Header firstHeader = httpRequest.getFirstHeader("If-Modified-Since");
            if (firstHeader != null) {
                httpResponse.addHeader("Last-Modified", firstHeader.getValue());
            }
        }
    }

    private boolean a(HttpHost httpHost, HttpRequestWrapper httpRequestWrapper, HttpResponse httpResponse) {
        HttpCacheEntry httpCacheEntry;
        try {
            httpCacheEntry = this.g.b(httpHost, httpRequestWrapper);
        } catch (IOException unused) {
            httpCacheEntry = null;
        }
        if (httpCacheEntry == null) {
            return false;
        }
        Header firstHeader = httpCacheEntry.getFirstHeader("Date");
        if (firstHeader == null) {
            return false;
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Date");
        if (firstHeader2 == null) {
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
