package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import okhttp3.Headers.Builder;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.DiskLruCache.Editor;
import okhttp3.internal.cache.DiskLruCache.Snapshot;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Cache implements Closeable, Flushable {
    final InternalCache a;
    final DiskLruCache b;
    int c;
    int d;
    private int e;
    private int f;
    private int g;

    static class CacheResponseBody extends ResponseBody {
        final Snapshot a;
        private final BufferedSource b;
        @Nullable
        private final String c;
        @Nullable
        private final String d;

        CacheResponseBody(final Snapshot snapshot, String str, String str2) {
            this.a = snapshot;
            this.c = str;
            this.d = str2;
            this.b = Okio.buffer((Source) new ForwardingSource(snapshot.getSource(1)) {
                public void close() {
                    snapshot.close();
                    super.close();
                }
            });
        }

        public MediaType contentType() {
            if (this.c != null) {
                return MediaType.parse(this.c);
            }
            return null;
        }

        public long contentLength() {
            long j = -1;
            try {
                if (this.d != null) {
                    j = Long.parseLong(this.d);
                }
                return j;
            } catch (NumberFormatException unused) {
                return -1;
            }
        }

        public BufferedSource source() {
            return this.b;
        }
    }

    static final class Entry {
        private static final String a;
        private static final String b;
        private final String c;
        private final Headers d;
        private final String e;
        private final Protocol f;
        private final int g;
        private final String h;
        private final Headers i;
        @Nullable
        private final Handshake j;
        private final long k;
        private final long l;

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(Platform.get().getPrefix());
            sb.append("-Sent-Millis");
            a = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Platform.get().getPrefix());
            sb2.append("-Received-Millis");
            b = sb2.toString();
        }

        Entry(Source source) {
            TlsVersion tlsVersion;
            try {
                BufferedSource buffer = Okio.buffer(source);
                this.c = buffer.readUtf8LineStrict();
                this.e = buffer.readUtf8LineStrict();
                Builder builder = new Builder();
                int a2 = Cache.a(buffer);
                for (int i2 = 0; i2 < a2; i2++) {
                    builder.a(buffer.readUtf8LineStrict());
                }
                this.d = builder.build();
                StatusLine parse = StatusLine.parse(buffer.readUtf8LineStrict());
                this.f = parse.protocol;
                this.g = parse.code;
                this.h = parse.message;
                Builder builder2 = new Builder();
                int a3 = Cache.a(buffer);
                for (int i3 = 0; i3 < a3; i3++) {
                    builder2.a(buffer.readUtf8LineStrict());
                }
                String str = builder2.get(a);
                String str2 = builder2.get(b);
                builder2.removeAll(a);
                builder2.removeAll(b);
                long j2 = 0;
                this.k = str != null ? Long.parseLong(str) : 0;
                if (str2 != null) {
                    j2 = Long.parseLong(str2);
                }
                this.l = j2;
                this.i = builder2.build();
                if (a()) {
                    String readUtf8LineStrict = buffer.readUtf8LineStrict();
                    if (readUtf8LineStrict.length() > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("expected \"\" but was \"");
                        sb.append(readUtf8LineStrict);
                        sb.append("\"");
                        throw new IOException(sb.toString());
                    }
                    CipherSuite forJavaName = CipherSuite.forJavaName(buffer.readUtf8LineStrict());
                    List a4 = a(buffer);
                    List a5 = a(buffer);
                    if (!buffer.exhausted()) {
                        tlsVersion = TlsVersion.forJavaName(buffer.readUtf8LineStrict());
                    } else {
                        tlsVersion = TlsVersion.SSL_3_0;
                    }
                    this.j = Handshake.get(tlsVersion, forJavaName, a4, a5);
                } else {
                    this.j = null;
                }
            } finally {
                source.close();
            }
        }

        Entry(Response response) {
            this.c = response.request().url().toString();
            this.d = HttpHeaders.varyHeaders(response);
            this.e = response.request().method();
            this.f = response.protocol();
            this.g = response.code();
            this.h = response.message();
            this.i = response.headers();
            this.j = response.handshake();
            this.k = response.sentRequestAtMillis();
            this.l = response.receivedResponseAtMillis();
        }

        public void a(Editor editor) {
            BufferedSink buffer = Okio.buffer(editor.newSink(0));
            buffer.writeUtf8(this.c).writeByte(10);
            buffer.writeUtf8(this.e).writeByte(10);
            buffer.writeDecimalLong((long) this.d.size()).writeByte(10);
            int size = this.d.size();
            for (int i2 = 0; i2 < size; i2++) {
                buffer.writeUtf8(this.d.name(i2)).writeUtf8(": ").writeUtf8(this.d.value(i2)).writeByte(10);
            }
            buffer.writeUtf8(new StatusLine(this.f, this.g, this.h).toString()).writeByte(10);
            buffer.writeDecimalLong((long) (this.i.size() + 2)).writeByte(10);
            int size2 = this.i.size();
            for (int i3 = 0; i3 < size2; i3++) {
                buffer.writeUtf8(this.i.name(i3)).writeUtf8(": ").writeUtf8(this.i.value(i3)).writeByte(10);
            }
            buffer.writeUtf8(a).writeUtf8(": ").writeDecimalLong(this.k).writeByte(10);
            buffer.writeUtf8(b).writeUtf8(": ").writeDecimalLong(this.l).writeByte(10);
            if (a()) {
                buffer.writeByte(10);
                buffer.writeUtf8(this.j.cipherSuite().javaName()).writeByte(10);
                a(buffer, this.j.peerCertificates());
                a(buffer, this.j.localCertificates());
                buffer.writeUtf8(this.j.tlsVersion().javaName()).writeByte(10);
            }
            buffer.close();
        }

        private boolean a() {
            return this.c.startsWith("https://");
        }

        private List<Certificate> a(BufferedSource bufferedSource) {
            int a2 = Cache.a(bufferedSource);
            if (a2 == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                ArrayList arrayList = new ArrayList(a2);
                for (int i2 = 0; i2 < a2; i2++) {
                    String readUtf8LineStrict = bufferedSource.readUtf8LineStrict();
                    Buffer buffer = new Buffer();
                    buffer.write(ByteString.decodeBase64(readUtf8LineStrict));
                    arrayList.add(instance.generateCertificate(buffer.inputStream()));
                }
                return arrayList;
            } catch (CertificateException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        private void a(BufferedSink bufferedSink, List<Certificate> list) {
            try {
                bufferedSink.writeDecimalLong((long) list.size()).writeByte(10);
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    bufferedSink.writeUtf8(ByteString.of(((Certificate) list.get(i2)).getEncoded()).base64()).writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public boolean a(Request request, Response response) {
            return this.c.equals(request.url().toString()) && this.e.equals(request.method()) && HttpHeaders.varyMatches(response, this.d, request);
        }

        public Response a(Snapshot snapshot) {
            String str = this.i.get("Content-Type");
            String str2 = this.i.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.c).method(this.e, null).headers(this.d).build()).protocol(this.f).code(this.g).message(this.h).headers(this.i).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.j).sentRequestAtMillis(this.k).receivedResponseAtMillis(this.l).build();
        }
    }

    final class CacheRequestImpl implements CacheRequest {
        boolean a;
        private final Editor c;
        private Sink d;
        private Sink e;

        CacheRequestImpl(final Editor editor) {
            this.c = editor;
            this.d = editor.newSink(1);
            this.e = new ForwardingSink(this.d, Cache.this) {
                public void close() {
                    synchronized (Cache.this) {
                        if (!CacheRequestImpl.this.a) {
                            CacheRequestImpl.this.a = true;
                            Cache.this.c++;
                            super.close();
                            editor.commit();
                        }
                    }
                }
            };
        }

        /* JADX INFO: used method not loaded: okhttp3.internal.Util.closeQuietly(java.io.Closeable):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
            r4.c.abort();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
            okhttp3.internal.Util.closeQuietly((java.io.Closeable) r4.d);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void abort() {
            /*
                r4 = this;
                okhttp3.Cache r0 = okhttp3.Cache.this
                monitor-enter(r0)
                boolean r1 = r4.a     // Catch:{ all -> 0x001f }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x001f }
                return
            L_0x0009:
                r1 = 1
                r4.a = r1     // Catch:{ all -> 0x001f }
                okhttp3.Cache r2 = okhttp3.Cache.this     // Catch:{ all -> 0x001f }
                int r3 = r2.d     // Catch:{ all -> 0x001f }
                int r3 = r3 + r1
                r2.d = r3     // Catch:{ all -> 0x001f }
                monitor-exit(r0)     // Catch:{ all -> 0x001f }
                okio.Sink r0 = r4.d
                okhttp3.internal.Util.closeQuietly(r0)
                okhttp3.internal.cache.DiskLruCache$Editor r0 = r4.c     // Catch:{ IOException -> 0x001e }
                r0.abort()     // Catch:{ IOException -> 0x001e }
            L_0x001e:
                return
            L_0x001f:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001f }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.Cache.CacheRequestImpl.abort():void");
        }

        public Sink body() {
            return this.e;
        }
    }

    public Cache(File file, long j) {
        this(file, j, FileSystem.SYSTEM);
    }

    Cache(File file, long j, FileSystem fileSystem) {
        this.a = new InternalCache() {
            public Response get(Request request) {
                return Cache.this.a(request);
            }

            public CacheRequest put(Response response) {
                return Cache.this.a(response);
            }

            public void remove(Request request) {
                Cache.this.b(request);
            }

            public void update(Response response, Response response2) {
                Cache.this.a(response, response2);
            }

            public void trackConditionalCacheHit() {
                Cache.this.a();
            }

            public void trackResponse(CacheStrategy cacheStrategy) {
                Cache.this.a(cacheStrategy);
            }
        };
        this.b = DiskLruCache.create(fileSystem, file, 201105, 2, j);
    }

    public static String key(HttpUrl httpUrl) {
        return ByteString.encodeUtf8(httpUrl.toString()).md5().hex();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Response a(Request request) {
        try {
            Snapshot snapshot = this.b.get(key(request.url()));
            if (snapshot == null) {
                return null;
            }
            try {
                Entry entry = new Entry(snapshot.getSource(0));
                Response a2 = entry.a(snapshot);
                if (entry.a(request, a2)) {
                    return a2;
                }
                Util.closeQuietly((Closeable) a2.body());
                return null;
            } catch (IOException unused) {
                Util.closeQuietly((Closeable) snapshot);
                return null;
            }
        } catch (IOException unused2) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public CacheRequest a(Response response) {
        Editor editor;
        String method = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                b(response.request());
            } catch (IOException unused) {
            }
            return null;
        } else if (!method.equals("GET") || HttpHeaders.hasVaryAll(response)) {
            return null;
        } else {
            Entry entry = new Entry(response);
            try {
                editor = this.b.edit(key(response.request().url()));
                if (editor == null) {
                    return null;
                }
                try {
                    entry.a(editor);
                    return new CacheRequestImpl(editor);
                } catch (IOException unused2) {
                    a(editor);
                    return null;
                }
            } catch (IOException unused3) {
                editor = null;
                a(editor);
                return null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Request request) {
        this.b.remove(key(request.url()));
    }

    /* access modifiers changed from: 0000 */
    public void a(Response response, Response response2) {
        Editor editor;
        Entry entry = new Entry(response2);
        try {
            editor = ((CacheResponseBody) response.body()).a.edit();
            if (editor != null) {
                try {
                    entry.a(editor);
                    editor.commit();
                } catch (IOException unused) {
                }
            }
        } catch (IOException unused2) {
            editor = null;
            a(editor);
        }
    }

    private void a(@Nullable Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException unused) {
            }
        }
    }

    public void initialize() {
        this.b.initialize();
    }

    public void delete() {
        this.b.delete();
    }

    public void evictAll() {
        this.b.evictAll();
    }

    public Iterator<String> urls() {
        return new Iterator<String>() {
            final Iterator<Snapshot> a = Cache.this.b.snapshots();
            @Nullable
            String b;
            boolean c;

            public boolean hasNext() {
                if (this.b != null) {
                    return true;
                }
                this.c = false;
                while (this.a.hasNext()) {
                    Snapshot snapshot = (Snapshot) this.a.next();
                    try {
                        this.b = Okio.buffer(snapshot.getSource(0)).readUtf8LineStrict();
                        return true;
                    } catch (IOException unused) {
                    } finally {
                        snapshot.close();
                    }
                }
                return false;
            }

            /* renamed from: a */
            public String next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                String str = this.b;
                this.b = null;
                this.c = true;
                return str;
            }

            public void remove() {
                if (!this.c) {
                    throw new IllegalStateException("remove() before next()");
                }
                this.a.remove();
            }
        };
    }

    public synchronized int writeAbortCount() {
        return this.d;
    }

    public synchronized int writeSuccessCount() {
        return this.c;
    }

    public long size() {
        return this.b.size();
    }

    public long maxSize() {
        return this.b.getMaxSize();
    }

    public void flush() {
        this.b.flush();
    }

    public void close() {
        this.b.close();
    }

    public File directory() {
        return this.b.getDirectory();
    }

    public boolean isClosed() {
        return this.b.isClosed();
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(CacheStrategy cacheStrategy) {
        this.g++;
        if (cacheStrategy.networkRequest != null) {
            this.e++;
        } else if (cacheStrategy.cacheResponse != null) {
            this.f++;
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a() {
        this.f++;
    }

    public synchronized int networkCount() {
        return this.e;
    }

    public synchronized int hitCount() {
        return this.f;
    }

    public synchronized int requestCount() {
        return this.g;
    }

    static int a(BufferedSource bufferedSource) {
        try {
            long readDecimalLong = bufferedSource.readDecimalLong();
            String readUtf8LineStrict = bufferedSource.readUtf8LineStrict();
            if (readDecimalLong >= 0 && readDecimalLong <= 2147483647L) {
                if (readUtf8LineStrict.isEmpty()) {
                    return (int) readDecimalLong;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("expected an int but was \"");
            sb.append(readDecimalLong);
            sb.append(readUtf8LineStrict);
            sb.append("\"");
            throw new IOException(sb.toString());
        } catch (NumberFormatException e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
