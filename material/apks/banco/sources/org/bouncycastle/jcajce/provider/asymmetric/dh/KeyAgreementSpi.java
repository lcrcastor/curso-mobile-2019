package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Strings;

public class KeyAgreementSpi extends javax.crypto.KeyAgreementSpi {
    private static final Hashtable e = new Hashtable();
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;
    private BigInteger d;

    static {
        Integer valueOf = Integers.valueOf(64);
        Integer valueOf2 = Integers.valueOf(192);
        Integer valueOf3 = Integers.valueOf(128);
        Integer valueOf4 = Integers.valueOf(256);
        e.put("DES", valueOf);
        e.put("DESEDE", valueOf2);
        e.put("BLOWFISH", valueOf3);
        e.put("AES", valueOf4);
    }

    private byte[] a(BigInteger bigInteger) {
        int bitLength = (this.b.bitLength() + 7) / 8;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == bitLength) {
            return byteArray;
        }
        if (byteArray[0] == 0 && byteArray.length == bitLength + 1) {
            byte[] bArr = new byte[(byteArray.length - 1)];
            System.arraycopy(byteArray, 1, bArr, 0, bArr.length);
            return bArr;
        }
        byte[] bArr2 = new byte[bitLength];
        System.arraycopy(byteArray, 0, bArr2, bArr2.length - byteArray.length, byteArray.length);
        return bArr2;
    }

    /* access modifiers changed from: protected */
    public Key engineDoPhase(Key key, boolean z) {
        if (this.a == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        } else if (!(key instanceof DHPublicKey)) {
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        } else {
            DHPublicKey dHPublicKey = (DHPublicKey) key;
            if (!dHPublicKey.getParams().getG().equals(this.c) || !dHPublicKey.getParams().getP().equals(this.b)) {
                throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
            } else if (z) {
                this.d = dHPublicKey.getY().modPow(this.a, this.b);
                return null;
            } else {
                this.d = dHPublicKey.getY().modPow(this.a, this.b);
                return new BCDHPublicKey(this.d, dHPublicKey.getParams());
            }
        }
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] bArr, int i) {
        if (this.a == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        byte[] a2 = a(this.d);
        if (bArr.length - i < a2.length) {
            throw new ShortBufferException("DHKeyAgreement - buffer too short");
        }
        System.arraycopy(a2, 0, bArr, i, a2.length);
        return a2.length;
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String str) {
        if (this.a == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        String upperCase = Strings.toUpperCase(str);
        byte[] a2 = a(this.d);
        if (!e.containsKey(upperCase)) {
            return new SecretKeySpec(a2, str);
        }
        byte[] bArr = new byte[(((Integer) e.get(upperCase)).intValue() / 8)];
        System.arraycopy(a2, 0, bArr, 0, bArr.length);
        if (upperCase.startsWith("DES")) {
            DESParameters.setOddParity(bArr);
        }
        return new SecretKeySpec(bArr, str);
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() {
        if (this.a != null) {
            return a(this.d);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, SecureRandom secureRandom) {
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        this.b = dHPrivateKey.getParams().getP();
        this.c = dHPrivateKey.getParams().getG();
        BigInteger x = dHPrivateKey.getX();
        this.d = x;
        this.a = x;
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        DHParameterSpec dHParameterSpec;
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        if (algorithmParameterSpec == null) {
            this.b = dHPrivateKey.getParams().getP();
            dHParameterSpec = dHPrivateKey.getParams();
        } else if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
        } else {
            dHParameterSpec = (DHParameterSpec) algorithmParameterSpec;
            this.b = dHParameterSpec.getP();
        }
        this.c = dHParameterSpec.getG();
        BigInteger x = dHPrivateKey.getX();
        this.d = x;
        this.a = x;
    }
}
