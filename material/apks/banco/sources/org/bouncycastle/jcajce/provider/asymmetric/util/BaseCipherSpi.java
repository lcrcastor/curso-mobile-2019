package org.bouncycastle.jcajce.provider.asymmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public abstract class BaseCipherSpi extends CipherSpi {
    private Class[] a = {IvParameterSpec.class, PBEParameterSpec.class, RC2ParameterSpec.class, RC5ParameterSpec.class};
    protected AlgorithmParameters engineParams = null;
    protected Wrapper wrapEngine = null;

    protected BaseCipherSpi() {
    }

    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        return null;
    }

    public int engineGetKeySize(Key key) {
        return key.getEncoded().length;
    }

    public int engineGetOutputSize(int i) {
        return -1;
    }

    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    public void engineSetMode(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("can't support mode ");
        sb.append(str);
        throw new NoSuchAlgorithmException(sb.toString());
    }

    public void engineSetPadding(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Padding ");
        sb.append(str);
        sb.append(" unknown.");
        throw new NoSuchPaddingException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public Key engineUnwrap(byte[] bArr, String str, int i) {
        try {
            byte[] engineDoFinal = this.wrapEngine == null ? engineDoFinal(bArr, 0, bArr.length) : this.wrapEngine.unwrap(bArr, 0, bArr.length);
            if (i == 3) {
                return new SecretKeySpec(engineDoFinal, str);
            }
            if (!str.equals("") || i != 2) {
                try {
                    KeyFactory instance = KeyFactory.getInstance(str, BouncyCastleProvider.PROVIDER_NAME);
                    if (i == 1) {
                        return instance.generatePublic(new X509EncodedKeySpec(engineDoFinal));
                    }
                    if (i == 2) {
                        return instance.generatePrivate(new PKCS8EncodedKeySpec(engineDoFinal));
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown key type ");
                    sb.append(i);
                    throw new InvalidKeyException(sb.toString());
                } catch (NoSuchProviderException e) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Unknown key type ");
                    sb2.append(e.getMessage());
                    throw new InvalidKeyException(sb2.toString());
                } catch (NoSuchAlgorithmException e2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Unknown key type ");
                    sb3.append(e2.getMessage());
                    throw new InvalidKeyException(sb3.toString());
                } catch (InvalidKeySpecException e3) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Unknown key type ");
                    sb4.append(e3.getMessage());
                    throw new InvalidKeyException(sb4.toString());
                }
            } else {
                try {
                    PrivateKeyInfo instance2 = PrivateKeyInfo.getInstance(engineDoFinal);
                    PrivateKey privateKey = BouncyCastleProvider.getPrivateKey(instance2);
                    if (privateKey != null) {
                        return privateKey;
                    }
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("algorithm ");
                    sb5.append(instance2.getPrivateKeyAlgorithm().getAlgorithm());
                    sb5.append(" not supported");
                    throw new InvalidKeyException(sb5.toString());
                } catch (Exception unused) {
                    throw new InvalidKeyException("Invalid key encoding.");
                }
            }
        } catch (InvalidCipherTextException e4) {
            throw new InvalidKeyException(e4.getMessage());
        } catch (BadPaddingException e5) {
            throw new InvalidKeyException(e5.getMessage());
        } catch (IllegalBlockSizeException e6) {
            throw new InvalidKeyException(e6.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineWrap(Key key) {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            return this.wrapEngine == null ? engineDoFinal(encoded, 0, encoded.length) : this.wrapEngine.wrap(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }
}
