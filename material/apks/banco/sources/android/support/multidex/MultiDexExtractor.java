package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

final class MultiDexExtractor implements Closeable {
    private final File a;
    private final long b;
    private final File c;
    private final RandomAccessFile d;
    private final FileChannel e;
    private final FileLock f;

    static class ExtractedDex extends File {
        public long a = -1;

        public ExtractedDex(File file, String str) {
            super(file, str);
        }
    }

    MultiDexExtractor(File file, File file2) {
        StringBuilder sb = new StringBuilder();
        sb.append("MultiDexExtractor(");
        sb.append(file.getPath());
        sb.append(", ");
        sb.append(file2.getPath());
        sb.append(")");
        Log.i("MultiDex", sb.toString());
        this.a = file;
        this.c = file2;
        this.b = b(file);
        File file3 = new File(file2, "MultiDex.lock");
        this.d = new RandomAccessFile(file3, "rw");
        try {
            this.e = this.d.getChannel();
            String str = "MultiDex";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Blocking on lock ");
            sb2.append(file3.getPath());
            Log.i(str, sb2.toString());
            this.f = this.e.lock();
            String str2 = "MultiDex";
            StringBuilder sb3 = new StringBuilder();
            sb3.append(file3.getPath());
            sb3.append(" locked");
            Log.i(str2, sb3.toString());
        } catch (IOException | Error | RuntimeException e2) {
            a((Closeable) this.e);
            throw e2;
        } catch (IOException | Error | RuntimeException e3) {
            a((Closeable) this.d);
            throw e3;
        }
    }

