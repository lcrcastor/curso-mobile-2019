package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntrySerializationException;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntrySerializer;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

@Immutable
public class DefaultHttpCacheEntrySerializer implements HttpCacheEntrySerializer {
    public void writeTo(HttpCacheEntry httpCacheEntry, OutputStream outputStream) {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        try {
            objectOutputStream.writeObject(httpCacheEntry);
        } finally {
            objectOutputStream.close();
        }
    }

    public HttpCacheEntry readFrom(InputStream inputStream) {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            HttpCacheEntry httpCacheEntry = (HttpCacheEntry) objectInputStream.readObject();
            objectInputStream.close();
            return httpCacheEntry;
        } catch (ClassNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Class not found: ");
            sb.append(e.getMessage());
            throw new HttpCacheEntrySerializationException(sb.toString(), e);
        } catch (Throwable th) {
            objectInputStream.close();
            throw th;
        }
    }
}
