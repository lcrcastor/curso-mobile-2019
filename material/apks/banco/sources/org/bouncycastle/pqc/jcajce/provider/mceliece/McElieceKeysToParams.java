package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;

public class McElieceKeysToParams {
    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (privateKey instanceof BCMcEliecePrivateKey) {
            BCMcEliecePrivateKey bCMcEliecePrivateKey = (BCMcEliecePrivateKey) privateKey;
            McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = new McEliecePrivateKeyParameters(bCMcEliecePrivateKey.getOIDString(), bCMcEliecePrivateKey.getN(), bCMcEliecePrivateKey.getK(), bCMcEliecePrivateKey.getField(), bCMcEliecePrivateKey.getGoppaPoly(), bCMcEliecePrivateKey.getSInv(), bCMcEliecePrivateKey.getP1(), bCMcEliecePrivateKey.getP2(), bCMcEliecePrivateKey.getH(), bCMcEliecePrivateKey.getQInv(), bCMcEliecePrivateKey.getMcElieceParameters());
            return mcEliecePrivateKeyParameters;
        }
        throw new InvalidKeyException("can't identify McEliece private key.");
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof BCMcEliecePublicKey) {
            BCMcEliecePublicKey bCMcEliecePublicKey = (BCMcEliecePublicKey) publicKey;
            McEliecePublicKeyParameters mcEliecePublicKeyParameters = new McEliecePublicKeyParameters(bCMcEliecePublicKey.getOIDString(), bCMcEliecePublicKey.getN(), bCMcEliecePublicKey.getT(), bCMcEliecePublicKey.getG(), bCMcEliecePublicKey.getMcElieceParameters());
            return mcEliecePublicKeyParameters;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("can't identify McEliece public key: ");
        sb.append(publicKey.getClass().getName());
        throw new InvalidKeyException(sb.toString());
    }
}
