package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.Settings;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FileLruCache {
    static final String a = "FileLruCache";
    /* access modifiers changed from: private */
    public static final AtomicLong b = new AtomicLong();
    private final String c;
    private final Limits d;
    private final File e;
    private boolean f;
    private boolean g;
    private final Object h;
    /* access modifiers changed from: private */
    public AtomicLong i = new AtomicLong(0);

    static class BufferFile {
        private static final FilenameFilter a = new FilenameFilter() {
            public boolean accept(File file, String str) {
                return !str.startsWith("buffer");
            }
        };
        private static final FilenameFilter b = new FilenameFilter() {
            public boolean accept(File file, String str) {
                return str.startsWith("buffer");
            }
        };

        private BufferFile() {
        }

        static void a(File file) {
            File[] listFiles = file.listFiles(b());
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
        }

        static FilenameFilter a() {
            return a;
        }

        static FilenameFilter b() {
            return b;
        }

        static File b(File file) {
            StringBuilder sb = new StringBuilder();
            sb.append("buffer");
            sb.append(Long.valueOf(FileLruCache.b.incrementAndGet()).toString());
            return new File(file, sb.toString());
        }
    }

    static class CloseCallbackOutputStream extends OutputStream {
        final OutputStream a;
        final StreamCloseCallback b;

        CloseCallbackOutputStream(OutputStream outputStream, StreamCloseCallback streamCloseCallback) {
            this.a = outputStream;
            this.b = streamCloseCallback;
        }

        public void close() {
            try {
                this.a.close();
            } finally {
                this.b.a();
            }
        }

        public void flush() {
            this.a.flush();
        }

        public void write(byte[] bArr, int i, int i2) {
            this.a.write(bArr, i, i2);
        }

        public void write(byte[] bArr) {
            this.a.write(bArr);
        }

        public void write(int i) {
            this.a.write(i);
        }
    }

    static final class CopyingInputStream extends InputStream {
        final InputStream a;
        final OutputStream b;

        public boolean markSupported() {
            return false;
        }

        CopyingInputStream(InputStream inputStream, OutputStream outputStream) {
            this.a = inputStream;
            this.b = outputStream;
        }

        public int available() {
            return this.a.available();
        }

        public void close() {
            try {
                this.a.close();
            } finally {
                this.b.close();
            }
        }

        public void mark(int i) {
            throw new UnsupportedOperationException();
        }

        public int read(byte[] bArr) {
            int read = this.a.read(bArr);
            if (read > 0) {
                this.b.write(bArr, 0, read);
            }
            return read;
        }

        public int read() {
            int read = this.a.read();
            if (read >= 0) {
                this.b.write(read);
            }
            return read;
        }

        public int read(byte[] bArr, int i, int i2) {
            int read = this.a.read(bArr, i, i2);
            if (read > 0) {
                this.b.write(bArr, i, read);
            }
            return read;
        }

        public synchronized void reset() {
            throw new UnsupportedOperationException();
        }

        public long skip(long j) {
            byte[] bArr = new byte[1024];
            long j2 = 0;
            while (j2 < j) {
                int read = read(bArr, 0, (int) Math.min(j - j2, (long) bArr.length));
                if (read < 0) {
                    return j2;
                }
                j2 += (long) read;
            }
            return j2;
        }
    }

    public static final class Limits {
        private int a = 1048576;
        private int b = 1024;

        /* access modifiers changed from: 0000 */
        public int a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public int b() {
            return this.b;
        }
    }

    static final class ModifiedFile implements Comparable<ModifiedFile> {
        private final File a;
        private final long b;

        ModifiedFile(File file) {
            this.a = file;
            this.b = file.lastModified();
        }

        /* access modifiers changed from: 0000 */
        public File a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public long b() {
            return this.b;
        }

        /* renamed from: a */
        public int compareTo(ModifiedFile modifiedFile) {
            if (b() < modifiedFile.b()) {
                return -1;
            }
            if (b() > modifiedFile.b()) {
                return 1;
            }
            return a().compareTo(modifiedFile.a());
        }

        public boolean equals(Object obj) {
            return (obj instanceof ModifiedFile) && compareTo((ModifiedFile) obj) == 0;
        }

        public int hashCode() {
            return ((1073 + this.a.hashCode()) * 37) + ((int) (this.b % 2147483647L));
        }
    }

    interface StreamCloseCallback {
        void a();
    }

    static final class StreamHeader {
        private StreamHeader() {
        }

        static void a(OutputStream outputStream, JSONObject jSONObject) {
            byte[] bytes = jSONObject.toString().getBytes();
            outputStream.write(0);
            outputStream.write((bytes.length >> 16) & 255);
            outputStream.write((bytes.length >> 8) & 255);
            outputStream.write((bytes.length >> 0) & 255);
            outputStream.write(bytes);
        }

        static JSONObject a(InputStream inputStream) {
            if (inputStream.read() != 0) {
                return null;
            }
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < 3; i3++) {
                int read = inputStream.read();
                if (read == -1) {
                    Logger.log(LoggingBehavior.CACHE, FileLruCache.a, "readHeader: stream.read returned -1 while reading header size");
                    return null;
                }
                i2 = (i2 << 8) + (read & 255);
            }
            byte[] bArr = new byte[i2];
            while (i < bArr.length) {
                int read2 = inputStream.read(bArr, i, bArr.length - i);
                if (read2 < 1) {
                    LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                    String str = FileLruCache.a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("readHeader: stream.read stopped at ");
                    sb.append(Integer.valueOf(i));
                    sb.append(" when expected ");
                    sb.append(bArr.length);
                    Logger.log(loggingBehavior, str, sb.toString());
                    return null;
                }
                i += read2;
            }
            try {
                Object nextValue = new JSONTokener(new String(bArr)).nextValue();
                if (nextValue instanceof JSONObject) {
                    return (JSONObject) nextValue;
                }
                LoggingBehavior loggingBehavior2 = LoggingBehavior.CACHE;
                String str2 = FileLruCache.a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("readHeader: expected JSONObject, got ");
                sb2.append(nextValue.getClass().getCanonicalName());
                Logger.log(loggingBehavior2, str2, sb2.toString());
                return null;
            } catch (JSONException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    public FileLruCache(Context context, String str, Limits limits) {
        this.c = str;
        this.d = limits;
        this.e = new File(context.getCacheDir(), str);
        this.h = new Object();
        if (this.e.mkdirs() || this.e.isDirectory()) {
            BufferFile.a(this.e);
        }
    }

    public InputStream get(String str) {
        return get(str, null);
    }

    public InputStream get(String str, String str2) {
        File file = new File(this.e, Utility.a(str));
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), 8192);
            try {
                JSONObject a2 = StreamHeader.a(bufferedInputStream);
                if (a2 == null) {
                    return null;
                }
                String optString = a2.optString("key");
                if (optString != null) {
                    if (optString.equals(str)) {
                        String optString2 = a2.optString("tag", null);
                        if ((str2 != null || optString2 == null) && (str2 == null || str2.equals(optString2))) {
                            long time = new Date().getTime();
                            LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                            String str3 = a;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Setting lastModified to ");
                            sb.append(Long.valueOf(time));
                            sb.append(" for ");
                            sb.append(file.getName());
                            Logger.log(loggingBehavior, str3, sb.toString());
                            file.setLastModified(time);
                            return bufferedInputStream;
                        }
                        bufferedInputStream.close();
                        return null;
                    }
                }
                bufferedInputStream.close();
                return null;
            } finally {
                bufferedInputStream.close();
            }
        } catch (IOException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public OutputStream a(String str) {
        return openPutStream(str, null);
    }

    public OutputStream openPutStream(String str, String str2) {
        final File b2 = BufferFile.b(this.e);
        b2.delete();
        if (!b2.createNewFile()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Could not create file at ");
            sb.append(b2.getAbsolutePath());
            throw new IOException(sb.toString());
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(b2);
            final long currentTimeMillis = System.currentTimeMillis();
            final String str3 = str;
            AnonymousClass1 r1 = new StreamCloseCallback() {
                public void a() {
                    if (currentTimeMillis < FileLruCache.this.i.get()) {
                        b2.delete();
                    } else {
                        FileLruCache.this.a(str3, b2);
                    }
                }
            };
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new CloseCallbackOutputStream(fileOutputStream, r1), 8192);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("key", str);
                if (!Utility.isNullOrEmpty(str2)) {
                    jSONObject.put("tag", str2);
                }
                StreamHeader.a(bufferedOutputStream, jSONObject);
                return bufferedOutputStream;
            } catch (JSONException e2) {
                LoggingBehavior loggingBehavior = LoggingBehavior.CACHE;
                String str4 = a;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error creating JSON header for cache file: ");
                sb2.append(e2);
                Logger.log(loggingBehavior, 5, str4, sb2.toString());
                throw new IOException(e2.getMessage());
            } catch (Throwable th) {
                bufferedOutputStream.close();
                throw th;
            }
        } catch (FileNotFoundException e3) {
            LoggingBehavior loggingBehavior2 = LoggingBehavior.CACHE;
            String str5 = a;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Error creating buffer output stream: ");
            sb3.append(e3);
            Logger.log(loggingBehavior2, 5, str5, sb3.toString());
            throw new IOException(e3.getMessage());
        }
    }

    public void clearCache() {
        final File[] listFiles = this.e.listFiles(BufferFile.a());
        this.i.set(System.currentTimeMillis());
        if (listFiles != null) {
            Settings.getExecutor().execute(new Runnable() {
                public void run() {
                    for (File delete : listFiles) {
                        delete.delete();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, File file) {
        if (!file.renameTo(new File(this.e, Utility.a(str)))) {
            file.delete();
        }
        b();
    }

    public InputStream interceptAndPut(String str, InputStream inputStream) {
        return new CopyingInputStream(inputStream, a(str));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{FileLruCache: tag:");
        sb.append(this.c);
        sb.append(" file:");
        sb.append(this.e.getName());
        sb.append("}");
        return sb.toString();
    }

    private void b() {
        synchronized (this.h) {
            if (!this.f) {
                this.f = true;
                Settings.getExecutor().execute(new Runnable() {
                    public void run() {
                        FileLruCache.this.c();
                    }
                });
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c() {
        /*
            r17 = this;
            r1 = r17
            java.lang.Object r2 = r1.h
            monitor-enter(r2)
            r3 = 0
            r1.f = r3     // Catch:{ all -> 0x0102 }
            r4 = 1
            r1.g = r4     // Catch:{ all -> 0x0102 }
            monitor-exit(r2)     // Catch:{ all -> 0x0102 }
            com.facebook.LoggingBehavior r2 = com.facebook.LoggingBehavior.CACHE     // Catch:{ all -> 0x00ef }
            java.lang.String r4 = a     // Catch:{ all -> 0x00ef }
            java.lang.String r5 = "trim started"
            com.facebook.internal.Logger.log(r2, r4, r5)     // Catch:{ all -> 0x00ef }
            java.util.PriorityQueue r2 = new java.util.PriorityQueue     // Catch:{ all -> 0x00ef }
            r2.<init>()     // Catch:{ all -> 0x00ef }
            java.io.File r4 = r1.e     // Catch:{ all -> 0x00ef }
            java.io.FilenameFilter r5 = com.facebook.internal.FileLruCache.BufferFile.a()     // Catch:{ all -> 0x00ef }
            java.io.File[] r4 = r4.listFiles(r5)     // Catch:{ all -> 0x00ef }
            r7 = 0
            if (r4 == 0) goto L_0x0089
            int r9 = r4.length     // Catch:{ all -> 0x00ef }
            r10 = r7
            r12 = r10
            r7 = 0
        L_0x002c:
            if (r7 >= r9) goto L_0x0085
            r8 = r4[r7]     // Catch:{ all -> 0x00ef }
            com.facebook.internal.FileLruCache$ModifiedFile r14 = new com.facebook.internal.FileLruCache$ModifiedFile     // Catch:{ all -> 0x00ef }
            r14.<init>(r8)     // Catch:{ all -> 0x00ef }
            r2.add(r14)     // Catch:{ all -> 0x00ef }
            com.facebook.LoggingBehavior r15 = com.facebook.LoggingBehavior.CACHE     // Catch:{ all -> 0x00ef }
            java.lang.String r3 = a     // Catch:{ all -> 0x00ef }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ef }
            r5.<init>()     // Catch:{ all -> 0x00ef }
            java.lang.String r6 = "  trim considering time="
            r5.append(r6)     // Catch:{ all -> 0x00ef }
            r16 = r2
            long r1 = r14.b()     // Catch:{ all -> 0x007f }
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x007f }
            r5.append(r1)     // Catch:{ all -> 0x007f }
            java.lang.String r1 = " name="
            r5.append(r1)     // Catch:{ all -> 0x007f }
            java.io.File r1 = r14.a()     // Catch:{ all -> 0x007f }
            java.lang.String r1 = r1.getName()     // Catch:{ all -> 0x007f }
            r5.append(r1)     // Catch:{ all -> 0x007f }
            java.lang.String r1 = r5.toString()     // Catch:{ all -> 0x007f }
            com.facebook.internal.Logger.log(r15, r3, r1)     // Catch:{ all -> 0x007f }
            long r1 = r8.length()     // Catch:{ all -> 0x007f }
            r3 = 0
            long r5 = r10 + r1
            r1 = 1
            long r10 = r12 + r1
            int r7 = r7 + 1
            r12 = r10
            r2 = r16
            r1 = r17
            r3 = 0
            r10 = r5
            goto L_0x002c
        L_0x007f:
            r0 = move-exception
            r2 = r0
            r1 = r17
            goto L_0x00f1
        L_0x0085:
            r16 = r2
            r7 = r10
            goto L_0x008c
        L_0x0089:
            r16 = r2
            r12 = r7
        L_0x008c:
            com.facebook.internal.FileLruCache$Limits r2 = r1.d     // Catch:{ all -> 0x00ef }
            int r2 = r2.a()     // Catch:{ all -> 0x00ef }
            long r2 = (long) r2     // Catch:{ all -> 0x00ef }
            int r4 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r4 > 0) goto L_0x00b4
            com.facebook.internal.FileLruCache$Limits r2 = r1.d     // Catch:{ all -> 0x00ef }
            int r2 = r2.b()     // Catch:{ all -> 0x00ef }
            long r2 = (long) r2
            int r4 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x00a3
            goto L_0x00b4
        L_0x00a3:
            java.lang.Object r2 = r1.h
            monitor-enter(r2)
            r3 = 0
            r1.g = r3     // Catch:{ all -> 0x00b0 }
            java.lang.Object r3 = r1.h     // Catch:{ all -> 0x00b0 }
            r3.notifyAll()     // Catch:{ all -> 0x00b0 }
            monitor-exit(r2)     // Catch:{ all -> 0x00b0 }
            return
        L_0x00b0:
            r0 = move-exception
            r3 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x00b0 }
            throw r3
        L_0x00b4:
            r2 = r16
            java.lang.Object r3 = r2.remove()     // Catch:{ all -> 0x00ef }
            com.facebook.internal.FileLruCache$ModifiedFile r3 = (com.facebook.internal.FileLruCache.ModifiedFile) r3     // Catch:{ all -> 0x00ef }
            java.io.File r3 = r3.a()     // Catch:{ all -> 0x00ef }
            com.facebook.LoggingBehavior r4 = com.facebook.LoggingBehavior.CACHE     // Catch:{ all -> 0x00ef }
            java.lang.String r5 = a     // Catch:{ all -> 0x00ef }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ef }
            r6.<init>()     // Catch:{ all -> 0x00ef }
            java.lang.String r9 = "  trim removing "
            r6.append(r9)     // Catch:{ all -> 0x00ef }
            java.lang.String r9 = r3.getName()     // Catch:{ all -> 0x00ef }
            r6.append(r9)     // Catch:{ all -> 0x00ef }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00ef }
            com.facebook.internal.Logger.log(r4, r5, r6)     // Catch:{ all -> 0x00ef }
            long r4 = r3.length()     // Catch:{ all -> 0x00ef }
            r6 = 0
            long r9 = r7 - r4
            r4 = 1
            long r6 = r12 - r4
            r3.delete()     // Catch:{ all -> 0x00ef }
            r16 = r2
            r12 = r6
            r7 = r9
            goto L_0x008c
        L_0x00ef:
            r0 = move-exception
            r2 = r0
        L_0x00f1:
            java.lang.Object r3 = r1.h
            monitor-enter(r3)
            r4 = 0
            r1.g = r4     // Catch:{ all -> 0x00fe }
            java.lang.Object r4 = r1.h     // Catch:{ all -> 0x00fe }
            r4.notifyAll()     // Catch:{ all -> 0x00fe }
            monitor-exit(r3)     // Catch:{ all -> 0x00fe }
            throw r2
        L_0x00fe:
            r0 = move-exception
            r2 = r0
            monitor-exit(r3)     // Catch:{ all -> 0x00fe }
            throw r2
        L_0x0102:
            r0 = move-exception
            r3 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x0102 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FileLruCache.c():void");
    }
}
