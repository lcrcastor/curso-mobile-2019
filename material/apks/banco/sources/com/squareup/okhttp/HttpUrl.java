package com.squareup.okhttp;

import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.HttpHost;
import java.net.IDN;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import okio.Buffer;

public final class HttpUrl {
    private static final char[] a = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    /* access modifiers changed from: private */
    public final String b;
    private final String c;
    private final String d;
    /* access modifiers changed from: private */
    public final String e;
    /* access modifiers changed from: private */
    public final int f;
    private final List<String> g;
    private final List<String> h;
    private final String i;
    private final String j;

    public static final class Builder {
        String a;
        String b = "";
        String c = "";
        String d;
        int e = -1;
        final List<String> f = new ArrayList();
        List<String> g;
        String h;

        public Builder() {
            this.f.add("");
        }

        public Builder scheme(String str) {
            if (str == null) {
                throw new IllegalArgumentException("scheme == null");
            }
            if (str.equalsIgnoreCase(HttpHost.DEFAULT_SCHEME_NAME)) {
                this.a = HttpHost.DEFAULT_SCHEME_NAME;
            } else if (str.equalsIgnoreCase("https")) {
                this.a = "https";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected scheme: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            return this;
        }

        public Builder username(String str) {
            if (str == null) {
                throw new IllegalArgumentException("username == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false);
            return this;
        }

        public Builder encodedUsername(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedUsername == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", true, false);
            return this;
        }

        public Builder password(String str) {
            if (str == null) {
                throw new IllegalArgumentException("password == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]\\^`{}|/\\?#", false, false);
            return this;
        }

        public Builder encodedPassword(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPassword == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]\\^`{}|/\\?#", true, false);
            return this;
        }

        public Builder host(String str) {
            if (str == null) {
                throw new IllegalArgumentException("host == null");
            }
            String g2 = g(str, 0, str.length());
            if (g2 == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected host: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            this.d = g2;
            return this;
        }

        public Builder port(int i) {
            if (i <= 0 || i > 65535) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected port: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            this.e = i;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.e != -1 ? this.e : HttpUrl.defaultPort(this.a);
        }

        public Builder addPathSegment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("pathSegment == null");
            }
            a(str, 0, str.length(), false, false);
            return this;
        }

        public Builder addEncodedPathSegment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPathSegment == null");
            }
            a(str, 0, str.length(), false, true);
            return this;
        }

        public Builder setPathSegment(int i, String str) {
            if (str == null) {
                throw new IllegalArgumentException("pathSegment == null");
            }
            String a2 = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", false, false);
            if (b(a2) || c(a2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected path segment: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            this.f.set(i, a2);
            return this;
        }

        public Builder setEncodedPathSegment(int i, String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPathSegment == null");
            }
            String a2 = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", true, false);
            this.f.set(i, a2);
            if (!b(a2) && !c(a2)) {
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected path segment: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder removePathSegment(int i) {
            this.f.remove(i);
            if (this.f.isEmpty()) {
                this.f.add("");
            }
            return this;
        }

        public Builder encodedPath(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPath == null");
            } else if (!str.startsWith("/")) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected encodedPath: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            } else {
                a(str, 0, str.length());
                return this;
            }
        }

        public Builder query(String str) {
            this.g = str != null ? HttpUrl.a(HttpUrl.a(str, " \"'<>#", false, true)) : null;
            return this;
        }

        public Builder encodedQuery(String str) {
            this.g = str != null ? HttpUrl.a(HttpUrl.a(str, " \"'<>#", true, true)) : null;
            return this;
        }

        public Builder addQueryParameter(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " \"'<>#&=", false, true));
            this.g.add(str2 != null ? HttpUrl.a(str2, " \"'<>#&=", false, true) : null);
            return this;
        }

        public Builder addEncodedQueryParameter(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("encodedName == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " \"'<>#&=", true, true));
            this.g.add(str2 != null ? HttpUrl.a(str2, " \"'<>#&=", true, true) : null);
            return this;
        }

        public Builder setQueryParameter(String str, String str2) {
            removeAllQueryParameters(str);
            addQueryParameter(str, str2);
            return this;
        }

        public Builder setEncodedQueryParameter(String str, String str2) {
            removeAllEncodedQueryParameters(str);
            addEncodedQueryParameter(str, str2);
            return this;
        }

        public Builder removeAllQueryParameters(String str) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            } else if (this.g == null) {
                return this;
            } else {
                a(HttpUrl.a(str, " \"'<>#&=", false, true));
                return this;
            }
        }

        public Builder removeAllEncodedQueryParameters(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedName == null");
            } else if (this.g == null) {
                return this;
            } else {
                a(HttpUrl.a(str, " \"'<>#&=", true, true));
                return this;
            }
        }

        private void a(String str) {
            for (int size = this.g.size() - 2; size >= 0; size -= 2) {
                if (str.equals(this.g.get(size))) {
                    this.g.remove(size + 1);
                    this.g.remove(size);
                    if (this.g.isEmpty()) {
                        this.g = null;
                        return;
                    }
                }
            }
        }

        public Builder fragment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("fragment == null");
            }
            this.h = HttpUrl.a(str, "", false, false);
            return this;
        }

        public Builder encodedFragment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedFragment == null");
            }
            this.h = HttpUrl.a(str, "", true, false);
            return this;
        }

        public HttpUrl build() {
            if (this.a == null) {
                throw new IllegalStateException("scheme == null");
            } else if (this.d != null) {
                return new HttpUrl(this);
            } else {
                throw new IllegalStateException("host == null");
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("://");
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                sb.append(this.b);
                if (!this.c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.c);
                }
                sb.append('@');
            }
            if (this.d.indexOf(58) != -1) {
                sb.append('[');
                sb.append(this.d);
                sb.append(']');
            } else {
                sb.append(this.d);
            }
            int a2 = a();
            if (a2 != HttpUrl.defaultPort(this.a)) {
                sb.append(':');
                sb.append(a2);
            }
            HttpUrl.a(sb, this.f);
            if (this.g != null) {
                sb.append('?');
                HttpUrl.b(sb, this.g);
            }
            if (this.h != null) {
                sb.append('#');
                sb.append(this.h);
            }
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public HttpUrl a(HttpUrl httpUrl, String str) {
            int i;
            String str2 = str;
            boolean z = false;
            int b2 = b(str2, 0, str.length());
            int c2 = c(str2, b2, str.length());
            if (d(str2, b2, c2) != -1) {
                if (str2.regionMatches(true, b2, "https:", 0, 6)) {
                    this.a = "https";
                    b2 += "https:".length();
                } else {
                    if (!str2.regionMatches(true, b2, "http:", 0, 5)) {
                        return null;
                    }
                    this.a = HttpHost.DEFAULT_SCHEME_NAME;
                    b2 += "http:".length();
                }
            } else if (httpUrl == null) {
                return null;
            } else {
                this.a = httpUrl.b;
            }
            int e2 = e(str2, b2, c2);
            if (e2 >= 2 || httpUrl == null || !httpUrl.b.equals(this.a)) {
                int i2 = b2 + e2;
                boolean z2 = false;
                while (true) {
                    i = HttpUrl.b(str2, i2, c2, "@/\\?#");
                    char charAt = i != c2 ? str2.charAt(i) : 65535;
                    if (!(charAt == 65535 || charAt == '#' || charAt == '/' || charAt == '\\')) {
                        switch (charAt) {
                            case '?':
                                break;
                            case '@':
                                if (!z) {
                                    int a2 = HttpUrl.b(str2, i2, i, ":");
                                    int i3 = a2;
                                    String a3 = HttpUrl.a(str2, i2, a2, " \"':;<=>@[]^`{}|/\\?#", true, false);
                                    if (z2) {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(this.b);
                                        sb.append("%40");
                                        sb.append(a3);
                                        a3 = sb.toString();
                                    }
                                    this.b = a3;
                                    if (i3 != i) {
                                        this.c = HttpUrl.a(str2, i3 + 1, i, " \"':;<=>@[]\\^`{}|/\\?#", true, false);
                                        z = true;
                                    }
                                    z2 = true;
                                } else {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(this.c);
                                    sb2.append("%40");
                                    sb2.append(HttpUrl.a(str2, i2, i, " \"':;<=>@[]\\^`{}|/\\?#", true, false));
                                    this.c = sb2.toString();
                                }
                                i2 = i + 1;
                                continue;
                        }
                    }
                }
                int f2 = f(str2, i2, i);
                int i4 = f2 + 1;
                if (i4 < i) {
                    this.d = g(str2, i2, f2);
                    this.e = i(str2, i4, i);
                    if (this.e == -1) {
                        return null;
                    }
                } else {
                    this.d = g(str2, i2, f2);
                    this.e = HttpUrl.defaultPort(this.a);
                }
                if (this.d == null) {
                    return null;
                }
            } else {
                this.b = httpUrl.encodedUsername();
                this.c = httpUrl.encodedPassword();
                this.d = httpUrl.e;
                this.e = httpUrl.f;
                this.f.clear();
                this.f.addAll(httpUrl.encodedPathSegments());
                if (b2 == c2 || str2.charAt(b2) == '#') {
                    encodedQuery(httpUrl.encodedQuery());
                }
                i = b2;
            }
            int a4 = HttpUrl.b(str2, i, c2, "?#");
            a(str2, i, a4);
            if (a4 < c2 && str2.charAt(a4) == '?') {
                int a5 = HttpUrl.b(str2, a4, c2, "#");
                this.g = HttpUrl.a(HttpUrl.a(str2, a4 + 1, a5, " \"'<>#", true, true));
                a4 = a5;
            }
            if (a4 < c2 && str2.charAt(a4) == '#') {
                this.h = HttpUrl.a(str2, 1 + a4, c2, "", true, false);
            }
            return build();
        }

        private void a(String str, int i, int i2) {
            if (i != i2) {
                char charAt = str.charAt(i);
                if (charAt == '/' || charAt == '\\') {
                    this.f.clear();
                    this.f.add("");
                    i++;
                } else {
                    this.f.set(this.f.size() - 1, "");
                }
                while (true) {
                    int i3 = r11;
                    if (i3 < i2) {
                        r11 = HttpUrl.b(str, i3, i2, "/\\");
                        boolean z = r11 < i2;
                        a(str, i3, r11, z, true);
                        if (z) {
                            r11++;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        private void a(String str, int i, int i2, boolean z, boolean z2) {
            String a2 = HttpUrl.a(str, i, i2, " \"<>^`{}|/\\?#", z2, false);
            if (!b(a2)) {
                if (c(a2)) {
                    b();
                    return;
                }
                if (((String) this.f.get(this.f.size() - 1)).isEmpty()) {
                    this.f.set(this.f.size() - 1, a2);
                } else {
                    this.f.add(a2);
                }
                if (z) {
                    this.f.add("");
                }
            }
        }

        private boolean b(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }

        private boolean c(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        private void b() {
            if (!((String) this.f.remove(this.f.size() - 1)).isEmpty() || this.f.isEmpty()) {
                this.f.add("");
            } else {
                this.f.set(this.f.size() - 1, "");
            }
        }

        private int b(String str, int i, int i2) {
            while (i < i2) {
                switch (str.charAt(i)) {
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        i++;
                    default:
                        return i;
                }
            }
            return i2;
        }

        private int c(String str, int i, int i2) {
            int i3 = i2 - 1;
            while (i3 >= i) {
                switch (str.charAt(i3)) {
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case ' ':
                        i3--;
                    default:
                        return i3 + 1;
                }
            }
            return i;
        }

        private static int d(String str, int i, int i2) {
            if (i2 - i < 2) {
                return -1;
            }
            char charAt = str.charAt(i);
            if ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z')) {
                return -1;
            }
            while (true) {
                i++;
                if (i >= i2) {
                    return -1;
                }
                char charAt2 = str.charAt(i);
                if ((charAt2 < 'a' || charAt2 > 'z') && !((charAt2 >= 'A' && charAt2 <= 'Z') || charAt2 == '+' || charAt2 == '-' || charAt2 == '.')) {
                    if (charAt2 == ':') {
                        return i;
                    }
                    return -1;
                }
            }
        }

        private static int e(String str, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                char charAt = str.charAt(i);
                if (charAt != '\\' && charAt != '/') {
                    break;
                }
                i3++;
                i++;
            }
            return i3;
        }

        private static int f(String str, int i, int i2) {
            while (i < i2) {
                char charAt = str.charAt(i);
                if (charAt == ':') {
                    return i;
                }
                if (charAt == '[') {
                    do {
                        i++;
                        if (i >= i2) {
                            break;
                        }
                    } while (str.charAt(i) != ']');
                }
                i++;
            }
            return i2;
        }

        private static String g(String str, int i, int i2) {
            String a2 = HttpUrl.a(str, i, i2);
            String str2 = null;
            if (!a2.startsWith("[") || !a2.endsWith("]")) {
                String d2 = d(a2);
                if (d2 == null) {
                    return null;
                }
                int length = d2.length();
                if (HttpUrl.b(d2, 0, length, "\u0000\t\n\r #%/:?@[\\]") != length) {
                    return null;
                }
                return d2;
            }
            InetAddress h2 = h(a2, 1, a2.length() - 1);
            if (h2 != null) {
                str2 = h2.getHostAddress();
            }
            return str2;
        }

        private static InetAddress h(String str, int i, int i2) {
            byte[] bArr = new byte[16];
            int i3 = 0;
            int i4 = -1;
            int i5 = -1;
            while (true) {
                if (i >= i2) {
                    break;
                } else if (i3 == bArr.length) {
                    return null;
                } else {
                    int i6 = i + 2;
                    if (i6 > i2 || !str.regionMatches(i, "::", 0, 2)) {
                        if (i3 != 0) {
                            if (str.regionMatches(i, ":", 0, 1)) {
                                i++;
                            } else if (!str.regionMatches(i, ".", 0, 1) || !a(str, i5, i2, bArr, i3 - 2)) {
                                return null;
                            } else {
                                i3 += 2;
                            }
                        }
                        i5 = i;
                    } else if (i4 != -1) {
                        return null;
                    } else {
                        i3 += 2;
                        if (i6 == i2) {
                            i4 = i3;
                            break;
                        }
                        i4 = i3;
                        i5 = i6;
                    }
                    i = i5;
                    int i7 = 0;
                    while (i < i2) {
                        int a2 = HttpUrl.a(str.charAt(i));
                        if (a2 == -1) {
                            break;
                        }
                        i7 = (i7 << 4) + a2;
                        i++;
                    }
                    int i8 = i - i5;
                    if (i8 == 0 || i8 > 4) {
                        return null;
                    }
                    int i9 = i3 + 1;
                    bArr[i3] = (byte) ((i7 >>> 8) & 255);
                    i3 = i9 + 1;
                    bArr[i9] = (byte) (i7 & 255);
                }
            }
            if (i3 != bArr.length) {
                if (i4 == -1) {
                    return null;
                }
                int i10 = i3 - i4;
                System.arraycopy(bArr, i4, bArr, bArr.length - i10, i10);
                Arrays.fill(bArr, i4, (bArr.length - i3) + i4, 0);
            }
            try {
                return InetAddress.getByAddress(bArr);
            } catch (UnknownHostException unused) {
                throw new AssertionError();
            }
        }

        private static boolean a(String str, int i, int i2, byte[] bArr, int i3) {
            int i4 = i3;
            while (i < i2) {
                if (i4 == bArr.length) {
                    return false;
                }
                if (i4 != i3) {
                    if (str.charAt(i) != '.') {
                        return false;
                    }
                    i++;
                }
                int i5 = i;
                int i6 = 0;
                while (i5 < i2) {
                    char charAt = str.charAt(i5);
                    if (charAt < '0' || charAt > '9') {
                        break;
                    } else if (i6 == 0 && i != i5) {
                        return false;
                    } else {
                        i6 = ((i6 * 10) + charAt) - 48;
                        if (i6 > 255) {
                            return false;
                        }
                        i5++;
                    }
                }
                if (i5 - i == 0) {
                    return false;
                }
                int i7 = i4 + 1;
                bArr[i4] = (byte) i6;
                i4 = i7;
                i = i5;
            }
            if (i4 != i3 + 4) {
                return false;
            }
            return true;
        }

        private static String d(String str) {
            try {
                String lowerCase = IDN.toASCII(str).toLowerCase(Locale.US);
                if (lowerCase.isEmpty()) {
                    return null;
                }
                return lowerCase;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        private static int i(String str, int i, int i2) {
            try {
                int parseInt = Integer.parseInt(HttpUrl.a(str, i, i2, "", false, false));
                if (parseInt <= 0 || parseInt > 65535) {
                    return -1;
                }
                return parseInt;
            } catch (NumberFormatException unused) {
                return -1;
            }
        }
    }

    static int a(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - TarjetasConstants.ULT_NUM_AMEX;
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        if (c2 < 'A' || c2 > 'F') {
            return -1;
        }
        return (c2 - 'A') + 10;
    }

    private HttpUrl(Builder builder) {
        this.b = builder.a;
        this.c = b(builder.b);
        this.d = b(builder.c);
        this.e = builder.d;
        this.f = builder.a();
        this.g = a(builder.f);
        String str = null;
        this.h = builder.g != null ? a(builder.g) : null;
        if (builder.h != null) {
            str = b(builder.h);
        }
        this.i = str;
        this.j = builder.toString();
    }

    public URL url() {
        try {
            return new URL(this.j);
        } catch (MalformedURLException e2) {
            throw new RuntimeException(e2);
        }
    }

    public URI uri() {
        try {
            return new URI(this.j);
        } catch (URISyntaxException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("not valid as a java.net.URI: ");
            sb.append(this.j);
            throw new IllegalStateException(sb.toString());
        }
    }

    public String scheme() {
        return this.b;
    }

    public boolean isHttps() {
        return this.b.equals("https");
    }

    public String encodedUsername() {
        if (this.c.isEmpty()) {
            return "";
        }
        int length = this.b.length() + 3;
        return this.j.substring(length, b(this.j, length, this.j.length(), ":@"));
    }

    public String username() {
        return this.c;
    }

    public String encodedPassword() {
        if (this.d.isEmpty()) {
            return "";
        }
        return this.j.substring(this.j.indexOf(58, this.b.length() + 3) + 1, this.j.indexOf(64));
    }

    public String password() {
        return this.d;
    }

    public String host() {
        return this.e;
    }

    public int port() {
        return this.f;
    }

    public static int defaultPort(String str) {
        if (str.equals(HttpHost.DEFAULT_SCHEME_NAME)) {
            return 80;
        }
        return str.equals("https") ? 443 : -1;
    }

    public int pathSize() {
        return this.g.size();
    }

    public String encodedPath() {
        int indexOf = this.j.indexOf(47, this.b.length() + 3);
        return this.j.substring(indexOf, b(this.j, indexOf, this.j.length(), "?#"));
    }

    static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append('/');
            sb.append((String) list.get(i2));
        }
    }

    public List<String> encodedPathSegments() {
        int indexOf = this.j.indexOf(47, this.b.length() + 3);
        int b2 = b(this.j, indexOf, this.j.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (indexOf < b2) {
            int i2 = indexOf + 1;
            int b3 = b(this.j, i2, b2, "/");
            arrayList.add(this.j.substring(i2, b3));
            indexOf = b3;
        }
        return arrayList;
    }

    public List<String> pathSegments() {
        return this.g;
    }

    public String encodedQuery() {
        if (this.h == null) {
            return null;
        }
        int indexOf = this.j.indexOf(63) + 1;
        return this.j.substring(indexOf, b(this.j, indexOf + 1, this.j.length(), "#"));
    }

    static void b(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String str = (String) list.get(i2);
            String str2 = (String) list.get(i2 + 1);
            if (i2 > 0) {
                sb.append('&');
            }
            sb.append(str);
            if (str2 != null) {
                sb.append('=');
                sb.append(str2);
            }
        }
    }

    static List<String> a(String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 <= str.length()) {
            int indexOf = str.indexOf(38, i2);
            if (indexOf == -1) {
                indexOf = str.length();
            }
            int indexOf2 = str.indexOf(61, i2);
            if (indexOf2 == -1 || indexOf2 > indexOf) {
                arrayList.add(str.substring(i2, indexOf));
                arrayList.add(null);
            } else {
                arrayList.add(str.substring(i2, indexOf2));
                arrayList.add(str.substring(indexOf2 + 1, indexOf));
            }
            i2 = indexOf + 1;
        }
        return arrayList;
    }

    public String query() {
        if (this.h == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        b(sb, this.h);
        return sb.toString();
    }

    public int querySize() {
        if (this.h != null) {
            return this.h.size() / 2;
        }
        return 0;
    }

    public String queryParameter(String str) {
        if (this.h == null) {
            return null;
        }
        int size = this.h.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            if (str.equals(this.h.get(i2))) {
                return (String) this.h.get(i2 + 1);
            }
        }
        return null;
    }

    public Set<String> queryParameterNames() {
        if (this.h == null) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = this.h.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            linkedHashSet.add(this.h.get(i2));
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public List<String> queryParameterValues(String str) {
        if (this.h == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = this.h.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            if (str.equals(this.h.get(i2))) {
                arrayList.add(this.h.get(i2 + 1));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public String queryParameterName(int i2) {
        return (String) this.h.get(i2 * 2);
    }

    public String queryParameterValue(int i2) {
        return (String) this.h.get((i2 * 2) + 1);
    }

    public String encodedFragment() {
        if (this.i == null) {
            return null;
        }
        return this.j.substring(this.j.indexOf(35) + 1);
    }

    public String fragment() {
        return this.i;
    }

    public HttpUrl resolve(String str) {
        return new Builder().a(this, str);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.a = this.b;
        builder.b = encodedUsername();
        builder.c = encodedPassword();
        builder.d = this.e;
        builder.e = this.f;
        builder.f.clear();
        builder.f.addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.h = encodedFragment();
        return builder;
    }

    public static HttpUrl parse(String str) {
        return new Builder().a(null, str);
    }

    public static HttpUrl get(URL url) {
        return parse(url.toString());
    }

    public static HttpUrl get(URI uri) {
        return parse(uri.toString());
    }

    public boolean equals(Object obj) {
        return (obj instanceof HttpUrl) && ((HttpUrl) obj).j.equals(this.j);
    }

    public int hashCode() {
        return this.j.hashCode();
    }

    public String toString() {
        return this.j;
    }

    /* access modifiers changed from: private */
    public static int b(String str, int i2, int i3, String str2) {
        while (i2 < i3) {
            if (str2.indexOf(str.charAt(i2)) != -1) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    static String b(String str) {
        return a(str, 0, str.length());
    }

    private List<String> a(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (String str : list) {
            arrayList.add(str != null ? b(str) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    static String a(String str, int i2, int i3) {
        for (int i4 = i2; i4 < i3; i4++) {
            if (str.charAt(i4) == '%') {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, i4);
                a(buffer, str, i4, i3);
                return buffer.readUtf8();
            }
        }
        return str.substring(i2, i3);
    }

    static void a(Buffer buffer, String str, int i2, int i3) {
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (codePointAt == 37) {
                int i4 = i2 + 2;
                if (i4 < i3) {
                    int a2 = a(str.charAt(i2 + 1));
                    int a3 = a(str.charAt(i4));
                    if (!(a2 == -1 || a3 == -1)) {
                        buffer.writeByte((a2 << 4) + a3);
                        i2 = i4;
                        i2 += Character.charCount(codePointAt);
                    }
                }
            }
            buffer.writeUtf8CodePoint(codePointAt);
            i2 += Character.charCount(codePointAt);
        }
    }

    static String a(String str, int i2, int i3, String str2, boolean z, boolean z2) {
        int i4 = i2;
        while (i4 < i3) {
            int codePointAt = str.codePointAt(i4);
            if (codePointAt < 32 || codePointAt >= 127 || str2.indexOf(codePointAt) != -1 || ((codePointAt == 37 && !z) || (z2 && codePointAt == 43))) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, i4);
                a(buffer, str, i4, i3, str2, z, z2);
                return buffer.readUtf8();
            }
            i4 += Character.charCount(codePointAt);
        }
        return str.substring(i2, i3);
    }

    static void a(Buffer buffer, String str, int i2, int i3, String str2, boolean z, boolean z2) {
        Buffer buffer2 = null;
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (!z || !(codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13)) {
                if (z2 && codePointAt == 43) {
                    buffer.writeUtf8(z ? "%20" : "%2B");
                } else if (codePointAt < 32 || codePointAt >= 127 || str2.indexOf(codePointAt) != -1 || (codePointAt == 37 && !z)) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    buffer2.writeUtf8CodePoint(codePointAt);
                    while (!buffer2.exhausted()) {
                        byte readByte = buffer2.readByte() & UnsignedBytes.MAX_VALUE;
                        buffer.writeByte(37);
                        buffer.writeByte((int) a[(readByte >> 4) & 15]);
                        buffer.writeByte((int) a[readByte & Ascii.SI]);
                    }
                } else {
                    buffer.writeUtf8CodePoint(codePointAt);
                }
            }
            i2 += Character.charCount(codePointAt);
        }
    }

    static String a(String str, String str2, boolean z, boolean z2) {
        return a(str, 0, str.length(), str2, z, z2);
    }
}
