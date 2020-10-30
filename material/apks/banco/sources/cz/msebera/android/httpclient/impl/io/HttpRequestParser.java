package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.ConnectionClosedException;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpRequestFactory;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@NotThreadSafe
@Deprecated
public class HttpRequestParser extends AbstractMessageParser<HttpMessage> {
    private final HttpRequestFactory a;
    private final CharArrayBuffer b = new CharArrayBuffer(128);

    public HttpRequestParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpRequestFactory httpRequestFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.a = (HttpRequestFactory) Args.notNull(httpRequestFactory, "Request factory");
    }

    /* access modifiers changed from: protected */
    public HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) {
        this.b.clear();
        if (sessionInputBuffer.readLine(this.b) == -1) {
            throw new ConnectionClosedException("Client closed connection");
        }
        return this.a.newHttpRequest(this.lineParser.parseRequestLine(this.b, new ParserCursor(0, this.b.length())));
    }
}
