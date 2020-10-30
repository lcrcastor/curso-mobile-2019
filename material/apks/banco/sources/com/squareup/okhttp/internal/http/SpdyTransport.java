package com.squareup.okhttp.internal.http;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.Okio;
import okio.Sink;

public final class SpdyTransport implements Transport {
    private static final List<ByteString> a = Util.immutableList((T[]) new ByteString[]{ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding")});
    private static final List<ByteString> b = Util.immutableList((T[]) new ByteString[]{ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade")});
    private final HttpEngine c;
    private final SpdyConnection d;
    private SpdyStream e;

    public boolean canReuseConnection() {
        return true;
    }

    public void releaseConnectionOnIdle() {
    }

    public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
        this.c = httpEngine;
        this.d = spdyConnection;
    }

    public Sink createRequestBody(Request request, long j) {
        return this.e.getSink();
    }

    public void writeRequestHeaders(Request request) {
        if (this.e == null) {
            this.c.writingRequestHeaders();
            this.e = this.d.newStream(writeNameValueBlock(request, this.d.getProtocol(), RequestLine.version(this.c.getConnection().getProtocol())), this.c.a(), true);
            this.e.readTimeout().timeout((long) this.c.a.getReadTimeout(), TimeUnit.MILLISECONDS);
        }
    }

    public void writeRequestBody(RetryableSink retryableSink) {
        retryableSink.writeToSocket(this.e.getSink());
    }

    public void finishRequest() {
        this.e.getSink().close();
    }

    public Builder readResponseHeaders() {
        return readNameValueBlock(this.e.getResponseHeaders(), this.d.getProtocol());
    }

    public static List<Header> writeNameValueBlock(Request request, Protocol protocol, String str) {
        Headers headers = request.headers();
        ArrayList arrayList = new ArrayList(headers.size() + 10);
        arrayList.add(new Header(Header.TARGET_METHOD, request.method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
        String hostHeader = HttpEngine.hostHeader(request.url());
        if (Protocol.SPDY_3 == protocol) {
            arrayList.add(new Header(Header.VERSION, str));
            arrayList.add(new Header(Header.TARGET_HOST, hostHeader));
        } else if (Protocol.HTTP_2 == protocol) {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, hostHeader));
        } else {
            throw new AssertionError();
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, request.url().getProtocol()));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            ByteString encodeUtf8 = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
            String value = headers.value(i);
            if (!a(protocol, encodeUtf8) && !encodeUtf8.equals(Header.TARGET_METHOD) && !encodeUtf8.equals(Header.TARGET_PATH) && !encodeUtf8.equals(Header.TARGET_SCHEME) && !encodeUtf8.equals(Header.TARGET_AUTHORITY) && !encodeUtf8.equals(Header.TARGET_HOST) && !encodeUtf8.equals(Header.VERSION)) {
                if (linkedHashSet.add(encodeUtf8)) {
                    arrayList.add(new Header(encodeUtf8, value));
                } else {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= arrayList.size()) {
                            break;
                        } else if (((Header) arrayList.get(i2)).name.equals(encodeUtf8)) {
                            arrayList.set(i2, new Header(encodeUtf8, a(((Header) arrayList.get(i2)).value.utf8(), value)));
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(0);
        sb.append(str2);
        return sb.toString();
    }

    public static Builder readNameValueBlock(List<Header> list, Protocol protocol) {
        Headers.Builder builder = new Headers.Builder();
        builder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
        int size = list.size();
        String str = null;
        String str2 = "HTTP/1.1";
        int i = 0;
        while (i < size) {
            ByteString byteString = ((Header) list.get(i)).name;
            String utf8 = ((Header) list.get(i)).value.utf8();
            String str3 = str2;
            String str4 = str;
            int i2 = 0;
            while (i2 < utf8.length()) {
                int indexOf = utf8.indexOf(0, i2);
                if (indexOf == -1) {
                    indexOf = utf8.length();
                }
                String substring = utf8.substring(i2, indexOf);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    str4 = substring;
                } else if (byteString.equals(Header.VERSION)) {
                    str3 = substring;
                } else if (!a(protocol, byteString)) {
                    builder.add(byteString.utf8(), substring);
                }
                i2 = indexOf + 1;
            }
            i++;
            str = str4;
            str2 = str3;
        }
        if (str == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(str);
        StatusLine parse = StatusLine.parse(sb.toString());
        return new Builder().protocol(protocol).code(parse.code).message(parse.message).headers(builder.build());
    }

    public ResponseBody openResponseBody(Response response) {
        return new RealResponseBody(response.headers(), Okio.buffer(this.e.getSource()));
    }

    public void disconnect(HttpEngine httpEngine) {
        if (this.e != null) {
            this.e.close(ErrorCode.CANCEL);
        }
    }

    private static boolean a(Protocol protocol, ByteString byteString) {
        if (protocol == Protocol.SPDY_3) {
            return a.contains(byteString);
        }
        if (protocol == Protocol.HTTP_2) {
            return b.contains(byteString);
        }
        throw new AssertionError(protocol);
    }
}
