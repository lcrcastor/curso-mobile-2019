package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueueFileEventStorage implements EventsStorage {
    private final Context a;
    private final File b;
    private final String c;
    private final File d;
    private QueueFile e = new QueueFile(this.d);
    private File f;

    public QueueFileEventStorage(Context context, File file, String str, String str2) {
        this.a = context;
        this.b = file;
        this.c = str2;
        this.d = new File(this.b, str);
        a();
    }

    private void a() {
        this.f = new File(this.b, this.c);
        if (!this.f.exists()) {
            this.f.mkdirs();
        }
    }

    public void add(byte[] bArr) {
        this.e.add(bArr);
    }

    public int getWorkingFileUsedSizeInBytes() {
        return this.e.usedBytes();
    }

    public void rollOver(String str) {
        this.e.close();
        a(this.d, new File(this.f, str));
        this.e = new QueueFile(this.d);
    }

    private void a(File file, File file2) {
        FileInputStream fileInputStream;
        OutputStream moveOutputStream;
        OutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                moveOutputStream = getMoveOutputStream(file2);
            } catch (Throwable th) {
                th = th;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream");
                CommonUtils.closeOrLog(outputStream, "Failed to close output stream");
                file.delete();
                throw th;
            }
            try {
                CommonUtils.copyStream(fileInputStream, moveOutputStream, new byte[1024]);
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream");
                CommonUtils.closeOrLog(moveOutputStream, "Failed to close output stream");
                file.delete();
            } catch (Throwable th2) {
                Throwable th3 = th2;
                outputStream = moveOutputStream;
                th = th3;
                CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream");
                CommonUtils.closeOrLog(outputStream, "Failed to close output stream");
                file.delete();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            CommonUtils.closeOrLog(fileInputStream, "Failed to close file input stream");
            CommonUtils.closeOrLog(outputStream, "Failed to close output stream");
            file.delete();
            throw th;
        }
    }

    public OutputStream getMoveOutputStream(File file) {
        return new FileOutputStream(file);
    }

    public File getWorkingDirectory() {
        return this.b;
    }

    public File getRollOverDirectory() {
        return this.f;
    }

    public List<File> getBatchOfFilesToSend(int i) {
        ArrayList arrayList = new ArrayList();
        for (File add : this.f.listFiles()) {
            arrayList.add(add);
            if (arrayList.size() >= i) {
                break;
            }
        }
        return arrayList;
    }

    public void deleteFilesInRollOverDirectory(List<File> list) {
        for (File file : list) {
            CommonUtils.logControlled(this.a, String.format("deleting sent analytics file %s", new Object[]{file.getName()}));
            file.delete();
        }
    }

    public List<File> getAllFilesInRollOverDirectory() {
        return Arrays.asList(this.f.listFiles());
    }

    public void deleteWorkingFile() {
        try {
            this.e.close();
        } catch (IOException unused) {
        }
        this.d.delete();
    }

    public boolean isWorkingFileEmpty() {
        return this.e.isEmpty();
    }

    public boolean canWorkingFileStore(int i, int i2) {
        return this.e.hasSpaceFor(i, i2);
    }
}
