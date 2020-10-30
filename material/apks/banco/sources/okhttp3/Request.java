package okhttp3;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpMethod;

public final class Request {
    final HttpUrl a;
    final String b;
    final Headers c;
    @Nullable
    final RequestBody d;
    final Map<Class<?>, Object> e;
    private volatile CacheControl f;

    public static class Builder {
        HttpUrl a;
        String b;
        okhttp3.Headers.Builder c;
        RequestBody d;
        Map<Class<?>, Object> e;

        public Builder() {
            this.e = Collections.emptyMap();
            this.b = "GET";
            this.c = new okhttp3.Headers.Builder();
        }

        Builder(Request request) {
            Map<Class<?>, Object> map;
            this.e = Collections.emptyMap();
            this.a = request.a;
            this.b = request.b;
            this.d = request.d;
            if (request.e.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = new LinkedHashMap<>(request.e);
            }
            this.e = map;
            this.c = request.c.newBuilder();
        }

        public Builder url(HttpUrl httpUrl) {
            if (httpUrl == null) {
                throw new NullPointerException("url == null");
            }
            this.a = httpUrl;
            return this;
        }

        public Builder url(String str) {
            if (str == null) {
                throw new NullPointerException("url == null");
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
            return url(HttpUrl.get(str));
        }

        public Builder url(URL url) {
            if (url != null) {
                return url(HttpUrl.get(url.toString()));
            }
            throw new NullPointerException("url == null");
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

        public Builder delete(@Nullable RequestBody requestBody) {
            return method("DELETE", requestBody);
        }

        public Builder delete() {
            return delete(Util.EMPTY_REQUEST);
        }

        public Builder put(RequestBody requestBody) {
            return method("PUT", requestBody);
        }

        public Builder patch(RequestBody requestBody) {
            return method("PATCH", requestBody);
        }

        public Builder method(String str, @Nullable RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("method == null");
            } else if (str.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
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

        public Builder tag(@Nullable Object obj) {
            return tag(Object.class, obj);
        }

        public <T> Builder tag(Class<? super T> cls, @Nullable T t) {
            if (cls == null) {
                throw new NullPointerException("type == null");
            }
            if (t == null) {
                this.e.remove(cls);
            } else {
                if (this.e.isEmpty()) {
                    this.e = new LinkedHashMap();
                }
                this.e.put(cls, cls.cast(t));
            }
            return this;
        }

        public Request build() {
            if (this.a != null) {
                return new Request(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    Request(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c.build();
        this.d = builder.d;
        this.e = Util.immutableMap(builder.e);
    }

    public HttpUrl url() {
        return this.a;
    }

    public String method() {
        return this.b;
    }

    public Headers headers() {
        return this.c;
    }

    @Nullable
    public String header(String str) {
        return this.c.get(str);
    }

    public List<String> headers(String str) {
        return this.c.values(str);
    }

    @Nullable
    public RequestBody body() {
        return this.d;
    }

    @Nullable
    public Object tag() {
        return tag(Object.class);
    }

    @Nullable
    public <T> T tag(Class<? extends T> cls) {
        return cls.cast(this.e.get(cls));
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.f;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl parse = CacheControl.parse(this.c);
        this.f = parse;
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
        sb.append(", tags=");
        sb.append(this.e);
        sb.append('}');
        return sb.toString();
    }
}
