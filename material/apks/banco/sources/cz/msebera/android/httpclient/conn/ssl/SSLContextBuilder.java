package cz.msebera.android.httpclient.conn.ssl;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

@NotThreadSafe
@Deprecated
public class SSLContextBuilder {
    private String a;
    private Set<KeyManager> b = new LinkedHashSet();
    private Set<TrustManager> c = new LinkedHashSet();
    private SecureRandom d;

    static class KeyManagerDelegate implements X509KeyManager {
        private final X509KeyManager a;
        private final PrivateKeyStrategy b;

        KeyManagerDelegate(X509KeyManager x509KeyManager, PrivateKeyStrategy privateKeyStrategy) {
            this.a = x509KeyManager;
            this.b = privateKeyStrategy;
        }

        public String[] getClientAliases(String str, Principal[] principalArr) {
            return this.a.getClientAliases(str, principalArr);
        }

        public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
            HashMap hashMap = new HashMap();
            for (String str : strArr) {
                String[] clientAliases = this.a.getClientAliases(str, principalArr);
                if (clientAliases != null) {
                    for (String str2 : clientAliases) {
                        hashMap.put(str2, new PrivateKeyDetails(str, this.a.getCertificateChain(str2)));
                    }
                }
            }
            return this.b.chooseAlias(hashMap, socket);
        }

        public String[] getServerAliases(String str, Principal[] principalArr) {
            return this.a.getServerAliases(str, principalArr);
        }

        public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
            HashMap hashMap = new HashMap();
            String[] serverAliases = this.a.getServerAliases(str, principalArr);
            if (serverAliases != null) {
                for (String str2 : serverAliases) {
                    hashMap.put(str2, new PrivateKeyDetails(str, this.a.getCertificateChain(str2)));
                }
            }
            return this.b.chooseAlias(hashMap, socket);
        }

        public X509Certificate[] getCertificateChain(String str) {
            return this.a.getCertificateChain(str);
        }

        public PrivateKey getPrivateKey(String str) {
            return this.a.getPrivateKey(str);
        }
    }

    static class TrustManagerDelegate implements X509TrustManager {
        private final X509TrustManager a;
        private final TrustStrategy b;

        TrustManagerDelegate(X509TrustManager x509TrustManager, TrustStrategy trustStrategy) {
            this.a = x509TrustManager;
            this.b = trustStrategy;
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            this.a.checkClientTrusted(x509CertificateArr, str);
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            if (!this.b.isTrusted(x509CertificateArr, str)) {
                this.a.checkServerTrusted(x509CertificateArr, str);
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return this.a.getAcceptedIssuers();
        }
    }

    public SSLContextBuilder useTLS() {
        this.a = "TLS";
        return this;
    }

    public SSLContextBuilder useSSL() {
        this.a = "SSL";
        return this;
    }

    public SSLContextBuilder useProtocol(String str) {
        this.a = str;
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        this.d = secureRandom;
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore, TrustStrategy trustStrategy) {
        TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore);
        TrustManager[] trustManagers = instance.getTrustManagers();
        if (trustManagers != null) {
            if (trustStrategy != null) {
                for (int i = 0; i < trustManagers.length; i++) {
                    TrustManager trustManager = trustManagers[i];
                    if (trustManager instanceof X509TrustManager) {
                        trustManagers[i] = new TrustManagerDelegate((X509TrustManager) trustManager, trustStrategy);
                    }
                }
            }
            for (TrustManager add : trustManagers) {
                this.c.add(add);
            }
        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore) {
        return loadTrustMaterial(keyStore, null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr) {
        loadKeyMaterial(keyStore, cArr, null);
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr, PrivateKeyStrategy privateKeyStrategy) {
        KeyManagerFactory instance = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore, cArr);
        KeyManager[] keyManagers = instance.getKeyManagers();
        if (keyManagers != null) {
            if (privateKeyStrategy != null) {
                for (int i = 0; i < keyManagers.length; i++) {
                    KeyManager keyManager = keyManagers[i];
                    if (keyManager instanceof X509KeyManager) {
                        keyManagers[i] = new KeyManagerDelegate((X509KeyManager) keyManager, privateKeyStrategy);
                    }
                }
            }
            for (KeyManager add : keyManagers) {
                this.b.add(add);
            }
        }
        return this;
    }

    public SSLContext build() {
        SSLContext instance = SSLContext.getInstance(this.a != null ? this.a : "TLS");
        TrustManager[] trustManagerArr = null;
        KeyManager[] keyManagerArr = !this.b.isEmpty() ? (KeyManager[]) this.b.toArray(new KeyManager[this.b.size()]) : null;
        if (!this.c.isEmpty()) {
            trustManagerArr = (TrustManager[]) this.c.toArray(new TrustManager[this.c.size()]);
        }
        instance.init(keyManagerArr, trustManagerArr, this.d);
        return instance;
    }
}
