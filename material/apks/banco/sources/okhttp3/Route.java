package okhttp3;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import javax.annotation.Nullable;

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

    public Address address() {
        return this.a;
    }

    public Proxy proxy() {
        return this.b;
    }

    public InetSocketAddress socketAddress() {
        return this.c;
    }

    public boolean requiresTunnel() {
        return this.a.i != null && this.b.type() == Type.HTTP;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Route) {
            Route route = (Route) obj;
            if (route.a.equals(this.a) && route.b.equals(this.b) && route.c.equals(this.c)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Route{");
        sb.append(this.c);
        sb.append("}");
        return sb.toString();
    }
}
