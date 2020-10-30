package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.ListaLeyenda;
import com.google.gson.annotations.SerializedName;

public class GetLimitesExtraccionBodyResponseBean extends ErrorBodyBean {
    @SerializedName("listaLimitesPermanentes")
    public ListaLimitesPermanentesBean limitesPermanentes;
    @SerializedName("listaLimitesTemporales")
    public ListaLimitesTemporalesBean limitesTemporales;
    @SerializedName("listaLeyendas")
    public ListaLeyenda listaLeyendas;

    public ListaLeyenda getListaLeyendas() {
        return this.listaLeyendas;
    }

    public void setListaLeyendas(ListaLeyenda listaLeyenda) {
        this.listaLeyendas = listaLeyenda;
    }

    public ListaLimitesPermanentesBean getLimitesPermanentes() {
        return this.limitesPermanentes;
    }

    public void setLimitesPermanentes(ListaLimitesPermanentesBean listaLimitesPermanentesBean) {
        this.limitesPermanentes = listaLimitesPermanentesBean;
    }

    public ListaLimitesTemporalesBean getLimitesTemporales() {
        return this.limitesTemporales;
    }

    public void setLimitesTemporales(ListaLimitesTemporalesBean listaLimitesTemporalesBean) {
        this.limitesTemporales = listaLimitesTemporalesBean;
    }
}
