package org.bouncycastle.crypto.tls;

import java.io.IOException;

public class TlsFatalAlert extends IOException {
    private static final long serialVersionUID = 3584313123679111168L;
    private short a;

    public TlsFatalAlert(short s) {
        this.a = s;
    }

    public short getAlertDescription() {
        return this.a;
    }
}
