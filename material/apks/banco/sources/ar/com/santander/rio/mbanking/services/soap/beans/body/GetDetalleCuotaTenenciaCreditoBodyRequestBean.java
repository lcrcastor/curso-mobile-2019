package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetDetalleCuotaTenenciaCreditoBodyRequestBean {
    String nroCredito;
    String sucCredito;
    String tipoCredito;

    public GetDetalleCuotaTenenciaCreditoBodyRequestBean(String str, String str2, String str3) {
        this.sucCredito = str;
        this.nroCredito = str2;
        this.tipoCredito = str3;
    }
}
