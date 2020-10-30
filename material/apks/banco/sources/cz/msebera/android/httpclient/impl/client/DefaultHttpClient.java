package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.protocol.RequestAddCookies;
import cz.msebera.android.httpclient.client.protocol.RequestAuthCache;
import cz.msebera.android.httpclient.client.protocol.RequestClientConnControl;
import cz.msebera.android.httpclient.client.protocol.RequestDefaultHeaders;
import cz.msebera.android.httpclient.client.protocol.RequestProxyAuthentication;
import cz.msebera.android.httpclient.client.protocol.RequestTargetAuthentication;
import cz.msebera.android.httpclient.client.protocol.ResponseProcessCookies;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.params.HttpProtocolParams;
import cz.msebera.android.httpclient.params.SyncBasicHttpParams;
import cz.msebera.android.httpclient.protocol.BasicHttpProcessor;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.protocol.RequestContent;
import cz.msebera.android.httpclient.protocol.RequestExpectContinue;
import cz.msebera.android.httpclient.protocol.RequestTargetHost;
import cz.msebera.android.httpclient.protocol.RequestUserAgent;
import cz.msebera.android.httpclient.util.VersionInfo;

@ThreadSafe
@Deprecated
public class DefaultHttpClient extends AbstractHttpClient {
    public DefaultHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        super(clientConnectionManager, httpParams);
    }

    public DefaultHttpClient(ClientConnectionManager clientConnectionManager) {
        super(clientConnectionManager, null);
    }

    public DefaultHttpClient(HttpParams httpParams) {
        super(null, httpParams);
    }

    public DefaultHttpClient() {
        super(null, null);
    }

    /* access modifiers changed from: protected */
    public HttpParams createHttpParams() {
        SyncBasicHttpParams syncBasicHttpParams = new SyncBasicHttpParams();
        setDefaultHttpParams(syncBasicHttpParams);
        return syncBasicHttpParams;
    }

    public static void setDefaultHttpParams(HttpParams httpParams) {
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams, HTTP.DEF_CONTENT_CHARSET.name());
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setUserAgent(httpParams, VersionInfo.getUserAgent("Apache-HttpClient", "cz.msebera.android.httpclient.client", DefaultHttpClient.class));
    }

    /* access modifiers changed from: protected */
    public BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor basicHttpProcessor = new BasicHttpProcessor();
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestDefaultHeaders());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestContent());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestTargetHost());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestClientConnControl());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestUserAgent());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestExpectContinue());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestAddCookies());
        basicHttpProcessor.addInterceptor((HttpResponseInterceptor) new ResponseProcessCookies());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestAuthCache());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestTargetAuthentication());
        basicHttpProcessor.addInterceptor((HttpRequestInterceptor) new RequestProxyAuthentication());
        return basicHttpProcessor;
    }
}
