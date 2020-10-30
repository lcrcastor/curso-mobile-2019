package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpMethod;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

public final class Request {
    /* access modifiers changed from: private */
    public final HttpUrl a;
    /* access modifiers changed from: private */
    public final String b;
    /* access modifiers changed from: private */
    public final Headers c;
    /* access modifiers changed from: private */
    public final RequestBody d;
    /* access modifiers changed from: private */
    public final Object e;
    private volatile URL f;
    private volatile URI g;
    private volatile CacheControl h;

    public static class Builder {
        /* access modifiers changed from: private */
        public HttpUrl a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public com.squareup.okhttp.Headers.Builder c;
        /* access modifiers changed from: private */
        public RequestBody d;
        /* access modifiers changed from: private */
        public Object e;

        public Builder() {
            this.b = "GET";
            this.c = new com.squareup.okhttp.Headers.Builder();
        }

        private Builder(Request request) {
            this.a = request.a;
            this.b = request.b;
            this.d = request.d;
            this.e = request.e;
            this.c = request.c.newBuilder();
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl == null) {
                throw new IllegalArgumentException("url == null");
            }
            this.a = httpUrl;
            return this;
        }

        public Builder url(String str) {
            if (str == null) {
                throw new IllegalArgumentException("url == null");
            }
            if (str.regionMatches(true, 0, "ws:", 0, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("http:");
                sb.append(str.substring(3));
                str = sb.toString();
            } else {
                if (str.regionMatches(true, 0, "wss:", 0, 4)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("https:");
                    sb2.append(str.substring(4));
                    str = sb2.toString();
                }
            }
            HttpUrl parse = HttpUrl.parse(str);
            if (parse != null) {
                return url(parse);
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("unexpected url: ");
            sb3.append(str);
            throw new IllegalArgumentException(sb3.toString());
        }

        public Builder url(URL url) {
            if (url == null) {
                throw new IllegalArgumentException("url == null");
            }
            HttpUrl httpUrl = HttpUrl.get(url);
            if (httpUrl != null) {
                return url(httpUrl);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected url: ");
            sb.append(url);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder header(String str, String str2) {
            this.c.set(str, str2);
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.c.add(str, str2);
            return this;
        }

        public Builder removeHeader(String str) {
            this.c.removeAll(str);
            return this;
        }

        public Builder headers(Headers headers) {
            this.c = headers.newBuilder();
            return this;
        }

        public Builder cacheControl(CacheControl cacheControl) {
            String cacheControl2 = cacheControl.toString();
            if (cacheControl2.isEmpty()) {
                return removeHeader("Cache-Control");
            }
            return header("Cache-Control", cacheControl2);
        }

        public Builder get() {
            return method("GET", null);
        }

        public Builder head() {
            return method("HEAD", null);
        }

        public Builder post(RequestBody requestBody) {
            return method("POST", requestBody);
        }

        public Builder delete(RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder delete() {
            return delete(RequestBody.create((MediaType) null, new byte[0]));
        }

        public Builder put(RequestBody requestBody) {
            return method("PUT", requestBody);
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder method(String str, RequestBody requestBody) {
            if (str == null || str.length() == 0) {
                throw new IllegalArgumentException("method == null || method.length() == 0");
            } else if (requestBody != null && !HttpMethod.permitsRequestBody(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append("method ");
                sb.append(str);
                sb.append(" must not have a request body.");
                throw new IllegalArgumentException(sb.toString());
            } else if (requestBody != null || !HttpMethod.requiresRequestBody(str)) {
                this.b = str;
                this.d = requestBody;
                return this;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("method ");
                sb2.append(str);
                sb2.append(" must have a request body.");
                throw new IllegalArgumentException(sb2.toString());
            }
        }

        public Builder tag(Object obj) {
            this.e = obj;
            return this;
        }

        public Request build() {
            if (this.a != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    private Request(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c.build();
        this.d = builder.d;
        this.e = builder.e != null ? builder.e : this;
    }

    public HttpUrl httpUrl() {
        return this.a;
    }

    public URL url() {
        URL url = this.f;
        if (url != null) {
            return url;
        }
        URL url2 = this.a.url();
        this.f = url2;
        return url2;
    }

    public URI uri() {
        try {
            URI uri = this.g;
            if (uri != null) {
                return uri;
            }
            URI uri2 = this.a.uri();
            this.g = uri2;
            return uri2;
        } catch (IllegalStateException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public String urlString() {
        return this.a.toString();
    }

    public String method() {
        return this.b;
    }

    public Headers headers() {
        return this.c;
    }

    public String header(String str) {
        return this.c.get(str);
    }

    public List<String> headers(String str) {
        return this.c.values(str);
    }

    public RequestBody body() {
        return this.d;
    }

    public Object tag() {
        return this.e;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.h;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl parse = CacheControl.parse(this.c);
        this.h = parse;
        return parse;
    }

    public boolean isHttps() {
        return this.a.isHttps();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{method=");
        sb.append(this.b);
        sb.append(", url=");
        sb.append(this.a);
        sb.append(", tag=");
        sb.append(this.e != this ? this.e : null);
        sb.append('}');
        return sb.toString();
    }
}
