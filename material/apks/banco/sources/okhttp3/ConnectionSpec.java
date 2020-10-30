package okhttp3;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

public final class ConnectionSpec {
    public static final ConnectionSpec CLEARTEXT = new Builder(false).build();
    public static final ConnectionSpec COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    public static final ConnectionSpec MODERN_TLS = new Builder(true).cipherSuites(f).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    public static final ConnectionSpec RESTRICTED_TLS = new Builder(true).cipherSuites(e).tlsVersions(TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
    private static final CipherSuite[] e = {CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256};
    private static final CipherSuite[] f = {CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
    final boolean a;
    final boolean b;
    @Nullable
    final String[] c;
    @Nullable
    final String[] d;

    public static final class Builder {
        boolean a;
        @Nullable
        String[] b;
        @Nullable
        String[] c;
        boolean d;

        Builder(boolean z) {
            this.a = z;
        }

        public Builder(ConnectionSpec connectionSpec) {
            this.a = connectionSpec.a;
            this.b = connectionSpec.c;
            this.c = connectionSpec.d;
            this.d = connectionSpec.b;
        }

        public Builder allEnabledCipherSuites() {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            this.b = null;
            return this;
        }

        public Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            String[] strArr = new String[cipherSuiteArr.length];
            for (int i = 0; i < cipherSuiteArr.length; i++) {
                strArr[i] = cipherSuiteArr[i].b;
            }
            return cipherSuites(strArr);
        }

        public Builder cipherSuites(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one cipher suite is required");
            } else {
                this.b = (String[]) strArr.clone();
                return this;
            }
        }

        public Builder allEnabledTlsVersions() {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            this.c = null;
            return this;
        }

        public Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            String[] strArr = new String[tlsVersionArr.length];
            for (int i = 0; i < tlsVersionArr.length; i++) {
                strArr[i] = tlsVersionArr[i].a;
            }
            return tlsVersions(strArr);
        }

        public Builder tlsVersions(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one TLS version is required");
            } else {
                this.c = (String[]) strArr.clone();
                return this;
            }
        }

        public Builder supportsTlsExtensions(boolean z) {
            if (!this.a) {
                throw new IllegalStateException("no TLS extensions for cleartext connections");
            }
            this.d = z;
            return this;
        }

        public ConnectionSpec build() {
            return new ConnectionSpec(this);
        }
    }

    ConnectionSpec(Builder builder) {
        this.a = builder.a;
        this.c = builder.b;
        this.d = builder.c;
        this.b = builder.d;
    }

    public boolean isTls() {
        return this.a;
    }

    @Nullable
    public List<CipherSuite> cipherSuites() {
        if (this.c != null) {
            return CipherSuite.a(this.c);
        }
        return null;
    }

    @Nullable
    public List<TlsVersion> tlsVersions() {
        if (this.d != null) {
            return TlsVersion.a(this.d);
        }
        return null;
    }

    public boolean supportsTlsExtensions() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(SSLSocket sSLSocket, boolean z) {
        ConnectionSpec b2 = b(sSLSocket, z);
        if (b2.d != null) {
            sSLSocket.setEnabledProtocols(b2.d);
        }
        if (b2.c != null) {
            sSLSocket.setEnabledCipherSuites(b2.c);
        }
    }

    private ConnectionSpec b(SSLSocket sSLSocket, boolean z) {
        String[] strArr;
        String[] strArr2;
        if (this.c != null) {
            strArr = Util.intersect(CipherSuite.a, sSLSocket.getEnabledCipherSuites(), this.c);
        } else {
            strArr = sSLSocket.getEnabledCipherSuites();
        }
        if (this.d != null) {
            strArr2 = Util.intersect(Util.NATURAL_ORDER, sSLSocket.getEnabledProtocols(), this.d);
        } else {
            strArr2 = sSLSocket.getEnabledProtocols();
        }
        String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
        int indexOf = Util.indexOf(CipherSuite.a, supportedCipherSuites, "TLS_FALLBACK_SCSV");
        if (z && indexOf != -1) {
            strArr = Util.concat(strArr, supportedCipherSuites[indexOf]);
        }
        return new Builder(this).cipherSuites(strArr).tlsVersions(strArr2).build();
    }

    public boolean isCompatible(SSLSocket sSLSocket) {
        if (!this.a) {
            return false;
        }
        if (this.d != null && !Util.nonEmptyIntersection(Util.NATURAL_ORDER, this.d, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        if (this.c == null || Util.nonEmptyIntersection(CipherSuite.a, this.c, sSLSocket.getEnabledCipherSuites())) {
            return true;
        }
        return false;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ConnectionSpec)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        ConnectionSpec connectionSpec = (ConnectionSpec) obj;
        if (this.a != connectionSpec.a) {
            return false;
        }
        return !this.a || (Arrays.equals(this.c, connectionSpec.c) && Arrays.equals(this.d, connectionSpec.d) && this.b == connectionSpec.b);
    }

    public int hashCode() {
        if (this.a) {
            return ((((527 + Arrays.hashCode(this.c)) * 31) + Arrays.hashCode(this.d)) * 31) + (this.b ^ true ? 1 : 0);
        }
        return 17;
    }

    public String toString() {
        if (!this.a) {
            return "ConnectionSpec()";
        }
        String obj = this.c != null ? cipherSuites().toString() : "[all enabled]";
        String obj2 = this.d != null ? tlsVersions().toString() : "[all enabled]";
        StringBuilder sb = new StringBuilder();
        sb.append("ConnectionSpec(cipherSuites=");
        sb.append(obj);
        sb.append(", tlsVersions=");
        sb.append(obj2);
        sb.append(", supportsTlsExtensions=");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
