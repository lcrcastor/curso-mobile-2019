package com.squareup.okhttp.internal;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

public class Platform {
    private static final Platform a = a();

    static class Android extends Platform {
        private final OptionalMethod<Socket> a;
        private final OptionalMethod<Socket> b;
        private final Method c;
        private final Method d;
        private final OptionalMethod<Socket> e;
        private final OptionalMethod<Socket> f;

        public Android(OptionalMethod<Socket> optionalMethod, OptionalMethod<Socket> optionalMethod2, Method method, Method method2, OptionalMethod<Socket> optionalMethod3, OptionalMethod<Socket> optionalMethod4) {
            this.a = optionalMethod;
            this.b = optionalMethod2;
            this.c = method;
            this.d = method2;
            this.e = optionalMethod3;
            this.f = optionalMethod4;
        }

        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) {
            try {
                socket.connect(inetSocketAddress, i);
            } catch (SecurityException e2) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e2);
                throw iOException;
            }
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            if (str != null) {
                this.a.b(sSLSocket, Boolean.valueOf(true));
                this.b.b(sSLSocket, str);
            }
            if (this.f != null && this.f.a(sSLSocket)) {
                this.f.d(sSLSocket, a(list));
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            String str = null;
            if (this.e == null || !this.e.a(sSLSocket)) {
                return null;
            }
            byte[] bArr = (byte[]) this.e.d(sSLSocket, new Object[0]);
            if (bArr != null) {
                str = new String(bArr, Util.UTF_8);
            }
            return str;
        }

        public void tagSocket(Socket socket) {
            if (this.c != null) {
                try {
                    this.c.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e2) {
                    throw new RuntimeException(e2);
                } catch (InvocationTargetException e3) {
                    throw new RuntimeException(e3.getCause());
                }
            }
        }

        public void untagSocket(Socket socket) {
            if (this.d != null) {
                try {
                    this.d.invoke(null, new Object[]{socket});
                } catch (IllegalAccessException e2) {
                    throw new RuntimeException(e2);
                } catch (InvocationTargetException e3) {
                    throw new RuntimeException(e3.getCause());
                }
            }
        }
    }

    static class JdkWithJettyBootPlatform extends Platform {
        private final Method a;
        private final Method b;
        private final Method c;
        private final Class<?> d;
        private final Class<?> e;

        public JdkWithJettyBootPlatform(Method method, Method method2, Method method3, Class<?> cls, Class<?> cls2) {
            this.a = method;
            this.b = method2;
            this.c = method3;
            this.d = cls;
            this.e = cls2;
        }

        public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            for (int i = 0; i < size; i++) {
                Protocol protocol = (Protocol) list.get(i);
                if (protocol != Protocol.HTTP_1_0) {
                    arrayList.add(protocol.toString());
                }
            }
            try {
                Object newProxyInstance = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.d, this.e}, new JettyNegoProvider(arrayList));
                this.a.invoke(null, new Object[]{sSLSocket, newProxyInstance});
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new AssertionError(e2);
            }
        }

        public void afterHandshake(SSLSocket sSLSocket) {
            try {
                this.c.invoke(null, new Object[]{sSLSocket});
            } catch (IllegalAccessException | InvocationTargetException unused) {
                throw new AssertionError();
            }
        }

        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                Object[] objArr = {sSLSocket};
                String str = null;
                JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.b.invoke(null, objArr));
                if (jettyNegoProvider.b || jettyNegoProvider.c != null) {
                    if (!jettyNegoProvider.b) {
                        str = jettyNegoProvider.c;
                    }
                    return str;
                }
                Internal.logger.log(Level.INFO, "ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?");
                return null;
            } catch (IllegalAccessException | InvocationTargetException unused) {
                throw new AssertionError();
            }
        }
    }

    static class JettyNegoProvider implements InvocationHandler {
        private final List<String> a;
        /* access modifiers changed from: private */
        public boolean b;
        /* access modifiers changed from: private */
        public String c;

        public JettyNegoProvider(List<String> list) {
            this.a = list;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            String name = method.getName();
            Class<String> returnType = method.getReturnType();
            if (objArr == null) {
                objArr = Util.EMPTY_STRING_ARRAY;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.b = true;
                return null;
            } else if (name.equals("protocols") && objArr.length == 0) {
                return this.a;
            } else {
                if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                    List list = (List) objArr[0];
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (this.a.contains(list.get(i))) {
                            String str = (String) list.get(i);
                            this.c = str;
                            return str;
                        }
                    }
                    String str2 = (String) this.a.get(0);
                    this.c = str2;
                    return str2;
                } else if ((!name.equals("protocolSelected") && !name.equals("selected")) || objArr.length != 1) {
                    return method.invoke(this, objArr);
                } else {
                    this.c = (String) objArr[0];
                    return null;
                }
            }
        }
    }

    public void afterHandshake(SSLSocket sSLSocket) {
    }

    public void configureTlsExtensions(SSLSocket sSLSocket, String str, List<Protocol> list) {
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void tagSocket(Socket socket) {
    }

    public void untagSocket(Socket socket) {
    }

    public static Platform get() {
        return a;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) {
        socket.connect(inetSocketAddress, i);
    }

    private static Platform a() {
        OptionalMethod optionalMethod;
        Method method;
        Method method2;
        try {
            Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
        } catch (ClassNotFoundException unused) {
            try {
                Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
            } catch (ClassNotFoundException unused2) {
                String str = "org.eclipse.jetty.alpn.ALPN";
                try {
                    Class cls = Class.forName(str);
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("$Provider");
                    Class cls2 = Class.forName(sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("$ClientProvider");
                    Class cls3 = Class.forName(sb2.toString());
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append("$ServerProvider");
                    Class cls4 = Class.forName(sb3.toString());
                    JdkWithJettyBootPlatform jdkWithJettyBootPlatform = new JdkWithJettyBootPlatform(cls.getMethod("put", new Class[]{SSLSocket.class, cls2}), cls.getMethod("get", new Class[]{SSLSocket.class}), cls.getMethod(ProductAction.ACTION_REMOVE, new Class[]{SSLSocket.class}), cls3, cls4);
                    return jdkWithJettyBootPlatform;
                } catch (ClassNotFoundException | NoSuchMethodException unused3) {
                    return new Platform();
                }
            }
        }
        OptionalMethod optionalMethod2 = null;
        OptionalMethod optionalMethod3 = new OptionalMethod(null, "setUseSessionTickets", Boolean.TYPE);
        OptionalMethod optionalMethod4 = new OptionalMethod(null, "setHostname", String.class);
        try {
            Class cls5 = Class.forName("android.net.TrafficStats");
            method = cls5.getMethod("tagSocket", new Class[]{Socket.class});
            try {
                method2 = cls5.getMethod("untagSocket", new Class[]{Socket.class});
                try {
                    Class.forName("android.net.Network");
                    optionalMethod = new OptionalMethod(byte[].class, "getAlpnSelectedProtocol", new Class[0]);
                    try {
                        optionalMethod2 = new OptionalMethod(null, "setAlpnProtocols", byte[].class);
                    } catch (ClassNotFoundException | NoSuchMethodException unused4) {
                    }
                } catch (ClassNotFoundException | NoSuchMethodException unused5) {
                    optionalMethod = null;
                }
            } catch (ClassNotFoundException | NoSuchMethodException unused6) {
                method2 = null;
                optionalMethod = null;
            }
        } catch (ClassNotFoundException | NoSuchMethodException unused7) {
            method2 = null;
            method = null;
            optionalMethod = null;
        }
        Android android2 = new Android(optionalMethod3, optionalMethod4, method, method2, optionalMethod, optionalMethod2);
        return android2;
    }

    static byte[] a(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = (Protocol) list.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.writeByte(protocol.toString().length());
                buffer.writeUtf8(protocol.toString());
            }
        }
        return buffer.readByteArray();
    }
}
