package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import org.bouncycastle.util.Arrays;

public final class SessionParameters {
    private int a;
    private short b;
    private byte[] c;
    private Certificate d;
    private byte[] e;

    public static final class Builder {
        private int a = -1;
        private short b = -1;
        private byte[] c = null;
        private Certificate d = null;
        private byte[] e = null;

        private void a(boolean z, String str) {
            if (!z) {
                StringBuilder sb = new StringBuilder();
                sb.append("Required session parameter '");
                sb.append(str);
                sb.append("' not configured");
                throw new IllegalStateException(sb.toString());
            }
        }

        public SessionParameters build() {
            boolean z = false;
            a(this.a >= 0, "cipherSuite");
            a(this.b >= 0, "compressionAlgorithm");
            if (this.c != null) {
                z = true;
            }
            a(z, "masterSecret");
            SessionParameters sessionParameters = new SessionParameters(this.a, this.b, this.c, this.d, this.e);
            return sessionParameters;
        }

        public Builder setCipherSuite(int i) {
            this.a = i;
            return this;
        }

        public Builder setCompressionAlgorithm(short s) {
            this.b = s;
            return this;
        }

        public Builder setMasterSecret(byte[] bArr) {
            this.c = bArr;
            return this;
        }

        public Builder setPeerCertificate(Certificate certificate) {
            this.d = certificate;
            return this;
        }

        public Builder setServerExtensions(Hashtable hashtable) {
            byte[] byteArray;
            if (hashtable == null) {
                byteArray = null;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TlsProtocol.writeExtensions(byteArrayOutputStream, hashtable);
                byteArray = byteArrayOutputStream.toByteArray();
            }
            this.e = byteArray;
            return this;
        }
    }

    private SessionParameters(int i, short s, byte[] bArr, Certificate certificate, byte[] bArr2) {
        this.a = i;
        this.b = s;
        this.c = Arrays.clone(bArr);
        this.d = certificate;
        this.e = bArr2;
    }

    public void clear() {
        if (this.c != null) {
            Arrays.fill(this.c, 0);
        }
    }

    public SessionParameters copy() {
        SessionParameters sessionParameters = new SessionParameters(this.a, this.b, this.c, this.d, this.e);
        return sessionParameters;
    }

    public int getCipherSuite() {
        return this.a;
    }

    public short getCompressionAlgorithm() {
        return this.b;
    }

    public byte[] getMasterSecret() {
        return this.c;
    }

    public Certificate getPeerCertificate() {
        return this.d;
    }

    public Hashtable readServerExtensions() {
        if (this.e == null) {
            return null;
        }
        return TlsProtocol.readExtensions(new ByteArrayInputStream(this.e));
    }
}
