package com.twincoders.twinpush.sdk.communications.asyhttp;

import android.content.Context;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.conn.params.ConnManagerParams;
import cz.msebera.android.httpclient.conn.params.ConnPerRouteBean;
import cz.msebera.android.httpclient.conn.scheme.PlainSocketFactory;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry;
import cz.msebera.android.httpclient.conn.scheme.SocketFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.entity.HttpEntityWrapper;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.conn.tsccm.ThreadSafeClientConnManager;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.protocol.SyncBasicHttpContext;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;

public class AsyncHttpClient {
    private static int a = 10;
    private static int b = 10000;
    private final DefaultHttpClient c;
    private final HttpContext d = new SyncBasicHttpContext(new BasicHttpContext());
    private ThreadPoolExecutor e;
    private final Map<Context, List<WeakReference<Future<?>>>> f;
    /* access modifiers changed from: private */
    public final Map<String, String> g;

    static class InflatingEntity extends HttpEntityWrapper {
        public long getContentLength() {
            return -1;
        }

        public InflatingEntity(HttpEntity httpEntity) {
            super(httpEntity);
        }

        public InputStream getContent() {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }
    }

    public AsyncHttpClient() {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(basicHttpParams, (long) b);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(a));
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 10);
        HttpConnectionParams.setSoTimeout(basicHttpParams, b);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, b);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(basicHttpParams, String.format("android-async-http/%s (http://loopj.com/android-async-http)", new Object[]{"1.4.3"}));
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, (SocketFactory) PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", (SocketFactory) SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
        this.c = new DefaultHttpClient(threadSafeClientConnManager, basicHttpParams);
        this.c.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest httpRequest, HttpContext httpContext) {
                if (!httpRequest.containsHeader("Accept-Encoding")) {
                    httpRequest.addHeader("Accept-Encoding", io.fabric.sdk.android.services.network.HttpRequest.ENCODING_GZIP);
                }
                for (String str : AsyncHttpClient.this.g.keySet()) {
                    httpRequest.addHeader(str, (String) AsyncHttpClient.this.g.get(str));
                }
            }
        });
        this.c.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse httpResponse, HttpContext httpContext) {
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    Header contentEncoding = entity.getContentEncoding();
                    if (contentEncoding != null) {
                        HeaderElement[] elements = contentEncoding.getElements();
                        int length = elements.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            } else if (elements[i].getName().equalsIgnoreCase(io.fabric.sdk.android.services.network.HttpRequest.ENCODING_GZIP)) {
                                httpResponse.setEntity(new InflatingEntity(httpResponse.getEntity()));
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
            }
        });
        this.c.setHttpRequestRetryHandler(new RetryHandler(5));
        this.e = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.f = new WeakHashMap();
        this.g = new HashMap();
    }

    public HttpClient getHttpClient() {
        return this.c;
    }

    public HttpContext getHttpContext() {
        return this.d;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.d.setAttribute("http.cookie-store", cookieStore);
    }

    public void setThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        this.e = threadPoolExecutor;
    }

    public void setUserAgent(String str) {
        HttpProtocolParams.setUserAgent(this.c.getParams(), str);
    }

    public void setTimeout(int i) {
        HttpParams params = this.c.getParams();
        ConnManagerParams.setTimeout(params, (long) i);
        HttpConnectionParams.setSoTimeout(params, i);
        HttpConnectionParams.setConnectionTimeout(params, i);
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.c.getConnectionManager().getSchemeRegistry().register(new Scheme("https", (SocketFactory) sSLSocketFactory, 443));
    }

    public void addHeader(String str, String str2) {
        this.g.put(str, str2);
    }

    public void setBasicAuth(String str, String str2) {
        setBasicAuth(str, str2, AuthScope.ANY);
    }

    public void setBasicAuth(String str, String str2, AuthScope authScope) {
        this.c.getCredentialsProvider().setCredentials(authScope, new UsernamePasswordCredentials(str, str2));
    }

    public void cancelRequests(Context context, boolean z) {
        List<WeakReference> list = (List) this.f.get(context);
        if (list != null) {
            for (WeakReference weakReference : list) {
                Future future = (Future) weakReference.get();
                if (future != null) {
                    future.cancel(z);
                }
            }
        }
        this.f.remove(context);
    }

    public void get(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        get(null, str, null, asyncHttpResponseHandler);
    }

    public void get(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        get(null, str, requestParams, asyncHttpResponseHandler);
    }

    public void get(Context context, String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        get(context, str, null, asyncHttpResponseHandler);
    }

    public void get(Context context, String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        sendRequest(this.c, this.d, new HttpGet(getUrlWithQueryString(str, requestParams)), null, asyncHttpResponseHandler, context);
    }

    public void get(Context context, String str, Header[] headerArr, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpGet httpGet = new HttpGet(getUrlWithQueryString(str, requestParams));
        if (headerArr != null) {
            httpGet.setHeaders(headerArr);
        }
        sendRequest(this.c, this.d, httpGet, null, asyncHttpResponseHandler, context);
    }

    public void post(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        post(null, str, null, asyncHttpResponseHandler);
    }

    public void post(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        post(null, str, requestParams, asyncHttpResponseHandler);
    }

    public void post(Context context, String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        post(context, str, a(requestParams), null, asyncHttpResponseHandler);
    }

    public void post(Context context, String str, HttpEntity httpEntity, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        sendRequest(this.c, this.d, a(new HttpPost(str), httpEntity), str2, asyncHttpResponseHandler, context);
    }

    public void post(Context context, String str, Header[] headerArr, RequestParams requestParams, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpPost httpPost = new HttpPost(str);
        if (requestParams != null) {
            httpPost.setEntity(a(requestParams));
        }
        if (headerArr != null) {
            httpPost.setHeaders(headerArr);
        }
        sendRequest(this.c, this.d, httpPost, str2, asyncHttpResponseHandler, context);
    }

    public void post(Context context, String str, Header[] headerArr, HttpEntity httpEntity, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpEntityEnclosingRequestBase a2 = a(new HttpPost(str), httpEntity);
        if (headerArr != null) {
            a2.setHeaders(headerArr);
        }
        sendRequest(this.c, this.d, a2, str2, asyncHttpResponseHandler, context);
    }

    public void put(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        put(null, str, null, asyncHttpResponseHandler);
    }

    public void put(String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        put(null, str, requestParams, asyncHttpResponseHandler);
    }

    public void put(Context context, String str, RequestParams requestParams, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        put(context, str, a(requestParams), null, asyncHttpResponseHandler);
    }

    public void put(Context context, String str, HttpEntity httpEntity, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        sendRequest(this.c, this.d, a(new HttpPut(str), httpEntity), str2, asyncHttpResponseHandler, context);
    }

    public void put(Context context, String str, Header[] headerArr, HttpEntity httpEntity, String str2, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpEntityEnclosingRequestBase a2 = a(new HttpPut(str), httpEntity);
        if (headerArr != null) {
            a2.setHeaders(headerArr);
        }
        sendRequest(this.c, this.d, a2, str2, asyncHttpResponseHandler, context);
    }

    public void delete(String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        delete(null, str, asyncHttpResponseHandler);
    }

    public void delete(Context context, String str, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        sendRequest(this.c, this.d, new HttpDelete(str), null, asyncHttpResponseHandler, context);
    }

    public void delete(Context context, String str, Header[] headerArr, AsyncHttpResponseHandler asyncHttpResponseHandler) {
        HttpDelete httpDelete = new HttpDelete(str);
        if (headerArr != null) {
            httpDelete.setHeaders(headerArr);
        }
        sendRequest(this.c, this.d, httpDelete, null, asyncHttpResponseHandler, context);
    }

    /* access modifiers changed from: protected */
    public void sendRequest(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str, AsyncHttpResponseHandler asyncHttpResponseHandler, Context context) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        Future submit = this.e.submit(new AsyncHttpRequest(defaultHttpClient, httpContext, httpUriRequest, asyncHttpResponseHandler));
        if (context != null) {
            List list = (List) this.f.get(context);
            if (list == null) {
                list = new LinkedList();
                this.f.put(context, list);
            }
            list.add(new WeakReference(submit));
        }
    }

    public static String getUrlWithQueryString(String str, RequestParams requestParams) {
        if (requestParams == null) {
            return str;
        }
        String paramString = requestParams.getParamString();
        if (str.indexOf("?") == -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("?");
            sb.append(paramString);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("&");
        sb2.append(paramString);
        return sb2.toString();
    }

    private HttpEntity a(RequestParams requestParams) {
        if (requestParams != null) {
            return requestParams.getEntity();
        }
        return null;
    }

    private HttpEntityEnclosingRequestBase a(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, HttpEntity httpEntity) {
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.setEntity(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }
}
