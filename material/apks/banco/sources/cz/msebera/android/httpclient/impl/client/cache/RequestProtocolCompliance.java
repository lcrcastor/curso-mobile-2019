package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.entity.AbstractHttpEntity;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicHttpResponse;
import cz.msebera.android.httpclient.message.BasicStatusLine;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Immutable
class RequestProtocolCompliance {
    private static final List<String> b = Arrays.asList(new String[]{HeaderConstants.CACHE_CONTROL_MIN_FRESH, HeaderConstants.CACHE_CONTROL_MAX_STALE, "max-age"});
    private final boolean a;

    public RequestProtocolCompliance() {
        this.a = false;
    }

    public RequestProtocolCompliance(boolean z) {
        this.a = z;
    }

    public List<RequestProtocolError> a(HttpRequest httpRequest) {
        ArrayList arrayList = new ArrayList();
        RequestProtocolError k = k(httpRequest);
        if (k != null) {
            arrayList.add(k);
        }
        if (!this.a) {
            RequestProtocolError l = l(httpRequest);
            if (l != null) {
                arrayList.add(l);
            }
        }
        RequestProtocolError m = m(httpRequest);
        if (m != null) {
            arrayList.add(m);
        }
        return arrayList;
    }

    public void a(HttpRequestWrapper httpRequestWrapper) {
        if (e(httpRequestWrapper)) {
            ((HttpEntityEnclosingRequest) httpRequestWrapper).setEntity(null);
        }
        h(httpRequestWrapper);
        g(httpRequestWrapper);
        f(httpRequestWrapper);
        d(httpRequestWrapper);
        if (c(httpRequestWrapper) || b(httpRequestWrapper)) {
            httpRequestWrapper.setProtocolVersion(HttpVersion.HTTP_1_1);
        }
    }

