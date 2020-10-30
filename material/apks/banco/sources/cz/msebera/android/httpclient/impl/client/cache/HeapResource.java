package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Immutable
public class HeapResource implements Resource {
    private static final long serialVersionUID = -2078599905620463394L;
    private final byte[] a;

    public void dispose() {
    }

    public HeapResource(byte[] bArr) {
        this.a = bArr;
    }

    /* access modifiers changed from: 0000 */
    public byte[] a() {
        return this.a;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.a);
    }

    public long length() {
        return (long) this.a.length;
    }
}
