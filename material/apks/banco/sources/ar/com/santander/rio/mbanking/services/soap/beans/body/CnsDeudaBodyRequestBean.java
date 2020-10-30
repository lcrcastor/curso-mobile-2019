package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class CnsDeudaBodyRequestBean {
    public String tipoDeuda;

    public CnsDeudaBodyRequestBean(String str) {
        this.tipoDeuda = str;
    }
}
