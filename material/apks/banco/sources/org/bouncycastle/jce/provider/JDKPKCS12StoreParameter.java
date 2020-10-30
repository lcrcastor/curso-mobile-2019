package org.bouncycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;

public class JDKPKCS12StoreParameter implements LoadStoreParameter {
    private OutputStream a;
    private ProtectionParameter b;
    private boolean c;

    public OutputStream getOutputStream() {
        return this.a;
    }

    public ProtectionParameter getProtectionParameter() {
        return this.b;
    }

    public boolean isUseDEREncoding() {
        return this.c;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.a = outputStream;
    }

    public void setPassword(char[] cArr) {
        this.b = new PasswordProtection(cArr);
    }

    public void setProtectionParameter(ProtectionParameter protectionParameter) {
        this.b = protectionParameter;
    }

    public void setUseDEREncoding(boolean z) {
        this.c = z;
    }
}
