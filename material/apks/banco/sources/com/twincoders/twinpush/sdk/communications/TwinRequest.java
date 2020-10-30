package com.twincoders.twinpush.sdk.communications;

import com.twincoders.twinpush.sdk.communications.asyhttp.AsyncHttpClient;
import java.util.List;

public interface TwinRequest {

    public interface DefaultListener extends ErrorListener {
        void onSuccess();
    }

    public interface ErrorListener {
        void onError(Exception exc);
    }

    public enum HttpMethod {
        GET,
        POST,
        DELETE
    }

    public interface OnRequestFinishListener {
        void onRequestFinish();
    }

    void addOnRequestFinishListener(OnRequestFinishListener onRequestFinishListener);

    void addParam(TwinRequestParam twinRequestParam);

    void addParam(String str, Object obj);

    void cancel();

    String getBodyContent();

    String getContentType();

    String getEncoding();

    HttpMethod getHttpMethod();

    ErrorListener getListener();

    String getName();

    List<TwinRequestParam> getParams();

    TwinRequestLauncher getRequestLauncher();

    String getURL();

    Boolean isCanceled();

    boolean isDummy();

    boolean isHttpResponseStatusValid(int i);

    void launch();

    void onRequestError(Exception exc);

    void onResponseProcess(String str);

    void onSetupClient(AsyncHttpClient asyncHttpClient);

    void setRequestLauncher(TwinRequestLauncher twinRequestLauncher);
}
