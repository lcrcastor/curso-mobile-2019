package ar.com.santander.rio.mbanking.app.ui.constants;

public class EnvioConstants {
    public static final String RECARGAR_PANTALLA_INICIAL_ENV = "RECARGAR_PANTALLA_INICIAL_ENV";
    public static final String RECARGAR_PANTALLA_INICIAL_EXT = "RECARGAR_PANTALLA_INICIAL_EXT";
    public static final int cIntentBusquedaAvanzada = 11;
    public static final int cIntentComprobanteCancelacion = 9;
    public static final int cIntentComprobanteEnvio = 6;
    public static final int cIntentConfirmarEnvio = 3;
    public static final int cIntentCrearNuevoDestinatario = 2;
    public static final int cIntentDetalleOperacion = 8;
    public static final int cIntentEditarDestinatario = 5;
    public static final int cIntentListadoDestinatarios = 12;
    public static final int cIntentOperacionesRealizadas = 7;

    /* renamed from: cIntentPrepararEnvío reason: contains not printable characters */
    public static final int f231cIntentPrepararEnvo = 10;
    public static final int cIntentSeleccionarContacto = 4;
    public static final int cIntentSeleccionarContactoDeAgenda = 1;

    public static class BodyRequest {

        public static class TipoOperacion {
            public static final String EnvioDinero = "ENV";
            public static final String ExtraccionSinTarjeta = "EXT";
        }
    }

    public static class Estado {
        public static final String Bloqueado = "BLO";
        public static final String Cancelado = "CAN";
        public static final String Cobrado = "COB";
        public static final String Pendiente = "PEN";
        public static final String Vencido = "VEN";
    }

    public static class ListadoDestinatarios {

        public static class ViewMode {
            public static final String ABMOnly = "ABM_ONLY";
            public static final String InfoCreate = "CREATE";
            public static final String InfoEdit = "EDIT";
            public static final String InfoMode = "DESTINATARIO_INFO_VISUAL_MODE";
            public static final String Mode = "DESTINATARIOS_VISUAL_MODE";
            public static final String MultipleChoice = "MULTIPLE_CHOICE";
            public static final String SingleChoice = "SINGLE_CHOICE";
        }
    }
}
