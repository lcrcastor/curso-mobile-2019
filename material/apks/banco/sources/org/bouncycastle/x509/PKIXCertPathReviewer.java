package org.bouncycastle.x509;

import cz.msebera.android.httpclient.HttpHost;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityInformationAccess;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralSubtree;
import org.bouncycastle.asn1.x509.NameConstraints;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.qualified.MonetaryValue;
import org.bouncycastle.asn1.x509.qualified.QCStatement;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.i18n.filter.TrustedInput;
import org.bouncycastle.i18n.filter.UntrustedInput;
import org.bouncycastle.jce.provider.AnnotatedException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.provider.CertPathValidatorUtilities;
import org.bouncycastle.jce.provider.PKIXNameConstraintValidator;
import org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import org.bouncycastle.util.Integers;

public class PKIXCertPathReviewer extends CertPathValidatorUtilities {
    private static final String a = X509Extensions.QCStatements.getId();
    private static final String b = X509Extensions.CRLDistributionPoints.getId();
    private static final String c = X509Extensions.AuthorityInfoAccess.getId();
    protected CertPath certPath;
    protected List certs;
    private boolean d;
    protected List[] errors;
    protected int n;
    protected List[] notifications;
    protected PKIXParameters pkixParams;
    protected PolicyNode policyTree;
    protected PublicKey subjectPublicKey;
    protected TrustAnchor trustAnchor;
    protected Date validDate;

    public PKIXCertPathReviewer() {
    }

    public PKIXCertPathReviewer(CertPath certPath2, PKIXParameters pKIXParameters) {
        init(certPath2, pKIXParameters);
    }

