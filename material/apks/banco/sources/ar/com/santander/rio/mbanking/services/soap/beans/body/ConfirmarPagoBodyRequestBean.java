package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class ConfirmarPagoBodyRequestBean {
    String identificadorUVA;
    String importeCuota;
    String nroCredito;
    String nroCtaDebito;
    String sucCredito;
    String sucCtaDebito;
    String tipoCredito;
    String tipoCtaDebito;

    public ConfirmarPagoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        this.sucCtaDebito = str;
        this.tipoCtaDebito = str2;
        this.nroCtaDebito = str3;
        this.sucCredito = str4;
        this.tipoCredito = str5;
        this.nroCredito = str6;
        this.importeCuota = str7;
        this.identificadorUVA = str8;
    }
}
