package cz.msebera.android.httpclient.client.cache;

public interface HttpCacheUpdateCallback {
    HttpCacheEntry update(HttpCacheEntry httpCacheEntry);
}
