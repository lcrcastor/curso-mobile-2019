package cz.msebera.android.httpclient.ssl;

import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.util.Args;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509TrustManager;

@NotThreadSafe
public class SSLContextBuilder {
    private String a;
    private final Set<KeyManager> b = new LinkedHashSet();
    private final Set<TrustManager> c = new LinkedHashSet();
    private SecureRandom d;

    static class KeyManagerDelegate extends X509ExtendedKeyManager {
        private final X509ExtendedKeyManager a;
        private final PrivateKeyStrategy b;

        KeyManagerDelegate(X509ExtendedKeyManager x509ExtendedKeyManager, PrivateKeyStrategy privateKeyStrategy) {
            this.a = x509ExtendedKeyManager;
            this.b = privateKeyStrategy;
        }

        public String[] getClientAliases(String str, Principal[] principalArr) {
            return this.a.getClientAliases(str, principalArr);
        }

        public Map<String, PrivateKeyDetails> a(String[] strArr, Principal[] principalArr) {
            HashMap hashMap = new HashMap();
            for (String str : strArr) {
                String[] clientAliases = this.a.getClientAliases(str, principalArr);
                if (clientAliases != null) {
                    for (String str2 : clientAliases) {
                        hashMap.put(str2, new PrivateKeyDetails(str, this.a.getCertificateChain(str2)));
                    }
                }
            }
            return hashMap;
        }

        public Map<String, PrivateKeyDetails> a(String str, Principal[] principalArr) {
            HashMap hashMap = new HashMap();
            String[] serverAliases = this.a.getServerAliases(str, principalArr);
            if (serverAliases != null) {
                for (String str2 : serverAliases) {
                    hashMap.put(str2, new PrivateKeyDetails(str, this.a.getCertificateChain(str2)));
                }
            }
            return hashMap;
        }

        public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
            return this.b.chooseAlias(a(strArr, principalArr), socket);
        }

        public String[] getServerAliases(String str, Principal[] principalArr) {
            return this.a.getServerAliases(str, principalArr);
        }

        public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
            return this.b.chooseAlias(a(str, principalArr), socket);
        }

        public X509Certificate[] getCertificateChain(String str) {
            return this.a.getCertificateChain(str);
        }

        public PrivateKey getPrivateKey(String str) {
            return this.a.getPrivateKey(str);
        }

        public String chooseEngineClientAlias(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
            return this.b.chooseAlias(a(strArr, principalArr), null);
        }

        public String chooseEngineServerAlias(String str, Principal[] principalArr, SSLEngine sSLEngine) {
            return this.b.chooseAlias(a(str, principalArr), null);
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

    public static SSLContextBuilder create() {
        return new SSLContextBuilder();
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

    public SSLContextBuilder loadTrustMaterial(TrustStrategy trustStrategy) {
        return loadTrustMaterial((KeyStore) null, trustStrategy);
    }

    /* JADX INFO: finally extract failed */
    public SSLContextBuilder loadTrustMaterial(File file, char[] cArr, TrustStrategy trustStrategy) {
        Args.notNull(file, "Truststore file");
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            instance.load(fileInputStream, cArr);
            fileInputStream.close();
            return loadTrustMaterial(instance, trustStrategy);
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
    }

    public SSLContextBuilder loadTrustMaterial(File file, char[] cArr) {
        return loadTrustMaterial(file, cArr, (TrustStrategy) null);
    }

    public SSLContextBuilder loadTrustMaterial(File file) {
        return loadTrustMaterial(file, (char[]) null);
    }

    /* JADX INFO: finally extract failed */
    public SSLContextBuilder loadTrustMaterial(URL url, char[] cArr, TrustStrategy trustStrategy) {
        Args.notNull(url, "Truststore URL");
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream openStream = url.openStream();
        try {
            instance.load(openStream, cArr);
            openStream.close();
            return loadTrustMaterial(instance, trustStrategy);
        } catch (Throwable th) {
            openStream.close();
            throw th;
        }
    }

    public SSLContextBuilder loadTrustMaterial(URL url, char[] cArr) {
        return loadTrustMaterial(url, cArr, (TrustStrategy) null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr, PrivateKeyStrategy privateKeyStrategy) {
        KeyManagerFactory instance = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        instance.init(keyStore, cArr);
        KeyManager[] keyManagers = instance.getKeyManagers();
        if (keyManagers != null) {
            if (privateKeyStrategy != null) {
                for (int i = 0; i < keyManagers.length; i++) {
                    KeyManager keyManager = keyManagers[i];
                    if (keyManager instanceof X509ExtendedKeyManager) {
                        keyManagers[i] = new KeyManagerDelegate((X509ExtendedKeyManager) keyManager, privateKeyStrategy);
                    }
                }
            }
            for (KeyManager add : keyManagers) {
                this.b.add(add);
            }
        }
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArr) {
        return loadKeyMaterial(keyStore, cArr, (PrivateKeyStrategy) null);
    }

    /* JADX INFO: finally extract failed */
    public SSLContextBuilder loadKeyMaterial(File file, char[] cArr, char[] cArr2, PrivateKeyStrategy privateKeyStrategy) {
        Args.notNull(file, "Keystore file");
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            instance.load(fileInputStream, cArr);
            fileInputStream.close();
            return loadKeyMaterial(instance, cArr2, privateKeyStrategy);
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
    }

    public SSLContextBuilder loadKeyMaterial(File file, char[] cArr, char[] cArr2) {
        return loadKeyMaterial(file, cArr, cArr2, (PrivateKeyStrategy) null);
    }

    /* JADX INFO: finally extract failed */
    public SSLContextBuilder loadKeyMaterial(URL url, char[] cArr, char[] cArr2, PrivateKeyStrategy privateKeyStrategy) {
        Args.notNull(url, "Keystore URL");
        KeyStore instance = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream openStream = url.openStream();
        try {
            instance.load(openStream, cArr);
            openStream.close();
            return loadKeyMaterial(instance, cArr2, privateKeyStrategy);
        } catch (Throwable th) {
            openStream.close();
            throw th;
        }
    }

    public SSLContextBuilder loadKeyMaterial(URL url, char[] cArr, char[] cArr2) {
        return loadKeyMaterial(url, cArr, cArr2, (PrivateKeyStrategy) null);
    }

    /* access modifiers changed from: protected */
    public void initSSLContext(SSLContext sSLContext, Collection<KeyManager> collection, Collection<TrustManager> collection2, SecureRandom secureRandom) {
        TrustManager[] trustManagerArr = null;
        KeyManager[] keyManagerArr = !collection.isEmpty() ? (KeyManager[]) collection.toArray(new KeyManager[collection.size()]) : null;
        if (!collection2.isEmpty()) {
            trustManagerArr = (TrustManager[]) collection2.toArray(new TrustManager[collection2.size()]);
        }
        sSLContext.init(keyManagerArr, trustManagerArr, secureRandom);
    }

    public SSLContext build() {
        SSLContext instance = SSLContext.getInstance(this.a != null ? this.a : "TLS");
        initSSLContext(instance, this.b, this.c, this.d);
        return instance;
    }
}
