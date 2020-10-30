package com.twincoders.twinpush.sdk.util;

import android.annotation.SuppressLint;
import android.util.Base64;
import com.twincoders.twinpush.sdk.logging.Ln;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class StringEncrypter {
    Cipher a;
    Cipher b;

    @SuppressLint({"TrulyRandom"})
    public StringEncrypter(String str) {
        byte[] bArr = {-87, -101, -56, 50, 86, 52, -29, 3};
        try {
            SecretKey generateSecret = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(str.toCharArray(), bArr, 19));
            this.a = Cipher.getInstance(generateSecret.getAlgorithm());
            this.b = Cipher.getInstance(generateSecret.getAlgorithm());
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, 19);
            this.a.init(1, generateSecret, pBEParameterSpec);
            this.b.init(2, generateSecret, pBEParameterSpec);
        } catch (InvalidAlgorithmParameterException e) {
            Ln.e(e);
        } catch (InvalidKeySpecException e2) {
            Ln.e(e2);
        } catch (NoSuchPaddingException e3) {
            Ln.e(e3);
        } catch (NoSuchAlgorithmException e4) {
            Ln.e(e4);
        } catch (InvalidKeyException e5) {
            Ln.e(e5);
        }
    }

    public String encryptString(String str) {
        return Base64.encodeToString(a(str), 0);
    }

    public String decryptString(String str) {
        return a(Base64.decode(str, 0));
    }

    private byte[] a(String str) {
        try {
            return this.a.doFinal(str.getBytes(UrlUtils.UTF8));
        } catch (BadPaddingException e) {
            Ln.e(e);
            return null;
        } catch (IllegalBlockSizeException e2) {
            Ln.e(e2);
            return null;
        } catch (UnsupportedEncodingException e3) {
            Ln.e(e3);
            return null;
        }
    }

    private String a(byte[] bArr) {
        try {
            return new String(this.b.doFinal(bArr), UrlUtils.UTF8);
        } catch (BadPaddingException e) {
            Ln.e(e);
            return null;
        } catch (IllegalBlockSizeException e2) {
            Ln.e(e2);
            return null;
        } catch (UnsupportedEncodingException e3) {
            Ln.e(e3);
            return null;
        }
    }
}
