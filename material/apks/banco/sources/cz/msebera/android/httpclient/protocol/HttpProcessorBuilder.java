package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import java.util.LinkedList;
import java.util.List;

public class HttpProcessorBuilder {
    private ChainBuilder<HttpRequestInterceptor> a;
    private ChainBuilder<HttpResponseInterceptor> b;

    public static HttpProcessorBuilder create() {
        return new HttpProcessorBuilder();
    }

    HttpProcessorBuilder() {
    }

    private ChainBuilder<HttpRequestInterceptor> a() {
        if (this.a == null) {
            this.a = new ChainBuilder<>();
        }
        return this.a;
    }

    private ChainBuilder<HttpResponseInterceptor> b() {
        if (this.b == null) {
            this.b = new ChainBuilder<>();
        }
        return this.b;
    }

    public HttpProcessorBuilder addFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        a().a(httpRequestInterceptor);
        return this;
    }

    public HttpProcessorBuilder addLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        a().b(httpRequestInterceptor);
        return this;
    }

    public HttpProcessorBuilder add(HttpRequestInterceptor httpRequestInterceptor) {
        return addLast(httpRequestInterceptor);
    }

    public HttpProcessorBuilder addAllFirst(HttpRequestInterceptor... httpRequestInterceptorArr) {
        if (httpRequestInterceptorArr == null) {
            return this;
        }
        a().a((E[]) httpRequestInterceptorArr);
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpRequestInterceptor... httpRequestInterceptorArr) {
        if (httpRequestInterceptorArr == null) {
            return this;
        }
        a().b((E[]) httpRequestInterceptorArr);
        return this;
    }

    public HttpProcessorBuilder addAll(HttpRequestInterceptor... httpRequestInterceptorArr) {
        return addAllLast(httpRequestInterceptorArr);
    }

    public HttpProcessorBuilder addFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        b().a(httpResponseInterceptor);
        return this;
    }

    public HttpProcessorBuilder addLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        b().b(httpResponseInterceptor);
        return this;
    }

    public HttpProcessorBuilder add(HttpResponseInterceptor httpResponseInterceptor) {
        return addLast(httpResponseInterceptor);
    }

    public HttpProcessorBuilder addAllFirst(HttpResponseInterceptor... httpResponseInterceptorArr) {
        if (httpResponseInterceptorArr == null) {
            return this;
        }
        b().a((E[]) httpResponseInterceptorArr);
        return this;
    }

    public HttpProcessorBuilder addAllLast(HttpResponseInterceptor... httpResponseInterceptorArr) {
        if (httpResponseInterceptorArr == null) {
            return this;
        }
        b().b((E[]) httpResponseInterceptorArr);
        return this;
    }

    public HttpProcessorBuilder addAll(HttpResponseInterceptor... httpResponseInterceptorArr) {
        return addAllLast(httpResponseInterceptorArr);
    }

    public HttpProcessor build() {
        LinkedList linkedList = null;
        List a2 = this.a != null ? this.a.a() : null;
        if (this.b != null) {
            linkedList = this.b.a();
        }
        return new ImmutableHttpProcessor(a2, (List<HttpResponseInterceptor>) linkedList);
    }
}
