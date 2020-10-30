package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;

@Immutable
public class BasicHeader implements Header, Serializable, Cloneable {
    private static final long serialVersionUID = -5427236326487562174L;
    private final String a;
    private final String b;

    public BasicHeader(String str, String str2) {
        this.a = (String) Args.notNull(str, "Name");
        this.b = str2;
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public String toString() {
        return BasicLineFormatter.INSTANCE.formatHeader((CharArrayBuffer) null, (Header) this).toString();
    }

    public HeaderElement[] getElements() {
        if (this.b != null) {
            return BasicHeaderValueParser.parseElements(this.b, (HeaderValueParser) null);
        }
        return new HeaderElement[0];
    }

    public Object clone() {
        return super.clone();
    }
}
