package cz.msebera.android.httpclient.entity;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
public class ByteArrayEntity extends AbstractHttpEntity implements Cloneable {
    private final byte[] a;
    private final int b;
    private final int c;
    @Deprecated
    protected final byte[] content;

    public boolean isRepeatable() {
        return true;
    }

    public boolean isStreaming() {
        return false;
    }

    public ByteArrayEntity(byte[] bArr, ContentType contentType) {
        Args.notNull(bArr, "Source byte array");
        this.content = bArr;
        this.a = bArr;
        this.b = 0;
        this.c = this.a.length;
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public ByteArrayEntity(byte[] bArr, int i, int i2, ContentType contentType) {
        Args.notNull(bArr, "Source byte array");
        if (i >= 0 && i <= bArr.length && i2 >= 0) {
            int i3 = i + i2;
            if (i3 >= 0 && i3 <= bArr.length) {
                this.content = bArr;
                this.a = bArr;
                this.b = i;
                this.c = i2;
                if (contentType != null) {
                    setContentType(contentType.toString());
                    return;
                }
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("off: ");
        sb.append(i);
        sb.append(" len: ");
        sb.append(i2);
        sb.append(" b.length: ");
        sb.append(bArr.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public ByteArrayEntity(byte[] bArr) {
        this(bArr, null);
    }

    public ByteArrayEntity(byte[] bArr, int i, int i2) {
        this(bArr, i, i2, null);
    }

    public long getContentLength() {
        return (long) this.c;
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.a, this.b, this.c);
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        outputStream.write(this.a, this.b, this.c);
        outputStream.flush();
    }

    public Object clone() {
        return super.clone();
    }
}
