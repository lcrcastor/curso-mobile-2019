package okhttp3.internal.cache;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
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
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class DiskLruCache implements Closeable, Flushable {
    static final Pattern a = Pattern.compile("[a-z0-9_-]{1,120}");
    static final /* synthetic */ boolean m = true;
    final FileSystem b;
    final File c;
    final int d;
    BufferedSink e;
    final LinkedHashMap<String, Entry> f = new LinkedHashMap<>(0, 0.75f, true);
    int g;
    boolean h;
    boolean i;
    boolean j;
    boolean k;
    boolean l;
    private final File n;
    private final File o;
    private final File p;
    private final int q;
    private long r;
    private long s = 0;
    private long t = 0;
    private final Executor u;
    private final Runnable v = new Runnable() {
        /* JADX INFO: used method not loaded: okio.Okio.buffer(okio.Sink):null, types can be incorrect */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r4.a.l = true;
            r4.a.e = okio.Okio.buffer(okio.Okio.blackhole());
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x002f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0018 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                okhttp3.internal.cache.DiskLruCache r0 = okhttp3.internal.cache.DiskLruCache.this
                monitor-enter(r0)
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ all -> 0x0041 }
                boolean r1 = r1.i     // Catch:{ all -> 0x0041 }
                r2 = 1
                r1 = r1 ^ r2
                okhttp3.internal.cache.DiskLruCache r3 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ all -> 0x0041 }
                boolean r3 = r3.j     // Catch:{ all -> 0x0041 }
                r1 = r1 | r3
                if (r1 == 0) goto L_0x0012
                monitor-exit(r0)     // Catch:{ all -> 0x0041 }
                return
            L_0x0012:
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ IOException -> 0x0018 }
                r1.c()     // Catch:{ IOException -> 0x0018 }
                goto L_0x001c
            L_0x0018:
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ all -> 0x0041 }
                r1.k = r2     // Catch:{ all -> 0x0041 }
            L_0x001c:
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ IOException -> 0x002f }
                boolean r1 = r1.b()     // Catch:{ IOException -> 0x002f }
                if (r1 == 0) goto L_0x003f
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ IOException -> 0x002f }
                r1.a()     // Catch:{ IOException -> 0x002f }
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ IOException -> 0x002f }
                r3 = 0
                r1.g = r3     // Catch:{ IOException -> 0x002f }
                goto L_0x003f
            L_0x002f:
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ all -> 0x0041 }
                r1.l = r2     // Catch:{ all -> 0x0041 }
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ all -> 0x0041 }
                okio.Sink r2 = okio.Okio.blackhole()     // Catch:{ all -> 0x0041 }
                okio.BufferedSink r2 = okio.Okio.buffer(r2)     // Catch:{ all -> 0x0041 }
                r1.e = r2     // Catch:{ all -> 0x0041 }
            L_0x003f:
                monitor-exit(r0)     // Catch:{ all -> 0x0041 }
                return
            L_0x0041:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0041 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.AnonymousClass1.run():void");
        }
    };

    public final class Snapshot implements Closeable {
        /* access modifiers changed from: private */
        public final String b;
        private final long c;
        private final Source[] d;
        private final long[] e;

        Snapshot(String str, long j, Source[] sourceArr, long[] jArr) {
            this.b = str;
            this.c = j;
            this.d = sourceArr;
            this.e = jArr;
        }

        public String key() {
            return this.b;
        }

        @Nullable
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

    public final class Editor {
        final Entry a;
        final boolean[] b;
        private boolean d;

        Editor(Entry entry) {
            this.a = entry;
            this.b = entry.e ? null : new boolean[DiskLruCache.this.d];
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            if (this.a.f == this) {
                for (int i = 0; i < DiskLruCache.this.d; i++) {
                    try {
                        DiskLruCache.this.b.delete(this.a.d[i]);
                    } catch (IOException unused) {
                    }
                }
                this.a.f = null;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:0x002e, code lost:
            return null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public okio.Source newSource(int r5) {
            /*
                r4 = this;
                okhttp3.internal.cache.DiskLruCache r0 = okhttp3.internal.cache.DiskLruCache.this
                monitor-enter(r0)
                boolean r1 = r4.d     // Catch:{ all -> 0x002f }
                if (r1 == 0) goto L_0x000d
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x002f }
                r5.<init>()     // Catch:{ all -> 0x002f }
                throw r5     // Catch:{ all -> 0x002f }
            L_0x000d:
                okhttp3.internal.cache.DiskLruCache$Entry r1 = r4.a     // Catch:{ all -> 0x002f }
                boolean r1 = r1.e     // Catch:{ all -> 0x002f }
                r2 = 0
                if (r1 == 0) goto L_0x002d
                okhttp3.internal.cache.DiskLruCache$Entry r1 = r4.a     // Catch:{ all -> 0x002f }
                okhttp3.internal.cache.DiskLruCache$Editor r1 = r1.f     // Catch:{ all -> 0x002f }
                if (r1 == r4) goto L_0x001b
                goto L_0x002d
            L_0x001b:
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ FileNotFoundException -> 0x002b }
                okhttp3.internal.io.FileSystem r1 = r1.b     // Catch:{ FileNotFoundException -> 0x002b }
                okhttp3.internal.cache.DiskLruCache$Entry r3 = r4.a     // Catch:{ FileNotFoundException -> 0x002b }
                java.io.File[] r3 = r3.c     // Catch:{ FileNotFoundException -> 0x002b }
                r5 = r3[r5]     // Catch:{ FileNotFoundException -> 0x002b }
                okio.Source r5 = r1.source(r5)     // Catch:{ FileNotFoundException -> 0x002b }
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                return r5
            L_0x002b:
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                return r2
            L_0x002d:
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                return r2
            L_0x002f:
                r5 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002f }
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.Editor.newSource(int):okio.Source");
        }

        public Sink newSink(int i) {
            synchronized (DiskLruCache.this) {
                if (this.d) {
                    throw new IllegalStateException();
                } else if (this.a.f != this) {
                    Sink blackhole = Okio.blackhole();
                    return blackhole;
                } else {
                    if (!this.a.e) {
                        this.b[i] = true;
                    }
                    try {
                        AnonymousClass1 r1 = new FaultHidingSink(DiskLruCache.this.b.sink(this.a.d[i])) {
                            /* access modifiers changed from: protected */
                            public void a(IOException iOException) {
                                synchronized (DiskLruCache.this) {
                                    Editor.this.a();
                                }
                            }
                        };
                        return r1;
                    } catch (FileNotFoundException unused) {
                        return Okio.blackhole();
                    }
                }
            }
        }

        public void commit() {
            synchronized (DiskLruCache.this) {
                if (this.d) {
                    throw new IllegalStateException();
                }
                if (this.a.f == this) {
                    DiskLruCache.this.a(this, true);
                }
                this.d = true;
            }
        }

        public void abort() {
            synchronized (DiskLruCache.this) {
                if (this.d) {
                    throw new IllegalStateException();
                }
                if (this.a.f == this) {
                    DiskLruCache.this.a(this, false);
                }
                this.d = true;
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(5:2|3|(2:7|8)|9|10) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0013 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void abortUnlessCommitted() {
            /*
                r3 = this;
                okhttp3.internal.cache.DiskLruCache r0 = okhttp3.internal.cache.DiskLruCache.this
                monitor-enter(r0)
                boolean r1 = r3.d     // Catch:{ all -> 0x0015 }
                if (r1 != 0) goto L_0x0013
                okhttp3.internal.cache.DiskLruCache$Entry r1 = r3.a     // Catch:{ all -> 0x0015 }
                okhttp3.internal.cache.DiskLruCache$Editor r1 = r1.f     // Catch:{ all -> 0x0015 }
                if (r1 != r3) goto L_0x0013
                okhttp3.internal.cache.DiskLruCache r1 = okhttp3.internal.cache.DiskLruCache.this     // Catch:{ IOException -> 0x0013 }
                r2 = 0
                r1.a(r3, r2)     // Catch:{ IOException -> 0x0013 }
            L_0x0013:
                monitor-exit(r0)     // Catch:{ all -> 0x0015 }
                return
            L_0x0015:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0015 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.Editor.abortUnlessCommitted():void");
        }
    }

    final class Entry {
        final String a;
        final long[] b;
        final File[] c;
        final File[] d;
        boolean e;
        Editor f;
        long g;

        Entry(String str) {
            this.a = str;
            this.b = new long[DiskLruCache.this.d];
            this.c = new File[DiskLruCache.this.d];
            this.d = new File[DiskLruCache.this.d];
            StringBuilder sb = new StringBuilder(str);
            sb.append('.');
            int length = sb.length();
            for (int i = 0; i < DiskLruCache.this.d; i++) {
                sb.append(i);
                this.c[i] = new File(DiskLruCache.this.c, sb.toString());
                sb.append(".tmp");
                this.d[i] = new File(DiskLruCache.this.c, sb.toString());
                sb.setLength(length);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(String[] strArr) {
            if (strArr.length != DiskLruCache.this.d) {
                throw b(strArr);
            }
            int i = 0;
            while (i < strArr.length) {
                try {
                    this.b[i] = Long.parseLong(strArr[i]);
                    i++;
                } catch (NumberFormatException unused) {
                    throw b(strArr);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(BufferedSink bufferedSink) {
            for (long writeDecimalLong : this.b) {
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
            Source[] sourceArr = new Source[DiskLruCache.this.d];
            long[] jArr = (long[]) this.b.clone();
            int i = 0;
            int i2 = 0;
            while (i2 < DiskLruCache.this.d) {
                try {
                    sourceArr[i2] = DiskLruCache.this.b.source(this.c[i2]);
                    i2++;
                } catch (FileNotFoundException unused) {
                    while (i < DiskLruCache.this.d && sourceArr[i] != null) {
                        Util.closeQuietly((Closeable) sourceArr[i]);
                        i++;
                    }
                    try {
                        DiskLruCache.this.a(this);
                    } catch (IOException unused2) {
                    }
                    return null;
                }
            }
            Snapshot snapshot = new Snapshot(this.a, this.g, sourceArr, jArr);
            return snapshot;
        }
    }

    DiskLruCache(FileSystem fileSystem, File file, int i2, int i3, long j2, Executor executor) {
        this.b = fileSystem;
        this.c = file;
        this.q = i2;
        this.n = new File(file, "journal");
        this.o = new File(file, "journal.tmp");
        this.p = new File(file, "journal.bkp");
        this.d = i3;
        this.r = j2;
        this.u = executor;
    }

    public synchronized void initialize() {
        if (!m && !Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (!this.i) {
            if (this.b.exists(this.p)) {
                if (this.b.exists(this.n)) {
                    this.b.delete(this.p);
                } else {
                    this.b.rename(this.p, this.n);
                }
            }
            if (this.b.exists(this.n)) {
                try {
                    d();
                    f();
                    this.i = true;
                    return;
                } catch (IOException e2) {
                    Platform platform = Platform.get();
                    StringBuilder sb = new StringBuilder();
                    sb.append("DiskLruCache ");
                    sb.append(this.c);
                    sb.append(" is corrupt: ");
                    sb.append(e2.getMessage());
                    sb.append(", removing");
                    platform.log(5, sb.toString(), e2);
                    delete();
                    this.j = false;
                } catch (Throwable th) {
                    this.j = false;
                    throw th;
                }
            }
            a();
            this.i = true;
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
        r8.g = r1 - r8.f.size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
        if (r0.exhausted() == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006b, code lost:
        a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
        r8.e = e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0078, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x005c */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:23:0x0079=Splitter:B:23:0x0079, B:16:0x005c=Splitter:B:16:0x005c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
            r8 = this;
            okhttp3.internal.io.FileSystem r0 = r8.b
            java.io.File r1 = r8.n
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
            int r6 = r8.q     // Catch:{ all -> 0x00ad }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ all -> 0x00ad }
            boolean r3 = r6.equals(r3)     // Catch:{ all -> 0x00ad }
            if (r3 == 0) goto L_0x0079
            int r3 = r8.d     // Catch:{ all -> 0x00ad }
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
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r2 = r8.f     // Catch:{ all -> 0x00ad }
            int r2 = r2.size()     // Catch:{ all -> 0x00ad }
            int r1 = r1 - r2
            r8.g = r1     // Catch:{ all -> 0x00ad }
            boolean r1 = r0.exhausted()     // Catch:{ all -> 0x00ad }
            if (r1 != 0) goto L_0x006f
            r8.a()     // Catch:{ all -> 0x00ad }
            goto L_0x0075
        L_0x006f:
            okio.BufferedSink r1 = r8.e()     // Catch:{ all -> 0x00ad }
            r8.e = r1     // Catch:{ all -> 0x00ad }
        L_0x0075:
            okhttp3.internal.Util.closeQuietly(r0)
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
            okhttp3.internal.Util.closeQuietly(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.d():void");
    }

    private BufferedSink e() {
        return Okio.buffer((Sink) new FaultHidingSink(this.b.appendingSink(this.n)) {
            static final /* synthetic */ boolean a = true;

            static {
                Class<DiskLruCache> cls = DiskLruCache.class;
            }

            /* access modifiers changed from: protected */
            public void a(IOException iOException) {
                if (a || Thread.holdsLock(DiskLruCache.this)) {
                    DiskLruCache.this.h = true;
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
                this.f.remove(str2);
                return;
            }
        } else {
            str2 = str.substring(i2, indexOf2);
        }
        Entry entry = (Entry) this.f.get(str2);
        if (entry == null) {
            entry = new Entry(str2);
            this.f.put(str2, entry);
        }
        if (indexOf2 != -1 && indexOf == "CLEAN".length() && str.startsWith("CLEAN")) {
            String[] split = str.substring(indexOf2 + 1).split(UtilsCuentas.SEPARAOR2);
            entry.e = true;
            entry.f = null;
            entry.a(split);
        } else if (indexOf2 == -1 && indexOf == "DIRTY".length() && str.startsWith("DIRTY")) {
            entry.f = new Editor(entry);
        } else if (!(indexOf2 == -1 && indexOf == "READ".length() && str.startsWith("READ"))) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unexpected journal line: ");
            sb2.append(str);
            throw new IOException(sb2.toString());
        }
    }

    private void f() {
        this.b.delete(this.o);
        Iterator it = this.f.values().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            int i2 = 0;
            if (entry.f == null) {
                while (i2 < this.d) {
                    this.s += entry.b[i2];
                    i2++;
                }
            } else {
                entry.f = null;
                while (i2 < this.d) {
                    this.b.delete(entry.c[i2]);
                    this.b.delete(entry.d[i2]);
                    i2++;
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public synchronized void a() {
        if (this.e != null) {
            this.e.close();
        }
        BufferedSink buffer = Okio.buffer(this.b.sink(this.o));
        try {
            buffer.writeUtf8("libcore.io.DiskLruCache").writeByte(10);
            buffer.writeUtf8("1").writeByte(10);
            buffer.writeDecimalLong((long) this.q).writeByte(10);
            buffer.writeDecimalLong((long) this.d).writeByte(10);
            buffer.writeByte(10);
            for (Entry entry : this.f.values()) {
                if (entry.f != null) {
                    buffer.writeUtf8("DIRTY").writeByte(32);
                    buffer.writeUtf8(entry.a);
                    buffer.writeByte(10);
                } else {
                    buffer.writeUtf8("CLEAN").writeByte(32);
                    buffer.writeUtf8(entry.a);
                    entry.a(buffer);
                    buffer.writeByte(10);
                }
            }
            buffer.close();
            if (this.b.exists(this.n)) {
                this.b.rename(this.n, this.p);
            }
            this.b.rename(this.o, this.n);
            this.b.delete(this.p);
            this.e = e();
            this.h = false;
            this.l = false;
        } catch (Throwable th) {
            buffer.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004f, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized okhttp3.internal.cache.DiskLruCache.Snapshot get(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r3.initialize()     // Catch:{ all -> 0x0050 }
            r3.g()     // Catch:{ all -> 0x0050 }
            r3.b(r4)     // Catch:{ all -> 0x0050 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r0 = r3.f     // Catch:{ all -> 0x0050 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0050 }
            okhttp3.internal.cache.DiskLruCache$Entry r0 = (okhttp3.internal.cache.DiskLruCache.Entry) r0     // Catch:{ all -> 0x0050 }
            r1 = 0
            if (r0 == 0) goto L_0x004e
            boolean r2 = r0.e     // Catch:{ all -> 0x0050 }
            if (r2 != 0) goto L_0x001a
            goto L_0x004e
        L_0x001a:
            okhttp3.internal.cache.DiskLruCache$Snapshot r0 = r0.a()     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0022
            monitor-exit(r3)
            return r1
        L_0x0022:
            int r1 = r3.g     // Catch:{ all -> 0x0050 }
            int r1 = r1 + 1
            r3.g = r1     // Catch:{ all -> 0x0050 }
            okio.BufferedSink r1 = r3.e     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = "READ"
            okio.BufferedSink r1 = r1.writeUtf8(r2)     // Catch:{ all -> 0x0050 }
            r2 = 32
            okio.BufferedSink r1 = r1.writeByte(r2)     // Catch:{ all -> 0x0050 }
            okio.BufferedSink r4 = r1.writeUtf8(r4)     // Catch:{ all -> 0x0050 }
            r1 = 10
            r4.writeByte(r1)     // Catch:{ all -> 0x0050 }
            boolean r4 = r3.b()     // Catch:{ all -> 0x0050 }
            if (r4 == 0) goto L_0x004c
            java.util.concurrent.Executor r4 = r3.u     // Catch:{ all -> 0x0050 }
            java.lang.Runnable r1 = r3.v     // Catch:{ all -> 0x0050 }
            r4.execute(r1)     // Catch:{ all -> 0x0050 }
        L_0x004c:
            monitor-exit(r3)
            return r0
        L_0x004e:
            monitor-exit(r3)
            return r1
        L_0x0050:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.get(java.lang.String):okhttp3.internal.cache.DiskLruCache$Snapshot");
    }

    @Nullable
    public Editor edit(String str) {
        return a(str, -1);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized okhttp3.internal.cache.DiskLruCache.Editor a(java.lang.String r6, long r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.initialize()     // Catch:{ all -> 0x0074 }
            r5.g()     // Catch:{ all -> 0x0074 }
            r5.b(r6)     // Catch:{ all -> 0x0074 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r0 = r5.f     // Catch:{ all -> 0x0074 }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x0074 }
            okhttp3.internal.cache.DiskLruCache$Entry r0 = (okhttp3.internal.cache.DiskLruCache.Entry) r0     // Catch:{ all -> 0x0074 }
            r1 = -1
            int r3 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            r1 = 0
            if (r3 == 0) goto L_0x0023
            if (r0 == 0) goto L_0x0021
            long r2 = r0.g     // Catch:{ all -> 0x0074 }
            int r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r4 == 0) goto L_0x0023
        L_0x0021:
            monitor-exit(r5)
            return r1
        L_0x0023:
            if (r0 == 0) goto L_0x002b
            okhttp3.internal.cache.DiskLruCache$Editor r7 = r0.f     // Catch:{ all -> 0x0074 }
            if (r7 == 0) goto L_0x002b
            monitor-exit(r5)
            return r1
        L_0x002b:
            boolean r7 = r5.k     // Catch:{ all -> 0x0074 }
            if (r7 != 0) goto L_0x006b
            boolean r7 = r5.l     // Catch:{ all -> 0x0074 }
            if (r7 == 0) goto L_0x0034
            goto L_0x006b
        L_0x0034:
            okio.BufferedSink r7 = r5.e     // Catch:{ all -> 0x0074 }
            java.lang.String r8 = "DIRTY"
            okio.BufferedSink r7 = r7.writeUtf8(r8)     // Catch:{ all -> 0x0074 }
            r8 = 32
            okio.BufferedSink r7 = r7.writeByte(r8)     // Catch:{ all -> 0x0074 }
            okio.BufferedSink r7 = r7.writeUtf8(r6)     // Catch:{ all -> 0x0074 }
            r8 = 10
            r7.writeByte(r8)     // Catch:{ all -> 0x0074 }
            okio.BufferedSink r7 = r5.e     // Catch:{ all -> 0x0074 }
            r7.flush()     // Catch:{ all -> 0x0074 }
            boolean r7 = r5.h     // Catch:{ all -> 0x0074 }
            if (r7 == 0) goto L_0x0056
            monitor-exit(r5)
            return r1
        L_0x0056:
            if (r0 != 0) goto L_0x0062
            okhttp3.internal.cache.DiskLruCache$Entry r0 = new okhttp3.internal.cache.DiskLruCache$Entry     // Catch:{ all -> 0x0074 }
            r0.<init>(r6)     // Catch:{ all -> 0x0074 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r7 = r5.f     // Catch:{ all -> 0x0074 }
            r7.put(r6, r0)     // Catch:{ all -> 0x0074 }
        L_0x0062:
            okhttp3.internal.cache.DiskLruCache$Editor r6 = new okhttp3.internal.cache.DiskLruCache$Editor     // Catch:{ all -> 0x0074 }
            r6.<init>(r0)     // Catch:{ all -> 0x0074 }
            r0.f = r6     // Catch:{ all -> 0x0074 }
            monitor-exit(r5)
            return r6
        L_0x006b:
            java.util.concurrent.Executor r6 = r5.u     // Catch:{ all -> 0x0074 }
            java.lang.Runnable r7 = r5.v     // Catch:{ all -> 0x0074 }
            r6.execute(r7)     // Catch:{ all -> 0x0074 }
            monitor-exit(r5)
            return r1
        L_0x0074:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.a(java.lang.String, long):okhttp3.internal.cache.DiskLruCache$Editor");
    }

    public File getDirectory() {
        return this.c;
    }

    public synchronized long getMaxSize() {
        return this.r;
    }

    public synchronized void setMaxSize(long j2) {
        this.r = j2;
        if (this.i) {
            this.u.execute(this.v);
        }
    }

    public synchronized long size() {
        initialize();
        return this.s;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00fe, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(okhttp3.internal.cache.DiskLruCache.Editor r12, boolean r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            okhttp3.internal.cache.DiskLruCache$Entry r0 = r12.a     // Catch:{ all -> 0x00ff }
            okhttp3.internal.cache.DiskLruCache$Editor r1 = r0.f     // Catch:{ all -> 0x00ff }
            if (r1 == r12) goto L_0x000d
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ff }
            r12.<init>()     // Catch:{ all -> 0x00ff }
            throw r12     // Catch:{ all -> 0x00ff }
        L_0x000d:
            r1 = 0
            if (r13 == 0) goto L_0x004d
            boolean r2 = r0.e     // Catch:{ all -> 0x00ff }
            if (r2 != 0) goto L_0x004d
            r2 = 0
        L_0x0015:
            int r3 = r11.d     // Catch:{ all -> 0x00ff }
            if (r2 >= r3) goto L_0x004d
            boolean[] r3 = r12.b     // Catch:{ all -> 0x00ff }
            boolean r3 = r3[r2]     // Catch:{ all -> 0x00ff }
            if (r3 != 0) goto L_0x0039
            r12.abort()     // Catch:{ all -> 0x00ff }
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ff }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ff }
            r13.<init>()     // Catch:{ all -> 0x00ff }
            java.lang.String r0 = "Newly created entry didn't create value for index "
            r13.append(r0)     // Catch:{ all -> 0x00ff }
            r13.append(r2)     // Catch:{ all -> 0x00ff }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x00ff }
            r12.<init>(r13)     // Catch:{ all -> 0x00ff }
            throw r12     // Catch:{ all -> 0x00ff }
        L_0x0039:
            okhttp3.internal.io.FileSystem r3 = r11.b     // Catch:{ all -> 0x00ff }
            java.io.File[] r4 = r0.d     // Catch:{ all -> 0x00ff }
            r4 = r4[r2]     // Catch:{ all -> 0x00ff }
            boolean r3 = r3.exists(r4)     // Catch:{ all -> 0x00ff }
            if (r3 != 0) goto L_0x004a
            r12.abort()     // Catch:{ all -> 0x00ff }
            monitor-exit(r11)
            return
        L_0x004a:
            int r2 = r2 + 1
            goto L_0x0015
        L_0x004d:
            int r12 = r11.d     // Catch:{ all -> 0x00ff }
            if (r1 >= r12) goto L_0x0088
            java.io.File[] r12 = r0.d     // Catch:{ all -> 0x00ff }
            r12 = r12[r1]     // Catch:{ all -> 0x00ff }
            if (r13 == 0) goto L_0x0080
            okhttp3.internal.io.FileSystem r2 = r11.b     // Catch:{ all -> 0x00ff }
            boolean r2 = r2.exists(r12)     // Catch:{ all -> 0x00ff }
            if (r2 == 0) goto L_0x0085
            java.io.File[] r2 = r0.c     // Catch:{ all -> 0x00ff }
            r2 = r2[r1]     // Catch:{ all -> 0x00ff }
            okhttp3.internal.io.FileSystem r3 = r11.b     // Catch:{ all -> 0x00ff }
            r3.rename(r12, r2)     // Catch:{ all -> 0x00ff }
            long[] r12 = r0.b     // Catch:{ all -> 0x00ff }
            r3 = r12[r1]     // Catch:{ all -> 0x00ff }
            okhttp3.internal.io.FileSystem r12 = r11.b     // Catch:{ all -> 0x00ff }
            long r5 = r12.size(r2)     // Catch:{ all -> 0x00ff }
            long[] r12 = r0.b     // Catch:{ all -> 0x00ff }
            r12[r1] = r5     // Catch:{ all -> 0x00ff }
            long r7 = r11.s     // Catch:{ all -> 0x00ff }
            r12 = 0
            long r9 = r7 - r3
            long r2 = r9 + r5
            r11.s = r2     // Catch:{ all -> 0x00ff }
            goto L_0x0085
        L_0x0080:
            okhttp3.internal.io.FileSystem r2 = r11.b     // Catch:{ all -> 0x00ff }
            r2.delete(r12)     // Catch:{ all -> 0x00ff }
        L_0x0085:
            int r1 = r1 + 1
            goto L_0x004d
        L_0x0088:
            int r12 = r11.g     // Catch:{ all -> 0x00ff }
            r1 = 1
            int r12 = r12 + r1
            r11.g = r12     // Catch:{ all -> 0x00ff }
            r12 = 0
            r0.f = r12     // Catch:{ all -> 0x00ff }
            boolean r12 = r0.e     // Catch:{ all -> 0x00ff }
            r12 = r12 | r13
            r2 = 10
            r3 = 32
            if (r12 == 0) goto L_0x00c5
            r0.e = r1     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            java.lang.String r1 = "CLEAN"
            okio.BufferedSink r12 = r12.writeUtf8(r1)     // Catch:{ all -> 0x00ff }
            r12.writeByte(r3)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            java.lang.String r1 = r0.a     // Catch:{ all -> 0x00ff }
            r12.writeUtf8(r1)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            r0.a(r12)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            r12.writeByte(r2)     // Catch:{ all -> 0x00ff }
            if (r13 == 0) goto L_0x00e3
            long r12 = r11.t     // Catch:{ all -> 0x00ff }
            r1 = 1
            long r3 = r12 + r1
            r11.t = r3     // Catch:{ all -> 0x00ff }
            r0.g = r12     // Catch:{ all -> 0x00ff }
            goto L_0x00e3
        L_0x00c5:
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r12 = r11.f     // Catch:{ all -> 0x00ff }
            java.lang.String r13 = r0.a     // Catch:{ all -> 0x00ff }
            r12.remove(r13)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            java.lang.String r13 = "REMOVE"
            okio.BufferedSink r12 = r12.writeUtf8(r13)     // Catch:{ all -> 0x00ff }
            r12.writeByte(r3)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            java.lang.String r13 = r0.a     // Catch:{ all -> 0x00ff }
            r12.writeUtf8(r13)     // Catch:{ all -> 0x00ff }
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            r12.writeByte(r2)     // Catch:{ all -> 0x00ff }
        L_0x00e3:
            okio.BufferedSink r12 = r11.e     // Catch:{ all -> 0x00ff }
            r12.flush()     // Catch:{ all -> 0x00ff }
            long r12 = r11.s     // Catch:{ all -> 0x00ff }
            long r0 = r11.r     // Catch:{ all -> 0x00ff }
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r2 > 0) goto L_0x00f6
            boolean r12 = r11.b()     // Catch:{ all -> 0x00ff }
            if (r12 == 0) goto L_0x00fd
        L_0x00f6:
            java.util.concurrent.Executor r12 = r11.u     // Catch:{ all -> 0x00ff }
            java.lang.Runnable r13 = r11.v     // Catch:{ all -> 0x00ff }
            r12.execute(r13)     // Catch:{ all -> 0x00ff }
        L_0x00fd:
            monitor-exit(r11)
            return
        L_0x00ff:
            r12 = move-exception
            monitor-exit(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.a(okhttp3.internal.cache.DiskLruCache$Editor, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return this.g >= 2000 && this.g >= this.f.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        return r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean remove(java.lang.String r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            r6.initialize()     // Catch:{ all -> 0x0029 }
            r6.g()     // Catch:{ all -> 0x0029 }
            r6.b(r7)     // Catch:{ all -> 0x0029 }
            java.util.LinkedHashMap<java.lang.String, okhttp3.internal.cache.DiskLruCache$Entry> r0 = r6.f     // Catch:{ all -> 0x0029 }
            java.lang.Object r7 = r0.get(r7)     // Catch:{ all -> 0x0029 }
            okhttp3.internal.cache.DiskLruCache$Entry r7 = (okhttp3.internal.cache.DiskLruCache.Entry) r7     // Catch:{ all -> 0x0029 }
            r0 = 0
            if (r7 != 0) goto L_0x0017
            monitor-exit(r6)
            return r0
        L_0x0017:
            boolean r7 = r6.a(r7)     // Catch:{ all -> 0x0029 }
            if (r7 == 0) goto L_0x0027
            long r1 = r6.s     // Catch:{ all -> 0x0029 }
            long r3 = r6.r     // Catch:{ all -> 0x0029 }
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0027
            r6.k = r0     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r6)
            return r7
        L_0x0029:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache.DiskLruCache.remove(java.lang.String):boolean");
    }

    /* access modifiers changed from: 0000 */
    public boolean a(Entry entry) {
        if (entry.f != null) {
            entry.f.a();
        }
        for (int i2 = 0; i2 < this.d; i2++) {
            this.b.delete(entry.c[i2]);
            this.s -= entry.b[i2];
            entry.b[i2] = 0;
        }
        this.g++;
        this.e.writeUtf8("REMOVE").writeByte(32).writeUtf8(entry.a).writeByte(10);
        this.f.remove(entry.a);
        if (b()) {
            this.u.execute(this.v);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.j;
    }

    private synchronized void g() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void flush() {
        if (this.i) {
            g();
            c();
            this.e.flush();
        }
    }

    public synchronized void close() {
        Entry[] entryArr;
        if (this.i) {
            if (!this.j) {
                for (Entry entry : (Entry[]) this.f.values().toArray(new Entry[this.f.size()])) {
                    if (entry.f != null) {
                        entry.f.abort();
                    }
                }
                c();
                this.e.close();
                this.e = null;
                this.j = true;
                return;
            }
        }
        this.j = true;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        while (this.s > this.r) {
            a((Entry) this.f.values().iterator().next());
        }
        this.k = false;
    }

    public void delete() {
        close();
        this.b.deleteContents(this.c);
    }

    public synchronized void evictAll() {
        initialize();
        for (Entry a2 : (Entry[]) this.f.values().toArray(new Entry[this.f.size()])) {
            a(a2);
        }
        this.k = false;
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
        initialize();
        return new Iterator<Snapshot>() {
            final Iterator<Entry> a = new ArrayList(DiskLruCache.this.f.values()).iterator();
            Snapshot b;
            Snapshot c;

            public boolean hasNext() {
                if (this.b != null) {
                    return true;
                }
                synchronized (DiskLruCache.this) {
                    if (DiskLruCache.this.j) {
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
