package cz.msebera.android.httpclient.impl.io;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpMessage;
import cz.msebera.android.httpclient.MessageConstraintException;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.ProtocolException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.config.MessageConstraints;
import cz.msebera.android.httpclient.io.HttpMessageParser;
import cz.msebera.android.httpclient.io.SessionInputBuffer;
import cz.msebera.android.httpclient.message.BasicLineParser;
import cz.msebera.android.httpclient.message.LineParser;
import cz.msebera.android.httpclient.message.TokenParser;
import cz.msebera.android.httpclient.params.HttpParamConfig;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public abstract class AbstractMessageParser<T extends HttpMessage> implements HttpMessageParser<T> {
    private final SessionInputBuffer a;
    private final MessageConstraints b;
    private final List<CharArrayBuffer> c;
    private int d;
    private T e;
    protected final LineParser lineParser;

    public abstract T parseHead(SessionInputBuffer sessionInputBuffer);

    @Deprecated
    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser2, HttpParams httpParams) {
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(httpParams, "HTTP parameters");
        this.a = sessionInputBuffer;
        this.b = HttpParamConfig.getMessageConstraints(httpParams);
        if (lineParser2 == null) {
            lineParser2 = BasicLineParser.INSTANCE;
        }
        this.lineParser = lineParser2;
        this.c = new ArrayList();
        this.d = 0;
    }

    public AbstractMessageParser(SessionInputBuffer sessionInputBuffer, LineParser lineParser2, MessageConstraints messageConstraints) {
        this.a = (SessionInputBuffer) Args.notNull(sessionInputBuffer, "Session input buffer");
        if (lineParser2 == null) {
            lineParser2 = BasicLineParser.INSTANCE;
        }
        this.lineParser = lineParser2;
        if (messageConstraints == null) {
            messageConstraints = MessageConstraints.DEFAULT;
        }
        this.b = messageConstraints;
        this.c = new ArrayList();
        this.d = 0;
    }

    public static Header[] parseHeaders(SessionInputBuffer sessionInputBuffer, int i, int i2, LineParser lineParser2) {
        ArrayList arrayList = new ArrayList();
        if (lineParser2 == null) {
            lineParser2 = BasicLineParser.INSTANCE;
        }
        return parseHeaders(sessionInputBuffer, i, i2, lineParser2, arrayList);
    }

    public static Header[] parseHeaders(SessionInputBuffer sessionInputBuffer, int i, int i2, LineParser lineParser2, List<CharArrayBuffer> list) {
        int i3;
        Args.notNull(sessionInputBuffer, "Session input buffer");
        Args.notNull(lineParser2, "Line parser");
        Args.notNull(list, "Header line list");
        CharArrayBuffer charArrayBuffer = null;
        CharArrayBuffer charArrayBuffer2 = null;
        while (true) {
            if (charArrayBuffer == null) {
                charArrayBuffer = new CharArrayBuffer(64);
            } else {
                charArrayBuffer.clear();
            }
            i3 = 0;
            if (sessionInputBuffer.readLine(charArrayBuffer) == -1 || charArrayBuffer.length() < 1) {
                Header[] headerArr = new Header[list.size()];
            } else {
                if ((charArrayBuffer.charAt(0) == ' ' || charArrayBuffer.charAt(0) == 9) && charArrayBuffer2 != null) {
                    while (i3 < charArrayBuffer.length()) {
                        char charAt = charArrayBuffer.charAt(i3);
                        if (charAt != ' ' && charAt != 9) {
                            break;
                        }
                        i3++;
                    }
                    if (i2 <= 0 || ((charArrayBuffer2.length() + 1) + charArrayBuffer.length()) - i3 <= i2) {
                        charArrayBuffer2.append((char) TokenParser.SP);
                        charArrayBuffer2.append(charArrayBuffer, i3, charArrayBuffer.length() - i3);
                    } else {
                        throw new MessageConstraintException("Maximum line length limit exceeded");
                    }
                } else {
                    list.add(charArrayBuffer);
                    charArrayBuffer2 = charArrayBuffer;
                    charArrayBuffer = null;
                }
                if (i > 0 && list.size() >= i) {
                    throw new MessageConstraintException("Maximum header count exceeded");
                }
            }
        }
        Header[] headerArr2 = new Header[list.size()];
        while (i3 < list.size()) {
            try {
                headerArr2[i3] = lineParser2.parseHeader((CharArrayBuffer) list.get(i3));
                i3++;
            } catch (ParseException e2) {
                throw new ProtocolException(e2.getMessage());
            }
        }
        return headerArr2;
    }

    public T parse() {
        switch (this.d) {
            case 0:
                try {
                    this.e = parseHead(this.a);
                    this.d = 1;
                    break;
                } catch (ParseException e2) {
                    throw new ProtocolException(e2.getMessage(), e2);
                }
            case 1:
                break;
            default:
                throw new IllegalStateException("Inconsistent parser state");
        }
        this.e.setHeaders(parseHeaders(this.a, this.b.getMaxHeaderCount(), this.b.getMaxLineLength(), this.lineParser, this.c));
        T t = this.e;
        this.e = null;
        this.c.clear();
        this.d = 0;
        return t;
    }
}
