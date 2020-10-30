package org.bouncycastle.x509;

import java.security.cert.CertSelector;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;

public class ExtendedPKIXParameters extends PKIXParameters {
    public static final int CHAIN_VALIDITY_MODEL = 1;
    public static final int PKIX_VALIDITY_MODEL = 0;
    private List a = new ArrayList();
    private Selector b;
    private boolean c;
    private List d = new ArrayList();
    private Set e = new HashSet();
    private Set f = new HashSet();
    private Set g = new HashSet();
    private Set h = new HashSet();
    private int i = 0;
    private boolean j = false;

    public ExtendedPKIXParameters(Set set) {
        super(set);
    }

    public static ExtendedPKIXParameters getInstance(PKIXParameters pKIXParameters) {
        try {
            ExtendedPKIXParameters extendedPKIXParameters = new ExtendedPKIXParameters(pKIXParameters.getTrustAnchors());
            extendedPKIXParameters.setParams(pKIXParameters);
            return extendedPKIXParameters;
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    public void addAddionalStore(Store store) {
        addAdditionalStore(store);
    }

    public void addAdditionalStore(Store store) {
        if (store != null) {
            this.d.add(store);
        }
    }

    public void addStore(Store store) {
        if (store != null) {
            this.a.add(store);
        }
    }

    public Object clone() {
        try {
            ExtendedPKIXParameters extendedPKIXParameters = new ExtendedPKIXParameters(getTrustAnchors());
            extendedPKIXParameters.setParams(this);
            return extendedPKIXParameters;
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    public List getAdditionalStores() {
        return Collections.unmodifiableList(this.d);
    }

    public Set getAttrCertCheckers() {
        return Collections.unmodifiableSet(this.h);
    }

    public Set getNecessaryACAttributes() {
        return Collections.unmodifiableSet(this.f);
    }

    public Set getProhibitedACAttributes() {
        return Collections.unmodifiableSet(this.g);
    }

    public List getStores() {
        return Collections.unmodifiableList(new ArrayList(this.a));
    }

    public Selector getTargetConstraints() {
        if (this.b != null) {
            return (Selector) this.b.clone();
        }
        return null;
    }

    public Set getTrustedACIssuers() {
        return Collections.unmodifiableSet(this.e);
    }

    public int getValidityModel() {
        return this.i;
    }

    public boolean isAdditionalLocationsEnabled() {
        return this.c;
    }

    public boolean isUseDeltasEnabled() {
        return this.j;
    }

    public void setAdditionalLocationsEnabled(boolean z) {
        this.c = z;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.Object>, for r3v0, types: [java.util.Set<java.lang.Object>, java.util.Collection, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setAttrCertCheckers(java.util.Set<java.lang.Object> r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0008
            java.util.Set r3 = r2.h
            r3.clear()
            return
        L_0x0008:
            java.util.Iterator r0 = r3.iterator()
        L_0x000c:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x003c
            java.lang.Object r1 = r0.next()
            boolean r1 = r1 instanceof org.bouncycastle.x509.PKIXAttrCertChecker
            if (r1 != 0) goto L_0x000c
            java.lang.ClassCastException r3 = new java.lang.ClassCastException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "All elements of set must be of type "
            r0.append(r1)
            java.lang.Class<org.bouncycastle.x509.PKIXAttrCertChecker> r1 = org.bouncycastle.x509.PKIXAttrCertChecker.class
            java.lang.String r1 = r1.getName()
            r0.append(r1)
            java.lang.String r1 = "."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            throw r3
        L_0x003c:
            java.util.Set r0 = r2.h
            r0.clear()
            java.util.Set r0 = r2.h
            r0.addAll(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setAttrCertCheckers(java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.security.cert.CertStore>, for r2v0, types: [java.util.List, java.util.List<java.security.cert.CertStore>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setCertStores(java.util.List<java.security.cert.CertStore> r2) {
        /*
            r1 = this;
            if (r2 == 0) goto L_0x0016
            java.util.Iterator r2 = r2.iterator()
        L_0x0006:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0016
            java.lang.Object r0 = r2.next()
            java.security.cert.CertStore r0 = (java.security.cert.CertStore) r0
            r1.addCertStore(r0)
            goto L_0x0006
        L_0x0016:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setCertStores(java.util.List):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.Object>, for r3v0, types: [java.util.Set<java.lang.Object>, java.util.Collection, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setNecessaryACAttributes(java.util.Set<java.lang.Object> r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0008
            java.util.Set r3 = r2.f
            r3.clear()
            return
        L_0x0008:
            java.util.Iterator r0 = r3.iterator()
        L_0x000c:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0022
            java.lang.Object r1 = r0.next()
            boolean r1 = r1 instanceof java.lang.String
            if (r1 != 0) goto L_0x000c
            java.lang.ClassCastException r3 = new java.lang.ClassCastException
            java.lang.String r0 = "All elements of set must be of type String."
            r3.<init>(r0)
            throw r3
        L_0x0022:
            java.util.Set r0 = r2.f
            r0.clear()
            java.util.Set r0 = r2.f
            r0.addAll(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setNecessaryACAttributes(java.util.Set):void");
    }

    /* access modifiers changed from: protected */
    public void setParams(PKIXParameters pKIXParameters) {
        setDate(pKIXParameters.getDate());
        setCertPathCheckers(pKIXParameters.getCertPathCheckers());
        setCertStores(pKIXParameters.getCertStores());
        setAnyPolicyInhibited(pKIXParameters.isAnyPolicyInhibited());
        setExplicitPolicyRequired(pKIXParameters.isExplicitPolicyRequired());
        setPolicyMappingInhibited(pKIXParameters.isPolicyMappingInhibited());
        setRevocationEnabled(pKIXParameters.isRevocationEnabled());
        setInitialPolicies(pKIXParameters.getInitialPolicies());
        setPolicyQualifiersRejected(pKIXParameters.getPolicyQualifiersRejected());
        setSigProvider(pKIXParameters.getSigProvider());
        setTargetCertConstraints(pKIXParameters.getTargetCertConstraints());
        try {
            setTrustAnchors(pKIXParameters.getTrustAnchors());
            if (pKIXParameters instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters extendedPKIXParameters = (ExtendedPKIXParameters) pKIXParameters;
                this.i = extendedPKIXParameters.i;
                this.j = extendedPKIXParameters.j;
                this.c = extendedPKIXParameters.c;
                this.b = extendedPKIXParameters.b == null ? null : (Selector) extendedPKIXParameters.b.clone();
                this.a = new ArrayList(extendedPKIXParameters.a);
                this.d = new ArrayList(extendedPKIXParameters.d);
                this.e = new HashSet(extendedPKIXParameters.e);
                this.g = new HashSet(extendedPKIXParameters.g);
                this.f = new HashSet(extendedPKIXParameters.f);
                this.h = new HashSet(extendedPKIXParameters.h);
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.Object>, for r3v0, types: [java.util.Set<java.lang.Object>, java.util.Collection, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProhibitedACAttributes(java.util.Set<java.lang.Object> r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0008
            java.util.Set r3 = r2.g
            r3.clear()
            return
        L_0x0008:
            java.util.Iterator r0 = r3.iterator()
        L_0x000c:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0022
            java.lang.Object r1 = r0.next()
            boolean r1 = r1 instanceof java.lang.String
            if (r1 != 0) goto L_0x000c
            java.lang.ClassCastException r3 = new java.lang.ClassCastException
            java.lang.String r0 = "All elements of set must be of type String."
            r3.<init>(r0)
            throw r3
        L_0x0022:
            java.util.Set r0 = r2.g
            r0.clear()
            java.util.Set r0 = r2.g
            r0.addAll(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setProhibitedACAttributes(java.util.Set):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.lang.Object>, for r3v0, types: [java.util.List, java.util.Collection, java.util.List<java.lang.Object>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setStores(java.util.List<java.lang.Object> r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x000a
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2.a = r3
            return
        L_0x000a:
            java.util.Iterator r0 = r3.iterator()
        L_0x000e:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0024
            java.lang.Object r1 = r0.next()
            boolean r1 = r1 instanceof org.bouncycastle.util.Store
            if (r1 != 0) goto L_0x000e
            java.lang.ClassCastException r3 = new java.lang.ClassCastException
            java.lang.String r0 = "All elements of list must be of type org.bouncycastle.util.Store."
            r3.<init>(r0)
            throw r3
        L_0x0024:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>(r3)
            r2.a = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setStores(java.util.List):void");
    }

    public void setTargetCertConstraints(CertSelector certSelector) {
        super.setTargetCertConstraints(certSelector);
        this.b = certSelector != null ? X509CertStoreSelector.getInstance((X509CertSelector) certSelector) : null;
    }

    public void setTargetConstraints(Selector selector) {
        this.b = selector != null ? (Selector) selector.clone() : null;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Set, code=java.util.Set<java.lang.Object>, for r3v0, types: [java.util.Set<java.lang.Object>, java.util.Collection, java.util.Set] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setTrustedACIssuers(java.util.Set<java.lang.Object> r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0008
            java.util.Set r3 = r2.e
            r3.clear()
            return
        L_0x0008:
            java.util.Iterator r0 = r3.iterator()
        L_0x000c:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x003c
            java.lang.Object r1 = r0.next()
            boolean r1 = r1 instanceof java.security.cert.TrustAnchor
            if (r1 != 0) goto L_0x000c
            java.lang.ClassCastException r3 = new java.lang.ClassCastException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "All elements of set must be of type "
            r0.append(r1)
            java.lang.Class<java.security.cert.TrustAnchor> r1 = java.security.cert.TrustAnchor.class
            java.lang.String r1 = r1.getName()
            r0.append(r1)
            java.lang.String r1 = "."
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r3.<init>(r0)
            throw r3
        L_0x003c:
            java.util.Set r0 = r2.e
            r0.clear()
            java.util.Set r0 = r2.e
            r0.addAll(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.ExtendedPKIXParameters.setTrustedACIssuers(java.util.Set):void");
    }

    public void setUseDeltasEnabled(boolean z) {
        this.j = z;
    }

    public void setValidityModel(int i2) {
        this.i = i2;
    }
}
