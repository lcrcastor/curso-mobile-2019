package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HttpCacheEntry;
import cz.msebera.android.httpclient.client.utils.URIUtils;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

@Immutable
class CacheKeyGenerator {
    private static final URI a = URI.create("http://example.com/");

    CacheKeyGenerator() {
    }

    public String a(HttpHost httpHost, HttpRequest httpRequest) {
        if (!a(httpRequest)) {
            return a(httpRequest.getRequestLine().getUri());
        }
        return a(String.format("%s%s", new Object[]{httpHost.toString(), httpRequest.getRequestLine().getUri()}));
    }

    public String a(String str) {
        try {
            URL url = new URL(URIUtils.resolve(a, str).toASCIIString());
            String protocol = url.getProtocol();
            String host = url.getHost();
            int a2 = a(url.getPort(), protocol);
            String path = url.getPath();
            String query = url.getQuery();
            if (query != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(path);
                sb.append("?");
                sb.append(query);
                path = sb.toString();
            }
            return new URL(protocol, host, a2, path).toString();
        } catch (IllegalArgumentException unused) {
            return str;
        } catch (MalformedURLException unused2) {
            return str;
        }
    }

    private int a(int i, String str) {
        if (i == -1 && HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(str)) {
            return 80;
        }
        if (i != -1 || !"https".equalsIgnoreCase(str)) {
            return i;
        }
        return 443;
    }

    private boolean a(HttpRequest httpRequest) {
        String uri = httpRequest.getRequestLine().getUri();
        return "*".equals(uri) || uri.startsWith("/");
    }

    /* access modifiers changed from: protected */
    public String a(Header[] headerArr) {
        if (headerArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        int length = headerArr.length;
        int i = 0;
        boolean z = true;
        while (i < length) {
            Header header = headerArr[i];
            if (!z) {
                sb.append(", ");
            }
            sb.append(header.getValue().trim());
            i++;
            z = false;
        }
        return sb.toString();
    }

    public String a(HttpHost httpHost, HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        if (!httpCacheEntry.hasVariants()) {
            return a(httpHost, httpRequest);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a(httpRequest, httpCacheEntry));
        sb.append(a(httpHost, httpRequest));
        return sb.toString();
    }

    public String a(HttpRequest httpRequest, HttpCacheEntry httpCacheEntry) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Header elements : httpCacheEntry.getHeaders("Vary")) {
            for (HeaderElement name : elements.getElements()) {
                arrayList.add(name.getName());
            }
        }
        Collections.sort(arrayList);
        try {
            StringBuilder sb = new StringBuilder("{");
            boolean z = true;
            for (String str : arrayList) {
                if (!z) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(str, Consts.UTF_8.name()));
                sb.append("=");
                sb.append(URLEncoder.encode(a(httpRequest.getHeaders(str)), Consts.UTF_8.name()));
                z = false;
            }
            sb.append("}");
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("couldn't encode to UTF-8", e);
        }
    }
}
