package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;
import okhttp3.ConnectionSpec;
import okhttp3.internal.Internal;

public final class ConnectionSpecSelector {
    private final List<ConnectionSpec> a;
    private int b = 0;
    private boolean c;
    private boolean d;

    public ConnectionSpecSelector(List<ConnectionSpec> list) {
        this.a = list;
    }

    public ConnectionSpec configureSecureSocket(SSLSocket sSLSocket) {
        ConnectionSpec connectionSpec;
        int i = this.b;
        int size = this.a.size();
        while (true) {
            if (i >= size) {
                connectionSpec = null;
                break;
            }
            connectionSpec = (ConnectionSpec) this.a.get(i);
            if (connectionSpec.isCompatible(sSLSocket)) {
                this.b = i + 1;
                break;
            }
            i++;
        }
        if (connectionSpec == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to find acceptable protocols. isFallback=");
            sb.append(this.d);
            sb.append(", modes=");
            sb.append(this.a);
            sb.append(", supported protocols=");
            sb.append(Arrays.toString(sSLSocket.getEnabledProtocols()));
            throw new UnknownServiceException(sb.toString());
        }
        this.c = a(sSLSocket);
        Internal.instance.apply(connectionSpec, sSLSocket, this.d);
        return connectionSpec;
    }

    public boolean connectionFailed(IOException iOException) {
        boolean z = true;
        this.d = true;
        if (!this.c || (iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) {
            return false;
        }
        boolean z2 = iOException instanceof SSLHandshakeException;
        if ((z2 && (iOException.getCause() instanceof CertificateException)) || (iOException instanceof SSLPeerUnverifiedException)) {
            return false;
        }
        if (!z2 && !(iOException instanceof SSLProtocolException)) {
            z = false;
        }
        return z;
    }

    private boolean a(SSLSocket sSLSocket) {
        for (int i = this.b; i < this.a.size(); i++) {
            if (((ConnectionSpec) this.a.get(i)).isCompatible(sSLSocket)) {
                return true;
            }
        }
        return false;
    }
}
