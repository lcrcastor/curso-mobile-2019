package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheInvalidator;
import cz.msebera.android.httpclient.client.cache.HttpCacheStorage;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateCallback;
import cz.msebera.android.httpclient.client.cache.HttpCacheUpdateException;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class BasicHttpCache implements HttpCache {
    private static final Set<String> b = new HashSet(Arrays.asList(new String[]{"HEAD", "GET", "OPTIONS", "TRACE"}));
    public HttpClientAndroidLog a;
    /* access modifiers changed from: private */
    public final CacheKeyGenerator c;
    private final ResourceFactory d;
    private final long e;
    private final CacheEntryUpdater f;
    private final CachedHttpResponseGenerator g;
    private final HttpCacheInvalidator h;
    private final HttpCacheStorage i;

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig, CacheKeyGenerator cacheKeyGenerator, HttpCacheInvalidator httpCacheInvalidator) {
        this.a = new HttpClientAndroidLog(getClass());
        this.d = resourceFactory;
        this.c = cacheKeyGenerator;
        this.f = new CacheEntryUpdater(resourceFactory);
        this.e = cacheConfig.getMaxObjectSize();
        this.g = new CachedHttpResponseGenerator();
        this.i = httpCacheStorage;
        this.h = httpCacheInvalidator;
    }

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig, CacheKeyGenerator cacheKeyGenerator) {
        this(resourceFactory, httpCacheStorage, cacheConfig, cacheKeyGenerator, new CacheInvalidator(cacheKeyGenerator, httpCacheStorage));
    }

    public BasicHttpCache(ResourceFactory resourceFactory, HttpCacheStorage httpCacheStorage, CacheConfig cacheConfig) {
        this(resourceFactory, httpCacheStorage, cacheConfig, new CacheKeyGenerator());
    }

    public BasicHttpCache(CacheConfig cacheConfig) {
        this(new HeapResourceFactory(), new BasicHttpCacheStorage(cacheConfig), cacheConfig);
    }

    public BasicHttpCache() {
        this(CacheConfig.DEFAULT);
    }

    public void a(HttpHost httpHost, HttpRequest httpRequest) {
        if (!b.contains(httpRequest.getRequestLine().getMethod())) {
            this.i.removeEntry(this.c.a(httpHost, httpRequest));
        }
    }

    public void a(HttpHost httpHost, HttpRequest httpRequest, HttpResponse httpResponse) {
        if (!b.contains(httpRequest.getRequestLine().getMethod())) {
            this.h.flushInvalidatedCacheEntries(httpHost, httpRequest, httpResponse);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        if (httpCacheEntry.hasVariants()) {
            c(httpHost, httpRequest, httpCacheEntry);
        } else {
            b(httpHost, httpRequest, httpCacheEntry);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        this.i.putEntry(this.c.a(httpHost, httpRequest), httpCacheEntry);
    }

    /* access modifiers changed from: 0000 */
    public void c(HttpHost httpHost, final HttpRequest httpRequest, final HttpCacheEntry httpCacheEntry) {
        String a2 = this.c.a(httpHost, httpRequest);
        final String a3 = this.c.a(httpHost, httpRequest, httpCacheEntry);
        this.i.putEntry(a3, httpCacheEntry);
        try {
            this.i.updateEntry(a2, new HttpCacheUpdateCallback() {
                public HttpCacheEntry update(HttpCacheEntry httpCacheEntry) {
                    return BasicHttpCache.this.a(httpRequest.getRequestLine().getUri(), httpCacheEntry, httpCacheEntry, BasicHttpCache.this.c.a(httpRequest, httpCacheEntry), a3);
                }
            });
        } catch (HttpCacheUpdateException e2) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Could not update key [");
            sb.append(a2);
            sb.append("]");
            httpClientAndroidLog.warn(sb.toString(), e2);
        }
    }

    public void a(HttpHost httpHost, HttpRequest httpRequest, Variant variant) {
        String a2 = this.c.a(httpHost, httpRequest);
        final HttpCacheEntry b2 = variant.b();
        final String a3 = this.c.a(httpRequest, b2);
        final String a4 = variant.a();
        final HttpRequest httpRequest2 = httpRequest;
        AnonymousClass2 r0 = new HttpCacheUpdateCallback() {
            public HttpCacheEntry update(HttpCacheEntry httpCacheEntry) {
                return BasicHttpCache.this.a(httpRequest2.getRequestLine().getUri(), httpCacheEntry, b2, a3, a4);
            }
        };
        try {
            this.i.updateEntry(a2, r0);
        } catch (HttpCacheUpdateException e2) {
            HttpClientAndroidLog httpClientAndroidLog = this.a;
            StringBuilder sb = new StringBuilder();
            sb.append("Could not update key [");
            sb.append(a2);
            sb.append("]");
            httpClientAndroidLog.warn(sb.toString(), e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(HttpResponse httpResponse, Resource resource) {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        boolean z = false;
        if (statusCode != 200 && statusCode != 206) {
            return false;
        }
        Header firstHeader = httpResponse.getFirstHeader("Content-Length");
        if (firstHeader == null) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(firstHeader.getValue());
            if (resource == null) {
                return false;
            }
            if (resource.length() < ((long) parseInt)) {
                z = true;
            }
            return z;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public CloseableHttpResponse b(HttpResponse httpResponse, Resource resource) {
        Integer valueOf = Integer.valueOf(httpResponse.getFirstHeader("Content-Length").getValue());
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse((ProtocolVersion) HttpVersion.HTTP_1_1, (int) HttpStatus.SC_BAD_GATEWAY, "Bad Gateway");
        basicHttpResponse.setHeader("Content-Type", "text/plain;charset=UTF-8");
        byte[] bytes = String.format("Received incomplete response with Content-Length %d but actual body length %d", new Object[]{valueOf, Long.valueOf(resource.length())}).getBytes();
        basicHttpResponse.setHeader("Content-Length", Integer.toString(bytes.length));
        basicHttpResponse.setEntity(new ByteArrayEntity(bytes));
        return Proxies.a(basicHttpResponse);
    }

    /* access modifiers changed from: 0000 */
    public HttpCacheEntry a(String str, HttpCacheEntry httpCacheEntry, HttpCacheEntry httpCacheEntry2, String str2, String str3) {
        if (httpCacheEntry == null) {
            httpCacheEntry = httpCacheEntry2;
        }
        Resource resource = null;
        if (httpCacheEntry.getResource() != null) {
            resource = this.d.copy(str, httpCacheEntry.getResource());
        }
        Resource resource2 = resource;
        HashMap hashMap = new HashMap(httpCacheEntry.getVariantMap());
        hashMap.put(str2, str3);
        HttpCacheEntry httpCacheEntry3 = new HttpCacheEntry(httpCacheEntry.getRequestDate(), httpCacheEntry.getResponseDate(), httpCacheEntry.getStatusLine(), httpCacheEntry.getAllHeaders(), resource2, hashMap, httpCacheEntry.getRequestMethod());
        return httpCacheEntry3;
    }

    public HttpCacheEntry a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2) {
        HttpCacheEntry a2 = this.f.a(httpRequest.getRequestLine().getUri(), httpCacheEntry, date, date2, httpResponse);
        a(httpHost, httpRequest, a2);
        return a2;
    }

    public HttpCacheEntry a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2, String str) {
        HttpCacheEntry a2 = this.f.a(httpRequest.getRequestLine().getUri(), httpCacheEntry, date, date2, httpResponse);
        this.i.putEntry(str, a2);
        return a2;
    }

    public CloseableHttpResponse a(HttpHost httpHost, HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse, Date date, Date date2) {
        SizeLimitedResponseReader a2 = a(httpRequest, closeableHttpResponse);
        try {
            a2.a();
            if (a2.b()) {
                return a2.d();
            }
            Resource c2 = a2.c();
            if (a((HttpResponse) closeableHttpResponse, c2)) {
                CloseableHttpResponse b2 = b((HttpResponse) closeableHttpResponse, c2);
                closeableHttpResponse.close();
                return b2;
            }
            HttpCacheEntry httpCacheEntry = new HttpCacheEntry(date, date2, closeableHttpResponse.getStatusLine(), closeableHttpResponse.getAllHeaders(), c2, httpRequest.getRequestLine().getMethod());
            a(httpHost, httpRequest, httpCacheEntry);
            CloseableHttpResponse a3 = this.g.a(HttpRequestWrapper.wrap(httpRequest, httpHost), httpCacheEntry);
            closeableHttpResponse.close();
            return a3;
        } catch (Throwable th) {
            if (1 != 0) {
                closeableHttpResponse.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public SizeLimitedResponseReader a(HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse) {
        SizeLimitedResponseReader sizeLimitedResponseReader = new SizeLimitedResponseReader(this.d, this.e, httpRequest, closeableHttpResponse);
        return sizeLimitedResponseReader;
    }

    public HttpCacheEntry b(HttpHost httpHost, HttpRequest httpRequest) {
        HttpCacheEntry entry = this.i.getEntry(this.c.a(httpHost, httpRequest));
        if (entry == null) {
            return null;
        }
        if (!entry.hasVariants()) {
            return entry;
        }
        String str = (String) entry.getVariantMap().get(this.c.a(httpRequest, entry));
        if (str == null) {
            return null;
        }
        return this.i.getEntry(str);
    }

    public void c(HttpHost httpHost, HttpRequest httpRequest) {
        this.h.flushInvalidatedCacheEntries(httpHost, httpRequest);
    }

    public Map<String, Variant> d(HttpHost httpHost, HttpRequest httpRequest) {
        HashMap hashMap = new HashMap();
        HttpCacheEntry entry = this.i.getEntry(this.c.a(httpHost, httpRequest));
        if (entry == null || !entry.hasVariants()) {
            return hashMap;
        }
        for (Entry entry2 : entry.getVariantMap().entrySet()) {
            a((String) entry2.getKey(), (String) entry2.getValue(), (Map<String, Variant>) hashMap);
        }
        return hashMap;
    }

    private void a(String str, String str2, Map<String, Variant> map) {
        HttpCacheEntry entry = this.i.getEntry(str2);
        if (entry != null) {
            Header firstHeader = entry.getFirstHeader("ETag");
            if (firstHeader != null) {
                map.put(firstHeader.getValue(), new Variant(str, str2, entry));
            }
        }
    }
}
