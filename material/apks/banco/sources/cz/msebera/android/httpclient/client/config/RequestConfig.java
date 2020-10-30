package cz.msebera.android.httpclient.client.config;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.Immutable;
import java.net.InetAddress;
import java.util.Collection;

@Immutable
public class RequestConfig implements Cloneable {
    public static final RequestConfig DEFAULT = new Builder().build();
    private final boolean a;
    private final HttpHost b;
    private final InetAddress c;
    private final boolean d;
    private final String e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final int i;
    private final boolean j;
    private final Collection<String> k;
    private final Collection<String> l;
    private final int m;
    private final int n;
    private final int o;
    private final boolean p;

    public static class Builder {
        private boolean a;
        private HttpHost b;
        private InetAddress c;
        private boolean d = false;
        private String e;
        private boolean f = true;
        private boolean g = true;
        private boolean h;
        private int i = 50;
        private boolean j = true;
        private Collection<String> k;
        private Collection<String> l;
        private int m = -1;
        private int n = -1;
        private int o = -1;
        private boolean p = true;

        Builder() {
        }

        public Builder setExpectContinueEnabled(boolean z) {
            this.a = z;
            return this;
        }

        public Builder setProxy(HttpHost httpHost) {
            this.b = httpHost;
            return this;
        }

        public Builder setLocalAddress(InetAddress inetAddress) {
            this.c = inetAddress;
            return this;
        }

        @Deprecated
        public Builder setStaleConnectionCheckEnabled(boolean z) {
            this.d = z;
            return this;
        }

        public Builder setCookieSpec(String str) {
            this.e = str;
            return this;
        }

        public Builder setRedirectsEnabled(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setRelativeRedirectsAllowed(boolean z) {
            this.g = z;
            return this;
        }

        public Builder setCircularRedirectsAllowed(boolean z) {
            this.h = z;
            return this;
        }

        public Builder setMaxRedirects(int i2) {
            this.i = i2;
            return this;
        }

        public Builder setAuthenticationEnabled(boolean z) {
            this.j = z;
            return this;
        }

        public Builder setTargetPreferredAuthSchemes(Collection<String> collection) {
            this.k = collection;
            return this;
        }

        public Builder setProxyPreferredAuthSchemes(Collection<String> collection) {
            this.l = collection;
            return this;
        }

        public Builder setConnectionRequestTimeout(int i2) {
            this.m = i2;
            return this;
        }

        public Builder setConnectTimeout(int i2) {
            this.n = i2;
            return this;
        }

        public Builder setSocketTimeout(int i2) {
            this.o = i2;
            return this;
        }

        public Builder setDecompressionEnabled(boolean z) {
            this.p = z;
            return this;
        }

        public RequestConfig build() {
            boolean z = this.a;
            HttpHost httpHost = this.b;
            InetAddress inetAddress = this.c;
            boolean z2 = this.d;
            String str = this.e;
            boolean z3 = this.f;
            boolean z4 = this.g;
            boolean z5 = this.h;
            int i2 = this.i;
            boolean z6 = this.j;
            Collection<String> collection = this.k;
            Collection<String> collection2 = this.l;
            int i3 = this.m;
            int i4 = this.n;
            int i5 = i4;
            int i6 = i5;
            RequestConfig requestConfig = new RequestConfig(z, httpHost, inetAddress, z2, str, z3, z4, z5, i2, z6, collection, collection2, i3, i6, this.o, this.p);
            return requestConfig;
        }
    }

    RequestConfig(boolean z, HttpHost httpHost, InetAddress inetAddress, boolean z2, String str, boolean z3, boolean z4, boolean z5, int i2, boolean z6, Collection<String> collection, Collection<String> collection2, int i3, int i4, int i5, boolean z7) {
        this.a = z;
        this.b = httpHost;
        this.c = inetAddress;
        this.d = z2;
        this.e = str;
        this.f = z3;
        this.g = z4;
        this.h = z5;
        this.i = i2;
        this.j = z6;
        this.k = collection;
        this.l = collection2;
        this.m = i3;
        this.n = i4;
        this.o = i5;
        this.p = z7;
    }

    public boolean isExpectContinueEnabled() {
        return this.a;
    }

    public HttpHost getProxy() {
        return this.b;
    }

    public InetAddress getLocalAddress() {
        return this.c;
    }

    @Deprecated
    public boolean isStaleConnectionCheckEnabled() {
        return this.d;
    }

    public String getCookieSpec() {
        return this.e;
    }

    public boolean isRedirectsEnabled() {
        return this.f;
    }

    public boolean isRelativeRedirectsAllowed() {
        return this.g;
    }

    public boolean isCircularRedirectsAllowed() {
        return this.h;
    }

    public int getMaxRedirects() {
        return this.i;
    }

    public boolean isAuthenticationEnabled() {
        return this.j;
    }

    public Collection<String> getTargetPreferredAuthSchemes() {
        return this.k;
    }

    public Collection<String> getProxyPreferredAuthSchemes() {
        return this.l;
    }

    public int getConnectionRequestTimeout() {
        return this.m;
    }

    public int getConnectTimeout() {
        return this.n;
    }

    public int getSocketTimeout() {
        return this.o;
    }

    public boolean isDecompressionEnabled() {
        return this.p;
    }

    /* access modifiers changed from: protected */
    public RequestConfig clone() {
        return (RequestConfig) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("expectContinueEnabled=");
        sb.append(this.a);
        sb.append(", proxy=");
        sb.append(this.b);
        sb.append(", localAddress=");
        sb.append(this.c);
        sb.append(", cookieSpec=");
        sb.append(this.e);
        sb.append(", redirectsEnabled=");
        sb.append(this.f);
        sb.append(", relativeRedirectsAllowed=");
        sb.append(this.g);
        sb.append(", maxRedirects=");
        sb.append(this.i);
        sb.append(", circularRedirectsAllowed=");
        sb.append(this.h);
        sb.append(", authenticationEnabled=");
        sb.append(this.j);
        sb.append(", targetPreferredAuthSchemes=");
        sb.append(this.k);
        sb.append(", proxyPreferredAuthSchemes=");
        sb.append(this.l);
        sb.append(", connectionRequestTimeout=");
        sb.append(this.m);
        sb.append(", connectTimeout=");
        sb.append(this.n);
        sb.append(", socketTimeout=");
        sb.append(this.o);
        sb.append(", decompressionEnabled=");
        sb.append(this.p);
        sb.append("]");
        return sb.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(RequestConfig requestConfig) {
        return new Builder().setExpectContinueEnabled(requestConfig.isExpectContinueEnabled()).setProxy(requestConfig.getProxy()).setLocalAddress(requestConfig.getLocalAddress()).setStaleConnectionCheckEnabled(requestConfig.isStaleConnectionCheckEnabled()).setCookieSpec(requestConfig.getCookieSpec()).setRedirectsEnabled(requestConfig.isRedirectsEnabled()).setRelativeRedirectsAllowed(requestConfig.isRelativeRedirectsAllowed()).setCircularRedirectsAllowed(requestConfig.isCircularRedirectsAllowed()).setMaxRedirects(requestConfig.getMaxRedirects()).setAuthenticationEnabled(requestConfig.isAuthenticationEnabled()).setTargetPreferredAuthSchemes(requestConfig.getTargetPreferredAuthSchemes()).setProxyPreferredAuthSchemes(requestConfig.getProxyPreferredAuthSchemes()).setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout()).setConnectTimeout(requestConfig.getConnectTimeout()).setSocketTimeout(requestConfig.getSocketTimeout()).setDecompressionEnabled(requestConfig.isDecompressionEnabled());
    }
}