    private X509CRL a(String str) {
        try {
            URL url = new URL(str);
            if (!url.getProtocol().equals(HttpHost.DEFAULT_SCHEME_NAME)) {
                if (!url.getProtocol().equals("https")) {
                    return null;
                }
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                return (X509CRL) CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME).generateCRL(httpURLConnection.getInputStream());
            }
            throw new Exception(httpURLConnection.getResponseMessage());
        } catch (Exception e) {
            throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.loadCrlDistPointError", new Object[]{new UntrustedInput(str), e.getMessage(), e, e.getClass().getName()}));
        }
    }

    private void a() {
        GeneralName instance;
        PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
        for (int size = this.certs.size() - 1; size > 0; size--) {
            int i = this.n;
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            if (!isSelfIssued(x509Certificate)) {
                X500Principal subjectPrincipal = getSubjectPrincipal(x509Certificate);
                try {
                    ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream((InputStream) new ByteArrayInputStream(subjectPrincipal.getEncoded())).readObject();
                    pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                    pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                    ASN1Sequence aSN1Sequence2 = (ASN1Sequence) getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME);
                    if (aSN1Sequence2 != null) {
                        for (int i2 = 0; i2 < aSN1Sequence2.size(); i2++) {
                            instance = GeneralName.getInstance(aSN1Sequence2.getObjectAt(i2));
                            pKIXNameConstraintValidator.checkPermitted(instance);
                            pKIXNameConstraintValidator.checkExcluded(instance);
                        }
                    }
                } catch (AnnotatedException e) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.ncExtError"), e, this.certPath, size);
                } catch (IOException e2) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.ncSubjectNameError", new Object[]{new UntrustedInput(subjectPrincipal)}), e2, this.certPath, size);
                } catch (PKIXNameConstraintValidatorException e3) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.notPermittedDN", new Object[]{new UntrustedInput(subjectPrincipal.getName())}), e3, this.certPath, size);
                } catch (PKIXNameConstraintValidatorException e4) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.excludedDN", new Object[]{new UntrustedInput(subjectPrincipal.getName())}), e4, this.certPath, size);
                } catch (AnnotatedException e5) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.subjAltNameExtError"), e5, this.certPath, size);
                } catch (PKIXNameConstraintValidatorException e6) {
                    throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.notPermittedEmail", new Object[]{new UntrustedInput(instance)}), e6, this.certPath, size);
                } catch (CertPathReviewerException e7) {
                    addError(e7.getErrorMessage(), e7.getIndex());
                    return;
                }
            }
            ASN1Sequence aSN1Sequence3 = (ASN1Sequence) getExtensionValue(x509Certificate, NAME_CONSTRAINTS);
            if (aSN1Sequence3 != null) {
                NameConstraints instance2 = NameConstraints.getInstance(aSN1Sequence3);
                GeneralSubtree[] permittedSubtrees = instance2.getPermittedSubtrees();
                if (permittedSubtrees != null) {
                    pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                }
                GeneralSubtree[] excludedSubtrees = instance2.getExcludedSubtrees();
                if (excludedSubtrees != null) {
                    for (int i3 = 0; i3 != excludedSubtrees.length; i3++) {
                        pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i3]);
                    }
                }
            }
        }
    }

    private boolean a(X509Certificate x509Certificate, int i) {
        ErrorBundle errorBundle;
        ErrorBundle errorBundle2;
        int i2 = i;
        char c2 = 0;
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) getExtensionValue(x509Certificate, a);
            int i3 = 0;
            boolean z = false;
            while (i3 < aSN1Sequence.size()) {
                QCStatement instance = QCStatement.getInstance(aSN1Sequence.getObjectAt(i3));
                if (QCStatement.id_etsi_qcs_QcCompliance.equals(instance.getStatementId())) {
                    errorBundle2 = new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcEuCompliance");
                } else {
                    if (!QCStatement.id_qcs_pkixQCSyntax_v1.equals(instance.getStatementId())) {
                        if (QCStatement.id_etsi_qcs_QcSSCD.equals(instance.getStatementId())) {
                            errorBundle2 = new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcSSCD");
                        } else if (QCStatement.id_etsi_qcs_LimiteValue.equals(instance.getStatementId())) {
                            MonetaryValue instance2 = MonetaryValue.getInstance(instance.getStatementInfo());
                            instance2.getCurrency();
                            double doubleValue = instance2.getAmount().doubleValue() * Math.pow(10.0d, instance2.getExponent().doubleValue());
                            if (instance2.getCurrency().isAlphabetic()) {
                                Object[] objArr = new Object[3];
                                objArr[c2] = instance2.getCurrency().getAlphabetic();
                                objArr[1] = new TrustedInput(new Double(doubleValue));
                                objArr[2] = instance2;
                                errorBundle = new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueAlpha", objArr);
                            } else {
                                errorBundle = new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcLimitValueNum", new Object[]{Integers.valueOf(instance2.getCurrency().getNumeric()), new TrustedInput(new Double(doubleValue)), instance2});
                            }
                            addNotification(errorBundle, i2);
                        } else {
                            addNotification(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcUnknownStatement", new Object[]{instance.getStatementId(), new UntrustedInput(instance)}), i2);
                            z = true;
                        }
                    }
                    i3++;
                    c2 = 0;
                }
                addNotification(errorBundle2, i2);
                i3++;
                c2 = 0;
            }
            return !z;
        } catch (AnnotatedException unused) {
            addError(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.QcStatementExtError"), i2);
            return false;
        }
    }

    private void b() {
        BasicConstraints basicConstraints;
        int i = this.n;
        int i2 = 0;
        for (int size = this.certs.size() - 1; size > 0; size--) {
            int i3 = this.n;
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            if (!isSelfIssued(x509Certificate)) {
                if (i <= 0) {
                    addError(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.pathLenghtExtended"));
                }
                i--;
                i2++;
            }
            try {
                basicConstraints = BasicConstraints.getInstance(getExtensionValue(x509Certificate, BASIC_CONSTRAINTS));
            } catch (AnnotatedException unused) {
                addError(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.processLengthConstError"), size);
                basicConstraints = null;
            }
            if (basicConstraints != null) {
                BigInteger pathLenConstraint = basicConstraints.getPathLenConstraint();
                if (pathLenConstraint != null) {
                    int intValue = pathLenConstraint.intValue();
                    if (intValue < i) {
                        i = intValue;
                    }
                }
            }
        }
        addNotification(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.totalPathLength", new Object[]{Integers.valueOf(i2)}));
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x00c4 */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02da A[Catch:{ AnnotatedException -> 0x02df }] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x02fe A[LOOP:1: B:106:0x02f8->B:108:0x02fe, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0330 A[LOOP:2: B:110:0x032a->B:112:0x0330, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0378  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0381  */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x03b4 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x010a  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0155  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x017c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x02b5 A[SYNTHETIC, Splitter:B:90:0x02b5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r22 = this;
            r10 = r22
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.certPathValidDate"
            r11 = 2
            java.lang.Object[] r4 = new java.lang.Object[r11]
            org.bouncycastle.i18n.filter.TrustedInput r5 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r6 = r10.validDate
            r5.<init>(r6)
            r12 = 0
            r4[r12] = r5
            org.bouncycastle.i18n.filter.TrustedInput r5 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r6 = new java.util.Date
            r6.<init>()
            r5.<init>(r6)
            r13 = 1
            r4[r13] = r5
            r1.<init>(r2, r3, r4)
            r10.addNotification(r1)
            java.util.List r1 = r10.certs     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.util.List r2 = r10.certs     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            int r2 = r2.size()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            int r2 = r2 - r13
            java.lang.Object r1 = r1.get(r2)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.security.cert.X509Certificate r1 = (java.security.cert.X509Certificate) r1     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.security.cert.PKIXParameters r2 = r10.pkixParams     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.util.Set r2 = r2.getTrustAnchors()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.util.Collection r2 = r10.getTrustAnchors(r1, r2)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            int r3 = r2.size()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            if (r3 <= r13) goto L_0x006b
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.conflictingTrustAnchors"
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            int r2 = r2.size()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.lang.Integer r2 = org.bouncycastle.util.Integers.valueOf(r2)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r6[r12] = r2     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            org.bouncycastle.i18n.filter.UntrustedInput r2 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            javax.security.auth.x500.X500Principal r1 = r1.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r2.<init>(r1)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r6[r13] = r2     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r3.<init>(r4, r5, r6)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r10.addError(r3)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            goto L_0x009a
        L_0x006b:
            boolean r3 = r2.isEmpty()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            if (r3 == 0) goto L_0x009d
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.noTrustAnchorFound"
            java.lang.Object[] r5 = new java.lang.Object[r11]     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            org.bouncycastle.i18n.filter.UntrustedInput r6 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            javax.security.auth.x500.X500Principal r1 = r1.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r6.<init>(r1)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r5[r12] = r6     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.security.cert.PKIXParameters r1 = r10.pkixParams     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.util.Set r1 = r1.getTrustAnchors()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            int r1 = r1.size()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.lang.Integer r1 = org.bouncycastle.util.Integers.valueOf(r1)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r5[r13] = r1     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r2.<init>(r3, r4, r5)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            r10.addError(r2)     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
        L_0x009a:
            r2 = 0
            goto L_0x0105
        L_0x009d:
            java.util.Iterator r2 = r2.iterator()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.lang.Object r2 = r2.next()     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.security.cert.TrustAnchor r2 = (java.security.cert.TrustAnchor) r2     // Catch:{ CertPathReviewerException -> 0x00fb, Throwable -> 0x00d7 }
            java.security.cert.X509Certificate r3 = r2.getTrustedCert()     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            if (r3 == 0) goto L_0x00b6
            java.security.cert.X509Certificate r3 = r2.getTrustedCert()     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            java.security.PublicKey r3 = r3.getPublicKey()     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            goto L_0x00ba
        L_0x00b6:
            java.security.PublicKey r3 = r2.getCAPublicKey()     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
        L_0x00ba:
            java.security.cert.PKIXParameters r4 = r10.pkixParams     // Catch:{ SignatureException -> 0x00c4, Exception -> 0x0105 }
            java.lang.String r4 = r4.getSigProvider()     // Catch:{ SignatureException -> 0x00c4, Exception -> 0x0105 }
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.verifyX509Certificate(r1, r3, r4)     // Catch:{ SignatureException -> 0x00c4, Exception -> 0x0105 }
            goto L_0x0105
        L_0x00c4:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.trustButInvalidCert"
            r1.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            r10.addError(r1)     // Catch:{ CertPathReviewerException -> 0x00d4, Throwable -> 0x00d1 }
            goto L_0x0105
        L_0x00d1:
            r0 = move-exception
            r1 = r0
            goto L_0x00da
        L_0x00d4:
            r0 = move-exception
            r1 = r0
            goto L_0x00fe
        L_0x00d7:
            r0 = move-exception
            r1 = r0
            r2 = 0
        L_0x00da:
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.unknown"
            java.lang.Object[] r6 = new java.lang.Object[r11]
            org.bouncycastle.i18n.filter.UntrustedInput r7 = new org.bouncycastle.i18n.filter.UntrustedInput
            java.lang.String r8 = r1.getMessage()
            r7.<init>(r8)
            r6[r12] = r7
            org.bouncycastle.i18n.filter.UntrustedInput r7 = new org.bouncycastle.i18n.filter.UntrustedInput
            r7.<init>(r1)
            r6[r13] = r7
            r3.<init>(r4, r5, r6)
            r10.addError(r3)
            goto L_0x0105
        L_0x00fb:
            r0 = move-exception
            r1 = r0
            r2 = 0
        L_0x00fe:
            org.bouncycastle.i18n.ErrorBundle r1 = r1.getErrorMessage()
            r10.addError(r1)
        L_0x0105:
            r15 = r2
            r16 = 5
            if (r15 == 0) goto L_0x0152
            java.security.cert.X509Certificate r1 = r15.getTrustedCert()
            if (r1 == 0) goto L_0x0115
            javax.security.auth.x500.X500Principal r2 = getSubjectPrincipal(r1)     // Catch:{ IllegalArgumentException -> 0x011f }
            goto L_0x0139
        L_0x0115:
            javax.security.auth.x500.X500Principal r2 = new javax.security.auth.x500.X500Principal     // Catch:{ IllegalArgumentException -> 0x011f }
            java.lang.String r3 = r15.getCAName()     // Catch:{ IllegalArgumentException -> 0x011f }
            r2.<init>(r3)     // Catch:{ IllegalArgumentException -> 0x011f }
            goto L_0x0139
        L_0x011f:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.trustDNInvalid"
            java.lang.Object[] r5 = new java.lang.Object[r13]
            org.bouncycastle.i18n.filter.UntrustedInput r6 = new org.bouncycastle.i18n.filter.UntrustedInput
            java.lang.String r7 = r15.getCAName()
            r6.<init>(r7)
            r5[r12] = r6
            r2.<init>(r3, r4, r5)
            r10.addError(r2)
            r2 = 0
        L_0x0139:
            if (r1 == 0) goto L_0x0153
            boolean[] r1 = r1.getKeyUsage()
            if (r1 == 0) goto L_0x0153
            boolean r1 = r1[r16]
            if (r1 != 0) goto L_0x0153
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.trustKeyUsage"
            r1.<init>(r3, r4)
            r10.addNotification(r1)
            goto L_0x0153
        L_0x0152:
            r2 = 0
        L_0x0153:
            if (r15 == 0) goto L_0x017c
            java.security.cert.X509Certificate r1 = r15.getTrustedCert()
            if (r1 == 0) goto L_0x0160
            java.security.PublicKey r3 = r1.getPublicKey()
            goto L_0x0164
        L_0x0160:
            java.security.PublicKey r3 = r15.getCAPublicKey()
        L_0x0164:
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r4 = getAlgorithmIdentifier(r3)     // Catch:{ CertPathValidatorException -> 0x016f }
            r4.getObjectId()     // Catch:{ CertPathValidatorException -> 0x016f }
            r4.getParameters()     // Catch:{ CertPathValidatorException -> 0x016f }
            goto L_0x017e
        L_0x016f:
            org.bouncycastle.i18n.ErrorBundle r4 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r5 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r6 = "CertPathReviewer.trustPubKeyError"
            r4.<init>(r5, r6)
            r10.addError(r4)
            goto L_0x017e
        L_0x017c:
            r1 = 0
            r3 = 0
        L_0x017e:
            java.util.List r4 = r10.certs
            int r4 = r4.size()
            int r4 = r4 - r13
            r5 = r1
            r7 = r2
            r8 = r3
            r9 = r4
        L_0x0189:
            if (r9 < 0) goto L_0x0441
            int r1 = r10.n
            int r6 = r1 - r9
            java.util.List r1 = r10.certs
            java.lang.Object r1 = r1.get(r9)
            r4 = r1
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4
            r1 = 3
            if (r8 == 0) goto L_0x01cc
            java.security.cert.PKIXParameters r2 = r10.pkixParams     // Catch:{ GeneralSecurityException -> 0x01a6 }
            java.lang.String r2 = r2.getSigProvider()     // Catch:{ GeneralSecurityException -> 0x01a6 }
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.verifyX509Certificate(r4, r8, r2)     // Catch:{ GeneralSecurityException -> 0x01a6 }
            goto L_0x0273
        L_0x01a6:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r14 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r11 = "CertPathReviewer.signatureNotVerified"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r18 = r2.getMessage()
            r1[r12] = r18
            r1[r13] = r2
            java.lang.Class r2 = r2.getClass()
            java.lang.String r2 = r2.getName()
            r17 = 2
            r1[r17] = r2
            r3.<init>(r14, r11, r1)
        L_0x01c7:
            r10.addError(r3, r9)
            goto L_0x0273
        L_0x01cc:
            boolean r2 = isSelfIssued(r4)
            if (r2 == 0) goto L_0x020f
            java.security.PublicKey r2 = r4.getPublicKey()     // Catch:{ GeneralSecurityException -> 0x01ed }
            java.security.cert.PKIXParameters r3 = r10.pkixParams     // Catch:{ GeneralSecurityException -> 0x01ed }
            java.lang.String r3 = r3.getSigProvider()     // Catch:{ GeneralSecurityException -> 0x01ed }
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.verifyX509Certificate(r4, r2, r3)     // Catch:{ GeneralSecurityException -> 0x01ed }
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ GeneralSecurityException -> 0x01ed }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r11 = "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"
            r2.<init>(r3, r11)     // Catch:{ GeneralSecurityException -> 0x01ed }
            r10.addError(r2, r9)     // Catch:{ GeneralSecurityException -> 0x01ed }
            goto L_0x0273
        L_0x01ed:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r11 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r14 = "CertPathReviewer.signatureNotVerified"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r18 = r2.getMessage()
            r1[r12] = r18
            r1[r13] = r2
            java.lang.Class r2 = r2.getClass()
            java.lang.String r2 = r2.getName()
            r17 = 2
            r1[r17] = r2
            r3.<init>(r11, r14, r1)
            goto L_0x01c7
        L_0x020f:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r11 = "CertPathReviewer.NoIssuerPublicKey"
            r2.<init>(r3, r11)
            org.bouncycastle.asn1.ASN1ObjectIdentifier r3 = org.bouncycastle.asn1.x509.X509Extensions.AuthorityKeyIdentifier
            java.lang.String r3 = r3.getId()
            byte[] r3 = r4.getExtensionValue(r3)
            if (r3 == 0) goto L_0x0270
            org.bouncycastle.asn1.ASN1Primitive r3 = org.bouncycastle.x509.extension.X509ExtensionUtil.fromExtensionValue(r3)     // Catch:{ IOException -> 0x0270 }
            org.bouncycastle.asn1.x509.AuthorityKeyIdentifier r3 = org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(r3)     // Catch:{ IOException -> 0x0270 }
            org.bouncycastle.asn1.x509.GeneralNames r11 = r3.getAuthorityCertIssuer()     // Catch:{ IOException -> 0x0270 }
            if (r11 == 0) goto L_0x0270
            org.bouncycastle.asn1.x509.GeneralName[] r11 = r11.getNames()     // Catch:{ IOException -> 0x0270 }
            r11 = r11[r12]     // Catch:{ IOException -> 0x0270 }
            java.math.BigInteger r3 = r3.getAuthorityCertSerialNumber()     // Catch:{ IOException -> 0x0270 }
            if (r3 == 0) goto L_0x0270
            r14 = 7
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ IOException -> 0x0270 }
            org.bouncycastle.i18n.LocaleString r1 = new org.bouncycastle.i18n.LocaleString     // Catch:{ IOException -> 0x0270 }
            java.lang.String r13 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r12 = "missingIssuer"
            r1.<init>(r13, r12)     // Catch:{ IOException -> 0x0270 }
            r12 = 0
            r14[r12] = r1     // Catch:{ IOException -> 0x0270 }
            java.lang.String r1 = " \""
            r12 = 1
            r14[r12] = r1     // Catch:{ IOException -> 0x0270 }
            r1 = 2
            r14[r1] = r11     // Catch:{ IOException -> 0x0270 }
            java.lang.String r1 = "\" "
            r11 = 3
            r14[r11] = r1     // Catch:{ IOException -> 0x0270 }
            r1 = 4
            org.bouncycastle.i18n.LocaleString r11 = new org.bouncycastle.i18n.LocaleString     // Catch:{ IOException -> 0x0270 }
            java.lang.String r12 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r13 = "missingSerial"
            r11.<init>(r12, r13)     // Catch:{ IOException -> 0x0270 }
            r14[r1] = r11     // Catch:{ IOException -> 0x0270 }
            java.lang.String r1 = " "
            r14[r16] = r1     // Catch:{ IOException -> 0x0270 }
            r1 = 6
            r14[r1] = r3     // Catch:{ IOException -> 0x0270 }
            r2.setExtraArguments(r14)     // Catch:{ IOException -> 0x0270 }
        L_0x0270:
            r10.addError(r2, r9)
        L_0x0273:
            java.util.Date r1 = r10.validDate     // Catch:{ CertificateNotYetValidException -> 0x0292, CertificateExpiredException -> 0x0279 }
            r4.checkValidity(r1)     // Catch:{ CertificateNotYetValidException -> 0x0292, CertificateExpiredException -> 0x0279 }
            goto L_0x02ad
        L_0x0279:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.certificateExpired"
            r11 = 1
            java.lang.Object[] r12 = new java.lang.Object[r11]
            org.bouncycastle.i18n.filter.TrustedInput r11 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r13 = r4.getNotAfter()
            r11.<init>(r13)
            r13 = 0
            r12[r13] = r11
            r1.<init>(r2, r3, r12)
            goto L_0x02aa
        L_0x0292:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.certificateNotYetValid"
            r11 = 1
            java.lang.Object[] r12 = new java.lang.Object[r11]
            org.bouncycastle.i18n.filter.TrustedInput r11 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r13 = r4.getNotBefore()
            r11.<init>(r13)
            r13 = 0
            r12[r13] = r11
            r1.<init>(r2, r3, r12)
        L_0x02aa:
            r10.addError(r1, r9)
        L_0x02ad:
            java.security.cert.PKIXParameters r1 = r10.pkixParams
            boolean r1 = r1.isRevocationEnabled()
            if (r1 == 0) goto L_0x0378
            java.lang.String r1 = b     // Catch:{ AnnotatedException -> 0x02c5 }
            org.bouncycastle.asn1.ASN1Primitive r1 = getExtensionValue(r4, r1)     // Catch:{ AnnotatedException -> 0x02c5 }
            if (r1 == 0) goto L_0x02c3
            org.bouncycastle.asn1.x509.CRLDistPoint r1 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r1)     // Catch:{ AnnotatedException -> 0x02c5 }
            r14 = r1
            goto L_0x02d2
        L_0x02c3:
            r14 = 0
            goto L_0x02d2
        L_0x02c5:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.crlDistPtExtError"
            r1.<init>(r2, r3)
            r10.addError(r1, r9)
            goto L_0x02c3
        L_0x02d2:
            java.lang.String r1 = c     // Catch:{ AnnotatedException -> 0x02df }
            org.bouncycastle.asn1.ASN1Primitive r1 = getExtensionValue(r4, r1)     // Catch:{ AnnotatedException -> 0x02df }
            if (r1 == 0) goto L_0x02eb
            org.bouncycastle.asn1.x509.AuthorityInformationAccess r1 = org.bouncycastle.asn1.x509.AuthorityInformationAccess.getInstance(r1)     // Catch:{ AnnotatedException -> 0x02df }
            goto L_0x02ec
        L_0x02df:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.crlAuthInfoAccError"
            r1.<init>(r2, r3)
            r10.addError(r1, r9)
        L_0x02eb:
            r1 = 0
        L_0x02ec:
            java.util.Vector r11 = r10.getCRLDistUrls(r14)
            java.util.Vector r12 = r10.getOCSPUrls(r1)
            java.util.Iterator r1 = r11.iterator()
        L_0x02f8:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0322
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r13 = "CertPathReviewer.crlDistPoint"
            r19 = r6
            r14 = 1
            java.lang.Object[] r6 = new java.lang.Object[r14]
            org.bouncycastle.i18n.filter.UntrustedUrlInput r14 = new org.bouncycastle.i18n.filter.UntrustedUrlInput
            r20 = r7
            java.lang.Object r7 = r1.next()
            r14.<init>(r7)
            r7 = 0
            r6[r7] = r14
            r2.<init>(r3, r13, r6)
            r10.addNotification(r2, r9)
            r6 = r19
            r7 = r20
            goto L_0x02f8
        L_0x0322:
            r19 = r6
            r20 = r7
            java.util.Iterator r1 = r12.iterator()
        L_0x032a:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x034c
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r6 = "CertPathReviewer.ocspLocation"
            r7 = 1
            java.lang.Object[] r13 = new java.lang.Object[r7]
            org.bouncycastle.i18n.filter.UntrustedUrlInput r7 = new org.bouncycastle.i18n.filter.UntrustedUrlInput
            java.lang.Object r14 = r1.next()
            r7.<init>(r14)
            r14 = 0
            r13[r14] = r7
            r2.<init>(r3, r6, r13)
            r10.addNotification(r2, r9)
            goto L_0x032a
        L_0x034c:
            java.security.cert.PKIXParameters r2 = r10.pkixParams     // Catch:{ CertPathReviewerException -> 0x0365 }
            java.util.Date r6 = r10.validDate     // Catch:{ CertPathReviewerException -> 0x0365 }
            r1 = r10
            r3 = r4
            r13 = r4
            r4 = r6
            r14 = r19
            r6 = r8
            r21 = r15
            r15 = r20
            r7 = r11
            r11 = r8
            r8 = r12
            r12 = r9
            r1.checkRevocation(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ CertPathReviewerException -> 0x0363 }
            goto L_0x037f
        L_0x0363:
            r0 = move-exception
            goto L_0x036f
        L_0x0365:
            r0 = move-exception
            r13 = r4
            r11 = r8
            r12 = r9
            r21 = r15
            r14 = r19
            r15 = r20
        L_0x036f:
            r1 = r0
            org.bouncycastle.i18n.ErrorBundle r1 = r1.getErrorMessage()
            r10.addError(r1, r12)
            goto L_0x037f
        L_0x0378:
            r13 = r4
            r14 = r6
            r11 = r8
            r12 = r9
            r21 = r15
            r15 = r7
        L_0x037f:
            if (r15 == 0) goto L_0x03ad
            javax.security.auth.x500.X500Principal r1 = r13.getIssuerX500Principal()
            boolean r1 = r1.equals(r15)
            if (r1 != 0) goto L_0x03ad
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.certWrongIssuer"
            r4 = 2
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.String r6 = r15.getName()
            r7 = 0
            r5[r7] = r6
            javax.security.auth.x500.X500Principal r6 = r13.getIssuerX500Principal()
            java.lang.String r6 = r6.getName()
            r8 = 1
            r5[r8] = r6
            r1.<init>(r2, r3, r5)
            r10.addError(r1, r12)
            goto L_0x03b0
        L_0x03ad:
            r4 = 2
            r7 = 0
            r8 = 1
        L_0x03b0:
            int r1 = r10.n
            if (r14 == r1) goto L_0x0413
            if (r13 == 0) goto L_0x03c8
            int r1 = r13.getVersion()
            if (r1 != r8) goto L_0x03c8
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.noCACert"
            r1.<init>(r2, r3)
            r10.addError(r1, r12)
        L_0x03c8:
            java.lang.String r1 = BASIC_CONSTRAINTS     // Catch:{ AnnotatedException -> 0x03f1 }
            org.bouncycastle.asn1.ASN1Primitive r1 = getExtensionValue(r13, r1)     // Catch:{ AnnotatedException -> 0x03f1 }
            org.bouncycastle.asn1.x509.BasicConstraints r1 = org.bouncycastle.asn1.x509.BasicConstraints.getInstance(r1)     // Catch:{ AnnotatedException -> 0x03f1 }
            if (r1 == 0) goto L_0x03e7
            boolean r1 = r1.isCA()     // Catch:{ AnnotatedException -> 0x03f1 }
            if (r1 != 0) goto L_0x03fd
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ AnnotatedException -> 0x03f1 }
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.noCACert"
            r1.<init>(r2, r3)     // Catch:{ AnnotatedException -> 0x03f1 }
        L_0x03e3:
            r10.addError(r1, r12)     // Catch:{ AnnotatedException -> 0x03f1 }
            goto L_0x03fd
        L_0x03e7:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ AnnotatedException -> 0x03f1 }
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.noBasicConstraints"
            r1.<init>(r2, r3)     // Catch:{ AnnotatedException -> 0x03f1 }
            goto L_0x03e3
        L_0x03f1:
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.errorProcesingBC"
            r1.<init>(r2, r3)
            r10.addError(r1, r12)
        L_0x03fd:
            boolean[] r1 = r13.getKeyUsage()
            if (r1 == 0) goto L_0x0413
            boolean r1 = r1[r16]
            if (r1 != 0) goto L_0x0413
            org.bouncycastle.i18n.ErrorBundle r1 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r2 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r3 = "CertPathReviewer.noCertSign"
            r1.<init>(r2, r3)
            r10.addError(r1, r12)
        L_0x0413:
            javax.security.auth.x500.X500Principal r1 = r13.getSubjectX500Principal()
            java.util.List r2 = r10.certs     // Catch:{ CertPathValidatorException -> 0x0428 }
            java.security.PublicKey r2 = getNextWorkingKey(r2, r12)     // Catch:{ CertPathValidatorException -> 0x0428 }
            org.bouncycastle.asn1.x509.AlgorithmIdentifier r3 = getAlgorithmIdentifier(r2)     // Catch:{ CertPathValidatorException -> 0x0429 }
            r3.getObjectId()     // Catch:{ CertPathValidatorException -> 0x0429 }
            r3.getParameters()     // Catch:{ CertPathValidatorException -> 0x0429 }
            goto L_0x0435
        L_0x0428:
            r2 = r11
        L_0x0429:
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r5 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r6 = "CertPathReviewer.pubKeyError"
            r3.<init>(r5, r6)
            r10.addError(r3, r12)
        L_0x0435:
            int r9 = r12 + -1
            r7 = r1
            r8 = r2
            r5 = r13
            r15 = r21
            r11 = 2
            r12 = 0
            r13 = 1
            goto L_0x0189
        L_0x0441:
            r11 = r8
            r2 = r15
            r10.trustAnchor = r2
            r10.subjectPublicKey = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.PKIXCertPathReviewer.c():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:200:0x0421, code lost:
        throw new org.bouncycastle.x509.CertPathReviewerException(new org.bouncycastle.i18n.ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.policyInhibitExtError"), r1.certPath, r10);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:198:0x0411 */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x025f A[Catch:{ AnnotatedException -> 0x061b, AnnotatedException -> 0x0452, AnnotatedException -> 0x0433, AnnotatedException -> 0x0422, AnnotatedException -> 0x0387, CertPathValidatorException -> 0x0375, CertPathValidatorException -> 0x0218, CertPathValidatorException -> 0x00d4, CertPathReviewerException -> 0x062c }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x03bf A[Catch:{ AnnotatedException -> 0x061b, AnnotatedException -> 0x0452, AnnotatedException -> 0x0433, AnnotatedException -> 0x0422, AnnotatedException -> 0x0387, CertPathValidatorException -> 0x0375, CertPathValidatorException -> 0x0218, CertPathValidatorException -> 0x00d4, CertPathReviewerException -> 0x062c }] */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0403 A[Catch:{ AnnotatedException -> 0x0411 }] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0233 A[Catch:{ AnnotatedException -> 0x061b, AnnotatedException -> 0x0452, AnnotatedException -> 0x0433, AnnotatedException -> 0x0422, AnnotatedException -> 0x0387, CertPathValidatorException -> 0x0375, CertPathValidatorException -> 0x0218, CertPathValidatorException -> 0x00d4, CertPathReviewerException -> 0x062c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d() {
        /*
            r32 = this;
            r1 = r32
            java.security.cert.PKIXParameters r2 = r1.pkixParams
            java.util.Set r2 = r2.getInitialPolicies()
            int r3 = r1.n
            r4 = 1
            int r3 = r3 + r4
            java.util.ArrayList[] r3 = new java.util.ArrayList[r3]
            r5 = 0
            r6 = 0
        L_0x0010:
            int r7 = r3.length
            if (r6 >= r7) goto L_0x001d
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r3[r6] = r7
            int r6 = r6 + 1
            goto L_0x0010
        L_0x001d:
            java.util.HashSet r10 = new java.util.HashSet
            r10.<init>()
            java.lang.String r6 = "2.5.29.32.0"
            r10.add(r6)
            org.bouncycastle.jce.provider.PKIXPolicyNode r6 = new org.bouncycastle.jce.provider.PKIXPolicyNode
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r9 = 0
            r11 = 0
            java.util.HashSet r12 = new java.util.HashSet
            r12.<init>()
            java.lang.String r13 = "2.5.29.32.0"
            r14 = 0
            r7 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13, r14)
            r7 = r3[r5]
            r7.add(r6)
            java.security.cert.PKIXParameters r7 = r1.pkixParams
            boolean r7 = r7.isExplicitPolicyRequired()
            if (r7 == 0) goto L_0x004b
            r7 = 0
            goto L_0x004e
        L_0x004b:
            int r7 = r1.n
            int r7 = r7 + r4
        L_0x004e:
            java.security.cert.PKIXParameters r8 = r1.pkixParams
            boolean r8 = r8.isAnyPolicyInhibited()
            if (r8 == 0) goto L_0x0058
            r8 = 0
            goto L_0x005b
        L_0x0058:
            int r8 = r1.n
            int r8 = r8 + r4
        L_0x005b:
            java.security.cert.PKIXParameters r9 = r1.pkixParams
            boolean r9 = r9.isPolicyMappingInhibited()
            if (r9 == 0) goto L_0x0065
            r9 = 0
            goto L_0x0068
        L_0x0065:
            int r9 = r1.n
            int r9 = r9 + r4
        L_0x0068:
            java.util.List r10 = r1.certs     // Catch:{ CertPathReviewerException -> 0x062c }
            int r10 = r10.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            int r10 = r10 - r4
            r12 = r8
            r13 = r9
            r9 = 0
            r8 = r6
            r6 = 0
        L_0x0074:
            if (r10 < 0) goto L_0x0465
            int r6 = r1.n     // Catch:{ CertPathReviewerException -> 0x062c }
            int r6 = r6 - r10
            java.util.List r14 = r1.certs     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.Object r14 = r14.get(r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            r15 = r14
            java.security.cert.X509Certificate r15 = (java.security.cert.X509Certificate) r15     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r14 = CERTIFICATE_POLICIES     // Catch:{ AnnotatedException -> 0x0452 }
            org.bouncycastle.asn1.ASN1Primitive r14 = getExtensionValue(r15, r14)     // Catch:{ AnnotatedException -> 0x0452 }
            org.bouncycastle.asn1.ASN1Sequence r14 = (org.bouncycastle.asn1.ASN1Sequence) r14     // Catch:{ AnnotatedException -> 0x0452 }
            if (r14 == 0) goto L_0x027f
            if (r8 == 0) goto L_0x027f
            java.util.Enumeration r11 = r14.getObjects()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.HashSet r4 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0097:
            boolean r16 = r11.hasMoreElements()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r16 == 0) goto L_0x00ef
            java.lang.Object r5 = r11.nextElement()     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.x509.PolicyInformation r5 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            r22 = r11
            org.bouncycastle.asn1.ASN1ObjectIdentifier r11 = r5.getPolicyIdentifier()     // Catch:{ CertPathReviewerException -> 0x062c }
            r23 = r2
            java.lang.String r2 = r11.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.add(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r2 = "2.5.29.32.0"
            r24 = r8
            java.lang.String r8 = r11.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r2 = r2.equals(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 != 0) goto L_0x00e7
            org.bouncycastle.asn1.ASN1Sequence r2 = r5.getPolicyQualifiers()     // Catch:{ CertPathValidatorException -> 0x00d4 }
            java.util.Set r2 = getQualifierSet(r2)     // Catch:{ CertPathValidatorException -> 0x00d4 }
            boolean r5 = processCertD1i(r6, r3, r11, r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 != 0) goto L_0x00e7
            processCertD1ii(r6, r3, r11, r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x00e7
        L_0x00d4:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.policyQualifierError"
            r3.<init>(r4, r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r5 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>(r3, r2, r5, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r4     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x00e7:
            r11 = r22
            r2 = r23
            r8 = r24
            r5 = 0
            goto L_0x0097
        L_0x00ef:
            r23 = r2
            r24 = r8
            if (r9 == 0) goto L_0x011c
            java.lang.String r2 = "2.5.29.32.0"
            boolean r2 = r9.contains(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x00fe
            goto L_0x011c
        L_0x00fe:
            java.util.Iterator r2 = r9.iterator()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.HashSet r5 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r5.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0107:
            boolean r8 = r2.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 == 0) goto L_0x011b
            java.lang.Object r8 = r2.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r9 = r4.contains(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r9 == 0) goto L_0x0107
            r5.add(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x0107
        L_0x011b:
            r4 = r5
        L_0x011c:
            if (r12 > 0) goto L_0x0132
            int r2 = r1.n     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r6 >= r2) goto L_0x0129
            boolean r2 = isSelfIssued(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x0129
            goto L_0x0132
        L_0x0129:
            r25 = r4
            r30 = r12
            r22 = r14
            r12 = r15
            goto L_0x022f
        L_0x0132:
            java.util.Enumeration r2 = r14.getObjects()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0136:
            boolean r5 = r2.hasMoreElements()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 == 0) goto L_0x0129
            java.lang.Object r5 = r2.nextElement()     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.x509.PolicyInformation r5 = org.bouncycastle.asn1.x509.PolicyInformation.getInstance(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r8 = "2.5.29.32.0"
            org.bouncycastle.asn1.ASN1ObjectIdentifier r9 = r5.getPolicyIdentifier()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r9 = r9.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r8 = r8.equals(r9)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 == 0) goto L_0x022b
            org.bouncycastle.asn1.ASN1Sequence r2 = r5.getPolicyQualifiers()     // Catch:{ CertPathValidatorException -> 0x0218 }
            java.util.Set r2 = getQualifierSet(r2)     // Catch:{ CertPathValidatorException -> 0x0218 }
            int r5 = r6 + -1
            r5 = r3[r5]     // Catch:{ CertPathReviewerException -> 0x062c }
            r8 = 0
        L_0x0161:
            int r9 = r5.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 >= r9) goto L_0x0129
            java.lang.Object r9 = r5.get(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r9     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.Set r11 = r9.getExpectedPolicies()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0175:
            boolean r16 = r11.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r16 == 0) goto L_0x0209
            r25 = r4
            java.lang.Object r4 = r11.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            r26 = r5
            boolean r5 = r4 instanceof java.lang.String     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 == 0) goto L_0x018a
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x0194
        L_0x018a:
            boolean r5 = r4 instanceof org.bouncycastle.asn1.ASN1ObjectIdentifier     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 == 0) goto L_0x0201
            org.bouncycastle.asn1.ASN1ObjectIdentifier r4 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r4     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = r4.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0194:
            java.util.Iterator r5 = r9.getChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
            r16 = 0
        L_0x019a:
            boolean r17 = r5.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r17 == 0) goto L_0x01b9
            java.lang.Object r17 = r5.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            r27 = r5
            r5 = r17
            org.bouncycastle.jce.provider.PKIXPolicyNode r5 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r5     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r5 = r5.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r5 = r4.equals(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 == 0) goto L_0x01b6
            r16 = 1
        L_0x01b6:
            r5 = r27
            goto L_0x019a
        L_0x01b9:
            if (r16 != 0) goto L_0x01ed
            java.util.HashSet r5 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r5.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r5.add(r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            r28 = r11
            org.bouncycastle.jce.provider.PKIXPolicyNode r11 = new org.bouncycastle.jce.provider.PKIXPolicyNode     // Catch:{ CertPathReviewerException -> 0x062c }
            r29 = r15
            java.util.ArrayList r15 = new java.util.ArrayList     // Catch:{ CertPathReviewerException -> 0x062c }
            r15.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r21 = 0
            r22 = r14
            r14 = r11
            r30 = r12
            r12 = r29
            r16 = r6
            r17 = r5
            r18 = r9
            r19 = r2
            r20 = r4
            r14.<init>(r15, r16, r17, r18, r19, r20, r21)     // Catch:{ CertPathReviewerException -> 0x062c }
            r9.addChild(r11)     // Catch:{ CertPathReviewerException -> 0x062c }
            r4 = r3[r6]     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.add(r11)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x01f4
        L_0x01ed:
            r28 = r11
            r30 = r12
            r22 = r14
            r12 = r15
        L_0x01f4:
            r15 = r12
            r14 = r22
            r4 = r25
            r5 = r26
            r11 = r28
            r12 = r30
            goto L_0x0175
        L_0x0201:
            r30 = r12
            r4 = r25
            r5 = r26
            goto L_0x0175
        L_0x0209:
            r25 = r4
            r26 = r5
            r30 = r12
            r22 = r14
            r12 = r15
            int r8 = r8 + 1
            r12 = r30
            goto L_0x0161
        L_0x0218:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.policyQualifierError"
            r3.<init>(r4, r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r5 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>(r3, r2, r5, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r4     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x022b:
            r30 = r12
            goto L_0x0136
        L_0x022f:
            int r2 = r6 + -1
        L_0x0231:
            if (r2 < 0) goto L_0x0259
            r4 = r3[r2]     // Catch:{ CertPathReviewerException -> 0x062c }
            r8 = r24
            r5 = 0
        L_0x0238:
            int r9 = r4.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 >= r9) goto L_0x0254
            java.lang.Object r9 = r4.get(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r9     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r11 = r9.hasChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r11 != 0) goto L_0x0251
            org.bouncycastle.jce.provider.PKIXPolicyNode r8 = removePolicyNode(r8, r3, r9)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 != 0) goto L_0x0251
            goto L_0x0254
        L_0x0251:
            int r5 = r5 + 1
            goto L_0x0238
        L_0x0254:
            r24 = r8
            int r2 = r2 + -1
            goto L_0x0231
        L_0x0259:
            java.util.Set r2 = r12.getCriticalExtensionOIDs()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x027a
            java.lang.String r4 = CERTIFICATE_POLICIES     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r2 = r2.contains(r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            r4 = r3[r6]     // Catch:{ CertPathReviewerException -> 0x062c }
            r5 = 0
        L_0x0268:
            int r8 = r4.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 >= r8) goto L_0x027a
            java.lang.Object r8 = r4.get(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r8 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            r8.setCritical(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            int r5 = r5 + 1
            goto L_0x0268
        L_0x027a:
            r11 = r24
            r9 = r25
            goto L_0x028a
        L_0x027f:
            r23 = r2
            r24 = r8
            r30 = r12
            r22 = r14
            r12 = r15
            r11 = r24
        L_0x028a:
            if (r22 != 0) goto L_0x028d
            r11 = 0
        L_0x028d:
            if (r7 > 0) goto L_0x02a0
            if (r11 != 0) goto L_0x02a0
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.noValidPolicyTree"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x02a0:
            int r2 = r1.n     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r6 == r2) goto L_0x0446
            java.lang.String r2 = POLICY_MAPPINGS     // Catch:{ AnnotatedException -> 0x0433 }
            org.bouncycastle.asn1.ASN1Primitive r2 = getExtensionValue(r12, r2)     // Catch:{ AnnotatedException -> 0x0433 }
            if (r2 == 0) goto L_0x0307
            r4 = r2
            org.bouncycastle.asn1.ASN1Sequence r4 = (org.bouncycastle.asn1.ASN1Sequence) r4     // Catch:{ CertPathReviewerException -> 0x062c }
            r5 = 0
        L_0x02b0:
            int r8 = r4.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 >= r8) goto L_0x0307
            org.bouncycastle.asn1.ASN1Encodable r8 = r4.getObjectAt(r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.ASN1Sequence r8 = (org.bouncycastle.asn1.ASN1Sequence) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            r14 = 0
            org.bouncycastle.asn1.ASN1Encodable r15 = r8.getObjectAt(r14)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r15 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r15     // Catch:{ CertPathReviewerException -> 0x062c }
            r14 = 1
            org.bouncycastle.asn1.ASN1Encodable r8 = r8.getObjectAt(r14)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r8 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r14 = "2.5.29.32.0"
            java.lang.String r15 = r15.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r14 = r14.equals(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r14 == 0) goto L_0x02e7
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.invalidPolicyMapping"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x02e7:
            java.lang.String r14 = "2.5.29.32.0"
            java.lang.String r8 = r8.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r8 = r14.equals(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 == 0) goto L_0x0304
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.invalidPolicyMapping"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0304:
            int r5 = r5 + 1
            goto L_0x02b0
        L_0x0307:
            if (r2 == 0) goto L_0x03a0
            org.bouncycastle.asn1.ASN1Sequence r2 = (org.bouncycastle.asn1.ASN1Sequence) r2     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.HashMap r4 = new java.util.HashMap     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.HashSet r5 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r5.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r8 = 0
        L_0x0316:
            int r14 = r2.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 >= r14) goto L_0x035f
            org.bouncycastle.asn1.ASN1Encodable r14 = r2.getObjectAt(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.ASN1Sequence r14 = (org.bouncycastle.asn1.ASN1Sequence) r14     // Catch:{ CertPathReviewerException -> 0x062c }
            r15 = 0
            org.bouncycastle.asn1.ASN1Encodable r16 = r14.getObjectAt(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            r15 = r16
            org.bouncycastle.asn1.ASN1ObjectIdentifier r15 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r15     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r15 = r15.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            r31 = r2
            r2 = 1
            org.bouncycastle.asn1.ASN1Encodable r14 = r14.getObjectAt(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r14 = (org.bouncycastle.asn1.ASN1ObjectIdentifier) r14     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r2 = r14.getId()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r14 = r4.containsKey(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r14 != 0) goto L_0x0351
            java.util.HashSet r14 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r14.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r14.add(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.put(r15, r14)     // Catch:{ CertPathReviewerException -> 0x062c }
            r5.add(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x035a
        L_0x0351:
            java.lang.Object r14 = r4.get(r15)     // Catch:{ CertPathReviewerException -> 0x062c }
            java.util.Set r14 = (java.util.Set) r14     // Catch:{ CertPathReviewerException -> 0x062c }
            r14.add(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x035a:
            int r8 = r8 + 1
            r2 = r31
            goto L_0x0316
        L_0x035f:
            java.util.Iterator r2 = r5.iterator()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0363:
            boolean r5 = r2.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r5 == 0) goto L_0x03a0
            java.lang.Object r5 = r2.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r13 <= 0) goto L_0x0399
            prepareNextCertB1(r6, r3, r5, r4, r12)     // Catch:{ AnnotatedException -> 0x0387, CertPathValidatorException -> 0x0375 }
            goto L_0x0363
        L_0x0375:
            r0 = move-exception
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.policyQualifierError"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r0, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0387:
            r0 = move-exception
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.policyExtError"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r0, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0399:
            if (r13 > 0) goto L_0x0363
            org.bouncycastle.jce.provider.PKIXPolicyNode r11 = prepareNextCertB2(r6, r3, r5, r11)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x0363
        L_0x03a0:
            boolean r2 = isSelfIssued(r12)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 != 0) goto L_0x03b3
            if (r7 == 0) goto L_0x03aa
            int r7 = r7 + -1
        L_0x03aa:
            if (r13 == 0) goto L_0x03ae
            int r13 = r13 + -1
        L_0x03ae:
            if (r30 == 0) goto L_0x03b3
            int r2 = r30 + -1
            goto L_0x03b5
        L_0x03b3:
            r2 = r30
        L_0x03b5:
            java.lang.String r4 = POLICY_CONSTRAINTS     // Catch:{ AnnotatedException -> 0x0422 }
            org.bouncycastle.asn1.ASN1Primitive r4 = getExtensionValue(r12, r4)     // Catch:{ AnnotatedException -> 0x0422 }
            org.bouncycastle.asn1.ASN1Sequence r4 = (org.bouncycastle.asn1.ASN1Sequence) r4     // Catch:{ AnnotatedException -> 0x0422 }
            if (r4 == 0) goto L_0x03f9
            java.util.Enumeration r4 = r4.getObjects()     // Catch:{ AnnotatedException -> 0x0422 }
        L_0x03c3:
            boolean r5 = r4.hasMoreElements()     // Catch:{ AnnotatedException -> 0x0422 }
            if (r5 == 0) goto L_0x03f9
            java.lang.Object r5 = r4.nextElement()     // Catch:{ AnnotatedException -> 0x0422 }
            org.bouncycastle.asn1.ASN1TaggedObject r5 = (org.bouncycastle.asn1.ASN1TaggedObject) r5     // Catch:{ AnnotatedException -> 0x0422 }
            int r6 = r5.getTagNo()     // Catch:{ AnnotatedException -> 0x0422 }
            switch(r6) {
                case 0: goto L_0x03e8;
                case 1: goto L_0x03d7;
                default: goto L_0x03d6;
            }     // Catch:{ AnnotatedException -> 0x0422 }
        L_0x03d6:
            goto L_0x03c3
        L_0x03d7:
            r6 = 0
            org.bouncycastle.asn1.ASN1Integer r5 = org.bouncycastle.asn1.ASN1Integer.getInstance(r5, r6)     // Catch:{ AnnotatedException -> 0x0422 }
            java.math.BigInteger r5 = r5.getValue()     // Catch:{ AnnotatedException -> 0x0422 }
            int r5 = r5.intValue()     // Catch:{ AnnotatedException -> 0x0422 }
            if (r5 >= r13) goto L_0x03c3
            r13 = r5
            goto L_0x03c3
        L_0x03e8:
            r6 = 0
            org.bouncycastle.asn1.ASN1Integer r5 = org.bouncycastle.asn1.ASN1Integer.getInstance(r5, r6)     // Catch:{ AnnotatedException -> 0x0422 }
            java.math.BigInteger r5 = r5.getValue()     // Catch:{ AnnotatedException -> 0x0422 }
            int r5 = r5.intValue()     // Catch:{ AnnotatedException -> 0x0422 }
            if (r5 >= r7) goto L_0x03c3
            r7 = r5
            goto L_0x03c3
        L_0x03f9:
            java.lang.String r4 = INHIBIT_ANY_POLICY     // Catch:{ AnnotatedException -> 0x0411 }
            org.bouncycastle.asn1.ASN1Primitive r4 = getExtensionValue(r12, r4)     // Catch:{ AnnotatedException -> 0x0411 }
            org.bouncycastle.asn1.ASN1Integer r4 = (org.bouncycastle.asn1.ASN1Integer) r4     // Catch:{ AnnotatedException -> 0x0411 }
            if (r4 == 0) goto L_0x040e
            java.math.BigInteger r4 = r4.getValue()     // Catch:{ AnnotatedException -> 0x0411 }
            int r4 = r4.intValue()     // Catch:{ AnnotatedException -> 0x0411 }
            if (r4 >= r2) goto L_0x040e
            r2 = r4
        L_0x040e:
            r30 = r2
            goto L_0x0446
        L_0x0411:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.policyInhibitExtError"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0422:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.policyConstExtError"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0433:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.policyMapExtError"
            r3.<init>(r4, r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r5 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>(r3, r2, r5, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r4     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0446:
            r8 = r11
            int r10 = r10 + -1
            r6 = r12
            r2 = r23
            r12 = r30
            r4 = 1
            r5 = 0
            goto L_0x0074
        L_0x0452:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.policyExtError"
            r3.<init>(r4, r5)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r5 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>(r3, r2, r5, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r4     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0465:
            r23 = r2
            r24 = r8
            boolean r2 = isSelfIssued(r6)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 != 0) goto L_0x0473
            if (r7 <= 0) goto L_0x0473
            int r7 = r7 + -1
        L_0x0473:
            java.lang.String r2 = POLICY_CONSTRAINTS     // Catch:{ AnnotatedException -> 0x061b }
            org.bouncycastle.asn1.ASN1Primitive r2 = getExtensionValue(r6, r2)     // Catch:{ AnnotatedException -> 0x061b }
            org.bouncycastle.asn1.ASN1Sequence r2 = (org.bouncycastle.asn1.ASN1Sequence) r2     // Catch:{ AnnotatedException -> 0x061b }
            if (r2 == 0) goto L_0x04a9
            java.util.Enumeration r2 = r2.getObjects()     // Catch:{ AnnotatedException -> 0x061b }
            r5 = r7
        L_0x0482:
            boolean r4 = r2.hasMoreElements()     // Catch:{ AnnotatedException -> 0x061b }
            if (r4 == 0) goto L_0x04a7
            java.lang.Object r4 = r2.nextElement()     // Catch:{ AnnotatedException -> 0x061b }
            org.bouncycastle.asn1.ASN1TaggedObject r4 = (org.bouncycastle.asn1.ASN1TaggedObject) r4     // Catch:{ AnnotatedException -> 0x061b }
            int r6 = r4.getTagNo()     // Catch:{ AnnotatedException -> 0x061b }
            if (r6 == 0) goto L_0x0496
            r14 = 0
            goto L_0x0482
        L_0x0496:
            r14 = 0
            org.bouncycastle.asn1.ASN1Integer r4 = org.bouncycastle.asn1.ASN1Integer.getInstance(r4, r14)     // Catch:{ AnnotatedException -> 0x061b }
            java.math.BigInteger r4 = r4.getValue()     // Catch:{ AnnotatedException -> 0x061b }
            int r4 = r4.intValue()     // Catch:{ AnnotatedException -> 0x061b }
            if (r4 != 0) goto L_0x0482
            r5 = 0
            goto L_0x0482
        L_0x04a7:
            r14 = 0
            goto L_0x04ab
        L_0x04a9:
            r14 = 0
            r5 = r7
        L_0x04ab:
            if (r24 != 0) goto L_0x04c9
            java.security.cert.PKIXParameters r2 = r1.pkixParams     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r2 = r2.isExplicitPolicyRequired()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x04c6
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.explicitPolicy"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x04c6:
            r11 = 0
            goto L_0x0608
        L_0x04c9:
            r2 = r23
            boolean r4 = isAnyPolicy(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r4 == 0) goto L_0x0573
            java.security.cert.PKIXParameters r2 = r1.pkixParams     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r2 = r2.isExplicitPolicyRequired()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x056f
            boolean r2 = r9.isEmpty()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r2 == 0) goto L_0x04f0
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.explicitPolicy"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x04f0:
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r2.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r4 = 0
        L_0x04f6:
            int r6 = r3.length     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r4 >= r6) goto L_0x052c
            r6 = r3[r4]     // Catch:{ CertPathReviewerException -> 0x062c }
            r7 = 0
        L_0x04fc:
            int r8 = r6.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r7 >= r8) goto L_0x0529
            java.lang.Object r8 = r6.get(r7)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r8 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r10 = "2.5.29.32.0"
            java.lang.String r11 = r8.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r10 = r10.equals(r11)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r10 == 0) goto L_0x0526
            java.util.Iterator r8 = r8.getChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0518:
            boolean r10 = r8.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r10 == 0) goto L_0x0526
            java.lang.Object r10 = r8.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            r2.add(r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x0518
        L_0x0526:
            int r7 = r7 + 1
            goto L_0x04fc
        L_0x0529:
            int r4 = r4 + 1
            goto L_0x04f6
        L_0x052c:
            java.util.Iterator r2 = r2.iterator()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0530:
            boolean r4 = r2.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r4 == 0) goto L_0x0544
            java.lang.Object r4 = r2.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r4 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r4     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r4 = r4.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            r9.contains(r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x0530
        L_0x0544:
            if (r24 == 0) goto L_0x056f
            int r2 = r1.n     // Catch:{ CertPathReviewerException -> 0x062c }
            r4 = 1
            int r2 = r2 - r4
        L_0x054a:
            if (r2 < 0) goto L_0x056f
            r4 = r3[r2]     // Catch:{ CertPathReviewerException -> 0x062c }
            r7 = r24
            r6 = 0
        L_0x0551:
            int r8 = r4.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r6 >= r8) goto L_0x056a
            java.lang.Object r8 = r4.get(r6)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r8 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r9 = r8.hasChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r9 != 0) goto L_0x0567
            org.bouncycastle.jce.provider.PKIXPolicyNode r7 = removePolicyNode(r7, r3, r8)     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0567:
            int r6 = r6 + 1
            goto L_0x0551
        L_0x056a:
            int r2 = r2 + -1
            r24 = r7
            goto L_0x054a
        L_0x056f:
            r11 = r24
            goto L_0x0608
        L_0x0573:
            java.util.HashSet r4 = new java.util.HashSet     // Catch:{ CertPathReviewerException -> 0x062c }
            r4.<init>()     // Catch:{ CertPathReviewerException -> 0x062c }
            r6 = 0
        L_0x0579:
            int r7 = r3.length     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r6 >= r7) goto L_0x05bd
            r7 = r3[r6]     // Catch:{ CertPathReviewerException -> 0x062c }
            r8 = 0
        L_0x057f:
            int r9 = r7.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 >= r9) goto L_0x05ba
            java.lang.Object r9 = r7.get(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r9 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r9     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r10 = "2.5.29.32.0"
            java.lang.String r11 = r9.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r10 = r10.equals(r11)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r10 == 0) goto L_0x05b7
            java.util.Iterator r9 = r9.getChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x059b:
            boolean r10 = r9.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r10 == 0) goto L_0x05b7
            java.lang.Object r10 = r9.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r10 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r10     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r11 = "2.5.29.32.0"
            java.lang.String r12 = r10.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r11 = r11.equals(r12)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r11 != 0) goto L_0x059b
            r4.add(r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x059b
        L_0x05b7:
            int r8 = r8 + 1
            goto L_0x057f
        L_0x05ba:
            int r6 = r6 + 1
            goto L_0x0579
        L_0x05bd:
            java.util.Iterator r4 = r4.iterator()     // Catch:{ CertPathReviewerException -> 0x062c }
            r6 = r24
        L_0x05c3:
            boolean r7 = r4.hasNext()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r7 == 0) goto L_0x05de
            java.lang.Object r7 = r4.next()     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r7 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r7     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r8 = r7.getValidPolicy()     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r8 = r2.contains(r8)     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r8 != 0) goto L_0x05c3
            org.bouncycastle.jce.provider.PKIXPolicyNode r6 = removePolicyNode(r6, r3, r7)     // Catch:{ CertPathReviewerException -> 0x062c }
            goto L_0x05c3
        L_0x05de:
            if (r6 == 0) goto L_0x0607
            int r2 = r1.n     // Catch:{ CertPathReviewerException -> 0x062c }
            r4 = 1
            int r2 = r2 - r4
        L_0x05e4:
            if (r2 < 0) goto L_0x0607
            r4 = r3[r2]     // Catch:{ CertPathReviewerException -> 0x062c }
            r7 = r6
            r6 = 0
        L_0x05ea:
            int r8 = r4.size()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r6 >= r8) goto L_0x0603
            java.lang.Object r8 = r4.get(r6)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.jce.provider.PKIXPolicyNode r8 = (org.bouncycastle.jce.provider.PKIXPolicyNode) r8     // Catch:{ CertPathReviewerException -> 0x062c }
            boolean r9 = r8.hasChildren()     // Catch:{ CertPathReviewerException -> 0x062c }
            if (r9 != 0) goto L_0x0600
            org.bouncycastle.jce.provider.PKIXPolicyNode r7 = removePolicyNode(r7, r3, r8)     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x0600:
            int r6 = r6 + 1
            goto L_0x05ea
        L_0x0603:
            int r2 = r2 + -1
            r6 = r7
            goto L_0x05e4
        L_0x0607:
            r11 = r6
        L_0x0608:
            if (r5 > 0) goto L_0x0639
            if (r11 != 0) goto L_0x0639
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.invalidPolicy"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x061b:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x062c }
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.policyConstExtError"
            r2.<init>(r3, r4)     // Catch:{ CertPathReviewerException -> 0x062c }
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException     // Catch:{ CertPathReviewerException -> 0x062c }
            java.security.cert.CertPath r4 = r1.certPath     // Catch:{ CertPathReviewerException -> 0x062c }
            r3.<init>(r2, r4, r10)     // Catch:{ CertPathReviewerException -> 0x062c }
            throw r3     // Catch:{ CertPathReviewerException -> 0x062c }
        L_0x062c:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = r2.getErrorMessage()
            int r2 = r2.getIndex()
            r1.addError(r3, r2)
        L_0x0639:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.PKIXCertPathReviewer.d():void");
    }

    private void e() {
        int size;
        List<PKIXCertPathChecker> certPathCheckers = this.pkixParams.getCertPathCheckers();
        for (PKIXCertPathChecker init : certPathCheckers) {
            try {
                init.init(false);
            } catch (CertPathValidatorException e) {
                throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.certPathCheckerError", new Object[]{e.getMessage(), e, e.getClass().getName()}), e);
            } catch (CertPathValidatorException e2) {
                throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.criticalExtensionError", new Object[]{e2.getMessage(), e2, e2.getClass().getName()}), e2.getCause(), this.certPath, size);
            } catch (CertPathReviewerException e3) {
                addError(e3.getErrorMessage(), e3.getIndex());
                return;
            }
        }
        size = this.certs.size() - 1;
        while (size >= 0) {
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            Set<String> criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null) {
                if (!criticalExtensionOIDs.isEmpty()) {
                    criticalExtensionOIDs.remove(KEY_USAGE);
                    criticalExtensionOIDs.remove(CERTIFICATE_POLICIES);
                    criticalExtensionOIDs.remove(POLICY_MAPPINGS);
                    criticalExtensionOIDs.remove(INHIBIT_ANY_POLICY);
                    criticalExtensionOIDs.remove(ISSUING_DISTRIBUTION_POINT);
                    criticalExtensionOIDs.remove(DELTA_CRL_INDICATOR);
                    criticalExtensionOIDs.remove(POLICY_CONSTRAINTS);
                    criticalExtensionOIDs.remove(BASIC_CONSTRAINTS);
                    criticalExtensionOIDs.remove(SUBJECT_ALTERNATIVE_NAME);
                    criticalExtensionOIDs.remove(NAME_CONSTRAINTS);
                    if (criticalExtensionOIDs.contains(a) && a(x509Certificate, size)) {
                        criticalExtensionOIDs.remove(a);
                    }
                    for (PKIXCertPathChecker check : certPathCheckers) {
                        check.check(x509Certificate, criticalExtensionOIDs);
                    }
                    if (!criticalExtensionOIDs.isEmpty()) {
                        for (String aSN1ObjectIdentifier : criticalExtensionOIDs) {
                            addError(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.unknownCriticalExt", new Object[]{new ASN1ObjectIdentifier(aSN1ObjectIdentifier)}), size);
                        }
                    }
                }
            }
            size--;
        }
    }

    /* access modifiers changed from: protected */
    public void addError(ErrorBundle errorBundle) {
        this.errors[0].add(errorBundle);
    }

    /* access modifiers changed from: protected */
    public void addError(ErrorBundle errorBundle, int i) {
        if (i < -1 || i >= this.n) {
            throw new IndexOutOfBoundsException();
        }
        this.errors[i + 1].add(errorBundle);
    }

    /* access modifiers changed from: protected */
    public void addNotification(ErrorBundle errorBundle) {
        this.notifications[0].add(errorBundle);
    }

    /* access modifiers changed from: protected */
    public void addNotification(ErrorBundle errorBundle, int i) {
        if (i < -1 || i >= this.n) {
            throw new IndexOutOfBoundsException();
        }
        this.notifications[i + 1].add(errorBundle);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x02b9  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x02da  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x02a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void checkCRLs(java.security.cert.PKIXParameters r18, java.security.cert.X509Certificate r19, java.util.Date r20, java.security.cert.X509Certificate r21, java.security.PublicKey r22, java.util.Vector r23, int r24) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r22
            r5 = r24
            org.bouncycastle.x509.X509CRLStoreSelector r6 = new org.bouncycastle.x509.X509CRLStoreSelector
            r6.<init>()
            javax.security.auth.x500.X500Principal r7 = getEncodedIssuerPrincipal(r19)     // Catch:{ IOException -> 0x04b7 }
            byte[] r7 = r7.getEncoded()     // Catch:{ IOException -> 0x04b7 }
            r6.addIssuerName(r7)     // Catch:{ IOException -> 0x04b7 }
            r6.setCertificateChecking(r3)
            r7 = 3
            r9 = 0
            org.bouncycastle.jce.provider.PKIXCRLUtil r11 = CRL_UTIL     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.Set r11 = r11.findCRLs(r6, r2)     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.Iterator r12 = r11.iterator()     // Catch:{ AnnotatedException -> 0x0084 }
            boolean r11 = r11.isEmpty()     // Catch:{ AnnotatedException -> 0x0084 }
            if (r11 == 0) goto L_0x00be
            org.bouncycastle.jce.provider.PKIXCRLUtil r11 = CRL_UTIL     // Catch:{ AnnotatedException -> 0x0084 }
            org.bouncycastle.x509.X509CRLStoreSelector r13 = new org.bouncycastle.x509.X509CRLStoreSelector     // Catch:{ AnnotatedException -> 0x0084 }
            r13.<init>()     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.Set r11 = r11.findCRLs(r13, r2)     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ AnnotatedException -> 0x0084 }
            r13.<init>()     // Catch:{ AnnotatedException -> 0x0084 }
        L_0x0043:
            boolean r14 = r11.hasNext()     // Catch:{ AnnotatedException -> 0x0084 }
            if (r14 == 0) goto L_0x0057
            java.lang.Object r14 = r11.next()     // Catch:{ AnnotatedException -> 0x0084 }
            java.security.cert.X509CRL r14 = (java.security.cert.X509CRL) r14     // Catch:{ AnnotatedException -> 0x0084 }
            javax.security.auth.x500.X500Principal r14 = r14.getIssuerX500Principal()     // Catch:{ AnnotatedException -> 0x0084 }
            r13.add(r14)     // Catch:{ AnnotatedException -> 0x0084 }
            goto L_0x0043
        L_0x0057:
            int r11 = r13.size()     // Catch:{ AnnotatedException -> 0x0084 }
            org.bouncycastle.i18n.ErrorBundle r14 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ AnnotatedException -> 0x0084 }
            java.lang.String r15 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r8 = "CertPathReviewer.noCrlInCertstore"
            java.lang.Object[] r10 = new java.lang.Object[r7]     // Catch:{ AnnotatedException -> 0x0084 }
            org.bouncycastle.i18n.filter.UntrustedInput r7 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ AnnotatedException -> 0x0084 }
            java.util.Collection r6 = r6.getIssuerNames()     // Catch:{ AnnotatedException -> 0x0084 }
            r7.<init>(r6)     // Catch:{ AnnotatedException -> 0x0084 }
            r10[r9] = r7     // Catch:{ AnnotatedException -> 0x0084 }
            org.bouncycastle.i18n.filter.UntrustedInput r6 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ AnnotatedException -> 0x0084 }
            r6.<init>(r13)     // Catch:{ AnnotatedException -> 0x0084 }
            r7 = 1
            r10[r7] = r6     // Catch:{ AnnotatedException -> 0x0084 }
            java.lang.Integer r6 = org.bouncycastle.util.Integers.valueOf(r11)     // Catch:{ AnnotatedException -> 0x0084 }
            r7 = 2
            r10[r7] = r6     // Catch:{ AnnotatedException -> 0x0084 }
            r14.<init>(r15, r8, r10)     // Catch:{ AnnotatedException -> 0x0084 }
            r1.addNotification(r14, r5)     // Catch:{ AnnotatedException -> 0x0084 }
            goto L_0x00be
        L_0x0084:
            r0 = move-exception
            r6 = r0
            org.bouncycastle.i18n.ErrorBundle r7 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r8 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r10 = "CertPathReviewer.crlExtractionError"
            r11 = 3
            java.lang.Object[] r12 = new java.lang.Object[r11]
            java.lang.Throwable r11 = r6.getCause()
            java.lang.String r11 = r11.getMessage()
            r12[r9] = r11
            java.lang.Throwable r11 = r6.getCause()
            r13 = 1
            r12[r13] = r11
            java.lang.Throwable r6 = r6.getCause()
            java.lang.Class r6 = r6.getClass()
            java.lang.String r6 = r6.getName()
            r11 = 2
            r12[r11] = r6
            r7.<init>(r8, r10, r12)
            r1.addError(r7, r5)
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.Iterator r12 = r6.iterator()
        L_0x00be:
            r7 = 0
        L_0x00bf:
            boolean r8 = r12.hasNext()
            if (r8 == 0) goto L_0x012f
            java.lang.Object r7 = r12.next()
            java.security.cert.X509CRL r7 = (java.security.cert.X509CRL) r7
            java.util.Date r8 = r7.getNextUpdate()
            if (r8 == 0) goto L_0x0107
            java.util.Date r8 = r18.getDate()
            java.util.Date r10 = r7.getNextUpdate()
            boolean r8 = r8.before(r10)
            if (r8 == 0) goto L_0x00e0
            goto L_0x0107
        L_0x00e0:
            org.bouncycastle.i18n.ErrorBundle r8 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r10 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r11 = "CertPathReviewer.localInvalidCRL"
            r13 = 2
            java.lang.Object[] r14 = new java.lang.Object[r13]
            org.bouncycastle.i18n.filter.TrustedInput r13 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r15 = r7.getThisUpdate()
            r13.<init>(r15)
            r14[r9] = r13
            org.bouncycastle.i18n.filter.TrustedInput r13 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r15 = r7.getNextUpdate()
            r13.<init>(r15)
            r15 = 1
            r14[r15] = r13
            r8.<init>(r10, r11, r14)
            r1.addNotification(r8, r5)
            goto L_0x00bf
        L_0x0107:
            org.bouncycastle.i18n.ErrorBundle r8 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r10 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r11 = "CertPathReviewer.localValidCRL"
            r12 = 2
            java.lang.Object[] r13 = new java.lang.Object[r12]
            org.bouncycastle.i18n.filter.TrustedInput r12 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r14 = r7.getThisUpdate()
            r12.<init>(r14)
            r13[r9] = r12
            org.bouncycastle.i18n.filter.TrustedInput r12 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r14 = r7.getNextUpdate()
            r12.<init>(r14)
            r14 = 1
            r13[r14] = r12
            r8.<init>(r10, r11, r13)
            r1.addNotification(r8, r5)
            r10 = 1
            goto L_0x0130
        L_0x012f:
            r10 = 0
        L_0x0130:
            if (r10 != 0) goto L_0x0234
            java.util.Iterator r8 = r23.iterator()
        L_0x0136:
            boolean r11 = r8.hasNext()
            if (r11 == 0) goto L_0x0234
            java.lang.Object r11 = r8.next()     // Catch:{ CertPathReviewerException -> 0x0223 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ CertPathReviewerException -> 0x0223 }
            java.security.cert.X509CRL r12 = r1.a(r11)     // Catch:{ CertPathReviewerException -> 0x0223 }
            if (r12 == 0) goto L_0x018d
            javax.security.auth.x500.X500Principal r13 = r19.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x0223 }
            javax.security.auth.x500.X500Principal r14 = r12.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x0223 }
            boolean r13 = r13.equals(r14)     // Catch:{ CertPathReviewerException -> 0x0223 }
            if (r13 != 0) goto L_0x0199
            org.bouncycastle.i18n.ErrorBundle r13 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x0192 }
            java.lang.String r14 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r15 = "CertPathReviewer.onlineCRLWrongCA"
            r6 = 3
            java.lang.Object[] r9 = new java.lang.Object[r6]     // Catch:{ CertPathReviewerException -> 0x0192 }
            org.bouncycastle.i18n.filter.UntrustedInput r6 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ CertPathReviewerException -> 0x0192 }
            javax.security.auth.x500.X500Principal r12 = r12.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x0192 }
            java.lang.String r12 = r12.getName()     // Catch:{ CertPathReviewerException -> 0x0192 }
            r6.<init>(r12)     // Catch:{ CertPathReviewerException -> 0x0192 }
            r12 = 0
            r9[r12] = r6     // Catch:{ CertPathReviewerException -> 0x0192 }
            org.bouncycastle.i18n.filter.UntrustedInput r6 = new org.bouncycastle.i18n.filter.UntrustedInput     // Catch:{ CertPathReviewerException -> 0x0192 }
            javax.security.auth.x500.X500Principal r12 = r19.getIssuerX500Principal()     // Catch:{ CertPathReviewerException -> 0x0192 }
            java.lang.String r12 = r12.getName()     // Catch:{ CertPathReviewerException -> 0x0192 }
            r6.<init>(r12)     // Catch:{ CertPathReviewerException -> 0x0192 }
            r12 = 1
            r9[r12] = r6     // Catch:{ CertPathReviewerException -> 0x0192 }
            org.bouncycastle.i18n.filter.UntrustedUrlInput r6 = new org.bouncycastle.i18n.filter.UntrustedUrlInput     // Catch:{ CertPathReviewerException -> 0x0192 }
            r6.<init>(r11)     // Catch:{ CertPathReviewerException -> 0x0192 }
            r11 = 2
            r9[r11] = r6     // Catch:{ CertPathReviewerException -> 0x0192 }
            r13.<init>(r14, r15, r9)     // Catch:{ CertPathReviewerException -> 0x0192 }
            r1.addNotification(r13, r5)     // Catch:{ CertPathReviewerException -> 0x0192 }
        L_0x018d:
            r16 = r7
        L_0x018f:
            r13 = 3
            goto L_0x022f
        L_0x0192:
            r0 = move-exception
            r6 = r0
            r16 = r7
        L_0x0196:
            r13 = 3
            goto L_0x0228
        L_0x0199:
            java.util.Date r6 = r12.getNextUpdate()     // Catch:{ CertPathReviewerException -> 0x0223 }
            if (r6 == 0) goto L_0x01e9
            java.security.cert.PKIXParameters r6 = r1.pkixParams     // Catch:{ CertPathReviewerException -> 0x01e4 }
            java.util.Date r6 = r6.getDate()     // Catch:{ CertPathReviewerException -> 0x01e4 }
            java.util.Date r9 = r12.getNextUpdate()     // Catch:{ CertPathReviewerException -> 0x01e4 }
            boolean r6 = r6.before(r9)     // Catch:{ CertPathReviewerException -> 0x01e4 }
            if (r6 == 0) goto L_0x01b0
            goto L_0x01e9
        L_0x01b0:
            org.bouncycastle.i18n.ErrorBundle r6 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x01e4 }
            java.lang.String r9 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r13 = "CertPathReviewer.onlineInvalidCRL"
            r14 = 3
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ CertPathReviewerException -> 0x01e4 }
            org.bouncycastle.i18n.filter.TrustedInput r14 = new org.bouncycastle.i18n.filter.TrustedInput     // Catch:{ CertPathReviewerException -> 0x01e4 }
            r16 = r7
            java.util.Date r7 = r12.getThisUpdate()     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r14.<init>(r7)     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r7 = 0
            r15[r7] = r14     // Catch:{ CertPathReviewerException -> 0x01e2 }
            org.bouncycastle.i18n.filter.TrustedInput r7 = new org.bouncycastle.i18n.filter.TrustedInput     // Catch:{ CertPathReviewerException -> 0x01e2 }
            java.util.Date r12 = r12.getNextUpdate()     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r7.<init>(r12)     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r12 = 1
            r15[r12] = r7     // Catch:{ CertPathReviewerException -> 0x01e2 }
            org.bouncycastle.i18n.filter.UntrustedUrlInput r7 = new org.bouncycastle.i18n.filter.UntrustedUrlInput     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r7.<init>(r11)     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r11 = 2
            r15[r11] = r7     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r6.<init>(r9, r13, r15)     // Catch:{ CertPathReviewerException -> 0x01e2 }
            r1.addNotification(r6, r5)     // Catch:{ CertPathReviewerException -> 0x01e2 }
            goto L_0x018f
        L_0x01e2:
            r0 = move-exception
            goto L_0x01e7
        L_0x01e4:
            r0 = move-exception
            r16 = r7
        L_0x01e7:
            r6 = r0
            goto L_0x0196
        L_0x01e9:
            r16 = r7
            org.bouncycastle.i18n.ErrorBundle r6 = new org.bouncycastle.i18n.ErrorBundle     // Catch:{ CertPathReviewerException -> 0x021e }
            java.lang.String r7 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r9 = "CertPathReviewer.onlineValidCRL"
            r13 = 3
            java.lang.Object[] r10 = new java.lang.Object[r13]     // Catch:{ CertPathReviewerException -> 0x021c }
            org.bouncycastle.i18n.filter.TrustedInput r14 = new org.bouncycastle.i18n.filter.TrustedInput     // Catch:{ CertPathReviewerException -> 0x021c }
            java.util.Date r15 = r12.getThisUpdate()     // Catch:{ CertPathReviewerException -> 0x021c }
            r14.<init>(r15)     // Catch:{ CertPathReviewerException -> 0x021c }
            r15 = 0
            r10[r15] = r14     // Catch:{ CertPathReviewerException -> 0x021c }
            org.bouncycastle.i18n.filter.TrustedInput r14 = new org.bouncycastle.i18n.filter.TrustedInput     // Catch:{ CertPathReviewerException -> 0x021c }
            java.util.Date r15 = r12.getNextUpdate()     // Catch:{ CertPathReviewerException -> 0x021c }
            r14.<init>(r15)     // Catch:{ CertPathReviewerException -> 0x021c }
            r15 = 1
            r10[r15] = r14     // Catch:{ CertPathReviewerException -> 0x021c }
            org.bouncycastle.i18n.filter.UntrustedUrlInput r14 = new org.bouncycastle.i18n.filter.UntrustedUrlInput     // Catch:{ CertPathReviewerException -> 0x021c }
            r14.<init>(r11)     // Catch:{ CertPathReviewerException -> 0x021c }
            r11 = 2
            r10[r11] = r14     // Catch:{ CertPathReviewerException -> 0x021c }
            r6.<init>(r7, r9, r10)     // Catch:{ CertPathReviewerException -> 0x021c }
            r1.addNotification(r6, r5)     // Catch:{ CertPathReviewerException -> 0x021c }
            r10 = 1
            goto L_0x0238
        L_0x021c:
            r0 = move-exception
            goto L_0x0220
        L_0x021e:
            r0 = move-exception
            r13 = 3
        L_0x0220:
            r6 = r0
            r10 = 1
            goto L_0x0228
        L_0x0223:
            r0 = move-exception
            r16 = r7
            r13 = 3
            r6 = r0
        L_0x0228:
            org.bouncycastle.i18n.ErrorBundle r6 = r6.getErrorMessage()
            r1.addNotification(r6, r5)
        L_0x022f:
            r7 = r16
            r9 = 0
            goto L_0x0136
        L_0x0234:
            r16 = r7
            r12 = r16
        L_0x0238:
            if (r12 == 0) goto L_0x04a5
            r6 = 7
            if (r21 == 0) goto L_0x025a
            boolean[] r7 = r21.getKeyUsage()
            if (r7 == 0) goto L_0x025a
            int r8 = r7.length
            if (r8 < r6) goto L_0x024b
            r8 = 6
            boolean r7 = r7[r8]
            if (r7 != 0) goto L_0x025a
        L_0x024b:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.noCrlSigningPermited"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x025a:
            if (r4 == 0) goto L_0x0496
            java.lang.String r7 = "BC"
            r12.verify(r4, r7)     // Catch:{ Exception -> 0x0485 }
            java.math.BigInteger r4 = r19.getSerialNumber()
            java.security.cert.X509CRLEntry r4 = r12.getRevokedCertificate(r4)
            if (r4 == 0) goto L_0x02f9
            boolean r7 = r4.hasExtensions()
            if (r7 == 0) goto L_0x029f
            org.bouncycastle.asn1.ASN1ObjectIdentifier r7 = org.bouncycastle.asn1.x509.X509Extensions.ReasonCode     // Catch:{ AnnotatedException -> 0x028e }
            java.lang.String r7 = r7.getId()     // Catch:{ AnnotatedException -> 0x028e }
            org.bouncycastle.asn1.ASN1Primitive r7 = getExtensionValue(r4, r7)     // Catch:{ AnnotatedException -> 0x028e }
            org.bouncycastle.asn1.ASN1Enumerated r7 = org.bouncycastle.asn1.ASN1Enumerated.getInstance(r7)     // Catch:{ AnnotatedException -> 0x028e }
            if (r7 == 0) goto L_0x029f
            java.lang.String[] r8 = crlReasons
            java.math.BigInteger r7 = r7.getValue()
            int r7 = r7.intValue()
            r7 = r8[r7]
            goto L_0x02a0
        L_0x028e:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlReasonExtError"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x029f:
            r7 = 0
        L_0x02a0:
            if (r7 != 0) goto L_0x02a6
            java.lang.String[] r7 = crlReasons
            r7 = r7[r6]
        L_0x02a6:
            org.bouncycastle.i18n.LocaleString r6 = new org.bouncycastle.i18n.LocaleString
            java.lang.String r8 = "org.bouncycastle.x509.CertPathReviewerMessages"
            r6.<init>(r8, r7)
            java.util.Date r7 = r4.getRevocationDate()
            r8 = r20
            boolean r7 = r8.before(r7)
            if (r7 != 0) goto L_0x02da
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.certRevoked"
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            org.bouncycastle.i18n.filter.TrustedInput r8 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r4 = r4.getRevocationDate()
            r8.<init>(r4)
            r4 = 0
            r7[r4] = r8
            r4 = 1
            r7[r4] = r6
            r2.<init>(r3, r5, r7)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x02da:
            org.bouncycastle.i18n.ErrorBundle r7 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r8 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r9 = "CertPathReviewer.revokedAfterValidation"
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]
            org.bouncycastle.i18n.filter.TrustedInput r13 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r4 = r4.getRevocationDate()
            r13.<init>(r4)
            r4 = 0
            r11[r4] = r13
            r4 = 1
            r11[r4] = r6
            r7.<init>(r8, r9, r11)
            r1.addNotification(r7, r5)
            goto L_0x0305
        L_0x02f9:
            org.bouncycastle.i18n.ErrorBundle r4 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r6 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r7 = "CertPathReviewer.notRevoked"
            r4.<init>(r6, r7)
            r1.addNotification(r4, r5)
        L_0x0305:
            java.util.Date r4 = r12.getNextUpdate()
            if (r4 == 0) goto L_0x0337
            java.util.Date r4 = r12.getNextUpdate()
            java.security.cert.PKIXParameters r6 = r1.pkixParams
            java.util.Date r6 = r6.getDate()
            boolean r4 = r4.before(r6)
            if (r4 == 0) goto L_0x0337
            org.bouncycastle.i18n.ErrorBundle r4 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r6 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r7 = "CertPathReviewer.crlUpdateAvailable"
            r8 = 1
            java.lang.Object[] r9 = new java.lang.Object[r8]
            org.bouncycastle.i18n.filter.TrustedInput r11 = new org.bouncycastle.i18n.filter.TrustedInput
            java.util.Date r13 = r12.getNextUpdate()
            r11.<init>(r13)
            r13 = 0
            r9[r13] = r11
            r4.<init>(r6, r7, r9)
            r1.addNotification(r4, r5)
            goto L_0x0339
        L_0x0337:
            r8 = 1
            r13 = 0
        L_0x0339:
            java.lang.String r4 = ISSUING_DISTRIBUTION_POINT     // Catch:{ AnnotatedException -> 0x0476 }
            org.bouncycastle.asn1.ASN1Primitive r4 = getExtensionValue(r12, r4)     // Catch:{ AnnotatedException -> 0x0476 }
            java.lang.String r5 = DELTA_CRL_INDICATOR     // Catch:{ AnnotatedException -> 0x0467 }
            org.bouncycastle.asn1.ASN1Primitive r5 = getExtensionValue(r12, r5)     // Catch:{ AnnotatedException -> 0x0467 }
            if (r5 == 0) goto L_0x03f7
            org.bouncycastle.x509.X509CRLStoreSelector r6 = new org.bouncycastle.x509.X509CRLStoreSelector
            r6.<init>()
            javax.security.auth.x500.X500Principal r7 = getIssuerPrincipal(r12)     // Catch:{ IOException -> 0x03e6 }
            byte[] r7 = r7.getEncoded()     // Catch:{ IOException -> 0x03e6 }
            r6.addIssuerName(r7)     // Catch:{ IOException -> 0x03e6 }
            org.bouncycastle.asn1.ASN1Integer r5 = (org.bouncycastle.asn1.ASN1Integer) r5
            java.math.BigInteger r5 = r5.getPositiveValue()
            r6.setMinCRLNumber(r5)
            java.lang.String r5 = CRL_NUMBER     // Catch:{ AnnotatedException -> 0x03d5 }
            org.bouncycastle.asn1.ASN1Primitive r5 = getExtensionValue(r12, r5)     // Catch:{ AnnotatedException -> 0x03d5 }
            org.bouncycastle.asn1.ASN1Integer r5 = (org.bouncycastle.asn1.ASN1Integer) r5     // Catch:{ AnnotatedException -> 0x03d5 }
            java.math.BigInteger r5 = r5.getPositiveValue()     // Catch:{ AnnotatedException -> 0x03d5 }
            r11 = 1
            java.math.BigInteger r7 = java.math.BigInteger.valueOf(r11)     // Catch:{ AnnotatedException -> 0x03d5 }
            java.math.BigInteger r5 = r5.subtract(r7)     // Catch:{ AnnotatedException -> 0x03d5 }
            r6.setMaxCRLNumber(r5)     // Catch:{ AnnotatedException -> 0x03d5 }
            org.bouncycastle.jce.provider.PKIXCRLUtil r5 = CRL_UTIL     // Catch:{ AnnotatedException -> 0x03c4 }
            java.util.Set r2 = r5.findCRLs(r6, r2)     // Catch:{ AnnotatedException -> 0x03c4 }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ AnnotatedException -> 0x03c4 }
        L_0x0383:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x03b2
            java.lang.Object r5 = r2.next()
            java.security.cert.X509CRL r5 = (java.security.cert.X509CRL) r5
            java.lang.String r6 = ISSUING_DISTRIBUTION_POINT     // Catch:{ AnnotatedException -> 0x03a1 }
            org.bouncycastle.asn1.ASN1Primitive r5 = getExtensionValue(r5, r6)     // Catch:{ AnnotatedException -> 0x03a1 }
            if (r4 != 0) goto L_0x039a
            if (r5 != 0) goto L_0x0383
            goto L_0x03b3
        L_0x039a:
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x0383
            goto L_0x03b3
        L_0x03a1:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.distrPtExtError"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x03b2:
            r8 = 0
        L_0x03b3:
            if (r8 != 0) goto L_0x03f7
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.noBaseCRL"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x03c4:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlExtractionError"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x03d5:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlNbrExtError"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x03e6:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlIssuerException"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x03f7:
            if (r4 == 0) goto L_0x04a5
            org.bouncycastle.asn1.x509.IssuingDistributionPoint r2 = org.bouncycastle.asn1.x509.IssuingDistributionPoint.getInstance(r4)
            java.lang.String r4 = BASIC_CONSTRAINTS     // Catch:{ AnnotatedException -> 0x0456 }
            org.bouncycastle.asn1.ASN1Primitive r3 = getExtensionValue(r3, r4)     // Catch:{ AnnotatedException -> 0x0456 }
            org.bouncycastle.asn1.x509.BasicConstraints r3 = org.bouncycastle.asn1.x509.BasicConstraints.getInstance(r3)     // Catch:{ AnnotatedException -> 0x0456 }
            boolean r4 = r2.onlyContainsUserCerts()
            if (r4 == 0) goto L_0x0424
            if (r3 == 0) goto L_0x0424
            boolean r4 = r3.isCA()
            if (r4 == 0) goto L_0x0424
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.crlOnlyUserCert"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x0424:
            boolean r4 = r2.onlyContainsCACerts()
            if (r4 == 0) goto L_0x0441
            if (r3 == 0) goto L_0x0432
            boolean r3 = r3.isCA()
            if (r3 != 0) goto L_0x0441
        L_0x0432:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.crlOnlyCaCert"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x0441:
            boolean r2 = r2.onlyContainsAttributeCerts()
            if (r2 == 0) goto L_0x04a5
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.crlOnlyAttrCert"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x0456:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlBCExtError"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x0467:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.deltaCrlExtError"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x0476:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.distrPtExtError"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x0485:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlVerifyFailed"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        L_0x0496:
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.crlNoIssuerPublicKey"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x04a5:
            if (r10 != 0) goto L_0x04b6
            org.bouncycastle.i18n.ErrorBundle r2 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r3 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r4 = "CertPathReviewer.noValidCrlFound"
            r2.<init>(r3, r4)
            org.bouncycastle.x509.CertPathReviewerException r3 = new org.bouncycastle.x509.CertPathReviewerException
            r3.<init>(r2)
            throw r3
        L_0x04b6:
            return
        L_0x04b7:
            r0 = move-exception
            r2 = r0
            org.bouncycastle.i18n.ErrorBundle r3 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r4 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r5 = "CertPathReviewer.crlIssuerException"
            r3.<init>(r4, r5)
            org.bouncycastle.x509.CertPathReviewerException r4 = new org.bouncycastle.x509.CertPathReviewerException
            r4.<init>(r3, r2)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.PKIXCertPathReviewer.checkCRLs(java.security.cert.PKIXParameters, java.security.cert.X509Certificate, java.util.Date, java.security.cert.X509Certificate, java.security.PublicKey, java.util.Vector, int):void");
    }

    /* access modifiers changed from: protected */
    public void checkRevocation(PKIXParameters pKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, Vector vector, Vector vector2, int i) {
        checkCRLs(pKIXParameters, x509Certificate, date, x509Certificate2, publicKey, vector, i);
    }

    /* access modifiers changed from: protected */
    public void doChecks() {
        if (!this.d) {
            throw new IllegalStateException("Object not initialized. Call init() first.");
        } else if (this.notifications == null) {
            this.notifications = new List[(this.n + 1)];
            this.errors = new List[(this.n + 1)];
            for (int i = 0; i < this.notifications.length; i++) {
                this.notifications[i] = new ArrayList();
                this.errors[i] = new ArrayList();
            }
            c();
            a();
            b();
            d();
            e();
        }
    }

    /* access modifiers changed from: protected */
    public Vector getCRLDistUrls(CRLDistPoint cRLDistPoint) {
        Vector vector = new Vector();
        if (cRLDistPoint != null) {
            DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
            for (DistributionPoint distributionPoint : distributionPoints) {
                DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2.getType() == 0) {
                    GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].getTagNo() == 6) {
                            vector.add(((DERIA5String) names[i].getName()).getString());
                        }
                    }
                }
            }
        }
        return vector;
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getCertPathSize() {
        return this.n;
    }

    public List getErrors(int i) {
        doChecks();
        return this.errors[i + 1];
    }

    public List[] getErrors() {
        doChecks();
        return this.errors;
    }

    public List getNotifications(int i) {
        doChecks();
        return this.notifications[i + 1];
    }

    public List[] getNotifications() {
        doChecks();
        return this.notifications;
    }

    /* access modifiers changed from: protected */
    public Vector getOCSPUrls(AuthorityInformationAccess authorityInformationAccess) {
        Vector vector = new Vector();
        if (authorityInformationAccess != null) {
            AccessDescription[] accessDescriptions = authorityInformationAccess.getAccessDescriptions();
            for (int i = 0; i < accessDescriptions.length; i++) {
                if (accessDescriptions[i].getAccessMethod().equals(AccessDescription.id_ad_ocsp)) {
                    GeneralName accessLocation = accessDescriptions[i].getAccessLocation();
                    if (accessLocation.getTagNo() == 6) {
                        vector.add(((DERIA5String) accessLocation.getName()).getString());
                    }
                }
            }
        }
        return vector;
    }

    public PolicyNode getPolicyTree() {
        doChecks();
        return this.policyTree;
    }

    public PublicKey getSubjectPublicKey() {
        doChecks();
        return this.subjectPublicKey;
    }

    public TrustAnchor getTrustAnchor() {
        doChecks();
        return this.trustAnchor;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.security.cert.TrustAnchor>, for r8v0, types: [java.util.Set<java.security.cert.TrustAnchor>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection getTrustAnchors(java.security.cert.X509Certificate r7, java.util.Set<java.security.cert.TrustAnchor> r8) {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r8 = r8.iterator()
            java.security.cert.X509CertSelector r1 = new java.security.cert.X509CertSelector
            r1.<init>()
            javax.security.auth.x500.X500Principal r2 = getEncodedIssuerPrincipal(r7)     // Catch:{ IOException -> 0x0091 }
            byte[] r2 = r2.getEncoded()     // Catch:{ IOException -> 0x0091 }
            r1.setSubject(r2)     // Catch:{ IOException -> 0x0091 }
            org.bouncycastle.asn1.ASN1ObjectIdentifier r2 = org.bouncycastle.asn1.x509.X509Extensions.AuthorityKeyIdentifier     // Catch:{ IOException -> 0x0091 }
            java.lang.String r2 = r2.getId()     // Catch:{ IOException -> 0x0091 }
            byte[] r2 = r7.getExtensionValue(r2)     // Catch:{ IOException -> 0x0091 }
            if (r2 == 0) goto L_0x0050
            org.bouncycastle.asn1.ASN1Primitive r2 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r2)     // Catch:{ IOException -> 0x0091 }
            org.bouncycastle.asn1.ASN1OctetString r2 = (org.bouncycastle.asn1.ASN1OctetString) r2     // Catch:{ IOException -> 0x0091 }
            byte[] r2 = r2.getOctets()     // Catch:{ IOException -> 0x0091 }
            org.bouncycastle.asn1.ASN1Primitive r2 = org.bouncycastle.asn1.ASN1Primitive.fromByteArray(r2)     // Catch:{ IOException -> 0x0091 }
            org.bouncycastle.asn1.x509.AuthorityKeyIdentifier r2 = org.bouncycastle.asn1.x509.AuthorityKeyIdentifier.getInstance(r2)     // Catch:{ IOException -> 0x0091 }
            java.math.BigInteger r3 = r2.getAuthorityCertSerialNumber()     // Catch:{ IOException -> 0x0091 }
            r1.setSerialNumber(r3)     // Catch:{ IOException -> 0x0091 }
            byte[] r2 = r2.getKeyIdentifier()     // Catch:{ IOException -> 0x0091 }
            if (r2 == 0) goto L_0x0050
            org.bouncycastle.asn1.DEROctetString r3 = new org.bouncycastle.asn1.DEROctetString     // Catch:{ IOException -> 0x0091 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0091 }
            byte[] r2 = r3.getEncoded()     // Catch:{ IOException -> 0x0091 }
            r1.setSubjectKeyIdentifier(r2)     // Catch:{ IOException -> 0x0091 }
        L_0x0050:
            boolean r2 = r8.hasNext()
            if (r2 == 0) goto L_0x0090
            java.lang.Object r2 = r8.next()
            java.security.cert.TrustAnchor r2 = (java.security.cert.TrustAnchor) r2
            java.security.cert.X509Certificate r3 = r2.getTrustedCert()
            if (r3 == 0) goto L_0x0070
            java.security.cert.X509Certificate r3 = r2.getTrustedCert()
            boolean r3 = r1.match(r3)
            if (r3 == 0) goto L_0x0050
        L_0x006c:
            r0.add(r2)
            goto L_0x0050
        L_0x0070:
            java.lang.String r3 = r2.getCAName()
            if (r3 == 0) goto L_0x0050
            java.security.PublicKey r3 = r2.getCAPublicKey()
            if (r3 == 0) goto L_0x0050
            javax.security.auth.x500.X500Principal r3 = getEncodedIssuerPrincipal(r7)
            javax.security.auth.x500.X500Principal r4 = new javax.security.auth.x500.X500Principal
            java.lang.String r5 = r2.getCAName()
            r4.<init>(r5)
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0050
            goto L_0x006c
        L_0x0090:
            return r0
        L_0x0091:
            org.bouncycastle.i18n.ErrorBundle r7 = new org.bouncycastle.i18n.ErrorBundle
            java.lang.String r8 = "org.bouncycastle.x509.CertPathReviewerMessages"
            java.lang.String r0 = "CertPathReviewer.trustAnchorIssuerError"
            r7.<init>(r8, r0)
            org.bouncycastle.x509.CertPathReviewerException r8 = new org.bouncycastle.x509.CertPathReviewerException
            r8.<init>(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.PKIXCertPathReviewer.getTrustAnchors(java.security.cert.X509Certificate, java.util.Set):java.util.Collection");
    }

    public void init(CertPath certPath2, PKIXParameters pKIXParameters) {
        if (this.d) {
            throw new IllegalStateException("object is already initialized!");
        }
        this.d = true;
        if (certPath2 == null) {
            throw new NullPointerException("certPath was null");
        }
        this.certPath = certPath2;
        this.certs = certPath2.getCertificates();
        this.n = this.certs.size();
        if (this.certs.isEmpty()) {
            throw new CertPathReviewerException(new ErrorBundle("org.bouncycastle.x509.CertPathReviewerMessages", "CertPathReviewer.emptyCertPath"));
        }
        this.pkixParams = (PKIXParameters) pKIXParameters.clone();
        this.validDate = getValidDate(this.pkixParams);
        this.notifications = null;
        this.errors = null;
        this.trustAnchor = null;
        this.subjectPublicKey = null;
        this.policyTree = null;
    }

    public boolean isValidCertPath() {
        doChecks();
        for (List isEmpty : this.errors) {
            if (!isEmpty.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
