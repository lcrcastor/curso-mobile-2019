package org.bouncycastle.jce.provider;

import android.support.v4.os.EnvironmentCompat;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralSubtree;
import org.bouncycastle.asn1.x509.IssuingDistributionPoint;
import org.bouncycastle.asn1.x509.NameConstraints;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509CertStoreSelector;

public class RFC3280CertPathUtilities {
    public static final String ANY_POLICY = "2.5.29.32.0";
    public static final String AUTHORITY_KEY_IDENTIFIER = X509Extensions.AuthorityKeyIdentifier.getId();
    public static final String BASIC_CONSTRAINTS = X509Extensions.BasicConstraints.getId();
    public static final String CERTIFICATE_POLICIES = X509Extensions.CertificatePolicies.getId();
    public static final String CRL_DISTRIBUTION_POINTS = X509Extensions.CRLDistributionPoints.getId();
    public static final String CRL_NUMBER = X509Extensions.CRLNumber.getId();
    protected static final int CRL_SIGN = 6;
    public static final String DELTA_CRL_INDICATOR = X509Extensions.DeltaCRLIndicator.getId();
    public static final String FRESHEST_CRL = X509Extensions.FreshestCRL.getId();
    public static final String INHIBIT_ANY_POLICY = X509Extensions.InhibitAnyPolicy.getId();
    public static final String ISSUING_DISTRIBUTION_POINT = X509Extensions.IssuingDistributionPoint.getId();
    protected static final int KEY_CERT_SIGN = 5;
    public static final String KEY_USAGE = X509Extensions.KeyUsage.getId();
    public static final String NAME_CONSTRAINTS = X509Extensions.NameConstraints.getId();
    public static final String POLICY_CONSTRAINTS = X509Extensions.PolicyConstraints.getId();
    public static final String POLICY_MAPPINGS = X509Extensions.PolicyMappings.getId();
    public static final String SUBJECT_ALTERNATIVE_NAME = X509Extensions.SubjectAlternativeName.getId();
    private static final PKIXCRLUtil a = new PKIXCRLUtil();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    private static void a(DistributionPoint distributionPoint, ExtendedPKIXParameters extendedPKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, CertStatus certStatus, ReasonsMask reasonsMask, List list) {
        Iterator it;
        X509CRL x509crl;
        DistributionPoint distributionPoint2 = distributionPoint;
        ExtendedPKIXParameters extendedPKIXParameters2 = extendedPKIXParameters;
        X509Certificate x509Certificate3 = x509Certificate;
        Date date2 = date;
        CertStatus certStatus2 = certStatus;
        ReasonsMask reasonsMask2 = reasonsMask;
        Date date3 = new Date(System.currentTimeMillis());
        if (date.getTime() > date3.getTime()) {
            throw new AnnotatedException("Validation time is in future.");
        }
        Iterator it2 = CertPathValidatorUtilities.getCompleteCRLs(distributionPoint2, x509Certificate3, date3, extendedPKIXParameters2).iterator();
        Throwable th = null;
        boolean z = false;
        while (it2.hasNext() && certStatus.b() == 11 && !reasonsMask.a()) {
            try {
                X509CRL x509crl2 = (X509CRL) it2.next();
                ReasonsMask processCRLD = processCRLD(x509crl2, distributionPoint2);
                if (!processCRLD.c(reasonsMask2)) {
                    continue;
                } else {
                    ReasonsMask reasonsMask3 = processCRLD;
                    X509CRL x509crl3 = x509crl2;
                    it = it2;
                    try {
                        PublicKey processCRLG = processCRLG(x509crl3, processCRLF(x509crl2, x509Certificate3, x509Certificate2, publicKey, extendedPKIXParameters2, list));
                        if (extendedPKIXParameters.isUseDeltasEnabled()) {
                            try {
                                x509crl = processCRLH(CertPathValidatorUtilities.getDeltaCRLs(date3, extendedPKIXParameters2, x509crl3), processCRLG);
                            } catch (AnnotatedException e) {
                                th = e;
                                it2 = it;
                            }
                        } else {
                            x509crl = null;
                        }
                        if (extendedPKIXParameters.getValidityModel() != 1) {
                            try {
                                if (x509Certificate.getNotAfter().getTime() < x509crl3.getThisUpdate().getTime()) {
                                    throw new AnnotatedException("No valid CRL for current time found.");
                                }
                            } catch (AnnotatedException e2) {
                                e = e2;
                                th = e;
                                it2 = it;
                            }
                        }
                        processCRLB1(distributionPoint2, x509Certificate3, x509crl3);
                        processCRLB2(distributionPoint2, x509Certificate3, x509crl3);
                        processCRLC(x509crl, x509crl3, extendedPKIXParameters2);
                        processCRLI(date2, x509crl, x509Certificate3, certStatus2, extendedPKIXParameters2);
                        processCRLJ(date2, x509crl3, x509Certificate3, certStatus2);
                        if (certStatus.b() == 8) {
                            certStatus2.a(11);
                        }
                        reasonsMask2.a(reasonsMask3);
                        Set criticalExtensionOIDs = x509crl3.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs != null) {
                            HashSet hashSet = new HashSet(criticalExtensionOIDs);
                            hashSet.remove(X509Extensions.IssuingDistributionPoint.getId());
                            hashSet.remove(X509Extensions.DeltaCRLIndicator.getId());
                            if (!hashSet.isEmpty()) {
                                throw new AnnotatedException("CRL contains unsupported critical extensions.");
                            }
                        }
                        if (x509crl != null) {
                            Set criticalExtensionOIDs2 = x509crl.getCriticalExtensionOIDs();
                            if (criticalExtensionOIDs2 != null) {
                                HashSet hashSet2 = new HashSet(criticalExtensionOIDs2);
                                hashSet2.remove(X509Extensions.IssuingDistributionPoint.getId());
                                hashSet2.remove(X509Extensions.DeltaCRLIndicator.getId());
                                if (!hashSet2.isEmpty()) {
                                    throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
                                }
                            }
                        }
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

    /* JADX WARNING: Removed duplicated region for block: B:49:0x00db  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00e8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void checkCRLs(org.bouncycastle.x509.ExtendedPKIXParameters r20, java.security.cert.X509Certificate r21, java.util.Date r22, java.security.cert.X509Certificate r23, java.security.PublicKey r24, java.util.List r25) {
        /*
            java.lang.String r1 = CRL_DISTRIBUTION_POINTS     // Catch:{ Exception -> 0x0162 }
            r11 = r21
            org.bouncycastle.asn1.ASN1Primitive r1 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r11, r1)     // Catch:{ Exception -> 0x0162 }
            org.bouncycastle.asn1.x509.CRLDistPoint r1 = org.bouncycastle.asn1.x509.CRLDistPoint.getInstance(r1)     // Catch:{ Exception -> 0x0162 }
            r12 = r20
            org.bouncycastle.jce.provider.CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(r1, r12)     // Catch:{ AnnotatedException -> 0x0159 }
            org.bouncycastle.jce.provider.CertStatus r13 = new org.bouncycastle.jce.provider.CertStatus
            r13.<init>()
            org.bouncycastle.jce.provider.ReasonsMask r14 = new org.bouncycastle.jce.provider.ReasonsMask
            r14.<init>()
            r10 = 0
            r9 = 0
            r8 = 11
            if (r1 == 0) goto L_0x0075
            org.bouncycastle.asn1.x509.DistributionPoint[] r1 = r1.getDistributionPoints()     // Catch:{ Exception -> 0x006c }
            if (r1 == 0) goto L_0x0075
            r16 = r9
            r7 = 0
            r17 = 0
        L_0x002c:
            int r2 = r1.length
            if (r7 >= r2) goto L_0x0069
            int r2 = r13.b()
            if (r2 != r8) goto L_0x0069
            boolean r2 = r14.a()
            if (r2 != 0) goto L_0x0069
            java.lang.Object r2 = r20.clone()
            r3 = r2
            org.bouncycastle.x509.ExtendedPKIXParameters r3 = (org.bouncycastle.x509.ExtendedPKIXParameters) r3
            r2 = r1[r7]     // Catch:{ AnnotatedException -> 0x005b }
            r4 = r11
            r5 = r22
            r6 = r23
            r18 = r7
            r7 = r24
            r15 = 11
            r8 = r13
            r9 = r14
            r10 = r25
            a(r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ AnnotatedException -> 0x0059 }
            r17 = 1
            goto L_0x0062
        L_0x0059:
            r0 = move-exception
            goto L_0x0060
        L_0x005b:
            r0 = move-exception
            r18 = r7
            r15 = 11
        L_0x0060:
            r16 = r0
        L_0x0062:
            int r7 = r18 + 1
            r8 = 11
            r9 = 0
            r10 = 0
            goto L_0x002c
        L_0x0069:
            r15 = 11
            goto L_0x007b
        L_0x006c:
            r0 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "Distribution points could not be read."
            r1.<init>(r2, r0)
            throw r1
        L_0x0075:
            r15 = 11
            r16 = 0
            r17 = 0
        L_0x007b:
            int r1 = r13.b()
            if (r1 != r15) goto L_0x00d7
            boolean r1 = r14.a()
            if (r1 != 0) goto L_0x00d7
            org.bouncycastle.asn1.ASN1InputStream r1 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x00cd }
            javax.security.auth.x500.X500Principal r2 = org.bouncycastle.jce.provider.CertPathValidatorUtilities.getEncodedIssuerPrincipal(r21)     // Catch:{ Exception -> 0x00cd }
            byte[] r2 = r2.getEncoded()     // Catch:{ Exception -> 0x00cd }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00cd }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.readObject()     // Catch:{ Exception -> 0x00cd }
            org.bouncycastle.asn1.x509.DistributionPoint r2 = new org.bouncycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x00c9 }
            org.bouncycastle.asn1.x509.DistributionPointName r3 = new org.bouncycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x00c9 }
            org.bouncycastle.asn1.x509.GeneralNames r4 = new org.bouncycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x00c9 }
            org.bouncycastle.asn1.x509.GeneralName r5 = new org.bouncycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x00c9 }
            r6 = 4
            r5.<init>(r6, r1)     // Catch:{ AnnotatedException -> 0x00c9 }
            r4.<init>(r5)     // Catch:{ AnnotatedException -> 0x00c9 }
            r1 = 0
            r3.<init>(r1, r4)     // Catch:{ AnnotatedException -> 0x00c9 }
            r1 = 0
            r2.<init>(r3, r1, r1)     // Catch:{ AnnotatedException -> 0x00c9 }
            java.lang.Object r1 = r20.clone()     // Catch:{ AnnotatedException -> 0x00c9 }
            r3 = r1
            org.bouncycastle.x509.ExtendedPKIXParameters r3 = (org.bouncycastle.x509.ExtendedPKIXParameters) r3     // Catch:{ AnnotatedException -> 0x00c9 }
            r4 = r11
            r5 = r22
            r6 = r23
            r7 = r24
            r8 = r13
            r9 = r14
            r10 = r25
            a(r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ AnnotatedException -> 0x00c9 }
            r1 = r16
            r17 = 1
            goto L_0x00d9
        L_0x00c9:
            r0 = move-exception
            r16 = r0
            goto L_0x00d7
        L_0x00cd:
            r0 = move-exception
            r1 = r0
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException     // Catch:{ AnnotatedException -> 0x00c9 }
            java.lang.String r3 = "Issuer from certificate for CRL could not be reencoded."
            r2.<init>(r3, r1)     // Catch:{ AnnotatedException -> 0x00c9 }
            throw r2     // Catch:{ AnnotatedException -> 0x00c9 }
        L_0x00d7:
            r1 = r16
        L_0x00d9:
            if (r17 != 0) goto L_0x00e8
            boolean r2 = r1 instanceof org.bouncycastle.jce.provider.AnnotatedException
            if (r2 == 0) goto L_0x00e0
            throw r1
        L_0x00e0:
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "No valid CRL found."
            r2.<init>(r3, r1)
            throw r2
        L_0x00e8:
            int r1 = r13.b()
            if (r1 == r15) goto L_0x0139
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.lang.String r2 = "yyyy-MM-dd HH:mm:ss Z"
            r1.<init>(r2)
            java.lang.String r2 = "UTC"
            java.util.TimeZone r2 = java.util.TimeZone.getTimeZone(r2)
            r1.setTimeZone(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Certificate revocation after "
            r2.append(r3)
            java.util.Date r3 = r13.a()
            java.lang.String r1 = r1.format(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            java.lang.String r1 = ", reason: "
            r2.append(r1)
            java.lang.String[] r1 = crlReasons
            int r3 = r13.b()
            r1 = r1[r3]
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            r2.<init>(r1)
            throw r2
        L_0x0139:
            boolean r1 = r14.a()
            r2 = 12
            if (r1 != 0) goto L_0x014a
            int r1 = r13.b()
            if (r1 != r15) goto L_0x014a
            r13.a(r2)
        L_0x014a:
            int r1 = r13.b()
            if (r1 != r2) goto L_0x0158
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "Certificate status could not be determined."
            r1.<init>(r2)
            throw r1
        L_0x0158:
            return
        L_0x0159:
            r0 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r1 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "No additional CRL locations could be decoded from CRL distribution point extension."
            r1.<init>(r2, r0)
            throw r1
        L_0x0162:
            r0 = move-exception
            r1 = r0
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "CRL distribution point extension could not be read."
            r2.<init>(r3, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3280CertPathUtilities.checkCRLs(org.bouncycastle.x509.ExtendedPKIXParameters, java.security.cert.X509Certificate, java.util.Date, java.security.cert.X509Certificate, java.security.PublicKey, java.util.List):void");
    }

    protected static PKIXPolicyNode prepareCertB(CertPath certPath, int i, List[] listArr, PKIXPolicyNode pKIXPolicyNode, int i2) {
        HashMap hashMap;
        X509Certificate x509Certificate;
        Iterator it;
        boolean z;
        Set set;
        CertPath certPath2 = certPath;
        int i3 = i;
        List[] listArr2 = listArr;
        List certificates = certPath.getCertificates();
        X509Certificate x509Certificate2 = (X509Certificate) certificates.get(i3);
        int size = certificates.size() - i3;
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate2, POLICY_MAPPINGS));
            if (instance == null) {
                return pKIXPolicyNode;
            }
            HashMap hashMap2 = new HashMap();
            HashSet hashSet = new HashSet();
            for (int i4 = 0; i4 < instance.size(); i4++) {
                ASN1Sequence aSN1Sequence = (ASN1Sequence) instance.getObjectAt(i4);
                String id2 = ((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0)).getId();
                String id3 = ((ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(1)).getId();
                if (!hashMap2.containsKey(id2)) {
                    HashSet hashSet2 = new HashSet();
                    hashSet2.add(id3);
                    hashMap2.put(id2, hashSet2);
                    hashSet.add(id2);
                } else {
                    ((Set) hashMap2.get(id2)).add(id3);
                }
            }
            Iterator it2 = hashSet.iterator();
            PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
            while (it2.hasNext()) {
                String str = (String) it2.next();
                if (i2 > 0) {
                    Iterator it3 = listArr2[size].iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z = false;
                            break;
                        }
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) it3.next();
                        if (pKIXPolicyNode3.getValidPolicy().equals(str)) {
                            pKIXPolicyNode3.expectedPolicies = (Set) hashMap2.get(str);
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        Iterator it4 = listArr2[size].iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                break;
                            }
                            PKIXPolicyNode pKIXPolicyNode4 = (PKIXPolicyNode) it4.next();
                            if (ANY_POLICY.equals(pKIXPolicyNode4.getValidPolicy())) {
                                try {
                                    Enumeration objects = ((ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate2, CERTIFICATE_POLICIES)).getObjects();
                                    while (true) {
                                        if (!objects.hasMoreElements()) {
                                            set = null;
                                            break;
                                        }
                                        try {
                                            PolicyInformation instance2 = PolicyInformation.getInstance(objects.nextElement());
                                            if (ANY_POLICY.equals(instance2.getPolicyIdentifier().getId())) {
                                                try {
                                                    set = CertPathValidatorUtilities.getQualifierSet(instance2.getPolicyQualifiers());
                                                    break;
                                                } catch (CertPathValidatorException e) {
                                                    throw new ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", e, certPath2, i3);
                                                }
                                            }
                                        } catch (Exception e2) {
                                            throw new CertPathValidatorException("Policy information could not be decoded.", e2, certPath2, i3);
                                        }
                                    }
                                    boolean contains = x509Certificate2.getCriticalExtensionOIDs() != null ? x509Certificate2.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES) : false;
                                    PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) pKIXPolicyNode4.getParent();
                                    if (ANY_POLICY.equals(pKIXPolicyNode5.getValidPolicy())) {
                                        x509Certificate = x509Certificate2;
                                        PKIXPolicyNode pKIXPolicyNode6 = r7;
                                        it = it2;
                                        hashMap = hashMap2;
                                        PKIXPolicyNode pKIXPolicyNode7 = pKIXPolicyNode5;
                                        PKIXPolicyNode pKIXPolicyNode8 = new PKIXPolicyNode(new ArrayList(), size, (Set) hashMap2.get(str), pKIXPolicyNode5, set, str, contains);
                                        pKIXPolicyNode7.addChild(pKIXPolicyNode6);
                                        listArr2[size].add(pKIXPolicyNode6);
                                    }
                                } catch (AnnotatedException e3) {
                                    throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", e3, certPath2, i3);
                                }
                            }
                        }
                    }
                    x509Certificate = x509Certificate2;
                    it = it2;
                    hashMap = hashMap2;
                } else {
                    x509Certificate = x509Certificate2;
                    String str2 = str;
                    it = it2;
                    hashMap = hashMap2;
                    if (i2 <= 0) {
                        Iterator it5 = listArr2[size].iterator();
                        while (it5.hasNext()) {
                            PKIXPolicyNode pKIXPolicyNode9 = (PKIXPolicyNode) it5.next();
                            String str3 = str2;
                            if (pKIXPolicyNode9.getValidPolicy().equals(str3)) {
                                ((PKIXPolicyNode) pKIXPolicyNode9.getParent()).removeChild(pKIXPolicyNode9);
                                it5.remove();
                                for (int i5 = size - 1; i5 >= 0; i5--) {
                                    List list = listArr2[i5];
                                    PKIXPolicyNode pKIXPolicyNode10 = pKIXPolicyNode2;
                                    for (int i6 = 0; i6 < list.size(); i6++) {
                                        PKIXPolicyNode pKIXPolicyNode11 = (PKIXPolicyNode) list.get(i6);
                                        if (!pKIXPolicyNode11.hasChildren()) {
                                            pKIXPolicyNode10 = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode10, listArr2, pKIXPolicyNode11);
                                            if (pKIXPolicyNode10 == null) {
                                                break;
                                            }
                                        }
                                    }
                                    pKIXPolicyNode2 = pKIXPolicyNode10;
                                }
                            }
                            str2 = str3;
                        }
                    }
                }
                it2 = it;
                x509Certificate2 = x509Certificate;
                hashMap2 = hashMap;
            }
            return pKIXPolicyNode2;
        } catch (AnnotatedException e4) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e4, certPath2, i3);
        }
    }

    protected static void prepareNextCertA(CertPath certPath, int i) {
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_MAPPINGS));
            if (instance != null) {
                int i2 = 0;
                while (i2 < instance.size()) {
                    try {
                        ASN1Sequence instance2 = DERSequence.getInstance(instance.getObjectAt(i2));
                        ASN1ObjectIdentifier instance3 = ASN1ObjectIdentifier.getInstance(instance2.getObjectAt(0));
                        ASN1ObjectIdentifier instance4 = ASN1ObjectIdentifier.getInstance(instance2.getObjectAt(1));
                        if (ANY_POLICY.equals(instance3.getId())) {
                            throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", null, certPath, i);
                        } else if (ANY_POLICY.equals(instance4.getId())) {
                            throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", null, certPath, i);
                        } else {
                            i2++;
                        }
                    } catch (Exception e) {
                        throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", e, certPath, i);
                    }
                }
            }
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", e2, certPath, i);
        }
    }

    protected static void prepareNextCertG(CertPath certPath, int i, PKIXNameConstraintValidator pKIXNameConstraintValidator) {
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), NAME_CONSTRAINTS));
            NameConstraints instance2 = instance != null ? NameConstraints.getInstance(instance) : null;
            if (instance2 != null) {
                GeneralSubtree[] permittedSubtrees = instance2.getPermittedSubtrees();
                if (permittedSubtrees != null) {
                    try {
                        pKIXNameConstraintValidator.intersectPermittedSubtree(permittedSubtrees);
                    } catch (Exception e) {
                        throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", e, certPath, i);
                    }
                }
                GeneralSubtree[] excludedSubtrees = instance2.getExcludedSubtrees();
                if (excludedSubtrees != null) {
                    int i2 = 0;
                    while (i2 != excludedSubtrees.length) {
                        try {
                            pKIXNameConstraintValidator.addExcludedSubtree(excludedSubtrees[i2]);
                            i2++;
                        } catch (Exception e2) {
                            throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", e2, certPath, i);
                        }
                    }
                }
            }
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", e3, certPath, i);
        }
    }

    protected static int prepareNextCertH1(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int prepareNextCertH2(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int prepareNextCertH3(CertPath certPath, int i, int i2) {
        return (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i)) || i2 == 0) ? i2 : i2 - 1;
    }

    protected static int prepareNextCertI1(CertPath certPath, int i, int i2) {
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (instance != null) {
                Enumeration objects = instance.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject instance2 = ASN1TaggedObject.getInstance(objects.nextElement());
                        if (instance2.getTagNo() == 0) {
                            int intValue = ASN1Integer.getInstance(instance2, false).getValue().intValue();
                            if (intValue < i2) {
                                return intValue;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e, certPath, i);
                    }
                }
            }
            return i2;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e2, certPath, i);
        }
    }

    protected static int prepareNextCertI2(CertPath certPath, int i, int i2) {
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (instance != null) {
                Enumeration objects = instance.getObjects();
                while (true) {
                    if (!objects.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject instance2 = ASN1TaggedObject.getInstance(objects.nextElement());
                        if (instance2.getTagNo() == 1) {
                            int intValue = ASN1Integer.getInstance(instance2, false).getValue().intValue();
                            if (intValue < i2) {
                                return intValue;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e, certPath, i);
                    }
                }
            }
            return i2;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e2, certPath, i);
        }
    }

    protected static int prepareNextCertJ(CertPath certPath, int i, int i2) {
        try {
            ASN1Integer instance = ASN1Integer.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), INHIBIT_ANY_POLICY));
            if (instance != null) {
                int intValue = instance.getValue().intValue();
                if (intValue < i2) {
                    return intValue;
                }
            }
            return i2;
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertK(CertPath certPath, int i) {
        try {
            BasicConstraints instance = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            if (instance == null) {
                throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
            } else if (!instance.isCA()) {
                throw new CertPathValidatorException("Not a CA certificate");
            }
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static int prepareNextCertL(CertPath certPath, int i, int i2) {
        if (CertPathValidatorUtilities.isSelfIssued((X509Certificate) certPath.getCertificates().get(i))) {
            return i2;
        }
        if (i2 > 0) {
            return i2 - 1;
        }
        throw new ExtCertPathValidatorException("Max path length not greater than zero", null, certPath, i);
    }

    protected static int prepareNextCertM(CertPath certPath, int i, int i2) {
        try {
            BasicConstraints instance = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), BASIC_CONSTRAINTS));
            if (instance != null) {
                BigInteger pathLenConstraint = instance.getPathLenConstraint();
                if (pathLenConstraint != null) {
                    int intValue = pathLenConstraint.intValue();
                    if (intValue < i2) {
                        return intValue;
                    }
                }
            }
            return i2;
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e, certPath, i);
        }
    }

    protected static void prepareNextCertN(CertPath certPath, int i) {
        boolean[] keyUsage = ((X509Certificate) certPath.getCertificates().get(i)).getKeyUsage();
        if (keyUsage != null && !keyUsage[5]) {
            throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", null, certPath, i);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.security.cert.PKIXCertPathChecker>, for r5v0, types: [java.util.List, java.util.List<java.security.cert.PKIXCertPathChecker>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void prepareNextCertO(java.security.cert.CertPath r2, int r3, java.util.Set r4, java.util.List<java.security.cert.PKIXCertPathChecker> r5) {
        /*
            java.util.List r0 = r2.getCertificates()
            java.lang.Object r0 = r0.get(r3)
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            java.util.Iterator r5 = r5.iterator()
        L_0x000e:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x002d
            java.lang.Object r1 = r5.next()     // Catch:{ CertPathValidatorException -> 0x001e }
            java.security.cert.PKIXCertPathChecker r1 = (java.security.cert.PKIXCertPathChecker) r1     // Catch:{ CertPathValidatorException -> 0x001e }
            r1.check(r0, r4)     // Catch:{ CertPathValidatorException -> 0x001e }
            goto L_0x000e
        L_0x001e:
            r4 = move-exception
            java.security.cert.CertPathValidatorException r5 = new java.security.cert.CertPathValidatorException
            java.lang.String r0 = r4.getMessage()
            java.lang.Throwable r4 = r4.getCause()
            r5.<init>(r0, r4, r2, r3)
            throw r5
        L_0x002d:
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L_0x004b
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r5 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Certificate has unsupported critical extension: "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r0 = 0
            r5.<init>(r4, r0, r2, r3)
            throw r5
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3280CertPathUtilities.prepareNextCertO(java.security.cert.CertPath, int, java.util.Set, java.util.List):void");
    }

    protected static Set processCRLA1i(Date date, ExtendedPKIXParameters extendedPKIXParameters, X509Certificate x509Certificate, X509CRL x509crl) {
        HashSet hashSet = new HashSet();
        if (extendedPKIXParameters.isUseDeltasEnabled()) {
            try {
                CRLDistPoint instance = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, FRESHEST_CRL));
                if (instance == null) {
                    try {
                        instance = CRLDistPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, FRESHEST_CRL));
                    } catch (AnnotatedException e) {
                        throw new AnnotatedException("Freshest CRL extension could not be decoded from CRL.", e);
                    }
                }
                if (instance != null) {
                    try {
                        CertPathValidatorUtilities.addAdditionalStoresFromCRLDistributionPoint(instance, extendedPKIXParameters);
                        try {
                            hashSet.addAll(CertPathValidatorUtilities.getDeltaCRLs(date, extendedPKIXParameters, x509crl));
                            return hashSet;
                        } catch (AnnotatedException e2) {
                            throw new AnnotatedException("Exception obtaining delta CRLs.", e2);
                        }
                    } catch (AnnotatedException e3) {
                        throw new AnnotatedException("No new delta CRL locations could be added from Freshest CRL extension.", e3);
                    }
                }
            } catch (AnnotatedException e4) {
                throw new AnnotatedException("Freshest CRL extension could not be decoded from certificate.", e4);
            }
        }
        return hashSet;
    }

    protected static Set[] processCRLA1ii(Date date, ExtendedPKIXParameters extendedPKIXParameters, X509Certificate x509Certificate, X509CRL x509crl) {
        HashSet hashSet = new HashSet();
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        x509CRLStoreSelector.setCertificateChecking(x509Certificate);
        try {
            x509CRLStoreSelector.addIssuerName(x509crl.getIssuerX500Principal().getEncoded());
            x509CRLStoreSelector.setCompleteCRLEnabled(true);
            Set findCRLs = a.findCRLs(x509CRLStoreSelector, extendedPKIXParameters, date);
            if (extendedPKIXParameters.isUseDeltasEnabled()) {
                try {
                    hashSet.addAll(CertPathValidatorUtilities.getDeltaCRLs(date, extendedPKIXParameters, x509crl));
                } catch (AnnotatedException e) {
                    throw new AnnotatedException("Exception obtaining delta CRLs.", e);
                }
            }
            return new Set[]{findCRLs, hashSet};
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot extract issuer from CRL.");
            sb.append(e2);
            throw new AnnotatedException(sb.toString(), e2);
        }
    }

    protected static void processCRLB1(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        boolean z;
        ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT);
        boolean z2 = extensionValue != null && IssuingDistributionPoint.getInstance(extensionValue).isIndirectCRL();
        byte[] encoded = CertPathValidatorUtilities.getIssuerPrincipal(x509crl).getEncoded();
        if (distributionPoint.getCRLIssuer() != null) {
            GeneralName[] names = distributionPoint.getCRLIssuer().getNames();
            z = false;
            for (int i = 0; i < names.length; i++) {
                if (names[i].getTagNo() == 4) {
                    try {
                        if (Arrays.areEqual(names[i].getName().toASN1Primitive().getEncoded(), encoded)) {
                            z = true;
                        }
                    } catch (IOException e) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e);
                    }
                }
            }
            if (z && !z2) {
                throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
            } else if (!z) {
                throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
            }
        } else {
            z = CertPathValidatorUtilities.getIssuerPrincipal(x509crl).equals(CertPathValidatorUtilities.getEncodedIssuerPrincipal(obj));
        }
        if (!z) {
            throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
        }
    }

    protected static void processCRLB2(DistributionPoint distributionPoint, Object obj, X509CRL x509crl) {
        GeneralName[] generalNameArr;
        try {
            IssuingDistributionPoint instance = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (instance != null) {
                if (instance.getDistributionPoint() != null) {
                    DistributionPointName distributionPoint2 = IssuingDistributionPoint.getInstance(instance).getDistributionPoint();
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    if (distributionPoint2.getType() == 0) {
                        GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                        for (GeneralName add : names) {
                            arrayList.add(add);
                        }
                    }
                    if (distributionPoint2.getType() == 1) {
                        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                        try {
                            Enumeration objects = ASN1Sequence.getInstance(ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getIssuerPrincipal(x509crl).getEncoded())).getObjects();
                            while (objects.hasMoreElements()) {
                                aSN1EncodableVector.add((ASN1Encodable) objects.nextElement());
                            }
                            aSN1EncodableVector.add(distributionPoint2.getName());
                            arrayList.add(new GeneralName(X509Name.getInstance(new DERSequence(aSN1EncodableVector))));
                        } catch (IOException e) {
                            throw new AnnotatedException("Could not read CRL issuer.", e);
                        }
                    }
                    if (distributionPoint.getDistributionPoint() != null) {
                        DistributionPointName distributionPoint3 = distributionPoint.getDistributionPoint();
                        GeneralName[] generalNameArr2 = null;
                        if (distributionPoint3.getType() == 0) {
                            generalNameArr2 = GeneralNames.getInstance(distributionPoint3.getName()).getNames();
                        }
                        if (distributionPoint3.getType() == 1) {
                            if (distributionPoint.getCRLIssuer() != null) {
                                generalNameArr = distributionPoint.getCRLIssuer().getNames();
                            } else {
                                generalNameArr = new GeneralName[1];
                                try {
                                    generalNameArr[0] = new GeneralName(new X509Name((ASN1Sequence) ASN1Sequence.fromByteArray(CertPathValidatorUtilities.getEncodedIssuerPrincipal(obj).getEncoded())));
                                } catch (IOException e2) {
                                    throw new AnnotatedException("Could not read certificate issuer.", e2);
                                }
                            }
                            generalNameArr2 = generalNameArr;
                            for (int i = 0; i < generalNameArr2.length; i++) {
                                Enumeration objects2 = ASN1Sequence.getInstance(generalNameArr2[i].getName().toASN1Primitive()).getObjects();
                                ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
                                while (objects2.hasMoreElements()) {
                                    aSN1EncodableVector2.add((ASN1Encodable) objects2.nextElement());
                                }
                                aSN1EncodableVector2.add(distributionPoint3.getName());
                                generalNameArr2[i] = new GeneralName(new X509Name((ASN1Sequence) new DERSequence(aSN1EncodableVector2)));
                            }
                        }
                        if (generalNameArr2 != null) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= generalNameArr2.length) {
                                    break;
                                } else if (arrayList.contains(generalNameArr2[i2])) {
                                    z = true;
                                    break;
                                } else {
                                    i2++;
                                }
                            }
                        }
                        if (!z) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    } else if (distributionPoint.getCRLIssuer() == null) {
                        throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
                    } else {
                        GeneralName[] names2 = distributionPoint.getCRLIssuer().getNames();
                        int i3 = 0;
                        while (true) {
                            if (i3 >= names2.length) {
                                break;
                            } else if (arrayList.contains(names2[i3])) {
                                z = true;
                                break;
                            } else {
                                i3++;
                            }
                        }
                        if (!z) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    }
                }
                try {
                    BasicConstraints instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Extension) obj, BASIC_CONSTRAINTS));
                    if (obj instanceof X509Certificate) {
                        if (instance.onlyContainsUserCerts() && instance2 != null && instance2.isCA()) {
                            throw new AnnotatedException("CA Cert CRL only contains user certificates.");
                        } else if (instance.onlyContainsCACerts() && (instance2 == null || !instance2.isCA())) {
                            throw new AnnotatedException("End CRL only contains CA certificates.");
                        }
                    }
                    if (instance.onlyContainsAttributeCerts()) {
                        throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
                    }
                } catch (Exception e3) {
                    throw new AnnotatedException("Basic constraints extension could not be decoded.", e3);
                }
            }
        } catch (Exception e4) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
        }
    }

    protected static void processCRLC(X509CRL x509crl, X509CRL x509crl2, ExtendedPKIXParameters extendedPKIXParameters) {
        if (x509crl != null) {
            try {
                IssuingDistributionPoint instance = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl2, ISSUING_DISTRIBUTION_POINT));
                if (extendedPKIXParameters.isUseDeltasEnabled()) {
                    if (!x509crl.getIssuerX500Principal().equals(x509crl2.getIssuerX500Principal())) {
                        throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
                    }
                    try {
                        IssuingDistributionPoint instance2 = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
                        boolean z = true;
                        if (instance != null ? !instance.equals(instance2) : instance2 != null) {
                            z = false;
                        }
                        if (!z) {
                            throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                        }
                        try {
                            ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(x509crl2, AUTHORITY_KEY_IDENTIFIER);
                            try {
                                ASN1Primitive extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509crl, AUTHORITY_KEY_IDENTIFIER);
                                if (extensionValue == null) {
                                    throw new AnnotatedException("CRL authority key identifier is null.");
                                } else if (extensionValue2 == null) {
                                    throw new AnnotatedException("Delta CRL authority key identifier is null.");
                                } else if (!extensionValue.equals(extensionValue2)) {
                                    throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                                }
                            } catch (AnnotatedException e) {
                                throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", e);
                            }
                        } catch (AnnotatedException e2) {
                            throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", e2);
                        }
                    } catch (Exception e3) {
                        throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", e3);
                    }
                }
            } catch (Exception e4) {
                throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e4);
            }
        }
    }

    protected static ReasonsMask processCRLD(X509CRL x509crl, DistributionPoint distributionPoint) {
        try {
            IssuingDistributionPoint instance = IssuingDistributionPoint.getInstance(CertPathValidatorUtilities.getExtensionValue(x509crl, ISSUING_DISTRIBUTION_POINT));
            if (instance != null && instance.getOnlySomeReasons() != null && distributionPoint.getReasons() != null) {
                return new ReasonsMask(distributionPoint.getReasons()).b(new ReasonsMask(instance.getOnlySomeReasons()));
            }
            if ((instance == null || instance.getOnlySomeReasons() == null) && distributionPoint.getReasons() == null) {
                return ReasonsMask.a;
            }
            return (distributionPoint.getReasons() == null ? ReasonsMask.a : new ReasonsMask(distributionPoint.getReasons())).b(instance == null ? ReasonsMask.a : new ReasonsMask(instance.getOnlySomeReasons()));
        } catch (Exception e) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e);
        }
    }

    protected static Set processCRLF(X509CRL x509crl, Object obj, X509Certificate x509Certificate, PublicKey publicKey, ExtendedPKIXParameters extendedPKIXParameters, List list) {
        int i;
        X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
        try {
            x509CertStoreSelector.setSubject(CertPathValidatorUtilities.getIssuerPrincipal(x509crl).getEncoded());
            try {
                Collection findCertificates = CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXParameters.getStores());
                findCertificates.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXParameters.getAdditionalStores()));
                findCertificates.addAll(CertPathValidatorUtilities.findCertificates(x509CertStoreSelector, extendedPKIXParameters.getCertStores()));
                findCertificates.add(x509Certificate);
                Iterator it = findCertificates.iterator();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    X509Certificate x509Certificate2 = (X509Certificate) it.next();
                    if (x509Certificate2.equals(x509Certificate)) {
                        arrayList.add(x509Certificate2);
                        arrayList2.add(publicKey);
                    } else {
                        try {
                            CertPathBuilder instance = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME);
                            X509CertStoreSelector x509CertStoreSelector2 = new X509CertStoreSelector();
                            x509CertStoreSelector2.setCertificate(x509Certificate2);
                            ExtendedPKIXParameters extendedPKIXParameters2 = (ExtendedPKIXParameters) extendedPKIXParameters.clone();
                            extendedPKIXParameters2.setTargetCertConstraints(x509CertStoreSelector2);
                            ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters = (ExtendedPKIXBuilderParameters) ExtendedPKIXBuilderParameters.getInstance(extendedPKIXParameters2);
                            if (list.contains(x509Certificate2)) {
                                extendedPKIXBuilderParameters.setRevocationEnabled(false);
                            } else {
                                extendedPKIXBuilderParameters.setRevocationEnabled(true);
                            }
                            List certificates = instance.build(extendedPKIXBuilderParameters).getCertPath().getCertificates();
                            arrayList.add(x509Certificate2);
                            arrayList2.add(CertPathValidatorUtilities.getNextWorkingKey(certificates, 0));
                        } catch (CertPathBuilderException e) {
                            throw new AnnotatedException("Internal error.", e);
                        } catch (CertPathValidatorException e2) {
                            throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e2);
                        } catch (Exception e3) {
                            throw new RuntimeException(e3.getMessage());
                        }
                    }
                }
                HashSet hashSet = new HashSet();
                AnnotatedException annotatedException = null;
                for (i = 0; i < arrayList.size(); i++) {
                    boolean[] keyUsage = ((X509Certificate) arrayList.get(i)).getKeyUsage();
                    if (keyUsage == null || (keyUsage.length >= 7 && keyUsage[6])) {
                        hashSet.add(arrayList2.get(i));
                    } else {
                        annotatedException = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                    }
                }
                if (hashSet.isEmpty() && annotatedException == null) {
                    throw new AnnotatedException("Cannot find a valid issuer certificate.");
                } else if (!hashSet.isEmpty() || annotatedException == null) {
                    return hashSet;
                } else {
                    throw annotatedException;
                }
            } catch (AnnotatedException e4) {
                throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", e4);
            }
        } catch (IOException e5) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", e5);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.security.PublicKey>, for r3v0, types: [java.util.Set<java.security.PublicKey>, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.security.PublicKey processCRLG(java.security.cert.X509CRL r2, java.util.Set<java.security.PublicKey> r3) {
        /*
            java.util.Iterator r3 = r3.iterator()
            r0 = 0
        L_0x0005:
            boolean r1 = r3.hasNext()
            if (r1 == 0) goto L_0x0017
            java.lang.Object r0 = r3.next()
            java.security.PublicKey r0 = (java.security.PublicKey) r0
            r2.verify(r0)     // Catch:{ Exception -> 0x0015 }
            return r0
        L_0x0015:
            r0 = move-exception
            goto L_0x0005
        L_0x0017:
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "Cannot verify CRL."
            r2.<init>(r3, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCRLG(java.security.cert.X509CRL, java.util.Set):java.security.PublicKey");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.security.cert.X509CRL>, for r3v0, types: [java.util.Set, java.util.Set<java.security.cert.X509CRL>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.security.cert.X509CRL processCRLH(java.util.Set<java.security.cert.X509CRL> r3, java.security.PublicKey r4) {
        /*
            java.util.Iterator r3 = r3.iterator()
            r0 = 0
            r1 = r0
        L_0x0006:
            boolean r2 = r3.hasNext()
            if (r2 == 0) goto L_0x0018
            java.lang.Object r1 = r3.next()
            java.security.cert.X509CRL r1 = (java.security.cert.X509CRL) r1
            r1.verify(r4)     // Catch:{ Exception -> 0x0016 }
            return r1
        L_0x0016:
            r1 = move-exception
            goto L_0x0006
        L_0x0018:
            if (r1 == 0) goto L_0x0022
            org.bouncycastle.jce.provider.AnnotatedException r3 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r4 = "Cannot verify delta CRL."
            r3.<init>(r4, r1)
            throw r3
        L_0x0022:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3280CertPathUtilities.processCRLH(java.util.Set, java.security.PublicKey):java.security.cert.X509CRL");
    }

    protected static void processCRLI(Date date, X509CRL x509crl, Object obj, CertStatus certStatus, ExtendedPKIXParameters extendedPKIXParameters) {
        if (extendedPKIXParameters.isUseDeltasEnabled() && x509crl != null) {
            CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    protected static void processCRLJ(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) {
        if (certStatus.b() == 11) {
            CertPathValidatorUtilities.getCertStatus(date, x509crl, obj, certStatus);
        }
    }

    protected static void processCertA(CertPath certPath, ExtendedPKIXParameters extendedPKIXParameters, int i, PublicKey publicKey, boolean z, X500Principal x500Principal, X509Certificate x509Certificate) {
        List certificates = certPath.getCertificates();
        X509Certificate x509Certificate2 = (X509Certificate) certificates.get(i);
        if (!z) {
            try {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate2, publicKey, extendedPKIXParameters.getSigProvider());
            } catch (GeneralSecurityException e) {
                throw new ExtCertPathValidatorException("Could not validate certificate signature.", e, certPath, i);
            }
        }
        try {
            x509Certificate2.checkValidity(CertPathValidatorUtilities.getValidCertDateFromValidityModel(extendedPKIXParameters, certPath, i));
            if (extendedPKIXParameters.isRevocationEnabled()) {
                try {
                    checkCRLs(extendedPKIXParameters, x509Certificate2, CertPathValidatorUtilities.getValidCertDateFromValidityModel(extendedPKIXParameters, certPath, i), x509Certificate, publicKey, certificates);
                } catch (AnnotatedException e2) {
                    throw new ExtCertPathValidatorException(e2.getMessage(), e2.getCause() != null ? e2.getCause() : e2, certPath, i);
                }
            }
            if (!CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate2).equals(x500Principal)) {
                StringBuilder sb = new StringBuilder();
                sb.append("IssuerName(");
                sb.append(CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate2));
                sb.append(") does not match SubjectName(");
                sb.append(x500Principal);
                sb.append(") of signing certificate.");
                throw new ExtCertPathValidatorException(sb.toString(), null, certPath, i);
            }
        } catch (CertificateExpiredException e3) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not validate certificate: ");
            sb2.append(e3.getMessage());
            throw new ExtCertPathValidatorException(sb2.toString(), e3, certPath, i);
        } catch (CertificateNotYetValidException e4) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Could not validate certificate: ");
            sb3.append(e4.getMessage());
            throw new ExtCertPathValidatorException(sb3.toString(), e4, certPath, i);
        } catch (AnnotatedException e5) {
            throw new ExtCertPathValidatorException("Could not validate time of certificate.", e5, certPath, i);
        }
    }

    protected static void processCertBC(CertPath certPath, int i, PKIXNameConstraintValidator pKIXNameConstraintValidator) {
        List certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i);
        int size = certificates.size();
        int i2 = size - i;
        if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate) || i2 >= size) {
            try {
                ASN1Sequence instance = DERSequence.getInstance(new ASN1InputStream(CertPathValidatorUtilities.getSubjectPrincipal(x509Certificate).getEncoded()).readObject());
                try {
                    pKIXNameConstraintValidator.checkPermittedDN(instance);
                    pKIXNameConstraintValidator.checkExcludedDN(instance);
                    try {
                        GeneralNames instance2 = GeneralNames.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME));
                        Enumeration elements = new X509Name(instance).getValues(X509Name.EmailAddress).elements();
                        while (elements.hasMoreElements()) {
                            GeneralName generalName = new GeneralName(1, (String) elements.nextElement());
                            try {
                                pKIXNameConstraintValidator.checkPermitted(generalName);
                                pKIXNameConstraintValidator.checkExcluded(generalName);
                            } catch (PKIXNameConstraintValidatorException e) {
                                throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", e, certPath, i);
                            }
                        }
                        if (instance2 != null) {
                            try {
                                GeneralName[] names = instance2.getNames();
                                int i3 = 0;
                                while (i3 < names.length) {
                                    try {
                                        pKIXNameConstraintValidator.checkPermitted(names[i3]);
                                        pKIXNameConstraintValidator.checkExcluded(names[i3]);
                                        i3++;
                                    } catch (PKIXNameConstraintValidatorException e2) {
                                        throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", e2, certPath, i);
                                    }
                                }
                            } catch (Exception e3) {
                                throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", e3, certPath, i);
                            }
                        }
                    } catch (Exception e4) {
                        throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", e4, certPath, i);
                    }
                } catch (PKIXNameConstraintValidatorException e5) {
                    throw new CertPathValidatorException("Subtree check for certificate subject failed.", e5, certPath, i);
                }
            } catch (Exception e6) {
                throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", e6, certPath, i);
            }
        }
    }

    protected static PKIXPolicyNode processCertD(CertPath certPath, int i, Set set, PKIXPolicyNode pKIXPolicyNode, List[] listArr, int i2) {
        String id2;
        Set set2;
        Iterator it;
        PKIXPolicyNode pKIXPolicyNode2;
        CertPath certPath2 = certPath;
        int i3 = i;
        Set set3 = set;
        List[] listArr2 = listArr;
        List certificates = certPath.getCertificates();
        X509Certificate x509Certificate = (X509Certificate) certificates.get(i3);
        int size = certificates.size();
        int i4 = size - i3;
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, CERTIFICATE_POLICIES));
            if (instance == null || pKIXPolicyNode == null) {
                return null;
            }
            Enumeration objects = instance.getObjects();
            HashSet hashSet = new HashSet();
            while (objects.hasMoreElements()) {
                PolicyInformation instance2 = PolicyInformation.getInstance(objects.nextElement());
                ASN1ObjectIdentifier policyIdentifier = instance2.getPolicyIdentifier();
                hashSet.add(policyIdentifier.getId());
                if (!ANY_POLICY.equals(policyIdentifier.getId())) {
                    try {
                        Set qualifierSet = CertPathValidatorUtilities.getQualifierSet(instance2.getPolicyQualifiers());
                        if (!CertPathValidatorUtilities.processCertD1i(i4, listArr2, policyIdentifier, qualifierSet)) {
                            CertPathValidatorUtilities.processCertD1ii(i4, listArr2, policyIdentifier, qualifierSet);
                        }
                    } catch (CertPathValidatorException e) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", e, certPath2, i3);
                    }
                }
            }
            if (set.isEmpty() || set3.contains(ANY_POLICY)) {
                set.clear();
                set3.addAll(hashSet);
            } else {
                HashSet hashSet2 = new HashSet();
                for (Object next : set) {
                    if (hashSet.contains(next)) {
                        hashSet2.add(next);
                    }
                }
                set.clear();
                set3.addAll(hashSet2);
            }
            if (i2 > 0 || (i4 < size && CertPathValidatorUtilities.isSelfIssued(x509Certificate))) {
                Enumeration objects2 = instance.getObjects();
                while (true) {
                    if (!objects2.hasMoreElements()) {
                        break;
                    }
                    PolicyInformation instance3 = PolicyInformation.getInstance(objects2.nextElement());
                    if (ANY_POLICY.equals(instance3.getPolicyIdentifier().getId())) {
                        Set qualifierSet2 = CertPathValidatorUtilities.getQualifierSet(instance3.getPolicyQualifiers());
                        List list = listArr2[i4 - 1];
                        for (int i5 = 0; i5 < list.size(); i5++) {
                            PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) list.get(i5);
                            Iterator it2 = pKIXPolicyNode3.getExpectedPolicies().iterator();
                            while (it2.hasNext()) {
                                Object next2 = it2.next();
                                if (next2 instanceof String) {
                                    id2 = (String) next2;
                                } else if (next2 instanceof ASN1ObjectIdentifier) {
                                    id2 = ((ASN1ObjectIdentifier) next2).getId();
                                } else {
                                    Set set4 = qualifierSet2;
                                }
                                String str = id2;
                                Iterator children = pKIXPolicyNode3.getChildren();
                                boolean z = false;
                                while (children.hasNext()) {
                                    if (str.equals(((PKIXPolicyNode) children.next()).getValidPolicy())) {
                                        z = true;
                                    }
                                }
                                if (!z) {
                                    HashSet hashSet3 = new HashSet();
                                    hashSet3.add(str);
                                    PKIXPolicyNode pKIXPolicyNode4 = r7;
                                    it = it2;
                                    set2 = qualifierSet2;
                                    pKIXPolicyNode2 = pKIXPolicyNode3;
                                    PKIXPolicyNode pKIXPolicyNode5 = new PKIXPolicyNode(new ArrayList(), i4, hashSet3, pKIXPolicyNode3, qualifierSet2, str, false);
                                    pKIXPolicyNode2.addChild(pKIXPolicyNode4);
                                    listArr2[i4].add(pKIXPolicyNode4);
                                } else {
                                    set2 = qualifierSet2;
                                    it = it2;
                                    pKIXPolicyNode2 = pKIXPolicyNode3;
                                }
                                pKIXPolicyNode3 = pKIXPolicyNode2;
                                it2 = it;
                                qualifierSet2 = set2;
                            }
                            Set set5 = qualifierSet2;
                        }
                    }
                }
            }
            PKIXPolicyNode pKIXPolicyNode6 = pKIXPolicyNode;
            for (int i6 = i4 - 1; i6 >= 0; i6--) {
                List list2 = listArr2[i6];
                PKIXPolicyNode pKIXPolicyNode7 = pKIXPolicyNode6;
                for (int i7 = 0; i7 < list2.size(); i7++) {
                    PKIXPolicyNode pKIXPolicyNode8 = (PKIXPolicyNode) list2.get(i7);
                    if (!pKIXPolicyNode8.hasChildren()) {
                        pKIXPolicyNode7 = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode7, listArr2, pKIXPolicyNode8);
                        if (pKIXPolicyNode7 == null) {
                            break;
                        }
                    }
                }
                pKIXPolicyNode6 = pKIXPolicyNode7;
            }
            Set criticalExtensionOIDs = x509Certificate.getCriticalExtensionOIDs();
            if (criticalExtensionOIDs != null) {
                boolean contains = criticalExtensionOIDs.contains(CERTIFICATE_POLICIES);
                List list3 = listArr2[i4];
                for (int i8 = 0; i8 < list3.size(); i8++) {
                    ((PKIXPolicyNode) list3.get(i8)).setCritical(contains);
                }
            }
            return pKIXPolicyNode6;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e2, certPath2, i3);
        }
    }

    protected static PKIXPolicyNode processCertE(CertPath certPath, int i, PKIXPolicyNode pKIXPolicyNode) {
        try {
            if (DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), CERTIFICATE_POLICIES)) == null) {
                return null;
            }
            return pKIXPolicyNode;
        } catch (AnnotatedException e) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e, certPath, i);
        }
    }

    protected static void processCertF(CertPath certPath, int i, PKIXPolicyNode pKIXPolicyNode, int i2) {
        if (i2 <= 0 && pKIXPolicyNode == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", null, certPath, i);
        }
    }

    protected static int wrapupCertA(int i, X509Certificate x509Certificate) {
        return (CertPathValidatorUtilities.isSelfIssued(x509Certificate) || i == 0) ? i : i - 1;
    }

    protected static int wrapupCertB(CertPath certPath, int i, int i2) {
        try {
            ASN1Sequence instance = DERSequence.getInstance(CertPathValidatorUtilities.getExtensionValue((X509Certificate) certPath.getCertificates().get(i), POLICY_CONSTRAINTS));
            if (instance != null) {
                Enumeration objects = instance.getObjects();
                while (objects.hasMoreElements()) {
                    ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) objects.nextElement();
                    if (aSN1TaggedObject.getTagNo() == 0) {
                        try {
                            if (ASN1Integer.getInstance(aSN1TaggedObject, false).getValue().intValue() == 0) {
                                return 0;
                            }
                        } catch (Exception e) {
                            throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", e, certPath, i);
                        }
                    }
                }
            }
            return i2;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", e2, certPath, i);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.security.cert.PKIXCertPathChecker>, for r4v0, types: [java.util.List, java.util.List<java.security.cert.PKIXCertPathChecker>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void wrapupCertF(java.security.cert.CertPath r2, int r3, java.util.List<java.security.cert.PKIXCertPathChecker> r4, java.util.Set r5) {
        /*
            java.util.List r0 = r2.getCertificates()
            java.lang.Object r0 = r0.get(r3)
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            java.util.Iterator r4 = r4.iterator()
        L_0x000e:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x0027
            java.lang.Object r1 = r4.next()     // Catch:{ CertPathValidatorException -> 0x001e }
            java.security.cert.PKIXCertPathChecker r1 = (java.security.cert.PKIXCertPathChecker) r1     // Catch:{ CertPathValidatorException -> 0x001e }
            r1.check(r0, r5)     // Catch:{ CertPathValidatorException -> 0x001e }
            goto L_0x000e
        L_0x001e:
            r4 = move-exception
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r5 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r0 = "Additional certificate path checker failed."
            r5.<init>(r0, r4, r2, r3)
            throw r5
        L_0x0027:
            boolean r4 = r5.isEmpty()
            if (r4 != 0) goto L_0x0045
            org.bouncycastle.jce.exception.ExtCertPathValidatorException r4 = new org.bouncycastle.jce.exception.ExtCertPathValidatorException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Certificate has unsupported critical extension: "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            r0 = 0
            r4.<init>(r5, r0, r2, r3)
            throw r4
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.RFC3280CertPathUtilities.wrapupCertF(java.security.cert.CertPath, int, java.util.List, java.util.Set):void");
    }

    protected static PKIXPolicyNode wrapupCertG(CertPath certPath, ExtendedPKIXParameters extendedPKIXParameters, Set set, int i, List[] listArr, PKIXPolicyNode pKIXPolicyNode, Set set2) {
        int size = certPath.getCertificates().size();
        if (pKIXPolicyNode != null) {
            if (!CertPathValidatorUtilities.isAnyPolicy(set)) {
                HashSet<PKIXPolicyNode> hashSet = new HashSet<>();
                for (List list : listArr) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) list.get(i2);
                        if (ANY_POLICY.equals(pKIXPolicyNode2.getValidPolicy())) {
                            Iterator children = pKIXPolicyNode2.getChildren();
                            while (children.hasNext()) {
                                PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) children.next();
                                if (!ANY_POLICY.equals(pKIXPolicyNode3.getValidPolicy())) {
                                    hashSet.add(pKIXPolicyNode3);
                                }
                            }
                        }
                    }
                }
                for (PKIXPolicyNode pKIXPolicyNode4 : hashSet) {
                    if (!set.contains(pKIXPolicyNode4.getValidPolicy())) {
                        pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode4);
                    }
                }
                if (pKIXPolicyNode != null) {
                    for (int i3 = size - 1; i3 >= 0; i3--) {
                        List list2 = listArr[i3];
                        for (int i4 = 0; i4 < list2.size(); i4++) {
                            PKIXPolicyNode pKIXPolicyNode5 = (PKIXPolicyNode) list2.get(i4);
                            if (!pKIXPolicyNode5.hasChildren()) {
                                pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode5);
                            }
                        }
                    }
                }
            } else if (extendedPKIXParameters.isExplicitPolicyRequired()) {
                if (set2.isEmpty()) {
                    throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
                }
                HashSet<PKIXPolicyNode> hashSet2 = new HashSet<>();
                for (List list3 : listArr) {
                    for (int i5 = 0; i5 < list3.size(); i5++) {
                        PKIXPolicyNode pKIXPolicyNode6 = (PKIXPolicyNode) list3.get(i5);
                        if (ANY_POLICY.equals(pKIXPolicyNode6.getValidPolicy())) {
                            Iterator children2 = pKIXPolicyNode6.getChildren();
                            while (children2.hasNext()) {
                                hashSet2.add(children2.next());
                            }
                        }
                    }
                }
                for (PKIXPolicyNode validPolicy : hashSet2) {
                    set2.contains(validPolicy.getValidPolicy());
                }
                if (pKIXPolicyNode != null) {
                    for (int i6 = size - 1; i6 >= 0; i6--) {
                        List list4 = listArr[i6];
                        for (int i7 = 0; i7 < list4.size(); i7++) {
                            PKIXPolicyNode pKIXPolicyNode7 = (PKIXPolicyNode) list4.get(i7);
                            if (!pKIXPolicyNode7.hasChildren()) {
                                pKIXPolicyNode = CertPathValidatorUtilities.removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode7);
                            }
                        }
                    }
                }
            }
            return pKIXPolicyNode;
        } else if (!extendedPKIXParameters.isExplicitPolicyRequired()) {
            return null;
        } else {
            throw new ExtCertPathValidatorException("Explicit policy requested but none available.", null, certPath, i);
        }
    }
}
