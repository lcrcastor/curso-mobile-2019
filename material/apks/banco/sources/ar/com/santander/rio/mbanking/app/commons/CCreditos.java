package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.app.module.creditos.model.Resultado;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.services.model.creditos.ListaDestino;
import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyenda;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosCredito;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.DatosLeyenda;
import ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel.Listaleyendas;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetDetalleCuotaTenenciaCreditoBodyResponseBean;
import java.util.List;

public class CCreditos {
    public static String TASA_FIJA = "Fija";
    public static String TASA_TRADICIONAL = "Tradicional";
    public static final String TASA_UVA = "UVA";
    public static String TASA_VARIABLE = "Variable";

    public static String formatTipoTasa(String str, String str2) {
        if (str2 != null && str2.equalsIgnoreCase("S")) {
            return "UVA";
        }
        if (str2 != null && str2.equalsIgnoreCase("N")) {
            return TASA_TRADICIONAL;
        }
        if (str.equalsIgnoreCase("F")) {
            return TASA_FIJA;
        }
        return str.equalsIgnoreCase("V") ? TASA_VARIABLE : "";
    }

    public static String formatTipoIsUVA(String str) {
        if (str == null || !str.equalsIgnoreCase("S")) {
            return (str == null || !str.equalsIgnoreCase("N")) ? "" : TASA_TRADICIONAL;
        }
        return "UVA";
    }

    public static String formatTipoTasaVariableFija(String str) {
        if (str.equalsIgnoreCase("F")) {
            return TASA_FIJA;
        }
        return str.equalsIgnoreCase("V") ? TASA_VARIABLE : "";
    }

