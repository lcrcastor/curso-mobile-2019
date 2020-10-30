package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public class CertificateStatusRequest {
    protected Object request;
    protected short statusType;

    public CertificateStatusRequest(short s, Object obj) {
        if (!isCorrectType(s, obj)) {
            throw new IllegalArgumentException("'request' is not an instance of the correct type");
        }
        this.statusType = s;
        this.request = obj;
    }

    protected static boolean isCorrectType(short s, Object obj) {
        if (s == 1) {
            return obj instanceof OCSPStatusRequest;
        }
        throw new IllegalArgumentException("'statusType' is an unsupported value");
    }

    public static CertificateStatusRequest parse(InputStream inputStream) {
        short readUint8 = TlsUtils.readUint8(inputStream);
        if (readUint8 == 1) {
            return new CertificateStatusRequest(readUint8, OCSPStatusRequest.parse(inputStream));
        }
        throw new TlsFatalAlert(50);
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeUint8(this.statusType, outputStream);
        if (this.statusType != 1) {
            throw new TlsFatalAlert(80);
        }
        ((OCSPStatusRequest) this.request).encode(outputStream);
    }

    public OCSPStatusRequest getOCSPStatusRequest() {
        if (isCorrectType(1, this.request)) {
            return (OCSPStatusRequest) this.request;
        }
        throw new IllegalStateException("'request' is not an OCSPStatusRequest");
    }

    public Object getRequest() {
        return this.request;
    }

    public short getStatusType() {
        return this.statusType;
    }
}
