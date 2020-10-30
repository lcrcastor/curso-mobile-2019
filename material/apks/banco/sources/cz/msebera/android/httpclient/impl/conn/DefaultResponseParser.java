package cz.msebera.android.httpclient.impl.conn;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.HttpResponseFactory;
import cz.msebera.android.httpclient.NoHttpResponseException;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.conn.params.ConnConnectionPNames;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.io.AbstractMessageParser;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;

@ThreadSafe
@Deprecated
public class DefaultResponseParser extends AbstractMessageParser<HttpMessage> {
    private final HttpResponseFactory a;
    private final CharArrayBuffer b;
    private final int c;
    public HttpClientAndroidLog log = new HttpClientAndroidLog(getClass());

    public DefaultResponseParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser, HttpResponseFactory httpResponseFactory, HttpParams httpParams) {
        super(sessionInputBuffer, lineParser, httpParams);
        Args.notNull(httpResponseFactory, "Response factory");
        this.a = httpResponseFactory;
        this.b = new CharArrayBuffer(128);
        this.c = getMaxGarbageLines(httpParams);
    }

    /* access modifiers changed from: protected */
    public int getMaxGarbageLines(HttpParams httpParams) {
        return httpParams.getIntParameter(ConnConnectionPNames.MAX_STATUS_LINE_GARBAGE, SubsamplingScaleImageView.TILE_SIZE_AUTO);
    }

    /* access modifiers changed from: protected */
    public HttpMessage parseHead(SessionInputBuffer sessionInputBuffer) {
        int i = 0;
        while (true) {
            this.b.clear();
            int readLine = sessionInputBuffer.readLine(this.b);
            if (readLine == -1 && i == 0) {
                throw new NoHttpResponseException("The target server failed to respond");
            }
            ParserCursor parserCursor = new ParserCursor(0, this.b.length());
            if (this.lineParser.hasProtocolVersion(this.b, parserCursor)) {
                return this.a.newHttpResponse(this.lineParser.parseStatusLine(this.b, parserCursor), null);
            } else if (readLine != -1 && i < this.c) {
                if (this.log.isDebugEnabled()) {
                    HttpClientAndroidLog httpClientAndroidLog = this.log;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Garbage in response: ");
                    sb.append(this.b.toString());
                    httpClientAndroidLog.debug(sb.toString());
                }
                i++;
            }
        }
        throw new ProtocolException("The server failed to respond with a valid HTTP response");
    }
}
