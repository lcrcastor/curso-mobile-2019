package okhttp3.internal.platform;

import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;

class AndroidPlatform extends Platform {
    private final Class<?> a;
    private final OptionalMethod<Socket> b;
    private final OptionalMethod<Socket> c;
    private final OptionalMethod<Socket> d;
    private final OptionalMethod<Socket> e;
    private final CloseGuard f = CloseGuard.a();

    static final class AndroidCertificateChainCleaner extends CertificateChainCleaner {
        private final Object a;
        private final Method b;

        public int hashCode() {
            return 0;
        }

        AndroidCertificateChainCleaner(Object obj, Method method) {
            this.a = obj;
            this.b = method;
        }

        public List<Certificate> clean(List<Certificate> list, String str) {
            try {
                X509Certificate[] x509CertificateArr = (X509Certificate[]) list.toArray(new X509Certificate[list.size()]);
                return (List) this.b.invoke(this.a, new Object[]{x509CertificateArr, "RSA", str});
            } catch (InvocationTargetException e) {
                SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(e.getMessage());
                sSLPeerUnverifiedException.initCause(e);
                throw sSLPeerUnverifiedException;
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public boolean equals(Object obj) {
            return obj instanceof AndroidCertificateChainCleaner;
        }
    }

    static final class AndroidTrustRootIndex implements TrustRootIndex {
        private final X509TrustManager a;
        private final Method b;

        AndroidTrustRootIndex(X509TrustManager x509TrustManager, Method method) {
            this.b = method;
            this.a = x509TrustManager;
        }

        public X509Certificate findByIssuerAndSignature(X509Certificate x509Certificate) {
            try {
                TrustAnchor trustAnchor = (TrustAnchor) this.b.invoke(this.a, new Object[]{x509Certificate});
                return trustAnchor != null ? trustAnchor.getTrustedCert() : null;
            } catch (IllegalAccessException e) {
                throw Util.assertionError("unable to get issues and signature", e);
            } catch (InvocationTargetException unused) {
                return null;
            }
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AndroidTrustRootIndex)) {
                return false;
            }
            AndroidTrustRootIndex androidTrustRootIndex = (AndroidTrustRootIndex) obj;
            if (!this.a.equals(androidTrustRootIndex.a) || !this.b.equals(androidTrustRootIndex.b)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() + (this.b.hashCode() * 31);
        }
    }

    static final class CloseGuard {
        private final Method a;
        private final Method b;
        private final Method c;

        CloseGuard(Method method, Method method2, Method method3) {
            this.a = method;
            this.b = method2;
            this.c = method3;
        }

        /* access modifiers changed from: 0000 */
        public Object a(String str) {
            if (this.a != null) {
                try {
                    Object invoke = this.a.invoke(null, new Object[0]);
                    this.b.invoke(invoke, new Object[]{str});
                    return invoke;
                } catch (Exception unused) {
                }
            }
            return null;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                this.c.invoke(obj, new Object[0]);
                return true;
            } catch (Exception unused) {
                return false;
            }
        }

        static CloseGuard a() {
            Method method;
            Method method2;
            Method method3 = null;
            try {
                Class cls = Class.forName("dalvik.system.CloseGuard");
                Method method4 = cls.getMethod("get", new Class[0]);
                method = cls.getMethod("open", new Class[]{String.class});
                method2 = cls.getMethod("warnIfOpen", new Class[0]);
                method3 = method4;
            } catch (Exception unused) {
                method2 = null;
                method = null;
            }
            return new CloseGuard(method3, method, method2);
        }
    }

    AndroidPlatform(Class<?> cls, OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4) {
        this.a = cls;
        this.b = optionalMethod;
        this.c = optionalMethod2;
        this.d = optionalMethod3;
        this.e = optionalMethod4;
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) {
        try {
            socket.connect(inetSocketAddress, i);
        } catch (AssertionError e2) {
            if (Util.isAndroidGetsocknameError(e2)) {
                throw new IOException(e2);
            }
            throw e2;
        } catch (SecurityException e3) {
            IOException iOException = new IOException("Exception in connect");
            iOException.initCause(e3);
            throw iOException;
        } catch (ClassCastException e4) {
            if (VERSION.SDK_INT == 26) {
                IOException iOException2 = new IOException("Exception in connect");
                iOException2.initCause(e4);
                throw iOException2;
            }
            throw e4;
        }
    }

    /* access modifiers changed from: protected */
    public X509TrustManager trustManager(SSLSocketFactory sSLSocketFactory) {
        Object a2 = a(sSLSocketFactory, this.a, "sslParameters");
        if (a2 == null) {
            try {
                a2 = a(sSLSocketFactory, Class.forName("com.google.android.gms.org.conscrypt.SSLParametersImpl", false, sSLSocketFactory.getClass().getClassLoader()), "sslParameters");
            } catch (ClassNotFoundException unused) {
                return super.trustManager(sSLSocketFactory);
            }
        }
        X509TrustManager x509TrustManager = (X509TrustManager) a(a2, X509TrustManager.class, "x509TrustManager");
        if (x509TrustManager != null) {
            return x509TrustManager;
        }
        return (X509TrustManager) a(a2, X509TrustManager.class, "trustManager");
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
        if (str != null) {
            this.b.b(sSLSocket, Boolean.valueOf(true));
            this.c.b(sSLSocket, str);
        }
        if (this.e != null && this.e.a(sSLSocket)) {
            this.e.d(sSLSocket, a(list));
        }
    }

