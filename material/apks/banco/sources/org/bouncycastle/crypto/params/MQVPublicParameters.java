package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class MQVPublicParameters implements CipherParameters {
    private ECPublicKeyParameters a;
    private ECPublicKeyParameters b;

    public MQVPublicParameters(ECPublicKeyParameters eCPublicKeyParameters, ECPublicKeyParameters eCPublicKeyParameters2) {
        this.a = eCPublicKeyParameters;
        this.b = eCPublicKeyParameters2;
    }

    public ECPublicKeyParameters getEphemeralPublicKey() {
        return this.b;
    }

    public ECPublicKeyParameters getStaticPublicKey() {
        return this.a;
    }
}
