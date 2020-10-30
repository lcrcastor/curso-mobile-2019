package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Immutable
class ResponseCachingPolicy {
    private static final String[] b = {"s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, HeaderConstants.PUBLIC};
    private static final Set<Integer> f = new HashSet(Arrays.asList(new Integer[]{Integer.valueOf(200), Integer.valueOf(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION), Integer.valueOf(HttpStatus.SC_MULTIPLE_CHOICES), Integer.valueOf(HttpStatus.SC_MOVED_PERMANENTLY), Integer.valueOf(HttpStatus.SC_GONE)}));
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());
    private final long c;
    private final boolean d;
    private final boolean e;
    private final Set<Integer> g;

    private boolean a(int i) {
        if (i >= 100 && i <= 101) {
            return false;
        }
        if (i >= 200 && i <= 206) {
            return false;
        }
        if (i >= 300 && i <= 307) {
            return false;
        }
        if (i < 400 || i > 417) {
            return i < 500 || i > 505;
        }
        return false;
    }

    public ResponseCachingPolicy(long j, boolean z, boolean z2, boolean z3) {
        this.c = j;
        this.d = z;
        this.e = z2;
        if (z3) {
            this.g = new HashSet(Arrays.asList(new Integer[]{Integer.valueOf(HttpStatus.SC_PARTIAL_CONTENT)}));
            return;
        }
        this.g = new HashSet(Arrays.asList(new Integer[]{Integer.valueOf(HttpStatus.SC_PARTIAL_CONTENT), Integer.valueOf(HttpStatus.SC_SEE_OTHER)}));
    }

    public boolean a(String str, HttpResponse httpResponse) {
        boolean z;
        boolean z2 = false;
        if ("GET".equals(str) || "HEAD".equals(str)) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (f.contains(Integer.valueOf(statusCode))) {
                z = true;
            } else if (this.g.contains(Integer.valueOf(statusCode)) || a(statusCode)) {
                return false;
            } else {
                z = false;
            }
            Header firstHeader = httpResponse.getFirstHeader("Content-Length");
            if ((firstHeader != null && ((long) Integer.parseInt(firstHeader.getValue())) > this.c) || httpResponse.getHeaders("Age").length > 1 || httpResponse.getHeaders("Expires").length > 1) {
                return false;
            }
            Header[] headers = httpResponse.getHeaders("Date");
            if (headers.length != 1 || DateUtils.parseDate(headers[0].getValue()) == null) {
                return false;
            }
            for (Header elements : httpResponse.getHeaders("Vary")) {
                for (HeaderElement name : elements.getElements()) {
                    if ("*".equals(name.getName())) {
                        return false;
                    }
                }
            }
            if (a(httpResponse)) {
                return false;
            }
            if (z || b(httpResponse)) {
                z2 = true;
            }
            return z2;
        }
        this.a.debug("Response was not cacheable.");
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(HttpResponse httpResponse) {
        HeaderElement[] elements;
        for (Header elements2 : httpResponse.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : elements2.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(headerElement.getName()) || HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName()) || (this.d && HeaderConstants.PRIVATE.equals(headerElement.getName()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean a(HttpMessage httpMessage, String[] strArr) {
        HeaderElement[] elements;
        for (Header elements2 : httpMessage.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : elements2.getElements()) {
                for (String equalsIgnoreCase : strArr) {
                    if (equalsIgnoreCase.equalsIgnoreCase(headerElement.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean b(HttpResponse httpResponse) {
        if (httpResponse.getFirstHeader("Expires") != null) {
            return true;
        }
        return a((HttpMessage) httpResponse, new String[]{"max-age", "s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE, HeaderConstants.PUBLIC});
    }

    public boolean a(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (a(httpRequest)) {
            this.a.debug("Response was not cacheable.");
            return false;
        }
        if (a((HttpMessage) httpRequest, new String[]{HeaderConstants.CACHE_CONTROL_NO_STORE})) {
            return false;
        }
        if (httpRequest.getRequestLine().getUri().contains("?")) {
            if (this.e && d(httpResponse)) {
                this.a.debug("Response was not cacheable as it had a query string.");
                return false;
            } else if (!b(httpResponse)) {
                this.a.debug("Response was not cacheable as it is missing explicit caching headers.");
                return false;
            }
        }
        if (c(httpResponse)) {
            return false;
        }
        if (this.d) {
            Header[] headers = httpRequest.getHeaders("Authorization");
            if (headers != null && headers.length > 0 && !a((HttpMessage) httpResponse, b)) {
                return false;
            }
        }
        return a(httpRequest.getRequestLine().getMethod(), httpResponse);
    }

    private boolean c(HttpResponse httpResponse) {
        boolean z = false;
        if (httpResponse.getFirstHeader("Cache-Control") != null) {
            return false;
        }
        Header firstHeader = httpResponse.getFirstHeader("Expires");
        Header firstHeader2 = httpResponse.getFirstHeader("Date");
        if (firstHeader == null || firstHeader2 == null) {
            return false;
        }
        Date parseDate = DateUtils.parseDate(firstHeader.getValue());
        Date parseDate2 = DateUtils.parseDate(firstHeader2.getValue());
        if (parseDate == null || parseDate2 == null) {
            return false;
        }
        if (parseDate.equals(parseDate2) || parseDate.before(parseDate2)) {
            z = true;
        }
        return z;
    }

    private boolean d(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Via");
        if (firstHeader != null) {
            HeaderElement[] elements = firstHeader.getElements();
            if (elements.length > 0) {
                String str = elements[0].toString().split("\\s")[0];
                if (str.contains("/")) {
                    return str.equals("HTTP/1.0");
                }
                return str.equals("1.0");
            }
        }
        return HttpVersion.HTTP_1_0.equals(httpResponse.getProtocolVersion());
    }

    private boolean a(HttpRequest httpRequest) {
        return httpRequest.getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) > 0;
    }
}
