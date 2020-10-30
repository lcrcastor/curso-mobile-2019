package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.InputLimit;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Immutable
public class FileResourceFactory implements ResourceFactory {
    private final File a;
    private final BasicIdGenerator b = new BasicIdGenerator();

    public FileResourceFactory(File file) {
        this.a = file;
    }

    private File a(String str) {
        StringBuilder sb = new StringBuilder();
        this.b.a(sb);
        sb.append('.');
        int min = Math.min(str.length(), 100);
        for (int i = 0; i < min; i++) {
            char charAt = str.charAt(i);
            if (Character.isLetterOrDigit(charAt) || charAt == '.') {
                sb.append(charAt);
            } else {
                sb.append('-');
            }
        }
        return new File(this.a, sb.toString());
    }

    /* JADX INFO: finally extract failed */
    public Resource generate(String str, InputStream inputStream, InputLimit inputLimit) {
        File a2 = a(str);
        FileOutputStream fileOutputStream = new FileOutputStream(a2);
        try {
            byte[] bArr = new byte[2048];
            long j = 0;
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
                long j2 = j + ((long) read);
                if (inputLimit != null && j2 > inputLimit.getValue()) {
                    inputLimit.reached();
                    break;
                }
                j = j2;
            }
            fileOutputStream.close();
            return new FileResource(a2);
        } catch (Throwable th) {
            fileOutputStream.close();
            throw th;
        }
    }

    public Resource copy(String str, Resource resource) {
        File a2 = a(str);
        if (resource instanceof FileResource) {
            IOUtils.a(((FileResource) resource).a(), a2);
        } else {
            IOUtils.b(resource.getInputStream(), new FileOutputStream(a2));
        }
        return new FileResource(a2);
    }
}
