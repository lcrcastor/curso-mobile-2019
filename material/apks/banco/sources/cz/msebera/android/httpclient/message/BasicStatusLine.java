package cz.msebera.android.httpclient.message;

import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.StatusLine;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import java.io.Serializable;

@Immutable
public class BasicStatusLine implements StatusLine, Serializable, Cloneable {
    private static final long serialVersionUID = -2443303766890459269L;
    private final ProtocolVersion a;
    private final int b;
    private final String c;

    public BasicStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        this.a = (ProtocolVersion) Args.notNull(protocolVersion, "Version");
        this.b = Args.notNegative(i, "Status code");
        this.c = str;
    }

    public int getStatusCode() {
        return this.b;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.a;
    }

    public String getReasonPhrase() {
        return this.c;
    }

    public String toString() {
        return BasicLineFormatter.INSTANCE.formatStatusLine((CharArrayBuffer) null, (StatusLine) this).toString();
    }

    public Object clone() {
        return super.clone();
    }
}
