package com.twincoders.twinpush.sdk.communications;

import com.twincoders.twinpush.sdk.communications.TwinRequest.HttpMethod;
import com.twincoders.twinpush.sdk.communications.TwinRequest.OnRequestFinishListener;
import com.twincoders.twinpush.sdk.communications.asyhttp.AsyncHttpClient;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultRequest implements TwinRequest {
    private TwinRequestLauncher a;
    private List<OnRequestFinishListener> b = new ArrayList();
    private String c = "UTF-8";
    protected String contentType = "";
    private Boolean d = Boolean.valueOf(false);
    protected boolean dummy = false;
    protected HttpMethod httpMethod = HttpMethod.POST;
    protected String requestName;
    protected List<TwinRequestParam> requestParams = new ArrayList();
    protected String url = "";

    public String getBodyContent() {
        return "";
    }

    public boolean isHttpResponseStatusValid(int i) {
        return false;
    }

    public void onSetupClient(AsyncHttpClient asyncHttpClient) {
    }

    public void addParam(String str, Object obj) {
        if (obj != null) {
            this.requestParams.add(new DefaultRequestParam(str, obj));
        }
    }

    public void addParam(TwinRequestParam twinRequestParam) {
        this.requestParams.add(twinRequestParam);
    }

    public String getName() {
        return this.requestName;
    }

    public List<TwinRequestParam> getParams() {
        return this.requestParams;
    }

    public void setRequestLauncher(TwinRequestLauncher twinRequestLauncher) {
        this.a = twinRequestLauncher;
    }

    public Boolean isCanceled() {
        return this.d;
    }

    public void launch() {
        this.d = Boolean.valueOf(false);
        this.a.launchRequest(this);
    }

    public void cancel() {
        this.d = Boolean.valueOf(true);
        this.a.cancelRequest(this);
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public void onRequestError(Exception exc) {
        if (!isCanceled().booleanValue()) {
            getListener().onError(exc);
            notifyFinishListeners();
        }
    }

    public String getURL() {
        return this.url;
    }

    public String getEncoding() {
        return this.c;
    }

    public String getContentType() {
        return this.contentType;
    }

    public TwinRequestLauncher getRequestLauncher() {
        return this.a;
    }

    public boolean isDummy() {
        return this.dummy;
    }

    public void addOnRequestFinishListener(OnRequestFinishListener onRequestFinishListener) {
        if (!this.b.contains(onRequestFinishListener)) {
            this.b.add(onRequestFinishListener);
        }
    }

    /* access modifiers changed from: protected */
    public void notifyFinishListeners() {
        for (OnRequestFinishListener onRequestFinish : this.b) {
            onRequestFinish.onRequestFinish();
        }
    }
}
