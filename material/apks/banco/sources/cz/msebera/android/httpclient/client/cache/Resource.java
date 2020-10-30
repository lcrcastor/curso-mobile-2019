package cz.msebera.android.httpclient.client.cache;

import java.io.InputStream;
import java.io.Serializable;

public interface Resource extends Serializable {
    void dispose();

    InputStream getInputStream();

    long length();
}
