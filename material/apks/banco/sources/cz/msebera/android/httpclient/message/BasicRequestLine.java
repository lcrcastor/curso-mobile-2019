package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.RequestLine;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;

@Immutable
public class BasicRequestLine implements RequestLine, Serializable, Cloneable {
    private static final long serialVersionUID = 2810581718468737193L;
    private final ProtocolVersion a;
    private final String b;
    private final String c;

    public BasicRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        this.b = (String) Args.notNull(str, "Method");
        this.c = (String) Args.notNull(str2, "URI");
        this.a = (ProtocolVersion) Args.notNull(protocolVersion, "Version");
    }

    public String getMethod() {
        return this.b;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.a;
    }

    public String getUri() {
        return this.c;
    }

    public String toString() {
        return BasicLineFormatter.INSTANCE.formatRequestLine((CharArrayBuffer) null, (RequestLine) this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
