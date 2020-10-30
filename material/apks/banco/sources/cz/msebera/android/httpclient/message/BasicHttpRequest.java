package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;

@NotThreadSafe
public class BasicHttpRequest extends AbstractHttpMessage implements HttpRequest {
    private final String a;
    private final String b;
    private RequestLine c;

    public BasicHttpRequest(String str, String str2) {
        this.a = (String) Args.notNull(str, "Method name");
        this.b = (String) Args.notNull(str2, "Request URI");
        this.c = null;
    }

    public BasicHttpRequest(String str, String str2, ProtocolVersion protocolVersion) {
        this(new BasicRequestLine(str, str2, protocolVersion));
    }

    public BasicHttpRequest(RequestLine requestLine) {
        this.c = (RequestLine) Args.notNull(requestLine, "Request line");
        this.a = requestLine.getMethod();
        this.b = requestLine.getUri();
    }

    public ProtocolVersion getProtocolVersion() {
        return getRequestLine().getProtocolVersion();
    }

    public RequestLine getRequestLine() {
        if (this.c == null) {
            this.c = new BasicRequestLine(this.a, this.b, HttpVersion.HTTP_1_1);
        }
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(TokenParser.SP);
        sb.append(this.b);
        sb.append(TokenParser.SP);
        sb.append(this.headergroup);
        return sb.toString();
    }
}