    @Nullable
    public String getSelectedProtocol(SSLSocket sSLSocket) {
        String str = null;
        if (this.d == null || !this.d.a(sSLSocket)) {
            return null;
        }
        byte[] bArr = (byte[]) this.d.d(sSLSocket, new Object[0]);
        if (bArr != null) {
            str = new String(bArr, Util.UTF_8);
        }
        return str;
    }

    public void log(int i, String str, Throwable th) {
        int min;
        int i2 = 5;
        if (i != 5) {
            i2 = 3;
        }
        if (th != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(10);
            sb.append(Log.getStackTraceString(th));
            str = sb.toString();
        }
        int i3 = 0;
        int length = str.length();
        while (i3 < length) {
            int indexOf = str.indexOf(10, i3);
            if (indexOf == -1) {
                indexOf = length;
            }
            while (true) {
                min = Math.min(indexOf, i3 + 4000);
                Log.println(i2, "OkHttp", str.substring(i3, min));
                if (min >= indexOf) {
                    break;
                }
                i3 = min;
            }
            i3 = min + 1;
        }
    }

    public Object getStackTraceForCloseable(String str) {
        return this.f.a(str);
    }

    public void logCloseableLeak(String str, Object obj) {
        if (!this.f.a(obj)) {
            log(5, str, null);
        }
    }

    public boolean isCleartextTrafficPermitted(String str) {
        try {
            Class cls = Class.forName("android.security.NetworkSecurityPolicy");
            return a(str, cls, cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]));
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            return super.isCleartextTrafficPermitted(str);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
            throw Util.assertionError("unable to determine cleartext support", e2);
        }
    }

    private boolean a(String str, Class<?> cls, Object obj) {
        try {
            return ((Boolean) cls.getMethod("isCleartextTrafficPermitted", new Class[]{String.class}).invoke(obj, new Object[]{str})).booleanValue();
        } catch (NoSuchMethodException unused) {
            return b(str, cls, obj);
        }
    }

    private boolean b(String str, Class<?> cls, Object obj) {
        try {
            return ((Boolean) cls.getMethod("isCleartextTrafficPermitted", new Class[0]).invoke(obj, new Object[0])).booleanValue();
        } catch (NoSuchMethodException unused) {
            return super.isCleartextTrafficPermitted(str);
        }
    }

    private static boolean b() {
        if (Security.getProvider("GMSCore_OpenSSL") != null) {
            return true;
        }
        try {
            Class.forName("android.net.Network");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager x509TrustManager) {
        try {
            Class cls = Class.forName("android.net.http.X509TrustManagerExtensions");
            return new AndroidCertificateChainCleaner(cls.getConstructor(new Class[]{X509TrustManager.class}).newInstance(new Object[]{x509TrustManager}), cls.getMethod("checkServerTrusted", new Class[]{X509Certificate[].class, String.class, String.class}));
        } catch (Exception unused) {
            return super.buildCertificateChainCleaner(x509TrustManager);
        }
    }

    public static Platform a() {
        Class cls;
        OptionalMethod optionalMethod;
        OptionalMethod optionalMethod2;
        try {
            cls = Class.forName("com.android.org.conscrypt.SSLParametersImpl");
        } catch (ClassNotFoundException unused) {
            try {
                cls = Class.forName("org.apache.harmony.xnet.provider.jsse.SSLParametersImpl");
            } catch (ClassNotFoundException unused2) {
                return null;
            }
        }
        Class cls2 = cls;
        OptionalMethod optionalMethod3 = new OptionalMethod(null, "setUseSessionTickets", Boolean.TYPE);
        OptionalMethod optionalMethod4 = new OptionalMethod(null, "setHostname", String.class);
        if (b()) {
            OptionalMethod optionalMethod5 = new OptionalMethod(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
            optionalMethod = new OptionalMethod(null, "setAlpnProtocols", byte[].class);
            optionalMethod2 = optionalMethod5;
        } else {
            optionalMethod2 = null;
            optionalMethod = null;
        }
        AndroidPlatform androidPlatform = new AndroidPlatform(cls2, optionalMethod3, optionalMethod4, optionalMethod2, optionalMethod);
        return androidPlatform;
    }

    public TrustRootIndex buildTrustRootIndex(X509TrustManager x509TrustManager) {
        try {
            Method declaredMethod = x509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[]{X509Certificate.class});
            declaredMethod.setAccessible(true);
            return new AndroidTrustRootIndex(x509TrustManager, declaredMethod);
        } catch (NoSuchMethodException unused) {
            return super.buildTrustRootIndex(x509TrustManager);
        }
    }

    public SSLContext getSSLContext() {
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 22) {
            try {
                return SSLContext.getInstance("TLSv1.2");
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        try {
            return SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException("No TLS provider", e2);
        }
    }
}
