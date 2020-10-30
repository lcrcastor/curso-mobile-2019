package org.bouncycastle.crypto.tls;

public interface TlsPeer {
    TlsCipher getCipher();

    TlsCompression getCompression();

    void notifyAlertRaised(short s, short s2, String str, Exception exc);

    void notifyAlertReceived(short s, short s2);

    void notifyHandshakeComplete();

    void notifySecureRenegotiation(boolean z);

    boolean shouldUseGMTUnixTime();
}
