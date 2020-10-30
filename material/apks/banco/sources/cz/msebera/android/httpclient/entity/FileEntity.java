package cz.msebera.android.httpclient.entity;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
public class FileEntity extends AbstractHttpEntity implements Cloneable {
    protected final File file;

    public boolean isRepeatable() {
        return true;
    }

    public boolean isStreaming() {
        return false;
    }

    @Deprecated
    public FileEntity(File file2, String str) {
        this.file = (File) Args.notNull(file2, "File");
        setContentType(str);
    }

    public FileEntity(File file2, ContentType contentType) {
        this.file = (File) Args.notNull(file2, "File");
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public FileEntity(File file2) {
        this.file = (File) Args.notNull(file2, "File");
    }

    public long getContentLength() {
        return this.file.length();
    }

    public InputStream getContent() {
        return new FileInputStream(this.file);
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        FileInputStream fileInputStream = new FileInputStream(this.file);
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    outputStream.flush();
                    return;
                }
            }
        } finally {
            fileInputStream.close();
        }
    }

    public Object clone() {
        return super.clone();
    }
}
