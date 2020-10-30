package cz.msebera.android.httpclient.client.entity;

import cz.msebera.android.httpclient.HttpEntity;
import java.io.InputStream;

public class DeflateDecompressingEntity extends DecompressingEntity {
    public DeflateDecompressingEntity(HttpEntity httpEntity) {
        super(httpEntity, new InputStreamFactory() {
            public InputStream create(InputStream inputStream) {
                return new DeflateInputStream(inputStream);
            }
        });
    }
}
