package cz.msebera.android.httpclient.entity.mime.content;

import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MIME;
import cz.msebera.android.httpclient.util.Args;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBody extends AbstractContentBody {
    private final File a;
    private final String b;

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    @Deprecated
    public FileBody(File file, String str, String str2, String str3) {
        this(file, ContentType.create(str2, str3), str);
    }

    @Deprecated
    public FileBody(File file, String str, String str2) {
        this(file, null, str, str2);
    }

    @Deprecated
    public FileBody(File file, String str) {
        this(file, ContentType.create(str), (String) null);
    }

    public FileBody(File file) {
        this(file, ContentType.DEFAULT_BINARY, file != null ? file.getName() : null);
    }

    public FileBody(File file, ContentType contentType, String str) {
        super(contentType);
        Args.notNull(file, "File");
        this.a = file;
        this.b = str;
    }

    public FileBody(File file, ContentType contentType) {
        this(file, contentType, file != null ? file.getName() : null);
    }

    public InputStream getInputStream() {
        return new FileInputStream(this.a);
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        FileInputStream fileInputStream = new FileInputStream(this.a);
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

    public long getContentLength() {
        return this.a.length();
    }

    public String getFilename() {
        return this.b;
    }

    public File getFile() {
        return this.a;
    }
}
