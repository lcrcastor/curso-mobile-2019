package org.bouncycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.TargetInformation;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.PKIXAttrCertChecker;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CertStoreSelector;

class RFC3281CertPathUtilities {
    private static final String a = X509Extensions.TargetInformation.getId();
    private static final String b = X509Extensions.NoRevAvail.getId();
    private static final String c = X509Extensions.CRLDistributionPoints.getId();
    private static final String d = X509Extensions.AuthorityInfoAccess.getId();

    RFC3281CertPathUtilities() {
    }

    protected static CertPathValidatorResult a(CertPath certPath, ExtendedPKIXParameters extendedPKIXParameters) {
        try {
            try {
                return CertPathValidator.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).validate(certPath, extendedPKIXParameters);
            } catch (CertPathValidatorException e) {
                throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.getMessage());
            }
        } catch (NoSuchProviderException e3) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e3);
        } catch (NoSuchAlgorithmException e4) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e4);
        }
    }

    protected static void a(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) {
        boolean z = false;
        for (TrustAnchor trustAnchor : extendedPKIXParameters.getTrustedACIssuers()) {
            if (x509Certificate.getSubjectX500Principal().getName("RFC2253").equals(trustAnchor.getCAName()) || x509Certificate.equals(trustAnchor.getTrustedCert())) {
                z = true;
            }
        }
        if (!z) {
            throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
        }
    }

    private static void a(DistributionPoint distributionPoint, X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters, Date date, X509Certificate x509Certificate, CertStatus certStatus, ReasonsMask reasonsMask, List list) {
        Iterator it;
        X509CRL x509crl;
        DistributionPoint distributionPoint2 = distributionPoint;
        X509AttributeCertificate x509AttributeCertificate2 = x509AttributeCertificate;
        ExtendedPKIXParameters extendedPKIXParameters2 = extendedPKIXParameters;
        Date date2 = date;
        CertStatus certStatus2 = certStatus;
        ReasonsMask reasonsMask2 = reasonsMask;
        if (x509AttributeCertificate2.getExtensionValue(X509Extensions.NoRevAvail.getId()) == null) {
            Date date3 = new Date(System.currentTimeMillis());
            if (date.getTime() > date3.getTime()) {
                throw new AnnotatedException("Validation time is in future.");
            }
            Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(distributionPoint2, x509AttributeCertificate2, date3, extendedPKIXParameters2).iterator();
            Throwable th = null;
            boolean z = false;
            while (it2.hasNext() && certStatus.b() == 11 && !reasonsMask.a()) {
                try {
                    X509CRL x509crl2 = (X509CRL) it2.next();
                    ReasonsMask processCRLD = RFC3280CertPathUtilities.processCRLD(x509crl2, distributionPoint2);
                    if (!processCRLD.c(reasonsMask2)) {
                        continue;
                    } else {
                        ReasonsMask reasonsMask3 = processCRLD;
                        X509CRL x509crl3 = x509crl2;
                        it = it2;
                        try {
                            PublicKey processCRLG = RFC3280CertPathUtilities.processCRLG(x509crl3, RFC3280CertPathUtilities.processCRLF(x509crl2, x509AttributeCertificate2, null, null, extendedPKIXParameters2, list));
                            if (extendedPKIXParameters.isUseDeltasEnabled()) {
                                try {
                                    x509crl = RFC3280CertPathUtilities.processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date3, extendedPKIXParameters2, x509crl3), processCRLG);
                                } catch (AnnotatedException e) {
                                    th = e;
                                    it2 = it;
                                }
                            } else {
                                x509crl = null;
                            }
                            if (extendedPKIXParameters.getValidityModel() != 1) {
                                try {
                                    if (x509AttributeCertificate.getNotAfter().getTime() < x509crl3.getThisUpdate().getTime()) {
                                        throw new AnnotatedException("No valid CRL for current time found.");
                                    }
                                } catch (AnnotatedException e2) {
                                    e = e2;
                                    th = e;
                                    it2 = it;
                                }
                            }
                            RFC3280CertPathUtilities.processCRLB1(distributionPoint2, x509AttributeCertificate2, x509crl3);
                            RFC3280CertPathUtilities.processCRLB2(distributionPoint2, x509AttributeCertificate2, x509crl3);
                            RFC3280CertPathUtilities.processCRLC(x509crl, x509crl3, extendedPKIXParameters2);
                            RFC3280CertPathUtilities.processCRLI(date2, x509crl, x509AttributeCertificate2, certStatus2, extendedPKIXParameters2);
                            RFC3280CertPathUtilities.processCRLJ(date2, x509crl3, x509AttributeCertificate2, certStatus2);
                            if (certStatus.b() == 8) {
                                certStatus2.a(11);
                            }
                            reasonsMask2.a(reasonsMask3);
                            it2 = it;
                            z = true;
                        } catch (AnnotatedException e3) {
                            e = e3;
                            th = e;
                            it2 = it;
                        }
                    }
                } catch (AnnotatedException e4) {
                    e = e4;
                    it = it2;
                    th = e;
                    it2 = it;
                }
            }
            if (!z) {
                throw th;
            }
        }
    }

    protected static void a(X509AttributeCertificate x509AttributeCertificate, CertPath certPath, CertPath certPath2, ExtendedPKIXParameters extendedPKIXParameters) {
        Set criticalExtensionOIDs = x509AttributeCertificate.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs.contains(a)) {
            try {
                TargetInformation.getInstance(CertPathValidatorUtilities.getExtensionValue(x509AttributeCertificate, a));
            } catch (AnnotatedException e) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e);
            } catch (IllegalArgumentException e2) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e2);
            }
        }
        criticalExtensionOIDs.remove(a);
        for (PKIXAttrCertChecker check : extendedPKIXParameters.getAttrCertCheckers()) {
            check.check(x509AttributeCertificate, certPath, certPath2, criticalExtensionOIDs);
        }
        if (!criticalExtensionOIDs.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Attribute certificate contains unsupported critical extensions: ");
            sb.append(criticalExtensionOIDs);
            throw new CertPathValidatorException(sb.toString());
        }
    }

    protected static void a(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) {
        for (String str : extendedPKIXParameters.getProhibitedACAttributes()) {
            if (x509AttributeCertificate.getAttributes(str) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Attribute certificate contains prohibited attribute: ");
                sb.append(str);
                sb.append(".");
                throw new CertPathValidatorException(sb.toString());
            }
        }
        for (String str2 : extendedPKIXParameters.getNecessaryACAttributes()) {
            if (x509AttributeCertificate.getAttributes(str2) == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Attribute certificate does not contain necessary attribute: ");
                sb2.append(str2);
                sb2.append(".");
                throw new CertPathValidatorException(sb2.toString());
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00fc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void a(org.bouncycastle.x509.X509AttributeCertificate r20, org.bouncycastle.x509.ExtendedPKIXParameters r21, java.security.cert.X509Certificate r22, java.util.Date r23, java.util.List r24) {
        /*
            r9 = r20
            boolean r1 = r21.isRevocationEnabled()
            if (r1 == 0) goto L_0x0183
            java.lang.String r1 = b
            byte[] r1 = r9.getExtensionValue(r1)
            if (r1 != 0) goto L_0x016b
            java.lang.String r1 = c     // Catch:{ AnnotatedException -> 0x0161 }
            org.bouncycastle.asn1.ASN1Primitive r1 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r9, r1)     // Catch:{ AnnotatedException -> 0x0161 }
            org.bouncycastle.asn1.x509.CRLDistPoint r1 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r1)     // Catch:{ AnnotatedException -> 0x0161 }
            r10 = r21
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(r1, r10)     // Catch:{ AnnotatedException -> 0x0158 }
            org.bouncycastle.jce.provider.CertStatus r11 = new org.bouncycastle.jce.provider.CertStatus
            r11.<init>()
            org.bouncycastle.jce.provider.ReasonsMask r12 = new org.bouncycastle.jce.provider.ReasonsMask
            r12.<init>()
            r14 = 0
            r15 = 11
            r8 = 0
            if (r1 == 0) goto L_0x0083
            org.bouncycastle.asn1.x509.DistributionPoint[] r7 = r1.getDistributionPoints()     // Catch:{ Exception -> 0x007a }
            r6 = 0
            r16 = 0
        L_0x0036:
            int r1 = r7.length     // Catch:{ AnnotatedException -> 0x006d }
            if (r6 >= r1) goto L_0x0069
            int r1 = r11.b()     // Catch:{ AnnotatedException -> 0x006d }
            if (r1 != r15) goto L_0x0069
            boolean r1 = r12.a()     // Catch:{ AnnotatedException -> 0x006d }
            if (r1 != 0) goto L_0x0069
            java.lang.Object r1 = r21.clone()     // Catch:{ AnnotatedException -> 0x006d }
            r3 = r1
            org.bouncycastle.x509.ExtendedPKIXParameters r3 = (org.bouncycastle.x509.ExtendedPKIXParameters) r3     // Catch:{ AnnotatedException -> 0x006d }
            r1 = r7[r6]     // Catch:{ AnnotatedException -> 0x006d }
            r2 = r9
            r4 = r23
            r5 = r22
            r17 = r6
            r6 = r11
            r18 = r7
            r7 = r12
            r13 = 0
            r8 = r24
            a(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ AnnotatedException -> 0x0067 }
            int r6 = r17 + 1
            r7 = r18
            r8 = 0
            r16 = 1
            goto L_0x0036
        L_0x0067:
            r0 = move-exception
            goto L_0x006f
        L_0x0069:
            r13 = 0
            r17 = r14
            goto L_0x0088
        L_0x006d:
            r0 = move-exception
            r13 = 0
        L_0x006f:
            r1 = r0
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "No valid CRL for distribution point found."
            r2.<init>(r3, r1)
            r17 = r2
            goto L_0x0088
        L_0x007a:
            r0 = move-exception
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r1 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r2 = "Distribution points could not be read."
            r1.<init>(r2, r0)
            throw r1
        L_0x0083:
            r13 = 0
            r17 = r14
            r16 = 0
        L_0x0088:
            int r1 = r11.b()
            if (r1 != r15) goto L_0x00f0
            boolean r1 = r12.a()
            if (r1 != 0) goto L_0x00f0
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x00de }
            org.bouncycastle.x509.AttributeCertificateIssuer r2 = r20.getIssuer()     // Catch:{ Exception -> 0x00de }
            java.security.Principal[] r2 = r2.getPrincipals()     // Catch:{ Exception -> 0x00de }
            r2 = r2[r13]     // Catch:{ Exception -> 0x00de }
            javax.security.auth.x500.X500Principal r2 = (javax.security.auth.x500.X500Principal) r2     // Catch:{ Exception -> 0x00de }
            byte[] r2 = r2.getEncoded()     // Catch:{ Exception -> 0x00de }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00de }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()     // Catch:{ Exception -> 0x00de }
            org.bouncycastle.asn1.x509.DistributionPoint r2 = new org.bouncycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x00db }
            org.bouncycastle.asn1.x509.DistributionPointName r3 = new org.bouncycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x00db }
            org.bouncycastle.asn1.x509.GeneralNames r4 = new org.bouncycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x00db }
            org.bouncycastle.asn1.x509.GeneralName r5 = new org.bouncycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x00db }
            r6 = 4
            r5.<init>(r6, r1)     // Catch:{ AnnotatedException -> 0x00db }
            r4.<init>(r5)     // Catch:{ AnnotatedException -> 0x00db }
            r3.<init>(r13, r4)     // Catch:{ AnnotatedException -> 0x00db }
            r2.<init>(r3, r14, r14)     // Catch:{ AnnotatedException -> 0x00db }
            java.lang.Object r1 = r21.clone()     // Catch:{ AnnotatedException -> 0x00db }
            r3 = r1
            org.bouncycastle.x509.ExtendedPKIXParameters r3 = (org.bouncycastle.x509.ExtendedPKIXParameters) r3     // Catch:{ AnnotatedException -> 0x00db }
            r1 = r2
            r2 = r9
            r4 = r23
            r5 = r22
            r6 = r11
            r7 = r12
            r8 = r24
            a(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ AnnotatedException -> 0x00db }
            r2 = r17
            r16 = 1
            goto L_0x00f2
        L_0x00db:
            r0 = move-exception
            r1 = r0
            goto L_0x00e8
        L_0x00de:
            r0 = move-exception
            r1 = r0
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch:{ AnnotatedException -> 0x00db }
            java.lang.String r3 = "Issuer from certificate for CRL could not be reencoded."
            r2.<init>(r3, r1)     // Catch:{ AnnotatedException -> 0x00db }
            throw r2     // Catch:{ AnnotatedException -> 0x00db }
        L_0x00e8:
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "No valid CRL for distribution point found."
            r2.<init>(r3, r1)
            goto L_0x00f2
        L_0x00f0:
            r2 = r17
        L_0x00f2:
            if (r16 != 0) goto L_0x00fc
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r1 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r3 = "No valid CRL found."
            r1.<init>(r3, r2)
            throw r1
        L_0x00fc:
            int r1 = r11.b()
            if (r1 == r15) goto L_0x0139
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Attribute certificate revocation after "
            r1.append(r2)
            java.util.Date r2 = r11.a()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            java.lang.String r1 = ", reason: "
            r2.append(r1)
            java.lang.String[] r1 = org.bouncycastle.jce.provider.RFC3280CertPathUtilities.crlReasons
            int r3 = r11.b()
            r1 = r1[r3]
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            java.security.cert.CertPathValidatorException r2 = new java.security.cert.CertPathValidatorException
            r2.<init>(r1)
            throw r2
        L_0x0139:
            boolean r1 = r12.a()
            r2 = 12
            if (r1 != 0) goto L_0x014a
            int r1 = r11.b()
            if (r1 != r15) goto L_0x014a
            r11.a(r2)
        L_0x014a:
            int r1 = r11.b()
            if (r1 != r2) goto L_0x0183
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r2 = "Attribute certificate status could not be determined."
            r1.<init>(r2)
            throw r1
        L_0x0158:
            r0 = move-exception
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r2 = "No additional CRL locations could be decoded from CRL distribution point extension."
            r1.<init>(r2, r0)
            throw r1
        L_0x0161:
            r0 = move-exception
            r1 = r0
            java.security.cert.CertPathValidatorException r2 = new java.security.cert.CertPathValidatorException
            java.lang.String r3 = "CRL distribution point extension could not be read."
            r2.<init>(r3, r1)
            throw r2
        L_0x016b:
            java.lang.String r1 = c
            byte[] r1 = r9.getExtensionValue(r1)
            if (r1 != 0) goto L_0x017b
            java.lang.String r1 = d
            byte[] r1 = r9.getExtensionValue(r1)
            if (r1 == 0) goto L_0x0183
        L_0x017b:
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r2 = "No rev avail extension is set, but also an AC revocation pointer."
            r1.<init>(r2)
            throw r1
        L_0x0183:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3281CertPathUtilities.a(org.bouncycastle.x509.X509AttributeCertificate, org.bouncycastle.x509.ExtendedPKIXParameters, java.security.cert.X509Certificate, java.util.Date, java.util.List):void");
    }

    protected static void b(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) {
        if (x509Certificate.getKeyUsage() != null && !x509Certificate.getKeyUsage()[0] && !x509Certificate.getKeyUsage()[1]) {
            throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
        } else if (x509Certificate.getBasicConstraints() != -1) {
            throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
        }
    }

    protected static void b(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) {
        try {
            x509AttributeCertificate.checkValidity(CertPathValidatorUtilities.getValidDate(extendedPKIXParameters));
        } catch (CertificateExpiredException e) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e);
        } catch (CertificateNotYetValidException e2) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e2);
        }
    }

    protected static CertPath c(X509AttributeCertificate x509AttributeCertificate, ExtendedPKIXParameters extendedPKIXParameters) {
        HashSet<X509Certificate> hashSet = new HashSet<>();
        int i = 0;
        if (x509AttributeCertificate.getHolder().getIssuer() != null) {
            X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
            x509CertStoreSelector.setSerialNumber(x509AttributeCertificate.getHolder().getSerialNumber());
            Principal[] issuer = x509AttributeCertificate.getHolder().getIssuer();
            int i2 = 0;
            while (i2 < issuer.length) {
                try {
                    if (issuer[i2] instanceof X500Principal) {
                        x509CertStoreSelector.setIssuer(((X500Principal) issuer[i2]).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXParameters.getStores()));
                    i2++;
                } catch (AnnotatedException e) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e);
                } catch (IOException e2) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e2);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
            }
        }
        if (x509AttributeCertificate.getHolder().getEntityNames() != null) {
            X509CertStoreSelector x509CertStoreSelector2 = new X509CertStoreSelector();
            Principal[] entityNames = x509AttributeCertificate.getHolder().getEntityNames();
            while (i < entityNames.length) {
                try {
                    if (entityNames[i] instanceof X500Principal) {
                        x509CertStoreSelector2.setIssuer(((X500Principal) entityNames[i]).getEncoded());
                    }
                    hashSet.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector2, extendedPKIXParameters.getStores()));
                    i++;
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e3);
                } catch (IOException e4) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e4);
                }
            }
            if (hashSet.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
            }
        }
        ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) ExtendedPKIXBuilderParameters.getInstance(extendedPKIXParameters);
        Throwable th = null;
        CertPathBuilderResult certPathBuilderResult = null;
        for (X509Certificate certificate : hashSet) {
            X509CertStoreSelector x509CertStoreSelector3 = new X509CertStoreSelector();
            x509CertStoreSelector3.setCertificate(certificate);
            extendedPKIXBuilderParameters.setTargetConstraints(x509CertStoreSelector3);
            try {
                try {
                    certPathBuilderResult = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).build(ExtendedPKIXBuilderParameters.getInstance(extendedPKIXBuilderParameters));
                } catch (CertPathBuilderException e5) {
                    th = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e5);
                } catch (InvalidAlgorithmParameterException e6) {
                    throw new RuntimeException(e6.getMessage());
                }
            } catch (NoSuchProviderException e7) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e7);
            } catch (NoSuchAlgorithmException e8) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e8);
            }
        }
        if (th == null) {
            return certPathBuilderResult.getCertPath();
        }
        throw th;
    }
}
