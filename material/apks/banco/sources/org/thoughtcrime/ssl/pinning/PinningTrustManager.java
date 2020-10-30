package org.thoughtcrime.ssl.pinning;

import android.util.Log;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class PinningTrustManager implements X509TrustManager {
    private final TrustManager[] a;
    private final SystemKeyStore b;
    private final long c;
    private final List<byte[]> d = new LinkedList();
    private final Set<X509Certificate> e = Collections.synchronizedSet(new HashSet());

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public PinningTrustManager(SystemKeyStore systemKeyStore, String[] strArr, long j) {
        this.a = a(systemKeyStore);
        this.b = systemKeyStore;
        this.c = j;
        for (String a2 : strArr) {
            this.d.add(a(a2));
        }
    }

    private TrustManager[] a(SystemKeyStore systemKeyStore) {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
            instance.init(systemKeyStore.a);
            return instance.getTrustManagers();
        } catch (NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        } catch (KeyStoreException e3) {
            throw new AssertionError(e3);
        }
    }

    private boolean a(X509Certificate x509Certificate) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(x509Certificate.getPublicKey().getEncoded());
            for (byte[] equals : this.d) {
                if (Arrays.equals(equals, digest)) {
                    return true;
                }
            }
            return false;
        } catch (NoSuchAlgorithmException e2) {
            throw new CertificateException(e2);
        }
    }

    private void a(X509Certificate[] x509CertificateArr, String str) {
        for (TrustManager trustManager : this.a) {
            ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, str);
        }
    }

    private void a(X509Certificate[] x509CertificateArr) {
        if (this.c == 0 || System.currentTimeMillis() <= this.c) {
            X509Certificate[] a2 = CertificateChainCleaner.a(x509CertificateArr, this.b);
            int length = a2.length;
            int i = 0;
            while (i < length) {
                if (!a(a2[i])) {
                    i++;
                } else {
                    return;
                }
            }
            throw new CertificateException("No valid pins found in chain!");
        }
        Log.w("PinningTrustManager", "Certificate pins are stale, falling back to system trust.");
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        throw new CertificateException("Client certificates not supported!");
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        if (!this.e.contains(x509CertificateArr[0])) {
            a(x509CertificateArr, str);
            a(x509CertificateArr);
            this.e.add(x509CertificateArr[0]);
        }
    }

    private byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public void clearCache() {
        this.e.clear();
    }
}
