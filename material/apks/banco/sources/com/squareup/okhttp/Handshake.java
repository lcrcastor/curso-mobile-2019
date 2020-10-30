package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class Handshake {
    private final String a;
    private final List<Certificate> b;
    private final List<Certificate> c;

    private Handshake(String str, List<Certificate> list, List<Certificate> list2) {
        this.a = str;
        this.b = list;
        this.c = list2;
    }

    public static Handshake get(SSLSession sSLSession) {
        Certificate[] certificateArr;
        List list;
        List list2;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        }
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
        return new Handshake(cipherSuite, list, list2);
    }

    public static Handshake get(String str, List<Certificate> list, List<Certificate> list2) {
        if (str != null) {
            return new Handshake(str, Util.immutableList(list), Util.immutableList(list2));
        }
        throw new IllegalArgumentException("cipherSuite == null");
    }

    public String cipherSuite() {
        return this.a;
    }

    public List<Certificate> peerCertificates() {
        return this.b;
    }

    public Principal peerPrincipal() {
        if (!this.b.isEmpty()) {
            return ((X509Certificate) this.b.get(0)).getSubjectX500Principal();
        }
        return null;
    }

    public List<Certificate> localCertificates() {
        return this.c;
    }

    public Principal localPrincipal() {
        if (!this.c.isEmpty()) {
            return ((X509Certificate) this.c.get(0)).getSubjectX500Principal();
        }
        return null;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Handshake)) {
            return false;
        }
        Handshake handshake = (Handshake) obj;
        if (this.a.equals(handshake.a) && this.b.equals(handshake.b) && this.c.equals(handshake.c)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }
}
