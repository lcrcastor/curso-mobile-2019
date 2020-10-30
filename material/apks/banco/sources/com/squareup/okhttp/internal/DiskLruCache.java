package com.squareup.okhttp.internal;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class DiskLruCache implements Closeable {
    static final Pattern a = Pattern.compile("[a-z0-9_-]{1,120}");
    static final /* synthetic */ boolean b = true;
    /* access modifiers changed from: private */
    public static final Sink u = new Sink() {
        public void close() {
        }

        public void flush() {
        }

        public void write(Buffer buffer, long j) {
            buffer.skip(j);
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }
    };
    /* access modifiers changed from: private */
    public final FileSystem c;
    /* access modifiers changed from: private */
    public final File d;
    private final File e;
    private final File f;
    private final File g;
    private final int h;
    private long i;
    /* access modifiers changed from: private */
    public final int j;
    private long k = 0;
    private BufferedSink l;
    /* access modifiers changed from: private */
    public final LinkedHashMap<String, Entry> m = new LinkedHashMap<>(0, 0.75f, true);
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public boolean o;
    /* access modifiers changed from: private */
    public boolean p;
    /* access modifiers changed from: private */
    public boolean q;
    private long r = 0;
    private final Executor s;
    private final Runnable t = new Runnable() {
        public void run() {
            synchronized (DiskLruCache.this) {
                if (!(!DiskLruCache.this.p) && !DiskLruCache.this.q) {
                    try {
                        DiskLruCache.this.i();
                        if (DiskLruCache.this.g()) {
                            DiskLruCache.this.f();
                            DiskLruCache.this.n = 0;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };

    public final class Editor {
        /* access modifiers changed from: private */
        public final Entry b;
        /* access modifiers changed from: private */
        public final boolean[] c;
        /* access modifiers changed from: private */
        public boolean d;
        private boolean e;

        private Editor(Entry entry) {
            this.b = entry;
            this.c = entry.f ? null : new boolean[DiskLruCache.this.j];
        }

        public Source newSource(int i) {
            synchronized (DiskLruCache.this) {
                if (this.b.g != this) {
                    throw new IllegalStateException();
                } else if (!this.b.f) {
                    return null;
                } else {
                    try {
                        Source source = DiskLruCache.this.c.source(this.b.d[i]);
                        return source;
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }
            }
        }

        public Sink newSink(int i) {
            AnonymousClass1 r1;
            synchronized (DiskLruCache.this) {
                if (this.b.g != this) {
                    throw new IllegalStateException();
                }
                if (!this.b.f) {
                    this.c[i] = true;
                }
                try {
                    r1 = new FaultHidingSink(DiskLruCache.this.c.sink(this.b.e[i])) {
                        /* access modifiers changed from: protected */
                        public void a(IOException iOException) {
                            synchronized (DiskLruCache.this) {
                                Editor.this.d = true;
                            }
                        }
                    };
                } catch (FileNotFoundException unused) {
                    return DiskLruCache.u;
                }
            }
            return r1;
        }

        public void commit() {
            synchronized (DiskLruCache.this) {
                if (this.d) {
                    DiskLruCache.this.a(this, false);
                    DiskLruCache.this.a(this.b);
                } else {
                    DiskLruCache.this.a(this, true);
                }
                this.e = true;
            }
        }

        public void abort() {
            synchronized (DiskLruCache.this) {
                DiskLruCache.this.a(this, false);
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(5:2|3|(2:5|6)|7|8) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x000d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void abortUnlessCommitted() {
            /*
                r3 = this;
                com.squareup.okhttp.internal.DiskLruCache r0 = com.squareup.okhttp.internal.DiskLruCache.this
                monitor-enter(r0)
                boolean r1 = r3.e     // Catch:{ all -> 0x000f }
                if (r1 != 0) goto L_0x000d
                com.squareup.okhttp.internal.DiskLruCache r1 = com.squareup.okhttp.internal.DiskLruCache.this     // Catch:{ IOException -> 0x000d }
                r2 = 0
                r1.a(r3, r2)     // Catch:{ IOException -> 0x000d }
            L_0x000d:
                monitor-exit(r0)     // Catch:{ all -> 0x000f }
                return
            L_0x000f:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x000f }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.Editor.abortUnlessCommitted():void");
        }
    }

    final class Entry {
        /* access modifiers changed from: private */
        public final String b;
        /* access modifiers changed from: private */
        public final long[] c;
        /* access modifiers changed from: private */
        public final File[] d;
        /* access modifiers changed from: private */
        public final File[] e;
        /* access modifiers changed from: private */
        public boolean f;
        /* access modifiers changed from: private */
        public Editor g;
        /* access modifiers changed from: private */
        public long h;

        private Entry(String str) {
            this.b = str;
            this.c = new long[DiskLruCache.this.j];
            this.d = new File[DiskLruCache.this.j];
            this.e = new File[DiskLruCache.this.j];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i = 0; i < DiskLruCache.this.j; i++) {
                sb.append(i);
                this.d[i] = new File(DiskLruCache.this.d, sb.toString());
                sb.append(".tmp");
                this.e[i] = new File(DiskLruCache.this.d, sb.toString());
                sb.setLength(length);
            }
        }

        /* access modifiers changed from: private */
        public void a(String[] strArr) {
            if (strArr.length != DiskLruCache.this.j) {
                throw b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.c[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException unused) {
                    throw b(strArr);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(BufferedSink bufferedSink) {
            for (long writeDecimalLong : this.c) {
                bufferedSink.writeByte(32).writeDecimalLong(writeDecimalLong);
            }
        }

        private IOException b(String[] strArr) {
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected journal line: ");
            sb.append(Arrays.toString(strArr));
            throw new IOException(sb.toString());
        }

        /* access modifiers changed from: 0000 */
        public Snapshot a() {
            if (!Thread.holdsLock(DiskLruCache.this)) {
                throw new AssertionError();
            }
            Source[] sourceArr = new Source[DiskLruCache.this.j];
            long[] jArr = (long[]) this.c.clone();
            int i = 0;
            int i2 = 0;
            while (i2 < DiskLruCache.this.j) {
                try {
                    sourceArr[i2] = DiskLruCache.this.c.source(this.d[i2]);
                    i2++;
                } catch (FileNotFoundException unused) {
                    while (i < DiskLruCache.this.j && sourceArr[i] != null) {
                        Util.closeQuietly((Closeable) sourceArr[i]);
                        i++;
                    }
                    return null;
                }
            }
            Snapshot snapshot = new Snapshot(this.b, this.h, sourceArr, jArr);
            return snapshot;
        }
    }

    public final class Snapshot implements Closeable {
        /* access modifiers changed from: private */
        public final String b;
        private final long c;
        private final Source[] d;
        private final long[] e;

        private Snapshot(String str, long j, Source[] sourceArr, long[] jArr) {
            this.b = str;
            this.c = j;
            this.d = sourceArr;
            this.e = jArr;
        }

        public String key() {
            return this.b;
        }

        public Editor edit() {
            return DiskLruCache.this.a(this.b, this.c);
        }

        public Source getSource(int i) {
            return this.d[i];
        }

        public long getLength(int i) {
            return this.e[i];
        }

        public void close() {
            for (Source closeQuietly : this.d) {
                Util.closeQuietly((Closeable) closeQuietly);
            }
        }
    }

    DiskLruCache(FileSystem fileSystem, File file, int i2, int i3, long j2, Executor executor) {
        this.c = fileSystem;
        this.d = file;
        this.h = i2;
        this.e = new File(file, "journal");
        this.f = new File(file, "journal.tmp");
        this.g = new File(file, "journal.bkp");
        this.j = i3;
        this.i = j2;
        this.s = executor;
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (!b && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (!this.p) {
            if (this.c.exists(this.g)) {
                if (this.c.exists(this.e)) {
                    this.c.delete(this.g);
                } else {
                    this.c.rename(this.g, this.e);
                }
            }
            if (this.c.exists(this.e)) {
                try {
                    c();
                    e();
                    this.p = true;
                    return;
                } catch (IOException e2) {
                    Platform platform = Platform.get();
                    StringBuilder sb = new StringBuilder();
                    sb.append("DiskLruCache ");
                    sb.append(this.d);
                    sb.append(" is corrupt: ");
                    sb.append(e2.getMessage());
                    sb.append(", removing");
                    platform.logW(sb.toString());
                    delete();
                    this.q = false;
                }
            }
            f();
            this.p = true;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i2, int i3, long j2) {
        if (j2 <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (i3 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        } else {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true));
            DiskLruCache diskLruCache = new DiskLruCache(fileSystem, file, i2, i3, j2, threadPoolExecutor);
            return diskLruCache;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:16|17|(1:19)(1:20)|21|22) */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r8.n = r1 - r8.m.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        if (r0.exhausted() == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        f();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
        r8.l = d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0078, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x005c */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x0079=Splitter:B:23:0x0079, B:16:0x005c=Splitter:B:16:0x005c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r8 = this;
            com.squareup.okhttp.internal.io.FileSystem r0 = r8.c
            java.io.File r1 = r8.e
            okio.Source r0 = r0.source(r1)
            okio.BufferedSource r0 = okio.Okio.buffer(r0)
            java.lang.String r1 = r0.readUtf8LineStrict()     // Catch:{ all -> 0x00ad }
            java.lang.String r2 = r0.readUtf8LineStrict()     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = r0.readUtf8LineStrict()     // Catch:{ all -> 0x00ad }
            java.lang.String r4 = r0.readUtf8LineStrict()     // Catch:{ all -> 0x00ad }
            java.lang.String r5 = r0.readUtf8LineStrict()     // Catch:{ all -> 0x00ad }
            java.lang.String r6 = "libcore.io.DiskLruCache"
            boolean r6 = r6.equals(r1)     // Catch:{ all -> 0x00ad }
            if (r6 == 0) goto L_0x0079
            java.lang.String r6 = "1"
            boolean r6 = r6.equals(r2)     // Catch:{ all -> 0x00ad }
            if (r6 == 0) goto L_0x0079
            int r6 = r8.h     // Catch:{ all -> 0x00ad }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ all -> 0x00ad }
            boolean r3 = r6.equals(r3)     // Catch:{ all -> 0x00ad }
            if (r3 == 0) goto L_0x0079
            int r3 = r8.j     // Catch:{ all -> 0x00ad }
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ all -> 0x00ad }
            boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x00ad }
            if (r3 == 0) goto L_0x0079
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r5)     // Catch:{ all -> 0x00ad }
            if (r3 != 0) goto L_0x0051
            goto L_0x0079
        L_0x0051:
            r1 = 0
        L_0x0052:
            java.lang.String r2 = r0.readUtf8LineStrict()     // Catch:{ EOFException -> 0x005c }
            r8.a(r2)     // Catch:{ EOFException -> 0x005c }
            int r1 = r1 + 1
            goto L_0x0052
        L_0x005c:
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.DiskLruCache$Entry> r2 = r8.m     // Catch:{ all -> 0x00ad }
            int r2 = r2.size()     // Catch:{ all -> 0x00ad }
            int r1 = r1 - r2
            r8.n = r1     // Catch:{ all -> 0x00ad }
            boolean r1 = r0.exhausted()     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x006f
            r8.f()     // Catch:{ all -> 0x00ad }
            goto L_0x0075
        L_0x006f:
            okio.BufferedSink r1 = r8.d()     // Catch:{ all -> 0x00ad }
            r8.l = r1     // Catch:{ all -> 0x00ad }
        L_0x0075:
            com.squareup.okhttp.internal.Util.closeQuietly(r0)
            return
        L_0x0079:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x00ad }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ad }
            r6.<init>()     // Catch:{ all -> 0x00ad }
            java.lang.String r7 = "unexpected journal header: ["
            r6.append(r7)     // Catch:{ all -> 0x00ad }
            r6.append(r1)     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x00ad }
            r6.append(r2)     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x00ad }
            r6.append(r4)     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = ", "
            r6.append(r1)     // Catch:{ all -> 0x00ad }
            r6.append(r5)     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = "]"
            r6.append(r1)     // Catch:{ all -> 0x00ad }
            java.lang.String r1 = r6.toString()     // Catch:{ all -> 0x00ad }
            r3.<init>(r1)     // Catch:{ all -> 0x00ad }
            throw r3     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r1 = move-exception
            com.squareup.okhttp.internal.Util.closeQuietly(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.c():void");
    }

    private BufferedSink d() {
        return Okio.buffer((Sink) new FaultHidingSink(this.c.appendingSink(this.e)) {
            static final /* synthetic */ boolean a = true;

            static {
                Class<DiskLruCache> cls = DiskLruCache.class;
            }

            /* access modifiers changed from: protected */
            public void a(IOException iOException) {
                if (a || Thread.holdsLock(DiskLruCache.this)) {
                    DiskLruCache.this.o = true;
                    return;
                }
                throw new AssertionError();
            }
        });
    }

    private void a(String str) {
        String str2;
        int indexOf = str.indexOf(32);
        if (indexOf == -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("unexpected journal line: ");
            sb.append(str);
            throw new IOException(sb.toString());
        }
        int i2 = indexOf + 1;
        int indexOf2 = str.indexOf(32, i2);
        if (indexOf2 == -1) {
            str2 = str.substring(i2);
            if (indexOf == "REMOVE".length() && str.startsWith("REMOVE")) {
                this.m.remove(str2);
                return;
            }
        } else {
            str2 = str.substring(i2, indexOf2);
        }
        Entry entry = (Entry) this.m.get(str2);
        if (entry == null) {
            entry = new Entry(str2);
            this.m.put(str2, entry);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(UtilsCuentas.SEPARAOR2);
            entry.f = true;
            entry.g = null;
            entry.a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            entry.g = new Editor(entry);
        } else if (!(indexOf2 == -1 && indexOf == "READ".length() && str.startsWith("READ"))) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unexpected journal line: ");
            sb2.append(str);
            throw new IOException(sb2.toString());
        }
    }

    private void e() {
        this.c.delete(this.f);
        Iterator it = this.m.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i2 = 0;
            if (entry.g == null) {
                while (i2 < this.j) {
                    this.k += entry.c[i2];
                    i2++;
                }
            } else {
                entry.g = null;
                while (i2 < this.j) {
                    this.c.delete(entry.d[i2]);
                    this.c.delete(entry.e[i2]);
                    i2++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void f() {
        if (this.l != null) {
            this.l.close();
        }
        BufferedSink buffer = Okio.buffer(this.c.sink(this.f));
        try {
            buffer.writeUtf8("libcore.io.DiskLruCache").writeByte(10);
            buffer.writeUtf8("1").writeByte(10);
            buffer.writeDecimalLong((long) this.h).writeByte(10);
            buffer.writeDecimalLong((long) this.j).writeByte(10);
            buffer.writeByte(10);
            for (Entry entry : this.m.values()) {
                if (entry.g != null) {
                    buffer.writeUtf8("DIRTY").writeByte(32);
                    buffer.writeUtf8(entry.b);
                    buffer.writeByte(10);
                } else {
                    buffer.writeUtf8("CLEAN").writeByte(32);
                    buffer.writeUtf8(entry.b);
                    entry.a(buffer);
                    buffer.writeByte(10);
                }
            }
            buffer.close();
            if (this.c.exists(this.e)) {
                this.c.rename(this.e, this.g);
            }
            this.c.rename(this.f, this.e);
            this.c.delete(this.g);
            this.l = d();
            this.o = false;
        } catch (Throwable th) {
            buffer.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.DiskLruCache.Snapshot get(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r3.a()     // Catch:{ all -> 0x0052 }
            r3.h()     // Catch:{ all -> 0x0052 }
            r3.b(r4)     // Catch:{ all -> 0x0052 }
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.DiskLruCache$Entry> r0 = r3.m     // Catch:{ all -> 0x0052 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0052 }
            com.squareup.okhttp.internal.DiskLruCache$Entry r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0     // Catch:{ all -> 0x0052 }
            r1 = 0
            if (r0 == 0) goto L_0x0050
            boolean r2 = r0.f     // Catch:{ all -> 0x0052 }
            if (r2 != 0) goto L_0x001c
            goto L_0x0050
        L_0x001c:
            com.squareup.okhttp.internal.DiskLruCache$Snapshot r0 = r0.a()     // Catch:{ all -> 0x0052 }
            if (r0 != 0) goto L_0x0024
            monitor-exit(r3)
            return r1
        L_0x0024:
            int r1 = r3.n     // Catch:{ all -> 0x0052 }
            int r1 = r1 + 1
            r3.n = r1     // Catch:{ all -> 0x0052 }
            okio.BufferedSink r1 = r3.l     // Catch:{ all -> 0x0052 }
            java.lang.String r2 = "READ"
            okio.BufferedSink r1 = r1.writeUtf8(r2)     // Catch:{ all -> 0x0052 }
            r2 = 32
            okio.BufferedSink r1 = r1.writeByte(r2)     // Catch:{ all -> 0x0052 }
            okio.BufferedSink r4 = r1.writeUtf8(r4)     // Catch:{ all -> 0x0052 }
            r1 = 10
            r4.writeByte(r1)     // Catch:{ all -> 0x0052 }
            boolean r4 = r3.g()     // Catch:{ all -> 0x0052 }
            if (r4 == 0) goto L_0x004e
            java.util.concurrent.Executor r4 = r3.s     // Catch:{ all -> 0x0052 }
            java.lang.Runnable r1 = r3.t     // Catch:{ all -> 0x0052 }
            r4.execute(r1)     // Catch:{ all -> 0x0052 }
        L_0x004e:
            monitor-exit(r3)
            return r0
        L_0x0050:
            monitor-exit(r3)
            return r1
        L_0x0052:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.get(java.lang.String):com.squareup.okhttp.internal.DiskLruCache$Snapshot");
    }

    public Editor edit(String str) {
        return a(str, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.DiskLruCache.Editor a(java.lang.String r6, long r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.a()     // Catch:{ all -> 0x0067 }
            r5.h()     // Catch:{ all -> 0x0067 }
            r5.b(r6)     // Catch:{ all -> 0x0067 }
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.DiskLruCache$Entry> r0 = r5.m     // Catch:{ all -> 0x0067 }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x0067 }
            com.squareup.okhttp.internal.DiskLruCache$Entry r0 = (com.squareup.okhttp.internal.DiskLruCache.Entry) r0     // Catch:{ all -> 0x0067 }
            r1 = -1
            int r3 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            r1 = 0
            if (r3 == 0) goto L_0x0025
            if (r0 == 0) goto L_0x0023
            long r2 = r0.h     // Catch:{ all -> 0x0067 }
            int r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r4 == 0) goto L_0x0025
        L_0x0023:
            monitor-exit(r5)
            return r1
        L_0x0025:
            if (r0 == 0) goto L_0x002f
            com.squareup.okhttp.internal.DiskLruCache$Editor r7 = r0.g     // Catch:{ all -> 0x0067 }
            if (r7 == 0) goto L_0x002f
            monitor-exit(r5)
            return r1
        L_0x002f:
            okio.BufferedSink r7 = r5.l     // Catch:{ all -> 0x0067 }
            java.lang.String r8 = "DIRTY"
            okio.BufferedSink r7 = r7.writeUtf8(r8)     // Catch:{ all -> 0x0067 }
            r8 = 32
            okio.BufferedSink r7 = r7.writeByte(r8)     // Catch:{ all -> 0x0067 }
            okio.BufferedSink r7 = r7.writeUtf8(r6)     // Catch:{ all -> 0x0067 }
            r8 = 10
            r7.writeByte(r8)     // Catch:{ all -> 0x0067 }
            okio.BufferedSink r7 = r5.l     // Catch:{ all -> 0x0067 }
            r7.flush()     // Catch:{ all -> 0x0067 }
            boolean r7 = r5.o     // Catch:{ all -> 0x0067 }
            if (r7 == 0) goto L_0x0051
            monitor-exit(r5)
            return r1
        L_0x0051:
            if (r0 != 0) goto L_0x005d
            com.squareup.okhttp.internal.DiskLruCache$Entry r0 = new com.squareup.okhttp.internal.DiskLruCache$Entry     // Catch:{ all -> 0x0067 }
            r0.<init>(r6)     // Catch:{ all -> 0x0067 }
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.DiskLruCache$Entry> r7 = r5.m     // Catch:{ all -> 0x0067 }
            r7.put(r6, r0)     // Catch:{ all -> 0x0067 }
        L_0x005d:
            com.squareup.okhttp.internal.DiskLruCache$Editor r6 = new com.squareup.okhttp.internal.DiskLruCache$Editor     // Catch:{ all -> 0x0067 }
            r6.<init>(r0)     // Catch:{ all -> 0x0067 }
            r0.g = r6     // Catch:{ all -> 0x0067 }
            monitor-exit(r5)
            return r6
        L_0x0067:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.a(java.lang.String, long):com.squareup.okhttp.internal.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.d;
    }

    public synchronized long getMaxSize() {
        return this.i;
    }

    public synchronized void setMaxSize(long j2) {
        this.i = j2;
        if (this.p) {
            this.s.execute(this.t);
        }
    }

    public synchronized long size() {
        a();
        return this.k;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x011b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(com.squareup.okhttp.internal.DiskLruCache.Editor r12, boolean r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            com.squareup.okhttp.internal.DiskLruCache$Entry r0 = r12.b     // Catch:{ all -> 0x011c }
            com.squareup.okhttp.internal.DiskLruCache$Editor r1 = r0.g     // Catch:{ all -> 0x011c }
            if (r1 == r12) goto L_0x0011
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x011c }
            r12.<init>()     // Catch:{ all -> 0x011c }
            throw r12     // Catch:{ all -> 0x011c }
        L_0x0011:
            r1 = 0
            if (r13 == 0) goto L_0x0057
            boolean r2 = r0.f     // Catch:{ all -> 0x011c }
            if (r2 != 0) goto L_0x0057
            r2 = 0
        L_0x001b:
            int r3 = r11.j     // Catch:{ all -> 0x011c }
            if (r2 >= r3) goto L_0x0057
            boolean[] r3 = r12.c     // Catch:{ all -> 0x011c }
            boolean r3 = r3[r2]     // Catch:{ all -> 0x011c }
            if (r3 != 0) goto L_0x0041
            r12.abort()     // Catch:{ all -> 0x011c }
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x011c }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x011c }
            r13.<init>()     // Catch:{ all -> 0x011c }
            java.lang.String r0 = "Newly created entry didn't create value for index "
            r13.append(r0)     // Catch:{ all -> 0x011c }
            r13.append(r2)     // Catch:{ all -> 0x011c }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x011c }
            r12.<init>(r13)     // Catch:{ all -> 0x011c }
            throw r12     // Catch:{ all -> 0x011c }
        L_0x0041:
            com.squareup.okhttp.internal.io.FileSystem r3 = r11.c     // Catch:{ all -> 0x011c }
            java.io.File[] r4 = r0.e     // Catch:{ all -> 0x011c }
            r4 = r4[r2]     // Catch:{ all -> 0x011c }
            boolean r3 = r3.exists(r4)     // Catch:{ all -> 0x011c }
            if (r3 != 0) goto L_0x0054
            r12.abort()     // Catch:{ all -> 0x011c }
            monitor-exit(r11)
            return
        L_0x0054:
            int r2 = r2 + 1
            goto L_0x001b
        L_0x0057:
            int r12 = r11.j     // Catch:{ all -> 0x011c }
            if (r1 >= r12) goto L_0x009a
            java.io.File[] r12 = r0.e     // Catch:{ all -> 0x011c }
            r12 = r12[r1]     // Catch:{ all -> 0x011c }
            if (r13 == 0) goto L_0x0092
            com.squareup.okhttp.internal.io.FileSystem r2 = r11.c     // Catch:{ all -> 0x011c }
            boolean r2 = r2.exists(r12)     // Catch:{ all -> 0x011c }
            if (r2 == 0) goto L_0x0097
            java.io.File[] r2 = r0.d     // Catch:{ all -> 0x011c }
            r2 = r2[r1]     // Catch:{ all -> 0x011c }
            com.squareup.okhttp.internal.io.FileSystem r3 = r11.c     // Catch:{ all -> 0x011c }
            r3.rename(r12, r2)     // Catch:{ all -> 0x011c }
            long[] r12 = r0.c     // Catch:{ all -> 0x011c }
            r3 = r12[r1]     // Catch:{ all -> 0x011c }
            com.squareup.okhttp.internal.io.FileSystem r12 = r11.c     // Catch:{ all -> 0x011c }
            long r5 = r12.size(r2)     // Catch:{ all -> 0x011c }
            long[] r12 = r0.c     // Catch:{ all -> 0x011c }
            r12[r1] = r5     // Catch:{ all -> 0x011c }
            long r7 = r11.k     // Catch:{ all -> 0x011c }
            r12 = 0
            long r9 = r7 - r3
            long r2 = r9 + r5
            r11.k = r2     // Catch:{ all -> 0x011c }
            goto L_0x0097
        L_0x0092:
            com.squareup.okhttp.internal.io.FileSystem r2 = r11.c     // Catch:{ all -> 0x011c }
            r2.delete(r12)     // Catch:{ all -> 0x011c }
        L_0x0097:
            int r1 = r1 + 1
            goto L_0x0057
        L_0x009a:
            int r12 = r11.n     // Catch:{ all -> 0x011c }
            r1 = 1
            int r12 = r12 + r1
            r11.n = r12     // Catch:{ all -> 0x011c }
            r12 = 0
            r0.g = r12     // Catch:{ all -> 0x011c }
            boolean r12 = r0.f     // Catch:{ all -> 0x011c }
            r12 = r12 | r13
            r2 = 10
            r3 = 32
            if (r12 == 0) goto L_0x00de
            r0.f = r1     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            java.lang.String r1 = "CLEAN"
            okio.BufferedSink r12 = r12.writeUtf8(r1)     // Catch:{ all -> 0x011c }
            r12.writeByte(r3)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            java.lang.String r1 = r0.b     // Catch:{ all -> 0x011c }
            r12.writeUtf8(r1)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            r0.a(r12)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            r12.writeByte(r2)     // Catch:{ all -> 0x011c }
            if (r13 == 0) goto L_0x0100
            long r12 = r11.r     // Catch:{ all -> 0x011c }
            r1 = 1
            long r3 = r12 + r1
            r11.r = r3     // Catch:{ all -> 0x011c }
            r0.h = r12     // Catch:{ all -> 0x011c }
            goto L_0x0100
        L_0x00de:
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.DiskLruCache$Entry> r12 = r11.m     // Catch:{ all -> 0x011c }
            java.lang.String r13 = r0.b     // Catch:{ all -> 0x011c }
            r12.remove(r13)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            java.lang.String r13 = "REMOVE"
            okio.BufferedSink r12 = r12.writeUtf8(r13)     // Catch:{ all -> 0x011c }
            r12.writeByte(r3)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            java.lang.String r13 = r0.b     // Catch:{ all -> 0x011c }
            r12.writeUtf8(r13)     // Catch:{ all -> 0x011c }
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            r12.writeByte(r2)     // Catch:{ all -> 0x011c }
        L_0x0100:
            okio.BufferedSink r12 = r11.l     // Catch:{ all -> 0x011c }
            r12.flush()     // Catch:{ all -> 0x011c }
            long r12 = r11.k     // Catch:{ all -> 0x011c }
            long r0 = r11.i     // Catch:{ all -> 0x011c }
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x0113
            boolean r12 = r11.g()     // Catch:{ all -> 0x011c }
            if (r12 == 0) goto L_0x011a
        L_0x0113:
            java.util.concurrent.Executor r12 = r11.s     // Catch:{ all -> 0x011c }
            java.lang.Runnable r13 = r11.t     // Catch:{ all -> 0x011c }
            r12.execute(r13)     // Catch:{ all -> 0x011c }
        L_0x011a:
            monitor-exit(r11)
            return
        L_0x011c:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.DiskLruCache.a(com.squareup.okhttp.internal.DiskLruCache$Editor, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean g() {
        return this.n >= 2000 && this.n >= this.m.size();
    }

    public synchronized boolean remove(String str) {
        a();
        h();
        b(str);
        Entry entry = (Entry) this.m.get(str);
        if (entry == null) {
            return false;
        }
        return a(entry);
    }

    /* access modifiers changed from: private */
    public boolean a(Entry entry) {
        if (entry.g != null) {
            entry.g.d = true;
        }
        for (int i2 = 0; i2 < this.j; i2++) {
            this.c.delete(entry.d[i2]);
            this.k -= entry.c[i2];
            entry.c[i2] = 0;
        }
        this.n++;
        this.l.writeUtf8("REMOVE").writeByte(32).writeUtf8(entry.b).writeByte(10);
        this.m.remove(entry.b);
        if (g()) {
            this.s.execute(this.t);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.q;
    }

    private synchronized void h() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() {
        if (this.p) {
            h();
            i();
            this.l.flush();
        }
    }

    public synchronized void close() {
        Entry[] entryArr;
        if (this.p) {
            if (!this.q) {
                for (Entry entry : (Entry[]) this.m.values().toArray(new Entry[this.m.size()])) {
                    if (entry.g != null) {
                        entry.g.abort();
                    }
                }
                i();
                this.l.close();
                this.l = null;
                this.q = true;
                return;
            }
        }
        this.q = true;
    }

    /* access modifiers changed from: private */
    public void i() {
        while (this.k > this.i) {
            a((Entry) this.m.values().iterator().next());
        }
    }

    public void delete() {
        close();
        this.c.deleteContents(this.d);
    }

    public synchronized void evictAll() {
        a();
        for (Entry a2 : (Entry[]) this.m.values().toArray(new Entry[this.m.size()])) {
            a(a2);
        }
    }

    private void b(String str) {
        if (!a.matcher(str).matches()) {
            StringBuilder sb = new StringBuilder();
            sb.append("keys must match regex [a-z0-9_-]{1,120}: \"");
            sb.append(str);
            sb.append("\"");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public synchronized Iterator<Snapshot> snapshots() {
        a();
        return new Iterator<Snapshot>() {
            final Iterator<Entry> a = new ArrayList(DiskLruCache.this.m.values()).iterator();
            Snapshot b;
            Snapshot c;

            public boolean hasNext() {
                if (this.b != null) {
                    return true;
                }
                synchronized (DiskLruCache.this) {
                    if (DiskLruCache.this.q) {
                        return false;
                    }
                    while (this.a.hasNext()) {
                        Snapshot a2 = ((Entry) this.a.next()).a();
                        if (a2 != null) {
                            this.b = a2;
                            return true;
                        }
                    }
                    return false;
                }
            }

            /* renamed from: a */
            public Snapshot next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.c = this.b;
                this.b = null;
                return this.c;
            }

            public void remove() {
                if (this.c == null) {
                    throw new IllegalStateException("remove() before next()");
                }
                try {
                    DiskLruCache.this.remove(this.c.b);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    this.c = null;
                    throw th;
                }
                this.c = null;
            }
        };
    }
}
