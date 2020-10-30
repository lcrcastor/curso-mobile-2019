package android.support.v4.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile extends DocumentFile {
    private File a;

    public boolean isVirtual() {
        return false;
    }

    RawDocumentFile(DocumentFile documentFile, File file) {
        super(documentFile);
        this.a = file;
    }

    public DocumentFile createFile(String str, String str2) {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(".");
            sb.append(extensionFromMimeType);
            str2 = sb.toString();
        }
        File file = new File(this.a, str2);
        try {
            file.createNewFile();
            return new RawDocumentFile(this, file);
        } catch (IOException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to createFile: ");
            sb2.append(e);
            Log.w("DocumentFile", sb2.toString());
            return null;
        }
    }

    public DocumentFile createDirectory(String str) {
        File file = new File(this.a, str);
        if (file.isDirectory() || file.mkdir()) {
            return new RawDocumentFile(this, file);
        }
        return null;
    }

    public Uri getUri() {
        return Uri.fromFile(this.a);
    }

    public String getName() {
        return this.a.getName();
    }

    public String getType() {
        if (this.a.isDirectory()) {
            return null;
        }
        return a(this.a.getName());
    }

    public boolean isDirectory() {
        return this.a.isDirectory();
    }

    public boolean isFile() {
        return this.a.isFile();
    }

    public long lastModified() {
        return this.a.lastModified();
    }

    public long length() {
        return this.a.length();
    }

    public boolean canRead() {
        return this.a.canRead();
    }

    public boolean canWrite() {
        return this.a.canWrite();
    }

    public boolean delete() {
        a(this.a);
        return this.a.delete();
    }

    public boolean exists() {
        return this.a.exists();
    }

    public DocumentFile[] listFiles() {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = this.a.listFiles();
        if (listFiles != null) {
            for (File rawDocumentFile : listFiles) {
                arrayList.add(new RawDocumentFile(this, rawDocumentFile));
            }
        }
        return (DocumentFile[]) arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    public boolean renameTo(String str) {
        File file = new File(this.a.getParentFile(), str);
        if (!this.a.renameTo(file)) {
            return false;
        }
        this.a = file;
        return true;
    }

    private static String a(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1).toLowerCase());
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        }
        return "application/octet-stream";
    }

    private static boolean a(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return true;
        }
        boolean z = true;
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                z &= a(file2);
            }
            if (!file2.delete()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to delete ");
                sb.append(file2);
                Log.w("DocumentFile", sb.toString());
                z = false;
            }
        }
        return z;
    }
}
