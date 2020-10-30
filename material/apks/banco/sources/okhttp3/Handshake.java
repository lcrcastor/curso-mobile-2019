package okhttp3;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import okhttp3.internal.Util;

public final class Handshake {
    private final TlsVersion a;
    private final CipherSuite b;
    private final List<Certificate> c;
    private final List<Certificate> d;

    private Handshake(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        this.a = tlsVersion;
        this.b = cipherSuite;
        this.c = list;
        this.d = list2;
    }

    public static Handshake get(SSLSession sSLSession) {
        Certificate[] certificateArr;
        List list;
        List list2;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        } else if ("SSL_NULL_WITH_NULL_NULL".equals(cipherSuite)) {
            throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
        } else {
            CipherSuite forJavaName = CipherSuite.forJavaName(cipherSuite);
            String protocol = sSLSession.getProtocol();
            if (protocol == null) {
                throw new IllegalStateException("tlsVersion == null");
            } else if ("NONE".equals(protocol)) {
                throw new IOException("tlsVersion == NONE");
            } else {
                TlsVersion forJavaName2 = TlsVersion.forJavaName(protocol);
                try {
                    certificateArr = sSLSession.getPeerCertificates();
                } catch (SSLPeerUnverifiedException unused) {
                    certificateArr = null;
                }
                if (certificateArr != null) {
                    list = Util.immutableList((T[]) certificateArr);
                } else {
                    list = Collections.emptyList();
                }
                Certificate[] localCertificates = sSLSession.getLocalCertificates();
                if (localCertificates != null) {
                    list2 = Util.immutableList((T[]) localCertificates);
                } else {
                    list2 = Collections.emptyList();
                }
                return new Handshake(forJavaName2, forJavaName, list, list2);
            }
        }
    }

    public static Handshake get(TlsVersion tlsVersion, CipherSuite cipherSuite, List<Certificate> list, List<Certificate> list2) {
        if (tlsVersion == null) {
            throw new NullPointerException("tlsVersion == null");
        } else if (cipherSuite != null) {
            return new Handshake(tlsVersion, cipherSuite, Util.immutableList(list), Util.immutableList(list2));
        } else {
            throw new NullPointerException("cipherSuite == null");
        }
    }

    public TlsVersion tlsVersion() {
        return this.a;
    }

    public CipherSuite cipherSuite() {
        return this.b;
    }

    public List<Certificate> peerCertificates() {
        return this.c;
    }

    @Nullable
    public Principal peerPrincipal() {
        if (!this.c.isEmpty()) {
            return ((X509Certificate) this.c.get(0)).getSubjectX500Principal();
        }
        return null;
    }

    public List<Certificate> localCertificates() {
        return this.d;
    }

    @Nullable
    public Principal localPrincipal() {
        if (!this.d.isEmpty()) {
            return ((X509Certificate) this.d.get(0)).getSubjectX500Principal();
        }
        return null;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Handshake)) {
            return false;
        }
        Handshake handshake = (Handshake) obj;
        if (this.a.equals(handshake.a) && this.b.equals(handshake.b) && this.c.equals(handshake.c) && this.d.equals(handshake.d)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
    }
}
