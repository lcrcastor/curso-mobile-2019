package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okhttp3.internal.http.RequestLine;
import okhttp3.internal.http.StatusLine;
import okio.Buffer;
import okio.ByteString;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Http2Codec implements HttpCodec {
    private static final ByteString b = ByteString.encodeUtf8("connection");
    private static final ByteString c = ByteString.encodeUtf8("host");
    private static final ByteString d = ByteString.encodeUtf8("keep-alive");
    private static final ByteString e = ByteString.encodeUtf8("proxy-connection");
    private static final ByteString f = ByteString.encodeUtf8("transfer-encoding");
    private static final ByteString g = ByteString.encodeUtf8("te");
    private static final ByteString h = ByteString.encodeUtf8("encoding");
    private static final ByteString i = ByteString.encodeUtf8("upgrade");
    private static final List<ByteString> j = Util.immutableList((T[]) new ByteString[]{b, c, d, e, g, f, h, i, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY});
    private static final List<ByteString> k = Util.immutableList((T[]) new ByteString[]{b, c, d, e, g, f, h, i});
    final StreamAllocation a;
    private final Chain l;
    private final Http2Connection m;
    private Http2Stream n;
    private final Protocol o;

    class StreamFinishingSource extends ForwardingSource {
        boolean a = false;
        long b = 0;

        StreamFinishingSource(Source source) {
            super(source);
        }

        public long read(Buffer buffer, long j) {
            try {
                long read = delegate().read(buffer, j);
                if (read > 0) {
                    this.b += read;
                }
                return read;
            } catch (IOException e) {
                a(e);
                throw e;
            }
        }

        public void close() {
            super.close();
            a(null);
        }

        private void a(IOException iOException) {
            if (!this.a) {
                this.a = true;
                Http2Codec.this.a.streamFinished(false, Http2Codec.this, this.b, iOException);
            }
        }
    }

    public Http2Codec(OkHttpClient okHttpClient, Chain chain, StreamAllocation streamAllocation, Http2Connection http2Connection) {
        Protocol protocol;
        this.l = chain;
        this.a = streamAllocation;
        this.m = http2Connection;
        if (okHttpClient.protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            protocol = Protocol.H2_PRIOR_KNOWLEDGE;
        } else {
            protocol = Protocol.HTTP_2;
        }
        this.o = protocol;
    }

    public Sink createRequestBody(Request request, long j2) {
        return this.n.getSink();
    }

    public void writeRequestHeaders(Request request) {
        if (this.n == null) {
            this.n = this.m.newStream(http2HeadersList(request), request.body() != null);
            this.n.readTimeout().timeout((long) this.l.readTimeoutMillis(), TimeUnit.MILLISECONDS);
            this.n.writeTimeout().timeout((long) this.l.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public void flushRequest() {
        this.m.flush();
    }

    public void finishRequest() {
        this.n.getSink().close();
    }

    public Builder readResponseHeaders(boolean z) {
        Builder readHttp2HeadersList = readHttp2HeadersList(this.n.takeResponseHeaders(), this.o);
        if (!z || Internal.instance.code(readHttp2HeadersList) != 100) {
            return readHttp2HeadersList;
        }
        return null;
    }

    public static List<Header> http2HeadersList(Request request) {
        Headers headers = request.headers();
        ArrayList arrayList = new ArrayList(headers.size() + 4);
        arrayList.add(new Header(Header.TARGET_METHOD, request.method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
        String header = request.header("Host");
        if (header != null) {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, header));
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, request.url().scheme()));
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            ByteString encodeUtf8 = ByteString.encodeUtf8(headers.name(i2).toLowerCase(Locale.US));
            if (!j.contains(encodeUtf8)) {
                arrayList.add(new Header(encodeUtf8, headers.value(i2)));
            }
        }
        return arrayList;
    }

    public static Builder readHttp2HeadersList(List<Header> list, Protocol protocol) {
        Headers.Builder builder = new Headers.Builder();
        int size = list.size();
        Headers.Builder builder2 = builder;
        StatusLine statusLine = null;
        for (int i2 = 0; i2 < size; i2++) {
            Header header = (Header) list.get(i2);
            if (header != null) {
                ByteString byteString = header.name;
                String utf8 = header.value.utf8();
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("HTTP/1.1 ");
                    sb.append(utf8);
                    statusLine = StatusLine.parse(sb.toString());
                } else if (!k.contains(byteString)) {
                    Internal.instance.addLenient(builder2, byteString.utf8(), utf8);
                }
            } else if (statusLine != null && statusLine.code == 100) {
                builder2 = new Headers.Builder();
                statusLine = null;
            }
        }
        if (statusLine != null) {
            return new Builder().protocol(protocol).code(statusLine.code).message(statusLine.message).headers(builder2.build());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public ResponseBody openResponseBody(Response response) {
        this.a.eventListener.responseBodyStart(this.a.call);
        return new RealResponseBody(response.header("Content-Type"), HttpHeaders.contentLength(response), Okio.buffer((Source) new StreamFinishingSource(this.n.getSource())));
    }

    public void cancel() {
        if (this.n != null) {
            this.n.closeLater(ErrorCode.CANCEL);
        }
    }
}
