package com.vusecurity.androidsdkwithbankcode;

public class VUMobileTokenSDK {
    public static int ERROR_CODIGO_ASOCIACION_INCORRECTO = 101;
    public static int ERROR_CUPON_EXPIRADO = 102;
    public static int ERROR_TIME_SYNC = 103;

    private VUMobileTokenSDK() {
    }

    public static void getEncryptedSeed(String str, String str2, String str3, SeedCallback seedCallback) {
        RequestManager.INSTANCE.a(seedCallback, str, str2, str3);
    }

    public static void getTimeDelta(String str, boolean z, String str2, TimeDeltaCallback timeDeltaCallback) {
        RequestManager.INSTANCE.a(timeDeltaCallback, str, str2);
    }

    public static String decryptedSeed(String str, String str2) {
        return SecretManager.a(str, str2);
    }

    public static String genTotp(String str, long j) {
        return SecretManager.a(str.getBytes(), j);
    }

    public static long getTotpTtl(long j) {
        return SecretManager.c(j);
    }

    public static String encryptSeed(String str, String str2) {
        return SecretManager.b(str, str2);
    }
}
