package ar.com.santander.rio.mbanking.app.ui.constants;

public class TransferenciasConstants {
    public static final int CBUInputLength = 22;
    public static final String DIALOG_CONFIRMATION_TRANS_CTA_MIGRADA = "DIALOGCONFIRMATIONABMGETCTAMIGRADA";
    public static final String DolaresMoneda = "1";
    public static final int INTENT_AGENDA_DESTINATARIO = 10;
    public static final String INTENT_CUENTA_SELECCIONADA = "Cuenta Seleccionada";
    public static final String INTENT_CUENTA_SELECCIONADA_MIGRADA = "Cuenta Seleccionada Migrada";
    public static final int INTENT_SOLICITUD_AUMENTO = 50;
    public static final int MaxAliasInputLength = 20;
    public static final int MaxBSRInput1 = 3;
    public static final int MaxBSRInput2 = 6;
    public static final int MaxBSRInput3 = 1;
    public static final int MinAliasInputLength = 6;
    public static final int MinBSRInput2 = 5;
    public static final String PREFERENCE_ONBOARDING = "TransferenceOnBoarding";
    public static final String PesosMoneda = "0";
    public static final String cBANCO_CUENTAS_PROPIAS = "Cuentas Propias";
    public static final String cBANCO_DESTINO_BSR = "BANCO SANTANDER";
    public static final String cBANCO_DESTINO_OB = "Banco Destino: ";
    public static final String cBANCO_OB = "Otros Bancos";
    public static final String cBANCO_PROPIA = "Propia";
    public static final String cBANCO_SR_TERCEROS = "Terceros Santander";
    public static final String cINTENT_EXTRA_ACCION_VERIFICAR = "DISPLAY_MODE";
    public static final String cINTENT_EXTRA_CUENTAS_PROPIAS = "CUENTAS_PROPIAS";
    public static final String cINTENT_EXTRA_CUENTA_AGENDA = "CUENTA_AGENDA";
    public static final String cINTENT_EXTRA_CUENTA_PARA = "CUENTA_PARA";
    public static final String cINTENT_EXTRA_CUENTA_PROPIA = "cINTENT_EXTRA_CUENTA_PROPIA";
    public static final String cINTENT_EXTRA_DATA_ORIGEN_EDITAR = "EDITAR";
    public static final String cINTENT_EXTRA_DATA_ORIGEN_ELIMINAR = "ELIMINAR";
    public static final String cINTENT_EXTRA_DATA_ORIGEN_NUEVO = "NUEVO";
    public static final String cINTENT_EXTRA_DATA_TIPO_DE_CUENTA = "Tipo de cuenta";
    public static final String cINTENT_EXTRA_DATA_TIPO_DE_CUENTA_PROPIA = "Tipo de Cuenta: Propia";
    public static final String cINTENT_EXTRA_DESTINATARIO = "DESTINATARIO";
    public static final String cINTENT_EXTRA_DESTINATARIOS_WS = "DESTINATARIOS_WS";
    public static final String cINTENT_EXTRA_DESTINATARIO_BSR_BEAN = "DESTINATARIOBSRBEAN";
    public static final String cINTENT_EXTRA_DESTINATARIO_OB_BEAN = "DESTINATARIOOBBEAN";
    public static final String cINTENT_EXTRA_EJECUTAR_ALTA = "EJECUTAR_ALTA";
    public static final String cINTENT_EXTRA_EJECUTAR_REASIGNACION = "EJECUTAR_REASIGNACION";
    public static final String cINTENT_EXTRA_FILTER_CURRENCY = "FILTER_CURRENCY";
    public static final String cINTENT_EXTRA_LISTA_DESTINATARIOS = "LISTA_DESTINATARIOS";
    public static final String cINTENT_EXTRA_ORIGEN = "ORIGEN";
    public static final String cINTENT_EXTRA_SOLICITUD_AUMENTO = "SOLICITUD_AUMENTO";
    public static final String cINTENT_EXTRA_VERIFICA_DATOS_TRANSF = "VERIFICA_DATOS_TRANSF";
    public static final int cIntentSeleccionarContactoDeAgenda = 1;

    public static class CuentaBanco {
        public static final String BANCO_SANTANDER_RIO = "SR";
        public static final String OTROS = "OB";
    }

    public static final class Currencies {
        public static final String Dolares = "Dólares";
        public static final String Pesos = "Pesos";
    }

    public static class EjecutarAlta {
        public static final String NO = "N";
        public static final String SI = "S";
    }

    public static class ListadoDestinatarios {

        public static class ViewMode {
            public static final String ABMOnly = "ABM_ONLY";
            public static final String InfoCreate = "CREATE";
            public static final String InfoEdit = "EDIT";
            public static final String InfoMode = "DESTINATARIO_INFO_VISUAL_MODE";
            public static final String Mode = "DESTINATARIOS_VISUAL_MODE";
            public static final String SingleChoice = "SINGLE_CHOICE";
        }
    }

    public static class Origen {
        public static final String ORIGEN_NUEVO = "NuevoAgenda";
        public static final String ORIGEN_PARA = "Para";
    }

    public static final class SymbolCurrencies {
        public static final String Dolares = "U$S";
        public static final String Pesos = "$";
    }

    public static class TipoAlta {
        public static final String ALIAS = "Alias";
        public static final String CBU = "CBU/CVU";
        public static final String CUENTABSR = "BSR";
        public static final String MANUAL = "MANUAL";
    }

    public static class TipoCuentaDestino {
        public static final String CAJA_AHORRO_DOLARES = "12";
        public static final String CAJA_AHORRO_PESOS = "11";
        public static final String CUENTA_CORRIENTE_DOLARES = "02";
        public static final String CUENTA_CORRIENTE_PESOS = "01";
    }

    public static class TipoDestino {
        public static final String ALTA_OTROS_BANCOS = "05";
        public static final String ALTA_TERCEROS_BSR = "04";
        public static final String OTROS_BANCOS = "03";
        public static final String PROPIAS = "01";
        public static final String TERCEROS_BSR = "02";
    }

    public static final class TipoMoneda {
        public static final String Dolares = "USD";
        public static final String Pesos = "ARS";
    }

    public static class TipoOperacion {
        public static final String Alta = "A";
        public static final String Baja = "B";
        public static final String Modificacion = "M";
        public static final String Reasignacion = "R";
    }

    public static final class accionVerificarDatosTransf {
        public static final String EDITAR = "EDITAR_DEST";
        public static final String SELECCION = "SELECCION_DEST";
    }

    public static final class leyendas {
        public static final Integer DIA = Integer.valueOf(24);
        public static final String HORAS = "TIS_HORAS";
        public static final String TRANSF_SOL_AL_1 = "TRANSF_SOL_AL_1";
        public static final String TRANSF_SOL_AL_2 = "TRANSF_SOL_AL_2";
    }
}
