package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route {
    final Address a;
    final Proxy b;
    final InetSocketAddress c;

    public Route(Address address, Proxy proxy, InetSocketAddress inetSocketAddress) {
        if (address == null) {
            throw new NullPointerException("address == null");
        } else if (proxy == null) {
            throw new NullPointerException("proxy == null");
        } else if (inetSocketAddress == null) {
            throw new NullPointerException("inetSocketAddress == null");
        } else {
            this.a = address;
            this.b = proxy;
            this.c = inetSocketAddress;
        }
    }

    public Address getAddress() {
        return this.a;
    }

    public Proxy getProxy() {
        return this.b;
    }

    public InetSocketAddress getSocketAddress() {
        return this.c;
    }

    public boolean requiresTunnel() {
        return this.a.e != null && this.b.type() == Type.HTTP;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof Route)) {
            return false;
        }
        Route route = (Route) obj;
        if (this.a.equals(route.a) && this.b.equals(route.b) && this.c.equals(route.c)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }
}
