package okhttp3.internal.http2;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import org.bouncycastle.asn1.eac.CertificateBody;

final class Hpack {
    static final Header[] a = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, HttpHost.DEFAULT_SCHEME_NAME), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header(ClientCookie.EXPIRES_ATTR, ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
    static final Map<ByteString, Integer> b = a();

    static final class Reader {
        Header[] a;
        int b;
        int c;
        int d;
        private final List<Header> e;
        private final BufferedSource f;
        private final int g;
        private int h;

        Reader(int i, Source source) {
            this(i, i, source);
        }

        Reader(int i, int i2, Source source) {
            this.e = new ArrayList();
            this.a = new Header[8];
            this.b = this.a.length - 1;
            this.c = 0;
            this.d = 0;
            this.g = i;
            this.h = i2;
            this.f = Okio.buffer(source);
        }

        private void d() {
            if (this.h >= this.d) {
                return;
            }
            if (this.h == 0) {
                e();
            } else {
                a(this.d - this.h);
            }
        }

        private void e() {
            Arrays.fill(this.a, null);
            this.b = this.a.length - 1;
            this.c = 0;
            this.d = 0;
        }

        private int a(int i) {
            int i2 = 0;
            if (i > 0) {
                int length = this.a.length;
                while (true) {
                    length--;
                    if (length < this.b || i <= 0) {
                        System.arraycopy(this.a, this.b + 1, this.a, this.b + 1 + i2, this.c);
                        this.b += i2;
                    } else {
                        i -= this.a[length].a;
                        this.d -= this.a[length].a;
                        this.c--;
                        i2++;
                    }
                }
                System.arraycopy(this.a, this.b + 1, this.a, this.b + 1 + i2, this.c);
                this.b += i2;
            }
            return i2;
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            while (!this.f.exhausted()) {
                byte readByte = this.f.readByte() & UnsignedBytes.MAX_VALUE;
                if (readByte == 128) {
                    throw new IOException("index == 0");
                } else if ((readByte & UnsignedBytes.MAX_POWER_OF_TWO) == 128) {
                    b(a((int) readByte, (int) CertificateBody.profileType) - 1);
                } else if (readByte == 64) {
                    g();
                } else if ((readByte & SignedBytes.MAX_POWER_OF_TWO) == 64) {
                    e(a((int) readByte, 63) - 1);
                } else if ((readByte & 32) == 32) {
                    this.h = a((int) readByte, 31);
                    if (this.h < 0 || this.h > this.g) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Invalid dynamic table size update ");
                        sb.append(this.h);
                        throw new IOException(sb.toString());
                    }
                    d();
                } else if (readByte == 16 || readByte == 0) {
                    f();
                } else {
                    d(a((int) readByte, 15) - 1);
                }
            }
        }

        public List<Header> b() {
            ArrayList arrayList = new ArrayList(this.e);
            this.e.clear();
            return arrayList;
        }

        private void b(int i) {
            if (g(i)) {
                this.e.add(Hpack.a[i]);
                return;
            }
            int c2 = c(i - Hpack.a.length);
            if (c2 < 0 || c2 >= this.a.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Header index too large ");
                sb.append(i + 1);
                throw new IOException(sb.toString());
            }
            this.e.add(this.a[c2]);
        }

        private int c(int i) {
            return this.b + 1 + i;
        }

        private void d(int i) {
            this.e.add(new Header(f(i), c()));
        }

        private void f() {
            this.e.add(new Header(Hpack.a(c()), c()));
        }

        private void e(int i) {
            a(-1, new Header(f(i), c()));
        }

        private void g() {
            a(-1, new Header(Hpack.a(c()), c()));
        }

        private ByteString f(int i) {
            if (g(i)) {
                return Hpack.a[i].name;
            }
            int c2 = c(i - Hpack.a.length);
            if (c2 >= 0 && c2 < this.a.length) {
                return this.a[c2].name;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Header index too large ");
            sb.append(i + 1);
            throw new IOException(sb.toString());
        }

        private boolean g(int i) {
            return i >= 0 && i <= Hpack.a.length - 1;
        }

        private void a(int i, Header header) {
            this.e.add(header);
            int i2 = header.a;
            if (i != -1) {
                i2 -= this.a[c(i)].a;
            }
            if (i2 > this.h) {
                e();
                return;
            }
            int a2 = a((this.d + i2) - this.h);
            if (i == -1) {
                if (this.c + 1 > this.a.length) {
                    Header[] headerArr = new Header[(this.a.length * 2)];
                    System.arraycopy(this.a, 0, headerArr, this.a.length, this.a.length);
                    this.b = this.a.length - 1;
                    this.a = headerArr;
                }
                int i3 = this.b;
                this.b = i3 - 1;
                this.a[i3] = header;
                this.c++;
            } else {
                this.a[i + c(i) + a2] = header;
            }
            this.d += i2;
        }

        private int h() {
            return this.f.readByte() & UnsignedBytes.MAX_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i, int i2) {
            int i3 = i & i2;
            if (i3 < i2) {
                return i3;
            }
            int i4 = 0;
            while (true) {
                int h2 = h();
                if ((h2 & 128) == 0) {
                    return i2 + (h2 << i4);
                }
                i2 += (h2 & CertificateBody.profileType) << i4;
                i4 += 7;
            }
        }

        /* access modifiers changed from: 0000 */
        public ByteString c() {
            int h2 = h();
            boolean z = (h2 & 128) == 128;
            int a2 = a(h2, (int) CertificateBody.profileType);
            if (z) {
                return ByteString.of(Huffman.a().a(this.f.readByteArray((long) a2)));
            }
            return this.f.readByteString((long) a2);
        }
    }

    static final class Writer {
        int a;
        int b;
        Header[] c;
        int d;
        int e;
        int f;
        private final Buffer g;
        private final boolean h;
        private int i;
        private boolean j;

        Writer(Buffer buffer) {
            this(4096, true, buffer);
        }

        Writer(int i2, boolean z, Buffer buffer) {
            this.i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            this.c = new Header[8];
            this.d = this.c.length - 1;
            this.e = 0;
            this.f = 0;
            this.a = i2;
            this.b = i2;
            this.h = z;
            this.g = buffer;
        }

        private void a() {
            Arrays.fill(this.c, null);
            this.d = this.c.length - 1;
            this.e = 0;
            this.f = 0;
        }

        private int b(int i2) {
            int i3 = 0;
            if (i2 > 0) {
                int length = this.c.length;
                while (true) {
                    length--;
                    if (length < this.d || i2 <= 0) {
                        System.arraycopy(this.c, this.d + 1, this.c, this.d + 1 + i3, this.e);
                        Arrays.fill(this.c, this.d + 1, this.d + 1 + i3, null);
                        this.d += i3;
                    } else {
                        i2 -= this.c[length].a;
                        this.f -= this.c[length].a;
                        this.e--;
                        i3++;
                    }
                }
                System.arraycopy(this.c, this.d + 1, this.c, this.d + 1 + i3, this.e);
                Arrays.fill(this.c, this.d + 1, this.d + 1 + i3, null);
                this.d += i3;
            }
            return i3;
        }

        private void a(Header header) {
            int i2 = header.a;
            if (i2 > this.b) {
                a();
                return;
            }
            b((this.f + i2) - this.b);
            if (this.e + 1 > this.c.length) {
                Header[] headerArr = new Header[(this.c.length * 2)];
                System.arraycopy(this.c, 0, headerArr, this.c.length, this.c.length);
                this.d = this.c.length - 1;
                this.c = headerArr;
            }
            int i3 = this.d;
            this.d = i3 - 1;
            this.c[i3] = header;
            this.e++;
            this.f += i2;
        }

        /* access modifiers changed from: 0000 */
        public void a(List<Header> list) {
            int i2;
            int i3;
            if (this.j) {
                if (this.i < this.b) {
                    a(this.i, 31, 32);
                }
                this.j = false;
                this.i = SubsamplingScaleImageView.TILE_SIZE_AUTO;
                a(this.b, 31, 32);
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                Header header = (Header) list.get(i4);
                ByteString asciiLowercase = header.name.toAsciiLowercase();
                ByteString byteString = header.value;
                Integer num = (Integer) Hpack.b.get(asciiLowercase);
                if (num != null) {
                    i3 = num.intValue() + 1;
                    if (i3 > 1 && i3 < 8) {
                        if (Util.equal(Hpack.a[i3 - 1].value, byteString)) {
                            i2 = i3;
                        } else if (Util.equal(Hpack.a[i3].value, byteString)) {
                            i2 = i3;
                            i3++;
                        }
                    }
                    i2 = i3;
                    i3 = -1;
                } else {
                    i3 = -1;
                    i2 = -1;
                }
                if (i3 == -1) {
                    int i5 = this.d + 1;
                    int length = this.c.length;
                    while (true) {
                        if (i5 >= length) {
                            break;
                        }
                        if (Util.equal(this.c[i5].name, asciiLowercase)) {
                            if (Util.equal(this.c[i5].value, byteString)) {
                                i3 = Hpack.a.length + (i5 - this.d);
                                break;
                            } else if (i2 == -1) {
                                i2 = (i5 - this.d) + Hpack.a.length;
                            }
                        }
                        i5++;
                    }
                }
                if (i3 != -1) {
                    a(i3, CertificateBody.profileType, 128);
                } else if (i2 == -1) {
                    this.g.writeByte(64);
                    a(asciiLowercase);
                    a(byteString);
                    a(header);
                } else if (!asciiLowercase.startsWith(Header.PSEUDO_PREFIX) || Header.TARGET_AUTHORITY.equals(asciiLowercase)) {
                    a(i2, 63, 64);
                    a(byteString);
                    a(header);
                } else {
                    a(i2, 15, 0);
                    a(byteString);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2, int i3, int i4) {
            if (i2 < i3) {
                this.g.writeByte(i2 | i4);
                return;
            }
            this.g.writeByte(i4 | i3);
            int i5 = i2 - i3;
            while (i5 >= 128) {
                this.g.writeByte(128 | (i5 & CertificateBody.profileType));
                i5 >>>= 7;
            }
            this.g.writeByte(i5);
        }

        /* access modifiers changed from: 0000 */
        public void a(ByteString byteString) {
            if (!this.h || Huffman.a().a(byteString) >= byteString.size()) {
                a(byteString.size(), CertificateBody.profileType, 0);
                this.g.write(byteString);
                return;
            }
            Buffer buffer = new Buffer();
            Huffman.a().a(byteString, buffer);
            ByteString readByteString = buffer.readByteString();
            a(readByteString.size(), CertificateBody.profileType, 128);
            this.g.write(readByteString);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i2) {
            this.a = i2;
            int min = Math.min(i2, 16384);
            if (this.b != min) {
                if (min < this.b) {
                    this.i = Math.min(this.i, min);
                }
                this.j = true;
                this.b = min;
                b();
            }
        }

        private void b() {
            if (this.b >= this.f) {
                return;
            }
            if (this.b == 0) {
                a();
            } else {
                b(this.f - this.b);
            }
        }
    }

    private Hpack() {
    }

    private static Map<ByteString, Integer> a() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(a.length);
        for (int i = 0; i < a.length; i++) {
            if (!linkedHashMap.containsKey(a[i].name)) {
                linkedHashMap.put(a[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    static ByteString a(ByteString byteString) {
        int size = byteString.size();
        int i = 0;
        while (i < size) {
            byte b2 = byteString.getByte(i);
            if (b2 < 65 || b2 > 90) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("PROTOCOL_ERROR response malformed: mixed case name: ");
                sb.append(byteString.utf8());
                throw new IOException(sb.toString());
            }
        }
        return byteString;
    }
}
