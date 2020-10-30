package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GetSimularPagosBodyRequestBean {
    String SucCtaDebito;
    String importeCuota;
    String nroCredito;
    String nroCtaDebito;
    String sucCredito;
    String tipoCredito;
    String tipoCtaDebito;

    public GetSimularPagosBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.SucCtaDebito = str;
        this.tipoCtaDebito = str2;
        this.nroCtaDebito = str3;
        this.tipoCredito = str4;
        this.sucCredito = str5;
        this.nroCredito = str6;
        this.importeCuota = str7;
    }
}
