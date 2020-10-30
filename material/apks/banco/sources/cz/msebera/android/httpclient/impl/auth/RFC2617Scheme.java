package cz.msebera.android.httpclient.impl.auth;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.auth.ChallengeState;
import cz.msebera.android.httpclient.auth.params.AuthPNames;
import cz.msebera.android.httpclient.message.BasicHeaderValueParser;
import cz.msebera.android.httpclient.message.ParserCursor;
import cz.msebera.android.httpclient.util.CharArrayBuffer;
import cz.msebera.android.httpclient.util.CharsetUtils;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@NotThreadSafe
public abstract class RFC2617Scheme extends AuthSchemeBase implements Serializable {
    private static final long serialVersionUID = -2845454858205884623L;
    private final Map<String, String> a;
    private transient Charset b;

    @Deprecated
    public RFC2617Scheme(ChallengeState challengeState) {
        super(challengeState);
        this.a = new HashMap();
        this.b = Consts.ASCII;
    }

    public RFC2617Scheme(Charset charset) {
        this.a = new HashMap();
        if (charset == null) {
            charset = Consts.ASCII;
        }
        this.b = charset;
    }

    public RFC2617Scheme() {
        this(Consts.ASCII);
    }

    public Charset getCredentialsCharset() {
        return this.b != null ? this.b : Consts.ASCII;
    }

    /* access modifiers changed from: 0000 */
    public String a(HttpRequest httpRequest) {
        String str = (String) httpRequest.getParams().getParameter(AuthPNames.CREDENTIAL_CHARSET);
        return str == null ? getCredentialsCharset().name() : str;
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(CharArrayBuffer charArrayBuffer, int i, int i2) {
        HeaderElement[] parseElements = BasicHeaderValueParser.INSTANCE.parseElements(charArrayBuffer, new ParserCursor(i, charArrayBuffer.length()));
        this.a.clear();
        for (HeaderElement headerElement : parseElements) {
            this.a.put(headerElement.getName().toLowerCase(Locale.ROOT), headerElement.getValue());
        }
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParameters() {
        return this.a;
    }

    public String getParameter(String str) {
        if (str == null) {
            return null;
        }
        return (String) this.a.get(str.toLowerCase(Locale.ROOT));
    }

    public String getRealm() {
        return getParameter("realm");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeUTF(this.b.name());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.b = CharsetUtils.get(objectInputStream.readUTF());
        if (this.b == null) {
            this.b = Consts.ASCII;
        }
    }
}
