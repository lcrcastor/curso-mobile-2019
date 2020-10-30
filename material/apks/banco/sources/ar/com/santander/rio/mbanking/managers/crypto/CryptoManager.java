package ar.com.santander.rio.mbanking.managers.crypto;

import android.content.Context;
import ar.com.santander.rio.mbanking.R;
import ar.com.santander.rio.mbanking.managers.preferences.KeyConfiguration;
import com.grab.android.vending.billing.util.Base64;
import com.grab.android.vending.billing.util.Base64DecoderException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptoManager {
    private SecretKey a;
    private PBEParameterSpec b;

    public CryptoManager(Context context) {
        try {
            a(context);
            b(context);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CryptoException(e);
        }
    }

    public String encrypt(String str) {
        try {
            return Base64.encode(a().doFinal(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptoException(e);
        }
    }

    public String decrypt(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            return new String(b().doFinal(Base64.decode(str)), "UTF-8");
        } catch (Base64DecoderException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptoException(e);
        }
    }

    private Cipher a() {
        try {
            Cipher instance = Cipher.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            new SecureRandom().nextBytes(new byte[16]);
            instance.init(1, this.a, this.b);
            return instance;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptoException(e);
        }
    }

    private Cipher b() {
        try {
            Cipher instance = Cipher.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            new SecureRandom().nextBytes(new byte[16]);
            instance.init(2, this.a, this.b);
            return instance;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptoException(e);
        }
    }

    private void a(Context context) {
        KeyConfiguration instance = KeyConfiguration.getInstance(context);
        if (!instance.hasKey()) {
            instance.generateKey();
        }
        this.a = a(instance.getKey());
    }

    private SecretKey a(String str) {
        return SecretKeyFactory.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC").generateSecret(new PBEKeySpec(str.toCharArray()));
    }

    private void b(Context context) {
        this.b = b(context.getString(R.string.ID21101_ENVEFECT_LBL_DOCUMENTO));
    }

    private PBEParameterSpec b(String str) {
        return new PBEParameterSpec(str.getBytes("UTF-8"), 20);
    }
}
