package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import javax.annotation.Nullable;

@GwtIncompatible
@Beta
public final class InetAddresses {
    private static final Splitter a = Splitter.on('.').limit(4);
    private static final Inet4Address b = ((Inet4Address) forString("127.0.0.1"));
    /* access modifiers changed from: private */
    public static final Inet4Address c = ((Inet4Address) forString("0.0.0.0"));

    @Beta
    public static final class TeredoInfo {
        private final Inet4Address a;
        private final Inet4Address b;
        private final int c;
        private final int d;

        public TeredoInfo(@Nullable Inet4Address inet4Address, @Nullable Inet4Address inet4Address2, int i, int i2) {
            boolean z = false;
            Preconditions.checkArgument(i >= 0 && i <= 65535, "port '%s' is out of range (0 <= port <= 0xffff)", i);
            if (i2 >= 0 && i2 <= 65535) {
                z = true;
            }
            Preconditions.checkArgument(z, "flags '%s' is out of range (0 <= flags <= 0xffff)", i2);
            this.a = (Inet4Address) MoreObjects.firstNonNull(inet4Address, InetAddresses.c);
            this.b = (Inet4Address) MoreObjects.firstNonNull(inet4Address2, InetAddresses.c);
            this.c = i;
            this.d = i2;
        }

        public Inet4Address getServer() {
            return this.a;
        }

        public Inet4Address getClient() {
            return this.b;
        }

        public int getPort() {
            return this.c;
        }

        public int getFlags() {
            return this.d;
        }
    }

    private InetAddresses() {
    }

    private static Inet4Address a(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", bArr.length);
        return (Inet4Address) b(bArr);
    }

    public static InetAddress forString(String str) {
        byte[] a2 = a(str);
        if (a2 != null) {
            return b(a2);
        }
        throw a("'%s' is not an IP string literal.", str);
    }

    public static boolean isInetAddress(String str) {
        return a(str) != null;
    }

