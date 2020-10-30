package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class CnsDeudaRecargaBodyRequestBean {
    public String tipoDeuda;

    public CnsDeudaRecargaBodyRequestBean(String str) {
        this.tipoDeuda = str;
    }
}
