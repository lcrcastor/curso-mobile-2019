package ar.com.santander.rio.mbanking.app.commons;

import ar.com.santander.rio.mbanking.services.soap.beans.body.Leyenda;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaLeyendas;

public class CLegend {

    public enum TypeLegend {
        PLAFIJ_LEG,
        PLAFIJ_AUT,
        PLAFIJ_VER,
        PLAFIJ_REC,
        PAG_COMP,
        PAG_REC
    }

    private CLegend() {
    }

    public static CLegend getInstance() {
        return new CLegend();
    }

    public Leyenda getLegend(ListaLeyendas listaLeyendas, TypeLegend typeLegend) {
        if (listaLeyendas != null) {
            try {
                if (listaLeyendas.lstLeyendas != null) {
                    for (Leyenda leyenda : listaLeyendas.lstLeyendas) {
                        if (typeLegend.name().toLowerCase().equals(leyenda.idLeyenda.toLowerCase())) {
                            return leyenda;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public String getDescriptionLegend(ListaLeyendas listaLeyendas, TypeLegend typeLegend) {
        return getDescriptionLegend(listaLeyendas, typeLegend, null);
    }

    public String getDescriptionLegend(ListaLeyendas listaLeyendas, TypeLegend typeLegend, String str) {
        Leyenda legend = getLegend(listaLeyendas, typeLegend);
        return legend != null ? legend.descripcion : str;
    }
}
