package com.squareup.okhttp.internal.spdy;

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
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import org.bouncycastle.asn1.eac.CertificateBody;

final class Hpack {
    /* access modifiers changed from: private */
    public static final Header[] a = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, HttpHost.DEFAULT_SCHEME_NAME), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header(ClientCookie.EXPIRES_ATTR, ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
    /* access modifiers changed from: private */
    public static final Map<ByteString, Integer> b = c();

    static final class Reader {
        Header[] a = new Header[8];
        int b = (this.a.length - 1);
        int c = 0;
        int d = 0;
        private final List<Header> e = new ArrayList();
        private final BufferedSource f;
        private int g;
        private int h;

        Reader(int i, Source source) {
            this.g = i;
            this.h = i;
            this.f = Okio.buffer(source);
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            this.g = i;
            this.h = i;
            d();
        }

        private void d() {
            if (this.h >= this.d) {
                return;
            }
            if (this.h == 0) {
                e();
            } else {
                b(this.d - this.h);
            }
        }

        private void e() {
            this.e.clear();
            Arrays.fill(this.a, null);
            this.b = this.a.length - 1;
            this.c = 0;
            this.d = 0;
        }

        private int b(int i) {
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
                    c(a((int) readByte, (int) CertificateBody.profileType) - 1);
                } else if (readByte == 64) {
                    g();
                } else if ((readByte & SignedBytes.MAX_POWER_OF_TWO) == 64) {
                    f(a((int) readByte, 63) - 1);
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
                    e(a((int) readByte, 15) - 1);
                }
            }
        }

        public List<Header> b() {
            ArrayList arrayList = new ArrayList(this.e);
            this.e.clear();
            return arrayList;
        }

        private void c(int i) {
            if (h(i)) {
                this.e.add(Hpack.a[i]);
                return;
            }
            int d2 = d(i - Hpack.a.length);
            if (d2 < 0 || d2 > this.a.length - 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Header index too large ");
                sb.append(i + 1);
                throw new IOException(sb.toString());
            }
            this.e.add(this.a[d2]);
        }

        private int d(int i) {
            return this.b + 1 + i;
        }

        private void e(int i) {
            this.e.add(new Header(g(i), c()));
        }

        private void f() {
            this.e.add(new Header(Hpack.b(c()), c()));
        }

        private void f(int i) {
            a(-1, new Header(g(i), c()));
        }

        private void g() {
            a(-1, new Header(Hpack.b(c()), c()));
        }

        private ByteString g(int i) {
            if (h(i)) {
                return Hpack.a[i].name;
            }
            return this.a[d(i - Hpack.a.length)].name;
        }

        private boolean h(int i) {
            return i >= 0 && i <= Hpack.a.length - 1;
        }

        private void a(int i, Header header) {
            this.e.add(header);
            int i2 = header.a;
            if (i != -1) {
                i2 -= this.a[d(i)].a;
            }
            if (i2 > this.h) {
                e();
                return;
            }
            int b2 = b((this.d + i2) - this.h);
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
                this.a[i + d(i) + b2] = header;
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
        private final Buffer a;

        Writer(Buffer buffer) {
            this.a = buffer;
        }

        /* access modifiers changed from: 0000 */
        public void a(List<Header> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ByteString asciiLowercase = ((Header) list.get(i)).name.toAsciiLowercase();
                Integer num = (Integer) Hpack.b.get(asciiLowercase);
                if (num != null) {
                    a(num.intValue() + 1, 15, 0);
                    a(((Header) list.get(i)).value);
                } else {
                    this.a.writeByte(0);
                    a(asciiLowercase);
                    a(((Header) list.get(i)).value);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, int i2, int i3) {
            if (i < i2) {
                this.a.writeByte(i | i3);
                return;
            }
            this.a.writeByte(i3 | i2);
            int i4 = i - i2;
            while (i4 >= 128) {
                this.a.writeByte(128 | (i4 & CertificateBody.profileType));
                i4 >>>= 7;
            }
            this.a.writeByte(i4);
        }

        /* access modifiers changed from: 0000 */
        public void a(ByteString byteString) {
            a(byteString.size(), CertificateBody.profileType, 0);
            this.a.write(byteString);
        }
    }

    private Hpack() {
    }

    private static Map<ByteString, Integer> c() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(a.length);
        for (int i = 0; i < a.length; i++) {
            if (!linkedHashMap.containsKey(a[i].name)) {
                linkedHashMap.put(a[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    /* access modifiers changed from: private */
    public static ByteString b(ByteString byteString) {
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
