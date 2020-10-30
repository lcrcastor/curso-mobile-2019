package cz.msebera.android.httpclient.entity;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.impl.io.EmptyInputStream;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.Asserts;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
public class BasicHttpEntity extends AbstractHttpEntity {
    private InputStream a;
    private long b = -1;

    public boolean isRepeatable() {
        return false;
    }

    public long getContentLength() {
        return this.b;
    }

    public InputStream getContent() {
        Asserts.check(this.a != null, "Content has not been provided");
        return this.a;
    }

    public void setContentLength(long j) {
        this.b = j;
    }

    public void setContent(InputStream inputStream) {
        this.a = inputStream;
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        InputStream content = getContent();
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int read = content.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
        } finally {
            content.close();
        }
    }

    public boolean isStreaming() {
        return (this.a == null || this.a == EmptyInputStream.INSTANCE) ? false : true;
    }
}