    private void d(HttpRequest httpRequest) {
        HeaderElement[] elements;
        ArrayList arrayList = new ArrayList();
        Header[] headers = httpRequest.getHeaders("Cache-Control");
        int length = headers.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            boolean z2 = z;
            for (HeaderElement headerElement : headers[i].getElements()) {
                if (!b.contains(headerElement.getName())) {
                    arrayList.add(headerElement);
                }
                if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(headerElement.getName())) {
                    z2 = true;
                }
            }
            i++;
            z = z2;
        }
        if (z) {
            httpRequest.removeHeaders("Cache-Control");
            httpRequest.setHeader("Cache-Control", a((List<HeaderElement>) arrayList));
        }
    }

    private String a(List<HeaderElement> list) {
        StringBuilder sb = new StringBuilder("");
        boolean z = true;
        for (HeaderElement headerElement : list) {
            if (!z) {
                sb.append(",");
            } else {
                z = false;
            }
            sb.append(headerElement.toString());
        }
        return sb.toString();
    }

    private boolean e(HttpRequest httpRequest) {
        return "TRACE".equals(httpRequest.getRequestLine().getMethod()) && (httpRequest instanceof HttpEntityEnclosingRequest);
    }

    private void f(HttpRequest httpRequest) {
        if ("OPTIONS".equals(httpRequest.getRequestLine().getMethod())) {
            Header firstHeader = httpRequest.getFirstHeader("Max-Forwards");
            if (firstHeader != null) {
                httpRequest.removeHeaders("Max-Forwards");
                httpRequest.setHeader("Max-Forwards", Integer.toString(Integer.parseInt(firstHeader.getValue()) - 1));
            }
        }
    }

    private void g(HttpRequest httpRequest) {
        if ("OPTIONS".equals(httpRequest.getRequestLine().getMethod()) && (httpRequest instanceof HttpEntityEnclosingRequest)) {
            a((HttpEntityEnclosingRequest) httpRequest);
        }
    }

    private void a(HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        if (httpEntityEnclosingRequest.getEntity().getContentType() == null) {
            ((AbstractHttpEntity) httpEntityEnclosingRequest.getEntity()).setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        }
    }

    private void h(HttpRequest httpRequest) {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest httpEntityEnclosingRequest = (HttpEntityEnclosingRequest) httpRequest;
            if (!httpEntityEnclosingRequest.expectContinue() || httpEntityEnclosingRequest.getEntity() == null) {
                i(httpRequest);
            } else {
                j(httpRequest);
            }
        } else {
            i(httpRequest);
        }
    }

    private void i(HttpRequest httpRequest) {
        HeaderElement[] elements;
        Header[] headers = httpRequest.getHeaders("Expect");
        ArrayList arrayList = new ArrayList();
        int length = headers.length;
        ArrayList<HeaderElement> arrayList2 = arrayList;
        int i = 0;
        boolean z = false;
        while (i < length) {
            Header header = headers[i];
            boolean z2 = z;
            for (HeaderElement headerElement : header.getElements()) {
                if (!HTTP.EXPECT_CONTINUE.equalsIgnoreCase(headerElement.getName())) {
                    arrayList2.add(headerElement);
                } else {
                    z2 = true;
                }
            }
            if (z2) {
                httpRequest.removeHeader(header);
                for (HeaderElement name : arrayList2) {
                    httpRequest.addHeader(new BasicHeader("Expect", name.getName()));
                }
                return;
            }
            arrayList2 = new ArrayList<>();
            i++;
            z = z2;
        }
    }

    private void j(HttpRequest httpRequest) {
        Header[] headers = httpRequest.getHeaders("Expect");
        int length = headers.length;
        int i = 0;
        boolean z = false;
        while (i < length) {
            boolean z2 = z;
            for (HeaderElement name : headers[i].getElements()) {
                if (HTTP.EXPECT_CONTINUE.equalsIgnoreCase(name.getName())) {
                    z2 = true;
                }
            }
            i++;
            z = z2;
        }
        if (!z) {
            httpRequest.addHeader("Expect", HTTP.EXPECT_CONTINUE);
        }
    }

    /* access modifiers changed from: protected */
    public boolean b(HttpRequest httpRequest) {
        ProtocolVersion protocolVersion = httpRequest.getProtocolVersion();
        if (protocolVersion.getMajor() == HttpVersion.HTTP_1_1.getMajor() && protocolVersion.getMinor() > HttpVersion.HTTP_1_1.getMinor()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean c(HttpRequest httpRequest) {
        return httpRequest.getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) < 0;
    }

    public HttpResponse a(RequestProtocolError requestProtocolError) {
        switch (requestProtocolError) {
            case BODY_BUT_NO_LENGTH_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_LENGTH_REQUIRED, ""));
            case WEAK_ETAG_AND_RANGE_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "Weak eTag not compatible with byte range"));
            case WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "Weak eTag not compatible with PUT or DELETE requests"));
            case NO_CACHE_DIRECTIVE_WITH_FIELD_NAME:
                return new BasicHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "No-Cache directive MUST NOT include a field name"));
            default:
                throw new IllegalStateException("The request was compliant, therefore no error can be generated for it.");
        }
    }

    private RequestProtocolError k(HttpRequest httpRequest) {
        if (!"GET".equals(httpRequest.getRequestLine().getMethod()) || httpRequest.getFirstHeader("Range") == null) {
            return null;
        }
        Header firstHeader = httpRequest.getFirstHeader("If-Range");
        if (firstHeader != null && firstHeader.getValue().startsWith("W/")) {
            return RequestProtocolError.WEAK_ETAG_AND_RANGE_ERROR;
        }
        return null;
    }

    private RequestProtocolError l(HttpRequest httpRequest) {
        String method = httpRequest.getRequestLine().getMethod();
        if (!"PUT".equals(method) && !"DELETE".equals(method)) {
            return null;
        }
        Header firstHeader = httpRequest.getFirstHeader("If-Match");
        if (firstHeader == null) {
            Header firstHeader2 = httpRequest.getFirstHeader("If-None-Match");
            if (firstHeader2 != null && firstHeader2.getValue().startsWith("W/")) {
                return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
            }
        } else if (firstHeader.getValue().startsWith("W/")) {
            return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
        }
        return null;
    }

    private RequestProtocolError m(HttpRequest httpRequest) {
        HeaderElement[] elements;
        for (Header elements2 : httpRequest.getHeaders("Cache-Control")) {
            for (HeaderElement headerElement : elements2.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equalsIgnoreCase(headerElement.getName()) && headerElement.getValue() != null) {
                    return RequestProtocolError.NO_CACHE_DIRECTIVE_WITH_FIELD_NAME;
                }
            }
        }
        return null;
    }
}
