package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class MQVPrivateParameters implements CipherParameters {
    private ECPrivateKeyParameters a;
    private ECPrivateKeyParameters b;
    private ECPublicKeyParameters c;

    public MQVPrivateParameters(ECPrivateKeyParameters eCPrivateKeyParameters, ECPrivateKeyParameters eCPrivateKeyParameters2) {
        this(eCPrivateKeyParameters, eCPrivateKeyParameters2, null);
    }

    public MQVPrivateParameters(ECPrivateKeyParameters eCPrivateKeyParameters, ECPrivateKeyParameters eCPrivateKeyParameters2, ECPublicKeyParameters eCPublicKeyParameters) {
        this.a = eCPrivateKeyParameters;
        this.b = eCPrivateKeyParameters2;
        this.c = eCPublicKeyParameters;
    }

    public ECPrivateKeyParameters getEphemeralPrivateKey() {
        return this.b;
    }

    public ECPublicKeyParameters getEphemeralPublicKey() {
        return this.c;
    }

    public ECPrivateKeyParameters getStaticPrivateKey() {
        return this.a;
    }
}
