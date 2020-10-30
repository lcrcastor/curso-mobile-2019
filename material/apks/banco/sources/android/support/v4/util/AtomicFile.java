package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File a;
    private final File b;

    public AtomicFile(@NonNull File file) {
        this.a = file;
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        sb.append(".bak");
        this.b = new File(sb.toString());
    }

    @NonNull
    public File getBaseFile() {
        return this.a;
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
    }

    @NonNull
    public FileOutputStream startWrite() {
        if (this.a.exists()) {
            if (this.b.exists()) {
                this.a.delete();
            } else if (!this.a.renameTo(this.b)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Couldn't rename file ");
                sb.append(this.a);
                sb.append(" to backup file ");
                sb.append(this.b);
                Log.w("AtomicFile", sb.toString());
            }
        }
        try {
            return new FileOutputStream(this.a);
        } catch (FileNotFoundException unused) {
            if (!this.a.getParentFile().mkdirs()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Couldn't create directory ");
                sb2.append(this.a);
                throw new IOException(sb2.toString());
            }
            try {
                return new FileOutputStream(this.a);
            } catch (FileNotFoundException unused2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Couldn't create ");
                sb3.append(this.a);
                throw new IOException(sb3.toString());
            }
        }
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.b.delete();
            } catch (IOException e) {
                Log.w("AtomicFile", "finishWrite: Got exception:", e);
            }
        }
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.a.delete();
                this.b.renameTo(this.a);
            } catch (IOException e) {
                Log.w("AtomicFile", "failWrite: Got exception:", e);
            }
        }
    }

    @NonNull
    public FileInputStream openRead() {
        if (this.b.exists()) {
            this.a.delete();
            this.b.renameTo(this.a);
        }
        return new FileInputStream(this.a);
    }

    @NonNull
    public byte[] readFully() {
        FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    return bArr;
                }
                i += read;
                int available = openRead.available();
                if (available > bArr.length - i) {
                    byte[] bArr2 = new byte[(available + i)];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            }
        } finally {
            openRead.close();
        }
    }

    private static boolean a(@NonNull FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.getFD().sync();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }
}
