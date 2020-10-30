package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class TipoImpuestoBean {
    public String descImpuesto;
    public String impExtranj;
    public String impLocal;
    public String marcaCobro;
    public String tipoImpuesto;

    public TipoImpuestoBean() {
    }

    public TipoImpuestoBean(String str, String str2, String str3, String str4, String str5) {
        this.tipoImpuesto = str;
        this.descImpuesto = str2;
        this.impExtranj = str3;
        this.marcaCobro = str4;
        this.impLocal = str5;
    }
}
