package ar.com.santander.rio.mbanking.app.ui.constants;

import android.support.media.ExifInterface;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.ClaseTarjeta;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginConstants {
    public static String ACEPTA_TERMINOS = "S";
    public static String ACEPTA_TYC = "aceptaTyC";
    public static String CANCEL = "CANCEL";
    public static String LOGIN_OK = "10";
    public static String LOGIN_PRIMER_INGRESO = "10";
    public static String LOGIN_SINONIMO = "10";
    public static String NO_ACEPTA_TYC = "N";
    public static String SUSCRIPCION = "suscripcion";
    public static String SUSC_ALTA_CEL = "AC";
    public static String SUSC_ALTA_MAIL = "AM";
    public static String SUSC_NO_SUSC = "NS";
    public static String SUSC_SA = "SA";
    public static String SUSC_TO = "TO";
    public static final Map<String, String> TIPOS_DOCUMENTO;
    public static String TIPO_CUENTA_CJA_AHO_DOLAR = "04";
    public static String TIPO_CUENTA_CJA_AHO_PESOS = "01";
    public static String TIPO_CUENTA_CTA_CTE_DOLAR = "03";
    public static String TIPO_CUENTA_CTA_CTE_PESOS = "00";
    public static String TIPO_CUENTA_CU = "02";
    public static String TIPO_CUENTA_CU_DOLAR = "10";
    public static String TIPO_CUENTA_CU_PESOS = "09";
    public static String TIPO_SEGMENTO_ADVANCE = "ADVANCE";
    public static String TIPO_SEGMENTO_ADVANCE_MENU_TEXT = "EXCLUSIVOS";
    public static String TIPO_SEGMENTO_SELECT = "SELECT";
    public static String TIPO_SEGMENTO_UNIVERSIDADES = "UNIVERSIDADES";

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("N", "DNI");
        hashMap.put("C", "LIBRETA CÍVICA");
        hashMap.put(ExifInterface.LONGITUDE_EAST, "LIBRETA DE ENROLAMIENTO");
        hashMap.put("I", "CÉDULA DE IDENTIDAD");
        hashMap.put("M", "CÉDULA MILITAR");
        hashMap.put("P", "PASAPORTE");
        hashMap.put("T", "CUIT");
        hashMap.put(ClaseTarjeta.PLATINUM, "CUIL");
        hashMap.put("D", "CDI");
        hashMap.put("X", "DNI EXTRANJERO");
        hashMap.put("F", "CERTIFICADO INTERNACIONAL");
        TIPOS_DOCUMENTO = Collections.unmodifiableMap(hashMap);
    }
}
