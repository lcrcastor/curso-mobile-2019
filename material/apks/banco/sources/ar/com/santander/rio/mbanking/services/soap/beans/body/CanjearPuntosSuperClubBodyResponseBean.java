package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CanjearPuntosSuperClubBodyResponseBean extends ErrorBodyBean {
    @SerializedName("fechaCanje")
    public String fechaCanje;
    @SerializedName("leyenda")
    public ListaLeyendasSuperClubBean listaLeyendas;
    @SerializedName("nroComprobante")
    public String nroComprobante;

    public CanjearPuntosSuperClubBodyResponseBean() {
        this.listaLeyendas = new ListaLeyendasSuperClubBean();
    }

    public CanjearPuntosSuperClubBodyResponseBean(String str, String str2, ListaLeyendasSuperClubBean listaLeyendasSuperClubBean) {
        this.nroComprobante = str;
        this.fechaCanje = str2;
        this.listaLeyendas = listaLeyendasSuperClubBean;
    }
}
