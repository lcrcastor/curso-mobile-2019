package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetPromocionesBodyResponseBean extends ErrorBodyBean {
    public String urlPromociones;

    public GetPromocionesBodyResponseBean() {
    }

    public GetPromocionesBodyResponseBean(String str) {
        this.urlPromociones = str;
    }
}
