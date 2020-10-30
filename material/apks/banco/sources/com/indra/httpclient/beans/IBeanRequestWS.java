package com.indra.httpclient.beans;

public interface IBeanRequestWS<T> {
    Class<T> getBeanResponseClass();

    IBeanWS getBeanToRequest();

    int getMethod();

    String getUrlWS();
}
