package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GeneraMandatoExtEnvMandatarioBean {
    public String numeroCuenta;
    public String sucursalCuenta;
    public String tipoCuenta;

    public GeneraMandatoExtEnvMandatarioBean() {
    }

    public GeneraMandatoExtEnvMandatarioBean(String str, String str2, String str3) {
        this.tipoCuenta = str;
        this.sucursalCuenta = str2;
        this.numeroCuenta = str3;
    }
}
