package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.X509Certificate;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.Selector;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509AttributeCertificate;

public class PKIXAttrCertPathValidatorSpi extends CertPathValidatorSpi {
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) {
        if (!(certPathParameters instanceof ExtendedPKIXParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Parameters must be a ");
            sb.append(ExtendedPKIXParameters.class.getName());
            sb.append(" instance.");
            throw new InvalidAlgorithmParameterException(sb.toString());
        }
        ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters) certPathParameters;
        Selector targetConstraints = extendedPKIXParameters.getTargetConstraints();
        if (!(targetConstraints instanceof X509AttributeCertStoreSelector)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("TargetConstraints must be an instance of ");
            sb2.append(X509AttributeCertStoreSelector.class.getName());
            sb2.append(" for ");
            sb2.append(getClass().getName());
            sb2.append(" class.");
            throw new InvalidAlgorithmParameterException(sb2.toString());
        }
        X509AttributeCertificate attributeCert = ((X509AttributeCertStoreSelector) targetConstraints).getAttributeCert();
        CertPath c = RFC3281CertPathUtilities.c(attributeCert, extendedPKIXParameters);
        CertPathValidatorResult a = RFC3281CertPathUtilities.a(certPath, extendedPKIXParameters);
        X509Certificate x509Certificate = (X509Certificate) certPath.getCertificates().get(0);
        RFC3281CertPathUtilities.b(x509Certificate, extendedPKIXParameters);
        RFC3281CertPathUtilities.a(x509Certificate, extendedPKIXParameters);
        RFC3281CertPathUtilities.b(attributeCert, extendedPKIXParameters);
        RFC3281CertPathUtilities.a(attributeCert, certPath, c, extendedPKIXParameters);
        RFC3281CertPathUtilities.a(attributeCert, extendedPKIXParameters);
        try {
            RFC3281CertPathUtilities.a(attributeCert, extendedPKIXParameters, x509Certificate, CertPathValidatorUtilities.getValidCertDateFromValidityModel(extendedPKIXParameters, null, -1), certPath.getCertificates());
            return a;
        } catch (AnnotatedException e) {
            throw new ExtCertPathValidatorException("Could not get validity date from attribute certificate.", e);
        }
    }
}
