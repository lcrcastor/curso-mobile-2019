package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.methods.HttpRequestWrapper;
import cz.msebera.android.httpclient.client.utils.DateUtils;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.util.ArrayList;
import java.util.Date;

@Immutable
class ResponseProtocolCompliance {
    ResponseProtocolCompliance() {
    }

    public void a(HttpRequestWrapper httpRequestWrapper, HttpResponse httpResponse) {
        if (c((HttpRequest) httpRequestWrapper, httpResponse)) {
            a(httpResponse);
            httpResponse.setEntity(null);
        }
        b(httpRequestWrapper, httpResponse);
        c(httpRequestWrapper, httpResponse);
        a((HttpRequest) httpRequestWrapper, httpResponse);
        b((HttpRequest) httpRequestWrapper, httpResponse);
        d(httpResponse);
        e(httpResponse);
        c(httpResponse);
        b(httpResponse);
    }

    private void a(HttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            IOUtils.a(entity);
        }
    }

    private void b(HttpResponse httpResponse) {
        WarningValue[] a;
        Date parseDate = DateUtils.parseDate(httpResponse.getFirstHeader("Date").getValue());
        if (parseDate != null) {
            Header[] headers = httpResponse.getHeaders("Warning");
            if (headers != null && headers.length != 0) {
                ArrayList<Header> arrayList = new ArrayList<>();
                int length = headers.length;
                int i = 0;
                boolean z = false;
                while (i < length) {
                    boolean z2 = z;
                    for (WarningValue warningValue : WarningValue.a(headers[i])) {
                        Date j = warningValue.j();
                        if (j == null || j.equals(parseDate)) {
                            arrayList.add(new BasicHeader("Warning", warningValue.toString()));
                        } else {
                            z2 = true;
                        }
                    }
                    i++;
                    z = z2;
                }
                if (z) {
                    httpResponse.removeHeaders("Warning");
                    for (Header addHeader : arrayList) {
                        httpResponse.addHeader(addHeader);
                    }
                }
            }
        }
    }

    private void c(HttpResponse httpResponse) {
        HeaderElement[] elements;
        HttpResponse httpResponse2 = httpResponse;
        Header[] headers = httpResponse2.getHeaders("Content-Encoding");
        if (headers != null && headers.length != 0) {
            ArrayList<Header> arrayList = new ArrayList<>();
            int length = headers.length;
            int i = 0;
            boolean z = false;
            while (i < length) {
                Header header = headers[i];
                StringBuilder sb = new StringBuilder();
                boolean z2 = z;
                boolean z3 = true;
                for (HeaderElement headerElement : header.getElements()) {
                    if (HTTP.IDENTITY_CODING.equalsIgnoreCase(headerElement.getName())) {
                        z2 = true;
                    } else {
                        if (!z3) {
                            sb.append(",");
                        }
                        sb.append(headerElement.toString());
                        z3 = false;
                    }
                }
                String sb2 = sb.toString();
                if (!"".equals(sb2)) {
                    arrayList.add(new BasicHeader("Content-Encoding", sb2));
                }
                i++;
                z = z2;
            }
            if (z) {
                httpResponse2.removeHeaders("Content-Encoding");
                for (Header addHeader : arrayList) {
                    httpResponse2.addHeader(addHeader);
                }
            }
        }
    }

    private void d(HttpResponse httpResponse) {
        if (httpResponse.getFirstHeader("Date") == null) {
            httpResponse.addHeader("Date", DateUtils.formatDate(new Date()));
        }
    }

    private void a(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getFirstHeader("Range") == null && httpResponse.getStatusLine().getStatusCode() == 206) {
            a(httpResponse);
            throw new ClientProtocolException("partial content was returned for a request that did not ask for it");
        }
    }

    private void b(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("OPTIONS") && httpResponse.getStatusLine().getStatusCode() == 200 && httpResponse.getFirstHeader("Content-Length") == null) {
            httpResponse.addHeader("Content-Length", "0");
        }
    }

    private void e(HttpResponse httpResponse) {
        String[] strArr = {"Allow", "Content-Encoding", "Content-Language", "Content-Length", "Content-MD5", "Content-Range", "Content-Type", "Last-Modified"};
        if (httpResponse.getStatusLine().getStatusCode() == 304) {
            for (String removeHeaders : strArr) {
                httpResponse.removeHeaders(removeHeaders);
            }
        }
    }

    private boolean c(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "HEAD".equals(httpRequest.getRequestLine().getMethod()) || httpResponse.getStatusLine().getStatusCode() == 204 || httpResponse.getStatusLine().getStatusCode() == 205 || httpResponse.getStatusLine().getStatusCode() == 304;
    }

    private void b(HttpRequestWrapper httpRequestWrapper, HttpResponse httpResponse) {
        if (httpResponse.getStatusLine().getStatusCode() == 100) {
            HttpRequest original = httpRequestWrapper.getOriginal();
            if (!(original instanceof HttpEntityEnclosingRequest) || !((HttpEntityEnclosingRequest) original).expectContinue()) {
                a(httpResponse);
                throw new ClientProtocolException("The incoming request did not contain a 100-continue header, but the response was a Status 100, continue.");
            }
        }
    }

    private void c(HttpRequestWrapper httpRequestWrapper, HttpResponse httpResponse) {
        if (httpRequestWrapper.getOriginal().getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) < 0) {
            f(httpResponse);
        }
    }

    private void f(HttpResponse httpResponse) {
        httpResponse.removeHeaders("TE");
        httpResponse.removeHeaders("Transfer-Encoding");
    }
}
