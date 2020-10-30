package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.HttpRequestRetryHandler;
import cz.msebera.android.httpclient.client.NonRepeatableRequestException;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpExecutionAware;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.conn.routing.HttpRoute;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;

@Immutable
public class RetryExec implements ClientExecChain {
    private final ClientExecChain a;
    private final HttpRequestRetryHandler b;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public RetryExec(ClientExecChain clientExecChain, HttpRequestRetryHandler httpRequestRetryHandler) {
        Args.notNull(clientExecChain, "HTTP request executor");
        Args.notNull(httpRequestRetryHandler, "HTTP request retry handler");
        this.a = clientExecChain;
        this.b = httpRequestRetryHandler;
    }

    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) {
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        Header[] allHeaders = httpRequestWrapper.getAllHeaders();
        int i = 1;
        while (true) {
            try {
                return this.a.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
            } catch (IOException e) {
                if (httpExecutionAware != null && httpExecutionAware.isAborted()) {
                    this.log.debug("Request has been aborted");
                    throw e;
                } else if (this.b.retryRequest(e, i, httpClientContext)) {
                    if (this.log.isInfoEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog = this.log;
                        StringBuilder sb = new StringBuilder();
                        sb.append("I/O exception (");
                        sb.append(e.getClass().getName());
                        sb.append(") caught when processing request to ");
                        sb.append(httpRoute);
                        sb.append(": ");
                        sb.append(e.getMessage());
                        httpClientAndroidLog.info(sb.toString());
                    }
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(e.getMessage(), e);
                    }
                    if (!RequestEntityProxy.a((HttpRequest) httpRequestWrapper)) {
                        this.log.debug("Cannot retry non-repeatable request");
                        throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity", e);
                    }
                    httpRequestWrapper.setHeaders(allHeaders);
                    if (this.log.isInfoEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Retrying request to ");
                        sb2.append(httpRoute);
                        httpClientAndroidLog2.info(sb2.toString());
                    }
                    i++;
                } else if (e instanceof NoHttpResponseException) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(httpRoute.getTargetHost().toHostString());
                    sb3.append(" failed to respond");
                    NoHttpResponseException noHttpResponseException = new NoHttpResponseException(sb3.toString());
                    noHttpResponseException.setStackTrace(e.getStackTrace());
                    throw noHttpResponseException;
                } else {
                    throw e;
                }
            }
        }
    }
}
