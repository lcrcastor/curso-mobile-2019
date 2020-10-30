package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.VolleyLog;
import java.io.EOFException;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class DiskBasedCache implements Cache {
    private final Map<String, CacheHeader> a;
    private long b;
    private final File c;
    private final int d;

    static class CacheHeader {
        public long a;
        public String b;
        public String c;
        public long d;
        public long e;
        public long f;
        public long g;
        public Map<String, String> h;

        private CacheHeader() {
        }

        public CacheHeader(String str, Entry entry) {
            this.b = str;
            this.a = (long) entry.data.length;
            this.c = entry.etag;
            this.d = entry.serverDate;
            this.e = entry.lastModified;
            this.f = entry.ttl;
            this.g = entry.softTtl;
            this.h = entry.responseHeaders;
        }

        public static CacheHeader a(InputStream inputStream) {
            CacheHeader cacheHeader = new CacheHeader();
            if (DiskBasedCache.a(inputStream) != 538247942) {
                throw new IOException();
            }
            cacheHeader.b = DiskBasedCache.c(inputStream);
            cacheHeader.c = DiskBasedCache.c(inputStream);
            if (cacheHeader.c.equals("")) {
                cacheHeader.c = null;
            }
            cacheHeader.d = DiskBasedCache.b(inputStream);
            cacheHeader.e = DiskBasedCache.b(inputStream);
            cacheHeader.f = DiskBasedCache.b(inputStream);
            cacheHeader.g = DiskBasedCache.b(inputStream);
            cacheHeader.h = DiskBasedCache.d(inputStream);
            return cacheHeader;
        }

        public Entry a(byte[] bArr) {
            Entry entry = new Entry();
            entry.data = bArr;
            entry.etag = this.c;
            entry.serverDate = this.d;
            entry.lastModified = this.e;
            entry.ttl = this.f;
            entry.softTtl = this.g;
            entry.responseHeaders = this.h;
            return entry;
        }

        public boolean a(OutputStream outputStream) {
            try {
                DiskBasedCache.a(outputStream, 538247942);
                DiskBasedCache.a(outputStream, this.b);
                DiskBasedCache.a(outputStream, this.c == null ? "" : this.c);
                DiskBasedCache.a(outputStream, this.d);
                DiskBasedCache.a(outputStream, this.e);
                DiskBasedCache.a(outputStream, this.f);
                DiskBasedCache.a(outputStream, this.g);
                DiskBasedCache.a(this.h, outputStream);
                outputStream.flush();
                return true;
            } catch (IOException e2) {
                VolleyLog.d("%s", e2.toString());
                return false;
            }
        }
    }

    static class CountingInputStream extends FilterInputStream {
        /* access modifiers changed from: private */
        public int a;

        private CountingInputStream(InputStream inputStream) {
            super(inputStream);
            this.a = 0;
        }

        public int read() {
            int read = super.read();
            if (read != -1) {
                this.a++;
            }
            return read;
        }

        public int read(byte[] bArr, int i, int i2) {
            int read = super.read(bArr, i, i2);
            if (read != -1) {
                this.a += read;
            }
            return read;
        }
    }

    public DiskBasedCache(File file, int i) {
        this.a = new LinkedHashMap(16, 0.75f, true);
        this.b = 0;
        this.c = file;
        this.d = i;
    }

    public DiskBasedCache(File file) {
        this(file, 5242880);
    }

    public synchronized void clear() {
        File[] listFiles = this.c.listFiles();
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
        this.a.clear();
        this.b = 0;
        VolleyLog.d("Cache cleared.", new Object[0]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        return r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0066 A[SYNTHETIC, Splitter:B:32:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0088 A[SYNTHETIC, Splitter:B:45:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0093 A[SYNTHETIC, Splitter:B:54:0x0093] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.android.volley.Cache.Entry get(java.lang.String r14) {
        /*
            r13 = this;
            monitor-enter(r13)
            java.util.Map<java.lang.String, com.android.volley.toolbox.DiskBasedCache$CacheHeader> r0 = r13.a     // Catch:{ all -> 0x009a }
            java.lang.Object r0 = r0.get(r14)     // Catch:{ all -> 0x009a }
            com.android.volley.toolbox.DiskBasedCache$CacheHeader r0 = (com.android.volley.toolbox.DiskBasedCache.CacheHeader) r0     // Catch:{ all -> 0x009a }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r13)
            return r1
        L_0x000e:
            java.io.File r2 = r13.getFileForKey(r14)     // Catch:{ all -> 0x009a }
            r3 = 1
            r4 = 0
            r5 = 2
            com.android.volley.toolbox.DiskBasedCache$CountingInputStream r6 = new com.android.volley.toolbox.DiskBasedCache$CountingInputStream     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            r8.<init>(r2)     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            r7.<init>(r8)     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x006e, NegativeArraySizeException -> 0x004c, all -> 0x0049 }
            com.android.volley.toolbox.DiskBasedCache.CacheHeader.a(r6)     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            long r7 = r2.length()     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            int r9 = r6.a     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            long r9 = (long) r9     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            long r11 = r7 - r9
            int r7 = (int) r11     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            byte[] r7 = a(r6, r7)     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            com.android.volley.Cache$Entry r0 = r0.a(r7)     // Catch:{ IOException -> 0x0047, NegativeArraySizeException -> 0x0045 }
            if (r6 == 0) goto L_0x0043
            r6.close()     // Catch:{ IOException -> 0x0041 }
            goto L_0x0043
        L_0x0041:
            monitor-exit(r13)
            return r1
        L_0x0043:
            monitor-exit(r13)
            return r0
        L_0x0045:
            r0 = move-exception
            goto L_0x004e
        L_0x0047:
            r0 = move-exception
            goto L_0x0070
        L_0x0049:
            r14 = move-exception
            r6 = r1
            goto L_0x0091
        L_0x004c:
            r0 = move-exception
            r6 = r1
        L_0x004e:
            java.lang.String r7 = "%s: %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0090 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x0090 }
            r5[r4] = r2     // Catch:{ all -> 0x0090 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0090 }
            r5[r3] = r0     // Catch:{ all -> 0x0090 }
            com.android.volley.VolleyLog.d(r7, r5)     // Catch:{ all -> 0x0090 }
            r13.remove(r14)     // Catch:{ all -> 0x0090 }
            if (r6 == 0) goto L_0x006c
            r6.close()     // Catch:{ IOException -> 0x006a }
            goto L_0x006c
        L_0x006a:
            monitor-exit(r13)
            return r1
        L_0x006c:
            monitor-exit(r13)
            return r1
        L_0x006e:
            r0 = move-exception
            r6 = r1
        L_0x0070:
            java.lang.String r7 = "%s: %s"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x0090 }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ all -> 0x0090 }
            r5[r4] = r2     // Catch:{ all -> 0x0090 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0090 }
            r5[r3] = r0     // Catch:{ all -> 0x0090 }
            com.android.volley.VolleyLog.d(r7, r5)     // Catch:{ all -> 0x0090 }
            r13.remove(r14)     // Catch:{ all -> 0x0090 }
            if (r6 == 0) goto L_0x008e
            r6.close()     // Catch:{ IOException -> 0x008c }
            goto L_0x008e
        L_0x008c:
            monitor-exit(r13)
            return r1
        L_0x008e:
            monitor-exit(r13)
            return r1
        L_0x0090:
            r14 = move-exception
        L_0x0091:
            if (r6 == 0) goto L_0x0099
            r6.close()     // Catch:{ IOException -> 0x0097 }
            goto L_0x0099
        L_0x0097:
            monitor-exit(r13)
            return r1
        L_0x0099:
            throw r14     // Catch:{ all -> 0x009a }
        L_0x009a:
            r14 = move-exception
            monitor-exit(r13)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.DiskBasedCache.get(java.lang.String):com.android.volley.Cache$Entry");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:26|27|(2:36|37)|38|39) */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x0065 */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x005c A[SYNTHETIC, Splitter:B:33:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0068 A[SYNTHETIC, Splitter:B:41:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x006b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initialize() {
        /*
            r8 = this;
            monitor-enter(r8)
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            boolean r0 = r0.exists()     // Catch:{ all -> 0x0070 }
            r1 = 0
            if (r0 != 0) goto L_0x0024
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            boolean r0 = r0.mkdirs()     // Catch:{ all -> 0x0070 }
            if (r0 != 0) goto L_0x0022
            java.lang.String r0 = "Unable to create cache dir %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0070 }
            java.io.File r3 = r8.c     // Catch:{ all -> 0x0070 }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x0070 }
            r2[r1] = r3     // Catch:{ all -> 0x0070 }
            com.android.volley.VolleyLog.e(r0, r2)     // Catch:{ all -> 0x0070 }
        L_0x0022:
            monitor-exit(r8)
            return
        L_0x0024:
            java.io.File r0 = r8.c     // Catch:{ all -> 0x0070 }
            java.io.File[] r0 = r0.listFiles()     // Catch:{ all -> 0x0070 }
            if (r0 != 0) goto L_0x002e
            monitor-exit(r8)
            return
        L_0x002e:
            int r2 = r0.length     // Catch:{ all -> 0x0070 }
        L_0x002f:
            if (r1 >= r2) goto L_0x006e
            r3 = r0[r1]     // Catch:{ all -> 0x0070 }
            r4 = 0
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x005a }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x005a }
            r6.<init>(r3)     // Catch:{ IOException -> 0x005a }
            r5.<init>(r6)     // Catch:{ IOException -> 0x005a }
            com.android.volley.toolbox.DiskBasedCache$CacheHeader r4 = com.android.volley.toolbox.DiskBasedCache.CacheHeader.a(r5)     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            long r6 = r3.length()     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            r4.a = r6     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            java.lang.String r6 = r4.b     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            r8.a(r6, r4)     // Catch:{ IOException -> 0x0056, all -> 0x0053 }
            if (r5 == 0) goto L_0x006b
            r5.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006b
        L_0x0053:
            r0 = move-exception
            r4 = r5
            goto L_0x0060
        L_0x0056:
            r4 = r5
            goto L_0x005a
        L_0x0058:
            r0 = move-exception
            goto L_0x0060
        L_0x005a:
            if (r3 == 0) goto L_0x0066
            r3.delete()     // Catch:{ all -> 0x0058 }
            goto L_0x0066
        L_0x0060:
            if (r4 == 0) goto L_0x0065
            r4.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0065:
            throw r0     // Catch:{ all -> 0x0070 }
        L_0x0066:
            if (r4 == 0) goto L_0x006b
            r4.close()     // Catch:{ IOException -> 0x006b }
        L_0x006b:
            int r1 = r1 + 1
            goto L_0x002f
        L_0x006e:
            monitor-exit(r8)
            return
        L_0x0070:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.DiskBasedCache.initialize():void");
    }

    public synchronized void invalidate(String str, boolean z) {
        Entry entry = get(str);
        if (entry != null) {
            entry.softTtl = 0;
            if (z) {
                entry.ttl = 0;
            }
            put(str, entry);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:12|13|(1:15)|16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        if (r0.delete() == false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
        com.android.volley.VolleyLog.d("Could not clean up file %s", r0.getAbsolutePath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0059, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0045 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void put(java.lang.String r7, com.android.volley.Cache.Entry r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            byte[] r0 = r8.data     // Catch:{ all -> 0x005a }
            int r0 = r0.length     // Catch:{ all -> 0x005a }
            r6.a(r0)     // Catch:{ all -> 0x005a }
            java.io.File r0 = r6.getFileForKey(r7)     // Catch:{ all -> 0x005a }
            r1 = 0
            r2 = 1
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0045 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0045 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x0045 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0045 }
            com.android.volley.toolbox.DiskBasedCache$CacheHeader r4 = new com.android.volley.toolbox.DiskBasedCache$CacheHeader     // Catch:{ IOException -> 0x0045 }
            r4.<init>(r7, r8)     // Catch:{ IOException -> 0x0045 }
            boolean r5 = r4.a(r3)     // Catch:{ IOException -> 0x0045 }
            if (r5 != 0) goto L_0x0038
            r3.close()     // Catch:{ IOException -> 0x0045 }
            java.lang.String r7 = "Failed to write header for %s"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0045 }
            java.lang.String r3 = r0.getAbsolutePath()     // Catch:{ IOException -> 0x0045 }
            r8[r1] = r3     // Catch:{ IOException -> 0x0045 }
            com.android.volley.VolleyLog.d(r7, r8)     // Catch:{ IOException -> 0x0045 }
            java.io.IOException r7 = new java.io.IOException     // Catch:{ IOException -> 0x0045 }
            r7.<init>()     // Catch:{ IOException -> 0x0045 }
            throw r7     // Catch:{ IOException -> 0x0045 }
        L_0x0038:
            byte[] r8 = r8.data     // Catch:{ IOException -> 0x0045 }
            r3.write(r8)     // Catch:{ IOException -> 0x0045 }
            r3.close()     // Catch:{ IOException -> 0x0045 }
            r6.a(r7, r4)     // Catch:{ IOException -> 0x0045 }
            monitor-exit(r6)
            return
        L_0x0045:
            boolean r7 = r0.delete()     // Catch:{ all -> 0x005a }
            if (r7 != 0) goto L_0x0058
            java.lang.String r7 = "Could not clean up file %s"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ all -> 0x005a }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ all -> 0x005a }
            r8[r1] = r0     // Catch:{ all -> 0x005a }
            com.android.volley.VolleyLog.d(r7, r8)     // Catch:{ all -> 0x005a }
        L_0x0058:
            monitor-exit(r6)
            return
        L_0x005a:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.DiskBasedCache.put(java.lang.String, com.android.volley.Cache$Entry):void");
    }

    public synchronized void remove(String str) {
        boolean delete = getFileForKey(str).delete();
        b(str);
        if (!delete) {
            VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", str, a(str));
        }
    }

    private String a(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(str.substring(0, length).hashCode());
        StringBuilder sb = new StringBuilder();
        sb.append(valueOf);
        sb.append(String.valueOf(str.substring(length).hashCode()));
        return sb.toString();
    }

    public File getFileForKey(String str) {
        return new File(this.c, a(str));
    }

    private void a(int i) {
        long j;
        long j2;
        long j3 = (long) i;
        if (this.b + j3 >= ((long) this.d)) {
            if (VolleyLog.DEBUG) {
                VolleyLog.v("Pruning old cache entries.", new Object[0]);
            }
            long j4 = this.b;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            Iterator it = this.a.entrySet().iterator();
            int i2 = 0;
            while (true) {
                if (!it.hasNext()) {
                    j = elapsedRealtime;
                    break;
                }
                CacheHeader cacheHeader = (CacheHeader) ((Map.Entry) it.next()).getValue();
                if (getFileForKey(cacheHeader.b).delete()) {
                    j2 = j3;
                    j = elapsedRealtime;
                    this.b -= cacheHeader.a;
                } else {
                    j2 = j3;
                    j = elapsedRealtime;
                    VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", cacheHeader.b, a(cacheHeader.b));
                }
                it.remove();
                i2++;
                if (((float) (this.b + j2)) < ((float) this.d) * 0.9f) {
                    break;
                }
                j3 = j2;
                elapsedRealtime = j;
            }
            if (VolleyLog.DEBUG) {
                VolleyLog.v("pruned %d files, %d bytes, %d ms", Integer.valueOf(i2), Long.valueOf(this.b - j4), Long.valueOf(SystemClock.elapsedRealtime() - j));
            }
        }
    }

    private void a(String str, CacheHeader cacheHeader) {
        if (!this.a.containsKey(str)) {
            this.b += cacheHeader.a;
        } else {
            this.b += cacheHeader.a - ((CacheHeader) this.a.get(str)).a;
        }
        this.a.put(str, cacheHeader);
    }

    private void b(String str) {
        CacheHeader cacheHeader = (CacheHeader) this.a.get(str);
        if (cacheHeader != null) {
            this.b -= cacheHeader.a;
            this.a.remove(str);
        }
    }

    private static byte[] a(InputStream inputStream, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == i) {
            return bArr;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Expected ");
        sb.append(i);
        sb.append(" bytes, read ");
        sb.append(i2);
        sb.append(" bytes");
        throw new IOException(sb.toString());
    }

    private static int e(InputStream inputStream) {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static void a(OutputStream outputStream, int i) {
        outputStream.write((i >> 0) & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write((i >> 24) & 255);
    }

    static int a(InputStream inputStream) {
        return (e(inputStream) << 24) | (e(inputStream) << 0) | 0 | (e(inputStream) << 8) | (e(inputStream) << 16);
    }

    static void a(OutputStream outputStream, long j) {
        outputStream.write((byte) ((int) (j >>> 0)));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static long b(InputStream inputStream) {
        return ((((long) e(inputStream)) & 255) << 0) | 0 | ((((long) e(inputStream)) & 255) << 8) | ((((long) e(inputStream)) & 255) << 16) | ((((long) e(inputStream)) & 255) << 24) | ((((long) e(inputStream)) & 255) << 32) | ((((long) e(inputStream)) & 255) << 40) | ((((long) e(inputStream)) & 255) << 48) | ((((long) e(inputStream)) & 255) << 56);
    }

    static void a(OutputStream outputStream, String str) {
        byte[] bytes = str.getBytes("UTF-8");
        a(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    static String c(InputStream inputStream) {
        return new String(a(inputStream, (int) b(inputStream)), "UTF-8");
    }

    static void a(Map<String, String> map, OutputStream outputStream) {
        if (map != null) {
            a(outputStream, map.size());
            for (Map.Entry entry : map.entrySet()) {
                a(outputStream, (String) entry.getKey());
                a(outputStream, (String) entry.getValue());
            }
            return;
        }
        a(outputStream, 0);
    }

    static Map<String, String> d(InputStream inputStream) {
        int a2 = a(inputStream);
        Map<String, String> emptyMap = a2 == 0 ? Collections.emptyMap() : new HashMap<>(a2);
        for (int i = 0; i < a2; i++) {
            emptyMap.put(c(inputStream).intern(), c(inputStream).intern());
        }
        return emptyMap;
    }
}
