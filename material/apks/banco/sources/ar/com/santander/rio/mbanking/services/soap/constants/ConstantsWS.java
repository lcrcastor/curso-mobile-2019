package ar.com.santander.rio.mbanking.services.soap.constants;

import android.util.Base64;
import ar.com.santander.rio.mbanking.utils.RSACryptoUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.UnsupportedEncodingException;

public class ConstantsWS {
    public static final String DEFAULT_ID_RUNTIME_ANDROID = "5";
    public static final String DEFAULT_IP = "000.000.000.000";
    public static final String DEFAULT_LATITUDE = "0.00";
    public static final String DEFAULT_LONGITUDE = "0.00";
    public static final String FORMAT_DATETIME_REQUEST = "yyyyMMddHHmmss";
    public static final String ID_APPLICATION = "MBI";

    public static native String getKey1();

    public static native String getKey2();

    public static native String getPublicToken();

    static {
        System.loadLibrary("keys");
    }

    public static String getUsernameApp() {
        String str = "";
        try {
            return new String(Base64.decode(getKey1(), 0), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getDefaultPasswordApp() {
        String str = "";
        try {
            return new String(Base64.decode(getKey2(), 0), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String getAccessApp() {
        byte[] decode = Base64.decode(getPublicToken(), 0);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("usuario", getUsernameApp());
        jsonObject.addProperty("password", getDefaultPasswordApp());
        String str = "";
        try {
            return RSACryptoUtil.encrypt(new GsonBuilder().create().toJson((JsonElement) jsonObject), new String(decode, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
}
