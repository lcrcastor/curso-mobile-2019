package org.bouncycastle.crypto.tls;

public class LegacyTlsClient extends DefaultTlsClient {
    protected CertificateVerifyer verifyer;

    public LegacyTlsClient(CertificateVerifyer certificateVerifyer) {
        this.verifyer = certificateVerifyer;
    }

    public TlsAuthentication getAuthentication() {
        return new LegacyTlsAuthentication(this.verifyer);
    }
}
