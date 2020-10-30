package cz.msebera.android.httpclient.entity;

import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class EntityTemplate extends AbstractHttpEntity {
    private final ContentProducer a;

    public long getContentLength() {
        return -1;
    }

    public boolean isRepeatable() {
        return true;
    }

    public boolean isStreaming() {
        return false;
    }

    public EntityTemplate(ContentProducer contentProducer) {
        this.a = (ContentProducer) Args.notNull(contentProducer, "Content producer");
    }

    public InputStream getContent() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        writeTo(byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        this.a.writeTo(outputStream);
    }
}
