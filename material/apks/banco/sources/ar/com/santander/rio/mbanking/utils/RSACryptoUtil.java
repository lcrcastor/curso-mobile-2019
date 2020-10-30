package ar.com.santander.rio.mbanking.utils;

import android.util.Base64;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.security.Security;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSACryptoUtil {
    public static String encrypt(String str, String str2) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            AsymmetricKeyParameter createKey = PublicKeyFactory.createKey(Base64.decode(str2, 0));
            PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSAEngine());
            pKCS1Encoding.init(true, createKey);
            byte[] bytes = str.getBytes();
            return a(pKCS1Encoding.processBlock(bytes, 0, bytes.length));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toString((b & UnsignedBytes.MAX_VALUE) + Ascii.NUL, 16).substring(1));
        }
        return sb.toString();
    }
}
