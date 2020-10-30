package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.ListaCuentas;
import com.google.gson.annotations.SerializedName;

public class ConsultaSolicitudCrediticiaBodyRequestBean {
    @SerializedName("listaCuentas")
    ListaCuentas listaCuentas;

    public ListaCuentas getListaCuentas() {
        return this.listaCuentas;
    }

    public void setListaCuentas(ListaCuentas listaCuentas2) {
        this.listaCuentas = listaCuentas2;
    }
}
