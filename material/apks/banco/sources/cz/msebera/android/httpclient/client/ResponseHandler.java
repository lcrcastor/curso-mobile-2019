package cz.msebera.android.httpclient.client;

import cz.msebera.android.httpclient.HttpResponse;

public interface ResponseHandler<T> {
    T handleResponse(HttpResponse httpResponse);
}
