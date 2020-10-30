package com.google.common.net;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.Serializable;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@GwtCompatible
@Immutable
@Beta
public final class HostAndPort implements Serializable {
    private static final long serialVersionUID = 0;
    private final String a;
    private final int b;
    private final boolean c;

    private static boolean a(int i) {
        return i >= 0 && i <= 65535;
    }

    private HostAndPort(String str, int i, boolean z) {
        this.a = str;
        this.b = i;
        this.c = z;
    }

    public String getHost() {
        return this.a;
    }

    @Deprecated
    public String getHostText() {
        return this.a;
    }

    public boolean hasPort() {
        return this.b >= 0;
    }

    public int getPort() {
        Preconditions.checkState(hasPort());
        return this.b;
    }

    public int getPortOrDefault(int i) {
        return hasPort() ? this.b : i;
    }

    public static HostAndPort fromParts(String str, int i) {
        Preconditions.checkArgument(a(i), "Port out of range: %s", i);
        HostAndPort fromString = fromString(str);
        Preconditions.checkArgument(!fromString.hasPort(), "Host has a port: %s", (Object) str);
        return new HostAndPort(fromString.a, i, fromString.c);
    }

    public static HostAndPort fromHost(String str) {
        HostAndPort fromString = fromString(str);
        Preconditions.checkArgument(!fromString.hasPort(), "Host has a port: %s", (Object) str);
        return fromString;
    }

    public static HostAndPort fromString(String str) {
        String str2;
        String str3;
        Preconditions.checkNotNull(str);
        int i = -1;
        boolean z = false;
        if (str.startsWith("[")) {
            String[] a2 = a(str);
            str2 = a2[0];
            str3 = a2[1];
        } else {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                int i2 = indexOf + 1;
                if (str.indexOf(58, i2) == -1) {
                    String substring = str.substring(0, indexOf);
                    str2 = substring;
                    str3 = str.substring(i2);
                }
            }
            if (indexOf >= 0) {
                z = true;
            }
            str3 = null;
            str2 = str;
        }
        if (!Strings.isNullOrEmpty(str3)) {
            Preconditions.checkArgument(!str3.startsWith(Constants.SYMBOL_POSITIVE), "Unparseable port number: %s", (Object) str);
            try {
                i = Integer.parseInt(str3);
                Preconditions.checkArgument(a(i), "Port number out of range: %s", (Object) str);
            } catch (NumberFormatException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unparseable port number: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return new HostAndPort(str2, i, z);
    }

    private static String[] a(String str) {
        Preconditions.checkArgument(str.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", (Object) str);
        int indexOf = str.indexOf(58);
        int lastIndexOf = str.lastIndexOf(93);
        Preconditions.checkArgument(indexOf > -1 && lastIndexOf > indexOf, "Invalid bracketed host/port: %s", (Object) str);
        String substring = str.substring(1, lastIndexOf);
        int i = lastIndexOf + 1;
        if (i == str.length()) {
            return new String[]{substring, ""};
        }
        Preconditions.checkArgument(str.charAt(i) == ':', "Only a colon may follow a close bracket: %s", (Object) str);
        int i2 = lastIndexOf + 2;
        for (int i3 = i2; i3 < str.length(); i3++) {
            Preconditions.checkArgument(Character.isDigit(str.charAt(i3)), "Port must be numeric: %s", (Object) str);
        }
        return new String[]{substring, str.substring(i2)};
    }

    public HostAndPort withDefaultPort(int i) {
        Preconditions.checkArgument(a(i));
        return (hasPort() || this.b == i) ? this : new HostAndPort(this.a, i, this.c);
    }

    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(!this.c, "Possible bracketless IPv6 literal: %s", (Object) this.a);
        return this;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HostAndPort)) {
            return false;
        }
        HostAndPort hostAndPort = (HostAndPort) obj;
        if (!(Objects.equal(this.a, hostAndPort.a) && this.b == hostAndPort.b && this.c == hostAndPort.c)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(this.a, Integer.valueOf(this.b), Boolean.valueOf(this.c));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.a.length() + 8);
        if (this.a.indexOf(58) >= 0) {
            sb.append('[');
            sb.append(this.a);
            sb.append(']');
        } else {
            sb.append(this.a);
        }
        if (hasPort()) {
            sb.append(':');
            sb.append(this.b);
        }
        return sb.toString();
    }
}
