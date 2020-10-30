package ar.com.santander.rio.mbanking.utils;

import ar.com.santander.rio.mbanking.services.soap.beans.body.PagoServiciosBodyResponseBean;

public class UtilsSube {
    public static String separarIdentificadorSube(String str) {
        return str.replaceAll(UtilsCuentas.SEPARAOR2, "").replaceAll("(.{4})", "$1 ").trim();
    }

    public static String importeSinDecimales(String str) {
        int indexOf = str.indexOf(",");
        return indexOf > 0 ? str.substring(0, indexOf) : str;
    }

    public static String importePesosSeparado(String str) {
        if (!str.contains("$")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("$ ");
        sb.append(str.replace("$", ""));
        return sb.toString();
    }

    public static String subeImporteformater(String str) {
        return importePesosSeparado(importeSinDecimales(str));
    }

    public static String fechaRecarga(PagoServiciosBodyResponseBean pagoServiciosBodyResponseBean) {
        String str;
        String str2 = "%s %s";
        Object[] objArr = new Object[2];
        objArr[0] = pagoServiciosBodyResponseBean.fecha;
        if (pagoServiciosBodyResponseBean.hora.isEmpty()) {
            str = "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(pagoServiciosBodyResponseBean.hora);
            sb.append(" hs.");
            str = sb.toString();
        }
        objArr[1] = str;
        return String.format(str2, objArr).trim();
    }
}
