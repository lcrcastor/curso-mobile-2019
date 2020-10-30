package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.services.model.creditos.ListaDestino;
import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import ar.com.santander.rio.mbanking.services.model.general.ListaLeyendaProd;
import java.util.List;

public class CCreditosProd {
    public static String TASA_FIJA = "Fija";
    public static String TASA_VARIABLE = "Variable";

    public static String formatTipoTasa(String str) {
        if (str.equalsIgnoreCase("F")) {
            return TASA_FIJA;
        }
        return str.equalsIgnoreCase("V") ? TASA_VARIABLE : "";
    }

    public static String getLeyenda(ListaLeyendaProd listaLeyendaProd, String str) {
        if (!(listaLeyendaProd == null || listaLeyendaProd.getLeyendaList() == null)) {
            for (Leyenda leyenda : listaLeyendaProd.getLeyendaList()) {
                if (leyenda.getIdLeyenda().equalsIgnoreCase(str)) {
                    return leyenda.getDescLeyenda();
                }
            }
        }
        return "";
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
