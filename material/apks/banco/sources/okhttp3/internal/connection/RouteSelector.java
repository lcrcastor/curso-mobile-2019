package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;

public final class RouteSelector {
    private final Address a;
    private final RouteDatabase b;
    private final Call c;
    private final EventListener d;
    private List<Proxy> e = Collections.emptyList();
    private int f;
    private List<InetSocketAddress> g = Collections.emptyList();
    private final List<Route> h = new ArrayList();

    public static final class Selection {
        private final List<Route> a;
        private int b = 0;

        Selection(List<Route> list) {
            this.a = list;
        }

        public boolean hasNext() {
            return this.b < this.a.size();
        }

        public Route next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            List<Route> list = this.a;
            int i = this.b;
            this.b = i + 1;
            return (Route) list.get(i);
        }

        public List<Route> getAll() {
            return new ArrayList(this.a);
        }
    }

    public RouteSelector(Address address, RouteDatabase routeDatabase, Call call, EventListener eventListener) {
        this.a = address;
        this.b = routeDatabase;
        this.c = call;
        this.d = eventListener;
        a(address.url(), address.proxy());
    }

    public boolean hasNext() {
        return a() || !this.h.isEmpty();
    }

    public Selection next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ArrayList arrayList = new ArrayList();
        while (a()) {
            Proxy b2 = b();
            int size = this.g.size();
            for (int i = 0; i < size; i++) {
                Route route = new Route(this.a, b2, (InetSocketAddress) this.g.get(i));
                if (this.b.shouldPostpone(route)) {
                    this.h.add(route);
                } else {
                    arrayList.add(route);
                }
            }
            if (!arrayList.isEmpty()) {
                break;
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.addAll(this.h);
            this.h.clear();
        }
        return new Selection(arrayList);
    }

    public void connectFailed(Route route, IOException iOException) {
        if (!(route.proxy().type() == Type.DIRECT || this.a.proxySelector() == null)) {
            this.a.proxySelector().connectFailed(this.a.url().uri(), route.proxy().address(), iOException);
        }
        this.b.failed(route);
    }

    private void a(HttpUrl httpUrl, Proxy proxy) {
        List<Proxy> list;
        if (proxy != null) {
            this.e = Collections.singletonList(proxy);
        } else {
            List select = this.a.proxySelector().select(httpUrl.uri());
            if (select == null || select.isEmpty()) {
                list = Util.immutableList((T[]) new Proxy[]{Proxy.NO_PROXY});
            } else {
                list = Util.immutableList(select);
            }
            this.e = list;
        }
        this.f = 0;
    }

    private boolean a() {
        return this.f < this.e.size();
    }

    private Proxy b() {
        if (!a()) {
            StringBuilder sb = new StringBuilder();
            sb.append("No route to ");
            sb.append(this.a.url().host());
            sb.append("; exhausted proxy configurations: ");
            sb.append(this.e);
            throw new SocketException(sb.toString());
        }
        List<Proxy> list = this.e;
        int i = this.f;
        this.f = i + 1;
        Proxy proxy = (Proxy) list.get(i);
        a(proxy);
        return proxy;
    }

    private void a(Proxy proxy) {
        String str;
        int i;
        this.g = new ArrayList();
        if (proxy.type() == Type.DIRECT || proxy.type() == Type.SOCKS) {
            str = this.a.url().host();
            i = this.a.url().port();
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
            i = inetSocketAddress.getPort();
        }
        if (i < 1 || i > 65535) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("No route to ");
            sb2.append(str);
            sb2.append(":");
            sb2.append(i);
            sb2.append("; port is out of range");
            throw new SocketException(sb2.toString());
        } else if (proxy.type() == Type.SOCKS) {
            this.g.add(InetSocketAddress.createUnresolved(str, i));
        } else {
            this.d.dnsStart(this.c, str);
            List lookup = this.a.dns().lookup(str);
            if (lookup.isEmpty()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.a.dns());
                sb3.append(" returned no addresses for ");
                sb3.append(str);
                throw new UnknownHostException(sb3.toString());
            }
            this.d.dnsEnd(this.c, str, lookup);
            int size = lookup.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.g.add(new InetSocketAddress((InetAddress) lookup.get(i2), i));
            }
        }
    }

    static String a(InetSocketAddress inetSocketAddress) {
        InetAddress address = inetSocketAddress.getAddress();
        if (address == null) {
            return inetSocketAddress.getHostName();
        }
        return address.getHostAddress();
    }
}
