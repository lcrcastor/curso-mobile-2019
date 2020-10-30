package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PublicKeyParameters;

public class McElieceCCA2KeysToParams {
    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey privateKey) {
        if (privateKey instanceof BCMcElieceCCA2PrivateKey) {
            BCMcElieceCCA2PrivateKey bCMcElieceCCA2PrivateKey = (BCMcElieceCCA2PrivateKey) privateKey;
            McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = new McElieceCCA2PrivateKeyParameters(bCMcElieceCCA2PrivateKey.getOIDString(), bCMcElieceCCA2PrivateKey.getN(), bCMcElieceCCA2PrivateKey.getK(), bCMcElieceCCA2PrivateKey.getField(), bCMcElieceCCA2PrivateKey.getGoppaPoly(), bCMcElieceCCA2PrivateKey.getP(), bCMcElieceCCA2PrivateKey.getH(), bCMcElieceCCA2PrivateKey.getQInv(), bCMcElieceCCA2PrivateKey.getMcElieceCCA2Parameters());
            return mcElieceCCA2PrivateKeyParameters;
        }
        throw new InvalidKeyException("can't identify McElieceCCA2 private key.");
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey publicKey) {
        if (publicKey instanceof BCMcElieceCCA2PublicKey) {
            BCMcElieceCCA2PublicKey bCMcElieceCCA2PublicKey = (BCMcElieceCCA2PublicKey) publicKey;
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters = new McElieceCCA2PublicKeyParameters(bCMcElieceCCA2PublicKey.getOIDString(), bCMcElieceCCA2PublicKey.getN(), bCMcElieceCCA2PublicKey.getT(), bCMcElieceCCA2PublicKey.getG(), bCMcElieceCCA2PublicKey.getMcElieceCCA2Parameters());
            return mcElieceCCA2PublicKeyParameters;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("can't identify McElieceCCA2 public key: ");
        sb.append(publicKey.getClass().getName());
        throw new InvalidKeyException(sb.toString());
    }
}
