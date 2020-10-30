package cz.msebera.android.httpclient.client.protocol;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.conn.routing.RouteInfo;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieSpec;
import cz.msebera.android.httpclient.cookie.CookieSpecProvider;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Immutable
public class RequestAddCookies implements HttpRequestInterceptor {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        URI uri;
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
            HttpClientContext adapt = HttpClientContext.adapt(httpContext);
            CookieStore cookieStore = adapt.getCookieStore();
            if (cookieStore == null) {
                this.log.debug("Cookie store not specified in HTTP context");
                return;
            }
            Lookup cookieSpecRegistry = adapt.getCookieSpecRegistry();
            if (cookieSpecRegistry == null) {
                this.log.debug("CookieSpec registry not specified in HTTP context");
                return;
            }
            HttpHost targetHost = adapt.getTargetHost();
            if (targetHost == null) {
                this.log.debug("Target host not set in the context");
                return;
            }
            RouteInfo httpRoute = adapt.getHttpRoute();
            if (httpRoute == null) {
                this.log.debug("Connection route not set in the context");
                return;
            }
            String cookieSpec = adapt.getRequestConfig().getCookieSpec();
            if (cookieSpec == null) {
                cookieSpec = "default";
            }
            if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("CookieSpec selected: ");
                sb.append(cookieSpec);
                httpClientAndroidLog.debug(sb.toString());
            }
            String str = null;
            if (httpRequest instanceof HttpUriRequest) {
                uri = ((HttpUriRequest) httpRequest).getURI();
            } else {
                try {
                    uri = new URI(httpRequest.getRequestLine().getUri());
                } catch (URISyntaxException unused) {
                    uri = null;
                }
            }
            if (uri != null) {
                str = uri.getPath();
            }
            String hostName = targetHost.getHostName();
            int port = targetHost.getPort();
            if (port < 0) {
                port = httpRoute.getTargetHost().getPort();
            }
            boolean z = false;
            if (port < 0) {
                port = 0;
            }
            if (TextUtils.isEmpty(str)) {
                str = "/";
            }
            CookieOrigin cookieOrigin = new CookieOrigin(hostName, port, str, httpRoute.isSecure());
            CookieSpecProvider cookieSpecProvider = (CookieSpecProvider) cookieSpecRegistry.lookup(cookieSpec);
            if (cookieSpecProvider == null) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog2 = this.log;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unsupported cookie policy: ");
                    sb2.append(cookieSpec);
                    httpClientAndroidLog2.debug(sb2.toString());
                }
                return;
            }
            CookieSpec create = cookieSpecProvider.create(adapt);
            List<Cookie> cookies = cookieStore.getCookies();
            ArrayList arrayList = new ArrayList();
            Date date = new Date();
            for (Cookie cookie : cookies) {
                if (cookie.isExpired(date)) {
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog3 = this.log;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Cookie ");
                        sb3.append(cookie);
                        sb3.append(" expired");
                        httpClientAndroidLog3.debug(sb3.toString());
                    }
                    z = true;
                } else if (create.match(cookie, cookieOrigin)) {
                    if (this.log.isDebugEnabled()) {
                        HttpClientAndroidLog httpClientAndroidLog4 = this.log;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Cookie ");
                        sb4.append(cookie);
                        sb4.append(" match ");
                        sb4.append(cookieOrigin);
                        httpClientAndroidLog4.debug(sb4.toString());
                    }
                    arrayList.add(cookie);
                }
            }
            if (z) {
                cookieStore.clearExpired(date);
            }
            if (!arrayList.isEmpty()) {
                for (Header addHeader : create.formatCookies(arrayList)) {
                    httpRequest.addHeader(addHeader);
                }
            }
            if (create.getVersion() > 0) {
                Header versionHeader = create.getVersionHeader();
                if (versionHeader != null) {
                    httpRequest.addHeader(versionHeader);
                }
            }
            httpContext.setAttribute("http.cookie-spec", create);
            httpContext.setAttribute("http.cookie-origin", cookieOrigin);
        }
    }
}
