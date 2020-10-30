package ar.com.santander.rio.mbanking.app.ui.constants;

import android.content.Context;
import android.text.Html;
import ar.com.santander.rio.mbanking.app.commons.accessibilty.CAccessibility;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class FondosConstants {
    public static final String ACCION_RESCATAR = "RESCATAR_FONDO";
    public static final String ACCION_SUSCRIBIR_CUOTAPARTES = "ACCION_SUSCRIBIR_CUOTAPARTES";
    public static final String ACCION_SUSCRIBIR_NUEVO = "ACCION_SUSCRIBIR_NUEVO";
    public static final String ACCION_TRANSFERIR = "TRANSFERIR_FONDOS";
    public static final String FONDO_NO_SUSCRIBIBLE = "N";
    public static final String FONDO_SUSCRIBIBLE = "S";
    public static final int INTENT_BUSQUEDA_AVANZADA = 2;
    public static final int INTENT_DETALLE = 1;
    public static final String INTENT_EXTRA_ACCION = "ACCION";
    public static final String INTENT_EXTRA_CATEGORIAS = "CATEGORIAS";
    public static final String INTENT_EXTRA_CONTRATO = "CONTRATO";
    public static final String INTENT_EXTRA_CONTRATO_SUSC = "CONTRATO_SUSC";
    public static final String INTENT_EXTRA_CONTRATO_TRANSF = "CONTRATO_TRANSF";
    public static final String INTENT_EXTRA_CUENTA = "CUENTA";
    public static final String INTENT_EXTRA_DETALLE_FONDO = "DETALLE_FONDO";
    public static final String INTENT_EXTRA_DET_CUENTA = "DET_CUENTA";
    public static final String INTENT_EXTRA_FONDO = "FONDO";
    public static final String INTENT_EXTRA_FONDOS = "FONDOS";
    public static final String INTENT_EXTRA_INFOADM_VIEWMODE = "VIEWMODE";
    public static final String INTENT_EXTRA_LEGALES = "LEGALES";
    public static final String INTENT_EXTRA_LST_CUENTAS = "CUENTAS";
    public static final String INTENT_EXTRA_LST_CUENTAS_OPERATIVAS = "CUENTAS_OPERATIVAS";
    public static final String INTENT_EXTRA_LST_MOVIMIENTOS = "LISTA_MOVIMIENTOS";
    public static final String INTENT_EXTRA_ORIGEN = "ORIGEN";
    public static final String INTENT_EXTRA_RECARGAR_FONDOS = "RECARGAR_LISTADO_FONDOS";
    public static final int INTENT_INFORMACION_FONDO = 3;
    public static final int INTENT_RESCATAR_FONDO = 4;
    public static final int INTENT_SUSCRIBIR_FONDO = 6;
    public static final int INTENT_TRANSFERIR_FONDO = 5;
    public static final String MONEDA_NUMERICO_DOLARES = "2";
    public static final String MONEDA_NUMERICO_PESOS = "0";
    public static final String ONBOARDING = "onBoardingfondos";
    public static final String ORIGEN_BUSQUEDA_AVANZADA = "BUSQUEDA_AVANZADA";
    public static final String ORIGEN_DETALLE = "DETALLE_FONDO";
    public static final String ORIGEN_INFORMACION = "INFORMACION_FONDOS";
    public static final String ORIGEN_SUSCRIBIR = "SUSCRIBIR_FONDO";
    public static final String ORIGEN_TENENCIAS = "TENENCIAS_FONDOS";
    public static final String ORIGEN_TRANSFERIR = "TRANSFERIR_FONDO";
    public static final String ORIGEN_ULTIMOS_MOVIMIENTOS = "ULTIMOS_MOVIMIENTOS";

    public class InfoAdmViewMode {
        public static final String VIEWMODE_HONORARIOS = "HONORARIOS";
        public static final String VIEWMODE_HORARIOS = "HORARIOS";

        public InfoAdmViewMode() {
        }
    }

    public class LeyendasLegales {
        public static final String SIMULACION = "FCI_SIMULACION";
        public static final String TENENCIA = "FCI_TENENCIA";

        public LeyendasLegales() {
        }
    }

    public static String applyAccesibilityFilterName(Context context, String str) {
        String str2 = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(str);
            sb.append(UtilsCuentas.SEPARAOR2);
            String sb2 = sb.toString();
            try {
                str2 = sb2.replace(" I ", " uno ");
                sb2 = str2.replace(" II ", " dos ");
                str2 = sb2.replace(" III ", " tres ");
                sb2 = str2.replace(" IV ", " cuatro ");
                str2 = sb2.replace(" V ", " cinco ");
                sb2 = str2.replace(" VI ", " seis ");
                sb2.trim();
                return CAccessibility.getInstance(context).applyFilterGeneral(Html.fromHtml(sb2).toString());
            } catch (Exception e) {
                e = e;
                str2 = sb2;
                e.printStackTrace();
                return str2;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            return str2;
        }
    }
}
