package ar.com.santander.rio.mbanking.services.soap.beans.body;

public class GeneraMandatoEnvMandatoBean {
    public String importe;
    public String numeroDocDest;
    public String tipoDocDest;

    public GeneraMandatoEnvMandatoBean() {
    }

    public GeneraMandatoEnvMandatoBean(String str, String str2, String str3) {
        this.importe = str;
        this.tipoDocDest = str2;
        this.numeroDocDest = str3;
    }
}
