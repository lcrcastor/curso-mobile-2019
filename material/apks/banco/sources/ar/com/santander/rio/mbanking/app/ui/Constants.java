package ar.com.santander.rio.mbanking.app.ui;

import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants;
import java.util.Locale;

public class Constants {
    public static final Integer[] ARR_STATES_UPDATE_APP = {Integer.valueOf(2), Integer.valueOf(3)};
    public static final String AUTOCOMPLETE_API_BASE = "https://maps.googleapis.com/maps/api";
    public static int CHECK_VERSION_TIME_OUT_SERVER = 7;
    public static final String CURRENCY_DEFAULT_LONG_TERM_DEPOSIT = "ARS";
    public static final String CURRENT_SO = "Android";
    public static int DEFAULT_RETRY = 0;
    public static int DEFAULT_TIME_OUT_SERVER = 30;
    public static final String DIR_EXTERNAL_STORAGE = "Santander";
    public static final String DIR_ROOT = "/data/data/ar.com.santander.rio.mbanking/files";
    public static final String DIR_STORAGE_FUNDS = "fondos";
    public static final String DIR_STORAGE_RECEIPTS = "comprobantes";
    public static final String DIR_STORAGE_RECEIPTS_TMP = "compartidos";
    public static final String DIR_STORAGE_SEGUROS = "seguros";
    public static final String EXTENSION_FILE_STORAGE = ".jpg";
    public static final String FORMAT_ABREV_NUMBER_ACCOUNT = "0000000";
    public static final String FORMAT_ABREV_SUC_ACCOUNT = "0000";
    public static final String FORMAT_ABREV_SUC_ACCOUNT_V2 = "000";
    public static final String FORMAT_AMOUNT_WS = "000000000000.00";
    public static String FORMAT_DATE_APP = "dd/MM/yy";
    public static String FORMAT_DATE_APP_2 = "dd/MM/yyyy";
    public static String FORMAT_DATE_APP_3 = "dd/MM/yy,";
    public static String FORMAT_DATE_APP_4 = "dd/MM/yyyy,";
    public static String FORMAT_DATE_CREDIT_CARDS_MAIN = "dd/MM";
    public static String FORMAT_DATE_DASH = "yyyy-MM-dd";
    public static final String FORMAT_DATE_FILE_EXTENSION = "dd-MM-yy";
    public static String FORMAT_DATE_NAME_MONTH_APP = "dd MMMM yyyy";
    public static String FORMAT_DATE_NULL = "**/**/**";
    public static String FORMAT_DATE_TIME = "yyyyMMddHHmmss";
    public static String FORMAT_DATE_TIME_COMPROBANTE = "dd/MM/yyyy HH:mm' hs.'";
    public static String FORMAT_DATE_WS_1 = "yyyyMMdd";
    public static String FORMAT_DATE_WS_2 = "ddMMyyyy";
    public static String FORMAT_DATE_WS_3 = "ddMMyy";
    public static String FORMAT_TIME = "HH:mm:ss";
    public static final String GEO_API_BASE = "/geocode";
    public static String ID_NOTIFICACION_INDEX = "ID_NOTIFICACION_INDEX";
    public static final String INTENT_FACEBOOK = "com.facebook.katana";
    public static final String INTENT_GMAIL = "com.google.android.gm";
    public static final String INTENT_HANGOUTS = "com.google.android.talk";
    public static final String INTENT_TWITTER = "com.twitter.android";
    public static final String INTENT_WHATSAPP = "com.whatsapp";
    public static final double LAT_DEFAULT = -34.606737d;
    public static final String LEYENDA_CUOTA_PURA_UVAS = "CUOTA_PURA_UVAS";
    public static final String LEYENDA_PRE_TRAD = "PRE_TRAD";
    public static final String LEYENDA_PRE_UVA = "PRE_UVA";
    public static final String LEYENDA_TASA_CRED_CFTEA = "CRED_CFTEA";
    public static final String LEYENDA_TASA_CRED_CFTEA_SIMP = "CRED_CFTEA_SIMP";
    public static final String LEYENDA_TASA_CRED_TEA = "CRED_TEA";
    public static final String LEYENDA_TASA_CRED_TNA = "CRED_TNA";
    public static String LEYEND_ASTERISK = " *";
    public static final int LIMIT_MY_PLACES = 5;
    public static Locale LOCALE_DEFAULT_APP = new Locale("es", "AR");
    public static Locale LOCALE_DEFAULT_WS = Locale.ENGLISH;
    public static final double LON_DEFAULT = -58.373381d;
    public static final int MAX_DAYS_DIFF_FILTER_TRANSACTIONS = 60;
    public static int MAX_DECIMALS = 2;
    public static int MAX_LENGTH_AMOUNT = 18;
    public static final int MENU_HEIGHT_IN_PD = 81;
    public static String MIME_PDF = "application/pdf";
    public static int MIN_METERS_REQUEST_LOCATION = 100;
    public static int MIN_TIME_REQUEST_LOCATION = 1000;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1452;
    public static final String OPTION_PLACE = "/details";
    public static final String OUT_JSON = "/json";
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_FLAG_1 = 1;
    public static final String PLACES_API_BASE = "/place";
    public static final String PLACE_ARGENTINA_LAT = "-38.416097";
    public static final String PLACE_ARGENTINA_LON = "-63.61667199999999";
    public static String PUSH_PREFERENCES = "PUSH_PREFERENCES";
    public static final String SHARE_BODY_EMAIL = "Share.Pattern.EMail.Cuerpo";
    public static final String SHARE_BODY_FB = "Share.Pattern.Facebook.Cuerpo";
    public static final String SHARE_BODY_SMS = "Share.Pattern.SMS.Cuerpo";
    public static final String SHARE_BODY_TWITTER = "Share.Pattern.Twitter.Cuerpo";
    public static final String SHARE_TITLE_EMAIL = "Share.Pattern.EMail.Titulo";
    public static final String SHARE_TITLE_FB = "Share.Pattern.Facebook.Titulo";
    public static String SPLASH_ID_RUNTIME = "5";
    public static final String STR_ID_GOOGLE_GEOCODER = "GoogleGeocoder.Query";
    public static String SYMBOL_CURRENCY_DOLAR = "U$S";
    public static String SYMBOL_CURRENCY_DOLLAR_DESCRIPTION = "DÓLARES";
    public static String SYMBOL_CURRENCY_DOLLAR_STR = "USD";
    public static String SYMBOL_CURRENCY_PESOS = "$";
    public static String SYMBOL_CURRENCY_PESOS_DESCRIPTION = "PESOS";
    public static String SYMBOL_CURRENCY_PESOS_STR = "ARS";
    public static final String SYMBOL_NEGATIVE = "-";
    public static final String SYMBOL_PERCENTAGE = "%";
    public static final String SYMBOL_POSITIVE = "+";
    public static String TAG_LOG_ERROR_SESSION_LOST = "SESSION_LOST";
    public static final String TYPE_AUTOCOMPLETE = "/queryautocomplete";
    public static final String[] VALUES_EXPIRED_ACTION_LONG_TERM_DEPOSIT = {"DC", SegurosConstants.LINK_OPCION_RC, "RCI"};
    public static final String VALUE_DEFAULT_INPUT_AMOUNT_EMPTY = "0";
    public static final String VALUE_DEFAULT_PLAZO_FIJO = "0";
    public static final String VALUE_DEFAULT_TYPE_ACCOUNT_PAYMENTS = "02";
    public static String WS_ACCESS_PRIVATE = "N";
    public static String WS_ACCESS_PUBLIC = "S";
    public static final int ZONE_CAJEROS = 8;
    public static final int ZONE_PROMOCIONES = 1;
    public static final int ZONE_SUCURSALES = 7;
    public static float ZOOM_LEVEL_CAMERA = 14.0f;

    public static final class CUSTOM_URL_DOMAIN {
        public static final String CONSTITUCION_PLAZO_FIJO = "CONSTITUCIONPLAZOFIJO";
        public static final String HOME = "HOME";
        public static final String LOGIN = "LOGIN";
        public static final String PAGO_SERVICIOS = "PAGOSERVICIOS";
        public static final String PAGO_TARJETAS = "PAGOTARJETAS";
        public static final String SEGURO_AUTOMOVIL = "SEGUROAUTOS";
        public static final String SEGURO_MOVIL = "SEGUROMOVIL";
        public static final String SEGURO_OBJETO = "SEGUROOBJETO";
        public static final String SEGURO_VIVIENDA = "SEGUROVIVIENDA";
        public static final String SOLICITAR_CREDITO = "SOLICITARPRESTAMO";
        public static final String SORPRESA = "SORPRESA";
        public static final String SUPERCLUB = "SUPERCLUB";
        public static final String SUSCRIPCION_FONDOS = "SUSCRIPCIONFONDOS";
        public static final String TOKENOBP = "TOKENOBP";
        public static final String WOMEN = "WOMEN";
    }
}
