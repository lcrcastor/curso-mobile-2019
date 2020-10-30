package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ABMDestinatarioTransfBodyResponseBean extends ErrorBodyBean {
    @SerializedName("fechaOperacion")
    public String fechaOperacion;
    @SerializedName("nroComprobante")
    public String nroComprobante;

    public ABMDestinatarioTransfBodyResponseBean() {
    }

    public ABMDestinatarioTransfBodyResponseBean(String str, String str2) {
        this.nroComprobante = str;
        this.fechaOperacion = str2;
    }
}
