package cz.msebera.android.httpclient.protocol;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpResponseInterceptor;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import java.util.List;

@ThreadSafe
public final class ImmutableHttpProcessor implements HttpProcessor {
    private final HttpRequestInterceptor[] a;
    private final HttpResponseInterceptor[] b;

    public ImmutableHttpProcessor(HttpRequestInterceptor[] httpRequestInterceptorArr, HttpResponseInterceptor[] httpResponseInterceptorArr) {
        if (httpRequestInterceptorArr != null) {
            int length = httpRequestInterceptorArr.length;
            this.a = new HttpRequestInterceptor[length];
            System.arraycopy(httpRequestInterceptorArr, 0, this.a, 0, length);
        } else {
            this.a = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorArr != null) {
            int length2 = httpResponseInterceptorArr.length;
            this.b = new HttpResponseInterceptor[length2];
            System.arraycopy(httpResponseInterceptorArr, 0, this.b, 0, length2);
            return;
        }
        this.b = new HttpResponseInterceptor[0];
    }

    public ImmutableHttpProcessor(List<HttpRequestInterceptor> list, List<HttpResponseInterceptor> list2) {
        if (list != null) {
            this.a = (HttpRequestInterceptor[]) list.toArray(new HttpRequestInterceptor[list.size()]);
        } else {
            this.a = new HttpRequestInterceptor[0];
        }
        if (list2 != null) {
            this.b = (HttpResponseInterceptor[]) list2.toArray(new HttpResponseInterceptor[list2.size()]);
        } else {
            this.b = new HttpResponseInterceptor[0];
        }
    }

    @Deprecated
    public ImmutableHttpProcessor(HttpRequestInterceptorList httpRequestInterceptorList, HttpResponseInterceptorList httpResponseInterceptorList) {
        if (httpRequestInterceptorList != null) {
            int requestInterceptorCount = httpRequestInterceptorList.getRequestInterceptorCount();
            this.a = new HttpRequestInterceptor[requestInterceptorCount];
            for (int i = 0; i < requestInterceptorCount; i++) {
                this.a[i] = httpRequestInterceptorList.getRequestInterceptor(i);
            }
        } else {
            this.a = new HttpRequestInterceptor[0];
        }
        if (httpResponseInterceptorList != null) {
            int responseInterceptorCount = httpResponseInterceptorList.getResponseInterceptorCount();
            this.b = new HttpResponseInterceptor[responseInterceptorCount];
            for (int i2 = 0; i2 < responseInterceptorCount; i2++) {
                this.b[i2] = httpResponseInterceptorList.getResponseInterceptor(i2);
            }
            return;
        }
        this.b = new HttpResponseInterceptor[0];
    }

    public ImmutableHttpProcessor(HttpRequestInterceptor... httpRequestInterceptorArr) {
        this(httpRequestInterceptorArr, (HttpResponseInterceptor[]) null);
    }

    public ImmutableHttpProcessor(HttpResponseInterceptor... httpResponseInterceptorArr) {
        this((HttpRequestInterceptor[]) null, httpResponseInterceptorArr);
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) {
        for (HttpRequestInterceptor process : this.a) {
            process.process(httpRequest, httpContext);
        }
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) {
        for (HttpResponseInterceptor process : this.b) {
            process.process(httpResponse, httpContext);
        }
    }
}
