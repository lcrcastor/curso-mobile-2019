package cz.msebera.android.httpclient.io;

import cz.msebera.android.httpclient.HttpMessage;

public interface HttpMessageWriter<T extends HttpMessage> {
    void write(T t);
}