    /* access modifiers changed from: 0000 */
    public List<? extends File> a(Context context, String str, boolean z) {
        List<? extends File> list;
        StringBuilder sb = new StringBuilder();
        sb.append("MultiDexExtractor.load(");
        sb.append(this.a.getPath());
        sb.append(", ");
        sb.append(z);
        sb.append(", ");
        sb.append(str);
        sb.append(")");
        Log.i("MultiDex", sb.toString());
        if (!this.f.isValid()) {
            throw new IllegalStateException("MultiDexExtractor was closed");
        }
        if (z || a(context, this.a, this.b, str)) {
            if (z) {
                Log.i("MultiDex", "Forced extraction must be performed.");
            } else {
                Log.i("MultiDex", "Detected that extraction must be performed.");
            }
            list = a();
            a(context, str, a(this.a), this.b, list);
        } else {
            try {
                list = a(context, str);
            } catch (IOException e2) {
                Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e2);
                list = a();
                a(context, str, a(this.a), this.b, list);
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("load found ");
        sb2.append(list.size());
        sb2.append(" secondary dex files");
        Log.i("MultiDex", sb2.toString());
        return list;
    }

    public void close() {
        this.f.release();
        this.e.close();
        this.d.close();
    }

    private List<ExtractedDex> a(Context context, String str) {
        String str2 = str;
        Log.i("MultiDex", "loading existing secondary dex files");
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getName());
        sb.append(".classes");
        String sb2 = sb.toString();
        SharedPreferences a2 = a(context);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append("dex.number");
        int i = a2.getInt(sb3.toString(), 1);
        ArrayList arrayList = new ArrayList(i - 1);
        int i2 = 2;
        while (i2 <= i) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(i2);
            sb4.append(".zip");
            ExtractedDex extractedDex = new ExtractedDex(this.c, sb4.toString());
            if (extractedDex.isFile()) {
                extractedDex.a = b(extractedDex);
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str2);
                sb5.append("dex.crc.");
                sb5.append(i2);
                long j = a2.getLong(sb5.toString(), -1);
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str2);
                sb6.append("dex.time.");
                sb6.append(i2);
                long j2 = a2.getLong(sb6.toString(), -1);
                long lastModified = extractedDex.lastModified();
                if (j2 == lastModified) {
                    String str3 = sb2;
                    SharedPreferences sharedPreferences = a2;
                    if (j == extractedDex.a) {
                        arrayList.add(extractedDex);
                        i2++;
                        sb2 = str3;
                        a2 = sharedPreferences;
                    }
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Invalid extracted dex: ");
                sb7.append(extractedDex);
                sb7.append(" (key \"");
                sb7.append(str2);
                sb7.append("\"), expected modification time: ");
                sb7.append(j2);
                sb7.append(", modification time: ");
                sb7.append(lastModified);
                sb7.append(", expected crc: ");
                sb7.append(j);
                sb7.append(", file crc: ");
                sb7.append(extractedDex.a);
                throw new IOException(sb7.toString());
            }
            StringBuilder sb8 = new StringBuilder();
            sb8.append("Missing extracted secondary dex file '");
            sb8.append(extractedDex.getPath());
            sb8.append("'");
            throw new IOException(sb8.toString());
        }
        return arrayList;
    }

    private static boolean a(Context context, File file, long j, String str) {
        SharedPreferences a2 = a(context);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("timestamp");
        if (a2.getLong(sb.toString(), -1) == a(file)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("crc");
            if (a2.getLong(sb2.toString(), -1) == j) {
                return false;
            }
        }
        return true;
    }

    private static long a(File file) {
        long lastModified = file.lastModified();
        return lastModified == -1 ? lastModified - 1 : lastModified;
    }

    private static long b(File file) {
        long a2 = ZipUtil.a(file);
        return a2 == -1 ? a2 - 1 : a2;
    }

    private List<ExtractedDex> a() {
        ExtractedDex extractedDex;
        boolean z;
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.getName());
        sb.append(".classes");
        String sb2 = sb.toString();
        b();
        ArrayList arrayList = new ArrayList();
        ZipFile zipFile = new ZipFile(this.a);
        int i = 2;
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("classes");
            sb3.append(2);
            sb3.append(".dex");
            ZipEntry entry = zipFile.getEntry(sb3.toString());
            while (entry != null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(sb2);
                sb4.append(i);
                sb4.append(".zip");
                extractedDex = new ExtractedDex(this.c, sb4.toString());
                arrayList.add(extractedDex);
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Extraction is needed for file ");
                sb5.append(extractedDex);
                Log.i("MultiDex", sb5.toString());
                int i2 = 0;
                z = false;
                while (i2 < 3 && !z) {
                    i2++;
                    a(zipFile, entry, (File) extractedDex, sb2);
                    extractedDex.a = b(extractedDex);
                    z = true;
                    String str = "MultiDex";
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("Extraction ");
                    sb6.append(z ? "succeeded" : "failed");
                    sb6.append(" '");
                    sb6.append(extractedDex.getAbsolutePath());
                    sb6.append("': length ");
                    sb6.append(extractedDex.length());
                    sb6.append(" - crc: ");
                    sb6.append(extractedDex.a);
                    Log.i(str, sb6.toString());
                    if (!z) {
                        extractedDex.delete();
                        if (extractedDex.exists()) {
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("Failed to delete corrupted secondary dex '");
                            sb7.append(extractedDex.getPath());
                            sb7.append("'");
                            Log.w("MultiDex", sb7.toString());
                        }
                    }
                }
                if (!z) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append("Could not create zip file ");
                    sb8.append(extractedDex.getAbsolutePath());
                    sb8.append(" for secondary dex (");
                    sb8.append(i);
                    sb8.append(")");
                    throw new IOException(sb8.toString());
                }
                i++;
                StringBuilder sb9 = new StringBuilder();
                sb9.append("classes");
                sb9.append(i);
                sb9.append(".dex");
                entry = zipFile.getEntry(sb9.toString());
            }
            try {
                zipFile.close();
            } catch (IOException e2) {
                Log.w("MultiDex", "Failed to close resource", e2);
            }
            return arrayList;
        } catch (IOException e3) {
            String str2 = "MultiDex";
            StringBuilder sb10 = new StringBuilder();
            sb10.append("Failed to read crc from ");
            sb10.append(extractedDex.getAbsolutePath());
            Log.w(str2, sb10.toString(), e3);
            z = false;
        } catch (Throwable th) {
            try {
                zipFile.close();
            } catch (IOException e4) {
                Log.w("MultiDex", "Failed to close resource", e4);
            }
            throw th;
        }
    }

    private static void a(Context context, String str, long j, long j2, List<ExtractedDex> list) {
        Editor edit = a(context).edit();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("timestamp");
        edit.putLong(sb.toString(), j);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("crc");
        edit.putLong(sb2.toString(), j2);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("dex.number");
        edit.putInt(sb3.toString(), list.size() + 1);
        int i = 2;
        for (ExtractedDex extractedDex : list) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append("dex.crc.");
            sb4.append(i);
            edit.putLong(sb4.toString(), extractedDex.a);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append("dex.time.");
            sb5.append(i);
            edit.putLong(sb5.toString(), extractedDex.lastModified());
            i++;
        }
        edit.commit();
    }

    private static SharedPreferences a(Context context) {
        return context.getSharedPreferences("multidex.version", VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private void b() {
        File[] listFiles = this.c.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.getName().equals("MultiDex.lock");
            }
        });
        if (listFiles == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to list secondary dex dir content (");
            sb.append(this.c.getPath());
            sb.append(").");
            Log.w("MultiDex", sb.toString());
            return;
        }
        for (File file : listFiles) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Trying to delete old file ");
            sb2.append(file.getPath());
            sb2.append(" of size ");
            sb2.append(file.length());
            Log.i("MultiDex", sb2.toString());
            if (!file.delete()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Failed to delete old file ");
                sb3.append(file.getPath());
                Log.w("MultiDex", sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Deleted old file ");
                sb4.append(file.getPath());
                Log.i("MultiDex", sb4.toString());
            }
        }
    }

    private static void a(ZipFile zipFile, ZipEntry zipEntry, File file, String str) {
        ZipOutputStream zipOutputStream;
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        StringBuilder sb = new StringBuilder();
        sb.append("tmp-");
        sb.append(str);
        File createTempFile = File.createTempFile(sb.toString(), ".zip", file.getParentFile());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Extracting ");
        sb2.append(createTempFile.getPath());
        Log.i("MultiDex", sb2.toString());
        try {
            zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(createTempFile)));
            ZipEntry zipEntry2 = new ZipEntry("classes.dex");
            zipEntry2.setTime(zipEntry.getTime());
            zipOutputStream.putNextEntry(zipEntry2);
            byte[] bArr = new byte[16384];
            for (int read = inputStream.read(bArr); read != -1; read = inputStream.read(bArr)) {
                zipOutputStream.write(bArr, 0, read);
            }
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            if (!createTempFile.setReadOnly()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Failed to mark readonly \"");
                sb3.append(createTempFile.getAbsolutePath());
                sb3.append("\" (tmp of \"");
                sb3.append(file.getAbsolutePath());
                sb3.append("\")");
                throw new IOException(sb3.toString());
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Renaming to ");
            sb4.append(file.getPath());
            Log.i("MultiDex", sb4.toString());
            if (!createTempFile.renameTo(file)) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Failed to rename \"");
                sb5.append(createTempFile.getAbsolutePath());
                sb5.append("\" to \"");
                sb5.append(file.getAbsolutePath());
                sb5.append("\"");
                throw new IOException(sb5.toString());
            }
            a((Closeable) inputStream);
            createTempFile.delete();
        } catch (Throwable th) {
            a((Closeable) inputStream);
            createTempFile.delete();
            throw th;
        }
    }

    private static void a(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e2) {
            Log.w("MultiDex", "Failed to close resource", e2);
        }
    }
}
