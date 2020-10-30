package cz.msebera.android.httpclient.config;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;

@Immutable
public class ConnectionConfig implements Cloneable {
    public static final ConnectionConfig DEFAULT = new Builder().build();
    private final int a;
    private final int b;
    private final Charset c;
    private final CodingErrorAction d;
    private final CodingErrorAction e;
    private final MessageConstraints f;

    public static class Builder {
        private int a;
        private int b = -1;
        private Charset c;
        private CodingErrorAction d;
        private CodingErrorAction e;
        private MessageConstraints f;

        Builder() {
        }

        public Builder setBufferSize(int i) {
            this.a = i;
            return this;
        }

        public Builder setFragmentSizeHint(int i) {
            this.b = i;
            return this;
        }

        public Builder setCharset(Charset charset) {
            this.c = charset;
            return this;
        }

        public Builder setMalformedInputAction(CodingErrorAction codingErrorAction) {
            this.d = codingErrorAction;
            if (codingErrorAction != null && this.c == null) {
                this.c = Consts.ASCII;
            }
            return this;
        }

        public Builder setUnmappableInputAction(CodingErrorAction codingErrorAction) {
            this.e = codingErrorAction;
            if (codingErrorAction != null && this.c == null) {
                this.c = Consts.ASCII;
            }
            return this;
        }

        public Builder setMessageConstraints(MessageConstraints messageConstraints) {
            this.f = messageConstraints;
            return this;
        }

        public ConnectionConfig build() {
            Charset charset = this.c;
            if (charset == null && !(this.d == null && this.e == null)) {
                charset = Consts.ASCII;
            }
            Charset charset2 = charset;
            int i = this.a > 0 ? this.a : 8192;
            ConnectionConfig connectionConfig = new ConnectionConfig(i, this.b >= 0 ? this.b : i, charset2, this.d, this.e, this.f);
            return connectionConfig;
        }
    }

    ConnectionConfig(int i, int i2, Charset charset, CodingErrorAction codingErrorAction, CodingErrorAction codingErrorAction2, MessageConstraints messageConstraints) {
        this.a = i;
        this.b = i2;
        this.c = charset;
        this.d = codingErrorAction;
        this.e = codingErrorAction2;
        this.f = messageConstraints;
    }

    public int getBufferSize() {
        return this.a;
    }

    public int getFragmentSizeHint() {
        return this.b;
    }

    public Charset getCharset() {
        return this.c;
    }

    public CodingErrorAction getMalformedInputAction() {
        return this.d;
    }

    public CodingErrorAction getUnmappableInputAction() {
        return this.e;
    }

    public MessageConstraints getMessageConstraints() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    public ConnectionConfig clone() {
        return (ConnectionConfig) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[bufferSize=");
        sb.append(this.a);
        sb.append(", fragmentSizeHint=");
        sb.append(this.b);
        sb.append(", charset=");
        sb.append(this.c);
        sb.append(", malformedInputAction=");
        sb.append(this.d);
        sb.append(", unmappableInputAction=");
        sb.append(this.e);
        sb.append(", messageConstraints=");
        sb.append(this.f);
        sb.append("]");
        return sb.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(ConnectionConfig connectionConfig) {
        Args.notNull(connectionConfig, "Connection config");
        return new Builder().setCharset(connectionConfig.getCharset()).setMalformedInputAction(connectionConfig.getMalformedInputAction()).setUnmappableInputAction(connectionConfig.getUnmappableInputAction()).setMessageConstraints(connectionConfig.getMessageConstraints());
    }
}
