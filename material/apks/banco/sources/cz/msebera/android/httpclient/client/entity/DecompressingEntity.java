package cz.msebera.android.httpclient.client.entity;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.HttpEntityWrapper;
import cz.msebera.android.httpclient.util.Args;
import java.io.InputStream;
import java.io.OutputStream;

public class DecompressingEntity extends HttpEntityWrapper {
    private final InputStreamFactory a;
    private InputStream b;

    public Header getContentEncoding() {
        return null;
    }

    public long getContentLength() {
        return -1;
    }

    public DecompressingEntity(HttpEntity httpEntity, InputStreamFactory inputStreamFactory) {
        super(httpEntity);
        this.a = inputStreamFactory;
    }

    private InputStream a() {
        return new LazyDecompressingInputStream(this.wrappedEntity.getContent(), this.a);
    }

    public InputStream getContent() {
        if (!this.wrappedEntity.isStreaming()) {
            return a();
        }
        if (this.b == null) {
            this.b = a();
        }
        return this.b;
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        InputStream content = getContent();
        try {
            byte[] bArr = new byte[2048];
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
}
