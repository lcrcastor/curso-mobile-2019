package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.entity.AbstractHttpEntity;
import cz.msebera.android.httpclient.util.Args;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;

@NotThreadSafe
class CombinedEntity extends AbstractHttpEntity {
    private final Resource a;
    private final InputStream b;

    class ResourceStream extends FilterInputStream {
        protected ResourceStream(InputStream inputStream) {
            super(inputStream);
        }

        public void close() {
            try {
                super.close();
            } finally {
                CombinedEntity.this.a();
            }
        }
    }

    public long getContentLength() {
        return -1;
    }

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return true;
    }

    CombinedEntity(Resource resource, InputStream inputStream) {
        this.a = resource;
        this.b = new SequenceInputStream(new ResourceStream(resource.getInputStream()), inputStream);
    }

    public InputStream getContent() {
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

    /* access modifiers changed from: private */
    public void a() {
        this.a.dispose();
    }
}
