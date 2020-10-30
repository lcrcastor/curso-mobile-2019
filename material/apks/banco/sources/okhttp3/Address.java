package okhttp3;

import cz.msebera.android.httpclient.HttpHost;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.HttpUrl.Builder;
import okhttp3.internal.Util;

public final class Address {
    final HttpUrl a;
    final Dns b;
    final SocketFactory c;
    final Authenticator d;
    final List<Protocol> e;
    final List<ConnectionSpec> f;
    final ProxySelector g;
    @Nullable
    final Proxy h;
    @Nullable
    final SSLSocketFactory i;
    @Nullable
    final HostnameVerifier j;
    @Nullable
    final CertificatePinner k;

    public Address(String str, int i2, Dns dns, SocketFactory socketFactory, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier, @Nullable CertificatePinner certificatePinner, Authenticator authenticator, @Nullable Proxy proxy, List<Protocol> list, List<ConnectionSpec> list2, ProxySelector proxySelector) {
        this.a = new Builder().scheme(sSLSocketFactory != null ? "https" : HttpHost.DEFAULT_SCHEME_NAME).host(str).port(i2).build();
        if (dns == null) {
            throw new NullPointerException("dns == null");
        }
        this.b = dns;
        if (socketFactory == null) {
            throw new NullPointerException("socketFactory == null");
        }
        this.c = socketFactory;
        if (authenticator == null) {
            throw new NullPointerException("proxyAuthenticator == null");
        }
        this.d = authenticator;
        if (list == null) {
            throw new NullPointerException("protocols == null");
        }
        this.e = Util.immutableList(list);
        if (list2 == null) {
            throw new NullPointerException("connectionSpecs == null");
        }
        this.f = Util.immutableList(list2);
        if (proxySelector == null) {
            throw new NullPointerException("proxySelector == null");
        }
        this.g = proxySelector;
        this.h = proxy;
        this.i = sSLSocketFactory;
        this.j = hostnameVerifier;
        this.k = certificatePinner;
    }

    public HttpUrl url() {
        return this.a;
    }

    public Dns dns() {
        return this.b;
    }

    public SocketFactory socketFactory() {
        return this.c;
    }

    public Authenticator proxyAuthenticator() {
        return this.d;
    }

    public List<Protocol> protocols() {
        return this.e;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.f;
    }

    public ProxySelector proxySelector() {
        return this.g;
    }

    @Nullable
    public Proxy proxy() {
        return this.h;
    }

    @Nullable
    public SSLSocketFactory sslSocketFactory() {
        return this.i;
    }

    @Nullable
    public HostnameVerifier hostnameVerifier() {
        return this.j;
    }

    @Nullable
    public CertificatePinner certificatePinner() {
        return this.k;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Address) {
            Address address = (Address) obj;
            if (this.a.equals(address.a) && a(address)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i2 = 0;
        int hashCode = (((((((((((((((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31) + (this.h != null ? this.h.hashCode() : 0)) * 31) + (this.i != null ? this.i.hashCode() : 0)) * 31) + (this.j != null ? this.j.hashCode() : 0)) * 31;
        if (this.k != null) {
            i2 = this.k.hashCode();
        }
        return hashCode + i2;
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Address address) {
        return this.b.equals(address.b) && this.d.equals(address.d) && this.e.equals(address.e) && this.f.equals(address.f) && this.g.equals(address.g) && Util.equal(this.h, address.h) && Util.equal(this.i, address.i) && Util.equal(this.j, address.j) && Util.equal(this.k, address.k) && url().port() == address.url().port();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address{");
        sb.append(this.a.host());
        sb.append(":");
        sb.append(this.a.port());
        if (this.h != null) {
            sb.append(", proxy=");
            sb.append(this.h);
        } else {
            sb.append(", proxySelector=");
            sb.append(this.g);
        }
        sb.append("}");
        return sb.toString();
    }
}
