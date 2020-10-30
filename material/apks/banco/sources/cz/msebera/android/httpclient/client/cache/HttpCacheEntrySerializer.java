package cz.msebera.android.httpclient.client.cache;

import java.io.InputStream;
import java.io.OutputStream;

public interface HttpCacheEntrySerializer {
    HttpCacheEntry readFrom(InputStream inputStream);

    void writeTo(HttpCacheEntry httpCacheEntry, OutputStream outputStream);
}
