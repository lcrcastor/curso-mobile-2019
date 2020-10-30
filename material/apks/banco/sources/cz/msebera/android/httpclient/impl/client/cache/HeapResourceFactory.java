package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.InputLimit;
import cz.msebera.android.httpclient.client.cache.Resource;
import cz.msebera.android.httpclient.client.cache.ResourceFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Immutable
public class HeapResourceFactory implements ResourceFactory {
    public Resource generate(String str, InputStream inputStream, InputLimit inputLimit) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[2048];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
            long j2 = j + ((long) read);
            if (inputLimit != null && j2 > inputLimit.getValue()) {
                inputLimit.reached();
                break;
            }
            j = j2;
        }
        return a(byteArrayOutputStream.toByteArray());
    }

    public Resource copy(String str, Resource resource) {
        byte[] bArr;
        if (resource instanceof HeapResource) {
            bArr = ((HeapResource) resource).a();
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.b(resource.getInputStream(), byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
        }
        return a(bArr);
    }

    /* access modifiers changed from: 0000 */
    public Resource a(byte[] bArr) {
        return new HeapResource(bArr);
    }
}
