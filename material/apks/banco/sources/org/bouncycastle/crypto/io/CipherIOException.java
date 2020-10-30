package org.bouncycastle.crypto.io;

import java.io.IOException;

public class CipherIOException extends IOException {
    private static final long serialVersionUID = 1;
    private final Throwable a;

    public CipherIOException(String str, Throwable th) {
        super(str);
        this.a = th;
    }

    public Throwable getCause() {
        return this.a;
    }
}
