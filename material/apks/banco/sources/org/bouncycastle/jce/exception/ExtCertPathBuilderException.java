package org.bouncycastle.jce.exception;

import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;

public class ExtCertPathBuilderException extends CertPathBuilderException implements ExtException {
    private Throwable a;

    public ExtCertPathBuilderException(String str, Throwable th) {
        super(str);
        this.a = th;
    }

    public ExtCertPathBuilderException(String str, Throwable th, CertPath certPath, int i) {
        super(str, th);
        this.a = th;
    }

    public Throwable getCause() {
        return this.a;
    }
}
