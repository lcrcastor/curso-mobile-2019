package okhttp3.internal.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.internal.Internal;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.http.HttpHeaders;

public final class CacheStrategy {
    @Nullable
    public final Response cacheResponse;
    @Nullable
    public final Request networkRequest;

    public static class Factory {
        final long a;
        final Request b;
        final Response c;
        private Date d;
        private String e;
        private Date f;
        private String g;
        private Date h;
        private long i;
        private long j;
        private String k;
        private int l = -1;

        public Factory(long j2, Request request, Response response) {
            this.a = j2;
            this.b = request;
            this.c = response;
            if (response != null) {
                this.i = response.sentRequestAtMillis();
                this.j = response.receivedResponseAtMillis();
                Headers headers = response.headers();
                int size = headers.size();
                for (int i2 = 0; i2 < size; i2++) {
                    String name = headers.name(i2);
                    String value = headers.value(i2);
                    if ("Date".equalsIgnoreCase(name)) {
                        this.d = HttpDate.parse(value);
                        this.e = value;
                    } else if ("Expires".equalsIgnoreCase(name)) {
                        this.h = HttpDate.parse(value);
                    } else if ("Last-Modified".equalsIgnoreCase(name)) {
                        this.f = HttpDate.parse(value);
                        this.g = value;
                    } else if ("ETag".equalsIgnoreCase(name)) {
                        this.k = value;
                    } else if ("Age".equalsIgnoreCase(name)) {
                        this.l = HttpHeaders.parseSeconds(value, -1);
                    }
                }
            }
        }

        public CacheStrategy get() {
            CacheStrategy a2 = a();
            return (a2.networkRequest == null || !this.b.cacheControl().onlyIfCached()) ? a2 : new CacheStrategy(null, null);
        }

        private CacheStrategy a() {
            String str;
            String str2;
            if (this.c == null) {
                return new CacheStrategy(this.b, null);
            }
            if (this.b.isHttps() && this.c.handshake() == null) {
                return new CacheStrategy(this.b, null);
            }
            if (!CacheStrategy.isCacheable(this.c, this.b)) {
                return new CacheStrategy(this.b, null);
            }
            CacheControl cacheControl = this.b.cacheControl();
            if (cacheControl.noCache() || a(this.b)) {
                return new CacheStrategy(this.b, null);
            }
            CacheControl cacheControl2 = this.c.cacheControl();
            if (cacheControl2.immutable()) {
                return new CacheStrategy(null, this.c);
            }
            long c2 = c();
            long b2 = b();
            if (cacheControl.maxAgeSeconds() != -1) {
                b2 = Math.min(b2, TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds()));
            }
            long j2 = 0;
            long millis = cacheControl.minFreshSeconds() != -1 ? TimeUnit.SECONDS.toMillis((long) cacheControl.minFreshSeconds()) : 0;
            if (!cacheControl2.mustRevalidate() && cacheControl.maxStaleSeconds() != -1) {
                j2 = TimeUnit.SECONDS.toMillis((long) cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                long j3 = c2 + millis;
                if (j3 < b2 + j2) {
                    Builder newBuilder = this.c.newBuilder();
                    if (j3 >= b2) {
                        newBuilder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (c2 > 86400000 && d()) {
                        newBuilder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, newBuilder.build());
                }
            }
            if (this.k != null) {
                str2 = "If-None-Match";
                str = this.k;
            } else if (this.f != null) {
                str2 = "If-Modified-Since";
                str = this.g;
            } else if (this.d == null) {
                return new CacheStrategy(this.b, null);
            } else {
                str2 = "If-Modified-Since";
                str = this.e;
            }
            Headers.Builder newBuilder2 = this.b.headers().newBuilder();
            Internal.instance.addLenient(newBuilder2, str2, str);
            return new CacheStrategy(this.b.newBuilder().headers(newBuilder2.build()).build(), this.c);
        }

        private long b() {
            long j2;
            long j3;
            CacheControl cacheControl = this.c.cacheControl();
            if (cacheControl.maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis((long) cacheControl.maxAgeSeconds());
            }
            long j4 = 0;
            if (this.h != null) {
                if (this.d != null) {
                    j3 = this.d.getTime();
                } else {
                    j3 = this.j;
                }
                long time = this.h.getTime() - j3;
                if (time > 0) {
                    j4 = time;
                }
                return j4;
            } else if (this.f == null || this.c.request().url().query() != null) {
                return 0;
            } else {
                if (this.d != null) {
                    j2 = this.d.getTime();
                } else {
                    j2 = this.i;
                }
                long time2 = j2 - this.f.getTime();
                if (time2 > 0) {
                    j4 = time2 / 10;
                }
                return j4;
            }
        }

        private long c() {
            long j2 = 0;
            if (this.d != null) {
                j2 = Math.max(0, this.j - this.d.getTime());
            }
            if (this.l != -1) {
                j2 = Math.max(j2, TimeUnit.SECONDS.toMillis((long) this.l));
            }
            return j2 + (this.j - this.i) + (this.a - this.j);
        }

        private boolean d() {
            return this.c.cacheControl().maxAgeSeconds() == -1 && this.h == null;
        }

        private static boolean a(Request request) {
            return (request.header("If-Modified-Since") == null && request.header("If-None-Match") == null) ? false : true;
        }
    }

    CacheStrategy(Request request, Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0038, code lost:
        if (r3.cacheControl().noStore() != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0042, code lost:
        if (r4.cacheControl().noStore() != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0046, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
        if (r3.cacheControl().isPrivate() == false) goto L_0x0046;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCacheable(okhttp3.Response r3, okhttp3.Request r4) {
        /*
            int r0 = r3.code()
            r1 = 0
            switch(r0) {
                case 200: goto L_0x0030;
                case 203: goto L_0x0030;
                case 204: goto L_0x0030;
                case 300: goto L_0x0030;
                case 301: goto L_0x0030;
                case 302: goto L_0x0009;
                case 307: goto L_0x0009;
                case 308: goto L_0x0030;
                case 404: goto L_0x0030;
                case 405: goto L_0x0030;
                case 410: goto L_0x0030;
                case 414: goto L_0x0030;
                case 501: goto L_0x0030;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0046
        L_0x0009:
            java.lang.String r0 = "Expires"
            java.lang.String r0 = r3.header(r0)
            if (r0 != 0) goto L_0x0030
            okhttp3.CacheControl r0 = r3.cacheControl()
            int r0 = r0.maxAgeSeconds()
            r2 = -1
            if (r0 != r2) goto L_0x0030
            okhttp3.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPublic()
            if (r0 != 0) goto L_0x0030
            okhttp3.CacheControl r0 = r3.cacheControl()
            boolean r0 = r0.isPrivate()
            if (r0 == 0) goto L_0x0046
        L_0x0030:
            okhttp3.CacheControl r3 = r3.cacheControl()
            boolean r3 = r3.noStore()
            if (r3 != 0) goto L_0x0045
            okhttp3.CacheControl r3 = r4.cacheControl()
            boolean r3 = r3.noStore()
            if (r3 != 0) goto L_0x0045
            r1 = 1
        L_0x0045:
            return r1
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.CacheStrategy.isCacheable(okhttp3.Response, okhttp3.Request):boolean");
    }
}
