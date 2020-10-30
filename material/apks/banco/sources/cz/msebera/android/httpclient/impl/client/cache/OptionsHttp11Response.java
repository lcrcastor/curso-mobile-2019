package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderIterator;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.message.AbstractHttpMessage;
import cz.msebera.android.httpclient.message.BasicStatusLine;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.params.HttpParams;
import java.util.Locale;

@Immutable
final class OptionsHttp11Response extends AbstractHttpMessage implements HttpResponse {
    private final StatusLine a = new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_NOT_IMPLEMENTED, "");
    private final ProtocolVersion b = HttpVersion.HTTP_1_1;

    public void addHeader(Header header) {
    }

    public void addHeader(String str, String str2) {
    }

    public HttpEntity getEntity() {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public void removeHeader(Header header) {
    }

    public void removeHeaders(String str) {
    }

    public void setEntity(HttpEntity httpEntity) {
    }

    public void setHeader(Header header) {
    }

    public void setHeader(String str, String str2) {
    }

    public void setHeaders(Header[] headerArr) {
    }

    public void setLocale(Locale locale) {
    }

    public void setParams(HttpParams httpParams) {
    }

    public void setReasonPhrase(String str) {
    }

    public void setStatusCode(int i) {
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i, String str) {
    }

    public void setStatusLine(StatusLine statusLine) {
    }

    OptionsHttp11Response() {
    }

    public StatusLine getStatusLine() {
        return this.a;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.b;
    }

    public boolean containsHeader(String str) {
        return this.headergroup.containsHeader(str);
    }

    public Header[] getHeaders(String str) {
        return this.headergroup.getHeaders(str);
    }

    public Header getFirstHeader(String str) {
        return this.headergroup.getFirstHeader(str);
    }

    public Header getLastHeader(String str) {
        return this.headergroup.getLastHeader(str);
    }

    public Header[] getAllHeaders() {
        return this.headergroup.getAllHeaders();
    }

    public HeaderIterator headerIterator() {
        return this.headergroup.iterator();
    }

    public HeaderIterator headerIterator(String str) {
        return this.headergroup.iterator(str);
    }

    public HttpParams getParams() {
        if (this.params == null) {
            this.params = new BasicHttpParams();
        }
        return this.params;
    }
}
