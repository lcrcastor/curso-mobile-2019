package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.cache.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@ThreadSafe
public class FileResource implements Resource {
    private static final long serialVersionUID = 4132244415919043397L;
    private final File a;
    private volatile boolean b = false;

    public FileResource(File file) {
        this.a = file;
    }

    /* access modifiers changed from: 0000 */
    public synchronized File a() {
        return this.a;
    }

    public synchronized InputStream getInputStream() {
        return new FileInputStream(this.a);
    }

    public synchronized long length() {
        return this.a.length();
    }

    public synchronized void dispose() {
        if (!this.b) {
            this.b = true;
            this.a.delete();
        }
    }
}
