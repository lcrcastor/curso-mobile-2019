package org.bouncycastle.crypto.tls;

public class LegacyTlsAuthentication extends ServerOnlyTlsAuthentication {
    protected CertificateVerifyer verifyer;

    public LegacyTlsAuthentication(CertificateVerifyer certificateVerifyer) {
        this.verifyer = certificateVerifyer;
    }

    public void notifyServerCertificate(Certificate certificate) {
        if (!this.verifyer.isValid(certificate.getCertificateList())) {
            throw new TlsFatalAlert(90);
        }
    }
}
