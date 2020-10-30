package cz.msebera.android.httpclient.entity;

import android.support.v4.media.session.PlaybackStateCompat;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.io.InputStream;
import java.io.OutputStream;

@NotThreadSafe
public class InputStreamEntity extends AbstractHttpEntity {
    private final InputStream a;
    private final long b;

    public boolean isRepeatable() {
        return false;
    }

    public boolean isStreaming() {
        return true;
    }

    public InputStreamEntity(InputStream inputStream) {
        this(inputStream, -1);
    }

    public InputStreamEntity(InputStream inputStream, long j) {
        this(inputStream, j, null);
    }

    public InputStreamEntity(InputStream inputStream, ContentType contentType) {
        this(inputStream, -1, contentType);
    }

    public InputStreamEntity(InputStream inputStream, long j, ContentType contentType) {
        this.a = (InputStream) Args.notNull(inputStream, "Source input stream");
        this.b = j;
        if (contentType != null) {
            setContentType(contentType.toString());
        }
    }

    public long getContentLength() {
        return this.b;
    }

    public InputStream getContent() {
        return this.a;
    }

    public void writeTo(OutputStream outputStream) {
        Args.notNull(outputStream, "Output stream");
        InputStream inputStream = this.a;
        try {
            byte[] bArr = new byte[4096];
            if (this.b >= 0) {
                long j = this.b;
                while (true) {
                    if (j <= 0) {
                        break;
                    }
                    int read = inputStream.read(bArr, 0, (int) Math.min(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM, j));
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, read);
                    j -= (long) read;
                }
            } else {
                while (true) {
                    int read2 = inputStream.read(bArr);
                    if (read2 == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, read2);
                }
            }
        } finally {
            inputStream.close();
        }
    }
}
