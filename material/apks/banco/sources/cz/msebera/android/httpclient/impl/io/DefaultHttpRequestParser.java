package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.ConnectionClosedException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestFactory;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.config.MessageConstraints;
import cz.msebera.android.httpclient.impl.DefaultHttpRequestFactory;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@NotThreadSafe
public class DefaultHttpRequestParser extends AbstractMessageParser<HttpRequest> {
    private final HttpRequestFactory a;
    private final CharArrayBuffer b;

    @Deprecated
    public DefaultHttpRequestParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpRequestFactory httpRequestFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        this.a = (HttpRequestFactory) Args.notNull(httpRequestFactory, "Request factory");
        this.b = new CharArrayBuffer(128);
    }

    public DefaultHttpRequestParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpRequestFactory httpRequestFactory, MessageConstraints messageConstraints) {
        super(sessionInputBuffer, lineParser, messageConstraints);
        if (httpRequestFactory == null) {
            httpRequestFactory = DefaultHttpRequestFactory.INSTANCE;
        }
        this.a = httpRequestFactory;
        this.b = new CharArrayBuffer(128);
    }

    public DefaultHttpRequestParser(SessionInputBuffer sessionInputBuffer, MessageConstraints messageConstraints) {
        this(sessionInputBuffer, (LineParser) null, (HttpRequestFactory) null, messageConstraints);
    }

    public DefaultHttpRequestParser(SessionInputBuffer sessionInputBuffer) {
        this(sessionInputBuffer, (LineParser) null, (HttpRequestFactory) null, MessageConstraints.DEFAULT);
    }

    /* access modifiers changed from: protected */
    public HttpRequest parseHead(SessionInputBuffer sessionInputBuffer) {
        this.b.clear();
        if (sessionInputBuffer.readLine(this.b) == -1) {
            throw new ConnectionClosedException("Client closed connection");
        }
        return this.a.newHttpRequest(this.lineParser.parseRequestLine(this.b, new ParserCursor(0, this.b.length())));
    }
}
