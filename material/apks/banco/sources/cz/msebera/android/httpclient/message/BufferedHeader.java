package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.FormattedHeader;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;

@NotThreadSafe
public class BufferedHeader implements FormattedHeader, Serializable, Cloneable {
    private static final long serialVersionUID = -2768352615787625448L;
    private final String a;
    private final CharArrayBuffer b;
    private final int c;

    public BufferedHeader(CharArrayBuffer charArrayBuffer) {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int indexOf = charArrayBuffer.indexOf(58);
        if (indexOf == -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid header: ");
            sb.append(charArrayBuffer.toString());
            throw new ParseException(sb.toString());
        }
        String substringTrimmed = charArrayBuffer.substringTrimmed(0, indexOf);
        if (substringTrimmed.length() == 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Invalid header: ");
            sb2.append(charArrayBuffer.toString());
            throw new ParseException(sb2.toString());
        }
        this.b = charArrayBuffer;
        this.a = substringTrimmed;
        this.c = indexOf + 1;
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b.substringTrimmed(this.c, this.b.length());
    }

    public HeaderElement[] getElements() {
        ParserCursor parserCursor = new ParserCursor(0, this.b.length());
        parserCursor.updatePos(this.c);
        return BasicHeaderValueParser.INSTANCE.parseElements(this.b, parserCursor);
    }

    public int getValuePos() {
        return this.c;
    }

    public CharArrayBuffer getBuffer() {
        return this.b;
    }

    public String toString() {
        return this.b.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
