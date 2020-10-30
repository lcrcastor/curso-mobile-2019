package ar.com.santander.rio.mbanking.app.ui.constants;

public class SegurosConstants {
    public static final String CONFIRMACION = "CONFIRMACION";
    public static final String INTENT_CAMBIO_DISPOSITIVO = "CAMBIO_DISPOSITIVO_INTENT";
    public static final int INTENT_COMPRA_PROTEGIDA = 1;
    public static final int INTENT_CONTRATAR_SEGURO = 4;
    public static final String INTENT_COTIZACION = "COTIZACION_SEGURO_INTENT";
    public static final String INTENT_EXTRA_OCUPACION = "INTENT_EXTRA_OCUPACION";
    public static final String INTENT_EXTRA_OCUPACIONES = "INTENT_EXTRA_OCUPACIONES";
    public static final String INTENT_LISTA_SEGUROS = "LISTA_SEGUROS_INTENT";
    public static final String INTENT_LISTA_SEGUROS_CARTERA = "INTENT_LISTA_SEGUROS_CARTERA";
    public static final String INTENT_LISTA_TOTAL_SEGUROS = "TOTAL_SEGUROS_INTENT";
    public static final int INTENT_OCUPACIONES = 3;
    public static final String INTENT_SEGUROS_CARTERA = "INTENT_SEGUROS_CARTERA";
    public static final int INTENT_SEGURO_MOVIL = 2;
    public static final int INTENT_SEGURO_OBJETO = 7;
    public static final String INTENT_TARJETAS_YA_ASEGURADAS = "TARJETAS_YA_ASEGURADAS_INTENT";
    public static final String INTENT_TIPO_ALTA_MOVIL = "TIPO_ALTA_MOVIL_INTENT";
    public static final String LINK_OPCION_CS = "CS";
    public static final String LINK_OPCION_NM = "NM";
    public static final String LINK_OPCION_RC = "RC";
    public static final String LINK_OPCION_RD = "RD";
    public static final int MAXIMO_TARJETAS_ASEGURABLES = 3;
    public static final String POLIZA_FILE_NAME = "%s.pdf";
    public static final String PREFERENCE_ONBOARDING = "OnBoardingSeguros";
    public static final int REQUEST_CODE_SEGURO_OBJETO = 65543;
    public static final String SEGURO_DEEPLINK_ACTION = "SEGURO_DEEPLINK_ACTION";
    public static final int VERIFICATION_BATTERY = 6;
    public static final int VERIFICATION_DISPLAY = 5;

    public static class CodRamo {
        public static final String PROTECCION_CARTERA = "52";
        public static final String SEGURO_MOVIL = "25";
        public static final String TENENCIA_COMPRA_PROTEGIDA = "53";
        public static final String TENENCIA_COMPRA_PROTEGIDA_2 = "26";
    }

    public static class TipoAlta {
        public static final String ALTA_DISPOSITIVO = "ALTA_DISPOSITIVO";
        public static final String ALTA_O_CAMBIO_DISPOSITIVO = "ALTA_O_CAMBIO";
        public static final String CAMBIO_DISPOSITIVO = "CAMBIO_DISPOSITIVO";
        public static final String DISPOSITIVO_ASEGURADO = "DISPOSITIVO_ASEGURADO";
        public static final String NO_ASEGURABLE = "NO_ASEGURABLE";
        public static final String SEGURO_CARTERA = "SEGURO_CARTERA";
    }

    public static class TipoAltaTenencia {
        public static final String POSIBILIDAD_REEMPLAZO = "REEMPLAZO_OK";
        public static final String USUARIO_CON_SEGUROS_MOVILES = "R";
    }

    public static class TipoOperacion {
        public static final String ALTA_DISPOSITIVO = "A";
        public static final String REEMPLAZO_DISPOSITIVO = "R";
    }

    public static class idLeyenda {
        public static final String COBERTURA_OBJETO_DETALLE = "SEGOBJ_DET";
        public static final String COMPRA_PROTEGIDA_DETALLE = "SEG_DET_COM";
        public static final String COMPRA_PROTEGIDA_TYC = "SEG_TYC_COM";
        public static final String SEGURO_MOVIL_DETALLE = "SEG_DET_MOV";
        public static final String SEGURO_MOVIL_TYC = "SEG_TYC_MOV";
    }
}
