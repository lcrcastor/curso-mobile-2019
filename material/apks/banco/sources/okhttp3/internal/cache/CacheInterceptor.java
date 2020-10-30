package okhttp3.internal.cache;

import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheStrategy.Factory;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class CacheInterceptor implements Interceptor {
    final InternalCache a;

    public CacheInterceptor(InternalCache internalCache) {
        this.a = internalCache;
    }

    public Response intercept(Chain chain) {
        Response response = this.a != null ? this.a.get(chain.request()) : null;
        CacheStrategy cacheStrategy = new Factory(System.currentTimeMillis(), chain.request(), response).get();
        Request request = cacheStrategy.networkRequest;
        Response response2 = cacheStrategy.cacheResponse;
        if (this.a != null) {
            this.a.trackResponse(cacheStrategy);
        }
        if (response != null && response2 == null) {
            Util.closeQuietly((Closeable) response.body());
        }
        if (request == null && response2 == null) {
            return new Builder().request(chain.request()).protocol(Protocol.HTTP_1_1).code(HttpStatus.SC_GATEWAY_TIMEOUT).message("Unsatisfiable Request (only-if-cached)").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1).receivedResponseAtMillis(System.currentTimeMillis()).build();
        }
        if (request == null) {
            return response2.newBuilder().cacheResponse(a(response2)).build();
        }
        try {
            Response proceed = chain.proceed(request);
            if (proceed == null && response != null) {
            }
            if (response2 != null) {
                if (proceed.code() == 304) {
                    Response build = response2.newBuilder().headers(a(response2.headers(), proceed.headers())).sentRequestAtMillis(proceed.sentRequestAtMillis()).receivedResponseAtMillis(proceed.receivedResponseAtMillis()).cacheResponse(a(response2)).networkResponse(a(proceed)).build();
                    proceed.body().close();
                    this.a.trackConditionalCacheHit();
                    this.a.update(response2, build);
                    return build;
                }
                Util.closeQuietly((Closeable) response2.body());
            }
            Response build2 = proceed.newBuilder().cacheResponse(a(response2)).networkResponse(a(proceed)).build();
            if (this.a != null) {
                if (HttpHeaders.hasBody(build2) && CacheStrategy.isCacheable(build2, request)) {
                    return a(this.a.put(build2), build2);
                }
                if (HttpMethod.invalidatesCache(request.method())) {
                    try {
                        this.a.remove(request);
                    } catch (IOException unused) {
                    }
                }
            }
            return build2;
        } finally {
            if (response != null) {
                Util.closeQuietly((Closeable) response.body());
            }
        }
    }

    private static Response a(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body(null).build();
    }

    private Response a(final CacheRequest cacheRequest, Response response) {
        if (cacheRequest == null) {
            return response;
        }
        Sink body = cacheRequest.body();
        if (body == null) {
            return response;
        }
        final BufferedSource source = response.body().source();
        final BufferedSink buffer = Okio.buffer(body);
        AnonymousClass1 r2 = new Source() {
            boolean a;

            public long read(Buffer buffer, long j) {
                try {
                    long read = source.read(buffer, j);
                    if (read == -1) {
                        if (!this.a) {
                            this.a = true;
                            buffer.close();
                        }
                        return -1;
                    }
                    buffer.copyTo(buffer.buffer(), buffer.size() - read, read);
                    buffer.emitCompleteSegments();
                    return read;
                } catch (IOException e2) {
                    if (!this.a) {
                        this.a = true;
                        cacheRequest.abort();
                    }
                    throw e2;
                }
            }

            public Timeout timeout() {
                return source.timeout();
            }

            public void close() {
                if (!this.a && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.a = true;
                    cacheRequest.abort();
                }
                source.close();
            }
        };
        return response.newBuilder().body(new RealResponseBody(response.header("Content-Type"), response.body().contentLength(), Okio.buffer((Source) r2))).build();
    }

    private static Headers a(Headers headers, Headers headers2) {
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if ((!"Warning".equalsIgnoreCase(name) || !value.startsWith("1")) && (b(name) || !a(name) || headers2.get(name) == null)) {
                Internal.instance.addLenient(builder, name, value);
            }
        }
        int size2 = headers2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String name2 = headers2.name(i2);
            if (!b(name2) && a(name2)) {
                Internal.instance.addLenient(builder, name2, headers2.value(i2));
            }
        }
        return builder.build();
    }

    static boolean a(String str) {
        return !"Connection".equalsIgnoreCase(str) && !HTTP.CONN_KEEP_ALIVE.equalsIgnoreCase(str) && !"Proxy-Authenticate".equalsIgnoreCase(str) && !"Proxy-Authorization".equalsIgnoreCase(str) && !"TE".equalsIgnoreCase(str) && !"Trailers".equalsIgnoreCase(str) && !"Transfer-Encoding".equalsIgnoreCase(str) && !"Upgrade".equalsIgnoreCase(str);
    }

    static boolean b(String str) {
        return "Content-Length".equalsIgnoreCase(str) || "Content-Encoding".equalsIgnoreCase(str) || "Content-Type".equalsIgnoreCase(str);
    }
}
