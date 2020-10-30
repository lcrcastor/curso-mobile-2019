package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.OkHeaders;
import cz.msebera.android.httpclient.HttpStatus;
import java.util.Collections;
import java.util.List;

public final class Response {
    /* access modifiers changed from: private */
    public final Request a;
    /* access modifiers changed from: private */
    public final Protocol b;
    /* access modifiers changed from: private */
    public final int c;
    /* access modifiers changed from: private */
    public final String d;
    /* access modifiers changed from: private */
    public final Handshake e;
    /* access modifiers changed from: private */
    public final Headers f;
    /* access modifiers changed from: private */
    public final ResponseBody g;
    /* access modifiers changed from: private */
    public Response h;
    /* access modifiers changed from: private */
    public Response i;
    /* access modifiers changed from: private */
    public final Response j;
    private volatile CacheControl k;

    public static class Builder {
        /* access modifiers changed from: private */
        public Request a;
        /* access modifiers changed from: private */
        public Protocol b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public Handshake e;
        /* access modifiers changed from: private */
        public com.squareup.okhttp.Headers.Builder f;
        /* access modifiers changed from: private */
        public ResponseBody g;
        /* access modifiers changed from: private */
        public Response h;
        /* access modifiers changed from: private */
        public Response i;
        /* access modifiers changed from: private */
        public Response j;

        public Builder() {
            this.c = -1;
            this.f = new com.squareup.okhttp.Headers.Builder();
        }

        private Builder(Response response) {
            this.c = -1;
            this.a = response.a;
            this.b = response.b;
            this.c = response.c;
            this.d = response.d;
            this.e = response.e;
            this.f = response.f.newBuilder();
            this.g = response.g;
            this.h = response.h;
            this.i = response.i;
            this.j = response.j;
        }

        public Builder request(Request request) {
            this.a = request;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.b = protocol;
            return this;
        }

        public Builder code(int i2) {
            this.c = i2;
            return this;
        }

        public Builder message(String str) {
            this.d = str;
            return this;
        }

        public Builder handshake(Handshake handshake) {
            this.e = handshake;
            return this;
        }

        public Builder header(String str, String str2) {
            this.f.set(str, str2);
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.f.add(str, str2);
            return this;
        }

        public Builder removeHeader(String str) {
            this.f.removeAll(str);
            return this;
        }

        public Builder headers(Headers headers) {
            this.f = headers.newBuilder();
            return this;
        }

        public Builder body(ResponseBody responseBody) {
            this.g = responseBody;
            return this;
        }

        public Builder networkResponse(Response response) {
            if (response != null) {
                a("networkResponse", response);
            }
            this.h = response;
            return this;
        }

        public Builder cacheResponse(Response response) {
            if (response != null) {
                a("cacheResponse", response);
            }
            this.i = response;
            return this;
        }

        private void a(String str, Response response) {
            if (response.g != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(".body != null");
                throw new IllegalArgumentException(sb.toString());
            } else if (response.h != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(".networkResponse != null");
                throw new IllegalArgumentException(sb2.toString());
            } else if (response.i != null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(".cacheResponse != null");
                throw new IllegalArgumentException(sb3.toString());
            } else if (response.j != null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(".priorResponse != null");
                throw new IllegalArgumentException(sb4.toString());
            }
        }

        public Builder priorResponse(Response response) {
            if (response != null) {
                a(response);
            }
            this.j = response;
            return this;
        }

        private void a(Response response) {
            if (response.g != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        public Response build() {
            if (this.a == null) {
                throw new IllegalStateException("request == null");
            } else if (this.b == null) {
                throw new IllegalStateException("protocol == null");
            } else if (this.c >= 0) {
                return new Response(this);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("code < 0: ");
                sb.append(this.c);
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    private Response(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f.build();
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.j = builder.j;
    }

    public Request request() {
        return this.a;
    }

    public Protocol protocol() {
        return this.b;
    }

    public int code() {
        return this.c;
    }

    public boolean isSuccessful() {
        return this.c >= 200 && this.c < 300;
    }

    public String message() {
        return this.d;
    }

    public Handshake handshake() {
        return this.e;
    }

    public List<String> headers(String str) {
        return this.f.values(str);
    }

    public String header(String str) {
        return header(str, null);
    }

    public String header(String str, String str2) {
        String str3 = this.f.get(str);
        return str3 != null ? str3 : str2;
    }

    public Headers headers() {
        return this.f;
    }

    public ResponseBody body() {
        return this.g;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    public boolean isRedirect() {
        switch (this.c) {
            case HttpStatus.SC_MULTIPLE_CHOICES /*300*/:
            case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
            case HttpStatus.SC_MOVED_TEMPORARILY /*302*/:
            case HttpStatus.SC_SEE_OTHER /*303*/:
            case 307:
            case 308:
                return true;
            default:
                return false;
        }
    }

    public Response networkResponse() {
        return this.h;
    }

    public Response cacheResponse() {
        return this.i;
    }

    public Response priorResponse() {
        return this.j;
    }

    public List<Challenge> challenges() {
        String str;
        if (this.c == 401) {
            str = "WWW-Authenticate";
        } else if (this.c != 407) {
            return Collections.emptyList();
        } else {
            str = "Proxy-Authenticate";
        }
        return OkHeaders.parseChallenges(headers(), str);
    }

    public CacheControl cacheControl() {
        CacheControl cacheControl = this.k;
        if (cacheControl != null) {
            return cacheControl;
        }
        CacheControl parse = CacheControl.parse(this.f);
        this.k = parse;
        return parse;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Response{protocol=");
        sb.append(this.b);
        sb.append(", code=");
        sb.append(this.c);
        sb.append(", message=");
        sb.append(this.d);
        sb.append(", url=");
        sb.append(this.a.urlString());
        sb.append('}');
        return sb.toString();
    }
}
