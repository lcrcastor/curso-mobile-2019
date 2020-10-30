package com.indra.httpclient.error;

public interface IErrorWS {
    void onErrorResponse();

    void onErrorServer();

    void onWarningResponse();
}
