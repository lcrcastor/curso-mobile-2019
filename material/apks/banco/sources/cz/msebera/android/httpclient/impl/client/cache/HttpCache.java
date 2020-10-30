package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import java.util.Date;
import java.util.Map;

interface HttpCache {
    HttpCacheEntry a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2);

    HttpCacheEntry a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry, HttpResponse httpResponse, Date date, Date date2, String str);

    CloseableHttpResponse a(HttpHost httpHost, HttpRequest httpRequest, CloseableHttpResponse closeableHttpResponse, Date date, Date date2);

    void a(HttpHost httpHost, HttpRequest httpRequest);

    void a(HttpHost httpHost, HttpRequest httpRequest, HttpResponse httpResponse);

    void a(HttpHost httpHost, HttpRequest httpRequest, Variant variant);

    HttpCacheEntry b(HttpHost httpHost, HttpRequest httpRequest);

    void c(HttpHost httpHost, HttpRequest httpRequest);

    Map<String, Variant> d(HttpHost httpHost, HttpRequest httpRequest);
}
