package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters certPathParameters) {
        PublicKey cAPublicKey;
        X500Principal x500Principal;
        PublicKey publicKey;
        HashSet hashSet;
        int i;
        boolean z;
        List list;
        int i2;
        ArrayList[] arrayListArr;
        HashSet hashSet2;
        CertPath certPath2 = certPath;
        CertPathParameters certPathParameters2 = certPathParameters;
        if (!(certPathParameters2 instanceof PKIXParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Parameters must be a ");
            sb.append(PKIXParameters.class.getName());
            sb.append(" instance.");
            throw new InvalidAlgorithmParameterException(sb.toString());
        }
        ExtendedPKIXParameters instance = certPathParameters2 instanceof ExtendedPKIXParameters ? (ExtendedPKIXParameters) certPathParameters2 : ExtendedPKIXParameters.getInstance((PKIXParameters) certPathParameters2);
        if (instance.getTrustAnchors() == null) {
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
        List certificates = certPath.getCertificates();
        int size = certificates.size();
        if (certificates.isEmpty()) {
            throw new CertPathValidatorException("Certification path is empty.", null, certPath2, 0);
        }
        Set initialPolicies = instance.getInitialPolicies();
        try {
            TrustAnchor findTrustAnchor = CertPathValidatorUtilities.findTrustAnchor((X509Certificate) certificates.get(certificates.size() - 1), instance.getTrustAnchors(), instance.getSigProvider());
            if (findTrustAnchor == null) {
                throw new CertPathValidatorException("Trust anchor for certification path not found.", null, certPath2, -1);
            }
            int i3 = size + 1;
            ArrayList[] arrayListArr2 = new ArrayList[i3];
            for (int i4 = 0; i4 < arrayListArr2.length; i4++) {
                arrayListArr2[i4] = new ArrayList();
            }
            HashSet hashSet3 = new HashSet();
            hashSet3.add(RFC3280CertPathUtilities.ANY_POLICY);
            PKIXPolicyNode pKIXPolicyNode = new PKIXPolicyNode(new ArrayList(), 0, hashSet3, null, new HashSet(), RFC3280CertPathUtilities.ANY_POLICY, false);
            arrayListArr2[0].add(pKIXPolicyNode);
            PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
            HashSet hashSet4 = new HashSet();
            int i5 = instance.isExplicitPolicyRequired() ? 0 : i3;
            int i6 = instance.isAnyPolicyInhibited() ? 0 : i3;
            if (instance.isPolicyMappingInhibited()) {
                i3 = 0;
            }
            X509Certificate trustedCert = findTrustAnchor.getTrustedCert();
            if (trustedCert != null) {
                try {
                    x500Principal = CertPathValidatorUtilities.getSubjectPrincipal(trustedCert);
                    cAPublicKey = trustedCert.getPublicKey();
                } catch (IllegalArgumentException e) {
                    throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", e, certPath2, -1);
                }
            } else {
                X500Principal x500Principal2 = new X500Principal(findTrustAnchor.getCAName());
                cAPublicKey = findTrustAnchor.getCAPublicKey();
                x500Principal = x500Principal2;
            }
            PublicKey publicKey2 = cAPublicKey;
            try {
                AlgorithmIdentifier algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
                algorithmIdentifier.getAlgorithm();
                algorithmIdentifier.getParameters();
                if (instance.getTargetConstraints() != null) {
                    publicKey = publicKey2;
                    if (!instance.getTargetConstraints().match((X509Certificate) certificates.get(0))) {
                        throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", null, certPath2, 0);
                    }
                } else {
                    publicKey = publicKey2;
                }
                List certPathCheckers = instance.getCertPathCheckers();
                Iterator it = certPathCheckers.iterator();
                while (it.hasNext()) {
                    int i7 = i5;
                    Iterator it2 = it;
                    ((PKIXCertPathChecker) it.next()).init(false);
                    i5 = i7;
                    it = it2;
                }
                int i8 = i3;
                PKIXPolicyNode pKIXPolicyNode2 = pKIXPolicyNode;
                Set set = initialPolicies;
                int i9 = i5;
                X509Certificate x509Certificate = null;
                int i10 = size;
                X509Certificate x509Certificate2 = trustedCert;
                int size2 = certificates.size() - 1;
                int i11 = i6;
                X509Certificate x509Certificate3 = x509Certificate2;
                while (size2 >= 0) {
                    int i12 = size - size2;
                    List list2 = certPathCheckers;
                    X509Certificate x509Certificate4 = (X509Certificate) certificates.get(size2);
                    int i13 = i11;
                    if (size2 == certificates.size() - 1) {
                        i = i13;
                        z = true;
                    } else {
                        i = i13;
                        z = false;
                    }
                    CertPath certPath3 = certPath2;
                    List list3 = certificates;
                    int i14 = i12;
                    HashSet hashSet5 = hashSet4;
                    ExtendedPKIXParameters extendedPKIXParameters = instance;
                    int i15 = i9;
                    int i16 = i10;
                    PKIXNameConstraintValidator pKIXNameConstraintValidator2 = pKIXNameConstraintValidator;
                    ArrayList[] arrayListArr3 = arrayListArr2;
                    TrustAnchor trustAnchor = findTrustAnchor;
                    RFC3280CertPathUtilities.processCertA(certPath3, instance, size2, publicKey, z, x500Principal, x509Certificate3);
                    RFC3280CertPathUtilities.processCertBC(certPath2, size2, pKIXNameConstraintValidator2);
                    PKIXPolicyNode processCertE = RFC3280CertPathUtilities.processCertE(certPath2, size2, RFC3280CertPathUtilities.processCertD(certPath3, size2, hashSet5, pKIXPolicyNode2, arrayListArr3, i));
                    RFC3280CertPathUtilities.processCertF(certPath2, size2, processCertE, i15);
                    if (i14 == size) {
                        list = list2;
                        i2 = i16;
                        arrayListArr = arrayListArr3;
                        pKIXPolicyNode2 = processCertE;
                        i11 = i;
                        i9 = i15;
                    } else if (x509Certificate4 == null || x509Certificate4.getVersion() != 1) {
                        RFC3280CertPathUtilities.prepareNextCertA(certPath2, size2);
                        arrayListArr = arrayListArr3;
                        PKIXPolicyNode prepareCertB = RFC3280CertPathUtilities.prepareCertB(certPath2, size2, arrayListArr, processCertE, i8);
                        RFC3280CertPathUtilities.prepareNextCertG(certPath2, size2, pKIXNameConstraintValidator2);
                        int prepareNextCertH1 = RFC3280CertPathUtilities.prepareNextCertH1(certPath2, size2, i15);
                        int prepareNextCertH2 = RFC3280CertPathUtilities.prepareNextCertH2(certPath2, size2, i8);
                        int prepareNextCertH3 = RFC3280CertPathUtilities.prepareNextCertH3(certPath2, size2, i);
                        int prepareNextCertI1 = RFC3280CertPathUtilities.prepareNextCertI1(certPath2, size2, prepareNextCertH1);
                        int prepareNextCertI2 = RFC3280CertPathUtilities.prepareNextCertI2(certPath2, size2, prepareNextCertH2);
                        int prepareNextCertJ = RFC3280CertPathUtilities.prepareNextCertJ(certPath2, size2, prepareNextCertH3);
                        RFC3280CertPathUtilities.prepareNextCertK(certPath2, size2);
                        i2 = RFC3280CertPathUtilities.prepareNextCertM(certPath2, size2, RFC3280CertPathUtilities.prepareNextCertL(certPath2, size2, i16));
                        RFC3280CertPathUtilities.prepareNextCertN(certPath2, size2);
                        Set criticalExtensionOIDs = x509Certificate4.getCriticalExtensionOIDs();
                        if (criticalExtensionOIDs != null) {
                            hashSet2 = new HashSet(criticalExtensionOIDs);
                            hashSet2.remove(RFC3280CertPathUtilities.KEY_USAGE);
                            hashSet2.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                            hashSet2.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                            hashSet2.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                            hashSet2.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                            hashSet2.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                            hashSet2.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                            hashSet2.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                            hashSet2.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                            hashSet2.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                        } else {
                            hashSet2 = new HashSet();
                        }
                        list = list2;
                        RFC3280CertPathUtilities.prepareNextCertO(certPath2, size2, hashSet2, list);
                        x500Principal = CertPathValidatorUtilities.getSubjectPrincipal(x509Certificate4);
                        try {
                            PublicKey nextWorkingKey = CertPathValidatorUtilities.getNextWorkingKey(certPath.getCertificates(), size2);
                            AlgorithmIdentifier algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(nextWorkingKey);
                            algorithmIdentifier2.getAlgorithm();
                            algorithmIdentifier2.getParameters();
                            pKIXPolicyNode2 = prepareCertB;
                            i8 = prepareNextCertI2;
                            i11 = prepareNextCertJ;
                            publicKey = nextWorkingKey;
                            x509Certificate3 = x509Certificate4;
                            i9 = prepareNextCertI1;
                        } catch (CertPathValidatorException e2) {
                            throw new CertPathValidatorException("Next working key could not be retrieved.", e2, certPath2, size2);
                        }
                    } else {
                        throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", null, certPath2, size2);
                    }
                    size2--;
                    x509Certificate = x509Certificate4;
                    hashSet4 = hashSet5;
                    certificates = list3;
                    instance = extendedPKIXParameters;
                    certPathCheckers = list;
                    findTrustAnchor = trustAnchor;
                    int i17 = i2;
                    arrayListArr2 = arrayListArr;
                    pKIXNameConstraintValidator = pKIXNameConstraintValidator2;
                    i10 = i17;
                }
                HashSet hashSet6 = hashSet4;
                ArrayList[] arrayListArr4 = arrayListArr2;
                TrustAnchor trustAnchor2 = findTrustAnchor;
                ExtendedPKIXParameters extendedPKIXParameters2 = instance;
                List list4 = certPathCheckers;
                int i18 = size2 + 1;
                int wrapupCertB = RFC3280CertPathUtilities.wrapupCertB(certPath2, i18, RFC3280CertPathUtilities.wrapupCertA(i9, x509Certificate));
                Set criticalExtensionOIDs2 = x509Certificate.getCriticalExtensionOIDs();
                if (criticalExtensionOIDs2 != null) {
                    hashSet = new HashSet(criticalExtensionOIDs2);
                    hashSet.remove(RFC3280CertPathUtilities.KEY_USAGE);
                    hashSet.remove(RFC3280CertPathUtilities.CERTIFICATE_POLICIES);
                    hashSet.remove(RFC3280CertPathUtilities.POLICY_MAPPINGS);
                    hashSet.remove(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY);
                    hashSet.remove(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT);
                    hashSet.remove(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
                    hashSet.remove(RFC3280CertPathUtilities.POLICY_CONSTRAINTS);
                    hashSet.remove(RFC3280CertPathUtilities.BASIC_CONSTRAINTS);
                    hashSet.remove(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME);
                    hashSet.remove(RFC3280CertPathUtilities.NAME_CONSTRAINTS);
                    hashSet.remove(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS);
                } else {
                    hashSet = new HashSet();
                }
                RFC3280CertPathUtilities.wrapupCertF(certPath2, i18, list4, hashSet);
                X509Certificate x509Certificate5 = x509Certificate;
                PKIXPolicyNode wrapupCertG = RFC3280CertPathUtilities.wrapupCertG(certPath2, extendedPKIXParameters2, set, i18, arrayListArr4, pKIXPolicyNode2, hashSet6);
                if (wrapupCertB > 0 || wrapupCertG != null) {
                    return new PKIXCertPathValidatorResult(trustAnchor2, wrapupCertG, x509Certificate5.getPublicKey());
                }
                throw new CertPathValidatorException("Path processing failed on policy.", null, certPath2, size2);
            } catch (CertPathValidatorException e3) {
                throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e3, certPath2, -1);
            }
        } catch (AnnotatedException e4) {
            AnnotatedException annotatedException = e4;
            throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException, certPath2, certificates.size() - 1);
        }
    }
}
