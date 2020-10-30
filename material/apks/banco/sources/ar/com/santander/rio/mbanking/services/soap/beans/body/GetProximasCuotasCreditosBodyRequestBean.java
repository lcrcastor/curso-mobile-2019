package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetProximasCuotasCreditosBodyRequestBean {
    String nroCredito;
    String sucCredito;
    String tipoCredito;

    public GetProximasCuotasCreditosBodyRequestBean(String str, String str2, String str3) {
        this.nroCredito = str;
        this.sucCredito = str2;
        this.tipoCredito = str3;
    }
}
