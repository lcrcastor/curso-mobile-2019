package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import okio.BufferedSource;

public final class RealResponseBody extends ResponseBody {
    private final Headers a;
    private final BufferedSource b;

    public RealResponseBody(Headers headers, BufferedSource bufferedSource) {
        this.a = headers;
        this.b = bufferedSource;
    }

    public MediaType contentType() {
        String str = this.a.get("Content-Type");
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    public long contentLength() {
        return OkHeaders.contentLength(this.a);
    }

    public BufferedSource source() {
        return this.b;
    }
}
