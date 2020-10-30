package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

public final class ConnectionSpec {
    public static final ConnectionSpec CLEARTEXT = new Builder(false).build();
    public static final ConnectionSpec COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    public static final ConnectionSpec MODERN_TLS = new Builder(true).cipherSuites(c).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    private static final CipherSuite[] c = {CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
    final boolean a;
    final boolean b;
    /* access modifiers changed from: private */
    public final String[] d;
    /* access modifiers changed from: private */
    public final String[] e;

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public String[] b;
        /* access modifiers changed from: private */
        public String[] c;
        /* access modifiers changed from: private */
        public boolean d;

        Builder(boolean z) {
            this.a = z;
        }

        public Builder(ConnectionSpec connectionSpec) {
            this.a = connectionSpec.a;
            this.b = connectionSpec.d;
            this.c = connectionSpec.e;
            this.d = connectionSpec.b;
        }

        public Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            String[] strArr = new String[cipherSuiteArr.length];
            for (int i = 0; i < cipherSuiteArr.length; i++) {
                strArr[i] = cipherSuiteArr[i].a;
            }
            this.b = strArr;
            return this;
        }

        public Builder cipherSuites(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            }
            if (strArr == null) {
                this.b = null;
            } else {
                this.b = (String[]) strArr.clone();
            }
            return this;
        }

        public Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (tlsVersionArr.length == 0) {
                throw new IllegalArgumentException("At least one TlsVersion is required");
            } else {
                String[] strArr = new String[tlsVersionArr.length];
                for (int i = 0; i < tlsVersionArr.length; i++) {
                    strArr[i] = tlsVersionArr[i].a;
                }
                this.c = strArr;
                return this;
            }
        }

        public Builder tlsVersions(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            }
            if (strArr == null) {
                this.c = null;
            } else {
                this.c = (String[]) strArr.clone();
            }
            return this;
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

    private ConnectionSpec(Builder builder) {
        this.a = builder.a;
        this.d = builder.b;
        this.e = builder.c;
        this.b = builder.d;
    }

    public boolean isTls() {
        return this.a;
    }

    public List<CipherSuite> cipherSuites() {
        if (this.d == null) {
            return null;
        }
        CipherSuite[] cipherSuiteArr = new CipherSuite[this.d.length];
        for (int i = 0; i < this.d.length; i++) {
            cipherSuiteArr[i] = CipherSuite.forJavaName(this.d[i]);
        }
        return Util.immutableList((T[]) cipherSuiteArr);
    }

    public List<TlsVersion> tlsVersions() {
        TlsVersion[] tlsVersionArr = new TlsVersion[this.e.length];
        for (int i = 0; i < this.e.length; i++) {
            tlsVersionArr[i] = TlsVersion.forJavaName(this.e[i]);
        }
        return Util.immutableList((T[]) tlsVersionArr);
    }

    public boolean supportsTlsExtensions() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(SSLSocket sSLSocket, boolean z) {
        ConnectionSpec b2 = b(sSLSocket, z);
        sSLSocket.setEnabledProtocols(b2.e);
        String[] strArr = b2.d;
        if (strArr != null) {
            sSLSocket.setEnabledCipherSuites(strArr);
        }
    }

    private ConnectionSpec b(SSLSocket sSLSocket, boolean z) {
        String[] strArr;
        String[] strArr2;
        if (this.d != null) {
            strArr = (String[]) Util.intersect(String.class, this.d, sSLSocket.getEnabledCipherSuites());
        } else {
            strArr = null;
        }
        if (!z || !Arrays.asList(sSLSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV")) {
            strArr2 = strArr;
        } else {
            if (strArr == null) {
                strArr = sSLSocket.getEnabledCipherSuites();
            }
            strArr2 = new String[(strArr.length + 1)];
            System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
            strArr2[strArr2.length - 1] = "TLS_FALLBACK_SCSV";
        }
        return new Builder(this).cipherSuites(strArr2).tlsVersions((String[]) Util.intersect(String.class, this.e, sSLSocket.getEnabledProtocols())).build();
    }

    public boolean isCompatible(SSLSocket sSLSocket) {
        boolean z = false;
        if (!this.a) {
            return false;
        }
        if (!a(this.e, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        if (this.d != null) {
            z = a(this.d, sSLSocket.getEnabledCipherSuites());
        } else if (sSLSocket.getEnabledCipherSuites().length > 0) {
            z = true;
        }
        return z;
    }

    private static boolean a(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr2 == null || strArr.length == 0 || strArr2.length == 0) {
            return false;
        }
        for (String a2 : strArr) {
            if (a((T[]) strArr2, (T) a2)) {
                return true;
            }
        }
        return false;
    }

    private static <T> boolean a(T[] tArr, T t) {
        for (T equal : tArr) {
            if (Util.equal(t, equal)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
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
        return !this.a || (Arrays.equals(this.d, connectionSpec.d) && Arrays.equals(this.e, connectionSpec.e) && this.b == connectionSpec.b);
    }

    public int hashCode() {
        if (this.a) {
            return ((((527 + Arrays.hashCode(this.d)) * 31) + Arrays.hashCode(this.e)) * 31) + (this.b ^ true ? 1 : 0);
        }
        return 17;
    }

    public String toString() {
        String str;
        if (!this.a) {
            return "ConnectionSpec()";
        }
        List cipherSuites = cipherSuites();
        if (cipherSuites == null) {
            str = "[use default]";
        } else {
            str = cipherSuites.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ConnectionSpec(cipherSuites=");
        sb.append(str);
        sb.append(", tlsVersions=");
        sb.append(tlsVersions());
        sb.append(", supportsTlsExtensions=");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
