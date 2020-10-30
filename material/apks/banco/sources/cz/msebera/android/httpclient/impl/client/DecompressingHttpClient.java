package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.client.protocol.RequestAcceptEncoding;
import cz.msebera.android.httpclient.client.protocol.ResponseContentEncoding;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;
import java.io.IOException;

@Deprecated
public class DecompressingHttpClient implements HttpClient {
    private final HttpClient a;
    private final HttpRequestInterceptor b;
    private final HttpResponseInterceptor c;

    public DecompressingHttpClient() {
        this(new DefaultHttpClient());
    }

    public DecompressingHttpClient(HttpClient httpClient) {
        this(httpClient, new RequestAcceptEncoding(), new ResponseContentEncoding());
    }

    DecompressingHttpClient(HttpClient httpClient, HttpRequestInterceptor httpRequestInterceptor, HttpResponseInterceptor httpResponseInterceptor) {
        this.a = httpClient;
        this.b = httpRequestInterceptor;
        this.c = httpResponseInterceptor;
    }

    public HttpParams getParams() {
        return this.a.getParams();
    }

    public ClientConnectionManager getConnectionManager() {
        return this.a.getConnectionManager();
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest) {
        return execute(a(httpUriRequest), (HttpRequest) httpUriRequest, (HttpContext) null);
    }

    public HttpClient getHttpClient() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public HttpHost a(HttpUriRequest httpUriRequest) {
        return URIUtils.extractHost(httpUriRequest.getURI());
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) {
        return execute(a(httpUriRequest), (HttpRequest) httpUriRequest, httpContext);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
        return execute(httpHost, httpRequest, (HttpContext) null);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        HttpRequest httpRequest2;
        HttpResponse execute;
        if (httpContext == null) {
            try {
                httpContext = new BasicHttpContext();
            } catch (HttpException e) {
                EntityUtils.consume(execute.getEntity());
                throw e;
            } catch (IOException e2) {
                EntityUtils.consume(execute.getEntity());
                throw e2;
            } catch (RuntimeException e3) {
                EntityUtils.consume(execute.getEntity());
                throw e3;
            } catch (HttpException e4) {
                throw new ClientProtocolException((Throwable) e4);
            }
        }
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            httpRequest2 = new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest) httpRequest);
        } else {
            httpRequest2 = new RequestWrapper(httpRequest);
        }
        this.b.process(httpRequest2, httpContext);
        execute = this.a.execute(httpHost, httpRequest2, httpContext);
        this.c.process(execute, httpContext);
        if (Boolean.TRUE.equals(httpContext.getAttribute(ResponseContentEncoding.UNCOMPRESSED))) {
            execute.removeHeaders("Content-Length");
            execute.removeHeaders("Content-Encoding");
            execute.removeHeaders("Content-MD5");
        }
        return execute;
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) {
        return execute(a(httpUriRequest), (HttpRequest) httpUriRequest, responseHandler);
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        return execute(a(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) {
        return execute(httpHost, httpRequest, responseHandler, null);
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        HttpResponse execute = execute(httpHost, httpRequest, httpContext);
        try {
            T handleResponse = responseHandler.handleResponse(execute);
            return handleResponse;
        } finally {
            HttpEntity entity = execute.getEntity();
            if (entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }
}
