package org.bouncycastle.x509.util;

import io.reactivex.annotations.SchedulerSupport;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.util.StoreException;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509CertPairStoreSelector;
import org.bouncycastle.x509.X509CertStoreSelector;

public class LDAPStoreHelper {
    private static String b = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String c = "ignore";
    private static int e = 32;
    private static long f = 60000;
    private X509LDAPCertStoreParameters a;
    private Map d = new HashMap(e);

    public LDAPStoreHelper(X509LDAPCertStoreParameters x509LDAPCertStoreParameters) {
        this.a = x509LDAPCertStoreParameters;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:695)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0042  */
    private java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = r5.toLowerCase()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r6.toLowerCase()
            r1.append(r2)
            java.lang.String r2 = "="
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            int r0 = r0.indexOf(r1)
            r1 = -1
            if (r0 != r1) goto L_0x0023
            java.lang.String r5 = ""
            return r5
        L_0x0023:
            int r6 = r6.length()
            int r0 = r0 + r6
            java.lang.String r5 = r5.substring(r0)
            r6 = 44
            int r0 = r5.indexOf(r6)
            if (r0 != r1) goto L_0x0038
        L_0x0034:
            int r0 = r5.length()
        L_0x0038:
            int r2 = r0 + -1
            char r2 = r5.charAt(r2)
            r3 = 92
            if (r2 != r3) goto L_0x004b
            int r0 = r0 + 1
            int r0 = r5.indexOf(r6, r0)
            if (r0 != r1) goto L_0x0038
            goto L_0x0034
        L_0x004b:
            r6 = 0
            java.lang.String r5 = r5.substring(r6, r0)
            r0 = 61
            int r0 = r5.indexOf(r0)
            r1 = 1
            int r0 = r0 + r1
            java.lang.String r5 = r5.substring(r0)
            char r0 = r5.charAt(r6)
            r2 = 32
            if (r0 != r2) goto L_0x0068
            java.lang.String r5 = r5.substring(r1)
        L_0x0068:
            java.lang.String r0 = "\""
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x0074
            java.lang.String r5 = r5.substring(r1)
        L_0x0074:
            java.lang.String r0 = "\""
            boolean r0 = r5.endsWith(r0)
            if (r0 == 0) goto L_0x0085
            int r0 = r5.length()
            int r0 = r0 - r1
            java.lang.String r5 = r5.substring(r6, r0)
        L_0x0085:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private String a(X509CertStoreSelector x509CertStoreSelector) {
        try {
            byte[] subjectAsBytes = x509CertStoreSelector.getSubjectAsBytes();
            if (subjectAsBytes != null) {
                return new X500Principal(subjectAsBytes).getName("RFC1779");
            }
            return null;
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception processing name: ");
            sb.append(e2.getMessage());
            throw new StoreException(sb.toString(), e2);
        }
    }

