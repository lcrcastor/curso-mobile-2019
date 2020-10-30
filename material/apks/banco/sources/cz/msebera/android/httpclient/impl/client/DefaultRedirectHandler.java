package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.CircularRedirectException;
import cz.msebera.android.httpclient.client.RedirectHandler;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.net.URI;
import java.net.URISyntaxException;

@Immutable
@Deprecated
public class DefaultRedirectHandler implements RedirectHandler {
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        boolean z = true;
        if (statusCode != 307) {
            switch (statusCode) {
                case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
                case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
                    break;
                case HttpStatus.SC_SEE_OTHER /*303*/:
                    return true;
                default:
                    return false;
            }
        }
        String method = ((HttpRequest) httpContext.getAttribute("http.request")).getRequestLine().getMethod();
        if (!method.equalsIgnoreCase("GET") && !method.equalsIgnoreCase("HEAD")) {
            z = false;
        }
        return z;
    }

    public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) {
        URI uri;
        Args.notNull(httpResponse, "HTTP response");
        Header firstHeader = httpResponse.getFirstHeader("location");
        if (firstHeader == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Received redirect response ");
            sb.append(httpResponse.getStatusLine());
            sb.append(" but no location header");
            throw new ProtocolException(sb.toString());
        }
        String value = firstHeader.getValue();
        if (this.log.isDebugEnabled()) {
            HttpClientAndroidLog httpClientAndroidLog = this.log;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Redirect requested to location '");
            sb2.append(value);
            sb2.append("'");
            httpClientAndroidLog.debug(sb2.toString());
        }
        try {
            URI uri2 = new URI(value);
            HttpParams params = httpResponse.getParams();
            if (!uri2.isAbsolute()) {
                if (params.isParameterTrue(ClientPNames.REJECT_RELATIVE_REDIRECT)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Relative redirect location '");
                    sb3.append(uri2);
                    sb3.append("' not allowed");
                    throw new ProtocolException(sb3.toString());
                }
                HttpHost httpHost = (HttpHost) httpContext.getAttribute("http.target_host");
                Asserts.notNull(httpHost, "Target host");
                try {
                    uri2 = URIUtils.resolve(URIUtils.rewriteURI(new URI(((HttpRequest) httpContext.getAttribute("http.request")).getRequestLine().getUri()), httpHost, true), uri2);
                } catch (URISyntaxException e) {
                    throw new ProtocolException(e.getMessage(), e);
                }
            }
            if (params.isParameterFalse(ClientPNames.ALLOW_CIRCULAR_REDIRECTS)) {
                RedirectLocations redirectLocations = (RedirectLocations) httpContext.getAttribute("http.protocol.redirect-locations");
                if (redirectLocations == null) {
                    redirectLocations = new RedirectLocations();
                    httpContext.setAttribute("http.protocol.redirect-locations", redirectLocations);
                }
                if (uri2.getFragment() != null) {
                    try {
                        uri = URIUtils.rewriteURI(uri2, new HttpHost(uri2.getHost(), uri2.getPort(), uri2.getScheme()), true);
                    } catch (URISyntaxException e2) {
                        throw new ProtocolException(e2.getMessage(), e2);
                    }
                } else {
                    uri = uri2;
                }
                if (redirectLocations.contains(uri)) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Circular redirect to '");
                    sb4.append(uri);
                    sb4.append("'");
                    throw new CircularRedirectException(sb4.toString());
                }
                redirectLocations.add(uri);
            }
            return uri2;
        } catch (URISyntaxException e3) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Invalid redirect URI: ");
            sb5.append(value);
            throw new ProtocolException(sb5.toString(), e3);
        }
    }
}
