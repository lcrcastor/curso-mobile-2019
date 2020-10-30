package com.twincoders.twinpush.sdk.communications.security;

import com.twincoders.twinpush.sdk.logging.Strings;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class TwinPushTrustManager implements X509TrustManager {
    String a = null;
    Map<String, String> b = null;
    Map<String, String> c = null;

    class CertificateInfo {
        RSAPublicKey a;
        String b = new BigInteger(1, this.a.getEncoded()).toString(16);
        Map<String, String> c;
        Map<String, String> d;

        public CertificateInfo(X509Certificate x509Certificate) {
            this.a = (RSAPublicKey) x509Certificate.getPublicKey();
            this.c = b(x509Certificate.getIssuerDN().getName());
            this.d = b(x509Certificate.getSubjectDN().getName());
        }

        private Map<String, String> b(String str) {
            HashMap hashMap = new HashMap();
            for (String split : str.split(",")) {
                String[] split2 = split.split("=");
                if (split2.length == 2) {
                    hashMap.put(split2[0], split2[1]);
                }
            }
            return hashMap;
        }

        public void a(String str) {
            if (!Strings.equalsIgnoreCase(str, this.b)) {
                throw new CertificateException(String.format("checkServerTrusted: Expected public key: %s, got public key: %s", new Object[]{this.b, str}));
            }
        }

        public void a(Map<String, String> map) {
            a(map, this.c, "issuer");
        }

        public void b(Map<String, String> map) {
            a(map, this.d, "subject");
        }

        private void a(Map<String, String> map, Map<String, String> map2, String str) {
            for (String str2 : map.keySet()) {
                String str3 = (String) map.get(str2);
                String str4 = (String) map2.get(str2);
                if (!Strings.equalsIgnoreCase(str3, str4)) {
                    throw new CertificateException(String.format("checkServerTrusted: Expected %s %s: %s, got: %s", new Object[]{str, str2, str3, str4}));
                }
            }
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
        if (x509CertificateArr == null) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
        } else if (x509CertificateArr.length <= 0) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
        } else if (str == null || !str.toUpperCase(Locale.ENGLISH).contains("RSA")) {
            throw new CertificateException(String.format("checkServerTrusted: Expected AuthType RSA, got %s", new Object[]{str}));
        } else {
            try {
                TrustManagerFactory instance = TrustManagerFactory.getInstance("X509");
                instance.init(null);
                for (TrustManager trustManager : instance.getTrustManagers()) {
                    ((X509TrustManager) trustManager).checkServerTrusted(x509CertificateArr, str);
                }
                CertificateInfo certificateInfo = new CertificateInfo(x509CertificateArr[0]);
                if (this.a != null) {
                    certificateInfo.a(this.a);
                }
                if (this.b != null) {
                    certificateInfo.a(this.b);
                }
                if (this.c != null) {
                    certificateInfo.b(this.c);
                }
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
        throw new CertificateException("Client certificates not supported!");
    }

    public void setPublicKey(String str) {
        this.a = str;
    }

    public void setIssuerChecks(Map<String, String> map) {
        this.b = map;
    }

    public void setSubjectChecks(Map<String, String> map) {
        this.c = map;
    }
}
