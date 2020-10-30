package org.bouncycastle.jce.provider;

import io.reactivex.annotations.SchedulerSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;

public class X509LDAPCertStoreSpi extends CertStoreSpi {
    private static String b = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String c = "ignore";
    private X509LDAPCertStoreParameters a;

    public X509LDAPCertStoreSpi(CertStoreParameters certStoreParameters) {
        super(certStoreParameters);
        if (!(certStoreParameters instanceof X509LDAPCertStoreParameters)) {
            StringBuilder sb = new StringBuilder();
            sb.append(X509LDAPCertStoreSpi.class.getName());
            sb.append(": parameter must be a ");
            sb.append(X509LDAPCertStoreParameters.class.getName());
            sb.append(" object\n");
            sb.append(certStoreParameters.toString());
            throw new InvalidAlgorithmParameterException(sb.toString());
        }
        this.a = (X509LDAPCertStoreParameters) certStoreParameters;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0035 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x004e  */
    private java.lang.String a(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r0 = r5.toLowerCase()
            java.lang.String r1 = r6.toLowerCase()
            int r0 = r0.indexOf(r1)
            int r6 = r6.length()
            int r0 = r0 + r6
            java.lang.String r5 = r5.substring(r0)
            r6 = 44
            int r0 = r5.indexOf(r6)
            r1 = -1
            if (r0 != r1) goto L_0x0022
        L_0x001e:
            int r0 = r5.length()
        L_0x0022:
            int r2 = r0 + -1
            char r2 = r5.charAt(r2)
            r3 = 92
            if (r2 != r3) goto L_0x0035
            int r0 = r0 + 1
            int r0 = r5.indexOf(r6, r0)
            if (r0 != r1) goto L_0x0022
            goto L_0x001e
        L_0x0035:
            r6 = 0
            java.lang.String r5 = r5.substring(r6, r0)
            r0 = 61
            int r0 = r5.indexOf(r0)
            r1 = 1
            int r0 = r0 + r1
            java.lang.String r5 = r5.substring(r0)
            char r0 = r5.charAt(r6)
            r2 = 32
            if (r0 != r2) goto L_0x0052
            java.lang.String r5 = r5.substring(r1)
        L_0x0052:
            java.lang.String r0 = "\""
            boolean r0 = r5.startsWith(r0)
            if (r0 == 0) goto L_0x005e
            java.lang.String r5 = r5.substring(r1)
        L_0x005e:
            java.lang.String r0 = "\""
            boolean r0 = r5.endsWith(r0)
            if (r0 == 0) goto L_0x006f
            int r0 = r5.length()
            int r0 = r0 - r1
            java.lang.String r5 = r5.substring(r6, r0)
        L_0x006f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509LDAPCertStoreSpi.a(java.lang.String, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d9 A[SYNTHETIC, Splitter:B:36:0x00d9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set a(java.lang.String r8, java.lang.String r9, java.lang.String[] r10) {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            java.lang.String r1 = "="
            r0.append(r1)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            r0 = 0
            if (r8 != 0) goto L_0x0018
            r9 = r0
        L_0x0018:
            java.util.HashSet r8 = new java.util.HashSet
            r8.<init>()
            javax.naming.directory.DirContext r1 = r7.a()     // Catch:{ Exception -> 0x00bf }
            javax.naming.directory.SearchControls r0 = new javax.naming.directory.SearchControls     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r0.<init>()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r2 = 2
            r0.setSearchScope(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r2 = 0
            r0.setCountLimit(r2)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r2 = 0
            r3 = 0
        L_0x0031:
            int r4 = r10.length     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            if (r3 >= r4) goto L_0x00b1
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5 = r10[r3]     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r4[r2] = r5     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r0.setReturningAttributes(r4)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5.<init>()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r6 = "(&("
            r5.append(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5.append(r9)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r6 = ")("
            r5.append(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r6 = r4[r2]     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5.append(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r6 = "=*))"
            r5.append(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            if (r9 != 0) goto L_0x0078
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5.<init>()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r6 = "("
            r5.append(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r4 = r4[r2]     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r5.append(r4)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r4 = "=*)"
            r5.append(r4)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
        L_0x0078:
            org.bouncycastle.jce.X509LDAPCertStoreParameters r4 = r7.a     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.String r4 = r4.getBaseDN()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.NamingEnumeration r4 = r1.search(r4, r5, r0)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
        L_0x0082:
            boolean r5 = r4.hasMoreElements()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            if (r5 == 0) goto L_0x00ae
            java.lang.Object r5 = r4.next()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.directory.SearchResult r5 = (javax.naming.directory.SearchResult) r5     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.directory.Attributes r5 = r5.getAttributes()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.NamingEnumeration r5 = r5.getAll()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            java.lang.Object r5 = r5.next()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.directory.Attribute r5 = (javax.naming.directory.Attribute) r5     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            javax.naming.NamingEnumeration r5 = r5.getAll()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
        L_0x00a0:
            boolean r6 = r5.hasMore()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            if (r6 == 0) goto L_0x0082
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            r8.add(r6)     // Catch:{ Exception -> 0x00b9, all -> 0x00b7 }
            goto L_0x00a0
        L_0x00ae:
            int r3 = r3 + 1
            goto L_0x0031
        L_0x00b1:
            if (r1 == 0) goto L_0x00b6
            r1.close()     // Catch:{ Exception -> 0x00b6 }
        L_0x00b6:
            return r8
        L_0x00b7:
            r8 = move-exception
            goto L_0x00d7
        L_0x00b9:
            r8 = move-exception
            r0 = r1
            goto L_0x00c0
        L_0x00bc:
            r8 = move-exception
            r1 = r0
            goto L_0x00d7
        L_0x00bf:
            r8 = move-exception
        L_0x00c0:
            java.security.cert.CertStoreException r9 = new java.security.cert.CertStoreException     // Catch:{ all -> 0x00bc }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bc }
            r10.<init>()     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = "Error getting results from LDAP directory "
            r10.append(r1)     // Catch:{ all -> 0x00bc }
            r10.append(r8)     // Catch:{ all -> 0x00bc }
            java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x00bc }
            r9.<init>(r8)     // Catch:{ all -> 0x00bc }
            throw r9     // Catch:{ all -> 0x00bc }
        L_0x00d7:
            if (r1 == 0) goto L_0x00dc
            r1.close()     // Catch:{ Exception -> 0x00dc }
        L_0x00dc:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509LDAPCertStoreSpi.a(java.lang.String, java.lang.String, java.lang.String[]):java.util.Set");
    }

    private Set a(X509CertSelector x509CertSelector) {
        return a(x509CertSelector, new String[]{this.a.getUserCertificateAttribute()}, this.a.getLdapUserCertificateAttributeName(), this.a.getUserCertificateSubjectAttributeName());
    }

    private Set a(X509CertSelector x509CertSelector, String[] strArr, String str, String str2) {
        String str3;
        Set a2;
        HashSet hashSet = new HashSet();
        try {
            if (x509CertSelector.getSubjectAsBytes() == null && x509CertSelector.getSubjectAsString() == null) {
                if (x509CertSelector.getCertificate() == null) {
                    a2 = a(str, "*", strArr);
                    hashSet.addAll(a2);
                    return hashSet;
                }
            }
            String str4 = null;
            if (x509CertSelector.getCertificate() != null) {
                String name = x509CertSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
                str4 = x509CertSelector.getCertificate().getSerialNumber().toString();
                str3 = name;
            } else {
                str3 = x509CertSelector.getSubjectAsBytes() != null ? new X500Principal(x509CertSelector.getSubjectAsBytes()).getName("RFC1779") : x509CertSelector.getSubjectAsString();
            }
            String a3 = a(str3, str2);
            StringBuilder sb = new StringBuilder();
            sb.append("*");
            sb.append(a3);
            sb.append("*");
            hashSet.addAll(a(str, sb.toString(), strArr));
            if (str4 == null || this.a.getSearchForSerialNumberIn() == null) {
                return hashSet;
            }
            String searchForSerialNumberIn = this.a.getSearchForSerialNumberIn();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("*");
            sb2.append(str4);
            sb2.append("*");
            a2 = a(searchForSerialNumberIn, sb2.toString(), strArr);
            hashSet.addAll(a2);
            return hashSet;
        } catch (IOException e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("exception processing selector: ");
            sb3.append(e);
            throw new CertStoreException(sb3.toString());
        }
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

    private Set b(X509CertSelector x509CertSelector) {
        String[] strArr = {this.a.getCACertificateAttribute()};
        Set a2 = a(x509CertSelector, strArr, this.a.getLdapCACertificateAttributeName(), this.a.getCACertificateSubjectAttributeName());
        if (a2.isEmpty()) {
            a2.addAll(a(null, "*", strArr));
        }
        return a2;
    }

    private Set c(X509CertSelector x509CertSelector) {
        String[] strArr = {this.a.getCrossCertificateAttribute()};
        Set a2 = a(x509CertSelector, strArr, this.a.getLdapCrossCertificateAttributeName(), this.a.getCrossCertificateSubjectAttributeName());
        if (a2.isEmpty()) {
            a2.addAll(a(null, "*", strArr));
        }
        return a2;
    }

    public Collection engineGetCRLs(CRLSelector cRLSelector) {
        String certificateRevocationListIssuerAttributeName;
        String name;
        String[] strArr = {this.a.getCertificateRevocationListAttribute()};
        if (!(cRLSelector instanceof X509CRLSelector)) {
            throw new CertStoreException("selector is not a X509CRLSelector");
        }
        X509CRLSelector x509CRLSelector = (X509CRLSelector) cRLSelector;
        HashSet hashSet = new HashSet();
        String ldapCertificateRevocationListAttributeName = this.a.getLdapCertificateRevocationListAttributeName();
        HashSet<byte[]> hashSet2 = new HashSet<>();
        if (x509CRLSelector.getIssuerNames() != null) {
            for (Object next : x509CRLSelector.getIssuerNames()) {
                if (next instanceof String) {
                    certificateRevocationListIssuerAttributeName = this.a.getCertificateRevocationListIssuerAttributeName();
                    name = (String) next;
                } else {
                    certificateRevocationListIssuerAttributeName = this.a.getCertificateRevocationListIssuerAttributeName();
                    name = new X500Principal((byte[]) next).getName("RFC1779");
                }
                String a2 = a(name, certificateRevocationListIssuerAttributeName);
                StringBuilder sb = new StringBuilder();
                sb.append("*");
                sb.append(a2);
                sb.append("*");
                hashSet2.addAll(a(ldapCertificateRevocationListAttributeName, sb.toString(), strArr));
            }
        } else {
            hashSet2.addAll(a(ldapCertificateRevocationListAttributeName, "*", strArr));
        }
        hashSet2.addAll(a(null, "*", strArr));
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            for (byte[] byteArrayInputStream : hashSet2) {
                CRL generateCRL = instance.generateCRL(new ByteArrayInputStream(byteArrayInputStream));
                if (x509CRLSelector.match(generateCRL)) {
                    hashSet.add(generateCRL);
                }
            }
            return hashSet;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("CRL cannot be constructed from LDAP result ");
            sb2.append(e);
            throw new CertStoreException(sb2.toString());
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:14|15|16|(1:18)|19|(1:21)|22|23|(6:27|28|29|(2:31|43)(1:42)|41|24)|40) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x007d */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0087 A[Catch:{ Exception -> 0x00a1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection engineGetCertificates(java.security.cert.CertSelector r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof java.security.cert.X509CertSelector
            if (r0 != 0) goto L_0x000c
            java.security.cert.CertStoreException r7 = new java.security.cert.CertStoreException
            java.lang.String r0 = "selector is not a X509CertSelector"
            r7.<init>(r0)
            throw r7
        L_0x000c:
            java.security.cert.X509CertSelector r7 = (java.security.cert.X509CertSelector) r7
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Set r1 = r6.a(r7)
            java.util.Set r2 = r6.b(r7)
            r1.addAll(r2)
            java.util.Set r2 = r6.c(r7)
            r1.addAll(r2)
            java.util.Iterator r1 = r1.iterator()
            java.lang.String r2 = "X.509"
            java.lang.String r3 = "BC"
            java.security.cert.CertificateFactory r2 = java.security.cert.CertificateFactory.getInstance(r2, r3)     // Catch:{ Exception -> 0x00a1 }
        L_0x0031:
            boolean r3 = r1.hasNext()     // Catch:{ Exception -> 0x00a1 }
            if (r3 == 0) goto L_0x00a0
            java.lang.Object r3 = r1.next()     // Catch:{ Exception -> 0x00a1 }
            byte[] r3 = (byte[]) r3     // Catch:{ Exception -> 0x00a1 }
            if (r3 == 0) goto L_0x0031
            int r4 = r3.length     // Catch:{ Exception -> 0x00a1 }
            if (r4 != 0) goto L_0x0043
            goto L_0x0031
        L_0x0043:
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x00a1 }
            r4.<init>()     // Catch:{ Exception -> 0x00a1 }
            r4.add(r3)     // Catch:{ Exception -> 0x00a1 }
            org.bouncycastle.asn1.ASN1InputStream r5 = new org.bouncycastle.asn1.ASN1InputStream     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            r5.<init>(r3)     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            org.bouncycastle.asn1.ASN1Primitive r3 = r5.readObject()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            org.bouncycastle.asn1.x509.CertificatePair r3 = org.bouncycastle.asn1.x509.CertificatePair.getInstance(r3)     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            r4.clear()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            org.bouncycastle.asn1.x509.Certificate r5 = r3.getForward()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            if (r5 == 0) goto L_0x006c
            org.bouncycastle.asn1.x509.Certificate r5 = r3.getForward()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            byte[] r5 = r5.getEncoded()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            r4.add(r5)     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
        L_0x006c:
            org.bouncycastle.asn1.x509.Certificate r5 = r3.getReverse()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            if (r5 == 0) goto L_0x007d
            org.bouncycastle.asn1.x509.Certificate r3 = r3.getReverse()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            byte[] r3 = r3.getEncoded()     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
            r4.add(r3)     // Catch:{ IOException | IllegalArgumentException -> 0x007d }
        L_0x007d:
            java.util.Iterator r3 = r4.iterator()     // Catch:{ Exception -> 0x00a1 }
        L_0x0081:
            boolean r4 = r3.hasNext()     // Catch:{ Exception -> 0x00a1 }
            if (r4 == 0) goto L_0x0031
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object r5 = r3.next()     // Catch:{ Exception -> 0x00a1 }
            byte[] r5 = (byte[]) r5     // Catch:{ Exception -> 0x00a1 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x00a1 }
            java.security.cert.Certificate r4 = r2.generateCertificate(r4)     // Catch:{ Exception -> 0x0081 }
            boolean r5 = r7.match(r4)     // Catch:{ Exception -> 0x0081 }
            if (r5 == 0) goto L_0x0081
            r0.add(r4)     // Catch:{ Exception -> 0x0081 }
            goto L_0x0081
        L_0x00a0:
            return r0
        L_0x00a1:
            r7 = move-exception
            java.security.cert.CertStoreException r0 = new java.security.cert.CertStoreException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "certificate cannot be constructed from LDAP result: "
            r1.append(r2)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.<init>(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509LDAPCertStoreSpi.engineGetCertificates(java.security.cert.CertSelector):java.util.Collection");
    }
}
