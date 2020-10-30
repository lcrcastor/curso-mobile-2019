package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import cz.msebera.android.httpclient.protocol.HTTP;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class HttpTransport implements Transport {
    private final HttpEngine a;
    private final HttpConnection b;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.a = httpEngine;
        this.b = httpConnection;
    }

    public Sink createRequestBody(Request request, long j) {
        if (HTTP.CHUNK_CODING.equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            return this.b.newChunkedSink();
        }
        if (j != -1) {
            return this.b.newFixedLengthSink(j);
        }
        throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
    }

    public void finishRequest() {
        this.b.flush();
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        this.b.writeRequestBody(retryableSink);
    }

    public void writeRequestHeaders(Request request) {
        this.a.writingRequestHeaders();
        this.b.writeRequest(request.headers(), RequestLine.a(request, this.a.getConnection().getRoute().getProxy().type(), this.a.getConnection().getProtocol()));
    }

    public Builder readResponseHeaders() {
        return this.b.readResponse();
    }

    public void releaseConnectionOnIdle() {
        if (canReuseConnection()) {
            this.b.poolOnIdle();
        } else {
            this.b.closeOnIdle();
        }
    }

    public boolean canReuseConnection() {
        if (!"close".equalsIgnoreCase(this.a.getRequest().header("Connection")) && !"close".equalsIgnoreCase(this.a.getResponse().header("Connection")) && !this.b.isClosed()) {
            return true;
        }
        return false;
    }

    public ResponseBody openResponseBody(Response response) {
        return new RealResponseBody(response.headers(), Okio.buffer(a(response)));
    }

    private Source a(Response response) {
        if (!HttpEngine.hasBody(response)) {
            return this.b.newFixedLengthSource(0);
        }
        if (HTTP.CHUNK_CODING.equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return this.b.newChunkedSource(this.a);
        }
        long contentLength = OkHeaders.contentLength(response);
        if (contentLength != -1) {
            return this.b.newFixedLengthSource(contentLength);
        }
        return this.b.newUnknownLengthSource();
    }

    public void disconnect(HttpEngine httpEngine) {
        this.b.closeIfOwnedBy(httpEngine);
    }
}
