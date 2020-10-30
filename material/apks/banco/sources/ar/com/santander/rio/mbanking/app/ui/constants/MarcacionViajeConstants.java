package ar.com.santander.rio.mbanking.app.ui.constants;

public class MarcacionViajeConstants {
    public static final String RESCODE_USER000454 = "USER000454";
    public static final String cINTENT_EXTRA_ACCION = "ACCION_MARCACION";
    public static final String cINTENT_EXTRA_AYUDA = "AYUDA_MARCACION";
    public static final String cINTENT_EXTRA_GET_VIAJES = "GET_VIAJES_MARCACION";
    public static final String cINTENT_EXTRA_RES4ERROR = "RES4_ERROR";
    public static final String cINTENT_EXTRA_TARJETAS = "TARJETAS_MARCACION";
    public static final String cINTENT_EXTRA_USUARIOS = "USUARIOS_MARCACION";

    public static final class Accion {
        public static final String MODIFICAR_ELIMINAR = "3";
        public static final String NUEVO = "4";
        public static final String SOLO_ELIMINAR = "2";
        public static final String SOLO_MODIFICAR = "1";
        public static final String SOLO_VISUALIZAR = "0";
    }

    public static class ClaseTarjeta {
        public static final String BLACK = "H";
        public static final String NACIONAL = "N";
    }

    public static class Reintento {
        public static final String Reintento_Falso = "false";
        public static final String Reintento_Verdadero = "true";
    }

    public static class TipoOperacion {
        public static final String Alta = "A";
        public static final String Baja = "B";
        public static final String Modificacion = "M";
    }
}