    public static String getLeyenda(ListaLeyenda listaLeyenda, String str) {
        if (!(listaLeyenda == null || listaLeyenda.getLeyendaList() == null)) {
            for (Leyenda leyenda : listaLeyenda.getLeyendaList()) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase(str)) {
                    return leyenda.getDescLeyenda();
                }
            }
        }
        return "";
    }

    public static String getLeyendaTasa(ListaLeyenda listaLeyenda, String str, String str2) {
        String str3 = "";
        if (!(listaLeyenda == null || listaLeyenda.getLeyendaList() == null || str2 == null || str2.isEmpty())) {
            for (Leyenda leyenda : listaLeyenda.getLeyendaList()) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase(str)) {
                    return leyenda.getDescLeyenda();
                }
            }
        }
        return str3;
    }

    public static String getLeyendaTasaDetalleCuota(Listaleyendas listaleyendas, GetDetalleCuotaTenenciaCreditoBodyResponseBean getDetalleCuotaTenenciaCreditoBodyResponseBean) {
        StringBuilder sb = new StringBuilder();
        if (!(listaleyendas == null || listaleyendas.getDatosleyenda() == null)) {
            for (DatosLeyenda datosLeyenda : listaleyendas.getDatosleyenda()) {
                if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TNA) && getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaTNA() != null && !getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaTNA().isEmpty()) {
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TEA) && getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTasaTEA() != null && !getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTasaTEA().isEmpty()) {
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA) && getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCFTNA() != null && !getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCFTNA().isEmpty()) {
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA_SIMP) && getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCTFNAIVA() != null && !getDetalleCuotaTenenciaCreditoBodyResponseBean.getCreTtasaCTFNAIVA().isEmpty()) {
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                }
            }
        }
        return sb.toString();
    }

    public static String getLeyendaTasa(Listaleyendas listaleyendas, DatosCredito datosCredito) {
        String str = "";
        if (!(listaleyendas == null || listaleyendas.getDatosleyenda() == null)) {
            for (DatosLeyenda datosLeyenda : listaleyendas.getDatosleyenda()) {
                if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TNA) && datosCredito.getTasatna() != null && !datosCredito.getTasatna().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                    str = sb.toString();
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TEA) && datosCredito.getTasatea() != null && !datosCredito.getTasatea().isEmpty()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(datosLeyenda.getDescripcion());
                    sb2.append("\n\n");
                    str = sb2.toString();
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA) && datosCredito.getTasacftna() != null && !datosCredito.getTasacftna().isEmpty()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(datosLeyenda.getDescripcion());
                    sb3.append("\n\n");
                    str = sb3.toString();
                } else if (datosLeyenda.getIdleyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA_SIMP) && datosCredito.getTasactfnaiva() != null && !datosCredito.getTasactfnaiva().isEmpty()) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(datosLeyenda.getDescripcion());
                    sb4.append("\n\n");
                    str = sb4.toString();
                }
            }
        }
        return str;
    }

    public static String getLeyendaTasa(ListaLeyenda listaLeyenda, Resultado resultado) {
        String str = "";
        if (!(listaLeyenda == null || listaLeyenda.getLeyendaList() == null)) {
            for (Leyenda leyenda : listaLeyenda.getLeyendaList()) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TNA) && resultado.getTasaNominalAnual() != null && !resultado.getTasaNominalAnual().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(leyenda.getDescLeyenda());
                    sb.append("\n\n");
                    str = sb.toString();
                } else if (leyenda.getIdLeyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_TEA) && resultado.getTasaEfectAnual() != null && !resultado.getTasaEfectAnual().isEmpty()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(leyenda.getDescLeyenda());
                    sb2.append("\n\n");
                    str = sb2.toString();
                } else if (leyenda.getIdLeyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA) && resultado.getTasaCFTNA() != null && !resultado.getTasaCFTNA().isEmpty()) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(leyenda.getDescLeyenda());
                    sb3.append("\n\n");
                    str = sb3.toString();
                } else if (leyenda.getIdLeyenda().equalsIgnoreCase(Constants.LEYENDA_TASA_CRED_CFTEA_SIMP) && resultado.getTasaCFTNAIVA() != null && !resultado.getTasaCFTNAIVA().isEmpty()) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(leyenda.getDescLeyenda());
                    sb4.append("\n\n");
                    str = sb4.toString();
                }
            }
        }
        return str;
    }

    public static String getLeyendaTasa(ListaLeyenda listaLeyenda, String[] strArr) {
        String str = "";
        if (listaLeyenda == null || listaLeyenda.getLeyendaList() == null) {
            return str;
        }
        String str2 = str;
        int i = 0;
        while (i < listaLeyenda.getLeyendaList().size()) {
            Leyenda leyenda = (Leyenda) listaLeyenda.getLeyendaList().get(i);
            String str3 = str2;
            for (String equalsIgnoreCase : strArr) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase(equalsIgnoreCase)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str3);
                    sb.append(leyenda.getDescLeyenda());
                    sb.append("\n\n");
                    str3 = sb.toString();
                }
            }
            i++;
            str2 = str3;
        }
        return str2;
    }

    public static String getLeyendaTasa(Listaleyendas listaleyendas, String[] strArr) {
        String str = "";
        if (listaleyendas == null || listaleyendas.getDatosleyenda() == null) {
            return str;
        }
        String str2 = str;
        int i = 0;
        while (i < listaleyendas.getDatosleyenda().size()) {
            DatosLeyenda datosLeyenda = (DatosLeyenda) listaleyendas.getDatosleyenda().get(i);
            String str3 = str2;
            for (String equalsIgnoreCase : strArr) {
                if (datosLeyenda.getIdleyenda().equalsIgnoreCase(equalsIgnoreCase)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str3);
                    sb.append(datosLeyenda.getDescripcion());
                    sb.append("\n\n");
                    str3 = sb.toString();
                }
            }
            i++;
            str2 = str3;
        }
        return str2;
    }

    public static String getDestino(List<ListaDestino> list, String str) {
        for (ListaDestino listaDestino : list) {
            if (str.equalsIgnoreCase(listaDestino.getDestPrestamo())) {
                return listaDestino.getDestFondo();
            }
        }
        return "";
    }
}
