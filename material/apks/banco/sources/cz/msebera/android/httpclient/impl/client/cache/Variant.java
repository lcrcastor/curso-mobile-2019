package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;

class Variant {
    private final String a;
    private final String b;
    private final HttpCacheEntry c;

    public Variant(String str, String str2, HttpCacheEntry httpCacheEntry) {
        this.a = str;
        this.b = str2;
        this.c = httpCacheEntry;
    }

    public String a() {
        return this.b;
    }

    public HttpCacheEntry b() {
        return this.c;
    }
}
