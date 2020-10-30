package okhttp3;

import com.google.common.base.Ascii;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBody extends RequestBody {
    public static final MediaType ALTERNATIVE = MediaType.get("multipart/alternative");
    public static final MediaType DIGEST = MediaType.get("multipart/digest");
    public static final MediaType FORM = MediaType.get("multipart/form-data");
    public static final MediaType MIXED = MediaType.get("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.get("multipart/parallel");
    private static final byte[] a = {58, 32};
    private static final byte[] b = {Ascii.CR, 10};
    private static final byte[] c = {45, 45};
    private final ByteString d;
    private final MediaType e;
    private final MediaType f;
    private final List<Part> g;
    private long h = -1;

    public static final class Builder {
        private final ByteString a;
        private MediaType b;
        private final List<Part> c;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder(String str) {
            this.b = MultipartBody.MIXED;
            this.c = new ArrayList();
            this.a = ByteString.encodeUtf8(str);
        }

        public Builder setType(MediaType mediaType) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            } else if (!mediaType.type().equals("multipart")) {
                StringBuilder sb = new StringBuilder();
                sb.append("multipart != ");
                sb.append(mediaType);
                throw new IllegalArgumentException(sb.toString());
            } else {
                this.b = mediaType;
                return this;
            }
        }

        public Builder addPart(RequestBody requestBody) {
            return addPart(Part.create(requestBody));
        }

        public Builder addPart(@Nullable Headers headers, RequestBody requestBody) {
            return addPart(Part.create(headers, requestBody));
        }

        public Builder addFormDataPart(String str, String str2) {
            return addPart(Part.createFormData(str, str2));
        }

        public Builder addFormDataPart(String str, @Nullable String str2, RequestBody requestBody) {
            return addPart(Part.createFormData(str, str2, requestBody));
        }

        public Builder addPart(Part part) {
            if (part == null) {
                throw new NullPointerException("part == null");
            }
            this.c.add(part);
            return this;
        }

        public MultipartBody build() {
            if (!this.c.isEmpty()) {
                return new MultipartBody(this.a, this.b, this.c);
            }
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
    }

    public static final class Part {
        @Nullable
        final Headers a;
        final RequestBody b;

        public static Part create(RequestBody requestBody) {
            return create(null, requestBody);
        }

        public static Part create(@Nullable Headers headers, RequestBody requestBody) {
            if (requestBody == null) {
                throw new NullPointerException("body == null");
            } else if (headers != null && headers.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            } else if (headers == null || headers.get("Content-Length") == null) {
                return new Part(headers, requestBody);
            } else {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
        }

        public static Part createFormData(String str, String str2) {
            return createFormData(str, null, RequestBody.create((MediaType) null, str2));
        }

        public static Part createFormData(String str, @Nullable String str2, RequestBody requestBody) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            StringBuilder sb = new StringBuilder("form-data; name=");
            MultipartBody.a(sb, str);
            if (str2 != null) {
                sb.append("; filename=");
                MultipartBody.a(sb, str2);
            }
            return create(Headers.of("Content-Disposition", sb.toString()), requestBody);
        }

        private Part(@Nullable Headers headers, RequestBody requestBody) {
            this.a = headers;
            this.b = requestBody;
        }

        @Nullable
        public Headers headers() {
            return this.a;
        }

        public RequestBody body() {
            return this.b;
        }
    }

    MultipartBody(ByteString byteString, MediaType mediaType, List<Part> list) {
        this.d = byteString;
        this.e = mediaType;
        StringBuilder sb = new StringBuilder();
        sb.append(mediaType);
        sb.append("; boundary=");
        sb.append(byteString.utf8());
        this.f = MediaType.get(sb.toString());
        this.g = Util.immutableList(list);
    }

    public MediaType type() {
        return this.e;
    }

    public String boundary() {
        return this.d.utf8();
    }

    public int size() {
        return this.g.size();
    }

    public List<Part> parts() {
        return this.g;
    }

    public Part part(int i) {
        return (Part) this.g.get(i);
    }

    public MediaType contentType() {
        return this.f;
    }

    public long contentLength() {
        long j = this.h;
        if (j != -1) {
            return j;
        }
        long a2 = a((BufferedSink) null, true);
        this.h = a2;
        return a2;
    }

    public void writeTo(BufferedSink bufferedSink) {
        a(bufferedSink, false);
    }

    /* JADX WARNING: type inference failed for: r13v1, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r13v3, types: [okio.Buffer] */
    /* JADX WARNING: type inference failed for: r13v4 */
    /* JADX WARNING: type inference failed for: r13v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long a(@javax.annotation.Nullable okio.BufferedSink r13, boolean r14) {
        /*
            r12 = this;
            if (r14 == 0) goto L_0x0009
            okio.Buffer r13 = new okio.Buffer
            r13.<init>()
            r0 = r13
            goto L_0x000a
        L_0x0009:
            r0 = 0
        L_0x000a:
            java.util.List<okhttp3.MultipartBody$Part> r1 = r12.g
            int r1 = r1.size()
            r2 = 0
            r3 = 0
            r4 = r3
            r3 = 0
        L_0x0015:
            if (r3 >= r1) goto L_0x00a9
            java.util.List<okhttp3.MultipartBody$Part> r6 = r12.g
            java.lang.Object r6 = r6.get(r3)
            okhttp3.MultipartBody$Part r6 = (okhttp3.MultipartBody.Part) r6
            okhttp3.Headers r7 = r6.a
            okhttp3.RequestBody r6 = r6.b
            byte[] r8 = c
            r13.write(r8)
            okio.ByteString r8 = r12.d
            r13.write(r8)
            byte[] r8 = b
            r13.write(r8)
            if (r7 == 0) goto L_0x0059
            int r8 = r7.size()
            r9 = 0
        L_0x0039:
            if (r9 >= r8) goto L_0x0059
            java.lang.String r10 = r7.name(r9)
            okio.BufferedSink r10 = r13.writeUtf8(r10)
            byte[] r11 = a
            okio.BufferedSink r10 = r10.write(r11)
            java.lang.String r11 = r7.value(r9)
            okio.BufferedSink r10 = r10.writeUtf8(r11)
            byte[] r11 = b
            r10.write(r11)
            int r9 = r9 + 1
            goto L_0x0039
        L_0x0059:
            okhttp3.MediaType r7 = r6.contentType()
            if (r7 == 0) goto L_0x0072
            java.lang.String r8 = "Content-Type: "
            okio.BufferedSink r8 = r13.writeUtf8(r8)
            java.lang.String r7 = r7.toString()
            okio.BufferedSink r7 = r8.writeUtf8(r7)
            byte[] r8 = b
            r7.write(r8)
        L_0x0072:
            long r7 = r6.contentLength()
            r9 = -1
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 == 0) goto L_0x008c
            java.lang.String r9 = "Content-Length: "
            okio.BufferedSink r9 = r13.writeUtf8(r9)
            okio.BufferedSink r9 = r9.writeDecimalLong(r7)
            byte[] r10 = b
            r9.write(r10)
            goto L_0x0092
        L_0x008c:
            if (r14 == 0) goto L_0x0092
            r0.clear()
            return r9
        L_0x0092:
            byte[] r9 = b
            r13.write(r9)
            if (r14 == 0) goto L_0x009d
            long r9 = r4 + r7
            r4 = r9
            goto L_0x00a0
        L_0x009d:
            r6.writeTo(r13)
        L_0x00a0:
            byte[] r6 = b
            r13.write(r6)
            int r3 = r3 + 1
            goto L_0x0015
        L_0x00a9:
            byte[] r1 = c
            r13.write(r1)
            okio.ByteString r1 = r12.d
            r13.write(r1)
            byte[] r1 = c
            r13.write(r1)
            byte[] r1 = b
            r13.write(r1)
            if (r14 == 0) goto L_0x00c9
            long r13 = r0.size()
            long r1 = r4 + r13
            r0.clear()
            goto L_0x00ca
        L_0x00c9:
            r1 = r4
        L_0x00ca:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.MultipartBody.a(okio.BufferedSink, boolean):long");
    }

    static StringBuilder a(StringBuilder sb, String str) {
        sb.append(TokenParser.DQUOTE);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == 10) {
                sb.append("%0A");
            } else if (charAt == 13) {
                sb.append("%0D");
            } else if (charAt != '\"') {
                sb.append(charAt);
            } else {
                sb.append("%22");
            }
        }
        sb.append(TokenParser.DQUOTE);
        return sb;
    }
}
