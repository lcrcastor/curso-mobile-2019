package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.util.Vector;

public abstract class AbstractTlsKeyExchange implements TlsKeyExchange {
    protected TlsContext context;
    protected int keyExchange;
    protected Vector supportedSignatureAlgorithms;

    protected AbstractTlsKeyExchange(int i, Vector vector) {
        this.keyExchange = i;
        this.supportedSignatureAlgorithms = vector;
    }

    public byte[] generateServerKeyExchange() {
        if (!requiresServerKeyExchange()) {
            return null;
        }
        throw new TlsFatalAlert(80);
    }

    public void init(TlsContext tlsContext) {
        Vector vector;
        this.context = tlsContext;
        ProtocolVersion clientVersion = tlsContext.getClientVersion();
        if (TlsUtils.isSignatureAlgorithmsExtensionAllowed(clientVersion)) {
            if (this.supportedSignatureAlgorithms == null) {
                int i = this.keyExchange;
                if (i != 1) {
                    if (i != 3) {
                        if (i != 5) {
                            if (i != 7) {
                                if (i != 9) {
                                    switch (i) {
                                        case 13:
                                        case 14:
                                            break;
                                        case 15:
                                        case 18:
                                        case 19:
                                            break;
                                        case 16:
                                        case 17:
                                            vector = TlsUtils.getDefaultECDSASignatureAlgorithms();
                                            break;
                                        default:
                                            switch (i) {
                                                case 21:
                                                case 24:
                                                    break;
                                                case 22:
                                                    break;
                                                case 23:
                                                    break;
                                                default:
                                                    throw new IllegalStateException("unsupported key exchange algorithm");
                                            }
                                    }
                                }
                            }
                        }
                    }
                    vector = TlsUtils.getDefaultDSSSignatureAlgorithms();
                    this.supportedSignatureAlgorithms = vector;
                }
                vector = TlsUtils.getDefaultRSASignatureAlgorithms();
                this.supportedSignatureAlgorithms = vector;
            }
        } else if (this.supportedSignatureAlgorithms != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("supported_signature_algorithms not allowed for ");
            sb.append(clientVersion);
            throw new IllegalStateException(sb.toString());
        }
    }

    public void processClientCertificate(Certificate certificate) {
    }

    public void processClientKeyExchange(InputStream inputStream) {
        throw new TlsFatalAlert(80);
    }

    public void processServerCertificate(Certificate certificate) {
        Vector vector = this.supportedSignatureAlgorithms;
    }

    public void processServerCredentials(TlsCredentials tlsCredentials) {
        processServerCertificate(tlsCredentials.getCertificate());
    }

    public void processServerKeyExchange(InputStream inputStream) {
        if (!requiresServerKeyExchange()) {
            throw new TlsFatalAlert(10);
        }
    }

    public boolean requiresServerKeyExchange() {
        return false;
    }

    public void skipClientCredentials() {
    }

    public void skipServerKeyExchange() {
        if (requiresServerKeyExchange()) {
            throw new TlsFatalAlert(10);
        }
    }
}