    @Nullable
    private static byte[] a(String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '.') {
                z2 = true;
            } else if (charAt == ':') {
                if (z2) {
                    return null;
                }
                z = true;
            } else if (Character.digit(charAt, 16) == -1) {
                return null;
            }
        }
        if (z) {
            if (z2) {
                str = d(str);
                if (str == null) {
                    return null;
                }
            }
            return c(str);
        } else if (z2) {
            return b(str);
        } else {
            return null;
        }
    }

    @Nullable
    private static byte[] b(String str) {
        byte[] bArr = new byte[4];
        try {
            int i = 0;
            for (String e : a.split(str)) {
                int i2 = i + 1;
                bArr[i] = e(e);
                i = i2;
            }
            if (i != 4) {
                bArr = null;
            }
            return bArr;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    @Nullable
    private static byte[] c(String str) {
        int i;
        int i2;
        String[] split = str.split(":", 10);
        if (split.length < 3 || split.length > 9) {
            return null;
        }
        int i3 = -1;
        for (int i4 = 1; i4 < split.length - 1; i4++) {
            if (split[i4].length() == 0) {
                if (i3 >= 0) {
                    return null;
                }
                i3 = i4;
            }
        }
        if (i3 >= 0) {
            i2 = (split.length - i3) - 1;
            if (split[0].length() == 0) {
                i = i3 - 1;
                if (i != 0) {
                    return null;
                }
            } else {
                i = i3;
            }
            if (split[split.length - 1].length() == 0) {
                i2--;
                if (i2 != 0) {
                    return null;
                }
            }
        } else {
            i = split.length;
            i2 = 0;
        }
        int i5 = 8 - (i + i2);
        if (i3 < 0 ? i5 != 0 : i5 < 1) {
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(16);
        int i6 = 0;
        while (i6 < i) {
            try {
                allocate.putShort(f(split[i6]));
                i6++;
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        for (int i7 = 0; i7 < i5; i7++) {
            allocate.putShort(0);
        }
        while (i2 > 0) {
            allocate.putShort(f(split[split.length - i2]));
            i2--;
        }
        return allocate.array();
    }

    @Nullable
    private static String d(String str) {
        int lastIndexOf = str.lastIndexOf(58) + 1;
        String substring = str.substring(0, lastIndexOf);
        byte[] b2 = b(str.substring(lastIndexOf));
        if (b2 == null) {
            return null;
        }
        String hexString = Integer.toHexString(((b2[0] & UnsignedBytes.MAX_VALUE) << 8) | (b2[1] & UnsignedBytes.MAX_VALUE));
        String hexString2 = Integer.toHexString((b2[3] & UnsignedBytes.MAX_VALUE) | ((b2[2] & UnsignedBytes.MAX_VALUE) << 8));
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(hexString);
        sb.append(":");
        sb.append(hexString2);
        return sb.toString();
    }

    private static byte e(String str) {
        int parseInt = Integer.parseInt(str);
        if (parseInt <= 255 && (!str.startsWith("0") || str.length() <= 1)) {
            return (byte) parseInt;
        }
        throw new NumberFormatException();
    }

    private static short f(String str) {
        int parseInt = Integer.parseInt(str, 16);
        if (parseInt <= 65535) {
            return (short) parseInt;
        }
        throw new NumberFormatException();
    }

    private static InetAddress b(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr);
        } catch (UnknownHostException e) {
            throw new AssertionError(e);
        }
    }

    public static String toAddrString(InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        byte[] address = inetAddress.getAddress();
        int[] iArr = new int[8];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 2;
            iArr[i] = Ints.fromBytes(0, 0, address[i2], address[i2 + 1]);
        }
        a(iArr);
        return b(iArr);
    }

    private static void a(int[] iArr) {
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < iArr.length + 1; i4++) {
            if (i4 >= iArr.length || iArr[i4] != 0) {
                if (i2 >= 0) {
                    int i5 = i4 - i2;
                    if (i5 > i) {
                        i = i5;
                    } else {
                        i2 = i3;
                    }
                    i3 = i2;
                    i2 = -1;
                }
            } else if (i2 < 0) {
                i2 = i4;
            }
        }
        if (i >= 2) {
            Arrays.fill(iArr, i3, i + i3, -1);
        }
    }

    private static String b(int[] iArr) {
        StringBuilder sb = new StringBuilder(39);
        int i = 0;
        boolean z = false;
        while (i < iArr.length) {
            boolean z2 = iArr[i] >= 0;
            if (z2) {
                if (z) {
                    sb.append(':');
                }
                sb.append(Integer.toHexString(iArr[i]));
            } else if (i == 0 || z) {
                sb.append("::");
            }
            i++;
            z = z2;
        }
        return sb.toString();
    }

    public static String toUriString(InetAddress inetAddress) {
        if (!(inetAddress instanceof Inet6Address)) {
            return toAddrString(inetAddress);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(toAddrString(inetAddress));
        sb.append("]");
        return sb.toString();
    }

    public static InetAddress forUriString(String str) {
        InetAddress g = g(str);
        if (g != null) {
            return g;
        }
        throw a("Not a valid URI IP literal: '%s'", str);
    }

    @Nullable
    private static InetAddress g(String str) {
        int i;
        Preconditions.checkNotNull(str);
        if (!str.startsWith("[") || !str.endsWith("]")) {
            i = 4;
        } else {
            str = str.substring(1, str.length() - 1);
            i = 16;
        }
        byte[] a2 = a(str);
        if (a2 == null || a2.length != i) {
            return null;
        }
        return b(a2);
    }

    public static boolean isUriInetAddress(String str) {
        return g(str) != null;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6Address) {
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        if (address[12] == 0 && address[13] == 0 && address[14] == 0 && (address[15] == 0 || address[15] == 1)) {
            return false;
        }
        return true;
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", (Object) toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean is6to4Address(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        if (address[0] == 32 && address[1] == 2) {
            return true;
        }
        return false;
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", (Object) toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    public static boolean isTeredoAddress(Inet6Address inet6Address) {
        byte[] address = inet6Address.getAddress();
        if (address[0] == 32 && address[1] == 1 && address[2] == 0 && address[3] == 0) {
            return true;
        }
        return false;
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6Address) {
        Preconditions.checkArgument(isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", (Object) toAddrString(inet6Address));
        byte[] address = inet6Address.getAddress();
        Inet4Address a2 = a(Arrays.copyOfRange(address, 4, 8));
        short readShort = ByteStreams.newDataInput(address, 8).readShort() & 65535;
        short readShort2 = 65535 & (ByteStreams.newDataInput(address, 10).readShort() ^ -1);
        byte[] copyOfRange = Arrays.copyOfRange(address, 12, 16);
        for (int i = 0; i < copyOfRange.length; i++) {
            copyOfRange[i] = (byte) (copyOfRange[i] ^ -1);
        }
        return new TeredoInfo(a2, a(copyOfRange), readShort2, readShort);
    }

    public static boolean isIsatapAddress(Inet6Address inet6Address) {
        boolean z = false;
        if (isTeredoAddress(inet6Address)) {
            return false;
        }
        byte[] address = inet6Address.getAddress();
        if ((address[8] | 3) != 3) {
            return false;
        }
        if (address[9] == 0 && address[10] == 94 && address[11] == -2) {
            z = true;
        }
        return z;
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", (Object) toAddrString(inet6Address));
        return a(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        return isCompatIPv4Address(inet6Address) || is6to4Address(inet6Address) || isTeredoAddress(inet6Address);
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (isCompatIPv4Address(inet6Address)) {
            return getCompatIPv4Address(inet6Address);
        }
        if (is6to4Address(inet6Address)) {
            return get6to4IPv4Address(inet6Address);
        }
        if (isTeredoAddress(inet6Address)) {
            return getTeredoInfo(inet6Address).getClient();
        }
        throw a("'%s' has no embedded IPv4 address.", toAddrString(inet6Address));
    }

    public static boolean isMappedIPv4Address(String str) {
        byte[] a2 = a(str);
        if (a2 == null || a2.length != 16) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= 10) {
                for (int i2 = 10; i2 < 12; i2++) {
                    if (a2[i2] != -1) {
                        return false;
                    }
                }
                return true;
            } else if (a2[i] != 0) {
                return false;
            } else {
                i++;
            }
        }
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        boolean z;
        long j;
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address) inetAddress;
        }
        byte[] address = inetAddress.getAddress();
        int i = 0;
        while (true) {
            if (i >= 15) {
                z = true;
                break;
            } else if (address[i] != 0) {
                z = false;
                break;
            } else {
                i++;
            }
        }
        if (z && address[15] == 1) {
            return b;
        }
        if (z && address[15] == 0) {
            return c;
        }
        Inet6Address inet6Address = (Inet6Address) inetAddress;
        if (hasEmbeddedIPv4ClientAddress(inet6Address)) {
            j = (long) getEmbeddedIPv4ClientAddress(inet6Address).hashCode();
        } else {
            j = ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong();
        }
        int asInt = Hashing.murmur3_32().hashLong(j).asInt() | -536870912;
        if (asInt == -1) {
            asInt = -2;
        }
        return a(Ints.toByteArray(asInt));
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    public static Inet4Address fromInteger(int i) {
        return a(Ints.toByteArray(i));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[(bArr.length - i) - 1];
        }
        return InetAddress.getByAddress(bArr2);
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        int length = address.length - 1;
        while (length >= 0 && address[length] == 0) {
            address[length] = -1;
            length--;
        }
        Preconditions.checkArgument(length >= 0, "Decrementing %s would wrap.", (Object) inetAddress);
        address[length] = (byte) (address[length] - 1);
        return b(address);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.net.InetAddress increment(java.net.InetAddress r6) {
        /*
            byte[] r0 = r6.getAddress()
            int r1 = r0.length
            r2 = 1
            int r1 = r1 - r2
        L_0x0007:
            r3 = 0
            if (r1 < 0) goto L_0x0014
            byte r4 = r0[r1]
            r5 = -1
            if (r4 != r5) goto L_0x0014
            r0[r1] = r3
            int r1 = r1 + -1
            goto L_0x0007
        L_0x0014:
            if (r1 < 0) goto L_0x0017
            r3 = 1
        L_0x0017:
            java.lang.String r4 = "Incrementing %s would wrap."
            com.google.common.base.Preconditions.checkArgument(r3, r4, r6)
            byte r6 = r0[r1]
            int r6 = r6 + r2
            byte r6 = (byte) r6
            r0[r1] = r6
            java.net.InetAddress r6 = b(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.net.InetAddresses.increment(java.net.InetAddress):java.net.InetAddress");
    }

    public static boolean isMaximum(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        for (byte b2 : address) {
            if (b2 != -1) {
                return false;
            }
        }
        return true;
    }

    private static IllegalArgumentException a(String str, Object... objArr) {
        return new IllegalArgumentException(String.format(Locale.ROOT, str, objArr));
    }
}
