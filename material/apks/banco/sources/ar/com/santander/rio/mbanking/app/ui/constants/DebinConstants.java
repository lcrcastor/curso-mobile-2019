package ar.com.santander.rio.mbanking.app.ui.constants;

public class DebinConstants {
    public static final String COMPRADOR = "C";
    public static final String DIALOG_CONFIRMATION_ABM_CTA_MIGRADA = "DIALOGCONFIRMATIONABMGETCTAMIGRADA";
    public static final String DIALOG_CONFIRMATION_ABM_PREAUTOZICACIONE = "IALOGCONFIRMATIONABMPREAUTOZICACIONE";
    public static final String DIALOG_FIRMA_CREDIN = "DIALOGSALIENDODESANTANDERRIO";
    public static final int INTENT_ABM_DEBIN = 2;
    public static final int INTENT_ADHESION_DEBIN = 4;
    public static final int INTENT_BUSCADOR_DEBIN = 3;
    public static final String INTENT_EXTRA_CUENTAS_ADHERIDAS = "INTENT_EXTRA_CUENTAS_ADHERIDAS";
    public static final String INTENT_EXTRA_DEBIN = "INTENT_EXTRA_DEBIN";
    public static final String INTENT_EXTRA_DETALLE_DEBIN = "INTENT_EXTRA_DETALLE_DEBIN";
    public static final String INTENT_EXTRA_HAY_CUENTAS_ADHERIDAS = "INTENT_EXTRA_HAY_CUENTAS_ADHERIDAS";
    public static final String INTENT_EXTRA_LEYENDA = "INTENT_EXTRA_LEYENDA";
    public static final String INTENT_EXTRA_LISTA_DESCRIPCIONES = "INTENT_EXTRA_LISTA_DESCRIPCIONES";
    public static final String INTENT_EXTRA_TIPO_ACCION = "INTENT_EXTRA_TIPO_ACCION";
    public static final String INTENT_FIRMA_CREDIN = "INTENT_FIRMA_CREDIN";
    public static final int INTENT_NUEVO_DEBIN = 1;
    public static final int INTENT_PRE_AUTORZACIONES = 5;
    public static final String INTENT_URL_CREDIN = "INTENT_URL_CREDIN";
    public static final String POP_UP_PREAUTORIZACIONES = "POPUPPREAUTORIZACIONES";
    public static final String TAB_ENVIADAS = "Enviadas";
    public static final String TAB_RECIBIDAS = "Recibidas";
    public static final String VENDEDOR = "V";

    public static final class ACCION_ABM_CUENTA {
        public static final String ANULAR = "A";
        public static final String CREAR = "C";
        public static final String DESCONOCER = "D";
        public static final String PAGAR = "P";
        public static final String RECHAZAR = "R";
    }

    public static final class ACCION_ABM_PREAUTORIZACION {
        public static final String ACEPTAR = "A";
        public static final String DESCONOCER = "D";
        public static final String DESVINCULAR = "B";
        public static final String PAGAR = "P";
        public static final String RECHAZAR = "R";
    }

    public static final class ACCION_ADHESION_CUENTA {
        public static final String ADHERIR = "A";
        public static final String DESVINCULAR = "B";
    }

    public static final class ACTION_CONSULTA_ADHESION_VENDEDOR {
        public static final String ADHERIR_NUEVO = "ADHERIR_NUEVO";
        public static final String GESTION_ADHESION = "GESTION_ADHESION";
    }

    public static final class PRE_AUTORIZACIONES {
        public static final String COMPROBANTE_PRE_AUTORIZACION_FRAGMENT = "COMPROBANTE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT";
        public static final String CONDEBIN = "CONDEBIN";
        public static final String CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT = "CONFIRMAR_AUTORZACION_DEBIN_FRAGMENT";
        public static final String CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS = "CONSULTA_TENENCIA_PRE_AUTORIZACIONES_RECIBIDAS";
        public static final String DETALLE_PREAUTORIZACION_KEY = "DETALLE_PREAUTORIZACION_ARG";
        public static final String DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT = "DETALLE_PRE_AUTORIZACION_COMPRADOR_FRAGMENT";
        public static final String ESTREPEAUT = "ESTPREAUT";
        public static final String MONEDADESCSIMBOLO = "MONEDADESCSIMBOLO";
        public static final String PERIODOPREAUT = "PERIODOPREAUT";
        public static final String SHARED_PREAUTORIZACIONES_DEBIN_FRAGMENT = "SHARED_PREAUTORIZACIONES_DEBIN_FRAGMENT";
        public static final String TPOCTACORTA = "TPOCTACORTA";
    }

    public static final class STATUS_ADHESION_CUENTA {
        public static final int ADHERIDA = 0;
        public static final int NO_ADHERIDA = 1;
    }

    public static final class STATUS_FLAGS {
        public static String NEGATIVE = "N";
        public static String POSITIVE = "S";
    }
}
