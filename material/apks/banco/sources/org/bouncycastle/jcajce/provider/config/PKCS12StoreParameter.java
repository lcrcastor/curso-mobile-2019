package org.bouncycastle.jcajce.provider.config;

import java.io.OutputStream;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;

public class PKCS12StoreParameter implements LoadStoreParameter {
    private final OutputStream a;
    private final ProtectionParameter b;
    private final boolean c;

    public PKCS12StoreParameter(OutputStream outputStream, ProtectionParameter protectionParameter) {
        this(outputStream, protectionParameter, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, ProtectionParameter protectionParameter, boolean z) {
        this.a = outputStream;
        this.b = protectionParameter;
        this.c = z;
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] cArr) {
        this(outputStream, cArr, false);
    }

    public PKCS12StoreParameter(OutputStream outputStream, char[] cArr, boolean z) {
        this(outputStream, (ProtectionParameter) new PasswordProtection(cArr), z);
    }

    public OutputStream getOutputStream() {
        return this.a;
    }

    public ProtectionParameter getProtectionParameter() {
        return this.b;
    }

    public boolean isForDEREncoding() {
        return this.c;
    }
}
