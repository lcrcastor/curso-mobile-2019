package org.thoughtcrime.ssl.pinning;

import android.content.Context;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;

public class SystemKeyStore {
    private static SystemKeyStore b;
    final KeyStore a;
    private final HashMap<Principal, X509Certificate> c;

    public static synchronized SystemKeyStore getInstance(Context context) {
        SystemKeyStore systemKeyStore;
        synchronized (SystemKeyStore.class) {
            if (b == null) {
                b = new SystemKeyStore(context);
            }
            systemKeyStore = b;
        }
        return systemKeyStore;
    }

    private SystemKeyStore(Context context) {
        KeyStore a2 = a(context);
        this.c = a(a2);
        this.a = a2;
    }

    public boolean isTrustRoot(X509Certificate x509Certificate) {
        X509Certificate x509Certificate2 = (X509Certificate) this.c.get(x509Certificate.getSubjectX500Principal());
        return x509Certificate2 != null && x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
    }

    public X509Certificate getTrustRootFor(X509Certificate x509Certificate) {
        X509Certificate x509Certificate2 = (X509Certificate) this.c.get(x509Certificate.getIssuerX500Principal());
        if (x509Certificate2 == null || x509Certificate2.getSubjectX500Principal().equals(x509Certificate.getSubjectX500Principal())) {
            return null;
        }
        try {
            x509Certificate.verify(x509Certificate2.getPublicKey());
            return x509Certificate2;
        } catch (GeneralSecurityException unused) {
            return null;
        }
    }

    private HashMap<Principal, X509Certificate> a(KeyStore keyStore) {
        try {
            HashMap<Principal, X509Certificate> hashMap = new HashMap<>();
            Enumeration aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                X509Certificate x509Certificate = (X509Certificate) keyStore.getCertificate((String) aliases.nextElement());
                if (x509Certificate != null) {
                    hashMap.put(x509Certificate.getSubjectX500Principal(), x509Certificate);
                }
            }
            return hashMap;
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        android.util.Log.w("SystemKeyStore", r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0037, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0038, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        throw new java.lang.AssertionError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        throw new java.lang.AssertionError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0046, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004c, code lost:
        throw new java.lang.AssertionError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0053, code lost:
        throw new java.lang.AssertionError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0054, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x005a, code lost:
        throw new java.lang.AssertionError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0025, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003f A[ExcHandler: NotFoundException (r4v4 'e' android.content.res.Resources$NotFoundException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0046 A[ExcHandler: CertificateException (r4v3 'e' java.security.cert.CertificateException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004d A[ExcHandler: NoSuchAlgorithmException (r4v2 'e' java.security.NoSuchAlgorithmException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0054 A[ExcHandler: KeyStoreException (r4v1 'e' java.security.KeyStoreException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.security.KeyStore a(android.content.Context r4) {
        /*
            r3 = this;
            java.lang.String r0 = "BKS"
            java.security.KeyStore r0 = java.security.KeyStore.getInstance(r0)     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            int r2 = org.thoughtcrime.ssl.pinning.R.raw.cacerts     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            java.io.InputStream r4 = r4.openRawResource(r2)     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            r2 = 143360(0x23000, float:2.0089E-40)
            r1.<init>(r4, r2)     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
            java.lang.String r4 = "changeit"
            char[] r4 = r4.toCharArray()     // Catch:{ all -> 0x002c }
            r0.load(r1, r4)     // Catch:{ all -> 0x002c }
            r1.close()     // Catch:{ IOException -> 0x0025, KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f }
            goto L_0x002b
        L_0x0025:
            r4 = move-exception
            java.lang.String r1 = "SystemKeyStore"
            android.util.Log.w(r1, r4)     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
        L_0x002b:
            return r0
        L_0x002c:
            r4 = move-exception
            r1.close()     // Catch:{ IOException -> 0x0031, KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f }
            goto L_0x0037
        L_0x0031:
            r0 = move-exception
            java.lang.String r1 = "SystemKeyStore"
            android.util.Log.w(r1, r0)     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
        L_0x0037:
            throw r4     // Catch:{ KeyStoreException -> 0x0054, NoSuchAlgorithmException -> 0x004d, CertificateException -> 0x0046, NotFoundException -> 0x003f, IOException -> 0x0038 }
        L_0x0038:
            r4 = move-exception
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>(r4)
            throw r0
        L_0x003f:
            r4 = move-exception
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>(r4)
            throw r0
        L_0x0046:
            r4 = move-exception
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>(r4)
            throw r0
        L_0x004d:
            r4 = move-exception
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>(r4)
            throw r0
        L_0x0054:
            r4 = move-exception
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.thoughtcrime.ssl.pinning.SystemKeyStore.a(android.content.Context):java.security.KeyStore");
    }
}
