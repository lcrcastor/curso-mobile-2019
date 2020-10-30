package cz.msebera.android.httpclient.entity;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

@NotThreadSafe
public class SerializableEntity extends AbstractHttpEntity {
    private byte[] a;
    private Serializable b;

    public boolean isRepeatable() {
        return true;
    }

    public SerializableEntity(Serializable serializable, boolean z) {
        Args.notNull(serializable, "Source object");
        if (z) {
            a(serializable);
        } else {
            this.b = serializable;
        }
    }

    public SerializableEntity(Serializable serializable) {
        Args.notNull(serializable, "Source object");
        this.b = serializable;
    }

    private void a(Serializable serializable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.flush();
        this.a = byteArrayOutputStream.toByteArray();
    }

    public InputStream getContent() {
        if (this.a == null) {
            a(this.b);
        }
        return new ByteArrayInputStream(this.a);
    }

    public long getContentLength() {
        if (this.a == null) {
            return -1;
        }
        return (long) this.a.length;
    }

    public boolean isStreaming() {
        return this.a == null;
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        if (this.a == null) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(this.b);
            objectOutputStream.flush();
            return;
        }
        outputStream.write(this.a);
        outputStream.flush();
    }
}
