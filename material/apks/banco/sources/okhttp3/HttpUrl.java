package okhttp3;

import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.message.TokenParser;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;

public final class HttpUrl {
    private static final char[] d = {TarjetasConstants.ULT_NUM_AMEX, '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final String a;
    final String b;
    final int c;
    private final String e;
    private final String f;
    private final List<String> g;
    @Nullable
    private final List<String> h;
    @Nullable
    private final String i;
    private final String j;

    public static final class Builder {
        @Nullable
        String a;
        String b = "";
        String c = "";
        @Nullable
        String d;
        int e = -1;
        final List<String> f = new ArrayList();
        @Nullable
        List<String> g;
        @Nullable
        String h;

        public Builder() {
            this.f.add("");
        }

        public Builder scheme(String str) {
            if (str == null) {
                throw new NullPointerException("scheme == null");
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
                throw new NullPointerException("username == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        public Builder encodedUsername(String str) {
            if (str == null) {
                throw new NullPointerException("encodedUsername == null");
            }
            this.b = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder password(String str) {
            if (str == null) {
                throw new NullPointerException("password == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        public Builder encodedPassword(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPassword == null");
            }
            this.c = HttpUrl.a(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder host(String str) {
            if (str == null) {
                throw new NullPointerException("host == null");
            }
            String e2 = e(str, 0, str.length());
            if (e2 == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("unexpected host: ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            }
            this.d = e2;
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
                throw new NullPointerException("pathSegment == null");
            }
            a(str, 0, str.length(), false, false);
            return this;
        }

        public Builder addPathSegments(String str) {
            if (str != null) {
                return a(str, false);
            }
            throw new NullPointerException("pathSegments == null");
        }

        public Builder addEncodedPathSegment(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegment == null");
            }
            a(str, 0, str.length(), false, true);
            return this;
        }

        public Builder addEncodedPathSegments(String str) {
            if (str != null) {
                return a(str, true);
            }
            throw new NullPointerException("encodedPathSegments == null");
        }

        private Builder a(String str, boolean z) {
            int i = 0;
            do {
                int delimiterOffset = Util.delimiterOffset(str, i, str.length(), "/\\");
                a(str, i, delimiterOffset, delimiterOffset < str.length(), z);
                i = delimiterOffset + 1;
            } while (i <= str.length());
            return this;
        }

        public Builder setPathSegment(int i, String str) {
            if (str == null) {
                throw new NullPointerException("pathSegment == null");
            }
            String a2 = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", false, false, false, true, null);
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
                throw new NullPointerException("encodedPathSegment == null");
            }
            String a2 = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", true, false, false, true, null);
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
                throw new NullPointerException("encodedPath == null");
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

        public Builder query(@Nullable String str) {
            List<String> list;
            if (str != null) {
                list = HttpUrl.a(HttpUrl.a(str, " \"'<>#", false, false, true, true));
            } else {
                list = null;
            }
            this.g = list;
            return this;
        }

        public Builder encodedQuery(@Nullable String str) {
            List<String> list;
            if (str != null) {
                list = HttpUrl.a(HttpUrl.a(str, " \"'<>#", true, false, true, true));
            } else {
                list = null;
            }
            this.g = list;
            return this;
        }

        public Builder addQueryParameter(String str, @Nullable String str2) {
            String str3;
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
            List<String> list = this.g;
            if (str2 != null) {
                str3 = HttpUrl.a(str2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true);
            } else {
                str3 = null;
            }
            list.add(str3);
            return this;
        }

        public Builder addEncodedQueryParameter(String str, @Nullable String str2) {
            String str3;
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(HttpUrl.a(str, " \"'<>#&=", true, false, true, true));
            List<String> list = this.g;
            if (str2 != null) {
                str3 = HttpUrl.a(str2, " \"'<>#&=", true, false, true, true);
            } else {
                str3 = null;
            }
            list.add(str3);
            return this;
        }

        public Builder setQueryParameter(String str, @Nullable String str2) {
            removeAllQueryParameters(str);
            addQueryParameter(str, str2);
            return this;
        }

        public Builder setEncodedQueryParameter(String str, @Nullable String str2) {
            removeAllEncodedQueryParameters(str);
            addEncodedQueryParameter(str, str2);
            return this;
        }

        public Builder removeAllQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            } else if (this.g == null) {
                return this;
            } else {
                a(HttpUrl.a(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
                return this;
            }
        }

        public Builder removeAllEncodedQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            } else if (this.g == null) {
                return this;
            } else {
                a(HttpUrl.a(str, " \"'<>#&=", true, false, true, true));
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

        public Builder fragment(@Nullable String str) {
            String str2;
            if (str != null) {
                str2 = HttpUrl.a(str, "", false, false, false, false);
            } else {
                str2 = null;
            }
            this.h = str2;
            return this;
        }

        public Builder encodedFragment(@Nullable String str) {
            String str2;
            if (str != null) {
                str2 = HttpUrl.a(str, "", true, false, false, false);
            } else {
                str2 = null;
            }
            this.h = str2;
            return this;
        }

        /* access modifiers changed from: 0000 */
        public Builder b() {
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                this.f.set(i, HttpUrl.a((String) this.f.get(i), "[]", true, true, false, true));
            }
            if (this.g != null) {
                int size2 = this.g.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str = (String) this.g.get(i2);
                    if (str != null) {
                        this.g.set(i2, HttpUrl.a(str, "\\^`{|}", true, true, true, true));
                    }
                }
            }
            if (this.h != null) {
                this.h = HttpUrl.a(this.h, " \"#<>\\^`{|}", true, true, false, false);
            }
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
        public Builder a(@Nullable HttpUrl httpUrl, String str) {
            int i;
            int i2;
            int delimiterOffset;
            int i3;
            HttpUrl httpUrl2 = httpUrl;
            String str2 = str;
            int skipLeadingAsciiWhitespace = Util.skipLeadingAsciiWhitespace(str2, 0, str.length());
            int skipTrailingAsciiWhitespace = Util.skipTrailingAsciiWhitespace(str2, skipLeadingAsciiWhitespace, str.length());
            int b2 = b(str2, skipLeadingAsciiWhitespace, skipTrailingAsciiWhitespace);
            if (b2 != -1) {
                if (str2.regionMatches(true, skipLeadingAsciiWhitespace, "https:", 0, 6)) {
                    this.a = "https";
                    skipLeadingAsciiWhitespace += "https:".length();
                } else {
                    if (str2.regionMatches(true, skipLeadingAsciiWhitespace, "http:", 0, 5)) {
                        this.a = HttpHost.DEFAULT_SCHEME_NAME;
                        skipLeadingAsciiWhitespace += "http:".length();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Expected URL scheme 'http' or 'https' but was '");
                        sb.append(str2.substring(0, b2));
                        sb.append("'");
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
            } else if (httpUrl2 != null) {
                this.a = httpUrl2.a;
            } else {
                throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
            }
            int c2 = c(str2, skipLeadingAsciiWhitespace, skipTrailingAsciiWhitespace);
            char c3 = '#';
            if (c2 >= 2 || httpUrl2 == null || !httpUrl2.a.equals(this.a)) {
                int i4 = skipLeadingAsciiWhitespace + c2;
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    delimiterOffset = Util.delimiterOffset(str2, i4, skipTrailingAsciiWhitespace, "@/\\?#");
                    char charAt = delimiterOffset != skipTrailingAsciiWhitespace ? str2.charAt(delimiterOffset) : 65535;
                    if (!(charAt == 65535 || charAt == c3 || charAt == '/' || charAt == '\\')) {
                        switch (charAt) {
                            case '?':
                                break;
                            case '@':
                                if (!z) {
                                    int delimiterOffset2 = Util.delimiterOffset(str2, i4, delimiterOffset, ':');
                                    int i5 = delimiterOffset2;
                                    i3 = delimiterOffset;
                                    String a2 = HttpUrl.a(str2, i4, delimiterOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                                    if (z2) {
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(this.b);
                                        sb2.append("%40");
                                        sb2.append(a2);
                                        a2 = sb2.toString();
                                    }
                                    this.b = a2;
                                    if (i5 != i3) {
                                        this.c = HttpUrl.a(str2, i5 + 1, i3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                                        z = true;
                                    }
                                    z2 = true;
                                } else {
                                    i3 = delimiterOffset;
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append(this.c);
                                    sb3.append("%40");
                                    sb3.append(HttpUrl.a(str2, i4, i3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null));
                                    this.c = sb3.toString();
                                }
                                i4 = i3 + 1;
                                continue;
                        }
                    }
                    c3 = '#';
                }
                i = delimiterOffset;
                int d2 = d(str2, i4, i);
                int i6 = d2 + 1;
                if (i6 < i) {
                    this.d = e(str2, i4, d2);
                    this.e = f(str2, i6, i);
                    if (this.e == -1) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("Invalid URL port: \"");
                        sb4.append(str2.substring(i6, i));
                        sb4.append(TokenParser.DQUOTE);
                        throw new IllegalArgumentException(sb4.toString());
                    }
                } else {
                    this.d = e(str2, i4, d2);
                    this.e = HttpUrl.defaultPort(this.a);
                }
                if (this.d == null) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Invalid URL host: \"");
                    sb5.append(str2.substring(i4, d2));
                    sb5.append(TokenParser.DQUOTE);
                    throw new IllegalArgumentException(sb5.toString());
                }
            } else {
                this.b = httpUrl.encodedUsername();
                this.c = httpUrl.encodedPassword();
                this.d = httpUrl2.b;
                this.e = httpUrl2.c;
                this.f.clear();
                this.f.addAll(httpUrl.encodedPathSegments());
                if (skipLeadingAsciiWhitespace == skipTrailingAsciiWhitespace || str2.charAt(skipLeadingAsciiWhitespace) == '#') {
                    encodedQuery(httpUrl.encodedQuery());
                }
                i = skipLeadingAsciiWhitespace;
            }
            int delimiterOffset3 = Util.delimiterOffset(str2, i, skipTrailingAsciiWhitespace, "?#");
            a(str2, i, delimiterOffset3);
            if (delimiterOffset3 >= skipTrailingAsciiWhitespace || str2.charAt(delimiterOffset3) != '?') {
                i2 = delimiterOffset3;
            } else {
                i2 = Util.delimiterOffset(str2, delimiterOffset3, skipTrailingAsciiWhitespace, '#');
                this.g = HttpUrl.a(HttpUrl.a(str2, delimiterOffset3 + 1, i2, " \"'<>#", true, false, true, true, null));
            }
            if (i2 < skipTrailingAsciiWhitespace && str2.charAt(i2) == '#') {
                this.h = HttpUrl.a(str2, i2 + 1, skipTrailingAsciiWhitespace, "", true, false, false, false, null);
            }
            return this;
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
                        r11 = Util.delimiterOffset(str, i3, i2, "/\\");
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
            String a2 = HttpUrl.a(str, i, i2, " \"<>^`{}|/\\?#", z2, false, false, true, null);
            if (!b(a2)) {
                if (c(a2)) {
                    c();
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

        private void c() {
            if (!((String) this.f.remove(this.f.size() - 1)).isEmpty() || this.f.isEmpty()) {
                this.f.add("");
            } else {
                this.f.set(this.f.size() - 1, "");
            }
        }

        private static int b(String str, int i, int i2) {
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
                if ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && !((charAt2 >= '0' && charAt2 <= '9') || charAt2 == '+' || charAt2 == '-' || charAt2 == '.'))) {
                    if (charAt2 == ':') {
                        return i;
                    }
                    return -1;
                }
            }
        }

        private static int c(String str, int i, int i2) {
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

        private static int d(String str, int i, int i2) {
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

        private static String e(String str, int i, int i2) {
            return Util.canonicalizeHost(HttpUrl.a(str, i, i2, false));
        }

        private static int f(String str, int i, int i2) {
            try {
                int parseInt = Integer.parseInt(HttpUrl.a(str, i, i2, "", false, false, false, true, null));
                if (parseInt <= 0 || parseInt > 65535) {
                    return -1;
                }
                return parseInt;
            } catch (NumberFormatException unused) {
                return -1;
            }
        }
    }

    HttpUrl(Builder builder) {
        this.a = builder.a;
        this.e = a(builder.b, false);
        this.f = a(builder.c, false);
        this.b = builder.d;
        this.c = builder.a();
        this.g = a(builder.f, false);
        String str = null;
        this.h = builder.g != null ? a(builder.g, true) : null;
        if (builder.h != null) {
            str = a(builder.h, false);
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
        String builder = newBuilder().b().toString();
        try {
            return new URI(builder);
        } catch (URISyntaxException e2) {
            try {
                return URI.create(builder.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
            } catch (Exception unused) {
                throw new RuntimeException(e2);
            }
        }
    }

    public String scheme() {
        return this.a;
    }

    public boolean isHttps() {
        return this.a.equals("https");
    }

    public String encodedUsername() {
        if (this.e.isEmpty()) {
            return "";
        }
        int length = this.a.length() + 3;
        return this.j.substring(length, Util.delimiterOffset(this.j, length, this.j.length(), ":@"));
    }

    public String username() {
        return this.e;
    }

    public String encodedPassword() {
        if (this.f.isEmpty()) {
            return "";
        }
        return this.j.substring(this.j.indexOf(58, this.a.length() + 3) + 1, this.j.indexOf(64));
    }

    public String password() {
        return this.f;
    }

    public String host() {
        return this.b;
    }

    public int port() {
        return this.c;
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
        int indexOf = this.j.indexOf(47, this.a.length() + 3);
        return this.j.substring(indexOf, Util.delimiterOffset(this.j, indexOf, this.j.length(), "?#"));
    }

    static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append('/');
            sb.append((String) list.get(i2));
        }
    }

    public List<String> encodedPathSegments() {
        int indexOf = this.j.indexOf(47, this.a.length() + 3);
        int delimiterOffset = Util.delimiterOffset(this.j, indexOf, this.j.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (indexOf < delimiterOffset) {
            int i2 = indexOf + 1;
            int delimiterOffset2 = Util.delimiterOffset(this.j, i2, delimiterOffset, '/');
            arrayList.add(this.j.substring(i2, delimiterOffset2));
            indexOf = delimiterOffset2;
        }
        return arrayList;
    }

    public List<String> pathSegments() {
        return this.g;
    }

    @Nullable
    public String encodedQuery() {
        if (this.h == null) {
            return null;
        }
        int indexOf = this.j.indexOf(63) + 1;
        return this.j.substring(indexOf, Util.delimiterOffset(this.j, indexOf, this.j.length(), '#'));
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

    @Nullable
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

    @Nullable
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
        if (this.h != null) {
            return (String) this.h.get(i2 * 2);
        }
        throw new IndexOutOfBoundsException();
    }

    public String queryParameterValue(int i2) {
        if (this.h != null) {
            return (String) this.h.get((i2 * 2) + 1);
        }
        throw new IndexOutOfBoundsException();
    }

    @Nullable
    public String encodedFragment() {
        if (this.i == null) {
            return null;
        }
        return this.j.substring(this.j.indexOf(35) + 1);
    }

    @Nullable
    public String fragment() {
        return this.i;
    }

    public String redact() {
        return newBuilder("/...").username("").password("").build().toString();
    }

    @Nullable
    public HttpUrl resolve(String str) {
        Builder newBuilder = newBuilder(str);
        if (newBuilder != null) {
            return newBuilder.build();
        }
        return null;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.a = this.a;
        builder.b = encodedUsername();
        builder.c = encodedPassword();
        builder.d = this.b;
        builder.e = this.c != defaultPort(this.a) ? this.c : -1;
        builder.f.clear();
        builder.f.addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.h = encodedFragment();
        return builder;
    }

    @Nullable
    public Builder newBuilder(String str) {
        try {
            return new Builder().a(this, str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    @Nullable
    public static HttpUrl parse(String str) {
        try {
            return get(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    public static HttpUrl get(String str) {
        return new Builder().a((HttpUrl) null, str).build();
    }

    @Nullable
    public static HttpUrl get(URL url) {
        return parse(url.toString());
    }

    @Nullable
    public static HttpUrl get(URI uri) {
        return parse(uri.toString());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof HttpUrl) && ((HttpUrl) obj).j.equals(this.j);
    }

    public int hashCode() {
        return this.j.hashCode();
    }

    public String toString() {
        return this.j;
    }

    @Nullable
    public String topPrivateDomain() {
        if (Util.verifyAsIpAddress(this.b)) {
            return null;
        }
        return PublicSuffixDatabase.get().getEffectiveTldPlusOne(this.b);
    }

    static String a(String str, boolean z) {
        return a(str, 0, str.length(), z);
    }

    private List<String> a(List<String> list, boolean z) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            String str = (String) list.get(i2);
            arrayList.add(str != null ? a(str, z) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    static String a(String str, int i2, int i3, boolean z) {
        for (int i4 = i2; i4 < i3; i4++) {
            char charAt = str.charAt(i4);
            if (charAt == '%' || (charAt == '+' && z)) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, i4);
                a(buffer, str, i4, i3, z);
                return buffer.readUtf8();
            }
        }
        return str.substring(i2, i3);
    }

    static void a(Buffer buffer, String str, int i2, int i3, boolean z) {
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (codePointAt == 37) {
                int i4 = i2 + 2;
                if (i4 < i3) {
                    int decodeHexDigit = Util.decodeHexDigit(str.charAt(i2 + 1));
                    int decodeHexDigit2 = Util.decodeHexDigit(str.charAt(i4));
                    if (!(decodeHexDigit == -1 || decodeHexDigit2 == -1)) {
                        buffer.writeByte((decodeHexDigit << 4) + decodeHexDigit2);
                        i2 = i4;
                        i2 += Character.charCount(codePointAt);
                    }
                    buffer.writeUtf8CodePoint(codePointAt);
                    i2 += Character.charCount(codePointAt);
                }
            }
            if (codePointAt == 43 && z) {
                buffer.writeByte(32);
                i2 += Character.charCount(codePointAt);
            }
            buffer.writeUtf8CodePoint(codePointAt);
            i2 += Character.charCount(codePointAt);
        }
    }

    static boolean a(String str, int i2, int i3) {
        int i4 = i2 + 2;
        if (i4 >= i3 || str.charAt(i2) != '%' || Util.decodeHexDigit(str.charAt(i2 + 1)) == -1 || Util.decodeHexDigit(str.charAt(i4)) == -1) {
            return false;
        }
        return true;
    }

    static String a(String str, int i2, int i3, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        String str3;
        String str4 = str;
        int i4 = i3;
        int i5 = i2;
        while (i5 < i4) {
            int codePointAt = str4.codePointAt(i5);
            if (codePointAt < 32 || codePointAt == 127 || (codePointAt >= 128 && z4)) {
                str3 = str2;
            } else {
                str3 = str2;
                if (str3.indexOf(codePointAt) == -1 && ((codePointAt != 37 || (z && (!z2 || a(str4, i5, i4)))) && (codePointAt != 43 || !z3))) {
                    i5 += Character.charCount(codePointAt);
                }
            }
            Buffer buffer = new Buffer();
            buffer.writeUtf8(str4, i2, i5);
            a(buffer, str4, i5, i4, str3, z, z2, z3, z4, charset);
            return buffer.readUtf8();
        }
        return str4.substring(i2, i4);
    }

    static void a(Buffer buffer, String str, int i2, int i3, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        Buffer buffer2 = null;
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (!z || !(codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13)) {
                if (codePointAt == 43 && z3) {
                    buffer.writeUtf8(z ? Constants.SYMBOL_POSITIVE : "%2B");
                } else if (codePointAt < 32 || codePointAt == 127 || ((codePointAt >= 128 && z4) || str2.indexOf(codePointAt) != -1 || (codePointAt == 37 && (!z || (z2 && !a(str, i2, i3)))))) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    if (charset == null || charset.equals(Util.UTF_8)) {
                        buffer2.writeUtf8CodePoint(codePointAt);
                    } else {
                        buffer2.writeString(str, i2, Character.charCount(codePointAt) + i2, charset);
                    }
                    while (!buffer2.exhausted()) {
                        byte readByte = buffer2.readByte() & UnsignedBytes.MAX_VALUE;
                        buffer.writeByte(37);
                        buffer.writeByte((int) d[(readByte >> 4) & 15]);
                        buffer.writeByte((int) d[readByte & Ascii.SI]);
                    }
                } else {
                    buffer.writeUtf8CodePoint(codePointAt);
                }
            }
            i2 += Character.charCount(codePointAt);
        }
    }

    static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, charset);
    }

    static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, null);
    }
}
