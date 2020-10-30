package org.bouncycastle.crypto.tls;

import java.io.IOException;

public class DTLSTransport implements DatagramTransport {
    private final DTLSRecordLayer a;

    DTLSTransport(DTLSRecordLayer dTLSRecordLayer) {
        this.a = dTLSRecordLayer;
    }

    public void close() {
        this.a.close();
    }

    public int getReceiveLimit() {
        return this.a.getReceiveLimit();
    }

    public int getSendLimit() {
        return this.a.getSendLimit();
    }

    public int receive(byte[] bArr, int i, int i2, int i3) {
        try {
            return this.a.receive(bArr, i, i2, i3);
        } catch (TlsFatalAlert e) {
            this.a.a(e.getAlertDescription());
            throw e;
        } catch (IOException e2) {
            this.a.a(80);
            throw e2;
        } catch (RuntimeException unused) {
            this.a.a(80);
            throw new TlsFatalAlert(80);
        }
    }

    public void send(byte[] bArr, int i, int i2) {
        try {
            this.a.send(bArr, i, i2);
        } catch (TlsFatalAlert e) {
            this.a.a(e.getAlertDescription());
            throw e;
        } catch (IOException e2) {
            this.a.a(80);
            throw e2;
        } catch (RuntimeException unused) {
            this.a.a(80);
            throw new TlsFatalAlert(80);
        }
    }
}
