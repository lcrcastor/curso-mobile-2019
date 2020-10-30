package com.squareup.okhttp;

import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.DiskLruCache.Editor;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.File;
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
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Cache {
    final InternalCache a = new InternalCache() {
        public Response get(Request request) {
            return Cache.this.a(request);
        }

        public CacheRequest put(Response response) {
            return Cache.this.a(response);
        }

        public void remove(Request request) {
            Cache.this.c(request);
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
    /* access modifiers changed from: private */
    public final DiskLruCache b;
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public int d;
    private int e;
    private int f;
    private int g;

    final class CacheRequestImpl implements CacheRequest {
        private final Editor b;
        private Sink c;
        /* access modifiers changed from: private */
        public boolean d;
        private Sink e;

        public CacheRequestImpl(final Editor editor) {
            this.b = editor;
            this.c = editor.newSink(1);
            this.e = new ForwardingSink(this.c, Cache.this) {
                public void close() {
                    synchronized (Cache.this) {
                        if (!CacheRequestImpl.this.d) {
                            CacheRequestImpl.this.d = true;
                            Cache.this.c = Cache.this.c + 1;
                            super.close();
                            editor.commit();
                        }
                    }
                }
            };
        }

        /* JADX INFO: used method not loaded: com.squareup.okhttp.internal.Util.closeQuietly(java.io.Closeable):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
            r2.b.abort();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            com.squareup.okhttp.internal.Util.closeQuietly((java.io.Closeable) r2.c);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void abort() {
            /*
                r2 = this;
                com.squareup.okhttp.Cache r0 = com.squareup.okhttp.Cache.this
                monitor-enter(r0)
                boolean r1 = r2.d     // Catch:{ all -> 0x001d }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                return
            L_0x0009:
                r1 = 1
                r2.d = r1     // Catch:{ all -> 0x001d }
                com.squareup.okhttp.Cache r1 = com.squareup.okhttp.Cache.this     // Catch:{ all -> 0x001d }
                r1.d = r1.d + 1     // Catch:{ all -> 0x001d }
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                okio.Sink r0 = r2.c
                com.squareup.okhttp.internal.Util.closeQuietly(r0)
                com.squareup.okhttp.internal.DiskLruCache$Editor r0 = r2.b     // Catch:{ IOException -> 0x001c }
                r0.abort()     // Catch:{ IOException -> 0x001c }
            L_0x001c:
                return
            L_0x001d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x001d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.Cache.CacheRequestImpl.abort():void");
        }

        public Sink body() {
            return this.e;
        }
    }

    static class CacheResponseBody extends ResponseBody {
        /* access modifiers changed from: private */
        public final Snapshot a;
        private final BufferedSource b;
        private final String c;
        private final String d;

        public CacheResponseBody(final Snapshot snapshot, String str, String str2) {
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
        private final String a;
        private final Headers b;
        private final String c;
        private final Protocol d;
        private final int e;
        private final String f;
        private final Headers g;
        private final Handshake h;

        public Entry(Source source) {
            try {
                BufferedSource buffer = Okio.buffer(source);
                this.a = buffer.readUtf8LineStrict();
                this.c = buffer.readUtf8LineStrict();
                Builder builder = new Builder();
                int a2 = Cache.b(buffer);
                for (int i = 0; i < a2; i++) {
                    builder.a(buffer.readUtf8LineStrict());
                }
                this.b = builder.build();
                StatusLine parse = StatusLine.parse(buffer.readUtf8LineStrict());
                this.d = parse.protocol;
                this.e = parse.code;
                this.f = parse.message;
                Builder builder2 = new Builder();
                int a3 = Cache.b(buffer);
                for (int i2 = 0; i2 < a3; i2++) {
                    builder2.a(buffer.readUtf8LineStrict());
                }
                this.g = builder2.build();
                if (a()) {
                    String readUtf8LineStrict = buffer.readUtf8LineStrict();
                    if (readUtf8LineStrict.length() > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("expected \"\" but was \"");
                        sb.append(readUtf8LineStrict);
                        sb.append("\"");
                        throw new IOException(sb.toString());
                    }
                    this.h = Handshake.get(buffer.readUtf8LineStrict(), a(buffer), a(buffer));
                } else {
                    this.h = null;
                }
            } finally {
                source.close();
            }
        }

        public Entry(Response response) {
            this.a = response.request().urlString();
            this.b = OkHeaders.varyHeaders(response);
            this.c = response.request().method();
            this.d = response.protocol();
            this.e = response.code();
            this.f = response.message();
            this.g = response.headers();
            this.h = response.handshake();
        }

        public void a(Editor editor) {
            BufferedSink buffer = Okio.buffer(editor.newSink(0));
            buffer.writeUtf8(this.a);
            buffer.writeByte(10);
            buffer.writeUtf8(this.c);
            buffer.writeByte(10);
            buffer.writeDecimalLong((long) this.b.size());
            buffer.writeByte(10);
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                buffer.writeUtf8(this.b.name(i));
                buffer.writeUtf8(": ");
                buffer.writeUtf8(this.b.value(i));
                buffer.writeByte(10);
            }
            buffer.writeUtf8(new StatusLine(this.d, this.e, this.f).toString());
            buffer.writeByte(10);
            buffer.writeDecimalLong((long) this.g.size());
            buffer.writeByte(10);
            int size2 = this.g.size();
            for (int i2 = 0; i2 < size2; i2++) {
                buffer.writeUtf8(this.g.name(i2));
                buffer.writeUtf8(": ");
                buffer.writeUtf8(this.g.value(i2));
                buffer.writeByte(10);
            }
            if (a()) {
                buffer.writeByte(10);
                buffer.writeUtf8(this.h.cipherSuite());
                buffer.writeByte(10);
                a(buffer, this.h.peerCertificates());
                a(buffer, this.h.localCertificates());
            }
            buffer.close();
        }

        private boolean a() {
            return this.a.startsWith("https://");
        }

        private List<Certificate> a(BufferedSource bufferedSource) {
            int a2 = Cache.b(bufferedSource);
            if (a2 == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                ArrayList arrayList = new ArrayList(a2);
                for (int i = 0; i < a2; i++) {
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
                bufferedSink.writeDecimalLong((long) list.size());
                bufferedSink.writeByte(10);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bufferedSink.writeUtf8(ByteString.of(((Certificate) list.get(i)).getEncoded()).base64());
                    bufferedSink.writeByte(10);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException(e2.getMessage());
            }
        }

        public boolean a(Request request, Response response) {
            return this.a.equals(request.urlString()) && this.c.equals(request.method()) && OkHeaders.varyMatches(response, this.b, request);
        }

        public Response a(Request request, Snapshot snapshot) {
            String str = this.g.get("Content-Type");
            String str2 = this.g.get("Content-Length");
            return new Response.Builder().request(new Request.Builder().url(this.a).method(this.c, null).headers(this.b).build()).protocol(this.d).code(this.e).message(this.f).headers(this.g).body(new CacheResponseBody(snapshot, str, str2)).handshake(this.h).build();
        }
    }

    public Cache(File file, long j) {
        this.b = DiskLruCache.create(FileSystem.SYSTEM, file, 201105, 2, j);
    }

    private static String b(Request request) {
        return Util.md5Hex(request.urlString());
    }

    /* access modifiers changed from: 0000 */
    public Response a(Request request) {
        try {
            Snapshot snapshot = this.b.get(b(request));
            if (snapshot == null) {
                return null;
            }
            try {
                Entry entry = new Entry(snapshot.getSource(0));
                Response a2 = entry.a(request, snapshot);
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

    /* access modifiers changed from: private */
    public CacheRequest a(Response response) {
        Editor editor;
        String method = response.request().method();
        if (HttpMethod.invalidatesCache(response.request().method())) {
            try {
                c(response.request());
            } catch (IOException unused) {
            }
            return null;
        } else if (!method.equals("GET") || OkHeaders.hasVaryAll(response)) {
            return null;
        } else {
            Entry entry = new Entry(response);
            try {
                editor = this.b.edit(b(response.request()));
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

    /* access modifiers changed from: private */
    public void c(Request request) {
        this.b.remove(b(request));
    }

    /* access modifiers changed from: private */
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

    private void a(Editor editor) {
        if (editor != null) {
            try {
                editor.abort();
            } catch (IOException unused) {
            }
        }
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

    public synchronized int getWriteAbortCount() {
        return this.d;
    }

    public synchronized int getWriteSuccessCount() {
        return this.c;
    }

    public long getSize() {
        return this.b.size();
    }

    public long getMaxSize() {
        return this.b.getMaxSize();
    }

    public void flush() {
        this.b.flush();
    }

    public void close() {
        this.b.close();
    }

    public File getDirectory() {
        return this.b.getDirectory();
    }

    public boolean isClosed() {
        return this.b.isClosed();
    }

    /* access modifiers changed from: private */
    public synchronized void a(CacheStrategy cacheStrategy) {
        this.g++;
        if (cacheStrategy.networkRequest != null) {
            this.e++;
        } else if (cacheStrategy.cacheResponse != null) {
            this.f++;
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a() {
        this.f++;
    }

    public synchronized int getNetworkCount() {
        return this.e;
    }

    public synchronized int getHitCount() {
        return this.f;
    }

    public synchronized int getRequestCount() {
        return this.g;
    }

    /* access modifiers changed from: private */
    public static int b(BufferedSource bufferedSource) {
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
