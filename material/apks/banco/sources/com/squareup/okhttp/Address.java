package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address {
    final Proxy a;
    final String b;
    final int c;
    final SocketFactory d;
    final SSLSocketFactory e;
    final HostnameVerifier f;
    final CertificatePinner g;
    final Authenticator h;
    final List<Protocol> i;
    final List<ConnectionSpec> j;
    final ProxySelector k;

    public Address(String str, int i2, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, CertificatePinner certificatePinner, Authenticator authenticator, Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        if (str == null) {
            throw new NullPointerException("uriHost == null");
        } else if (i2 <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("uriPort <= 0: ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        } else if (authenticator == null) {
            throw new IllegalArgumentException("authenticator == null");
        } else if (list == null) {
            throw new IllegalArgumentException("protocols == null");
        } else if (proxySelector == null) {
            throw new IllegalArgumentException("proxySelector == null");
        } else {
            this.a = proxy;
            this.b = str;
            this.c = i2;
            this.d = socketFactory;
            this.e = sSLSocketFactory;
            this.f = hostnameVerifier;
            this.g = certificatePinner;
            this.h = authenticator;
            this.i = Util.immutableList(list);
            this.j = Util.immutableList(list2);
            this.k = proxySelector;
        }
    }

    public String getUriHost() {
        return this.b;
    }

    public int getUriPort() {
        return this.c;
    }

    public SocketFactory getSocketFactory() {
        return this.d;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.e;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f;
    }

    public Authenticator getAuthenticator() {
        return this.h;
    }

    public List<Protocol> getProtocols() {
        return this.i;
    }

    public List<ConnectionSpec> getConnectionSpecs() {
        return this.j;
    }

    public Proxy getProxy() {
        return this.a;
    }

    public ProxySelector getProxySelector() {
        return this.k;
    }

    public CertificatePinner getCertificatePinner() {
        return this.g;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Address)) {
            return false;
        }
        Address address = (Address) obj;
        if (Util.equal(this.a, address.a) && this.b.equals(address.b) && this.c == address.c && Util.equal(this.e, address.e) && Util.equal(this.f, address.f) && Util.equal(this.g, address.g) && Util.equal(this.h, address.h) && Util.equal(this.i, address.i) && Util.equal(this.j, address.j) && Util.equal(this.k, address.k)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        int i2 = 0;
        int hashCode = (((((((((527 + (this.a != null ? this.a.hashCode() : 0)) * 31) + this.b.hashCode()) * 31) + this.c) * 31) + (this.e != null ? this.e.hashCode() : 0)) * 31) + (this.f != null ? this.f.hashCode() : 0)) * 31;
        if (this.g != null) {
            i2 = this.g.hashCode();
        }
        return ((((((((hashCode + i2) * 31) + this.h.hashCode()) * 31) + this.i.hashCode()) * 31) + this.j.hashCode()) * 31) + this.k.hashCode();
    }
}
