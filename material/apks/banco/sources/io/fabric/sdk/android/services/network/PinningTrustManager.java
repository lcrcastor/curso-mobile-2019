package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
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

class PinningTrustManager implements X509TrustManager {
    private static final X509Certificate[] a = new X509Certificate[0];
    private final TrustManager[] b;
    private final SystemKeyStore c;
    private final long d;
    private final List<byte[]> e = new LinkedList();
    private final Set<X509Certificate> f = Collections.synchronizedSet(new HashSet());

    public PinningTrustManager(SystemKeyStore systemKeyStore, PinningInfoProvider pinningInfoProvider) {
        this.b = a(systemKeyStore);
        this.c = systemKeyStore;
        this.d = pinningInfoProvider.getPinCreationTimeInMillis();
        for (String a2 : pinningInfoProvider.getPins()) {
            this.e.add(a(a2));
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
            for (byte[] equals : this.e) {
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
        for (TrustManager trustManager : this.b) {
            ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, str);
        }
    }

    private void a(X509Certificate[] x509CertificateArr) {
        if (this.d == -1 || System.currentTimeMillis() - this.d <= 15552000000L) {
            X509Certificate[] a2 = CertificateChainCleaner.a(x509CertificateArr, this.c);
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
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate pins are stale, (");
        sb.append(System.currentTimeMillis() - this.d);
        sb.append(" millis vs ");
        sb.append(15552000000L);
        sb.append(" millis) ");
        sb.append("falling back to system trust.");
        logger.w(str, sb.toString());
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        throw new CertificateException("Client certificates not supported!");
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        if (!this.f.contains(x509CertificateArr[0])) {
            a(x509CertificateArr, str);
            a(x509CertificateArr);
            this.f.add(x509CertificateArr[0]);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return a;
    }

    private byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