    private List a(String str) {
        List list = (List) this.d.get(str);
        long currentTimeMillis = System.currentTimeMillis();
        if (list == null || ((Date) list.get(0)).getTime() < currentTimeMillis - f) {
            return null;
        }
        return (List) list.get(1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009a A[LOOP:0: B:25:0x009a->B:27:0x009d, LOOP_START, PHI: r4 
      PHI: (r4v2 int) = (r4v1 int), (r4v3 int) binds: [B:24:0x0098, B:27:0x009d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00db A[LOOP:1: B:33:0x00d5->B:35:0x00db, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List a(org.bouncycastle.x509.X509AttributeCertStoreSelector r7, java.lang.String[] r8, java.lang.String[] r9, java.lang.String[] r10) {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r7.getHolder()
            r3 = 0
            if (r2 == 0) goto L_0x003d
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r7.getHolder()
            java.math.BigInteger r2 = r2.getSerialNumber()
            if (r2 == 0) goto L_0x002a
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r7.getHolder()
            java.math.BigInteger r2 = r2.getSerialNumber()
            java.lang.String r2 = r2.toString()
            r1.add(r2)
        L_0x002a:
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r7.getHolder()
            java.security.Principal[] r2 = r2.getEntityNames()
            if (r2 == 0) goto L_0x003d
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r7.getHolder()
            java.security.Principal[] r2 = r2.getEntityNames()
            goto L_0x003e
        L_0x003d:
            r2 = r3
        L_0x003e:
            org.bouncycastle.x509.X509AttributeCertificate r4 = r7.getAttributeCert()
            if (r4 == 0) goto L_0x006d
            org.bouncycastle.x509.X509AttributeCertificate r4 = r7.getAttributeCert()
            org.bouncycastle.x509.AttributeCertificateHolder r4 = r4.getHolder()
            java.security.Principal[] r4 = r4.getEntityNames()
            if (r4 == 0) goto L_0x005e
            org.bouncycastle.x509.X509AttributeCertificate r2 = r7.getAttributeCert()
            org.bouncycastle.x509.AttributeCertificateHolder r2 = r2.getHolder()
            java.security.Principal[] r2 = r2.getEntityNames()
        L_0x005e:
            org.bouncycastle.x509.X509AttributeCertificate r4 = r7.getAttributeCert()
            java.math.BigInteger r4 = r4.getSerialNumber()
            java.lang.String r4 = r4.toString()
            r1.add(r4)
        L_0x006d:
            r4 = 0
            if (r2 == 0) goto L_0x0087
            r3 = r2[r4]
            boolean r3 = r3 instanceof javax.security.auth.x500.X500Principal
            if (r3 == 0) goto L_0x0081
            r2 = r2[r4]
            javax.security.auth.x500.X500Principal r2 = (javax.security.auth.x500.X500Principal) r2
            java.lang.String r3 = "RFC1779"
            java.lang.String r3 = r2.getName(r3)
            goto L_0x0087
        L_0x0081:
            r2 = r2[r4]
            java.lang.String r3 = r2.getName()
        L_0x0087:
            java.math.BigInteger r2 = r7.getSerialNumber()
            if (r2 == 0) goto L_0x0098
            java.math.BigInteger r7 = r7.getSerialNumber()
            java.lang.String r7 = r7.toString()
            r1.add(r7)
        L_0x0098:
            if (r3 == 0) goto L_0x00c3
        L_0x009a:
            int r7 = r10.length
            if (r4 >= r7) goto L_0x00c3
            r7 = r10[r4]
            java.lang.String r7 = r6.a(r3, r7)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "*"
            r2.append(r5)
            r2.append(r7)
            java.lang.String r7 = "*"
            r2.append(r7)
            java.lang.String r7 = r2.toString()
            java.util.List r7 = r6.a(r9, r7, r8)
            r0.addAll(r7)
            int r4 = r4 + 1
            goto L_0x009a
        L_0x00c3:
            int r7 = r1.size()
            if (r7 <= 0) goto L_0x00f3
            org.bouncycastle.jce.X509LDAPCertStoreParameters r7 = r6.a
            java.lang.String r7 = r7.getSearchForSerialNumberIn()
            if (r7 == 0) goto L_0x00f3
            java.util.Iterator r7 = r1.iterator()
        L_0x00d5:
            boolean r10 = r7.hasNext()
            if (r10 == 0) goto L_0x00f3
            java.lang.Object r10 = r7.next()
            java.lang.String r10 = (java.lang.String) r10
            org.bouncycastle.jce.X509LDAPCertStoreParameters r2 = r6.a
            java.lang.String r2 = r2.getSearchForSerialNumberIn()
            java.lang.String[] r2 = r6.b(r2)
            java.util.List r10 = r6.a(r2, r10, r8)
            r0.addAll(r10)
            goto L_0x00d5
        L_0x00f3:
            int r7 = r1.size()
            if (r7 != 0) goto L_0x0104
            if (r3 != 0) goto L_0x0104
            java.lang.String r7 = "*"
            java.util.List r7 = r6.a(r9, r7, r8)
            r0.addAll(r7)
        L_0x0104:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(org.bouncycastle.x509.X509AttributeCertStoreSelector, java.lang.String[], java.lang.String[], java.lang.String[]):java.util.List");
    }

    private List a(X509CRLStoreSelector x509CRLStoreSelector, String[] strArr, String[] strArr2, String[] strArr3) {
        ArrayList arrayList = new ArrayList();
        HashSet<X500Principal> hashSet = new HashSet<>();
        if (x509CRLStoreSelector.getIssuers() != null) {
            hashSet.addAll(x509CRLStoreSelector.getIssuers());
        }
        if (x509CRLStoreSelector.getCertificateChecking() != null) {
            hashSet.add(a(x509CRLStoreSelector.getCertificateChecking()));
        }
        if (x509CRLStoreSelector.getAttrCertificateChecking() != null) {
            Principal[] principals = x509CRLStoreSelector.getAttrCertificateChecking().getIssuer().getPrincipals();
            for (int i = 0; i < principals.length; i++) {
                if (principals[i] instanceof X500Principal) {
                    hashSet.add(principals[i]);
                }
            }
        }
        String str = null;
        for (X500Principal name : hashSet) {
            str = name.getName("RFC1779");
            for (String a2 : strArr3) {
                String a3 = a(str, a2);
                StringBuilder sb = new StringBuilder();
                sb.append("*");
                sb.append(a3);
                sb.append("*");
                arrayList.addAll(a(strArr2, sb.toString(), strArr));
            }
        }
        if (str == null) {
            arrayList.addAll(a(strArr2, "*", strArr));
        }
        return arrayList;
    }

    private List a(X509CertPairStoreSelector x509CertPairStoreSelector, String[] strArr, String[] strArr2, String[] strArr3) {
        ArrayList arrayList = new ArrayList();
        String a2 = x509CertPairStoreSelector.getForwardSelector() != null ? a(x509CertPairStoreSelector.getForwardSelector()) : null;
        if (!(x509CertPairStoreSelector.getCertPair() == null || x509CertPairStoreSelector.getCertPair().getForward() == null)) {
            a2 = x509CertPairStoreSelector.getCertPair().getForward().getSubjectX500Principal().getName("RFC1779");
        }
        if (a2 != null) {
            for (String a3 : strArr3) {
                String a4 = a(a2, a3);
                StringBuilder sb = new StringBuilder();
                sb.append("*");
                sb.append(a4);
                sb.append("*");
                arrayList.addAll(a(strArr2, sb.toString(), strArr));
            }
        }
        if (a2 == null) {
            arrayList.addAll(a(strArr2, "*", strArr));
        }
        return arrayList;
    }

    private List a(X509CertStoreSelector x509CertStoreSelector, String[] strArr, String[] strArr2, String[] strArr3) {
        ArrayList arrayList = new ArrayList();
        String a2 = a(x509CertStoreSelector);
        String bigInteger = x509CertStoreSelector.getSerialNumber() != null ? x509CertStoreSelector.getSerialNumber().toString() : null;
        if (x509CertStoreSelector.getCertificate() != null) {
            a2 = x509CertStoreSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
            bigInteger = x509CertStoreSelector.getCertificate().getSerialNumber().toString();
        }
        if (a2 != null) {
            for (String a3 : strArr3) {
                String a4 = a(a2, a3);
                StringBuilder sb = new StringBuilder();
                sb.append("*");
                sb.append(a4);
                sb.append("*");
                arrayList.addAll(a(strArr2, sb.toString(), strArr));
            }
        }
        if (!(bigInteger == null || this.a.getSearchForSerialNumberIn() == null)) {
            arrayList.addAll(a(b(this.a.getSearchForSerialNumberIn()), bigInteger, strArr));
        }
        if (bigInteger == null && a2 == null) {
            arrayList.addAll(a(strArr2, "*", strArr));
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0108, code lost:
        if (r0 != null) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x010d, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0119, code lost:
        if (r0 != null) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x011c, code lost:
        return r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0114 A[SYNTHETIC, Splitter:B:41:0x0114] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List a(java.lang.String[] r6, java.lang.String r7, java.lang.String[] r8) {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            if (r6 != 0) goto L_0x0006
            r6 = r1
            goto L_0x0053
        L_0x0006:
            java.lang.String r2 = ""
            java.lang.String r3 = "**"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0012
            java.lang.String r7 = "*"
        L_0x0012:
            r3 = r2
            r2 = 0
        L_0x0014:
            int r4 = r6.length
            if (r2 >= r4) goto L_0x003d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            java.lang.String r3 = "("
            r4.append(r3)
            r3 = r6[r2]
            r4.append(r3)
            java.lang.String r3 = "="
            r4.append(r3)
            r4.append(r7)
            java.lang.String r3 = ")"
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            int r2 = r2 + 1
            goto L_0x0014
        L_0x003d:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "(|"
            r6.append(r7)
            r6.append(r3)
            java.lang.String r7 = ")"
            r6.append(r7)
            java.lang.String r6 = r6.toString()
        L_0x0053:
            java.lang.String r7 = ""
        L_0x0055:
            int r2 = r8.length
            if (r0 >= r2) goto L_0x0076
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r7)
            java.lang.String r7 = "("
            r2.append(r7)
            r7 = r8[r0]
            r2.append(r7)
            java.lang.String r7 = "=*)"
            r2.append(r7)
            java.lang.String r7 = r2.toString()
            int r0 = r0 + 1
            goto L_0x0055
        L_0x0076:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "(|"
            r0.append(r2)
            r0.append(r7)
            java.lang.String r7 = ")"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "(&"
            r0.append(r2)
            r0.append(r6)
            java.lang.String r2 = ""
            r0.append(r2)
            r0.append(r7)
            java.lang.String r2 = ")"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            if (r6 != 0) goto L_0x00ad
            goto L_0x00ae
        L_0x00ad:
            r7 = r0
        L_0x00ae:
            java.util.List r6 = r5.a(r7)
            if (r6 == 0) goto L_0x00b5
            return r6
        L_0x00b5:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            javax.naming.directory.DirContext r0 = r5.a()     // Catch:{ NamingException -> 0x0118, all -> 0x0110 }
            javax.naming.directory.SearchControls r1 = new javax.naming.directory.SearchControls     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            r1.<init>()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            r2 = 2
            r1.setSearchScope(r2)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            r2 = 0
            r1.setCountLimit(r2)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            r1.setReturningAttributes(r8)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            org.bouncycastle.jce.X509LDAPCertStoreParameters r8 = r5.a     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            java.lang.String r8 = r8.getBaseDN()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.NamingEnumeration r8 = r0.search(r8, r7, r1)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
        L_0x00d9:
            boolean r1 = r8.hasMoreElements()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            if (r1 == 0) goto L_0x0105
            java.lang.Object r1 = r8.next()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.directory.SearchResult r1 = (javax.naming.directory.SearchResult) r1     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.directory.Attributes r1 = r1.getAttributes()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.NamingEnumeration r1 = r1.getAll()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            java.lang.Object r1 = r1.next()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.directory.Attribute r1 = (javax.naming.directory.Attribute) r1     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            javax.naming.NamingEnumeration r1 = r1.getAll()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
        L_0x00f7:
            boolean r2 = r1.hasMore()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            if (r2 == 0) goto L_0x00d9
            java.lang.Object r2 = r1.next()     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            r6.add(r2)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            goto L_0x00f7
        L_0x0105:
            r5.a(r7, r6)     // Catch:{ NamingException -> 0x0119, all -> 0x010e }
            if (r0 == 0) goto L_0x011c
        L_0x010a:
            r0.close()     // Catch:{ Exception -> 0x011c }
            return r6
        L_0x010e:
            r6 = move-exception
            goto L_0x0112
        L_0x0110:
            r6 = move-exception
            r0 = r1
        L_0x0112:
            if (r0 == 0) goto L_0x0117
            r0.close()     // Catch:{ Exception -> 0x0117 }
        L_0x0117:
            throw r6
        L_0x0118:
            r0 = r1
        L_0x0119:
            if (r0 == 0) goto L_0x011c
            goto L_0x010a
        L_0x011c:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.lang.String[], java.lang.String, java.lang.String[]):java.util.List");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<byte[]>, for r5v0, types: [java.util.List, java.util.List<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.List<byte[]> r5, org.bouncycastle.x509.X509AttributeCertStoreSelector r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r5 = r5.iterator()
            org.bouncycastle.jce.provider.X509AttrCertParser r1 = new org.bouncycastle.jce.provider.X509AttrCertParser
            r1.<init>()
        L_0x000e:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L_0x0032
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ StreamParsingException -> 0x000e }
            java.lang.Object r3 = r5.next()     // Catch:{ StreamParsingException -> 0x000e }
            byte[] r3 = (byte[]) r3     // Catch:{ StreamParsingException -> 0x000e }
            r2.<init>(r3)     // Catch:{ StreamParsingException -> 0x000e }
            r1.engineInit(r2)     // Catch:{ StreamParsingException -> 0x000e }
            java.lang.Object r2 = r1.engineRead()     // Catch:{ StreamParsingException -> 0x000e }
            org.bouncycastle.x509.X509AttributeCertificate r2 = (org.bouncycastle.x509.X509AttributeCertificate) r2     // Catch:{ StreamParsingException -> 0x000e }
            boolean r3 = r6.match(r2)     // Catch:{ StreamParsingException -> 0x000e }
            if (r3 == 0) goto L_0x000e
            r0.add(r2)     // Catch:{ StreamParsingException -> 0x000e }
            goto L_0x000e
        L_0x0032:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.util.List, org.bouncycastle.x509.X509AttributeCertStoreSelector):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<byte[]>, for r5v0, types: [java.util.List, java.util.List<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.List<byte[]> r5, org.bouncycastle.x509.X509CRLStoreSelector r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            org.bouncycastle.jce.provider.X509CRLParser r1 = new org.bouncycastle.jce.provider.X509CRLParser
            r1.<init>()
            java.util.Iterator r5 = r5.iterator()
        L_0x000e:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L_0x0032
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ StreamParsingException -> 0x000e }
            java.lang.Object r3 = r5.next()     // Catch:{ StreamParsingException -> 0x000e }
            byte[] r3 = (byte[]) r3     // Catch:{ StreamParsingException -> 0x000e }
            r2.<init>(r3)     // Catch:{ StreamParsingException -> 0x000e }
            r1.engineInit(r2)     // Catch:{ StreamParsingException -> 0x000e }
            java.lang.Object r2 = r1.engineRead()     // Catch:{ StreamParsingException -> 0x000e }
            java.security.cert.X509CRL r2 = (java.security.cert.X509CRL) r2     // Catch:{ StreamParsingException -> 0x000e }
            boolean r3 = r6.match(r2)     // Catch:{ StreamParsingException -> 0x000e }
            if (r3 == 0) goto L_0x000e
            r0.add(r2)     // Catch:{ StreamParsingException -> 0x000e }
            goto L_0x000e
        L_0x0032:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.util.List, org.bouncycastle.x509.X509CRLStoreSelector):java.util.Set");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:3|4|5|6|7|(2:9|14)(1:15)|10|1) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0026 */
    /* JADX WARNING: Removed duplicated region for block: B:11:? A[ExcHandler: IOException | CertificateParsingException (unused java.lang.Throwable), SYNTHETIC, Splitter:B:5:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.List r9, org.bouncycastle.x509.X509CertPairStoreSelector r10) {
        /*
            r8 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            r1 = 0
        L_0x0006:
            int r2 = r9.size()
            if (r1 >= r2) goto L_0x0066
            org.bouncycastle.jce.provider.X509CertPairParser r2 = new org.bouncycastle.jce.provider.X509CertPairParser     // Catch:{ StreamParsingException -> 0x0026 }
            r2.<init>()     // Catch:{ StreamParsingException -> 0x0026 }
            java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream     // Catch:{ StreamParsingException -> 0x0026 }
            java.lang.Object r4 = r9.get(r1)     // Catch:{ StreamParsingException -> 0x0026 }
            byte[] r4 = (byte[]) r4     // Catch:{ StreamParsingException -> 0x0026 }
            r3.<init>(r4)     // Catch:{ StreamParsingException -> 0x0026 }
            r2.engineInit(r3)     // Catch:{ StreamParsingException -> 0x0026 }
            java.lang.Object r2 = r2.engineRead()     // Catch:{ StreamParsingException -> 0x0026 }
            org.bouncycastle.x509.X509CertificatePair r2 = (org.bouncycastle.x509.X509CertificatePair) r2     // Catch:{ StreamParsingException -> 0x0026 }
            goto L_0x005a
        L_0x0026:
            java.lang.Object r2 = r9.get(r1)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            byte[] r2 = (byte[]) r2     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            int r3 = r1 + 1
            java.lang.Object r4 = r9.get(r3)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            byte[] r4 = (byte[]) r4     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.x509.X509CertificatePair r5 = new org.bouncycastle.x509.X509CertificatePair     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.x509.CertificatePair r6 = new org.bouncycastle.asn1.x509.CertificatePair     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.ASN1InputStream r7 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            r7.<init>(r2)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.ASN1Primitive r2 = r7.readObject()     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.x509.Certificate r2 = org.bouncycastle.asn1.x509.Certificate.getInstance(r2)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.ASN1InputStream r7 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            r7.<init>(r4)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.ASN1Primitive r4 = r7.readObject()     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            org.bouncycastle.asn1.x509.Certificate r4 = org.bouncycastle.asn1.x509.Certificate.getInstance(r4)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            r6.<init>(r2, r4)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            r5.<init>(r6)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            r1 = r3
            r2 = r5
        L_0x005a:
            boolean r3 = r10.match(r2)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
            if (r3 == 0) goto L_0x0063
            r0.add(r2)     // Catch:{ IOException | CertificateParsingException -> 0x0063, IOException | CertificateParsingException -> 0x0063 }
        L_0x0063:
            int r1 = r1 + 1
            goto L_0x0006
        L_0x0066:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.util.List, org.bouncycastle.x509.X509CertPairStoreSelector):java.util.Set");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<byte[]>, for r5v0, types: [java.util.List, java.util.List<byte[]>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.util.List<byte[]> r5, org.bouncycastle.x509.X509CertStoreSelector r6) {
        /*
            r4 = this;
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r5 = r5.iterator()
            org.bouncycastle.jce.provider.X509CertParser r1 = new org.bouncycastle.jce.provider.X509CertParser
            r1.<init>()
        L_0x000e:
            boolean r2 = r5.hasNext()
            if (r2 == 0) goto L_0x0032
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x000e }
            java.lang.Object r3 = r5.next()     // Catch:{ Exception -> 0x000e }
            byte[] r3 = (byte[]) r3     // Catch:{ Exception -> 0x000e }
            r2.<init>(r3)     // Catch:{ Exception -> 0x000e }
            r1.engineInit(r2)     // Catch:{ Exception -> 0x000e }
            java.lang.Object r2 = r1.engineRead()     // Catch:{ Exception -> 0x000e }
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch:{ Exception -> 0x000e }
            boolean r3 = r6.match(r2)     // Catch:{ Exception -> 0x000e }
            if (r3 == 0) goto L_0x000e
            r0.add(r2)     // Catch:{ Exception -> 0x000e }
            goto L_0x000e
        L_0x0032:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.x509.util.LDAPStoreHelper.a(java.util.List, org.bouncycastle.x509.X509CertStoreSelector):java.util.Set");
    }

    private DirContext a() {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", b);
        properties.setProperty("java.naming.batchsize", "0");
        properties.setProperty("java.naming.provider.url", this.a.getLdapURL());
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
        properties.setProperty("java.naming.referral", c);
        properties.setProperty("java.naming.security.authentication", SchedulerSupport.NONE);
        return new InitialDirContext(properties);
    }

    private X500Principal a(X509Certificate x509Certificate) {
        return x509Certificate.getIssuerX500Principal();
    }

    private synchronized void a(String str, List list) {
        Map map;
        Date date = new Date(System.currentTimeMillis());
        ArrayList arrayList = new ArrayList();
        arrayList.add(date);
        arrayList.add(list);
        if (this.d.containsKey(str)) {
            map = this.d;
        } else {
            if (this.d.size() >= e) {
                long time = date.getTime();
                Object obj = null;
                for (Entry entry : this.d.entrySet()) {
                    long time2 = ((Date) ((List) entry.getValue()).get(0)).getTime();
                    if (time2 < time) {
                        obj = entry.getKey();
                        time = time2;
                    }
                }
                this.d.remove(obj);
            }
            map = this.d;
        }
        map.put(str, arrayList);
    }

    private String[] b(String str) {
        return str.split("\\s+");
    }

    public Collection getAACertificates(X509AttributeCertStoreSelector x509AttributeCertStoreSelector) {
        String[] b2 = b(this.a.getAACertificateAttribute());
        String[] b3 = b(this.a.getLdapAACertificateAttributeName());
        String[] b4 = b(this.a.getAACertificateSubjectAttributeName());
        Set a2 = a(a(x509AttributeCertStoreSelector, b2, b3, b4), x509AttributeCertStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509AttributeCertStoreSelector(), b2, b3, b4), x509AttributeCertStoreSelector));
        }
        return a2;
    }

    public Collection getAttributeAuthorityRevocationLists(X509CRLStoreSelector x509CRLStoreSelector) {
        String[] b2 = b(this.a.getAttributeAuthorityRevocationListAttribute());
        String[] b3 = b(this.a.getLdapAttributeAuthorityRevocationListAttributeName());
        String[] b4 = b(this.a.getAttributeAuthorityRevocationListIssuerAttributeName());
        Set a2 = a(a(x509CRLStoreSelector, b2, b3, b4), x509CRLStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CRLStoreSelector(), b2, b3, b4), x509CRLStoreSelector));
        }
        return a2;
    }

    public Collection getAttributeCertificateAttributes(X509AttributeCertStoreSelector x509AttributeCertStoreSelector) {
        String[] b2 = b(this.a.getAttributeCertificateAttributeAttribute());
        String[] b3 = b(this.a.getLdapAttributeCertificateAttributeAttributeName());
        String[] b4 = b(this.a.getAttributeCertificateAttributeSubjectAttributeName());
        Set a2 = a(a(x509AttributeCertStoreSelector, b2, b3, b4), x509AttributeCertStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509AttributeCertStoreSelector(), b2, b3, b4), x509AttributeCertStoreSelector));
        }
        return a2;
    }

    public Collection getAttributeCertificateRevocationLists(X509CRLStoreSelector x509CRLStoreSelector) {
        String[] b2 = b(this.a.getAttributeCertificateRevocationListAttribute());
        String[] b3 = b(this.a.getLdapAttributeCertificateRevocationListAttributeName());
        String[] b4 = b(this.a.getAttributeCertificateRevocationListIssuerAttributeName());
        Set a2 = a(a(x509CRLStoreSelector, b2, b3, b4), x509CRLStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CRLStoreSelector(), b2, b3, b4), x509CRLStoreSelector));
        }
        return a2;
    }

    public Collection getAttributeDescriptorCertificates(X509AttributeCertStoreSelector x509AttributeCertStoreSelector) {
        String[] b2 = b(this.a.getAttributeDescriptorCertificateAttribute());
        String[] b3 = b(this.a.getLdapAttributeDescriptorCertificateAttributeName());
        String[] b4 = b(this.a.getAttributeDescriptorCertificateSubjectAttributeName());
        Set a2 = a(a(x509AttributeCertStoreSelector, b2, b3, b4), x509AttributeCertStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509AttributeCertStoreSelector(), b2, b3, b4), x509AttributeCertStoreSelector));
        }
        return a2;
    }

    public Collection getAuthorityRevocationLists(X509CRLStoreSelector x509CRLStoreSelector) {
        String[] b2 = b(this.a.getAuthorityRevocationListAttribute());
        String[] b3 = b(this.a.getLdapAuthorityRevocationListAttributeName());
        String[] b4 = b(this.a.getAuthorityRevocationListIssuerAttributeName());
        Set a2 = a(a(x509CRLStoreSelector, b2, b3, b4), x509CRLStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CRLStoreSelector(), b2, b3, b4), x509CRLStoreSelector));
        }
        return a2;
    }

    public Collection getCACertificates(X509CertStoreSelector x509CertStoreSelector) {
        String[] b2 = b(this.a.getCACertificateAttribute());
        String[] b3 = b(this.a.getLdapCACertificateAttributeName());
        String[] b4 = b(this.a.getCACertificateSubjectAttributeName());
        Set a2 = a(a(x509CertStoreSelector, b2, b3, b4), x509CertStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CertStoreSelector(), b2, b3, b4), x509CertStoreSelector));
        }
        return a2;
    }

    public Collection getCertificateRevocationLists(X509CRLStoreSelector x509CRLStoreSelector) {
        String[] b2 = b(this.a.getCertificateRevocationListAttribute());
        String[] b3 = b(this.a.getLdapCertificateRevocationListAttributeName());
        String[] b4 = b(this.a.getCertificateRevocationListIssuerAttributeName());
        Set a2 = a(a(x509CRLStoreSelector, b2, b3, b4), x509CRLStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CRLStoreSelector(), b2, b3, b4), x509CRLStoreSelector));
        }
        return a2;
    }

    public Collection getCrossCertificatePairs(X509CertPairStoreSelector x509CertPairStoreSelector) {
        String[] b2 = b(this.a.getCrossCertificateAttribute());
        String[] b3 = b(this.a.getLdapCrossCertificateAttributeName());
        String[] b4 = b(this.a.getCrossCertificateSubjectAttributeName());
        Set a2 = a(a(x509CertPairStoreSelector, b2, b3, b4), x509CertPairStoreSelector);
        if (a2.size() == 0) {
            X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
            X509CertPairStoreSelector x509CertPairStoreSelector2 = new X509CertPairStoreSelector();
            x509CertPairStoreSelector2.setForwardSelector(x509CertStoreSelector);
            x509CertPairStoreSelector2.setReverseSelector(x509CertStoreSelector);
            a2.addAll(a(a(x509CertPairStoreSelector2, b2, b3, b4), x509CertPairStoreSelector));
        }
        return a2;
    }

    public Collection getDeltaCertificateRevocationLists(X509CRLStoreSelector x509CRLStoreSelector) {
        String[] b2 = b(this.a.getDeltaRevocationListAttribute());
        String[] b3 = b(this.a.getLdapDeltaRevocationListAttributeName());
        String[] b4 = b(this.a.getDeltaRevocationListIssuerAttributeName());
        Set a2 = a(a(x509CRLStoreSelector, b2, b3, b4), x509CRLStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CRLStoreSelector(), b2, b3, b4), x509CRLStoreSelector));
        }
        return a2;
    }

    public Collection getUserCertificates(X509CertStoreSelector x509CertStoreSelector) {
        String[] b2 = b(this.a.getUserCertificateAttribute());
        String[] b3 = b(this.a.getLdapUserCertificateAttributeName());
        String[] b4 = b(this.a.getUserCertificateSubjectAttributeName());
        Set a2 = a(a(x509CertStoreSelector, b2, b3, b4), x509CertStoreSelector);
        if (a2.size() == 0) {
            a2.addAll(a(a(new X509CertStoreSelector(), b2, b3, b4), x509CertStoreSelector));
        }
        return a2;
    }
}
