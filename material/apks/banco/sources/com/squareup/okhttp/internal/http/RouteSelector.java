package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public final class RouteSelector {
    private final Address a;
    private final URI b;
    private final Network c;
    private final OkHttpClient d;
    private final RouteDatabase e;
    private Proxy f;
    private InetSocketAddress g;
    private List<Proxy> h = Collections.emptyList();
    private int i;
    private List<InetSocketAddress> j = Collections.emptyList();
    private int k;
    private final List<Route> l = new ArrayList();

    private RouteSelector(Address address, URI uri, OkHttpClient okHttpClient) {
        this.a = address;
        this.b = uri;
        this.d = okHttpClient;
        this.e = Internal.instance.routeDatabase(okHttpClient);
        this.c = Internal.instance.network(okHttpClient);
        a(uri, address.getProxy());
    }

    public static RouteSelector get(Address address, Request request, OkHttpClient okHttpClient) {
        return new RouteSelector(address, request.uri(), okHttpClient);
    }

    public boolean hasNext() {
        return c() || a() || e();
    }

    public Route next() {
        if (!c()) {
            if (a()) {
                this.f = b();
            } else if (e()) {
                return f();
            } else {
                throw new NoSuchElementException();
            }
        }
        this.g = d();
        Route route = new Route(this.a, this.f, this.g);
        if (!this.e.shouldPostpone(route)) {
            return route;
        }
        this.l.add(route);
        return next();
    }

    public void connectFailed(Route route, IOException iOException) {
        if (!(route.getProxy().type() == Type.DIRECT || this.a.getProxySelector() == null)) {
            this.a.getProxySelector().connectFailed(this.b, route.getProxy().address(), iOException);
        }
        this.e.failed(route);
    }

    private void a(URI uri, Proxy proxy) {
        if (proxy != null) {
            this.h = Collections.singletonList(proxy);
        } else {
            this.h = new ArrayList();
            List select = this.d.getProxySelector().select(uri);
            if (select != null) {
                this.h.addAll(select);
            }
            this.h.removeAll(Collections.singleton(Proxy.NO_PROXY));
            this.h.add(Proxy.NO_PROXY);
        }
        this.i = 0;
    }

    private boolean a() {
        return this.i < this.h.size();
    }

    private Proxy b() {
        if (!a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No route to ");
            sb.append(this.a.getUriHost());
            sb.append("; exhausted proxy configurations: ");
            sb.append(this.h);
            throw new SocketException(sb.toString());
        }
        List<Proxy> list = this.h;
        int i2 = this.i;
        this.i = i2 + 1;
        Proxy proxy = (Proxy) list.get(i2);
        a(proxy);
        return proxy;
    }

    private void a(Proxy proxy) {
        int i2;
        String str;
        this.j = new ArrayList();
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.SOCKS) {
            str = this.a.getUriHost();
            i2 = Util.getEffectivePort(this.b);
        } else {
            SocketAddress address = proxy.address();
            if (!(address instanceof InetSocketAddress)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Proxy.address() is not an InetSocketAddress: ");
                sb.append(address.getClass());
                throw new IllegalArgumentException(sb.toString());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
            str = a(inetSocketAddress);
            i2 = inetSocketAddress.getPort();
        }
        if (i2 < 1 || i2 > 65535) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("No route to ");
            sb2.append(str);
            sb2.append(":");
            sb2.append(i2);
            sb2.append("; port is out of range");
            throw new SocketException(sb2.toString());
        }
        for (InetAddress inetSocketAddress2 : this.c.resolveInetAddresses(str)) {
            this.j.add(new InetSocketAddress(inetSocketAddress2, i2));
        }
        this.k = 0;
    }

    static String a(InetSocketAddress inetSocketAddress) {
        InetAddress address = inetSocketAddress.getAddress();
        if (address == null) {
            return inetSocketAddress.getHostName();
        }
        return address.getHostAddress();
    }

    private boolean c() {
        return this.k < this.j.size();
    }

    private InetSocketAddress d() {
        if (!c()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No route to ");
            sb.append(this.a.getUriHost());
            sb.append("; exhausted inet socket addresses: ");
            sb.append(this.j);
            throw new SocketException(sb.toString());
        }
        List<InetSocketAddress> list = this.j;
        int i2 = this.k;
        this.k = i2 + 1;
        return (InetSocketAddress) list.get(i2);
    }

    private boolean e() {
        return !this.l.isEmpty();
    }

    private Route f() {
        return (Route) this.l.remove(0);
    }
}
