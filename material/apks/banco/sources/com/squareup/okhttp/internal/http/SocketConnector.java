package com.squareup.okhttp.internal.http;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.okhttp.Address;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import cz.msebera.android.httpclient.protocol.HTTP;
import java.io.IOException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocket;
import okio.Source;

public class SocketConnector {
    private final Connection a;
    private final ConnectionPool b;

    public static class ConnectedSocket {
        public final Protocol alpnProtocol;
        public final Handshake handshake;
        public final Route route;
        public final Socket socket;

        public ConnectedSocket(Route route2, Socket socket2) {
            this.route = route2;
            this.socket = socket2;
            this.alpnProtocol = null;
            this.handshake = null;
        }

        public ConnectedSocket(Route route2, SSLSocket sSLSocket, Protocol protocol, Handshake handshake2) {
            this.route = route2;
            this.socket = sSLSocket;
            this.alpnProtocol = protocol;
            this.handshake = handshake2;
        }
    }

    public SocketConnector(Connection connection, ConnectionPool connectionPool) {
        this.a = connection;
        this.b = connectionPool;
    }

    public ConnectedSocket connectCleartext(int i, int i2, Route route) {
        return new ConnectedSocket(route, a(i2, i, route));
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0088 A[Catch:{ all -> 0x00f1, IOException -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00dc A[Catch:{ all -> 0x00f1, IOException -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x011f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.squareup.okhttp.internal.http.SocketConnector.ConnectedSocket connectTls(int r19, int r20, int r21, com.squareup.okhttp.Request r22, com.squareup.okhttp.Route r23, java.util.List<com.squareup.okhttp.ConnectionSpec> r24, boolean r25) {
        /*
            r18 = this;
            r7 = r23
            com.squareup.okhttp.Address r8 = r23.getAddress()
            com.squareup.okhttp.internal.ConnectionSpecSelector r9 = new com.squareup.okhttp.internal.ConnectionSpecSelector
            r1 = r24
            r9.<init>(r1)
            r11 = r18
            r12 = r19
            r13 = r20
            r14 = 0
        L_0x0014:
            java.net.Socket r15 = r11.a(r13, r12, r7)
            boolean r1 = r23.requiresTunnel()
            if (r1 == 0) goto L_0x0029
            r1 = r11
            r2 = r13
            r3 = r21
            r4 = r22
            r5 = r7
            r6 = r15
            r1.a(r2, r3, r4, r5, r6)
        L_0x0029:
            r1 = 0
            r2 = 1
            javax.net.ssl.SSLSocketFactory r3 = r8.getSslSocketFactory()     // Catch:{ IOException -> 0x00fb }
            java.lang.String r4 = r8.getUriHost()     // Catch:{ IOException -> 0x00fb }
            int r5 = r8.getUriPort()     // Catch:{ IOException -> 0x00fb }
            java.net.Socket r3 = r3.createSocket(r15, r4, r5, r2)     // Catch:{ IOException -> 0x00fb }
            javax.net.ssl.SSLSocket r3 = (javax.net.ssl.SSLSocket) r3     // Catch:{ IOException -> 0x00fb }
            com.squareup.okhttp.ConnectionSpec r4 = r9.configureSecureSocket(r3)     // Catch:{ IOException -> 0x00f7 }
            com.squareup.okhttp.internal.Platform r5 = com.squareup.okhttp.internal.Platform.get()     // Catch:{ IOException -> 0x00f7 }
            boolean r6 = r4.supportsTlsExtensions()     // Catch:{ all -> 0x00f1 }
            if (r6 == 0) goto L_0x0056
            java.lang.String r6 = r8.getUriHost()     // Catch:{ all -> 0x00f1 }
            java.util.List r2 = r8.getProtocols()     // Catch:{ all -> 0x00f1 }
            r5.configureTlsExtensions(r3, r6, r2)     // Catch:{ all -> 0x00f1 }
        L_0x0056:
            r3.startHandshake()     // Catch:{ all -> 0x00f1 }
            javax.net.ssl.SSLSession r2 = r3.getSession()     // Catch:{ all -> 0x00f1 }
            com.squareup.okhttp.Handshake r2 = com.squareup.okhttp.Handshake.get(r2)     // Catch:{ all -> 0x00f1 }
            boolean r4 = r4.supportsTlsExtensions()     // Catch:{ all -> 0x00f1 }
            if (r4 == 0) goto L_0x0072
            java.lang.String r4 = r5.getSelectedProtocol(r3)     // Catch:{ all -> 0x00f1 }
            if (r4 == 0) goto L_0x0072
            com.squareup.okhttp.Protocol r4 = com.squareup.okhttp.Protocol.get(r4)     // Catch:{ all -> 0x00f1 }
            goto L_0x0073
        L_0x0072:
            r4 = 0
        L_0x0073:
            r5.afterHandshake(r3)     // Catch:{ IOException -> 0x00f7 }
            javax.net.ssl.HostnameVerifier r5 = r8.getHostnameVerifier()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = r8.getUriHost()     // Catch:{ IOException -> 0x00f7 }
            javax.net.ssl.SSLSession r10 = r3.getSession()     // Catch:{ IOException -> 0x00f7 }
            boolean r5 = r5.verify(r6, r10)     // Catch:{ IOException -> 0x00f7 }
            if (r5 != 0) goto L_0x00dc
            javax.net.ssl.SSLSession r2 = r3.getSession()     // Catch:{ IOException -> 0x00f7 }
            java.security.cert.Certificate[] r2 = r2.getPeerCertificates()     // Catch:{ IOException -> 0x00f7 }
            r2 = r2[r1]     // Catch:{ IOException -> 0x00f7 }
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch:{ IOException -> 0x00f7 }
            javax.net.ssl.SSLPeerUnverifiedException r4 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ IOException -> 0x00f7 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f7 }
            r5.<init>()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = "Hostname "
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = r8.getUriHost()     // Catch:{ IOException -> 0x00f7 }
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = " not verified:"
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = "\n    certificate: "
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = com.squareup.okhttp.CertificatePinner.pin(r2)     // Catch:{ IOException -> 0x00f7 }
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = "\n    DN: "
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.security.Principal r6 = r2.getSubjectDN()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = r6.getName()     // Catch:{ IOException -> 0x00f7 }
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = "\n    subjectAltNames: "
            r5.append(r6)     // Catch:{ IOException -> 0x00f7 }
            java.util.List r2 = com.squareup.okhttp.internal.tls.OkHostnameVerifier.allSubjectAltNames(r2)     // Catch:{ IOException -> 0x00f7 }
            r5.append(r2)     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r2 = r5.toString()     // Catch:{ IOException -> 0x00f7 }
            r4.<init>(r2)     // Catch:{ IOException -> 0x00f7 }
            throw r4     // Catch:{ IOException -> 0x00f7 }
        L_0x00dc:
            com.squareup.okhttp.CertificatePinner r5 = r8.getCertificatePinner()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r6 = r8.getUriHost()     // Catch:{ IOException -> 0x00f7 }
            java.util.List r10 = r2.peerCertificates()     // Catch:{ IOException -> 0x00f7 }
            r5.check(r6, r10)     // Catch:{ IOException -> 0x00f7 }
            com.squareup.okhttp.internal.http.SocketConnector$ConnectedSocket r5 = new com.squareup.okhttp.internal.http.SocketConnector$ConnectedSocket     // Catch:{ IOException -> 0x00f7 }
            r5.<init>(r7, r3, r4, r2)     // Catch:{ IOException -> 0x00f7 }
            return r5
        L_0x00f1:
            r0 = move-exception
            r2 = r0
            r5.afterHandshake(r3)     // Catch:{ IOException -> 0x00f7 }
            throw r2     // Catch:{ IOException -> 0x00f7 }
        L_0x00f7:
            r0 = move-exception
            r4 = r3
            r3 = r0
            goto L_0x00fe
        L_0x00fb:
            r0 = move-exception
            r3 = r0
            r4 = 0
        L_0x00fe:
            if (r25 == 0) goto L_0x0109
            boolean r5 = r9.connectionFailed(r3)
            if (r5 == 0) goto L_0x0109
            r16 = 1
            goto L_0x010b
        L_0x0109:
            r16 = 0
        L_0x010b:
            com.squareup.okhttp.internal.Util.closeQuietly(r4)
            com.squareup.okhttp.internal.Util.closeQuietly(r15)
            if (r14 != 0) goto L_0x011a
            com.squareup.okhttp.internal.http.RouteException r1 = new com.squareup.okhttp.internal.http.RouteException
            r1.<init>(r3)
            r14 = r1
            goto L_0x011d
        L_0x011a:
            r14.addConnectException(r3)
        L_0x011d:
            if (r16 != 0) goto L_0x0014
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.SocketConnector.connectTls(int, int, int, com.squareup.okhttp.Request, com.squareup.okhttp.Route, java.util.List, boolean):com.squareup.okhttp.internal.http.SocketConnector$ConnectedSocket");
    }

    private Socket a(int i, int i2, Route route) {
        Socket socket;
        Platform platform = Platform.get();
        try {
            Proxy proxy = route.getProxy();
            Address address = route.getAddress();
            if (proxy.type() != Type.DIRECT) {
                if (proxy.type() != Type.HTTP) {
                    socket = new Socket(proxy);
                    socket.setSoTimeout(i);
                    platform.connectSocket(socket, route.getSocketAddress(), i2);
                    return socket;
                }
            }
            socket = address.getSocketFactory().createSocket();
            socket.setSoTimeout(i);
            platform.connectSocket(socket, route.getSocketAddress(), i2);
            return socket;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    private void a(int i, int i2, Request request, Route route, Socket socket) {
        try {
            Request a2 = a(request);
            HttpConnection httpConnection = new HttpConnection(this.b, this.a, socket);
            httpConnection.setTimeouts(i, i2);
            URL url = a2.url();
            StringBuilder sb = new StringBuilder();
            sb.append("CONNECT ");
            sb.append(url.getHost());
            sb.append(":");
            sb.append(Util.getEffectivePort(url));
            sb.append(" HTTP/1.1");
            String sb2 = sb.toString();
            while (true) {
                httpConnection.writeRequest(a2.headers(), sb2);
                httpConnection.flush();
                Response build = httpConnection.readResponse().request(a2).build();
                long contentLength = OkHeaders.contentLength(build);
                if (contentLength == -1) {
                    contentLength = 0;
                }
                Source newFixedLengthSource = httpConnection.newFixedLengthSource(contentLength);
                Util.skipAll(newFixedLengthSource, SubsamplingScaleImageView.TILE_SIZE_AUTO, TimeUnit.MILLISECONDS);
                newFixedLengthSource.close();
                int code = build.code();
                if (code != 200) {
                    if (code != 407) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Unexpected response code for CONNECT: ");
                        sb3.append(build.code());
                        throw new IOException(sb3.toString());
                    }
                    a2 = OkHeaders.processAuthHeader(route.getAddress().getAuthenticator(), build, route.getProxy());
                    if (a2 == null) {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                } else if (httpConnection.bufferSize() > 0) {
                    throw new IOException("TLS tunnel buffered too many bytes!");
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    private Request a(Request request) {
        String str;
        String host = request.url().getHost();
        int effectivePort = Util.getEffectivePort(request.url());
        if (effectivePort == Util.getDefaultPort("https")) {
            str = host;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(host);
            sb.append(":");
            sb.append(effectivePort);
            str = sb.toString();
        }
        Builder header = new Builder().url(new URL("https", host, effectivePort, "/")).header("Host", str).header("Proxy-Connection", HTTP.CONN_KEEP_ALIVE);
        String header2 = request.header("User-Agent");
        if (header2 != null) {
            header.header("User-Agent", header2);
        }
        String header3 = request.header("Proxy-Authorization");
        if (header3 != null) {
            header.header("Proxy-Authorization", header3);
        }
        return header.build();
    }
}
