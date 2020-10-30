package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@NotThreadSafe
@Deprecated
public class HttpResponseParser extends AbstractMessageParser<HttpMessage> {
    private final HttpResponseFactory a;
    private final CharArrayBuffer b = new CharArrayBuffer(128);

    public HttpResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.a = (HttpResponseFactory) Args.notNull(httpResponseFactory, "Response factory");
    }

    /* access modifiers changed from: protected */
    public HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) {
        this.b.clear();
        if (sessionInputBuffer.readLine(this.b) == -1) {
            throw new NoHttpResponseException("The target server failed to respond");
        }
        return this.a.newHttpResponse(this.lineParser.parseStatusLine(this.b, new ParserCursor(0, this.b.length())), null);
    }
}
