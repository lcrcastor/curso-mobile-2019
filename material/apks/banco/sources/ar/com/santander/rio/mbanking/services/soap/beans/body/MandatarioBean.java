package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class MandatarioBean {
    public String descCtaDebito;
    public String numeroCuenta;
    public String sucursalCuenta;
    public String tipoCuenta;

    public MandatarioBean() {
    }

    public MandatarioBean(String str, String str2, String str3, String str4) {
        this.tipoCuenta = str;
        this.sucursalCuenta = str2;
        this.numeroCuenta = str3;
        this.descCtaDebito = str4;
    }
}
